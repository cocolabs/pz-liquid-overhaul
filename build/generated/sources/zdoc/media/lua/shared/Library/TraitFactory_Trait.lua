---@class TraitFactory.Trait : zombie.characters.traits.TraitFactory.Trait
---@field public traitID String
---@field public name String
---@field public cost int
---@field public description String
---@field public prof boolean
---@field public texture Texture
---@field private removeInMP boolean
---@field private freeRecipes List|Unknown
---@field public MutuallyExclusive ArrayList|String
---@field public XPBoostMap HashMap|PerkFactory.Perks|Integer
TraitFactory_Trait = {}

---@public
---@param perk PerkFactory.Perks
---@param level int
---@return void
function TraitFactory_Trait:addXPBoost(perk, level) end

---Specified by:
---
---getRightLabel in interface IListBoxItem
---@public
---@return String
function TraitFactory_Trait:getRightLabel() end

---@public
---@param removeInMP boolean
---@return void
function TraitFactory_Trait:setRemoveInMP(removeInMP) end

---@public
---@return HashMap|PerkFactory.Perks|Integer
function TraitFactory_Trait:getXPBoostMap() end

---@public
---@return List|Unknown
function TraitFactory_Trait:getFreeRecipes() end

---@public
---@param desc String
---@return void
function TraitFactory_Trait:setDescription(desc) end

---@public
---@return int
function TraitFactory_Trait:getCost() end

---@public
---@param arg0 List|Unknown
---@return void
function TraitFactory_Trait:setFreeRecipes(arg0) end

---@public
---@return boolean
function TraitFactory_Trait:isFree() end

---@public
---@return Texture
function TraitFactory_Trait:getTexture() end

---@public
---@return String
function TraitFactory_Trait:getDescription() end

---@public
---@return boolean
function TraitFactory_Trait:isRemoveInMP() end

---@public
---@return ArrayList|String
function TraitFactory_Trait:getMutuallyExclusiveTraits() end

---Specified by:
---
---getLabel in interface IListBoxItem
---@public
---@return String
function TraitFactory_Trait:getLabel() end

---@public
---@return String
function TraitFactory_Trait:getType() end

---Specified by:
---
---getLeftLabel in interface IListBoxItem
---@public
---@return String
function TraitFactory_Trait:getLeftLabel() end
