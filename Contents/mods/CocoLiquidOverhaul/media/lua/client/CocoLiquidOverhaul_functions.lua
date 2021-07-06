
-- CocoLiquidOverhaul_Round
function CocoLiquidOverhaul_Round(x)
	return x + 0.5 - (x + 0.5) % 1;
end;

-- CocoLiquidOverhaul_GetFirstNotEmpty_WaterGallonPetrol
function CocoLiquidOverhaul_GetFirstNotEmpty_WaterGallonPetrol(inventory)
	local result = nil;
	local items = inventory:getItems();
	for i = 0, items:size()-1 do
		local item = items:get(i);
		if item:getType() == "Coco_WaterGallonPetrol" and item:getUsedDelta() < 1 then
			result = item;
			break;
		end;
	end;
	return result;
end;

-- CocoLiquidOverhaul_GetFirstNotEmpty_WaterGallonFull
function CocoLiquidOverhaul_GetFirstNotEmpty_WaterGallonFull(inventory)
	local result = nil;
	local items = inventory:getItems();
	for i = 0, items:size()-1 do
		local item = items:get(i);
		if item:getType() == "Coco_WaterGallonFull" and item:getUsedDelta() < 1 then
			result = item;
			break;
		end;
	end;
	return result;
end;

-- CocoLiquidOverhaul_GetAllWaterGallonEmpty
function CocoLiquidOverhaul_GetNotEmptyBigGallonWater(inventory)
	local result = {};
	local items = inventory:getItems();
	for i = 0, items:size()-1 do
		local item = items:get(i);
		if item:getType() == "Coco_WaterGallonEmpty" then
			table.insert(result, item);
		end;
	end;
	return result;
end;

-- CocoLiquidOverhaul_GetAllPetrolPourableContainer
function CocoLiquidOverhaul_GetAllPetrolPourableContainer(inventory)
	local result = {};
	local items = inventory:getItems();
	for i = 0, items:size()-1 do
		local item = items:get(i);
		if item:getType() == "Coco_WaterGallonEmpty" or item:getType() == "EmptyPetrolCan" then
			table.insert(result, item);
		elseif item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan" then
			if item:getUsedDelta() < 1 then
				table.insert(result, item);
			end;
		end;
	end;
	return result;
end;
