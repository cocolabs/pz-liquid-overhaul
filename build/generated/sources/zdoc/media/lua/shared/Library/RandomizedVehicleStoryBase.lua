---@class RandomizedVehicleStoryBase : zombie.randomizedWorld.randomizedVehicleStory.RandomizedVehicleStoryBase
---@field private chance int
---@field private totalChance int
---@field private rvsMap HashMap|Unknown|Unknown
---@field protected horizontalZone boolean
---@field public baseChance float
---@field protected minX int
---@field protected minY int
---@field protected maxX int
---@field protected maxY int
---@field protected minZoneWidth int
---@field protected minZoneHeight int
RandomizedVehicleStoryBase = {}

---@public
---@return int
function RandomizedVehicleStoryBase:getMaximumDays() end

---@public
---@param arg0 int
---@return void
function RandomizedVehicleStoryBase:setMaximumDays(arg0) end

---@private
---@return RandomizedVehicleStoryBase
function RandomizedVehicleStoryBase:getRandomStory() end

---@public
---@param arg0 IsoMetaGrid.Zone
---@param arg1 IsoChunk
---@return IsoGridSquare
function RandomizedVehicleStoryBase:getCenterOfChunk(arg0, arg1) end

---@public
---@return int
function RandomizedVehicleStoryBase:getMinimumDays() end

---@public
---@param arg0 int
---@return void
function RandomizedVehicleStoryBase:setMinimumDays(arg0) end

---@public
---@return String
function RandomizedVehicleStoryBase:getName() end

---@public
---@param arg0 BaseVehicle
---@param arg1 BaseVehicle
---@param arg2 int
---@param arg3 int
---@param arg4 boolean
---@param arg5 boolean
---@return void
function RandomizedVehicleStoryBase:addSmashedOverlay(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@param arg0 IsoMetaGrid.Zone
---@param arg1 IsoChunk
---@return void
function RandomizedVehicleStoryBase:randomizeVehicleStory(arg0, arg1) end

---@public
---@return String
function RandomizedVehicleStoryBase:getDebugLine() end

---@public
---@param arg0 IsoMetaGrid.Zone
---@param arg1 IsoChunk
---@return void
function RandomizedVehicleStoryBase:initAllRVSMapChance(arg0, arg1) end

---@public
---@return void
function RandomizedVehicleStoryBase:registerCustomOutfits() end

---@public
---@return int
function RandomizedVehicleStoryBase:getChance() end

---@public
---@param arg0 IsoMetaGrid.Zone
---@param arg1 IsoChunk
---@param arg2 boolean
---@return boolean
function RandomizedVehicleStoryBase:doRandomStory(arg0, arg1, arg2) end

---@public
---@param arg0 int
---@return void
function RandomizedVehicleStoryBase:setChance(arg0) end

---@public
---@param arg0 IsoMetaGrid.Zone
---@param arg1 IsoChunk
---@param arg2 boolean
---@return boolean
function RandomizedVehicleStoryBase:isValid(arg0, arg1, arg2) end
