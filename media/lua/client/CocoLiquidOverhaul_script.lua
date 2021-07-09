-- TakeFuelBigWaterBottle_DoAction
local function TakeFuelBigWaterBottle_DoAction(worldobjects, playerObj, square, petrolCan)
	
	playerObj:StopAllActionQueue()

	-- let's equip it
	if playerObj:getPrimaryHandItem() ~= petrolCan and playerObj:getSecondaryHandItem() ~= petrolCan then
		ISInventoryPaneContextMenu.equipWeapon(petrolCan, true, true, playerObj:getPlayerNum())
	end

	-- let's start the timed action
	ISTimedActionQueue.add(CLO_ActionTakeFuel:new(playerObj, square, petrolCan, 5000))
end

-- PourGasInto_DoAction
local function PourGasInto_DoAction(playerObj, itemFrom, itemTo)
	local inventory = playerObj:getInventory()

	playerObj:StopAllActionQueue()

	-- transform empty big water bottle
	if itemTo:getType() == "Coco_WaterGallonEmpty" then
		-- transform empty gas can
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
	ISTimedActionQueue.add(CLO_ActionPourInto:new(playerObj, itemFrom, itemTo, itemFromEndingDelta, itemToEndingDelta))
end

-- TakeFuelBigWaterBottle_Context
local function TakeFuelBigWaterBottle_Context(playerNum, context, worldobjects, test)
	if test == true then return true end

	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local square = playerObj:getCurrentSquare()

	local fuelAmount = 0
	local petrolCan = nil
	local pumpFound = false

	-- Get square around player
	for y = square:getY() - 1, square:getY() + 1 do
		if pumpFound then break end
		for x = square:getX() - 1, square:getX() + 1 do
			if pumpFound then break end

			local sq = getCell():getGridSquare(x, y, square:getZ())
			if not sq then break end

			-- Check if there is fuel on that square
			if sq:getProperties():Is("fuelAmount") then

				-- Store the fuel amount
				fuelAmount = tonumber(sq:getProperties():Val("fuelAmount"))

				-- If there is fuel let's continue
				if fuelAmount > 0 then

					-- Let's find an not empty petrol can first
					local notEmptyPetrolCan = CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
					if notEmptyPetrolCan then
						-- We didnt find any let's find an empty one
						petrolCan = notEmptyPetrolCan
					else
						local emptyPetrolCan = inventory:getFirstTypeRecurse("Coco_WaterGallonEmpty")
						if emptyPetrolCan then
							petrolCan = emptyPetrolCan
						end
					end

					if petrolCan ~= nil then
						context:addOption(getText("ContextMenu_TakeGasFromPumpWithBigWaterBottle"), worldobjects, TakeFuelBigWaterBottle_DoAction, playerObj, sq, petrolCan)
					end

					pumpFound = true
				end
			end
		end
	end
end

-- CheckLiquidContainerContent_Context
local function CheckLiquidContainerContent_Context(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum)
	local inventory = playerObj:getInventory()
	local mainContainer = nil
	local isWater = false
	local isMilk = false
	local isAlcohol = false
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
				itemType == "Coco_WaterGallonFull" or itemType == "WaterBleachBottle" or itemType == "WaterBowl" or itemType == "BucketWaterFull" or itemType == "WaterPot" or itemType == "WaterMug" or itemType == "WaterPaintbucket" or itemType == "WaterSaucepan" or itemType == "BeerWaterFull" or
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
		local storageAvailable = CLO_Funcs.Round(1 / mainContainer:getUseDelta())
		local storageContain = CLO_Funcs.Round(storageAvailable * mainContainer:getUsedDelta())

		local option = context:addOption(getText("ContextMenu_Liquid_container_info"))
		local tooltip = ISWorldObjectContextMenu.addToolTip()
		if isWater then
			tooltip.description = getText("ContextMenu_Liquid_water_name")
		elseif isMilk then
			tooltip.description = getText("ContextMenu_Liquid_milk_name")
		elseif isAlcohol then
			tooltip.description = getText("ContextMenu_Liquid_alcohol_name")
		else
			tooltip.description = getText("ContextMenu_Liquid_petrol_name")
		end
		tooltip.description = tooltip.description .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
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
		local pourableContainers = CLO_Funcs.GetAllPetrolPourableContainer(inventory)

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
					local storageAvailable = CLO_Funcs.Round(1 / item:getUseDelta())
					local storageContain = CLO_Funcs.Round(storageAvailable * item:getUsedDelta())
					local option = subMenu:addOption(itemName, playerObj, PourGasInto_DoAction, mainContainer, item)
					local tooltip = ISWorldObjectContextMenu.addToolTip()
					tooltip.description = getText("ContextMenu_Liquid_petrol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
					option.toolTip = tooltip
				else
					subMenu:addOption(itemName, playerObj, PourGasInto_DoAction, mainContainer, item)
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

				if (luautils.walkAdj(playerObj, sq) and CLO_Funcs.IsObjectWaterDispenser(obj)) then
					CLO_Funcs.FixWaterDispenser(obj)

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
	if not CLO_ModSettings.Debug then
		return false
	end

	local playerObj = getSpecificPlayer(playerNum)
	local square = playerObj:getCurrentSquare()

	if not CLO_Funcs.HasDispenserOnSquare(square) then
		local option = context:addOption("DEBUG Dispenser")
		local subMenu = context:getNew(context)
		context:addSubMenu(option, subMenu)

		local option1 = context:addOption("Create Empty Dispenser")
		local subMenu1 = context:getNew(context)
		subMenu:addSubMenu(option1, subMenu1)
		subMenu1:addOption("Facing North", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Empty.N)
		subMenu1:addOption("Facing East", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Empty.E)
		subMenu1:addOption("Facing South", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Empty.S)
		subMenu1:addOption("Facing West", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Empty.W)

		local option2 = context:addOption("Create Water Dispenser")
		local subMenu2 = context:getNew(context)
		subMenu:addSubMenu(option2, subMenu2)
		subMenu2:addOption("Facing North", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Water.N)
		subMenu2:addOption("Facing East", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Water.E)
		subMenu2:addOption("Facing South", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Water.S)
		subMenu2:addOption("Facing West", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Water.W)

		local option3 = context:addOption("Create Gas Dispenser")
		local subMenu3 = context:getNew(context)
		subMenu:addSubMenu(option3, subMenu3)
		subMenu3:addOption("Facing North", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Petrol.N)
		subMenu3:addOption("Facing East", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Petrol.E)
		subMenu3:addOption("Facing South", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Petrol.S)
		subMenu3:addOption("Facing West", playerObj, CLO_Funcs.CreateWaterDispenser, square, CLO_CustomDispenser.Petrol.W)
	end
end

Events.OnPreFillWorldObjectContextMenu.Add(TakeFuelBigWaterBottle_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(CheckLiquidContainerContent_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(PouGasInto_Context)
Events.OnPreFillWorldObjectContextMenu.Add(InteractWaterDispenser_Context)
Events.OnPreFillWorldObjectContextMenu.Add(Debug_Context)
