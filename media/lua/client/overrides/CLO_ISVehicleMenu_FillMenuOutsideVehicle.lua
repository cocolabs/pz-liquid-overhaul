CLO_Print("Overriding: 'ISVehicleMenu.FillMenuOutsideVehicle'")
function ISVehicleMenu.FillMenuOutsideVehicle(player, context, vehicle, test)
    local playerObj = getSpecificPlayer(player)
    --[[
        local subOption = context:addOption("Vehicle")
        local vehicleMenu = ISContextMenu:getNew(context)
        context:addSubMenu(subOption, vehicleMenu)
        vehicleMenu:addOption("Info", playerObj, ISVehicleMenu.onInfo, vehicle)
    --]]
    --	context:addOption("Vehicle Info", playerObj, ISVehicleMenu.onInfo, vehicle)
    ISVehicleMenu.FillPartMenu(player, context, nil, vehicle);

    context:addOption(getText("ContextMenu_VehicleMechanics"), playerObj, ISVehicleMenu.onMechanic, vehicle);

    local part = vehicle:getClosestWindow(playerObj);
    if part then
        local window = part:getWindow()
        if not window:isDestroyed() and not window:isOpen() then
            context:addOption(getText("ContextMenu_Vehicle_Smashwindow", getText("IGUI_VehiclePart" .. part:getId())), playerObj, ISVehiclePartMenu.onSmashWindow, part)
        end
    end

    -- remove burnt vehicles
    if string.match(vehicle:getScript():getName(), "Burnt") then
        local option = context:addOption(getText("ContextMenu_RemoveBurntVehicle"), playerObj, ISVehicleMenu.onRemoveBurntVehicle, vehicle);
        local toolTip = ISToolTip:new();
        toolTip:initialise();
        toolTip:setVisible(false);
        option.toolTip = toolTip;
        toolTip:setName(getText("ContextMenu_RemoveBurntVehicle"));
        toolTip.description = getText("Tooltip_removeBurntVehicle") .. " <LINE> <LINE> ";

        if playerObj:getInventory():containsTypeRecurse("WeldingMask") then
            toolTip.description = toolTip.description .. " <LINE> <RGB:1,1,1> " .. getItemNameFromFullType("Base.WeldingMask") .. " 1/1";
        else
            toolTip.description = toolTip.description .. " <LINE> <RGB:1,0,0> " .. getItemNameFromFullType("Base.WeldingMask") .. " 0/1";
            option.notAvailable = true;
        end

        local blowTorch = ISBlacksmithMenu.getBlowTorchWithMostUses(playerObj:getInventory());
        if blowTorch then
            local blowTorchUseLeft = blowTorch:getDrainableUsesInt();
            if blowTorchUseLeft >= 20 then
                toolTip.description = toolTip.description .. " <LINE> <RGB:1,1,1> " .. getItemNameFromFullType("Base.BlowTorch") .. getText("ContextMenu_Uses") .. " " .. blowTorchUseLeft .. "/" .. 20;
            else
                toolTip.description = toolTip.description .. " <LINE> <RGB:1,0,0> " .. getItemNameFromFullType("Base.BlowTorch") .. getText("ContextMenu_Uses") .. " " .. blowTorchUseLeft .. "/" .. 20;
                option.notAvailable = true;
            end
        else
            toolTip.description = toolTip.description .. " <LINE> <RGB:1,0,0> " .. getItemNameFromFullType("Base.BlowTorch") .. " 0/5";
            option.notAvailable = true;
        end
    end

    if ISWashVehicle.hasBlood(vehicle) then
        local option = context:addOption(getText("ContextMenu_Vehicle_Wash"), playerObj, ISVehicleMenu.onWash, vehicle);
        local toolTip = ISToolTip:new();
        toolTip:initialise();
        toolTip:setVisible(false);
        toolTip:setName(getText("Tooltip_Vehicle_WashTitle"));
        toolTip.description = getText("Tooltip_Vehicle_WashWaterRequired1", 100 / ISWashVehicle.BLOOD_PER_WATER);
        local waterAvailable = ISWashVehicle.getWaterAmountForPlayer(playerObj);
        option.notAvailable = waterAvailable <= 0
        if waterAvailable == 1 then
            toolTip.description = toolTip.description .. " <BR> " .. getText("Tooltip_Vehicle_WashWaterRequired2");
        else
            toolTip.description = toolTip.description .. " <BR> " .. getText("Tooltip_Vehicle_WashWaterRequired3", waterAvailable);
        end
        option.toolTip = toolTip;
    end

    local vehicleMenu = nil
    if getCore():getDebug() or ISVehicleMechanics.cheat or (isClient() and isAdmin()) then
        local subOption = context:addOption("[DEBUG] Vehicle")
        vehicleMenu = ISContextMenu:getNew(context)
        context:addSubMenu(subOption, vehicleMenu)
    end

    if getCore():getDebug() then
        vehicleMenu:addOption("Reload Vehicle Textures", vehicle:getScript():getName(), reloadVehicleTextures)
        if ISVehicleMechanics.cheat then
            vehicleMenu:addOption("ISVehicleMechanics.cheat=false", playerObj, ISVehicleMechanics.onCheatToggle)
        else
            vehicleMenu:addOption("ISVehicleMechanics.cheat=true", playerObj, ISVehicleMechanics.onCheatToggle)
        end
        vehicleMenu:addOption("Roadtrip UI", playerObj, ISVehicleMenu.onRoadtrip);
        vehicleMenu:addOption("Vehicle Angles UI", playerObj, ISVehicleMenu.onDebugAngles, vehicle);
        vehicleMenu:addOption("Vehicle HSV UI", playerObj, ISVehicleMenu.onDebugColor, vehicle);
        vehicleMenu:addOption("Vehicle Blood UI", playerObj, ISVehicleMenu.onDebugBlood, vehicle);
        vehicleMenu:addOption("Vehicle Editor", playerObj, ISVehicleMenu.onDebugEditor, vehicle);
        if not isClient() then
            ISVehicleMenu.addSetScriptMenu(vehicleMenu, playerObj, vehicle)
        end
    end

    if getCore():getDebug() or ISVehicleMechanics.cheat or (isClient() and isAdmin()) then
        vehicleMenu:addOption("Remove vehicle", playerObj, ISVehicleMechanics.onCheatRemove, vehicle);
    end
end