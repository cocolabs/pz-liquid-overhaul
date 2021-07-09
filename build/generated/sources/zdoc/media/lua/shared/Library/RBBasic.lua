---@class RBBasic : zombie.randomizedWorld.randomizedBuilding.RBBasic
---@field private specificProfessionDistribution ArrayList|Unknown
---@field private specificProfessionRoomDistribution Map|Unknown|Unknown
---@field private coldFood ArrayList|Unknown
---@field private plankStash Map|Unknown|Unknown
---@field private deadSurvivorsStory ArrayList|Unknown
---@field private totalChanceRDS int
---@field private rdsMap HashMap|Unknown|Unknown
---@field private uniqueRDSSpawned ArrayList|Unknown
RBBasic = {}

---@private
---@param arg0 BuildingDef
---@return void
function RBBasic:initRDSMap(arg0) end

---@public
---@param arg0 BuildingDef
---@return void
function RBBasic:randomizeBuilding(arg0) end

---@public
---@param arg0 BuildingDef
---@param arg1 String
---@return void
function RBBasic:doProfessionStory(arg0, arg1) end

---@public
---@return ArrayList|Unknown
function RBBasic:getSurvivorProfession() end

---@public
---@return ArrayList|Unknown
function RBBasic:getUniqueRDSSpawned() end

---@public
---@return ArrayList|Unknown
function RBBasic:getSurvivorStories() end

---@public
---@param arg0 BuildingDef
---@param arg1 RandomizedDeadSurvivorBase
---@return void
function RBBasic:doRandomDeadSurvivorStory(arg0, arg1) end

---@private
---@param arg0 BuildingDef
---@return void
function RBBasic:addRandomDeadSurvivorStory(arg0) end
