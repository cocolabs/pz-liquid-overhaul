---@class ClimbOverFenceState : zombie.ai.states.ClimbOverFenceState
---@field private _instance ClimbOverFenceState
---@field PARAM_START_X Integer
---@field PARAM_START_Y Integer
---@field PARAM_Z Integer
---@field PARAM_END_X Integer
---@field PARAM_END_Y Integer
---@field PARAM_DIR Integer
---@field PARAM_ZOMBIE_ON_FLOOR Integer
---@field PARAM_PREV_STATE Integer
---@field PARAM_SCRATCH Integer
---@field PARAM_COUNTER Integer
---@field PARAM_SOLID_FLOOR Integer
---@field PARAM_SHEET_ROPE Integer
---@field PARAM_RUN Integer
---@field PARAM_SPRINT Integer
---@field PARAM_COLLIDABLE Integer
ClimbOverFenceState = {}

---@public
---@param arg0 IsoGameCharacter
---@param arg1 IsoDirections
---@return void
function ClimbOverFenceState:setParams(arg0, arg1) end

---@public
---@return ClimbOverFenceState
function ClimbOverFenceState:instance() end

---@private
---@param arg0 IsoGameCharacter
---@return IsoObject
function ClimbOverFenceState:getFence(arg0) end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 AnimEvent
---@return void
function ClimbOverFenceState:animEvent(arg0, arg1) end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 MoveDeltaModifiers
---@return void
function ClimbOverFenceState:getDeltaModifiers(arg0, arg1) end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbOverFenceState:enter(owner) end

---Overrides:
---
---exit in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbOverFenceState:exit(owner) end

---@private
---@param arg0 IsoGameCharacter
---@param arg1 float
---@return void
function ClimbOverFenceState:slideY(arg0, arg1) end

---@private
---@param arg0 IsoZombie
---@return void
function ClimbOverFenceState:setLungeXVars(arg0) end

---@private
---@param arg0 IsoGameCharacter
---@param arg1 float
---@return void
function ClimbOverFenceState:slideX(arg0, arg1) end

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbOverFenceState:execute(owner) end
