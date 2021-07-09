CLO_Funcs = {}

-- CLO_Funcs.Round
function CLO_Funcs.Round(x)
	return x + 0.5 - (x + 0.5) % 1
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol
function CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
	local result = nil
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
function CLO_Funcs.GetFirstNotEmpty_WaterGallonFull(inventory)
	local result = nil
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
function CLO_Funcs.FixWaterDispenser(obj)
	local modData = obj:getModData()

	if modData.dispenserType ~= "empty" then
		modData.waterMax = tonumber(CLO_ModSettings.WaterDispenserWaterMax)
		if modData.waterAmount then
			modData.waterAmount = tonumber(modData.waterAmount)
		else
			modData.waterAmount = ZombRand(1, modData.waterMax)
		end
		if modData.waterAmount > modData.waterMax then
			modData.waterAmount = modData.waterMax
		end
	end

	if modData.dispenserType == nil then
		modData.name = "Water Dispenser"
		modData.dispenserType = "water"
	end
end

-- CLO_Funcs.CreateWaterDispenser
function CLO_Funcs.CreateWaterDispenser(playerObj, square, spriteName)
	local x, y, z = square:getX(), square:getY(), square:getZ()

	--local obj = IsoObject.new(square, spriteName, "WaterDispenser");
	local obj = IsoThumpable.new(getCell(), square, spriteName, true, nil)
	square:AddSpecialObject(obj)
	CLO_Funcs.FixWaterDispenser(obj)

	return obj
end

-- CLO_Funcs.HasDispenserOnSquare
function CLO_Funcs.HasDispenserOnSquare(square)
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
function CLO_Funcs.IsObjectWaterDispenser(obj)
	local modData = obj:getModData()
	if obj:getProperties():Val("CustomName") == "Dispenser" or modData.dispenserType then
		return true
	end
	return false
end

-- CLO_Funcs.HasDispenserBigWaterBottle
function CLO_Funcs.HasDispenserBigWaterBottle(obj)
	local modData = obj:getModData()
	if CLO_Funcs.IsObjectWaterDispenser(obj) and modData.dispenserType ~= "none" then
		return true
	end
	return false
end

-- CLO_Funcs.RemoveBigWaterBottleFromDispenser
function CLO_Funcs.RemoveBigWaterBottleFromDispenser(obj)
	local modData = obj:getModData()
	modData.name = "Empty Dispenser"
	modData.dispenserType = "empty"
	modData.waterAmount = 0
	modData.waterMax = 0
	-- TO-DO: change sprite
end

-- CLO_Funcs.AddBigWaterBottleFromDispenser
function CLO_Funcs.AddBigWaterBottleFromDispenser(obj)
	local modData = obj:getModData()
	modData.name = "Water Dispenser"
	modData.dispenserType = "water"
	-- TO-DO: change sprite
	-- TO-DO: get the bottle water amount
	-- TO-DO: set max and amount
end
