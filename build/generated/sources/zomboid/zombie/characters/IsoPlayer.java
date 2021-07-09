package zombie.characters;

import fmod.fmod.BaseSoundListener;
import fmod.fmod.DummySoundListener;
import fmod.fmod.FMODSoundEmitter;
import fmod.fmod.SoundListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.joml.Vector3f;
import se.krka.kahlua.vm.KahluaTable;
import zombie.DebugFileWatcher;
import zombie.GameSounds;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.PredicatedFileWatcher;
import zombie.SandboxOptions;
import zombie.SoundManager;
import zombie.SystemDisabler;
import zombie.ZomboidFileSystem;
import zombie.ZomboidGlobals;
import zombie.Lua.LuaEventManager;
import zombie.ai.State;
import zombie.ai.sadisticAIDirector.SleepingEvent;
import zombie.ai.states.BumpedState;
import zombie.ai.states.ClimbDownSheetRopeState;
import zombie.ai.states.ClimbOverFenceState;
import zombie.ai.states.ClimbOverWallState;
import zombie.ai.states.ClimbSheetRopeState;
import zombie.ai.states.ClimbThroughWindowState;
import zombie.ai.states.CloseWindowState;
import zombie.ai.states.CollideWithWallState;
import zombie.ai.states.FakeDeadZombieState;
import zombie.ai.states.FishingState;
import zombie.ai.states.FitnessState;
import zombie.ai.states.ForecastBeatenPlayerState;
import zombie.ai.states.IdleState;
import zombie.ai.states.OpenWindowState;
import zombie.ai.states.PathFindState;
import zombie.ai.states.PlayerActionsState;
import zombie.ai.states.PlayerEmoteState;
import zombie.ai.states.PlayerExtState;
import zombie.ai.states.PlayerFallDownState;
import zombie.ai.states.PlayerFallingState;
import zombie.ai.states.PlayerGetUpState;
import zombie.ai.states.PlayerHitReactionState;
import zombie.ai.states.PlayerOnGroundState;
import zombie.ai.states.PlayerSitOnGroundState;
import zombie.ai.states.SmashWindowState;
import zombie.ai.states.StaggerBackState;
import zombie.ai.states.SwipeStatePlayer;
import zombie.audio.BaseSoundEmitter;
import zombie.audio.DummySoundEmitter;
import zombie.audio.GameSound;
import zombie.characters.AttachedItems.AttachedItems;
import zombie.characters.BodyDamage.BodyDamage;
import zombie.characters.BodyDamage.BodyPart;
import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.BodyDamage.Fitness;
import zombie.characters.BodyDamage.Nutrition;
import zombie.characters.CharacterTimedActions.BaseAction;
import zombie.characters.Moodles.MoodleType;
import zombie.characters.Moodles.Moodles;
import zombie.characters.action.ActionContext;
import zombie.characters.action.ActionGroup;
import zombie.characters.skills.PerkFactory;
import zombie.core.Color;
import zombie.core.Core;
import zombie.core.Rand;
import zombie.core.Translator;
import zombie.core.Collections.ZombieSortedList;
import zombie.core.logger.ExceptionLogger;
import zombie.core.logger.LoggerManager;
import zombie.core.math.PZMath;
import zombie.core.network.ByteBufferWriter;
import zombie.core.opengl.Shader;
import zombie.core.profiling.PerformanceProfileProbe;
import zombie.core.skinnedmodel.ModelManager;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.core.skinnedmodel.advancedanimation.AnimLayer;
import zombie.core.skinnedmodel.animation.AnimationPlayer;
import zombie.core.skinnedmodel.visual.BaseVisual;
import zombie.core.skinnedmodel.visual.HumanVisual;
import zombie.core.skinnedmodel.visual.IHumanVisual;
import zombie.core.skinnedmodel.visual.ItemVisuals;
import zombie.core.textures.ColorInfo;
import zombie.core.utils.OnceEvery;
import zombie.core.utils.UpdateLimit;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.debug.DebugType;
import zombie.gameStates.MainScreenState;
import zombie.input.GameKeyboard;
import zombie.input.JoypadManager;
import zombie.input.Mouse;
import zombie.inventory.InventoryItem;
import zombie.inventory.InventoryItemFactory;
import zombie.inventory.types.Clothing;
import zombie.inventory.types.DrainableComboItem;
import zombie.inventory.types.HandWeapon;
import zombie.inventory.types.WeaponType;
import zombie.iso.IsoCamera;
import zombie.iso.IsoCell;
import zombie.iso.IsoChunk;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;
import zombie.iso.IsoPhysicsObject;
import zombie.iso.IsoUtils;
import zombie.iso.IsoWorld;
import zombie.iso.SliceY;
import zombie.iso.Vector2;
import zombie.iso.SpriteDetails.IsoFlagType;
import zombie.iso.SpriteDetails.IsoObjectType;
import zombie.iso.areas.SafeHouse;
import zombie.iso.objects.IsoBarricade;
import zombie.iso.objects.IsoCurtain;
import zombie.iso.objects.IsoDoor;
import zombie.iso.objects.IsoThumpable;
import zombie.iso.objects.IsoWindow;
import zombie.iso.objects.IsoWindowFrame;
import zombie.iso.weather.ClimateManager;
import zombie.network.BodyDamageSync;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.PacketTypes;
import zombie.network.PassengerMap;
import zombie.network.PlayerNetHistory;
import zombie.network.ServerLOS;
import zombie.network.ServerMap;
import zombie.network.ServerOptions;
import zombie.network.ServerWorldDatabase;
import zombie.savefile.ClientPlayerDB;
import zombie.savefile.PlayerDB;
import zombie.scripting.objects.VehicleScript;
import zombie.ui.TutorialManager;
import zombie.ui.UIManager;
import zombie.util.StringUtils;
import zombie.util.Type;
import zombie.util.list.PZArrayUtil;
import zombie.vehicles.BaseVehicle;
import zombie.vehicles.PathFindBehavior2;
import zombie.vehicles.PolygonalMap2;
import zombie.vehicles.VehiclePart;
import zombie.vehicles.VehicleWindow;
import zombie.vehicles.VehiclesDB2;

public final class IsoPlayer extends IsoLivingCharacter implements IHumanVisual {
   private String attackType;
   public static String DEATH_MUSIC_NAME = "NewMusic_Death";
   private boolean allowSprint;
   private boolean allowRun;
   public static boolean isTestAIMode = false;
   public static final byte NetRemoteState_Idle = 0;
   public static final byte NetRemoteState_Walk = 1;
   public static final byte NetRemoteState_Run = 2;
   public static final boolean NoSound = false;
   private static final float TIME_RIGHT_PRESSED_SECONDS = 0.15F;
   public static int assumedPlayer = 0;
   public static int numPlayers = 1;
   public static final int MAX = 4;
   public static final IsoPlayer[] players = new IsoPlayer[4];
   public static byte NetRemoteState_Attack = 3;
   private static IsoPlayer instance;
   private static final Object instanceLock = "IsoPlayer.instance Lock";
   private static final Vector2 testHitPosition = new Vector2();
   private static int FollowDeadCount = 240;
   private static final Stack StaticTraits = new Stack();
   private static final OnceEvery networkUpdate = new OnceEvery(0.1F);
   private static int lmx = -1;
   private static int lmy = -1;
   private boolean ignoreAutoVault;
   private static final Vector2 tempo = new Vector2();
   private static final Vector2 tempVector2 = new Vector2();
   private static final String forwardStr = "Forward";
   private static final String backwardStr = "Backward";
   private static final String leftStr = "Left";
   private static final String rightStr = "Right";
   private static boolean CoopPVP = false;
   private boolean ignoreContextKey;
   private boolean ignoreInputsForDirection;
   private float extUpdateCount;
   private static final int s_randomIdleFidgetInterval = 5000;
   public boolean attackStarted;
   private static final PredicatedFileWatcher m_isoPlayerTriggerWatcher;
   private final PredicatedFileWatcher m_setClothingTriggerWatcher;
   private static Vector2 tempVector2_1;
   private static Vector2 tempVector2_2;
   protected final HumanVisual humanVisual;
   protected final ItemVisuals itemVisuals;
   public boolean targetedByZombie;
   public float lastTargeted;
   public float TimeSinceOpenDoor;
   public boolean bRemote;
   public int TimeSinceLastNetData;
   public String accessLevel;
   public String tagPrefix;
   public boolean showTag;
   public boolean factionPvp;
   public int OnlineID;
   public int OnlineChunkGridWidth;
   public final PlayerNetHistory netHistory;
   public boolean bJoypadMovementActive;
   public boolean bJoypadIgnoreAimUntilCentered;
   public boolean bJoypadIgnoreChargingRT;
   protected boolean bJoypadBDown;
   protected boolean bJoypadSprint;
   public boolean mpTorchCone;
   public float mpTorchDist;
   public float mpTorchStrength;
   public int PlayerIndex;
   public int serverPlayerIndex;
   public float useChargeDelta;
   public int JoypadBind;
   public float ContextPanic;
   public float numNearbyBuildingsRooms;
   public boolean isCharging;
   public boolean isChargingLT;
   private boolean bLookingWhileInVehicle;
   public boolean JustMoved;
   public boolean L3Pressed;
   public float maxWeightDelta;
   public float CurrentSpeed;
   public float MaxSpeed;
   public boolean bDeathFinished;
   public boolean isSpeek;
   public boolean isVoiceMute;
   public ZombieSortedList zombiesToSend;
   private boolean m_ghostMode;
   public final Vector2 playerMoveDir;
   public BaseSoundListener soundListener;
   public String username;
   public boolean dirtyRecalcGridStack;
   public float dirtyRecalcGridStackTime;
   public float runningTime;
   public float timePressedContext;
   public float chargeTime;
   public float useChargeTime;
   public boolean bPressContext;
   public float closestZombie;
   public final Vector2 lastAngle;
   public String SaveFileName;
   public boolean bBannedAttacking;
   public int sqlID;
   protected int ClearSpottedTimer;
   protected float timeSinceLastStab;
   protected Stack LastSpotted;
   protected boolean bChangeCharacterDebounce;
   protected int followID;
   protected final Stack FollowCamStack;
   protected boolean bSeenThisFrame;
   protected boolean bCouldBeSeenThisFrame;
   protected float AsleepTime;
   protected final Stack spottedList;
   protected int TicksSinceSeenZombie;
   protected boolean Waiting;
   protected IsoSurvivor DragCharacter;
   protected float heartDelay;
   protected float heartDelayMax;
   protected long heartEventInstance;
   protected float DrunkOscilatorStepSin;
   protected float DrunkOscilatorRateSin;
   protected float DrunkOscilatorStepCos;
   protected float DrunkOscilatorRateCos;
   protected float DrunkOscilatorStepCos2;
   protected float DrunkOscilatorRateCos2;
   protected float DrunkSin;
   protected float DrunkCos;
   protected float DrunkCos2;
   protected float MinOscilatorRate;
   protected float MaxOscilatorRate;
   protected float DesiredSinRate;
   protected float DesiredCosRate;
   protected float OscilatorChangeRate;
   protected String Forname;
   protected String Surname;
   protected int DialogMood;
   protected int ping;
   protected IsoMovingObject DragObject;
   private double lastSeenZombieTime;
   private BaseSoundEmitter testemitter;
   private int checkSafehouse;
   private boolean attackFromBehind;
   private float TimeRightPressed;
   private long aimKeyDownMS;
   private long runKeyDownMS;
   private long sprintKeyDownMS;
   private int hypothermiaCache;
   private int hyperthermiaCache;
   private float ticksSincePressedMovement;
   private boolean flickTorch;
   private float checkNearbyRooms;
   private boolean bUseVehicle;
   private boolean bUsedVehicle;
   private float useVehicleDuration;
   private static final Vector3f tempVector3f;
   private final IsoPlayer.InputState inputState;
   private boolean isWearingNightVisionGoggles;
   private Integer transactionID;
   private float MoveSpeed;
   private int offSetXUI;
   private int offSetYUI;
   private float combatSpeed;
   private double HoursSurvived;
   private boolean bSentDeath;
   private boolean noClip;
   private boolean authorizeMeleeAction;
   private boolean authorizeShoveStomp;
   private boolean blockMovement;
   private Nutrition nutrition;
   private Fitness fitness;
   private boolean forceOverrideAnim;
   private boolean initiateAttack;
   private final ColorInfo tagColor;
   private String displayName;
   private boolean seeNonPvpZone;
   private final HashMap mechanicsItem;
   private int sleepingPillsTaken;
   private long lastPillsTaken;
   private long heavyBreathInstance;
   private String heavyBreathSoundName;
   private boolean allChatMuted;
   private boolean forceAim;
   private boolean forceRun;
   private boolean forceSprint;
   private boolean LastRT;
   private final UpdateLimit ULbeatenVehicle;
   private boolean bMultiplayer;
   private String SaveFileIP;
   private BaseVehicle vehicle4testCollision;
   private long steamID;
   private final IsoPlayer.VehicleContainerData vehicleContainerData;
   private boolean isWalking;
   private int footInjuryTimer;
   private boolean bSneakDebounce;
   private float m_turnDelta;
   private boolean m_bKnockedDown;
   private boolean m_isPlayerMoving;
   private float m_walkSpeed;
   private float m_walkInjury;
   private float m_runSpeed;
   private float m_idleSpeed;
   private float m_deltaX;
   private float m_deltaY;
   private float m_windspeed;
   private float m_windForce;
   private float m_IPX;
   private float m_IPY;
   private float m_moveDelta;
   private float pressedRunTimer;
   private boolean pressedRun;
   private boolean m_meleePressed;
   private boolean m_lastAttackWasShove;
   private boolean m_isPerformingAnAction;
   private boolean pathfindRun;
   private static final IsoPlayer.MoveVars s_moveVars;
   private static final ArrayList s_targetsProne;
   private static final ArrayList s_targetsStanding;
   private boolean bReloadButtonDown;
   private boolean bRackButtonDown;
   private boolean bReloadKeyDown;
   private boolean bRackKeyDown;

   public IsoPlayer(IsoCell var1) {
      this(var1, (SurvivorDesc)null, 0, 0, 0);
   }

