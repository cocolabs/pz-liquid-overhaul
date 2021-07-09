---@class MainScreenState : zombie.gameStates.MainScreenState
---@field public Version String
---@field public ambient Audio
---@field public totalScale float
---@field public alpha float
---@field public alphaStep float
---@field private RestartDebounceClickTimer int
---@field public Elements ArrayList|MainScreenState.ScreenElement
---@field public targetAlpha float
---@field lastH int
---@field lastW int
---@field Logo MainScreenState.ScreenElement
---@field public instance MainScreenState
---@field public showLogo boolean
---@field private FadeAlpha float
---@field lightningTime float
---@field lastLightningTime float
---@field public lightningDelta float
---@field public lightningTargetDelta float
---@field public lightningFullTimer float
---@field public lightningCount float
---@field public lightOffCount float
---@field private connectToServerState ConnectToServerState
MainScreenState = {}

---Overrides:
---
---exit in class GameState
---@public
---@return void
function MainScreenState:exit() end

---@private
---@param arg0 BufferedImage
---@param arg1 BufferedImage
---@return double
function MainScreenState:getIconRatio(arg0, arg1) end

---Overrides:
---
---update in class GameState
---@public
---@return GameStateMachine.StateAction
function MainScreenState:update() end

---Overrides:
---
---enter in class GameState
---@public
---@return void
function MainScreenState:enter() end

---@public
---@return MainScreenState
function MainScreenState:getInstance() end

---@public
---@param args String[]
---@return void
function MainScreenState:main(args) end

---Overrides:
---
---redirectState in class GameState
---@public
---@return GameState
function MainScreenState:redirectState() end

---@public
---@return boolean
function MainScreenState:ShouldShowLogo() end

---@public
---@param tex Texture
---@param x int
---@param y int
---@param width int
---@param height int
---@param col Color
---@return void
---@overload fun(tex:Texture, x:int, y:int, width:int, height:int, alpha:float)
function MainScreenState:DrawTexture(tex, x, y, width, height, col) end

---@public
---@param tex Texture
---@param x int
---@param y int
---@param width int
---@param height int
---@param alpha float
---@return void
function MainScreenState:DrawTexture(tex, x, y, width, height, alpha) end

---@public
---@param image BufferedImage
---@return ByteBuffer
function MainScreenState:convertToByteBuffer(image) end

---@private
---@param arg0 String
---@param arg1 String[]
---@return String
function MainScreenState:wmic(arg0, arg1) end

---@public
---@param arg0 ConnectToServerState
---@return void
function MainScreenState:setConnectToServerState(arg0) end

---Overrides:
---
---render in class GameState
---@public
---@return void
function MainScreenState:render() end

---@public
---@return ByteBuffer[]
function MainScreenState:loadIcons() end

---@private
---@return void
function MainScreenState:printSpecs() end

---@private
---@param arg0 BufferedImage
---@param arg1 int
---@return ByteBuffer
function MainScreenState:loadInstance(arg0, arg1) end
