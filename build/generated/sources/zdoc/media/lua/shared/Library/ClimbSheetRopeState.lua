---@class ClimbSheetRopeState : zombie.ai.states.ClimbSheetRopeState
---@field private _instance ClimbSheetRopeState
ClimbSheetRopeState = {}

---@public
---@param arg0 IsoGameCharacter
---@return void
function ClimbSheetRopeState:exit(arg0) end

---@public
---@return ClimbSheetRopeState
function ClimbSheetRopeState:instance() end

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbSheetRopeState:execute(owner) end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbSheetRopeState:enter(owner) end
