---@class IsoBarbecue : zombie.iso.objects.IsoBarbecue
---@field bHasPropaneTank boolean
---@field FuelAmount int
---@field bLit boolean
---@field bIsSmouldering boolean
---@field protected LastUpdateTime float
---@field protected MinuteAccumulator float
---@field protected MinutesSinceExtinguished int
---@field normalSprite IsoSprite
---@field noTankSprite IsoSprite
---@field private heatSource IsoHeatSource
---@field private SMOULDER_MINUTES int
IsoBarbecue = {}

---@private
---@return void
function IsoBarbecue:updateSprite() end

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function IsoBarbecue:save(arg0, arg1) end

---@public
---@param tank InventoryItem
---@return void
function IsoBarbecue:setPropaneTank(tank) end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoBarbecue:getObjectName() end

---@public
---@param arg0 IsoSprite
---@return void
function IsoBarbecue:setSprite(arg0) end

---@public
---@return boolean
function IsoBarbecue:hasPropaneTank() end

---@public
---@return void
function IsoBarbecue:turnOn() end

---@public
---@return boolean
function IsoBarbecue:isLit() end

---@public
---@return InventoryItem
function IsoBarbecue:removePropaneTank() end

---Overrides:
---
---loadChange in class IsoObject
---@public
---@param change String
---@param bb ByteBuffer
---@return void
function IsoBarbecue:loadChange(change, bb) end

---@public
---@return float
function IsoBarbecue:getTemperature() end

---@public
---@param arg0 IsoSprite
---@return boolean
function IsoBarbecue:isSpriteWithPropaneTank(arg0) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoBarbecue:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@return boolean
function IsoBarbecue:isPropaneBBQ() end

---@public
---@param arg0 IsoSprite
---@return boolean
function IsoBarbecue:isSpriteWithoutPropaneTank(arg0) end

---Overrides:
---
---update in class IsoObject
---@public
---@return void
function IsoBarbecue:update() end

---@public
---@return boolean
function IsoBarbecue:hasFuel() end

---@public
---@param amount int
---@return int
function IsoBarbecue:useFuel(amount) end

---Overrides:
---
---addToWorld in class IsoObject
---@public
---@return void
function IsoBarbecue:addToWorld() end

---@public
---@return boolean
function IsoBarbecue:isSmouldering() end

---@public
---@param units int
---@return void
function IsoBarbecue:setFuelAmount(units) end

---@public
---@param units int
---@return void
function IsoBarbecue:addFuel(units) end

---@public
---@return void
function IsoBarbecue:removeFromWorld() end

---@public
---@return void
function IsoBarbecue:toggle() end

---@public
---@return void
function IsoBarbecue:extinguish() end

---@public
---@return void
function IsoBarbecue:turnOff() end

---@private
---@return void
function IsoBarbecue:updateHeatSource() end

---Overrides:
---
---saveChange in class IsoObject
---@public
---@param change String
---@param tbl KahluaTable
---@param bb ByteBuffer
---@return void
function IsoBarbecue:saveChange(change, tbl, bb) end

---@public
---@param lit boolean
---@return void
function IsoBarbecue:setLit(lit) end

---@public
---@return int
function IsoBarbecue:getFuelAmount() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoBarbecue:load(arg0, arg1, arg2) end
