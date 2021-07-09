---@class ChunkRegion : zombie.iso.areas.isoregion.ChunkRegion
---@field private totalCreated int
---@field private totalReleased int
---@field private totalReused int
---@field private nextID int
---@field private idStack ArrayDeque|Unknown
---@field private pool ArrayDeque|Unknown
---@field private isInPool boolean
---@field private color Color
---@field private ID int
---@field private zLayer int
---@field private squareSize int
---@field private roofCnt int
---@field private chunkBorderSquaresCnt int
---@field private enclosed boolean[]
---@field private enclosedCache boolean
---@field private connectedNeighbors ArrayList|Unknown
---@field private allNeighbors ArrayList|Unknown
---@field private isDirtyEnclosed boolean
---@field private isDirtyRoofed boolean
---@field private masterRegion MasterRegion
ChunkRegion = {}

---@protected
---@return ChunkRegion
function ChunkRegion:getConnectedNeighborWithLargestMaster() end

---@public
---@return Color
function ChunkRegion:getColor() end

---@protected
---@return void
function ChunkRegion:setDirtyEnclosed() end

---@protected
---@param arg0 ChunkRegion
---@return void
function ChunkRegion:addNeighbor(arg0) end

---@public
---@return int
function ChunkRegion:getzLayer() end

---@public
---@return int
function ChunkRegion:getChunkBorderSquaresCnt() end

---@public
---@return MasterRegion
function ChunkRegion:getMasterRegion() end

---@protected
---@return void
function ChunkRegion:resetChunkBorderSquaresCnt() end

---@protected
---@return ChunkRegion
function ChunkRegion:getFirstNeighborWithMaster() end

---@public
---@param arg0 ChunkRegion
---@return boolean
function ChunkRegion:containsConnectedNeighbor(arg0) end

---@private
---@return void
function ChunkRegion:resetEnclosed() end

---@protected
---@return void
function ChunkRegion:addRoof() end

---@protected
---@return void
function ChunkRegion:addSquareCount() end

---@protected
---@return MasterRegion
function ChunkRegion:unlinkFromMaster() end

---@protected
---@return void
function ChunkRegion:removeChunkBorderSquaresCnt() end

---@protected
---@param arg0 MasterRegion
---@return void
function ChunkRegion:setMasterRegion(arg0) end

---@protected
---@param arg0 ChunkRegion
---@return void
function ChunkRegion:removeNeighbor(arg0) end

---@protected
---@return void
function ChunkRegion:addChunkBorderSquaresCnt() end

---@protected
---@param arg0 ChunkRegion
---@return void
function ChunkRegion:addConnectedNeighbor(arg0) end

---@param arg0 int
---@return ChunkRegion
function ChunkRegion:alloc(arg0) end

---@protected
---@return ArrayList|Unknown
function ChunkRegion:getConnectedNeighbors() end

---@protected
---@return void
function ChunkRegion:resetRoofCnt() end

---@param arg0 ChunkRegion
---@return void
function ChunkRegion:release(arg0) end

---@protected
---@return ArrayList|Unknown
function ChunkRegion:getAllNeighbors() end

---@private
---@return ChunkRegion
function ChunkRegion:reset() end

---@public
---@return int
function ChunkRegion:getID() end

---@protected
---@param arg0 ChunkRegion
---@return void
function ChunkRegion:removeConnectedNeighbor(arg0) end

---@protected
---@return void
function ChunkRegion:printStats() end

---@public
---@param arg0 int
---@return boolean
function ChunkRegion:containsConnectedNeighborID(arg0) end

---@public
---@return ArrayList|Unknown
function ChunkRegion:getDebugConnectedNeighborCopy() end

---@protected
---@param arg0 byte
---@param arg1 boolean
---@return void
function ChunkRegion:setEnclosed(arg0, arg1) end

---@protected
---@return void
function ChunkRegion:unlinkNeighbors() end

---@public
---@return int
function ChunkRegion:getSquareSize() end

---@public
---@return int
function ChunkRegion:getRoofCnt() end

---@public
---@return boolean
function ChunkRegion:getIsEnclosed() end
