---@class GameWindow : zombie.GameWindow
---@field public GAME_TITLE String @Game title
---@field private s_fpsTracking FPSTracking
---@field private stringUTF ThreadLocal|Unknown
---@field public GameInput Input
---@field public DEBUG_SAVE boolean
---@field public OkToSaveOnExit boolean
---@field public lastP String
---@field public states GameStateMachine
---@field public bServerDisconnected boolean
---@field public bLoadedAsClient boolean
---@field public kickReason String
---@field public DrawReloadingLua boolean
---@field public ActivatedJoyPad JoypadManager.Joypad
---@field public version String
---@field public closeRequested boolean
---@field public averageFPS float
---@field private doRenderEvent boolean
---@field public bLuaDebuggerKeyDown boolean
---@field public fileSystem FileSystem
---@field public assetManagers AssetManagers
---@field public bGameThreadExited boolean
---@field public GameThread Thread
---@field public texturePacks ArrayList|Unknown
---@field public texturePackTextures FileSystem.TexturePackTextures
GameWindow = {}

---@private
---@return void
function GameWindow:init() end

---@public
---@param output ByteBuffer
---@param str String
---@return void
---@overload fun(output:DataOutputStream, str:String)
function GameWindow:WriteString(output, str) end

---throws java.io.IOException
---@public
---@param output DataOutputStream
---@param str String
---@return void
function GameWindow:WriteString(output, str) end

---@private
---@return void
function GameWindow:run_ez() end

---@public
---@param output ByteBuffer
---@param str String
---@return void
function GameWindow:WriteStringUTF(output, str) end

---@public
---@return String
function GameWindow:getCoopServerHome() end

---@public
---@param arg0 Thread
---@param arg1 Throwable
---@return void
function GameWindow:uncaughtException(arg0, arg1) end

---@public
---@param input ByteBuffer
---@return String
---@overload fun(input:DataInputStream)
function GameWindow:ReadString(input) end

---throws java.io.IOException
---@public
---@param input DataInputStream
---@return String
function GameWindow:ReadString(input) end

---@private
---@param arg0 String
---@param arg1 String
---@return void
function GameWindow:installRequiredLibrary(arg0, arg1) end

---throws java.io.FileNotFoundException, java.io.IOException
---@public
---@param bDoChars boolean
---@return void
function GameWindow:save(bDoChars) end

---@protected
---@return void
function GameWindow:renderInternal() end

---@public
---@param arg0 String
---@param arg1 int
---@return void
---@overload fun(arg0:String, arg1:int, arg2:String)
function GameWindow:LoadTexturePack(arg0, arg1) end

---@public
---@param arg0 String
---@param arg1 int
---@param arg2 String
---@return void
function GameWindow:LoadTexturePack(arg0, arg1, arg2) end

---@private
---@return void
function GameWindow:mainThread() end

---@private
---@param arg0 Thread
---@param arg1 Throwable
---@return void
function GameWindow:uncaughtGlobalException(arg0, arg1) end

---@private
---@param arg0 Thread
---@param arg1 Throwable
---@return void
function GameWindow:uncaughtExceptionMainThread(arg0, arg1) end

---@public
---@param text String
---@return void
function GameWindow:DoLoadingText(text) end

---@private
---@return void
function GameWindow:initShared() end

---Render the current frame
---@public
---@return void
function GameWindow:render() end

---@private
---@return void
function GameWindow:logic() end

---@public
---@return void
function GameWindow:setTexturePackLookup() end

---@public
---@return void
function GameWindow:InitGameThread() end

---@public
---@return void
function GameWindow:savePlayer() end

---@private
---@return void
function GameWindow:renameSaveFolders() end

---throws java.io.EOFException, java.io.IOException
---@public
---@param _in DataInputStream
---@return int
function GameWindow:readInt(_in) end

---@private
---@return void
function GameWindow:exit() end

---@private
---@return void
function GameWindow:frameStep() end

---@private
---@return void
function GameWindow:checkRequiredLibraries() end

---@public
---@param b boolean
---@return void
function GameWindow:doRenderEvent(b) end

---@private
---@return void
function GameWindow:mainThreadInit() end

---@private
---@return void
function GameWindow:enter() end

---@public
---@param arg0 DataInputStream
---@return long
function GameWindow:readLong(arg0) end

---@private
---@return void
function GameWindow:onGameThreadExited() end

---@public
---@param input ByteBuffer
---@return String
function GameWindow:ReadStringUTF(input) end

---@public
---@param pack String
---@return void
function GameWindow:LoadTexturePackDDS(pack) end

---@public
---@return void
function GameWindow:InitDisplay() end
