---@class ActionProgressBar : zombie.ui.ActionProgressBar
---@field background Texture
---@field foreground Texture
---@field deltaValue float
---@field public delayHide int
ActionProgressBar = {}

---@public
---@return float
function ActionProgressBar:getValue() end

---Overrides:
---
---render in class UIElement
---@public
---@return void
function ActionProgressBar:render() end

---@public
---@param delta float
---@return void
function ActionProgressBar:setValue(delta) end
