-- CLO_Round
function CLO_Round(x)
	return x + 0.5 - (x + 0.5) % 1
end

-- CLO_GetFirstNotEmpty_WaterGallonPetrol (petrol)
function CLO_GetFirstNotEmpty_WaterGallonPetrol(inventory)
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

-- CLO_GetFirstNotEmpty_WaterGallonFull (water)
function CLO_GetFirstNotEmpty_WaterGallonFull(inventory)
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

-- CLO_GetAllWaterGallonEmpty (empty)
function CLO_GetNotEmptyBigGallonWater(inventory)
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

-- CLO_GetAllPetrolPourableContainer (petrol containers)
function CLO_GetAllPetrolPourableContainer(inventory)
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

-- CLO_FixWaterDispenser
function CLO_FixWaterDispenser(obj)
	local modData = obj:getModData()
	local objProps = obj:getProperties()

	modData.name = "Empty Dispenser"
	modData.spriteName = obj:getSpriteName()
	modData.waterMax = tonumber(ModSettings.WaterDispenserWaterMax)
	if modData.waterAmount then
		modData.waterAmount = tonumber(modData.waterAmount)
	else
		modData.waterAmount = ZombRand(1, modData.waterMax)
	end
	if modData.waterAmount > modData.waterMax then
		modData.waterAmount = modData.waterMax
	end

	if modData.dispenserType == nil then
		modData.dispenserType = "water" -- none, water, gas
	end
	if modData.dispenserType == "none" then
	end

	--objProps:Set("CustomName", nil);
end

-- CLO_CreateWaterDispenser
function CLO_CreateWaterDispenser(playerObj, square, spriteName)
	local x, y, z = square:getX(), square:getY(), square:getZ()

	--local obj = IsoObject.new(square, spriteName, "WaterDispenser");
	local obj = IsoThumpable.new(getCell(), square, spriteName, true, nil)
	square:AddSpecialObject(obj)
	CLO_FixWaterDispenser(obj)

	return obj
end

-- CLO_HasDispenserOnSquare
function CLO_HasDispenserOnSquare(square)
	local result = false
	for i = 0, square:getObjects():size() - 1 do
		local obj = square:getObjects():get(i)
		if CLO_IsObjectWaterDispenser(obj) then
			result = true
			break
		end
	end
	return result
end

-- CLO_IsObjectWaterDispenser
function CLO_IsObjectWaterDispenser(obj)
	local modData = obj:getModData()
	if obj:getProperties():Val("CustomName") == "Dispenser" or modData.dispenserType then
		return true
	end
	return false
end

-- CLO_HasDispenserBigWaterBottle
function CLO_HasDispenserBigWaterBottle(obj)
	local modData = obj:getModData()
	if CLO_IsObjectWaterDispenser(obj) and modData.dispenserType ~= "none" then
		return true
	end
	return false
end

-- CLO_RemoveBigWaterBottleFromDispenser
function CLO_RemoveBigWaterBottleFromDispenser()
end

-- CLO_AddBigWaterBottleFromDispenser
function CLO_AddBigWaterBottleFromDispenser()
end
