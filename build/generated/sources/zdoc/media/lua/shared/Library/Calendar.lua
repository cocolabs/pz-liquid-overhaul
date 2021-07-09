---@class Calendar : java.util.Calendar
---@field public ERA int
---@field public YEAR int
---@field public MONTH int
---@field public WEEK_OF_YEAR int
---@field public WEEK_OF_MONTH int
---@field public DATE int
---@field public DAY_OF_MONTH int
---@field public DAY_OF_YEAR int
---@field public DAY_OF_WEEK int
---@field public DAY_OF_WEEK_IN_MONTH int
---@field public AM_PM int
---@field public HOUR int
---@field public HOUR_OF_DAY int
---@field public MINUTE int
---@field public SECOND int
---@field public MILLISECOND int
---@field public ZONE_OFFSET int
---@field public DST_OFFSET int
---@field public FIELD_COUNT int
---@field public SUNDAY int
---@field public MONDAY int
---@field public TUESDAY int
---@field public WEDNESDAY int
---@field public THURSDAY int
---@field public FRIDAY int
---@field public SATURDAY int
---@field public JANUARY int
---@field public FEBRUARY int
---@field public MARCH int
---@field public APRIL int
---@field public MAY int
---@field public JUNE int
---@field public JULY int
---@field public AUGUST int
---@field public SEPTEMBER int
---@field public OCTOBER int
---@field public NOVEMBER int
---@field public DECEMBER int
---@field public UNDECIMBER int
---@field public AM int
---@field public PM int
---@field public ALL_STYLES int
---@field STANDALONE_MASK int
---@field public SHORT int
---@field public LONG int
---@field public NARROW_FORMAT int
---@field public NARROW_STANDALONE int
---@field public SHORT_FORMAT int
---@field public LONG_FORMAT int
---@field public SHORT_STANDALONE int
---@field public LONG_STANDALONE int
---@field protected fields int[]
---@field protected isSet boolean[]
---@field private stamp int[]
---@field protected time long
---@field protected isTimeSet boolean
---@field protected areFieldsSet boolean
---@field areAllFieldsSet boolean
---@field private lenient boolean
---@field private zone TimeZone
---@field private sharedZone boolean
---@field private firstDayOfWeek int
---@field private minimalDaysInFirstWeek int
---@field private cachedLocaleData ConcurrentMap|Unknown|Unknown
---@field private UNSET int
---@field private COMPUTED int
---@field private MINIMUM_USER_STAMP int
---@field ALL_FIELDS int
---@field private nextStamp int
---@field currentSerialVersion int
---@field private serialVersionOnStream int
---@field serialVersionUID long
---@field ERA_MASK int
---@field YEAR_MASK int
---@field MONTH_MASK int
---@field WEEK_OF_YEAR_MASK int
---@field WEEK_OF_MONTH_MASK int
---@field DAY_OF_MONTH_MASK int
---@field DATE_MASK int
---@field DAY_OF_YEAR_MASK int
---@field DAY_OF_WEEK_MASK int
---@field DAY_OF_WEEK_IN_MONTH_MASK int
---@field AM_PM_MASK int
---@field HOUR_MASK int
---@field HOUR_OF_DAY_MASK int
---@field MINUTE_MASK int
---@field SECOND_MASK int
---@field MILLISECOND_MASK int
---@field ZONE_OFFSET_MASK int
---@field DST_OFFSET_MASK int
---@field private FIELD_NAME String[]
Calendar = {}

