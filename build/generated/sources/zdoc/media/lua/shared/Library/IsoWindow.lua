---@class IsoWindow : zombie.iso.objects.IsoWindow
---@field public Health int
---@field public MaxHealth int
---@field public type IsoWindow.WindowType
---@field closedSprite IsoSprite
---@field smashedSprite IsoSprite
---@field public north boolean
---@field public Locked boolean
---@field public PermaLocked boolean
---@field public open boolean
---@field openSprite IsoSprite
---@field private destroyed boolean
---@field private glassRemoved boolean
---@field private glassRemovedSprite IsoSprite
---@field public OldNumPlanks int
IsoWindow = {}

---throws java.io.IOException
---
---Overrides:
---
---load in class IsoObject
---@public
---@param input ByteBuffer
---@param WorldVersion int
---@return void
function IsoWindow:load(input, WorldVersion) end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 IsoGridSquare
---@param arg2 IsoGridSquare
---@param arg3 boolean
---@return boolean
function IsoWindow:canClimbThroughHelper(arg0, arg1, arg2, arg3) end

---@public
---@return IsoGameCharacter
---@overload fun(arg0:IsoGridSquare)
function IsoWindow:getFirstCharacterClimbingThrough() end

---@public
---@param arg0 IsoGridSquare
---@return IsoGameCharacter
function IsoWindow:getFirstCharacterClimbingThrough(arg0) end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoWindow:openCloseCurtain(chr) end

---@public
---@param arg0 float
---@return void
---@overload fun(arg0:float, arg1:IsoMovingObject)
---@overload fun(arg0:float, arg1:boolean)
function IsoWindow:Damage(arg0) end

---@public
---@param arg0 float
---@param arg1 IsoMovingObject
---@return void
function IsoWindow:Damage(arg0, arg1) end

---@public
---@param arg0 float
---@param arg1 boolean
---@return void
function IsoWindow:Damage(arg0, arg1) end

---@public
---@return boolean
---@overload fun(sq:IsoGridSquare, north:boolean)
function IsoWindow:canAddSheetRope() end

---@public
---@param sq IsoGridSquare
---@param north boolean
---@return boolean
function IsoWindow:canAddSheetRope(sq, north) end

---@public
---@return boolean
function IsoWindow:isGlassRemoved() end

---@public
---@param destroyed boolean
---@return void
function IsoWindow:setSmashed(destroyed) end

---throws java.io.IOException
---
---Overrides:
---
---save in class IsoObject
---@public
---@param output ByteBuffer
---@return void
function IsoWindow:save(output) end

---Specified by:
---
---isDestroyed in interface Thumpable
---@public
---@return boolean
function IsoWindow:isDestroyed() end

---@public
---@param sprite IsoSprite
---@return void
function IsoWindow:setOpenSprite(sprite) end

---@public
---@param sq IsoGridSquare
---@return boolean
function IsoWindow:isSheetRopeHere(sq) end

---@public
---@return boolean
function IsoWindow:isPermaLocked() end

---@public
---@return void
function IsoWindow:removeFromWorld() end

---@public
---@return boolean
function IsoWindow:isLocked() end

---@public
---@param arg0 int
---@param arg1 boolean
---@return IsoBarricade
function IsoWindow:addBarricadesDebug(arg0, arg1) end

---@public
---@return int
---@overload fun(sq:IsoGridSquare, north:boolean)
function IsoWindow:countAddSheetRope() end

---@public
---@param sq IsoGridSquare
---@param north boolean
---@return int
function IsoWindow:countAddSheetRope(sq, north) end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoWindow:saveState(arg0) end

---@public
---@param owner IsoGameCharacter
---@param weapon HandWeapon
---@return void
function IsoWindow:WeaponHit(owner, weapon) end

---@public
---@return IsoSprite
function IsoWindow:getOpenSprite() end

---@public
---@param arg0 IsoPlayer
---@param arg1 String
---@return boolean
---@overload fun(arg0:IsoPlayer, arg1:IsoGridSquare, arg2:boolean, arg3:String)
function IsoWindow:addSheetRope(arg0, arg1) end

