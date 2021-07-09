---@class DebugOptions : zombie.debug.DebugOptions
---@field public VERSION int
---@field public instance DebugOptions
---@field private options ArrayList|Unknown
---@field private m_options ArrayList|Unknown
---@field public AssetSlowLoad BooleanDebugOption
---@field public CharacterCreateAllOutfits BooleanDebugOption
---@field public CharacterRenderAimCone BooleanDebugOption
---@field public CharacterRenderAngle BooleanDebugOption
---@field public CharacterRenderTestDotSide BooleanDebugOption
---@field public CharacterRenderDeferredMovement BooleanDebugOption
---@field public CharacterRenderDeferredAngles BooleanDebugOption
---@field public CharacterRegisterDebugVariables BooleanDebugOption
---@field public CharacterAnimateDeferredRotationOnly BooleanDebugOption
---@field public CharacterAnimateNoBoneMasks BooleanDebugOption
---@field public CharacterAnimateNoBoneTwists BooleanDebugOption
---@field public CharacterAnimateZeroCounterRotationBone BooleanDebugOption
---@field public ModelRenderTranslationData BooleanDebugOption
---@field public ModelRenderBip01 BooleanDebugOption
---@field public ModelRenderPrimaryHandBone BooleanDebugOption
---@field public ModelRenderSecondaryHandBone BooleanDebugOption
---@field public ModelRenderSkipCharacters BooleanDebugOption
---@field public CheatClockVisible BooleanDebugOption
---@field public CheatDoorUnlock BooleanDebugOption
---@field public CheatPlayerStartInvisible BooleanDebugOption
---@field public CheatPlayerInvisibleSprint BooleanDebugOption
---@field public CheatPlayerSeeEveryone BooleanDebugOption
---@field public CheatUnlimitedAmmo BooleanDebugOption
---@field public CheatRecipeKnowAll BooleanDebugOption
---@field public CheatTimedActionInstant BooleanDebugOption
---@field public CheatVehicleMechanicsAnywhere BooleanDebugOption
---@field public CheatVehicleStartWithoutKey BooleanDebugOption
---@field public CheatWindowUnlock BooleanDebugOption
---@field public CollideWithObstaclesRadius BooleanDebugOption
---@field public CollideWithObstaclesRender BooleanDebugOption
---@field public DeadBodyAtlasRender BooleanDebugOption
---@field public DebugScenarioForceLaunch BooleanDebugOption
---@field public MechanicsRenderHitbox BooleanDebugOption
---@field public ModelRenderAttachments BooleanDebugOption
---@field public ModelRenderAxis BooleanDebugOption
---@field public ModelRenderBones BooleanDebugOption
---@field public ModelRenderBounds BooleanDebugOption
---@field public ModelRenderLights BooleanDebugOption
---@field public ModelRenderMuzzleflash BooleanDebugOption
---@field public ModelRenderSkipVehicles BooleanDebugOption
---@field public ModelRenderWeaponHitPoint BooleanDebugOption
---@field public ModelRenderWireframe BooleanDebugOption
---@field public ModelSkeleton BooleanDebugOption
---@field public ModRenderLoaded BooleanDebugOption
---@field public PathfindPathToMouseAllowCrawl BooleanDebugOption
---@field public PathfindPathToMouseEnable BooleanDebugOption
---@field public PathfindPathToMouseIgnoreCrawlCost BooleanDebugOption
---@field public PathfindRenderPath BooleanDebugOption
---@field public PathfindRenderWaiting BooleanDebugOption
---@field public PhysicsRender BooleanDebugOption
---@field public PolymapRenderClusters BooleanDebugOption
---@field public PolymapRenderConnections BooleanDebugOption
---@field public PolymapRenderCrawling BooleanDebugOption
---@field public PolymapRenderLineClearCollide BooleanDebugOption
---@field public PolymapRenderNodes BooleanDebugOption
---@field public TooltipInfo BooleanDebugOption
---@field public TranslationPrefix BooleanDebugOption
---@field public UIRenderOutline BooleanDebugOption
---@field public UIDebugConsole BooleanDebugOption
---@field public VehicleCycleColor BooleanDebugOption
---@field public VehicleRenderBlood0 BooleanDebugOption
---@field public VehicleRenderBlood50 BooleanDebugOption
---@field public VehicleRenderBlood100 BooleanDebugOption
---@field public VehicleRenderDamage0 BooleanDebugOption
---@field public VehicleRenderDamage1 BooleanDebugOption
---@field public VehicleRenderDamage2 BooleanDebugOption
---@field public VehicleRenderRust0 BooleanDebugOption
---@field public VehicleRenderRust50 BooleanDebugOption
---@field public VehicleRenderRust100 BooleanDebugOption
---@field public VehicleRenderOutline BooleanDebugOption
---@field public VehicleRenderArea BooleanDebugOption
---@field public VehicleRenderAuthorizations BooleanDebugOption
---@field public VehicleRenderAttackPositions BooleanDebugOption
---@field public VehicleRenderExit BooleanDebugOption
---@field public VehicleRenderIntersectedSquares BooleanDebugOption
---@field public VehicleSpawnEverywhere BooleanDebugOption
---@field public WorldSoundRender BooleanDebugOption
---@field public LightingRender BooleanDebugOption
---@field public SkyboxShow BooleanDebugOption
---@field public WorldStreamerSlowLoad BooleanDebugOption
---@field public DebugDraw_SkipVBODraw BooleanDebugOption
---@field public DebugDraw_SkipDrawNonSkinnedModel BooleanDebugOption
---@field public DebugDraw_SkipWorldShading BooleanDebugOption
---@field public GameProfilerEnabled BooleanDebugOption
---@field public GameTimeSpeedHalf BooleanDebugOption
---@field public GameTimeSpeedQuarter BooleanDebugOption
---@field public ThreadCrash_Enabled BooleanDebugOption
---@field public ThreadCrash_GameThread BooleanDebugOption[]
---@field public ThreadCrash_RenderThread BooleanDebugOption[]
---@field public WorldChunkMap5x5 BooleanDebugOption
---@field public ZombieRenderCanCrawlUnderVehicle BooleanDebugOption
---@field public ZombieRenderFakeDead BooleanDebugOption
---@field public ZombieRenderMemory BooleanDebugOption
---@field public ZombieOutfitRandom BooleanDebugOption
---@field public IsoSprite IsoSprite
---@field public Network Network
---@field public OffscreenBuffer OffscreenBuffer
---@field public Terrain Terrain
---@field public Weather Weather
---@field public Animation Animation
---@field private m_triggerWatcher PredicatedFileWatcher
DebugOptions = {}