   public IsoPlayer(IsoCell var1, SurvivorDesc var2, int var3, int var4, int var5) {
      super(var1, (float)var3, (float)var4, (float)var5);
      this.attackType = null;
      this.allowSprint = true;
      this.allowRun = true;
      this.ignoreAutoVault = false;
      this.ignoreContextKey = false;
      this.ignoreInputsForDirection = false;
      this.extUpdateCount = 0.0F;
      this.attackStarted = false;
      this.humanVisual = new HumanVisual(this);
      this.itemVisuals = new ItemVisuals();
      this.targetedByZombie = false;
      this.lastTargeted = 1.0E8F;
      this.TimeSinceLastNetData = 0;
      this.accessLevel = "";
      this.tagPrefix = "";
      this.showTag = true;
      this.factionPvp = false;
      this.OnlineID = 1;
      this.netHistory = new PlayerNetHistory();
      this.bJoypadMovementActive = true;
      this.bJoypadIgnoreChargingRT = false;
      this.bJoypadBDown = false;
      this.bJoypadSprint = false;
      this.mpTorchCone = false;
      this.mpTorchDist = 0.0F;
      this.mpTorchStrength = 0.0F;
      this.PlayerIndex = 0;
      this.serverPlayerIndex = 1;
      this.useChargeDelta = 0.0F;
      this.JoypadBind = -1;
      this.ContextPanic = 0.0F;
      this.numNearbyBuildingsRooms = 0.0F;
      this.isCharging = false;
      this.isChargingLT = false;
      this.bLookingWhileInVehicle = false;
      this.JustMoved = false;
      this.L3Pressed = false;
      this.maxWeightDelta = 1.0F;
      this.CurrentSpeed = 0.0F;
      this.MaxSpeed = 0.09F;
      this.bDeathFinished = false;
      this.m_ghostMode = false;
      this.playerMoveDir = new Vector2(0.0F, 0.0F);
      this.username = "Bob";
      this.dirtyRecalcGridStack = true;
      this.dirtyRecalcGridStackTime = 10.0F;
      this.runningTime = 0.0F;
      this.timePressedContext = 0.0F;
      this.chargeTime = 0.0F;
      this.useChargeTime = 0.0F;
      this.bPressContext = false;
      this.closestZombie = 1000000.0F;
      this.lastAngle = new Vector2();
      this.bBannedAttacking = false;
      this.sqlID = -1;
      this.ClearSpottedTimer = -1;
      this.timeSinceLastStab = 0.0F;
      this.LastSpotted = new Stack();
      this.bChangeCharacterDebounce = false;
      this.followID = 0;
      this.FollowCamStack = new Stack();
      this.bSeenThisFrame = false;
      this.bCouldBeSeenThisFrame = false;
      this.AsleepTime = 0.0F;
      this.spottedList = new Stack();
      this.TicksSinceSeenZombie = 9999999;
      this.Waiting = true;
      this.DragCharacter = null;
      this.heartDelay = 30.0F;
      this.heartDelayMax = 30.0F;
      this.DrunkOscilatorStepSin = 0.0F;
      this.DrunkOscilatorRateSin = 0.1F;
      this.DrunkOscilatorStepCos = 0.0F;
      this.DrunkOscilatorRateCos = 0.1F;
      this.DrunkOscilatorStepCos2 = 0.0F;
      this.DrunkOscilatorRateCos2 = 0.07F;
      this.DrunkSin = 0.0F;
      this.DrunkCos = 23784.0F;
      this.DrunkCos2 = 61616.0F;
      this.MinOscilatorRate = 0.01F;
      this.MaxOscilatorRate = 0.15F;
      this.DesiredSinRate = 0.0F;
      this.DesiredCosRate = 0.0F;
      this.OscilatorChangeRate = 0.05F;
      this.Forname = "Bob";
      this.Surname = "Smith";
      this.DialogMood = 1;
      this.ping = 0;
      this.DragObject = null;
      this.lastSeenZombieTime = 2.0D;
      this.checkSafehouse = 200;
      this.attackFromBehind = false;
      this.TimeRightPressed = 0.0F;
      this.aimKeyDownMS = 0L;
      this.runKeyDownMS = 0L;
      this.sprintKeyDownMS = 0L;
      this.hypothermiaCache = -1;
      this.hyperthermiaCache = -1;
      this.ticksSincePressedMovement = 0.0F;
      this.flickTorch = false;
      this.checkNearbyRooms = 0.0F;
      this.bUseVehicle = false;
      this.inputState = new IsoPlayer.InputState();
      this.isWearingNightVisionGoggles = false;
      this.transactionID = 0;
      this.MoveSpeed = 0.06F;
      this.offSetXUI = 0;
      this.offSetYUI = 0;
      this.combatSpeed = 1.0F;
      this.HoursSurvived = 0.0D;
      this.noClip = false;
      this.authorizeMeleeAction = true;
      this.authorizeShoveStomp = true;
      this.blockMovement = false;
      this.forceOverrideAnim = false;
      this.initiateAttack = false;
      this.tagColor = new ColorInfo(1.0F, 1.0F, 1.0F, 1.0F);
      this.displayName = null;
      this.seeNonPvpZone = false;
      this.mechanicsItem = new HashMap();
      this.sleepingPillsTaken = 0;
      this.lastPillsTaken = 0L;
      this.heavyBreathInstance = 0L;
      this.heavyBreathSoundName = null;
      this.allChatMuted = false;
      this.forceAim = false;
      this.forceRun = false;
      this.forceSprint = false;
      this.ULbeatenVehicle = new UpdateLimit(200L);
      this.vehicle4testCollision = null;
      this.vehicleContainerData = new IsoPlayer.VehicleContainerData();
      this.isWalking = false;
      this.footInjuryTimer = 0;
      this.m_turnDelta = 0.0F;
      this.m_bKnockedDown = false;
      this.m_isPlayerMoving = false;
      this.m_walkSpeed = 0.0F;
      this.m_walkInjury = 0.0F;
      this.m_runSpeed = 0.0F;
      this.m_idleSpeed = 0.0F;
      this.m_deltaX = 0.0F;
      this.m_deltaY = 0.0F;
      this.m_windspeed = 0.0F;
      this.m_windForce = 0.0F;
      this.m_IPX = 0.0F;
      this.m_IPY = 0.0F;
      this.m_moveDelta = 0.0F;
      this.pressedRunTimer = 0.0F;
      this.pressedRun = false;
      this.m_meleePressed = false;
      this.m_lastAttackWasShove = false;
      this.m_isPerformingAnAction = false;
      this.pathfindRun = false;
      this.bReloadButtonDown = false;
      this.bRackButtonDown = false;
      this.bReloadKeyDown = false;
      this.bRackKeyDown = false;
      this.registerVariableCallbacks();
      this.Traits.addAll(StaticTraits);
      StaticTraits.clear();
      this.dir = IsoDirections.W;
      this.nutrition = new Nutrition(this);
      this.fitness = new Fitness(this);
      this.PathSpeed = 0.08F;
      this.initWornItems("Human");
      this.initAttachedItems("Human");
      this.clothingWetness = new ClothingWetness(this);
      if (var2 != null) {
         this.descriptor = var2;
      } else {
         this.descriptor = new SurvivorDesc();
      }

      this.setFemale(this.descriptor.isFemale());
      this.Dressup(this.descriptor);
      this.getHumanVisual().copyFrom(this.descriptor.humanVisual);
      this.InitSpriteParts(this.descriptor);
      LuaEventManager.triggerEvent("OnCreateLivingCharacter", this, this.descriptor);
      if (!GameClient.bClient && !GameServer.bServer) {
      }

      this.descriptor.Instance = this;
      this.SpeakColour = new Color(Rand.Next(135) + 120, Rand.Next(135) + 120, Rand.Next(135) + 120, 255);
      if (GameClient.bClient) {
         if (Core.getInstance().getMpTextColor() != null) {
            this.SpeakColour = new Color(Core.getInstance().getMpTextColor().r, Core.getInstance().getMpTextColor().g, Core.getInstance().getMpTextColor().b, 1.0F);
         } else {
            Core.getInstance().setMpTextColor(new ColorInfo(this.SpeakColour.r, this.SpeakColour.g, this.SpeakColour.b, 1.0F));

            try {
               Core.getInstance().saveOptions();
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }
      }

      if (Core.GameMode.equals("LastStand")) {
         this.Traits.add("Strong");
      }

      if (this.Traits.Strong.isSet()) {
         this.maxWeightDelta = 1.5F;
      }

      if (this.Traits.Weak.isSet()) {
         this.maxWeightDelta = 0.75F;
      }

      if (this.Traits.Feeble.isSet()) {
         this.maxWeightDelta = 0.9F;
      }

      if (this.Traits.Stout.isSet()) {
         this.maxWeightDelta = 1.25F;
      }

      this.descriptor.temper = 5.0F;
      if (this.Traits.ShortTemper.isSet()) {
         this.descriptor.temper = 7.5F;
      } else if (this.Traits.Patient.isSet()) {
         this.descriptor.temper = 2.5F;
      }

      if (this.Traits.Injured.isSet()) {
         this.getBodyDamage().AddRandomDamage();
      }

      this.bMultiplayer = GameServer.bServer || GameClient.bClient;
      this.zombiesToSend = new ZombieSortedList(this, 20);
      this.vehicle4testCollision = null;
      if (Core.bDebug && DebugOptions.instance.CheatPlayerStartInvisible.getValue()) {
         this.m_ghostMode = true;
         this.setGodMod(true);
      }

      this.actionContext.setGroup(ActionGroup.getActionGroup("player"));
      this.initializeStates();
      DebugFileWatcher.instance.add(m_isoPlayerTriggerWatcher);
      this.m_setClothingTriggerWatcher = new PredicatedFileWatcher(ZomboidFileSystem.instance.getMessagingDirSub("Trigger_SetClothing.xml"), TriggerXmlFile.class, this::onTrigger_setClothingToXmlTriggerFile);
   }

   private void registerVariableCallbacks() {
      this.setVariable("CombatSpeed", () -> {
         return this.combatSpeed;
      }, (var1) -> {
         this.combatSpeed = var1;
      });
      this.setVariable("CriticalHit", () -> {
         return this.isCrit;
      }, (var1) -> {
         this.isCrit = var1;
      });
      this.setVariable("TurnDelta", () -> {
         return this.m_turnDelta;
      }, (var1) -> {
         this.m_turnDelta = var1;
      });
      this.setVariable("bKnockedDown", () -> {
         return this.m_bKnockedDown;
      }, (var1) -> {
         this.m_bKnockedDown = var1;
      });
      this.setVariable("sneaking", this::isSneaking, this::setSneaking);
      this.setVariable("initiateAttack", () -> {
         return this.initiateAttack;
      }, (var1) -> {
         this.initiateAttack = var1;
      });
      this.setVariable("isMoving", this::isPlayerMoving);
      this.setVariable("isRunning", this::isRunning, this::setRunning);
      this.setVariable("isSprinting", this::isSprinting, this::setSprinting);
      this.setVariable("run", this::isRunning, this::setRunning);
      this.setVariable("sprint", this::isSprinting, this::setSprinting);
      this.setVariable("isStrafing", this::isStrafing);
      this.setVariable("WalkSpeed", () -> {
         return this.m_walkSpeed;
      }, (var1) -> {
         this.m_walkSpeed = var1;
      });
      this.setVariable("WalkInjury", () -> {
         return this.m_walkInjury;
      }, (var1) -> {
         this.m_walkInjury = var1;
      });
      this.setVariable("RunSpeed", () -> {
         return this.m_runSpeed;
      }, (var1) -> {
         this.m_runSpeed = var1;
      });
      this.setVariable("IdleSpeed", () -> {
         return this.m_idleSpeed;
      }, (var1) -> {
         this.m_idleSpeed = var1;
      });
      this.setVariable("DeltaX", () -> {
         return this.m_deltaX;
      }, (var1) -> {
         this.m_deltaX = var1;
      });
      this.setVariable("DeltaY", () -> {
         return this.m_deltaY;
      }, (var1) -> {
         this.m_deltaY = var1;
      });
      this.setVariable("Windspeed", () -> {
         return this.m_windspeed;
      }, (var1) -> {
         this.m_windspeed = var1;
      });
      this.setVariable("WindForce", () -> {
         return this.m_windForce;
      }, (var1) -> {
         this.m_windForce = var1;
      });
      this.setVariable("IPX", () -> {
         return this.m_IPX;
      }, (var1) -> {
         this.m_IPX = var1;
      });
      this.setVariable("IPY", () -> {
         return this.m_IPY;
      }, (var1) -> {
         this.m_IPY = var1;
      });
      this.setVariable("MoveDelta", () -> {
         return this.m_moveDelta;
      }, (var1) -> {
         this.m_moveDelta = var1;
      });
      this.setVariable("attacktype", () -> {
         return this.attackType;
      });
      this.setVariable("aim", this::isAiming);
      this.setVariable("bdead", () -> {
         return (!GameClient.bClient || this.bSentDeath) && this.isDead();
      });
      this.setVariable("bdoshove", () -> {
         return this.bDoShove;
      });
      this.setVariable("bfalling", () -> {
         return this.z > 0.0F && this.fallTime > 2.0F;
      });
      this.setVariable("baimatfloor", this::isAimAtFloor);
      this.setVariable("attackfrombehind", () -> {
         return this.attackFromBehind;
      });
      this.setVariable("bundervehicle", this::isUnderVehicle);
      this.setVariable("reanimatetimer", this::getReanimateTimer);
      this.setVariable("isattacking", this::isAttacking);
      this.setVariable("beensprintingfor", this::getBeenSprintingFor);
      this.setVariable("bannedAttacking", () -> {
         return this.bBannedAttacking;
      });
      this.setVariable("meleePressed", () -> {
         return this.m_meleePressed;
      });
      this.setVariable("AttackAnim", false);
      this.setVariable("BumpFall", false);
      this.setVariable("IsPerformingAnAction", this::isPerformingAnAction, this::setPerformingAnAction);
   }

   public Vector2 getDeferredMovement(Vector2 var1) {
      super.getDeferredMovement(var1);
      if (DebugOptions.instance.CheatPlayerInvisibleSprint.getValue() && this.isGhostMode() && (this.IsRunning() || this.isSprinting()) && !this.isCurrentState(ClimbOverFenceState.instance()) && !this.isCurrentState(ClimbThroughWindowState.instance())) {
         if (this.getPath2() == null && !this.pressedMovement(false)) {
            return var1.set(0.0F, 0.0F);
         }

         if (this.getCurrentBuilding() != null) {
            var1.scale(2.5F);
            return var1;
         }

         var1.scale(7.5F);
      }

      return var1;
   }

   public float getTurnDelta() {
      return !DebugOptions.instance.CheatPlayerInvisibleSprint.getValue() || !this.isGhostMode() || !this.isRunning() && !this.isSprinting() ? super.getTurnDelta() : 10.0F;
   }

   public void setPerformingAnAction(boolean var1) {
      this.m_isPerformingAnAction = var1;
   }

   public boolean isPerformingAnAction() {
      return this.m_isPerformingAnAction;
   }

   public boolean isAttacking() {
      return !StringUtils.isNullOrWhitespace(this.getAttackType());
   }

   public boolean shouldBeTurning() {
      if (this.isPerformingAnAction()) {
      }

      return super.shouldBeTurning();
   }

   public static void invokeOnPlayerInstance(Runnable var0) {
      synchronized(instanceLock) {
         if (instance != null) {
            var0.run();
         }

      }
   }

   public static IsoPlayer getInstance() {
      return instance;
   }

   public static void setInstance(IsoPlayer var0) {
      synchronized(instanceLock) {
         instance = var0;
      }
   }

   public static boolean hasInstance() {
      return instance != null;
   }

   private static void onTrigger_ResetIsoPlayerModel(String var0) {
      if (instance != null) {
         DebugLog.log(DebugType.General, "DebugFileWatcher Hit. Resetting player model: " + var0);
         instance.resetModel();
      } else {
         DebugLog.log(DebugType.General, "DebugFileWatcher Hit. Player instance null : " + var0);
      }

   }

   public static Stack getStaticTraits() {
      return StaticTraits;
   }

   public static int getFollowDeadCount() {
      return FollowDeadCount;
   }

   public static void setFollowDeadCount(int var0) {
      FollowDeadCount = var0;
   }

   public static ArrayList getAllFileNames() {
      ArrayList var0 = new ArrayList();
      String var1 = ZomboidFileSystem.instance.getGameModeCacheDir() + File.separator + Core.GameSaveWorld;

      for(int var2 = 1; var2 < 100; ++var2) {
         File var3 = new File(var1 + File.separator + "map_p" + var2 + ".bin");
         if (var3.exists()) {
            var0.add("map_p" + var2 + ".bin");
         }
      }

      return var0;
   }

   public static String getUniqueFileName() {
      int var0 = 0;
      String var1 = ZomboidFileSystem.instance.getGameModeCacheDir() + File.separator + Core.GameSaveWorld;

      for(int var2 = 1; var2 < 100; ++var2) {
         File var3 = new File(var1 + File.separator + "map_p" + var2 + ".bin");
         if (var3.exists()) {
            var0 = var2;
         }
      }

      ++var0;
      return ZomboidFileSystem.instance.getFileNameInCurrentSave("map_p" + var0 + ".bin");
   }

   public static ArrayList getAllSavedPlayers() {
      ArrayList var0;
      if (GameClient.bClient) {
         var0 = ClientPlayerDB.getInstance().getAllNetworkPlayers();
      } else {
         var0 = PlayerDB.getInstance().getAllLocalPlayers();
      }

      for(int var1 = var0.size() - 1; var1 >= 0; --var1) {
         if (((IsoPlayer)var0.get(var1)).isDead()) {
            var0.remove(var1);
         }
      }

      return var0;
   }

   public static boolean isServerPlayerIDValid(String var0) {
      if (GameClient.bClient) {
         String var1 = ServerOptions.instance.ServerPlayerID.getValue();
         return var1 != null && !var1.isEmpty() ? var1.equals(var0) : true;
      } else {
         return true;
      }
   }

   public static int getPlayerIndex() {
      return instance == null ? assumedPlayer : instance.PlayerIndex;
   }

   public static boolean allPlayersDead() {
      for(int var0 = 0; var0 < numPlayers; ++var0) {
         if (players[var0] != null && !players[var0].isDead()) {
            return false;
         }
      }

      if (IsoWorld.instance != null && !IsoWorld.instance.AddCoopPlayers.isEmpty()) {
         return false;
      } else {
         return true;
      }
   }

   public static ArrayList getPlayers() {
      return new ArrayList(Arrays.asList(players));
   }

   public static boolean allPlayersAsleep() {
      int var0 = 0;
      int var1 = 0;

      for(int var2 = 0; var2 < numPlayers; ++var2) {
         if (players[var2] != null && !players[var2].isDead()) {
            ++var0;
            if (players[var2] != null && players[var2].isAsleep()) {
               ++var1;
            }
         }
      }

      return var0 > 0 && var0 == var1;
   }

   public static boolean getCoopPVP() {
      return CoopPVP;
   }

   public static void setCoopPVP(boolean var0) {
      CoopPVP = var0;
   }

   public void TestZombieSpotPlayer(IsoMovingObject var1) {
      var1.spotted(this, false);
      if (var1 instanceof IsoZombie) {
         float var2 = var1.DistTo(this);
         if (var2 < this.closestZombie && !var1.isOnFloor()) {
            this.closestZombie = var2;
         }
      }

   }

   public float getPathSpeed() {
      float var1 = this.getMoveSpeed() * 0.9F;
      switch(this.Moodles.getMoodleLevel(MoodleType.Endurance)) {
      case 1:
         var1 *= 0.95F;
         break;
      case 2:
         var1 *= 0.9F;
         break;
      case 3:
         var1 *= 0.8F;
         break;
      case 4:
         var1 *= 0.6F;
      }

      if (this.stats.enduranceRecharging) {
         var1 *= 0.85F;
      }

      if (this.getMoodles().getMoodleLevel(MoodleType.HeavyLoad) > 0) {
         float var2 = this.getInventory().getCapacityWeight();
         float var3 = (float)this.getMaxWeight();
         float var4 = Math.min(2.0F, var2 / var3) - 1.0F;
         var1 *= 0.65F + 0.35F * (1.0F - var4);
      }

      return var1;
   }

   public boolean isGhostMode() {
      return this.m_ghostMode;
   }

   public void setGhostMode(boolean var1) {
      this.m_ghostMode = var1;
   }

   public boolean isSeeEveryone() {
      if (Core.bDebug && DebugOptions.instance.CheatPlayerSeeEveryone.getValue()) {
         return true;
      } else {
         return this.isGhostMode() && GameClient.bClient;
      }
   }

   public Vector2 getPlayerMoveDir() {
      return this.playerMoveDir;
   }

   public void setPlayerMoveDir(Vector2 var1) {
      this.playerMoveDir.set(var1);
   }

   public void MoveUnmodded(Vector2 var1) {
      if (this.getSlowFactor() > 0.0F) {
         var1.x *= 1.0F - this.getSlowFactor();
         var1.y *= 1.0F - this.getSlowFactor();
      }

      super.MoveUnmodded(var1);
   }

   public void nullifyAiming() {
      this.isCharging = false;
      this.setIsAiming(false);
   }

   public boolean isAimKeyDown() {
      if (this.PlayerIndex != 0) {
         return false;
      } else {
         int var1 = Core.getInstance().getKey("Aim");
         boolean var2 = GameKeyboard.isKeyDown(var1);
         if (!var2) {
            return false;
         } else {
            boolean var3 = var1 == 29 || var1 == 157;
            return !var3 || !UIManager.isMouseOverInventory();
         }
      }
   }

   private void initializeStates() {
      HashMap var1 = this.getStateUpdateLookup();
      var1.clear();
      if (this.getVehicle() == null) {
         var1.put("actions", PlayerActionsState.instance());
         var1.put("climbfence", ClimbOverFenceState.instance());
         var1.put("climbdownrope", ClimbDownSheetRopeState.instance());
         var1.put("climbrope", ClimbSheetRopeState.instance());
         var1.put("climbwall", ClimbOverWallState.instance());
         var1.put("climbwindow", ClimbThroughWindowState.instance());
         var1.put("emote", PlayerEmoteState.instance());
         var1.put("ext", PlayerExtState.instance());
         var1.put("sitext", PlayerExtState.instance());
         var1.put("falldown", PlayerFallDownState.instance());
         var1.put("falling", PlayerFallingState.instance());
         var1.put("getup", PlayerGetUpState.instance());
         var1.put("idle", IdleState.instance());
         var1.put("melee", SwipeStatePlayer.instance());
         var1.put("shove", SwipeStatePlayer.instance());
         var1.put("ranged", SwipeStatePlayer.instance());
         var1.put("onground", PlayerOnGroundState.instance());
         var1.put("openwindow", OpenWindowState.instance());
         var1.put("closewindow", CloseWindowState.instance());
         var1.put("smashwindow", SmashWindowState.instance());
         var1.put("fishing", FishingState.instance());
         var1.put("fitness", FitnessState.instance());
         var1.put("hitreaction", PlayerHitReactionState.instance());
         var1.put("collide", CollideWithWallState.instance());
         var1.put("bumped", BumpedState.instance());
         var1.put("bumped-bump", BumpedState.instance());
         var1.put("sitonground", PlayerSitOnGroundState.instance());
      } else {
         var1.put("idle", IdleState.instance());
         var1.put("melee", SwipeStatePlayer.instance());
         var1.put("shove", SwipeStatePlayer.instance());
         var1.put("ranged", SwipeStatePlayer.instance());
      }

   }

   public ActionContext getActionContext() {
      return this.actionContext;
   }

   protected void onAnimPlayerCreated(AnimationPlayer var1) {
      super.onAnimPlayerCreated(var1);
      var1.addBoneReparent("Bip01_L_Thigh", "Bip01");
      var1.addBoneReparent("Bip01_R_Thigh", "Bip01");
      var1.addBoneReparent("Bip01_L_Clavicle", "Bip01_Spine1");
      var1.addBoneReparent("Bip01_R_Clavicle", "Bip01_Spine1");
      var1.addBoneReparent("Bip01_Prop1", "Bip01_R_Hand");
      var1.addBoneReparent("Bip01_Prop2", "Bip01_L_Hand");
   }

   public String GetAnimSetName() {
      return this.getVehicle() == null ? "player" : "player-vehicle";
   }

   public boolean IsInMeleeAttack() {
      return this.isCurrentState(SwipeStatePlayer.instance());
   }

   public void load(ByteBuffer var1, int var2) throws IOException {
      byte var3 = var1.get();
      int var4 = var1.getInt();
      super.load(var1, var2);
      this.setHoursSurvived(var1.getDouble());
      SurvivorDesc var5 = this.descriptor;
      this.setFemale(var5.isFemale());
      this.InitSpriteParts(var5);
      this.SpeakColour = new Color(Rand.Next(135) + 120, Rand.Next(135) + 120, Rand.Next(135) + 120, 255);
      if (GameClient.bClient) {
         if (Core.getInstance().getMpTextColor() != null) {
            this.SpeakColour = new Color(Core.getInstance().getMpTextColor().r, Core.getInstance().getMpTextColor().g, Core.getInstance().getMpTextColor().b, 1.0F);
         } else {
            Core.getInstance().setMpTextColor(new ColorInfo(this.SpeakColour.r, this.SpeakColour.g, this.SpeakColour.b, 1.0F));

            try {
               Core.getInstance().saveOptions();
            } catch (IOException var11) {
               var11.printStackTrace();
            }
         }
      }

      this.PathSpeed = 0.07F;
      this.setZombieKills(var1.getInt());
      ArrayList var6 = this.savedInventoryItems;
      byte var7 = var1.get();

      for(int var8 = 0; var8 < var7; ++var8) {
         String var9 = GameWindow.ReadString(var1);
         short var10 = var1.getShort();
         if (var10 >= 0 && var10 < var6.size() && this.wornItems.getBodyLocationGroup().getLocation(var9) != null) {
            this.wornItems.setItem(var9, (InventoryItem)var6.get(var10));
         }
      }

      short var12 = var1.getShort();
      if (var12 >= 0 && var12 < var6.size()) {
         this.leftHandItem = (InventoryItem)var6.get(var12);
      }

      var12 = var1.getShort();
      if (var12 >= 0 && var12 < var6.size()) {
         this.rightHandItem = (InventoryItem)var6.get(var12);
      }

      this.setVariable("Weapon", WeaponType.getWeaponType((IsoGameCharacter)this).type);
      this.setSurvivorKills(var1.getInt());
      this.initSpritePartsEmpty();
      this.nutrition.load(var1);
      this.setAllChatMuted(var1.get() == 1);
      this.tagPrefix = GameWindow.ReadString(var1);
      this.setTagColor(new ColorInfo(var1.getFloat(), var1.getFloat(), var1.getFloat(), 1.0F));
      this.setDisplayName(GameWindow.ReadString(var1));
      this.showTag = var1.get() == 1;
      this.factionPvp = var1.get() == 1;
      if (var1.get() == 1) {
         this.savedVehicleX = var1.getFloat();
         this.savedVehicleY = var1.getFloat();
         this.savedVehicleSeat = (short)var1.get();
         this.savedVehicleRunning = var1.get() == 1;
         this.z = 0.0F;
      }

      int var13 = var1.getInt();

      for(int var14 = 0; var14 < var13; ++var14) {
         this.mechanicsItem.put(var1.getLong(), var1.getLong());
      }

      this.fitness.load(var1, var2);
   }

   public void save(ByteBuffer var1) throws IOException {
      IsoPlayer var2 = instance;
      instance = this;

      try {
         super.save(var1);
      } finally {
         instance = var2;
      }

      var1.putDouble(this.getHoursSurvived());
      var1.putInt(this.getZombieKills());
      if (this.wornItems.size() > 127) {
         throw new RuntimeException("too many worn items");
      } else {
         var1.put((byte)this.wornItems.size());
         this.wornItems.forEach((var2x) -> {
            GameWindow.WriteString(var1, var2x.getLocation());
            var1.putShort((short)this.savedInventoryItems.indexOf(var2x.getItem()));
         });
         var1.putShort((short)this.savedInventoryItems.indexOf(this.getPrimaryHandItem()));
         var1.putShort((short)this.savedInventoryItems.indexOf(this.getSecondaryHandItem()));
         var1.putInt(this.getSurvivorKills());
         this.nutrition.save(var1);
         var1.put((byte)(this.isAllChatMuted() ? 1 : 0));
         GameWindow.WriteString(var1, this.tagPrefix);
         var1.putFloat(this.getTagColor().r);
         var1.putFloat(this.getTagColor().g);
         var1.putFloat(this.getTagColor().b);
         GameWindow.WriteString(var1, this.displayName);
         var1.put((byte)(this.showTag ? 1 : 0));
         var1.put((byte)(this.factionPvp ? 1 : 0));
         if (this.vehicle != null) {
            var1.put((byte)1);
            var1.putFloat(this.vehicle.x);
            var1.putFloat(this.vehicle.y);
            var1.put((byte)this.vehicle.getSeat(this));
            var1.put((byte)(this.vehicle.isEngineRunning() ? 1 : 0));
         } else {
            var1.put((byte)0);
         }

         var1.putInt(this.mechanicsItem.size());
         Iterator var3 = this.mechanicsItem.keySet().iterator();

         while(var3.hasNext()) {
            Long var4 = (Long)var3.next();
            var1.putLong(var4);
            var1.putLong((Long)this.mechanicsItem.get(var4));
         }

         this.fitness.save(var1);
      }
   }

   public void save() throws IOException {
      synchronized(SliceY.SliceBufferLock) {
         ByteBuffer var2 = SliceY.SliceBuffer;
         var2.clear();
         var2.put((byte)80);
         var2.put((byte)76);
         var2.put((byte)89);
         var2.put((byte)82);
         var2.putInt(175);
         GameWindow.WriteString(var2, this.bMultiplayer ? ServerOptions.instance.ServerPlayerID.getValue() : "");
         var2.putInt((int)(this.x / 10.0F));
         var2.putInt((int)(this.y / 10.0F));
         var2.putInt((int)this.x);
         var2.putInt((int)this.y);
         var2.putInt((int)this.z);
         this.save(var2);
         File var3 = new File(ZomboidFileSystem.instance.getGameModeCacheDir() + Core.GameSaveWorld + File.separator + "map_p.bin");
         if (!Core.getInstance().isNoSave()) {
            FileOutputStream var4 = new FileOutputStream(var3);
            Throwable var5 = null;

            try {
               BufferedOutputStream var6 = new BufferedOutputStream(var4);
               Throwable var7 = null;

               try {
                  var6.write(var2.array(), 0, var2.position());
               } catch (Throwable var33) {
                  var7 = var33;
                  throw var33;
               } finally {
                  if (var6 != null) {
                     if (var7 != null) {
                        try {
                           var6.close();
                        } catch (Throwable var32) {
                           var7.addSuppressed(var32);
                        }
                     } else {
                        var6.close();
                     }
                  }

               }
            } catch (Throwable var35) {
               var5 = var35;
               throw var35;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var31) {
                        var5.addSuppressed(var31);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         }

         if (this.getVehicle() != null && !GameClient.bClient) {
            VehiclesDB2.instance.updateVehicleAndTrailer(this.getVehicle());
         }

      }
   }

   public void save(String var1) throws IOException {
      this.SaveFileName = var1;
      synchronized(SliceY.SliceBufferLock) {
         SliceY.SliceBuffer.clear();
         SliceY.SliceBuffer.putInt(175);
         GameWindow.WriteString(SliceY.SliceBuffer, this.bMultiplayer ? ServerOptions.instance.ServerPlayerID.getValue() : "");
         this.save(SliceY.SliceBuffer);
         File var3 = new File(var1);
         FileOutputStream var4 = new FileOutputStream(var3);
         Throwable var5 = null;

         try {
            BufferedOutputStream var6 = new BufferedOutputStream(var4);
            Throwable var7 = null;

            try {
               var6.write(SliceY.SliceBuffer.array(), 0, SliceY.SliceBuffer.position());
            } catch (Throwable var33) {
               var7 = var33;
               throw var33;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var32) {
                        var7.addSuppressed(var32);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } catch (Throwable var35) {
            var5 = var35;
            throw var35;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var31) {
                     var5.addSuppressed(var31);
                  }
               } else {
                  var4.close();
               }
            }

         }

      }
   }

