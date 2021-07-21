---@class IsoWorldInventoryObject : zombie.iso.objects.IsoWorldInventoryObject
---@field public item InventoryItem
---@field public xoff float
---@field public yoff float
---@field public zoff float
---@field public removeProcess boolean
---@field public dropTime double
IsoWorldInventoryObject = {}

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function IsoWorldInventoryObject:save(arg0, arg1) end

---@public
---@return void
function IsoWorldInventoryObject:update() end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoWorldInventoryObject:getObjectName() end

---@public
---@param arg0 int
---@return float
function IsoWorldInventoryObject:getScreenPosX(arg0) end

---@public
---@return void
function IsoWorldInventoryObject:addToWorld() end

---@public
---@return boolean
function IsoWorldInventoryObject:finishupdate() end

---Overrides:
---
---DoTooltip in class IsoObject
---@public
---@param tooltipUI ObjectTooltip
---@return void
function IsoWorldInventoryObject:DoTooltip(tooltipUI) end

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
---@return void
function IsoWorldInventoryObject:softReset() end

---Overrides:
---
---HasTooltip in class IsoObject
---@public
---@return boolean
function IsoWorldInventoryObject:HasTooltip() end

---Overrides:
---
---removeFromSquare in class IsoObject
---@public
---@return void
function IsoWorldInventoryObject:removeFromSquare() end

---@public
---@param arg0 int
---@return void
function IsoWorldInventoryObject:setWaterAmount(arg0) end

---@public
---@return InventoryItem
function IsoWorldInventoryObject:getItem() end

---@public
---@return int
function IsoWorldInventoryObject:getWaterAmount() end

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
---@return void
function IsoWorldInventoryObject:debugHitTest() end

---@public
---@return void
function IsoWorldInventoryObject:updateSprite() end

---@public
---@param arg0 int
---@return float
function IsoWorldInventoryObject:getScreenPosY(arg0) end

---@public
---@return void
function IsoWorldInventoryObject:removeFromWorld() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoWorldInventoryObject:load(arg0, arg1, arg2) end

---@private
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return void
function IsoWorldInventoryObject:debugDrawLocation(arg0, arg1, arg2) end

---Overrides:
---
---Serialize in class IsoObject
---@public
---@return boolean
function IsoWorldInventoryObject:Serialize() end

---Overrides:
---
---onMouseLeftClick in class IsoObject
---@public
---@param x int
---@param y int
---@return boolean
function IsoWorldInventoryObject:onMouseLeftClick(x, y) end
