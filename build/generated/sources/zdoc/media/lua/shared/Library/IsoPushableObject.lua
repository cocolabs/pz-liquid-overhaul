---@class IsoPushableObject : zombie.iso.IsoPushableObject
---@field public carryCapacity int
---@field public emptyWeight float
---@field public connectList ArrayList|IsoPushableObject
---@field public ox float
---@field public oy float
IsoPushableObject = {}

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function IsoPushableObject:save(arg0, arg1) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoPushableObject:load(arg0, arg1, arg2) end

---Overrides:
---
---getWeight in class IsoMovingObject
---@public
---@param x float
---@param y float
---@return float
function IsoPushableObject:getWeight(x, y) end

---Overrides:
---
---DoCollideNorS in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:DoCollideNorS() end

---Overrides:
---
---getObjectName in class IsoMovingObject
---@public
---@return String
function IsoPushableObject:getObjectName() end

---Overrides:
---
---Serialize in class IsoObject
---@public
---@return boolean
function IsoPushableObject:Serialize() end

---Overrides:
---
---update in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:update() end

---Overrides:
---
---DoCollideWorE in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:DoCollideWorE() end
