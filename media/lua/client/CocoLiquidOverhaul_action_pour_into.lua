require "TimedActions/ISBaseTimedAction"

CLO_ActionPourInto = ISBaseTimedAction:derive("ISTransferWaterAction")

-- isValid
function CLO_ActionPourInto:isValid()
	return true
end

-- start
function CLO_ActionPourInto:start()
	self.itemFrom:setJobType(getText("IGUI_JobType_PourOut"))
	self.itemTo:setJobType(getText("IGUI_JobType_PourIn"))

	self.itemFrom:setJobDelta(0.0)
	self.itemTo:setJobDelta(0.0)

	self:setOverrideHandModels(nil, "WaterBottle")
	self:setActionAnim("Pour")
end

-- stop
function CLO_ActionPourInto:stop()
	ISBaseTimedAction.stop(self)
	if self.itemFrom ~= nil then
		self.itemFrom:setJobDelta(0.0)
	end
	if self.itemTo ~= nil then
		self.itemTo:setJobDelta(0.0)
	end
end

-- update
function CLO_ActionPourInto:update()
	if self.itemFrom ~= nil and self.itemTo ~= nil then
		self.itemFrom:setJobDelta(self:getJobDelta())
		self.itemFrom:setUsedDelta(self.itemFromBeginDelta + ((self.itemFromEndingDelta - self.itemFromBeginDelta) * self:getJobDelta()))

		self.itemTo:setJobDelta(self:getJobDelta())
		self.itemTo:setUsedDelta(self.itemToBeginDelta + ((self.itemToEndingDelta - self.itemToBeginDelta) * self:getJobDelta()))
	end
end

-- perform
function CLO_ActionPourInto:perform()
	if self.itemFrom ~= nil and self.itemTo ~= nil then
		self.itemFrom:getContainer():setDrawDirty(true)
		self.itemFrom:setJobDelta(0.0)
		self.itemTo:setJobDelta(0.0)
		if self.itemTo:getContainer() then
			self.itemTo:getContainer():setDrawDirty(true)
		end

		if self.itemFromEndingDelta == 0 then
			self.itemFrom:setUsedDelta(0)
			self.itemFrom:Use()
		else
			self.itemFrom:setUsedDelta(self.itemFromEndingDelta)
		end

		self.itemTo:setUsedDelta(self.itemToEndingDelta)
		self.itemTo:updateWeight()
	end

	-- needed to remove from queue / start next.
	ISBaseTimedAction.perform(self)
end

-- new
---@param playerObj IsoPlayer
---@param itemFrom InventoryItem
---@param itemTo InventoryItem
function CLO_ActionPourInto:new(playerObj, itemFrom, itemTo, itemFromEndingDelta, itemToEndingDelta)
	local o = {}
	setmetatable(o, self)
	self.__index = self
	o.character = playerObj
	o.itemFrom = itemFrom
	o.itemFromBeginDelta = itemFrom:getUsedDelta()
	o.itemFromEndingDelta = itemFromEndingDelta
	o.itemTo = itemTo
	o.itemToBeginDelta = itemTo:getUsedDelta()
	o.itemToEndingDelta = itemToEndingDelta
	o.stopOnWalk = true
	o.stopOnRun = true
	o.maxTime = ((itemFrom:getUsedDelta() - itemFromEndingDelta) / itemFrom:getUseDelta()) * 5
	return o
end
