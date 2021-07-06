
-- CocoLiquidOverhaul_TakeFuelRunAction
local function CocoLiquidOverhaul_TakeFuelRunAction(worldobjects, playerObj, square, petrolCan, pumpObject)
	-- let's equip it
	if playerObj:getPrimaryHandItem() ~= petrolCan and playerObj:getSecondaryHandItem() ~= petrolCan then
		ISInventoryPaneContextMenu.equipWeapon(petrolCan, true, true, playerObj:getPlayerNum());
	end;
	
	-- let's start the timed action
	ISTimedActionQueue.add(CocoLiquidOverhaulActionTakeFuel:new(playerObj, square, petrolCan, pumpObject, 5000));
end;

-- CocoLiquidOverhaul_PourIntoRunAction
local function CocoLiquidOverhaul_PourIntoRunAction(playerObj, itemFrom, itemTo)
	local inventory = playerObj:getInventory();
	
	if itemTo:getType() == "Coco_WaterGallonEmpty" then
		inventory:Remove(itemTo);
		itemTo = inventory:AddItem("CocoLiquidOverhaulItems.Coco_WaterGallonPetrol");
		itemTo:setUsedDelta(0);
	elseif itemTo:getType() == "EmptyPetrolCan" then
		inventory:Remove(itemTo);
		itemTo = inventory:AddItem("Base.PetrolCan");
		itemTo:setUsedDelta(0);
	end;
	
	local petrolStorageAvailable = (1 - itemTo:getUsedDelta()) / itemTo:getUseDelta();
	local petrolStorageNeeded = itemFrom:getUsedDelta() / itemFrom:getUseDelta();
	
	local itemFromEndingDelta = 0;
	local itemToEndingDelta = nil;
	
	if petrolStorageAvailable >= petrolStorageNeeded then
		local petrolInA = itemTo:getUsedDelta() / itemTo:getUseDelta();
		local petrolInB = itemFrom:getUsedDelta() / itemFrom:getUseDelta();
		local totalPetrol = petrolInA + petrolInB;
		
		itemToEndingDelta = totalPetrol * itemTo:getUseDelta();
		itemFromEndingDelta = 0;
	elseif petrolStorageAvailable < petrolStorageNeeded then
		local petrolInB = itemFrom:getUsedDelta() / itemFrom:getUseDelta();
		local petrolRemainInB = petrolInB - petrolStorageAvailable;
		
		itemFromEndingDelta = petrolRemainInB * itemFrom:getUseDelta();
		itemToEndingDelta = 1;
	end;
	
	-- let's start the timed action
	ISTimedActionQueue.add(CocoLiquidOverhaulActionPourInto:new(playerObj, itemFrom, itemTo, itemFromEndingDelta, itemToEndingDelta));
end;

-- CocoLiquidOverhaul_TakeFuelContext
local function CocoLiquidOverhaul_TakeFuelContext(playerNum, context, worldobjects, test)
	if test == true then return true end;
	
	local playerObj = getSpecificPlayer(playerNum);
	local inventory = playerObj:getInventory();
	local square = playerObj:getCurrentSquare();
	
	local fuelAmount = 0;
	local petrolCan = nil;
	
	-- Get square around player
	for y = square:getY()-1, square:getY()+1 do
		for x = square:getX()-1, square:getX()+1 do
			local sq = getCell():getGridSquare(x, y, square:getZ());
			if not(sq) then
				break;
			end;
			
			-- Check if there is fuel on that square
			if sq:getProperties():Is("fuelAmount") then
				
				-- Store the fuel amount
				fuelAmount = tonumber(sq:getProperties():Val("fuelAmount"))
				
				-- If there is fuel let's continue
				if fuelAmount > 0 then
					
					square = sq;
					
					-- Let's find a gas pump object
					local pumpFound = false;
					for i = 0, sq:getObjects():size()-1 do
						local pumpObject = sq:getObjects():get(i);
						local objName = pumpObject:getSprite():getName()
						if objName == "location_shop_fossoil_01_14" or objName == "location_shop_fossoil_01_15" then
							
							-- Let's find an not empty petrol can first
							local notEmptyPetrolCan = CocoLiquidOverhaul_GetFirstNotEmpty_WaterGallonPetrol(inventory)
							if notEmptyPetrolCan then
								petrolCan = notEmptyPetrolCan;
							
							-- We didnt find any let's find an empty one
							else
								local emptyPetrolCan = inventory:getFirstTypeRecurse("Coco_WaterGallonEmpty");
								if emptyPetrolCan then
									petrolCan = emptyPetrolCan;
								end;
							end;
							
							if petrolCan then
								context:addOption(getText("ContextMenu_TakeGasFromPumpWithBigWaterBottle"), worldobjects, CocoLiquidOverhaul_TakeFuelRunAction, playerObj, square, petrolCan, pumpObject);
							end;
							
							pumpFound = true;
							break;
						end;
					end;
					
					-- We found the pump already
					if pumpFound then break end;
					
				end;
			end;
		end;
	end;
