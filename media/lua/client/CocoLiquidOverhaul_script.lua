-- TakeFuel_DoAction
local function TakeFuel_DoAction(worldobjects, playerObj, square, petrolCan, pumpObject)
	-- let's equip it
	if playerObj:getPrimaryHandItem() ~= petrolCan and playerObj:getSecondaryHandItem() ~= petrolCan then
		ISInventoryPaneContextMenu.equipWeapon(petrolCan, true, true, playerObj:getPlayerNum())
	end

	-- let's start the timed action
	ISTimedActionQueue.add(CocoLiquidOverhaulActionTakeFuel:new(playerObj, square, petrolCan, pumpObject, 5000))
end

-- PourInto_DoAction
local function PourInto_DoAction(playerObj, itemFrom, itemTo)
	local inventory = playerObj:getInventory()

	if itemTo:getType() == "Coco_WaterGallonEmpty" then
		inventory:Remove(itemTo)
		itemTo = inventory:AddItem("CocoLiquidOverhaulItems.Coco_WaterGallonPetrol")
		itemTo:setUsedDelta(0)
	elseif itemTo:getType() == "EmptyPetrolCan" then
		inventory:Remove(itemTo)
		itemTo = inventory:AddItem("Base.PetrolCan")
		itemTo:setUsedDelta(0)
	end

	local petrolStorageAvailable = (1 - itemTo:getUsedDelta()) / itemTo:getUseDelta()
	local petrolStorageNeeded = itemFrom:getUsedDelta() / itemFrom:getUseDelta()

	local itemFromEndingDelta = 0
	local itemToEndingDelta = nil

	if petrolStorageAvailable >= petrolStorageNeeded then
		local petrolInA = itemTo:getUsedDelta() / itemTo:getUseDelta()
		local petrolInB = itemFrom:getUsedDelta() / itemFrom:getUseDelta()
		local totalPetrol = petrolInA + petrolInB

		itemToEndingDelta = totalPetrol * itemTo:getUseDelta()
		itemFromEndingDelta = 0
	elseif petrolStorageAvailable < petrolStorageNeeded then
		local petrolInB = itemFrom:getUsedDelta() / itemFrom:getUseDelta()
		local petrolRemainInB = petrolInB - petrolStorageAvailable

		itemFromEndingDelta = petrolRemainInB * itemFrom:getUseDelta()
		itemToEndingDelta = 1
	end

	-- let's start the timed action
	ISTimedActionQueue.add(
		CocoLiquidOverhaulActionPourInto:new(playerObj, itemFrom, itemTo, itemFromEndingDelta, itemToEndingDelta)
	)
end

-- TakeFuel_Context
local function TakeFuel_Context(playerNum, context, worldobjects, test)
	if test == true then
		return true
	end

	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local square = playerObj:getCurrentSquare()

	local fuelAmount = 0
	local petrolCan = nil

	-- Get square around player
	for y = square:getY() - 1, square:getY() + 1 do
		for x = square:getX() - 1, square:getX() + 1 do
			local sq = getCell():getGridSquare(x, y, square:getZ())
			if not (sq) then
				break
			end

			-- Check if there is fuel on that square
			if sq:getProperties():Is("fuelAmount") then
				-- Store the fuel amount
				fuelAmount = tonumber(sq:getProperties():Val("fuelAmount"))

				-- If there is fuel let's continue
				if fuelAmount > 0 then
					square = sq

					-- Let's find a gas pump object
					local pumpFound = false
					for i = 0, sq:getObjects():size() - 1 do
						local pumpObject = sq:getObjects():get(i)
						local objName = pumpObject:getSprite():getName()
						if objName == "location_shop_fossoil_01_14" or objName == "location_shop_fossoil_01_15" then
							-- Let's find an not empty petrol can first
							local notEmptyPetrolCan = CLO_GetFirstNotEmpty_WaterGallonPetrol(inventory)
							if notEmptyPetrolCan then
								-- We didnt find any let's find an empty one
								petrolCan = notEmptyPetrolCan
							else
								local emptyPetrolCan = inventory:getFirstTypeRecurse("Coco_WaterGallonEmpty")
								if emptyPetrolCan then
									petrolCan = emptyPetrolCan
								end
							end

							if petrolCan then
								context:addOption(
									getText("ContextMenu_TakeGasFromPumpWithBigWaterBottle"),
									worldobjects,
									TakeFuel_DoAction,
									playerObj,
									square,
									petrolCan,
									pumpObject
								)
							end

							pumpFound = true
							break
						end
					end

					-- We found the pump already
					if pumpFound then
						break
					end
				end
			end
		end
	end
end

