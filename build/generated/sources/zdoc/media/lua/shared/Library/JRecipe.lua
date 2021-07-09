---@class JRecipe : zombie.scripting.objects.Recipe
---@field private canBeDoneFromFloor boolean
---@field public TimeToMake float
---@field public Sound String
---@field private AnimNode String
---@field private Prop1 String
---@field private Prop2 String
---@field public Source ArrayList|Recipe.Source
---@field public Result Recipe.Result
---@field public LuaTest String
---@field public LuaCreate String
---@field public LuaGrab String
---@field public name String
---@field private originalname String
---@field private nearItem String
---@field private LuaCanPerform String
---@field private tooltip String
---@field public skillRequired ArrayList|Unknown
---@field public LuaGiveXP String
---@field private needToBeLearn boolean
---@field private category String
---@field private removeResultItem boolean
---@field private heat float
---@field private noBrokenItems boolean
JRecipe = {}

---@public
---@return String
function JRecipe:getCategory() end

---@public
---@param arg0 int
---@return Recipe.RequiredSkill
function JRecipe:getRequiredSkill(arg0) end

---@public
---@return boolean
function JRecipe:isRemoveResultItem() end

---@public
---@return String
function JRecipe:getAnimNode() end

---Overrides:
---
---Load in class BaseScriptObject
---@public
---@param name String
---@param strArray String[]
---@return void
function JRecipe:Load(name, strArray) end

---@public
---@param arg0 String
---@return void
function JRecipe:setCanPerform(arg0) end

---@public
---@return boolean
function JRecipe:needToBeLearn() end

---@public
---@param originalname String
---@return void
function JRecipe:setOriginalname(originalname) end

---@public
---@return int
function JRecipe:getRequiredSkillCount() end

---@public
---@param arg0 String
---@return void
function JRecipe:setNearItem(arg0) end

---@public
---@param arg0 String
---@return void
function JRecipe:setProp1(arg0) end

---@public
---@return String
function JRecipe:getCanPerform() end

---@public
---@return String
function JRecipe:getProp1() end

---@public
---@param needToBeLearn boolean
---@return void
function JRecipe:setNeedToBeLearn(needToBeLearn) end

---@private
---@param arg0 String
---@return void
function JRecipe:DoResult(arg0) end

---@public
---@return String
function JRecipe:getOriginalname() end

---@public
---@return int
function JRecipe:getWaterAmountNeeded() end

---@public
---@return float
function JRecipe:getHeat() end

---@public
---@return String
function JRecipe:getFullType() end

---@public
---@param arg0 String
---@return Recipe.Source
function JRecipe:findSource(arg0) end

---@public
---@return String
function JRecipe:getProp2() end

---@public
---@param arg0 String
---@return void
function JRecipe:setAnimNode(arg0) end

---@public
---@return String
function JRecipe:getNearItem() end

---@public
---@return int
function JRecipe:getNumberOfNeededItem() end

---@public
---@param arg0 String
---@return boolean
function JRecipe:isKeep(arg0) end

---@public
---@return float
function JRecipe:getTimeToMake() end

---@public
---@return String
function JRecipe:getName() end

---@public
---@return String
function JRecipe:getTooltip() end

---@public
---@param arg0 String
---@return void
function JRecipe:setCategory(arg0) end

---@public
---@param arg0 String
---@return boolean
function JRecipe:isDestroy(arg0) end

---@public
---@param arg0 String
---@return void
function JRecipe:setProp2(arg0) end

---@public
---@return ArrayList|Unknown
function JRecipe:getRequiredSkills() end

---@public
---@param arg0 boolean
---@return void
function JRecipe:setRemoveResultItem(arg0) end

---@public
---@return boolean
function JRecipe:noBrokenItems() end

---@public
---@return ArrayList|Recipe.Source
function JRecipe:getSource() end

---@public
---@return boolean
function JRecipe:isCanBeDoneFromFloor() end

---@private
---@param arg0 String
---@return void
function JRecipe:DoSource(arg0) end

---@public
---@param canBeDoneFromFloor boolean
---@return void
function JRecipe:setCanBeDoneFromFloor(canBeDoneFromFloor) end

---@public
---@return String
function JRecipe:getSound() end

---@public
---@return Recipe.Result
function JRecipe:getResult() end

---@public
---@param a InventoryItem
---@return int
function JRecipe:FindIndexOf(a) end
