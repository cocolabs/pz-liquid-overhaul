---@class IsoCurtain : zombie.iso.objects.IsoCurtain
---@field public Barricaded boolean
---@field public BarricideMaxStrength Integer
---@field public BarricideStrength Integer
---@field public Health Integer
---@field public Locked boolean
---@field public MaxHealth Integer
---@field public PushedMaxStrength Integer
---@field public PushedStrength Integer
---@field closedSprite IsoSprite
---@field public north boolean
---@field public open boolean
---@field openSprite IsoSprite
---@field private destroyed boolean
IsoCurtain = {}

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoCurtain:getObjectName() end

---@public
---@param arg0 ByteBufferWriter
---@return void
function IsoCurtain:syncIsoObjectSend(arg0) end

---Overrides:
---
---syncIsoObject in class IsoObject
---@public
---@param bRemote boolean
---@param val byte
---@param source UdpConnection
---@return void
---@overload fun(arg0:boolean, arg1:byte, arg2:UdpConnection, arg3:ByteBuffer)
function IsoCurtain:syncIsoObject(bRemote, val, source) end

---@public
---@param arg0 boolean
---@param arg1 byte
---@param arg2 UdpConnection
---@param arg3 ByteBuffer
---@return void
function IsoCurtain:syncIsoObject(arg0, arg1, arg2, arg3) end

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoCurtain:load(input, WorldVersion) end

---@public
---@param arg0 IsoGridSquare
---@return boolean
---@overload fun(arg0:IsoGridSquare, arg1:IsoGridSquare)
function IsoCurtain:isAdjacentToSquare(arg0) end

---@public
---@param arg0 IsoGridSquare
---@param arg1 IsoGridSquare
---@return boolean
function IsoCurtain:isAdjacentToSquare(arg0, arg1) end

---Overrides:
---
---TestVision in class IsoObject
---@public
---@param from IsoGridSquare
---@param to IsoGridSquare
---@return IsoObject.VisionResult
function IsoCurtain:TestVision(from, to) end

---Overrides:
---
---onMouseLeftClick in class IsoObject
---@public
---@param x int
---@param y int
---@return boolean
function IsoCurtain:onMouseLeftClick(x, y) end

---@public
---@param arg0 JVector2
---@return JVector2
function IsoCurtain:getFacingPosition(arg0) end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoCurtain:removeSheet(chr) end

---@public
---@return IsoGridSquare
function IsoCurtain:getOppositeSquare() end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoCurtain:ToggleDoor(chr) end

---@public
---@return boolean
function IsoCurtain:IsOpen() end

---@public
---@return boolean
function IsoCurtain:getNorth() end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoObject
---@public
---@param output ByteBuffer
---@return void
function IsoCurtain:save(output) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoCurtain:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@return IsoObject
function IsoCurtain:getObjectAttachedTo() end

---@public
---@param arg0 IsoGameCharacter
---@return boolean
function IsoCurtain:canInteractWith(arg0) end

---@public
---@return void
function IsoCurtain:ToggleDoorSilent() end