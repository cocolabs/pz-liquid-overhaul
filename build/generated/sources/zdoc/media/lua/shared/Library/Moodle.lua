---@class Moodle : zombie.characters.Moodles.Moodle
---@field Type MoodleType
---@field private Level int
---@field Parent IsoGameCharacter
---@field private painTimer int
---@field private chevronColor Color
---@field private chevronIsUp boolean
---@field private chevronCount int
---@field private chevronMax int
---@field private colorNeg Color
---@field private colorPos Color
Moodle = {}

---@public
---@param arg0 int
---@param arg1 boolean
---@param arg2 Color
---@return boolean
function Moodle:chevronDifference(arg0, arg1, arg2) end

---@public
---@param val int
---@return void
function Moodle:SetLevel(val) end

---@public
---@return int
function Moodle:getChevronCount() end

---@public
---@return boolean
function Moodle:Update() end

---@public
---@return int
function Moodle:getLevel() end

---@public
---@param arg0 int
---@param arg1 boolean
---@param arg2 Color
---@return void
function Moodle:setChevron(arg0, arg1, arg2) end

---@public
---@return Color
function Moodle:getChevronColor() end

---@public
---@return boolean
function Moodle:isChevronIsUp() end
