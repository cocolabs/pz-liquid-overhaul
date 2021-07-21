---@class IsoPlayer : zombie.characters.IsoPlayer
---@field private attackType String
---@field public DEATH_MUSIC_NAME String
---@field private allowSprint boolean
---@field private allowRun boolean
---@field public isTestAIMode boolean
---@field public NetRemoteState_Idle byte
---@field public NetRemoteState_Walk byte
---@field public NetRemoteState_Run byte
---@field public NoSound boolean
---@field private TIME_RIGHT_PRESSED_SECONDS float
---@field public assumedPlayer int
---@field public numPlayers int
---@field public MAX int
---@field public players IsoPlayer[]
---@field public NetRemoteState_Attack byte
---@field public instance IsoPlayer
---@field private instanceLock Object
---@field private testHitPosition JVector2
---@field private FollowDeadCount int
---@field private StaticTraits Stack|Unknown
---@field private lmx int
---@field private lmy int
---@field private ignoreAutoVault boolean
---@field public remoteSneakLvl int
---@field public remoteStrLvl int
---@field public remoteFitLvl int
---@field public canSeeAll boolean
---@field public canHearAll boolean
---@field private tempo JVector2
---@field private tempVector2 JVector2
---@field private forwardStr String
---@field private backwardStr String
---@field private leftStr String
---@field private rightStr String
---@field private CoopPVP boolean
---@field private ignoreContextKey boolean
---@field private ignoreInputsForDirection boolean
---@field public spottedByPlayer boolean
---@field private spottedPlayerTimer HashMap|Unknown|Unknown
---@field private extUpdateCount float
---@field private s_randomIdleFidgetInterval int
---@field public attackStarted boolean
---@field private m_isoPlayerTriggerWatcher PredicatedFileWatcher
---@field private m_setClothingTriggerWatcher PredicatedFileWatcher
---@field private tempVector2_1 JVector2
---@field private tempVector2_2 JVector2
---@field protected humanVisual HumanVisual
---@field protected itemVisuals ItemVisuals
---@field public targetedByZombie boolean
---@field public lastTargeted float
---@field public TimeSinceOpenDoor float
---@field public bRemote boolean
---@field public TimeSinceLastNetData int
---@field public accessLevel String
---@field public tagPrefix String
---@field public showTag boolean
---@field public factionPvp boolean
---@field public OnlineID int
---@field public OnlineChunkGridWidth int
---@field public netHistory PlayerNetHistory
---@field public bJoypadMovementActive boolean
---@field public bJoypadIgnoreAimUntilCentered boolean
---@field public bJoypadIgnoreChargingRT boolean
---@field protected bJoypadBDown boolean
---@field protected bJoypadSprint boolean
---@field public mpTorchCone boolean
---@field public mpTorchDist float
---@field public mpTorchStrength float
---@field public PlayerIndex int
---@field public serverPlayerIndex int
---@field public useChargeDelta float
---@field public JoypadBind int
---@field public ContextPanic float
---@field public numNearbyBuildingsRooms float
---@field public isCharging boolean
---@field public isChargingLT boolean
---@field private bLookingWhileInVehicle boolean
---@field public JustMoved boolean
---@field public L3Pressed boolean
---@field public maxWeightDelta float
---@field public CurrentSpeed float
---@field public MaxSpeed float
---@field public bDeathFinished boolean
---@field public isSpeek boolean
---@field public isVoiceMute boolean
---@field public playerMoveDir JVector2
---@field public soundListener BaseSoundListener
---@field public username String
---@field public dirtyRecalcGridStack boolean
---@field public dirtyRecalcGridStackTime float
---@field public runningTime float
---@field public timePressedContext float
---@field public chargeTime float
---@field public useChargeTime float
---@field public bPressContext boolean
---@field public closestZombie float
---@field public lastAngle JVector2
---@field public SaveFileName String
---@field public bBannedAttacking boolean
---@field public sqlID int
---@field protected ClearSpottedTimer int
---@field protected timeSinceLastStab float
---@field protected LastSpotted Stack|Unknown
---@field protected bChangeCharacterDebounce boolean
---@field protected followID int
---@field protected FollowCamStack Stack|Unknown
---@field protected bSeenThisFrame boolean
---@field protected bCouldBeSeenThisFrame boolean
---@field protected AsleepTime float
---@field protected spottedList Stack|Unknown
---@field protected TicksSinceSeenZombie int
---@field protected Waiting boolean
---@field protected DragCharacter IsoSurvivor
---@field protected heartDelay float
---@field protected heartDelayMax float
---@field protected heartEventInstance long
---@field protected DrunkOscilatorStepSin float
---@field protected DrunkOscilatorRateSin float
---@field protected DrunkOscilatorStepCos float
---@field protected DrunkOscilatorRateCos float
---@field protected DrunkOscilatorStepCos2 float
---@field protected DrunkOscilatorRateCos2 float
---@field protected DrunkSin float
---@field protected DrunkCos float
---@field protected DrunkCos2 float
---@field protected MinOscilatorRate float
---@field protected MaxOscilatorRate float
---@field protected DesiredSinRate float
---@field protected DesiredCosRate float
---@field protected OscilatorChangeRate float
---@field protected Forname String
---@field protected Surname String
---@field protected DialogMood int
---@field protected ping int
---@field protected DragObject IsoMovingObject
---@field private lastSeenZombieTime double
---@field private testemitter BaseSoundEmitter
---@field private checkSafehouse int
---@field private attackFromBehind boolean
---@field private TimeRightPressed float
---@field private aimKeyDownMS long
---@field private runKeyDownMS long
---@field private sprintKeyDownMS long
---@field private hypothermiaCache int
---@field private hyperthermiaCache int
---@field private ticksSincePressedMovement float
---@field private flickTorch boolean
---@field private checkNearbyRooms float
---@field private bUseVehicle boolean
---@field private bUsedVehicle boolean
---@field private useVehicleDuration float
---@field private tempVector3f Vector3f
---@field private inputState IsoPlayer.InputState
---@field private isWearingNightVisionGoggles boolean
---@field private transactionID Integer
---@field private MoveSpeed float
---@field private offSetXUI int
---@field private offSetYUI int
---@field private combatSpeed float
---@field private HoursSurvived double
---@field private bSentDeath boolean
---@field private noClip boolean
---@field private authorizeMeleeAction boolean
---@field private authorizeShoveStomp boolean
---@field private blockMovement boolean
---@field private nutrition Nutrition
---@field private fitness Fitness
---@field private forceOverrideAnim boolean
---@field private initiateAttack boolean
---@field private tagColor ColorInfo
---@field private displayName String
---@field private seeNonPvpZone boolean
---@field private mechanicsItem HashMap|Unknown|Unknown
---@field private sleepingPillsTaken int
---@field private lastPillsTaken long
---@field private heavyBreathInstance long
---@field private heavyBreathSoundName String
---@field private allChatMuted boolean
---@field private forceAim boolean
---@field private forceRun boolean
---@field private forceSprint boolean
---@field private LastRT boolean
---@field private ULbeatenVehicle UpdateLimit
---@field private bMultiplayer boolean
---@field private SaveFileIP String
---@field private vehicle4testCollision BaseVehicle
---@field private steamID long
---@field private vehicleContainerData IsoPlayer.VehicleContainerData
---@field private isWalking boolean
---@field private footInjuryTimer int
---@field private bSneakDebounce boolean
---@field private footstepTimer UpdateLimit
---@field private m_turnDelta float
---@field public m_bKnockedDown boolean
---@field protected m_isPlayerMoving boolean
---@field private m_walkSpeed float
---@field private m_walkInjury float
---@field private m_runSpeed float
---@field private m_idleSpeed float
---@field private m_deltaX float
---@field private m_deltaY float
---@field private m_windspeed float
---@field private m_windForce float
---@field private m_IPX float
---@field private m_IPY float
---@field private pressedRunTimer float
---@field private pressedRun boolean
---@field private m_meleePressed boolean
---@field private m_lastAttackWasShove boolean
---@field private m_isPerformingAnAction boolean
---@field public networkAI NetworkPlayerAI
---@field public replay ReplayManager
---@field public strikes ArrayList|Unknown
---@field private pathfindRun boolean
---@field private s_moveVars IsoPlayer.MoveVars
---@field atkTimer int
---@field private s_targetsProne ArrayList|Unknown
---@field private s_targetsStanding ArrayList|Unknown
---@field private bReloadButtonDown boolean
---@field private bRackButtonDown boolean
---@field private bReloadKeyDown boolean
---@field private bRackKeyDown boolean
---@field shoottimer int
IsoPlayer = {}

