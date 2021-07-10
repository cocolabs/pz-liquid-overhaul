CLO_Funcs = {}

-- CLO_Funcs.Round
function CLO_Funcs.Round(x)
	return x + 0.5 - (x + 0.5) % 1
end

-- CLO_Funcs.GetFirstObjectOfType
---@param inventory InventoryContainer
---@param type string
function CLO_Funcs.GetFirstObjectOfType(inventory, type)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == type then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonPetrol" and item:getUsedDelta() > 0 then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonFull
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotEmpty_WaterGallonFull(inventory)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonFull" and item:getUsedDelta() > 0 then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetAllNotEmptyBigGallonWater
---@param inventory InventoryContainer
function CLO_Funcs.GetAllNotEmptyBigGallonWater(inventory)
	local result = {}
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonEmpty" then
			table.insert(result, item)
		end
	end
	return result
end

-- CLO_Funcs.GetAllPetrolPourableContainer
---@param inventory InventoryContainer
function CLO_Funcs.GetAllPetrolPourableContainer(inventory)
	local result = {}
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonEmpty" or item:getType() == "EmptyPetrolCan" then
			table.insert(result, item)
		elseif item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan" then
			if item:getUsedDelta() < 1 then
				table.insert(result, item)
			end
		end
	end
	return result
end

-- CLO_Funcs.FixWaterDispenser
---@param obj IsoObject
function CLO_Funcs.FixWaterDispenser(obj)
	--local modData = obj:getModData()
	--local objProps = obj:getProperties()

	--if objProps:Get("CustomName") ~= "empty" then
	--	modData.waterMax = tonumber(CLO_ModSettings.WaterDispenserWaterMax)
	--	if modData.waterAmount then
	--		modData.waterAmount = tonumber(modData.waterAmount)
	--	else
	--		modData.waterAmount = ZombRand(1, modData.waterMax)
	--	end
	--	if modData.waterAmount > modData.waterMax then
	--		modData.waterAmount = modData.waterMax
	--	end
	--end
end

-- CLO_Funcs.CreateWaterDispenser
---@param square IsoGridSquare
---@param spriteName string
function CLO_Funcs.CreateWaterDispenser(square, spriteName)
	if not square then return end

	local obj = IsoObject.new(square, spriteName, "")
	square:AddTileObject(obj)
	CLO_Funcs.FixWaterDispenser(obj)
	return obj
end

-- CLO_Funcs.DeleteDispenserObjectOnSquare
---@param square IsoGridSquare
function CLO_Funcs.DeleteDispenserObjectOnSquare(square)
	if not square then return end

	local obj = CLO_Funcs.GetDispenserObjectOnSquare(square)
	if obj then
		square:transmitRemoveItemFromSquare(obj)
		square:RecalcProperties()
		print("Deleting dispenser on square")
	end
end

-- CLO_Funcs.GetDispenserObjectOnSquare
---@param square IsoGridSquare
function CLO_Funcs.GetDispenserObjectOnSquare(square)
	if not square then return end

	local result = false
	for i = 0, square:getObjects():size() - 1 do
		local obj = square:getObjects():get(i)
		if CLO_Funcs.IsObjectWaterDispenser(obj) then
			result = obj
			break
		end
	end
	return result
end

-- CLO_Funcs.HasDispenserOnSquare
---@param square IsoGridSquare
function CLO_Funcs.HasDispenserOnSquare(square)
	if not square then return end

	local result = false
	for i = 0, square:getObjects():size() - 1 do
		local obj = square:getObjects():get(i)
		if CLO_Funcs.IsObjectWaterDispenser(obj) then
			result = true
			break
		end
	end
	return result
end

-- CLO_Funcs.IsObjectWaterDispenser
---@param obj IsoObject
function CLO_Funcs.IsObjectWaterDispenser(obj)
	if not obj then return end

	local customName = obj:getProperties():Val("CustomName")
	if customName == "Dispenser" or
			customName == CLO_CustomDispenser.Bottle.type or
			customName == CLO_CustomDispenser.Empty.type or
			customName == CLO_CustomDispenser.Petrol.type or
			customName == CLO_CustomDispenser.Water.type then
		return true
	end
end

-- CLO_Funcs.IsDispenserEmpty
-- Check if the dispenser has a bottle or not
---@param obj IsoObject the dispenser object
function CLO_Funcs.IsDispenserEmpty(obj)
	if not obj then return end

	local customName = obj:getProperties():Val("CustomName")
	if CLO_Funcs.IsObjectWaterDispenser(obj) and customName == CLO_CustomDispenser.Empty.type then
		return true
	end
end

