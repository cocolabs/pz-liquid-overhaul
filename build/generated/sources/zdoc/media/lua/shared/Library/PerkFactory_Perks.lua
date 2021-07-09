---@class PerkFactory.Perks : zombie.characters.skills.PerkFactory.Perks
---@field public None PerkFactory.Perks
---@field public Agility PerkFactory.Perks
---@field public Cooking PerkFactory.Perks
---@field public Melee PerkFactory.Perks
---@field public Crafting PerkFactory.Perks
---@field public Fitness PerkFactory.Perks
---@field public Strength PerkFactory.Perks
---@field public Blunt PerkFactory.Perks
---@field public Axe PerkFactory.Perks
---@field public Sprinting PerkFactory.Perks
---@field public Lightfoot PerkFactory.Perks
---@field public Nimble PerkFactory.Perks
---@field public Sneak PerkFactory.Perks
---@field public Woodwork PerkFactory.Perks
---@field public Aiming PerkFactory.Perks
---@field public Reloading PerkFactory.Perks
---@field public Farming PerkFactory.Perks
---@field public Survivalist PerkFactory.Perks
---@field public Fishing PerkFactory.Perks
---@field public Trapping PerkFactory.Perks
---@field public Passiv PerkFactory.Perks
---@field public Firearm PerkFactory.Perks
---@field public PlantScavenging PerkFactory.Perks
---@field public Doctor PerkFactory.Perks
---@field public Electricity PerkFactory.Perks
---@field public Blacksmith PerkFactory.Perks
---@field public MetalWelding PerkFactory.Perks
---@field public Melting PerkFactory.Perks
---@field public Mechanics PerkFactory.Perks
---@field public Spear PerkFactory.Perks
---@field public Maintenance PerkFactory.Perks
---@field public SmallBlade PerkFactory.Perks
---@field public LongBlade PerkFactory.Perks
---@field public SmallBlunt PerkFactory.Perks
---@field public Combat PerkFactory.Perks
---@field public Tailoring PerkFactory.Perks
---@field public MAX PerkFactory.Perks
---@field private index int
PerkFactory_Perks = {}

---Returns the enum constant of this type with the specified name.
---
---The string must match exactly an identifier used to declare an
---
---enum constant in this type.  (Extraneous whitespace characters are
---
---not permitted.)
---@public
---@param name String @the name of the enum constant to be returned.
---@return PerkFactory.Perks @the enum constant with the specified name
function PerkFactory_Perks:valueOf(name) end

---@public
---@return int
function PerkFactory_Perks:index() end

---Returns an array containing the constants of this enum type, in
---
---the order they are declared.  This method may be used to iterate
---
---over the constants as follows:
---
---
---
---for (PerkFactory.Perks c : PerkFactory.Perks.values())
---
---    System.out.println(c);
---
---
---@public
---@return PerkFactory.Perks[] @an array containing the constants of this enum type, in the order they are declared
function PerkFactory_Perks:values() end

---@public
---@param str String
---@return PerkFactory.Perks
function PerkFactory_Perks:FromString(str) end

---@public
---@return int
function PerkFactory_Perks:getMaxIndex() end

---@public
---@param value int
---@return PerkFactory.Perks
function PerkFactory_Perks:fromIndex(value) end
