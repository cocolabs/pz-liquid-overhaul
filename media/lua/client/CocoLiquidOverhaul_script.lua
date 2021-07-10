-- TakeFuelBigWaterBottle_DoAction
---@param playerObj IsoPlayer
---@param square IsoGridSquare
---@param petrolCan InventoryItem
local function TakeFuelBigWaterBottle_DoAction(playerObj, square, petrolCan)
	if playerObj:isPerformingAnAction() then return end

	if luautils.walkAdj(playerObj, square) then
		if playerObj:getPrimaryHandItem() ~= petrolCan and playerObj:getSecondaryHandItem() ~= petrolCan then
			ISInventoryPaneContextMenu.equipWeapon(petrolCan, false, false, playerObj:getPlayerNum())
		end
		ISTimedActionQueue.add(CLO_ActionTakeFuel:new(playerObj, square, petrolCan, 5000))
	end
end

-- PourGasInto_DoAction
---@param playerObj IsoPlayer
---@param itemFrom InventoryItem
---@param itemTo InventoryItem
local function PourGasInto_DoAction(playerObj, itemFrom, itemTo)
	if playerObj:isPerformingAnAction() then return end

	local inventory = playerObj:getInventory()

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

	local itemFromEndingDelta, itemToEndingDelta = 0, 0

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
local function TakeFuelBigWaterBottle_Context(playerNum, context, _, test)
	if test then return end

	---@type IsoPlayer
	local playerObj = getSpecificPlayer(playerNum)
	---@type InventoryContainer
	local inventory = playerObj:getInventory()
	---@type IsoGridSquare
	local square = clickedSquare
	---@type InventoryItem
	local petrolCan

	-- Check if the square is adjacent to the player
	if square then

		-- Check if there is fuel on that square
		if CLO_Funcs.IsPetrolAvailableOnSquare(square) then

			-- If there is fuel let's continue
			if CLO_Funcs.GetPetrolAvailableOnSquare(square) > 0 then

				-- Let's find an not empty and not full petrol can first
				petrolCan = CLO_Funcs.GetFirstNotFull_WaterGallonPetrol(inventory)
				if not petrolCan then
					petrolCan = CLO_Funcs.GetFirstObjectOfType(inventory, "Coco_WaterGallonEmpty")
				end

				if petrolCan and instanceof(petrolCan, "InventoryItem") then
					context:addOption(getText("ContextMenu_TakeGasFromPumpWithBigWaterBottle"), playerObj, TakeFuelBigWaterBottle_DoAction, square, petrolCan)
				end
			end
		end

	end
end

