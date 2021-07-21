---@class InventoryContainer : zombie.inventory.types.InventoryContainer
---@field container ItemContainer
---@field capacity int
---@field weightReduction int
---@field private CanBeEquipped String
InventoryContainer = {}

---@public
---@return boolean
function InventoryContainer:IsInventoryContainer() end

---Overrides:
---
---DoTooltip in class InventoryItem
---@public
---@param tooltipUI ObjectTooltip
---@return void
---@overload fun(tooltipUI:ObjectTooltip, layout:ObjectTooltip.Layout)
function InventoryContainer:DoTooltip(tooltipUI) end

---Overrides:
---
---DoTooltip in class InventoryItem
---@public
---@param tooltipUI ObjectTooltip
---@param layout ObjectTooltip.Layout
---@return void
function InventoryContainer:DoTooltip(tooltipUI, layout) end

---@public
---@param arg0 ItemContainer
---@return void
function InventoryContainer:setItemContainer(arg0) end

---@public
---@return ItemContainer
function InventoryContainer:getInventory() end

---@public
---@return ItemContainer
function InventoryContainer:getItemContainer() end

---@public
---@param capacity int
---@return void
function InventoryContainer:setCapacity(capacity) end

---@public
---@return void
function InventoryContainer:updateAge() end

---@public
---@param canBeEquipped String
---@return void
function InventoryContainer:setCanBeEquipped(canBeEquipped) end

---@public
---@return String
function InventoryContainer:canBeEquipped() end

---@public
---@param weightReduction int
---@return void
function InventoryContainer:setWeightReduction(weightReduction) end

---@public
---@return int
function InventoryContainer:getWeightReduction() end

---@public
---@return float
function InventoryContainer:getBloodLevel() end

---Overrides:
---
---getContentsWeight in class InventoryItem
---@public
---@return float
function InventoryContainer:getContentsWeight() end

---@public
---@return int
function InventoryContainer:getCapacity() end

---Overrides:
---
---getEquippedWeight in class InventoryItem
---@public
---@return float
function InventoryContainer:getEquippedWeight() end

---@public
---@return float
function InventoryContainer:getInventoryWeight() end

---Overrides:
---
---getCategory in class InventoryItem
---@public
---@return String
function InventoryContainer:getCategory() end

---@public
---@param chr IsoGameCharacter
---@return int
function InventoryContainer:getEffectiveCapacity(chr) end

---@public
---@param arg0 float
---@return void
function InventoryContainer:setBloodLevel(arg0) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function InventoryContainer:load(arg0, arg1) end

---@public
---@return String
function InventoryContainer:getClothingExtraSubmenu() end

---throws java.io.IOException
---
---Overrides:
---
---save in class InventoryItem
---@public
---@param output ByteBuffer
---@param net boolean
---@return void
function InventoryContainer:save(output, net) end

---@public
---@return int
function InventoryContainer:getSaveType() end
