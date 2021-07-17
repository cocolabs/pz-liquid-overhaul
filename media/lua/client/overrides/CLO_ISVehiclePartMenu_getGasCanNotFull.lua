CLO_Print("Overriding: 'ISVehiclePartMenu.getGasCanNotFull'")
function ISVehiclePartMenu.getGasCanNotFull(playerObj, typeToItem)
    -- Prefer an equipped EmptyPetrolCan/PetrolCan, then the fullest PetrolCan, then any EmptyPetrolCan.
    local equipped = playerObj:getPrimaryHandItem()
    if equipped and (equipped:getType() == "PetrolCan" or equipped:getType() == "Coco_LargePetrolCan") and equipped:getUsedDelta() < 1 then
        return equipped
    elseif equipped and (equipped:getType() == "EmptyPetrolCan" or equipped:getType() == "Coco_LargeEmptyPetrolCan") then
        return equipped
    end
    if typeToItem["Base.PetrolCan"] or typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"] then
        local gasCan = nil
        local usedDelta = -1
        if typeToItem["Base.PetrolCan"] then
            for _,item in ipairs(typeToItem["Base.PetrolCan"]) do
                if item:getUsedDelta() < 1 and item:getUsedDelta() > usedDelta then
                    gasCan = item
                    usedDelta = gasCan:getUsedDelta()
                end
            end

        end
        if gasCan == nil and typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"] then
            for _,item in ipairs(typeToItem["CocoLiquidOverhaulItems.Coco_LargePetrolCan"]) do
                if item:getUsedDelta() < 1 and item:getUsedDelta() > usedDelta then
                    gasCan = item
                    usedDelta = gasCan:getUsedDelta()
                end
            end
        end
        if gasCan then return gasCan end
    end
    if typeToItem["Base.EmptyPetrolCan"] then
        return typeToItem["Base.EmptyPetrolCan"][1]
    elseif typeToItem["CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan"] then
        return typeToItem["CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan"][1]
    end
    return nil
end