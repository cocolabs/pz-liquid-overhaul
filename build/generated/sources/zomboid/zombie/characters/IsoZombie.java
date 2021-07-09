package zombie.characters;

import fmod.javafmod;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.PersistentOutfits;
import zombie.SandboxOptions;
import zombie.SharedDescriptors;
import zombie.SoundManager;
import zombie.VirtualZombieManager;
import zombie.WorldSoundManager;
import zombie.Lua.LuaEventManager;
import zombie.ai.State;
import zombie.ai.ZombieGroupManager;
import zombie.ai.astar.AStarPathFinder;
import zombie.ai.astar.Mover;
import zombie.ai.states.AttackState;
import zombie.ai.states.BumpedState;
import zombie.ai.states.BurntToDeath;
import zombie.ai.states.ClimbOverFenceState;
import zombie.ai.states.ClimbOverWallState;
import zombie.ai.states.ClimbThroughWindowState;
import zombie.ai.states.CrawlingZombieTurnState;
import zombie.ai.states.FakeDeadAttackState;
import zombie.ai.states.FakeDeadZombieState;
import zombie.ai.states.IdleState;
import zombie.ai.states.LungeState;
import zombie.ai.states.PathFindState;
import zombie.ai.states.PlayerHitReactionState;
import zombie.ai.states.StaggerBackState;
import zombie.ai.states.ThumpState;
import zombie.ai.states.WalkTowardState;
import zombie.ai.states.ZombieEatBodyState;
import zombie.ai.states.ZombieFaceTargetState;
import zombie.ai.states.ZombieFallDownState;
import zombie.ai.states.ZombieFallingState;
import zombie.ai.states.ZombieGetDownState;
import zombie.ai.states.ZombieGetUpFromCrawlState;
import zombie.ai.states.ZombieGetUpState;
import zombie.ai.states.ZombieHitReactionState;
import zombie.ai.states.ZombieIdleState;
import zombie.ai.states.ZombieOnGroundState;
import zombie.ai.states.ZombieReanimateState;
import zombie.ai.states.ZombieSittingState;
import zombie.ai.states.ZombieTurnAlerted;
import zombie.characterTextures.BloodBodyPartType;
import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.action.ActionContext;
import zombie.characters.action.ActionGroup;
import zombie.characters.skills.PerkFactory;
import zombie.core.Core;
import zombie.core.PerformanceSettings;
import zombie.core.Rand;
import zombie.core.math.PZMath;
import zombie.core.opengl.RenderSettings;
import zombie.core.opengl.Shader;
import zombie.core.profiling.PerformanceProfileProbe;
import zombie.core.skinnedmodel.DeadBodyAtlas;
import zombie.core.skinnedmodel.ModelManager;
import zombie.core.skinnedmodel.animation.AnimationPlayer;
import zombie.core.skinnedmodel.population.Outfit;
import zombie.core.skinnedmodel.population.OutfitManager;
import zombie.core.skinnedmodel.population.OutfitRNG;
import zombie.core.skinnedmodel.visual.BaseVisual;
import zombie.core.skinnedmodel.visual.HumanVisual;
import zombie.core.skinnedmodel.visual.IHumanVisual;
import zombie.core.skinnedmodel.visual.ItemVisual;
import zombie.core.skinnedmodel.visual.ItemVisuals;
import zombie.core.textures.ColorInfo;
import zombie.core.textures.Texture;
import zombie.core.utils.BooleanGrid;
import zombie.core.utils.OnceEvery;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.debug.DebugType;
import zombie.inventory.InventoryItem;
import zombie.inventory.types.HandWeapon;
import zombie.iso.IsoCamera;
import zombie.iso.IsoCell;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;
import zombie.iso.IsoUtils;
import zombie.iso.IsoWorld;
import zombie.iso.LosUtil;
import zombie.iso.Vector2;
import zombie.iso.SpriteDetails.IsoFlagType;
import zombie.iso.SpriteDetails.IsoObjectType;
import zombie.iso.areas.IsoBuilding;
import zombie.iso.objects.IsoDeadBody;
import zombie.iso.objects.IsoDoor;
import zombie.iso.objects.IsoFireManager;
import zombie.iso.objects.IsoThumpable;
import zombie.iso.objects.IsoWindow;
import zombie.iso.objects.IsoWindowFrame;
import zombie.iso.objects.IsoZombieGiblets;
import zombie.iso.objects.interfaces.Thumpable;
import zombie.iso.sprite.IsoAnim;
import zombie.iso.sprite.IsoSprite;
import zombie.iso.sprite.IsoSpriteInstance;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.ServerLOS;
import zombie.network.ServerMap;
import zombie.scripting.ScriptManager;
import zombie.ui.TextManager;
import zombie.ui.UIFont;
import zombie.util.StringUtils;
import zombie.util.Type;
import zombie.util.list.PZArrayUtil;
import zombie.vehicles.AttackVehicleState;
import zombie.vehicles.BaseVehicle;
import zombie.vehicles.PolygonalMap2;
import zombie.vehicles.VehiclePart;

public final class IsoZombie extends IsoGameCharacter implements IHumanVisual {
   public static final byte NetRemoteState_Idle = 1;
   public static final byte NetRemoteState_Walk = 2;
   public static final byte NetRemoteState_Stagger = 3;
   public static final byte NetRemoteState_Lunge = 4;
   public static final byte NetRemoteState_Bite = 5;
   public static final byte NetRemoteState_WalkToward = 6;
   public static final byte NetRemoteState_StaggerBack = 7;
   public static final byte NetRemoteState_StaggerBackDie = 8;
   public static final byte SPEED_SPRINTER = 1;
   public static final byte SPEED_FAST_SHAMBLER = 2;
   public static final byte SPEED_SHAMBLER = 3;
   public static final byte SPEED_RANDOM = 4;
   private boolean alwaysKnockedDown;
   private boolean onlyJawStab;
   private boolean forceEatingAnimation;
   private boolean noTeeth;
   public long zombieSoundInstance;
   public static float baseSpeed = 0.029F;
   private static int AllowRepathDelayMax = 120;
   public static final boolean SPRINTER_FIXES = true;
   public int HurtPlayerTimer;
   public int LastTargetSeenX;
   public int LastTargetSeenY;
   public int LastTargetSeenZ;
   public boolean Ghost;
   public float LungeTimer;
   public long LungeSoundTime;
   public IsoMovingObject target;
   public int iIgnoreDirectionChange;
   public float TimeSinceSeenFlesh;
   private float targetSeenTime;
   public int FollowCount;
   public float wanderSpeed;
   public int ZombieID;
   private float BonusSpotTime;
   public boolean bStaggerBack;
   public boolean bKnockedDown;
   private boolean bBecomeCrawler;
   private boolean bFakeDead;
   private boolean bForceFakeDead;
   private boolean bWasFakeDead;
   private boolean bReanimate;
   public Texture atlasTex;
   private boolean bReanimatedPlayer;
   public boolean bIndoorZombie;
   public int thumpFlag;
   public boolean thumpSent;
   public boolean mpIdleSound;
   public float nextIdleSound;
   public static final float EAT_BODY_DIST = 1.0F;
   public static final float EAT_BODY_TIME = 3600.0F;
   public static final float LUNGE_TIME = 180.0F;
   public static final float CRAWLER_DAMAGE_DOT = 0.9F;
   public static final float CRAWLER_DAMAGE_RANGE = 1.5F;
   private boolean useless;
   public int speedType;
   public ZombieGroup group;
   public boolean inactive;
   public int strength;
   public int cognition;
   private ArrayList itemsToSpawnAtDeath;
   public String serverState;
   private float soundReactDelay;
   private final IsoGameCharacter.Location delayedSound;
   private boolean bSoundSourceRepeating;
   public Object soundSourceTarget;
   public float soundAttract;
   public float soundAttractTimeout;
   private final Vector2 hitAngle;
   public boolean alerted;
   private String walkType;
   private float footstepVolume;
   protected SharedDescriptors.Descriptor sharedDesc;
   public boolean bDressInRandomOutfit;
   public String pendingOutfitName;
   protected final HumanVisual humanVisual;
   private int crawlerType;
   private String playerAttackPosition;
   private float eatSpeed;
   private boolean sitAgainstWall;
   private static final int CHECK_FOR_CORPSE_TIMER_MAX = 10000;
   private float checkForCorpseTimer;
   public IsoDeadBody bodyToEat;
   public IsoMovingObject eatBodyTarget;
   private int hitTime;
   private int thumpTimer;
   private boolean hitLegsWhileOnFloor;
   public boolean collideWhileHit;
   private float m_characterTextureAnimTime;
   private float m_characterTextureAnimDuration;
   protected final ItemVisuals itemVisuals;
   private int hitHeadWhileOnFloor;
   private BaseVehicle vehicle4testCollision;
   public String SpriteName;
   public static final int PALETTE_COUNT = 3;
   public final Vector2 vectorToTarget;
   public float AllowRepathDelay;
   public boolean KeepItReal;
   public boolean Deaf;
   private boolean isSkeleton;
   public int palette;
   public int AttackAnimTime;
   public static int AttackAnimTimeMax = 50;
   public IsoMovingObject spottedLast;
   public int spotSoundDelay;
   public float movex;
   public float movey;
   private int stepFrameLast;
   private OnceEvery networkUpdate;
   public short lastRemoteUpdate;
   public short OnlineID;
   private static final ArrayList tempBodies = new ArrayList();
   float timeSinceRespondToSound;
   public String walkVariantUse;
   public String walkVariant;
   public boolean bLunger;
   public boolean bRunning;
   public boolean bCrawling;
   private boolean bCanCrawlUnderVehicle;
   private boolean bCanWalk;
   public int MoveDelay;
   public boolean bRemote;
   private static final IsoZombie.FloodFill floodFill = new IsoZombie.FloodFill();
   public boolean ImmortalTutorialZombie;

   public String getObjectName() {
      return "Zombie";
   }

   public void setVehicle4TestCollision(BaseVehicle var1) {
      this.vehicle4testCollision = var1;
   }

   public IsoZombie(IsoCell var1) {
      this(var1, (SurvivorDesc)null, -1);
   }

   public IsoZombie(IsoCell var1, SurvivorDesc var2, int var3) {
      super(var1, 0.0F, 0.0F, 0.0F);
      this.alwaysKnockedDown = false;
      this.onlyJawStab = false;
      this.forceEatingAnimation = false;
      this.noTeeth = false;
      this.zombieSoundInstance = -1L;
      this.HurtPlayerTimer = 10;
      this.LastTargetSeenX = -1;
      this.LastTargetSeenY = -1;
      this.LastTargetSeenZ = -1;
      this.Ghost = false;
      this.LungeTimer = 0.0F;
      this.LungeSoundTime = 0L;
      this.iIgnoreDirectionChange = 0;
      this.TimeSinceSeenFlesh = 100000.0F;
      this.targetSeenTime = 0.0F;
      this.FollowCount = 0;
      this.wanderSpeed = 0.018F;
      this.ZombieID = 0;
      this.BonusSpotTime = 0.0F;
      this.bStaggerBack = false;
      this.bKnockedDown = false;
      this.bBecomeCrawler = false;
      this.bFakeDead = false;
      this.bForceFakeDead = false;
      this.bWasFakeDead = false;
      this.bReanimate = false;
      this.atlasTex = null;
      this.bReanimatedPlayer = false;
      this.bIndoorZombie = false;
      this.thumpFlag = 0;
      this.thumpSent = false;
      this.mpIdleSound = false;
      this.nextIdleSound = 0.0F;
      this.useless = false;
      this.speedType = -1;
      this.inactive = false;
      this.strength = -1;
      this.cognition = -1;
      this.itemsToSpawnAtDeath = null;
      this.serverState = "-";
      this.soundReactDelay = 0.0F;
      this.delayedSound = new IsoGameCharacter.Location(-1, -1, -1);
      this.bSoundSourceRepeating = false;
      this.soundSourceTarget = null;
      this.soundAttract = 0.0F;
      this.soundAttractTimeout = 0.0F;
      this.hitAngle = new Vector2();
      this.alerted = false;
      this.walkType = null;
      this.footstepVolume = 1.0F;
      this.bDressInRandomOutfit = false;
      this.humanVisual = new HumanVisual(this);
      this.crawlerType = 0;
      this.playerAttackPosition = null;
      this.eatSpeed = 1.0F;
      this.sitAgainstWall = false;
      this.checkForCorpseTimer = 10000.0F;
      this.bodyToEat = null;
      this.hitTime = 0;
      this.thumpTimer = 0;
      this.hitLegsWhileOnFloor = false;
      this.collideWhileHit = true;
      this.m_characterTextureAnimTime = 0.0F;
      this.m_characterTextureAnimDuration = 1.0F;
      this.itemVisuals = new ItemVisuals();
      this.hitHeadWhileOnFloor = 0;
      this.vehicle4testCollision = null;
      this.SpriteName = "BobZ";
      this.vectorToTarget = new Vector2();
      this.AllowRepathDelay = 0.0F;
      this.KeepItReal = false;
      this.Deaf = false;
      this.isSkeleton = false;
      this.palette = 0;
      this.AttackAnimTime = 50;
      this.spottedLast = null;
      this.spotSoundDelay = 0;
      this.stepFrameLast = -1;
      this.networkUpdate = new OnceEvery(1.0F);
      this.lastRemoteUpdate = 0;
      this.OnlineID = -1;
      this.timeSinceRespondToSound = 1000000.0F;
      this.walkVariantUse = null;
      this.walkVariant = "ZombieWalk";
      this.bCanCrawlUnderVehicle = true;
      this.bCanWalk = true;
      this.MoveDelay = 0;
      this.registerVariableCallbacks();
      this.Health = 1.8F + Rand.Next(0.0F, 0.3F);
      this.weight = 0.7F;
      this.dir = IsoDirections.fromIndex(Rand.Next(8));
      this.humanVisual.randomBlood();
      if (var2 != null) {
         this.descriptor = var2;
         this.palette = var3;
      } else {
         this.descriptor = SurvivorFactory.CreateSurvivor();
         this.palette = Rand.Next(3) + 1;
      }

      this.setFemale(this.descriptor.isFemale());
      this.SpriteName = this.isFemale() ? "KateZ" : "BobZ";
      if (this.palette != 1) {
         this.SpriteName = this.SpriteName + this.palette;
      }

      this.InitSpritePartsZombie();
      this.sprite.def.tintr = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.sprite.def.tintg = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.sprite.def.tintb = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.setDefaultState(ZombieIdleState.instance());
      this.setFakeDead(false);
      this.DoZombieStats();
      this.width = 0.3F;
      this.targetAlpha[IsoPlayer.getPlayerIndex()] = 0.0F;
      this.finder.maxSearchDistance = 20;
      this.alpha[IsoPlayer.getPlayerIndex()] = 0.0F;
      if (this.isFemale()) {
         this.hurtSound = "FemaleZombieHurt";
      }

      this.initializeStates();
      this.actionContext.setGroup(ActionGroup.getActionGroup("zombie"));
      this.initWornItems("Human");
      this.initAttachedItems("Human");
   }

   public void initializeStates() {
      HashMap var1 = this.getStateUpdateLookup();
      var1.clear();
      if (this.bCrawling) {
         var1.put("attack", AttackState.instance());
         var1.put("fakedead", FakeDeadZombieState.instance());
         var1.put("fakedead-attack", FakeDeadAttackState.instance());
         var1.put("getup", ZombieGetUpFromCrawlState.instance());
         var1.put("hitreaction", ZombieHitReactionState.instance());
         var1.put("hitreaction-hit", ZombieHitReactionState.instance());
         var1.put("idle", ZombieIdleState.instance());
         var1.put("onground", ZombieOnGroundState.instance());
         var1.put("pathfind", PathFindState.instance());
         var1.put("reanimate", ZombieReanimateState.instance());
         var1.put("staggerback", StaggerBackState.instance());
         var1.put("thump", ThumpState.instance());
         var1.put("turn", CrawlingZombieTurnState.instance());
         var1.put("walktoward", WalkTowardState.instance());
      } else {
         var1.put("attack", AttackState.instance());
         var1.put("attackvehicle", AttackVehicleState.instance());
         var1.put("bumped", BumpedState.instance());
         var1.put("climbfence", ClimbOverFenceState.instance());
         var1.put("climbwindow", ClimbThroughWindowState.instance());
         var1.put("eatbody", ZombieEatBodyState.instance());
         var1.put("falldown", ZombieFallDownState.instance());
         var1.put("falling", ZombieFallingState.instance());
         var1.put("face-target", ZombieFaceTargetState.instance());
         var1.put("fakedead", FakeDeadZombieState.instance());
         var1.put("fakedead-attack", FakeDeadAttackState.instance());
         var1.put("getdown", ZombieGetDownState.instance());
         var1.put("getup", ZombieGetUpState.instance());
         var1.put("hitreaction", ZombieHitReactionState.instance());
         var1.put("hitreaction-hit", ZombieHitReactionState.instance());
         var1.put("idle", ZombieIdleState.instance());
         var1.put("lunge", LungeState.instance());
         var1.put("onground", ZombieOnGroundState.instance());
         var1.put("pathfind", PathFindState.instance());
         var1.put("sitting", ZombieSittingState.instance());
         var1.put("staggerback", StaggerBackState.instance());
         var1.put("thump", ThumpState.instance());
         var1.put("turnalerted", ZombieTurnAlerted.instance());
         var1.put("walktoward", WalkTowardState.instance());
      }

   }

