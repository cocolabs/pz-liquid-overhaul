---@class ClimbOverWallState : zombie.ai.states.ClimbOverWallState
---@field private _instance ClimbOverWallState
---@field PARAM_START_X Integer
---@field PARAM_START_Y Integer
---@field PARAM_Z Integer
---@field PARAM_END_X Integer
---@field PARAM_END_Y Integer
---@field PARAM_DIR Integer
ClimbOverWallState = {}

---@public
---@param arg0 IsoGameCharacter
---@return void
function ClimbOverWallState:execute(arg0) end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 IsoDirections
---@return void
function ClimbOverWallState:setParams(arg0, arg1) end

---@public
---@return ClimbOverWallState
function ClimbOverWallState:instance() end

---@public
---@param arg0 IsoGameCharacter
---@return void
function ClimbOverWallState:enter(arg0) end

---@public
---@param arg0 IsoGameCharacter
---@return void
function ClimbOverWallState:exit(arg0) end
