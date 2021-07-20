function CLO_Override_ISTakeFuel()

    CLO_Print("Overriding: 'ISTakeFuel:start'")
    function ISTakeFuel:start()
        self.petrolCan:setJobType(getText("ContextMenu_TakeGasFromPump"))
        self.petrolCan:setJobDelta(0.0)

        --- check if current item is empty
        local itemType = self.petrolCan:getType()
        local isEmpty = itemType == "EmptyPetrolCan"
        local customEmpty = nil
        for i = 1, #CLO_ModSettings.CustomFuelItems do
            local fuelItem = CLO_ModSettings.CustomFuelItems[i]
            if not isEmpty and itemType == fuelItem.empty then
                customEmpty = fuelItem
                break
            end
        end

        --- let's transform an empty can into an empty petrol can
        if isEmpty or customEmpty then
            local isPrimary = self.petrolCan == self.character:getPrimaryHandItem()
            local emptyCan = self.petrolCan
            if isEmpty then
                self.petrolCan = self.character:getInventory():AddItem("Base.PetrolCan")
            elseif customEmpty then
                self.petrolCan = self.character:getInventory():AddItem(customEmpty.module .. "." .. customEmpty.full)
            end
            self.petrolCan:setUsedDelta(0)

            if isPrimary then
                self.character:setPrimaryHandItem(self.petrolCan)
            else
                self.character:setSecondaryHandItem(self.petrolCan)
            end
            self.character:getInventory():Remove(emptyCan)
        end

        local pumpCurrent = 1000 + tonumber(self.square:getProperties():Val("fuelAmount"))
        local itemCurrent = math.floor(self.petrolCan:getUsedDelta() / self.petrolCan:getUseDelta() + 0.001)
        local itemMax = math.floor(1 / self.petrolCan:getUseDelta() + 0.001)
        local take = math.min(pumpCurrent, itemMax - itemCurrent)
        self.action:setTime(take * 50)
        self.itemStart = itemCurrent
        self.itemTarget = itemCurrent + take

        self:setActionAnim("TakeGasFromPump")
        self:setOverrideHandModels(nil, self.petrolCan:getStaticModel())
    end

end