   private void registerVariableCallbacks() {
      this.setVariable("hitHeadType", this::getHitHeadWhileOnFloor);
      this.setVariable("battack", () -> {
         if (this.target == null) {
            return false;
         } else {
            if (this.target instanceof IsoGameCharacter) {
               if (this.target.isOnFloor() && ((IsoGameCharacter)this.target).getCurrentState() != BumpedState.instance()) {
                  this.target = null;
                  return false;
               }

               BaseVehicle var1 = ((IsoGameCharacter)this.target).getVehicle();
               if (var1 != null) {
                  return false;
               }

               if (((IsoGameCharacter)this.target).ReanimatedCorpse != null) {
                  return false;
               }

               if (((IsoGameCharacter)this.target).getStateMachine().getCurrent() == ClimbOverWallState.instance()) {
                  return false;
               }
            }

            if (this.bReanimate) {
               return false;
            } else if (Math.abs(this.target.z - this.z) >= 0.2F) {
               return false;
            } else if (this.target instanceof IsoPlayer && ((IsoPlayer)this.target).isGhostMode()) {
               return false;
            } else if (this.bFakeDead) {
               return !this.isUnderVehicle() && this.DistTo(this.target) < 1.3F;
            } else if (!this.bCrawling) {
               IsoGridSquare var5 = this.getCurrentSquare();
               IsoGridSquare var2 = this.target.getCurrentSquare();
               if (var5 != null && var5.isSomethingTo(var2)) {
                  return false;
               } else {
                  float var3 = this.bCrawling ? 1.4F : 0.72F;
                  float var4 = this.vectorToTarget.getLength();
                  return var4 <= var3;
               }
            } else {
               return !this.isUnderVehicle() && this.DistTo(this.target) < 1.3F;
            }
         }
      });
      this.setVariable("isFacingTarget", this::isFacingTarget);
      this.setVariable("targetSeenTime", this::getTargetSeenTime);
      this.setVariable("targethitreaction", () -> {
         return this.target != null ? ((IsoGameCharacter)this.target).getHitReaction() : "";
      });
      this.setVariable("battackvehicle", () -> {
         if (this.getVariableBoolean("bPathfind")) {
            return false;
         } else if (this.isMoving()) {
            return false;
         } else if (this.target == null) {
            return false;
         } else if (Math.abs(this.target.z - this.z) >= 0.8F) {
            return false;
         } else if (this.target instanceof IsoPlayer && ((IsoPlayer)this.target).isGhostMode()) {
            return false;
         } else if (!(this.target instanceof IsoGameCharacter)) {
            return false;
         } else {
            BaseVehicle var1 = ((IsoGameCharacter)this.target).getVehicle();
            return var1 != null && var1.isCharacterAdjacentTo(this);
         }
      });
      this.setVariable("bdead", this::isDead);
      this.setVariable("beatbodytarget", () -> {
         if (this.isForceEatingAnimation()) {
            return true;
         } else {
            this.updateEatBodyTarget();
            return this.getEatBodyTarget() != null;
         }
      });
      this.setVariable("bbecomecrawler", this::isBecomeCrawler, this::setBecomeCrawler);
      this.setVariable("bfakedead", () -> {
         return this.bFakeDead;
      });
      this.setVariable("bfalling", () -> {
         return this.z > 0.0F && this.fallTime > 2.0F;
      });
      this.setVariable("bhastarget", () -> {
         if (this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).ReanimatedCorpse != null) {
            this.target = null;
         }

         return this.target != null;
      });
      this.setVariable("shouldSprint", () -> {
         if (this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).ReanimatedCorpse != null) {
            this.target = null;
         }

         return this.target != null || this.soundSourceTarget != null && !(this.soundSourceTarget instanceof IsoZombie);
      });
      this.setVariable("bknockeddown", () -> {
         return this.bKnockedDown;
      });
      this.setVariable("blunge", () -> {
         if (this.target == null) {
            return false;
         } else if ((int)this.getZ() != (int)this.target.getZ()) {
            return false;
         } else {
            if (this.target instanceof IsoGameCharacter) {
               if (((IsoGameCharacter)this.target).getVehicle() != null) {
                  return false;
               }

               if (((IsoGameCharacter)this.target).ReanimatedCorpse != null) {
                  return false;
               }
            }

            if (this.target instanceof IsoPlayer && ((IsoPlayer)this.target).isGhostMode()) {
               this.target = null;
               return false;
            } else {
               IsoGridSquare var1 = this.getCurrentSquare();
               IsoGridSquare var2 = this.target.getCurrentSquare();
               if (var2 != null && var2.isSomethingTo(var1) && this.getThumpTarget() != null) {
                  return false;
               } else if (this.isCurrentState(ZombieTurnAlerted.instance()) && !this.isFacingTarget()) {
                  return false;
               } else {
                  float var3 = this.vectorToTarget.getLength();
                  return var3 > 3.5F && (!(var3 <= 4.0F) || !(this.target instanceof IsoGameCharacter) || ((IsoGameCharacter)this.target).getVehicle() == null) ? false : !PolygonalMap2.instance.lineClearCollide(this.getX(), this.getY(), this.target.x, this.target.y, (int)this.getZ(), this.target, false, true);
               }
            }
         }
      });
      this.setVariable("bpassengerexposed", () -> {
         return AttackVehicleState.instance().isPassengerExposed(this);
      });
      this.setVariable("bistargetissmallvehicle", () -> {
         return this.target != null && this.target instanceof IsoPlayer && ((IsoPlayer)this.target).getVehicle() != null ? ((IsoPlayer)this.target).getVehicle().getScript().isSmallVehicle : true;
      });
      this.setVariable("breanimate", this::isReanimate, this::setReanimate);
      this.setVariable("brunning", () -> {
         return this.bRunning;
      });
      this.setVariable("bstaggerback", () -> {
         return this.bStaggerBack;
      });
      this.setVariable("btargetvehicle", () -> {
         return this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).getVehicle() != null;
      });
      this.setVariable("bthump", () -> {
         if (this.getThumpTarget() instanceof IsoObject && !(this.getThumpTarget() instanceof BaseVehicle)) {
            IsoObject var1 = (IsoObject)this.getThumpTarget();
            if (this.DistToSquared(var1.getX() + 0.5F, var1.getY() + 0.5F) > 9.0F) {
               this.setThumpTarget((Thumpable)null);
            }
         }

         if (this.getThumpTimer() > 0) {
            this.setThumpTarget((Thumpable)null);
         }

         return this.getThumpTarget() != null;
      });
      this.setVariable("bundervehicle", this::isUnderVehicle);
      this.setVariable("bBeingSteppedOn", this::isBeingSteppedOn);
      this.setVariable("distancetotarget", () -> {
         return this.target == null ? "" : String.valueOf(this.vectorToTarget.getLength() - this.getWidth() + this.target.getWidth());
      });
      this.setVariable("lasttargetseen", () -> {
         return this.LastTargetSeenX != -1;
      });
      this.setVariable("lungetimer", () -> {
         return this.LungeTimer;
      });
      this.setVariable("reanimatetimer", this::getReanimateTimer);
      this.setVariable("stateeventdelaytimer", this::getStateEventDelayTimer);
      this.setVariable("turndirection", () -> {
         if (this.getPath2() != null) {
            return "";
         } else {
            IsoDirections var1;
            boolean var2;
            if (this.target != null && this.vectorToTarget.getLength() != 0.0F) {
               var1 = IsoDirections.fromAngle(this.vectorToTarget);
               if (this.dir == var1) {
                  return "";
               } else {
                  var2 = CrawlingZombieTurnState.calculateDir(this, var1);
                  return var2 ? "left" : "right";
               }
            } else if (this.isCurrentState(WalkTowardState.instance())) {
               WalkTowardState.instance().calculateTargetLocation(this, tempo);
               Vector2 var10000 = tempo;
               var10000.x -= this.getX();
               var10000 = tempo;
               var10000.y -= this.getY();
               var1 = IsoDirections.fromAngle(tempo);
               if (this.dir == var1) {
                  return "";
               } else {
                  var2 = CrawlingZombieTurnState.calculateDir(this, var1);
                  return var2 ? "left" : "right";
               }
            } else {
               if (this.isCurrentState(PathFindState.instance())) {
               }

               return "";
            }
         }
      });
      this.setVariable("hitforce", this::getHitForce);
      this.setVariable("alerted", () -> {
         return this.alerted;
      });
      this.setVariable("zombiewalktype", () -> {
         return this.walkType;
      });
      this.setVariable("crawlertype", () -> {
         return this.crawlerType;
      });
      this.setVariable("bGetUpFromCrawl", this::shouldGetUpFromCrawl);
      this.setVariable("playerattackposition", this::getPlayerAttackPosition);
      this.setVariable("eatspeed", () -> {
         return this.eatSpeed;
      });
      this.setVariable("issitting", this::isSitAgainstWall);
   }

   public void actionStateChanged(ActionContext var1) {
      super.actionStateChanged(var1);
   }

   public ActionContext getActionContext() {
      return this.actionContext;
   }

   public String GetAnimSetName() {
      return this.bCrawling ? "zombie-crawler" : "zombie";
   }

   public void InitSpritePartsZombie() {
      SurvivorDesc var1 = this.descriptor;
      this.InitSpritePartsZombie(var1);
   }

   public void InitSpritePartsZombie(SurvivorDesc var1) {
      this.sprite.AnimMap.clear();
      this.sprite.AnimStack.clear();
      this.sprite.CurrentAnim = new IsoAnim();
      this.sprite.CurrentAnim.name = "REMOVE";
      this.legsSprite = this.sprite;
      this.legsSprite.name = var1.torso;
      this.ZombieID = Rand.Next(10000);
      this.bUseParts = true;
   }

   public void pathToCharacter(IsoGameCharacter var1) {
      if (!(this.AllowRepathDelay > 0.0F) || this.getCurrentState() != PathFindState.instance() && this.getCurrentState() != WalkTowardState.instance()) {
         super.pathToCharacter(var1);
      }
   }

   public void pathToLocationF(float var1, float var2, float var3) {
      if (!(this.AllowRepathDelay > 0.0F) || this.getCurrentState() != PathFindState.instance() && this.getCurrentState() != WalkTowardState.instance()) {
         super.pathToLocationF(var1, var2, var3);
      }
   }

   public void load(ByteBuffer var1, int var2) throws IOException {
      super.load(var1, var2);
      this.walkVariant = "ZombieWalk";
      this.SpriteName = "BobZ";
      if (this.palette != 1) {
         this.SpriteName = this.SpriteName + this.palette;
      }

      SurvivorDesc var3 = this.descriptor;
      this.setFemale(var3.isFemale());
      if (this.isFemale()) {
         if (this.palette == 1) {
            this.SpriteName = "KateZ";
         } else {
            this.SpriteName = "KateZ" + this.palette;
         }
      }

      if (this.isFemale()) {
         this.hurtSound = "FemaleZombieHurt";
      } else {
         this.hurtSound = "MaleZombieHurt";
      }

      this.InitSpritePartsZombie(var3);
      this.sprite.def.tintr = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.sprite.def.tintg = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.sprite.def.tintb = 0.95F + (float)Rand.Next(5) / 100.0F;
      this.setDefaultState(ZombieIdleState.instance());
      this.DoZombieStats();
      this.PathSpeed = var1.getFloat();
      this.setWidth(0.3F);
      this.TimeSinceSeenFlesh = (float)var1.getInt();
      this.alpha[IsoPlayer.getPlayerIndex()] = 0.0F;
      this.setFakeDead(var1.getInt() == 1);
      ArrayList var4 = this.savedInventoryItems;
      byte var5 = var1.get();

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = GameWindow.ReadString(var1);
         short var8 = var1.getShort();
         if (var8 >= 0 && var8 < var4.size() && this.wornItems.getBodyLocationGroup().getLocation(var7) != null) {
            this.wornItems.setItem(var7, (InventoryItem)var4.get(var8));
         }
      }

      this.setStateMachineLocked(false);
      this.setDefaultState();
      this.getCell().getZombieList().add(this);
   }

   public void save(ByteBuffer var1) throws IOException {
      super.save(var1);
      var1.putFloat(this.PathSpeed);
      var1.putInt((int)this.TimeSinceSeenFlesh);
      var1.putInt(this.isFakeDead() ? 1 : 0);
      if (this.wornItems.size() > 127) {
         throw new RuntimeException("too many worn items");
      } else {
         var1.put((byte)this.wornItems.size());
         this.wornItems.forEach((var2) -> {
            GameWindow.WriteString(var1, var2.getLocation());
            var1.putShort((short)this.savedInventoryItems.indexOf(var2.getItem()));
         });
      }
   }

   public void collideWith(IsoObject var1) {
      if (!this.Ghost) {
         if (var1.rerouteCollide != null) {
            var1 = this.rerouteCollide;
         }

         State var2 = this.getCurrentState();
         boolean var3 = var2 == PathFindState.instance() || var2 == LungeState.instance() || var2 == WalkTowardState.instance();
         if (var1 instanceof IsoWindow && ((IsoWindow)var1).canClimbThrough(this) && var3) {
            if ((var2 != PathFindState.instance() || this.isOnPath(var1)) && !this.bCrawling) {
               this.climbThroughWindow((IsoWindow)var1);
            }
         } else if (var1 instanceof IsoThumpable && ((IsoThumpable)var1).canClimbThrough(this) && var3) {
            if ((var2 != PathFindState.instance() || this.isOnPath(var1)) && !this.bCrawling) {
               this.climbThroughWindow((IsoThumpable)var1);
            }
         } else if ((!(var1 instanceof IsoDoor) || !((IsoDoor)var1).isHoppable()) && var1 instanceof Thumpable && (!(var1 instanceof IsoThumpable) || ((IsoThumpable)var1).isThumpable()) && var3) {
            boolean var4 = (var2 == PathFindState.instance() || var2 == WalkTowardState.instance()) && this.getPathFindBehavior2().isGoalSound();
            if (!SandboxOptions.instance.Lore.ThumpNoChasing.getValue() && this.target == null && !var4) {
               this.setVariable("bPathfind", false);
               this.setVariable("bMoving", false);
               this.setPath2((PolygonalMap2.Path)null);
            } else {
               if (var1 instanceof IsoThumpable && !SandboxOptions.instance.Lore.ThumpOnConstruction.getValue()) {
                  return;
               }

               Thumpable var5 = (Thumpable)var1;
               if (var1 instanceof IsoWindow && ((IsoWindow)var1).getThumpableFor(this) != null && ((IsoWindow)var1).isDestroyed()) {
                  var5 = ((IsoWindow)var1).getThumpableFor(this);
               }

               this.setThumpTarget(var5);
               this.setPath2((PolygonalMap2.Path)null);
            }
         }

         if (!this.bCrawling && IsoWindowFrame.isWindowFrame(var1) && var3 && (var2 != PathFindState.instance() || this.isOnPath(var1))) {
            this.climbThroughWindowFrame(var1);
         }

         super.collideWith(var1);
      }
   }

   private boolean isOnPath(IsoObject var1) {
      return false;
   }

   public void Hit(HandWeapon var1, IsoGameCharacter var2, float var3, boolean var4, float var5) {
      if (!Core.bTutorial || !this.ImmortalTutorialZombie) {
         BodyPartType var6 = BodyPartType.FromIndex(Rand.Next(BodyPartType.ToIndex(BodyPartType.Torso_Upper), BodyPartType.ToIndex(BodyPartType.Torso_Lower) + 1));
         if (Rand.NextBool(7)) {
            var6 = BodyPartType.Head;
         }

         if (var2.isCrit && Rand.NextBool(3)) {
            var6 = BodyPartType.Head;
         }

         LuaEventManager.triggerEvent("OnHitZombie", this, var2, var6, var1);
         super.Hit(var1, var2, var3, var4, var5);
         if (this.Health <= 0.0F && !this.isOnDeathDone()) {
            this.DoZombieInventory();
            LuaEventManager.triggerEvent("OnZombieDead", this);
            this.setOnDeathDone(true);
         }

         this.TimeSinceSeenFlesh = 0.0F;
         if (!this.isDead() && !this.emitter.isPlaying(this.getHurtSound())) {
            this.playHurtSound();
         }

         if (!this.isDead() && !this.isOnFloor() && !var4 && var1 != null && var1.getScriptItem().getCategories().contains("Blade") && var2 instanceof IsoPlayer && this.DistToProper(var2) <= 0.9F && (this.getCurrentState() == AttackState.instance() || this.getCurrentState() == LungeState.instance())) {
            this.setHitForce(0.5F);
            this.changeState(StaggerBackState.instance());
         }

      }
   }

   public void onMouseLeftClick() {
      if (IsoPlayer.getInstance() == null || !IsoPlayer.getInstance().isAiming()) {
         if (IsoPlayer.getInstance().IsAttackRange(this.getX(), this.getY(), this.getZ())) {
            Vector2 var1 = new Vector2(this.getX(), this.getY());
            var1.x -= IsoPlayer.getInstance().getX();
            var1.y -= IsoPlayer.getInstance().getY();
            var1.normalize();
            IsoPlayer.getInstance().DirectionFromVector(var1);
            IsoPlayer.getInstance().AttemptAttack();
         }

      }
   }

   private void renderAtlasTexture(float var1, float var2, float var3) {
      if (this.atlasTex != null) {
         if (IsoSprite.globalOffsetX == -1.0F) {
            IsoSprite.globalOffsetX = -IsoCamera.frameState.OffX;
            IsoSprite.globalOffsetY = -IsoCamera.frameState.OffY;
         }

         float var4 = IsoUtils.XToScreen(var1, var2, var3, 0);
         float var5 = IsoUtils.YToScreen(var1, var2, var3, 0);
         this.sx = var4;
         this.sy = var5;
         var4 = this.sx + IsoSprite.globalOffsetX;
         var5 = this.sy + IsoSprite.globalOffsetY;
         ColorInfo var6 = inf.set(1.0F, 1.0F, 1.0F, 1.0F);
         if (PerformanceSettings.LightingFrameSkip < 3 && this.getCurrentSquare() != null) {
            this.getCurrentSquare().interpolateLight(var6, var1 - (float)this.getCurrentSquare().getX(), var2 - (float)this.getCurrentSquare().getY());
         }

         this.atlasTex.render((float)((int)var4 - this.atlasTex.getWidth() / 2), (float)((int)var5 - this.atlasTex.getHeight() / 2), (float)this.atlasTex.getWidth(), (float)this.atlasTex.getHeight(), var6.r, var6.g, var6.b, var6.a, (Consumer)null);
      }
   }

   public void render(float var1, float var2, float var3, ColorInfo var4, boolean var5, boolean var6, Shader var7) {
      if (this.getCurrentState() == FakeDeadZombieState.instance()) {
         if (this.bDressInRandomOutfit) {
            ModelManager.instance.dressInRandomOutfit(this);
         }

         if (this.atlasTex == null) {
            this.atlasTex = DeadBodyAtlas.instance.getBodyTexture(this);
            DeadBodyAtlas.instance.render();
         }

         if (this.atlasTex != null) {
            this.renderAtlasTexture(var1, var2, var3);
         }

      } else {
         if (this.atlasTex != null) {
            this.atlasTex = null;
         }

         if (IsoCamera.CamCharacter != IsoPlayer.getInstance()) {
            this.targetAlpha[IsoPlayer.getPlayerIndex()] = 1.0F;
            this.alpha[IsoPlayer.getPlayerIndex()] = 1.0F;
         }

         super.render(var1, var2, var3, var4, var5, var6, var7);
      }
   }

   public void renderlast() {
      super.renderlast();
      if (DebugOptions.instance.ZombieRenderCanCrawlUnderVehicle.getValue() && this.isCanCrawlUnderVehicle()) {
         this.renderTextureOverHead("media/ui/FavoriteStar.png");
      }

      if (DebugOptions.instance.ZombieRenderMemory.getValue()) {
         String var1;
         if (this.target == null) {
            var1 = "media/ui/Moodles/Moodle_Icon_Bored.png";
         } else if (this.BonusSpotTime == 0.0F) {
            var1 = "media/ui/Moodles/Moodle_Icon_Angry.png";
         } else {
            var1 = "media/ui/Moodles/Moodle_Icon_Zombie.png";
         }

         this.renderTextureOverHead(var1);
         int var2 = (int)IsoUtils.XToScreenExact(this.x, this.y, this.z, 0);
         int var3 = (int)IsoUtils.YToScreenExact(this.x, this.y, this.z, 0);
         int var4 = TextManager.instance.getFontFromEnum(UIFont.Small).getLineHeight();
         TextManager.instance.DrawString((double)var2, (double)(var3 += var4), "AllowRepathDelay : " + this.AllowRepathDelay);
         TextManager.instance.DrawString((double)var2, (double)(var3 += var4), "BonusSpotTime : " + this.BonusSpotTime);
         TextManager.instance.DrawString((double)var2, (double)(var3 + var4), "TimeSinceSeenFlesh : " + this.TimeSinceSeenFlesh);
      }

   }

   protected boolean renderTextureInsteadOfModel(float var1, float var2) {
      boolean var3 = this.isCurrentState(WalkTowardState.instance()) || this.isCurrentState(PathFindState.instance());
      String var4 = "zombie";
      String var5 = var3 ? "walktoward" : "idle";
      byte var6 = 4;
      int var7 = (int)(this.m_characterTextureAnimTime / this.m_characterTextureAnimDuration * (float)var6);
      float var8 = (var3 ? 0.67F : 1.0F) * ((float)var7 / (float)var6);
      Texture var9 = DeadBodyAtlas.instance.getBodyTexture(this.isFemale(), var4, var5, this.getDir(), var7, var8);
      if (var9 != null && var9.isReady()) {
         float var10 = (float)Core.TileScale;
         float var11 = this.offsetX + 1.0F * var10;
         float var12 = this.offsetY + -89.0F * var10;
         float var13 = IsoUtils.XToScreen(var1, var2, this.getZ(), 0);
         float var14 = IsoUtils.YToScreen(var1, var2, this.getZ(), 0);
         var13 = var13 - IsoCamera.getOffX() - var11;
         var14 = var14 - IsoCamera.getOffY() - var12;
         var13 -= (float)(var9.getWidthOrig() / 2);
         var14 -= (float)var9.getHeightOrig();
         var14 -= 64.0F * var10;
         int var15 = IsoCamera.frameState.playerIndex;
         var9.render(var13, var14, (float)var9.getWidth(), (float)var9.getHeight(), 0.0F, 0.0F, 0.0F, this.alpha[var15], (Consumer)null);
      }

      if (DebugOptions.instance.CharacterRenderAngle.getValue()) {
         tempo.set(this.dir.ToVector());
         this.drawDirectionLine(tempo, 1.2F, 0.0F, 1.0F, 0.0F);
      }

      return true;
   }

   private void renderTextureOverHead(String var1) {
      float var2 = this.x;
      float var3 = this.y;
      float var4 = IsoUtils.XToScreen(var2, var3, this.getZ(), 0);
      float var5 = IsoUtils.YToScreen(var2, var3, this.getZ(), 0);
      var4 = var4 - IsoCamera.getOffX() - this.offsetX;
      var5 = var5 - IsoCamera.getOffY() - this.offsetY;
      var5 -= (float)(128 / (2 / Core.TileScale));
      Texture var6 = Texture.getSharedTexture(var1);
      float var7 = Core.getInstance().getZoom(IsoCamera.frameState.playerIndex);
      var7 = Math.max(var7, 1.0F);
      int var8 = (int)((float)var6.getWidth() * var7);
      int var9 = (int)((float)var6.getHeight() * var7);
      var6.render((float)((int)var4 - var8 / 2), (float)((int)var5 - var9), (float)var8, (float)var9);
   }

   protected void updateAlpha() {
      super.updateAlpha();
      if (this.isFakeDead()) {
         this.setAlpha(1.0F);
      }

   }

   public void RespondToSound() {
      if (!this.Ghost) {
         if (!this.Deaf) {
            if (!this.isUseless()) {
               float var1;
               if ((this.getCurrentState() == PathFindState.instance() || this.getCurrentState() == WalkTowardState.instance()) && this.getPathFindBehavior2().isGoalSound() && (int)this.z == this.getPathTargetZ() && this.bSoundSourceRepeating) {
                  var1 = this.DistToSquared((float)this.getPathTargetX(), (float)this.getPathTargetY());
                  if (var1 < 25.0F && LosUtil.lineClear(this.getCell(), (int)this.x, (int)this.y, (int)this.z, this.getPathTargetX(), this.getPathTargetY(), (int)this.z, false) != LosUtil.TestResults.Blocked) {
                     this.setVariable("bPathfind", false);
                     this.setVariable("bMoving", false);
                     this.setPath2((PolygonalMap2.Path)null);
                  }
               }

               if (this.soundReactDelay > 0.0F) {
                  this.soundReactDelay -= GameTime.getInstance().getMultiplier() / 1.6F;
                  if (this.soundReactDelay < 0.0F) {
                     this.soundReactDelay = 0.0F;
                  }

                  if (this.soundReactDelay > 0.0F) {
                     return;
                  }
               }

               var1 = 0.0F;
               Object var2 = null;
               WorldSoundManager.WorldSound var3 = WorldSoundManager.instance.getSoundZomb(this);
               float var4 = WorldSoundManager.instance.getSoundAttract(var3, this);
               if (var4 <= 0.0F) {
                  var3 = null;
               }

               if (var3 != null) {
                  var1 = var4;
                  var2 = var3.source;
                  this.soundAttract = var4;
                  this.soundAttractTimeout = 60.0F;
               } else if (this.soundAttractTimeout > 0.0F) {
                  this.soundAttractTimeout -= GameTime.getInstance().getMultiplier() / 1.6F;
                  if (this.soundAttractTimeout < 0.0F) {
                     this.soundAttractTimeout = 0.0F;
                  }
               }

               WorldSoundManager.ResultBiggestSound var5 = WorldSoundManager.instance.getBiggestSoundZomb((int)this.getX(), (int)this.getY(), (int)this.getZ(), true, this);
               if (var5.sound != null && (this.soundAttractTimeout == 0.0F || this.soundAttract * 2.0F < var5.attract)) {
                  var3 = var5.sound;
                  var1 = var5.attract;
                  var2 = var3.source;
               }

               if (var3 != null && var3.bRepeating && var3.z == (int)this.z) {
                  float var6 = this.DistToSquared((float)var3.x, (float)var3.y);
                  if (var6 < 25.0F && LosUtil.lineClear(this.getCell(), (int)this.x, (int)this.y, (int)this.z, var3.x, var3.y, (int)this.z, false) != LosUtil.TestResults.Blocked) {
                     var3 = null;
                  }
               }

               if (var3 != null) {
                  this.soundAttract = var1;
                  this.soundSourceTarget = var2;
                  this.soundReactDelay = (float)Rand.Next(0, 16);
                  this.delayedSound.x = var3.x;
                  this.delayedSound.y = var3.y;
                  this.delayedSound.z = var3.z;
                  this.bSoundSourceRepeating = var3.bRepeating;
               }

               if (this.delayedSound.x != -1 && this.soundReactDelay == 0.0F) {
                  int var10 = this.delayedSound.x;
                  int var7 = this.delayedSound.y;
                  int var8 = this.delayedSound.z;
                  this.delayedSound.x = -1;
                  float var9 = IsoUtils.DistanceManhatten(this.getX(), this.getY(), (float)var10, (float)var7) / 2.5F;
                  var10 += Rand.Next((int)(-var9), (int)var9);
                  var7 += Rand.Next((int)(-var9), (int)var9);
                  if ((this.getCurrentState() == PathFindState.instance() || this.getCurrentState() == WalkTowardState.instance()) && (this.getPathFindBehavior2().isGoalLocation() || this.getPathFindBehavior2().isGoalSound())) {
                     if (!IsoUtils.isSimilarDirection(this, (float)var10, (float)var7, this.getPathFindBehavior2().getTargetX(), this.getPathFindBehavior2().getTargetY(), 0.5F)) {
                        this.setTurnAlertedValues(var10, var7);
                        this.pathToSound(var10, var7, var8);
                        this.setLastHeardSound(this.getPathTargetX(), this.getPathTargetY(), this.getPathTargetZ());
                        this.AllowRepathDelay = (float)AllowRepathDelayMax;
                        this.timeSinceRespondToSound = 0.0F;
                     }

                     return;
                  }

                  if (this.timeSinceRespondToSound < 60.0F) {
                     return;
                  }

                  if (!IsoUtils.isSimilarDirection(this, (float)var10, (float)var7, this.x + this.getForwardDirection().x, this.y + this.getForwardDirection().y, 0.5F)) {
                     this.setTurnAlertedValues(var10, var7);
                  }

                  this.pathToSound(var10, var7, var8);
                  this.setLastHeardSound(this.getPathTargetX(), this.getPathTargetY(), this.getPathTargetZ());
                  this.AllowRepathDelay = (float)AllowRepathDelayMax;
                  this.timeSinceRespondToSound = 0.0F;
               }

            }
         }
      }
   }

   public void setTurnAlertedValues(int var1, int var2) {
      Vector2 var3 = new Vector2(this.getX() - ((float)var1 + 0.5F), this.getY() - ((float)var2 + 0.5F));
      float var4 = var3.getDirectionNeg();
      if (var4 < 0.0F) {
         var4 = Math.abs(var4);
      } else {
         var4 = new Float(6.283185307179586D - (double)var4);
      }

      Double var5 = new Double(Math.toDegrees((double)var4));
      Vector2 var6 = new Vector2(IsoDirections.reverse(this.getDir()).ToVector().x, IsoDirections.reverse(this.getDir()).ToVector().y);
      var6.normalize();
      float var7 = var6.getDirectionNeg();
      if (var7 < 0.0F) {
         var7 = Math.abs(var7);
      } else {
         var7 = new Float(6.283185307179586D - (double)var7);
      }

      Double var8 = new Double(Math.toDegrees((double)var7));
      if (var8.intValue() == 360) {
         var8 = 0.0D;
      }

      if (var5.intValue() == 360) {
         var5 = 0.0D;
      }

      String var9 = "0";
      boolean var10 = false;
      int var11;
      if (var5 > var8) {
         var11 = (new Double(var5 - var8)).intValue();
         if (var11 > 350 || var11 <= 35) {
            var9 = "45R";
         }

         if (var11 > 35 && var11 <= 80) {
            var9 = "90R";
         }

         if (var11 > 80 && var11 <= 125) {
            var9 = "135R";
         }

         if (var11 > 125 && var11 <= 170) {
            var9 = "180R";
         }

         if (var11 > 170 && var11 < 215) {
            var9 = "180L";
         }

         if (var11 >= 215 && var11 < 260) {
            var9 = "135L";
         }

         if (var11 >= 260 && var11 < 305) {
            var9 = "90L";
         }

         if (var11 >= 305 && var11 < 350) {
            var9 = "45L";
         }
      } else {
         var11 = (new Double(var8 - var5)).intValue();
         if (var11 > 10 && var11 <= 55) {
            var9 = "45L";
         }

         if (var11 > 55 && var11 <= 100) {
            var9 = "90L";
         }

         if (var11 > 100 && var11 <= 145) {
            var9 = "135L";
         }

         if (var11 > 145 && var11 <= 190) {
            var9 = "180L";
         }

         if (var11 > 190 && var11 < 235) {
            var9 = "180R";
         }

         if (var11 >= 235 && var11 < 280) {
            var9 = "135R";
         }

         if (var11 >= 280 && var11 < 325) {
            var9 = "90R";
         }

         if (var11 >= 325 || var11 < 10) {
            var9 = "45R";
         }
      }

      this.setVariable("turnalertedvalue", var9);
      ZombieTurnAlerted.instance().setParams(this, var3.set((float)var1 + 0.5F - this.x, (float)var2 + 0.5F - this.y).getDirection());
      this.alerted = true;
   }

   public void spotted(IsoMovingObject var1, boolean var2) {
      if (!GameClient.bClient) {
         if (this.getCurrentSquare() != null) {
            if (var1.getCurrentSquare() != null) {
               if (!this.getCurrentSquare().getProperties().Is(IsoFlagType.smoke) && !this.isUseless()) {
                  if (!(var1 instanceof IsoPlayer) || !((IsoPlayer)var1).isGhostMode()) {
                     IsoGameCharacter var3 = (IsoGameCharacter)Type.tryCastTo(var1, IsoGameCharacter.class);
                     if (var3 == null || !var3.isDead()) {
                        if (this.getCurrentSquare() == null) {
                           this.ensureOnTile();
                        }

                        if (var1.getCurrentSquare() == null) {
                           var1.ensureOnTile();
                        }

                        float var4 = 200.0F;
                        int var5 = var1 instanceof IsoPlayer && !GameServer.bServer ? ((IsoPlayer)var1).PlayerIndex : 0;
                        float var6 = (var1.getCurrentSquare().lighting[var5].lightInfo().r + var1.getCurrentSquare().lighting[var5].lightInfo().g + var1.getCurrentSquare().lighting[var5].lightInfo().b) / 3.0F;
                        float var7 = RenderSettings.getInstance().getAmbientForPlayer(var5);
                        float var8 = (this.getCurrentSquare().lighting[var5].lightInfo().r + this.getCurrentSquare().lighting[var5].lightInfo().g + this.getCurrentSquare().lighting[var5].lightInfo().b) / 3.0F;
                        var8 = var8 * var8 * var8;
                        if (var6 > 1.0F) {
                           var6 = 1.0F;
                        }

                        if (var6 < 0.0F) {
                           var6 = 0.0F;
                        }

                        if (var8 > 1.0F) {
                           var8 = 1.0F;
                        }

                        if (var8 < 0.0F) {
                           var8 = 0.0F;
                        }

                        float var9 = 1.0F - (var6 - var8);
                        if (var6 < 0.2F) {
                           var6 = 0.2F;
                        }

                        if (var7 < 0.2F) {
                           var7 = 0.2F;
                        }

                        if (var1.getCurrentSquare().getRoom() != this.getCurrentSquare().getRoom()) {
                           var4 = 50.0F;
                           if (var1.getCurrentSquare().getRoom() != null && this.getCurrentSquare().getRoom() == null || var1.getCurrentSquare().getRoom() == null && this.getCurrentSquare().getRoom() != null) {
                              var4 = 20.0F;
                              if (!var3.isAiming() && !var3.isSneaking()) {
                                 if (var1.getMovementLastFrame().getLength() <= 0.04F && var6 < 0.4F) {
                                    var4 = 10.0F;
                                 }
                              } else if (var6 < 0.4F) {
                                 var4 = 0.0F;
                              } else {
                                 var4 = 10.0F;
                              }
                           }
                        }

                        tempo.x = var1.getX();
                        tempo.y = var1.getY();
                        Vector2 var10000 = tempo;
                        var10000.x -= this.getX();
                        var10000 = tempo;
                        var10000.y -= this.getY();
                        if (var1.getCurrentSquare().getZ() != this.current.getZ()) {
                           int var10 = Math.abs(var1.getCurrentSquare().getZ() - this.current.getZ()) * 5;
                           ++var10;
                           var4 /= (float)var10;
                        }

                        float var22 = GameTime.getInstance().getViewDist();
                        if (!(tempo.getLength() > var22)) {
                           if (GameServer.bServer) {
                              this.bIndoorZombie = false;
                           }

                           if (tempo.getLength() < var22) {
                              var22 = tempo.getLength();
                           }

                           var22 *= 1.1F;
                           if (var22 > GameTime.getInstance().getViewDistMax()) {
                              var22 = GameTime.getInstance().getViewDistMax();
                           }

                           tempo.normalize();
                           Vector2 var11 = this.getLookVector(tempo2);
                           float var12 = var11.dot(tempo);
                           if (this.DistTo(var1) > 20.0F) {
                              var4 -= 10000.0F;
                           }

                           if ((double)var22 > 0.5D) {
                              if (var12 < -0.4F) {
                                 var4 = 0.0F;
                              } else if (var12 < -0.2F) {
                                 var4 /= 8.0F;
                              } else if (var12 < -0.0F) {
                                 var4 /= 4.0F;
                              } else if (var12 < 0.2F) {
                                 var4 /= 2.0F;
                              } else if (var12 <= 0.4F) {
                                 var4 *= 2.0F;
                              } else if (var12 > 0.4F) {
                                 var4 *= 8.0F;
                              } else if (var12 > 0.6F) {
                                 var4 *= 16.0F;
                              } else if (var12 > 0.8F) {
                                 var4 *= 32.0F;
                              }
                           }

                           if (var4 > 0.0F && this.target instanceof IsoPlayer) {
                              IsoPlayer var13 = (IsoPlayer)this.target;
                              if (!GameServer.bServer && var13.RemoteID == -1 && this.current.isCanSee(var13.PlayerIndex)) {
                                 ((IsoPlayer)this.target).targetedByZombie = true;
                                 ((IsoPlayer)this.target).lastTargeted = 0.0F;
                              }
                           }

                           var4 *= var9;
                           int var23 = (int)var1.getZ() - (int)this.getZ();
                           if (var23 >= 1) {
                              var4 /= (float)(var23 * 3);
                           }

                           var4 *= 1.0F - var22 / GameTime.getInstance().getViewDist();
                           var4 *= 1.0F - var22 / GameTime.getInstance().getViewDist();
                           var4 *= 1.0F - var22 / GameTime.getInstance().getViewDist();
                           float var14 = PZMath.clamp(var22 / 10.0F, 0.0F, 1.0F);
                           var4 *= 1.0F + (1.0F - var14) * 10.0F;
                           float var15 = var1.getMovementLastFrame().getLength();
                           if (var15 == 0.0F && var6 <= 0.2F) {
                              var6 = 0.0F;
                           }

                           if (var3 != null) {
                              if (var3.getTorchStrength() > 0.0F) {
                                 var4 *= 3.0F;
                              }

                              if (var15 < 0.01F) {
                                 var4 *= 0.5F;
                              } else if (var3.isSneaking()) {
                                 var4 *= 0.4F;
                              } else if (var3.isAiming()) {
                                 var4 *= 0.75F;
                              } else if (var15 < 0.06F) {
                                 var4 *= 0.8F;
                              } else if (var15 >= 0.06F) {
                                 var4 *= 2.4F;
                              }

                              if (this.eatBodyTarget != null) {
                                 var4 *= 0.7F;
                              }

                              if (var22 < 5.0F && (!var3.isRunning() && !var3.isSneaking() && !var3.isAiming() || var3.isRunning())) {
                                 var4 *= 3.0F;
                              }

                              if (this.spottedLast == var1 && this.TimeSinceSeenFlesh < 120.0F) {
                                 var4 = 1000.0F;
                              }

                              var4 *= var3.getSneakSpotMod();
                              var4 *= var7;
                              if (this.target != var1 && this.target != null) {
                                 float var16 = IsoUtils.DistanceManhatten(this.getX(), this.getY(), var1.getX(), var1.getY());
                                 float var17 = IsoUtils.DistanceManhatten(this.getX(), this.getY(), this.target.getX(), this.target.getY());
                                 if (var16 > var17) {
                                    return;
                                 }
                              }

                              var4 *= 0.3F;
                              if (var2) {
                                 var4 = 1000000.0F;
                              }

                              if (this.BonusSpotTime > 0.0F) {
                                 var4 = 1000000.0F;
                              }

                              var4 *= 1.2F;
                              if (SandboxOptions.instance.Lore.Sight.getValue() == 1) {
                                 var4 *= 2.5F;
                              }

                              if (SandboxOptions.instance.Lore.Sight.getValue() == 3) {
                                 var4 *= 0.45F;
                              }

                              if (this.inactive) {
                                 var4 *= 0.25F;
                              }

                              var4 *= 0.25F;
                              if (var1 instanceof IsoPlayer && ((IsoPlayer)var1).Traits.Inconspicuous.isSet()) {
                                 var4 *= 0.5F;
                              }

                              if (var1 instanceof IsoPlayer && ((IsoPlayer)var1).Traits.Conspicuous.isSet()) {
                                 var4 *= 2.0F;
                              }

                              var4 *= 1.6F;
                              IsoGridSquare var24 = null;
                              IsoGridSquare var25 = null;
                              if (this.getCurrentSquare() != var1.getCurrentSquare() && var1 instanceof IsoPlayer && ((IsoPlayer)var1).isSneaking()) {
                                 int var18 = Math.abs(this.getCurrentSquare().getX() - var1.getCurrentSquare().getX());
                                 int var19 = Math.abs(this.getCurrentSquare().getY() - var1.getCurrentSquare().getY());
                                 if (var18 > var19) {
                                    if (this.getCurrentSquare().getX() - var1.getCurrentSquare().getX() > 0) {
                                       var24 = var1.getCurrentSquare().nav[IsoDirections.E.index()];
                                    } else {
                                       var24 = var1.getCurrentSquare();
                                       var25 = var1.getCurrentSquare().nav[IsoDirections.W.index()];
                                    }
                                 } else if (this.getCurrentSquare().getY() - var1.getCurrentSquare().getY() > 0) {
                                    var24 = var1.getCurrentSquare().nav[IsoDirections.S.index()];
                                 } else {
                                    var24 = var1.getCurrentSquare();
                                    var25 = var1.getCurrentSquare().nav[IsoDirections.N.index()];
                                 }

                                 if (var24 != null && var1 instanceof IsoGameCharacter) {
                                    float var20 = ((IsoGameCharacter)var1).checkIsNearWall();
                                    if (var20 == 1.0F && var25 != null) {
                                       var20 = var25.getGridSneakModifier(true);
                                    }

                                    if (var20 > 1.0F) {
                                       float var21 = var1.DistTo(var24.x, var24.y);
                                       if (var21 > 1.0F) {
                                          var20 /= var21;
                                       }

                                       var4 /= var20;
                                    }
                                 }
                              }

                              var4 *= GameTime.instance.getMultiplier();
                              var4 = (float)Math.floor((double)var4);
                              if ((float)Rand.Next(400) >= var4) {
                                 if (var4 > 20.0F && var1 instanceof IsoPlayer && var22 < 15.0F) {
                                    ((IsoPlayer)var1).bCouldBeSeenThisFrame = true;
                                 }

                                 if (!((IsoPlayer)var1).isbCouldBeSeenThisFrame() && !((IsoPlayer)var1).isbSeenThisFrame() && ((IsoPlayer)var1).isSneaking() && ((IsoPlayer)var1).JustMoved && Rand.Next((int)(700.0F * GameTime.instance.getInvMultiplier())) == 0) {
                                    if (GameServer.bServer) {
                                       GameServer.addXp((IsoPlayer)var1, PerkFactory.Perks.Sneak, 1);
                                    } else {
                                       ((IsoPlayer)var1).getXp().AddXP(PerkFactory.Perks.Sneak, 1.0F);
                                    }
                                 }

                                 if (!((IsoPlayer)var1).isbCouldBeSeenThisFrame() && !((IsoPlayer)var1).isbSeenThisFrame() && ((IsoPlayer)var1).isSneaking() && ((IsoPlayer)var1).JustMoved && Rand.Next((int)(700.0F * GameTime.instance.getInvMultiplier())) == 0) {
                                    if (GameServer.bServer) {
                                       GameServer.addXp((IsoPlayer)var1, PerkFactory.Perks.Lightfoot, 1);
                                    } else {
                                       ((IsoPlayer)var1).getXp().AddXP(PerkFactory.Perks.Lightfoot, 1.0F);
                                    }
                                 }

                              } else {
                                 if (var1 instanceof IsoPlayer) {
                                    ((IsoPlayer)var1).setbSeenThisFrame(true);
                                 }

                                 if (!var2) {
                                    this.BonusSpotTime = 120.0F;
                                 }

                                 this.LastTargetSeenX = (int)var1.getX();
                                 this.LastTargetSeenY = (int)var1.getY();
                                 this.LastTargetSeenZ = (int)var1.getZ();
                                 if (this.stateMachine.getCurrent() != StaggerBackState.instance()) {
                                    if (this.target != var1) {
                                       this.targetSeenTime = 0.0F;
                                    }

                                    this.setTarget(var1);
                                    this.vectorToTarget.x = var1.getX();
                                    this.vectorToTarget.y = var1.getY();
                                    var10000 = this.vectorToTarget;
                                    var10000.x -= this.getX();
                                    var10000 = this.vectorToTarget;
                                    var10000.y -= this.getY();
                                    float var26 = this.vectorToTarget.getLength();
                                    if (!var2) {
                                       this.TimeSinceSeenFlesh = 0.0F;
                                       this.targetSeenTime += GameTime.getInstance().getRealworldSecondsSinceLastUpdate();
                                    }

                                    if (this.target != this.spottedLast || this.getCurrentState() != LungeState.instance() || !(this.LungeTimer > 0.0F)) {
                                       if (this.target != this.spottedLast || this.getCurrentState() != AttackVehicleState.instance()) {
                                          if ((int)this.getZ() == (int)this.target.getZ() && (var26 <= 3.5F || this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).getVehicle() != null && var26 <= 4.0F) && this.getStateEventDelayTimer() <= 0.0F && !PolygonalMap2.instance.lineClearCollide(this.getX(), this.getY(), var1.x, var1.y, (int)this.getZ(), var1)) {
                                             this.setTarget(var1);
                                             if (this.getCurrentState() == LungeState.instance()) {
                                                return;
                                             }
                                          }

                                          this.spottedLast = var1;
                                          if (!this.Ghost && !this.getCurrentSquare().getProperties().Is(IsoFlagType.smoke)) {
                                             this.setTarget(var1);
                                             if (this.AllowRepathDelay > 0.0F) {
                                                return;
                                             }

                                             if (this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).getVehicle() != null) {
                                                if ((this.getCurrentState() == PathFindState.instance() || this.getCurrentState() == WalkTowardState.instance()) && this.getPathFindBehavior2().getTargetChar() == this.target) {
                                                   return;
                                                }

                                                if (this.getCurrentState() == AttackVehicleState.instance()) {
                                                   return;
                                                }

                                                BaseVehicle var27 = ((IsoGameCharacter)this.target).getVehicle();
                                                if (Math.abs(var27.getCurrentSpeedKmHour()) > 0.1F && this.DistToSquared(var27) <= 16.0F) {
                                                   return;
                                                }

                                                this.pathToCharacter((IsoGameCharacter)this.target);
                                                this.AllowRepathDelay = 10.0F;
                                                return;
                                             }

                                             this.pathToCharacter(var3);
                                             if (Rand.Next(5) == 0) {
                                                this.spotSoundDelay = 200;
                                             }

                                             this.AllowRepathDelay = (float)(AllowRepathDelayMax * 4);
                                          }

                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               } else {
                  this.target = null;
                  this.spottedLast = null;
               }
            }
         }
      }
   }

   public void Move(Vector2 var1) {
      if (!GameClient.bClient) {
         this.nx += var1.x * GameTime.instance.getMultiplier();
         this.ny += var1.y * GameTime.instance.getMultiplier();
         this.movex = var1.x;
         this.movey = var1.y;
         this.reqMovement.x = var1.x;
         this.reqMovement.y = var1.y;
      }
   }

   public void MoveUnmodded(Vector2 var1) {
      if (this.speedType == 1 && (this.isCurrentState(LungeState.instance()) || this.isCurrentState(AttackState.instance()) || this.isCurrentState(StaggerBackState.instance()) || this.isCurrentState(ZombieHitReactionState.instance())) && this.target instanceof IsoGameCharacter) {
         float var2 = this.target.nx - this.x;
         float var3 = this.target.ny - this.y;
         float var4 = (float)Math.sqrt((double)(var2 * var2 + var3 * var3));
         var4 -= this.getWidth() + this.target.getWidth() - 0.1F;
         var4 = Math.max(0.0F, var4);
         if (var1.getLength() > var4) {
            var1.setLength(var4);
         }
      }

      super.MoveUnmodded(var1);
   }

   public void DoFootstepSound(float var1) {
      if (!GameServer.bServer) {
         if (!(var1 <= 0.0F)) {
            if (this.getCurrentSquare() != null) {
               if (!GameClient.bClient) {
                  boolean var6 = SoundManager.instance.isListenerInRange(this.getX(), this.getY(), 15.0F);
                  if (var6) {
                     this.footstepVolume = var1;
                     ZombieFootstepManager.instance.addCharacter(this);
                  }

               } else {
                  if (this.def != null && this.sprite != null && this.sprite.CurrentAnim != null && (this.sprite.CurrentAnim.name.contains("Run") || this.sprite.CurrentAnim.name.contains("Walk"))) {
                     int var2 = (int)this.def.Frame;
                     boolean var3;
                     if (var2 >= 0 && var2 < 5) {
                        var3 = this.stepFrameLast < 0 || this.stepFrameLast > 5;
                     } else {
                        var3 = this.stepFrameLast < 5;
                     }

                     if (var3) {
                        for(int var4 = 0; var4 < IsoPlayer.numPlayers; ++var4) {
                           IsoPlayer var5 = IsoPlayer.players[var4];
                           if (var5 != null && var5.DistToSquared(this) < 225.0F) {
                              ZombieFootstepManager.instance.addCharacter(this);
                              break;
                           }
                        }
                     }

                     this.stepFrameLast = var2;
                  } else {
                     this.stepFrameLast = -1;
                  }

               }
            }
         }
      }
   }

   public void preupdate() {
      if (GameServer.bServer && this.thumpSent) {
         this.thumpFlag = 0;
         this.thumpSent = false;
         this.mpIdleSound = false;
      }

      this.FollowCount = 0;
      super.preupdate();
   }

   public void postupdate() {
      IsoZombie.s_performance.postUpdate.invokeAndMeasure(this, IsoZombie::postUpdateInternal);
   }

   private void postUpdateInternal() {
      if (this.target instanceof IsoPlayer) {
         ++((IsoPlayer)this.target).getStats().NumChasingZombies;
      }

      super.postupdate();
      if (this.current == null && !GameClient.bClient) {
         this.removeFromWorld();
         this.removeFromSquare();
      }

      if (!GameServer.bServer) {
         IsoPlayer var1 = this.getReanimatedPlayer();
         if (var1 != null) {
            var1.setX(GameClient.bClient ? this.bx : this.getX());
            var1.setY(GameClient.bClient ? this.by : this.getY());
            var1.setZ(this.getZ());
            var1.setDir(this.getDir());
            var1.setForwardDirection(this.getForwardDirection());
            AnimationPlayer var2 = this.getAnimationPlayer();
            AnimationPlayer var3 = var1.getAnimationPlayer();
            if (var2 != null && var2.isReady() && var3 != null && var3.isReady()) {
               var3.setTargetAngle(var2.getAngle());
               var3.setAngleToTarget();
            }

            var1.setCurrent(this.getCell().getGridSquare((int)var1.x, (int)var1.y, (int)var1.z));
            var1.updateLightInfo();
            if (var1.soundListener != null) {
               var1.soundListener.setPos(var1.getX(), var1.getY(), var1.getZ());
               var1.soundListener.tick();
            }

            IsoPlayer var4 = IsoPlayer.getInstance();
            IsoPlayer.setInstance(var1);
            var1.updateLOS();
            IsoPlayer.setInstance(var4);
            if (GameClient.bClient && this.networkUpdate.Check()) {
               GameClient.instance.sendPlayer(var1);
            }

            var1.dirtyRecalcGridStackTime = 2.0F;
         }
      }

      if (this.targetSeenTime > 0.0F && !this.isTargetVisible()) {
         this.targetSeenTime = 0.0F;
      }

   }

   public boolean isSolidForSeparate() {
      if (!this.isCurrentState(FakeDeadZombieState.instance()) && !this.isCurrentState(ZombieFallDownState.instance()) && !this.isCurrentState(ZombieOnGroundState.instance()) && !this.isCurrentState(ZombieGetUpState.instance()) && (!this.isCurrentState(ZombieHitReactionState.instance()) || this.speedType == 1)) {
         return this.isSitAgainstWall() ? false : super.isSolidForSeparate();
      } else {
         return false;
      }
   }

   public boolean isPushableForSeparate() {
      if (!this.isCurrentState(ThumpState.instance()) && !this.isCurrentState(AttackState.instance()) && !this.isCurrentState(AttackVehicleState.instance()) && !this.isCurrentState(ZombieEatBodyState.instance()) && !this.isCurrentState(ZombieFaceTargetState.instance())) {
         return this.isSitAgainstWall() ? false : super.isPushableForSeparate();
      } else {
         return false;
      }
   }

   public boolean isPushedByForSeparate(IsoMovingObject var1) {
      if (var1 instanceof IsoZombie && ((IsoZombie)var1).getCurrentState() == ZombieHitReactionState.instance() && !((IsoZombie)var1).collideWhileHit) {
         return false;
      } else {
         return this.getCurrentState() == ZombieHitReactionState.instance() && !this.collideWhileHit ? false : super.isPushedByForSeparate(var1);
      }
   }

   public void update() {
      IsoZombie.s_performance.update.invokeAndMeasure(this, IsoZombie::updateInternal);
   }

   private void updateInternal() {
      if (SandboxOptions.instance.Lore.ActiveOnly.getValue() > 1) {
         if ((SandboxOptions.instance.Lore.ActiveOnly.getValue() != 2 || GameTime.instance.getHour() < 20 && GameTime.instance.getHour() > 8) && (SandboxOptions.instance.Lore.ActiveOnly.getValue() != 3 || GameTime.instance.getHour() <= 8 || GameTime.instance.getHour() >= 20)) {
            this.makeInactive(true);
         } else {
            this.makeInactive(false);
         }
      }

      float var2;
      if (!GameServer.bServer && this.vocalEvent == 0L && !this.isDead() && !this.isFakeDead() && SoundManager.instance.isListenerInRange(this.x, this.y, 20.0F)) {
         String var1 = "MaleZombieCombined";
         var2 = 0.0F;
         if (this.isFemale()) {
            var2 += 0.6F;
            if (Rand.Next(3) == 0) {
               var1 = "FemaleZombieCombined";
            }
         }

         this.vocalEvent = this.getEmitter().playVocals(var1);
         var2 += Rand.Next(-0.4F, 0.4F);
         javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Pitch", var2);
      }

      if (this.vocalEvent != 0L && !this.isDead() && this.isFakeDead() && this.getEmitter().isPlaying(this.vocalEvent)) {
         this.getEmitter().stopSound(this.vocalEvent);
         this.vocalEvent = 0L;
      }

      this.updateVocalProperties();
      if (this.bCrawling) {
         if (this.actionContext.getGroup() != ActionGroup.getActionGroup("zombie-crawler")) {
            this.advancedAnimator.OnAnimDataChanged(false);
            this.initializeStates();
            this.actionContext.setGroup(ActionGroup.getActionGroup("zombie-crawler"));
         }
      } else if (this.actionContext.getGroup() != ActionGroup.getActionGroup("zombie")) {
         this.advancedAnimator.OnAnimDataChanged(false);
         this.initializeStates();
         this.actionContext.setGroup(ActionGroup.getActionGroup("zombie"));
      }

      if (this.getThumpTimer() > 0) {
         --this.thumpTimer;
      }

      BaseVehicle var6 = this.getNearVehicle();
      if (var6 != null && this.target == null && var6.hasLightbar() && var6.lightbarSirenMode.get() > 0) {
         VehiclePart var7 = var6.getUseablePart(this, false);
         if (var7 != null && var7.getSquare().DistTo((IsoMovingObject)this) < 0.7F) {
            this.setThumpTarget(var6);
         }
      }

      this.doDeferredMovement();
      if (this.zombieSoundInstance != -1L) {
         if (this.target instanceof IsoPlayer) {
            var2 = (float)(Rand.Next(40) + 60) / 100.0F;
            javafmod.FMOD_Studio_SetParameter(this.zombieSoundInstance, "Aggitation", var2);
         } else {
            javafmod.FMOD_Studio_SetParameter(this.zombieSoundInstance, "Aggitation", 0.0F);
         }

         javafmod.FMOD_Studio_EventInstance3D(this.zombieSoundInstance, this.x - IsoPlayer.getInstance().x, this.y - IsoPlayer.getInstance().y, 0.0F);
      }

      this.updateEmitter();
      if (this.spotSoundDelay > 0) {
         --this.spotSoundDelay;
      }

      if (GameClient.bClient) {
         GameClient.instance.RecentlyDied.clear();
         if (this.lastRemoteUpdate > 800 && (this.legsSprite.CurrentAnim.name.equals("ZombieDeath") || this.legsSprite.CurrentAnim.name.equals("ZombieStaggerBack") || this.legsSprite.CurrentAnim.name.equals("ZombieGetUp"))) {
            DebugLog.log(DebugType.Zombie, "removing stale zombie 800 id=" + this.OnlineID);
            VirtualZombieManager.instance.removeZombieFromWorld(this);
            return;
         }

         if (GameClient.bFastForward) {
            VirtualZombieManager.instance.removeZombieFromWorld(this);
            return;
         }
      }

      if (GameClient.bClient && this.lastRemoteUpdate < 2000 && this.lastRemoteUpdate + 1000 / PerformanceSettings.getLockFPS() > 2000) {
         DebugLog.log(DebugType.Zombie, "lastRemoteUpdate 2000+ id=" + this.OnlineID);
      }

      this.lastRemoteUpdate = (short)(this.lastRemoteUpdate + 1000 / PerformanceSettings.getLockFPS());
      if (!GameClient.bClient || this.bRemote && this.lastRemoteUpdate <= 5000) {
         this.sprite = this.legsSprite;
         if (this.sprite != null) {
            this.updateCharacterTextureAnimTime();
            int var12;
            if (this.bRemote && GameClient.bClient) {
               this.Collidable = true;
               this.shootable = true;
               this.changeState(IdleState.instance());
               if (this.thumpFlag != 0) {
                  if (SoundManager.instance.isListenerInRange(this.x, this.y, 20.0F)) {
                     ZombieThumpManager.instance.addCharacter(this);
                  } else {
                     this.thumpFlag = 0;
                  }
               }

               if (this.mpIdleSound) {
                  if (SoundManager.instance.isListenerInRange(this.x, this.y, 20.0F)) {
                     String var13 = this.isFemale() ? "FemaleZombieIdle" : "MaleZombieIdle";
                     if (!this.getEmitter().isPlaying(var13)) {
                        ZombieVocalsManager.instance.addCharacter(this);
                     }
                  }

                  this.mpIdleSound = false;
               }

               if (this.vehicle4testCollision != null) {
                  BaseVehicle var14 = this.vehicle4testCollision;
                  this.vehicle4testCollision = null;
                  IsoPlayer var9 = (IsoPlayer)Type.tryCastTo(var14.getDriver(), IsoPlayer.class);
                  if (var14.isEngineRunning() && var9 != null && var9.isLocalPlayer()) {
                     if (this.isProne()) {
                        var12 = var14.testCollisionWithProneCharacter(this, false);
                        if (var12 > 0) {
                           super.update();
                           return;
                        }
                     } else {
                        float var15 = var14.getSpeed2D();
                        Vector2 var5 = (Vector2)((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).alloc();
                        if (var15 > 0.05F && var14.testCollisionWithCharacter(this, 0.3F, var5) != null) {
                           ((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).release(var5);
                           var14.hitCharacter(this);
                           super.update();
                           return;
                        }

                        ((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).release(var5);
                     }
                  }
               }

               super.update();
               this.separate();
            } else if (GameServer.bServer && this.bIndoorZombie) {
               super.update();
               if (GameServer.bServer && GameServer.doSendZombies()) {
                  GameServer.sendZombie(this);
               }

            } else {
               this.BonusSpotTime = PZMath.clamp(this.BonusSpotTime - GameTime.instance.getMultiplier(), 0.0F, Float.MAX_VALUE);
               this.TimeSinceSeenFlesh = PZMath.clamp(this.TimeSinceSeenFlesh + GameTime.instance.getMultiplier(), 0.0F, Float.MAX_VALUE);
               if (this.getStateMachine().getCurrent() != ClimbThroughWindowState.instance() && this.getStateMachine().getCurrent() != ClimbOverFenceState.instance() && this.getStateMachine().getCurrent() != CrawlingZombieTurnState.instance() && this.getStateMachine().getCurrent() != ZombieHitReactionState.instance() && this.getStateMachine().getCurrent() != ZombieFallDownState.instance()) {
                  this.setCollidable(true);
                  LuaEventManager.triggerEvent("OnZombieUpdate", this);
                  if (Core.bLastStand && this.getStateMachine().getCurrent() != ThumpState.instance() && this.getStateMachine().getCurrent() != AttackState.instance() && this.TimeSinceSeenFlesh > 120.0F && Rand.Next(36000) == 0) {
                     IsoPlayer var11 = null;
                     float var8 = 1000000.0F;

                     for(var12 = 0; var12 < IsoPlayer.numPlayers; ++var12) {
                        if (IsoPlayer.players[var12] != null && IsoPlayer.players[var12].DistTo(this) < var8 && !IsoPlayer.players[var12].isDead()) {
                           var8 = IsoPlayer.players[var12].DistTo(this);
                           var11 = IsoPlayer.players[var12];
                        }
                     }

                     if (var11 != null) {
                        this.AllowRepathDelay = -1.0F;
                        this.pathToCharacter(var11);
                     }

                  } else if (this.Health > 0.0F && this.vehicle4testCollision != null && this.testCollideWithVehicles(this.vehicle4testCollision)) {
                     this.vehicle4testCollision = null;
                  } else if (this.Health > 0.0F && this.vehicle4testCollision != null && this.isCollidedWithVehicle()) {
                     this.vehicle4testCollision.hitCharacter(this);
                     super.update();
                  } else {
                     this.vehicle4testCollision = null;
                     if (this.BonusSpotTime > 0.0F && this.spottedLast != null && !((IsoGameCharacter)this.spottedLast).isDead()) {
                        this.spotted(this.spottedLast, true);
                     }

                     if (GameServer.bServer && this.getStateMachine().getCurrent() == BurntToDeath.instance()) {
                        DebugLog.log(DebugType.Zombie, "Zombie is burning " + this.OnlineID);
                     }

                     super.update();
                     if (VirtualZombieManager.instance.isReused(this)) {
                        DebugLog.log(DebugType.Zombie, "Zombie added to ReusableZombies after super.update - RETURNING " + this);
                     } else {
                        if (GameServer.bServer && (GameServer.doSendZombies() || this.getStateMachine().getCurrent() == StaggerBackState.instance() || this.getStateMachine().getCurrent() == BurntToDeath.instance())) {
                           GameServer.sendZombie(this);
                        }

                        if (this.getStateMachine().getCurrent() != ClimbThroughWindowState.instance() && this.getStateMachine().getCurrent() != ClimbOverFenceState.instance() && this.getStateMachine().getCurrent() != CrawlingZombieTurnState.instance()) {
                           this.ensureOnTile();
                           State var10 = this.stateMachine.getCurrent();
                           if (var10 != StaggerBackState.instance() && var10 != BurntToDeath.instance() && var10 != FakeDeadZombieState.instance() && var10 != ZombieFallDownState.instance() && var10 != ZombieOnGroundState.instance() && var10 != ZombieHitReactionState.instance() && var10 != ZombieGetUpState.instance()) {
                              if (GameServer.bServer && this.OnlineID == -1) {
                                 this.OnlineID = ServerMap.instance.getUniqueZombieId();
                              } else {
                                 IsoSpriteInstance var10000;
                                 if (var10 == PathFindState.instance() && this.finder.progress == AStarPathFinder.PathFindProgress.notyetfound) {
                                    if (this.bCrawling) {
                                       this.PlayAnim("ZombieCrawl");
                                       this.def.AnimFrameIncrease = 0.0F;
                                    } else {
                                       this.PlayAnim("ZombieIdle");
                                       this.def.AnimFrameIncrease = 0.08F + (float)Rand.Next(1000) / 8000.0F;
                                       var10000 = this.def;
                                       var10000.AnimFrameIncrease *= 0.5F;
                                    }
                                 } else if (var10 != AttackState.instance() && var10 != AttackVehicleState.instance() && (this.nx != this.x || this.ny != this.y)) {
                                    if (this.walkVariantUse == null || var10 != LungeState.instance()) {
                                       this.walkVariantUse = this.walkVariant;
                                    }

                                    if (this.bCrawling) {
                                       this.walkVariantUse = "ZombieCrawl";
                                    }

                                    if (var10 != ZombieIdleState.instance() && var10 != StaggerBackState.instance() && var10 != ThumpState.instance() && var10 != FakeDeadZombieState.instance()) {
                                       if (this.bRunning) {
                                          this.PlayAnim("Run");
                                          this.def.setFrameSpeedPerFrame(0.33F);
                                       } else {
                                          this.PlayAnim(this.walkVariantUse);
                                          this.def.setFrameSpeedPerFrame(0.26F);
                                          var10000 = this.def;
                                          var10000.AnimFrameIncrease *= this.speedMod;
                                       }

                                       this.setShootable(true);
                                    }
                                 }
                              }

                              this.shootable = true;
                              this.solid = true;
                              this.tryThump((IsoGridSquare)null);
                              this.damageSheetRope();
                              this.AllowRepathDelay = PZMath.clamp(this.AllowRepathDelay - GameTime.instance.getMultiplier(), 0.0F, Float.MAX_VALUE);
                              int var3 = this.getSandboxMemoryDuration();
                              if (this.TimeSinceSeenFlesh > (float)var3 && this.target != null) {
                                 this.target = null;
                              }

                              if (this.target instanceof IsoGameCharacter && ((IsoGameCharacter)this.target).ReanimatedCorpse != null) {
                                 this.target = null;
                              }

                              if (this.target != null) {
                                 this.vectorToTarget.x = this.target.getX();
                                 this.vectorToTarget.y = this.target.getY();
                                 Vector2 var16 = this.vectorToTarget;
                                 var16.x -= this.getX();
                                 var16 = this.vectorToTarget;
                                 var16.y -= this.getY();
                                 this.updateZombieTripping();
                              }

                              if (IsoPlayer.getInstance() != null) {
                                 this.nextIdleSound -= GameTime.getInstance().getMultiplier() / 1.6F;
                                 if (this.nextIdleSound < 0.0F && (this.getCurrentState() == WalkTowardState.instance() || this.getCurrentState() == PathFindState.instance())) {
                                    this.nextIdleSound = (float)Rand.Next(300, 600);
                                    if (SoundManager.instance.isListenerInRange(this.getX(), this.getY(), 20.0F)) {
                                       String var4 = this.isFemale() ? "FemaleZombieIdle" : "MaleZombieIdle";
                                       if (!this.emitter.isPlaying(var4)) {
                                          ZombieVocalsManager.instance.addCharacter(this);
                                       }
                                    }
                                 }
                              }

                              if (GameServer.bServer && (this.getCurrentState() == WalkTowardState.instance() || this.getCurrentState() == PathFindState.instance()) && Rand.Next(Rand.AdjustForFramerate(360)) == 0) {
                                 this.mpIdleSound = true;
                              }

                              if (this.getCurrentState() != PathFindState.instance() && this.getCurrentState() != WalkTowardState.instance() && this.getCurrentState() != ClimbThroughWindowState.instance()) {
                                 this.setLastHeardSound(-1, -1, -1);
                              }

                              if (this.TimeSinceSeenFlesh > 240.0F && this.timeSinceRespondToSound > 5.0F) {
                                 this.RespondToSound();
                              }

                              this.timeSinceRespondToSound += GameTime.getInstance().getMultiplier() / 1.6F;
                              this.separate();
                              this.updateSearchForCorpse();
                              if (this.TimeSinceSeenFlesh > 2000.0F && this.timeSinceRespondToSound > 2000.0F) {
                                 ZombieGroupManager.instance.update(this);
                              }

                           }
                        }
                     }
                  }
               } else {
                  super.update();
                  if (GameServer.bServer && GameServer.doSendZombies()) {
                     GameServer.sendZombie(this);
                  }

               }
            }
         }
      } else {
         DebugLog.log(DebugType.Zombie, "removing stale zombie 5000 id=" + this.OnlineID);
         VirtualZombieManager.instance.removeZombieFromWorld(this);
      }
   }

   protected void calculateStats() {
   }

   private void updateZombieTripping() {
      if (this.speedType == 1 && StringUtils.isNullOrEmpty(this.getBumpType()) && this.target != null && Rand.NextBool(Rand.AdjustForFramerate(750))) {
         this.setBumpType("trippingFromSprint");
      }

   }

   private void updateVocalProperties() {
      if (SoundManager.instance.isListenerInRange(this.getX(), this.getY(), 20.0F)) {
         if (this.vocalEvent != 0L) {
            float var1 = 0.0F;
            if (this.target != null) {
               var1 = 1.0F;
            }

            IsoGameCharacter var2 = (IsoGameCharacter)Type.tryCastTo(this.target, IsoGameCharacter.class);
            if (var2 != null && var2.isDead()) {
               var1 = 0.0F;
            }

            float var3 = 0.0F;
            if (IsoPlayer.getInstance().getCurrentSquare() != null && this.getCurrentSquare() != null) {
               if (IsoPlayer.getInstance().getBuilding() != this.getBuilding()) {
                  var3 += 0.5F;
               }

               if (IsoPlayer.getInstance().getCurrentSquare().getRoom() != this.getCurrentSquare().getRoom()) {
                  var3 += 0.25F;
               }

               if (IsoPlayer.getInstance().getCurrentSquare().getZ() != this.getCurrentSquare().getZ()) {
                  var3 += 0.35F;
               }
            }

            var3 = Math.min(var3, 1.0F);
            javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Aggitation", var1);
            javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Muffle", var3);
            javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Rand", Rand.Next(0.0F, 3.0F));
            if (this.stateMachine.getCurrent() == ZombieHitReactionState.instance()) {
               javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Hit", 1.0F);
            } else if (this.stateMachine.getCurrent() != ZombieFallDownState.instance() && this.stateMachine.getCurrent() != ZombieOnGroundState.instance()) {
               javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Hit", 0.0F);
            } else {
               javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Hit", 0.5F);
            }
         }

      }
   }

   private void updateSearchForCorpse() {
      if (!this.bCrawling && this.target == null && this.eatBodyTarget == null) {
         if (this.bodyToEat != null) {
            if (this.bodyToEat.getStaticMovingObjectIndex() == -1) {
               this.bodyToEat = null;
            } else if (!this.isEatingOther(this.bodyToEat) && this.bodyToEat.getEatingZombies().size() >= 3) {
               this.bodyToEat = null;
            }
         }

         if (this.bodyToEat == null) {
            this.checkForCorpseTimer -= GameTime.getInstance().getMultiplier() / 1.6F;
            if (this.checkForCorpseTimer <= 0.0F) {
               this.checkForCorpseTimer = 10000.0F;
               tempBodies.clear();

               for(int var1 = -10; var1 < 10; ++var1) {
                  for(int var2 = -10; var2 < 10; ++var2) {
                     IsoGridSquare var3 = this.getCell().getGridSquare((double)(this.getX() + (float)var1), (double)(this.getY() + (float)var2), (double)this.getZ());
                     if (var3 != null) {
                        IsoDeadBody var4 = var3.getDeadBody();
                        if (var4 != null && !var4.isSkeleton() && !var4.isZombie() && var4.getEatingZombies().size() < 3 && !PolygonalMap2.instance.lineClearCollide(this.getX(), this.getY(), var4.x, var4.y, (int)this.getZ(), (IsoMovingObject)null, false, true)) {
                           tempBodies.add(var4);
                        }
                     }
                  }
               }

               if (!tempBodies.isEmpty()) {
                  this.bodyToEat = (IsoDeadBody)PZArrayUtil.pickRandom((List)tempBodies);
                  tempBodies.clear();
               }
            }
         }

         if (this.bodyToEat != null && this.isCurrentState(ZombieIdleState.instance())) {
            if (this.DistToSquared(this.bodyToEat) > 1.0F) {
               Vector2 var5 = tempo.set(this.x - this.bodyToEat.x, this.y - this.bodyToEat.y);
               var5.setLength(0.5F);
               this.pathToLocationF(this.bodyToEat.getX() + var5.x, this.bodyToEat.getY() + var5.y, this.bodyToEat.getZ());
            }

         }
      } else {
         this.checkForCorpseTimer = 10000.0F;
         this.bodyToEat = null;
      }
   }

   private void damageSheetRope() {
      if (Rand.Next(30) == 0 && this.current != null && (this.current.Is(IsoFlagType.climbSheetN) || this.current.Is(IsoFlagType.climbSheetE) || this.current.Is(IsoFlagType.climbSheetS) || this.current.Is(IsoFlagType.climbSheetW))) {
         IsoObject var1 = this.current.getSheetRope();
         if (var1 != null) {
            var1.sheetRopeHealth -= (float)Rand.Next(5, 15);
            if (var1.sheetRopeHealth < 40.0F) {
               this.current.damageSpriteSheetRopeFromBottom((IsoPlayer)null, this.current.Is(IsoFlagType.climbSheetN) || this.current.Is(IsoFlagType.climbSheetS));
               this.current.RecalcProperties();
            }

            if (var1.sheetRopeHealth <= 0.0F) {
               this.current.removeSheetRopeFromBottom((IsoPlayer)null, this.current.Is(IsoFlagType.climbSheetN) || this.current.Is(IsoFlagType.climbSheetS));
            }
         }
      }

   }

   public void getZombieWalkTowardSpeed(float var1, float var2, Vector2 var3) {
      float var4 = 1.0F;
      var4 = var2 / 24.0F;
      if (var4 < 1.0F) {
         var4 = 1.0F;
      }

      if (var4 > 1.3F) {
         var4 = 1.3F;
      }

      var3.setLength((var1 * this.getSpeedMod() + 0.006F) * var4);
      if (SandboxOptions.instance.Lore.Speed.getValue() == 1 && !this.inactive || this.speedType == 1) {
         var3.setLength(0.08F);
         this.bRunning = true;
      }

      if (var3.getLength() > var2) {
         var3.setLength(var2);
      }

   }

   public void getZombieLungeSpeed(Vector2 var1) {
      float var2 = this.LungeTimer / 180.0F;
      float var3 = this.getPathSpeed() + 0.03F * var2;
      var1.normalize();
      var1.setLength(var3 * this.getSpeedMod());
      this.bRunning = false;
      if (GameServer.bServer) {
         var2 = 1.0F;
      }

      if (SandboxOptions.instance.Lore.Speed.getValue() == 1 && !this.inactive || this.speedType == 1) {
         var1.setLength(0.08F + 0.01F * (1.0F - var2));
         this.bRunning = true;
      }

   }

   public void getZombieLungeSpeed(float var1, float var2, Vector2 var3) {
      float var4 = 1.0F;
      var4 = var2 / 24.0F;
      if (var4 < 1.0F) {
         var4 = 1.0F;
      }

      if (var4 > 1.3F) {
         var4 = 1.3F;
      }

      var3.setLength((var1 * this.getSpeedMod() + 0.006F) * var4);
      if (SandboxOptions.instance.Lore.Speed.getValue() == 1 && !this.inactive || this.speedType == 1) {
         var3.setLength(0.08F);
         this.bRunning = true;
      }

      if (var3.getLength() > var2) {
         var3.setLength(var2);
      }

   }

   public boolean tryThump(IsoGridSquare var1) {
      if (this.Ghost) {
         return false;
      } else if (this.bCrawling) {
         return false;
      } else {
         State var2 = this.getCurrentState();
         boolean var3 = var2 == PathFindState.instance() || var2 == LungeState.instance() || var2 == WalkTowardState.instance();
         if (!var3) {
            return false;
         } else {
            IsoGridSquare var4;
            if (var1 != null) {
               var4 = var1;
            } else {
               var4 = this.getFeelerTile(this.getFeelersize());
            }

            if (var4 != null && this.current != null) {
               IsoObject var5 = this.current.testCollideSpecialObjects(var4);
               if (var5 instanceof Thumpable) {
                  if (var5 instanceof IsoWindow && ((IsoWindow)var5).canClimbThrough(this)) {
                     this.climbThroughWindow((IsoWindow)var5);
                     return true;
                  }

                  if (var5 instanceof IsoThumpable && ((IsoThumpable)var5).canClimbThrough(this)) {
                     this.climbThroughWindow((IsoThumpable)var5);
                     return true;
                  }

                  if (var5 instanceof IsoWindow && ((IsoWindow)var5).getThumpableFor(this) != null) {
                     this.setThumpTarget(((IsoWindow)var5).getThumpableFor(this));
                     this.setPath2((PolygonalMap2.Path)null);
                     return true;
                  }

                  if (var5 instanceof IsoThumpable && ((IsoThumpable)var5).isThumpable() || var5 instanceof IsoWindow && !((IsoWindow)var5).isDestroyed() || var5 instanceof IsoDoor && !((IsoDoor)var5).isDestroyed() && !((IsoDoor)var5).open) {
                     int var7 = var4.getX() - this.current.getX();
                     int var8 = var4.getY() - this.current.getY();
                     IsoDirections var9 = IsoDirections.N;
                     if (var7 < 0 && Math.abs(var7) > Math.abs(var8)) {
                        var9 = IsoDirections.S;
                     }

                     if (var7 < 0 && Math.abs(var7) <= Math.abs(var8)) {
                        var9 = IsoDirections.SW;
                     }

                     if (var7 > 0 && Math.abs(var7) > Math.abs(var8)) {
                        var9 = IsoDirections.W;
                     }

                     if (var7 > 0 && Math.abs(var7) <= Math.abs(var8)) {
                        var9 = IsoDirections.SE;
                     }

                     if (var8 < 0 && Math.abs(var7) < Math.abs(var8)) {
                        var9 = IsoDirections.N;
                     }

                     if (var8 < 0 && Math.abs(var7) >= Math.abs(var8)) {
                        var9 = IsoDirections.NW;
                     }

                     if (var8 > 0 && Math.abs(var7) < Math.abs(var8)) {
                        var9 = IsoDirections.E;
                     }

                     if (var8 > 0 && Math.abs(var7) >= Math.abs(var8)) {
                        var9 = IsoDirections.NE;
                     }

                     if (this.getDir() == var9) {
                        boolean var10 = (var2 == PathFindState.instance() || var2 == WalkTowardState.instance()) && this.getPathFindBehavior2().isGoalSound();
                        if (SandboxOptions.instance.Lore.ThumpNoChasing.getValue() || this.target != null || var10) {
                           this.setThumpTarget((Thumpable)var5);
                           this.setPath2((PolygonalMap2.Path)null);
                        }
                     }

                     return true;
                  }
               }

               if (var5 != null && IsoWindowFrame.isWindowFrame(var5)) {
                  this.climbThroughWindowFrame(var5);
                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }
   }

   public void Wander() {
      GameServer.sendZombie(this);
      this.changeState(ZombieIdleState.instance());
   }

   public void DoZombieInventory() {
      if (!this.isReanimatedPlayer() && !this.wasFakeDead()) {
         this.getInventory().removeAllItems();
         this.getInventory().setSourceGrid(this.getCurrentSquare());
         this.wornItems.setFromItemVisuals(this.itemVisuals);
         this.wornItems.addItemsToItemContainer(this.getInventory());
         if (!GameClient.bClient) {
            IsoBuilding var1 = this.getCurrentBuilding();
            if (var1 != null && var1.getDef() != null && var1.getDef().getKeyId() != -1 && Rand.Next(4) == 0) {
               String var2 = "Base.Key" + (Rand.Next(5) + 1);
               InventoryItem var3 = this.inventory.AddItem(var2);
               var3.setKeyId(var1.getDef().getKeyId());
            }

            if (this.itemsToSpawnAtDeath != null && !this.itemsToSpawnAtDeath.isEmpty()) {
               for(int var4 = 0; var4 < this.itemsToSpawnAtDeath.size(); ++var4) {
                  this.inventory.AddItem((InventoryItem)this.itemsToSpawnAtDeath.get(var4));
               }

               this.itemsToSpawnAtDeath.clear();
            }
         }

      }
   }

   public void changeSpeed(int var1) {
      this.walkVariant = "ZombieWalk";
      this.speedType = var1;
      IsoSpriteInstance var10000;
      if (this.speedType == 3) {
         this.speedMod = 0.55F;
         this.speedMod += (float)Rand.Next(1500) / 10000.0F;
         this.walkVariant = this.walkVariant + "1";
         this.def.setFrameSpeedPerFrame(0.24F);
         var10000 = this.def;
         var10000.AnimFrameIncrease *= this.speedMod;
      } else {
         this.bLunger = true;
         this.speedMod = 0.85F;
         this.walkVariant = this.walkVariant + "2";
         this.speedMod += (float)Rand.Next(1500) / 10000.0F;
         this.def.setFrameSpeedPerFrame(0.24F);
         var10000 = this.def;
         var10000.AnimFrameIncrease *= this.speedMod;
      }

      this.PathSpeed = baseSpeed * this.speedMod;
      this.wanderSpeed = this.PathSpeed;
   }

   public void DoZombieStats() {
      if (SandboxOptions.instance.Lore.Cognition.getValue() == 1) {
         this.cognition = 1;
      }

      if (SandboxOptions.instance.Lore.Cognition.getValue() == 4) {
         this.cognition = Rand.Next(0, 2);
      }

      if (this.strength == -1 && SandboxOptions.instance.Lore.Strength.getValue() == 1) {
         this.strength = 5;
      }

      if (this.strength == -1 && SandboxOptions.instance.Lore.Strength.getValue() == 2) {
         this.strength = 3;
      }

      if (this.strength == -1 && SandboxOptions.instance.Lore.Strength.getValue() == 3) {
         this.strength = 1;
      }

      if (this.strength == -1 && SandboxOptions.instance.Lore.Strength.getValue() == 4) {
         this.strength = Rand.Next(1, 5);
      }

      if (this.speedType == -1) {
         this.speedType = SandboxOptions.instance.Lore.Speed.getValue();
         if (this.speedType == 4) {
            this.speedType = Rand.Next(2);
         }

         if (this.inactive) {
            this.speedType = 3;
         }
      }

      IsoSpriteInstance var10000;
      if (this.bCrawling) {
         this.speedMod = 0.3F;
         this.speedMod += (float)Rand.Next(1500) / 10000.0F;
         var10000 = this.def;
         var10000.AnimFrameIncrease *= 0.8F;
      } else if (SandboxOptions.instance.Lore.Speed.getValue() != 3 && this.speedType != 3 && Rand.Next(3) == 0) {
         if (SandboxOptions.instance.Lore.Speed.getValue() != 3 || this.speedType != 3) {
            this.bLunger = true;
            this.speedMod = 0.85F;
            this.walkVariant = this.walkVariant + "2";
            this.speedMod += (float)Rand.Next(1500) / 10000.0F;
            this.def.setFrameSpeedPerFrame(0.24F);
            var10000 = this.def;
            var10000.AnimFrameIncrease *= this.speedMod;
         }
      } else {
         this.speedMod = 0.55F;
         this.speedMod += (float)Rand.Next(1500) / 10000.0F;
         this.walkVariant = this.walkVariant + "1";
         this.def.setFrameSpeedPerFrame(0.24F);
         var10000 = this.def;
         var10000.AnimFrameIncrease *= this.speedMod;
      }

      this.walkType = Integer.toString(Rand.Next(5) + 1);
      if (this.speedType == 1) {
         this.setTurnDelta(1.0F);
         this.walkType = "sprint" + this.walkType;
      }

      if (this.speedType == 3) {
         this.walkType = Integer.toString(Rand.Next(3) + 1);
         this.walkType = "slow" + this.walkType;
      }

      this.PathSpeed = baseSpeed * this.speedMod;
      this.wanderSpeed = this.PathSpeed;
      this.initCanCrawlUnderVehicle();
   }

   public void DoZombieSpeeds(float var1) {
      IsoSpriteInstance var10000;
      if (this.bCrawling) {
         this.speedMod = var1;
         var10000 = this.def;
         var10000.AnimFrameIncrease *= 0.8F;
      } else if (Rand.Next(3) == 0 && SandboxOptions.instance.Lore.Speed.getValue() != 3) {
         if (SandboxOptions.instance.Lore.Speed.getValue() != 3) {
            this.bLunger = true;
            this.speedMod = var1;
            this.walkVariant = this.walkVariant + "2";
            this.def.setFrameSpeedPerFrame(0.24F);
            var10000 = this.def;
            var10000.AnimFrameIncrease *= this.speedMod;
         }
      } else {
         this.speedMod = var1;
         this.speedMod += (float)Rand.Next(1500) / 10000.0F;
         this.walkVariant = this.walkVariant + "1";
         this.def.setFrameSpeedPerFrame(0.24F);
         var10000 = this.def;
         var10000.AnimFrameIncrease *= this.speedMod;
      }

      this.PathSpeed = baseSpeed * this.speedMod;
      this.wanderSpeed = this.PathSpeed;
   }

   public boolean isFakeDead() {
      return this.bFakeDead;
   }

   public void setFakeDead(boolean var1) {
      if (var1 && Rand.Next(2) == 0) {
         this.setCrawlerType(2);
      }

      this.bFakeDead = var1;
   }

   public boolean isForceFakeDead() {
      return this.bForceFakeDead;
   }

   public void setForceFakeDead(boolean var1) {
      this.bForceFakeDead = var1;
   }

   public void HitSilence(HandWeapon var1, IsoZombie var2, float var3, boolean var4, float var5) {
      super.HitSilence(var1, var2, var3, var4, var5);
      this.target = var2;
      if (this.Health <= 0.0F && !this.isOnDeathDone()) {
         this.DoZombieInventory();
         this.setOnDeathDone(true);
      }

      this.TimeSinceSeenFlesh = 0.0F;
   }

   protected void DoDeathSilence(HandWeapon var1, IsoGameCharacter var2) {
      if (this.Health <= 0.0F && !this.isOnDeathDone()) {
         this.DoZombieInventory();
         this.setOnDeathDone(true);
      }

      super.DoDeathSilence(var1, var2);
   }

   public void Hit(BaseVehicle var1, float var2, float var3, Vector2 var4) {
      this.AttackedBy = var1.getDriver();
      this.setHitDir(var4);
      this.setHitForce(var2 * 0.15F);
      int var5 = (new Float(var2 * 6.0F)).intValue();
      this.target = var1.getCharacter(0);
      if (!this.bStaggerBack && !this.isOnFloor() && this.getCurrentState() != ZombieGetUpState.instance() && this.getCurrentState() != ZombieOnGroundState.instance()) {
         if (var3 > 0.0F) {
            this.setHitFromBehind(true);
            if (Rand.Next(100) <= var5) {
               if (Rand.Next(8) == 0) {
                  this.setBecomeCrawler(true);
               }

               this.bStaggerBack = true;
               this.bKnockedDown = true;
            } else {
               this.bStaggerBack = true;
            }
         } else if (var2 < 3.0F) {
            if (Rand.Next(100) <= var5) {
               if (Rand.Next(8) == 0) {
                  this.setBecomeCrawler(true);
               }

               this.bStaggerBack = true;
               this.bKnockedDown = true;
            } else {
               this.bStaggerBack = true;
            }
         } else if (var2 < 10.0F) {
            if (Rand.Next(8) == 0) {
               this.setBecomeCrawler(true);
            }

            this.bStaggerBack = true;
            this.bKnockedDown = true;
         } else {
            this.DoZombieInventory();
            this.Kill(var1.getCharacter(0));
         }
      } else {
         if (this.isFakeDead()) {
            this.setFakeDead(false);
         }

         this.setHitReaction("Floor");
         this.Health -= var2 / 5.0F;
         if (this.Health <= 0.0F) {
            this.DoZombieInventory();
            this.Kill(var1.getCharacter(0));
         }
      }

      if (!((float)Rand.Next(10) > var2)) {
         float var6 = 0.6F;
         if (SandboxOptions.instance.BloodLevel.getValue() > 1) {
            int var7 = Rand.Next(4, 10);
            if (var7 < 1) {
               var7 = 1;
            }

            if (Core.bLastStand) {
               var7 *= 3;
            }

            switch(SandboxOptions.instance.BloodLevel.getValue()) {
            case 2:
               var7 /= 2;
            case 3:
            default:
               break;
            case 4:
               var7 *= 2;
               break;
            case 5:
               var7 *= 5;
            }

            for(int var8 = 0; var8 < var7; ++var8) {
               this.splatBlood(2, 0.3F);
            }
         }

         if (SandboxOptions.instance.BloodLevel.getValue() > 1) {
            this.splatBloodFloorBig(0.3F);
         }

         if (SandboxOptions.instance.BloodLevel.getValue() > 1) {
            new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 1.5F, this.getHitDir().y * 1.5F);
            tempo.x = this.getHitDir().x;
            tempo.y = this.getHitDir().y;
            byte var12 = 3;
            byte var11 = 0;
            byte var9 = 1;
            switch(SandboxOptions.instance.BloodLevel.getValue()) {
            case 1:
               var9 = 0;
               break;
            case 2:
               var9 = 1;
               var12 = 5;
               var11 = 2;
            case 3:
            default:
               break;
            case 4:
               var9 = 3;
               var12 = 2;
               break;
            case 5:
               var9 = 10;
               var12 = 0;
            }

            for(int var10 = 0; var10 < var9; ++var10) {
               if (Rand.Next(this.isCloseKilled() ? 8 : var12) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 1.5F, this.getHitDir().y * 1.5F);
               }

               if (Rand.Next(this.isCloseKilled() ? 8 : var12) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 1.8F, this.getHitDir().y * 1.8F);
               }

               if (Rand.Next(this.isCloseKilled() ? 8 : var12) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 1.9F, this.getHitDir().y * 1.9F);
               }

               if (Rand.Next(this.isCloseKilled() ? 4 : var11) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 3.9F, this.getHitDir().y * 3.9F);
               }

               if (Rand.Next(this.isCloseKilled() ? 4 : var11) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 3.8F, this.getHitDir().y * 3.8F);
               }

               if (Rand.Next(this.isCloseKilled() ? 9 : 6) == 0) {
                  new IsoZombieGiblets(IsoZombieGiblets.GibletType.Eye, this.getCell(), this.getX(), this.getY(), this.getZ() + var6, this.getHitDir().x * 0.8F, this.getHitDir().y * 0.8F);
               }
            }
         }

      }
   }

   public void hitConsequences(HandWeapon var1, IsoGameCharacter var2, boolean var3, float var4) {
      if (!this.isOnlyJawStab() || this.isCloseKilled()) {
         if (this.shouldBecomeFakeDead(var2)) {
            this.setFakeDead(true);
            this.Health = 0.0F;
         }

         super.hitConsequences(var1, var2, var3, var4);
         if (DebugLog.isEnabled(DebugType.Combat)) {
            DebugLog.Combat.debugln(this + " got hit for " + var4);
         }

         if (this.vocalEvent != 0L) {
            javafmod.FMOD_Studio_SetParameter(this.vocalEvent, "Hit", 1.0F);
         }

         this.actionContext.reportEvent("wasHit");
         String var5 = var2.getVariableString("ZombieHitReaction");
         if ("Shot".equals(var5)) {
            var5 = "ShotBelly";
            var2.isCrit = Rand.Next(100) < ((IsoPlayer)var2).calculateCritChance(this);
            Vector2 var6 = var2.getForwardDirection();
            Vector2 var7 = this.getHitAngle();
            double var8 = (double)(var6.x * var7.y - var6.y * var7.x);
            double var10 = var8 >= 0.0D ? 1.0D : -1.0D;
            double var12 = (double)(var6.x * var7.x + var6.y * var7.y);
            double var14 = Math.acos(var12) * var10;
            if (var14 < 0.0D) {
               var14 += 6.283185307179586D;
            }

            String var16 = "";
            int var17;
            if (Math.toDegrees(var14) < 45.0D) {
               this.setHitFromBehind(true);
               var16 = "S";
               var17 = Rand.Next(9);
               if (var17 > 6) {
                  var16 = "L";
               }

               if (var17 > 4) {
                  var16 = "R";
               }
            }

            if (Math.toDegrees(var14) > 45.0D && Math.toDegrees(var14) < 90.0D) {
               this.setHitFromBehind(true);
               if (Rand.Next(4) == 0) {
                  var16 = "S";
               } else {
                  var16 = "R";
               }
            }

            if (Math.toDegrees(var14) > 90.0D && Math.toDegrees(var14) < 135.0D) {
               var16 = "R";
            }

            if (Math.toDegrees(var14) > 135.0D && Math.toDegrees(var14) < 180.0D) {
               if (Rand.Next(4) == 0) {
                  var16 = "N";
               } else {
                  var16 = "R";
               }
            }

            if (Math.toDegrees(var14) > 180.0D && Math.toDegrees(var14) < 225.0D) {
               var16 = "N";
               var17 = Rand.Next(9);
               if (var17 > 6) {
                  var16 = "L";
               }

               if (var17 > 4) {
                  var16 = "R";
               }
            }

            if (Math.toDegrees(var14) > 225.0D && Math.toDegrees(var14) < 270.0D) {
               if (Rand.Next(4) == 0) {
                  var16 = "N";
               } else {
                  var16 = "L";
               }
            }

            if (Math.toDegrees(var14) > 270.0D && Math.toDegrees(var14) < 315.0D) {
               this.setHitFromBehind(true);
               var16 = "L";
            }

            if (Math.toDegrees(var14) > 315.0D) {
               if (Rand.Next(4) == 0) {
                  var16 = "S";
               } else {
                  var16 = "L";
               }
            }

            if ("N".equals(var16)) {
               if (this.isHitFromBehind()) {
                  var5 = "ShotBellyStep";
               } else {
                  var17 = Rand.Next(2);
                  switch(var17) {
                  case 0:
                     var5 = "ShotBelly";
                     break;
                  case 1:
                     var5 = "ShotBellyStep";
                  }
               }
            }

            if ("S".equals(var16)) {
               var5 = "ShotBellyStep";
            }

            if ("L".equals(var16) || "R".equals(var16)) {
               if (this.isHitFromBehind()) {
                  var17 = Rand.Next(3);
                  switch(var17) {
                  case 0:
                     var5 = "ShotChest";
                     break;
                  case 1:
                     var5 = "ShotLeg";
                     break;
                  case 2:
                     var5 = "ShotShoulderStep";
                  }
               } else {
                  var17 = Rand.Next(5);
                  switch(var17) {
                  case 0:
                     var5 = "ShotChest";
                     break;
                  case 1:
                     var5 = "ShotChestStep";
                     break;
                  case 2:
                     var5 = "ShotLeg";
                     break;
                  case 3:
                     var5 = "ShotShoulder";
                     break;
                  case 4:
                     var5 = "ShotShoulderStep";
                  }
               }

               var5 = var5 + var16;
            }

            if (var2.isCrit) {
               if ("S".equals(var16)) {
                  var5 = "ShotHeadFwd";
               }

               if ("N".equals(var16)) {
                  var5 = "ShotHeadBwd";
               }

               if (("L".equals(var16) || "R".equals(var16)) && Rand.Next(4) == 0) {
                  var5 = "ShotHeadBwd";
               }
            }

            if (var5.contains("Head")) {
               this.addBlood(BloodBodyPartType.Head, false, true, true);
            } else if (var5.contains("Chest")) {
               this.addBlood(BloodBodyPartType.Torso_Upper, !this.isCrit, this.isCrit, true);
            } else if (var5.contains("Belly")) {
               this.addBlood(BloodBodyPartType.Torso_Lower, !this.isCrit, this.isCrit, true);
            } else {
               boolean var18;
               if (var5.contains("Leg")) {
                  var18 = Rand.Next(2) == 0;
                  if ("L".equals(var16)) {
                     this.addBlood(var18 ? BloodBodyPartType.LowerLeg_L : BloodBodyPartType.UpperLeg_L, !this.isCrit, this.isCrit, true);
                  } else {
                     this.addBlood(var18 ? BloodBodyPartType.LowerLeg_R : BloodBodyPartType.UpperLeg_R, !this.isCrit, this.isCrit, true);
                  }
               } else if (var5.contains("Shoulder")) {
                  var18 = Rand.Next(2) == 0;
                  if ("L".equals(var16)) {
                     this.addBlood(var18 ? BloodBodyPartType.ForeArm_L : BloodBodyPartType.UpperArm_L, !this.isCrit, this.isCrit, true);
                  } else {
                     this.addBlood(var18 ? BloodBodyPartType.ForeArm_R : BloodBodyPartType.UpperArm_R, !this.isCrit, this.isCrit, true);
                  }
               }
            }
         } else if (var1.getCategories().contains("Blunt")) {
            this.addBlood(BloodBodyPartType.FromIndex(Rand.Next(BloodBodyPartType.UpperArm_L.index(), BloodBodyPartType.Groin.index())), false, false, true);
         } else if (!var1.getCategories().contains("Unarmed")) {
            this.addBlood(BloodBodyPartType.FromIndex(Rand.Next(BloodBodyPartType.UpperArm_L.index(), BloodBodyPartType.Groin.index())), false, true, true);
         }

         if ("ShotHeadFwd".equals(var5) && Rand.Next(2) == 0) {
            var5 = "ShotHeadFwd02";
         }

         if (this.getEatBodyTarget() != null) {
            if (this.getVariableBoolean("onknees")) {
               var5 = "OnKnees";
            } else {
               var5 = "Eating";
            }
         }

         this.target = var2;
         if (var5 != null && !"".equals(var5)) {
            this.setHitReaction(var5);
         } else {
            this.bStaggerBack = true;
            this.setHitReaction("");
            if ("LEFT".equals(this.getPlayerAttackPosition()) || "RIGHT".equals(this.getPlayerAttackPosition())) {
               var2.isCrit = false;
            }
         }

         this.bKnockedDown = var2.isCrit || this.isOnFloor() || this.isAlwaysKnockedDown();
         this.checkClimbOverFenceHit();
         this.checkClimbThroughWindowHit();
         if (this.shouldBecomeCrawler(var2)) {
            this.setBecomeCrawler(true);
         }

      }
   }

   private void checkClimbOverFenceHit() {
      if (!this.isOnFloor()) {
         if (this.isCurrentState(ClimbOverFenceState.instance()) && this.getVariableBoolean("ClimbFenceStarted") && !this.isVariable("ClimbFenceOutcome", "fall") && !this.getVariableBoolean("ClimbFenceFlopped")) {
            HashMap var1 = (HashMap)this.StateMachineParams.get(ClimbOverFenceState.instance());
            byte var2 = 3;
            byte var3 = 4;
            int var4 = (Integer)var1.get(Integer.valueOf(var2));
            int var5 = (Integer)var1.get(Integer.valueOf(var3));
            this.climbFenceWindowHit(var4, var5);
         }
      }
   }

   private void checkClimbThroughWindowHit() {
      if (!this.isOnFloor()) {
         if (this.isCurrentState(ClimbThroughWindowState.instance()) && this.getVariableBoolean("ClimbWindowStarted") && !this.isVariable("ClimbWindowOutcome", "fall") && !this.getVariableBoolean("ClimbWindowFlopped")) {
            HashMap var1 = (HashMap)this.StateMachineParams.get(ClimbThroughWindowState.instance());
            byte var2 = 12;
            byte var3 = 13;
            int var4 = (Integer)var1.get(Integer.valueOf(var2));
            int var5 = (Integer)var1.get(Integer.valueOf(var3));
            this.climbFenceWindowHit(var4, var5);
         }
      }
   }

   private void climbFenceWindowHit(int var1, int var2) {
      if (this.getDir() == IsoDirections.W) {
         this.setX((float)var1 + 0.9F);
         this.setLx(this.getX());
      } else if (this.getDir() == IsoDirections.E) {
         this.setX((float)var1 + 0.1F);
         this.setLx(this.getX());
      } else if (this.getDir() == IsoDirections.N) {
         this.setY((float)var2 + 0.9F);
         this.setLy(this.getY());
      } else if (this.getDir() == IsoDirections.S) {
         this.setY((float)var2 + 0.1F);
         this.setLy(this.getY());
      }

      this.bStaggerBack = false;
      this.bKnockedDown = true;
      this.setOnFloor(true);
      this.setFallOnFront(true);
      this.setHitReaction("FenceWindow");
   }

   private boolean shouldBecomeFakeDead(IsoGameCharacter var1) {
      return false;
   }

   private boolean shouldBecomeCrawler(IsoGameCharacter var1) {
      if (this.isBecomeCrawler()) {
         return true;
      } else if (this.isCrawling()) {
         return false;
      } else if (Core.bLastStand) {
         return false;
      } else if (this.isDead()) {
         return false;
      } else if (this.isCloseKilled()) {
         return false;
      } else {
         IsoPlayer var2 = (IsoPlayer)Type.tryCastTo(var1, IsoPlayer.class);
         if (var2 != null && !var2.isAimAtFloor() && var2.bDoShove) {
            return false;
         } else {
            byte var3 = 30;
            if (var2 != null && var2.isAimAtFloor() && var2.bDoShove) {
               if (this.isHitLegsWhileOnFloor()) {
                  var3 = 7;
               } else {
                  var3 = 15;
               }
            }

            return Rand.NextBool(var3);
         }
      }
   }

   public void removeFromWorld() {
      VirtualZombieManager.instance.RemoveZombie(this);
      this.setPath2((PolygonalMap2.Path)null);
      PolygonalMap2.instance.cancelRequest(this);
      if (this.getFinder().progress != AStarPathFinder.PathFindProgress.notrunning && this.getFinder().progress != AStarPathFinder.PathFindProgress.found) {
         this.getFinder().progress = AStarPathFinder.PathFindProgress.notrunning;
      }

      if (this.group != null) {
         this.group.remove(this);
         this.group = null;
      }

      if (GameServer.bServer && this.OnlineID != -1) {
         ServerMap.instance.ZombieMap.remove(this.OnlineID);
         this.OnlineID = -1;
      }

      if (GameClient.bClient) {
         GameClient.instance.removeZombieFromCache(this);
      }

      this.getCell().getZombieList().remove(this);
      super.removeFromWorld();
   }

   public void resetForReuse() {
      this.bCrawling = false;
      this.initializeStates();
      this.actionContext.setGroup(ActionGroup.getActionGroup("zombie"));
      this.advancedAnimator.OnAnimDataChanged(false);
      this.setStateMachineLocked(false);
      this.setDefaultState();
      if (this.vocalEvent != 0L) {
         this.getEmitter().stopSound(this.vocalEvent);
         this.vocalEvent = 0L;
      }

      this.setSceneCulled(true);
      this.releaseAnimationPlayer();
      this.setCurrent((IsoGridSquare)null);
      this.setLast((IsoGridSquare)null);
      this.setOnFloor(false);
      this.setCanWalk(true);
      this.setFallOnFront(false);
      this.setHitTime(0);
      this.strength = -1;
      this.setImmortalTutorialZombie(false);
      this.setOnlyJawStab(false);
      this.setAlwaysKnockedDown(false);
      this.setForceEatingAnimation(false);
      this.setNoTeeth(false);
      this.cognition = -1;
      this.speedType = -1;
      this.bodyToEat = null;
      this.checkForCorpseTimer = 10000.0F;
      this.clearAttachedItems();
      this.setEatBodyTarget((IsoMovingObject)null, false);
      this.setSkeleton(false);
      this.setReanimatedPlayer(false);
      this.setBecomeCrawler(false);
      this.setWasFakeDead(false);
      this.setReanimate(false);
      this.DoZombieStats();
      this.alerted = false;
      this.soundReactDelay = 0.0F;
      this.delayedSound.x = this.delayedSound.y = this.delayedSound.z = -1;
      this.bSoundSourceRepeating = false;
      this.soundSourceTarget = null;
      this.soundAttract = 0.0F;
      this.soundAttractTimeout = 0.0F;
      if (SandboxOptions.instance.Lore.Toughness.getValue() == 1) {
         this.setHealth(3.5F + Rand.Next(0.0F, 0.3F));
      }

      if (SandboxOptions.instance.Lore.Toughness.getValue() == 2) {
         this.setHealth(1.8F + Rand.Next(0.0F, 0.3F));
      }

      if (SandboxOptions.instance.Lore.Toughness.getValue() == 3) {
         this.setHealth(0.5F + Rand.Next(0.0F, 0.3F));
      }

      if (SandboxOptions.instance.Lore.Toughness.getValue() == 4) {
         this.setHealth(Rand.Next(0.5F, 3.5F) + Rand.Next(0.0F, 0.3F));
      }

      this.setCollidable(true);
      this.setShootable(true);
      if (this.isOnFire()) {
         IsoFireManager.RemoveBurningCharacter(this);
         this.setOnFire(false);
      }

      if (this.AttachedAnimSprite != null) {
         this.AttachedAnimSprite.clear();
      }

      this.OnlineID = -1;
      this.bIndoorZombie = false;
      this.setVehicle4TestCollision((BaseVehicle)null);
      this.clearItemsToSpawnAtDeath();
      this.m_persistentOutfitId = 0;
      this.m_bPersistentOutfitInit = false;
      this.sharedDesc = null;
   }

   public boolean wasFakeDead() {
      return this.bWasFakeDead;
   }

   public void setWasFakeDead(boolean var1) {
      this.bWasFakeDead = var1;
   }

   public boolean isBecomeCrawler() {
      return this.bBecomeCrawler;
   }

   public void setBecomeCrawler(boolean var1) {
      this.bBecomeCrawler = var1;
   }

   public boolean isReanimate() {
      return this.bReanimate;
   }

   public void setReanimate(boolean var1) {
      this.bReanimate = var1;
   }

   public boolean isReanimatedPlayer() {
      return this.bReanimatedPlayer;
   }

   public void setReanimatedPlayer(boolean var1) {
      this.bReanimatedPlayer = var1;
   }

   public IsoPlayer getReanimatedPlayer() {
      for(int var1 = 0; var1 < IsoPlayer.numPlayers; ++var1) {
         IsoPlayer var2 = IsoPlayer.players[var1];
         if (var2 != null && var2.ReanimatedCorpse == this) {
            return var2;
         }
      }

      return null;
   }

   public void setFemaleEtc(boolean var1) {
      this.setFemale(var1);
      if (this.getDescriptor() != null) {
         this.getDescriptor().setFemale(var1);
      }

      this.SpriteName = var1 ? "KateZ" : "BobZ";
      this.hurtSound = var1 ? "FemaleZombieHurt" : "MaleZombieHurt";
   }

   public void addRandomBloodDirtHolesEtc() {
      this.addBlood((BloodBodyPartType)null, false, true, false);
      this.addDirt((BloodBodyPartType)null, OutfitRNG.Next(5, 10), false);
      this.addRandomVisualDamages();
      this.addRandomVisualBandages();
      int var1 = Math.max(8 - (int)IsoWorld.instance.getWorldAgeDays() / 30, 0);

      int var2;
      for(var2 = 0; var2 < 5; ++var2) {
         if (OutfitRNG.NextBool(var1)) {
            this.addBlood((BloodBodyPartType)null, false, true, false);
            this.addDirt((BloodBodyPartType)null, (Integer)null, false);
         }
      }

      for(var2 = 0; var2 < 8; ++var2) {
         if (OutfitRNG.NextBool(var1)) {
            BloodBodyPartType var3 = BloodBodyPartType.FromIndex(OutfitRNG.Next(0, BloodBodyPartType.MAX.index()));
            this.addHole(var3);
            this.addBlood(var3, true, false, false);
         }
      }

   }

   public void useDescriptor(SharedDescriptors.Descriptor var1) {
      this.getHumanVisual().clear();
      this.itemVisuals.clear();
      this.m_persistentOutfitId = var1 == null ? 0 : var1.getID();
      this.m_bPersistentOutfitInit = true;
      this.sharedDesc = var1;
      if (var1 != null) {
         this.setFemale(var1.isFemale());
         this.SpriteName = this.descriptor.isFemale() ? "KateZ" : "BobZ";
         this.hurtSound = this.isFemale() ? "FemaleZombieHurt" : "MaleZombieHurt";
         this.getHumanVisual().copyFrom(var1.getHumanVisual());
         this.itemVisuals.clear();

         for(int var2 = 0; var2 < var1.itemVisuals.size(); ++var2) {
            ItemVisual var3 = new ItemVisual((ItemVisual)var1.itemVisuals.get(var2));
            this.itemVisuals.add(var3);
         }

      }
   }

   public SharedDescriptors.Descriptor getSharedDescriptor() {
      return this.sharedDesc;
   }

   public int getSharedDescriptorID() {
      return this.getPersistentOutfitID();
   }

   public int getScreenProperX(int var1) {
      return (int)(IsoUtils.XToScreen(this.x, this.y, this.z, 0) - IsoCamera.cameras[var1].getOffX());
   }

   public int getScreenProperY(int var1) {
      return (int)(IsoUtils.YToScreen(this.x, this.y, this.z, 0) - IsoCamera.cameras[var1].getOffY());
   }

   public BaseVisual getVisual() {
      return this.humanVisual;
   }

   public HumanVisual getHumanVisual() {
      return this.humanVisual;
   }

   public ItemVisuals getItemVisuals() {
      this.getItemVisuals(this.itemVisuals);
      return this.itemVisuals;
   }

   public void getItemVisuals(ItemVisuals var1) {
      if (this.isUsingWornItems()) {
         this.getWornItems().getItemVisuals(var1);
      } else if (var1 != this.itemVisuals) {
         var1.clear();
         var1.addAll(this.itemVisuals);
      }

   }

   public boolean isUsingWornItems() {
      return this.isOnDeathDone() || this.isReanimatedPlayer() || this.wasFakeDead();
   }

   public void setAsSurvivor() {
      String var1 = "Survivalist";
      switch(Rand.Next(3)) {
      case 1:
         var1 = "Survivalist02";
         break;
      case 2:
         var1 = "Survivalist03";
      }

      this.dressInPersistentOutfit(var1);
   }

   public void dressInRandomOutfit() {
      ZombiesZoneDefinition.dressInRandomOutfit(this);
   }

   public void dressInNamedOutfit(String var1) {
      this.wornItems.clear();
      this.getHumanVisual().clear();
      this.itemVisuals.clear();
      Outfit var2 = this.isFemale() ? OutfitManager.instance.FindFemaleOutfit(var1) : OutfitManager.instance.FindMaleOutfit(var1);
      if (var2 != null) {
         if (var2.isEmpty()) {
            var2.loadItems();
            this.pendingOutfitName = var1;
         } else {
            this.getHumanVisual().dressInNamedOutfit(var1, this.itemVisuals);
            this.getHumanVisual().synchWithOutfit(this.getHumanVisual().getOutfit());
         }
      }
   }

   public void dressInPersistentOutfitID(int var1) {
      this.getHumanVisual().clear();
      this.itemVisuals.clear();
      this.m_persistentOutfitId = var1;
      this.m_bPersistentOutfitInit = true;
      if (var1 != 0) {
         this.bDressInRandomOutfit = false;
         PersistentOutfits.instance.dressInOutfit(this, var1);
      }
   }

   public void dressInClothingItem(String var1) {
      this.wornItems.clear();
      this.getHumanVisual().dressInClothingItem(var1, this.itemVisuals);
   }

   public void clothingItemChanged(String var1) {
      super.clothingItemChanged(var1);
      if (!StringUtils.isNullOrWhitespace(this.pendingOutfitName)) {
         Outfit var2 = this.isFemale() ? OutfitManager.instance.FindFemaleOutfit(this.pendingOutfitName) : OutfitManager.instance.FindMaleOutfit(this.pendingOutfitName);
         if (var2 != null && !var2.isEmpty()) {
            this.dressInNamedOutfit(this.pendingOutfitName);
            this.pendingOutfitName = null;
            this.resetModelNextFrame();
         }
      }

   }

   public boolean WanderFromWindow() {
      if (this.getCurrentSquare() == null) {
         return false;
      } else {
         IsoZombie.FloodFill var1 = floodFill;
         var1.calculate(this, this.getCurrentSquare());
         IsoGridSquare var2 = var1.choose();
         var1.reset();
         if (var2 != null) {
            this.pathToLocation(var2.getX(), var2.getY(), var2.getZ());
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean isUseless() {
      return this.useless;
   }

   public void setUseless(boolean var1) {
      this.useless = var1;
   }

   public void setImmortalTutorialZombie(boolean var1) {
      this.ImmortalTutorialZombie = var1;
   }

   public boolean isTargetInCone(float var1, float var2) {
      if (this.target == null) {
         return false;
      } else {
         tempo.set(this.target.getX() - this.getX(), this.target.getY() - this.getY());
         float var3 = tempo.getLength();
         if (var3 == 0.0F) {
            return true;
         } else if (var3 > var1) {
            return false;
         } else {
            tempo.normalize();
            this.getVectorFromDirection(tempo2);
            float var4 = tempo.dot(tempo2);
            return var4 >= var2;
         }
      }
   }

   public boolean testCollideWithVehicles(BaseVehicle var1) {
      if (this.Health <= 0.0F) {
         return false;
      } else if (this.isProne()) {
         if (var1.getDriver() == null) {
            return false;
         } else {
            int var3 = var1.isEngineRunning() ? var1.testCollisionWithProneCharacter(this, true) : 0;
            if (var3 > 0) {
               if (!this.emitter.isPlaying(this.getHurtSound())) {
                  this.playHurtSound();
               }

               this.AttackedBy = var1.getDriver();
               var1.hitCharacter(this);
               if (this.Health <= 0.0F && !this.isOnDeathDone()) {
                  this.DoZombieInventory();
                  LuaEventManager.triggerEvent("OnZombieDead", this);
                  this.setOnDeathDone(true);
               }

               super.update();
               return true;
            } else {
               return false;
            }
         }
      } else {
         if (var1.shouldCollideWithCharacters()) {
            Vector2 var2 = (Vector2)((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).alloc();
            if (var1.testCollisionWithCharacter(this, 0.3F, var2) != null) {
               ((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).release(var2);
               var1.hitCharacter(this);
               super.update();
               return true;
            }

            ((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).release(var2);
         }

         return false;
      }
   }

   public boolean isCrawling() {
      return this.bCrawling;
   }

   public boolean isCanCrawlUnderVehicle() {
      return this.bCanCrawlUnderVehicle;
   }

   public void setCanCrawlUnderVehicle(boolean var1) {
      this.bCanCrawlUnderVehicle = var1;
   }

   public boolean isCanWalk() {
      return this.bCanWalk;
   }

   public void setCanWalk(boolean var1) {
      this.bCanWalk = var1;
   }

   public void initCanCrawlUnderVehicle() {
      byte var1 = 100;
      switch(SandboxOptions.instance.Lore.CrawlUnderVehicle.getValue()) {
      case 1:
         var1 = 0;
         break;
      case 2:
         var1 = 5;
         break;
      case 3:
         var1 = 10;
         break;
      case 4:
         var1 = 25;
         break;
      case 5:
         var1 = 50;
         break;
      case 6:
         var1 = 75;
         break;
      case 7:
         var1 = 100;
      }

      this.setCanCrawlUnderVehicle(Rand.Next(100) < var1);
   }

   public boolean shouldGetUpFromCrawl() {
      if (this.isCurrentState(ZombieGetUpFromCrawlState.instance())) {
         return true;
      } else if (this.isCurrentState(ZombieGetUpState.instance())) {
         return this.stateMachine.getPrevious() == ZombieGetUpFromCrawlState.instance();
      } else if (!this.isCrawling()) {
         return false;
      } else if (!this.isCanWalk()) {
         return false;
      } else {
         if (this.isBeingSteppedOn()) {
         }

         if (this.isCurrentState(PathFindState.instance())) {
            return this.getPathFindBehavior2().shouldGetUpFromCrawl();
         } else {
            if (this.isCurrentState(WalkTowardState.instance())) {
               float var1 = this.getPathFindBehavior2().getTargetX();
               float var2 = this.getPathFindBehavior2().getTargetY();
               if (this.DistToSquared(var1, var2) > 0.010000001F && PolygonalMap2.instance.lineClearCollide(this.x, this.y, var1, var2, (int)this.z, (IsoMovingObject)null)) {
                  return false;
               }
            }

            return this.isCurrentState(ZombieGetDownState.instance()) ? false : PolygonalMap2.instance.canStandAt(this.x, this.y, (int)this.z, (IsoMovingObject)null, false, true);
         }
      }
   }

   public void toggleCrawling() {
      boolean var1 = this.bCanCrawlUnderVehicle;
      if (this.bCrawling) {
         this.bCrawling = false;
         this.bKnockedDown = false;
         this.bStaggerBack = false;
         this.setFallOnFront(false);
         this.setOnFloor(false);
         this.DoZombieStats();
      } else {
         this.bCrawling = true;
         this.setOnFloor(true);
         this.DoZombieStats();
         this.walkVariant = "ZombieWalk";
      }

      this.bCanCrawlUnderVehicle = var1;
   }

   public void knockDown(boolean var1) {
      this.bKnockedDown = true;
      this.bStaggerBack = true;
      this.setHitReaction("");
      this.playerAttackPosition = var1 ? "BEHIND" : null;
      this.setHitForce(1.0F);
      this.reportEvent("wasHit");
   }

   public void addItemToSpawnAtDeath(InventoryItem var1) {
      if (this.itemsToSpawnAtDeath == null) {
         this.itemsToSpawnAtDeath = new ArrayList();
      }

      this.itemsToSpawnAtDeath.add(var1);
   }

   public void clearItemsToSpawnAtDeath() {
      if (this.itemsToSpawnAtDeath != null) {
         this.itemsToSpawnAtDeath.clear();
      }

   }

   public IsoMovingObject getEatBodyTarget() {
      return this.eatBodyTarget;
   }

   public void setEatBodyTarget(IsoMovingObject var1, boolean var2) {
      if (var1 != this.eatBodyTarget) {
         if (var2 || var1 == null || var1.getEatingZombies().size() < 3) {
            if (this.eatBodyTarget != null) {
               this.eatBodyTarget.getEatingZombies().remove(this);
            }

            this.eatBodyTarget = var1;
            if (var1 != null) {
               this.eatBodyTarget.getEatingZombies().add(this);
               this.eatSpeed = Rand.Next(0.8F, 1.2F) * GameTime.getAnimSpeedFix();
            }
         }
      }
   }

   private void updateEatBodyTarget() {
      if (this.bodyToEat != null && this.isCurrentState(ZombieIdleState.instance()) && this.DistToSquared(this.bodyToEat) <= 1.0F && (int)this.getZ() == (int)this.bodyToEat.getZ()) {
         this.setEatBodyTarget(this.bodyToEat, false);
         this.bodyToEat = null;
      }

      if (this.eatBodyTarget != null) {
         if (this.eatBodyTarget instanceof IsoDeadBody && this.eatBodyTarget.getStaticMovingObjectIndex() == -1) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

         if (this.target != null && !this.target.isOnFloor() && this.target != this.eatBodyTarget) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

         IsoPlayer var1 = (IsoPlayer)Type.tryCastTo(this.eatBodyTarget, IsoPlayer.class);
         if (var1 != null && var1.ReanimatedCorpse != null) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

         if (var1 != null && var1.isAlive() && !var1.isOnFloor() && !var1.isCurrentState(PlayerHitReactionState.instance())) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

         if (!this.isCurrentState(ZombieEatBodyState.instance()) && this.eatBodyTarget != null && this.DistToSquared(this.eatBodyTarget) > 1.0F) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

         if (this.eatBodyTarget != null && this.eatBodyTarget.getSquare() != null && this.current != null && this.current.isSomethingTo(this.eatBodyTarget.getSquare())) {
            this.setEatBodyTarget((IsoMovingObject)null, false);
         }

      }
   }

   private void updateCharacterTextureAnimTime() {
      boolean var1 = this.isCurrentState(WalkTowardState.instance()) || this.isCurrentState(PathFindState.instance());
      this.m_characterTextureAnimDuration = var1 ? 0.67F : 2.0F;
      this.m_characterTextureAnimTime += GameTime.getInstance().getTimeDelta();
      if (this.m_characterTextureAnimTime > this.m_characterTextureAnimDuration) {
         this.m_characterTextureAnimTime %= this.m_characterTextureAnimDuration;
      }

   }

   public Vector2 getHitAngle() {
      return this.hitAngle;
   }

   public void setHitAngle(Vector2 var1) {
      if (var1 != null) {
         this.hitAngle.set(var1);
      }
   }

   public int getCrawlerType() {
      return this.crawlerType;
   }

   public void setCrawlerType(int var1) {
      this.crawlerType = var1;
   }

   public void addRandomVisualBandages() {
      if (!"Tutorial".equals(Core.getInstance().getGameMode())) {
         for(int var1 = 0; var1 < 5; ++var1) {
            if (OutfitRNG.Next(10) == 0) {
               BodyPartType var2 = BodyPartType.getRandom();
               String var3 = var2.getBandageModel() + "_Blood";
               this.addBodyVisualFromItemType(var3);
            }
         }

      }
   }

   public void addVisualBandage(BodyPartType var1, boolean var2) {
      String var3 = var1.getBandageModel() + (var2 ? "_Blood" : "");
      this.addBodyVisualFromItemType(var3);
   }

   public void addRandomVisualDamages() {
      for(int var1 = 0; var1 < 5; ++var1) {
         if (OutfitRNG.Next(5) == 0) {
            String var2 = (String)OutfitRNG.pickRandom(ScriptManager.instance.getZedDmgMap());
            this.addBodyVisualFromItemType("Base." + var2);
         }
      }

   }

   public String getPlayerAttackPosition() {
      return this.playerAttackPosition;
   }

   public void setPlayerAttackPosition(String var1) {
      this.playerAttackPosition = var1;
   }

   public boolean isSitAgainstWall() {
      return this.sitAgainstWall;
   }

   public void setSitAgainstWall(boolean var1) {
      this.sitAgainstWall = var1;
   }

   public boolean isSkeleton() {
      if (Core.bDebug && DebugOptions.instance.ModelSkeleton.getValue()) {
         this.getHumanVisual().setSkinTextureIndex(2);
         return true;
      } else {
         return this.isSkeleton;
      }
   }

   public boolean isZombie() {
      return true;
   }

   public void setSkeleton(boolean var1) {
      this.isSkeleton = var1;
      if (var1) {
         this.getHumanVisual().setHairModel("");
         this.getHumanVisual().setBeardModel("");
         ModelManager.instance.Reset(this);
      }

   }

   public int getHitTime() {
      return this.hitTime;
   }

   public void setHitTime(int var1) {
      this.hitTime = var1;
   }

   public int getThumpTimer() {
      return this.thumpTimer;
   }

   public void setThumpTimer(int var1) {
      this.thumpTimer = var1;
   }

   public IsoMovingObject getTarget() {
      return this.target;
   }

   public void setTargetSeenTime(float var1) {
      this.targetSeenTime = var1;
   }

   public float getTargetSeenTime() {
      return this.targetSeenTime;
   }

   public boolean isTargetVisible() {
      IsoPlayer var1 = (IsoPlayer)Type.tryCastTo(this.target, IsoPlayer.class);
      if (var1 != null && this.getCurrentSquare() != null) {
         return GameServer.bServer ? ServerLOS.instance.isCouldSee(var1, this.getCurrentSquare()) : this.getCurrentSquare().isCouldSee(var1.getPlayerNum());
      } else {
         return false;
      }
   }

   public float getTurnDelta() {
      return this.m_turnDeltaNormal;
   }

   public boolean isAttacking() {
      return this.isZombieAttacking();
   }

   public boolean isZombieAttacking() {
      State var1 = this.getCurrentState();
      return var1 != null && var1.isAttacking(this);
   }

   public boolean isZombieAttacking(IsoMovingObject var1) {
      if (GameClient.bClient) {
         return this.legsSprite != null && this.legsSprite.CurrentAnim != null && "ZombieBite".equals(this.legsSprite.CurrentAnim.name);
      } else {
         return var1 == this.target && this.isCurrentState(AttackState.instance());
      }
   }

   public int getHitHeadWhileOnFloor() {
      return this.hitHeadWhileOnFloor;
   }

   public void setHitHeadWhileOnFloor(int var1) {
      this.hitHeadWhileOnFloor = var1;
   }

   public boolean isHitLegsWhileOnFloor() {
      return this.hitLegsWhileOnFloor;
   }

   public void setHitLegsWhileOnFloor(boolean var1) {
      this.hitLegsWhileOnFloor = var1;
   }

   public void makeInactive(boolean var1) {
      if (var1 != this.inactive) {
         if (var1) {
            this.walkType = Integer.toString(Rand.Next(3) + 1);
            this.walkType = "slow" + this.walkType;
            this.bRunning = false;
            this.inactive = true;
            this.speedType = 3;
         } else {
            this.speedType = -1;
            this.inactive = false;
            this.DoZombieStats();
         }

      }
   }

   public float getFootstepVolume() {
      return this.footstepVolume;
   }

   public boolean isFacingTarget() {
      if (this.target == null) {
         return false;
      } else {
         tempo.set(this.target.x - this.x, this.target.y - this.y).normalize();
         this.getLookVector(tempo2);
         float var1 = Vector2.dot(tempo.x, tempo.y, tempo2.x, tempo2.y);
         return (double)var1 >= 0.8D;
      }
   }

   public boolean isTargetLocationKnown() {
      if (this.target == null) {
         return false;
      } else if (this.BonusSpotTime > 0.0F) {
         return true;
      } else {
         return this.TimeSinceSeenFlesh < 1.0F;
      }
   }

   protected int getSandboxMemoryDuration() {
      int var1 = SandboxOptions.instance.Lore.Memory.getValue();
      short var2 = 160;
      if (this.inactive) {
         var2 = 5;
      } else if (var1 == 1) {
         var2 = 250;
      } else if (var1 == 3) {
         var2 = 100;
      } else if (var1 == 4) {
         var2 = 5;
      }

      int var3 = var2 * 5;
      return var3;
   }

   public boolean shouldDoFenceLunge() {
      if (!SandboxOptions.instance.Lore.ZombiesFenceLunge.getValue()) {
         return false;
      } else if (Rand.NextBool(3)) {
         return false;
      } else {
         IsoGameCharacter var1 = (IsoGameCharacter)Type.tryCastTo(this.target, IsoGameCharacter.class);
         if (var1 != null && (int)var1.getZ() == (int)this.getZ()) {
            if (var1.getVehicle() != null) {
               return false;
            } else {
               return (double)this.DistTo(var1) < 3.9D;
            }
         } else {
            return false;
         }
      }
   }

   public boolean isProne() {
      if (!this.isOnFloor()) {
         return false;
      } else if (this.bCrawling) {
         return true;
      } else {
         return this.isCurrentState(ZombieOnGroundState.instance()) || this.isCurrentState(FakeDeadZombieState.instance());
      }
   }

   public void setTarget(IsoMovingObject var1) {
      this.target = var1;
   }

   public boolean isAlwaysKnockedDown() {
      return this.alwaysKnockedDown;
   }

   public void setAlwaysKnockedDown(boolean var1) {
      this.alwaysKnockedDown = var1;
   }

   public void setDressInRandomOutfit(boolean var1) {
      this.bDressInRandomOutfit = var1;
   }

   public void setBodyToEat(IsoDeadBody var1) {
      this.bodyToEat = var1;
   }

   public boolean isForceEatingAnimation() {
      return this.forceEatingAnimation;
   }

   public void setForceEatingAnimation(boolean var1) {
      this.forceEatingAnimation = var1;
   }

   public boolean isOnlyJawStab() {
      return this.onlyJawStab;
   }

   public void setOnlyJawStab(boolean var1) {
      this.onlyJawStab = var1;
   }

   public boolean isNoTeeth() {
      return this.noTeeth;
   }

   public void setNoTeeth(boolean var1) {
      this.noTeeth = var1;
   }

   private static class s_performance {
      static final PerformanceProfileProbe update = new PerformanceProfileProbe("IsoZombie.update");
      static final PerformanceProfileProbe postUpdate = new PerformanceProfileProbe("IsoZombie.postUpdate");
   }

   private static final class FloodFill {
      private IsoGridSquare start;
      private final int FLOOD_SIZE;
      private final BooleanGrid visited;
      private final Stack stack;
      private IsoBuilding building;
      private Mover mover;
      private final ArrayList choices;

      private FloodFill() {
         this.start = null;
         this.FLOOD_SIZE = 11;
         this.visited = new BooleanGrid(11, 11);
         this.stack = new Stack();
         this.building = null;
         this.mover = null;
         this.choices = new ArrayList(121);
      }

      void calculate(Mover var1, IsoGridSquare var2) {
         this.start = var2;
         this.mover = var1;
         if (this.start.getRoom() != null) {
            this.building = this.start.getRoom().getBuilding();
         }

         boolean var3 = false;
         boolean var4 = false;
         if (this.push(this.start.getX(), this.start.getY())) {
            while((var2 = this.pop()) != null) {
               int var6 = var2.getX();

               int var5;
               for(var5 = var2.getY(); this.shouldVisit(var6, var5, var6, var5 - 1); --var5) {
               }

               var4 = false;
               var3 = false;

               while(true) {
                  this.visited.setValue(this.gridX(var6), this.gridY(var5), true);
                  IsoGridSquare var7 = IsoWorld.instance.CurrentCell.getGridSquare(var6, var5, this.start.getZ());
                  if (var7 != null) {
                     this.choices.add(var7);
                  }

                  if (!var3 && this.shouldVisit(var6, var5, var6 - 1, var5)) {
                     if (!this.push(var6 - 1, var5)) {
                        return;
                     }

                     var3 = true;
                  } else if (var3 && !this.shouldVisit(var6, var5, var6 - 1, var5)) {
                     var3 = false;
                  } else if (var3 && !this.shouldVisit(var6 - 1, var5, var6 - 1, var5 - 1) && !this.push(var6 - 1, var5)) {
                     return;
                  }

                  if (!var4 && this.shouldVisit(var6, var5, var6 + 1, var5)) {
                     if (!this.push(var6 + 1, var5)) {
                        return;
                     }

                     var4 = true;
                  } else if (var4 && !this.shouldVisit(var6, var5, var6 + 1, var5)) {
                     var4 = false;
                  } else if (var4 && !this.shouldVisit(var6 + 1, var5, var6 + 1, var5 - 1) && !this.push(var6 + 1, var5)) {
                     return;
                  }

                  ++var5;
                  if (!this.shouldVisit(var6, var5 - 1, var6, var5)) {
                     break;
                  }
               }
            }

         }
      }

      boolean shouldVisit(int var1, int var2, int var3, int var4) {
         if (this.gridX(var3) < 11 && this.gridX(var3) >= 0) {
            if (this.gridY(var4) < 11 && this.gridY(var4) >= 0) {
               if (this.visited.getValue(this.gridX(var3), this.gridY(var4))) {
                  return false;
               } else {
                  IsoGridSquare var5 = IsoWorld.instance.CurrentCell.getGridSquare(var3, var4, this.start.getZ());
                  if (var5 == null) {
                     return false;
                  } else if (!var5.Has(IsoObjectType.stairsBN) && !var5.Has(IsoObjectType.stairsMN) && !var5.Has(IsoObjectType.stairsTN)) {
                     if (!var5.Has(IsoObjectType.stairsBW) && !var5.Has(IsoObjectType.stairsMW) && !var5.Has(IsoObjectType.stairsTW)) {
                        if (var5.getRoom() != null && this.building == null) {
                           return false;
                        } else if (var5.getRoom() == null && this.building != null) {
                           return false;
                        } else {
                           return !IsoWorld.instance.CurrentCell.blocked(this.mover, var3, var4, this.start.getZ(), var1, var2, this.start.getZ());
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      boolean push(int var1, int var2) {
         IsoGridSquare var3 = IsoWorld.instance.CurrentCell.getGridSquare(var1, var2, this.start.getZ());
         this.stack.push(var3);
         return true;
      }

      IsoGridSquare pop() {
         return this.stack.isEmpty() ? null : (IsoGridSquare)this.stack.pop();
      }

      int gridX(int var1) {
         return var1 - (this.start.getX() - 5);
      }

      int gridY(int var1) {
         return var1 - (this.start.getY() - 5);
      }

      int gridX(IsoGridSquare var1) {
         return var1.getX() - (this.start.getX() - 5);
      }

      int gridY(IsoGridSquare var1) {
         return var1.getY() - (this.start.getY() - 5);
      }

      IsoGridSquare choose() {
         if (this.choices.isEmpty()) {
            return null;
         } else {
            int var1 = Rand.Next(this.choices.size());
            return (IsoGridSquare)this.choices.get(var1);
         }
      }

      void reset() {
         this.building = null;
         this.choices.clear();
         this.stack.clear();
         this.visited.clear();
      }

      // $FF: synthetic method
      FloodFill(Object var1) {
         this();
      }
   }

   public static enum ZombieSound {
      Burned(10),
      DeadCloseKilled(10),
      DeadNotCloseKilled(10),
      Hurt(10),
      Idle(15),
      Lunge(40),
      MAX(-1);

      private int radius;
      private static final IsoZombie.ZombieSound[] values = values();

      private ZombieSound(int var3) {
         this.radius = var3;
      }

      public int radius() {
         return this.radius;
      }

      public static IsoZombie.ZombieSound fromIndex(int var0) {
         return var0 >= 0 && var0 < values.length ? values[var0] : MAX;
      }
   }
}
