---@class Recipe.Source : zombie.scripting.objects.Recipe.Source
---@field public keep boolean
---@field private items ArrayList|Unknown
---@field public destroy boolean
---@field public count float
---@field public use float
Recipe_Source = {}

---@public
---@return float
function Recipe_Source:getUse() end

---@public
---@return float
function Recipe_Source:getCount() end

---@public
---@return boolean
function Recipe_Source:isKeep() end

---@public
---@return String
function Recipe_Source:getOnlyItem() end

---@public
---@return ArrayList|Unknown
function Recipe_Source:getItems() end

---@public
---@return boolean
function Recipe_Source:isDestroy() end
