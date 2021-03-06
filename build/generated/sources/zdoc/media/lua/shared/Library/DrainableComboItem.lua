---@class DrainableComboItem : zombie.inventory.types.DrainableComboItem
---@field protected bUseWhileEquiped boolean
---@field protected bUseWhileUnequiped boolean
---@field protected ticksPerEquipUse int
---@field protected useDelta float
---@field protected delta float
---@field protected ticks float
---@field public ReplaceOnDeplete String
---@field protected ReplaceOnDepleteFullType String
---@field private rainFactor float
---@field private canConsolidate boolean
---@field private WeightEmpty float
---@field protected Heat float
---@field protected LastCookMinute int
---@field public OnCooked String
DrainableComboItem = {}

---@public
---@return boolean
function DrainableComboItem:isUseWhileUnequiped() end

---@public
---@return int
function DrainableComboItem:getRemainingUses() end

---@public
---@return int @the ticksPerEquipUse
function DrainableComboItem:getTicksPerEquipUse() end

---@public
---@param arg0 float
---@return void
function DrainableComboItem:setHeat(arg0) end

---@public
---@param useDelta float @the useDelta to set
---@return void
function DrainableComboItem:setUseDelta(useDelta) end

---Overrides:
---
---update in class InventoryItem
---
---Specified by:
---
---update in interface IUpdater
---@public
---@return void
function DrainableComboItem:update() end

---@public
---@param arg0 float
---@return void
function DrainableComboItem:setWeightEmpty(arg0) end

---@public
---@param arg0 boolean
---@return void
function DrainableComboItem:setUseWhileUnequiped(arg0) end

---@public
---@return float
function DrainableComboItem:getWeightEmpty() end

---Overrides:
---
---Use in class InventoryItem
---@public
---@return void
function DrainableComboItem:Use() end

---@public
---@return String
function DrainableComboItem:getReplaceOnDepleteFullType() end

---@public
---@param arg0 boolean
---@return void
function DrainableComboItem:setCanConsolidate(arg0) end

---@public
---@param ticks float @the ticks to set
---@return void
function DrainableComboItem:setTicks(ticks) end

---@public
---@return String @the ReplaceOnDeplete
function DrainableComboItem:getReplaceOnDeplete() end

---@public
---@param ReplaceOnDeplete String
---@return void
function DrainableComboItem:setReplaceOnDeplete(ReplaceOnDeplete) end

---@public
---@param bUseWhileEquiped boolean @the bUseWhileEquiped to set
---@return void
function DrainableComboItem:setUseWhileEquiped(bUseWhileEquiped) end

---Overrides:
---
---finishupdate in class InventoryItem
---@public
---@return boolean
function DrainableComboItem:finishupdate() end

---Specified by:
---
---setUsedDelta in interface Drainable
---@public
---@param usedDelta float
---@return void
function DrainableComboItem:setUsedDelta(usedDelta) end

---@public
---@return float @the ticks
function DrainableComboItem:getTicks() end

---@public
---@return float
function DrainableComboItem:getRainFactor() end

---@public
---@return float
function DrainableComboItem:getHeat() end

---@public
---@param delta float @the delta to set
---@return void
function DrainableComboItem:setDelta(delta) end

---Specified by:
---
---renderlast in interface IUpdater
---@public
---@return void
function DrainableComboItem:renderlast() end

---@public
---@return float
function DrainableComboItem:getDrainableUsesFloat() end

---Overrides:
---
---CanStack in class InventoryItem
---@public
---@param item InventoryItem
---@return boolean
function DrainableComboItem:CanStack(item) end

---@public
---@param ticksPerEquipUse int @the ticksPerEquipUse to set
---@return void
function DrainableComboItem:setTicksPerEquipUse(ticksPerEquipUse) end

---Specified by:
---
---getUsedDelta in interface Drainable
---@public
---@return float
function DrainableComboItem:getUsedDelta() end

---@public
---@return boolean
function DrainableComboItem:IsDrainable() end

---@public
---@return float @the useDelta
function DrainableComboItem:getUseDelta() end

---@public
---@return boolean
function DrainableComboItem:canConsolidate() end

---@public
---@param arg0 float
---@return void
function DrainableComboItem:setRainFactor(arg0) end

---@public
---@return float @the delta
function DrainableComboItem:getDelta() end

---@public
---@return int
function DrainableComboItem:getSaveType() end

---@public
---@return int
function DrainableComboItem:getDrainableUsesInt() end

---@public
---@return void
function DrainableComboItem:updateWeight() end

---@public
---@return boolean
function DrainableComboItem:shouldUpdateInWorld() end

---@public
---@return boolean @the bUseWhileEquiped
function DrainableComboItem:isUseWhileEquiped() end

---Specified by:
---
---render in interface IUpdater
---@public
---@return void
function DrainableComboItem:render() end

---@public
---@return float
function DrainableComboItem:getInvHeat() end
