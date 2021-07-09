---@class IsoPushableObject : zombie.iso.IsoPushableObject
---@field public carryCapacity int
---@field public emptyWeight float
---@field public connectList ArrayList|IsoPushableObject
---@field public ox float
---@field public oy float
IsoPushableObject = {}

---Overrides:
---
---DoCollideWorE in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:DoCollideWorE() end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoMovingObject
---@public
---@param output ByteBuffer
---@return void
function IsoPushableObject:save(output) end

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoMovingObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoPushableObject:load(input, WorldVersion) end

---Overrides:
---
---Serialize in class IsoObject
---@public
---@return boolean
function IsoPushableObject:Serialize() end

---Overrides:
---
---DoCollideNorS in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:DoCollideNorS() end

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
---update in class IsoMovingObject
---@public
---@return void
function IsoPushableObject:update() end

---Overrides:
---
---getObjectName in class IsoMovingObject
---@public
---@return String
function IsoPushableObject:getObjectName() end