---@private
---@return void
function DebugOptions:initMessaging() end

---@public
---@param arg0 String
---@param arg1 boolean
---@return void
function DebugOptions:setBoolean(arg0, arg1) end

---@public
---@param arg0 IDebugOptionGroup
---@return void
function DebugOptions:setParent(arg0) end

---@private
---@param arg0 IDebugOptionGroup
---@return void
function DebugOptions:addDescendantOptions(arg0) end

---@public
---@return IDebugOptionGroup
function DebugOptions:getParent() end

---@private
---@param arg0 String
---@param arg1 boolean
---@return BooleanDebugOption
function DebugOptions:newDebugOnlyOption(arg0, arg1) end

---@public
---@param arg0 IDebugOption
---@return void
function DebugOptions:onChildAdded(arg0) end

---@public
---@return int
function DebugOptions:getOptionCount() end

---@private
---@param arg0 int
---@return void
function DebugOptions:testThreadCrashInternal(arg0) end

---@public
---@return Iterable|Unknown
function DebugOptions:getChildren() end

---@public
---@param arg0 int
---@return void
function DebugOptions:testThreadCrash(arg0) end

---@private
---@param arg0 String
---@param arg1 boolean
---@return BooleanDebugOption
function DebugOptions:newOption(arg0, arg1) end

---@private
---@param arg0 IDebugOption
---@return void
function DebugOptions:addOption(arg0) end

---@private
---@param arg0 String
---@return void
function DebugOptions:onTrigger_SetDebugOptions(arg0) end

---@public
---@param arg0 String
---@return boolean
function DebugOptions:getBoolean(arg0) end

---@private
---@param arg0 IDebugOptionGroup
---@return IDebugOptionGroup
function DebugOptions:newOptionGroup(arg0) end

---@public
---@return void
function DebugOptions:save() end

---@public
---@return void
function DebugOptions:load() end

---@public
---@return String
function DebugOptions:getName() end

---@public
---@param arg0 IDebugOption
---@return void
function DebugOptions:onDescendantAdded(arg0) end

---@public
---@param arg0 IDebugOption
---@return void
function DebugOptions:addChild(arg0) end

---@public
---@return void
function DebugOptions:init() end

---@public
---@param arg0 String
---@return BooleanDebugOption
function DebugOptions:getOptionByName(arg0) end

---@public
---@param arg0 int
---@return BooleanDebugOption
function DebugOptions:getOptionByIndex(arg0) end
