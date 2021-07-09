---@class JSGlobalObjectSystem : zombie.globalObjects.SGlobalObjectSystem
---@field protected name String
---@field protected loadedWorldVersion int
---@field protected objects ArrayList|Unknown
---@field protected lookup GlobalObjectLookup
---@field protected modData KahluaTable
---@field protected modDataKeys HashSet|Unknown
---@field protected objectModDataKeys HashSet|Unknown
---@field private tempTable KahluaTable
---@field private objectListPool ArrayDeque|Unknown
JSGlobalObjectSystem = {}

---@public
---@param arg0 String
---@param arg1 IsoPlayer
---@param arg2 KahluaTable
---@return void
function JSGlobalObjectSystem:receiveClientCommand(arg0, arg1, arg2) end

---@public
---@return KahluaTable
function JSGlobalObjectSystem:getInitialStateForClient() end

---@private
---@return String
function JSGlobalObjectSystem:getFileName() end

---@public
---@param arg0 int
---@param arg1 int
---@return boolean
function JSGlobalObjectSystem:hasObjectsInChunk(arg0, arg1) end

---@public
---@return void
---@overload fun(arg0:ByteBuffer)
function JSGlobalObjectSystem:save() end

---@public
---@param arg0 ByteBuffer
---@return void
function JSGlobalObjectSystem:save(arg0) end

---@public
---@return ArrayList|Unknown
function JSGlobalObjectSystem:allocList() end

---@public
---@return int
function JSGlobalObjectSystem:loadedWorldVersion() end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function JSGlobalObjectSystem:chunkLoaded(arg0, arg1) end

---@public
---@param arg0 KahluaTable
---@return void
function JSGlobalObjectSystem:setObjectModDataKeys(arg0) end

---@public
---@param arg0 int
---@return GlobalObject
function JSGlobalObjectSystem:getObjectByIndex(arg0) end

---@public
---@return int
function JSGlobalObjectSystem:getObjectCount() end

---@public
---@param arg0 int
---@param arg1 int
---@return ArrayList|Unknown
function JSGlobalObjectSystem:getObjectsInChunk(arg0, arg1) end

---@public
---@param arg0 GlobalObject
---@return void
function JSGlobalObjectSystem:removeObject(arg0) end

---@public
---@param arg0 KahluaTable
---@return void
function JSGlobalObjectSystem:setModDataKeys(arg0) end

---@public
---@return KahluaTable
function JSGlobalObjectSystem:getModData() end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return GlobalObject
function JSGlobalObjectSystem:getObjectAt(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return ArrayList|Unknown
function JSGlobalObjectSystem:getObjectsAdjacentTo(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return GlobalObject
function JSGlobalObjectSystem:newObject(arg0, arg1, arg2) end

---@public
---@return void
function JSGlobalObjectSystem:Reset() end

---@public
---@return void
---@overload fun(arg0:ByteBuffer, arg1:int)
function JSGlobalObjectSystem:load() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function JSGlobalObjectSystem:load(arg0, arg1) end

---@public
---@param arg0 ArrayList|Unknown
---@return void
function JSGlobalObjectSystem:finishedWithList(arg0) end

---@public
---@return void
function JSGlobalObjectSystem:update() end

---@public
---@param arg0 String
---@param arg1 KahluaTable
---@return void
function JSGlobalObjectSystem:sendCommand(arg0, arg1) end
