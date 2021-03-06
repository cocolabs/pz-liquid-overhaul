---@class MetaObject : zombie.iso.MetaObject
---@field type int
---@field x int
---@field y int
---@field def RoomDef
---@field bUsed boolean
MetaObject = {}

---@public
---@return int
function MetaObject:getX() end

---@public
---@return int
function MetaObject:getY() end

---@public
---@param bUsed boolean
---@return void
function MetaObject:setUsed(bUsed) end

---@public
---@return RoomDef
function MetaObject:getRoom() end

---@public
---@return boolean
function MetaObject:getUsed() end

---@public
---@return int
function MetaObject:getType() end
