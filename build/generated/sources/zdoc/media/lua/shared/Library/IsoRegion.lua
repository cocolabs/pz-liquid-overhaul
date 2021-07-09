---@class IsoRegion : zombie.iso.areas.isoregion.IsoRegion
---@field public PRINT_D boolean
---@field public CELL_DIM int
---@field public CELL_CHUNK_DIM int
---@field public CHUNK_DIM int
---@field public CHUNK_MAX_Z int
---@field public BIT_EMPTY byte
---@field public BIT_WALL_N byte
---@field public BIT_WALL_W byte
---@field public BIT_PATH_WALL_N byte
---@field public BIT_PATH_WALL_W byte
---@field public BIT_HAS_FLOOR byte
---@field public BIT_STAIRCASE byte
---@field public BIT_HAS_ROOF byte
---@field public DIR_NONE byte
---@field public DIR_N byte
---@field public DIR_W byte
---@field public DIR_2D_NW byte
---@field public DIR_S byte
---@field public DIR_E byte
---@field public DIR_2D_MAX byte
---@field public DIR_TOP byte
---@field public DIR_BOT byte
---@field public DIR_MAX byte
---@field protected CHUNK_LOAD_DIMENSIONS int
---@field protected DEBUG_LOAD_ALL_CHUNKS boolean
---@field public FILE_PRE String
---@field public FILE_SEP String
---@field public FILE_EXT String
---@field public FILE_DIR String
---@field private regionWorker IsoRegionWorker
---@field private dataRoot DataRoot
---@field protected lastChunkX int
---@field protected lastChunkY int
---@field private previousFlags byte
IsoRegion = {}

---@public
---@param arg0 IsoGridSquare
---@return void
---@overload fun(arg0:IsoGridSquare, arg1:boolean)
function IsoRegion:squareChanged(arg0) end

---@public
---@param arg0 IsoGridSquare
---@param arg1 boolean
---@return void
function IsoRegion:squareChanged(arg0, arg1) end

---@public
---@param arg0 boolean
---@return void
function IsoRegion:setDebugLoadAllChunks(arg0) end

---@public
---@param arg0 byte
---@param arg1 byte
---@return boolean
function IsoRegion:HasFlags(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 int
---@return int
function IsoRegion:hash(arg0, arg1) end

---@protected
---@param arg0 IsoGridSquare
---@return byte
function IsoRegion:calculateSquareFlags(arg0) end

---@public
---@return boolean
function IsoRegion:isDebugLoadAllChunks() end

---@public
---@return void
function IsoRegion:init() end

---@public
---@param arg0 ByteBuffer
---@param arg1 UdpConnection
---@return void
function IsoRegion:receiveClientRequestFullDataChunks(arg0, arg1) end

---@public
---@return void
function IsoRegion:reset() end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoRegion:receiveServerUpdatePacket(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return MasterRegion
function IsoRegion:getMasterRegion(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return byte
function IsoRegion:getSquareFlags(arg0, arg1, arg2) end

---@public
---@return void
function IsoRegion:ResetAllDataDebug() end

---@public
---@param arg0 IsoGridSquare
---@return void
function IsoRegion:setPreviousFlags(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return ChunkRegion
function IsoRegion:getChunkRegion(arg0, arg1, arg2) end

---@private
---@return void
function IsoRegion:clientResetDataCacheForChunks() end

---@public
---@param arg0 int
---@param arg1 int
---@return DataChunk
function IsoRegion:getDataChunk(arg0, arg1) end

---@public
---@param arg0 byte
---@return byte
function IsoRegion:GetOppositeDir(arg0) end

---@public
---@return void
function IsoRegion:update() end
