---@class FakeDeadZombieState : zombie.ai.states.FakeDeadZombieState
---@field private _instance FakeDeadZombieState
FakeDeadZombieState = {}

---Overrides:
---
---exit in class State
---@public
---@param owner IsoGameCharacter
---@return void
function FakeDeadZombieState:exit(owner) end

---@public
---@return FakeDeadZombieState
function FakeDeadZombieState:instance() end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function FakeDeadZombieState:enter(owner) end

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function FakeDeadZombieState:execute(owner) end
