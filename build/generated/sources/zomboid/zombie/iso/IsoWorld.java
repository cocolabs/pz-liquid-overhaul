package zombie.iso;

import fmod.fmod.FMODSoundEmitter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.krka.kahlua.vm.KahluaTable;
import zombie.CollisionManager;
import zombie.DebugFileWatcher;
import zombie.FliesSound;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.MapCollisionData;
import zombie.PersistentOutfits;
import zombie.PredicatedFileWatcher;
import zombie.ReanimatedPlayers;
import zombie.SandboxOptions;
import zombie.SharedDescriptors;
import zombie.SoundManager;
import zombie.SystemDisabler;
import zombie.VirtualZombieManager;
import zombie.WorldSoundManager;
import zombie.ZomboidFileSystem;
import zombie.ZomboidGlobals;
import zombie.Lua.LuaEventManager;
import zombie.Lua.LuaManager;
import zombie.ai.ZombieGroupManager;
import zombie.ai.states.FakeDeadZombieState;
import zombie.audio.BaseSoundEmitter;
import zombie.audio.DummySoundEmitter;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoSurvivor;
import zombie.characters.IsoZombie;
import zombie.characters.SurvivorDesc;
import zombie.characters.TriggerSetAnimationRecorderFile;
import zombie.characters.professions.ProfessionFactory;
import zombie.characters.traits.TraitFactory;
import zombie.core.Core;
import zombie.core.PerformanceSettings;
import zombie.core.SpriteRenderer;
import zombie.core.TilePropertyAliasMap;
import zombie.core.Translator;
import zombie.core.logger.ExceptionLogger;
import zombie.core.physics.WorldSimulation;
import zombie.core.profiling.PerformanceProfileProbe;
import zombie.core.skinnedmodel.DeadBodyAtlas;
import zombie.core.stash.StashSystem;
import zombie.core.textures.Texture;
import zombie.core.utils.OnceEvery;
import zombie.debug.DebugLog;
import zombie.debug.LineDrawer;
import zombie.erosion.ErosionGlobals;
import zombie.gameStates.GameLoadingState;
import zombie.globalObjects.GlobalObjectLookup;
import zombie.input.Mouse;
import zombie.inventory.ItemPickerJava;
import zombie.iso.SpriteDetails.IsoFlagType;
import zombie.iso.SpriteDetails.IsoObjectType;
import zombie.iso.areas.IsoBuilding;
import zombie.iso.areas.SafeHouse;
import zombie.iso.areas.isoregion.IsoRegion;
import zombie.iso.objects.IsoDeadBody;
import zombie.iso.objects.IsoFireManager;
import zombie.iso.objects.ObjectRenderEffects;
import zombie.iso.objects.RainManager;
import zombie.iso.sprite.IsoDirectionFrame;
import zombie.iso.sprite.IsoSprite;
import zombie.iso.sprite.IsoSpriteGrid;
import zombie.iso.sprite.IsoSpriteManager;
import zombie.iso.sprite.SkyBox;
import zombie.iso.weather.ClimateManager;
import zombie.iso.weather.WorldFlares;
import zombie.iso.weather.fog.ImprovedFog;
import zombie.iso.weather.fx.WeatherFxMask;
import zombie.network.BodyDamageSync;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.NetChecksum;
import zombie.network.ServerMap;
import zombie.network.ServerOptions;
import zombie.popman.ZombiePopulationManager;
import zombie.radio.ZomboidRadio;
import zombie.randomizedWorld.randomizedBuilding.RBBasic;
import zombie.randomizedWorld.randomizedBuilding.RBBurnt;
import zombie.randomizedWorld.randomizedBuilding.RBBurntCorpse;
import zombie.randomizedWorld.randomizedBuilding.RBBurntFireman;
import zombie.randomizedWorld.randomizedBuilding.RBKateAndBaldspot;
import zombie.randomizedWorld.randomizedBuilding.RBLooted;
import zombie.randomizedWorld.randomizedBuilding.RBOther;
import zombie.randomizedWorld.randomizedBuilding.RBSafehouse;
import zombie.randomizedWorld.randomizedBuilding.RBShopLooted;
import zombie.randomizedWorld.randomizedBuilding.RandomizedBuildingBase;
import zombie.randomizedWorld.randomizedVehicleStory.RVSAmbulanceCrash;
import zombie.randomizedWorld.randomizedVehicleStory.RVSBanditRoad;
import zombie.randomizedWorld.randomizedVehicleStory.RVSBurntCar;
import zombie.randomizedWorld.randomizedVehicleStory.RVSCarCrash;
import zombie.randomizedWorld.randomizedVehicleStory.RVSCarCrashCorpse;
import zombie.randomizedWorld.randomizedVehicleStory.RVSChangingTire;
import zombie.randomizedWorld.randomizedVehicleStory.RVSConstructionSite;
import zombie.randomizedWorld.randomizedVehicleStory.RVSFlippedCrash;
import zombie.randomizedWorld.randomizedVehicleStory.RVSPoliceBlockade;
import zombie.randomizedWorld.randomizedVehicleStory.RVSPoliceBlockadeShooting;
import zombie.randomizedWorld.randomizedVehicleStory.RVSTrailerCrash;
import zombie.randomizedWorld.randomizedVehicleStory.RVSUtilityVehicle;
import zombie.randomizedWorld.randomizedZoneStory.RZSBBQParty;
import zombie.randomizedWorld.randomizedZoneStory.RZSBeachParty;
import zombie.randomizedWorld.randomizedZoneStory.RZSBuryingCamp;
import zombie.randomizedWorld.randomizedZoneStory.RZSFishingTrip;
import zombie.randomizedWorld.randomizedZoneStory.RZSForestCamp;
import zombie.randomizedWorld.randomizedZoneStory.RZSForestCampEaten;
import zombie.randomizedWorld.randomizedZoneStory.RZSHunterCamp;
import zombie.randomizedWorld.randomizedZoneStory.RZSSexyTime;
import zombie.randomizedWorld.randomizedZoneStory.RZSTrapperCamp;
import zombie.savefile.ClientPlayerDB;
import zombie.savefile.PlayerDB;
import zombie.savefile.PlayerDBHelper;
import zombie.savefile.ServerPlayerDB;
import zombie.ui.TutorialManager;
import zombie.util.AddCoopPlayer;
import zombie.util.SharedStrings;
import zombie.vehicles.PolygonalMap2;
import zombie.vehicles.VehicleIDMap;
import zombie.vehicles.VehicleManager;
import zombie.vehicles.VehiclesDB2;

public final class IsoWorld {
   private String weather = "sunny";
   public final IsoMetaGrid MetaGrid = new IsoMetaGrid();
   private final ArrayList randomizedBuildingList = new ArrayList();
   private final ArrayList randomizedZoneList = new ArrayList();
   private final ArrayList randomizedVehicleStoryList = new ArrayList();
   private final RandomizedBuildingBase RBBasic = new RBBasic();
   private final HashMap spawnedZombieZone = new HashMap();
   private final HashMap allTiles = new HashMap();
   private float flashIsoCursorA = 1.0F;
   private boolean flashIsoCursorInc = false;
   public SkyBox sky = null;
   private static PredicatedFileWatcher m_setAnimationRecordingTriggerWatcher;
   private static boolean m_animationRecorderActive = false;
   private static boolean m_animationRecorderDiscard = false;
   private int timeSinceLastSurvivorInHorde = 4000;
   private int m_frameNo = 0;
   public final Helicopter helicopter = new Helicopter();
   public final ArrayList Characters = new ArrayList();
   public final ArrayDeque freeEmitters = new ArrayDeque();
   public final ArrayList currentEmitters = new ArrayList();
   public int x = 50;
   public int y = 50;
   public IsoCell CurrentCell;
   public static IsoWorld instance = new IsoWorld();
   public int TotalSurvivorsDead = 0;
   public int TotalSurvivorNights = 0;
   public int SurvivorSurvivalRecord = 0;
   public HashMap SurvivorDescriptors = new HashMap();
   public ArrayList AddCoopPlayers = new ArrayList();
   private static final IsoWorld.CompScoreToPlayer compScoreToPlayer = new IsoWorld.CompScoreToPlayer();
   static IsoWorld.CompDistToPlayer compDistToPlayer = new IsoWorld.CompDistToPlayer();
   public static String mapPath = "media/";
   public static boolean mapUseJar = true;
   boolean bLoaded = false;
   public static final HashMap PropertyValueMap = new HashMap();
   private static int WorldX = 0;
   private static int WorldY = 0;
   private SurvivorDesc luaDesc;
   private ArrayList luatraits;
   private int luaSpawnCellX = -1;
   private int luaSpawnCellY = -1;
   private int luaPosX = -1;
   private int luaPosY = -1;
   private int luaPosZ = -1;
   public static final int WorldVersion = 175;
   public static final int WorldVersion_Barricade = 87;
   public static final int WorldVersion_SandboxOptions = 88;
   public static final int WorldVersion_FliesSound = 121;
   public static final int WorldVersion_LootRespawn = 125;
   public static final int WorldVersion_OverlappingGenerators = 127;
   public static final int WorldVersion_ItemContainerIdenticalItems = 128;
   public static final int WorldVersion_VehicleSirenStartTime = 129;
   public static final int WorldVersion_CompostLastUpdated = 130;
   public static final int WorldVersion_DayLengthHours = 131;
   public static final int WorldVersion_LampOnPillar = 132;
   public static final int WorldVersion_AlarmClockRingSince = 134;
   public static final int WorldVersion_ClimateAdded = 135;
   public static final int WorldVersion_VehicleLightFocusing = 135;
   public static final int WorldVersion_GeneratorFuelFloat = 138;
   public static final int WorldVersion_InfectionTime = 142;
   public static final int WorldVersion_ClimateColors = 143;
   public static final int WorldVersion_BodyLocation = 144;
   public static final int WorldVersion_CharacterModelData = 145;
   public static final int WorldVersion_CharacterModelData2 = 146;
   public static final int WorldVersion_CharacterModelData3 = 147;
   public static final int WorldVersion_HumanVisualBlood = 148;
   public static final int WorldVersion_ItemContainerIdenticalItemsInt = 149;
   public static final int WorldVersion_PerkName = 152;
   public static final int WorldVersion_Thermos = 153;
   public static final int WorldVersion_AllPatches = 155;
   public static final int WorldVersion_ZombieRotStage = 156;
   public static final int WorldVersion_NewSandboxLootModifier = 157;
   public static final int WorldVersion_KateBobStorm = 158;
   public static final int WorldVersion_DeadBodyAngle = 159;
   public static final int WorldVersion_ChunkSpawnedRooms = 160;
   public static final int WorldVersion_DeathDragDown = 161;
   public static final int WorldVersion_CanUpgradePerk = 162;
   public static final int WorldVersion_ItemVisualFullType = 164;
   public static final int WorldVersion_VehicleBlood = 165;
   public static final int WorldVersion_DeadBodyZombieRotStage = 166;
   public static final int WorldVersion_Fitness = 167;
   public static final int WorldVersion_DeadBodyFakeDead = 168;
   public static final int WorldVersion_Fitness2 = 169;
   public static final int WorldVersion_NewFog = 170;
   public static final int WorldVersion_DeadBodyPersistentOutfitID = 171;
   public static final int WorldVersion_VehicleTowingID = 172;
   public static final int WorldVersion_VehicleJNITransform = 173;
   public static final int WorldVersion_VehicleTowAttachment = 174;
   public static final int WorldVersion_ChunkVehicles = 91;
   public static final int WorldVersion_PlayerVehicleSeat = 91;
   public static int SavedWorldVersion = -1;
   private boolean bDrawWorld = true;
   private final ArrayList zombieWithModel = new ArrayList();
   private final ArrayList zombieWithoutModel = new ArrayList();
   public static boolean NoZombies = false;
   public static int TotalWorldVersion = -1;
   public static int saveoffsetx;
   public static int saveoffsety;
   public boolean bDoChunkMapUpdate = true;
   private long emitterUpdateMS;
   public boolean emitterUpdate;