---@public
---@return boolean
function IsoPlayer:isPathfindRunning() end

---@private
---@return HandWeapon
function IsoPlayer:getWeapon() end

---@public
---@return Integer
function IsoPlayer:getTransactionID() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setForceAim(arg0) end

---@public
---@return boolean
function IsoPlayer:canSeePlayerStats() end

---@public
---@return boolean @the bCouldBeSeenThisFrame
function IsoPlayer:isbCouldBeSeenThisFrame() end

---@public
---@return boolean @the bChangeCharacterDebounce
function IsoPlayer:isbChangeCharacterDebounce() end

---@public
---@param MaxOscilatorRate float @the MaxOscilatorRate to set
---@return void
function IsoPlayer:setMaxOscilatorRate(MaxOscilatorRate) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setForceSprint(arg0) end

---@public
---@param bCouldBeSeenThisFrame boolean @the bCouldBeSeenThisFrame to set
---@return void
function IsoPlayer:setbCouldBeSeenThisFrame(bCouldBeSeenThisFrame) end

---@public
---@param arg0 Runnable
---@return void
function IsoPlayer:invokeOnPlayerInstance(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoPlayer:addWorldSoundUnlessInvisible(arg0, arg1, arg2) end

---@public
---@param arg0 String
---@return Long
function IsoPlayer:getMechanicsItem(arg0) end

---@public
---@return boolean
function IsoPlayer:isBannedAttacking() end

---@private
---@return void
function IsoPlayer:updateFootInjuries() end

---@private
---@param arg0 IsoDirections
---@return boolean
function IsoPlayer:doContextNSWE(arg0) end

---@private
---@return void
function IsoPlayer:enterExitVehicle() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setCanHearAll(arg0) end

---@public
---@return boolean
function IsoPlayer:isDoingActionThatCanBeCancelled() end

---@public
---@return void
function IsoPlayer:updateMovementRates() end

---@public
---@return void
function IsoPlayer:calculateContext() end

---@public
---@return Stack|String @the StaticTraits
function IsoPlayer:getStaticTraits() end

---@public
---@return boolean
function IsoPlayer:isAimControlActive() end

---@private
---@return void
function IsoPlayer:highlightRangedTargets() end

---@public
---@param chr IsoMovingObject
---@return void
function IsoPlayer:TestZombieSpotPlayer(chr) end

---@public
---@param aFollowDeadCount int @the FollowDeadCount to set
---@return void
function IsoPlayer:setFollowDeadCount(aFollowDeadCount) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setCanSeeAll(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setIgnoreContextKey(arg0) end

---@public
---@return boolean
function IsoPlayer:toggleForceSprint() end

---@private
---@param arg0 JVector2
---@return JVector2
function IsoPlayer:getJoypadAimVector(arg0) end

---@private
---@return void
function IsoPlayer:checkActionGroup() end

---@private
---@return void
function IsoPlayer:updateSneakKey() end

---@public
---@param DragCharacter IsoSurvivor @the DragCharacter to set
---@return void
function IsoPlayer:setDragCharacter(DragCharacter) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setAllowRun(arg0) end

---@private
---@return void
function IsoPlayer:updateSoundListener() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setBlockMovement(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setForceOverrideAnim(arg0) end

---@public
---@return boolean
function IsoPlayer:isCanHearAll() end

---@public
---@return int @the DialogMood
function IsoPlayer:getDialogMood() end

---@private
---@return void
function IsoPlayer:updateMechanicsItems() end

---@public
---@return float
function IsoPlayer:getTimeSinceLastStab() end

---@public
---@return Boolean
function IsoPlayer:isNearVehicle() end

---@public
---@param DrunkOscilatorRateCos float @the DrunkOscilatorRateCos to set
---@return void
function IsoPlayer:setDrunkOscilatorRateCos(DrunkOscilatorRateCos) end

---@private
---@return void
function IsoPlayer:updateSleepingPillsTaken() end

---@public
---@return float @the DrunkOscilatorStepSin
function IsoPlayer:getDrunkOscilatorStepSin() end

---Overrides:
---
---isOutside in class IsoGameCharacter
---@public
---@return boolean
function IsoPlayer:isOutside() end

---@public
---@param arg0 JVector2
---@return JVector2
function IsoPlayer:getAimVector(arg0) end

---@private
---@param arg0 String
---@return void
function IsoPlayer:onTrigger_ResetIsoPlayerModel(arg0) end

---@public
---@param hrs double
---@return void
function IsoPlayer:setHoursSurvived(hrs) end

---@private
---@return void
function IsoPlayer:updateHeavyBreathing() end

---@public
---@return String
function IsoPlayer:getUniqueFileName() end

---@public
---@param arg0 AnimLayer
---@param arg1 AnimEvent
---@return void
function IsoPlayer:OnAnimEvent(arg0, arg1) end

---If you've take more than 10 sleeping pills you lose some health
---
---If you're drunk, 1 pills = 2
---@public
---@param sleepingPillsTaken int
---@return void
function IsoPlayer:setSleepingPillsTaken(sleepingPillsTaken) end

---@public
---@return float @the DrunkSin
function IsoPlayer:getDrunkSin() end

---@public
---@return float @the maxWeightDelta
function IsoPlayer:getMaxWeightDelta() end

---@public
---@return float @the DrunkCos2
function IsoPlayer:getDrunkCos2() end

---@public
---@return boolean
function IsoPlayer:isSeeNonPvpZone() end

---@public
---@return boolean
function IsoPlayer:hasInstance() end

---@public
---@param arg0 IsoPlayer
---@return boolean
function IsoPlayer:checkCanSeeClient(arg0) end

---@public
---@return String
---@overload fun(arg0:Boolean)
function IsoPlayer:getUsername() end

---@public
---@param arg0 Boolean
---@return String
function IsoPlayer:getUsername(arg0) end

---@public
---@return int @the TicksSinceSeenZombie
function IsoPlayer:getTicksSinceSeenZombie() end

---@public
---@param Forname String @the Forname to set
---@return void
function IsoPlayer:setForname(Forname) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return void
function IsoPlayer:setPosition(arg0, arg1, arg2) end

---@public
---@return float @the heartDelayMax
function IsoPlayer:getHeartDelayMax() end

---@public
---@param arg0 JVector2
---@return JVector2
function IsoPlayer:getMouseAimVector(arg0) end

---@public
---@return float @the DrunkOscilatorStepCos2
function IsoPlayer:getDrunkOscilatorStepCos2() end

---@public
---@return ArrayList|String
function IsoPlayer:getAllFileNames() end

---@public
---@return boolean
function IsoPlayer:isAllChatMuted() end

---@public
---@param arg0 IsoDirections
---@return boolean
function IsoPlayer:climbOverWall(arg0) end

---@public
---@param TicksSinceSeenZombie int @the TicksSinceSeenZombie to set
---@return void
function IsoPlayer:setTicksSinceSeenZombie(TicksSinceSeenZombie) end

---@public
---@param aGhostMode boolean @the GhostMode to set
---@return void
function IsoPlayer:setGhostMode(aGhostMode) end

---@public
---@return void
function IsoPlayer:removeSaveFile() end

---@public
---@param arg0 ColorInfo
---@return void
function IsoPlayer:setTagColor(arg0) end

---@public
---@return void
function IsoPlayer:InitSpriteParts() end

---@public
---@return boolean
function IsoPlayer:isPerformingAnAction() end

---@private
---@return void
function IsoPlayer:updateDeathDragDown() end

---@public
---@param vec JVector2
---@return JVector2
function IsoPlayer:getControllerAimDir(vec) end

---@public
---@param DesiredSinRate float @the DesiredSinRate to set
---@return void
function IsoPlayer:setDesiredSinRate(DesiredSinRate) end

---Overrides:
---
---getPathSpeed in class IsoGameCharacter
---@public
---@return float @the PathSpeed
function IsoPlayer:getPathSpeed() end

---@public
---@param DialogMood int @the DialogMood to set
---@return void
function IsoPlayer:setDialogMood(DialogMood) end

---@public
---@return boolean
function IsoPlayer:isForceSprint() end

---@public
---@param Waiting boolean @the Waiting to set
---@return void
function IsoPlayer:setWaiting(Waiting) end

---@public
---@return boolean
function IsoPlayer:pressedAim() end

---Overrides:
---
---Scratched in class IsoGameCharacter
---@public
---@return void
function IsoPlayer:Scratched() end

---@public
---@return Stack|IsoMovingObject @the spottedList
function IsoPlayer:getSpottedList() end

---@public
---@return boolean
function IsoPlayer:isPushableForSeparate() end

---@private
---@return void
function IsoPlayer:registerVariableCallbacks() end

---@public
---@return boolean
function IsoPlayer:isForceOverrideAnim() end

---@public
---@return boolean
function IsoPlayer:toggleForceRun() end

---Overrides:
---
---Bitten in class IsoGameCharacter
---@public
---@return void
function IsoPlayer:Bitten() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setForceRun(arg0) end

---@public
---@param arg0 IsoPlayer
---@return void
function IsoPlayer:stopReceivingBodyDamageUpdates(arg0) end

---@private
---@return boolean
function IsoPlayer:updateUseKey() end

---@public
---@param arg0 IsoDirections
---@return IsoObject
function IsoPlayer:getContextDoorOrWindowOrWindowFrame(arg0) end

---@public
---@return int
function IsoPlayer:getSleepingPillsTaken() end

---@public
---@return double
function IsoPlayer:getHoursSurvived() end

---@public
---@return int @the ClearSpottedTimer
function IsoPlayer:getClearSpottedTimer() end

---@public
---@return void
function IsoPlayer:clearNetworkEvents() end

---@private
---@return void
function IsoPlayer:onIdlePerformFidgets() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setSeeNonPvpZone(arg0) end

---@public
---@return float @the MaxOscilatorRate
function IsoPlayer:getMaxOscilatorRate() end

---@public
---@return boolean
function IsoPlayer:isDeaf() end

---@public
---@return Nutrition
function IsoPlayer:getNutrition() end

---@public
---@return boolean
function IsoPlayer:isTimedActionInstant() end

---@public
---@return boolean
function IsoPlayer:isPlayerMoving() end

---@public
---@return long
function IsoPlayer:getSteamID() end

---@private
---@return void
function IsoPlayer:updateChangeCharacterKey() end

---@private
---@param arg0 IsoPlayer
---@return boolean
function IsoPlayer:checkSpottedPLayerTimer(arg0) end

---@protected
---@return void
function IsoPlayer:calculateStats() end

---@public
---@param arg0 IsoZombie
---@return float
function IsoPlayer:getZombieRelevenceScore(arg0) end

---@public
---@return ColorInfo
function IsoPlayer:getTagColor() end

---@public
---@return boolean
function IsoPlayer:getCoopPVP() end

---@private
---@return void
function IsoPlayer:checkReloading() end

---@public
---@return double
function IsoPlayer:getLastSeenZomboidTime() end

---@public
---@return int @the FollowDeadCount
function IsoPlayer:getFollowDeadCount() end

---@public
---@return boolean
function IsoPlayer:isAllowSprint() end

---@public
---@param arg0 boolean
---@return boolean
function IsoPlayer:isInTrees2(arg0) end

---@public
---@return boolean
function IsoPlayer:isLBPressed() end

---@public
---@param arg0 IsoDirections
---@return boolean
---@overload fun(arg0:IsoDirections, arg1:boolean)
function IsoPlayer:doContext(arg0) end

---@public
---@param arg0 IsoDirections
---@param arg1 boolean
---@return boolean
function IsoPlayer:doContext(arg0, arg1) end

---@public
---@return ItemVisuals
---@overload fun(arg0:ItemVisuals)
function IsoPlayer:getItemVisuals() end

---@public
---@param arg0 ItemVisuals
---@return void
function IsoPlayer:getItemVisuals(arg0) end

---@public
---@param arg0 String
---@param arg1 VehiclePart
---@param arg2 Long
---@return void
function IsoPlayer:addMechanicsItem(arg0, arg1, arg2) end

---@public
---@param DrunkOscilatorStepCos float @the DrunkOscilatorStepCos to set
---@return void
function IsoPlayer:setDrunkOscilatorStepCos(DrunkOscilatorStepCos) end

---@public
---@param arg0 float
---@return void
function IsoPlayer:setTimeSinceLastStab(arg0) end

---@public
---@return Stack|IsoMovingObject @the LastSpotted
function IsoPlayer:getLastSpotted() end

---@protected
---@return void
function IsoPlayer:updateStats_Sleeping() end

---@public
---@return boolean
function IsoPlayer:isAuthorizeShoveStomp() end

---@public
---@param enabled boolean
---@return void
function IsoPlayer:setCoopPVP(enabled) end

---@public
---@return void
---@overload fun(arg0:String)
function IsoPlayer:pressedAttack() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:pressedAttack(arg0) end

---@public
---@return float @the DrunkOscilatorStepCos
function IsoPlayer:getDrunkOscilatorStepCos() end

---@public
---@return JVector2 @the lastAngle
function IsoPlayer:getLastAngle() end

---@private
---@return void
function IsoPlayer:setAngleFromAim() end

---@public
---@return float @the DrunkOscilatorRateCos
function IsoPlayer:getDrunkOscilatorRateCos() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:setDisplayName(arg0) end

---@public
---@param aInstance IsoPlayer @the instance to set
---@return void
function IsoPlayer:setInstance(aInstance) end

---Overrides:
---
---update in class IsoGameCharacter
---@public
---@return void
function IsoPlayer:update() end

---@private
---@param arg0 JVector2
---@return JVector2
function IsoPlayer:getJoypadMoveVector(arg0) end

---@public
---@param arg0 boolean
---@return float
function IsoPlayer:getGlobalMovementMod(arg0) end

---@public
---@return IsoGameCharacter
function IsoPlayer:getClosestZombieDist() end

---@public
---@param arg0 IsoPlayer
---@return void
function IsoPlayer:startReceivingBodyDamageUpdates(arg0) end

---@public
---@param heartDelayMax int @the heartDelayMax to set
---@return void
function IsoPlayer:setHeartDelayMax(heartDelayMax) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setM_bKnockedDown(arg0) end

---@public
---@return BaseVehicle
function IsoPlayer:getUseableVehicle() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:setUsername(arg0) end

---@public
---@return float @the OscilatorChangeRate
function IsoPlayer:getOscilatorChangeRate() end

---@public
---@param offSetYUI int
---@return void
function IsoPlayer:setOffSetYUI(offSetYUI) end

---@public
---@param arg0 int
---@param arg1 IsoPlayer
---@return void
function IsoPlayer:setLocalPlayer(arg0, arg1) end

---@public
---@return JVector2 @the playerMoveDir
function IsoPlayer:getPlayerMoveDir() end

---@public
---@param arg0 String
---@return boolean
function IsoPlayer:isServerPlayerIDValid(arg0) end

---Overrides:
---
---isMaskClicked in class IsoGameCharacter
---@public
---@param x int
---@param y int
---@param flip boolean
---@return boolean
function IsoPlayer:isMaskClicked(x, y, flip) end

---Return the amount of temperature given by clothes wear
---@public
---@return float @temperature
function IsoPlayer:getPlayerClothingTemperature() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setPathfindRunning(arg0) end

---@private
---@return void
function IsoPlayer:checkVehicleContainers() end

---Overrides:
---
---OnDeath in class IsoGameCharacter
---@public
---@return void
function IsoPlayer:OnDeath() end

---@public
---@param DrunkOscilatorRateCos2 float @the DrunkOscilatorRateCos2 to set
---@return void
function IsoPlayer:setDrunkOscilatorRateCos2(DrunkOscilatorRateCos2) end

---@public
---@return String
function IsoPlayer:getAccessLevel() end

---@public
---@param MinOscilatorRate float @the MinOscilatorRate to set
---@return void
function IsoPlayer:setMinOscilatorRate(MinOscilatorRate) end

---@public
---@param lastAngle JVector2 @the lastAngle to set
---@return void
function IsoPlayer:setLastAngle(lastAngle) end

---@public
---@return HumanVisual
function IsoPlayer:getHumanVisual() end

---@public
---@return float
function IsoPlayer:getTorchDot() end

---@public
---@return boolean
function IsoPlayer:isAimKeyDown() end

---@public
---@return ArrayList|IsoPlayer
function IsoPlayer:getAllSavedPlayers() end

---@private
---@return void
function IsoPlayer:updateWhileInVehicle() end

---@public
---@param arg0 IsoMovingObject
---@return boolean
function IsoPlayer:isPushedByForSeparate(arg0) end

---@public
---@param DragObject IsoMovingObject @the DragObject to set
---@return void
function IsoPlayer:setDragObject(DragObject) end

---@public
---@return ActionContext
function IsoPlayer:getActionContext() end

---@public
---@param arg0 IsoDirections
---@return boolean
function IsoPlayer:isSafeToClimbOver(arg0) end

---@public
---@return boolean
function IsoPlayer:isSaveFileInUse() end

---@public
---@param DrunkOscilatorRateSin float @the DrunkOscilatorRateSin to set
---@return void
function IsoPlayer:setDrunkOscilatorRateSin(DrunkOscilatorRateSin) end

---@public
---@param aPlayerMoveDir JVector2 @the playerMoveDir to set
---@return void
function IsoPlayer:setPlayerMoveDir(aPlayerMoveDir) end

---@public
---@return boolean
function IsoPlayer:IsRunning() end

---@public
---@return boolean
function IsoPlayer:isTargetedByZombie() end

---@public
---@param b boolean
---@return void
function IsoPlayer:setBannedAttacking(b) end

---@public
---@param arg0 IsoZombie
---@return IsoZombie
function IsoPlayer:getClosestZombieToOtherZombie(arg0) end

---@public
---@return boolean
function IsoPlayer:isSaveFileIPValid() end

---@public
---@param ping int @the ping to set
---@return void
function IsoPlayer:setPing(ping) end

---@public
---@return boolean
function IsoPlayer:isForceAim() end

---@public
---@param OscilatorChangeRate float @the OscilatorChangeRate to set
---@return void
function IsoPlayer:setOscilatorChangeRate(OscilatorChangeRate) end

---@public
---@return float
function IsoPlayer:getLightDistance() end

---@private
---@return void
function IsoPlayer:updateEnableModelsKey() end

---@public
---@return boolean
function IsoPlayer:isIgnoreInputsForDirection() end

---@private
---@return boolean
function IsoPlayer:updateRemotePlayer() end

---@private
---@return void
function IsoPlayer:updateHeartSound() end

---@private
---@return void
function IsoPlayer:doServerFootstepSound() end

---@public
---@return String
function IsoPlayer:getTagPrefix() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:setTagPrefix(arg0) end

---@public
---@return IsoPlayer @the instance
function IsoPlayer:getInstance() end

---@public
---@return boolean
function IsoPlayer:isIgnoreContextKey() end

---@private
---@return void
function IsoPlayer:updateInternal1() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setAuthorizeShoveStomp(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setInitiateAttack(arg0) end

---@public
---@return boolean
function IsoPlayer:toggleForceAim() end

---@public
---@return void
function IsoPlayer:updateLOS() end

---@private
---@return void
function IsoPlayer:updateGodModeKey() end

---@public
---@return boolean
function IsoPlayer:IsUsingAimWeapon() end

---@public
---@param Surname String @the Surname to set
---@return void
function IsoPlayer:setSurname(Surname) end

---@public
---@param arg0 ModelManager
---@param arg1 boolean
---@return void
function IsoPlayer:onCullStateChanged(arg0, arg1) end

---@private
---@param arg0 IsoPlayer.MoveVars
---@return void
function IsoPlayer:updateMovementFromJoypad(arg0) end

---@public
---@return boolean @the Waiting
function IsoPlayer:isWaiting() end

---@public
---@return BaseVehicle
function IsoPlayer:getNearVehicle() end

---@public
---@return float
function IsoPlayer:getSeeNearbyCharacterDistance() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:dressInClothingItem(arg0) end

---@private
---@return boolean
function IsoPlayer:updateInternal2() end

---@public
---@return int
function IsoPlayer:getOffSetXUI() end

---@public
---@return void
function IsoPlayer:renderlast() end

---@private
---@param arg0 float
---@return void
function IsoPlayer:updateEndurance(arg0) end

---@public
---@return boolean
function IsoPlayer:isOnlyPlayerAsleep() end

---@public
---@param offSetXUI int
---@return void
function IsoPlayer:setOffSetXUI(offSetXUI) end

---@public
---@return float @the DesiredSinRate
function IsoPlayer:getDesiredSinRate() end

---@private
---@param arg0 IsoPlayer.InputState
---@return void
function IsoPlayer:UpdateInputState(arg0) end

---@public
---@param maxWeightDelta float @the maxWeightDelta to set
---@return void
function IsoPlayer:setMaxWeightDelta(maxWeightDelta) end

---@public
---@return boolean
function IsoPlayer:allPlayersDead() end

---@public
---@return boolean
function IsoPlayer:isAttacking() end

---@public
---@return boolean
function IsoPlayer:isBlockMovement() end

---@public
---@return String
function IsoPlayer:getAttackType() end

---@public
---@param arg0 Integer
---@return void
function IsoPlayer:setTransactionID(arg0) end

---Overrides:
---
---getCell in class IsoObject
---@public
---@return IsoCell @the cell
function IsoPlayer:getCell() end

---@public
---@return float @the DrunkOscilatorRateCos2
function IsoPlayer:getDrunkOscilatorRateCos2() end

---@private
---@param arg0 IsoPlayer.MoveVars
---@return void
function IsoPlayer:updateMovementFromKeyboardMouse(arg0) end

---@public
---@return boolean
function IsoPlayer:isWearingNightVisionGoggles() end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoPlayer:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@param DrunkCos2 float @the DrunkCos2 to set
---@return void
function IsoPlayer:setDrunkCos2(DrunkCos2) end

---@public
---@return boolean
function IsoPlayer:isFactionPvp() end

---@public
---@param DrunkSin float @the DrunkSin to set
---@return void
function IsoPlayer:setDrunkSin(DrunkSin) end

---@public
---@return float @the DrunkCos
function IsoPlayer:getDrunkCos() end

---@public
---@return BaseVisual
function IsoPlayer:getVisual() end

---@public
---@return InventoryItem
function IsoPlayer:getActiveLightItem() end

---@public
---@return Fitness
function IsoPlayer:getFitness() end

---@public
---@return boolean
function IsoPlayer:IsInMeleeAttack() end

---throws java.io.FileNotFoundException, java.io.IOException
---@public
---@param fileName String
---@return void
---@overload fun(arg0:ByteBuffer, arg1:int, arg2:boolean)
function IsoPlayer:load(fileName) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoPlayer:load(arg0, arg1, arg2) end

---@public
---@return float @the MinOscilatorRate
function IsoPlayer:getMinOscilatorRate() end

---@public
---@return boolean
function IsoPlayer:isLocalPlayer() end

---@public
---@return boolean
function IsoPlayer:isShowTag() end

---@private
---@return boolean
function IsoPlayer:updateWhileDead() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setShowTag(arg0) end

---@public
---@param DrunkOscilatorStepSin float @the DrunkOscilatorStepSin to set
---@return void
function IsoPlayer:setDrunkOscilatorStepSin(DrunkOscilatorStepSin) end

---@public
---@return float
function IsoPlayer:getAimingRangeMod() end

---@private
---@param arg0 IsoDirections
---@param arg1 IsoObject
---@return void
function IsoPlayer:doContextDoorOrWindowOrWindowFrame(arg0, arg1) end

---@private
---@return void
function IsoPlayer:updateAimingStance() end

---@public
---@param bChangeCharacterDebounce boolean @the bChangeCharacterDebounce to set
---@return void
function IsoPlayer:setbChangeCharacterDebounce(bChangeCharacterDebounce) end

---@public
---@return IsoMovingObject @the DragObject
function IsoPlayer:getDragObject() end

---@public
---@return boolean
function IsoPlayer:isAuthorizeMeleeAction() end

---@private
---@return boolean
function IsoPlayer:checkActionsBlockingMovement() end

---@public
---@return boolean
function IsoPlayer:isSeeEveryone() end

---@public
---@return String
function IsoPlayer:GetAnimSetName() end

---@public
---@return void
function IsoPlayer:nullifyAiming() end

---@public
---@return int
function IsoPlayer:getPlayerNum() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setIgnoreInputsForDirection(arg0) end

---@private
---@return void
function IsoPlayer:updateHitByVehicle() end

---@public
---@return float
function IsoPlayer:getReloadingMod() end

---@public
---@return boolean
function IsoPlayer:isAllowRun() end

---@private
---@param arg0 IsoPlayer.MoveVars
---@return void
function IsoPlayer:updateMovementFromInput(arg0) end

---Overrides:
---
---getTorchStrength in class IsoGameCharacter
---@public
---@return float
function IsoPlayer:getTorchStrength() end

---@public
---@return float
function IsoPlayer:getPlayerClothingInsulation() end

---@private
---@return void
function IsoPlayer:updateToggleToAim() end

---@public
---@return int
function IsoPlayer:getOffSetYUI() end

---@public
---@param arg0 long
---@return void
function IsoPlayer:setSteamID(arg0) end

---@public
---@param arg0 ByteBufferWriter
---@param arg1 String
---@return ByteBufferWriter
function IsoPlayer:createPlayerStats(arg0, arg1) end

---@public
---@param arg0 String
---@return void
function IsoPlayer:setAttackType(arg0) end

---@public
---@return boolean
function IsoPlayer:isNoClip() end

---@public
---@return boolean
function IsoPlayer:isCanSeeAll() end

---@public
---@param heartDelay float @the heartDelay to set
---@return void
function IsoPlayer:setHeartDelay(heartDelay) end

---@private
---@return void
function IsoPlayer:initializeStates() end

---@private
---@return void
function IsoPlayer:updateExt() end

---@public
---@return int @the followID
function IsoPlayer:getFollowID() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setPerformingAnAction(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setAllChatMuted(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setFactionPvp(arg0) end

---@public
---@return boolean
function IsoPlayer:isForceRun() end

---@public
---@return float
function IsoPlayer:getTurnDelta() end

---@public
---@param arg0 IsoGameCharacter
---@return int
function IsoPlayer:calculateCritChance(arg0) end

---@private
---@return boolean
function IsoPlayer:DoAimAnimOnAiming() end

---@public
---@param followID int @the followID to set
---@return void
function IsoPlayer:setFollowID(followID) end

---@public
---@return int
function IsoPlayer:getPlayerIndex() end

---@public
---@param ClearSpottedTimer int @the ClearSpottedTimer to set
---@return void
function IsoPlayer:setClearSpottedTimer(ClearSpottedTimer) end

---@public
---@return boolean
function IsoPlayer:pressedCancelAction() end

---Overrides:
---
---getObjectName in class IsoMovingObject
---@public
---@return String
function IsoPlayer:getObjectName() end

---@public
---@param LastSpotted Stack|IsoMovingObject @the LastSpotted to set
---@return void
function IsoPlayer:setLastSpotted(LastSpotted) end

---@private
---@return void
function IsoPlayer:onClothingOutfitPreviewChanged() end

---@public
---@return float @the heartDelay
function IsoPlayer:getHeartDelay() end

---Overrides:
---
---getMoveSpeed in class IsoGameCharacter
---@public
---@return float
function IsoPlayer:getMoveSpeed() end

---@private
---@return void
function IsoPlayer:highlightRangedTargetsInternal() end

---@public
---@return float
function IsoPlayer:getTimedActionTimeModifier() end

---@public
---@return boolean
function IsoPlayer:isBehaviourMoving() end

---@public
---@return float
function IsoPlayer:getInvAimingRangeMod() end

---@public
---@return String @the Forname
function IsoPlayer:getForname() end

---@public
---@return int @the ping
function IsoPlayer:getPing() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:dressInNamedOutfit(arg0) end

---@public
---@return boolean
function IsoPlayer:isTorchCone() end

---@public
---@return int
function IsoPlayer:getJoypadBind() end

---@public
---@param DrunkOscilatorStepCos2 float @the DrunkOscilatorStepCos2 to set
---@return void
function IsoPlayer:setDrunkOscilatorStepCos2(DrunkOscilatorStepCos2) end

---@public
---@return boolean
function IsoPlayer:AttemptAttack() end

---@private
---@return void
function IsoPlayer:postupdateInternal() end

---@private
---@return void
function IsoPlayer:updateTorchStrength() end

---@public
---@param arg0 String
---@return void
function IsoPlayer:setAccessLevel(arg0) end

---@public
---@param arg0 HandWeapon
---@param arg1 IsoGameCharacter
---@param arg2 boolean
---@param arg3 float
---@param arg4 boolean
---@return void
function IsoPlayer:hitConsequences(arg0, arg1, arg2, arg3, arg4) end

---@public
---@param DrunkCos float @the DrunkCos to set
---@return void
function IsoPlayer:setDrunkCos(DrunkCos) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setJoypadIgnoreAimUntilCentered(arg0) end

---@public
---@return boolean
function IsoPlayer:isIgnoreAutoVault() end

---@protected
---@param arg0 AnimationPlayer
---@return void
function IsoPlayer:onAnimPlayerCreated(arg0) end

---@public
---@return String @the Surname
function IsoPlayer:getSurname() end

---@public
---@return boolean @the GhostMode
function IsoPlayer:isGhostMode() end

---@private
---@return void
function IsoPlayer:updateTemperatureCheck() end

---@public
---@return String
function IsoPlayer:getTimeSurvived() end

---@public
---@param arg0 BaseVehicle
---@return void
function IsoPlayer:setVehicle4TestCollision(arg0) end

---@public
---@return boolean
function IsoPlayer:allPlayersAsleep() end

---@public
---@return float @the DesiredCosRate
function IsoPlayer:getDesiredCosRate() end

---@public
---@return void
---@overload fun(fileName:String)
---@overload fun(arg0:ByteBuffer, arg1:boolean)
function IsoPlayer:save() end

---throws java.io.IOException
---@public
---@param fileName String
---@return void
function IsoPlayer:save(fileName) end

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function IsoPlayer:save(arg0, arg1) end

---@public
---@return boolean
function IsoPlayer:isLookingWhileInVehicle() end

---@public
---@return boolean
function IsoPlayer:isSolidForSeparate() end

---@public
---@param arg0 boolean
---@return boolean
function IsoPlayer:pressedMovement(arg0) end

---@public
---@return String
function IsoPlayer:getDisplayName() end

---@public
---@param arg0 JVector2
---@return JVector2
function IsoPlayer:getDeferredMovement(arg0) end

---@public
---@return boolean @the bSeenThisFrame
function IsoPlayer:isbSeenThisFrame() end

---@public
---@param enabled boolean
---@return void
function IsoPlayer:setAuthorizeMeleeAction(enabled) end

---@public
---@return float
function IsoPlayer:getAimingMod() end

---@private
---@return void
function IsoPlayer:checkJoypadIgnoreAimUntilCentered() end

---@private
---@return boolean
function IsoPlayer:IsUsingAimHandWeapon() end

---@public
---@param AsleepTime float @the AsleepTime to set
---@return void
function IsoPlayer:setAsleepTime(AsleepTime) end

---@public
---@return boolean
function IsoPlayer:shouldBeTurning() end

---Overrides:
---
---DoAttack in class IsoLivingCharacter
---@public
---@param chargeDelta float
---@return boolean
---@overload fun(chargeDelta:float, forceShove:boolean, clickSound:String)
function IsoPlayer:DoAttack(chargeDelta) end

---@public
---@param chargeDelta float
---@param forceShove boolean
---@param clickSound String
---@return boolean
function IsoPlayer:DoAttack(chargeDelta, forceShove, clickSound) end

---@public
---@return IsoSurvivor @the DragCharacter
function IsoPlayer:getDragCharacter() end

---@public
---@return boolean
function IsoPlayer:IsAiming() end

---@public
---@return boolean
function IsoPlayer:isAttackStarted() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setIgnoreAutoVault(arg0) end

---@public
---@return void
function IsoPlayer:CalculateAim() end

---@public
---@param moveSpeed float
---@return void
function IsoPlayer:setMoveSpeed(moveSpeed) end

---@public
---@return float @the DrunkOscilatorRateSin
function IsoPlayer:getDrunkOscilatorRateSin() end

---@public
---@return float @the AsleepTime
function IsoPlayer:getAsleepTime() end

---@public
---@return int
function IsoPlayer:getOnlineID() end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setWearingNightVisionGoggles(arg0) end

---@public
---@return ArrayList|IsoPlayer
function IsoPlayer:getPlayers() end

---@public
---@param arg0 MoodleType
---@return int
function IsoPlayer:getMoodleLevel(arg0) end

---@public
---@param dir IsoDirections
---@param bTest boolean
---@return boolean
function IsoPlayer:hopFence(dir, bTest) end

---@public
---@param arg0 IsoDirections
---@return boolean
function IsoPlayer:canClimbOverWall(arg0) end

---@public
---@param DesiredCosRate float @the DesiredCosRate to set
---@return void
function IsoPlayer:setDesiredCosRate(DesiredCosRate) end

---@public
---@return void
function IsoPlayer:postupdate() end

---@public
---@param arg0 JVector2
---@return void
function IsoPlayer:MoveUnmodded(arg0) end

---@private
---@return void
function IsoPlayer:updateInteractKeyPanic() end

---@public
---@param bSeenThisFrame boolean @the bSeenThisFrame to set
---@return void
function IsoPlayer:setbSeenThisFrame(bSeenThisFrame) end

---@public
---@return float
function IsoPlayer:getInvAimingMod() end

---@public
---@param noClip boolean
---@return void
function IsoPlayer:setNoClip(noClip) end

---@private
---@return void
function IsoPlayer:attackWhileInVehicle() end

---@public
---@return boolean
function IsoPlayer:isSkeleton() end

---@public
---@param arg0 ActionContext
---@return void
function IsoPlayer:actionStateChanged(arg0) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 boolean
---@return void
function IsoPlayer:doBeatenVehicle(arg0, arg1, arg2, arg3) end

---@public
---@return void
function IsoPlayer:updateUsername() end

---@public
---@param arg0 ByteBuffer
---@param arg1 String
---@return String
function IsoPlayer:setPlayerStats(arg0, arg1) end

---@public
---@param arg0 boolean
---@return void
function IsoPlayer:setAllowSprint(arg0) end
