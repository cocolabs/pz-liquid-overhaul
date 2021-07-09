---@class DataCell : zombie.iso.areas.isoregion.DataCell
---@field private hashId int
---@field private cellX int
---@field private cellY int
---@field protected dataChunks Map|Unknown|Unknown
DataCell = {}

---@protected
---@param arg0 int
---@return DataChunk
function DataCell:getChunk(arg0) end

---@protected
---@param arg0 DataChunk
---@return void
function DataCell:setChunk(arg0) end

---@protected
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return DataChunk
function DataCell:addChunk(arg0, arg1, arg2) end

---@protected
---@param arg0 List|Unknown
---@return void
function DataCell:getAllChunks(arg0) end
