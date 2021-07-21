---@class DebugChunkState : zombie.gameStates.DebugChunkState
---@field public instance DebugChunkState
---@field private m_luaEnv EditVehicleState.LuaEnvironment
---@field private bExit boolean
---@field private m_gameUI ArrayList|Unknown
---@field private m_selfUI ArrayList|Unknown
---@field private m_bSuspendUI boolean
---@field private m_table KahluaTable
---@field private m_playerIndex int
---@field private m_z int
---@field private gridX int
---@field private gridY int
---@field private FONT UIFont
---@field keyQpressed boolean
---@field private VERSION int
---@field private options ArrayList|Unknown
---@field private BuildingRect DebugChunkState.BooleanDebugOption
---@field private ChunkGrid DebugChunkState.BooleanDebugOption
---@field private EmptySquares DebugChunkState.BooleanDebugOption
---@field private FlyBuzzEmitters DebugChunkState.BooleanDebugOption
---@field private LightSquares DebugChunkState.BooleanDebugOption
---@field private LineClearCollide DebugChunkState.BooleanDebugOption
---@field private ObjectPicker DebugChunkState.BooleanDebugOption
---@field private RoomLightRects DebugChunkState.BooleanDebugOption
---@field private ZoneRect DebugChunkState.BooleanDebugOption
DebugChunkState = {}

---Overrides:
---
---enter in class GameState
---@public
---@return void
function DebugChunkState:enter() end

---@public
---@param arg0 String
---@return Object
function DebugChunkState:fromLua0(arg0) end

---@public
---@return int
function DebugChunkState:getOptionCount() end

---@private
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 float
---@param arg7 float
---@param arg8 int
---@return void
function DebugChunkState:DrawIsoRect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) end

---@public
---@param arg0 String
---@param arg1 boolean
---@return void
function DebugChunkState:setBoolean(arg0, arg1) end

---@public
---@param arg0 KahluaTable
---@return void
function DebugChunkState:setTable(arg0) end

---@public
---@param arg0 String
---@param arg1 Object
---@param arg2 Object
---@return Object
function DebugChunkState:fromLua2(arg0, arg1, arg2) end

---@public
---@param arg0 String
---@return ConfigOption
function DebugChunkState:getOptionByName(arg0) end

---@public
---@return GameStateMachine.StateAction
function DebugChunkState:updateScene() end

---@private
---@return void
function DebugChunkState:renderUI() end

---@private
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 float
---@param arg7 float
---@param arg8 int
---@return void
function DebugChunkState:DrawIsoLine(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) end

---@return void
function DebugChunkState:drawPlayerInfo() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return boolean
function DebugChunkState:IsBehindStuffRecY(arg0, arg1, arg2) end

---@public
---@return void
function DebugChunkState:yield() end

---@public
---@param cell IsoCell
---@param x1 int
---@param y1 int
---@param z1 int
---@param x0 int
---@param y0 int
---@param z0 int
---@param bIgnoreDoors boolean
---@return LosUtil.TestResults
function DebugChunkState:lineClearCached(cell, x1, y1, z1, x0, y0, z0, bIgnoreDoors) end

---Overrides:
---
---exit in class GameState
---@public
---@return void
function DebugChunkState:exit() end

---Overrides:
---
---update in class GameState
---@public
---@return GameStateMachine.StateAction
function DebugChunkState:update() end

---@public
---@param arg0 String
---@return boolean
function DebugChunkState:getBoolean(arg0) end

---@private
---@return void
function DebugChunkState:updateCursor() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 String
---@return void
function DebugChunkState:DrawString(arg0, arg1, arg2) end

---@public
---@param arg0 String
---@param arg1 Object
---@return Object
function DebugChunkState:fromLua1(arg0, arg1) end

---@private
---@return void
function DebugChunkState:DrawBehindStuff() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 float
---@return void
function DebugChunkState:paintSquare(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@private
---@return void
function DebugChunkState:restoreGameUI() end

---@public
---@param arg0 int
---@return ConfigOption
function DebugChunkState:getOptionByIndex(arg0) end

---@public
---@return void
function DebugChunkState:load() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return boolean
function DebugChunkState:IsBehindStuffRecX(arg0, arg1, arg2) end

---@private
---@return void
function DebugChunkState:saveGameUI() end

---@private
---@return void
function DebugChunkState:drawCursor() end

---Overrides:
---
---render in class GameState
---@public
---@return void
function DebugChunkState:render() end

---@private
---@return void
function DebugChunkState:drawGrid() end

---@private
---@param arg0 int
---@param arg1 int
---@param arg2 int
---@param arg3 int
---@return boolean
function DebugChunkState:IsBehindStuffRecXY(arg0, arg1, arg2, arg3) end

---@private
---@param arg0 IsoGridSquare
---@return boolean
function DebugChunkState:IsBehindStuff(arg0) end

---@public
---@return void
function DebugChunkState:save() end

---@return void
function DebugChunkState:drawModData() end

---@public
---@return void
function DebugChunkState:renderScene() end

---@public
---@return void
function DebugChunkState:reenter() end
