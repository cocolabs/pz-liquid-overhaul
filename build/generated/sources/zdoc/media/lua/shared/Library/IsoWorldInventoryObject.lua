---@class IsoWorldInventoryObject : zombie.iso.objects.IsoWorldInventoryObject
---@field public item InventoryItem
---@field public xoff float
---@field public yoff float
---@field public zoff float
---@field public removeProcess boolean
---@field public dropTime double
IsoWorldInventoryObject = {}

---Overrides:
---
---Serialize in class IsoObject
---@public
---@return boolean
function IsoWorldInventoryObject:Serialize() end

---Overrides:
---
---HasTooltip in class IsoObject
---@public
---@return boolean
function IsoWorldInventoryObject:HasTooltip() end

---Overrides:
---
---onMouseLeftClick in class IsoObject
---@public
---@param x int
---@param y int
---@return boolean
function IsoWorldInventoryObject:onMouseLeftClick(x, y) end

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoWorldInventoryObject:load(input, WorldVersion) end

---@public
---@return InventoryItem
function IsoWorldInventoryObject:getItem() end

---@public
---@return int
function IsoWorldInventoryObject:getWaterAmount() end

---Overrides:
---
---renderObjectPicker in class IsoObject
---@public
---@param x float
---@param y float
---@param z float
---@param lightInfo ColorInfo
---@return void
function IsoWorldInventoryObject:renderObjectPicker(x, y, z, lightInfo) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoWorldInventoryObject:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@private
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return void
function IsoWorldInventoryObject:debugDrawLocation(arg0, arg1, arg2) end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoWorldInventoryObject:getObjectName() end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoObject
---@public
---@param output ByteBuffer
---@return void
function IsoWorldInventoryObject:save(output) end

---@public
---@param arg0 int
---@return void
function IsoWorldInventoryObject:setWaterAmount(arg0) end

---@public
---@return void
function IsoWorldInventoryObject:update() end

---@public
---@return void
function IsoWorldInventoryObject:removeFromWorld() end

---@public
---@param arg0 int
---@return float
function IsoWorldInventoryObject:getScreenPosY(arg0) end

---@public
---@return boolean
function IsoWorldInventoryObject:finishupdate() end

---@public
---@return void
function IsoWorldInventoryObject:softReset() end

---Overrides:
---
---DoTooltip in class IsoObject
---@public
---@param tooltipUI ObjectTooltip
---@return void
function IsoWorldInventoryObject:DoTooltip(tooltipUI) end

---@public
---@param arg0 int
---@return float
function IsoWorldInventoryObject:getScreenPosX(arg0) end

---@public
---@return void
function IsoWorldInventoryObject:updateSprite() end

---@private
---@return void
function IsoWorldInventoryObject:debugHitTest() end

---Overrides:
---
---removeFromSquare in class IsoObject
---@public
---@return void
function IsoWorldInventoryObject:removeFromSquare() end

---@public
---@return void
function IsoWorldInventoryObject:addToWorld() end
