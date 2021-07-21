---@class SteamUGCDetails : zombie.core.znet.SteamUGCDetails
---@field private ID long
---@field private title String
---@field private fileSize int
---@field private childIDs long[]
SteamUGCDetails = {}

---@public
---@return String
function SteamUGCDetails:getIDString() end

---@public
---@return String
function SteamUGCDetails:getState() end

---@public
---@return String
function SteamUGCDetails:getTitle() end

---@public
---@return long
function SteamUGCDetails:getID() end

---@public
---@return long[]
function SteamUGCDetails:getChildren() end

---@public
---@return int
function SteamUGCDetails:getNumChildren() end

---@public
---@param arg0 int
---@return long
function SteamUGCDetails:getChildID(arg0) end

---@public
---@return int
function SteamUGCDetails:getFileSize() end
