---@class Iterator : java.util.Iterator
Iterator = {}

---@public
---@return Object
function Iterator:next() end

---@public
---@return boolean
function Iterator:hasNext() end

---@public
---@return void
function Iterator:remove() end

---@public
---@param arg0 Consumer|Unknown
---@return void
function Iterator:forEachRemaining(arg0) end
