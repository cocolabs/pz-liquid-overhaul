CLO_Print("Overriding: 'ISVehiclePartMenu.getGasCanNotEmpty'")
function ISVehiclePartMenu.getGasCanNotEmpty(playerObj, typeToItem)
    -- Prefer an equipped PetrolCan, then the emptiest PetrolCan.
    local equipped = playerObj:getPrimaryHandItem()
    if equipped and (equipped:getType() == "PetrolCan" or equipped:getType() == "Coco_LargePetrolCan") and equipped:getUsedDelta() > 0 then
        return equipped
    end
    if typeToItem["Base.PetrolCan"] or typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"] then
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
        if gasCan == nil and typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"] then
            for _,item in ipairs(typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"]) do
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