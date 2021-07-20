function CLO_Override_ISAddGasolineToVehicle()

    CLO_Print("Overriding: 'ISAddGasolineToVehicle:start'")
    function ISAddGasolineToVehicle:start()
        --self.tankStart = self.part:getContainerContentAmount()
        --self.itemStart = self.item:getUsedDelta()
        --local add = self.part:getContainerCapacity() - self.tankStart
        --local take = math.min(add, self.itemStart * Vehicles.JerryCanLitres)
        --self.tankTarget = self.tankStart + take
        --self.itemTarget = self.itemStart - take / Vehicles.JerryCanLitres
        --self.amountSent = self.tankStart

        local tankCurrent = self.part:getContainerContentAmount()
        local tankMax = self.part:getContainerCapacity()
        local itemCurrent = math.floor(self.item:getUsedDelta() / self.item:getUseDelta() + 0.001)
        local add = tankMax - tankCurrent
        local take = math.min(add, itemCurrent)
        self.itemStart = itemCurrent
        self.itemTarget = math.floor(itemCurrent - take)

        self.action:setTime(take * 50)

        self:setActionAnim("refuelgascan")
        self:setOverrideHandModels(self.item:getStaticModel(), nil)
    end

    CLO_Print("Overriding: 'ISAddGasolineToVehicle:update'")
    function ISAddGasolineToVehicle:update()
        self.character:faceThisObject(self.vehicle)
        self.item:setJobDelta(self:getJobDelta())
        self.item:setJobType(getText("ContextMenu_VehicleAddGas"))

        local actionCurrent = math.floor(self.itemStart + (self.itemTarget - self.itemStart) * self:getJobDelta() + 0.001)
        local itemCurrent = math.floor(self.item:getUsedDelta() / self.item:getUseDelta() + 0.001)

        if actionCurrent < itemCurrent then
            local tankCurrent = self.part:getContainerContentAmount() + (itemCurrent - actionCurrent)
            local args = { vehicle = self.vehicle:getId(), part = self.part:getId(), amount = tankCurrent }
            sendClientCommand(self.character, 'vehicle', 'setContainerContentAmount', args)

            self.item:setUsedDelta(actionCurrent * self.item:getUseDelta())
            if self.item:getUsedDelta() == 0 then self.item:Use() end
        end

        self.character:setMetabolicTarget(Metabolics.HeavyDomestic);
    end

    CLO_Print("Overriding: 'ISAddGasolineToVehicle:perform'")
    function ISAddGasolineToVehicle:perform()
        self.item:setJobDelta(0)

        -- needed to remove from queue / start next.
        ISBaseTimedAction.perform(self)
    end
end