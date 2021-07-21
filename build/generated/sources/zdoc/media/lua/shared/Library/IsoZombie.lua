---@class IsoZombie : zombie.characters.IsoZombie
---@field public NetRemoteState_Idle byte
---@field public NetRemoteState_Walk byte
---@field public NetRemoteState_Stagger byte
---@field public NetRemoteState_Lunge byte
---@field public NetRemoteState_Bite byte
---@field public NetRemoteState_WalkToward byte
---@field public NetRemoteState_StaggerBack byte
---@field public NetRemoteState_StaggerBackDie byte
---@field public SPEED_SPRINTER byte
---@field public SPEED_FAST_SHAMBLER byte
---@field public SPEED_SHAMBLER byte
---@field public SPEED_RANDOM byte
---@field private alwaysKnockedDown boolean
---@field private onlyJawStab boolean
---@field private forceEatingAnimation boolean
---@field private noTeeth boolean
---@field public zombieSoundInstance long
---@field private AllowRepathDelayMax int
---@field public SPRINTER_FIXES boolean
---@field public HurtPlayerTimer int
---@field public LastTargetSeenX int
---@field public LastTargetSeenY int
---@field public LastTargetSeenZ int
---@field public Ghost boolean
---@field public LungeTimer float
---@field public LungeSoundTime long
---@field public target IsoMovingObject
---@field public iIgnoreDirectionChange int
---@field public TimeSinceSeenFlesh float
---@field private targetSeenTime float
---@field public FollowCount int
---@field public ZombieID int
---@field private BonusSpotTime float
---@field public bStaggerBack boolean
---@field public bKnockedDown boolean
---@field private bBecomeCrawler boolean
---@field private bFakeDead boolean
---@field private bForceFakeDead boolean
---@field private bWasFakeDead boolean
---@field private bReanimate boolean
---@field public atlasTex Texture
---@field private bReanimatedPlayer boolean
---@field public bIndoorZombie boolean
---@field public thumpFlag int
---@field public thumpSent boolean
---@field public mpIdleSound boolean
---@field public nextIdleSound float
---@field public EAT_BODY_DIST float
---@field public EAT_BODY_TIME float
---@field public LUNGE_TIME float
---@field public CRAWLER_DAMAGE_DOT float
---@field public CRAWLER_DAMAGE_RANGE float
---@field private useless boolean
---@field public speedType int
---@field public group ZombieGroup
---@field public inactive boolean
---@field public strength int
---@field public cognition int
---@field private itemsToSpawnAtDeath ArrayList|Unknown
---@field public serverState String
---@field private soundReactDelay float
---@field private delayedSound IsoGameCharacter.Location
---@field private bSoundSourceRepeating boolean
---@field public soundSourceTarget Object
---@field public soundAttract float
---@field public soundAttractTimeout float
---@field private hitAngle JVector2
---@field public alerted boolean
---@field private walkType String
---@field private footstepVolume float
---@field protected sharedDesc SharedDescriptors.Descriptor
---@field public bDressInRandomOutfit boolean
---@field public pendingOutfitName String
---@field protected humanVisual HumanVisual
---@field private crawlerType int
---@field private playerAttackPosition String
---@field private eatSpeed float
---@field private sitAgainstWall boolean
---@field private CHECK_FOR_CORPSE_TIMER_MAX int
---@field private checkForCorpseTimer float
---@field public bodyToEat IsoDeadBody
---@field public eatBodyTarget IsoMovingObject
---@field private hitTime int
---@field private thumpTimer int
---@field private hitLegsWhileOnFloor boolean
---@field public collideWhileHit boolean
---@field private m_characterTextureAnimTime float
---@field private m_characterTextureAnimDuration float
---@field public lastPlayerHit int
---@field protected itemVisuals ItemVisuals
---@field private hitHeadWhileOnFloor int
---@field private vehicle4testCollision BaseVehicle
---@field public SpriteName String
---@field public PALETTE_COUNT int
---@field public vectorToTarget JVector2
---@field public AllowRepathDelay float
---@field public KeepItReal boolean
---@field public Deaf boolean
---@field private isSkeleton boolean
---@field public networkAI NetworkZombieAI
---@field private m_sharedSkeleRepo SharedSkeleAnimationRepository
---@field public palette int
---@field public attackNetworkEvents ArrayDeque|Unknown
---@field public currentAttackNetworkEvent IsoZombie.AttackNetworkEvent
---@field public AttackAnimTime int
---@field public AttackAnimTimeMax int
---@field public spottedLast IsoMovingObject
---@field public spotSoundDelay int
---@field public movex float
---@field public movey float
---@field private stepFrameLast int
---@field private networkUpdate OnceEvery
---@field public lastRemoteUpdate short
---@field public OnlineID short
---@field private tempBodies ArrayList|Unknown
---@field timeSinceRespondToSound float
---@field public walkVariantUse String
---@field public walkVariant String
---@field public bLunger boolean
---@field public bRunning boolean
---@field public bCrawling boolean
---@field private bCanCrawlUnderVehicle boolean
---@field private bCanWalk boolean
---@field public MoveDelay int
---@field public bRemote boolean
---@field private floodFill IsoZombie.FloodFill
---@field public ImmortalTutorialZombie boolean
IsoZombie = {}

