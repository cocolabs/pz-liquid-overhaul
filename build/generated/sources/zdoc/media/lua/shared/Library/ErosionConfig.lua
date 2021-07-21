---@class ErosionConfig : zombie.erosion.ErosionConfig
---@field public seeds ErosionConfig.Seeds
---@field public time ErosionConfig.Time
---@field public debug ErosionConfig.Debug
---@field public season ErosionConfig.Season
ErosionConfig = {}

---@public
---@return void
function ErosionConfig:consolePrint() end

---@public
---@param bb ByteBuffer
---@return void
function ErosionConfig:load(bb) end

---@public
---@param _file String
---@return void
function ErosionConfig:writeFile(_file) end

---@public
---@param _file String
---@return boolean
function ErosionConfig:readFile(_file) end

---@public
---@return ErosionConfig.Debug
function ErosionConfig:getDebug() end

---@public
---@param bb ByteBuffer
---@return void
function ErosionConfig:save(bb) end
