function CLO_Override_ISVehicleMenu_FillPartMenu()
    CLO_Print("Overriding: 'ISVehicleMenu.FillPartMenu'")

    function ISVehicleMenu.FillPartMenu(playerIndex, context, slice, vehicle)
        local playerObj = getSpecificPlayer(playerIndex);
        local typeToItem = VehicleUtils.getItems(playerIndex)
        for i=1,vehicle:getPartCount() do
            local part = vehicle:getPartByIndex(i-1)
            if not vehicle:isEngineStarted() and part:isContainer() and part:getContainerContentType() == "Gasoline" then

                local customFuelItems = {}
                for i = 1, #CLO_ModSettings.CustomFuelItems do
                    local fuelItem = CLO_ModSettings.CustomFuelItems[i]
                    if typeToItem[fuelItem.module .. "." .. fuelItem.full] then
                        table.insert(customFuelItems, fuelItem)
                    end
                end

                if (typeToItem["Base.PetrolCan"] or #customFuelItems > 0) and part:getContainerContentAmount() < part:getContainerCapacity() then
                    if slice then
                        slice:addSlice(getText("ContextMenu_VehicleAddGas"), getTexture("Item_Petrol"), ISVehiclePartMenu.onAddGasoline, playerObj, part)
                    else
                        context:addOption(getText("ContextMenu_VehicleAddGas"), playerObj,ISVehiclePartMenu.onAddGasoline, part)
                    end
                end
                if ISVehiclePartMenu.getGasCanNotFull(playerObj, typeToItem) and part:getContainerContentAmount() > 0 then
                    if slice then
                        slice:addSlice(getText("ContextMenu_VehicleSiphonGas"), getTexture("Item_Petrol"), ISVehiclePartMenu.onTakeGasoline, playerObj, part)
                    else
                        context:addOption(getText("ContextMenu_VehicleSiphonGas"), playerObj, ISVehiclePartMenu.onTakeGasoline, part)
                    end
                end
                local square = ISVehiclePartMenu.getNearbyFuelPump(vehicle)
                if square and ((SandboxVars.AllowExteriorGenerator and square:haveElectricity()) or (SandboxVars.ElecShutModifier > -1 and GameTime:getInstance():getNightsSurvived() < SandboxVars.ElecShutModifier)) then
                    if square and part:getContainerContentAmount() < part:getContainerCapacity() then
                        if slice then
                            slice:addSlice(getText("ContextMenu_VehicleRefuelFromPump"), getTexture("media/ui/vehicles/vehicle_refuel_from_pump.png"), ISVehiclePartMenu.onPumpGasoline, playerObj, part)
                        else
                            context:addOption(getText("ContextMenu_VehicleRefuelFromPump"), playerObj, ISVehiclePartMenu.onPumpGasoline, part)
                        end
                    end
                end
            end
        end
    end
end