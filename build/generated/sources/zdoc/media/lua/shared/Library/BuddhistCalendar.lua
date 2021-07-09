---@class BuddhistCalendar : sun.util.BuddhistCalendar
---@field private serialVersionUID long
---@field private BUDDHIST_YEAR_OFFSET int
---@field private yearOffset int
BuddhistCalendar = {}

---@private
---@param arg0 ObjectInputStream
---@return void
function BuddhistCalendar:readObject(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 Locale
---@return Map|Unknown|Unknown
function BuddhistCalendar:getDisplayNames(arg0, arg1, arg2) end

---@public
---@return String
function BuddhistCalendar:toString() end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function BuddhistCalendar:set(arg0, arg1) end

---@public
---@param arg0 int
---@return int
function BuddhistCalendar:getActualMaximum(arg0) end

---@public
---@param arg0 Object
---@return boolean
function BuddhistCalendar:equals(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 Locale
---@return String
function BuddhistCalendar:getDisplayName(arg0, arg1, arg2) end

---@public
---@return int
function BuddhistCalendar:hashCode() end

---@public
---@param arg0 int
---@return int
function BuddhistCalendar:get(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function BuddhistCalendar:roll(arg0, arg1) end

---@public
---@return String
function BuddhistCalendar:getCalendarType() end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function BuddhistCalendar:add(arg0, arg1) end
