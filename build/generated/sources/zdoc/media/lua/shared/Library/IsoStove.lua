---@class IsoStove : zombie.iso.objects.IsoStove
---@field private s_tempObjects ArrayList|Unknown
---@field activated boolean
---@field soundInstance long
---@field private maxTemperature float
---@field private stopTime double
---@field private startTime double
---@field private currentTemperature float
---@field private secondsTimer int
---@field private firstTurnOn boolean
---@field private broken boolean
---@field private hasMetal boolean
IsoStove = {}

---Specified by:
---
---Activated in interface Activatable
---@public
---@return boolean
function IsoStove:Activated() end

---Turn on or off the stove, if no electricity it won't work
---
---Specified by:
---
---Toggle in interface Activatable
---@public
---@return void
function IsoStove:Toggle() end

---@public
---@return void
function IsoStove:update() end

---@public
---@return float
function IsoStove:getMaxTemperature() end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoStove:getObjectName() end

---@public
---@param arg0 float
---@return void
function IsoStove:setMaxTemperature(arg0) end

---@private
---@return void
function IsoStove:doSound() end

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoStove:load(input, WorldVersion) end

---@private
---@return void
function IsoStove:doOverlay() end

---@private
---@return boolean
function IsoStove:hasMetal() end

---@public
---@param arg0 boolean
---@return void
function IsoStove:setBroken(arg0) end

---Specified by:
---
---getActivatableType in interface Activatable
---@public
---@return String
function IsoStove:getActivatableType() end

---@public
---@return void
function IsoStove:sync() end

---@public
---@return boolean
function IsoStove:isTemperatureChanging() end

---@public
---@param arg0 int
---@return void
function IsoStove:setTimer(arg0) end

---@public
---@param arg0 boolean
---@param arg1 boolean
---@return void
function IsoStove:syncSpriteGridObjects(arg0, arg1) end

---@public
---@return int
function IsoStove:getTimer() end

---@public
---@return int
function IsoStove:isRunningFor() end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoObject
---@public
---@param output ByteBuffer
---@return void
function IsoStove:save(output) end

---Overrides:
---
---addToWorld in class IsoObject
---@public
---@return void
function IsoStove:addToWorld() end

---@public
---@param arg0 boolean
---@return void
function IsoStove:setActivated(arg0) end

---@public
---@param arg0 ByteBufferWriter
---@return void
function IsoStove:syncIsoObjectSend(arg0) end

---@public
---@return float
function IsoStove:getCurrentTemperature() end

---@public
---@return boolean
function IsoStove:isBroken() end

---@public
---@param arg0 boolean
---@param arg1 byte
---@param arg2 UdpConnection
---@param arg3 ByteBuffer
---@return void
function IsoStove:syncIsoObject(arg0, arg1, arg2, arg3) end
