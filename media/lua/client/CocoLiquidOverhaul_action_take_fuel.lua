require "TimedActions/ISBaseTimedAction"

CLO_ActionTakeFuel = ISBaseTimedAction:derive("ISTakeFuel")

-- isValid
function CLO_ActionTakeFuel:isValid()
	local pumpCurrent = tonumber(self.square:getProperties():Val("fuelAmount"))
	return pumpCurrent > 0
end

-- waitToStart
function CLO_ActionTakeFuel:waitToStart()
	self.character:faceLocation(self.square:getX(), self.square:getY())
	return self.character:shouldBeTurning()
end

-- start
function CLO_ActionTakeFuel:start()
	self.petrolCan:setJobType(getText("ContextMenu_TakeGasFromPump"))
	self.petrolCan:setJobDelta(0.0)

	-- let's transform an empty can into an empty petrol can
	if self.petrolCan:getType() == "Coco_WaterGallonEmpty" then
		local emptyCan = self.petrolCan
		self.petrolCan = self.character:getInventory():AddItem("CocoLiquidOverhaulItems.Coco_WaterGallonPetrol")
		self.petrolCan:setUsedDelta(0)
		self.character:setPrimaryHandItem(self.petrolCan)
		self.character:setSecondaryHandItem(self.petrolCan)
		self.character:getInventory():Remove(emptyCan)
	end

	local pumpCurrent = 1000 + tonumber(self.square:getProperties():Val("fuelAmount"))
	local itemCurrent = math.floor(self.petrolCan:getUsedDelta() / self.petrolCan:getUseDelta() + 0.001)
	local itemMax = math.floor(1 / self.petrolCan:getUseDelta() + 0.001)
	local take = math.min(pumpCurrent, itemMax - itemCurrent)
	self.action:setTime(take * 50)
	self.itemStart = itemCurrent
	self.itemTarget = itemCurrent + take

	self:setOverrideHandModels(nil, "WaterBottle")
	self:setActionAnim("TakeGasFromPump")
end

-- stop
function CLO_ActionTakeFuel:stop()
	self.petrolCan:setJobDelta(0.0)
	ISBaseTimedAction.stop(self)
end

-- update
function CLO_ActionTakeFuel:update()
	self.petrolCan:setJobDelta(self:getJobDelta())
	--self.character:faceLocation(self.square:getX(), self.square:getY())

	local actionCurrent = math.floor(self.itemStart + (self.itemTarget - self.itemStart) * self:getJobDelta() + 0.001)
	local itemCurrent = math.floor(self.petrolCan:getUsedDelta() / self.petrolCan:getUseDelta() + 0.001)
	if actionCurrent > itemCurrent then
		-- FIXME: sync in multiplayer
		local pumpCurrent = tonumber(self.square:getProperties():Val("fuelAmount"))
		self.square:getProperties():Set("fuelAmount", tostring(pumpCurrent - (actionCurrent - itemCurrent)))

		self.petrolCan:setUsedDelta(actionCurrent * self.petrolCan:getUseDelta() + 0.1)
	end

	self.character:setMetabolicTarget(Metabolics.LightWork)
end

-- perform
function CLO_ActionTakeFuel:perform()
	self.petrolCan:setJobDelta(0.0)

	local itemCurrent = math.floor(self.petrolCan:getUsedDelta() / self.petrolCan:getUseDelta() + 0.001)
	if self.itemTarget > itemCurrent then
		self.petrolCan:setUsedDelta(self.itemTarget * self.petrolCan:getUseDelta())
		-- FIXME: sync in multiplayer
		local pumpCurrent = tonumber(self.square:getProperties():Val("fuelAmount"))
		self.square:getProperties():Set("fuelAmount", tostring(pumpCurrent + (self.itemTarget - itemCurrent)))
	end

	ISBaseTimedAction.perform(self)
end

-- new
---@param playerObj IsoPlayer
---@param square IsoGridSquare
---@param petrolCan InventoryItem
function CLO_ActionTakeFuel:new(playerObj, square, petrolCan, maxTime)
	local o = {}
	setmetatable(o, self)
	self.__index = self
	o.character = playerObj
	o.square = square
	o.petrolCan = petrolCan
	o.stopOnWalk = true
	o.stopOnRun = true
	o.maxTime = maxTime
	return o
end
