CLO_Print("Overriding: 'ISTakeGasolineFromVehicle:start'")
function ISTakeGasolineFromVehicle:start()
    local itemType = self.item:getType()
    if itemType == "EmptyPetrolCan" or itemType == "Coco_LargeEmptyPetrolCan" then
        local wasPrimary = self.character:getPrimaryHandItem() == self.item
        local wasSecondary = self.character:getSecondaryHandItem() == self.item
        self.character:getInventory():DoRemoveItem(self.item)
        if itemType == "EmptyPetrolCan" then
            self.item = self.character:getInventory():AddItem("Base.PetrolCan")
        elseif itemType == "Coco_LargeEmptyPetrolCan" then
            self.item = self.character:getInventory():AddItem("CocoLiquidOverhaulItems.Coco_LargePetrolCan")
        end
        self.item:setUsedDelta(0)
        if wasPrimary then
            self.character:setPrimaryHandItem(self.item)
        end
        if wasSecondary then
            self.character:setSecondaryHandItem(self.item)
        end
    end
    self.tankStart = self.part:getContainerContentAmount()
    self.itemStart = self.item:getUsedDelta()
    local add = (1.0 - self.itemStart) * Vehicles.JerryCanLitres
    local take = math.min(add, self.tankStart)
    self.tankTarget = self.tankStart - take
    self.itemTarget = self.itemStart + take / Vehicles.JerryCanLitres
    self.amountSent = math.ceil(self.tankStart)

    self.action:setTime(take * 50)

    self:setActionAnim("TakeGasFromVehicle")
    self:setOverrideHandModels(nil, self.item:getStaticModel())
end