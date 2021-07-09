---@class ClimbDownSheetRopeState : zombie.ai.states.ClimbDownSheetRopeState
---@field private _instance ClimbDownSheetRopeState
ClimbDownSheetRopeState = {}

---@public
---@param arg0 IsoGameCharacter
---@return void
function ClimbDownSheetRopeState:exit(arg0) end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbDownSheetRopeState:enter(owner) end

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function ClimbDownSheetRopeState:execute(owner) end

---@public
---@return ClimbDownSheetRopeState
function ClimbDownSheetRopeState:instance() end
