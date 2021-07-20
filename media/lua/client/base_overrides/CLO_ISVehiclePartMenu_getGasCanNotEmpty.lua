function CLO_Override_ISVehiclePartMenu_getGasCanNotEmpty()
    CLO_Print("Overriding: 'ISVehiclePartMenu.getGasCanNotEmpty'")

    function ISVehiclePartMenu.getGasCanNotEmpty(playerObj, typeToItem)
        local customFuelItem = nil
        for i = 1, #CLO_ModSettings.CustomFuelItems do
            local fuelItem = CLO_ModSettings.CustomFuelItems[i]
            if typeToItem[fuelItem.module .. "." .. fuelItem.full] then
                customFuelItem = fuelItem
                break
            end
        end

        -- Prefer an equipped PetrolCan, then the emptiest PetrolCan.
        local equipped = playerObj:getPrimaryHandItem()
        if equipped and (equipped:getType() == "PetrolCan" or equipped:getType() == customFuelItem.full) and equipped:getUsedDelta() > 0 then
            return equipped
        end

        if typeToItem["Base.PetrolCan"] or typeToItem[customFuelItem.module .. "." .. customFuelItem.full] then
            local gasCan = nil
            local usedDelta = 1.1
            if typeToItem["Base.PetrolCan"] then
                for _,item in ipairs(typeToItem["Base.PetrolCan"]) do
                    if item:getUsedDelta() > 0 and item:getUsedDelta() < usedDelta then
                        gasCan = item
                        usedDelta = gasCan:getUsedDelta()
                    end
                end
            end
            if gasCan == nil and typeToItem[customFuelItem.module .. "." .. customFuelItem.full] then
                for _,item in ipairs(typeToItem[customFuelItem.module .. "." .. customFuelItem.full]) do
                    if item:getUsedDelta() > 0 and item:getUsedDelta() < usedDelta then
                        gasCan = item
                        usedDelta = gasCan:getUsedDelta()
                    end
                end
            end
            if gasCan then return gasCan end
        end
        return nil
    end
end