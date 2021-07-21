---@class LuaTimedActionNew : zombie.characters.CharacterTimedActions.LuaTimedActionNew
---@field _table KahluaTable
LuaTimedActionNew = {}

---Specified by:
---
---Succeeded in interface IPathfinder
---@public
---@param path Path
---@param mover Mover
---@return void
function LuaTimedActionNew:Succeeded(path, mover) end

---Overrides:
---
---start in class BaseAction
---@public
---@return void
function LuaTimedActionNew:start() end

---Overrides:
---
---stop in class BaseAction
---@public
---@return void
function LuaTimedActionNew:stop() end

---@public
---@param chr IsoGameCharacter
---@param x int
---@param y int
---@param z int
---@return void
function LuaTimedActionNew:Pathfind(chr, x, y, z) end

---Overrides:
---
---perform in class BaseAction
---@public
---@return void
function LuaTimedActionNew:perform() end

---@public
---@param arg0 AnimEvent
---@return void
function LuaTimedActionNew:OnAnimEvent(arg0) end

---@public
---@param maxTime int
---@return void
function LuaTimedActionNew:setTime(maxTime) end

---Specified by:
---
---Failed in interface IPathfinder
---@public
---@param mover Mover
---@return void
function LuaTimedActionNew:Failed(mover) end

---Overrides:
---
---valid in class BaseAction
---@public
---@return boolean
function LuaTimedActionNew:valid() end

---@public
---@return void
function LuaTimedActionNew:waitToStart() end

---Specified by:
---
---getName in interface IPathfinder
---@public
---@return String
function LuaTimedActionNew:getName() end

---Overrides:
---
---update in class BaseAction
---@public
---@return void
function LuaTimedActionNew:update() end
