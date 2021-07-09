---@class MasterRegion : zombie.iso.areas.isoregion.MasterRegion
---@field private totalCreated int
---@field private totalReleased int
---@field private totalReused int
---@field private nextID int
---@field private idStack ArrayDeque|Unknown
---@field private pool ArrayDeque|Unknown
---@field private isInPool boolean
---@field private ID int
---@field private color Color
---@field private enclosed boolean
---@field private chunkRegions ArrayList|Unknown
---@field private squareSize int
---@field private roofCnt int
---@field private isDirtyEnclosed boolean
---@field private isDirtyRoofed boolean
---@field private neighbors ArrayList|Unknown
MasterRegion = {}

---@public
---@return int
function MasterRegion:getID() end

---@protected
---@param arg0 ChunkRegion
---@return boolean
function MasterRegion:containsChunkRegion(arg0) end

---@private
---@return void
function MasterRegion:recalcEnclosed() end

---@public
---@return boolean
function MasterRegion:isPlayerRoom() end

---@public
---@return ArrayList|Unknown
function MasterRegion:getDebugChunkRegionCopy() end

---@protected
---@return void
function MasterRegion:linkNeighbors() end

---@private
---@param arg0 MasterRegion
---@return void
function MasterRegion:addNeighbor(arg0) end

---@public
---@return int
function MasterRegion:getRoofCnt() end

---@protected
---@param arg0 MasterRegion
---@return void
function MasterRegion:merge(arg0) end

---@public
---@return int
function MasterRegion:getSquareSize() end

---@public
---@return boolean
function MasterRegion:isEnclosed() end

---@protected
---@return void
function MasterRegion:printStats() end

---@protected
---@param arg0 int
---@return void
function MasterRegion:removeRoofs(arg0) end

---@public
---@return boolean
function MasterRegion:isFogMask() end

---@public
---@return Color
function MasterRegion:getColor() end

---@public
---@return ArrayList|Unknown
function MasterRegion:getNeighbors() end

---@protected
---@return void
function MasterRegion:setDirtyEnclosed() end

---@protected
---@param arg0 ArrayList|Unknown
---@return ArrayList|Unknown
function MasterRegion:swapChunkRegions(arg0) end

---@param arg0 MasterRegion
---@return void
function MasterRegion:release(arg0) end

---@public
---@return ArrayList|Unknown
function MasterRegion:getDebugConnectedNeighborCopy() end

---@public
---@return boolean
function MasterRegion:isFullyRoofed() end

---@protected
---@param arg0 ChunkRegion
---@return void
function MasterRegion:removeChunkRegion(arg0) end

---@protected
---@return void
function MasterRegion:unlinkNeighbors() end

---@protected
---@param arg0 ChunkRegion
---@return void
function MasterRegion:addChunkRegion(arg0) end

---@protected
---@return void
function MasterRegion:addRoof() end

---@private
---@return MasterRegion
function MasterRegion:reset() end

---@return MasterRegion
function MasterRegion:alloc() end

---@protected
---@return void
function MasterRegion:resetSquareSize() end

---@public
---@return int
function MasterRegion:size() end

---@private
---@param arg0 MasterRegion
---@return void
function MasterRegion:removeNeighbor(arg0) end