---Overrides:
---
---getObjectName in class IsoMovingObject
---@public
---@return String
function IsoZombie:getObjectName() end

---@public
---@return void
function IsoZombie:RespondToSound() end

---@public
---@param arg0 IsoMovingObject
---@return boolean
function IsoZombie:isPushedByForSeparate(arg0) end

---@protected
---@param arg0 AnimationPlayer
---@return void
function IsoZombie:onAnimPlayerCreated(arg0) end

---@private
---@return void
function IsoZombie:updateCharacterTextureAnimTime() end

---@public
---@return int
function IsoZombie:getThumpTimer() end

---@public
---@param weapon HandWeapon
---@param wielder IsoZombie
---@param damageSplit float
---@param bIgnoreDamage boolean
---@param modDelta float
---@return void
function IsoZombie:HitSilence(weapon, wielder, damageSplit, bIgnoreDamage, modDelta) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setOnlyJawStab(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setHitLegsWhileOnFloor(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:knockDown(arg0) end

---@public
---@return boolean
function IsoZombie:isSitAgainstWall() end

---@public
---@param arg0 String
---@return void
function IsoZombie:dressInNamedOutfit(arg0) end

---@param arg0 BaseVehicle
---@return void
function IsoZombie:doHit(arg0) end

---@public
---@return boolean
function IsoZombie:wasFakeDead() end

---Overrides:
---
---postupdate in class IsoGameCharacter
---@public
---@return void
function IsoZombie:postupdate() end

---@public
---@param arg0 int
---@return void
function IsoZombie:setCrawlerType(arg0) end

---@public
---@return int
function IsoZombie:getCrawlerType() end

---@private
---@param arg0 HandWeapon
---@param arg1 IsoGameCharacter
---@return void
function IsoZombie:processHitDirection(arg0, arg1) end

---@private
---@return void
function IsoZombie:updateSearchForCorpse() end

---@public
---@return SharedDescriptors.Descriptor
function IsoZombie:getSharedDescriptor() end

---@public
---@param arg0 BaseVehicle
---@param arg1 float
---@param arg2 float
---@param arg3 JVector2
---@return void
---@overload fun(arg0:HandWeapon, arg1:IsoGameCharacter, arg2:float, arg3:boolean, arg4:float, arg5:boolean)
function IsoZombie:Hit(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 HandWeapon
---@param arg1 IsoGameCharacter
---@param arg2 float
---@param arg3 boolean
---@param arg4 float
---@param arg5 boolean
---@return float
function IsoZombie:Hit(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@return void
function IsoZombie:dressInRandomOutfit() end

---@public
---@return boolean
function IsoZombie:isUsingWornItems() end

---@public
---@return void
function IsoZombie:initCanCrawlUnderVehicle() end

---@public
---@return IsoMovingObject
function IsoZombie:getTarget() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setDressInRandomOutfit(arg0) end

---@public
---@param arg0 ActionContext
---@return void
function IsoZombie:actionStateChanged(arg0) end

---@public
---@return ActionContext
function IsoZombie:getActionContext() end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return void
function IsoZombie:pathToLocationF(arg0, arg1, arg2) end

---@private
---@return void
function IsoZombie:updateZombieTripping() end

---@public
---@return boolean
function IsoZombie:isPushableForSeparate() end

---@public
---@param speed float
---@param dist float
---@param temp JVector2
---@return void
function IsoZombie:getZombieWalkTowardSpeed(speed, dist, temp) end

---@private
---@return void
function IsoZombie:checkClimbThroughWindowHit() end

---@private
---@param arg0 IsoObject
---@return boolean
function IsoZombie:isOnPath(arg0) end

---@public
---@param arg0 int
---@return void
function IsoZombie:setHitHeadWhileOnFloor(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setReanimate(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setSitAgainstWall(arg0) end

---@private
---@return void
function IsoZombie:updateInternal() end

---@public
---@return void
function IsoZombie:setAsSurvivor() end

---@public
---@return boolean
function IsoZombie:isFacingTarget() end

---@public
---@return float
function IsoZombie:getTurnDelta() end

---@public
---@param arg0 float
---@return void
function IsoZombie:setTargetSeenTime(arg0) end

---@private
---@return void
function IsoZombie:registerVariableCallbacks() end

---@public
---@return HumanVisual
function IsoZombie:getHumanVisual() end

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function IsoZombie:save(arg0, arg1) end

---@public
---@return boolean
function IsoZombie:isFakeDead() end

---@private
---@return void
function IsoZombie:damageSheetRope() end

---@public
---@return ItemVisuals
---@overload fun(arg0:ItemVisuals)
function IsoZombie:getItemVisuals() end

---@public
---@param arg0 ItemVisuals
---@return void
function IsoZombie:getItemVisuals(arg0) end

---@public
---@param arg0 String
---@return void
function IsoZombie:setPlayerAttackPosition(arg0) end

---@public
---@return boolean
function IsoZombie:isUseless() end

---@public
---@return boolean
function IsoZombie:isReanimate() end

---Overrides:
---
---collideWith in class IsoMovingObject
---@public
---@param obj IsoObject
---@return void
function IsoZombie:collideWith(obj) end

---@public
---@param arg0 int
---@return int
function IsoZombie:getScreenProperY(arg0) end

---@public
---@return int
function IsoZombie:getOnlineID() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setBecomeCrawler(arg0) end

---@public
---@param arg0 int
---@return void
function IsoZombie:setHitTime(arg0) end

---@public
---@return void
function IsoZombie:addRandomBloodDirtHolesEtc() end

---@private
---@return void
function IsoZombie:checkClimbOverFenceHit() end

---@public
---@param arg0 int
---@return void
function IsoZombie:changeSpeed(arg0) end

---@public
---@return boolean
function IsoZombie:isSolidForSeparate() end

---@public
---@return float
function IsoZombie:getTargetSeenTime() end

---@private
---@param arg0 int
---@param arg1 int
---@return void
function IsoZombie:climbFenceWindowHit(arg0, arg1) end

---@public
---@param useless boolean
---@return void
function IsoZombie:setUseless(useless) end

---@public
---@return int
function IsoZombie:getSharedDescriptorID() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function IsoZombie:load(arg0, arg1, arg2) end

---@public
---@return int
function IsoZombie:getHitTime() end

---@public
---@return boolean
function IsoZombie:isAttacking() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setCanCrawlUnderVehicle(arg0) end

---@public
---@return void
function IsoZombie:renderlast() end

---@public
---@return boolean
function IsoZombie:shouldDoFenceLunge() end

---@public
---@return void
function IsoZombie:resetForReuse() end

---@public
---@return void
function IsoZombie:initializeStates() end

---@public
---@return boolean
function IsoZombie:isForceEatingAnimation() end

---@public
---@return boolean
function IsoZombie:isZombie() end

---@public
---@return boolean
function IsoZombie:isProne() end

---@public
---@return void
function IsoZombie:DoZombieStats() end

---@public
---@return boolean
function IsoZombie:isReanimatedPlayer() end

---@public
---@return float
function IsoZombie:getFootstepVolume() end

---@private
---@param arg0 IsoGameCharacter
---@return boolean
function IsoZombie:shouldBecomeCrawler(arg0) end

---@private
---@return void
function IsoZombie:postUpdateInternal() end

---@public
---@param arg0 BaseVehicle
---@return void
function IsoZombie:setVehicle4TestCollision(arg0) end

---Overrides:
---
---update in class IsoGameCharacter
---@public
---@return void
function IsoZombie:update() end

---@public
---@param arg0 IsoDeadBody
---@return void
function IsoZombie:setBodyToEat(arg0) end

---@public
---@return boolean
function IsoZombie:isTargetLocationKnown() end

---@protected
---@param arg0 float
---@param arg1 float
---@return boolean
function IsoZombie:renderTextureInsteadOfModel(arg0, arg1) end

---@public
---@param arg0 float
---@return void
function IsoZombie:addBlood(arg0) end

---Overrides:
---
---spotted in class IsoMovingObject
---@public
---@param other IsoMovingObject
---@param bForced boolean
---@return void
function IsoZombie:spotted(other, bForced) end

---@public
---@return void
function IsoZombie:Wander() end

---@public
---@return boolean
function IsoZombie:isCrawling() end

---@public
---@return JVector2
function IsoZombie:getHitAngle() end

---@protected
---@param arg0 HandWeapon
---@param arg1 IsoGameCharacter
---@return void
function IsoZombie:DoDeathSilence(arg0, arg1) end

---@public
---@return boolean
function IsoZombie:WanderFromWindow() end

---@public
---@return void
function IsoZombie:getZombieLungeSpeed() end

---@public
---@param arg0 float
---@param arg1 float
---@return boolean
function IsoZombie:isTargetInCone(arg0, arg1) end

---@public
---@return boolean
function IsoZombie:isTargetVisible() end

---@public
---@return void
function IsoZombie:clearItemsToSpawnAtDeath() end

---@public
---@return void
function IsoZombie:addRandomVisualBandages() end

---@public
---@param bForceFakeDead boolean
---@return void
function IsoZombie:setForceFakeDead(bForceFakeDead) end

---@public
---@param arg0 int
---@return void
function IsoZombie:setThumpFlag(arg0) end

---@public
---@param bFakeDead boolean
---@return void
function IsoZombie:setFakeDead(bFakeDead) end

---@public
---@return boolean
function IsoZombie:isNoTeeth() end

---@public
---@param arg0 IsoGameCharacter
---@param arg1 boolean
---@return void
function IsoZombie:checkDead(arg0, arg1) end

---@private
---@return void
function IsoZombie:updateVocalProperties() end

---@public
---@return String
function IsoZombie:getPlayerAttackPosition() end

---@public
---@return void
function IsoZombie:DoZombieInventory() end

---@public
---@return boolean
function IsoZombie:isSkeleton() end

---@public
---@param arg0 SharedDescriptors.Descriptor
---@return void
function IsoZombie:useDescriptor(arg0) end

---@public
---@param arg0 InventoryItem
---@return void
function IsoZombie:addItemToSpawnAtDeath(arg0) end

---@public
---@return boolean
---@overload fun(arg0:IsoMovingObject)
function IsoZombie:isZombieAttacking() end

---@public
---@param arg0 IsoMovingObject
---@return boolean
function IsoZombie:isZombieAttacking(arg0) end

---@public
---@return boolean
function IsoZombie:shouldGetUpFromCrawl() end

---@public
---@param reanimated boolean
---@return void
function IsoZombie:setReanimatedPlayer(reanimated) end

---@public
---@return void
function IsoZombie:addRandomVisualDamages() end

---@public
---@param arg0 JVector2
---@return void
function IsoZombie:setHitAngle(arg0) end

---@protected
---@param arg0 int
---@param arg1 float
---@param arg2 float
---@return void
function IsoZombie:updateAlpha(arg0, arg1, arg2) end

---@public
---@param arg0 float
---@return void
function IsoZombie:DoFootstepSound(arg0) end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoZombie:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@param arg0 JVector2
---@return void
function IsoZombie:MoveUnmodded(arg0) end

---@public
---@param arg0 int
---@return void
function IsoZombie:setThumpTimer(arg0) end

---@public
---@return void
---@overload fun(arg0:SurvivorDesc)
function IsoZombie:InitSpritePartsZombie() end

---@public
---@param arg0 SurvivorDesc
---@return void
function IsoZombie:InitSpritePartsZombie(arg0) end

---@public
---@param arg0 String
---@return void
function IsoZombie:setWalkType(arg0) end

---@public
---@param arg0 String
---@return void
function IsoZombie:clothingItemChanged(arg0) end

---@public
---@return boolean
function IsoZombie:isBecomeCrawler() end

---@public
---@return boolean
function IsoZombie:isCanCrawlUnderVehicle() end

---@public
---@return IsoMovingObject
function IsoZombie:getEatBodyTarget() end

---@private
---@param arg0 String
---@return void
function IsoZombie:renderTextureOverHead(arg0) end

---@private
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return void
function IsoZombie:renderAtlasTexture(arg0, arg1, arg2) end

---@public
---@param arg0 IsoGameCharacter
---@return void
function IsoZombie:pathToCharacter(arg0) end

---@public
---@return void
function IsoZombie:onMouseLeftClick() end

---@public
---@param square IsoGridSquare
---@return boolean
function IsoZombie:tryThump(square) end

---@public
---@return boolean
function IsoZombie:isAlwaysKnockedDown() end

---@public
---@return void
function IsoZombie:toggleCrawling() end

---@public
---@param arg0 String
---@return void
function IsoZombie:dressInClothingItem(arg0) end

---@public
---@param arg0 BaseVehicle
---@return boolean
function IsoZombie:testCollideWithVehicles(arg0) end

---@public
---@param arg0 HandWeapon
---@param arg1 IsoGameCharacter
---@param arg2 boolean
---@param arg3 float
---@param arg4 boolean
---@return void
function IsoZombie:hitConsequences(arg0, arg1, arg2, arg3, arg4) end

---@public
---@param arg0 BodyPartType
---@param arg1 boolean
---@return void
function IsoZombie:addVisualBandage(arg0, arg1) end

---@public
---@param arg0 int
---@return int
function IsoZombie:getScreenProperX(arg0) end

---@public
---@param arg0 IsoMovingObject
---@param arg1 boolean
---@return void
function IsoZombie:setEatBodyTarget(arg0, arg1) end

---@public
---@return String
function IsoZombie:GetAnimSetName() end

---@public
---@return BaseVisual
function IsoZombie:getVisual() end

---@public
---@return int
function IsoZombie:getHitHeadWhileOnFloor() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setSkeleton(arg0) end

---Overrides:
---
---removeFromWorld in class IsoMovingObject
---@public
---@return void
function IsoZombie:removeFromWorld() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setNoTeeth(arg0) end

---@public
---@return boolean
function IsoZombie:isOnlyJawStab() end

---@public
---@return IsoPlayer
function IsoZombie:getReanimatedPlayer() end

---@public
---@param arg0 IsoMovingObject
---@return void
function IsoZombie:setTarget(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setWasFakeDead(arg0) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:makeInactive(arg0) end

---@public
---@param arg0 int
---@return void
function IsoZombie:dressInPersistentOutfitID(arg0) end

---Overrides:
---
---preupdate in class IsoMovingObject
---@public
---@return void
function IsoZombie:preupdate() end

---@public
---@return boolean
function IsoZombie:isHitLegsWhileOnFloor() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setFemaleEtc(arg0) end

---@private
---@return void
function IsoZombie:updateEatBodyTarget() end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setCanWalk(arg0) end

---@private
---@param arg0 IsoGameCharacter
---@return boolean
function IsoZombie:shouldBecomeFakeDead(arg0) end

---@protected
---@return void
function IsoZombie:calculateStats() end

---Overrides:
---
---Move in class IsoMovingObject
---@public
---@param dir JVector2
---@return void
function IsoZombie:Move(dir) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setForceEatingAnimation(arg0) end

---@public
---@param immortal boolean
---@return void
function IsoZombie:setImmortalTutorialZombie(immortal) end

---@public
---@param arg0 boolean
---@return void
function IsoZombie:setAlwaysKnockedDown(arg0) end

---@public
---@return boolean
function IsoZombie:isCanWalk() end

---@public
---@return boolean
function IsoZombie:isForceFakeDead() end

---@public
---@param arg0 int
---@param arg1 int
---@return void
function IsoZombie:setTurnAlertedValues(arg0, arg1) end

---@public
---@param spMod float
---@return void
function IsoZombie:DoZombieSpeeds(spMod) end

---@protected
---@return int
function IsoZombie:getSandboxMemoryDuration() end
