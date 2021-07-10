---@class DataChunk : zombie.iso.areas.isoregion.DataChunk
---@field private tmpSquares ArrayList|Unknown
---@field private tmpSquaresDone ArrayList|Unknown
---@field private cell DataCell
---@field private hashId int
---@field private chunkX int
---@field private chunkY int
---@field protected highestZ int
---@field protected lastUpdateStamp long
---@field private activeZLayers boolean[]
---@field private dirtyZLayers boolean[]
---@field private squareFlags byte[]
---@field private regionIDs byte[]
---@field private chunkRegions ArrayList|Unknown
---@field private squareArraySize int
---@field private selectedFlags byte
---@field private tmpx int
---@field private tmpy int
---@field private tmpMasters ArrayList|Unknown
---@field private tmpChunks ArrayList|Unknown
---@field private oldList ArrayList|Unknown
DataChunk = {}

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return int
function DataChunk:getCoord1D(arg0, arg1, arg2) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return byte
---@overload fun(arg0:int, arg1:int, arg2:int, arg3:byte, arg4:boolean)
function DataChunk:setOrAddSquare(arg0, arg1, arg2, arg3) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@param arg4 boolean
---@return byte
function DataChunk:setOrAddSquare(arg0, arg1, arg2, arg3, arg4) end

---@private
---@param arg0 ChunkRegion
---@param arg1 MasterRegion
---@return void
function DataChunk:floodFillExpandMaster(arg0, arg1) end

---@protected
---@return void
---@overload fun(arg0:int)
function DataChunk:recalculate() end

---@private
---@param arg0 int
---@return void
function DataChunk:recalculate(arg0) end

---@protected
---@param arg0 int
---@return void
function DataChunk:setDirty(arg0) end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return boolean
function DataChunk:validCoords(arg0, arg1, arg2) end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return ChunkRegion
function DataChunk:floodFill(arg0, arg1, arg2) end

---@protected
---@return void
function DataChunk:recalcRoofs() end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return ChunkRegion
function DataChunk:getRegion(arg0, arg1, arg2) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return boolean
function DataChunk:squareHasFlags(arg0, arg1, arg2, arg3) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return boolean
function DataChunk:squareCanConnect(arg0, arg1, arg2, arg3) end

---@private
---@param arg0 int
---@param arg1 byte
---@return void
function DataChunk:resetEnclosedSide(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return void
function DataChunk:setRegion(arg0, arg1, arg2, arg3) end

---@protected
---@return int
function DataChunk:getChunkX() end

---@protected
---@param arg0 IsoChunk
---@param arg1 ByteBuffer
---@return void
function DataChunk:readChunkDataIntoBuffer(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return byte
---@overload fun(arg0:int, arg1:int, arg2:int, arg3:boolean)
function DataChunk:getSquare(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 boolean
---@return byte
function DataChunk:getSquare(arg0, arg1, arg2, arg3) end

---@protected
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function DataChunk:load(arg0, arg1, arg2) end

---@protected
---@return void
function DataChunk:unsetDirtyAndFloodAll() end

---@protected
---@return int
function DataChunk:getChunkY() end

---@private
---@param arg0 int
---@param arg1 DataChunk
---@param arg2 byte
---@return void
function DataChunk:linkRegionsOnSide(arg0, arg1, arg2) end

---@protected
---@return int
function DataChunk:getHashId() end

---@protected
---@return void
function DataChunk:interConnect() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return boolean
function DataChunk:isExploredPos(arg0, arg1, arg2) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return void
function DataChunk:squareAddFlags(arg0, arg1, arg2, arg3) end

---@protected
---@param arg0 ByteBuffer
---@return void
function DataChunk:save(arg0) end

---@protected
---@param arg0 int
---@return ArrayList|Unknown
function DataChunk:getChunkRegions(arg0) end

---@protected
---@param arg0 DataChunk
---@param arg1 DataChunk
---@param arg2 DataChunk
---@param arg3 DataChunk
---@return void
function DataChunk:link(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return int
function DataChunk:squareGetFlags(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function DataChunk:setSelectedFlags(arg0, arg1, arg2) end

---@protected
---@param arg0 int
---@return boolean
function DataChunk:isDirty(arg0) end

---@protected
---@return void
function DataChunk:setDirtyAllActive() end

---@private
---@param arg0 int
---@return void
function DataChunk:ensureSquareArray(arg0) end

---@public
---@param arg0 byte
---@return boolean
function DataChunk:selectedHasFlags(arg0) end

---@private
---@param arg0 int
---@return void
function DataChunk:ensureSquares(arg0) end

---@private
---@param arg0 DataSquarePos
---@param arg1 byte
---@return DataSquarePos
function DataChunk:getNeighbor(arg0, arg1) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 byte
---@return void
function DataChunk:squareRemoveFlags(arg0, arg1, arg2, arg3) end