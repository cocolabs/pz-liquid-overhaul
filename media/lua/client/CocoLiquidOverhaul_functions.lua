CLO_Funcs = {}

-- CLO_Funcs.Round
function CLO_Funcs.Round(x)
	return x + 0.5 - (x + 0.5) % 1
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonPetrol" and item:getUsedDelta() < 1 then
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
		if item:getType() == "Coco_WaterGallonFull" and item:getUsedDelta() < 1 then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetNotEmptyBigGallonWater
---@param inventory InventoryContainer
function CLO_Funcs.GetNotEmptyBigGallonWater(inventory)
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
	local obj = IsoObject.new(square, spriteName, "")
	square:AddTileObject(obj)
	CLO_Funcs.FixWaterDispenser(obj)
	return obj
end

-- CLO_Funcs.DeleteDispenserObjectOnSquare
---@param square IsoGridSquare
function CLO_Funcs.DeleteDispenserObjectOnSquare(square)
	if not square then return nil end

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
	if not square then return nil end

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
	if not square then return nil end

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
	if not obj then return false end

	local customName = obj:getProperties():Val("CustomName")
	if customName == "Dispenser" or
			customName == CLO_CustomDispenser.Bottle.type or
			customName == CLO_CustomDispenser.Empty.type or
			customName == CLO_CustomDispenser.Petrol.type or
			customName == CLO_CustomDispenser.Water.type then
		return true
	end
	return false
end

-- CLO_Funcs.HasDispenserBigWaterBottle
---@param obj IsoObject
function CLO_Funcs.HasDispenserBigWaterBottle(obj)
	local customName = obj:getProperties():Val("CustomName")
	if CLO_Funcs.IsObjectWaterDispenser(obj) and customName ~= CLO_CustomDispenser.Empty.type then
		return true
	end
	return false
end

-- CLO_Funcs.RemoveBigWaterBottleFromDispenser
---@param obj IsoObject
function CLO_Funcs.RemoveBigWaterBottleFromDispenser(obj)
	local modData = obj:getModData()
	modData.waterAmount = 0
	modData.waterMax = 0
	-- TO-DO: change sprite
end

-- CLO_Funcs.AddBigWaterBottleFromDispenser
---@param obj IsoObject
function CLO_Funcs.AddBigWaterBottleFromDispenser(obj)
	--local modData = obj:getModData()
	-- TO-DO: change sprite
	-- TO-DO: get the bottle water amount
	-- TO-DO: set max and amount
end
