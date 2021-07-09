---@class IsoFireplace : zombie.iso.objects.IsoFireplace
---@field FuelAmount int
---@field bLit boolean
---@field bSmouldering boolean
---@field protected LastUpdateTime float
---@field protected MinuteAccumulator float
---@field protected MinutesSinceExtinguished int
---@field protected FuelSprite IsoSprite
---@field protected FuelSpriteIndex int
---@field protected FireSpriteIndex int
---@field protected LightSource IsoLightSource
---@field protected heatSource IsoHeatSource
---@field private SMOULDER_MINUTES int
IsoFireplace = {}

---Overrides:
---
---update in class IsoObject
---@public
---@return void
function IsoFireplace:update() end

---@public
---@return boolean
function IsoFireplace:isLit() end

---@private
---@return int
function IsoFireplace:calcLightRadius() end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoFireplace:getObjectName() end

---@private
---@return void
function IsoFireplace:updateFireSprite() end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoFireplace:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoFireplace:load(input, WorldVersion) end

---@public
---@return boolean
function IsoFireplace:hasFuel() end

---Overrides:
---
---loadChange in class IsoObject
---@public
---@param change String
---@param bb ByteBuffer
---@return void
function IsoFireplace:loadChange(change, bb) end

---@public
---@return float
function IsoFireplace:getTemperature() end

---@private
---@return void
function IsoFireplace:updateHeatSource() end

---@public
---@return boolean
function IsoFireplace:isSmouldering() end

---@public
---@param amount int
---@return int
function IsoFireplace:useFuel(amount) end

---@public
---@param units int
---@return void
function IsoFireplace:addFuel(units) end

---Overrides:
---
---removeFromWorld in class IsoObject
---@public
---@return void
function IsoFireplace:removeFromWorld() end

---@public
---@param units int
---@return void
function IsoFireplace:setFuelAmount(units) end

---@private
---@return void
function IsoFireplace:updateFuelSprite() end

---@public
---@param lit boolean
---@return void
function IsoFireplace:setLit(lit) end

---@private
---@return void
function IsoFireplace:updateLightSource() end

---Overrides:
---
---addToWorld in class IsoObject
---@public
---@return void
function IsoFireplace:addToWorld() end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoObject
---@public
---@param output ByteBuffer
---@return void
function IsoFireplace:save(output) end

---@public
---@param arg0 JVector2
---@return JVector2
function IsoFireplace:getFacingPosition(arg0) end

---@public
---@return int
function IsoFireplace:getFuelAmount() end

---@public
---@return void
function IsoFireplace:extinguish() end

---Overrides:
---
---saveChange in class IsoObject
---@public
---@param change String
---@param tbl KahluaTable
---@param bb ByteBuffer
---@return void
function IsoFireplace:saveChange(change, tbl, bb) end
