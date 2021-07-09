---@class JCGlobalObjectSystem : zombie.globalObjects.CGlobalObjectSystem
---@field protected name String
---@field protected modData KahluaTable
JCGlobalObjectSystem = {}

---@public
---@return KahluaTable
function JCGlobalObjectSystem:getModData() end

---@public
---@return void
function JCGlobalObjectSystem:Reset() end

---@public
---@param arg0 String
---@param arg1 KahluaTable
---@return void
function JCGlobalObjectSystem:receiveServerCommand(arg0, arg1) end

---@public
---@param arg0 String
---@param arg1 IsoPlayer
---@param arg2 KahluaTable
---@return void
function JCGlobalObjectSystem:sendCommand(arg0, arg1, arg2) end
