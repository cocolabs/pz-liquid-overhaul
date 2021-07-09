---@class SandboxOptions.DoubleSandboxOption : zombie.SandboxOptions.DoubleSandboxOption
---@field protected translation String
---@field protected tableName String
---@field protected shortName String
SandboxOptions_DoubleSandboxOption = {}

---@public
---@return String
function SandboxOptions_DoubleSandboxOption:getTranslatedName() end

---@public
---@return ConfigOption
function SandboxOptions_DoubleSandboxOption:asConfigOption() end

---@public
---@param arg0 KahluaTable
---@return void
function SandboxOptions_DoubleSandboxOption:fromTable(arg0) end

---@public
---@return String
function SandboxOptions_DoubleSandboxOption:getTableName() end

---@public
---@return String
function SandboxOptions_DoubleSandboxOption:getTooltip() end

---@public
---@return String
function SandboxOptions_DoubleSandboxOption:getShortName() end

---@public
---@param arg0 KahluaTable
---@return void
function SandboxOptions_DoubleSandboxOption:toTable(arg0) end

---@public
---@param arg0 String
---@return SandboxOptions.SandboxOption
function SandboxOptions_DoubleSandboxOption:setTranslation(arg0) end
