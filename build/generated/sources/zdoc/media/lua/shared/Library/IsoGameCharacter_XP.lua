---@class IsoGameCharacter.XP : zombie.characters.IsoGameCharacter.XP
---@field public level int
---@field public lastlevel int
---@field public TotalXP float
---@field public XPMap HashMap|PerkFactory.Perks|Float
---@field public XPMapMultiplier HashMap|Unknown|Unknown
---@field chr IsoGameCharacter
IsoGameCharacter_XP = {}

---@private
---@param arg0 PerkFactory.Perks
---@return boolean
function IsoGameCharacter_XP:isSkillExcludedFromSpeedReduction(arg0) end

---@public
---@param perk PerkFactory.Perks
---@return float
function IsoGameCharacter_XP:getMultiplier(perk) end

---@public
---@param type PerkFactory.Perks
---@return int
function IsoGameCharacter_XP:getPerkBoost(type) end

---@public
---@return HashMap|PerkFactory.Perks|IsoGameCharacter.XPMultiplier
function IsoGameCharacter_XP:getMultiplierMap() end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoGameCharacter_XP:save(arg0) end

---@private
---@param arg0 ByteBuffer
---@param arg1 int
---@return PerkFactory.Perks
function IsoGameCharacter_XP:loadPerk(arg0, arg1) end

---@private
---@param arg0 PerkFactory.Perks
---@return boolean
function IsoGameCharacter_XP:isSkillExcludedFromSpeedIncrease(arg0) end

---@public
---@param key PerkFactory.Perks
---@param perkLevel int
---@return void
function IsoGameCharacter_XP:setXPToLevel(key, perkLevel) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function IsoGameCharacter_XP:load(arg0, arg1) end

---@public
---@param arg0 float
---@return void
function IsoGameCharacter_XP:setTotalXP(arg0) end

---@public
---@param weapon HandWeapon
---@param amount int
---@return void
---@overload fun(type:PerkFactory.Perks, amount:float)
---@overload fun(type:PerkFactory.Perks, amount:float, callLua:boolean)
---@overload fun(type:PerkFactory.Perks, amount:float, callLua:boolean, addGlobalXp:boolean)
---@overload fun(arg0:PerkFactory.Perks, arg1:float, arg2:boolean, arg3:boolean, arg4:boolean, arg5:boolean)
function IsoGameCharacter_XP:AddXP(weapon, amount) end

---@public
---@param type PerkFactory.Perks
---@param amount float
---@return void
function IsoGameCharacter_XP:AddXP(type, amount) end

---@public
---@param type PerkFactory.Perks
---@param amount float
---@param callLua boolean
---@return void
function IsoGameCharacter_XP:AddXP(type, amount, callLua) end

---@public
---@param type PerkFactory.Perks
---@param amount float
---@param callLua boolean
---@param addGlobalXp boolean
---@return void
function IsoGameCharacter_XP:AddXP(type, amount, callLua, addGlobalXp) end

---@public
---@param arg0 PerkFactory.Perks
---@param arg1 float
---@param arg2 boolean
---@param arg3 boolean
---@param arg4 boolean
---@param arg5 boolean
---@return void
function IsoGameCharacter_XP:AddXP(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@param type PerkFactory.Perks
---@param amount float
---@return void
function IsoGameCharacter_XP:AddXPNoMultiplier(type, amount) end

---@public
---@param perks PerkFactory.Perks
---@param multiplier float
---@param minLevel int
---@param maxLevel int
---@return void
function IsoGameCharacter_XP:addXpMultiplier(perks, multiplier, minLevel, maxLevel) end

---@public
---@param type PerkFactory.Perks
---@return float
function IsoGameCharacter_XP:getXP(type) end

---@public
---@param newlevel int
---@return void
function IsoGameCharacter_XP:setLevel(newlevel) end

---@public
---@return float
function IsoGameCharacter_XP:getTotalXp() end

---@private
---@param arg0 ByteBuffer
---@param arg1 PerkFactory.Perks
---@return void
function IsoGameCharacter_XP:savePerk(arg0, arg1) end

---@public
---@return int
function IsoGameCharacter_XP:getLevel() end
