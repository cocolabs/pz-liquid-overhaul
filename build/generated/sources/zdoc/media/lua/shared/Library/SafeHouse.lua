---@class SafeHouse : zombie.iso.areas.SafeHouse
---@field private x int
---@field private y int
---@field private w int
---@field private h int
---@field private diffError int
---@field private owner String
---@field private players ArrayList|Unknown
---@field private lastVisited long
---@field private title String
---@field private playerConnected int
---@field private openTimer int
---@field private id String
---@field private safehouseList ArrayList|Unknown
SafeHouse = {}

---@public
---@param w int
---@return void
function SafeHouse:setW(w) end

---@public
---@param h int
---@return void
function SafeHouse:setH(h) end

---@public
---@return ArrayList|SafeHouse
function SafeHouse:getSafehouseList() end

---@public
---@return ArrayList|String
function SafeHouse:getPlayers() end

---Update the last visited value everytime someone is in this safehouse
---
---If it's not visited for some time (SafehouseRemoval serveroption) it's automatically removed.
---@public
---@param player IsoPlayer
---@return void
function SafeHouse:updateSafehouse(player) end

---@public
---@return int
function SafeHouse:getY2() end

---@public
---@param y int
---@return void
function SafeHouse:setY(y) end

---@public
---@return void
function SafeHouse:syncSafehouse() end

---@public
---@param arg0 String
---@return SafeHouse
---@overload fun(arg0:IsoPlayer)
function SafeHouse:alreadyHaveSafehouse(arg0) end

---@public
---@param arg0 IsoPlayer
---@return SafeHouse
function SafeHouse:alreadyHaveSafehouse(arg0) end

---@public
---@return int
function SafeHouse:getX2() end

---@public
---@param arg0 IsoGridSquare
---@param arg1 String
---@param arg2 boolean
---@return SafeHouse
function SafeHouse:isSafeHouse(arg0, arg1, arg2) end

---@public
---@param output ByteBuffer
---@return void
function SafeHouse:save(output) end

---@public
---@return String
function SafeHouse:getTitle() end

---@public
---@return int
function SafeHouse:getPlayerConnected() end

---@public
---@param player IsoPlayer
---@return void
---@overload fun(player:IsoPlayer, force:boolean)
function SafeHouse:removeSafeHouse(player) end

---@public
---@param player IsoPlayer
---@param force boolean
---@return void
function SafeHouse:removeSafeHouse(player, force) end

---@public
---@param arg0 String
---@return SafeHouse
---@overload fun(arg0:IsoPlayer)
function SafeHouse:hasSafehouse(arg0) end

---@public
---@param arg0 IsoPlayer
---@return SafeHouse
function SafeHouse:hasSafehouse(arg0) end

---@public
---@return long
function SafeHouse:getLastVisited() end

---@public
---@param arg0 int
---@return void
function SafeHouse:setOpenTimer(arg0) end

---@public
---@param lastVisited long
---@return void
function SafeHouse:setLastVisited(lastVisited) end

---@public
---@param player IsoPlayer
---@return boolean
function SafeHouse:allowSafeHouse(player) end

---@public
---@param arg0 int
---@return void
function SafeHouse:setPlayerConnected(arg0) end

---@public
---@return void
function SafeHouse:updateSafehousePlayersConnected() end

---@public
---@param player String
---@return void
function SafeHouse:addPlayer(player) end

---@public
---@return String
function SafeHouse:getId() end

---@public
---@param square IsoGridSquare
---@param player IsoPlayer
---@return SafeHouse
---@overload fun(x:int, y:int, w:int, h:int, player:String, remote:boolean)
function SafeHouse:addSafeHouse(square, player) end

---@public
---@param x int
---@param y int
---@param w int
---@param h int
---@param player String
---@param remote boolean
---@return SafeHouse
function SafeHouse:addSafeHouse(x, y, w, h, player, remote) end

---@public
---@param x int
---@return void
function SafeHouse:setX(x) end

---@public
---@return void
function SafeHouse:clearSafehouseList() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return SafeHouse
function SafeHouse:load(arg0, arg1) end

---@public
---@param players ArrayList|String
---@return void
function SafeHouse:setPlayers(players) end

---@public
---@param square IsoGridSquare
---@return SafeHouse
---@overload fun(x:int, y:int, w:int, h:int)
function SafeHouse:getSafeHouse(square) end

---@public
---@param x int
---@param y int
---@param w int
---@param h int
---@return SafeHouse
function SafeHouse:getSafeHouse(x, y, w, h) end

---@public
---@param owner String
---@return void
function SafeHouse:setOwner(owner) end

---@public
---@param player String
---@return void
function SafeHouse:removePlayer(player) end

---@public
---@param player IsoPlayer
---@return boolean
function SafeHouse:isOwner(player) end

---@public
---@return int
function SafeHouse:getW() end

---@public
---@param player IsoPlayer
---@return boolean
function SafeHouse:playerAllowed(player) end

---@public
---@param arg0 String
---@return void
function SafeHouse:setTitle(arg0) end

---@public
---@return String
function SafeHouse:getOwner() end

---@public
---@param arg0 IsoGridSquare
---@param arg1 IsoPlayer
---@return String
function SafeHouse:canBeSafehouse(arg0, arg1) end

---@public
---@return int
function SafeHouse:getX() end

---@public
---@param arg0 IsoPlayer
---@return void
function SafeHouse:kickOutOfSafehouse(arg0) end

---@public
---@return void
function SafeHouse:init() end

---@public
---@return int
function SafeHouse:getOpenTimer() end

---@public
---@return int
function SafeHouse:getY() end

---@public
---@return int
function SafeHouse:getH() end
