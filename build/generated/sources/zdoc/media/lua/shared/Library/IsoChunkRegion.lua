---@class IsoChunkRegion : zombie.iso.areas.isoregion.regions.IsoChunkRegion
---@field private manager IsoRegionManager
---@field private isInPool boolean
---@field private color Color
---@field private ID int
---@field private zLayer byte
---@field private squareSize byte
---@field private roofCnt byte
---@field private chunkBorderSquaresCnt byte
---@field private enclosed boolean[]
---@field private enclosedCache boolean
---@field private connectedNeighbors ArrayList|Unknown
---@field private allNeighbors ArrayList|Unknown
---@field private isDirtyEnclosed boolean
---@field private isoWorldRegion IsoWorldRegion
IsoChunkRegion = {}

---@public
---@return int
function IsoChunkRegion:getID() end

---@public
---@return int
function IsoChunkRegion:getSquareSize() end

---@protected
---@param arg0 IsoChunkRegion
---@return void
function IsoChunkRegion:removeConnectedNeighbor(arg0) end

---@protected
---@return void
function IsoChunkRegion:resetChunkBorderSquaresCnt() end

---@public
---@param arg0 IsoChunkRegion
---@return void
function IsoChunkRegion:addNeighbor(arg0) end

---@public
---@param arg0 int
---@return boolean
function IsoChunkRegion:containsConnectedNeighborID(arg0) end

---@protected
---@return boolean
function IsoChunkRegion:isInPool() end

---@public
---@return IsoWorldRegion
function IsoChunkRegion:getIsoWorldRegion() end

---@public
---@return void
function IsoChunkRegion:addRoof() end

---@protected
---@return void
function IsoChunkRegion:removeChunkBorderSquaresCnt() end

---@public
---@param arg0 IsoChunkRegion
---@return void
function IsoChunkRegion:addConnectedNeighbor(arg0) end

---@private
---@return void
function IsoChunkRegion:resetEnclosed() end

---@public
---@return void
function IsoChunkRegion:addChunkBorderSquaresCnt() end

---@public
---@return ArrayList|Unknown
function IsoChunkRegion:getDebugConnectedNeighborCopy() end

---@public
---@return void
function IsoChunkRegion:addSquareCount() end

---@public
---@return int
function IsoChunkRegion:getRoofCnt() end

---@public
---@return boolean
function IsoChunkRegion:getIsEnclosed() end

---@public
---@return ArrayList|Unknown
function IsoChunkRegion:getConnectedNeighbors() end

---@public
---@return Color
function IsoChunkRegion:getColor() end

---@public
---@return void
function IsoChunkRegion:resetRoofCnt() end

---@public
---@param arg0 byte
---@param arg1 boolean
---@return void
function IsoChunkRegion:setEnclosed(arg0, arg1) end

---@public
---@return int
function IsoChunkRegion:getChunkBorderSquaresCnt() end

---@public
---@return int
function IsoChunkRegion:getzLayer() end

---@public
---@param arg0 IsoWorldRegion
---@return void
function IsoChunkRegion:setIsoWorldRegion(arg0) end

---@protected
---@return IsoChunkRegion
function IsoChunkRegion:getFirstNeighborWithIsoWorldRegion() end

---@public
---@param arg0 IsoChunkRegion
---@return boolean
function IsoChunkRegion:containsConnectedNeighbor(arg0) end

---@protected
---@return void
function IsoChunkRegion:setDirtyEnclosed() end

---@public
---@return int
function IsoChunkRegion:getNeighborCount() end

---@public
---@return IsoChunkRegion
function IsoChunkRegion:getConnectedNeighborWithLargestIsoWorldRegion() end

---@protected
---@param arg0 IsoChunkRegion
---@return void
function IsoChunkRegion:removeNeighbor(arg0) end

---@protected
---@return void
function IsoChunkRegion:unlinkNeighbors() end

---@protected
---@return IsoChunkRegion
function IsoChunkRegion:reset() end

---@protected
---@param arg0 int
---@param arg1 int
---@return void
function IsoChunkRegion:init(arg0, arg1) end

---@protected
---@return ArrayList|Unknown
function IsoChunkRegion:getAllNeighbors() end

---@public
---@return IsoWorldRegion
function IsoChunkRegion:unlinkFromIsoWorldRegion() end
