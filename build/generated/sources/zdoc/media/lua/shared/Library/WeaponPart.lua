---@class WeaponPart : zombie.inventory.types.WeaponPart
---@field public TYPE_CANON String
---@field public TYPE_CLIP String
---@field public TYPE_RECOILPAD String
---@field public TYPE_SCOPE String
---@field public TYPE_SLING String
---@field public TYPE_STOCK String
---@field private maxRange float
---@field private minRangeRanged float
---@field private damage float
---@field private recoilDelay float
---@field private clipSize int
---@field private reloadTime int
---@field private aimingTime int
---@field private hitChance int
---@field private angle float
---@field private weightModifier float
---@field private mountOn ArrayList|Unknown
---@field private mountOnDisplayName ArrayList|Unknown
---@field private partType String
WeaponPart = {}

---@public
---@return int
function WeaponPart:getReloadTime() end

---Overrides:
---
---getCategory in class InventoryItem
---@public
---@return String
function WeaponPart:getCategory() end

---@public
---@return int
function WeaponPart:getSaveType() end

---@public
---@param angle float
---@return void
function WeaponPart:setAngle(angle) end

---@public
---@return int
function WeaponPart:getAimingTime() end

---@public
---@return float
function WeaponPart:getWeightModifier() end

---@public
---@param recoilDelay float
---@return void
function WeaponPart:setRecoilDelay(recoilDelay) end

---@public
---@param maxRange float
---@return void
function WeaponPart:setMaxRange(maxRange) end

---@public
---@param partType String
---@return void
function WeaponPart:setPartType(partType) end

---@public
---@return float
function WeaponPart:getMaxRange() end

---@public
---@return float
function WeaponPart:getRecoilDelay() end

---@public
---@return float
function WeaponPart:getMinRangeRanged() end

---@public
---@param weightModifier float
---@return void
function WeaponPart:setWeightModifier(weightModifier) end

---@public
---@return int
function WeaponPart:getClipSize() end

---@public
---@param mountOn ArrayList|String
---@return void
function WeaponPart:setMountOn(mountOn) end

---Overrides:
---
---DoTooltip in class InventoryItem
---@public
---@param tooltipUI ObjectTooltip
---@param layout ObjectTooltip.Layout
---@return void
function WeaponPart:DoTooltip(tooltipUI, layout) end

---@public
---@param clipSize int
---@return void
function WeaponPart:setClipSize(clipSize) end

---@public
---@param aimingTime int
---@return void
function WeaponPart:setAimingTime(aimingTime) end

---@public
---@return int
function WeaponPart:getHitChance() end

---@public
---@param reloadTime int
---@return void
function WeaponPart:setReloadTime(reloadTime) end

---@public
---@return float
function WeaponPart:getAngle() end

---@public
---@return String
function WeaponPart:getPartType() end

---@public
---@param hitChance int
---@return void
function WeaponPart:setHitChance(hitChance) end

---@public
---@return ArrayList|String
function WeaponPart:getMountOn() end

---@public
---@param damage float
---@return void
function WeaponPart:setDamage(damage) end

---@public
---@return float
function WeaponPart:getDamage() end

---@public
---@param minRangeRanged float
---@return void
function WeaponPart:setMinRangeRanged(minRangeRanged) end