---@public
---@param arg0 IsoPlayer
---@param arg1 IsoGridSquare
---@param arg2 boolean
---@param arg3 String
---@return boolean
function IsoWindow:addSheetRope(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 IsoMovingObject
---@return void
---@overload fun(arg0:boolean)
function IsoWindow:addBrokenGlass(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoWindow:addBrokenGlass(arg0) end

---@public
---@return IsoCurtain
function IsoWindow:HasCurtains() end

---@public
---@return IsoGridSquare
function IsoWindow:getOppositeSquare() end

---@public
---@param sq IsoGridSquare
---@return boolean
---@overload fun(sq:IsoGridSquare, north:boolean)
function IsoWindow:isTopOfSheetRopeHere(sq) end

---@public
---@param sq IsoGridSquare
---@param north boolean
---@return boolean
function IsoWindow:isTopOfSheetRopeHere(sq, north) end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoWindow:removeSheet(chr) end

---Specified by:
---
---Thump in interface Thumpable
---@public
---@param thumper IsoMovingObject
---@return void
function IsoWindow:Thump(thumper) end

---@public
---@return IsoGameCharacter
---@overload fun(arg0:IsoGridSquare)
function IsoWindow:getFirstCharacterClosing() end

---@public
---@param arg0 IsoGridSquare
---@return IsoGameCharacter
function IsoWindow:getFirstCharacterClosing(arg0) end

---@public
---@param chr IsoGameCharacter
---@return boolean
function IsoWindow:canClimbThrough(chr) end

---@public
---@return boolean
function IsoWindow:isBarricaded() end

---@public
---@return IsoBarricade
function IsoWindow:getBarricadeOnSameSquare() end

---Overrides:
---
---TestCollide in class IsoObject
---@public
---@param obj IsoMovingObject
---@param from IsoGridSquare
---@param to IsoGridSquare
---@return boolean
function IsoWindow:TestCollide(obj, from, to) end

---@public
---@param removed boolean
---@return void
function IsoWindow:setGlassRemoved(removed) end

---@public
---@return void
---@overload fun(bRemote:boolean)
---@overload fun(arg0:boolean, arg1:boolean)
function IsoWindow:smashWindow() end

---@public
---@param bRemote boolean
---@return void
function IsoWindow:smashWindow(bRemote) end

---@public
---@param arg0 boolean
---@param arg1 boolean
---@return void
function IsoWindow:smashWindow(arg0, arg1) end

---Overrides:
---
---TestVision in class IsoObject
---@public
---@param from IsoGridSquare
---@param to IsoGridSquare
---@return IsoObject.VisionResult
function IsoWindow:TestVision(from, to) end

---Overrides:
---
---AttackObject in class IsoObject
---@public
---@param owner IsoGameCharacter
---@return void
function IsoWindow:AttackObject(owner) end

---@public
---@param arg0 ByteBufferWriter
---@return void
function IsoWindow:syncIsoObjectSend(arg0) end

---Overrides:
---
---getObjectName in class IsoObject
---@public
---@return String
function IsoWindow:getObjectName() end

---@public
---@param lock boolean
---@return void
function IsoWindow:setIsLocked(lock) end

---Overrides:
---
---getFacingPosition in class IsoObject
---@public
---@param pos JVector2
---@return JVector2
function IsoWindow:getFacingPosition(pos) end

---@public
---@return void
function IsoWindow:addRandomBarricades() end

---@public
---@return boolean
function IsoWindow:isInvincible() end

---@public
---@return IsoGridSquare
function IsoWindow:getIndoorSquare() end

---@public
---@return IsoBarricade
function IsoWindow:getBarricadeOnOppositeSquare() end

---@public
---@return boolean
function IsoWindow:IsOpen() end

---@public
---@param player IsoPlayer
---@return boolean
---@overload fun(player:IsoPlayer, square:IsoGridSquare, north:boolean)
function IsoWindow:removeSheetRope(player) end

---@public
---@param player IsoPlayer
---@param square IsoGridSquare
---@param north boolean
---@return boolean
function IsoWindow:removeSheetRope(player, square, north) end

---@public
---@param arg0 IsoGameCharacter
---@return IsoBarricade
function IsoWindow:getBarricadeOppositeCharacter(arg0) end

---@public
---@return boolean
function IsoWindow:isSmashed() end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoWindow:ToggleWindow(chr) end

---@public
---@return IsoSprite
function IsoWindow:getSmashedSprite() end

---@public
---@param chr IsoGameCharacter
---@return void
function IsoWindow:addSheet(chr) end

---@public
---@return boolean
function IsoWindow:haveSheetRope() end

---@public
---@return void
function IsoWindow:removeBrokenGlass() end

---@public
---@param permaLock Boolean
---@return void
function IsoWindow:setPermaLocked(permaLock) end

---@public
---@param arg0 IsoGameCharacter
---@return IsoBarricade
function IsoWindow:getBarricadeForCharacter(arg0) end

---@public
---@return void
function IsoWindow:addToWorld() end

---@public
---@param arg0 boolean
---@param arg1 byte
---@param arg2 UdpConnection
---@param arg3 ByteBuffer
---@return void
function IsoWindow:syncIsoObject(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoWindow:loadState(arg0) end

---Overrides:
---
---onMouseLeftClick in class IsoObject
---@public
---@param x int
---@param y int
---@return boolean
function IsoWindow:onMouseLeftClick(x, y) end

---@private
---@return void
function IsoWindow:handleAlarm() end

---@public
---@return boolean
function IsoWindow:getNorth() end

---@public
---@param arg0 IsoGameCharacter
---@return IsoGridSquare
function IsoWindow:getAddSheetSquare(arg0) end

---@public
---@param sprite IsoSprite
---@return void
function IsoWindow:setSmashedSprite(sprite) end

---@public
---@param sq IsoGridSquare
---@return boolean
function IsoWindow:canClimbHere(sq) end

---@public
---@param arg0 IsoGameCharacter
---@return Thumpable
function IsoWindow:getThumpableFor(arg0) end

---@public
---@return IsoGridSquare
function IsoWindow:getInsideSquare() end
