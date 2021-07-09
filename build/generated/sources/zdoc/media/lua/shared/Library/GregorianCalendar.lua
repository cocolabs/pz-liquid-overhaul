---@class GregorianCalendar : java.util.GregorianCalendar
---@field public BC int
---@field BCE int
---@field public AD int
---@field CE int
---@field private EPOCH_OFFSET int
---@field private EPOCH_YEAR int
---@field MONTH_LENGTH int[]
---@field LEAP_MONTH_LENGTH int[]
---@field private ONE_SECOND int
---@field private ONE_MINUTE int
---@field private ONE_HOUR int
---@field private ONE_DAY long
---@field private ONE_WEEK long
---@field MIN_VALUES int[]
---@field LEAST_MAX_VALUES int[]
---@field MAX_VALUES int[]
---@field serialVersionUID long
---@field private gcal Gregorian
---@field private jcal JulianCalendar
---@field private jeras Era[]
---@field DEFAULT_GREGORIAN_CUTOVER long
---@field private gregorianCutover long
---@field private gregorianCutoverDate long
---@field private gregorianCutoverYear int
---@field private gregorianCutoverYearJulian int
---@field private gdate BaseCalendar.Date
---@field private cdate BaseCalendar.Date
---@field private calsys BaseCalendar
---@field private zoneOffsets int[]
---@field private originalFields int[]
---@field private cachedFixedDate long
GregorianCalendar = {}

---@private
---@param arg0 long
---@param arg1 long
---@return int
function GregorianCalendar:getWeekNumber(arg0, arg1) end

---@private
---@return BaseCalendar.Date
function GregorianCalendar:getGregorianCutoverDate() end

---@private
---@param arg0 ObjectInputStream
---@return void
function GregorianCalendar:readObject(arg0) end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getMaximum(arg0) end

---@public
---@param arg0 Object
---@return boolean
function GregorianCalendar:equals(arg0) end

---@private
---@return BaseCalendar.Date
function GregorianCalendar:getLastJulianDate() end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getLeastMaximum(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function GregorianCalendar:add(arg0, arg1) end

---@private
---@param arg0 int
---@return boolean
function GregorianCalendar:isCutoverYear(arg0) end

---@public
---@param arg0 int
---@return boolean
function GregorianCalendar:isLeapYear(arg0) end

---@private
---@param arg0 int
---@return int
---@overload fun(arg0:int, arg1:int)
function GregorianCalendar:monthLength(arg0) end

---@private
---@param arg0 int
---@param arg1 int
---@return int
function GregorianCalendar:monthLength(arg0, arg1) end

---@private
---@param arg0 BaseCalendar
---@param arg1 int
---@param arg2 int
---@return long
function GregorianCalendar:getFixedDate(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@return void
---@overload fun(arg0:int, arg1:boolean)
function GregorianCalendar:roll(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 boolean
---@return void
function GregorianCalendar:roll(arg0, arg1) end

---@public
---@param arg0 Date
---@return void
---@overload fun(arg0:long)
function GregorianCalendar:setGregorianChange(arg0) end

---@private
---@param arg0 long
---@return void
function GregorianCalendar:setGregorianChange(arg0) end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@return int
function GregorianCalendar:getRolledValue(arg0, arg1, arg2, arg3) end

---@private
---@return int
---@overload fun(arg0:int)
function GregorianCalendar:yearLength() end

---@private
---@param arg0 int
---@return int
function GregorianCalendar:yearLength(arg0) end

---@public
---@return int
function GregorianCalendar:getWeeksInWeekYear() end

---@public
---@return boolean
function GregorianCalendar:isWeekDateSupported() end

---@private
---@return void
function GregorianCalendar:pinDayOfMonth() end

---@private
---@param arg0 BaseCalendar.Date
---@param arg1 long
---@return long
function GregorianCalendar:getFixedDateMonth1(arg0, arg1) end

---@public
---@return ZonedDateTime
function GregorianCalendar:toZonedDateTime() end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getGreatestMinimum(arg0) end

---@private
---@return int
function GregorianCalendar:actualMonthLength() end

---@public
---@return Object
function GregorianCalendar:clone() end

---@private
---@param arg0 long
---@return BaseCalendar.Date
function GregorianCalendar:getCalendarDate(arg0) end

---@public
---@return int
function GregorianCalendar:hashCode() end

---@private
---@return long
function GregorianCalendar:getCurrentFixedDate() end

---@public
---@return TimeZone
function GregorianCalendar:getTimeZone() end

---@public
---@param arg0 ZonedDateTime
---@return GregorianCalendar
function GregorianCalendar:from(arg0) end

---@private
---@param arg0 BaseCalendar.Date
---@param arg1 long
---@return long
function GregorianCalendar:getFixedDateJan1(arg0, arg1) end

---@protected
---@return void
---@overload fun(arg0:int, arg1:int)
function GregorianCalendar:computeFields() end

---@private
---@param arg0 int
---@param arg1 int
---@return int
function GregorianCalendar:computeFields(arg0, arg1) end

---@public
---@return int
function GregorianCalendar:getWeekYear() end

---@public
---@param arg0 TimeZone
---@return void
function GregorianCalendar:setTimeZone(arg0) end

---@protected
---@return void
function GregorianCalendar:computeTime() end

---@private
---@return GregorianCalendar
function GregorianCalendar:getNormalizedCalendar() end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getActualMaximum(arg0) end

---@private
---@return long
function GregorianCalendar:getYearOffsetInMillis() end

---@private
---@return BaseCalendar
function GregorianCalendar:getCutoverCalendarSystem() end

---@public
---@return String
function GregorianCalendar:getCalendarType() end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function GregorianCalendar:setWeekDate(arg0, arg1, arg2) end

---@private
---@return BaseCalendar
function GregorianCalendar:getJulianCalendarSystem() end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getActualMinimum(arg0) end

---@public
---@param arg0 int
---@return int
function GregorianCalendar:getMinimum(arg0) end

---@private
---@return int
function GregorianCalendar:internalGetEra() end

---@public
---@return Date
function GregorianCalendar:getGregorianChange() end
