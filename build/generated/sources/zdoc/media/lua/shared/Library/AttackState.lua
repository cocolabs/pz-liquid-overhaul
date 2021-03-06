---@class AttackState : zombie.ai.states.AttackState
---@field private s_instance AttackState
---@field private frontStr String
---@field private backStr String
---@field private rightStr String
---@field private leftStr String
AttackState = {}

---@public
---@param arg0 IsoGameCharacter
---@return boolean
function AttackState:isAttacking(arg0) end

---Overrides:
---
---exit in class State
---@public
---@param owner IsoGameCharacter
---@return void
function AttackState:exit(owner) end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 AnimEvent
---@return void
function AttackState:animEvent(arg0, arg1) end

---@private
---@param arg0 String
---@param arg1 IsoGameCharacter
---@return void
function AttackState:triggerPlayerReaction(arg0, arg1) end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function AttackState:enter(owner) end

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function AttackState:execute(owner) end

---@public
---@return AttackState
function AttackState:instance() end
