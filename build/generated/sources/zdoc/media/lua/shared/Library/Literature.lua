---@class Literature : zombie.inventory.types.Literature
---@field public bAlreadyRead boolean
---@field public requireInHandOrInventory String
---@field public useOnConsume String
---@field private numberOfPages int
---@field private bookName String
---@field private LvlSkillTrained int
---@field private NumLevelsTrained int
---@field private SkillTrained String
---@field private alreadyReadPages int
---@field private canBeWrite boolean
---@field private customPages HashMap|Unknown|Unknown
---@field private lockedBy String
---@field private pageToWrite int
---@field private teachedRecipes List|Unknown
Literature = {}

---@public
---@param index Integer
---@return String
function Literature:seePage(index) end

---Overrides:
---
---DoTooltip in class InventoryItem
---@public
---@param tooltipUI ObjectTooltip
---@param layout ObjectTooltip.Layout
---@return void
function Literature:DoTooltip(tooltipUI, layout) end

---@public
---@return String
function Literature:getLockedBy() end

---Overrides:
---
---update in class InventoryItem
---@public
---@return void
function Literature:update() end

---@public
---@param bookName String
---@return void
function Literature:setBookName(bookName) end

---@public
---@return boolean
function Literature:IsLiterature() end

---@public
---@return int
function Literature:getLvlSkillTrained() end

---Overrides:
---
---getUnhappyChange in class InventoryItem
---@public
---@return float @the unhappyChange
function Literature:getUnhappyChange() end

---@public
---@param skillTrained String
---@return void
function Literature:setSkillTrained(skillTrained) end

---@public
---@param pageToWrite int
---@return void
function Literature:setPageToWrite(pageToWrite) end

---Overrides:
---
---finishupdate in class InventoryItem
---@public
---@return boolean
function Literature:finishupdate() end

---@public
---@param numberOfPages int
---@return void
function Literature:setNumberOfPages(numberOfPages) end

---@public
---@param lockedBy String
---@return void
function Literature:setLockedBy(lockedBy) end

---@public
---@param index Integer
---@param text String
---@return void
function Literature:addPage(index, text) end

---@public
---@return String
function Literature:getBookName() end

---@public
---@return int
function Literature:getNumLevelsTrained() end

---Overrides:
---
---getBoredomChange in class InventoryItem
---@public
---@return float @the boredomChange
function Literature:getBoredomChange() end

---@public
---@param lvlSkillTrained int
---@return void
function Literature:setLvlSkillTrained(lvlSkillTrained) end

---@public
---@param numLevelsTrained int
---@return void
function Literature:setNumLevelsTrained(numLevelsTrained) end

---@public
---@return String
function Literature:getSkillTrained() end

---@public
---@return int
function Literature:getNumberOfPages() end

---@public
---@param alreadyReadPages int
---@return void
function Literature:setAlreadyReadPages(alreadyReadPages) end

---Overrides:
---
---getCategory in class InventoryItem
---@public
---@return String
function Literature:getCategory() end

---@public
---@param teachedRecipes List|String
---@return void
function Literature:setTeachedRecipes(teachedRecipes) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function Literature:load(arg0, arg1) end

---@public
---@param customPages HashMap|Integer|String
---@return void
function Literature:setCustomPages(customPages) end

---@public
---@return int
function Literature:getSaveType() end

---@public
---@return int
function Literature:getPageToWrite() end

---throws java.io.IOException
---
---Overrides:
---
---save in class InventoryItem
---@public
---@param output ByteBuffer
---@param net boolean
---@return void
function Literature:save(output, net) end

---@public
---@return HashMap|Integer|String
function Literature:getCustomPages() end

---@public
---@return boolean
function Literature:canBeWrite() end

---Overrides:
---
---getStressChange in class InventoryItem
---@public
---@return float @the stressChange
function Literature:getStressChange() end

---@public
---@return List|String
function Literature:getTeachedRecipes() end

---@public
---@param canBeWrite boolean
---@return void
function Literature:setCanBeWrite(canBeWrite) end

---@public
---@return int
function Literature:getMaxLevelTrained() end

---@public
---@return int
function Literature:getAlreadyReadPages() end
