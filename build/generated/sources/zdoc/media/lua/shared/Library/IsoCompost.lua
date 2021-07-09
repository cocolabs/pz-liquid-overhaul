---@class IsoCompost : zombie.iso.objects.IsoCompost
---@field private compost float
---@field private LastUpdated float
IsoCompost = {}

---@public
---@return void
function IsoCompost:update() end

---@public
---@return void
function IsoCompost:syncCompost() end

---@public
---@return float
function IsoCompost:getCompost() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function IsoCompost:load(arg0, arg1) end

---@public
---@return void
function IsoCompost:addToWorld() end

---@public
---@return void
function IsoCompost:updateSprite() end

---@public
---@param arg0 float
---@return void
function IsoCompost:setCompost(arg0) end

---@public
---@return String
function IsoCompost:getObjectName() end

---@public
---@return void
function IsoCompost:remove() end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoCompost:save(arg0) end
