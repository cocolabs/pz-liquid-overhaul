---@class ModalDialog : zombie.ui.ModalDialog
---@field public bYes boolean
---@field public Name String
---@field handler UIEventHandler
---@field public Clicked boolean
ModalDialog = {}

---Overrides:
---
---ButtonClicked in class NewWindow
---@public
---@param name String
---@return void
function ModalDialog:ButtonClicked(name) end

---@public
---@param name String
---@return void
function ModalDialog:Clicked(name) end