-- GetLiquidContainerInfo_Context
local function GetLiquidContainerInfo_Context(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local mainContainer = nil
	local isWater = false
	local isTainted = false

	if #items == 1 then
		for i, v in ipairs(items) do
			item = v
			if not instanceof(v, "InventoryItem") then
				item = v.items[1]
			end

			local itemType = item:getType()

			-- We right clicked the WaterGallonPetrol
			if itemType == "Coco_WaterGallonPetrol" or itemType == "PetrolCan" then
				mainContainer = item
				break
			elseif
				itemType == "Coco_WaterGallonFull" or itemType == "WaterBleachBottle" or itemType == "WaterBowl" or
					itemType == "BucketWaterFull" or
					itemType == "WaterPot" or
					itemType == "WaterMug" or
					itemType == "WaterPaintbucket" or
					itemType == "WaterSaucepan" or
					itemType == "BeerWaterFull" or
					itemType == "WaterPopBottle" or
					itemType == "WineWaterFull" or
					itemType == "WhiskeyWaterFull" or
					itemType == "WaterBottleFull" or
					itemType == "GardeningSprayFull" or
					itemType == "WateredCanFull" or
					itemType == "MayonnaiseWaterFull" or
					itemType == "RemouladeWaterFull" or
					itemType == "FullKettle"
			 then
				mainContainer = item
				isWater = true
				break
			end
		end
	end

	if mainContainer then
		local storageAvailable = CLO_Round(1 / mainContainer:getUseDelta())
		local storageContain = CLO_Round(storageAvailable * mainContainer:getUsedDelta())

		local option = context:addOption(getText("ContextMenu_Liquid_container_info"))
		local tooltip = ISWorldObjectContextMenu.addToolTip()
		if isWater then
			tooltip.description =
				getText("ContextMenu_Liquid_water_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
		elseif isAlcohol then
			tooltip.description =
				getText("ContextMenu_Liquid_alcohol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
		else
			tooltip.description =
				getText("ContextMenu_Liquid_petrol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
		end
		option.toolTip = tooltip
	end
end

-- PouGasInto_Context
local function PouGasInto_Context(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local mainContainer = nil

	if #items == 1 then
		for i, v in ipairs(items) do
			item = v
			if not instanceof(v, "InventoryItem") then
				item = v.items[1]
			end

			-- We right clicked the WaterGallonPetrol
			if (item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan") and item:getUsedDelta() > 0 then
				mainContainer = item
				break
			end
		end
	end

	if mainContainer then
		-- Do we have a container to pour in
		local pourableContainers = CLO_GetAllPetrolPourableContainer(inventory)

		-- Let's filter to remove current container
		local availableContainers = {}
		for i, v in ipairs(pourableContainers) do
			local item = v
			if not instanceof(v, "InventoryItem") then
				item = v.items[1]
			end

			if item ~= mainContainer then
				if item:IsDrainable() and item:getUsedDelta() < 1 then
					table.insert(availableContainers, item)
				else
					table.insert(availableContainers, item)
				end
			end
		end

		-- if we still got a pourable container let's make the submenu
		if #availableContainers > 0 then
			local option = context:addOption(getText("ContextMenu_Pour_petrol_into"))
			local subMenu = context:getNew(context)
			context:addSubMenu(option, subMenu)

			-- let's add all item to submenu
			for i, v in ipairs(availableContainers) do
				local item = v
				if not instanceof(v, "InventoryItem") then
					item = v.items[1]
				end

				local itemName = item:getDisplayName()
				if item:IsDrainable() then
					-- itemName = item:getDisplayName() .. " (" .. tostring(math.floor(item:getUsedDelta() * 100)) .. "%)";
					local storageAvailable = CLO_Round(1 / item:getUseDelta())
					local storageContain = CLO_Round(storageAvailable * item:getUsedDelta())
					local option = subMenu:addOption(itemName, playerObj, PourInto_DoAction, mainContainer, item)
					local tooltip = ISWorldObjectContextMenu.addToolTip()
					tooltip.description =
						getText("ContextMenu_Liquid_petrol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
					option.toolTip = tooltip
				else
					--itemName = item:getDisplayName() .. " (0%)";
					subMenu:addOption(itemName, playerObj, PourInto_DoAction, mainContainer, item)
				end
			end
		end
	end
end

-- InteractWaterDispenser_Context
local function InteractWaterDispenser_Context(playerNum, context, worldobjects, test)
	if test == true then
		return true
	end

	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local square = playerObj:getCurrentSquare()

	local dispenserFound = false

	-- Get square around player
	for y = square:getY() - 0.5, square:getY() + 0.5 do
		for x = square:getX() - 0.5, square:getX() + 0.5 do
			local sq = getCell():getGridSquare(x, y, square:getZ())
			if not (sq) then
				break
			end

			-- check square objects
			for i = 0, sq:getObjects():size() - 1 do
				local obj = sq:getObjects():get(i)

				if (luautils.walkAdj(playerObj, sq) and CLO_IsObjectWaterDispenser(obj)) then
					CLO_FixWaterDispenser(obj)

					context:addOption("Dispenser Options", nil)

					dispenserFound = true
					break
				end
				if dispenserFound then
					break
				end
			end
			if dispenserFound then
				break
			end
		end
		if dispenserFound then
			break
		end
	end
end

-- Debug_Context
local function Debug_Context(playerNum, context, worldobjects, test)
	if not ModSettings.Debug then
		return false
	end

	local playerObj = getSpecificPlayer(playerNum)
	local square = playerObj:getCurrentSquare()

	if not CLO_HasDispenserOnSquare(square) then
		context:addOption(
			"Create Dispenser Here",
			playerObj,
			CLO_CreateWaterDispenser,
			square,
			"location_business_office_generic_01_49_empty_0"
		)
	end
end

Events.OnPreFillWorldObjectContextMenu.Add(TakeFuel_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(GetLiquidContainerInfo_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(PouGasInto_Context)
Events.OnPreFillWorldObjectContextMenu.Add(InteractWaterDispenser_Context)
Events.OnPreFillWorldObjectContextMenu.Add(Debug_Context)
