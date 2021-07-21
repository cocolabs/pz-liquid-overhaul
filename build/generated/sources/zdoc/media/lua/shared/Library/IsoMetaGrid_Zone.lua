---@class IsoMetaGrid.Zone : zombie.iso.IsoMetaGrid.Zone
---@field public id Double
---@field public hourLastSeen int
---@field public lastActionTimestamp int
---@field public haveConstruction boolean
---@field public spawnedZombies HashMap|Unknown|Unknown
---@field public zombiesTypeToSpawn String
---@field public spawnSpecialZombies Boolean
---@field public name String
---@field public type String
---@field public x int
---@field public y int
---@field public z int
---@field public w int
---@field public h int
---@field public pickedXForZoneStory int
---@field public pickedYForZoneStory int
---@field public pickedRZStory RandomizedZoneStoryBase
---@field private originalName String
IsoMetaGrid_Zone = {}

---@public
---@return ArrayList|IsoGridSquare
function IsoMetaGrid_Zone:getSquares() end

---@public
---@return float
function IsoMetaGrid_Zone:getHoursSinceLastSeen() end

---@public
---@return void
function IsoMetaGrid_Zone:sendToServer() end

---@public
---@return int
function IsoMetaGrid_Zone:getX() end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setY(arg0) end

---@public
---@return int
function IsoMetaGrid_Zone:getZombieDensity() end

---@public
---@return IsoGridSquare
function IsoMetaGrid_Zone:getRandomUnseenSquareInZone() end

---@public
---@return boolean
function IsoMetaGrid_Zone:haveCons() end

---@public
---@param have boolean
---@return void
function IsoMetaGrid_Zone:setHaveConstruction(have) end

---@public
---@param arg0 String
---@return void
function IsoMetaGrid_Zone:setOriginalName(arg0) end

---@public
---@return int
function IsoMetaGrid_Zone:getY() end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setPickedXForZoneStory(arg0) end

---@public
---@return String
function IsoMetaGrid_Zone:getName() end

---@public
---@return String
function IsoMetaGrid_Zone:getOriginalName() end

---@public
---@param sq IsoGridSquare
---@return void
function IsoMetaGrid_Zone:removeSquare(sq) end

---@public
---@return int
function IsoMetaGrid_Zone:getLastActionTimestamp() end

---@public
---@return IsoGridSquare
function IsoMetaGrid_Zone:getRandomSquareInZone() end

---@public
---@param x int
---@param y int
---@param z int
---@param w int
---@param h int
---@return boolean
function IsoMetaGrid_Zone:intersects(x, y, z, w, h) end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setH(arg0) end

---@public
---@param type String
---@return void
function IsoMetaGrid_Zone:setType(type) end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setW(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return boolean
function IsoMetaGrid_Zone:contains(arg0, arg1, arg2) end

---@public
---@return int
function IsoMetaGrid_Zone:getHeight() end

---@public
---@return int
function IsoMetaGrid_Zone:getWidth() end

---@public
---@return void
function IsoMetaGrid_Zone:setHourSeenToCurrent() end

---@public
---@param lastActionTimestamp int
---@return void
function IsoMetaGrid_Zone:setLastActionTimestamp(lastActionTimestamp) end

---@public
---@param sq IsoGridSquare
---@return void
function IsoMetaGrid_Zone:addSquare(sq) end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setX(arg0) end

---@public
---@return String
function IsoMetaGrid_Zone:getType() end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@param arg4 int
---@param arg5 ArrayList|Unknown
---@return boolean
function IsoMetaGrid_Zone:difference(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@param arg0 int
---@return void
function IsoMetaGrid_Zone:setPickedYForZoneStory(arg0) end

---@public
---@param name String
---@return void
function IsoMetaGrid_Zone:setName(name) end
