---@class BurntToDeath : zombie.ai.states.BurntToDeath
---@field private _instance BurntToDeath
BurntToDeath = {}

---Overrides:
---
---execute in class State
---@public
---@param owner IsoGameCharacter
---@return void
function BurntToDeath:execute(owner) end

---@public
---@return BurntToDeath
function BurntToDeath:instance() end

---Overrides:
---
---enter in class State
---@public
---@param owner IsoGameCharacter
---@return void
function BurntToDeath:enter(owner) end

---Overrides:
---
---exit in class State
---@public
---@param owner IsoGameCharacter
---@return void
function BurntToDeath:exit(owner) end