   public void load(String var1) throws IOException {
      File var2 = new File(var1);
      if (var2.exists()) {
         this.SaveFileName = var1;
         FileInputStream var3 = new FileInputStream(var2);
         Throwable var4 = null;

         try {
            BufferedInputStream var5 = new BufferedInputStream(var3);
            Throwable var6 = null;

            try {
               synchronized(SliceY.SliceBufferLock) {
                  SliceY.SliceBuffer.clear();
                  int var8 = var5.read(SliceY.SliceBuffer.array());
                  SliceY.SliceBuffer.limit(var8);
                  int var9 = SliceY.SliceBuffer.getInt();
                  if (var9 >= 69) {
                     this.SaveFileIP = GameWindow.ReadStringUTF(SliceY.SliceBuffer);
                     if (var9 < 71) {
                        this.SaveFileIP = ServerOptions.instance.ServerPlayerID.getValue();
                     }
                  } else if (GameClient.bClient) {
                     this.SaveFileIP = ServerOptions.instance.ServerPlayerID.getValue();
                  }

                  this.load(SliceY.SliceBuffer, var9);
               }
            } catch (Throwable var35) {
               var6 = var35;
               throw var35;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var33) {
                        var6.addSuppressed(var33);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (Throwable var37) {
            var4 = var37;
            throw var37;
         } finally {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var32) {
                     var4.addSuppressed(var32);
                  }
               } else {
                  var3.close();
               }
            }

         }

      }
   }

   public void setVehicle4TestCollision(BaseVehicle var1) {
      this.vehicle4testCollision = var1;
   }

   public boolean isSaveFileInUse() {
      if (this.SaveFileName == null) {
         return false;
      } else {
         for(int var1 = 0; var1 < numPlayers; ++var1) {
            if (players[var1] != null && this.SaveFileName.equals(players[var1].SaveFileName)) {
               return true;
            }
         }

         return false;
      }
   }

   public void removeSaveFile() {
      try {
         if (PlayerDB.isAvailable()) {
            PlayerDB.getInstance().saveLocalPlayersForce();
         }

         if (this.isNPC() && this.SaveFileName != null) {
            File var1 = new File(this.SaveFileName);
            if (var1.exists()) {
               var1.delete();
            }
         }
      } catch (Exception var2) {
         ExceptionLogger.logException(var2);
      }

   }

   public boolean isSaveFileIPValid() {
      return isServerPlayerIDValid(this.SaveFileIP);
   }

   public String getObjectName() {
      return "Player";
   }

   public int getJoypadBind() {
      return this.JoypadBind;
   }

   public boolean isLBPressed() {
      return this.JoypadBind == -1 ? false : JoypadManager.instance.isLBPressed(this.JoypadBind);
   }

   public Vector2 getControllerAimDir(Vector2 var1) {
      if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1 && this.bJoypadMovementActive) {
         float var2 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
         float var3 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
         if (this.bJoypadIgnoreAimUntilCentered) {
            if (var1.set(var2, var3).getLengthSquared() > 0.0F) {
               return var1.set(0.0F, 0.0F);
            }

            this.bJoypadIgnoreAimUntilCentered = false;
         }

         if (var1.set(var2, var3).getLength() < 0.3F) {
            var3 = 0.0F;
            var2 = 0.0F;
         }

         if (var2 == 0.0F && var3 == 0.0F) {
            return var1.set(0.0F, 0.0F);
         }

         var1.set(var2, var3);
         var1.normalize();
         var1.rotate(-0.7853982F);
      }

      return var1;
   }

   public Vector2 getMouseAimVector(Vector2 var1) {
      int var2 = Mouse.getX();
      int var3 = Mouse.getY();
      var1.x = IsoUtils.XToIso((float)var2, (float)var3 + 55.0F * this.def.getScaleY(), this.getZ()) - this.getX();
      var1.y = IsoUtils.YToIso((float)var2, (float)var3 + 55.0F * this.def.getScaleY(), this.getZ()) - this.getY();
      var1.normalize();
      return var1;
   }

   public Vector2 getAimVector(Vector2 var1) {
      return this.JoypadBind == -1 ? this.getMouseAimVector(var1) : this.getControllerAimDir(var1);
   }

   public float getGlobalMovementMod(boolean var1) {
      return !this.isGhostMode() && this.getAccessLevel().equals("None") && !this.isNoClip() ? super.getGlobalMovementMod(var1) : 1.0F;
   }

   public boolean isInTrees2(boolean var1) {
      return !this.isGhostMode() && this.getAccessLevel().equals("None") && !this.isNoClip() ? super.isInTrees2(var1) : false;
   }

   public float getMoveSpeed() {
      float var1 = 1.0F;

      for(int var2 = BodyPartType.ToIndex(BodyPartType.UpperLeg_L); var2 <= BodyPartType.ToIndex(BodyPartType.Foot_R); ++var2) {
         BodyPart var3 = this.getBodyDamage().getBodyPart(BodyPartType.FromIndex(var2));
         float var4 = 1.0F;
         if (var3.getFractureTime() > 20.0F) {
            var4 = 0.4F;
            if (var3.getFractureTime() > 50.0F) {
               var4 = 0.3F;
            }

            if (var3.getSplintFactor() > 0.0F) {
               var4 += var3.getSplintFactor() / 10.0F;
            }
         }

         if (var3.getFractureTime() < 20.0F && var3.getSplintFactor() > 0.0F) {
            var4 = 0.8F;
         }

         if (var4 > 0.7F && var3.getDeepWoundTime() > 0.0F) {
            var4 = 0.7F;
            if (var3.bandaged()) {
               var4 += 0.2F;
            }
         }

         if (var4 < var1) {
            var1 = var4;
         }
      }

      if (var1 != 1.0F) {
         return this.MoveSpeed * var1;
      } else if (this.getMoodles().getMoodleLevel(MoodleType.Panic) >= 4 && this.Traits.AdrenalineJunkie.isSet()) {
         float var5 = 1.0F;
         int var6 = this.getMoodles().getMoodleLevel(MoodleType.Panic) + 1;
         var5 += (float)var6 / 50.0F;
         return this.MoveSpeed * var5;
      } else {
         return this.MoveSpeed;
      }
   }

   public void setMoveSpeed(float var1) {
      this.MoveSpeed = var1;
   }

   public float getTorchStrength() {
      if (this.bRemote) {
         return this.mpTorchStrength;
      } else {
         InventoryItem var1 = this.getActiveLightItem();
         return var1 != null ? var1.getLightStrength() : 0.0F;
      }
   }

   public void Scratched() {
   }

   public void Bitten() {
   }

   public float getInvAimingMod() {
      int var1 = this.getPerkLevel(PerkFactory.Perks.Aiming);
      if (var1 == 1) {
         return 0.9F;
      } else if (var1 == 2) {
         return 0.86F;
      } else if (var1 == 3) {
         return 0.82F;
      } else if (var1 == 4) {
         return 0.74F;
      } else if (var1 == 5) {
         return 0.7F;
      } else if (var1 == 6) {
         return 0.66F;
      } else if (var1 == 7) {
         return 0.62F;
      } else if (var1 == 8) {
         return 0.58F;
      } else if (var1 == 9) {
         return 0.54F;
      } else {
         return var1 == 10 ? 0.5F : 0.9F;
      }
   }

   public float getAimingMod() {
      int var1 = this.getPerkLevel(PerkFactory.Perks.Aiming);
      if (var1 == 1) {
         return 1.1F;
      } else if (var1 == 2) {
         return 1.14F;
      } else if (var1 == 3) {
         return 1.18F;
      } else if (var1 == 4) {
         return 1.22F;
      } else if (var1 == 5) {
         return 1.26F;
      } else if (var1 == 6) {
         return 1.3F;
      } else if (var1 == 7) {
         return 1.34F;
      } else if (var1 == 8) {
         return 1.36F;
      } else if (var1 == 9) {
         return 1.4F;
      } else {
         return var1 == 10 ? 1.5F : 1.0F;
      }
   }

   public float getReloadingMod() {
      int var1 = this.getPerkLevel(PerkFactory.Perks.Reloading);
      return 3.5F - (float)var1 * 0.25F;
   }

   public float getAimingRangeMod() {
      int var1 = this.getPerkLevel(PerkFactory.Perks.Aiming);
      if (var1 == 1) {
         return 1.2F;
      } else if (var1 == 2) {
         return 1.28F;
      } else if (var1 == 3) {
         return 1.36F;
      } else if (var1 == 4) {
         return 1.42F;
      } else if (var1 == 5) {
         return 1.5F;
      } else if (var1 == 6) {
         return 1.58F;
      } else if (var1 == 7) {
         return 1.66F;
      } else if (var1 == 8) {
         return 1.72F;
      } else if (var1 == 9) {
         return 1.8F;
      } else {
         return var1 == 10 ? 2.0F : 1.1F;
      }
   }

   public boolean isPathfindRunning() {
      return this.pathfindRun;
   }

   public void setPathfindRunning(boolean var1) {
      this.pathfindRun = var1;
   }

   public boolean isBannedAttacking() {
      return this.bBannedAttacking;
   }

   public void setBannedAttacking(boolean var1) {
      this.bBannedAttacking = var1;
   }

   public float getInvAimingRangeMod() {
      int var1 = this.getPerkLevel(PerkFactory.Perks.Aiming);
      if (var1 == 1) {
         return 0.8F;
      } else if (var1 == 2) {
         return 0.7F;
      } else if (var1 == 3) {
         return 0.62F;
      } else if (var1 == 4) {
         return 0.56F;
      } else if (var1 == 5) {
         return 0.45F;
      } else if (var1 == 6) {
         return 0.38F;
      } else if (var1 == 7) {
         return 0.31F;
      } else if (var1 == 8) {
         return 0.24F;
      } else if (var1 == 9) {
         return 0.17F;
      } else {
         return var1 == 10 ? 0.1F : 0.8F;
      }
   }

   public void CalculateAim() {
      if (this.PlayerIndex == 0) {
         Mouse.setGrabbed(this.isAiming());
      }

   }

   public void render(float var1, float var2, float var3, ColorInfo var4, boolean var5, boolean var6, Shader var7) {
      super.render(var1, var2, var3, var4, var5, var6, var7);
   }

   public void renderlast() {
      super.renderlast();
   }

   public void doBeatenVehicle(float var1, float var2, float var3, boolean var4) {
      if (GameClient.bClient && this.isLocalPlayer() && var4) {
         this.changeState(ForecastBeatenPlayerState.instance());
      } else {
         if (GameClient.bClient && !this.isLocalPlayer() && !var4) {
            GameClient.instance.sendOnBeaten(this, var1, var2, var3);
         }

         float var5 = 1.0F;
         switch(SandboxOptions.instance.DamageToPlayerFromHitByACar.getValue()) {
         case 1:
            var5 = 0.0F;
            break;
         case 2:
            var5 = 0.5F;
         case 3:
         default:
            break;
         case 4:
            var5 = 2.0F;
            break;
         case 5:
            var5 = 5.0F;
         }

         float var6 = var1 * var5;
         if (var6 > 0.0F) {
            int var7 = (int)(2.0F + var6 * 0.07F);

            for(int var8 = 0; var8 < var7; ++var8) {
               int var9 = Rand.Next(BodyPartType.ToIndex(BodyPartType.Hand_L), BodyPartType.ToIndex(BodyPartType.MAX));
               BodyPart var10 = this.getBodyDamage().getBodyPart(BodyPartType.FromIndex(var9));
               float var11 = Math.max(Rand.Next(var6 - 15.0F, var6), 5.0F);
               if (this.Traits.FastHealer.isSet()) {
                  var11 = (float)((double)var11 * 0.8D);
               } else if (this.Traits.SlowHealer.isSet()) {
                  var11 = (float)((double)var11 * 1.2D);
               }

               switch(SandboxOptions.instance.InjurySeverity.getValue()) {
               case 1:
                  var11 *= 0.5F;
                  break;
               case 3:
                  var11 *= 1.5F;
               }

               var11 = (float)((double)var11 * 0.9D);
               var10.AddDamage(var11);
               if (var11 > 40.0F && Rand.Next(12) == 0) {
                  var10.generateDeepWound();
               }

               if (var11 > 10.0F && Rand.Next(100) <= 10 && SandboxOptions.instance.BoneFracture.getValue()) {
                  var10.setFractureTime(Rand.Next(Rand.Next(10.0F, var11 + 10.0F), Rand.Next(var11 + 20.0F, var11 + 30.0F)));
               }

               if (var11 > 30.0F && Rand.Next(100) <= 80 && SandboxOptions.instance.BoneFracture.getValue() && var9 == BodyPartType.ToIndex(BodyPartType.Head)) {
                  var10.setFractureTime(Rand.Next(Rand.Next(10.0F, var11 + 10.0F), Rand.Next(var11 + 20.0F, var11 + 30.0F)));
               }

               if (var11 > 10.0F && Rand.Next(100) <= 60 && SandboxOptions.instance.BoneFracture.getValue() && var9 > BodyPartType.ToIndex(BodyPartType.Groin)) {
                  var10.setFractureTime(Rand.Next(Rand.Next(10.0F, var11 + 20.0F), Rand.Next(var11 + 30.0F, var11 + 40.0F)));
               }
            }

            this.getBodyDamage().Update();
         }

         if (this.isAlive() && this.getCurrentState() != PlayerFallDownState.instance() && !this.m_bKnockedDown) {
            this.setReanimateTimer((float)(60 + Rand.Next(120)));
            this.m_bKnockedDown = true;
         }

      }
   }

   public void update() {
      IsoPlayer.s_performance.update.invokeAndMeasure(this, IsoPlayer::updateInternal1);
   }

   private void updateInternal1() {
      boolean var1 = this.updateInternal2();
      if (var1) {
         this.updateLOS();
         super.update();
         if (GameClient.bClient && networkUpdate.Check()) {
            GameClient.instance.sendPlayer(this);
         }
      }

   }

   private boolean updateInternal2() {
      if (isTestAIMode) {
         this.isNPC = true;
      }

      if (!this.attackStarted) {
         this.initiateAttack = false;
         this.setAttackType((String)null);
      }

      if (!this.isRunning() && !this.isSprinting()) {
         this.runningTime = 0.0F;
      } else {
         this.runningTime += GameTime.getInstance().getMultiplier() / 1.6F;
      }

      if (this.getLastCollideTime() > 0.0F) {
         this.setLastCollideTime(this.getLastCollideTime() - GameTime.getInstance().getMultiplier() / 1.6F);
      }

      this.updateDeathDragDown();
      this.updateGodModeKey();
      this.doDeferredMovement();
      this.updateHitByVehicle();
      this.updateEmitter();
      this.updateMechanicsItems();
      this.updateHeavyBreathing();
      this.updateTemperatureCheck();
      this.updateAimingStance();
      if (SystemDisabler.doCharacterStats) {
         this.nutrition.update();
      }

      this.fitness.update();
      this.updateSoundListener();
      if (GameClient.bClient && this.isLocalPlayer() && this.getSafetyCooldown() > 0.0F) {
         this.setSafetyCooldown(this.getSafetyCooldown() - GameTime.instance.getRealworldSecondsSinceLastUpdate());
      }

      if (!GameClient.bClient && !GameServer.bServer && this.bDeathFinished) {
         return false;
      } else {
         if (!GameClient.bClient && this.getCurrentBuildingDef() != null) {
            this.getCurrentBuildingDef().setHasBeenVisited(true);
         }

         if (this.checkSafehouse > 0 && GameServer.bServer) {
            --this.checkSafehouse;
            if (this.checkSafehouse == 0) {
               this.checkSafehouse = 200;
               SafeHouse var1 = SafeHouse.isSafeHouse(this.getCurrentSquare(), (String)null, false);
               if (var1 != null) {
                  var1.updateSafehouse(this);
               }
            }
         }

         if (this.bRemote && this.TimeSinceLastNetData > 600) {
            IsoWorld.instance.CurrentCell.getObjectList().remove(this);
            if (this.movingSq != null) {
               this.movingSq.getMovingObjects().remove(this);
            }
         }

         this.TimeSinceLastNetData = (int)((float)this.TimeSinceLastNetData + GameTime.instance.getMultiplier());
         this.TimeSinceOpenDoor += GameTime.instance.getMultiplier();
         this.lastTargeted += GameTime.instance.getMultiplier();
         this.targetedByZombie = false;
         this.checkActionGroup();
         if (this.updateRemotePlayer()) {
            return true;
         } else {
            assert !GameServer.bServer;

            assert !this.bRemote;

            assert !GameClient.bClient || this.isLocalPlayer();

            IsoCamera.CamCharacter = this;
            instance = this;
            if (this.isLocalPlayer()) {
               IsoCamera.cameras[this.PlayerIndex].update();
               if (UIManager.getMoodleUI((double)this.PlayerIndex) != null) {
                  UIManager.getMoodleUI((double)this.PlayerIndex).setCharacter(this);
               }
            }

            if (this.closestZombie > 1.2F) {
               this.slowTimer = -1.0F;
               this.slowFactor = 0.0F;
            }

            this.ContextPanic -= 1.5F * GameTime.instance.getTimeDelta();
            if (this.ContextPanic < 0.0F) {
               this.ContextPanic = 0.0F;
            }

            this.lastSeenZombieTime += (double)(GameTime.instance.getGameWorldSecondsSinceLastUpdate() / 60.0F / 60.0F);
            LuaEventManager.triggerEvent("OnPlayerUpdate", this);
            if (this.pressedMovement(false)) {
               this.ContextPanic = 0.0F;
               this.ticksSincePressedMovement = 0.0F;
            } else {
               this.ticksSincePressedMovement += GameTime.getInstance().getMultiplier() / 1.6F;
            }

            this.setVariable("pressedMovement", this.pressedMovement(true));
            if (this.updateWhileDead()) {
               return true;
            } else {
               this.updateHeartSound();
               this.updateSneakKey();
               this.checkIsNearWall();
               this.updateExt();
               this.updateInteractKeyPanic();
               if (this.isAsleep()) {
                  this.m_isPlayerMoving = false;
               }

               if ((this.getVehicle() == null || !this.getVehicle().isDriver(this) || !this.getVehicle().hasHorn() || Core.getInstance().getKey("Shout") != Core.getInstance().getKey("VehicleHorn")) && !this.isAsleep() && this.PlayerIndex == 0 && !this.Speaking && GameKeyboard.isKeyDown(Core.getInstance().getKey("Shout")) && !this.isNPC) {
               }

               if (!this.getIgnoreMovement() && !this.isAsleep()) {
                  if (this.checkActionsBlockingMovement()) {
                     if (this.getVehicle() != null && this.getVehicle().getDriver() == this && this.getVehicle().getController() != null) {
                        this.getVehicle().getController().clientControls.reset();
                        this.getVehicle().updatePhysics();
                     }

                     return true;
                  } else {
                     this.enterExitVehicle();
                     this.checkActionGroup();
                     this.checkReloading();
                     if (this.checkActionsBlockingMovement()) {
                        return true;
                     } else if (this.getVehicle() != null) {
                        this.updateWhileInVehicle();
                        return true;
                     } else {
                        this.checkVehicleContainers();
                        this.setCollidable(true);
                        this.CalculateAim();
                        this.bSeenThisFrame = false;
                        this.bCouldBeSeenThisFrame = false;
                        if (IsoCamera.CamCharacter == null && GameClient.bClient) {
                           IsoCamera.CamCharacter = instance;
                        }

                        if (this.updateUseKey()) {
                           return true;
                        } else {
                           this.updateEnableModelsKey();
                           this.updateChangeCharacterKey();
                           boolean var11 = false;
                           boolean var2 = false;
                           this.setRunning(false);
                           this.setSprinting(false);
                           this.useChargeTime = this.chargeTime;
                           if (!this.isBlockMovement() && !this.isNPC) {
                              if (!this.isCharging && !this.isChargingLT) {
                                 this.chargeTime = 0.0F;
                              } else {
                                 this.chargeTime += 1.0F * GameTime.instance.getMultiplier();
                              }

                              this.UpdateInputState(this.inputState);
                              var2 = this.inputState.bMelee;
                              var11 = this.inputState.isAttacking;
                              this.setRunning(this.inputState.bRunning);
                              this.setSprinting(this.inputState.bSprinting);
                              if (this.isSprinting() && !this.JustMoved) {
                                 this.setSprinting(false);
                              }

                              if (this.isSprinting()) {
                                 this.setRunning(false);
                              }

                              this.setIsAiming(this.inputState.isAiming);
                              this.isCharging = this.inputState.isCharging;
                              this.isChargingLT = this.inputState.isChargingLT;
                              this.updateMovementRates();
                              if (this.isAiming()) {
                                 this.StopAllActionQueueAiming();
                              }

                              if (var11) {
                                 this.setIsAiming(true);
                              }

                              this.Waiting = false;
                              if (this.isAiming()) {
                                 this.setMoving(false);
                                 this.setRunning(false);
                                 this.setSprinting(false);
                              }

                              ++this.TicksSinceSeenZombie;
                           }

                           if ((double)this.playerMoveDir.x == 0.0D && (double)this.playerMoveDir.y == 0.0D) {
                              this.setForceRun(false);
                              this.setForceSprint(false);
                           }

                           this.movementLastFrame.x = this.playerMoveDir.x;
                           this.movementLastFrame.y = this.playerMoveDir.y;
                           if (this.stateMachine.getCurrent() != StaggerBackState.instance() && this.stateMachine.getCurrent() != FakeDeadZombieState.instance() && UIManager.speedControls != null) {
                              if (GameKeyboard.isKeyDown(88) && Translator.debug) {
                                 Translator.loadFiles();
                              }

                              this.JustMoved = false;
                              IsoPlayer.MoveVars var3 = s_moveVars;
                              this.updateMovementFromInput(var3);
                              float var4 = var3.strafeX;
                              float var5 = var3.strafeY;
                              if (this.JustMoved && !this.isNPC) {
                                 if (UIManager.getSpeedControls().getCurrentGameSpeed() > 1) {
                                    UIManager.getSpeedControls().SetCurrentGameSpeed(1);
                                 }
                              } else if (this.stats.endurance < this.stats.endurancedanger && Rand.Next((int)(300.0F * GameTime.instance.getInvMultiplier())) == 0) {
                                 this.xp.AddXP(PerkFactory.Perks.Fitness, 1.0F);
                              }

                              if (this.JustMoved) {
                                 this.setBeenMovingFor(this.getBeenMovingFor() + 1.25F * GameTime.getInstance().getMultiplier());
                              } else {
                                 this.setBeenMovingFor(this.getBeenMovingFor() - 0.625F * GameTime.getInstance().getMultiplier());
                              }

                              if (this.JustMoved && this.isSprinting()) {
                                 this.setBeenSprintingFor(this.getBeenSprintingFor() + 1.25F * GameTime.getInstance().getMultiplier());
                              } else {
                                 this.setBeenSprintingFor(0.0F);
                              }

                              float var6 = 1.0F;
                              float var7 = 0.0F;
                              if (this.JustMoved && !this.isNPC) {
                                 if (!this.isRunning() && !this.isSprinting()) {
                                    var7 = 1.0F;
                                 } else {
                                    var7 = 1.5F;
                                 }
                              }

                              var6 *= var7;
                              if (var6 > 1.0F) {
                                 var6 *= this.getSprintMod();
                              }

                              if (var6 > 1.0F && this.Traits.Athletic.isSet()) {
                                 var6 *= 1.2F;
                              }

                              if (var6 > 1.0F) {
                                 if (this.Traits.Overweight.isSet()) {
                                    var6 *= 0.99F;
                                 }

                                 if (this.Traits.Obese.isSet()) {
                                    var6 *= 0.85F;
                                 }

                                 if (this.getNutrition().getWeight() > 120.0F) {
                                    var6 *= 0.97F;
                                 }

                                 if (this.Traits.OutOfShape.isSet()) {
                                    var6 *= 0.99F;
                                 }

                                 if (this.Traits.Unfit.isSet()) {
                                    var6 *= 0.8F;
                                 }
                              }

                              this.updateEndurance(var6);
                              if (this.isAiming() && this.JustMoved) {
                                 var6 *= 0.7F;
                              }

                              if (this.isAiming()) {
                                 var6 *= this.getNimbleMod();
                              }

                              this.isWalking = false;
                              if (var6 > 0.0F && !this.isNPC) {
                                 this.isWalking = true;
                                 LuaEventManager.triggerEvent("OnPlayerMove", this);
                              }

                              if (this.JustMoved) {
                                 this.sprite.Animate = true;
                              }

                              if (this.isNPC && this.GameCharacterAIBrain != null) {
                                 var2 = this.GameCharacterAIBrain.HumanControlVars.bMelee;
                                 this.bBannedAttacking = this.GameCharacterAIBrain.HumanControlVars.bBannedAttacking;
                              }

                              this.m_meleePressed = var2;
                              if (var2) {
                                 if (!this.m_lastAttackWasShove) {
                                    this.setMeleeDelay(Math.min(this.getMeleeDelay(), 2.0F));
                                 }

                                 if (!this.bBannedAttacking && this.isAuthorizeShoveStomp() && this.CanAttack() && this.getMeleeDelay() <= 0.0F) {
                                    this.bDoShove = true;
                                    if (!this.isCharging && !this.isChargingLT) {
                                       this.setIsAiming(false);
                                    }

                                    this.AttemptAttack(this.useChargeTime);
                                    this.useChargeTime = 0.0F;
                                    this.chargeTime = 0.0F;
                                 }
                              } else if (this.isAiming() && this.CanAttack()) {
                                 if (this.DragCharacter != null) {
                                    this.DragObject = null;
                                    this.DragCharacter.Dragging = false;
                                    this.DragCharacter = null;
                                 }

                                 if (var11 && !this.bBannedAttacking) {
                                    this.sprite.Animate = true;
                                    if (this.getRecoilDelay() <= 0.0F && this.getMeleeDelay() <= 0.0F) {
                                       this.AttemptAttack(this.useChargeTime);
                                    }

                                    this.useChargeTime = 0.0F;
                                    this.chargeTime = 0.0F;
                                 }
                              }

                              if (this.isAiming() && !this.isNPC) {
                                 if (this.JoypadBind != -1 && !this.bJoypadMovementActive) {
                                    if (this.getForwardDirection().getLengthSquared() > 0.0F) {
                                       this.DirectionFromVector(this.getForwardDirection());
                                    }
                                 } else {
                                    Vector2 var8 = tempVector2.set(0.0F, 0.0F);
                                    if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
                                       this.getControllerAimDir(var8);
                                    } else {
                                       this.getMouseAimVector(var8);
                                    }

                                    if (var8.getLengthSquared() > 0.0F) {
                                       this.DirectionFromVector(var8);
                                       this.setForwardDirection(var8);
                                    }
                                 }

                                 var3.NewFacing = this.dir;
                              }

                              if (this.getForwardDirection().x == 0.0F && this.getForwardDirection().y == 0.0F) {
                                 this.setForwardDirection(this.dir.ToVector());
                              }

                              if (this.lastAngle.x != this.getForwardDirection().x || this.lastAngle.y != this.getForwardDirection().y) {
                                 this.lastAngle.x = this.getForwardDirection().x;
                                 this.lastAngle.y = this.getForwardDirection().y;
                                 this.dirtyRecalcGridStackTime = 2.0F;
                              }

                              this.stats.endurance = PZMath.clamp(this.stats.endurance, 0.0F, 1.0F);
                              AnimationPlayer var12 = this.getAnimationPlayer();
                              if (var12 != null && var12.isReady()) {
                                 float var9 = var12.getAngle() + var12.getTwistAngle();
                                 this.dir = IsoDirections.fromAngle(tempVector2.setLengthAndDirection(var9, 1.0F));
                              } else if (!this.bFalling && !this.isAiming() && !var11) {
                                 this.dir = var3.NewFacing;
                              }

                              if (this.isAiming() && (GameWindow.ActivatedJoyPad == null || this.JoypadBind == -1)) {
                                 this.playerMoveDir.x = var3.moveX;
                                 this.playerMoveDir.y = var3.moveY;
                              }

                              if (!this.isAiming() && this.JustMoved) {
                                 this.playerMoveDir.x = this.getForwardDirection().x;
                                 this.playerMoveDir.y = this.getForwardDirection().y;
                              }

                              if (this.JustMoved) {
                                 if (this.isSprinting()) {
                                    this.CurrentSpeed = 1.5F;
                                 } else if (this.isRunning()) {
                                    this.CurrentSpeed = 1.0F;
                                 } else {
                                    this.CurrentSpeed = 0.5F;
                                 }
                              } else {
                                 this.CurrentSpeed = 0.0F;
                              }

                              this.LastRT = JoypadManager.instance.isRTPressed(this.getJoypadBind());
                              boolean var13 = this.IsInMeleeAttack();
                              if (!this.CharacterActions.isEmpty()) {
                                 BaseAction var10 = (BaseAction)this.CharacterActions.get(0);
                                 if (var10.overrideAnimation) {
                                    var13 = true;
                                 }
                              }

                              if (!var13 && !this.isForceOverrideAnim()) {
                                 if (this.getPath2() == null) {
                                    if (this.CurrentSpeed > 0.0F && (!this.bClimbing || this.lastFallSpeed > 0.0F)) {
                                       if (!this.isRunning() && !this.isSprinting()) {
                                          this.StopAllActionQueueWalking();
                                       } else {
                                          this.StopAllActionQueueRunning();
                                       }
                                    }
                                 } else {
                                    this.StopAllActionQueueWalking();
                                 }
                              }

                              if (this.slowTimer > 0.0F) {
                                 this.slowTimer -= GameTime.instance.getRealworldSecondsSinceLastUpdate();
                                 this.CurrentSpeed *= 1.0F - this.slowFactor;
                                 this.slowFactor -= GameTime.instance.getMultiplier() / 100.0F;
                                 if (this.slowFactor < 0.0F) {
                                    this.slowFactor = 0.0F;
                                 }
                              } else {
                                 this.slowFactor = 0.0F;
                              }

                              this.playerMoveDir.setLength(this.CurrentSpeed);
                              if (this.playerMoveDir.x != 0.0F || this.playerMoveDir.y != 0.0F) {
                                 this.dirtyRecalcGridStackTime = 10.0F;
                              }

                              if (this.getPath2() != null && this.current != this.last) {
                                 this.dirtyRecalcGridStackTime = 10.0F;
                              }

                              this.closestZombie = 1000000.0F;
                              this.weight = 0.3F;
                              this.separate();
                              this.updateSleepingPillsTaken();
                              this.updateTorchStrength();
                              if (this.isNPC && this.GameCharacterAIBrain != null) {
                                 this.GameCharacterAIBrain.postUpdateHuman(this);
                                 this.initiateAttack = this.GameCharacterAIBrain.HumanControlVars.initiateAttack;
                                 this.setRunning(this.GameCharacterAIBrain.HumanControlVars.bRunning);
                                 var4 = this.GameCharacterAIBrain.HumanControlVars.strafeX;
                                 var5 = this.GameCharacterAIBrain.HumanControlVars.strafeY;
                                 this.JustMoved = this.GameCharacterAIBrain.HumanControlVars.JustMoved;
                                 this.updateMovementRates();
                              }

                              this.m_isPlayerMoving = this.JustMoved || this.getPath2() != null && !this.getPathFindBehavior2().bStopping;
                              if ((this.isInTrees() || this.m_walkSpeed < 0.3F || this.m_walkInjury > 0.5F) && this.isSprinting() && !this.isGhostMode()) {
                                 this.setSprinting(false);
                                 this.setForceSprint(false);
                                 if (this.isInTreesNoBush()) {
                                    this.actionContext.reportEvent("wasBumped");
                                    this.setBumpType("left");
                                    this.setVariable("BumpDone", false);
                                    this.setVariable("BumpFall", true);
                                 }
                              }

                              this.m_deltaX = var4;
                              this.m_deltaY = var5;
                              this.m_windspeed = ClimateManager.getInstance().getWindSpeedMovement();
                              float var14 = this.getForwardDirection().getDirectionNeg();
                              this.m_windForce = ClimateManager.getInstance().getWindForceMovement(this, var14);
                              return true;
                           } else {
                              return true;
                           }
                        }
                     }
                  }
               } else {
                  return true;
               }
            }
         }
      }
   }

   private void updateMovementFromInput(IsoPlayer.MoveVars var1) {
      var1.moveX = 0.0F;
      var1.moveY = 0.0F;
      var1.strafeX = 0.0F;
      var1.strafeY = 0.0F;
      var1.NewFacing = this.dir;
      if (!TutorialManager.instance.StealControl) {
         if (!this.isBlockMovement()) {
            if (!this.isNPC) {
               if (!(this.fallTime > 2.0F)) {
                  if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
                     this.updateMovementFromJoypad(var1);
                  }

                  if (this.PlayerIndex == 0 && this.JoypadBind == -1) {
                     this.updateMovementFromKeyboardMouse(var1);
                  }

                  if (this.JustMoved) {
                     this.getForwardDirection().normalize();
                     UIManager.speedControls.SetCurrentGameSpeed(1);
                  }

               }
            }
         }
      }
   }

   private void updateMovementFromJoypad(IsoPlayer.MoveVars var1) {
      this.playerMoveDir.x = 0.0F;
      this.playerMoveDir.y = 0.0F;
      this.getJoypadAimVector(tempVector2);
      float var2 = tempVector2.x;
      float var3 = tempVector2.y;
      Vector2 var4 = this.getJoypadMoveVector(tempVector2);
      if (var4.getLength() > 1.0F) {
         var4.setLength(1.0F);
      }

      float var5 = var4.x;
      float var6 = var4.y;
      Vector2 var10000;
      if (Math.abs(var5) > 0.0F) {
         var10000 = this.playerMoveDir;
         var10000.x += 0.04F * var5;
         var10000 = this.playerMoveDir;
         var10000.y -= 0.04F * var5;
         this.JustMoved = true;
      }

      if (Math.abs(var6) > 0.0F) {
         var10000 = this.playerMoveDir;
         var10000.y += 0.04F * var6;
         var10000 = this.playerMoveDir;
         var10000.x += 0.04F * var6;
         this.JustMoved = true;
      }

      if (JoypadManager.instance.isL3Pressed(this.JoypadBind)) {
         if (!this.L3Pressed) {
            this.setSneaking(!this.isSneaking());
            this.L3Pressed = true;
         }
      } else {
         this.L3Pressed = false;
      }

      this.playerMoveDir.setLength(0.05F * (float)Math.pow((double)var4.getLength(), 9.0D));
      if (var2 == 0.0F && var3 == 0.0F) {
         if ((var5 != 0.0F || var6 != 0.0F) && this.playerMoveDir.getLengthSquared() > 0.0F) {
            var4 = tempVector2.set(this.playerMoveDir);
            var4.normalize();
            var1.NewFacing = IsoDirections.fromAngle(var4);
         }
      } else {
         Vector2 var7 = tempVector2.set(var2, var3);
         var7.normalize();
         var1.NewFacing = IsoDirections.fromAngle(var7);
      }

      PathFindBehavior2 var9 = this.getPathFindBehavior2();
      if (this.playerMoveDir.x == 0.0F && this.playerMoveDir.y == 0.0F && this.getPath2() != null && var9.isStrafing() && !var9.bStopping) {
         this.playerMoveDir.set(var9.getTargetX() - this.x, var9.getTargetY() - this.y);
         this.playerMoveDir.normalize();
      }

      if (this.playerMoveDir.x != 0.0F || this.playerMoveDir.y != 0.0F) {
         if (this.isStrafing()) {
            tempo.set(this.playerMoveDir.x, -this.playerMoveDir.y);
            tempo.normalize();
            float var8 = this.legsSprite.modelSlot.model.AnimPlayer.getRenderedAngle();
            if ((double)var8 > 6.283185307179586D) {
               var8 = (float)((double)var8 - 6.283185307179586D);
            }

            if (var8 < 0.0F) {
               var8 = (float)((double)var8 + 6.283185307179586D);
            }

            tempo.rotate(var8);
            var1.strafeX = tempo.x;
            var1.strafeY = tempo.y;
            this.m_IPX = this.playerMoveDir.x;
            this.m_IPY = this.playerMoveDir.y;
         } else {
            var1.moveX = this.playerMoveDir.x;
            var1.moveY = this.playerMoveDir.y;
            tempo.set(this.playerMoveDir);
            tempo.normalize();
            this.setForwardDirection(tempo);
         }
      }

   }

   private void updateMovementFromKeyboardMouse(IsoPlayer.MoveVars var1) {
      if (!this.isIgnoreInputsForDirection()) {
         int var2 = Core.getInstance().getKey("Left");
         int var3 = Core.getInstance().getKey("Right");
         int var4 = Core.getInstance().getKey("Forward");
         int var5 = Core.getInstance().getKey("Backward");
         boolean var6 = GameKeyboard.isKeyDown(var2);
         boolean var7 = GameKeyboard.isKeyDown(var3);
         boolean var8 = GameKeyboard.isKeyDown(var4);
         boolean var9 = GameKeyboard.isKeyDown(var5);
         if (!var6 && !var7 && !var8 && !var9 || var2 != 30 && var3 != 30 && var4 != 30 && var5 != 30 || !GameKeyboard.isKeyDown(29) && !GameKeyboard.isKeyDown(157) || !UIManager.isMouseOverInventory() || !Core.getInstance().isSelectingAll()) {
            if (Core.bAltMoveMethod) {
               if (var6 && !var7) {
                  var1.moveX -= 0.04F;
                  var1.NewFacing = IsoDirections.W;
               }

               if (var7 && !var6) {
                  var1.moveX += 0.04F;
                  var1.NewFacing = IsoDirections.E;
               }

               if (var8 && !var9) {
                  var1.moveY -= 0.04F;
                  if (var1.NewFacing == IsoDirections.W) {
                     var1.NewFacing = IsoDirections.NW;
                  } else if (var1.NewFacing == IsoDirections.E) {
                     var1.NewFacing = IsoDirections.NE;
                  } else {
                     var1.NewFacing = IsoDirections.N;
                  }
               }

               if (var9 && !var8) {
                  var1.moveY += 0.04F;
                  if (var1.NewFacing == IsoDirections.W) {
                     var1.NewFacing = IsoDirections.SW;
                  } else if (var1.NewFacing == IsoDirections.E) {
                     var1.NewFacing = IsoDirections.SE;
                  } else {
                     var1.NewFacing = IsoDirections.S;
                  }
               }
            } else {
               if (var6) {
                  var1.moveX = -1.0F;
               } else if (var7) {
                  var1.moveX = 1.0F;
               }

               if (var8) {
                  var1.moveY = 1.0F;
               } else if (var9) {
                  var1.moveY = -1.0F;
               }

               if (var1.moveX != 0.0F || var1.moveY != 0.0F) {
                  tempo.set(var1.moveX, var1.moveY);
                  tempo.normalize();
                  var1.NewFacing = IsoDirections.fromAngle(tempo);
               }
            }

            PathFindBehavior2 var10 = this.getPathFindBehavior2();
            if (var1.moveX == 0.0F && var1.moveY == 0.0F && this.getPath2() != null && var10.isStrafing() && !var10.bStopping) {
               Vector2 var11 = tempo.set(var10.getTargetX() - this.x, var10.getTargetY() - this.y);
               Vector2 var12 = tempo2.set(-1.0F, 0.0F);
               float var13 = 1.0F;
               float var14 = var11.dot(var12);
               float var15 = var14 / var13;
               var12 = tempo2.set(0.0F, -1.0F);
               var14 = var11.dot(var12);
               float var16 = var14 / var13;
               tempo.set(var16, var15);
               tempo.normalize();
               tempo.rotate(0.7853982F);
               var1.moveX = tempo.x;
               var1.moveY = tempo.y;
            }

            if (var1.moveX != 0.0F || var1.moveY != 0.0F) {
               if (this.stateMachine.getCurrent() == PathFindState.instance()) {
                  this.setDefaultState();
               }

               this.JustMoved = true;
               this.m_moveDelta = 1.0F;
               if (this.isStrafing()) {
                  tempo.set(var1.moveX, var1.moveY);
                  tempo.normalize();
                  float var17 = this.legsSprite.modelSlot.model.AnimPlayer.getRenderedAngle();
                  var17 = (float)((double)var17 + 0.7853981633974483D);
                  if ((double)var17 > 6.283185307179586D) {
                     var17 = (float)((double)var17 - 6.283185307179586D);
                  }

                  if (var17 < 0.0F) {
                     var17 = (float)((double)var17 + 6.283185307179586D);
                  }

                  tempo.rotate(var17);
                  var1.strafeX = tempo.x;
                  var1.strafeY = tempo.y;
                  this.m_IPX = var1.moveX;
                  this.m_IPY = var1.moveY;
               } else {
                  tempo.set(var1.moveX, -var1.moveY);
                  tempo.normalize();
                  tempo.rotate(-0.7853982F);
                  this.setForwardDirection(tempo);
               }
            }

         }
      }
   }

   private void updateAimingStance() {
      if (this.isVariable("LeftHandMask", "RaiseHand")) {
         this.clearVariable("LeftHandMask");
      }

      if (this.isAiming() && !this.isCurrentState(SwipeStatePlayer.instance())) {
         HandWeapon var1 = (HandWeapon)Type.tryCastTo(this.getPrimaryHandItem(), HandWeapon.class);
         var1 = var1 == null ? this.bareHands : var1;
         SwipeStatePlayer.instance().calcValidTargets(this, var1, true, s_targetsProne, s_targetsStanding);
         SwipeStatePlayer.HitInfo var3 = s_targetsStanding.isEmpty() ? null : (SwipeStatePlayer.HitInfo)s_targetsStanding.get(0);
         SwipeStatePlayer.HitInfo var4 = s_targetsProne.isEmpty() ? null : (SwipeStatePlayer.HitInfo)s_targetsProne.get(0);
         if (SwipeStatePlayer.instance().isProneTargetBetter(this, var3, var4)) {
            var3 = null;
         }

         boolean var5 = this.getVariableBoolean("AttackAnim") || this.getVariableBoolean("ShoveAnim") || this.getVariableBoolean("StompAnim");
         if (!var5) {
            this.setAimAtFloor(false);
         }

         if (var3 != null) {
            if (!var5) {
               this.setAimAtFloor(false);
            }
         } else if (var4 != null && !var5) {
            this.setAimAtFloor(true);
         }

         if (var3 != null) {
            boolean var6 = !this.getVariableBoolean("AttackAnim") && var1.getSwingAnim() != null && var1.CloseKillMove != null && var3.distSq < var1.getMinRange() * var1.getMinRange();
            if (var6 && (this.getSecondaryHandItem() == null || this.getSecondaryHandItem().getItemReplacementSecondHand() == null)) {
               this.setVariable("LeftHandMask", "RaiseHand");
            }
         }

         SwipeStatePlayer.instance().hitInfoPool.release((List)s_targetsStanding);
         SwipeStatePlayer.instance().hitInfoPool.release((List)s_targetsProne);
         s_targetsStanding.clear();
         s_targetsProne.clear();
      }
   }

   protected void calculateStats() {
      if (!this.bRemote) {
         super.calculateStats();
      }
   }

   protected void updateStats_Sleeping() {
      float var1 = 2.0F;
      if (allPlayersAsleep()) {
         var1 *= GameTime.instance.getDeltaMinutesPerDay();
      }

      Stats var10000 = this.stats;
      var10000.endurance = (float)((double)var10000.endurance + ZomboidGlobals.ImobileEnduranceReduce * SandboxOptions.instance.getEnduranceRegenMultiplier() * (double)this.getRecoveryMod() * (double)GameTime.instance.getMultiplier() * (double)var1);
      if (this.stats.endurance > 1.0F) {
         this.stats.endurance = 1.0F;
      }

      float var2;
      float var3;
      if (this.stats.fatigue > 0.0F) {
         var2 = 1.0F;
         if (this.Traits.Insomniac.isSet()) {
            var2 *= 0.5F;
         }

         if (this.Traits.NightOwl.isSet()) {
            var2 *= 1.4F;
         }

         var3 = 1.0F;
         if ("goodBed".equals(this.getBedType())) {
            var3 = 1.1F;
         }

         if ("badBed".equals(this.getBedType())) {
            var3 = 0.9F;
         }

         if ("floor".equals(this.getBedType())) {
            var3 = 0.6F;
         }

         float var4 = 1.0F / GameTime.instance.getMinutesPerDay() / 60.0F * GameTime.instance.getMultiplier() / 2.0F;
         this.timeOfSleep += var4;
         if (this.timeOfSleep > this.delayToActuallySleep) {
            float var5 = 1.0F;
            if (this.Traits.NeedsLessSleep.isSet()) {
               var5 *= 0.75F;
            } else if (this.Traits.NeedsMoreSleep.isSet()) {
               var5 *= 1.18F;
            }

            float var6 = 1.0F;
            if (this.stats.fatigue <= 0.3F) {
               var6 = 7.0F * var5;
               var10000 = this.stats;
               var10000.fatigue -= var4 / var6 * 0.3F * var2 * var3;
            } else {
               var6 = 5.0F * var5;
               var10000 = this.stats;
               var10000.fatigue -= var4 / var6 * 0.7F * var2 * var3;
            }
         }

         if (this.stats.fatigue < 0.0F) {
            this.stats.fatigue = 0.0F;
         }
      }

      if (this.Moodles.getMoodleLevel(MoodleType.FoodEaten) == 0) {
         var2 = this.getAppetiteMultiplier();
         var10000 = this.stats;
         var10000.hunger = (float)((double)var10000.hunger + ZomboidGlobals.HungerIncreaseWhileAsleep * SandboxOptions.instance.getStatsDecreaseMultiplier() * (double)var2 * (double)GameTime.instance.getMultiplier() * (double)GameTime.instance.getDeltaMinutesPerDay() * this.getHungerMultiplier());
      } else {
         var10000 = this.stats;
         var10000.hunger += (float)(ZomboidGlobals.HungerIncreaseWhenWellFed * SandboxOptions.instance.getStatsDecreaseMultiplier() * ZomboidGlobals.HungerIncreaseWhileAsleep * SandboxOptions.instance.getStatsDecreaseMultiplier() * (double)GameTime.instance.getMultiplier() * this.getHungerMultiplier() * (double)GameTime.instance.getDeltaMinutesPerDay());
      }

      if (this.ForceWakeUpTime == 0.0F) {
         this.ForceWakeUpTime = 9.0F;
      }

      var2 = GameTime.getInstance().getTimeOfDay();
      var3 = GameTime.getInstance().getLastTimeOfDay();
      if (var3 > var2) {
         if (var3 < this.ForceWakeUpTime) {
            var2 += 24.0F;
         } else {
            var3 -= 24.0F;
         }
      }

      boolean var7 = var2 >= this.ForceWakeUpTime && var3 < this.ForceWakeUpTime;
      if (this.getAsleepTime() > 16.0F) {
         var7 = true;
      }

      if (GameClient.bClient || numPlayers > 1) {
         var7 = var7 || this.pressedAim() || this.pressedMovement(false);
      }

      if (this.ForceWakeUp) {
         var7 = true;
      }

      if (this.Asleep && var7) {
         this.ForceWakeUp = false;
         SleepingEvent.instance.wakeUp(this);
         this.ForceWakeUpTime = -1.0F;
         if (GameClient.bClient) {
            GameClient.instance.sendPlayer(this);
         }

         this.dirtyRecalcGridStackTime = 20.0F;
      }

   }

   private void updateEndurance(float var1) {
      Stats var10000;
      float var2;
      if (this.isSitOnGround()) {
         var2 = (float)ZomboidGlobals.SittingEnduranceMultiplier;
         var2 *= 1.0F - this.stats.fatigue;
         var2 *= GameTime.instance.getMultiplier();
         var10000 = this.stats;
         var10000.endurance = (float)((double)var10000.endurance + ZomboidGlobals.ImobileEnduranceReduce * SandboxOptions.instance.getEnduranceRegenMultiplier() * (double)this.getRecoveryMod() * (double)var2);
         this.stats.endurance = PZMath.clamp(this.stats.endurance, 0.0F, 1.0F);
      } else {
         var2 = 1.0F;
         if (this.isSneaking()) {
            var2 = 1.5F;
         }

         float var5;
         float var8;
         if (!this.isRunning() && !this.isSprinting()) {
            if (this.CurrentSpeed > 0.0F && this.Moodles.getMoodleLevel(MoodleType.HeavyLoad) > 2) {
               var8 = 0.7F;
               if (this.Traits.Asthmatic.isSet()) {
                  var8 = 1.4F;
               }

               float var4 = 1.4F;
               if (this.Traits.Overweight.isSet()) {
                  var4 = 2.9F;
               }

               if (this.Traits.Athletic.isSet()) {
                  var4 = 0.8F;
               }

               var4 *= 3.0F;
               var4 *= this.getPacingMod();
               var4 *= this.getHyperthermiaMod();
               var5 = 2.8F;
               switch(this.Moodles.getMoodleLevel(MoodleType.HeavyLoad)) {
               case 2:
                  var5 = 1.5F;
                  break;
               case 3:
                  var5 = 1.9F;
                  break;
               case 4:
                  var5 = 2.3F;
               }

               var10000 = this.stats;
               var10000.endurance = (float)((double)var10000.endurance - ZomboidGlobals.RunningEnduranceReduce * (double)var4 * 0.5D * (double)var8 * (double)var2 * (double)GameTime.instance.getMultiplier() * (double)var5 / 2.0D);
            }
         } else {
            double var3 = ZomboidGlobals.RunningEnduranceReduce;
            if (this.isSprinting()) {
               var3 = ZomboidGlobals.SprintingEnduranceReduce;
            }

            var5 = 1.4F;
            if (this.Traits.Overweight.isSet()) {
               var5 = 2.9F;
            }

            if (this.Traits.Athletic.isSet()) {
               var5 = 0.8F;
            }

            var5 *= 2.3F;
            var5 *= this.getPacingMod();
            var5 *= this.getHyperthermiaMod();
            float var6 = 0.7F;
            if (this.Traits.Asthmatic.isSet()) {
               var6 = 1.4F;
            }

            if (this.Moodles.getMoodleLevel(MoodleType.HeavyLoad) == 0) {
               var10000 = this.stats;
               var10000.endurance = (float)((double)var10000.endurance - var3 * (double)var5 * 0.5D * (double)var6 * (double)GameTime.instance.getMultiplier() * (double)var2);
            } else {
               float var7 = 2.8F;
               switch(this.Moodles.getMoodleLevel(MoodleType.HeavyLoad)) {
               case 1:
                  var7 = 1.5F;
                  break;
               case 2:
                  var7 = 1.9F;
                  break;
               case 3:
                  var7 = 2.3F;
               }

               var10000 = this.stats;
               var10000.endurance = (float)((double)var10000.endurance - var3 * (double)var5 * 0.5D * (double)var6 * (double)GameTime.instance.getMultiplier() * (double)var7 * (double)var2);
            }
         }

         switch(this.Moodles.getMoodleLevel(MoodleType.Endurance)) {
         case 1:
            var1 *= 0.95F;
            break;
         case 2:
            var1 *= 0.9F;
            break;
         case 3:
            var1 *= 0.8F;
            break;
         case 4:
            var1 *= 0.6F;
         }

         if (this.stats.enduranceRecharging) {
            var1 *= 0.85F;
         }

         if (!this.isPlayerMoving()) {
            var8 = 1.0F;
            var8 *= 1.0F - this.stats.fatigue;
            var8 *= GameTime.instance.getMultiplier();
            if (this.Moodles.getMoodleLevel(MoodleType.HeavyLoad) <= 1) {
               var10000 = this.stats;
               var10000.endurance = (float)((double)var10000.endurance + ZomboidGlobals.ImobileEnduranceReduce * SandboxOptions.instance.getEnduranceRegenMultiplier() * (double)this.getRecoveryMod() * (double)var8);
            }
         }

         if (!this.isSprinting() && !this.isRunning() && this.CurrentSpeed > 0.0F) {
            var8 = 1.0F;
            var8 *= 1.0F - this.stats.fatigue;
            var8 *= GameTime.instance.getMultiplier();
            if (this.getMoodles().getMoodleLevel(MoodleType.Endurance) < 2) {
               if (this.Moodles.getMoodleLevel(MoodleType.HeavyLoad) <= 1) {
                  var10000 = this.stats;
                  var10000.endurance = (float)((double)var10000.endurance + ZomboidGlobals.ImobileEnduranceReduce / 4.0D * SandboxOptions.instance.getEnduranceRegenMultiplier() * (double)this.getRecoveryMod() * (double)var8);
               }
            } else {
               var10000 = this.stats;
               var10000.endurance = (float)((double)var10000.endurance - ZomboidGlobals.RunningEnduranceReduce / 7.0D * (double)var2);
            }
         }

      }
   }

   private boolean checkActionsBlockingMovement() {
      if (this.CharacterActions.isEmpty()) {
         return false;
      } else {
         BaseAction var1 = (BaseAction)this.CharacterActions.get(0);
         return var1.blockMovementEtc;
      }
   }

   private void updateInteractKeyPanic() {
      if (this.PlayerIndex == 0) {
         if (GameKeyboard.isKeyPressed(Core.getInstance().getKey("Interact"))) {
            this.ContextPanic += 0.6F;
         }

      }
   }

   private void updateSneakKey() {
      if (this.PlayerIndex != 0) {
         this.bSneakDebounce = false;
      } else {
         if (!this.isBlockMovement() && GameKeyboard.isKeyDown(Core.getInstance().getKey("Crouch"))) {
            if (!this.bSneakDebounce) {
               this.setSneaking(!this.isSneaking());
               this.bSneakDebounce = true;
            }
         } else {
            this.bSneakDebounce = false;
         }

      }
   }

   private void updateChangeCharacterKey() {
      if (Core.bDebug) {
         if (this.PlayerIndex == 0 && Core.bDebug && GameKeyboard.isKeyDown(22)) {
            if (!this.bChangeCharacterDebounce) {
               this.FollowCamStack.clear();
               this.bChangeCharacterDebounce = true;

               for(int var2 = 0; var2 < this.getCell().getObjectList().size(); ++var2) {
                  IsoMovingObject var1 = (IsoMovingObject)this.getCell().getObjectList().get(var2);
                  if (var1 instanceof IsoSurvivor) {
                     this.FollowCamStack.add((IsoSurvivor)var1);
                  }
               }

               if (!this.FollowCamStack.isEmpty()) {
                  if (this.followID >= this.FollowCamStack.size()) {
                     this.followID = 0;
                  }

                  IsoCamera.SetCharacterToFollow((IsoGameCharacter)this.FollowCamStack.get(this.followID));
                  ++this.followID;
               }

            }
         } else {
            this.bChangeCharacterDebounce = false;
         }
      }
   }

   private void updateEnableModelsKey() {
      if (Core.bDebug) {
         if (this.PlayerIndex == 0 && GameKeyboard.isKeyPressed(Core.getInstance().getKey("ToggleModelsEnabled"))) {
            ModelManager.instance.bDebugEnableModels = !ModelManager.instance.bDebugEnableModels;
         }

      }
   }

   private void updateDeathDragDown() {
      if (!this.isDead()) {
         if (this.isDeathDragDown()) {
            if (this.isGodMod()) {
               this.setDeathDragDown(false);
            } else if (!"EndDeath".equals(this.getHitReaction())) {
               for(int var1 = -1; var1 <= 1; ++var1) {
                  for(int var2 = -1; var2 <= 1; ++var2) {
                     IsoGridSquare var3 = this.getCell().getGridSquare((int)this.x + var2, (int)this.y + var1, (int)this.z);
                     if (var3 != null) {
                        for(int var4 = 0; var4 < var3.getMovingObjects().size(); ++var4) {
                           IsoMovingObject var5 = (IsoMovingObject)var3.getMovingObjects().get(var4);
                           IsoZombie var6 = (IsoZombie)Type.tryCastTo(var5, IsoZombie.class);
                           if (var6 != null && var6.isAlive() && !var6.isOnFloor()) {
                              this.setAttackingZombie(var6);
                              this.setHitReaction("EndDeath");
                              this.setBlockMovement(true);
                              return;
                           }
                        }
                     }
                  }
               }

               this.setDeathDragDown(false);
            }
         }
      }
   }

   private void updateGodModeKey() {
      if (Core.bDebug) {
         if (GameKeyboard.isKeyPressed(Core.getInstance().getKey("ToggleGodModeInvisible"))) {
            IsoPlayer var1 = null;

            for(int var2 = 0; var2 < numPlayers; ++var2) {
               if (players[var2] != null && !players[var2].isDead()) {
                  var1 = players[var2];
                  break;
               }
            }

            if (this == var1) {
               boolean var4 = !var1.isGodMod();
               DebugLog.General.println("Toggle GodMode: %s", var4 ? "ON" : "OFF");
               var1.setGhostMode(var4);
               var1.setGodMod(var4);

               for(int var3 = 0; var3 < numPlayers; ++var3) {
                  if (players[var3] != null && players[var3] != var1) {
                     players[var3].m_ghostMode = var4;
                     players[var3].setGodMod(var4);
                  }
               }
            }

         }
      }
   }

   private void checkReloading() {
      HandWeapon var1 = (HandWeapon)Type.tryCastTo(this.getPrimaryHandItem(), HandWeapon.class);
      if (var1 != null && var1.isReloadable(this)) {
         boolean var2 = false;
         boolean var3 = false;
         boolean var4;
         if (this.JoypadBind != -1 && this.bJoypadMovementActive) {
            var4 = JoypadManager.instance.isRBPressed(this.JoypadBind);
            if (var4) {
               var2 = !this.bReloadButtonDown;
            }

            this.bReloadButtonDown = var4;
            var4 = JoypadManager.instance.isLBPressed(this.JoypadBind);
            if (var4) {
               var3 = !this.bRackButtonDown;
            }

            this.bRackButtonDown = var4;
         }

         if (this.PlayerIndex == 0) {
            var4 = GameKeyboard.isKeyDown(Core.getInstance().getKey("ReloadWeapon"));
            if (var4) {
               var2 = !this.bReloadKeyDown;
            }

            this.bReloadKeyDown = var4;
            var4 = GameKeyboard.isKeyDown(Core.getInstance().getKey("Rack Firearm"));
            if (var4) {
               var3 = !this.bRackKeyDown;
            }

            this.bRackKeyDown = var4;
         }

         if (var2) {
            this.setVariable("WeaponReloadType", var1.getWeaponReloadType());
            LuaEventManager.triggerEvent("OnPressReloadButton", this, var1);
         } else if (var3) {
            this.setVariable("WeaponReloadType", var1.getWeaponReloadType());
            LuaEventManager.triggerEvent("OnPressRackButton", this, var1);
         }

      }
   }

   public void postupdate() {
      IsoPlayer.s_performance.postUpdate.invokeAndMeasure(this, IsoPlayer::postupdateInternal);
   }

   private void postupdateInternal() {
      super.postupdate();
      this.highlightRangedTargets();
      if (this.isNPC) {
         GameTime var1 = GameTime.getInstance();
         float var2 = 1.0F / var1.getMinutesPerDay() / 60.0F * var1.getMultiplier() / 2.0F;
         if (Core.bLastStand) {
            var2 = 1.0F / var1.getMinutesPerDay() / 60.0F * var1.getUnmoddedMultiplier() / 2.0F;
         }

         this.setHoursSurvived(this.getHoursSurvived() + (double)var2);
      }

   }

   private void highlightRangedTargets() {
      if (this.isLocalPlayer() && !this.isNPC) {
         if (this.isAiming()) {
            if (Core.getInstance().getOptionAimOutline() != 1) {
               IsoPlayer.s_performance.highlightRangedTargets.invokeAndMeasure(this, IsoPlayer::highlightRangedTargetsInternal);
            }
         }
      }
   }

   private void highlightRangedTargetsInternal() {
      HandWeapon var1 = (HandWeapon)Type.tryCastTo(this.getPrimaryHandItem(), HandWeapon.class);
      if (var1 == null || var1.getSwingAnim() == null || var1.getCondition() <= 0) {
         var1 = this.bareHands;
      }

      if (Core.getInstance().getOptionAimOutline() != 2 || var1.isRanged()) {
         boolean var2 = this.bDoShove;
         this.bDoShove = false;
         SwipeStatePlayer.AttackVars var3 = SwipeStatePlayer.instance().attackVars;
         SwipeStatePlayer.instance().CalcAttackVars(this, var3);
         SwipeStatePlayer.instance().CalcHitList(this, false, var3);

         for(int var4 = 0; var4 < SwipeStatePlayer.HitList.size(); ++var4) {
            SwipeStatePlayer.HitInfo var5 = (SwipeStatePlayer.HitInfo)SwipeStatePlayer.HitList.get(var4);
            IsoMovingObject var6 = var5.object;
            if (var6 instanceof IsoZombie) {
               float var7 = 1.0F - (float)var5.chance / 100.0F;
               float var8 = (float)var5.chance / 100.0F;
               float var9 = 0.4F;
               if ((double)var8 < 0.7D) {
                  var9 = 0.36F;
               }

               var6.bOutline[this.PlayerIndex] = true;
               if (var6.outlineColor[this.PlayerIndex] == null) {
                  var6.outlineColor[this.PlayerIndex] = new ColorInfo();
               }

               var6.outlineColor[this.PlayerIndex].set(var7 * 0.75F, var8 * var9, 0.0F, 1.0F);
            }

            if (var5.window != null) {
               var5.window.setHighlightColor(0.8F, 0.1F, 0.1F, 0.5F);
               var5.window.setHighlighted(true);
            }
         }

         this.bDoShove = var2;
      }
   }

   public boolean isSolidForSeparate() {
      return this.isGhostMode() ? false : super.isSolidForSeparate();
   }

   public boolean isPushableForSeparate() {
      if (this.isCurrentState(PlayerHitReactionState.instance())) {
         return false;
      } else {
         return this.isCurrentState(SwipeStatePlayer.instance()) ? false : super.isPushableForSeparate();
      }
   }

   public boolean isPushedByForSeparate(IsoMovingObject var1) {
      return !this.isPlayerMoving() && var1.isZombie() && ((IsoZombie)var1).isAttacking() ? false : super.isPushedByForSeparate(var1);
   }

   private void updateExt() {
      if (!this.isSneaking()) {
         this.extUpdateCount += GameTime.getInstance().getMultiplier() / 0.8F;
         if (!this.getAdvancedAnimator().containsAnyIdleNodes() && !this.isSitOnGround()) {
            this.extUpdateCount = 0.0F;
         }

         if (!(this.extUpdateCount <= 5000.0F)) {
            this.extUpdateCount = 0.0F;
            if (this.stats.NumVisibleZombies == 0 && this.stats.NumChasingZombies == 0) {
               if (Rand.NextBool(3)) {
                  if (this.getAdvancedAnimator().containsAnyIdleNodes() || this.isSitOnGround()) {
                     this.onIdlePerformFidgets();
                     this.reportEvent("EventDoExt");
                  }
               }
            }
         }
      }
   }

   private void onIdlePerformFidgets() {
      Moodles var6 = this.getMoodles();
      BodyDamage var7 = this.getBodyDamage();
      if (var6.getMoodleLevel(MoodleType.Hypothermia) > 0 && Rand.NextBool(7)) {
         this.setVariable("Ext", "Shiver");
      } else if (var6.getMoodleLevel(MoodleType.Hyperthermia) > 0 && Rand.NextBool(7)) {
         this.setVariable("Ext", "WipeBrow");
      } else if (var6.getMoodleLevel(MoodleType.Sick) > 0 && Rand.NextBool(7)) {
         if (Rand.NextBool(4)) {
            this.setVariable("Ext", "Cough");
         } else {
            this.setVariable("Ext", "PainStomach" + (Rand.Next(2) + 1));
         }

      } else if (var6.getMoodleLevel(MoodleType.Endurance) > 2 && Rand.NextBool(10)) {
         if (Rand.NextBool(5) && !this.isSitOnGround()) {
            this.setVariable("Ext", "BentDouble");
         } else {
            this.setVariable("Ext", "WipeBrow");
         }

      } else if (var6.getMoodleLevel(MoodleType.Tired) > 2 && Rand.NextBool(10)) {
         if (Rand.NextBool(7)) {
            this.setVariable("Ext", "TiredStretch");
         } else if (Rand.NextBool(7)) {
            this.setVariable("Ext", "Sway");
         } else {
            this.setVariable("Ext", "Yawn");
         }

      } else if (var7.doBodyPartsHaveInjuries(BodyPartType.Head, BodyPartType.Neck) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.Head, BodyPartType.Neck) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeHead");
         } else {
            this.setVariable("Ext", "PainHead" + (Rand.Next(2) + 1));
         }

      } else if (var7.doBodyPartsHaveInjuries(BodyPartType.UpperArm_L, BodyPartType.ForeArm_L) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.UpperArm_L, BodyPartType.ForeArm_L) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeArmL");
         } else {
            this.setVariable("Ext", "PainArmL");
         }

      } else if (var7.doBodyPartsHaveInjuries(BodyPartType.UpperArm_R, BodyPartType.ForeArm_R) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.UpperArm_R, BodyPartType.ForeArm_R) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeArmR");
         } else {
            this.setVariable("Ext", "PainArmR");
         }

      } else if (var7.doesBodyPartHaveInjury(BodyPartType.Hand_L) && Rand.NextBool(7)) {
         this.setVariable("Ext", "PainHandL");
      } else if (var7.doesBodyPartHaveInjury(BodyPartType.Hand_R) && Rand.NextBool(7)) {
         this.setVariable("Ext", "PainHandR");
      } else if (!this.isSitOnGround() && var7.doBodyPartsHaveInjuries(BodyPartType.UpperLeg_L, BodyPartType.LowerLeg_L) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.UpperLeg_L, BodyPartType.LowerLeg_L) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeLegL");
         } else {
            this.setVariable("Ext", "PainLegL");
         }

      } else if (!this.isSitOnGround() && var7.doBodyPartsHaveInjuries(BodyPartType.UpperLeg_R, BodyPartType.LowerLeg_R) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.UpperLeg_R, BodyPartType.LowerLeg_R) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeLegR");
         } else {
            this.setVariable("Ext", "PainLegR");
         }

      } else if (var7.doBodyPartsHaveInjuries(BodyPartType.Torso_Upper, BodyPartType.Torso_Lower) && Rand.NextBool(7)) {
         if (var7.areBodyPartsBleeding(BodyPartType.Torso_Upper, BodyPartType.Torso_Lower) && Rand.NextBool(2)) {
            this.setVariable("Ext", "WipeTorso" + (Rand.Next(2) + 1));
         } else {
            this.setVariable("Ext", "PainTorso");
         }

      } else if (WeaponType.getWeaponType((IsoGameCharacter)this) != WeaponType.barehand) {
         this.setVariable("Ext", Rand.Next(5) + 1 + "");
      } else if (Rand.NextBool(10)) {
         this.setVariable("Ext", "ChewNails");
      } else if (Rand.NextBool(10)) {
         this.setVariable("Ext", "ShiftWeight");
      } else if (Rand.NextBool(10)) {
         this.setVariable("Ext", "PullAtColar");
      } else if (Rand.NextBool(10)) {
         this.setVariable("Ext", "BridgeNose");
      } else {
         this.setVariable("Ext", Rand.Next(5) + 1 + "");
      }
   }

   private boolean updateUseKey() {
      if (GameServer.bServer) {
         return false;
      } else if (!this.isLocalPlayer()) {
         return false;
      } else if (this.PlayerIndex != 0) {
         return false;
      } else {
         this.timePressedContext += GameTime.instance.getRealworldSecondsSinceLastUpdate();
         boolean var1 = GameKeyboard.isKeyDown(Core.getInstance().getKey("Interact"));
         if (var1 && this.timePressedContext < 0.5F) {
            this.bPressContext = true;
         } else {
            if (this.bPressContext && (Core.CurrentTextEntryBox != null && Core.CurrentTextEntryBox.DoingTextEntry || !GameKeyboard.doLuaKeyPressed)) {
               this.bPressContext = false;
            }

            if (this.bPressContext && this.doContext(this.dir, false)) {
               this.timePressedContext = 0.0F;
               this.bPressContext = false;
               return true;
            }

            if (!var1) {
               this.bPressContext = false;
               this.timePressedContext = 0.0F;
            }
         }

         return false;
      }
   }

   private void updateHitByVehicle() {
      if (!GameServer.bServer) {
         if (this.isLocalPlayer()) {
            if (this.vehicle4testCollision != null && this.ULbeatenVehicle.Check() && SandboxOptions.instance.DamageToPlayerFromHitByACar.getValue() > 1) {
               BaseVehicle var1 = this.vehicle4testCollision;
               this.vehicle4testCollision = null;
               if (var1.isEngineRunning() && this.getVehicle() != var1) {
                  float var2 = var1.jniLinearVelocity.x;
                  float var3 = var1.jniLinearVelocity.z;
                  if (GameClient.bClient && this.isLocalPlayer()) {
                     var2 = var1.netLinearVelocity.x;
                     var3 = var1.netLinearVelocity.z;
                  }

                  float var4 = (float)Math.sqrt((double)(var2 * var2 + var3 * var3));
                  Vector2 var5 = (Vector2)((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).alloc();
                  Vector2 var6 = var1.testCollisionWithCharacter(this, 0.20000002F, var5);
                  if (var6 != null && var6.x != -1.0F) {
                     var6.x = (var6.x - var1.x) * var4 * 1.0F + this.x;
                     var6.y = (var6.y - var1.y) * var4 * 1.0F + this.x;
                     if (this.isOnFloor()) {
                        int var7 = var1.testCollisionWithProneCharacter(this, false);
                        if (var7 > 0) {
                           this.doBeatenVehicle(Math.max(var4 * 6.0F, 5.0F), var6.x, var6.y, false);
                        }

                        this.doBeatenVehicle(0.0F, var6.x, var6.y, false);
                     } else if (this.getCurrentState() != PlayerFallDownState.instance() && var4 > 0.1F) {
                        this.doBeatenVehicle(Math.max(var4 * 2.0F, 5.0F), var6.x, var6.y, false);
                     }
                  }

                  ((BaseVehicle.Vector2ObjectPool)BaseVehicle.TL_vector2_pool.get()).release(var5);
               }
            }
         }
      }
   }

   private void updateSoundListener() {
      if (!GameServer.bServer) {
         if (this.isLocalPlayer()) {
            if (this.soundListener == null) {
               this.soundListener = (BaseSoundListener)(Core.SoundDisabled ? new DummySoundListener(this.PlayerIndex) : new SoundListener(this.PlayerIndex));
            }

            this.soundListener.setPos(this.x, this.y, this.z);
            this.checkNearbyRooms -= GameTime.getInstance().getMultiplier() / 1.6F;
            if (this.checkNearbyRooms <= 0.0F) {
               this.checkNearbyRooms = 30.0F;
               this.numNearbyBuildingsRooms = (float)IsoWorld.instance.MetaGrid.countNearbyBuildingsRooms(this);
            }

            if (this.testemitter == null) {
               this.testemitter = (BaseSoundEmitter)(Core.SoundDisabled ? new DummySoundEmitter() : new FMODSoundEmitter());
               this.testemitter.setPos(this.x, this.y, this.z);
            }

            this.soundListener.tick();
            this.testemitter.tick();
         }
      }
   }

   protected void updateMovementRates() {
      this.calculateWalkSpeed();
      this.m_idleSpeed = this.calculateIdleSpeed();
      this.updateFootInjuries();
   }

   public void pressedAttack() {
      this.setSprinting(false);
      this.setForceSprint(false);
      if (!this.attackStarted && !this.isCurrentState(PlayerHitReactionState.instance())) {
         if (this.primaryHandModel != null && !StringUtils.isNullOrEmpty(this.primaryHandModel.maskVariableValue) && this.secondaryHandModel != null && !StringUtils.isNullOrEmpty(this.secondaryHandModel.maskVariableValue)) {
            this.bDoShove = false;
            this.setForceShove(false);
            this.initiateAttack = false;
            this.attackStarted = false;
            this.setAttackType((String)null);
         } else if (this.getPrimaryHandItem() != null && this.getPrimaryHandItem().getItemReplacementPrimaryHand() != null && this.getSecondaryHandItem() != null && this.getSecondaryHandItem().getItemReplacementSecondHand() != null) {
            this.bDoShove = false;
            this.setForceShove(false);
            this.initiateAttack = false;
            this.attackStarted = false;
            this.setAttackType((String)null);
         } else {
            this.initiateAttack = true;
            this.attackStarted = true;
            this.isCrit = false;
            this.attackFromBehind = false;
            WeaponType var1 = WeaponType.getWeaponType((IsoGameCharacter)this);
            this.setAttackType((String)PZArrayUtil.pickRandom(var1.possibleAttack));
            this.combatSpeed = this.calculateCombatSpeed();
            SwipeStatePlayer.AttackVars var2 = SwipeStatePlayer.instance().attackVars;
            SwipeStatePlayer.instance().CalcAttackVars(this, var2);
            if (var2.bDoShove && !this.isAuthorizeShoveStomp()) {
               this.bDoShove = false;
               this.setForceShove(false);
               this.initiateAttack = false;
               this.attackStarted = false;
               this.setAttackType((String)null);
            } else {
               this.useHandWeapon = var2.weapon;
               this.setAimAtFloor(var2.bAimAtFloor);
               this.bDoShove = var2.bDoShove;
               this.targetOnGround = var2.targetOnGround;
               if (var2.weapon != null && !StringUtils.isNullOrEmpty(var2.weapon.getFireMode())) {
                  this.setVariable("FireMode", var2.weapon.getFireMode());
               } else {
                  this.clearVariable("FireMode");
               }

               int var3;
               if (this.useHandWeapon != null && var1.isRanged && !this.bDoShove) {
                  var3 = this.useHandWeapon.getRecoilDelay();
                  Float var4 = (float)var3 * (1.0F - (float)this.getPerkLevel(PerkFactory.Perks.Aiming) / 30.0F);
                  this.setRecoilDelay((float)var4.intValue());
               }

               var3 = Rand.Next(0, 3);
               if (var3 == 0) {
                  this.setVariable("AttackVariationX", Rand.Next(-1.0F, -0.5F));
               }

               if (var3 == 1) {
                  this.setVariable("AttackVariationX", 0.0F);
               }

               if (var3 == 2) {
                  this.setVariable("AttackVariationX", Rand.Next(0.5F, 1.0F));
               }

               this.setVariable("AttackVariationY", 0.0F);
               ArrayList var9 = SwipeStatePlayer.HitList;
               SwipeStatePlayer.instance().CalcHitList(this, true, var2);
               IsoZombie var5 = null;
               if (!var9.isEmpty()) {
                  var5 = (IsoZombie)Type.tryCastTo(((SwipeStatePlayer.HitInfo)var9.get(0)).object, IsoZombie.class);
               }

               if (var5 == null) {
                  if (this.isAiming() && !this.m_meleePressed && this.useHandWeapon != this.bareHands) {
                     this.bDoShove = false;
                     this.setForceShove(false);
                  }

                  this.m_lastAttackWasShove = this.bDoShove;
                  if (var1.canMiss && !this.isAimAtFloor()) {
                     this.setAttackType("miss");
                  }

                  if (this.isAiming() && this.bDoShove) {
                     this.setVariable("bShoveAiming", true);
                  } else {
                     this.clearVariable("bShoveAiming");
                  }

               } else {
                  this.attackFromBehind = this.isBehind(var5);
                  float var6 = IsoUtils.DistanceTo(var5.x, var5.y, this.x, this.y);
                  this.setVariable("TargetDist", var6);
                  IsoZombie var7 = this.getClosestZombieToOtherZombie(var5);
                  var5.setHitFromBehind(this.attackFromBehind);
                  int var8 = this.calculateCritChance(var5);
                  if (this.attackFromBehind) {
                     if (var5.target == null) {
                        var8 += 30;
                     } else {
                        var8 += 5;
                     }
                  }

                  if (!var2.bAimAtFloor && (double)var6 > 1.25D && var1 == WeaponType.spear && (double)IsoUtils.DistanceTo(var5.x, var5.y, var7.x, var7.y) > 1.7D) {
                     this.setAttackType("overhead");
                     var8 += 30;
                  }

                  this.isCrit = Rand.Next(100) < var8;
                  if (this.attackFromBehind && var2.bCloseKill && var5.target == null) {
                     this.isCrit = true;
                  }

                  if (this.isCrit && !var2.bCloseKill && !this.bDoShove && var1 == WeaponType.knife) {
                     this.isCrit = false;
                  }

                  this.setAttackWasSuperAttack(false);
                  if (this.stats.NumChasingZombies > 1 && var2.bCloseKill && !this.bDoShove && var1 == WeaponType.knife) {
                     this.isCrit = false;
                  }

                  if (this.isCrit) {
                     this.combatSpeed *= 1.1F;
                  }

                  if (DebugLog.isEnabled(DebugType.Combat)) {
                     DebugLog.Combat.debugln("Hit zombie dist: " + var6 + " crit: " + this.isCrit + " (" + var8 + "%) from behind: " + this.attackFromBehind);
                  }

                  if (this.isAiming() && this.bDoShove) {
                     this.setVariable("bShoveAiming", true);
                  } else {
                     this.clearVariable("bShoveAiming");
                  }

                  if (this.useHandWeapon != null && var1.isRanged) {
                     this.setRecoilDelay((float)(this.useHandWeapon.getRecoilDelay() - this.getPerkLevel(PerkFactory.Perks.Aiming) * 2));
                  }

                  this.m_lastAttackWasShove = this.bDoShove;
               }
            }
         }
      }
   }

   public int calculateCritChance(IsoGameCharacter var1) {
      if (this.bDoShove) {
         byte var6 = 35;
         int var7 = var6 - this.getMoodles().getMoodleLevel(MoodleType.Endurance) * 5;
         var7 -= this.getMoodles().getMoodleLevel(MoodleType.HeavyLoad) * 5;
         var7 = (int)((double)var7 - (double)this.getMoodles().getMoodleLevel(MoodleType.Panic) * 1.3D);
         var7 += this.getPerkLevel(PerkFactory.Perks.Strength) * 2;
         return var7;
      } else if (this.bDoShove && var1.getStateMachine().getCurrent() == StaggerBackState.instance()) {
         return 100;
      } else if (this.getPrimaryHandItem() != null && this.getPrimaryHandItem() instanceof HandWeapon) {
         HandWeapon var2 = (HandWeapon)this.getPrimaryHandItem();
         int var3 = (int)var2.getCriticalChance();
         if (var2.isAlwaysKnockdown()) {
            return 100;
         } else {
            WeaponType var4 = WeaponType.getWeaponType((IsoGameCharacter)this);
            if (var4.isRanged) {
               var3 = (int)((float)var3 + (float)var2.getAimingPerkCritModifier() * ((float)this.getPerkLevel(PerkFactory.Perks.Aiming) / 2.0F));
               if (this.getBeenMovingFor() > (float)(var2.getAimingTime() + this.getPerkLevel(PerkFactory.Perks.Aiming) * 2)) {
                  var3 = (int)((float)var3 - (this.getBeenMovingFor() - (float)(var2.getAimingTime() + this.getPerkLevel(PerkFactory.Perks.Aiming) * 2)));
               }

               var3 += this.getPerkLevel(PerkFactory.Perks.Aiming) * 3;
               if (this.DistTo(var1) < 4.0F) {
                  var3 = (int)((float)var3 + (3.0F - this.DistTo(var1)) * 7.0F);
               } else if (this.DistTo(var1) >= 4.0F) {
                  var3 = (int)((float)var3 - (4.0F - this.DistTo(var1)) * 7.0F);
               }
            } else {
               if (var2.isTwoHandWeapon() && (this.getPrimaryHandItem() != var2 || this.getSecondaryHandItem() != var2)) {
                  var3 -= var3 / 3;
               }

               if (this.chargeTime < 2.0F) {
                  var3 -= var3 / 5;
               }

               int var5 = this.getPerkLevel(PerkFactory.Perks.Blunt);
               if (var2.getCategories().contains("Axe")) {
                  var5 = this.getPerkLevel(PerkFactory.Perks.Axe);
               }

               if (var2.getCategories().contains("LongBlade")) {
                  var5 = this.getPerkLevel(PerkFactory.Perks.LongBlade);
               }

               if (var2.getCategories().contains("Spear")) {
                  var5 = this.getPerkLevel(PerkFactory.Perks.Spear);
               }

               if (var2.getCategories().contains("SmallBlade")) {
                  var5 = this.getPerkLevel(PerkFactory.Perks.SmallBlade);
               }

               if (var2.getCategories().contains("SmallBlunt")) {
                  var5 = this.getPerkLevel(PerkFactory.Perks.SmallBlunt);
               }

               var3 += var5 * 3;
            }

            var3 -= this.getMoodles().getMoodleLevel(MoodleType.Endurance) * 5;
            var3 -= this.getMoodles().getMoodleLevel(MoodleType.HeavyLoad) * 5;
            var3 = (int)((double)var3 - (double)this.getMoodles().getMoodleLevel(MoodleType.Panic) * 1.3D);
            if (SandboxOptions.instance.Lore.Toughness.getValue() == 1) {
               var3 -= 6;
            }

            if (SandboxOptions.instance.Lore.Toughness.getValue() == 3) {
               var3 += 6;
            }

            if (var3 < 10) {
               var3 = 10;
            }

            if (var3 > 90) {
               var3 = 90;
            }

            return var3;
         }
      } else {
         return 0;
      }
   }

   private void checkJoypadIgnoreAimUntilCentered() {
      if (this.bJoypadIgnoreAimUntilCentered) {
         if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1 && this.bJoypadMovementActive) {
            float var1 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
            float var2 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
            if (var1 * var1 + var2 + var2 <= 0.0F) {
               this.bJoypadIgnoreAimUntilCentered = false;
            }
         }

      }
   }

   public boolean isAimControlActive() {
      if (this.isForceAim()) {
         return true;
      } else if (this.isAimKeyDown()) {
         return true;
      } else {
         return GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1 && this.bJoypadMovementActive && this.getJoypadAimVector(tempo).getLengthSquared() > 0.0F;
      }
   }

   private Vector2 getJoypadAimVector(Vector2 var1) {
      if (this.bJoypadIgnoreAimUntilCentered) {
         return var1.set(0.0F, 0.0F);
      } else {
         float var2 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
         float var3 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
         float var4 = JoypadManager.instance.getDeadZone(this.JoypadBind, 0);
         if (var3 * var3 + var2 * var2 < var4 * var4) {
            var2 = 0.0F;
            var3 = 0.0F;
         }

         return var1.set(var3, var2);
      }
   }

   private Vector2 getJoypadMoveVector(Vector2 var1) {
      float var2 = JoypadManager.instance.getMovementAxisY(this.JoypadBind);
      float var3 = JoypadManager.instance.getMovementAxisX(this.JoypadBind);
      float var4 = JoypadManager.instance.getDeadZone(this.JoypadBind, 0);
      if (var3 * var3 + var2 * var2 < var4 * var4) {
         var2 = 0.0F;
         var3 = 0.0F;
      }

      var1.set(var3, var2);
      if (this.isIgnoreInputsForDirection()) {
         var1.set(0.0F, 0.0F);
      }

      return var1;
   }

   private void updateToggleToAim() {
      if (this.PlayerIndex == 0) {
         if (!Core.getInstance().isToggleToAim()) {
            this.setForceAim(false);
         } else {
            boolean var1 = this.isAimKeyDown();
            long var2 = System.currentTimeMillis();
            if (var1) {
               if (this.aimKeyDownMS == 0L) {
                  this.aimKeyDownMS = var2;
               }
            } else {
               if (this.aimKeyDownMS != 0L && var2 - this.aimKeyDownMS < 500L) {
                  this.toggleForceAim();
               } else if (this.isForceAim()) {
                  if (this.aimKeyDownMS != 0L) {
                     this.toggleForceAim();
                  } else {
                     int var4 = Core.getInstance().getKey("Aim");
                     boolean var5 = var4 == 29 || var4 == 157;
                     if (var5 && UIManager.isMouseOverInventory()) {
                        this.toggleForceAim();
                     }
                  }
               }

               this.aimKeyDownMS = 0L;
            }

         }
      }
   }

   private void UpdateInputState(IsoPlayer.InputState var1) {
      var1.bMelee = false;
      if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
         if (this.bJoypadMovementActive) {
            var1.isAttacking = this.isCharging;
            if (this.bJoypadIgnoreChargingRT) {
               var1.isAttacking = false;
            }

            if (this.bJoypadIgnoreAimUntilCentered) {
               float var2 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
               float var3 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
               if (var2 == 0.0F && var3 == 0.0F) {
                  this.bJoypadIgnoreAimUntilCentered = false;
               }
            }
         }

         if (this.isChargingLT) {
            var1.bMelee = true;
            var1.isAttacking = false;
         }
      } else {
         var1.isAttacking = this.isCharging && Mouse.isButtonDownUICheck(0);
         if (GameKeyboard.isKeyDown(Core.getInstance().getKey("Melee")) && this.authorizeMeleeAction) {
            var1.bMelee = true;
            var1.isAttacking = false;
         }
      }

      boolean var4;
      if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
         if (this.bJoypadMovementActive) {
            var1.isCharging = JoypadManager.instance.isRTPressed(this.JoypadBind);
            var1.isChargingLT = JoypadManager.instance.isLTPressed(this.JoypadBind);
            if (this.bJoypadIgnoreChargingRT && !var1.isCharging) {
               this.bJoypadIgnoreChargingRT = false;
            }
         }

         var1.isAiming = false;
         var1.bRunning = false;
         var1.bSprinting = false;
         Vector2 var9 = this.getJoypadAimVector(tempVector2);
         if (var9.x == 0.0F && var9.y == 0.0F) {
            var1.isCharging = false;
            Vector2 var11 = this.getJoypadMoveVector(tempVector2);
            if (var11.x != 0.0F || var11.y != 0.0F) {
               if (this.isAllowRun()) {
                  var1.bRunning = JoypadManager.instance.isRTPressed(this.JoypadBind);
               }

               var1.isAttacking = false;
               var1.bMelee = false;
               this.bJoypadIgnoreChargingRT = true;
               var1.isCharging = false;
               var4 = JoypadManager.instance.isBPressed(this.JoypadBind);
               if (var1.bRunning && var4 && !this.bJoypadBDown) {
                  this.bJoypadSprint = !this.bJoypadSprint;
               }

               this.bJoypadBDown = var4;
               var1.bSprinting = this.bJoypadSprint;
            }
         } else {
            var1.isAiming = true;
         }

         if (!var1.bRunning) {
            this.bJoypadBDown = false;
            this.bJoypadSprint = false;
         }
      } else {
         var1.isAiming = (this.isAimKeyDown() || Mouse.isButtonDownUICheck(1) && this.TimeRightPressed >= 0.15F) && this.getPlayerNum() == 0 && StringUtils.isNullOrEmpty(this.getVariableString("BumpFallType"));
         if (Mouse.isButtonDown(1)) {
            this.TimeRightPressed += GameTime.getInstance().getRealworldSecondsSinceLastUpdate();
         } else {
            this.TimeRightPressed = 0.0F;
         }

         if (!this.isCharging) {
            var1.isCharging = Mouse.isButtonDownUICheck(1) && this.TimeRightPressed >= 0.15F || this.isAimKeyDown();
         } else {
            var1.isCharging = Mouse.isButtonDown(1) || this.isAimKeyDown();
         }

         int var8 = Core.getInstance().getKey("Run");
         int var10 = Core.getInstance().getKey("Sprint");
         if (this.isAllowRun()) {
            var1.bRunning = GameKeyboard.isKeyDown(var8);
         }

         if (this.isAllowSprint()) {
            if (!Core.OptiondblTapJogToSprint) {
               if (GameKeyboard.isKeyDown(var10)) {
                  var1.bSprinting = true;
                  this.pressedRunTimer = 1.0F;
               } else {
                  var1.bSprinting = false;
               }
            } else {
               if (!GameKeyboard.wasKeyDown(var8) && GameKeyboard.isKeyDown(var8) && this.pressedRunTimer < 30.0F && this.pressedRun) {
                  var1.bSprinting = true;
               }

               if (GameKeyboard.wasKeyDown(var8) && !GameKeyboard.isKeyDown(var8)) {
                  var1.bSprinting = false;
                  this.pressedRun = true;
               }

               if (!var1.bRunning) {
                  var1.bSprinting = false;
               }

               if (this.pressedRun) {
                  ++this.pressedRunTimer;
               }

               if (this.pressedRunTimer > 30.0F) {
                  this.pressedRunTimer = 0.0F;
                  this.pressedRun = false;
               }
            }
         }

         this.updateToggleToAim();
         if (var1.bRunning || var1.bSprinting) {
            this.setForceAim(false);
         }

         boolean var5;
         long var6;
         if (this.PlayerIndex == 0 && Core.getInstance().isToggleToRun()) {
            var4 = GameKeyboard.isKeyDown(var8);
            var5 = GameKeyboard.wasKeyDown(var8);
            var6 = System.currentTimeMillis();
            if (var4 && !var5) {
               this.runKeyDownMS = var6;
            } else if (!var4 && var5 && var6 - this.runKeyDownMS < 500L) {
               this.toggleForceRun();
            }
         }

         if (this.PlayerIndex == 0 && Core.getInstance().isToggleToSprint()) {
            var4 = GameKeyboard.isKeyDown(var10);
            var5 = GameKeyboard.wasKeyDown(var10);
            var6 = System.currentTimeMillis();
            if (var4 && !var5) {
               this.sprintKeyDownMS = var6;
            } else if (!var4 && var5 && var6 - this.sprintKeyDownMS < 500L) {
               this.toggleForceSprint();
            }
         }

         if (this.isForceAim()) {
            var1.isAiming = true;
            var1.isCharging = true;
         }

         if (this.isForceRun()) {
            var1.bRunning = true;
         }

         if (this.isForceSprint()) {
            var1.bSprinting = true;
         }
      }

   }

   public IsoZombie getClosestZombieToOtherZombie(IsoZombie var1) {
      IsoZombie var2 = null;
      ArrayList var3 = new ArrayList();
      ArrayList var4 = IsoWorld.instance.CurrentCell.getObjectList();

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         IsoMovingObject var6 = (IsoMovingObject)var4.get(var5);
         if (var6 != var1 && var6 instanceof IsoZombie) {
            var3.add((IsoZombie)var6);
         }
      }

      float var9 = 0.0F;

      for(int var10 = 0; var10 < var3.size(); ++var10) {
         IsoZombie var7 = (IsoZombie)var3.get(var10);
         float var8 = IsoUtils.DistanceTo(var7.x, var7.y, var1.x, var1.y);
         if (var2 == null || var8 < var9) {
            var2 = var7;
            var9 = var8;
         }
      }

      return var2;
   }

   /** @deprecated */
   @Deprecated
   public IsoGameCharacter getClosestZombieDist() {
      float var1 = 0.4F;
      boolean var2 = false;
      testHitPosition.x = this.x + this.getForwardDirection().x * var1;
      testHitPosition.y = this.y + this.getForwardDirection().y * var1;
      HandWeapon var3 = this.getWeapon();
      ArrayList var4 = new ArrayList();

      for(int var5 = (int)testHitPosition.x - (int)var3.getMaxRange(); var5 <= (int)testHitPosition.x + (int)var3.getMaxRange(); ++var5) {
         for(int var6 = (int)testHitPosition.y - (int)var3.getMaxRange(); var6 <= (int)testHitPosition.y + (int)var3.getMaxRange(); ++var6) {
            IsoGridSquare var7 = IsoWorld.instance.CurrentCell.getGridSquare((double)var5, (double)var6, (double)this.z);
            if (var7 != null && var7.getMovingObjects().size() > 0) {
               for(int var8 = 0; var8 < var7.getMovingObjects().size(); ++var8) {
                  IsoMovingObject var9 = (IsoMovingObject)var7.getMovingObjects().get(var8);
                  if (var9 instanceof IsoZombie) {
                     Vector2 var10 = tempVector2_1.set(this.getX(), this.getY());
                     Vector2 var11 = tempVector2_2.set(var9.getX(), var9.getY());
                     var11.x -= var10.x;
                     var11.y -= var10.y;
                     Vector2 var12 = this.getForwardDirection();
                     var11.normalize();
                     var12.normalize();
                     Float var13 = var11.dot(var12);
                     if (var13 >= var3.getMinAngle() || var9.isOnFloor()) {
                        var2 = true;
                     }

                     if (var2 && ((IsoZombie)var9).Health > 0.0F) {
                        ((IsoZombie)var9).setHitFromBehind(this.isBehind((IsoZombie)var9));
                        ((IsoZombie)var9).setHitAngle(((IsoZombie)var9).getForwardDirection());
                        ((IsoZombie)var9).setPlayerAttackPosition(((IsoZombie)var9).testDotSide(this));
                        float var14 = IsoUtils.DistanceTo(var9.x, var9.y, this.x, this.y);
                        if (var14 < var3.getMaxRange()) {
                           var4.add((IsoZombie)var9);
                        }
                     }
                  }
               }
            }
         }
      }

      if (!var4.isEmpty()) {
         Collections.sort(var4, new Comparator() {
            public int compare(IsoGameCharacter var1, IsoGameCharacter var2) {
               float var3 = IsoUtils.DistanceTo(var1.x, var1.y, IsoPlayer.testHitPosition.x, IsoPlayer.testHitPosition.y);
               float var4 = IsoUtils.DistanceTo(var2.x, var2.y, IsoPlayer.testHitPosition.x, IsoPlayer.testHitPosition.y);
               if (var3 > var4) {
                  return 1;
               } else {
                  return var4 > var3 ? -1 : 0;
               }
            }
         });
         return (IsoGameCharacter)var4.get(0);
      } else {
         return null;
      }
   }

   public void hitConsequences(HandWeapon var1, IsoGameCharacter var2, boolean var3, float var4) {
      if (var3) {
         if (GameServer.bServer) {
            this.sendObjectChange("Shove", new Object[]{"hitDirX", this.getHitDir().getX(), "hitDirY", this.getHitDir().getY(), "force", this.getHitForce()});
         } else {
            var2.xp.AddXP(PerkFactory.Perks.Strength, 2.0F);
            this.setHitForce(Math.min(0.5F, this.getHitForce()));
            this.stateMachine.changeState(StaggerBackState.instance(), (Iterable)null, true);
         }
      } else {
         this.BodyDamage.DamageFromWeapon(var1);
      }
   }

   private HandWeapon getWeapon() {
      if (this.getPrimaryHandItem() instanceof HandWeapon) {
         return (HandWeapon)this.getPrimaryHandItem();
      } else {
         return this.getSecondaryHandItem() instanceof HandWeapon ? (HandWeapon)this.getSecondaryHandItem() : (HandWeapon)InventoryItemFactory.CreateItem("BareHands");
      }
   }

   private void updateMechanicsItems() {
      if (!GameServer.bServer && !this.mechanicsItem.isEmpty()) {
         Iterator var1 = this.mechanicsItem.keySet().iterator();
         ArrayList var2 = new ArrayList();

         while(var1.hasNext()) {
            Long var3 = (Long)var1.next();
            Long var4 = (Long)this.mechanicsItem.get(var3);
            if (GameTime.getInstance().getCalender().getTimeInMillis() > var4 + 86400000L) {
               var2.add(var3);
            }
         }

         for(int var5 = 0; var5 < var2.size(); ++var5) {
            this.mechanicsItem.remove(var2.get(var5));
         }

      }
   }

   private void enterExitVehicle() {
      boolean var1 = this.PlayerIndex == 0 && GameKeyboard.isKeyDown(Core.getInstance().getKey("Interact"));
      if (var1) {
         this.bUseVehicle = true;
         this.useVehicleDuration += GameTime.instance.getRealworldSecondsSinceLastUpdate();
      }

      if (!this.bUsedVehicle && this.bUseVehicle && (!var1 || this.useVehicleDuration > 0.5F)) {
         this.bUsedVehicle = true;
         if (this.getVehicle() != null) {
            LuaEventManager.triggerEvent("OnUseVehicle", this, this.getVehicle(), this.useVehicleDuration > 0.5F);
         } else {
            for(int var2 = 0; var2 < this.getCell().vehicles.size(); ++var2) {
               BaseVehicle var3 = (BaseVehicle)this.getCell().vehicles.get(var2);
               if (var3.getUseablePart(this) != null) {
                  LuaEventManager.triggerEvent("OnUseVehicle", this, var3, this.useVehicleDuration > 0.5F);
                  break;
               }
            }
         }
      }

      if (!var1) {
         this.bUseVehicle = false;
         this.bUsedVehicle = false;
         this.useVehicleDuration = 0.0F;
      }

   }

   private void checkActionGroup() {
      ActionGroup var1 = this.actionContext.getGroup();
      ActionGroup var2;
      if (this.getVehicle() == null) {
         var2 = ActionGroup.getActionGroup("player");
         if (var1 != var2) {
            this.advancedAnimator.OnAnimDataChanged(false);
            this.initializeStates();
            this.actionContext.setGroup(var2);
            this.clearVariable("bEnteringVehicle");
            this.clearVariable("EnterAnimationFinished");
            this.clearVariable("bExitingVehicle");
            this.clearVariable("ExitAnimationFinished");
            this.clearVariable("bSwitchingSeat");
            this.clearVariable("SwitchSeatAnimationFinished");
            this.setHitReaction("");
         }
      } else {
         var2 = ActionGroup.getActionGroup("player-vehicle");
         if (var1 != var2) {
            this.advancedAnimator.OnAnimDataChanged(false);
            this.initializeStates();
            this.actionContext.setGroup(var2);
         }
      }

   }

   public BaseVehicle getUseableVehicle() {
      if (this.getVehicle() != null) {
         return null;
      } else {
         int var1 = ((int)this.x - 4) / 10 - 1;
         int var2 = ((int)this.y - 4) / 10 - 1;
         int var3 = (int)Math.ceil((double)((this.x + 4.0F) / 10.0F)) + 1;
         int var4 = (int)Math.ceil((double)((this.y + 4.0F) / 10.0F)) + 1;

         for(int var5 = var2; var5 < var4; ++var5) {
            for(int var6 = var1; var6 < var3; ++var6) {
               IsoChunk var7 = GameServer.bServer ? ServerMap.instance.getChunk(var6, var5) : IsoWorld.instance.CurrentCell.getChunkForGridSquare(var6 * 10, var5 * 10, 0);
               if (var7 != null) {
                  for(int var8 = 0; var8 < var7.vehicles.size(); ++var8) {
                     BaseVehicle var9 = (BaseVehicle)var7.vehicles.get(var8);
                     if (var9.getUseablePart(this) != null || var9.getBestSeat(this) != -1) {
                        return var9;
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   public Boolean isNearVehicle() {
      if (this.getVehicle() != null) {
         return false;
      } else {
         int var1 = ((int)this.x - 4) / 10 - 1;
         int var2 = ((int)this.y - 4) / 10 - 1;
         int var3 = (int)Math.ceil((double)((this.x + 4.0F) / 10.0F)) + 1;
         int var4 = (int)Math.ceil((double)((this.y + 4.0F) / 10.0F)) + 1;

         for(int var5 = var2; var5 < var4; ++var5) {
            for(int var6 = var1; var6 < var3; ++var6) {
               IsoChunk var7 = GameServer.bServer ? ServerMap.instance.getChunk(var6, var5) : IsoWorld.instance.CurrentCell.getChunkForGridSquare(var6 * 10, var5 * 10, 0);
               if (var7 != null) {
                  for(int var8 = 0; var8 < var7.vehicles.size(); ++var8) {
                     BaseVehicle var9 = (BaseVehicle)var7.vehicles.get(var8);
                     if ((double)var9.DistTo(this) < 3.5D) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   public BaseVehicle getNearVehicle() {
      if (this.getVehicle() != null) {
         return null;
      } else {
         int var1 = ((int)this.x - 4) / 10 - 1;
         int var2 = ((int)this.y - 4) / 10 - 1;
         int var3 = (int)Math.ceil((double)((this.x + 4.0F) / 10.0F)) + 1;
         int var4 = (int)Math.ceil((double)((this.y + 4.0F) / 10.0F)) + 1;

         for(int var5 = var2; var5 < var4; ++var5) {
            for(int var6 = var1; var6 < var3; ++var6) {
               IsoChunk var7 = GameServer.bServer ? ServerMap.instance.getChunk(var6, var5) : IsoWorld.instance.CurrentCell.getChunk(var6, var5);
               if (var7 != null) {
                  for(int var8 = 0; var8 < var7.vehicles.size(); ++var8) {
                     BaseVehicle var9 = (BaseVehicle)var7.vehicles.get(var8);
                     if ((int)this.getZ() == (int)var9.getZ() && (!this.isLocalPlayer() || var9.targetAlpha[this.PlayerIndex] != 0.0F) && !(this.DistToSquared((float)((int)var9.x), (float)((int)var9.y)) >= 16.0F) && PolygonalMap2.instance.intersectLineWithVehicle(this.x, this.y, this.x + this.getForwardDirection().x * 4.0F, this.y + this.getForwardDirection().y * 4.0F, var9, tempVector2) && !PolygonalMap2.instance.lineClearCollide(this.x, this.y, tempVector2.x, tempVector2.y, (int)this.z, var9, false, true)) {
                        return var9;
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   private void updateWhileInVehicle() {
      this.bLookingWhileInVehicle = false;
      ActionGroup var1 = this.actionContext.getGroup();
      ActionGroup var2 = ActionGroup.getActionGroup("player-vehicle");
      if (var1 != var2) {
         this.advancedAnimator.OnAnimDataChanged(false);
         this.initializeStates();
         this.actionContext.setGroup(var2);
      }

      if (GameClient.bClient && this.getVehicle().getSeat(this) == -1) {
         DebugLog.log("forced " + this.getUsername() + " out of vehicle seat -1");
         this.setVehicle((BaseVehicle)null);
      } else {
         this.dirtyRecalcGridStackTime = 10.0F;
         if (this.getVehicle().isDriver(this)) {
            this.getVehicle().updatePhysics();
            if (!this.isAiming()) {
               this.getVehicle().updateControls();
            }
         } else if (GameClient.connection != null) {
            PassengerMap.updatePassenger(this);
         }

         this.fallTime = 0.0F;
         this.bSeenThisFrame = false;
         this.bCouldBeSeenThisFrame = false;
         this.closestZombie = 1000000.0F;
         this.setBeenMovingFor(this.getBeenMovingFor() - 0.625F * GameTime.getInstance().getMultiplier());
         if (!this.Asleep) {
            float var3 = (float)ZomboidGlobals.SittingEnduranceMultiplier;
            var3 *= 1.0F - this.stats.fatigue;
            var3 *= GameTime.instance.getMultiplier();
            Stats var10000 = this.stats;
            var10000.endurance = (float)((double)var10000.endurance + ZomboidGlobals.ImobileEnduranceReduce * SandboxOptions.instance.getEnduranceRegenMultiplier() * (double)this.getRecoveryMod() * (double)var3);
            this.stats.endurance = PZMath.clamp(this.stats.endurance, 0.0F, 1.0F);
         }

         this.updateToggleToAim();
         if (this.vehicle != null) {
            Vector3f var10 = this.vehicle.getForwardVector(tempVector3f);
            boolean var4 = this.isAimControlActive();
            if (this.PlayerIndex == 0) {
               if (Mouse.isButtonDown(1)) {
                  this.TimeRightPressed += GameTime.getInstance().getRealworldSecondsSinceLastUpdate();
               } else {
                  this.TimeRightPressed = 0.0F;
               }

               var4 |= Mouse.isButtonDownUICheck(1) && this.TimeRightPressed >= 0.15F;
            }

            if (!var4 && this.isCurrentState(IdleState.instance())) {
               this.setForwardDirection(var10.x, var10.z);
               this.getForwardDirection().normalize();
            }

            if (this.lastAngle.x != this.getForwardDirection().x || this.lastAngle.y != this.getForwardDirection().y) {
               this.dirtyRecalcGridStackTime = 10.0F;
            }

            this.DirectionFromVector(this.getForwardDirection());
            AnimationPlayer var5 = this.getAnimationPlayer();
            if (var5 != null && var5.isReady()) {
               var5.SetForceDir(this.getForwardDirection());
               float var6 = var5.getAngle() + var5.getTwistAngle();
               this.dir = IsoDirections.fromAngle(tempVector2.setLengthAndDirection(var6, 1.0F));
            }

            boolean var11 = false;
            int var7 = this.vehicle.getSeat(this);
            VehiclePart var8 = this.vehicle.getPassengerDoor(var7);
            if (var8 != null) {
               VehicleWindow var9 = var8.findWindow();
               if (var9 != null && !var9.isHittable()) {
                  var11 = true;
               }
            }

            if (var11) {
               this.attackWhileInVehicle();
            } else if (var4) {
               this.bLookingWhileInVehicle = true;
               this.setAngleFromAim();
            } else {
               this.checkJoypadIgnoreAimUntilCentered();
               this.setIsAiming(false);
            }
         }

      }
   }

   private void attackWhileInVehicle() {
      this.setIsAiming(false);
      boolean var1 = false;
      boolean var2 = false;
      if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
         if (!this.bJoypadMovementActive) {
            return;
         }

         if (this.isChargingLT && !JoypadManager.instance.isLTPressed(this.JoypadBind)) {
            var2 = true;
         } else {
            var1 = this.isCharging && !JoypadManager.instance.isRTPressed(this.JoypadBind);
         }

         float var5 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
         float var4 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
         if (this.bJoypadIgnoreAimUntilCentered) {
            if (var5 == 0.0F && var4 == 0.0F) {
               this.bJoypadIgnoreAimUntilCentered = false;
            } else {
               var4 = 0.0F;
               var5 = 0.0F;
            }
         }

         this.setIsAiming(var5 * var5 + var4 * var4 >= 0.09F);
         this.isCharging = this.isAiming() && JoypadManager.instance.isRTPressed(this.JoypadBind);
         this.isChargingLT = this.isAiming() && JoypadManager.instance.isLTPressed(this.JoypadBind);
      } else {
         boolean var3 = this.isAimKeyDown();
         this.setIsAiming(var3 || Mouse.isButtonDownUICheck(1) && this.TimeRightPressed >= 0.15F);
         if (this.isCharging) {
            this.isCharging = var3 || Mouse.isButtonDown(1);
         } else {
            this.isCharging = var3 || Mouse.isButtonDownUICheck(1) && this.TimeRightPressed >= 0.15F;
         }

         if (this.isForceAim()) {
            this.setIsAiming(true);
            this.isCharging = true;
         }

         if (GameKeyboard.isKeyDown(Core.getInstance().getKey("Melee")) && this.authorizeMeleeAction) {
            var2 = true;
         } else {
            var1 = this.isCharging && Mouse.isButtonDownUICheck(0);
            if (var1) {
               this.setIsAiming(true);
            }
         }
      }

      if (!this.isCharging && !this.isChargingLT) {
         this.chargeTime = 0.0F;
      }

      if (this.isAiming() && !this.bBannedAttacking && this.CanAttack()) {
         this.chargeTime += GameTime.instance.getMultiplier();
         this.useChargeTime = this.chargeTime;
         this.m_meleePressed = var2;
         this.setAngleFromAim();
         if (var2) {
            this.sprite.Animate = true;
            this.bDoShove = true;
            this.AttemptAttack(this.useChargeTime);
            this.useChargeTime = 0.0F;
            this.chargeTime = 0.0F;
         } else if (var1) {
            this.sprite.Animate = true;
            if (this.getRecoilDelay() <= 0.0F) {
               this.AttemptAttack(this.useChargeTime);
            }

            this.useChargeTime = 0.0F;
            this.chargeTime = 0.0F;
         }

      }
   }

   private void setAngleFromAim() {
      Vector2 var1 = tempVector2;
      if (GameWindow.ActivatedJoyPad != null && this.JoypadBind != -1) {
         this.getControllerAimDir(var1);
      } else {
         var1.set(this.getX(), this.getY());
         int var2 = Mouse.getX();
         int var3 = Mouse.getY();
         var1.x -= IsoUtils.XToIso((float)var2, (float)var3 + 55.0F * this.def.getScaleY(), this.getZ());
         var1.y -= IsoUtils.YToIso((float)var2, (float)var3 + 55.0F * this.def.getScaleY(), this.getZ());
         var1.x = -var1.x;
         var1.y = -var1.y;
      }

      if (var1.getLengthSquared() > 0.0F) {
         var1.normalize();
         this.DirectionFromVector(var1);
         this.setForwardDirection(var1);
         if (this.lastAngle.x != var1.x || this.lastAngle.y != var1.y) {
            this.lastAngle.x = var1.x;
            this.lastAngle.y = var1.y;
            this.dirtyRecalcGridStackTime = 10.0F;
         }
      }

   }

   private void updateTorchStrength() {
      if (this.getTorchStrength() > 0.0F || this.flickTorch) {
         DrainableComboItem var1 = (DrainableComboItem)Type.tryCastTo(this.getActiveLightItem(), DrainableComboItem.class);
         if (var1 == null) {
            return;
         }

         if (Rand.Next(600 - (int)(0.4D / (double)var1.getUsedDelta() * 100.0D)) == 0) {
            this.flickTorch = true;
         }

         this.flickTorch = false;
         if (this.flickTorch) {
            if (Rand.Next(6) == 0) {
               var1.setActivated(false);
            } else {
               var1.setActivated(true);
            }

            if (Rand.Next(40) == 0) {
               this.flickTorch = false;
               var1.setActivated(true);
            }
         }
      }

   }

   public IsoCell getCell() {
      return IsoWorld.instance.CurrentCell;
   }

   public void calculateContext() {
      float var1 = this.x;
      float var2 = this.y;
      float var3 = this.x;
      IsoGridSquare[] var4 = new IsoGridSquare[4];
      if (this.dir == IsoDirections.N) {
         var4[2] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 - 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)var1, (double)(var2 - 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 - 1.0F), (double)var3);
      } else if (this.dir == IsoDirections.NE) {
         var4[2] = this.getCell().getGridSquare((double)var1, (double)(var2 - 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 - 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)var2, (double)var3);
      } else if (this.dir == IsoDirections.E) {
         var4[2] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 - 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)var2, (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 + 1.0F), (double)var3);
      } else if (this.dir == IsoDirections.SE) {
         var4[2] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)var2, (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 + 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)var1, (double)(var2 + 1.0F), (double)var3);
      } else if (this.dir == IsoDirections.S) {
         var4[2] = this.getCell().getGridSquare((double)(var1 + 1.0F), (double)(var2 + 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)var1, (double)(var2 + 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 + 1.0F), (double)var3);
      } else if (this.dir == IsoDirections.SW) {
         var4[2] = this.getCell().getGridSquare((double)var1, (double)(var2 + 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 + 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)var2, (double)var3);
      } else if (this.dir == IsoDirections.W) {
         var4[2] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 + 1.0F), (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)var2, (double)var3);
         var4[3] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 - 1.0F), (double)var3);
      } else if (this.dir == IsoDirections.NW) {
         var4[2] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)var2, (double)var3);
         var4[1] = this.getCell().getGridSquare((double)(var1 - 1.0F), (double)(var2 - 1.0F), (double)var3);
         var4[3] = this.getCell().getGridSquare((double)var1, (double)(var2 - 1.0F), (double)var3);
      }

      var4[0] = this.current;

      for(int var5 = 0; var5 < 4; ++var5) {
         IsoGridSquare var6 = var4[var5];
         if (var6 == null) {
         }
      }

   }

   public boolean isSafeToClimbOver(IsoDirections var1) {
      IsoGridSquare var2 = null;
      switch(var1) {
      case N:
         var2 = this.getCell().getGridSquare((double)this.x, (double)(this.y - 1.0F), (double)this.z);
         break;
      case S:
         var2 = this.getCell().getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z);
         break;
      case W:
         var2 = this.getCell().getGridSquare((double)(this.x - 1.0F), (double)this.y, (double)this.z);
         break;
      case E:
         var2 = this.getCell().getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z);
         break;
      default:
         return false;
      }

      if (var2 == null) {
         return false;
      } else if (var2.Is(IsoFlagType.water)) {
         return false;
      } else {
         return !var2.TreatAsSolidFloor() ? var2.HasStairsBelow() : true;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean doContext(IsoDirections var1, boolean var2) {
      return this.doContext(var1);
   }

   public boolean doContext(IsoDirections var1) {
      if (this.isIgnoreContextKey()) {
         return false;
      } else if (this.isBlockMovement()) {
         return false;
      } else {
         for(int var2 = 0; var2 < this.getCell().vehicles.size(); ++var2) {
            BaseVehicle var3 = (BaseVehicle)this.getCell().vehicles.get(var2);
            if (var3.getUseablePart(this) != null) {
               return false;
            }
         }

         float var7 = this.x - (float)((int)this.x);
         float var8 = this.y - (float)((int)this.y);
         IsoDirections var4 = IsoDirections.Max;
         IsoDirections var5 = IsoDirections.Max;
         if (var1 == IsoDirections.NW) {
            if (var8 < var7) {
               if (this.doContextNSWE(IsoDirections.N)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.W)) {
                  return true;
               }

               var4 = IsoDirections.S;
               var5 = IsoDirections.E;
            } else {
               if (this.doContextNSWE(IsoDirections.W)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.N)) {
                  return true;
               }

               var4 = IsoDirections.E;
               var5 = IsoDirections.S;
            }
         } else if (var1 == IsoDirections.NE) {
            var7 = 1.0F - var7;
            if (var8 < var7) {
               if (this.doContextNSWE(IsoDirections.N)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.E)) {
                  return true;
               }

               var4 = IsoDirections.S;
               var5 = IsoDirections.W;
            } else {
               if (this.doContextNSWE(IsoDirections.E)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.N)) {
                  return true;
               }

               var4 = IsoDirections.W;
               var5 = IsoDirections.S;
            }
         } else if (var1 == IsoDirections.SE) {
            var7 = 1.0F - var7;
            var8 = 1.0F - var8;
            if (var8 < var7) {
               if (this.doContextNSWE(IsoDirections.S)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.E)) {
                  return true;
               }

               var4 = IsoDirections.N;
               var5 = IsoDirections.W;
            } else {
               if (this.doContextNSWE(IsoDirections.E)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.S)) {
                  return true;
               }

               var4 = IsoDirections.W;
               var5 = IsoDirections.N;
            }
         } else if (var1 == IsoDirections.SW) {
            var8 = 1.0F - var8;
            if (var8 < var7) {
               if (this.doContextNSWE(IsoDirections.S)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.W)) {
                  return true;
               }

               var4 = IsoDirections.N;
               var5 = IsoDirections.E;
            } else {
               if (this.doContextNSWE(IsoDirections.W)) {
                  return true;
               }

               if (this.doContextNSWE(IsoDirections.S)) {
                  return true;
               }

               var4 = IsoDirections.E;
               var5 = IsoDirections.N;
            }
         } else {
            if (this.doContextNSWE(var1)) {
               return true;
            }

            var4 = var1.RotLeft(4);
         }

         IsoObject var6;
         if (var4 != IsoDirections.Max) {
            var6 = this.getContextDoorOrWindowOrWindowFrame(var4);
            if (var6 != null) {
               this.doContextDoorOrWindowOrWindowFrame(var4, var6);
               return true;
            }
         }

         if (var5 != IsoDirections.Max) {
            var6 = this.getContextDoorOrWindowOrWindowFrame(var5);
            if (var6 != null) {
               this.doContextDoorOrWindowOrWindowFrame(var5, var6);
               return true;
            }
         }

         return false;
      }
   }

   private boolean doContextNSWE(IsoDirections var1) {
      assert var1 == IsoDirections.N || var1 == IsoDirections.S || var1 == IsoDirections.W || var1 == IsoDirections.E;

      if (this.current == null) {
         return false;
      } else if (var1 == IsoDirections.N && this.current.Is(IsoFlagType.climbSheetN) && this.canClimbSheetRope(this.current)) {
         this.climbSheetRope();
         return true;
      } else if (var1 == IsoDirections.S && this.current.Is(IsoFlagType.climbSheetS) && this.canClimbSheetRope(this.current)) {
         this.climbSheetRope();
         return true;
      } else if (var1 == IsoDirections.W && this.current.Is(IsoFlagType.climbSheetW) && this.canClimbSheetRope(this.current)) {
         this.climbSheetRope();
         return true;
      } else if (var1 == IsoDirections.E && this.current.Is(IsoFlagType.climbSheetE) && this.canClimbSheetRope(this.current)) {
         this.climbSheetRope();
         return true;
      } else {
         IsoGridSquare var2 = this.current.nav[var1.index()];
         boolean var3 = IsoWindow.isTopOfSheetRopeHere(var2) && this.canClimbDownSheetRope(var2);
         IsoObject var4 = this.getContextDoorOrWindowOrWindowFrame(var1);
         if (var4 != null) {
            this.doContextDoorOrWindowOrWindowFrame(var1, var4);
            return true;
         } else {
            if (GameKeyboard.isKeyDown(42) && this.current != null && this.ticksSincePressedMovement > 15.0F) {
               IsoObject var5 = this.current.getDoor(true);
               if (var5 instanceof IsoDoor && ((IsoDoor)var5).isFacingSheet(this)) {
                  ((IsoDoor)var5).toggleCurtain();
                  return true;
               }

               IsoObject var6 = this.current.getDoor(false);
               if (var6 instanceof IsoDoor && ((IsoDoor)var6).isFacingSheet(this)) {
                  ((IsoDoor)var6).toggleCurtain();
                  return true;
               }

               IsoGridSquare var7;
               IsoObject var8;
               if (var1 == IsoDirections.E) {
                  var7 = IsoWorld.instance.CurrentCell.getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z);
                  var8 = var7 != null ? var7.getDoor(true) : null;
                  if (var8 instanceof IsoDoor && ((IsoDoor)var8).isFacingSheet(this)) {
                     ((IsoDoor)var8).toggleCurtain();
                     return true;
                  }
               }

               if (var1 == IsoDirections.S) {
                  var7 = IsoWorld.instance.CurrentCell.getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z);
                  var8 = var7 != null ? var7.getDoor(false) : null;
                  if (var8 instanceof IsoDoor && ((IsoDoor)var8).isFacingSheet(this)) {
                     ((IsoDoor)var8).toggleCurtain();
                     return true;
                  }
               }
            }

            boolean var9 = this.isSafeToClimbOver(var1);
            if (this.z > 0.0F && var3) {
               var9 = true;
            }

            if (this.timePressedContext < 0.5F && !var9) {
               return false;
            } else if (this.ignoreAutoVault) {
               return false;
            } else if (var1 == IsoDirections.N && this.getCurrentSquare().Is(IsoFlagType.HoppableN)) {
               this.climbOverFence(var1);
               return true;
            } else if (var1 == IsoDirections.W && this.getCurrentSquare().Is(IsoFlagType.HoppableW)) {
               this.climbOverFence(var1);
               return true;
            } else if (var1 == IsoDirections.S && IsoWorld.instance.CurrentCell.getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z) != null && IsoWorld.instance.CurrentCell.getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z).Is(IsoFlagType.HoppableN)) {
               this.climbOverFence(var1);
               return true;
            } else if (var1 == IsoDirections.E && IsoWorld.instance.CurrentCell.getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z) != null && IsoWorld.instance.CurrentCell.getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z).Is(IsoFlagType.HoppableW)) {
               this.climbOverFence(var1);
               return true;
            } else {
               return this.climbOverWall(var1);
            }
         }
      }
   }

   public IsoObject getContextDoorOrWindowOrWindowFrame(IsoDirections var1) {
      IsoGridSquare var2 = this.current.nav[var1.index()];
      IsoObject var3 = null;
      switch(var1) {
      case N:
         var3 = this.current.getOpenDoor(var1);
         if (var3 != null) {
            return var3;
         }

         var3 = this.current.getDoorOrWindowOrWindowFrame(var1, true);
         if (var3 != null) {
            return var3;
         }

         var3 = this.current.getDoor(true);
         if (var3 != null) {
            return var3;
         }

         if (var2 != null && !this.current.isBlockedTo(var2)) {
            var3 = var2.getOpenDoor(IsoDirections.S);
         }
         break;
      case S:
         var3 = this.current.getOpenDoor(var1);
         if (var3 != null) {
            return var3;
         }

         if (var2 != null) {
            boolean var4 = this.current.isBlockedTo(var2);
            var3 = var2.getDoorOrWindowOrWindowFrame(IsoDirections.N, var4);
            if (var3 != null) {
               return var3;
            }

            var3 = var2.getDoor(true);
         }
         break;
      case W:
         var3 = this.current.getOpenDoor(var1);
         if (var3 != null) {
            return var3;
         }

         var3 = this.current.getDoorOrWindowOrWindowFrame(var1, true);
         if (var3 != null) {
            return var3;
         }

         var3 = this.current.getDoor(false);
         if (var3 != null) {
            return var3;
         }

         if (var2 != null && !this.current.isBlockedTo(var2)) {
            var3 = var2.getOpenDoor(IsoDirections.E);
         }
         break;
      case E:
         var3 = this.current.getOpenDoor(var1);
         if (var3 != null) {
            return var3;
         }

         if (var2 != null) {
            boolean var5 = this.current.isBlockedTo(var2);
            var3 = var2.getDoorOrWindowOrWindowFrame(IsoDirections.W, var5);
            if (var3 != null) {
               return var3;
            }

            var3 = var2.getDoor(false);
         }
      }

      return var3;
   }

   private void doContextDoorOrWindowOrWindowFrame(IsoDirections var1, IsoObject var2) {
      IsoGridSquare var3 = this.current.nav[var1.index()];
      boolean var4 = IsoWindow.isTopOfSheetRopeHere(var3) && this.canClimbDownSheetRope(var3);
      if (var2 instanceof IsoDoor) {
         IsoDoor var5 = (IsoDoor)var2;
         if (GameKeyboard.isKeyDown(42) && var5.HasCurtains() != null && var5.isFacingSheet(this) && this.ticksSincePressedMovement > 15.0F) {
            var5.toggleCurtain();
         } else if (this.timePressedContext >= 0.5F) {
            if (var5.isHoppable() && !this.isIgnoreAutoVault()) {
               this.climbOverFence(var1);
            } else {
               var5.ToggleDoor(this);
            }
         } else {
            var5.ToggleDoor(this);
         }
      } else {
         IsoThumpable var7;
         if (var2 instanceof IsoThumpable && ((IsoThumpable)var2).isDoor()) {
            var7 = (IsoThumpable)var2;
            if (this.timePressedContext >= 0.5F) {
               if (var7.isHoppable() && !this.isIgnoreAutoVault()) {
                  this.climbOverFence(var1);
               } else {
                  var7.ToggleDoor(this);
               }
            } else {
               var7.ToggleDoor(this);
            }
         } else {
            IsoCurtain var6;
            if (var2 instanceof IsoWindow && !var2.getSquare().getProperties().Is(IsoFlagType.makeWindowInvincible)) {
               IsoWindow var8 = (IsoWindow)var2;
               if (GameKeyboard.isKeyDown(42)) {
                  var6 = var8.HasCurtains();
                  if (var6 != null && this.current != null && !var6.getSquare().isBlockedTo(this.current)) {
                     var6.ToggleDoor(this);
                  }
               } else if (this.timePressedContext >= 0.5F) {
                  if (var8.canClimbThrough(this)) {
                     this.climbThroughWindow(var8);
                  } else if (!var8.PermaLocked && !var8.isBarricaded() && !var8.IsOpen()) {
                     this.openWindow(var8);
                  }
               } else if (var8.Health > 0 && !var8.isDestroyed()) {
                  IsoBarricade var9 = var8.getBarricadeForCharacter(this);
                  if (!var8.open && var9 == null) {
                     this.openWindow(var8);
                  } else if (var9 == null) {
                     this.closeWindow(var8);
                  }
               } else if (var8.isGlassRemoved()) {
                  if (!this.isSafeToClimbOver(var1) && !var2.getSquare().haveSheetRope && !var4) {
                     return;
                  }

                  if (!var8.isBarricaded()) {
                     this.climbThroughWindow(var8);
                  }
               }
            } else if (var2 instanceof IsoThumpable && !var2.getSquare().getProperties().Is(IsoFlagType.makeWindowInvincible)) {
               var7 = (IsoThumpable)var2;
               if (GameKeyboard.isKeyDown(42)) {
                  var6 = var7.HasCurtains();
                  if (var6 != null && this.current != null && !var6.getSquare().isBlockedTo(this.current)) {
                     var6.ToggleDoor(this);
                  }
               } else if (this.timePressedContext >= 0.5F) {
                  if (var7.canClimbThrough(this)) {
                     this.climbThroughWindow(var7);
                  }
               } else {
                  if (!this.isSafeToClimbOver(var1) && !var2.getSquare().haveSheetRope && !var4) {
                     return;
                  }

                  if (var7.canClimbThrough(this)) {
                     this.climbThroughWindow(var7);
                  }
               }
            } else if (IsoWindowFrame.isWindowFrame(var2) && (this.timePressedContext >= 0.5F || this.isSafeToClimbOver(var1) || var4) && IsoWindowFrame.canClimbThrough(var2, this)) {
               this.climbThroughWindowFrame(var2);
            }
         }
      }

   }

   public boolean hopFence(IsoDirections var1, boolean var2) {
      float var4 = this.x - (float)((int)this.x);
      float var5 = this.y - (float)((int)this.y);
      if (var1 == IsoDirections.NW) {
         if (var5 < var4) {
            return this.hopFence(IsoDirections.N, var2) ? true : this.hopFence(IsoDirections.W, var2);
         } else {
            return this.hopFence(IsoDirections.W, var2) ? true : this.hopFence(IsoDirections.N, var2);
         }
      } else if (var1 == IsoDirections.NE) {
         var4 = 1.0F - var4;
         if (var5 < var4) {
            return this.hopFence(IsoDirections.N, var2) ? true : this.hopFence(IsoDirections.E, var2);
         } else {
            return this.hopFence(IsoDirections.E, var2) ? true : this.hopFence(IsoDirections.N, var2);
         }
      } else if (var1 == IsoDirections.SE) {
         var4 = 1.0F - var4;
         var5 = 1.0F - var5;
         if (var5 < var4) {
            return this.hopFence(IsoDirections.S, var2) ? true : this.hopFence(IsoDirections.E, var2);
         } else {
            return this.hopFence(IsoDirections.E, var2) ? true : this.hopFence(IsoDirections.S, var2);
         }
      } else if (var1 == IsoDirections.SW) {
         var5 = 1.0F - var5;
         if (var5 < var4) {
            return this.hopFence(IsoDirections.S, var2) ? true : this.hopFence(IsoDirections.W, var2);
         } else {
            return this.hopFence(IsoDirections.W, var2) ? true : this.hopFence(IsoDirections.S, var2);
         }
      } else if (this.current == null) {
         return false;
      } else {
         IsoGridSquare var6 = this.current.nav[var1.index()];
         if (var6 != null && !var6.Is(IsoFlagType.water)) {
            if (var1 == IsoDirections.N && this.getCurrentSquare().Is(IsoFlagType.HoppableN)) {
               if (var2) {
                  return true;
               } else {
                  this.climbOverFence(var1);
                  return true;
               }
            } else if (var1 == IsoDirections.W && this.getCurrentSquare().Is(IsoFlagType.HoppableW)) {
               if (var2) {
                  return true;
               } else {
                  this.climbOverFence(var1);
                  return true;
               }
            } else if (var1 == IsoDirections.S && IsoWorld.instance.CurrentCell.getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z) != null && IsoWorld.instance.CurrentCell.getGridSquare((double)this.x, (double)(this.y + 1.0F), (double)this.z).Is(IsoFlagType.HoppableN)) {
               if (var2) {
                  return true;
               } else {
                  this.climbOverFence(var1);
                  return true;
               }
            } else if (var1 == IsoDirections.E && IsoWorld.instance.CurrentCell.getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z) != null && IsoWorld.instance.CurrentCell.getGridSquare((double)(this.x + 1.0F), (double)this.y, (double)this.z).Is(IsoFlagType.HoppableW)) {
               if (var2) {
                  return true;
               } else {
                  this.climbOverFence(var1);
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public boolean canClimbOverWall(IsoDirections var1) {
      if (!this.isSafeToClimbOver(var1)) {
         return false;
      } else if (this.current.haveRoof) {
         return false;
      } else if (this.current.getBuilding() != null) {
         return false;
      } else {
         IsoGridSquare var2 = IsoWorld.instance.CurrentCell.getGridSquare(this.current.x, this.current.y, this.current.z + 1);
         if (var2 != null && var2.HasSlopedRoof()) {
            return false;
         } else {
            IsoGridSquare var3 = this.current.nav[var1.index()];
            if (var3.haveRoof) {
               return false;
            } else if (!var3.isSolid() && !var3.isSolidTrans()) {
               if (var3.getBuilding() != null) {
                  return false;
               } else {
                  IsoGridSquare var4 = IsoWorld.instance.CurrentCell.getGridSquare(var3.x, var3.y, var3.z + 1);
                  if (var4 != null && var4.HasSlopedRoof()) {
                     return false;
                  } else {
                     switch(var1) {
                     case N:
                        if (this.current.Is(IsoFlagType.CantClimb)) {
                           return false;
                        }

                        if (!this.current.Has(IsoObjectType.wall)) {
                           return false;
                        }

                        if (!this.current.Is(IsoFlagType.collideN)) {
                           return false;
                        }

                        if (this.current.Is(IsoFlagType.HoppableN)) {
                           return false;
                        }

                        if (var2 != null && var2.Is(IsoFlagType.collideN)) {
                           return false;
                        }
                        break;
                     case S:
                        if (var3.Is(IsoFlagType.CantClimb)) {
                           return false;
                        }

                        if (!var3.Has(IsoObjectType.wall)) {
                           return false;
                        }

                        if (!var3.Is(IsoFlagType.collideN)) {
                           return false;
                        }

                        if (var3.Is(IsoFlagType.HoppableN)) {
                           return false;
                        }

                        if (var4 != null && var4.Is(IsoFlagType.collideN)) {
                           return false;
                        }
                        break;
                     case W:
                        if (this.current.Is(IsoFlagType.CantClimb)) {
                           return false;
                        }

                        if (!this.current.Has(IsoObjectType.wall)) {
                           return false;
                        }

                        if (!this.current.Is(IsoFlagType.collideW)) {
                           return false;
                        }

                        if (this.current.Is(IsoFlagType.HoppableW)) {
                           return false;
                        }

                        if (var2 != null && var2.Is(IsoFlagType.collideW)) {
                           return false;
                        }
                        break;
                     case E:
                        if (var3.Is(IsoFlagType.CantClimb)) {
                           return false;
                        }

                        if (!var3.Has(IsoObjectType.wall)) {
                           return false;
                        }

                        if (!var3.Is(IsoFlagType.collideW)) {
                           return false;
                        }

                        if (var3.Is(IsoFlagType.HoppableW)) {
                           return false;
                        }

                        if (var4 != null && var4.Is(IsoFlagType.collideW)) {
                           return false;
                        }
                        break;
                     default:
                        return false;
                     }

                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }
   }

   public boolean climbOverWall(IsoDirections var1) {
      if (!this.canClimbOverWall(var1)) {
         return false;
      } else {
         this.dropHeavyItems();
         ClimbOverWallState.instance().setParams(this, var1);
         this.actionContext.reportEvent("EventClimbWall");
         return true;
      }
   }

   private void updateSleepingPillsTaken() {
      if (this.getSleepingPillsTaken() > 0 && this.lastPillsTaken > 0L && GameTime.instance.Calender.getTimeInMillis() - this.lastPillsTaken > 7200000L) {
         this.setSleepingPillsTaken(this.getSleepingPillsTaken() - 1);
      }

   }

   public boolean AttemptAttack() {
      return this.DoAttack(this.useChargeTime);
   }

   public boolean DoAttack(float var1) {
      return this.DoAttack(var1, false, (String)null);
   }

   public boolean DoAttack(float var1, boolean var2, String var3) {
      if (!this.authorizeMeleeAction) {
         return false;
      } else {
         this.setForceShove(var2);
         this.setClickSound(var3);
         this.pressedAttack();
         return false;
      }
   }

   public int getPlayerNum() {
      return this.PlayerIndex;
   }

   public void updateLOS() {
      this.spottedList.clear();
      this.stats.NumVisibleZombies = 0;
      this.stats.LastNumChasingZombies = this.stats.NumChasingZombies;
      this.stats.NumChasingZombies = 0;
      int var1 = 0;
      int var2 = 0;
      this.NumSurvivorsInVicinity = 0;
      int var3 = this.getCell().getObjectList().size();

      for(int var4 = 0; var4 < var3; ++var4) {
         IsoMovingObject var5 = (IsoMovingObject)this.getCell().getObjectList().get(var4);
         if (!(var5 instanceof IsoPhysicsObject) && !(var5 instanceof BaseVehicle)) {
            if (var5 == this) {
               this.spottedList.add(var5);
            } else {
               float var6 = IsoUtils.DistanceManhatten(var5.getX(), var5.getY(), this.getX(), this.getY());
               if (var6 < 20.0F) {
                  ++var1;
               }

               if (var5.getCurrentSquare() != null) {
                  if (this.getCurrentSquare() == null) {
                     return;
                  }

                  float var7 = 3.5F - this.stats.getFatigue();
                  boolean var8 = GameServer.bServer ? ServerLOS.instance.isCouldSee(this, var5.getCurrentSquare()) : var5.getCurrentSquare().isCouldSee(this.PlayerIndex);
                  boolean var9 = GameServer.bServer ? var8 : var5.getCurrentSquare().isCanSee(this.PlayerIndex);
                  if (this.isAsleep() || !var9 && (!(var6 < var7) || !var8)) {
                     if (var5 != instance) {
                        var5.targetAlpha[this.PlayerIndex] = 0.0F;
                     }

                     if (var8) {
                        this.TestZombieSpotPlayer(var5);
                     }
                  } else {
                     this.TestZombieSpotPlayer(var5);
                     if (var5 instanceof IsoGameCharacter && ((IsoGameCharacter)var5).SpottedSinceAlphaZero[this.PlayerIndex]) {
                        if (var5 instanceof IsoSurvivor) {
                           ++this.NumSurvivorsInVicinity;
                        }

                        if (var5 instanceof IsoZombie) {
                           this.lastSeenZombieTime = 0.0D;
                           if (var5.getZ() >= this.getZ() - 1.0F && var6 < 7.0F && !((IsoZombie)((IsoZombie)var5)).Ghost && !((IsoZombie)((IsoZombie)var5)).isFakeDead() && var5.getCurrentSquare().getRoom() == this.getCurrentSquare().getRoom()) {
                              this.TicksSinceSeenZombie = 0;
                              ++this.stats.NumVisibleZombies;
                           }

                           if (var6 < 3.0F) {
                              ++var2;
                           }
                        }

                        this.spottedList.add(var5);
                        var5.targetAlpha[this.PlayerIndex] = 1.0F;
                        float var10 = 4.0F;
                        if (this.stats.NumVisibleZombies > 4) {
                           var10 = 7.0F;
                        }

                        if (var6 < var10 && var5 instanceof IsoZombie && (int)var5.getZ() == (int)this.getZ() && !this.isGhostMode() && !GameClient.bClient) {
                           GameTime.instance.setMultiplier(1.0F);
                           UIManager.getSpeedControls().SetCurrentGameSpeed(1);
                        }

                        if (var6 < var10 && var5 instanceof IsoZombie && (int)var5.getZ() == (int)this.getZ() && !this.LastSpotted.contains(var5)) {
                           Stats var10000 = this.stats;
                           var10000.NumVisibleZombies += 2;
                        }
                     } else {
                        var5.targetAlpha[this.PlayerIndex] = 1.0F;
                     }
                  }

                  if (this.isSeeEveryone()) {
                     var5.alpha[this.PlayerIndex] = var5.targetAlpha[this.PlayerIndex] = 1.0F;
                  }

                  if (var5 instanceof IsoGameCharacter && ((IsoGameCharacter)var5).isInvisible() && this.accessLevel.equals("")) {
                     var5.alpha[this.PlayerIndex] = var5.targetAlpha[this.PlayerIndex] = 0.0F;
                  }

                  if (var6 < 2.0F && var5.targetAlpha[this.PlayerIndex] == 1.0F) {
                     var5.alpha[this.PlayerIndex] = 1.0F;
                  }
               }
            }
         }
      }

      if (this.isAlive() && var2 > 0 && this.stats.LastVeryCloseZombies == 0 && this.stats.NumVisibleZombies > 0 && this.stats.LastNumVisibleZombies == 0 && this.timeSinceLastStab >= 600.0F) {
         this.timeSinceLastStab = 0.0F;
         this.getEmitter().playSoundImpl("ZombieSurprisedPlayer", (IsoObject)null);
      }

      if (this.stats.NumVisibleZombies > 0) {
         this.timeSinceLastStab = 0.0F;
      }

      if (this.timeSinceLastStab < 600.0F) {
         this.timeSinceLastStab += GameTime.getInstance().getMultiplier() / 1.6F;
      }

      float var11 = (float)var1 / 20.0F;
      if (var11 > 1.0F) {
         var11 = 1.0F;
      }

      var11 *= 0.6F;
      SoundManager.instance.BlendVolume(MainScreenState.ambient, var11);
      int var12 = 0;

      for(int var13 = 0; var13 < this.spottedList.size(); ++var13) {
         if (!this.LastSpotted.contains(this.spottedList.get(var13))) {
            this.LastSpotted.add(this.spottedList.get(var13));
         }

         if (this.spottedList.get(var13) instanceof IsoZombie) {
            ++var12;
         }
      }

      if (this.ClearSpottedTimer <= 0 && var12 == 0) {
         this.LastSpotted.clear();
         this.ClearSpottedTimer = 1000;
      } else {
         --this.ClearSpottedTimer;
      }

      this.stats.LastNumVisibleZombies = this.stats.NumVisibleZombies;
      this.stats.LastVeryCloseZombies = var2;
   }

   public String getTimeSurvived() {
      String var1 = "";
      int var2 = (int)this.getHoursSurvived();
      int var4 = var2 / 24;
      int var3 = var2 % 24;
      int var5 = var4 / 30;
      var4 %= 30;
      int var6 = var5 / 12;
      var5 %= 12;
      String var7 = Translator.getText("IGUI_Gametime_day");
      String var8 = Translator.getText("IGUI_Gametime_year");
      String var9 = Translator.getText("IGUI_Gametime_hour");
      String var10 = Translator.getText("IGUI_Gametime_month");
      if (var6 != 0) {
         if (var6 > 1) {
            var8 = Translator.getText("IGUI_Gametime_years");
         }

         if (var1.length() > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var6 + " " + var8;
      }

      if (var5 != 0) {
         if (var5 > 1) {
            var10 = Translator.getText("IGUI_Gametime_months");
         }

         if (var1.length() > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var5 + " " + var10;
      }

      if (var4 != 0) {
         if (var4 > 1) {
            var7 = Translator.getText("IGUI_Gametime_days");
         }

         if (var1.length() > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var4 + " " + var7;
      }

      if (var3 != 0) {
         if (var3 > 1) {
            var9 = Translator.getText("IGUI_Gametime_hours");
         }

         if (var1.length() > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var3 + " " + var9;
      }

      if (var1.isEmpty()) {
         int var11 = (int)(this.HoursSurvived * 60.0D);
         var1 = var11 + " " + Translator.getText("IGUI_Gametime_minutes");
      }

      return var1;
   }

   public boolean IsUsingAimWeapon() {
      if (this.leftHandItem == null) {
         return false;
      } else if (!(this.leftHandItem instanceof HandWeapon)) {
         return false;
      } else if (!this.isAiming()) {
         return false;
      } else {
         return ((HandWeapon)this.leftHandItem).bIsAimedFirearm;
      }
   }

   private boolean IsUsingAimHandWeapon() {
      if (this.leftHandItem == null) {
         return false;
      } else if (!(this.leftHandItem instanceof HandWeapon)) {
         return false;
      } else if (!this.isAiming()) {
         return false;
      } else {
         return ((HandWeapon)this.leftHandItem).bIsAimedHandWeapon;
      }
   }

   private boolean DoAimAnimOnAiming() {
      return this.IsUsingAimWeapon();
   }

   public int getSleepingPillsTaken() {
      return this.sleepingPillsTaken;
   }

   public void setSleepingPillsTaken(int var1) {
      this.sleepingPillsTaken = var1;
      if (this.getStats().Drunkenness > 10.0F) {
         ++this.sleepingPillsTaken;
      }

      this.lastPillsTaken = GameTime.instance.Calender.getTimeInMillis();
   }

   public boolean isOutside() {
      return this.getCurrentSquare() != null && this.getCurrentSquare().getRoom() == null && !this.isInARoom();
   }

   public double getLastSeenZomboidTime() {
      return this.lastSeenZombieTime;
   }

   public float getPlayerClothingTemperature() {
      float var1 = 0.0F;
      if (this.getClothingItem_Feet() != null) {
         var1 += ((Clothing)this.getClothingItem_Feet()).getTemperature();
      }

      if (this.getClothingItem_Hands() != null) {
         var1 += ((Clothing)this.getClothingItem_Hands()).getTemperature();
      }

      if (this.getClothingItem_Head() != null) {
         var1 += ((Clothing)this.getClothingItem_Head()).getTemperature();
      }

      if (this.getClothingItem_Legs() != null) {
         var1 += ((Clothing)this.getClothingItem_Legs()).getTemperature();
      }

      if (this.getClothingItem_Torso() != null) {
         var1 += ((Clothing)this.getClothingItem_Torso()).getTemperature();
      }

      return var1;
   }

   public float getPlayerClothingInsulation() {
      float var1 = 0.0F;
      if (this.getClothingItem_Feet() != null) {
         var1 += ((Clothing)this.getClothingItem_Feet()).getInsulation() * 0.1F;
      }

      if (this.getClothingItem_Hands() != null) {
         var1 += ((Clothing)this.getClothingItem_Hands()).getInsulation() * 0.0F;
      }

      if (this.getClothingItem_Head() != null) {
         var1 += ((Clothing)this.getClothingItem_Head()).getInsulation() * 0.0F;
      }

      if (this.getClothingItem_Legs() != null) {
         var1 += ((Clothing)this.getClothingItem_Legs()).getInsulation() * 0.3F;
      }

      if (this.getClothingItem_Torso() != null) {
         var1 += ((Clothing)this.getClothingItem_Torso()).getInsulation() * 0.6F;
      }

      return var1;
   }

   public InventoryItem getActiveLightItem() {
      if (this.rightHandItem != null && this.rightHandItem.isEmittingLight()) {
         return this.rightHandItem;
      } else if (this.leftHandItem != null && this.leftHandItem.isEmittingLight()) {
         return this.leftHandItem;
      } else {
         AttachedItems var1 = this.getAttachedItems();

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            InventoryItem var3 = var1.getItemByIndex(var2);
            if (var3.isEmittingLight()) {
               return var3;
            }
         }

         return null;
      }
   }

   public boolean isTorchCone() {
      if (this.bRemote) {
         return this.mpTorchCone;
      } else {
         InventoryItem var1 = this.getActiveLightItem();
         return var1 != null && var1.isTorchCone();
      }
   }

   public float getTorchDot() {
      if (this.bRemote) {
      }

      InventoryItem var1 = this.getActiveLightItem();
      return var1 != null ? var1.getTorchDot() : 0.0F;
   }

   public float getLightDistance() {
      if (this.bRemote) {
         return this.mpTorchDist;
      } else {
         InventoryItem var1 = this.getActiveLightItem();
         return var1 != null ? (float)var1.getLightDistance() : 0.0F;
      }
   }

   public boolean pressedMovement(boolean var1) {
      if (this.isNPC) {
         return false;
      } else {
         this.setVariable("pressedRunButton", GameKeyboard.isKeyDown(Core.getInstance().getKey("Run")));
         if (var1 || !this.isBlockMovement() && !this.isIgnoreInputsForDirection()) {
            if (this.PlayerIndex != 0 || !GameKeyboard.isKeyDown(Core.getInstance().getKey("Left")) && !GameKeyboard.isKeyDown(Core.getInstance().getKey("Right")) && !GameKeyboard.isKeyDown(Core.getInstance().getKey("Forward")) && !GameKeyboard.isKeyDown(Core.getInstance().getKey("Backward"))) {
               if (this.JoypadBind != -1) {
                  float var2 = JoypadManager.instance.getMovementAxisY(this.JoypadBind);
                  float var3 = JoypadManager.instance.getMovementAxisX(this.JoypadBind);
                  float var4 = JoypadManager.instance.getDeadZone(this.JoypadBind, 0);
                  if (Math.abs(var2) > var4 || Math.abs(var3) > var4) {
                     return true;
                  }
               }

               return false;
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public boolean pressedCancelAction() {
      if (this.isNPC) {
         return false;
      } else if (this.PlayerIndex == 0 && GameKeyboard.isKeyDown(Core.getInstance().getKey("CancelAction"))) {
         return true;
      } else {
         return this.JoypadBind != -1 ? JoypadManager.instance.isBPressed(this.JoypadBind) : false;
      }
   }

   public boolean pressedAim() {
      if (this.isNPC) {
         return false;
      } else {
         if (this.PlayerIndex == 0) {
            if (this.isAimKeyDown()) {
               return true;
            }

            if (Mouse.isButtonDownUICheck(1)) {
               return true;
            }
         }

         if (this.JoypadBind != -1) {
            float var1 = JoypadManager.instance.getAimingAxisY(this.JoypadBind);
            float var2 = JoypadManager.instance.getAimingAxisX(this.JoypadBind);
            if (Math.abs(var1) > 0.1F || Math.abs(var2) > 0.1F) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isDoingActionThatCanBeCancelled() {
      if (this.isDead()) {
         return false;
      } else if (!this.getCharacterActions().isEmpty()) {
         return true;
      } else {
         State var1 = this.getCurrentState();
         if (var1 != null && var1.isDoingActionThatCanBeCancelled()) {
            return true;
         } else {
            for(int var2 = 0; var2 < this.stateMachine.getSubStateCount(); ++var2) {
               var1 = this.stateMachine.getSubStateAt(var2);
               if (var1 != null && var1.isDoingActionThatCanBeCancelled()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public long getSteamID() {
      return this.steamID;
   }

   public void setSteamID(long var1) {
      this.steamID = var1;
   }

   public boolean isTargetedByZombie() {
      return this.targetedByZombie;
   }

   public boolean isMaskClicked(int var1, int var2, boolean var3) {
      return this.sprite == null ? false : this.sprite.isMaskClicked(this.dir, var1, var2, var3);
   }

   public int getOffSetXUI() {
      return this.offSetXUI;
   }

   public void setOffSetXUI(int var1) {
      this.offSetXUI = var1;
   }

   public int getOffSetYUI() {
      return this.offSetYUI;
   }

   public void setOffSetYUI(int var1) {
      this.offSetYUI = var1;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String var1) {
      this.username = var1;
   }

   public void updateUsername() {
      if (!GameClient.bClient && !GameServer.bServer) {
         this.username = this.getDescriptor().getForename() + this.getDescriptor().getSurname();
      }
   }

   public int getOnlineID() {
      return this.OnlineID;
   }

   public boolean isLocalPlayer() {
      for(int var1 = 0; var1 < numPlayers; ++var1) {
         if (players[var1] == this) {
            return true;
         }
      }

      return false;
   }

   public static void setLocalPlayer(int var0, IsoPlayer var1) {
      players[var0] = var1;
   }

   public boolean isOnlyPlayerAsleep() {
      if (!this.isAsleep()) {
         return false;
      } else {
         for(int var1 = 0; var1 < numPlayers; ++var1) {
            if (players[var1] != null && !players[var1].isDead() && players[var1] != this && players[var1].isAsleep()) {
               return false;
            }
         }

         return true;
      }
   }

   public void OnDeath() {
      super.OnDeath();
      this.advancedAnimator.SetState("death");
      if (!GameServer.bServer) {
         this.StopAllActionQueue();
         if (!GameClient.bClient || this.isLocalPlayer()) {
            this.dropHandItems();
         }

         if (this.isLocalPlayer()) {
            SoundManager.instance.PlaySound("PlayerDied", false, 0.8F);
         }

         if (allPlayersDead()) {
            SoundManager.instance.playMusic(DEATH_MUSIC_NAME);
         }

         if (this.isLocalPlayer()) {
            LuaEventManager.triggerEvent("OnPlayerDeath", this);
         }

         if (this.isLocalPlayer() && this.getVehicle() != null) {
            this.getVehicle().exit(this);
         }

         this.removeSaveFile();
         if (this.shouldBecomeZombieAfterDeath()) {
            this.forceAwake();
         }

         this.getMoodles().Update();
         this.getCell().setDrag((KahluaTable)null, this.getPlayerNum());
      }
   }

   public boolean isNoClip() {
      return this.noClip;
   }

   public void setNoClip(boolean var1) {
      this.noClip = var1;
   }

   public void setAuthorizeMeleeAction(boolean var1) {
      this.authorizeMeleeAction = var1;
   }

   public boolean isAuthorizeMeleeAction() {
      return this.authorizeMeleeAction;
   }

   public void setAuthorizeShoveStomp(boolean var1) {
      this.authorizeShoveStomp = var1;
   }

   public boolean isAuthorizeShoveStomp() {
      return this.authorizeShoveStomp;
   }

   public boolean isBlockMovement() {
      return this.blockMovement;
   }

   public void setBlockMovement(boolean var1) {
      this.blockMovement = var1;
   }

   public void startReceivingBodyDamageUpdates(IsoPlayer var1) {
      if (GameClient.bClient && var1 != null && var1 != this && this.isLocalPlayer() && !var1.isLocalPlayer()) {
         var1.resetBodyDamageRemote();
         BodyDamageSync.instance.startReceivingUpdates(var1.getOnlineID());
      }

   }

   public void stopReceivingBodyDamageUpdates(IsoPlayer var1) {
      if (GameClient.bClient && var1 != null && var1 != this && !var1.isLocalPlayer()) {
         BodyDamageSync.instance.stopReceivingUpdates(var1.getOnlineID());
      }

   }

   public Nutrition getNutrition() {
      return this.nutrition;
   }

   public Fitness getFitness() {
      return this.fitness;
   }

   private boolean updateRemotePlayer() {
      if (this.bRemote || GameClient.bClient && this.NetRemoteState == NetRemoteState_Attack && !this.def.Finished) {
         if (GameServer.bServer) {
            ServerLOS.instance.doServerZombieLOS(this);
            ServerLOS.instance.updateLOS(this);
            if (this.isDead()) {
               return true;
            }

            tempo.x = this.x - this.lx;
            tempo.y = this.y - this.ly;
            if (this.isAiming()) {
               this.DoFootstepSound(this.CurrentSpeed * (2.0F - this.getNimbleMod()));
            } else {
               this.DoFootstepSound(this.CurrentSpeed);
            }

            if (this.slowTimer > 0.0F) {
               this.slowTimer -= GameTime.instance.getRealworldSecondsSinceLastUpdate();
               this.slowFactor -= GameTime.instance.getMultiplier() / 100.0F;
               if (this.slowFactor < 0.0F) {
                  this.slowFactor = 0.0F;
               }
            } else {
               this.slowFactor = 0.0F;
            }
         }

         this.setBlendSpeed(0.08F);
         if (this.remoteMoveX != 0.0F || this.remoteMoveY != 0.0F) {
            this.playerMoveDir.x = this.remoteMoveX;
            this.playerMoveDir.y = this.remoteMoveY;
            tempo.x = this.remoteMoveX;
            tempo.y = this.remoteMoveY;
            this.Move(tempo);
            this.setBlendSpeed(tempo.getLength() * 2.0F * 0.9F);
         }

         if (GameClient.bClient) {
            this.netHistory.interpolate(this);
         }

         if (!GameServer.bServer && this.bRemote) {
            this.changeState(IdleState.instance());
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean updateWhileDead() {
      if (GameServer.bServer) {
         return false;
      } else if (!this.isLocalPlayer()) {
         return false;
      } else if (!this.isDead()) {
         return false;
      } else {
         this.setVariable("bPathfind", false);
         this.setMoving(false);
         this.m_isPlayerMoving = false;
         if (this.getVehicle() != null) {
            this.getVehicle().exit(this);
         }

         if (this.heartEventInstance != 0L) {
            this.getEmitter().stopSound(this.heartEventInstance);
            this.heartEventInstance = 0L;
         }

         if (GameClient.bClient && !this.bRemote && !this.bSentDeath) {
            GameClient.instance.sendDeath(this);
            ClientPlayerDB.getInstance().clientSendNetworkPlayerInt(this);
            this.bSentDeath = true;
         }

         return true;
      }
   }

   private void updateHeartSound() {
      if (!GameServer.bServer) {
         if (this.isLocalPlayer()) {
            GameSound var1 = GameSounds.getSound("HeartBeat");
            boolean var2 = var1 != null && var1.userVolume > 0.0F && this.stats.Panic > 0.0F;
            if (!this.Asleep && var2 && GameTime.getInstance().getTrueMultiplier() == 1.0F) {
               this.heartDelay -= GameTime.getInstance().getMultiplier() / 1.6F;
               if (this.heartEventInstance == 0L || !this.getEmitter().isPlaying(this.heartEventInstance)) {
                  this.heartEventInstance = this.getEmitter().playSoundImpl("HeartBeat", (IsoObject)null);
                  this.getEmitter().setVolume(this.heartEventInstance, 0.0F);
               }

               if (this.heartDelay <= 0.0F) {
                  this.heartDelayMax = (float)((int)((1.0F - this.stats.Panic / 100.0F * 0.7F) * 25.0F) * 2);
                  this.heartDelay = this.heartDelayMax;
                  if (this.heartEventInstance != 0L) {
                     this.getEmitter().setVolume(this.heartEventInstance, this.stats.Panic / 100.0F);
                  }
               }
            } else if (this.heartEventInstance != 0L) {
               this.getEmitter().setVolume(this.heartEventInstance, 0.0F);
            }

         }
      }
   }

   private void updateHeavyBreathing() {
   }

   private void checkVehicleContainers() {
      ArrayList var1 = this.vehicleContainerData.tempContainers;
      var1.clear();
      int var2 = (int)this.getX() - 4;
      int var3 = (int)this.getY() - 4;
      int var4 = (int)this.getX() + 4;
      int var5 = (int)this.getY() + 4;
      int var6 = var2 / 10;
      int var7 = var3 / 10;
      int var8 = (int)Math.ceil((double)((float)var4 / 10.0F));
      int var9 = (int)Math.ceil((double)((float)var5 / 10.0F));

      int var10;
      for(var10 = var7; var10 < var9; ++var10) {
         for(int var11 = var6; var11 < var8; ++var11) {
            IsoChunk var12 = GameServer.bServer ? ServerMap.instance.getChunk(var11, var10) : IsoWorld.instance.CurrentCell.getChunkForGridSquare(var11 * 10, var10 * 10, 0);
            if (var12 != null) {
               for(int var13 = 0; var13 < var12.vehicles.size(); ++var13) {
                  BaseVehicle var14 = (BaseVehicle)var12.vehicles.get(var13);
                  VehicleScript var15 = var14.getScript();
                  if (var15 != null) {
                     for(int var16 = 0; var16 < var15.getPartCount(); ++var16) {
                        VehicleScript.Part var17 = var15.getPart(var16);
                        if (var17.container != null && var17.area != null && var14.isInArea(var17.area, this)) {
                           IsoPlayer.VehicleContainer var18 = this.vehicleContainerData.freeContainers.isEmpty() ? new IsoPlayer.VehicleContainer() : (IsoPlayer.VehicleContainer)this.vehicleContainerData.freeContainers.pop();
                           var1.add(var18.set(var14, var16));
                        }
                     }
                  }
               }
            }
         }
      }

      if (var1.size() != this.vehicleContainerData.containers.size()) {
         this.vehicleContainerData.freeContainers.addAll(this.vehicleContainerData.containers);
         this.vehicleContainerData.containers.clear();
         this.vehicleContainerData.containers.addAll(var1);
         LuaEventManager.triggerEvent("OnContainerUpdate");
      } else {
         for(var10 = 0; var10 < var1.size(); ++var10) {
            IsoPlayer.VehicleContainer var19 = (IsoPlayer.VehicleContainer)var1.get(var10);
            IsoPlayer.VehicleContainer var20 = (IsoPlayer.VehicleContainer)this.vehicleContainerData.containers.get(var10);
            if (!var19.equals(var20)) {
               this.vehicleContainerData.freeContainers.addAll(this.vehicleContainerData.containers);
               this.vehicleContainerData.containers.clear();
               this.vehicleContainerData.containers.addAll(var1);
               LuaEventManager.triggerEvent("OnContainerUpdate");
               break;
            }
         }
      }

   }

   public void setJoypadIgnoreAimUntilCentered(boolean var1) {
      this.bJoypadIgnoreAimUntilCentered = var1;
   }

   public boolean canSeePlayerStats() {
      return this.accessLevel != "";
   }

   public ByteBufferWriter createPlayerStats(ByteBufferWriter var1, String var2) {
      PacketTypes.doPacket((short)123, var1);
      var1.putInt(this.getOnlineID());
      var1.putUTF(var2);
      var1.putUTF(this.getDisplayName());
      var1.putUTF(this.getDescriptor().getForename());
      var1.putUTF(this.getDescriptor().getSurname());
      var1.putUTF(this.getDescriptor().getProfession());
      var1.putUTF(this.accessLevel);
      var1.putUTF(this.getTagPrefix());
      if (this.accessLevel.equals("")) {
         this.m_ghostMode = false;
         this.setInvisible(false);
         this.setGodMod(false);
      }

      var1.putBoolean(this.isAllChatMuted());
      var1.putFloat(this.getTagColor().r);
      var1.putFloat(this.getTagColor().g);
      var1.putFloat(this.getTagColor().b);
      var1.putByte((byte)(this.showTag ? 1 : 0));
      var1.putByte((byte)(this.factionPvp ? 1 : 0));
      return var1;
   }

   public String setPlayerStats(ByteBuffer var1, String var2) {
      String var3 = GameWindow.ReadString(var1);
      String var4 = GameWindow.ReadString(var1);
      String var5 = GameWindow.ReadString(var1);
      String var6 = GameWindow.ReadString(var1);
      String var7 = GameWindow.ReadString(var1);
      String var8 = GameWindow.ReadString(var1);
      boolean var9 = var1.get() == 1;
      float var10 = var1.getFloat();
      float var11 = var1.getFloat();
      float var12 = var1.getFloat();
      String var13 = "";
      this.setTagColor(new ColorInfo(var10, var11, var12, 1.0F));
      this.setTagPrefix(var8);
      this.showTag = var1.get() == 1;
      this.factionPvp = var1.get() == 1;
      if (!var4.equals(this.getDescriptor().getForename())) {
         if (GameServer.bServer) {
            var13 = var2 + " Changed " + var3 + " forname in " + var4;
         } else {
            var13 = "Changed your forname in " + var4;
         }
      }

      this.getDescriptor().setForename(var4);
      if (!var5.equals(this.getDescriptor().getSurname())) {
         if (GameServer.bServer) {
            var13 = var2 + " Changed " + var3 + " surname in " + var5;
         } else {
            var13 = "Changed your surname in " + var5;
         }
      }

      this.getDescriptor().setSurname(var5);
      if (!var6.equals(this.getDescriptor().getProfession())) {
         if (GameServer.bServer) {
            var13 = var2 + " Changed " + var3 + " profession to " + var6;
         } else {
            var13 = "Changed your profession in " + var6;
         }
      }

      this.getDescriptor().setProfession(var6);
      if (!this.accessLevel.equals(var7)) {
         if (GameServer.bServer) {
            (new StringBuilder()).append(var2).append(" Changed ").append(this.getDisplayName()).append(" access level to ").append(var7).toString();

            try {
               ServerWorldDatabase.instance.setAccessLevel(this.username, var7);
            } catch (SQLException var15) {
               var15.printStackTrace();
            }
         } else if (GameClient.bClient && GameClient.username.equals(this.username)) {
            GameClient.accessLevel = var7;
            GameClient.connection.accessLevel = var7;
         }

         if (var7.equals("")) {
            this.m_ghostMode = false;
            this.setInvisible(false);
            this.setGodMod(false);
         }

         var13 = "Changed access level to " + var7;
         this.accessLevel = var7;
      }

      if (!this.getDisplayName().equals(var3)) {
         if (GameServer.bServer) {
            var13 = var2 + " Changed display name \"" + this.getDisplayName() + "\" to \"" + var3 + "\"";
            ServerWorldDatabase.instance.updateDisplayName(this.username, var3);
         } else {
            var13 = "Changed your display name to " + var3;
         }

         this.setDisplayName(var3);
      }

      if (var9 != this.isAllChatMuted()) {
         if (var9) {
            if (GameServer.bServer) {
               var13 = var2 + " Banned " + var3 + " from using /all chat";
            } else {
               var13 = "Banned you from using /all chat";
            }
         } else if (GameServer.bServer) {
            var13 = var2 + " Allowed " + var3 + " to use /all chat";
         } else {
            var13 = "Now allowed you to use /all chat";
         }
      }

      this.setAllChatMuted(var9);
      if (GameServer.bServer && !"".equals(var13)) {
         LoggerManager.getLogger("admin").write(var13);
      }

      if (GameClient.bClient) {
         LuaEventManager.triggerEvent("OnMiniScoreboardUpdate");
      }

      return var13;
   }

   public boolean isAllChatMuted() {
      return this.allChatMuted;
   }

   public void setAllChatMuted(boolean var1) {
      this.allChatMuted = var1;
   }

   public String getAccessLevel() {
      String var1 = this.accessLevel;
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -2004703995:
         if (var1.equals("moderator")) {
            var2 = 1;
         }
         break;
      case 3302:
         if (var1.equals("gm")) {
            var2 = 3;
         }
         break;
      case 92668751:
         if (var1.equals("admin")) {
            var2 = 0;
         }
         break;
      case 348607190:
         if (var1.equals("observer")) {
            var2 = 4;
         }
         break;
      case 530022739:
         if (var1.equals("overseer")) {
            var2 = 2;
         }
      }

      switch(var2) {
      case 0:
         return "Admin";
      case 1:
         return "Moderator";
      case 2:
         return "Overseer";
      case 3:
         return "GM";
      case 4:
         return "Observer";
      default:
         return "None";
      }
   }

   public void setAccessLevel(String var1) {
      this.accessLevel = var1;
   }

   public void addMechanicsItem(String var1, VehiclePart var2, Long var3) {
      byte var4 = 1;
      byte var5 = 1;
      if (this.mechanicsItem.get(Long.parseLong(var1)) == null) {
         if (var2.getTable("uninstall") != null && var2.getTable("uninstall").rawget("skills") != null) {
            String[] var6 = ((String)var2.getTable("uninstall").rawget("skills")).split(";");
            String[] var7 = var6;
            int var8 = var6.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String var10 = var7[var9];
               if (var10.contains("Mechanics")) {
                  int var11 = Integer.parseInt(var10.split(":")[1]);
                  if (var11 >= 6) {
                     var4 = 3;
                     var5 = 7;
                  } else if (var11 >= 4) {
                     var4 = 3;
                     var5 = 5;
                  } else if (var11 >= 2) {
                     var4 = 2;
                     var5 = 4;
                  } else if (Rand.Next(3) == 0) {
                     var4 = 2;
                     var5 = 2;
                  }
               }
            }
         }

         this.getXp().AddXP(PerkFactory.Perks.Mechanics, (float)Rand.Next(var4, var5));
      }

      this.mechanicsItem.put(Long.parseLong(var1), var3);
   }

   public void setPosition(float var1, float var2, float var3) {
      this.setX(var1);
      this.setY(var2);
      this.setZ(var3);
   }

   private void updateTemperatureCheck() {
      int var1 = this.Moodles.getMoodleLevel(MoodleType.Hypothermia);
      if (this.hypothermiaCache == -1 || this.hypothermiaCache != var1) {
         if (var1 >= 3 && var1 > this.hypothermiaCache && this.isAsleep() && !this.ForceWakeUp) {
            this.forceAwake();
         }

         this.hypothermiaCache = var1;
      }

      int var2 = this.Moodles.getMoodleLevel(MoodleType.Hyperthermia);
      if (this.hyperthermiaCache == -1 || this.hyperthermiaCache != var2) {
         if (var2 >= 3 && var2 > this.hyperthermiaCache && this.isAsleep() && !this.ForceWakeUp) {
            this.forceAwake();
         }

         this.hyperthermiaCache = var2;
      }

   }

   public float getZombieRelevenceScore(IsoZombie var1) {
      if (var1.getCurrentSquare() == null) {
         return -10000.0F;
      } else {
         float var2 = 0.0F;
         if (var1.getCurrentSquare().getCanSee(this.PlayerIndex)) {
            var2 += 100.0F;
         } else if (var1.getCurrentSquare().isCouldSee(this.PlayerIndex)) {
            var2 += 10.0F;
         }

         if (var1.getCurrentSquare().getRoom() != null && this.current.getRoom() == null) {
            var2 -= 20.0F;
         }

         if (var1.getCurrentSquare().getRoom() == null && this.current.getRoom() != null) {
            var2 -= 20.0F;
         }

         if (var1.getCurrentSquare().getRoom() != this.current.getRoom()) {
            var2 -= 20.0F;
         }

         float var3 = var1.DistTo(this);
         var2 -= var3;
         if (var3 < 20.0F) {
            var2 += 300.0F;
         }

         if (var3 < 15.0F) {
            var2 += 300.0F;
         }

         if (var3 < 10.0F) {
            var2 += 1000.0F;
         }

         if (var1.getTargetAlpha() < 1.0F && var2 > 0.0F) {
            var2 *= var1.getTargetAlpha();
         }

         return var2;
      }
   }

   public BaseVisual getVisual() {
      return this.humanVisual;
   }

   public HumanVisual getHumanVisual() {
      return this.humanVisual;
   }

   public ItemVisuals getItemVisuals() {
      return this.itemVisuals;
   }

   public void getItemVisuals(ItemVisuals var1) {
      if (!this.bRemote) {
         this.getWornItems().getItemVisuals(var1);
      } else {
         var1.clear();
         var1.addAll(this.itemVisuals);
      }

   }

   public void dressInNamedOutfit(String var1) {
      this.getHumanVisual().dressInNamedOutfit(var1, this.itemVisuals);
      this.onClothingOutfitPreviewChanged();
   }

   public void dressInClothingItem(String var1) {
      this.getHumanVisual().dressInClothingItem(var1, this.itemVisuals);
      this.onClothingOutfitPreviewChanged();
   }

   private void onClothingOutfitPreviewChanged() {
      if (this.isLocalPlayer()) {
         this.getInventory().clear();
         this.wornItems.setFromItemVisuals(this.itemVisuals);
         this.wornItems.addItemsToItemContainer(this.getInventory());
         this.itemVisuals.clear();
         this.resetModel();
      }
   }

   public void actionStateChanged(ActionContext var1) {
      super.actionStateChanged(var1);
   }

   public Vector2 getLastAngle() {
      return this.lastAngle;
   }

   public void setLastAngle(Vector2 var1) {
      this.lastAngle.set(var1);
   }

   public int getDialogMood() {
      return this.DialogMood;
   }

   public void setDialogMood(int var1) {
      this.DialogMood = var1;
   }

   public int getPing() {
      return this.ping;
   }

   public void setPing(int var1) {
      this.ping = var1;
   }

   public IsoMovingObject getDragObject() {
      return this.DragObject;
   }

   public void setDragObject(IsoMovingObject var1) {
      this.DragObject = var1;
   }

   public float getAsleepTime() {
      return this.AsleepTime;
   }

   public void setAsleepTime(float var1) {
      this.AsleepTime = var1;
   }

   public Stack getSpottedList() {
      return this.spottedList;
   }

   public int getTicksSinceSeenZombie() {
      return this.TicksSinceSeenZombie;
   }

   public void setTicksSinceSeenZombie(int var1) {
      this.TicksSinceSeenZombie = var1;
   }

   public boolean isWaiting() {
      return this.Waiting;
   }

   public void setWaiting(boolean var1) {
      this.Waiting = var1;
   }

   public IsoSurvivor getDragCharacter() {
      return this.DragCharacter;
   }

   public void setDragCharacter(IsoSurvivor var1) {
      this.DragCharacter = var1;
   }

   public float getHeartDelay() {
      return this.heartDelay;
   }

   public void setHeartDelay(float var1) {
      this.heartDelay = var1;
   }

   public float getHeartDelayMax() {
      return this.heartDelayMax;
   }

   public void setHeartDelayMax(int var1) {
      this.heartDelayMax = (float)var1;
   }

   public double getHoursSurvived() {
      return this.HoursSurvived;
   }

   public void setHoursSurvived(double var1) {
      this.HoursSurvived = var1;
   }

   public float getDrunkOscilatorStepSin() {
      return this.DrunkOscilatorStepSin;
   }

   public void setDrunkOscilatorStepSin(float var1) {
      this.DrunkOscilatorStepSin = var1;
   }

   public float getDrunkOscilatorRateSin() {
      return this.DrunkOscilatorRateSin;
   }

   public void setDrunkOscilatorRateSin(float var1) {
      this.DrunkOscilatorRateSin = var1;
   }

   public float getDrunkOscilatorStepCos() {
      return this.DrunkOscilatorStepCos;
   }

   public void setDrunkOscilatorStepCos(float var1) {
      this.DrunkOscilatorStepCos = var1;
   }

   public float getDrunkOscilatorRateCos() {
      return this.DrunkOscilatorRateCos;
   }

   public void setDrunkOscilatorRateCos(float var1) {
      this.DrunkOscilatorRateCos = var1;
   }

   public float getDrunkOscilatorStepCos2() {
      return this.DrunkOscilatorStepCos2;
   }

   public void setDrunkOscilatorStepCos2(float var1) {
      this.DrunkOscilatorStepCos2 = var1;
   }

   public float getDrunkOscilatorRateCos2() {
      return this.DrunkOscilatorRateCos2;
   }

   public void setDrunkOscilatorRateCos2(float var1) {
      this.DrunkOscilatorRateCos2 = var1;
   }

   public float getDrunkSin() {
      return this.DrunkSin;
   }

   public void setDrunkSin(float var1) {
      this.DrunkSin = var1;
   }

   public float getDrunkCos() {
      return this.DrunkCos;
   }

   public void setDrunkCos(float var1) {
      this.DrunkCos = var1;
   }

   public float getDrunkCos2() {
      return this.DrunkCos2;
   }

   public void setDrunkCos2(float var1) {
      this.DrunkCos2 = var1;
   }

   public float getMinOscilatorRate() {
      return this.MinOscilatorRate;
   }

   public void setMinOscilatorRate(float var1) {
      this.MinOscilatorRate = var1;
   }

   public float getMaxOscilatorRate() {
      return this.MaxOscilatorRate;
   }

   public void setMaxOscilatorRate(float var1) {
      this.MaxOscilatorRate = var1;
   }

   public float getDesiredSinRate() {
      return this.DesiredSinRate;
   }

   public void setDesiredSinRate(float var1) {
      this.DesiredSinRate = var1;
   }

   public float getDesiredCosRate() {
      return this.DesiredCosRate;
   }

   public void setDesiredCosRate(float var1) {
      this.DesiredCosRate = var1;
   }

   public float getOscilatorChangeRate() {
      return this.OscilatorChangeRate;
   }

   public void setOscilatorChangeRate(float var1) {
      this.OscilatorChangeRate = var1;
   }

   public float getMaxWeightDelta() {
      return this.maxWeightDelta;
   }

   public void setMaxWeightDelta(float var1) {
      this.maxWeightDelta = var1;
   }

   public String getForname() {
      return this.Forname;
   }

   public void setForname(String var1) {
      this.Forname = var1;
   }

   public String getSurname() {
      return this.Surname;
   }

   public void setSurname(String var1) {
      this.Surname = var1;
   }

   public boolean isbChangeCharacterDebounce() {
      return this.bChangeCharacterDebounce;
   }

   public void setbChangeCharacterDebounce(boolean var1) {
      this.bChangeCharacterDebounce = var1;
   }

   public int getFollowID() {
      return this.followID;
   }

   public void setFollowID(int var1) {
      this.followID = var1;
   }

   public boolean isbSeenThisFrame() {
      return this.bSeenThisFrame;
   }

   public void setbSeenThisFrame(boolean var1) {
      this.bSeenThisFrame = var1;
   }

   public boolean isbCouldBeSeenThisFrame() {
      return this.bCouldBeSeenThisFrame;
   }

   public void setbCouldBeSeenThisFrame(boolean var1) {
      this.bCouldBeSeenThisFrame = var1;
   }

   public float getTimeSinceLastStab() {
      return this.timeSinceLastStab;
   }

   public void setTimeSinceLastStab(float var1) {
      this.timeSinceLastStab = var1;
   }

   public Stack getLastSpotted() {
      return this.LastSpotted;
   }

   public void setLastSpotted(Stack var1) {
      this.LastSpotted = var1;
   }

   public int getClearSpottedTimer() {
      return this.ClearSpottedTimer;
   }

   public void setClearSpottedTimer(int var1) {
      this.ClearSpottedTimer = var1;
   }

   public boolean IsRunning() {
      return this.isRunning();
   }

   public void InitSpriteParts() {
   }

   public boolean IsAiming() {
      return this.isAiming();
   }

   public String getTagPrefix() {
      return this.tagPrefix;
   }

   public void setTagPrefix(String var1) {
      this.tagPrefix = var1;
   }

   public ColorInfo getTagColor() {
      return this.tagColor;
   }

   public void setTagColor(ColorInfo var1) {
      this.tagColor.set(var1);
   }

   public Integer getTransactionID() {
      return this.transactionID;
   }

   public void setTransactionID(Integer var1) {
      this.transactionID = var1;
   }

   public String getDisplayName() {
      if (GameClient.bClient) {
         if (this.displayName == null || this.displayName.equals("")) {
            this.displayName = this.username;
         }
      } else if (!GameServer.bServer) {
         this.displayName = this.username;
      }

      return this.displayName;
   }

   public void setDisplayName(String var1) {
      this.displayName = var1;
   }

   public boolean isSeeNonPvpZone() {
      return this.seeNonPvpZone;
   }

   public void setSeeNonPvpZone(boolean var1) {
      this.seeNonPvpZone = var1;
   }

   public boolean isShowTag() {
      return this.showTag;
   }

   public void setShowTag(boolean var1) {
      this.showTag = var1;
   }

   public boolean isFactionPvp() {
      return this.factionPvp;
   }

   public void setFactionPvp(boolean var1) {
      this.factionPvp = var1;
   }

   public boolean isForceAim() {
      return this.forceAim;
   }

   public void setForceAim(boolean var1) {
      this.forceAim = var1;
   }

   public boolean toggleForceAim() {
      this.forceAim = !this.forceAim;
      return this.forceAim;
   }

   public boolean isForceSprint() {
      return this.forceSprint;
   }

   public void setForceSprint(boolean var1) {
      this.forceSprint = var1;
   }

   public boolean toggleForceSprint() {
      this.forceSprint = !this.forceSprint;
      return this.forceSprint;
   }

   public boolean isForceRun() {
      return this.forceRun;
   }

   public void setForceRun(boolean var1) {
      this.forceRun = var1;
   }

   public boolean toggleForceRun() {
      this.forceRun = !this.forceRun;
      return this.forceRun;
   }

   public boolean isDeaf() {
      return this.Traits.Deaf.isSet();
   }

   public boolean isForceOverrideAnim() {
      return this.forceOverrideAnim;
   }

   public void setForceOverrideAnim(boolean var1) {
      this.forceOverrideAnim = var1;
   }

   public Long getMechanicsItem(String var1) {
      return (Long)this.mechanicsItem.get(Long.parseLong(var1));
   }

   public boolean isWearingNightVisionGoggles() {
      return this.isWearingNightVisionGoggles;
   }

   public void setWearingNightVisionGoggles(boolean var1) {
      this.isWearingNightVisionGoggles = var1;
   }

   public void OnAnimEvent(AnimLayer var1, AnimEvent var2) {
      super.OnAnimEvent(var1, var2);
      if (!this.CharacterActions.isEmpty()) {
         BaseAction var3 = (BaseAction)this.CharacterActions.get(0);
         var3.OnAnimEvent(var2);
      }
   }

   public void onCullStateChanged(ModelManager var1, boolean var2) {
      super.onCullStateChanged(var1, var2);
      if (!var2) {
         DebugFileWatcher.instance.add(this.m_setClothingTriggerWatcher);
      } else {
         DebugFileWatcher.instance.remove(this.m_setClothingTriggerWatcher);
      }

   }

   public boolean isTimedActionInstant() {
      if (Core.bDebug && DebugOptions.instance.CheatTimedActionInstant.getValue()) {
         return true;
      } else {
         return (GameClient.bClient || GameServer.bServer) && !"None".equals(this.getAccessLevel());
      }
   }

   public boolean isSkeleton() {
      return false;
   }

   public void addWorldSoundUnlessInvisible(int var1, int var2, boolean var3) {
      if (!this.isGhostMode()) {
         super.addWorldSoundUnlessInvisible(var1, var2, var3);
      }
   }

   private void updateFootInjuries() {
      InventoryItem var1 = this.getWornItems().getItem("Shoes");
      if (var1 == null || var1.getCondition() <= 0) {
         if (this.getCurrentSquare() != null) {
            if (this.getCurrentSquare().getBrokenGlass() != null) {
               BodyPartType var2 = BodyPartType.FromIndex(Rand.Next(BodyPartType.ToIndex(BodyPartType.Foot_L), BodyPartType.ToIndex(BodyPartType.Foot_R) + 1));
               BodyPart var3 = this.getBodyDamage().getBodyPart(var2);
               var3.generateDeepShardWound();
            }

            byte var7 = 0;
            boolean var8 = false;
            if (this.getCurrentSquare().getZone() != null && (this.getCurrentSquare().getZone().getType().equals("Forest") || this.getCurrentSquare().getZone().getType().equals("DeepForest"))) {
               var8 = true;
            }

            IsoObject var4 = this.getCurrentSquare().getFloor();
            if (var4 != null && var4.getSprite() != null && var4.getSprite().getName() != null) {
               String var5 = var4.getSprite().getName();
               if (var5.contains("blends_natural_01") && var8) {
                  var7 = 2;
               } else if (!var5.contains("blends_natural_01") && this.getCurrentSquare().getBuilding() == null) {
                  var7 = 1;
               }
            }

            if (var7 != 0) {
               if (this.isWalking && !this.isRunning() && !this.isSprinting()) {
                  this.footInjuryTimer += var7;
               } else if (this.isRunning() && !this.isSprinting()) {
                  this.footInjuryTimer += var7 + 2;
               } else {
                  if (!this.isSprinting()) {
                     if (this.footInjuryTimer > 0 && Rand.Next(3) == 0) {
                        --this.footInjuryTimer;
                     }

                     return;
                  }

                  this.footInjuryTimer += var7 + 5;
               }

               if (Rand.Next(Rand.AdjustForFramerate(7000 - this.footInjuryTimer)) <= 0) {
                  this.footInjuryTimer = 0;
                  BodyPartType var9 = BodyPartType.FromIndex(Rand.Next(BodyPartType.ToIndex(BodyPartType.Foot_L), BodyPartType.ToIndex(BodyPartType.Foot_R) + 1));
                  BodyPart var6 = this.getBodyDamage().getBodyPart(var9);
                  if (var6.getScratchTime() > 30.0F) {
                     if (!var6.isCut()) {
                        var6.setCut(true);
                        var6.setCutTime(Rand.Next(1.0F, 3.0F));
                     } else {
                        var6.setCutTime(var6.getCutTime() + Rand.Next(1.0F, 3.0F));
                     }
                  } else {
                     if (!var6.scratched()) {
                        var6.setScratched(true, true);
                        var6.setScratchTime(Rand.Next(1.0F, 3.0F));
                     } else {
                        var6.setScratchTime(var6.getScratchTime() + Rand.Next(1.0F, 3.0F));
                     }

                     if (var6.getScratchTime() > 20.0F && var6.getBleedingTime() == 0.0F) {
                        var6.setBleedingTime(Rand.Next(3.0F, 10.0F));
                     }
                  }
               }

            }
         }
      }
   }

   public int getMoodleLevel(MoodleType var1) {
      return this.getMoodles().getMoodleLevel(var1);
   }

   public boolean isAttackStarted() {
      return this.attackStarted;
   }

   public boolean isBehaviourMoving() {
      return this.hasPath() || super.isBehaviourMoving();
   }

   public boolean isPlayerMoving() {
      return this.m_isPlayerMoving;
   }

   public float getTimedActionTimeModifier() {
      return this.getBodyDamage().getThermoregulator() != null ? this.getBodyDamage().getThermoregulator().getTimedActionTimeModifier() : 1.0F;
   }

   public boolean isLookingWhileInVehicle() {
      return this.getVehicle() != null && this.bLookingWhileInVehicle;
   }

   public void setInitiateAttack(boolean var1) {
      this.initiateAttack = var1;
   }

   public boolean isIgnoreInputsForDirection() {
      return this.ignoreInputsForDirection;
   }

   public void setIgnoreInputsForDirection(boolean var1) {
      this.ignoreInputsForDirection = var1;
   }

   public boolean isIgnoreContextKey() {
      return this.ignoreContextKey;
   }

   public void setIgnoreContextKey(boolean var1) {
      this.ignoreContextKey = var1;
   }

   public boolean isIgnoreAutoVault() {
      return this.ignoreAutoVault;
   }

   public void setIgnoreAutoVault(boolean var1) {
      this.ignoreAutoVault = var1;
   }

   public boolean isAllowSprint() {
      return this.allowSprint;
   }

   public void setAllowSprint(boolean var1) {
      this.allowSprint = var1;
   }

   public boolean isAllowRun() {
      return this.allowRun;
   }

   public void setAllowRun(boolean var1) {
      this.allowRun = var1;
   }

   public String getAttackType() {
      return this.attackType;
   }

   public void setAttackType(String var1) {
      this.attackType = var1;
   }

   static {
      m_isoPlayerTriggerWatcher = new PredicatedFileWatcher(ZomboidFileSystem.instance.getMessagingDirSub("Trigger_ResetIsoPlayerModel.xml"), IsoPlayer::onTrigger_ResetIsoPlayerModel);
      tempVector2_1 = new Vector2();
      tempVector2_2 = new Vector2();
      tempVector3f = new Vector3f();
      s_moveVars = new IsoPlayer.MoveVars();
      s_targetsProne = new ArrayList();
      s_targetsStanding = new ArrayList();
   }

   private static class s_performance {
      static final PerformanceProfileProbe postUpdate = new PerformanceProfileProbe("IsoPlayer.postUpdate");
      static final PerformanceProfileProbe highlightRangedTargets = new PerformanceProfileProbe("IsoPlayer.highlightRangedTargets");
      static final PerformanceProfileProbe update = new PerformanceProfileProbe("IsoPlayer.update");
   }

   private static class VehicleContainerData {
      ArrayList tempContainers;
      ArrayList containers;
      Stack freeContainers;

      private VehicleContainerData() {
         this.tempContainers = new ArrayList();
         this.containers = new ArrayList();
         this.freeContainers = new Stack();
      }

      // $FF: synthetic method
      VehicleContainerData(Object var1) {
         this();
      }
   }

   private static class VehicleContainer {
      BaseVehicle vehicle;
      int containerIndex;

      private VehicleContainer() {
      }

      public IsoPlayer.VehicleContainer set(BaseVehicle var1, int var2) {
         this.vehicle = var1;
         this.containerIndex = var2;
         return this;
      }

      public boolean equals(Object var1) {
         return var1 instanceof IsoPlayer.VehicleContainer && this.vehicle == ((IsoPlayer.VehicleContainer)var1).vehicle && this.containerIndex == ((IsoPlayer.VehicleContainer)var1).containerIndex;
      }

      // $FF: synthetic method
      VehicleContainer(Object var1) {
         this();
      }
   }

   private static class InputState {
      public boolean bMelee;
      public boolean isAttacking;
      public boolean bRunning;
      public boolean bSprinting;
      boolean isAiming;
      boolean isCharging;
      boolean isChargingLT;

      private InputState() {
      }

      // $FF: synthetic method
      InputState(Object var1) {
         this();
      }
   }

   static final class MoveVars {
      float moveX;
      float moveY;
      float strafeX;
      float strafeY;
      IsoDirections NewFacing;
   }
}
