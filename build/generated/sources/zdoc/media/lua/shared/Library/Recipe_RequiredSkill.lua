---@class Recipe.RequiredSkill : zombie.scripting.objects.Recipe.RequiredSkill
---@field private perk PerkFactory.Perks
---@field private level int
Recipe_RequiredSkill = {}

---@public
---@return PerkFactory.Perks
function Recipe_RequiredSkill:getPerk() end

---@public
---@return int
function Recipe_RequiredSkill:getLevel() end
