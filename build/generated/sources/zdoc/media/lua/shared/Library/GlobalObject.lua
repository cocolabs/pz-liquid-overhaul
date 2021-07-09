---@class GlobalObject : zombie.globalObjects.GlobalObject
---@field protected system JSGlobalObjectSystem
---@field protected x int
---@field protected y int
---@field protected z int
---@field protected modData KahluaTable
---@field private tempTable KahluaTable
GlobalObject = {}

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function GlobalObject:setLocation(arg0, arg1, arg2) end

---@public
---@return void
function GlobalObject:Reset() end

---@public
---@param arg0 ByteBuffer
---@return void
function GlobalObject:save(arg0) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function GlobalObject:load(arg0, arg1) end

---@public
---@return JSGlobalObjectSystem
function GlobalObject:getSystem() end

---@public
---@return int
function GlobalObject:getX() end

---@public
---@return KahluaTable
function GlobalObject:getModData() end

---@public
---@return int
function GlobalObject:getY() end

---@public
---@return int
function GlobalObject:getZ() end
