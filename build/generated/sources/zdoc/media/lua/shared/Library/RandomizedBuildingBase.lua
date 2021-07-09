---@class RandomizedBuildingBase : zombie.randomizedWorld.randomizedBuilding.RandomizedBuildingBase
---@field private chance int
---@field private totalChance int
---@field private rbMap HashMap|Unknown|Unknown
---@field protected KBBuildingX int
---@field protected KBBuildingY int
---@field private weaponsList HashMap|Unknown|Unknown
RandomizedBuildingBase = {}

---@public
---@param arg0 int
---@return void
function RandomizedBuildingBase:setMinimumRooms(arg0) end

---@public
---@param arg0 int
---@return void
function RandomizedBuildingBase:setMinimumDays(arg0) end

---@public
---@return int
function RandomizedBuildingBase:getMinimumDays() end

---@public
---@param arg0 IsoGridSquare
---@return IsoWindow
function RandomizedBuildingBase:getWindow(arg0) end

---@public
---@param arg0 BuildingDef
---@param arg1 int
---@param arg2 String
---@param arg3 Integer
---@param arg4 RoomDef
---@return ArrayList|Unknown
function RandomizedBuildingBase:addZombies(arg0, arg1, arg2, arg3, arg4) end

---@public
---@param arg0 BuildingDef
---@param arg1 boolean
---@return boolean
function RandomizedBuildingBase:isValid(arg0, arg1) end

---@public
---@return void
function RandomizedBuildingBase:init() end

---@private
---@param arg0 BuildingDef
---@return void
function RandomizedBuildingBase:customizeStartingHouse(arg0) end

---@public
---@param arg0 IsoGridSquare
---@return IsoDoor
function RandomizedBuildingBase:getDoor(arg0) end

---@public
---@return int
function RandomizedBuildingBase:getChance() end

---@public
---@param arg0 int
---@param arg1 String
---@param arg2 Integer
---@param arg3 IsoGridSquare
---@return ArrayList|Unknown
function RandomizedBuildingBase:addZombiesOnSquare(arg0, arg1, arg2, arg3) end

---@public
---@return void
function RandomizedBuildingBase:initAllRBMapChance() end

---@public
---@param arg0 ItemContainer
---@param arg1 boolean
---@param arg2 boolean
---@param arg3 boolean
---@return HandWeapon
function RandomizedBuildingBase:addRandomRangedWeapon(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 IsoBuilding
---@return void
function RandomizedBuildingBase:ChunkLoaded(arg0) end

---@public
---@param arg0 IsoGridSquare
---@param arg1 int
---@return void
function RandomizedBuildingBase:addBarricade(arg0, arg1) end

---@protected
---@param arg0 BuildingDef
---@return void
function RandomizedBuildingBase:removeAllZombies(arg0) end

---@private
---@return RandomizedBuildingBase
function RandomizedBuildingBase:getRandomStory() end

---@public
---@return int
function RandomizedBuildingBase:getMinimumRooms() end

---@public
---@param arg0 int
---@return void
function RandomizedBuildingBase:setChance(arg0) end

---@public
---@param arg0 BuildingDef
---@param arg1 String
---@param arg2 int
---@return void
function RandomizedBuildingBase:spawnItemsInContainers(arg0, arg1, arg2) end

---@public
---@param arg0 BuildingDef
---@return void
function RandomizedBuildingBase:randomizeBuilding(arg0) end
