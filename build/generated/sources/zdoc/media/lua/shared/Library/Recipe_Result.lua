---@class Recipe.Result : zombie.scripting.objects.Recipe.Result
---@field public type String
---@field public count int
---@field public drainableCount int
---@field public module String
Recipe_Result = {}

---@public
---@return String
function Recipe_Result:getModule() end

---@public
---@return String
function Recipe_Result:getFullType() end

---@public
---@return int
function Recipe_Result:getCount() end

---@public
---@return int
function Recipe_Result:getDrainableCount() end

---@public
---@return String
function Recipe_Result:getType() end
