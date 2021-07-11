CLO_Funcs = {}

-- CLO_Funcs.Round
-- Round a number
function CLO_Funcs.Round(x)
	return x + 0.5 - (x + 0.5) % 1
end

-- CLO_Funcs.Hypo
-- Return hypotenuse
function CLO_Funcs.Hypo(x, y)
	return math.sqrt(x^2 + y^2)
end

-- CLO_Funcs.GetDistanceBetween
---@param squareA IsoGridSquare
---@param squareB IsoGridSquare
function CLO_Funcs.GetDistanceBetween(squareA, squareB)
	if not squareA or not squareB then return end

	if squareA:getZ() == squareB:getZ() then
		return CLO_Funcs.Hypo(squareB:getX() - squareA:getX(), squareB:getY() - squareA:getY())
	end
end

-- CLO_Funcs.IsPlayerNextToSquare
---@param playerObj IsoPlayer
---@param squareB IsoGridSquare
function CLO_Funcs.IsPlayerNextToSquare(playerObj, square, distance)
	if not playerObj or not square then return end
	if not distance then distance = 1.5 end
	return CLO_Funcs.GetDistanceBetween(playerObj:getCurrentSquare(), square) < distance
end

-- CLO_Funcs.GetFirstObjectOfType
-- Get the first item of a type from an inventory
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

-- CLO_Funcs.GetFirstNotEmpty_DrainableItemOfType
-- Get the first not empty drainable item of a type from an inventory
---@param inventory InventoryContainer
---@param type string
function CLO_Funcs.GetFirstNotEmpty_DrainableItemOfType(inventory, type)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		---@type InventoryItem
		local item = items:get(i)
		if item:getType() == type and item:IsDrainable() and item:getUsedDelta() > 0 then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetFirstNotFull_DrainableItemOfType
-- Get the first not full drainable item of a type from an inventory
---@param inventory InventoryContainer
---@param type string
function CLO_Funcs.GetFirstNotFull_DrainableItemOfType(inventory, type)
	local result
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		---@type InventoryItem
		local item = items:get(i)
		if item:getType() == type and item:IsDrainable() and item:getUsedDelta() < 1 then
			result = item
			break
		end
	end
	return result
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol
-- Get the first not empty big water bottle (petrol) from an inventory
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotEmpty_WaterGallonPetrol(inventory)
	return CLO_Funcs.GetFirstNotEmpty_DrainableItemOfType(inventory, "Coco_WaterGallonPetrol")
end

-- CLO_Funcs.GetFirstNotEmpty_WaterGallonFull
-- Get the first not empty big water bottle (water) from an inventory
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotEmpty_WaterGallonFull(inventory)
	return CLO_Funcs.GetFirstNotEmpty_DrainableItemOfType(inventory, "Coco_WaterGallonFull")
end

-- CLO_Funcs.GetFirstNotFull_WaterGallonPetrol
-- Get the first not full big water bottle (petrol) from an inventory
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotFull_WaterGallonPetrol(inventory)
	return CLO_Funcs.GetFirstNotFull_DrainableItemOfType(inventory, "Coco_WaterGallonPetrol")
end

-- CLO_Funcs.GetFirstNotFull_WaterGallonFull
-- Get the first not full big water bottle (water) from an inventory
---@param inventory InventoryContainer
function CLO_Funcs.GetFirstNotFull_WaterGallonFull(inventory)
	return CLO_Funcs.GetFirstNotFull_DrainableItemOfType(inventory, "Coco_WaterGallonFull")
end

-- CLO_Funcs.GetAllPetrolPourableContainer
-- Get an array of all empty and not full petrol containers from an inventory
---@param inventory InventoryContainer
function CLO_Funcs.GetAllPetrolPourableContainer(inventory)
	local result = {}
	local items = inventory:getItems()
	for i = 0, items:size() - 1 do
		local item = items:get(i)
		if item:getType() == "Coco_WaterGallonEmpty" or item:getType() == "EmptyPetrolCan" then
			table.insert(result, item)
		elseif (item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan") and item:getUsedDelta() < 1 then
			table.insert(result, item)
		end
	end
	return result
end

-- CLO_Funcs.IsPetrolAvailableOnSquare
-- Check if a square contains fuel
---@param square IsoGridSquare
function CLO_Funcs.IsPetrolAvailableOnSquare(square)
	if not square then return end
	return square:getProperties():Is("fuelAmount")
end

-- CLO_Funcs.GetPetrolAvailableOnSquare
-- Get the amount of fuel available on a square
---@param square IsoGridSquare
function CLO_Funcs.GetPetrolAvailableOnSquare(square)
	if not square then return end
	if CLO_Funcs.IsPetrolAvailableOnSquare(square) then
		return tonumber(square:getProperties():Val("fuelAmount"))
	end
	return 0
end

-- CLO_Funcs.FixWaterDispenser
-- Fix any default and custom dispenser
-- Change the max water amount and transform default dispenser into custom
---@param obj IsoObject
function CLO_Funcs.FixWaterDispenser(obj)
	local modData = obj:getModData()
	local objProps = obj:getProperties()
	local customName = objProps:Val("CustomName")

	if customName == "Dispenser" then
		CLO_Funcs.ReplaceDispenserObjectOnSquare(obj, CLO_CustomDispenser.Water)
	end

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
-- Create a dispenser object on a square
-- Use CLO_CustomDispenser.[type].[direction] from the proper sprite image
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
-- Delete a dispenser object from a square
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

-- CLO_Funcs.DeleteObject
-- Delete an object
---@param obj IsoObject
function CLO_Funcs.DeleteObject(obj)
	if not obj then return end

	local square = obj:getSquare()
	if square then
		square:transmitRemoveItemFromSquare(obj)
		square:RecalcProperties()
	end
end

-- CLO_Funcs.ReplaceDispenserObjectOnSquare
---@param obj IsoObject
---@param type table use the CLO_CustomDispenser variable
function CLO_Funcs.ReplaceDispenserObjectOnSquare(obj, customDispenser)
	if not obj or not customDispenser then return end

	---@type IsoGridSquare
	local square = obj:getSquare()
	if obj and square then
		local facing = obj:getProperties():Val("Facing")
		CLO_Funcs.CreateWaterDispenser(square, customDispenser[facing])
		CLO_Funcs.DeleteObject(obj)
	end
end

-- CLO_Funcs.GetDispenserObjectOnSquare
-- Get a dispenser object if any found on the square
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
-- Check if a dispenser is on that square
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
-- Check if the object is a default and custom dispenser
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
-- Remove the big water bottle of any type from an empty dispenser
-- Add the bottle to inventory
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
-- Add a big water bottle of any type to an empty dispenser
-- Take the bottle from inventory
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
