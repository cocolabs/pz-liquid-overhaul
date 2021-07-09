---@class IsoTelevision : zombie.iso.objects.IsoTelevision
---@field protected screenSprites ArrayList|Unknown
---@field protected defaultToNoise boolean
---@field private cacheObjectSprite IsoSprite
---@field protected facing IsoDirections
---@field private hasSetupScreens boolean
---@field private tickIsLightUpdate boolean
---@field private currentScreen IsoTelevision.Screens
---@field private spriteIndex int
IsoTelevision = {}

---@protected
---@return void
function IsoTelevision:updateTvScreen() end

---@public
---@return void
function IsoTelevision:clearTvScreenSprites() end

---@public
---@param arg0 IsoSprite
---@return void
function IsoTelevision:removeTvScreenSprite(arg0) end

---@protected
---@return void
function IsoTelevision:updateLightSource() end

---@private
---@param arg0 IsoTelevision.Screens
---@return void
function IsoTelevision:setScreen(arg0) end

---@protected
---@param arg0 boolean
---@return void
function IsoTelevision:init(arg0) end

---@private
---@return void
function IsoTelevision:setupDefaultScreens() end

---@public
---@return String
function IsoTelevision:getObjectName() end

---@public
---@return void
function IsoTelevision:renderlast() end

---@public
---@return void
function IsoTelevision:update() end

---@public
---@param arg0 IsoSprite
---@return void
function IsoTelevision:addTvScreenSprite(arg0) end

---@public
---@param arg0 IsoPlayer
---@return boolean
function IsoTelevision:isFacing(arg0) end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoTelevision:save(arg0) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function IsoTelevision:load(arg0, arg1) end