end;

-- CocoLiquidOverhaul_GetLiquidContainerInfoContext
local function CocoLiquidOverhaul_GetLiquidContainerInfoContext(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum);
	local inventory = playerObj:getInventory();
	local mainContainer = nil;
	local isWater = false;
	
	if #items == 1 then
		for i,v in ipairs(items) do
			item = v;
			if not instanceof(v, "InventoryItem") then item = v.items[1]; end;
			
			-- We right clicked the WaterGallonPetrol
			if item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan" then
				mainContainer = item;
				break;
				
			elseif item:getType() == "Coco_WaterGallonFull" or item:getType() == "WaterBleachBottle" or item:getType() == "WaterBowl" or item:getType() == "BucketWaterFull" or item:getType() == "WaterPot" or item:getType() == "WaterMug" or item:getType() == "WaterPaintbucket" or item:getType() == "WaterSaucepan" or item:getType() == "BeerWaterFull" or item:getType() == "WaterPopBottle" or item:getType() == "WineWaterFull" or item:getType() == "WhiskeyWaterFull" or item:getType() == "WaterBottleFull" or item:getType() == "GardeningSprayFull" or item:getType() == "WateredCanFull" or item:getType() == "MayonnaiseWaterFull" or item:getType() == "RemouladeWaterFull" or item:getType() == "FullKettle" then
				mainContainer = item;
				isWater = true
				break;
			end;
		end;
	end;
	
	if mainContainer then
		local storageAvailable = CocoLiquidOverhaul_Round(1 / mainContainer:getUseDelta());
		local storageContain = CocoLiquidOverhaul_Round(storageAvailable * mainContainer:getUsedDelta());
		
		local option = context:addOption(getText("ContextMenu_Liquid_container_info"));
		local tooltip = ISWorldObjectContextMenu.addToolTip();
		if isWater then
			tooltip.description = getText("ContextMenu_Liquid_water_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable);
		elseif isAlcohol then
			tooltip.description = getText("ContextMenu_Liquid_alcohol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable);
		else
			tooltip.description = getText("ContextMenu_Liquid_petrol_name") .. ": " .. tostring(storageContain) .. "/" .. tostring(storageAvailable);
		end;
		option.toolTip = tooltip;
	end;
end;

-- CocoLiquidOverhaul_PourIntoContext
local function CocoLiquidOverhaul_PourIntoContext(playerNum, context, items)
	local playerObj = getSpecificPlayer(playerNum);
	local inventory = playerObj:getInventory();
	local mainContainer = nil;
	
	if #items == 1 then
		for i,v in ipairs(items) do
			item = v;
			if not instanceof(v, "InventoryItem") then item = v.items[1]; end;
			
			-- We right clicked the WaterGallonPetrol
			if (item:getType() == "Coco_WaterGallonPetrol" or item:getType() == "PetrolCan") and item:getUsedDelta() > 0 then
				mainContainer = item;
				break;
			end;
		end;
	end;
	
	if mainContainer then
		-- Do we have a container to pour in
		local pourableContainers = CocoLiquidOverhaul_GetAllPetrolPourableContainer(inventory);
		
		-- Let's filter to remove current container
		local availableContainers = {};
		for i,v in ipairs(pourableContainers) do
			local item = v;
			if not instanceof(v, "InventoryItem") then item = v.items[1]; end;
			
			if item ~= mainContainer then
				if instanceof(item, "DrainableComboItem") and item:getUsedDelta() < 1 then
					table.insert(availableContainers, item);
				else
					table.insert(availableContainers, item);
				end;
			end;
		end;
		
		-- if we still got a pourable container let's make the submenu
		if #availableContainers > 0 then
			local option = context:addOption(getText("ContextMenu_Pour_petrol_into"));
			local subMenu = context:getNew(context);
			context:addSubMenu(option, subMenu);
			
			-- let's add all item to submenu
			for i,v in ipairs(availableContainers) do
				local item = v;
				if not instanceof(v, "InventoryItem") then item = v.items[1]; end;
				
				local itemName = item:getDisplayName();
				local usedPercent = " (0%)";
				if instanceof(item, "DrainableComboItem") then
					usedPercent = " (" .. tostring(math.floor(item:getUsedDelta() * 100)) .. "%)";
				end;
				subMenu:addOption(itemName .. usedPercent, playerObj, CocoLiquidOverhaul_PourIntoRunAction, mainContainer, item);
			end;
		end;
	end;
end;

Events.OnFillWorldObjectContextMenu.Add(CocoLiquidOverhaul_TakeFuelContext);
Events.OnPreFillInventoryObjectContextMenu.Add(CocoLiquidOverhaul_GetLiquidContainerInfoContext);
Events.OnPreFillInventoryObjectContextMenu.Add(CocoLiquidOverhaul_PourIntoContext);
