---@class IsoDirectionSet : zombie.iso.IsoDirectionSet
---@field public set int
IsoDirectionSet = {}

---@public
---@param dir IsoDirections
---@param amount int
---@return IsoDirections
function IsoDirectionSet:rotate(dir, amount) end

---@public
---@return IsoDirections
function IsoDirectionSet:getNext() end