---@public
---@param arg0 int
---@param arg1 int
---@return void
---@overload fun(arg0:int, arg1:int, arg2:int)
---@overload fun(arg0:int, arg1:int, arg2:int, arg3:int, arg4:int)
---@overload fun(arg0:int, arg1:int, arg2:int, arg3:int, arg4:int, arg5:int)
function Calendar:set(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function Calendar:set(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@param arg4 int
---@return void
function Calendar:set(arg0, arg1, arg2, arg3, arg4) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@param arg4 int
---@param arg5 int
---@return void
function Calendar:set(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@param arg0 Object
---@return boolean
function Calendar:before(arg0) end

---@private
---@param arg0 Locale
---@return void
function Calendar:setWeekCountData(arg0) end

---@public
---@param arg0 int
---@return int
function Calendar:getActualMinimum(arg0) end

---@public
---@param arg0 int
---@return boolean
function Calendar:isSet(arg0) end

---@public
---@return String
function Calendar:toString() end

---@private
---@param arg0 TimeZone
---@param arg1 Locale
---@return Calendar
function Calendar:createCalendar(arg0, arg1) end

---@public
---@param arg0 long
---@return void
function Calendar:setTimeInMillis(arg0) end

---@return int
function Calendar:selectFields() end

---@public
---@return Locale[]
function Calendar:getAvailableLocales() end

---@param arg0 int
---@return int
function Calendar:getBaseStyle(arg0) end

---@public
---@return Instant
function Calendar:toInstant() end

---@public
---@return void
---@overload fun(arg0:int)
function Calendar:clear() end

---@public
---@param arg0 int
---@return void
function Calendar:clear(arg0) end

---@private
---@param arg0 ObjectInputStream
---@return void
function Calendar:readObject(arg0) end

---@public
---@param arg0 Object
---@return boolean
function Calendar:equals(arg0) end

---@public
---@param arg0 Object
---@return boolean
function Calendar:after(arg0) end

---@public
---@return Calendar
---@overload fun(arg0:TimeZone)
---@overload fun(arg0:Locale)
---@overload fun(arg0:TimeZone, arg1:Locale)
function Calendar:getInstance() end

---@public
---@param arg0 TimeZone
---@return Calendar
function Calendar:getInstance(arg0) end

---@public
---@param arg0 Locale
---@return Calendar
function Calendar:getInstance(arg0) end

---@public
---@param arg0 TimeZone
---@param arg1 Locale
---@return Calendar
function Calendar:getInstance(arg0, arg1) end

---@param arg0 boolean
---@return void
function Calendar:setZoneShared(arg0) end

---@public
---@return int
function Calendar:getWeeksInWeekYear() end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 Locale
---@return String
function Calendar:getDisplayName(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@return int
function Calendar:getGreatestMinimum(arg0) end

---@param arg0 int
---@return void
function Calendar:setFieldsNormalized(arg0) end

---@public
---@param arg0 TimeZone
---@return void
function Calendar:setTimeZone(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@return void
---@overload fun(arg0:int, arg1:boolean)
function Calendar:roll(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 boolean
---@return void
function Calendar:roll(arg0, arg1) end

---@protected
---@return void
function Calendar:complete() end

---@return void
function Calendar:setUnnormalized() end

---@public
---@param arg0 int
---@return void
function Calendar:setFirstDayOfWeek(arg0) end

---@private
---@param arg0 int
---@param arg1 int
---@return int
function Calendar:aggregateStamp(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 Locale
---@return Map|Unknown|Unknown
function Calendar:getDisplayNames(arg0, arg1, arg2) end

---@param arg0 int
---@return boolean
function Calendar:isExternallySet(arg0) end

---@public
---@param arg0 Calendar
---@return int
---@overload fun(arg0:long)
function Calendar:compareTo(arg0) end

---@private
---@param arg0 long
---@return int
function Calendar:compareTo(arg0) end

---@public
---@return int
function Calendar:getMinimalDaysInFirstWeek() end

---@public
---@return boolean
function Calendar:isWeekDateSupported() end

---@private
---@return void
function Calendar:adjustStamp() end

---@public
---@param arg0 int
---@return int
function Calendar:get(arg0) end

---@public
---@return TimeZone
function Calendar:getTimeZone() end

---@public
---@param arg0 boolean
---@return void
function Calendar:setLenient(arg0) end

---@public
---@param arg0 int
---@return int
function Calendar:getMaximum(arg0) end

---@private
---@param arg0 ObjectOutputStream
---@return void
function Calendar:writeObject(arg0) end

---@public
---@param arg0 int
---@return void
function Calendar:setMinimalDaysInFirstWeek(arg0) end

---@return TimeZone
function Calendar:getZone() end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function Calendar:add(arg0, arg1) end

---@private
---@param arg0 Calendar
---@return long
function Calendar:getMillisOf(arg0) end

---@private
---@return void
function Calendar:invalidateWeekFields() end

---@protected
---@param arg0 int
---@return int
function Calendar:internalGet(arg0) end

---@private
---@param arg0 int
---@return boolean
function Calendar:isNarrowFormatStyle(arg0) end

---@public
---@return int
function Calendar:getWeekYear() end

---@param arg0 int
---@return void
function Calendar:setFieldsComputed(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function Calendar:setWeekDate(arg0, arg1, arg2) end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 DateFormatSymbols
---@return String[]
function Calendar:getFieldStrings(arg0, arg1, arg2) end

---@return boolean
function Calendar:isPartiallyNormalized() end

---@public
---@param arg0 int
---@return int
function Calendar:getActualMaximum(arg0) end

---@param arg0 int
---@param arg1 int
---@return void
function Calendar:internalSet(arg0, arg1) end

---@public
---@param arg0 int
---@return int
function Calendar:getLeastMaximum(arg0) end

---@public
---@return Date
function Calendar:getTime() end

---@public
---@return Set|Unknown
function Calendar:getAvailableCalendarTypes() end

---@public
---@return Object
function Calendar:clone() end

---@private
---@param arg0 int
---@return boolean
function Calendar:isStandaloneStyle(arg0) end

---@param arg0 int
---@param arg1 int
---@return boolean
function Calendar:isFieldSet(arg0, arg1) end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 Locale
---@return Map|Unknown|Unknown
function Calendar:getDisplayNamesImpl(arg0, arg1, arg2) end

---@return int
function Calendar:getSetStateFields() end

---@public
---@param arg0 Date
---@return void
function Calendar:setTime(arg0) end

---@private
---@param arg0 int
---@return int
function Calendar:toStandaloneStyle(arg0) end

---@private
---@return void
function Calendar:updateTime() end

---@return boolean
function Calendar:isFullyNormalized() end

---@param arg0 int
---@return String
function Calendar:getFieldName(arg0) end

---@private
---@param arg0 StringBuilder
---@param arg1 String
---@param arg2 boolean
---@param arg3 long
---@return void
function Calendar:appendValue(arg0, arg1, arg2, arg3) end

---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@param arg4 Locale
---@param arg5 int
---@return boolean
function Calendar:checkDisplayNameParams(arg0, arg1, arg2, arg3, arg4, arg5) end

---@protected
---@return void
function Calendar:computeFields() end

---@public
---@return long
function Calendar:getTimeInMillis() end

---@public
---@return int
function Calendar:hashCode() end

---@private
---@param arg0 int
---@return boolean
function Calendar:isNarrowStyle(arg0) end

---@public
---@param arg0 int
---@return int
function Calendar:getMinimum(arg0) end

---@public
---@return int
function Calendar:getFirstDayOfWeek() end

---@protected
---@return void
function Calendar:computeTime() end

---@public
---@return String
function Calendar:getCalendarType() end

---@public
---@return boolean
function Calendar:isLenient() end