-- CLO_Funcs.IsDispenserBottleEmpty
-- Check if the dispenser bottle is empty of liquid
---@param obj IsoObject the dispenser object
function CLO_Funcs.IsDispenserBottleEmpty(obj)
	if not obj then return end

	local customName = obj:getProperties():Val("CustomName")
	if CLO_Funcs.IsObjectWaterDispenser(obj) and customName ~= CLO_CustomDispenser.Empty.type then
		return false
	end
end

-- CLO_Funcs.RemoveBigWaterBottleFromDispenser
---@param playerObj IsoPlayer the player
---@param obj IsoObject the dispenser to remove the bottle
function CLO_Funcs.RemoveBigWaterBottleFromDispenser(playerObj, obj)
	if not playerObj or not obj then return end

	---@type InventoryContainer
	local inventory = playerObj:getInventory()

	if CLO_Funcs.IsObjectWaterDispenser(obj) and not CLO_Funcs.IsDispenserEmpty(obj) then
		local type
		local modData = obj:getModData()
		local objProps = obj:getProperties()

		local customName = objProps:Val("CustomName")

		if customName == CLO_CustomDispenser.Bottle.type then type = "Coco_WaterGallonEmpty"
		elseif customName == CLO_CustomDispenser.Water.type then type = "Coco_WaterGallonFull"
		elseif customName == CLO_CustomDispenser.Petrol.type then type = "Coco_WaterGallonPetrol" end

		if type ~= nil then

			local square = obj:getSquare()
			local facing = objProps:Val("Facing")

			CLO_Funcs.DeleteDispenserObjectOnSquare(square)
			if facing == "N" then
				CLO_Funcs.CreateWaterDispenser(square, CLO_CustomDispenser.Empty.N)
			elseif facing == "W" then
				CLO_Funcs.CreateWaterDispenser(square, CLO_CustomDispenser.Empty.W)
			elseif facing == "S" then
				CLO_Funcs.CreateWaterDispenser(square, CLO_CustomDispenser.Empty.S)
			elseif facing == "E" then
				CLO_Funcs.CreateWaterDispenser(square, CLO_CustomDispenser.Empty.E)
			end

			local itemName = "CocoLiquidOverhaulItems." .. type
			local addedItem = inventory:AddItem(itemName)
		end

		-- remove liquid
		--modData.waterAmount = 0
		--modData.waterMax = 0
		--modData.petrolAmount = 0
		--modData.petrolMax = 0
	end
end

-- CLO_Funcs.AddBigWaterBottleFromDispenser
---@param playerObj IsoPlayer the player
---@param obj IsoObject the dispenser to add the bottle to
---@param item InventoryItem the bottle to add
function CLO_Funcs.AddBigWaterBottleFromDispenser(playerObj, obj, item)
	if not playerObj or not obj then return end

	---@type InventoryContainer
	local inventory = playerObj:getInventory()
	local bottleItem = item

	if not instanceof(item, "InventoryItem") and CLO_ModSettings.Debug then
		-- let find an item
		if item == CLO_CustomDispenser.Bottle.type then
			item = CLO_Funcs.GetFirstObjectOfType(inventory, "Coco_WaterGallonEmpty")
		elseif item == CLO_CustomDispenser.Water.type then
			item = CLO_Funcs.GetFirstNotEmpty_WaterGallonFull(inventory)
		elseif item == CLO_CustomDispenser.Petrol.type then
			item = CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
		end

		if item then
			bottleItem = inventory:AddItem(item)
		end
	end

	if bottleItem and instanceof(bottleItem, "InventoryItem") and CLO_Funcs.IsObjectWaterDispenser(obj) and CLO_Funcs.IsDispenserEmpty(obj) then
		local type
		local itemType = bottleItem:getType()
		local modData = obj:getModData()
		local objProps = obj:getProperties()

		if itemType == "Coco_WaterGallonEmpty" then type = CLO_CustomDispenser.Bottle
		elseif itemType == "Coco_WaterGallonFull" then type = CLO_CustomDispenser.Water
		elseif itemType == "Coco_WaterGallonPetrol" then type = CLO_CustomDispenser.Petrol end

		if type ~= nil then

			local square = obj:getSquare()
			local facing = objProps:Val("Facing")

			CLO_Funcs.DeleteDispenserObjectOnSquare(square)
			if facing == "N" then
				CLO_Funcs.CreateWaterDispenser(square, type.N)
			elseif facing == "W" then
				CLO_Funcs.CreateWaterDispenser(square, type.W)
			elseif facing == "S" then
				CLO_Funcs.CreateWaterDispenser(square, type.S)
			elseif facing == "E" then
				CLO_Funcs.CreateWaterDispenser(square, type.E)
			end

			inventory:Remove(bottleItem)
		end
	end
end