-- CheckLiquidContainerContent_Context
local function CheckLiquidContainerContent_Context(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum)
	local mainContainer
	local isWater = false
	local isMilk = false
	local isAlcohol = false
	--local isTainted = false

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
	local mainContainer

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
					local checkOtion = subMenu:addOption(itemName, playerObj, PourGasInto_DoAction, mainContainer, item)
					local tooltip = ISWorldObjectContextMenu.addToolTip()
					tooltip.description = getText("ContextMenu_Liquid_petrol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable)
					checkOtion.toolTip = tooltip
				else
					subMenu:addOption(itemName, playerObj, PourGasInto_DoAction, mainContainer, item)
				end
			end
		end
	end
end

-- InteractWaterDispenser_Context
local function InteractWaterDispenser_Context(playerNum, context, _, test)
	if test then return end

	local playerObj = getSpecificPlayer(playerNum)
	local square = clickedSquare

	if square and CLO_Funcs.IsPlayerNextToSquare(playerObj, square) and CLO_Funcs.HasDispenserOnSquare(square) then
		local obj = CLO_Funcs.GetDispenserObjectOnSquare(square)
		CLO_Funcs.FixWaterDispenser(obj)
		context:addOption("Dispenser Options", nil)
	end
end

-- Debug_Context
local function Debug_Context(playerNum, context, _, test)
	if test or not CLO_ModSettings.Debug then return end

	local playerObj = getSpecificPlayer(playerNum)
	local square = clickedSquare

	if CLO_Funcs.HasDispenserOnSquare(square) then
		local dispenserObj = CLO_Funcs.GetDispenserObjectOnSquare(square)
		local customName = dispenserObj:getProperties():Val("CustomName")
		print("Selected dispenser type: " .. customName)
		local mainOption = context:addOption("[DEBUG] Dispenser")
		local mainSubMenu = context:getNew(context)

		if CLO_Funcs.IsDispenserEmpty(dispenserObj) then
			local addBottleOption = mainSubMenu:addOption("Add Bottle")
			local addBottleSubMenu = mainSubMenu:getNew(mainSubMenu)
			mainSubMenu:addSubMenu(addBottleOption, addBottleSubMenu)
			addBottleSubMenu:addOption("Empty", playerObj, CLO_Funcs.AddBigWaterBottleFromDispenser, dispenserObj, CLO_CustomDispenser.Bottle.type)
			addBottleSubMenu:addOption("Water", playerObj, CLO_Funcs.AddBigWaterBottleFromDispenser, dispenserObj, CLO_CustomDispenser.Water.type)
			addBottleSubMenu:addOption("Gas", playerObj, CLO_Funcs.AddBigWaterBottleFromDispenser, dispenserObj, CLO_CustomDispenser.Petrol.type)

		else mainSubMenu:addOption("Remove Bottle", playerObj, CLO_Funcs.RemoveBigWaterBottleFromDispenser, dispenserObj) end

		mainSubMenu:addOption("Delete Dispenser", square, CLO_Funcs.DeleteDispenserObjectOnSquare)

		context:addSubMenu(mainOption, mainSubMenu)

	else
		local mainOption = context:addOption("[DEBUG] Dispenser")
		local mainSubMenu = context:getNew(context)

		local option1 = mainSubMenu:addOption("Create Empty Dispenser")
		local subMenu1 = mainSubMenu:getNew(mainSubMenu)
		subMenu1:addOption("Facing North", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Empty.N)
		subMenu1:addOption("Facing East", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Empty.E)
		subMenu1:addOption("Facing South", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Empty.S)
		subMenu1:addOption("Facing West", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Empty.W)

		local option2 = mainSubMenu:addOption("Create Water Dispenser")
		local subMenu2 = mainSubMenu:getNew(mainSubMenu)
		subMenu2:addOption("Facing North", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Water.N)
		subMenu2:addOption("Facing East", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Water.E)
		subMenu2:addOption("Facing South", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Water.S)
		subMenu2:addOption("Facing West", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Water.W)

		local option3 = mainSubMenu:addOption("Create Gas Dispenser")
		local subMenu3 = mainSubMenu:getNew(mainSubMenu)
		subMenu3:addOption("Facing North", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Petrol.N)
		subMenu3:addOption("Facing East", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Petrol.E)
		subMenu3:addOption("Facing South", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Petrol.S)
		subMenu3:addOption("Facing West", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Petrol.W)

		local option4 = mainSubMenu:addOption("Create Empty Bottle Dispenser")
		local subMenu4 = mainSubMenu:getNew(mainSubMenu)
		subMenu4:addOption("Facing North", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Bottle.N)
		subMenu4:addOption("Facing East", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Bottle.E)
		subMenu4:addOption("Facing South", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Bottle.S)
		subMenu4:addOption("Facing West", square, CLO_Funcs.CreateWaterDispenser, CLO_CustomDispenser.Bottle.W)

		context:addSubMenu(mainOption, mainSubMenu)
		mainSubMenu:addSubMenu(option1, subMenu1)
		mainSubMenu:addSubMenu(option2, subMenu2)
		mainSubMenu:addSubMenu(option3, subMenu3)
		mainSubMenu:addSubMenu(option4, subMenu4)
	end
end

Events.OnPreFillWorldObjectContextMenu.Add(TakeFuelBigWaterBottle_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(CheckLiquidContainerContent_Context)
Events.OnPreFillInventoryObjectContextMenu.Add(PouGasInto_Context)
Events.OnPreFillWorldObjectContextMenu.Add(InteractWaterDispenser_Context)
Events.OnPreFillWorldObjectContextMenu.Add(Debug_Context)