   public IsoMetaGrid getMetaGrid() {
      return this.MetaGrid;
   }

   public IsoMetaGrid.Zone registerZone(String var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      return this.MetaGrid.registerZone(var1, var2, var3, var4, var5, var6, var7);
   }

   public IsoMetaGrid.Zone registerZoneNoOverlap(String var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      return this.MetaGrid.registerZoneNoOverlap(var1, var2, var3, var4, var5, var6, var7);
   }

   public void removeZonesForLotDirectory(String var1) {
      this.MetaGrid.removeZonesForLotDirectory(var1);
   }

   public BaseSoundEmitter getFreeEmitter() {
      Object var1 = null;
      if (this.freeEmitters.isEmpty()) {
         var1 = Core.SoundDisabled ? new DummySoundEmitter() : new FMODSoundEmitter();
      } else {
         var1 = (BaseSoundEmitter)this.freeEmitters.pop();
      }

      this.currentEmitters.add(var1);
      return (BaseSoundEmitter)var1;
   }

   public BaseSoundEmitter getFreeEmitter(float var1, float var2, float var3) {
      BaseSoundEmitter var4 = this.getFreeEmitter();
      var4.setPos(var1, var2, var3);
      return var4;
   }

   public IsoMetaGrid.Zone registerVehiclesZone(String var1, String var2, int var3, int var4, int var5, int var6, int var7, KahluaTable var8) {
      return this.MetaGrid.registerVehiclesZone(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public IsoMetaGrid.Zone registerMannequinZone(String var1, String var2, int var3, int var4, int var5, int var6, int var7, KahluaTable var8) {
      return this.MetaGrid.registerMannequinZone(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public void registerWaterFlow(float var1, float var2, float var3, float var4) {
      IsoWaterFlow.addFlow(var1, var2, var3, var4);
   }

   public void registerWaterZone(float var1, float var2, float var3, float var4, float var5, float var6) {
      IsoWaterFlow.addZone(var1, var2, var3, var4, var5, var6);
   }

   public void checkVehiclesZones() {
      this.MetaGrid.checkVehiclesZones();
   }

   public void setGameMode(String var1) {
      Core.GameMode = var1;
      Core.bLastStand = "LastStand".equals(var1);
      Core.getInstance().setChallenge(false);
      Core.ChallengeID = null;
   }

   public String getGameMode() {
      return Core.GameMode;
   }

   public void setWorld(String var1) {
      Core.GameSaveWorld = var1.trim();
   }

   public void setMap(String var1) {
      Core.GameMap = var1;
   }

   public String getMap() {
      return Core.GameMap;
   }

   public void renderTerrain() {
   }

   public int getFrameNo() {
      return this.m_frameNo;
   }

   public IsoObject getItemFromXYZIndexBuffer(ByteBuffer var1) {
      int var2 = var1.getInt();
      int var3 = var1.getInt();
      int var4 = var1.getInt();
      IsoGridSquare var5 = this.CurrentCell.getGridSquare(var2, var3, var4);
      if (var5 == null) {
         return null;
      } else {
         byte var6 = var1.get();
         return var6 >= 0 && var6 < var5.getObjects().size() ? (IsoObject)var5.getObjects().get(var6) : null;
      }
   }

   public IsoWorld() {
      if (!GameServer.bServer) {
      }

   }

   private static void initMessaging() {
      if (m_setAnimationRecordingTriggerWatcher == null) {
         m_setAnimationRecordingTriggerWatcher = new PredicatedFileWatcher(ZomboidFileSystem.instance.getMessagingDirSub("Trigger_AnimationRecorder.xml"), TriggerSetAnimationRecorderFile.class, IsoWorld::onTrigger_setAnimationRecorderTriggerFile);
         DebugFileWatcher.instance.add(m_setAnimationRecordingTriggerWatcher);
      }

   }

   private static void onTrigger_setAnimationRecorderTriggerFile(TriggerSetAnimationRecorderFile var0) {
      m_animationRecorderActive = var0.isRecording;
      m_animationRecorderDiscard = var0.discard;
   }

   public static boolean isAnimRecorderActive() {
      return m_animationRecorderActive;
   }

   public static boolean isAnimRecorderDiscardTriggered() {
      return m_animationRecorderDiscard;
   }

   public IsoSurvivor CreateRandomSurvivor(SurvivorDesc var1, IsoGridSquare var2, IsoPlayer var3) {
      return null;
   }

   public void CreateSwarm(int var1, int var2, int var3, int var4, int var5) {
   }

   public void ForceKillAllZombies() {
      GameTime.getInstance().RemoveZombiesIndiscriminate(1000);
   }

   public static int readInt(RandomAccessFile var0) throws EOFException, IOException {
      int var1 = var0.read();
      int var2 = var0.read();
      int var3 = var0.read();
      int var4 = var0.read();
      if ((var1 | var2 | var3 | var4) < 0) {
         throw new EOFException();
      } else {
         return (var1 << 0) + (var2 << 8) + (var3 << 16) + (var4 << 24);
      }
   }

   public static String readString(RandomAccessFile var0) throws EOFException, IOException {
      String var1 = var0.readLine();
      return var1;
   }

   public static int readInt(InputStream var0) throws EOFException, IOException {
      int var1 = var0.read();
      int var2 = var0.read();
      int var3 = var0.read();
      int var4 = var0.read();
      if ((var1 | var2 | var3 | var4) < 0) {
         throw new EOFException();
      } else {
         return (var1 << 0) + (var2 << 8) + (var3 << 16) + (var4 << 24);
      }
   }

   public static String readString(InputStream var0) throws IOException {
      StringBuilder var1 = new StringBuilder();
      int var2 = -1;
      boolean var3 = false;

      while(!var3) {
         switch(var2 = var0.read()) {
         case -1:
         case 10:
            var3 = true;
            break;
         case 13:
            throw new IllegalStateException("\r\n unsupported");
         default:
            var1.append((char)var2);
         }
      }

      if (var2 == -1 && var1.length() == 0) {
         return null;
      } else {
         return var1.toString();
      }
   }

   public void LoadTileDefinitions(IsoSpriteManager var1, String var2, int var3) {
      DebugLog.log("tiledef: loading " + var2);

      try {
         FileInputStream var4 = new FileInputStream(var2);
         Throwable var5 = null;

         try {
            BufferedInputStream var6 = new BufferedInputStream(var4);
            Throwable var7 = null;

            try {
               int var8 = readInt((InputStream)var6);
               int var9 = readInt((InputStream)var6);
               int var10 = readInt((InputStream)var6);
               SharedStrings var11 = new SharedStrings();
               boolean var12 = false;
               boolean var13 = false;
               ArrayList var14 = new ArrayList();
               HashMap var15 = new HashMap();
               HashMap var16 = new HashMap();
               String[] var17 = new String[]{"N", "E", "S", "W"};

               for(int var18 = 0; var18 < var17.length; ++var18) {
                  var16.put(var17[var18], new ArrayList());
               }

               ArrayList var82 = new ArrayList();
               HashMap var19 = new HashMap();
               int var20 = 0;
               int var21 = 0;
               int var22 = 0;
               int var23 = 0;
               HashSet var24 = new HashSet();

               String var27;
               label2172:
               for(int var25 = 0; var25 < var10; ++var25) {
                  String var26 = readString((InputStream)var6);
                  var27 = var26.trim();
                  String var28 = readString((InputStream)var6);
                  int var29 = readInt((InputStream)var6);
                  int var30 = readInt((InputStream)var6);
                  int var31 = readInt((InputStream)var6);
                  int var32 = readInt((InputStream)var6);

                  IsoSprite var34;
                  int var40;
                  for(int var33 = 0; var33 < var32; ++var33) {
                     if (var3 < 2) {
                        var34 = var1.AddSprite(var27 + "_" + var33, var3 * 100 * 1000 + 10000 + var31 * 1000 + var33);
                     } else {
                        var34 = var1.AddSprite(var27 + "_" + var33, var3 * 512 * 512 + var31 * 512 + var33);
                     }

                     if (Core.bDebug) {
                        if (this.allTiles.containsKey(var27)) {
                           ((ArrayList)this.allTiles.get(var27)).add(var27 + "_" + var33);
                        } else {
                           ArrayList var35 = new ArrayList();
                           var35.add(var27 + "_" + var33);
                           this.allTiles.put(var27, var35);
                        }
                     }

                     var14.add(var34);
                     var34.setName(var27 + "_" + var33);
                     var34.tileSheetIndex = var33;
                     if (var34.name.contains("damaged") || var34.name.contains("trash_")) {
                        var34.attachedFloor = true;
                        var34.getProperties().Set("attachedFloor", "true");
                     }

                     if (var34.name.startsWith("f_bushes") && var33 <= 31) {
                        var34.isBush = true;
                        var34.attachedFloor = true;
                     }

                     int var87 = readInt((InputStream)var6);

                     for(int var36 = 0; var36 < var87; ++var36) {
                        var26 = readString((InputStream)var6);
                        String var37 = var26.trim();
                        var26 = readString((InputStream)var6);
                        String var38 = var26.trim();
                        IsoObjectType var39 = IsoObjectType.FromString(var37);
                        if (var39 == IsoObjectType.MAX) {
                           var37 = var11.get(var37);
                           if (var37.equals("firerequirement")) {
                              var34.firerequirement = Integer.parseInt(var38);
                           } else if (var37.equals("fireRequirement")) {
                              var34.firerequirement = Integer.parseInt(var38);
                           } else if (var37.equals("BurntTile")) {
                              var34.burntTile = var38;
                           } else if (var37.equals("ForceAmbient")) {
                              var34.forceAmbient = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("solidfloor")) {
                              var34.solidfloor = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("canBeRemoved")) {
                              var34.canBeRemoved = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("attachedFloor")) {
                              var34.attachedFloor = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("cutW")) {
                              var34.cutW = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("cutN")) {
                              var34.cutN = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("solid")) {
                              var34.solid = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("solidTrans")) {
                              var34.solidTrans = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("invisible")) {
                              var34.invisible = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("alwaysDraw")) {
                              var34.alwaysDraw = true;
                              var34.getProperties().Set(var37, var38);
                           } else if ("FloorHeight".equals(var37)) {
                              if ("OneThird".equals(var38)) {
                                 var34.getProperties().Set(IsoFlagType.FloorHeightOneThird);
                              } else if ("TwoThirds".equals(var38)) {
                                 var34.getProperties().Set(IsoFlagType.FloorHeightTwoThirds);
                              }
                           } else if (var37.equals("MoveWithWind")) {
                              var34.moveWithWind = true;
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("WindType")) {
                              var34.windType = Integer.parseInt(var38);
                              var34.getProperties().Set(var37, var38);
                           } else if (var37.equals("RenderLayer")) {
                              var34.getProperties().Set(var37, var38);
                              if ("Default".equals(var38)) {
                                 var34.renderLayer = 0;
                              } else if ("Floor".equals(var38)) {
                                 var34.renderLayer = 1;
                              }
                           } else if (var37.equals("TreatAsWallOrder")) {
                              var34.treatAsWallOrder = true;
                              var34.getProperties().Set(var37, var38);
                           } else {
                              var34.getProperties().Set(var37, var38);
                              if ("WindowN".equals(var37) || "WindowW".equals(var37)) {
                                 var34.getProperties().Set(var37, var38, false);
                              }
                           }
                        } else {
                           if (var34.getType() != IsoObjectType.doorW && var34.getType() != IsoObjectType.doorN || var39 != IsoObjectType.wall) {
                              var34.setType(var39);
                           }

                           if (var39 == IsoObjectType.doorW) {
                              var34.getProperties().Set(IsoFlagType.doorW);
                           } else if (var39 == IsoObjectType.doorN) {
                              var34.getProperties().Set(IsoFlagType.doorN);
                           }
                        }

                        if (var39 == IsoObjectType.tree) {
                           if (var34.name.equals("e_riverbirch_1_1")) {
                              var38 = "1";
                           }

                           var34.getProperties().Set("tree", var38);
                           var34.getProperties().UnSet(IsoFlagType.solid);
                           var34.getProperties().Set(IsoFlagType.blocksight);
                           var40 = Integer.parseInt(var38);
                           if (var27.startsWith("vegetation_trees")) {
                              var40 = 4;
                           }

                           if (var40 < 1) {
                              var40 = 1;
                           }

                           if (var40 > 4) {
                              var40 = 4;
                           }

                           if (var40 == 1 || var40 == 2) {
                              var34.getProperties().UnSet(IsoFlagType.blocksight);
                           }
                        }

                        if (var37.equals("interior") && var38.equals("false")) {
                           var34.getProperties().Set(IsoFlagType.exterior);
                        }

                        if (var37.equals("HoppableN")) {
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.canPathN);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                        }

                        if (var37.equals("HoppableW")) {
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.canPathW);
                           var34.getProperties().Set(IsoFlagType.transparentW);
                        }

                        if (var37.equals("WallN")) {
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.setType(IsoObjectType.wall);
                           var34.cutN = true;
                           var34.getProperties().Set("WallN", "", false);
                        }

                        if (var37.equals("CantClimb")) {
                           var34.getProperties().Set(IsoFlagType.CantClimb);
                        } else if (var37.equals("container")) {
                           var34.getProperties().Set(var37, var38, false);
                        } else if (var37.equals("WallNTrans")) {
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.setType(IsoObjectType.wall);
                           var34.cutN = true;
                           var34.getProperties().Set("WallNTrans", "", false);
                        } else if (var37.equals("WallW")) {
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                           var34.getProperties().Set("WallW", "", false);
                        } else if (var37.equals("windowN")) {
                           var34.getProperties().Set("WindowN", "WindowN");
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.getProperties().Set("WindowN", "WindowN", false);
                        } else if (var37.equals("windowW")) {
                           var34.getProperties().Set("WindowW", "WindowW");
                           var34.getProperties().Set(IsoFlagType.transparentW);
                           var34.getProperties().Set("WindowW", "WindowW", false);
                        } else if (var37.equals("cutW")) {
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.cutW = true;
                        } else if (var37.equals("cutN")) {
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.cutN = true;
                        } else if (var37.equals("WallWTrans")) {
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.transparentW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                           var34.getProperties().Set("WallWTrans", "", false);
                        } else if (var37.equals("DoorWallN")) {
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.cutN = true;
                           var34.getProperties().Set("DoorWallN", "", false);
                        } else if (var37.equals("DoorWallW")) {
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.cutW = true;
                           var34.getProperties().Set("DoorWallW", "", false);
                        } else if (var37.equals("WallNW")) {
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                           var34.cutN = true;
                           var34.getProperties().Set("WallNW", "", false);
                        } else if (var37.equals("WallNWTrans")) {
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.getProperties().Set(IsoFlagType.transparentW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                           var34.cutN = true;
                           var34.getProperties().Set("WallNWTrans", "", false);
                        } else if (var37.equals("WallSE")) {
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.getProperties().Set(IsoFlagType.WallSE);
                           var34.getProperties().Set("WallSE", "WallSE");
                           var34.cutW = true;
                        } else if (var37.equals("WindowW")) {
                           var34.getProperties().Set(IsoFlagType.canPathW);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.getProperties().Set(IsoFlagType.transparentW);
                           var34.setType(IsoObjectType.windowFW);
                           if (var34.getProperties().Is(IsoFlagType.HoppableW)) {
                              if (Core.bDebug) {
                                 DebugLog.log("ERROR: WindowW sprite shouldn't have HoppableW (" + var34.getName() + ")");
                              }

                              var34.getProperties().UnSet(IsoFlagType.HoppableW);
                           }

                           var34.cutW = true;
                        } else if (var37.equals("WindowN")) {
                           var34.getProperties().Set(IsoFlagType.canPathN);
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.setType(IsoObjectType.windowFN);
                           if (var34.getProperties().Is(IsoFlagType.HoppableN)) {
                              if (Core.bDebug) {
                                 DebugLog.log("ERROR: WindowN sprite shouldn't have HoppableN (" + var34.getName() + ")");
                              }

                              var34.getProperties().UnSet(IsoFlagType.HoppableN);
                           }

                           var34.cutN = true;
                        } else if (var37.equals("UnbreakableWindowW")) {
                           var34.getProperties().Set(IsoFlagType.canPathW);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.getProperties().Set(IsoFlagType.transparentW);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                        } else if (var37.equals("UnbreakableWindowN")) {
                           var34.getProperties().Set(IsoFlagType.canPathN);
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.setType(IsoObjectType.wall);
                           var34.cutN = true;
                        } else if (var37.equals("UnbreakableWindowNW")) {
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.transparentN);
                           var34.getProperties().Set(IsoFlagType.collideN);
                           var34.getProperties().Set(IsoFlagType.cutN);
                           var34.getProperties().Set(IsoFlagType.collideW);
                           var34.getProperties().Set(IsoFlagType.cutW);
                           var34.setType(IsoObjectType.wall);
                           var34.cutW = true;
                           var34.cutN = true;
                        } else if ("NoWallLighting".equals(var37)) {
                           var34.getProperties().Set(IsoFlagType.NoWallLighting);
                        } else if ("ForceAmbient".equals(var37)) {
                           var34.getProperties().Set(IsoFlagType.ForceAmbient);
                        }

                        if (var37.equals("name")) {
                           var34.setParentObjectName(var38);
                        }
                     }

                     if (var34.getProperties().Is("lightR") || var34.getProperties().Is("lightG") || var34.getProperties().Is("lightB")) {
                        if (!var34.getProperties().Is("lightR")) {
                           var34.getProperties().Set("lightR", "0");
                        }

                        if (!var34.getProperties().Is("lightG")) {
                           var34.getProperties().Set("lightG", "0");
                        }

                        if (!var34.getProperties().Is("lightB")) {
                           var34.getProperties().Set("lightB", "0");
                        }
                     }

                     var34.getProperties().CreateKeySet();
                     if (Core.bDebug && var34.getProperties().Is("SmashedTileOffset") && !var34.getProperties().Is("GlassRemovedOffset")) {
                        DebugLog.General.error("Window sprite has SmashedTileOffset but no GlassRemovedOffset (" + var34.getName() + ")");
                     }
                  }

                  var15.clear();
                  Iterator var85 = var14.iterator();

                  while(true) {
                     while(true) {
                        String var88;
                        do {
                           if (!var85.hasNext()) {
                              var85 = var15.entrySet().iterator();

                              while(true) {
                                 while(true) {
                                    while(true) {
                                       String var42;
                                       ArrayList var89;
                                       boolean var91;
                                       int var92;
                                       Iterator var94;
                                       boolean var95;
                                       IsoSprite var100;
                                       do {
                                          if (!var85.hasNext()) {
                                             var14.clear();
                                             continue label2172;
                                          }

                                          Entry var86 = (Entry)var85.next();
                                          var88 = (String)var86.getKey();
                                          if (!var19.containsKey(var27)) {
                                             var19.put(var27, new ArrayList());
                                          }

                                          if (!((ArrayList)var19.get(var27)).contains(var88)) {
                                             ((ArrayList)var19.get(var27)).add(var88);
                                          }

                                          var89 = (ArrayList)var86.getValue();
                                          if (var89.size() == 1) {
                                             DebugLog.log("MOVABLES: Object has only one face defined for group: (" + var88 + ") sheet = " + var27);
                                          }

                                          if (var89.size() == 3) {
                                             DebugLog.log("MOVABLES: Object only has 3 sprites, _might_ have a error in settings, group: (" + var88 + ") sheet = " + var27);
                                          }

                                          String[] var90 = var17;
                                          int var93 = var17.length;

                                          for(var92 = 0; var92 < var93; ++var92) {
                                             String var98 = var90[var92];
                                             ((ArrayList)var16.get(var98)).clear();
                                          }

                                          var91 = ((IsoSprite)var89.get(0)).getProperties().Is("SpriteGridPos") && !((IsoSprite)var89.get(0)).getProperties().Val("SpriteGridPos").equals("None");
                                          var95 = true;
                                          var94 = var89.iterator();

                                          while(var94.hasNext()) {
                                             var100 = (IsoSprite)var94.next();
                                             boolean var41 = var100.getProperties().Is("SpriteGridPos") && !var100.getProperties().Val("SpriteGridPos").equals("None");
                                             if (var91 != var41) {
                                                var95 = false;
                                                DebugLog.log("MOVABLES: Difference in SpriteGrid settings for members of group: (" + var88 + ") sheet = " + var27);
                                                break;
                                             }

                                             if (!var100.getProperties().Is("Facing")) {
                                                var95 = false;
                                             } else {
                                                var42 = var100.getProperties().Val("Facing");
                                                byte var43 = -1;
                                                switch(var42.hashCode()) {
                                                case 69:
                                                   if (var42.equals("E")) {
                                                      var43 = 1;
                                                   }
                                                   break;
                                                case 78:
                                                   if (var42.equals("N")) {
                                                      var43 = 0;
                                                   }
                                                   break;
                                                case 83:
                                                   if (var42.equals("S")) {
                                                      var43 = 2;
                                                   }
                                                   break;
                                                case 87:
                                                   if (var42.equals("W")) {
                                                      var43 = 3;
                                                   }
                                                }

                                                switch(var43) {
                                                case 0:
                                                   ((ArrayList)var16.get("N")).add(var100);
                                                   break;
                                                case 1:
                                                   ((ArrayList)var16.get("E")).add(var100);
                                                   break;
                                                case 2:
                                                   ((ArrayList)var16.get("S")).add(var100);
                                                   break;
                                                case 3:
                                                   ((ArrayList)var16.get("W")).add(var100);
                                                   break;
                                                default:
                                                   DebugLog.log("MOVABLES: Invalid face (" + var100.getProperties().Val("Facing") + ") for group: (" + var88 + ") sheet = " + var27);
                                                   var95 = false;
                                                }
                                             }

                                             if (!var95) {
                                                DebugLog.log("MOVABLES: Not all members have a valid face defined for group: (" + var88 + ") sheet = " + var27);
                                                break;
                                             }
                                          }
                                       } while(!var95);

                                       int var96;
                                       int var105;
                                       if (!var91) {
                                          if (var89.size() > 4) {
                                             DebugLog.log("MOVABLES: Object has too many faces defined for group: (" + var88 + ") sheet = " + var27);
                                          } else {
                                             String[] var97 = var17;
                                             var40 = var17.length;

                                             for(var96 = 0; var96 < var40; ++var96) {
                                                var42 = var97[var96];
                                                if (((ArrayList)var16.get(var42)).size() > 1) {
                                                   DebugLog.log("MOVABLES: " + var42 + " face defined more than once for group: (" + var88 + ") sheet = " + var27);
                                                   var95 = false;
                                                }
                                             }

                                             if (var95) {
                                                ++var22;
                                                var94 = var89.iterator();

                                                while(var94.hasNext()) {
                                                   var100 = (IsoSprite)var94.next();
                                                   String[] var103 = var17;
                                                   int var104 = var17.length;

                                                   for(var105 = 0; var105 < var104; ++var105) {
                                                      String var106 = var103[var105];
                                                      ArrayList var109 = (ArrayList)var16.get(var106);
                                                      if (var109.size() > 0 && var109.get(0) != var100) {
                                                         var100.getProperties().Set(var106 + "offset", Integer.toString(var14.indexOf(var109.get(0)) - var14.indexOf(var100)));
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       } else {
                                          var92 = 0;
                                          IsoSpriteGrid[] var102 = new IsoSpriteGrid[var17.length];

                                          int var44;
                                          IsoSprite var46;
                                          label2142:
                                          for(var96 = 0; var96 < var17.length; ++var96) {
                                             ArrayList var99 = (ArrayList)var16.get(var17[var96]);
                                             if (var99.size() > 0) {
                                                if (var92 == 0) {
                                                   var92 = var99.size();
                                                }

                                                if (var92 != var99.size()) {
                                                   DebugLog.log("MOVABLES: Sprite count mismatch for multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                                   var95 = false;
                                                   break;
                                                }

                                                var82.clear();
                                                var105 = -1;
                                                var44 = -1;
                                                Iterator var45 = var99.iterator();

                                                while(true) {
                                                   String var47;
                                                   String[] var48;
                                                   int var49;
                                                   int var50;
                                                   if (var45.hasNext()) {
                                                      var46 = (IsoSprite)var45.next();
                                                      var47 = var46.getProperties().Val("SpriteGridPos");
                                                      if (!var82.contains(var47)) {
                                                         var82.add(var47);
                                                         var48 = var47.split(",");
                                                         if (var48.length == 2) {
                                                            var49 = Integer.parseInt(var48[0]);
                                                            var50 = Integer.parseInt(var48[1]);
                                                            if (var49 > var105) {
                                                               var105 = var49;
                                                            }

                                                            if (var50 > var44) {
                                                               var44 = var50;
                                                            }
                                                            continue;
                                                         }

                                                         DebugLog.log("MOVABLES: SpriteGrid position error for multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                                         var95 = false;
                                                      } else {
                                                         DebugLog.log("MOVABLES: double SpriteGrid position (" + var47 + ") for multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                                         var95 = false;
                                                      }
                                                   }

                                                   if (var105 == -1 || var44 == -1 || (var105 + 1) * (var44 + 1) != var99.size()) {
                                                      DebugLog.log("MOVABLES: SpriteGrid dimensions error for multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                                      var95 = false;
                                                      break label2142;
                                                   }

                                                   if (!var95) {
                                                      break label2142;
                                                   }

                                                   var102[var96] = new IsoSpriteGrid(var105 + 1, var44 + 1);
                                                   var45 = var99.iterator();

                                                   while(var45.hasNext()) {
                                                      var46 = (IsoSprite)var45.next();
                                                      var47 = var46.getProperties().Val("SpriteGridPos");
                                                      var48 = var47.split(",");
                                                      var49 = Integer.parseInt(var48[0]);
                                                      var50 = Integer.parseInt(var48[1]);
                                                      var102[var96].setSprite(var49, var50, var46);
                                                   }

                                                   if (!var102[var96].validate()) {
                                                      DebugLog.log("MOVABLES: SpriteGrid didn't validate for multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                                      var95 = false;
                                                      break label2142;
                                                   }
                                                   break;
                                                }
                                             }
                                          }

                                          if (var95 && var92 != 0) {
                                             ++var23;

                                             for(var96 = 0; var96 < var17.length; ++var96) {
                                                IsoSpriteGrid var101 = var102[var96];
                                                if (var101 != null) {
                                                   IsoSprite[] var107 = var101.getSprites();
                                                   var44 = var107.length;

                                                   for(int var108 = 0; var108 < var44; ++var108) {
                                                      var46 = var107[var108];
                                                      var46.setSpriteGrid(var101);

                                                      for(int var110 = 0; var110 < var17.length; ++var110) {
                                                         if (var110 != var96 && var102[var110] != null) {
                                                            var46.getProperties().Set(var17[var110] + "offset", Integer.toString(var14.indexOf(var102[var110].getAnchorSprite()) - var14.indexOf(var46)));
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          } else {
                                             DebugLog.log("MOVABLES: Error in multi sprite movable, group: (" + var88 + ") sheet = " + var27);
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           var34 = (IsoSprite)var85.next();
                           if (var34.getProperties().Is("StopCar")) {
                              var34.setType(IsoObjectType.isMoveAbleObject);
                           }
                        } while(!var34.getProperties().Is("IsMoveAble"));

                        if (var34.getProperties().Is("CustomName") && !var34.getProperties().Val("CustomName").equals("")) {
                           ++var20;
                           if (var34.getProperties().Is("GroupName")) {
                              var88 = var34.getProperties().Val("GroupName") + " " + var34.getProperties().Val("CustomName");
                              if (!var15.containsKey(var88)) {
                                 var15.put(var88, new ArrayList());
                              }

                              ((ArrayList)var15.get(var88)).add(var34);
                              var24.add(var88);
                           } else {
                              if (!var19.containsKey(var27)) {
                                 var19.put(var27, new ArrayList());
                              }

                              if (!((ArrayList)var19.get(var27)).contains(var34.getProperties().Val("CustomName"))) {
                                 ((ArrayList)var19.get(var27)).add(var34.getProperties().Val("CustomName"));
                              }

                              ++var21;
                              var24.add(var34.getProperties().Val("CustomName"));
                           }
                        } else {
                           DebugLog.log("[IMPORTANT] MOVABLES: Object has no custom name defined: sheet = " + var27);
                        }
                     }
                  }
               }

               if (var13) {
                  ArrayList var83 = new ArrayList(var24);
                  Collections.sort(var83);
                  Iterator var84 = var83.iterator();

                  while(var84.hasNext()) {
                     var27 = (String)var84.next();
                     System.out.println(var27.replaceAll(" ", "_").replaceAll("-", "_").replaceAll("'", "").replaceAll("\\.", "") + " = \"" + var27 + "\",");
                  }
               }

               if (var12) {
                  try {
                     this.saveMovableStats(var19, var3, var21, var22, var23, var20);
                  } catch (Exception var76) {
                  }
               }
            } catch (Throwable var77) {
               var7 = var77;
               throw var77;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var75) {
                        var7.addSuppressed(var75);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } catch (Throwable var79) {
            var5 = var79;
            throw var79;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var74) {
                     var5.addSuppressed(var74);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Exception var81) {
         ExceptionLogger.logException(var81);
      }

   }

   private void GenerateTilePropertyLookupTables() {
      TilePropertyAliasMap.instance.Generate(PropertyValueMap);
      PropertyValueMap.clear();
   }

   public void LoadTileDefinitionsPropertyStrings(IsoSpriteManager var1, String var2, int var3) {
      DebugLog.log("tiledef: loading " + var2);
      if (!GameServer.bServer) {
         Thread.yield();
         Core.getInstance().DoFrameReady();
      }

      try {
         FileInputStream var4 = new FileInputStream(var2);
         Throwable var5 = null;

         try {
            BufferedInputStream var6 = new BufferedInputStream(var4);
            Throwable var7 = null;

            try {
               int var8 = readInt((InputStream)var6);
               int var9 = readInt((InputStream)var6);
               int var10 = readInt((InputStream)var6);
               SharedStrings var11 = new SharedStrings();

               for(int var12 = 0; var12 < var10; ++var12) {
                  String var13 = readString((InputStream)var6);
                  String var14 = var13.trim();
                  String var15 = readString((InputStream)var6);
                  int var16 = readInt((InputStream)var6);
                  int var17 = readInt((InputStream)var6);
                  int var18 = readInt((InputStream)var6);
                  int var19 = readInt((InputStream)var6);

                  for(int var20 = 0; var20 < var19; ++var20) {
                     int var21 = readInt((InputStream)var6);

                     for(int var22 = 0; var22 < var21; ++var22) {
                        var13 = readString((InputStream)var6);
                        String var23 = var13.trim();
                        var13 = readString((InputStream)var6);
                        String var24 = var13.trim();
                        IsoObjectType var25 = IsoObjectType.FromString(var23);
                        var23 = var11.get(var23);
                        ArrayList var26 = null;
                        if (PropertyValueMap.containsKey(var23)) {
                           var26 = (ArrayList)PropertyValueMap.get(var23);
                        } else {
                           var26 = new ArrayList();
                           PropertyValueMap.put(var23, var26);
                        }

                        if (!var26.contains(var24)) {
                           var26.add(var24);
                        }
                     }
                  }
               }
            } catch (Throwable var50) {
               var7 = var50;
               throw var50;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var49) {
                        var7.addSuppressed(var49);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } catch (Throwable var52) {
            var5 = var52;
            throw var52;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var48) {
                     var5.addSuppressed(var48);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Exception var54) {
         Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, (String)null, var54);
      }

   }

   private void SetCustomPropertyValues() {
      ((ArrayList)PropertyValueMap.get("WindowN")).add("WindowN");
      ((ArrayList)PropertyValueMap.get("WindowW")).add("WindowW");
      ((ArrayList)PropertyValueMap.get("DoorWallN")).add("DoorWallN");
      ((ArrayList)PropertyValueMap.get("DoorWallW")).add("DoorWallW");
      ((ArrayList)PropertyValueMap.get("WallSE")).add("WallSE");
      ArrayList var1 = new ArrayList();

      for(int var2 = -96; var2 <= 96; ++var2) {
         String var3 = Integer.toString(var2);
         var1.add(var3);
      }

      PropertyValueMap.put("Noffset", var1);
      PropertyValueMap.put("Soffset", var1);
      PropertyValueMap.put("Woffset", var1);
      PropertyValueMap.put("Eoffset", var1);
      ((ArrayList)PropertyValueMap.get("tree")).add("5");
      ((ArrayList)PropertyValueMap.get("tree")).add("6");
      ((ArrayList)PropertyValueMap.get("lightR")).add("0");
      ((ArrayList)PropertyValueMap.get("lightG")).add("0");
      ((ArrayList)PropertyValueMap.get("lightB")).add("0");
   }

   private void saveMovableStats(Map var1, int var2, int var3, int var4, int var5, int var6) throws FileNotFoundException, IOException {
      File var7 = new File(ZomboidFileSystem.instance.getCacheDir());
      if (var7.exists() && var7.isDirectory()) {
         File var8 = new File(ZomboidFileSystem.instance.getCacheDir() + File.separator + "movables_stats_" + var2 + ".txt");

         try {
            FileWriter var9 = new FileWriter(var8, false);
            Throwable var10 = null;

            try {
               var9.write("### Movable objects ###" + System.lineSeparator());
               var9.write("Single Face: " + var3 + System.lineSeparator());
               var9.write("Multi Face: " + var4 + System.lineSeparator());
               var9.write("Multi Face & Multi Sprite: " + var5 + System.lineSeparator());
               var9.write("Total objects : " + (var3 + var4 + var5) + System.lineSeparator());
               var9.write(" " + System.lineSeparator());
               var9.write("Total sprites : " + var6 + System.lineSeparator());
               var9.write(" " + System.lineSeparator());
               Iterator var11 = var1.entrySet().iterator();

               while(var11.hasNext()) {
                  Entry var12 = (Entry)var11.next();
                  var9.write((String)var12.getKey() + System.lineSeparator());
                  Iterator var13 = ((ArrayList)var12.getValue()).iterator();

                  while(var13.hasNext()) {
                     String var14 = (String)var13.next();
                     var9.write("\t" + var14 + System.lineSeparator());
                  }
               }
            } catch (Throwable var23) {
               var10 = var23;
               throw var23;
            } finally {
               if (var9 != null) {
                  if (var10 != null) {
                     try {
                        var9.close();
                     } catch (Throwable var22) {
                        var10.addSuppressed(var22);
                     }
                  } else {
                     var9.close();
                  }
               }

            }
         } catch (Exception var25) {
            var25.printStackTrace();
         }
      }

   }

   private void addJumboTreeTileset(IsoSpriteManager var1, int var2, String var3, int var4, int var5, int var6) {
      byte var7 = 2;

      for(int var8 = 0; var8 < var5; ++var8) {
         for(int var9 = 0; var9 < var7; ++var9) {
            String var10 = "e_" + var3 + "JUMBO_1";
            int var11 = var8 * var7 + var9;
            IsoSprite var12 = var1.AddSprite(var10 + "_" + var11, var2 * 512 * 512 + var4 * 512 + var11);

            assert GameServer.bServer || !var12.CurrentAnim.Frames.isEmpty() && ((IsoDirectionFrame)var12.CurrentAnim.Frames.get(0)).getTexture(IsoDirections.N) != null;

            var12.setName(var10 + "_" + var11);
            var12.setType(IsoObjectType.tree);
            var12.getProperties().Set("tree", var9 == 0 ? "5" : "6");
            var12.getProperties().UnSet(IsoFlagType.solid);
            var12.getProperties().Set(IsoFlagType.blocksight);
            var12.getProperties().CreateKeySet();
            var12.moveWithWind = true;
            var12.windType = var6;
         }
      }

   }

   private void JumboTreeDefinitions(IsoSpriteManager var1, int var2) {
      this.addJumboTreeTileset(var1, var2, "americanholly", 1, 2, 3);
      this.addJumboTreeTileset(var1, var2, "americanlinden", 2, 6, 2);
      this.addJumboTreeTileset(var1, var2, "canadianhemlock", 3, 2, 3);
      this.addJumboTreeTileset(var1, var2, "carolinasilverbell", 4, 6, 1);
      this.addJumboTreeTileset(var1, var2, "cockspurhawthorn", 5, 6, 2);
      this.addJumboTreeTileset(var1, var2, "dogwood", 6, 6, 2);
      this.addJumboTreeTileset(var1, var2, "easternredbud", 7, 6, 2);
      this.addJumboTreeTileset(var1, var2, "redmaple", 8, 6, 2);
      this.addJumboTreeTileset(var1, var2, "riverbirch", 9, 6, 1);
      this.addJumboTreeTileset(var1, var2, "virginiapine", 10, 2, 1);
      this.addJumboTreeTileset(var1, var2, "yellowwood", 11, 6, 2);
      byte var8 = 12;
      byte var9 = 0;
      IsoSprite var10 = var1.AddSprite("jumbo_tree_01_" + var9, var2 * 512 * 512 + var8 * 512 + var9);
      var10.setName("jumbo_tree_01_" + var9);
      var10.setType(IsoObjectType.tree);
      var10.getProperties().Set("tree", "4");
      var10.getProperties().UnSet(IsoFlagType.solid);
      var10.getProperties().Set(IsoFlagType.blocksight);
   }

   public boolean LoadPlayerForInfo() throws FileNotFoundException, IOException {
      if (GameClient.bClient) {
         return ClientPlayerDB.getInstance().loadNetworkPlayerInfo(1);
      } else {
         File var1 = ZomboidFileSystem.instance.getFileInCurrentSave("map_p.bin");
         if (!var1.exists()) {
            PlayerDB.getInstance().importPlayersFromVehiclesDB();
            return PlayerDB.getInstance().loadLocalPlayerInfo(1);
         } else {
            FileInputStream var2 = new FileInputStream(var1);
            BufferedInputStream var3 = new BufferedInputStream(var2);
            synchronized(SliceY.SliceBufferLock) {
               SliceY.SliceBuffer.clear();
               int var5 = var3.read(SliceY.SliceBuffer.array());
               SliceY.SliceBuffer.limit(var5);
               var3.close();
               byte var6 = SliceY.SliceBuffer.get();
               byte var7 = SliceY.SliceBuffer.get();
               byte var8 = SliceY.SliceBuffer.get();
               byte var9 = SliceY.SliceBuffer.get();
               int var10 = -1;
               if (var6 == 80 && var7 == 76 && var8 == 89 && var9 == 82) {
                  var10 = SliceY.SliceBuffer.getInt();
               } else {
                  SliceY.SliceBuffer.rewind();
               }

               if (var10 >= 69) {
                  String var11 = GameWindow.ReadString(SliceY.SliceBuffer);
                  if (GameClient.bClient && var10 < 71) {
                     var11 = ServerOptions.instance.ServerPlayerID.getValue();
                  }

                  if (GameClient.bClient && !IsoPlayer.isServerPlayerIDValid(var11)) {
                     GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_ServerPlayerIDMismatch");
                     GameLoadingState.playerWrongIP = true;
                     return false;
                  }
               } else if (GameClient.bClient && ServerOptions.instance.ServerPlayerID.getValue().isEmpty()) {
                  GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_ServerPlayerIDMissing");
                  GameLoadingState.playerWrongIP = true;
                  return false;
               }

               WorldX = SliceY.SliceBuffer.getInt();
               WorldY = SliceY.SliceBuffer.getInt();
               IsoChunkMap.WorldXA = SliceY.SliceBuffer.getInt();
               IsoChunkMap.WorldYA = SliceY.SliceBuffer.getInt();
               IsoChunkMap.WorldZA = SliceY.SliceBuffer.getInt();
               IsoChunkMap.WorldXA += 300 * saveoffsetx;
               IsoChunkMap.WorldYA += 300 * saveoffsety;
               IsoChunkMap.SWorldX[0] = WorldX;
               IsoChunkMap.SWorldY[0] = WorldY;
               int[] var10000 = IsoChunkMap.SWorldX;
               var10000[0] += 30 * saveoffsetx;
               var10000 = IsoChunkMap.SWorldY;
               var10000[0] += 30 * saveoffsety;
               return true;
            }
         }
      }
   }

   public void init() throws FileNotFoundException, IOException {
      if (!Core.bTutorial) {
         this.randomizedBuildingList.add(new RBSafehouse());
         this.randomizedBuildingList.add(new RBBurnt());
         this.randomizedBuildingList.add(new RBOther());
         this.randomizedBuildingList.add(new RBLooted());
         this.randomizedBuildingList.add(new RBBurntFireman());
         this.randomizedBuildingList.add(new RBBurntCorpse());
         this.randomizedBuildingList.add(new RBShopLooted());
         this.randomizedBuildingList.add(new RBKateAndBaldspot());
         this.randomizedVehicleStoryList.add(new RVSUtilityVehicle());
         this.randomizedVehicleStoryList.add(new RVSConstructionSite());
         this.randomizedVehicleStoryList.add(new RVSBurntCar());
         this.randomizedVehicleStoryList.add(new RVSPoliceBlockadeShooting());
         this.randomizedVehicleStoryList.add(new RVSPoliceBlockade());
         this.randomizedVehicleStoryList.add(new RVSCarCrash());
         this.randomizedVehicleStoryList.add(new RVSAmbulanceCrash());
         this.randomizedVehicleStoryList.add(new RVSCarCrashCorpse());
         this.randomizedVehicleStoryList.add(new RVSChangingTire());
         this.randomizedVehicleStoryList.add(new RVSFlippedCrash());
         this.randomizedVehicleStoryList.add(new RVSBanditRoad());
         this.randomizedVehicleStoryList.add(new RVSTrailerCrash());
         this.randomizedZoneList.add(new RZSForestCamp());
         this.randomizedZoneList.add(new RZSForestCampEaten());
         this.randomizedZoneList.add(new RZSBuryingCamp());
         this.randomizedZoneList.add(new RZSBeachParty());
         this.randomizedZoneList.add(new RZSFishingTrip());
         this.randomizedZoneList.add(new RZSBBQParty());
         this.randomizedZoneList.add(new RZSHunterCamp());
         this.randomizedZoneList.add(new RZSSexyTime());
         this.randomizedZoneList.add(new RZSTrapperCamp());
      }

      zombie.randomizedWorld.randomizedBuilding.RBBasic.getUniqueRDSSpawned().clear();
      if (!GameClient.bClient && !GameServer.bServer) {
         BodyDamageSync.instance = null;
      } else {
         BodyDamageSync.instance = new BodyDamageSync();
      }

      if (GameServer.bServer) {
         Core.GameSaveWorld = GameServer.ServerName;
         LuaManager.GlobalObject.createWorld(Core.GameSaveWorld);
      }

      SavedWorldVersion = -1;
      File var1 = ZomboidFileSystem.instance.getFileInCurrentSave("map_ver.bin");
      if (var1.exists()) {
         FileInputStream var2 = new FileInputStream(var1);
         DataInputStream var3 = new DataInputStream(var2);
         SavedWorldVersion = var3.readInt();
         if (SavedWorldVersion >= 25) {
            String var4 = GameWindow.ReadString(var3);
            if (!GameClient.bClient) {
               Core.GameMap = var4;
            }
         }

         if (SavedWorldVersion >= 74) {
            this.setDifficulty(GameWindow.ReadString(var3));
         }

         var3.close();
      }

      if (!GameServer.bServer || System.getProperty("softreset") == null) {
         this.MetaGrid.CreateStep1();
      }

      LuaEventManager.triggerEvent("OnPreDistributionMerge");
      LuaEventManager.triggerEvent("OnDistributionMerge");
      LuaEventManager.triggerEvent("OnPostDistributionMerge");
      ItemPickerJava.Parse();
      VehiclesDB2.instance.init();
      LuaEventManager.triggerEvent("OnInitWorld");
      if (!GameClient.bClient) {
         SandboxOptions.instance.load();
      }

      ZomboidGlobals.toLua();
      ItemPickerJava.InitSandboxLootSettings();
      this.SurvivorDescriptors.clear();
      IsoSpriteManager.instance.Dispose();
      if (GameClient.bClient && ServerOptions.instance.DoLuaChecksum.getValue()) {
         try {
            NetChecksum.comparer.beginCompare();
            GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_Checksum");
            long var27 = System.currentTimeMillis();
            long var30 = var27;

            while(!GameClient.checksumValid) {
               if (GameWindow.bServerDisconnected) {
                  return;
               }

               if (System.currentTimeMillis() > var27 + 8000L) {
                  DebugLog.log("checksum: timed out waiting for the server to respond");
                  GameClient.connection.forceDisconnect();
                  GameWindow.bServerDisconnected = true;
                  GameWindow.kickReason = Translator.getText("UI_GameLoad_TimedOut");
                  return;
               }

               if (System.currentTimeMillis() > var30 + 1000L) {
                  DebugLog.log("checksum: waited one second");
                  var30 += 1000L;
               }

               NetChecksum.comparer.update();
               if (GameClient.checksumValid) {
                  break;
               }

               Thread.sleep(100L);
            }
         } catch (Exception var26) {
            var26.printStackTrace();
         }
      }

      GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_LoadTileDef");
      IsoSpriteManager var28 = IsoSpriteManager.instance;
      this.LoadTileDefinitionsPropertyStrings(var28, "media/tiledefinitions.tiles", 0);
      this.LoadTileDefinitionsPropertyStrings(var28, "media/newtiledefinitions.tiles", 1);
      this.LoadTileDefinitionsPropertyStrings(var28, "media/tiledefinitions_erosion.tiles", 2);
      this.LoadTileDefinitionsPropertyStrings(var28, "media/tiledefinitions_apcom.tiles", 3);
      this.LoadTileDefinitionsPropertyStrings(var28, "media/tiledefinitions_overlays.tiles", 4);
      ZomboidFileSystem.instance.loadModTileDefPropertyStrings();
      this.SetCustomPropertyValues();
      this.GenerateTilePropertyLookupTables();
      this.LoadTileDefinitions(var28, "media/tiledefinitions.tiles", 0);
      this.LoadTileDefinitions(var28, "media/newtiledefinitions.tiles", 1);
      this.LoadTileDefinitions(var28, "media/tiledefinitions_erosion.tiles", 2);
      this.LoadTileDefinitions(var28, "media/tiledefinitions_apcom.tiles", 3);
      this.LoadTileDefinitions(var28, "media/tiledefinitions_overlays.tiles", 4);
      this.JumboTreeDefinitions(var28, 5);
      ZomboidFileSystem.instance.loadModTileDefs();
      GameLoadingState.GameLoadingString = "";
      var28.AddSprite("media/ui/missing-tile.png");
      LuaEventManager.triggerEvent("OnLoadedTileDefinitions", var28);
      String var29 = "media/newtiledefinitions_175.tiles";
      File var31 = new File(var29);
      if (!var31.exists()) {
         var29 = "media/newtiledefinitions.tiles";
      }

      if (GameServer.bServer && System.getProperty("softreset") != null) {
         WorldConverter.instance.softreset(var28);
      }

      try {
         WeatherFxMask.init();
      } catch (Exception var24) {
         System.out.print(var24.getStackTrace());
      }

      IsoRegion.init();
      ObjectRenderEffects.init();
      WorldConverter.instance.convert(Core.GameSaveWorld, var28);
      if (!GameLoadingState.build23Stop) {
         SandboxOptions.instance.handleOldZombiesFile2();
         GameTime.getInstance().init();
         GameTime.getInstance().load();
         ZomboidRadio.getInstance().Init(SavedWorldVersion);
         if (GameServer.bServer && Core.getInstance().getPoisonousBerry() == null) {
            Core.getInstance().initPoisonousBerry();
         }

         if (GameServer.bServer && Core.getInstance().getPoisonousMushroom() == null) {
            Core.getInstance().initPoisonousMushroom();
         }

         ErosionGlobals.Boot(var28);
         WorldMarkers.instance.init();
         if (GameServer.bServer) {
            SharedDescriptors.initSharedDescriptors();
         }

         PersistentOutfits.instance.init();
         VirtualZombieManager.instance.init();
         VehicleIDMap.instance.Reset();
         VehicleManager.instance = new VehicleManager();
         GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_InitMap");
         this.MetaGrid.CreateStep2();
         ClimateManager.getInstance().init(this.MetaGrid);
         SafeHouse.init();
         if (!GameClient.bClient) {
            StashSystem.init();
         }

         LuaEventManager.triggerEvent("OnLoadMapZones");
         this.MetaGrid.load();
         this.MetaGrid.loadZones();
         this.MetaGrid.processZones();
         if (GameServer.bServer) {
            ServerMap.instance.init(this.MetaGrid);
         }

         boolean var32 = false;
         boolean var5 = false;
         if (GameClient.bClient) {
            if (ClientPlayerDB.getInstance().clientLoadNetworkPlayer() && ClientPlayerDB.getInstance().isAliveMainNetworkPlayer()) {
               var5 = true;
            }
         } else {
            var5 = PlayerDBHelper.isPlayerAlive(ZomboidFileSystem.instance.getGameModeCacheDir() + Core.GameSaveWorld, 1);
         }

         if (GameServer.bServer) {
            ServerPlayerDB.setAllow(true);
         }

         if (!GameClient.bClient && !GameServer.bServer) {
            PlayerDB.setAllow(true);
         }

         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;
         SafeHouse var10;
         int var34;
         if (var5) {
            var32 = true;
            if (!this.LoadPlayerForInfo()) {
               return;
            }

            WorldX = IsoChunkMap.SWorldX[IsoPlayer.getPlayerIndex()];
            WorldY = IsoChunkMap.SWorldY[IsoPlayer.getPlayerIndex()];
            var34 = IsoChunkMap.WorldXA;
            int var36 = IsoChunkMap.WorldYA;
            int var38 = IsoChunkMap.WorldZA;
         } else {
            var32 = false;
            if (GameClient.bClient && !ServerOptions.instance.SpawnPoint.getValue().isEmpty()) {
               String[] var9 = ServerOptions.instance.SpawnPoint.getValue().split(",");
               if (var9.length == 3) {
                  try {
                     IsoChunkMap.MPWorldXA = new Integer(var9[0].trim());
                     IsoChunkMap.MPWorldYA = new Integer(var9[1].trim());
                     IsoChunkMap.MPWorldZA = new Integer(var9[2].trim());
                  } catch (NumberFormatException var23) {
                     DebugLog.log("ERROR: SpawnPoint must be x,y,z, got \"" + ServerOptions.instance.SpawnPoint.getValue() + "\"");
                     IsoChunkMap.MPWorldXA = 0;
                     IsoChunkMap.MPWorldYA = 0;
                     IsoChunkMap.MPWorldZA = 0;
                  }
               } else {
                  DebugLog.log("ERROR: SpawnPoint must be x,y,z, got \"" + ServerOptions.instance.SpawnPoint.getValue() + "\"");
               }
            }

            if (this.getLuaSpawnCellX() < 0 || GameClient.bClient && (IsoChunkMap.MPWorldXA != 0 || IsoChunkMap.MPWorldYA != 0)) {
               if (GameClient.bClient) {
                  IsoChunkMap.WorldXA = IsoChunkMap.MPWorldXA;
                  IsoChunkMap.WorldYA = IsoChunkMap.MPWorldYA;
                  IsoChunkMap.WorldZA = IsoChunkMap.MPWorldZA;
                  WorldX = IsoChunkMap.WorldXA / 10;
                  WorldY = IsoChunkMap.WorldYA / 10;
               }
            } else {
               IsoChunkMap.WorldXA = this.getLuaPosX() + 300 * this.getLuaSpawnCellX();
               IsoChunkMap.WorldYA = this.getLuaPosY() + 300 * this.getLuaSpawnCellY();
               IsoChunkMap.WorldZA = this.getLuaPosZ();
               if (GameClient.bClient && ServerOptions.instance.SafehouseAllowRespawn.getValue()) {
                  for(int var39 = 0; var39 < SafeHouse.getSafehouseList().size(); ++var39) {
                     var10 = (SafeHouse)SafeHouse.getSafehouseList().get(var39);
                     if (var10.getPlayers().contains(GameClient.username)) {
                        IsoChunkMap.WorldXA = var10.getX() + var10.getH() / 2;
                        IsoChunkMap.WorldYA = var10.getY() + var10.getW() / 2;
                        IsoChunkMap.WorldZA = 0;
                     }
                  }
               }

               WorldX = IsoChunkMap.WorldXA / 10;
               WorldY = IsoChunkMap.WorldYA / 10;
            }
         }

         Core.getInstance();
         KahluaTable var40 = (KahluaTable)LuaManager.env.rawget("selectedDebugScenario");
         int var11;
         int var12;
         int var13;
         if (var40 != null) {
            KahluaTable var41 = (KahluaTable)var40.rawget("startLoc");
            var11 = ((Double)var41.rawget("x")).intValue();
            var12 = ((Double)var41.rawget("y")).intValue();
            var13 = ((Double)var41.rawget("z")).intValue();
            IsoChunkMap.WorldXA = var11;
            IsoChunkMap.WorldYA = var12;
            IsoChunkMap.WorldZA = var13;
            WorldX = IsoChunkMap.WorldXA / 10;
            WorldY = IsoChunkMap.WorldYA / 10;
         }

         MapCollisionData.instance.init(instance.getMetaGrid());
         ZombiePopulationManager.instance.init(instance.getMetaGrid());
         PolygonalMap2.instance.init(instance.getMetaGrid());
         GlobalObjectLookup.init(instance.getMetaGrid());
         if (!GameServer.bServer) {
            SpawnPoints.instance.initSinglePlayer();
         }

         WorldStreamer.instance.create();
         this.CurrentCell = CellLoader.LoadCellBinaryChunk(var28, WorldX, WorldY);
         ClimateManager.getInstance().postCellLoadSetSnow();
         GameLoadingState.GameLoadingString = Translator.getText("IGUI_MP_LoadWorld");
         MapCollisionData.instance.start();

         while(WorldStreamer.instance.isBusy()) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var22) {
               var22.printStackTrace();
            }
         }

         ArrayList var42 = new ArrayList();
         var42.addAll(IsoChunk.loadGridSquare);
         Iterator var43 = var42.iterator();

         while(var43.hasNext()) {
            IsoChunk var44 = (IsoChunk)var43.next();
            this.CurrentCell.ChunkMap[0].setChunkDirect(var44, false);
         }

         IsoChunk.bDoServerRequests = true;
         if (var32 && SystemDisabler.doPlayerCreation) {
            this.CurrentCell.LoadPlayer(SavedWorldVersion);
            if (GameClient.bClient) {
               IsoPlayer.getInstance().setUsername(GameClient.username);
            }
         } else {
            var10 = null;
            if (IsoPlayer.numPlayers == 0) {
               IsoPlayer.numPlayers = 1;
            }

            var11 = IsoChunkMap.WorldXA;
            var12 = IsoChunkMap.WorldYA;
            var13 = IsoChunkMap.WorldZA;
            if (GameClient.bClient && !ServerOptions.instance.SpawnPoint.getValue().isEmpty()) {
               String[] var14 = ServerOptions.instance.SpawnPoint.getValue().split(",");
               if (var14.length != 3) {
                  DebugLog.log("ERROR: SpawnPoint must be x,y,z, got \"" + ServerOptions.instance.SpawnPoint.getValue() + "\"");
               } else {
                  try {
                     int var15 = new Integer(var14[0].trim());
                     int var16 = new Integer(var14[1].trim());
                     int var17 = new Integer(var14[2].trim());
                     if (GameClient.bClient && ServerOptions.instance.SafehouseAllowRespawn.getValue()) {
                        for(int var18 = 0; var18 < SafeHouse.getSafehouseList().size(); ++var18) {
                           SafeHouse var19 = (SafeHouse)SafeHouse.getSafehouseList().get(var18);
                           if (var19.getPlayers().contains(GameClient.username)) {
                              var15 = var19.getX() + var19.getH() / 2;
                              var16 = var19.getY() + var19.getW() / 2;
                              var17 = 0;
                           }
                        }
                     }

                     if (this.CurrentCell.getGridSquare(var15, var16, var17) != null) {
                        var11 = var15;
                        var12 = var16;
                        var13 = var17;
                     }
                  } catch (NumberFormatException var25) {
                     DebugLog.log("ERROR: SpawnPoint must be x,y,z, got \"" + ServerOptions.instance.SpawnPoint.getValue() + "\"");
                  }
               }
            }

            IsoGridSquare var45 = this.CurrentCell.getGridSquare(var11, var12, var13);
            if (SystemDisabler.doPlayerCreation && !GameServer.bServer) {
               if (var45 != null && var45.isFree(false) && var45.getRoom() != null) {
                  IsoGridSquare var46 = var45;
                  var45 = var45.getRoom().getFreeTile();
                  if (var45 == null) {
                     var45 = var46;
                  }
               }

               IsoPlayer var47 = null;
               if (this.getLuaPlayerDesc() != null) {
                  if (GameClient.bClient && ServerOptions.instance.SafehouseAllowRespawn.getValue()) {
                     var45 = this.CurrentCell.getGridSquare(IsoChunkMap.WorldXA, IsoChunkMap.WorldYA, IsoChunkMap.WorldZA);
                     if (var45 != null && var45.isFree(false) && var45.getRoom() != null) {
                        IsoGridSquare var48 = var45;
                        var45 = var45.getRoom().getFreeTile();
                        if (var45 == null) {
                           var45 = var48;
                        }
                     }
                  }

                  if (var45 == null) {
                     throw new RuntimeException("can't create player at x,y,z=" + var11 + "," + var12 + "," + var13 + " because the square is null");
                  }

                  WorldSimulation.instance.create();
                  var47 = new IsoPlayer(instance.CurrentCell, this.getLuaPlayerDesc(), var45.getX(), var45.getY(), var45.getZ());
                  if (GameClient.bClient) {
                     var47.setUsername(GameClient.username);
                  }

                  var47.setDir(IsoDirections.SE);
                  var47.sqlID = 1;
                  IsoPlayer.players[0] = var47;
                  IsoPlayer.setInstance(var47);
                  IsoCamera.CamCharacter = var47;
               }

               IsoPlayer var49 = IsoPlayer.getInstance();
               var49.applyTraits(this.getLuaTraits());
               ProfessionFactory.Profession var50 = ProfessionFactory.getProfession(var49.getDescriptor().getProfession());
               Iterator var51;
               String var52;
               if (var50 != null && !var50.getFreeRecipes().isEmpty()) {
                  var51 = var50.getFreeRecipes().iterator();

                  while(var51.hasNext()) {
                     var52 = (String)var51.next();
                     var49.getKnownRecipes().add(var52);
                  }
               }

               var51 = this.getLuaTraits().iterator();

               label307:
               while(true) {
                  TraitFactory.Trait var53;
                  do {
                     do {
                        if (!var51.hasNext()) {
                           if (var45 != null && var45.getRoom() != null) {
                              var45.getRoom().def.setExplored(true);
                              var45.getRoom().building.setAllExplored(true);
                              if (!GameServer.bServer && !GameClient.bClient) {
                                 ZombiePopulationManager.instance.playerSpawnedAt(var45.getX(), var45.getY(), var45.getZ());
                              }
                           }

                           var49.createKeyRing();
                           if (!GameClient.bClient) {
                              Core.getInstance().initPoisonousBerry();
                              Core.getInstance().initPoisonousMushroom();
                           }

                           LuaEventManager.triggerEvent("OnNewGame", var47, var45);
                           break label307;
                        }

                        var52 = (String)var51.next();
                        var53 = TraitFactory.getTrait(var52);
                     } while(var53 == null);
                  } while(var53.getFreeRecipes().isEmpty());

                  Iterator var20 = var53.getFreeRecipes().iterator();

                  while(var20.hasNext()) {
                     String var21 = (String)var20.next();
                     var49.getKnownRecipes().add(var21);
                  }
               }
            }
         }

         if (PlayerDB.isAllow()) {
            PlayerDB.getInstance().m_canSavePlayers = true;
         }

         if (ClientPlayerDB.isAllow()) {
            ClientPlayerDB.getInstance().canSavePlayers = true;
         }

         TutorialManager.instance.ActiveControlZombies = false;
         ReanimatedPlayers.instance.loadReanimatedPlayers();
         if (IsoPlayer.getInstance() != null) {
            if (GameClient.bClient) {
               int var33 = (int)IsoPlayer.getInstance().getX();
               int var35 = (int)IsoPlayer.getInstance().getY();
               var34 = (int)IsoPlayer.getInstance().getZ();

               while(var34 > 0) {
                  IsoGridSquare var37 = this.CurrentCell.getGridSquare(var33, var35, var34);
                  if (var37 != null && var37.TreatAsSolidFloor()) {
                     break;
                  }

                  --var34;
                  IsoPlayer.getInstance().setZ((float)var34);
               }
            }

            IsoPlayer.getInstance().setCurrent(this.CurrentCell.getGridSquare((int)IsoPlayer.getInstance().getX(), (int)IsoPlayer.getInstance().getY(), (int)IsoPlayer.getInstance().getZ()));
         }

         if (!this.bLoaded) {
            if (!this.CurrentCell.getBuildingList().isEmpty()) {
            }

            if (!this.bLoaded) {
               this.PopulateCellWithSurvivors();
            }
         }

         if (IsoPlayer.players[0] != null && !this.CurrentCell.getObjectList().contains(IsoPlayer.players[0])) {
            this.CurrentCell.getObjectList().add(IsoPlayer.players[0]);
         }

         LightingThread.instance.create();
         GameLoadingState.GameLoadingString = "";
         initMessaging();
      }
   }

   public ArrayList getLuaTraits() {
      if (this.luatraits == null) {
         this.luatraits = new ArrayList();
      }

      return this.luatraits;
   }

   public void addLuaTrait(String var1) {
      this.getLuaTraits().add(var1);
   }

   public SurvivorDesc getLuaPlayerDesc() {
      return this.luaDesc;
   }

   public void setLuaPlayerDesc(SurvivorDesc var1) {
      this.luaDesc = var1;
   }

   public void KillCell() {
      this.helicopter.deactivate();
      CollisionManager.instance.ContactMap.clear();
      IsoDeadBody.Reset();
      FliesSound.instance.Reset();
      IsoObjectPicker.Instance.Init();
      IsoChunkMap.SharedChunks.clear();
      SoundManager.instance.StopMusic();
      WorldSoundManager.instance.KillCell();
      ZombieGroupManager.instance.Reset();
      this.CurrentCell.Dispose();
      IsoSpriteManager.instance.Dispose();
      this.CurrentCell = null;
      CellLoader.wanderRoom = null;
      IsoLot.Dispose();
      IsoGameCharacter.getSurvivorMap().clear();
      IsoPlayer.getInstance().setCurrent((IsoGridSquare)null);
      IsoPlayer.getInstance().setLast((IsoGridSquare)null);
      IsoPlayer.getInstance().square = null;
      RainManager.reset();
      IsoFireManager.Reset();
      IsoWaterFlow.Reset();
      this.MetaGrid.Dispose();
      instance = new IsoWorld();
   }

   public void setDrawWorld(boolean var1) {
      this.bDrawWorld = var1;
   }

   private void sceneCullZombies() {
      this.zombieWithModel.clear();
      this.zombieWithoutModel.clear();

      int var1;
      int var4;
      for(var1 = 0; var1 < this.CurrentCell.getZombieList().size(); ++var1) {
         IsoZombie var2 = (IsoZombie)this.CurrentCell.getZombieList().get(var1);
         boolean var3 = false;

         for(var4 = 0; var4 < IsoPlayer.numPlayers; ++var4) {
            IsoPlayer var5 = IsoPlayer.players[var4];
            if (var5 != null && var2.current != null) {
               float var6 = (float)var2.getScreenProperX(var4);
               float var7 = (float)var2.getScreenProperY(var4);
               if (!(var6 < -100.0F) && !(var7 < -100.0F) && !(var6 > (float)(Core.getInstance().getOffscreenWidth(var4) + 100)) && !(var7 > (float)(Core.getInstance().getOffscreenHeight(var4) + 100)) && (var2.alpha[var4] != 0.0F && var2.legsSprite.def.alpha != 0.0F || var2.current.isCouldSee(var4))) {
                  var3 = true;
                  break;
               }
            }
         }

         if (var3 && var2.isCurrentState(FakeDeadZombieState.instance())) {
            var3 = false;
         }

         if (var3) {
            this.zombieWithModel.add(var2);
         } else {
            this.zombieWithoutModel.add(var2);
         }
      }

      Collections.sort(this.zombieWithModel, compScoreToPlayer);
      var1 = 0;
      int var8 = 0;
      int var9 = 0;
      PerformanceSettings.AnimationSkip = 0;

      IsoZombie var10;
      for(var4 = 0; var4 < this.zombieWithModel.size(); ++var4) {
         var10 = (IsoZombie)this.zombieWithModel.get(var4);
         if (var9 < 510) {
            if (!var10.Ghost) {
               ++var8;
               ++var9;
               var10.setSceneCulled(false);
               if (var10.legsSprite != null && var10.legsSprite.modelSlot != null) {
                  if (var8 > PerformanceSettings.ZombieAnimationSpeedFalloffCount) {
                     ++var1;
                     var8 = 0;
                  }

                  if (var9 < PerformanceSettings.ZombieBonusFullspeedFalloff) {
                     var10.legsSprite.modelSlot.model.setInstanceSkip(var8 / PerformanceSettings.ZombieBonusFullspeedFalloff);
                     var8 = 0;
                  } else {
                     var10.legsSprite.modelSlot.model.setInstanceSkip(var1 + PerformanceSettings.AnimationSkip);
                  }

                  if (var10.legsSprite.modelSlot.model.AnimPlayer != null) {
                     if (var9 < PerformanceSettings.numberZombiesBlended) {
                        var10.legsSprite.modelSlot.model.AnimPlayer.bDoBlending = true;
                     } else {
                        var10.legsSprite.modelSlot.model.AnimPlayer.bDoBlending = false;
                     }
                  }
               }
            }
         } else {
            var10.setSceneCulled(true);
         }
      }

      for(var4 = 0; var4 < this.zombieWithoutModel.size(); ++var4) {
         var10 = (IsoZombie)this.zombieWithoutModel.get(var4);
         if (var10.hasActiveModel()) {
            var10.setSceneCulled(true);
         }
      }

   }

   public void render() {
      IsoWorld.s_performance.isoWorldRender.invokeAndMeasure(this, IsoWorld::renderInternal);
   }

   private void renderInternal() {
      if (this.bDrawWorld) {
         if (IsoCamera.CamCharacter != null) {
            SpriteRenderer.instance.doCoreIntParam(0, IsoCamera.CamCharacter.x);
            SpriteRenderer.instance.doCoreIntParam(1, IsoCamera.CamCharacter.y);
            SpriteRenderer.instance.doCoreIntParam(2, IsoCamera.CamCharacter.z);

            try {
               this.sceneCullZombies();
            } catch (Throwable var3) {
               ExceptionLogger.logException(var3);
            }

            try {
               WeatherFxMask.initMask();
               DeadBodyAtlas.instance.render();
               this.CurrentCell.render();
               this.DrawIsoCursorHelper();
               DeadBodyAtlas.instance.renderDebug();
               PolygonalMap2.instance.render();
               WorldSoundManager.instance.render();
               WorldFlares.debugRender();
               WorldMarkers.instance.debugRender();
               LineDrawer.render();
               WeatherFxMask.renderFxMask(IsoCamera.frameState.playerIndex);
               SkyBox.getInstance().render();
            } catch (Throwable var2) {
               ExceptionLogger.logException(var2);
            }

         }
      }
   }

   private void DrawIsoCursorHelper() {
      IsoPlayer var1 = IsoPlayer.getInstance();
      if (var1 != null && !var1.isDead() && var1.isAiming() && var1.PlayerIndex == 0 && var1.JoypadBind == -1) {
         float var2 = 0.05F;
         switch(Core.getInstance().getIsoCursorVisibility()) {
         case 0:
            return;
         case 1:
            var2 = 0.05F;
            break;
         case 2:
            var2 = 0.1F;
            break;
         case 3:
            var2 = 0.15F;
            break;
         case 4:
            var2 = 0.3F;
            break;
         case 5:
            var2 = 0.5F;
            break;
         case 6:
            var2 = 0.75F;
         }

         if (Core.getInstance().isFlashIsoCursor()) {
            if (this.flashIsoCursorInc) {
               this.flashIsoCursorA += 0.1F;
               if (this.flashIsoCursorA >= 1.0F) {
                  this.flashIsoCursorInc = false;
               }
            } else {
               this.flashIsoCursorA -= 0.1F;
               if (this.flashIsoCursorA <= 0.0F) {
                  this.flashIsoCursorInc = true;
               }
            }

            var2 = this.flashIsoCursorA;
         }

         Texture var3 = Texture.getSharedTexture("media/ui/isocursor.png");
         int var4 = (int)((float)(var3.getWidth() * Core.TileScale) / 2.0F);
         int var5 = (int)((float)(var3.getHeight() * Core.TileScale) / 2.0F);
         SpriteRenderer.instance.setDoAdditive(true);
         SpriteRenderer.instance.renderi(var3, Mouse.getX() - var4 / 2, Mouse.getY() - var5 / 2, var4, var5, var2, var2, var2, var2, (Consumer)null);
         SpriteRenderer.instance.setDoAdditive(false);
      }
   }

   public void update() {
      IsoWorld.s_performance.isoWorldUpdate.invokeAndMeasure(this, IsoWorld::updateInternal);
   }

   private void updateInternal() {
      ++this.m_frameNo;

      try {
         if (GameServer.bServer) {
            VehicleManager.instance.serverUpdate();
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      WorldSimulation.instance.update();
      ImprovedFog.update();
      this.helicopter.update();
      long var1 = System.currentTimeMillis();
      if (var1 - this.emitterUpdateMS >= 30L) {
         this.emitterUpdateMS = var1;
         this.emitterUpdate = true;
      } else {
         this.emitterUpdate = false;
      }

      int var3;
      for(var3 = 0; var3 < this.currentEmitters.size(); ++var3) {
         BaseSoundEmitter var4 = (BaseSoundEmitter)this.currentEmitters.get(var3);
         if (this.emitterUpdate || var4.hasSoundsToStart()) {
            var4.tick();
         }

         if (var4.isEmpty()) {
            this.currentEmitters.remove(var3);
            this.freeEmitters.push(var4);
            --var3;
         }
      }

      if (!GameClient.bClient && !GameServer.bServer) {
         IsoMetaCell var7 = this.MetaGrid.getCurrentCellData();
         if (var7 != null) {
            var7.checkTriggers();
         }
      }

      WorldSoundManager.instance.initFrame();
      ZombieGroupManager.instance.preupdate();
      OnceEvery.update();
      CollisionManager.instance.initUpdate();

      for(var3 = 0; var3 < this.CurrentCell.getBuildingList().size(); ++var3) {
         ((IsoBuilding)this.CurrentCell.getBuildingList().get(var3)).update();
      }

      ClimateManager.getInstance().update();
      ObjectRenderEffects.updateStatic();
      this.CurrentCell.update();
      IsoRegion.update();
      CollisionManager.instance.ResolveContacts();

      for(var3 = 0; var3 < this.AddCoopPlayers.size(); ++var3) {
         AddCoopPlayer var8 = (AddCoopPlayer)this.AddCoopPlayers.get(var3);
         var8.update();
         if (var8.isFinished()) {
            this.AddCoopPlayers.remove(var3--);
         }
      }

      try {
         if (PlayerDB.isAvailable()) {
            PlayerDB.getInstance().updateMain();
         }

         if (ClientPlayerDB.isAvailable()) {
            ClientPlayerDB.getInstance().updateMain();
         }

         VehiclesDB2.instance.updateMain();
      } catch (Exception var5) {
         ExceptionLogger.logException(var5);
      }

      WorldMarkers.instance.update();
      m_animationRecorderDiscard = false;
   }

   public IsoCell getCell() {
      return this.CurrentCell;
   }

   private void PopulateCellWithSurvivors() {
   }

   public int getWorldSquareY() {
      return this.CurrentCell.ChunkMap[IsoPlayer.getPlayerIndex()].WorldY * 10;
   }

   public int getWorldSquareX() {
      return this.CurrentCell.ChunkMap[IsoPlayer.getPlayerIndex()].WorldX * 10;
   }

   public IsoMetaChunk getMetaChunk(int var1, int var2) {
      return this.MetaGrid.getChunkData(var1, var2);
   }

   public IsoMetaChunk getMetaChunkFromTile(int var1, int var2) {
      return this.MetaGrid.getChunkDataFromTile(var1, var2);
   }

   public float getGlobalTemperature() {
      return ClimateManager.getInstance().getTemperature();
   }

   /** @deprecated */
   @Deprecated
   public void setGlobalTemperature(float var1) {
   }

   public String getWeather() {
      return this.weather;
   }

   public void setWeather(String var1) {
      this.weather = var1;
   }

   public int getLuaSpawnCellX() {
      return this.luaSpawnCellX;
   }

   public void setLuaSpawnCellX(int var1) {
      this.luaSpawnCellX = var1;
   }

   public int getLuaSpawnCellY() {
      return this.luaSpawnCellY;
   }

   public void setLuaSpawnCellY(int var1) {
      this.luaSpawnCellY = var1;
   }

   public int getLuaPosX() {
      return this.luaPosX;
   }

   public void setLuaPosX(int var1) {
      this.luaPosX = var1;
   }

   public int getLuaPosY() {
      return this.luaPosY;
   }

   public void setLuaPosY(int var1) {
      this.luaPosY = var1;
   }

   public int getLuaPosZ() {
      return this.luaPosZ;
   }

   public void setLuaPosZ(int var1) {
      this.luaPosZ = var1;
   }

   public String getWorld() {
      return Core.GameSaveWorld;
   }

   public void transmitWeather() {
      if (GameServer.bServer) {
         GameServer.sendWeather();
      }
   }

   public boolean isValidSquare(int var1, int var2, int var3) {
      return var3 >= 0 && var3 < 8 ? this.MetaGrid.isValidSquare(var1, var2) : false;
   }

   public ArrayList getRandomizedZoneList() {
      return this.randomizedZoneList;
   }

   public ArrayList getRandomizedBuildingList() {
      return this.randomizedBuildingList;
   }

   public ArrayList getRandomizedVehicleStoryList() {
      return this.randomizedVehicleStoryList;
   }

   public RandomizedBuildingBase getRBBasic() {
      return this.RBBasic;
   }

   public String getDifficulty() {
      return Core.getDifficulty();
   }

   public void setDifficulty(String var1) {
      Core.setDifficulty(var1);
   }

   public static boolean getZombiesDisabled() {
      return NoZombies || !SystemDisabler.doZombieCreation || SandboxOptions.instance.Zombies.getValue() == 6;
   }

   public static boolean getZombiesEnabled() {
      return !getZombiesDisabled();
   }

   public ClimateManager getClimateManager() {
      return ClimateManager.getInstance();
   }

   public IsoPuddles getPuddlesManager() {
      return IsoPuddles.getInstance();
   }

   public static int getWorldVersion() {
      return 175;
   }

   public HashMap getSpawnedZombieZone() {
      return this.spawnedZombieZone;
   }

   public int getTimeSinceLastSurvivorInHorde() {
      return this.timeSinceLastSurvivorInHorde;
   }

   public void setTimeSinceLastSurvivorInHorde(int var1) {
      this.timeSinceLastSurvivorInHorde = var1;
   }

   public float getWorldAgeDays() {
      float var1 = (float)GameTime.getInstance().getWorldAgeHours() / 24.0F;
      var1 += (float)((SandboxOptions.instance.TimeSinceApo.getValue() - 1) * 30);
      return var1;
   }

   public HashMap getAllTiles() {
      return this.allTiles;
   }

   public ArrayList getAllTilesName() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.allTiles.keySet().iterator();

      while(var2.hasNext()) {
         var1.add(var2.next());
      }

      Collections.sort(var1);
      return var1;
   }

   public ArrayList getAllTiles(String var1) {
      return (ArrayList)this.allTiles.get(var1);
   }

   private static class s_performance {
      static final PerformanceProfileProbe isoWorldUpdate = new PerformanceProfileProbe("IsoWorld.update");
      static final PerformanceProfileProbe isoWorldRender = new PerformanceProfileProbe("IsoWorld.render");
   }

   public class Frame {
      public ArrayList xPos = new ArrayList();
      public ArrayList yPos = new ArrayList();
      public ArrayList Type = new ArrayList();

      public Frame() {
         Iterator var2 = IsoWorld.instance.CurrentCell.getObjectList().iterator();

         while(var2 != null && var2.hasNext()) {
            IsoMovingObject var3 = (IsoMovingObject)var2.next();
            boolean var4 = true;
            byte var5;
            if (var3 instanceof IsoPlayer) {
               var5 = 0;
            } else if (var3 instanceof IsoSurvivor) {
               var5 = 1;
            } else {
               if (!(var3 instanceof IsoZombie) || ((IsoZombie)var3).Ghost) {
                  continue;
               }

               var5 = 2;
            }

            this.xPos.add((int)var3.getX());
            this.yPos.add((int)var3.getY());
            this.Type.add(Integer.valueOf(var5));
         }

      }
   }

   public static class MetaCell {
      public int x;
      public int y;
      public int zombieCount;
      public IsoDirections zombieMigrateDirection;
      public int[][] from = new int[3][3];
   }

   private static class CompScoreToPlayer implements Comparator {
      private CompScoreToPlayer() {
      }

      public int compare(IsoZombie var1, IsoZombie var2) {
         float var3 = this.getScore(var1);
         float var4 = this.getScore(var2);
         if (var3 < var4) {
            return 1;
         } else {
            return var3 > var4 ? -1 : 0;
         }
      }

      public float getScore(IsoZombie var1) {
         float var2 = Float.MIN_VALUE;

         for(int var3 = 0; var3 < 4; ++var3) {
            IsoPlayer var4 = IsoPlayer.players[var3];
            if (var4 != null && var4.current != null) {
               float var5 = var4.getZombieRelevenceScore(var1);
               var2 = Math.max(var2, var5);
            }
         }

         return var2;
      }

      // $FF: synthetic method
      CompScoreToPlayer(Object var1) {
         this();
      }
   }

   private static class CompDistToPlayer implements Comparator {
      public float px;
      public float py;

      private CompDistToPlayer() {
      }

      public int compare(IsoZombie var1, IsoZombie var2) {
         float var3 = IsoUtils.DistanceManhatten((float)((int)var1.x), (float)((int)var1.y), this.px, this.py);
         float var4 = IsoUtils.DistanceManhatten((float)((int)var2.x), (float)((int)var2.y), this.px, this.py);
         if (var3 < var4) {
            return -1;
         } else {
            return var3 > var4 ? 1 : 0;
         }
      }

      // $FF: synthetic method
      CompDistToPlayer(Object var1) {
         this();
      }
   }
}
