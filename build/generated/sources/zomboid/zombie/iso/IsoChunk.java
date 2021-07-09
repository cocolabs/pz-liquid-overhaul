package zombie.iso;

import gnu.trove.list.array.TIntArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.CRC32;
import zombie.ChunkMapFilenames;
import zombie.FliesSound;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.LoadGridsquarePerformanceWorkaround;
import zombie.LootRespawn;
import zombie.MapCollisionData;
import zombie.ReanimatedPlayers;
import zombie.SandboxOptions;
import zombie.SystemDisabler;
import zombie.VirtualZombieManager;
import zombie.WorldSoundManager;
import zombie.ZombieSpawnRecorder;
import zombie.ZomboidFileSystem;
import zombie.Lua.LuaEventManager;
import zombie.Lua.MapObjects;
import zombie.characterTextures.BloodBodyPartType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoSurvivor;
import zombie.characters.IsoZombie;
import zombie.core.Core;
import zombie.core.PerformanceSettings;
import zombie.core.Rand;
import zombie.core.logger.ExceptionLogger;
import zombie.core.logger.LoggerManager;
import zombie.core.math.PZMath;
import zombie.core.network.ByteBufferWriter;
import zombie.core.physics.Bullet;
import zombie.core.physics.WorldSimulation;
import zombie.core.properties.PropertyContainer;
import zombie.core.raknet.UdpConnection;
import zombie.core.stash.StashSystem;
import zombie.core.utils.BoundedQueue;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.debug.DebugType;
import zombie.erosion.ErosionData;
import zombie.erosion.ErosionMain;
import zombie.globalObjects.SGlobalObjects;
import zombie.iso.SpriteDetails.IsoFlagType;
import zombie.iso.SpriteDetails.IsoObjectType;
import zombie.iso.areas.IsoBuilding;
import zombie.iso.areas.IsoRoom;
import zombie.iso.objects.IsoDeadBody;
import zombie.iso.objects.IsoGenerator;
import zombie.iso.objects.IsoLightSwitch;
import zombie.iso.objects.IsoThumpable;
import zombie.iso.objects.IsoTree;
import zombie.iso.objects.RainManager;
import zombie.iso.sprite.IsoSprite;
import zombie.iso.sprite.IsoSpriteManager;
import zombie.network.ChunkChecksum;
import zombie.network.ClientChunkRequest;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.PacketTypes;
import zombie.network.ServerMap;
import zombie.network.ServerOptions;
import zombie.popman.ZombiePopulationManager;
import zombie.randomizedWorld.randomizedBuilding.RandomizedBuildingBase;
import zombie.randomizedWorld.randomizedVehicleStory.RandomizedVehicleStoryBase;
import zombie.randomizedWorld.randomizedZoneStory.RandomizedZoneStoryBase;
import zombie.scripting.ScriptManager;
import zombie.scripting.objects.VehicleScript;
import zombie.vehicles.BaseVehicle;
import zombie.vehicles.PolygonalMap2;
import zombie.vehicles.VehicleType;
import zombie.vehicles.VehiclesDB2;

public final class IsoChunk {
   public static boolean bDoServerRequests = true;
   public int wx = 0;
   public int wy = 0;
   public final IsoGridSquare[][] squares;
   public FliesSound.ChunkData corpseData;
   private ArrayList generatorsTouchingThisChunk;
   public int maxLevel = -1;
   public final ArrayList SoundList = new ArrayList();
   private int m_treeCount = 0;
   private IsoMetaGrid.Zone m_scavengeZone = null;
   private final TIntArrayList m_spawnedRooms = new TIntArrayList();
   public IsoChunk next;
   public int m_adjacentChunkLoadedCounter = 0;
   public IsoChunk.JobType jobType;
   public LotHeader lotheader;
   public final BoundedQueue FloorBloodSplats;
   public final ArrayList FloorBloodSplatsFade;
   private static final int MAX_BLOOD_SPLATS = 1000;
   private int nextSplatIndex;
   public static final byte[][] renderByIndex = new byte[][]{{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 1, 0, 0, 0, 0}, {1, 0, 0, 1, 0, 0, 1, 0, 0, 0}, {1, 0, 0, 1, 0, 1, 0, 0, 1, 0}, {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, {1, 1, 0, 1, 1, 0, 1, 1, 0, 0}, {1, 1, 0, 1, 1, 0, 1, 1, 0, 1}, {1, 1, 1, 1, 0, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
   public final ArrayList refs;
   public boolean bLoaded;
   private boolean blam;
   private boolean addZombies;
   private boolean bFixed2x;
   public final boolean[] lightCheck;
   public final boolean[] bLightingNeverDone;
   public final ArrayList roomLights;
   public final ArrayList vehicles;
   public int lootRespawnHour;
   private long hashCodeObjects;
   public int ObjectsSyncCount;
   private static int AddVehicles_ForTest_vtype = 0;
   private static int AddVehicles_ForTest_vskin = 0;
   private static int AddVehicles_ForTest_vrot = 0;
   private static final ArrayList BaseVehicleCheckedVehicles = new ArrayList();
   protected boolean physicsCheck;
   private static final int MAX_SHAPES = 4;
   private final IsoChunk.PhysicsShapes[] shapes;
   private static final byte[] bshapes = new byte[4];
   private static IsoChunk.ChunkGetter chunkGetter = new IsoChunk.ChunkGetter();
   private boolean loadedPhysics;
   public final Object vehiclesForAddToWorldLock;
   public ArrayList vehiclesForAddToWorld;
   public static ConcurrentLinkedQueue loadGridSquare = new ConcurrentLinkedQueue();
   private static final int BLOCK_SIZE = 65536;
   private static ByteBuffer SliceBuffer = ByteBuffer.allocate(65536);
   private static ByteBuffer SliceBufferLoad = ByteBuffer.allocate(65536);
   private static final Object WriteLock = new Object();
   private static final ArrayList tempBuildings = new ArrayList();
   private static final ArrayList Locks = new ArrayList();
   private static final Stack FreeLocks = new Stack();
   private static final IsoChunk.SanityCheck sanityCheck = new IsoChunk.SanityCheck();
   private static final CRC32 crcLoad = new CRC32();
   private static final CRC32 crcSave = new CRC32();
   private static String prefix = "map_";
   private ErosionData.Chunk erosion;
   private static final HashMap Fix2xMap = new HashMap();
   public int randomID;
   public long revision;

   public void updateSounds() {
      synchronized(WorldSoundManager.instance.SoundList) {
         int var2 = this.SoundList.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            WorldSoundManager.WorldSound var4 = (WorldSoundManager.WorldSound)this.SoundList.get(var3);
            if (var4 == null || var4.life <= 0) {
               this.SoundList.remove(var3);
               --var3;
               --var2;
            }
         }

      }
   }

   public IsoChunk(IsoCell var1) {
      this.jobType = IsoChunk.JobType.None;
      this.FloorBloodSplats = new BoundedQueue(1000);
      this.FloorBloodSplatsFade = new ArrayList();
      this.refs = new ArrayList();
      this.lightCheck = new boolean[4];
      this.bLightingNeverDone = new boolean[4];
      this.roomLights = new ArrayList();
      this.vehicles = new ArrayList();
      this.lootRespawnHour = -1;
      this.ObjectsSyncCount = 0;
      this.physicsCheck = false;
      this.shapes = new IsoChunk.PhysicsShapes[4];
      this.loadedPhysics = false;
      this.vehiclesForAddToWorldLock = new Object();
      this.vehiclesForAddToWorld = null;
      this.squares = new IsoGridSquare[8][100];

      for(int var2 = 0; var2 < 4; ++var2) {
         this.lightCheck[var2] = true;
         this.bLightingNeverDone[var2] = true;
      }

   }

   /** @deprecated */
   @Deprecated
   public long getHashCodeObjects() {
      this.recalcHashCodeObjects();
      return this.hashCodeObjects;
   }

   /** @deprecated */
   @Deprecated
   public void recalcHashCodeObjects() {
      long var1 = 0L;
      this.hashCodeObjects = var1;
   }

   /** @deprecated */
   @Deprecated
   public int hashCodeNoOverride() {
      return (int)this.hashCodeObjects;
   }

   public void addBloodSplat(float var1, float var2, float var3, int var4) {
      if (!(var1 < (float)(this.wx * 10)) && !(var1 >= (float)((this.wx + 1) * 10))) {
         if (!(var2 < (float)(this.wy * 10)) && !(var2 >= (float)((this.wy + 1) * 10))) {
            IsoGridSquare var5 = this.getGridSquare((int)(var1 - (float)(this.wx * 10)), (int)(var2 - (float)(this.wy * 10)), (int)var3);
            if (var5 != null && var5.isSolidFloor()) {
               IsoFloorBloodSplat var6 = new IsoFloorBloodSplat(var1 - (float)(this.wx * 10), var2 - (float)(this.wy * 10), var3, var4, (float)GameTime.getInstance().getWorldAgeHours());
               if (var4 < 8) {
                  var6.index = ++this.nextSplatIndex;
                  if (this.nextSplatIndex >= 10) {
                     this.nextSplatIndex = 0;
                  }
               }

               if (this.FloorBloodSplats.isFull()) {
                  IsoFloorBloodSplat var7 = (IsoFloorBloodSplat)this.FloorBloodSplats.removeFirst();
                  var7.fade = PerformanceSettings.getLockFPS() * 5;
                  this.FloorBloodSplatsFade.add(var7);
               }

               this.FloorBloodSplats.add(var6);
            }

         }
      }
   }

   public void AddCorpses(int var1, int var2) {
      if (!IsoWorld.getZombiesDisabled() && !"Tutorial".equals(Core.GameMode)) {
         IsoMetaChunk var3 = IsoWorld.instance.getMetaChunk(var1, var2);
         if (var3 != null) {
            float var4 = var3.getZombieIntensity();
            var4 *= 0.1F;
            int var5 = 0;
            if (var4 < 1.0F) {
               if ((float)Rand.Next(100) < var4 * 100.0F) {
                  var5 = 1;
               }
            } else {
               var5 = Rand.Next(0, (int)var4);
            }

            if (var5 > 0) {
               IsoGridSquare var6 = null;
               int var7 = 0;

               int var9;
               do {
                  int var8 = Rand.Next(10);
                  var9 = Rand.Next(10);
                  var6 = this.getGridSquare(var8, var9, 0);
                  ++var7;
               } while(var7 < 100 && (var6 == null || !RandomizedBuildingBase.is2x2AreaClear(var6)));

               if (var7 == 100) {
                  return;
               }

               if (var6 != null) {
                  byte var12 = 14;
                  if (Rand.Next(10) == 0) {
                     var12 = 50;
                  }

                  if (Rand.Next(40) == 0) {
                     var12 = 100;
                  }

                  for(var9 = 0; var9 < var12; ++var9) {
                     float var10 = (float)Rand.Next(3000) / 1000.0F;
                     float var11 = (float)Rand.Next(3000) / 1000.0F;
                     --var10;
                     --var11;
                     this.addBloodSplat((float)var6.getX() + var10, (float)var6.getY() + var11, (float)var6.getZ(), Rand.Next(20));
                  }

                  boolean var13 = Rand.Next(15 - SandboxOptions.instance.TimeSinceApo.getValue()) == 0;
                  VirtualZombieManager.instance.choices.clear();
                  VirtualZombieManager.instance.choices.add(var6);
                  IsoZombie var14 = VirtualZombieManager.instance.createRealZombieAlways(Rand.Next(8), false);
                  var14.setX((float)var6.x);
                  var14.setY((float)var6.y);
                  var14.setFakeDead(false);
                  var14.setHealth(0.0F);
                  var14.upKillCount = false;
                  if (!var13) {
                     var14.dressInRandomOutfit();

                     for(int var15 = 0; var15 < 10; ++var15) {
                        var14.addHole((BloodBodyPartType)null);
                        var14.addBlood((BloodBodyPartType)null, false, true, false);
                        var14.addDirt((BloodBodyPartType)null, (Integer)null, false);
                     }

                     var14.DoZombieInventory();
                  }

                  var14.setSkeleton(var13);
                  if (var13) {
                     var14.getHumanVisual().setSkinTextureIndex(2);
                  }

                  IsoDeadBody var16 = new IsoDeadBody(var14, true);
                  if (!var13 && Rand.Next(4) == 0) {
                     VirtualZombieManager.instance.createEatingZombies(var16, Rand.Next(1, 4));
                  }
               }
            }
         }

      }
   }

   public void AddBlood(int var1, int var2) {
      IsoMetaChunk var3 = IsoWorld.instance.getMetaChunk(var1, var2);
      if (var3 != null) {
         float var4 = var3.getZombieIntensity();
         var4 *= 0.1F;
         if (Rand.Next(40) == 0) {
            var4 += 10.0F;
         }

         int var5 = 0;
         if (var4 < 1.0F) {
            if ((float)Rand.Next(100) < var4 * 100.0F) {
               var5 = 1;
            }
         } else {
            var5 = Rand.Next(0, (int)var4);
         }

         if (var5 > 0) {
            VirtualZombieManager.instance.AddBloodToMap(var5, this);
         }
      }

   }

   private void checkVehiclePos(BaseVehicle var1, IsoChunk var2) {
      this.fixVehiclePos(var1, var2);
      IsoDirections var3 = var1.getDir();
      IsoGridSquare var4;
      switch(var3) {
      case E:
      case W:
         if (var1.x - (float)(var2.wx * 10) < var1.getScript().getExtents().x) {
            var4 = IsoWorld.instance.CurrentCell.getGridSquare((double)(var1.x - var1.getScript().getExtents().x), (double)var1.y, (double)var1.z);
            if (var4 == null) {
               return;
            }

            this.fixVehiclePos(var1, var4.chunk);
         }

         if (var1.x - (float)(var2.wx * 10) > 10.0F - var1.getScript().getExtents().x) {
            var4 = IsoWorld.instance.CurrentCell.getGridSquare((double)(var1.x + var1.getScript().getExtents().x), (double)var1.y, (double)var1.z);
            if (var4 == null) {
               return;
            }

            this.fixVehiclePos(var1, var4.chunk);
         }
         break;
      case N:
      case S:
         if (var1.y - (float)(var2.wy * 10) < var1.getScript().getExtents().z) {
            var4 = IsoWorld.instance.CurrentCell.getGridSquare((double)var1.x, (double)(var1.y - var1.getScript().getExtents().z), (double)var1.z);
            if (var4 == null) {
               return;
            }

            this.fixVehiclePos(var1, var4.chunk);
         }

         if (var1.y - (float)(var2.wy * 10) > 10.0F - var1.getScript().getExtents().z) {
            var4 = IsoWorld.instance.CurrentCell.getGridSquare((double)var1.x, (double)(var1.y + var1.getScript().getExtents().z), (double)var1.z);
            if (var4 == null) {
               return;
            }

            this.fixVehiclePos(var1, var4.chunk);
         }
      }

   }

   private boolean fixVehiclePos(BaseVehicle var1, IsoChunk var2) {
      BaseVehicle.MinMaxPosition var3 = var1.getMinMaxPosition();
      boolean var5 = false;
      IsoDirections var6 = var1.getDir();

      for(int var7 = 0; var7 < var2.vehicles.size(); ++var7) {
         BaseVehicle.MinMaxPosition var8 = ((BaseVehicle)var2.vehicles.get(var7)).getMinMaxPosition();
         float var4;
         switch(var6) {
         case E:
         case W:
            var4 = var8.minX - var3.maxX;
            if (var4 > 0.0F && var3.minY < var8.maxY && var3.maxY > var8.minY) {
               var1.x -= var4;
               var3.minX -= var4;
               var3.maxX -= var4;
               var5 = true;
            } else {
               var4 = var3.minX - var8.maxX;
               if (var4 > 0.0F && var3.minY < var8.maxY && var3.maxY > var8.minY) {
                  var1.x += var4;
                  var3.minX += var4;
                  var3.maxX += var4;
                  var5 = true;
               }
            }
            break;
         case N:
         case S:
            var4 = var8.minY - var3.maxY;
            if (var4 > 0.0F && var3.minX < var8.maxX && var3.maxX > var8.minX) {
               var1.y -= var4;
               var3.minY -= var4;
               var3.maxY -= var4;
               var5 = true;
            } else {
               var4 = var3.minY - var8.maxY;
               if (var4 > 0.0F && var3.minX < var8.maxX && var3.maxX > var8.minX) {
                  var1.y += var4;
                  var3.minY += var4;
                  var3.maxY += var4;
                  var5 = true;
               }
            }
         }
      }

      return var5;
   }

   private boolean isGoodVehiclePos(BaseVehicle var1, IsoChunk var2) {
      BaseVehicle.MinMaxPosition var3 = var1.getMinMaxPosition();
      boolean var4 = true;

      for(int var5 = 0; var5 < var2.vehicles.size(); ++var5) {
         BaseVehicle.MinMaxPosition var6 = ((BaseVehicle)var2.vehicles.get(var5)).getMinMaxPosition();
         if (var3.minX < var6.maxX && var3.maxX > var6.minX && var3.minY < var6.maxY && var3.maxY > var6.minY) {
            var4 = false;
            break;
         }
      }

      return var4;
   }

   private void AddVehicles_ForTest(IsoMetaGrid.Zone var1) {
      int var4;
      for(var4 = var1.y - this.wy * 10 + 3; var4 < 0; var4 += 6) {
      }

      int var5;
      for(var5 = var1.x - this.wx * 10 + 2; var5 < 0; var5 += 5) {
      }

      for(int var6 = var4; var6 < 10 && this.wy * 10 + var6 < var1.y + var1.h; var6 += 6) {
         for(int var7 = var5; var7 < 10 && this.wx * 10 + var7 < var1.x + var1.w; var7 += 5) {
            IsoGridSquare var8 = this.getGridSquare(var7, var6, 0);
            if (var8 != null) {
               BaseVehicle var9 = new BaseVehicle(IsoWorld.instance.CurrentCell);
               var9.setZone("Test");
               switch(AddVehicles_ForTest_vtype) {
               case 0:
                  var9.setScriptName("Base.CarNormal");
                  break;
               case 1:
                  var9.setScriptName("Base.SmallCar");
                  break;
               case 2:
                  var9.setScriptName("Base.SmallCar02");
                  break;
               case 3:
                  var9.setScriptName("Base.CarTaxi");
                  break;
               case 4:
                  var9.setScriptName("Base.CarTaxi2");
                  break;
               case 5:
                  var9.setScriptName("Base.PickUpTruck");
                  break;
               case 6:
                  var9.setScriptName("Base.PickUpVan");
                  break;
               case 7:
                  var9.setScriptName("Base.CarStationWagon");
                  break;
               case 8:
                  var9.setScriptName("Base.CarStationWagon2");
                  break;
               case 9:
                  var9.setScriptName("Base.VanSeats");
                  break;
               case 10:
                  var9.setScriptName("Base.Van");
                  break;
               case 11:
                  var9.setScriptName("Base.StepVan");
                  break;
               case 12:
                  var9.setScriptName("Base.PickUpTruck");
                  break;
               case 13:
                  var9.setScriptName("Base.PickUpVan");
                  break;
               case 14:
                  var9.setScriptName("Base.CarStationWagon");
                  break;
               case 15:
                  var9.setScriptName("Base.CarStationWagon2");
                  break;
               case 16:
                  var9.setScriptName("Base.VanSeats");
                  break;
               case 17:
                  var9.setScriptName("Base.Van");
                  break;
               case 18:
                  var9.setScriptName("Base.StepVan");
                  break;
               case 19:
                  var9.setScriptName("Base.SUV");
                  break;
               case 20:
                  var9.setScriptName("Base.OffRoad");
                  break;
               case 21:
                  var9.setScriptName("Base.ModernCar");
                  break;
               case 22:
                  var9.setScriptName("Base.ModernCar02");
                  break;
               case 23:
                  var9.setScriptName("Base.CarLuxury");
                  break;
               case 24:
                  var9.setScriptName("Base.SportsCar");
                  break;
               case 25:
                  var9.setScriptName("Base.PickUpVanLightsPolice");
                  break;
               case 26:
                  var9.setScriptName("Base.CarLightsPolice");
                  break;
               case 27:
                  var9.setScriptName("Base.PickUpVanLightsFire");
                  break;
               case 28:
                  var9.setScriptName("Base.PickUpTruckLightsFire");
                  break;
               case 29:
                  var9.setScriptName("Base.PickUpVanLights");
                  break;
               case 30:
                  var9.setScriptName("Base.PickUpTruckLights");
                  break;
               case 31:
                  var9.setScriptName("Base.CarLights");
                  break;
               case 32:
                  var9.setScriptName("Base.StepVanMail");
                  break;
               case 33:
                  var9.setScriptName("Base.VanSpiffo");
                  break;
               case 34:
                  var9.setScriptName("Base.VanAmbulance");
                  break;
               case 35:
                  var9.setScriptName("Base.VanRadio");
                  break;
               case 36:
                  var9.setScriptName("Base.PickupBurnt");
                  break;
               case 37:
                  var9.setScriptName("Base.CarNormalBurnt");
                  break;
               case 38:
                  var9.setScriptName("Base.TaxiBurnt");
                  break;
               case 39:
                  var9.setScriptName("Base.ModernCarBurnt");
                  break;
               case 40:
                  var9.setScriptName("Base.ModernCar02Burnt");
                  break;
               case 41:
                  var9.setScriptName("Base.SportsCarBurnt");
                  break;
               case 42:
                  var9.setScriptName("Base.SmallCarBurnt");
                  break;
               case 43:
                  var9.setScriptName("Base.SmallCar02Burnt");
                  break;
               case 44:
                  var9.setScriptName("Base.VanSeatsBurnt");
                  break;
               case 45:
                  var9.setScriptName("Base.VanBurnt");
                  break;
               case 46:
                  var9.setScriptName("Base.SUVBurnt");
                  break;
               case 47:
                  var9.setScriptName("Base.OffRoadBurnt");
                  break;
               case 48:
                  var9.setScriptName("Base.PickUpVanLightsBurnt");
                  break;
               case 49:
                  var9.setScriptName("Base.AmbulanceBurnt");
                  break;
               case 50:
                  var9.setScriptName("Base.VanRadioBurnt");
                  break;
               case 51:
                  var9.setScriptName("Base.PickupSpecialBurnt");
                  break;
               case 52:
                  var9.setScriptName("Base.NormalCarBurntPolice");
                  break;
               case 53:
                  var9.setScriptName("Base.LuxuryCarBurnt");
                  break;
               case 54:
                  var9.setScriptName("Base.PickUpVanBurnt");
                  break;
               case 55:
                  var9.setScriptName("Base.PickUpTruckMccoy");
               }

               var9.setDir(IsoDirections.W);
               double var10 = (double)(var9.getDir().toAngle() + 3.1415927F) % 6.283185307179586D;
               var9.savedRot.setAngleAxis(var10, 0.0D, 1.0D, 0.0D);
               if (AddVehicles_ForTest_vrot == 1) {
                  var9.savedRot.setAngleAxis(1.5707963267948966D, 0.0D, 0.0D, 1.0D);
               }

               if (AddVehicles_ForTest_vrot == 2) {
                  var9.savedRot.setAngleAxis(3.141592653589793D, 0.0D, 0.0D, 1.0D);
               }

               var9.jniTransform.setRotation(var9.savedRot);
               var9.setX((float)var8.x);
               var9.setY((float)var8.y + 3.0F - 3.0F);
               var9.setZ((float)var8.z);
               var9.jniTransform.origin.set(var9.getX() - WorldSimulation.instance.offsetX, var9.getZ(), var9.getY() - WorldSimulation.instance.offsetY);
               var9.setScript();
               this.checkVehiclePos(var9, this);
               this.vehicles.add(var9);
               var9.setSkinIndex(AddVehicles_ForTest_vskin);
               ++AddVehicles_ForTest_vrot;
               if (AddVehicles_ForTest_vrot >= 2) {
                  AddVehicles_ForTest_vrot = 0;
                  ++AddVehicles_ForTest_vskin;
                  if (AddVehicles_ForTest_vskin >= var9.getSkinCount()) {
                     AddVehicles_ForTest_vtype = (AddVehicles_ForTest_vtype + 1) % 56;
                     AddVehicles_ForTest_vskin = 0;
                  }
               }
            }
         }
      }

   }

   private void AddVehicles_OnZone(IsoMetaGrid.VehicleZone var1, String var2) {
      IsoDirections var3 = IsoDirections.N;
      byte var4 = 3;
      byte var5 = 4;
      if ((var1.w == var5 || var1.w == var5 + 1 || var1.w == var5 + 2) && (var1.h <= var4 || var1.h >= var5 + 2)) {
         var3 = IsoDirections.W;
      }

      var5 = 5;
      if (var1.dir != IsoDirections.Max) {
         var3 = var1.dir;
      }

      if (var3 != IsoDirections.N && var3 != IsoDirections.S) {
         var5 = 3;
         var4 = 5;
      }

      byte var6 = 10;

      float var7;
      for(var7 = (float)(var1.y - this.wy * 10) + (float)var5 / 2.0F; var7 < 0.0F; var7 += (float)var5) {
      }

      float var8;
      for(var8 = (float)(var1.x - this.wx * 10) + (float)var4 / 2.0F; var8 < 0.0F; var8 += (float)var4) {
      }

      float var9 = var7;

      label203:
      while(true) {
         if (var9 < 10.0F && (float)(this.wy * 10) + var9 < (float)(var1.y + var1.h)) {
            float var10 = var8;

            while(true) {
               label196: {
                  if (var10 < 10.0F && (float)(this.wx * 10) + var10 < (float)(var1.x + var1.w)) {
                     IsoGridSquare var11 = this.getGridSquare((int)var10, (int)var9, 0);
                     if (var11 == null) {
                        break label196;
                     }

                     VehicleType var12 = VehicleType.getRandomVehicleType(var2);
                     if (var12 != null) {
                        int var13 = var12.spawnRate;
                        switch(SandboxOptions.instance.CarSpawnRate.getValue()) {
                        case 1:
                        case 4:
                        default:
                           break;
                        case 2:
                           var13 = (int)Math.ceil((double)((float)var13 / 10.0F));
                           break;
                        case 3:
                           var13 = (int)Math.ceil((double)((float)var13 / 1.5F));
                           break;
                        case 5:
                           var13 *= 2;
                        }

                        if (SystemDisabler.doVehiclesEverywhere || DebugOptions.instance.VehicleSpawnEverywhere.getValue()) {
                           var13 = 100;
                        }

                        if (Rand.Next(100) <= var13) {
                           BaseVehicle var14 = new BaseVehicle(IsoWorld.instance.CurrentCell);
                           var14.setZone(var2);
                           var14.setVehicleType(var12.name);
                           if (var12.isSpecialCar) {
                              var14.setDoColor(false);
                           }

                           if (!this.RandomizeModel(var14, var1, var2, var12)) {
                              System.out.println("Problem with Vehicle spawning: " + var2 + " " + var12);
                              return;
                           }

                           byte var15 = 15;
                           switch(SandboxOptions.instance.CarAlarm.getValue()) {
                           case 1:
                              var15 = -1;
                              break;
                           case 2:
                              var15 = 3;
                              break;
                           case 3:
                              var15 = 8;
                           case 4:
                           default:
                              break;
                           case 5:
                              var15 = 25;
                              break;
                           case 6:
                              var15 = 50;
                           }

                           if (Rand.Next(100) < var15) {
                              var14.setAlarmed(true);
                           }

                           if (var1.isFaceDirection()) {
                              var14.setDir(var3);
                           } else if (var3 != IsoDirections.N && var3 != IsoDirections.S) {
                              var14.setDir(Rand.Next(2) == 0 ? IsoDirections.W : IsoDirections.E);
                           } else {
                              var14.setDir(Rand.Next(2) == 0 ? IsoDirections.N : IsoDirections.S);
                           }

                           float var16;
                           for(var16 = var14.getDir().toAngle() + 3.1415927F; (double)var16 > 6.283185307179586D; var16 = (float)((double)var16 - 6.283185307179586D)) {
                           }

                           if (var12.randomAngle) {
                              var16 = Rand.Next(0.0F, 6.2831855F);
                           }

                           var14.savedRot.setAngleAxis(var16, 0.0F, 1.0F, 0.0F);
                           var14.jniTransform.setRotation(var14.savedRot);
                           float var17 = var14.getScript().getExtents().z;
                           float var18 = 0.5F;
                           float var19 = (float)var11.x + 0.5F;
                           float var20 = (float)var11.y + 0.5F;
                           if (var3 == IsoDirections.N) {
                              var19 = (float)var11.x + (float)var4 / 2.0F - (float)((int)((float)var4 / 2.0F));
                              var20 = (float)var1.y + var17 / 2.0F + var18;
                              if (var20 >= (float)(var11.y + 1) && (int)var9 < var6 - 1 && this.getGridSquare((int)var10, (int)var9 + 1, 0) != null) {
                                 var11 = this.getGridSquare((int)var10, (int)var9 + 1, 0);
                              }
                           } else if (var3 == IsoDirections.S) {
                              var19 = (float)var11.x + (float)var4 / 2.0F - (float)((int)((float)var4 / 2.0F));
                              var20 = (float)(var1.y + var1.h) - var17 / 2.0F - var18;
                              if (var20 < (float)var11.y && (int)var9 > 0 && this.getGridSquare((int)var10, (int)var9 - 1, 0) != null) {
                                 var11 = this.getGridSquare((int)var10, (int)var9 - 1, 0);
                              }
                           } else if (var3 == IsoDirections.W) {
                              var19 = (float)var1.x + var17 / 2.0F + var18;
                              var20 = (float)var11.y + (float)var5 / 2.0F - (float)((int)((float)var5 / 2.0F));
                              if (var19 >= (float)(var11.x + 1) && (int)var10 < var6 - 1 && this.getGridSquare((int)var10 + 1, (int)var9, 0) != null) {
                                 var11 = this.getGridSquare((int)var10 + 1, (int)var9, 0);
                              }
                           } else if (var3 == IsoDirections.E) {
                              var19 = (float)(var1.x + var1.w) - var17 / 2.0F - var18;
                              var20 = (float)var11.y + (float)var5 / 2.0F - (float)((int)((float)var5 / 2.0F));
                              if (var19 < (float)var11.x && (int)var10 > 0 && this.getGridSquare((int)var10 - 1, (int)var9, 0) != null) {
                                 var11 = this.getGridSquare((int)var10 - 1, (int)var9, 0);
                              }
                           }

                           if (var19 < (float)var11.x + 0.005F) {
                              var19 = (float)var11.x + 0.005F;
                           }

                           if (var19 > (float)(var11.x + 1) - 0.005F) {
                              var19 = (float)(var11.x + 1) - 0.005F;
                           }

                           if (var20 < (float)var11.y + 0.005F) {
                              var20 = (float)var11.y + 0.005F;
                           }

                           if (var20 > (float)(var11.y + 1) - 0.005F) {
                              var20 = (float)(var11.y + 1) - 0.005F;
                           }

                           var14.setX(var19);
                           var14.setY(var20);
                           var14.setZ((float)var11.z);
                           var14.jniTransform.origin.set(var14.getX() - WorldSimulation.instance.offsetX, var14.getZ(), var14.getY() - WorldSimulation.instance.offsetY);
                           float var21 = 100.0F - Math.min(var12.baseVehicleQuality * 120.0F, 100.0F);
                           var14.rust = (float)Rand.Next(100) < var21 ? 1.0F : 0.0F;
                           if (doSpawnedVehiclesInInvalidPosition(var14) || GameClient.bClient) {
                              this.vehicles.add(var14);
                           }

                           if (var12.chanceOfOverCar > 0 && Rand.Next(100) <= var12.chanceOfOverCar) {
                              this.spawnVehicleRandomAngle(var11, var1, var2);
                           }
                        }
                        break label196;
                     }

                     System.out.println("Can't find car: " + var2);
                  }

                  var9 += (float)var5;
                  continue label203;
               }

               var10 += (float)var4;
            }
         }

         return;
      }
   }

   public static void removeFromCheckedVehicles(BaseVehicle var0) {
      BaseVehicleCheckedVehicles.remove(var0);
   }

   public static void addFromCheckedVehicles(BaseVehicle var0) {
      if (!BaseVehicleCheckedVehicles.contains(var0)) {
         BaseVehicleCheckedVehicles.add(var0);
      }

   }

   public static void Reset() {
      BaseVehicleCheckedVehicles.clear();
   }

   public static boolean doSpawnedVehiclesInInvalidPosition(BaseVehicle var0) {
      IsoGridSquare var1;
      if (GameServer.bServer) {
         var1 = ServerMap.instance.getGridSquare((int)var0.getX(), (int)var0.getY(), 0);
         if (var1 != null && var1.roomID != -1) {
            return false;
         }
      } else if (!GameClient.bClient) {
         var1 = IsoWorld.instance.CurrentCell.getGridSquare((int)var0.getX(), (int)var0.getY(), 0);
         if (var1 != null && var1.roomID != -1) {
            return false;
         }
      }

      boolean var3 = true;

      for(int var2 = 0; var2 < BaseVehicleCheckedVehicles.size(); ++var2) {
         if (((BaseVehicle)BaseVehicleCheckedVehicles.get(var2)).testCollisionWithVehicle(var0)) {
            var3 = false;
         }
      }

      if (var3) {
         addFromCheckedVehicles(var0);
      }

      return var3;
   }

   private void spawnVehicleRandomAngle(IsoGridSquare var1, IsoMetaGrid.Zone var2, String var3) {
      boolean var4 = true;
      byte var5 = 3;
      byte var6 = 4;
      if ((var2.w == var6 || var2.w == var6 + 1 || var2.w == var6 + 2) && (var2.h <= var5 || var2.h >= var6 + 2)) {
         var4 = false;
      }

      var6 = 5;
      if (!var4) {
         var6 = 3;
         var5 = 5;
      }

      VehicleType var7 = VehicleType.getRandomVehicleType(var3);
      if (var7 == null) {
         System.out.println("Can't find car: " + var3);
      } else {
         BaseVehicle var8 = new BaseVehicle(IsoWorld.instance.CurrentCell);
         var8.setZone(var3);
         if (this.RandomizeModel(var8, var2, var3, var7)) {
            if (var4) {
               var8.setDir(Rand.Next(2) == 0 ? IsoDirections.N : IsoDirections.S);
            } else {
               var8.setDir(Rand.Next(2) == 0 ? IsoDirections.W : IsoDirections.E);
            }

            float var9 = Rand.Next(0.0F, 6.2831855F);
            var8.savedRot.setAngleAxis(var9, 0.0F, 1.0F, 0.0F);
            var8.jniTransform.setRotation(var8.savedRot);
            if (var4) {
               var8.setX((float)var1.x + (float)var5 / 2.0F - (float)((int)((float)var5 / 2.0F)));
               var8.setY((float)var1.y);
            } else {
               var8.setX((float)var1.x);
               var8.setY((float)var1.y + (float)var6 / 2.0F - (float)((int)((float)var6 / 2.0F)));
            }

            var8.setZ((float)var1.z);
            var8.jniTransform.origin.set(var8.getX() - WorldSimulation.instance.offsetX, var8.getZ(), var8.getY() - WorldSimulation.instance.offsetY);
            if (doSpawnedVehiclesInInvalidPosition(var8) || GameClient.bClient) {
               this.vehicles.add(var8);
            }

         }
      }
   }

   public boolean RandomizeModel(BaseVehicle var1, IsoMetaGrid.Zone var2, String var3, VehicleType var4) {
      if (var4.vehiclesDefinition.isEmpty()) {
         System.out.println("no vehicle definition found for " + var3);
         return false;
      } else {
         float var5 = Rand.Next(0.0F, 100.0F);
         float var6 = 0.0F;
         VehicleType.VehicleTypeDefinition var7 = null;

         for(int var8 = 0; var8 < var4.vehiclesDefinition.size(); ++var8) {
            var7 = (VehicleType.VehicleTypeDefinition)var4.vehiclesDefinition.get(var8);
            var6 += var7.spawnChance;
            if (var5 < var6) {
               break;
            }
         }

         String var13 = var7.vehicleType;
         VehicleScript var9 = ScriptManager.instance.getVehicle(var13);
         if (var9 == null) {
            DebugLog.log("no such vehicle script \"" + var13 + "\" in IsoChunk.RandomizeModel");
            return false;
         } else {
            int var10 = var7.index;
            var1.setScriptName(var13);
            var1.setScript();

            try {
               if (var10 > -1) {
                  var1.setSkinIndex(var10);
               } else {
                  var1.setSkinIndex(Rand.Next(var1.getSkinCount()));
               }

               return true;
            } catch (Exception var12) {
               DebugLog.log("problem with " + var1.getScriptName());
               var12.printStackTrace();
               return false;
            }
         }
      }
   }

   private void AddVehicles_TrafficJam_W(IsoMetaGrid.Zone var1, String var2) {
      int var5;
      for(var5 = var1.y - this.wy * 10 + 1; var5 < 0; var5 += 3) {
      }

      int var6;
      for(var6 = var1.x - this.wx * 10 + 3; var6 < 0; var6 += 6) {
      }

      for(int var7 = var5; var7 < 10 && this.wy * 10 + var7 < var1.y + var1.h; var7 += 3 + Rand.Next(1)) {
         for(int var8 = var6; var8 < 10 && this.wx * 10 + var8 < var1.x + var1.w; var8 += 6 + Rand.Next(1)) {
            IsoGridSquare var9 = this.getGridSquare(var8, var7, 0);
            if (var9 != null) {
               VehicleType var10 = VehicleType.getRandomVehicleType(var2);
               if (var10 == null) {
                  System.out.println("Can't find car: " + var2);
                  break;
               }

               byte var11 = 80;
               if (SystemDisabler.doVehiclesEverywhere || DebugOptions.instance.VehicleSpawnEverywhere.getValue()) {
                  var11 = 100;
               }

               if (Rand.Next(100) <= var11) {
                  BaseVehicle var12 = new BaseVehicle(IsoWorld.instance.CurrentCell);
                  var12.setZone("TrafficJam");
                  var12.setVehicleType(var10.name);
                  if (!this.RandomizeModel(var12, var1, var2, var10)) {
                     return;
                  }

                  var12.setScript();
                  var12.setX((float)var9.x + Rand.Next(0.0F, 1.0F));
                  var12.setY((float)var9.y + Rand.Next(0.0F, 1.0F));
                  var12.setZ((float)var9.z);
                  var12.jniTransform.origin.set(var12.getX() - WorldSimulation.instance.offsetX, var12.getZ(), var12.getY() - WorldSimulation.instance.offsetY);
                  if (this.isGoodVehiclePos(var12, this)) {
                     var12.setSkinIndex(Rand.Next(var12.getSkinCount() - 1));
                     var12.setDir(IsoDirections.W);
                     float var13 = (float)Math.abs(var1.x + var1.w - var9.x);
                     var13 /= 20.0F;
                     var13 = Math.min(2.0F, var13);

                     float var14;
                     for(var14 = var12.getDir().toAngle() + 3.1415927F - 0.25F + Rand.Next(0.0F, var13); (double)var14 > 6.283185307179586D; var14 = (float)((double)var14 - 6.283185307179586D)) {
                     }

                     var12.savedRot.setAngleAxis(var14, 0.0F, 1.0F, 0.0F);
                     var12.jniTransform.setRotation(var12.savedRot);
                     if (doSpawnedVehiclesInInvalidPosition(var12) || GameClient.bClient) {
                        this.vehicles.add(var12);
                     }
                  }
               }
            }
         }
      }

   }

   private void AddVehicles_TrafficJam_E(IsoMetaGrid.Zone var1, String var2) {
      int var5;
      for(var5 = var1.y - this.wy * 10 + 1; var5 < 0; var5 += 3) {
      }

      int var6;
      for(var6 = var1.x - this.wx * 10 + 3; var6 < 0; var6 += 6) {
      }

      for(int var7 = var5; var7 < 10 && this.wy * 10 + var7 < var1.y + var1.h; var7 += 3 + Rand.Next(1)) {
         for(int var8 = var6; var8 < 10 && this.wx * 10 + var8 < var1.x + var1.w; var8 += 6 + Rand.Next(1)) {
            IsoGridSquare var9 = this.getGridSquare(var8, var7, 0);
            if (var9 != null) {
               VehicleType var10 = VehicleType.getRandomVehicleType(var2);
               if (var10 == null) {
                  System.out.println("Can't find car: " + var2);
                  break;
               }

               byte var11 = 80;
               if (SystemDisabler.doVehiclesEverywhere || DebugOptions.instance.VehicleSpawnEverywhere.getValue()) {
                  var11 = 100;
               }

               if (Rand.Next(100) <= var11) {
                  BaseVehicle var12 = new BaseVehicle(IsoWorld.instance.CurrentCell);
                  var12.setZone("TrafficJam");
                  var12.setVehicleType(var10.name);
                  if (!this.RandomizeModel(var12, var1, var2, var10)) {
                     return;
                  }

                  var12.setScript();
                  var12.setX((float)var9.x + Rand.Next(0.0F, 1.0F));
                  var12.setY((float)var9.y + Rand.Next(0.0F, 1.0F));
                  var12.setZ((float)var9.z);
                  var12.jniTransform.origin.set(var12.getX() - WorldSimulation.instance.offsetX, var12.getZ(), var12.getY() - WorldSimulation.instance.offsetY);
                  if (this.isGoodVehiclePos(var12, this)) {
                     var12.setSkinIndex(Rand.Next(var12.getSkinCount() - 1));
                     var12.setDir(IsoDirections.E);
                     float var13 = (float)Math.abs(var1.x + var1.w - var9.x - var1.w);
                     var13 /= 20.0F;
                     var13 = Math.min(2.0F, var13);

                     float var14;
                     for(var14 = var12.getDir().toAngle() + 3.1415927F - 0.25F + Rand.Next(0.0F, var13); (double)var14 > 6.283185307179586D; var14 = (float)((double)var14 - 6.283185307179586D)) {
                     }

                     var12.savedRot.setAngleAxis(var14, 0.0F, 1.0F, 0.0F);
                     var12.jniTransform.setRotation(var12.savedRot);
                     if (doSpawnedVehiclesInInvalidPosition(var12) || GameClient.bClient) {
                        this.vehicles.add(var12);
                     }
                  }
               }
            }
         }
      }

   }

   private void AddVehicles_TrafficJam_S(IsoMetaGrid.Zone var1, String var2) {
      int var5;
      for(var5 = var1.y - this.wy * 10 + 3; var5 < 0; var5 += 6) {
      }

      int var6;
      for(var6 = var1.x - this.wx * 10 + 1; var6 < 0; var6 += 3) {
      }

      for(int var7 = var5; var7 < 10 && this.wy * 10 + var7 < var1.y + var1.h; var7 += 6 + Rand.Next(-1, 1)) {
         for(int var8 = var6; var8 < 10 && this.wx * 10 + var8 < var1.x + var1.w; var8 += 3 + Rand.Next(1)) {
            IsoGridSquare var9 = this.getGridSquare(var8, var7, 0);
            if (var9 != null) {
               VehicleType var10 = VehicleType.getRandomVehicleType(var2);
               if (var10 == null) {
                  System.out.println("Can't find car: " + var2);
                  break;
               }

               byte var11 = 80;
               if (SystemDisabler.doVehiclesEverywhere || DebugOptions.instance.VehicleSpawnEverywhere.getValue()) {
                  var11 = 100;
               }

               if (Rand.Next(100) <= var11) {
                  BaseVehicle var12 = new BaseVehicle(IsoWorld.instance.CurrentCell);
                  var12.setZone("TrafficJam");
                  var12.setVehicleType(var10.name);
                  if (!this.RandomizeModel(var12, var1, var2, var10)) {
                     return;
                  }

                  var12.setScript();
                  var12.setX((float)var9.x + Rand.Next(0.0F, 1.0F));
                  var12.setY((float)var9.y + Rand.Next(0.0F, 1.0F));
                  var12.setZ((float)var9.z);
                  var12.jniTransform.origin.set(var12.getX() - WorldSimulation.instance.offsetX, var12.getZ(), var12.getY() - WorldSimulation.instance.offsetY);
                  if (this.isGoodVehiclePos(var12, this)) {
                     var12.setSkinIndex(Rand.Next(var12.getSkinCount() - 1));
                     var12.setDir(IsoDirections.S);
                     float var13 = (float)Math.abs(var1.y + var1.h - var9.y - var1.h);
                     var13 /= 20.0F;
                     var13 = Math.min(2.0F, var13);

                     float var14;
                     for(var14 = var12.getDir().toAngle() + 3.1415927F - 0.25F + Rand.Next(0.0F, var13); (double)var14 > 6.283185307179586D; var14 = (float)((double)var14 - 6.283185307179586D)) {
                     }

                     var12.savedRot.setAngleAxis(var14, 0.0F, 1.0F, 0.0F);
                     var12.jniTransform.setRotation(var12.savedRot);
                     if (doSpawnedVehiclesInInvalidPosition(var12) || GameClient.bClient) {
                        this.vehicles.add(var12);
                     }
                  }
               }
            }
         }
      }

   }

   private void AddVehicles_TrafficJam_N(IsoMetaGrid.Zone var1, String var2) {
      int var5;
      for(var5 = var1.y - this.wy * 10 + 3; var5 < 0; var5 += 6) {
      }

      int var6;
      for(var6 = var1.x - this.wx * 10 + 1; var6 < 0; var6 += 3) {
      }

      for(int var7 = var5; var7 < 10 && this.wy * 10 + var7 < var1.y + var1.h; var7 += 6 + Rand.Next(-1, 1)) {
         for(int var8 = var6; var8 < 10 && this.wx * 10 + var8 < var1.x + var1.w; var8 += 3 + Rand.Next(1)) {
            IsoGridSquare var9 = this.getGridSquare(var8, var7, 0);
            if (var9 != null) {
               VehicleType var10 = VehicleType.getRandomVehicleType(var2);
               if (var10 == null) {
                  System.out.println("Can't find car: " + var2);
                  break;
               }

               byte var11 = 80;
               if (SystemDisabler.doVehiclesEverywhere || DebugOptions.instance.VehicleSpawnEverywhere.getValue()) {
                  var11 = 100;
               }

               if (Rand.Next(100) <= var11) {
                  BaseVehicle var12 = new BaseVehicle(IsoWorld.instance.CurrentCell);
                  var12.setZone("TrafficJam");
                  var12.setVehicleType(var10.name);
                  if (!this.RandomizeModel(var12, var1, var2, var10)) {
                     return;
                  }

                  var12.setScript();
                  var12.setX((float)var9.x + Rand.Next(0.0F, 1.0F));
                  var12.setY((float)var9.y + Rand.Next(0.0F, 1.0F));
                  var12.setZ((float)var9.z);
                  var12.jniTransform.origin.set(var12.getX() - WorldSimulation.instance.offsetX, var12.getZ(), var12.getY() - WorldSimulation.instance.offsetY);
                  if (this.isGoodVehiclePos(var12, this)) {
                     var12.setSkinIndex(Rand.Next(var12.getSkinCount() - 1));
                     var12.setDir(IsoDirections.N);
                     float var13 = (float)Math.abs(var1.y + var1.h - var9.y);
                     var13 /= 20.0F;
                     var13 = Math.min(2.0F, var13);

                     float var14;
                     for(var14 = var12.getDir().toAngle() + 3.1415927F - 0.25F + Rand.Next(0.0F, var13); (double)var14 > 6.283185307179586D; var14 = (float)((double)var14 - 6.283185307179586D)) {
                     }

                     var12.savedRot.setAngleAxis(var14, 0.0F, 1.0F, 0.0F);
                     var12.jniTransform.setRotation(var12.savedRot);
                     if (doSpawnedVehiclesInInvalidPosition(var12) || GameClient.bClient) {
                        this.vehicles.add(var12);
                     }
                  }
               }
            }
         }
      }

   }

   public void AddVehicles() {
      if (SandboxOptions.instance.CarSpawnRate.getValue() != 1) {
         if (VehicleType.vehicles.isEmpty()) {
            VehicleType.init();
         }

         if (!GameClient.bClient) {
            if (SandboxOptions.instance.EnableVehicles.getValue()) {
               WorldSimulation.instance.create();
               IsoMetaCell var1 = IsoWorld.instance.getMetaGrid().getCellData(this.wx / 30, this.wy / 30);
               ArrayList var2 = var1 == null ? null : var1.vehicleZones;

               for(int var3 = 0; var2 != null && var3 < var2.size(); ++var3) {
                  IsoMetaGrid.VehicleZone var4 = (IsoMetaGrid.VehicleZone)var2.get(var3);
                  if (var4.x + var4.w >= this.wx * 10 && var4.y + var4.h >= this.wy * 10 && var4.x < (this.wx + 1) * 10 && var4.y < (this.wy + 1) * 10) {
                     String var5 = var4.name;
                     if (var5.isEmpty()) {
                        var5 = var4.type;
                     }

                     if (SandboxOptions.instance.TrafficJam.getValue()) {
                        if ("TrafficJamW".equalsIgnoreCase(var5)) {
                           this.AddVehicles_TrafficJam_W(var4, var5);
                        }

                        if ("TrafficJamE".equalsIgnoreCase(var5)) {
                           this.AddVehicles_TrafficJam_E(var4, var5);
                        }

                        if ("TrafficJamS".equalsIgnoreCase(var5)) {
                           this.AddVehicles_TrafficJam_S(var4, var5);
                        }

                        if ("TrafficJamN".equalsIgnoreCase(var5)) {
                           this.AddVehicles_TrafficJam_N(var4, var5);
                        }

                        if ("RTrafficJamW".equalsIgnoreCase(var5) && Rand.Next(100) < 10) {
                           this.AddVehicles_TrafficJam_W(var4, var5.replaceFirst("rtraffic", "traffic"));
                        }

                        if ("RTrafficJamE".equalsIgnoreCase(var5) && Rand.Next(100) < 10) {
                           this.AddVehicles_TrafficJam_E(var4, var5.replaceFirst("rtraffic", "traffic"));
                        }

                        if ("RTrafficJamS".equalsIgnoreCase(var5) && Rand.Next(100) < 10) {
                           this.AddVehicles_TrafficJam_S(var4, var5.replaceFirst("rtraffic", "traffic"));
                        }

                        if ("RTrafficJamN".equalsIgnoreCase(var5) && Rand.Next(100) < 10) {
                           this.AddVehicles_TrafficJam_N(var4, var5.replaceFirst("rtraffic", "traffic"));
                        }
                     }

                     if ("JunkYard".equalsIgnoreCase(var5) || "TrailerPark".equalsIgnoreCase(var5) || "ParkingStall".equalsIgnoreCase(var5) || "Farm".equalsIgnoreCase(var5) || "Sport".equalsIgnoreCase(var5) || "Bad".equalsIgnoreCase(var5) || "Medium".equalsIgnoreCase(var5) || "Good".equalsIgnoreCase(var5)) {
                        this.AddVehicles_OnZone(var4, var5);
                     }

                     if ("Fossoil".equalsIgnoreCase(var5) || "McCoy".equalsIgnoreCase(var5) || "Postal".equalsIgnoreCase(var5) || "Spiffo".equalsIgnoreCase(var5) || "Ambulance".equalsIgnoreCase(var5) || "Radio".equalsIgnoreCase(var5) || "Fire".equalsIgnoreCase(var5) || "Police".equalsIgnoreCase(var5) || "Ranger".equalsIgnoreCase(var5)) {
                        this.AddVehicles_OnZone(var4, var5);
                     }

                     if ("Burnt".equalsIgnoreCase(var5)) {
                        this.AddVehicles_OnZone(var4, var5);
                     }

                     if ("TestVehicles".equals(var5)) {
                        this.AddVehicles_ForTest(var4);
                     }
                  }
               }

               IsoMetaChunk var6 = IsoWorld.instance.getMetaChunk(this.wx, this.wy);
               if (var6 != null) {
                  for(int var7 = 0; var7 < var6.numZones(); ++var7) {
                     IsoMetaGrid.Zone var8 = var6.getZone(var7);
                     this.addRandomCarCrash(var8, false);
                  }

               }
            }
         }
      }
   }

   public void addSurvivorInHorde(boolean var1) {
      if (var1 || !IsoWorld.getZombiesDisabled()) {
         IsoMetaChunk var2 = IsoWorld.instance.getMetaChunk(this.wx, this.wy);
         if (var2 != null) {
            for(int var3 = 0; var3 < var2.numZones(); ++var3) {
               IsoMetaGrid.Zone var4 = var2.getZone(var3);
               if (this.canAddSurvivorInHorde(var4, var1)) {
                  byte var5 = 5;
                  float var6 = (float)GameTime.getInstance().getWorldAgeHours() / 24.0F;
                  var6 += (float)((SandboxOptions.instance.TimeSinceApo.getValue() - 1) * 30);
                  int var7 = (int)((float)var5 + var6 * 0.05F);
                  var7 = Math.max(20, var7);
                  if (var1 || Rand.Next(0.0F, 500.0F) < 0.4F * (float)var7) {
                     this.addSurvivorInHorde(var4);
                     if (var1) {
                        break;
                     }
                  }
               }
            }

         }
      }
   }

   private boolean canAddSurvivorInHorde(IsoMetaGrid.Zone var1, boolean var2) {
      if (!var2 && IsoWorld.instance.getTimeSinceLastSurvivorInHorde() > 0) {
         return false;
      } else if (!var2 && IsoWorld.getZombiesDisabled()) {
         return false;
      } else if (!var2 && var1.hourLastSeen != 0) {
         return false;
      } else if (!var2 && var1.haveConstruction) {
         return false;
      } else {
         return "Nav".equals(var1.getType());
      }
   }

   private void addSurvivorInHorde(IsoMetaGrid.Zone var1) {
      ++var1.hourLastSeen;
      IsoWorld.instance.setTimeSinceLastSurvivorInHorde(5000);
      int var2 = Math.max(var1.x, this.wx * 10);
      int var3 = Math.max(var1.y, this.wy * 10);
      int var4 = Math.min(var1.x + var1.w, (this.wx + 1) * 10);
      int var5 = Math.min(var1.y + var1.h, (this.wy + 1) * 10);
      float var6 = (float)var2 + (float)(var4 - var2) / 2.0F;
      float var7 = (float)var3 + (float)(var5 - var3) / 2.0F;
      VirtualZombieManager.instance.choices.clear();
      IsoGridSquare var8 = this.getGridSquare((int)var6 - this.wx * 10, (int)var7 - this.wy * 10, 0);
      if (var8.getBuilding() == null) {
         VirtualZombieManager.instance.choices.add(var8);
         int var9 = Rand.Next(15, 20);

         for(int var10 = 0; var10 < var9; ++var10) {
            IsoZombie var11 = VirtualZombieManager.instance.createRealZombieAlways(Rand.Next(8), false);
            if (var11 != null) {
               var11.dressInRandomOutfit();
               ZombieSpawnRecorder.instance.record(var11, "addSurvivorInHorde");
            }
         }

         IsoZombie var12 = VirtualZombieManager.instance.createRealZombieAlways(Rand.Next(8), false);
         if (var12 != null) {
            ZombieSpawnRecorder.instance.record(var12, "addSurvivorInHorde");
            var12.setAsSurvivor();
         }

      }
   }

   public boolean canAddRandomCarCrash(IsoMetaGrid.Zone var1, boolean var2) {
      if (!var2 && var1.hourLastSeen != 0) {
         return false;
      } else if (!var2 && var1.haveConstruction) {
         return false;
      } else if (!"Nav".equals(var1.getType())) {
         return false;
      } else {
         int var3 = Math.max(var1.x, this.wx * 10);
         int var4 = Math.max(var1.y, this.wy * 10);
         int var5 = Math.min(var1.x + var1.w, (this.wx + 1) * 10);
         int var6 = Math.min(var1.y + var1.h, (this.wy + 1) * 10);
         if (var1.w > 30 && var1.h < 13) {
            return var5 - var3 >= 10 && var6 - var4 >= 5;
         } else if (var1.h > 30 && var1.w < 13) {
            return var5 - var3 >= 5 && var6 - var4 >= 10;
         } else {
            return false;
         }
      }
   }

   public void addRandomCarCrash(IsoMetaGrid.Zone var1, boolean var2) {
      if (this.vehicles.isEmpty()) {
         if ("Nav".equals(var1.getType())) {
            RandomizedVehicleStoryBase.doRandomStory(var1, this, false);
         }
      }
   }

   public static boolean FileExists(int var0, int var1) {
      File var2 = ChunkMapFilenames.instance.getFilename(var0, var1);
      if (var2 == null) {
         var2 = ZomboidFileSystem.instance.getFileInCurrentSave(prefix + var0 + "_" + var1 + ".bin");
      }

      long var3 = 0L;
      return var2.exists();
   }

   private void checkPhysics() {
      if (this.physicsCheck) {
         WorldSimulation.instance.create();
         Bullet.beginUpdateChunk(this);
         byte var1 = 0;
         if (var1 < 8) {
            for(int var2 = 0; var2 < 10; ++var2) {
               for(int var3 = 0; var3 < 10; ++var3) {
                  this.calcPhysics(var3, var2, var1, this.shapes);
                  int var4 = 0;

                  for(int var5 = 0; var5 < 4; ++var5) {
                     if (this.shapes[var5] != null) {
                        bshapes[var4++] = (byte)(this.shapes[var5].ordinal() + 1);
                     }
                  }

                  Bullet.updateChunk(var3, var2, var1, var4, bshapes);
               }
            }
         }

         Bullet.endUpdateChunk();
         this.physicsCheck = false;
      }
   }

   private void calcPhysics(int var1, int var2, int var3, IsoChunk.PhysicsShapes[] var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         var4[var5] = null;
      }

      IsoGridSquare var11 = this.getGridSquare(var1, var2, var3);
      if (var11 != null) {
         int var6 = 0;
         boolean var7;
         int var8;
         if (var3 == 0) {
            var7 = false;

            for(var8 = 0; var8 < var11.getObjects().size(); ++var8) {
               IsoObject var9 = (IsoObject)var11.getObjects().get(var8);
               if (var9.sprite != null && var9.sprite.name != null && (var9.sprite.name.contains("lighting_outdoor_") || var9.sprite.name.equals("recreational_sports_01_21") || var9.sprite.name.equals("recreational_sports_01_19") || var9.sprite.name.equals("recreational_sports_01_32")) && (!var9.getProperties().Is("MoveType") || !"WallObject".equals(var9.getProperties().Val("MoveType")))) {
                  var7 = true;
                  break;
               }
            }

            if (var7) {
               var4[var6++] = IsoChunk.PhysicsShapes.Tree;
            }
         }

         var7 = false;
         if (!var11.getSpecialObjects().isEmpty()) {
            var8 = var11.getSpecialObjects().size();

            for(int var13 = 0; var13 < var8; ++var13) {
               IsoObject var10 = (IsoObject)var11.getSpecialObjects().get(var13);
               if (var10 instanceof IsoThumpable && ((IsoThumpable)var10).isBlockAllTheSquare()) {
                  var7 = true;
                  break;
               }
            }
         }

         PropertyContainer var12 = var11.getProperties();
         if (var11.hasTypes.isSet(IsoObjectType.isMoveAbleObject)) {
            var4[var6++] = IsoChunk.PhysicsShapes.Tree;
         }

         String var14;
         if (var11.hasTypes.isSet(IsoObjectType.tree)) {
            var14 = var11.getProperties().Val("tree");
            String var15 = var11.getProperties().Val("WindType");
            if (var14 == null) {
               var4[var6++] = IsoChunk.PhysicsShapes.Tree;
            }

            if (var14 != null && !var14.equals("1") && (var15 == null || !var15.equals("2") || !var14.equals("2") && !var14.equals("1"))) {
               var4[var6++] = IsoChunk.PhysicsShapes.Tree;
            }
         } else if (!var12.Is(IsoFlagType.solid) && !var12.Is(IsoFlagType.solidtrans) && !var12.Is(IsoFlagType.blocksight) && !var11.HasStairs() && !var7) {
            if (var3 > 0) {
               label206: {
                  if (var11.SolidFloorCached) {
                     if (!var11.SolidFloor) {
                        break label206;
                     }
                  } else if (!var11.TreatAsSolidFloor()) {
                     break label206;
                  }

                  if (var6 == var4.length) {
                     DebugLog.log(DebugType.General, "Error: Too many physics objects on gridsquare: " + var11.x + ", " + var11.y + ", " + var11.z);
                     return;
                  }

                  var4[var6++] = IsoChunk.PhysicsShapes.Floor;
               }
            }
         } else {
            if (var6 == var4.length) {
               DebugLog.log(DebugType.General, "Error: Too many physics objects on gridsquare: " + var11.x + ", " + var11.y + ", " + var11.z);
               return;
            }

            var4[var6++] = IsoChunk.PhysicsShapes.Solid;
         }

         if (!var11.getProperties().Is("CarSlowFactor")) {
            if (var12.Is(IsoFlagType.collideW) || var12.Is(IsoFlagType.windowW) || var11.getProperties().Is(IsoFlagType.DoorWallW) && !var11.getProperties().Is("GarageDoor")) {
               if (var6 == var4.length) {
                  DebugLog.log(DebugType.General, "Error: Too many physics objects on gridsquare: " + var11.x + ", " + var11.y + ", " + var11.z);
                  return;
               }

               var4[var6++] = IsoChunk.PhysicsShapes.WallW;
            }

            if (var12.Is(IsoFlagType.collideN) || var12.Is(IsoFlagType.windowN) || var11.getProperties().Is(IsoFlagType.DoorWallN) && !var11.getProperties().Is("GarageDoor")) {
               if (var6 == var4.length) {
                  DebugLog.log(DebugType.General, "Error: Too many physics objects on gridsquare: " + var11.x + ", " + var11.y + ", " + var11.z);
                  return;
               }

               var4[var6++] = IsoChunk.PhysicsShapes.WallN;
            }

            if (var11.Is("PhysicsShape")) {
               if (var6 == var4.length) {
                  DebugLog.log(DebugType.General, "Error: Too many physics objects on gridsquare: " + var11.x + ", " + var11.y + ", " + var11.z);
                  return;
               }

               var14 = var11.getProperties().Val("PhysicsShape");
               if ("Solid".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.Solid;
               } else if ("WallN".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.WallN;
               } else if ("WallW".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.WallW;
               } else if ("WallS".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.WallS;
               } else if ("WallE".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.WallE;
               } else if ("Tree".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.Tree;
               } else if ("Floor".equals(var14)) {
                  var4[var6++] = IsoChunk.PhysicsShapes.Floor;
               }
            }

         }
      }
   }

   public boolean LoadBrandNew(int var1, int var2) {
      this.wx = var1;
      this.wy = var2;
      if (!CellLoader.LoadCellBinaryChunk(IsoWorld.instance.CurrentCell, var1, var2, this)) {
         return false;
      } else {
         if (!Core.GameMode.equals("Tutorial") && !Core.GameMode.equals("LastStand") && !GameClient.bClient) {
            this.addZombies = true;
         }

         return true;
      }
   }

   public boolean LoadOrCreate(int var1, int var2, ByteBuffer var3) {
      this.wx = var1;
      this.wy = var2;
      if (var3 != null && !this.blam) {
         return this.LoadFromBuffer(var1, var2, var3);
      } else {
         File var4 = ChunkMapFilenames.instance.getFilename(var1, var2);
         if (var4 == null) {
            var4 = ZomboidFileSystem.instance.getFileInCurrentSave(prefix + var1 + "_" + var2 + ".bin");
         }

         if (var4.exists() && !this.blam) {
            try {
               this.LoadFromDisk();
            } catch (Exception var6) {
               ExceptionLogger.logException(var6, "Error loading chunk " + var1 + "," + var2);
               if (GameServer.bServer) {
                  LoggerManager.getLogger("map").write("Error loading chunk " + var1 + "," + var2);
                  LoggerManager.getLogger("map").write(var6);
               }

               this.BackupBlam(var1, var2, var6);
               return false;
            }

            if (GameClient.bClient) {
               GameClient.instance.worldObjectsSyncReq.putRequestSyncIsoChunk(this);
            }

            return true;
         } else {
            return this.LoadBrandNew(var1, var2);
         }
      }
   }

   public boolean LoadFromBuffer(int var1, int var2, ByteBuffer var3) {
      this.wx = var1;
      this.wy = var2;
      if (!this.blam) {
         try {
            this.LoadFromDiskOrBuffer(var3);
            return true;
         } catch (Exception var5) {
            ExceptionLogger.logException(var5);
            if (GameServer.bServer) {
               LoggerManager.getLogger("map").write("Error loading chunk " + var1 + "," + var2);
               LoggerManager.getLogger("map").write(var5);
            }

            this.BackupBlam(var1, var2, var5);
            return false;
         }
      } else {
         return this.LoadBrandNew(var1, var2);
      }
   }

   private void ensureSurroundNotNull(int var1, int var2, int var3) {
      IsoCell var4 = IsoWorld.instance.CurrentCell;

      for(int var5 = -1; var5 <= 1; ++var5) {
         for(int var6 = -1; var6 <= 1; ++var6) {
            if ((var5 != 0 || var6 != 0) && var1 + var5 >= 0 && var1 + var5 < 10 && var2 + var6 >= 0 && var2 + var6 < 10) {
               IsoGridSquare var7 = this.getGridSquare(var1 + var5, var2 + var6, var3);
               if (var7 == null) {
                  var7 = IsoGridSquare.getNew(var4, (SliceY)null, this.wx * 10 + var1 + var5, this.wy * 10 + var2 + var6, var3);
                  this.setSquare(var1 + var5, var2 + var6, var3, var7);
               }
            }
         }
      }

   }

   public void loadInWorldStreamerThread() {
      IsoCell var1 = IsoWorld.instance.CurrentCell;

      int var2;
      int var3;
      int var4;
      IsoGridSquare var5;
      for(var2 = 0; var2 <= this.maxLevel; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 10; ++var4) {
               var5 = this.getGridSquare(var4, var3, var2);
               if (var5 == null && var2 == 0) {
                  var5 = IsoGridSquare.getNew(IsoWorld.instance.CurrentCell, (SliceY)null, this.wx * 10 + var4, this.wy * 10 + var3, var2);
                  this.setSquare(var4, var3, var2, var5);
               }

               if (var2 == 0 && var5.getFloor() == null) {
                  DebugLog.log("ERROR: added floor at " + var5.x + "," + var5.y + "," + var5.z + " because there wasn't one");
                  IsoObject var6 = IsoObject.getNew();
                  var6.sprite = IsoSprite.getSprite(IsoSpriteManager.instance, (String)"carpentry_02_58", 0);
                  var6.square = var5;
                  var5.Objects.add(0, var6);
               }

               if (var5 != null) {
                  if (var2 > 0 && !var5.getObjects().isEmpty()) {
                     this.ensureSurroundNotNull(var4, var3, var2);

                     for(int var8 = var2 - 1; var8 > 0; --var8) {
                        IsoGridSquare var7 = this.getGridSquare(var4, var3, var8);
                        if (var7 == null) {
                           var7 = IsoGridSquare.getNew(var1, (SliceY)null, this.wx * 10 + var4, this.wy * 10 + var3, var8);
                           this.setSquare(var4, var3, var8, var7);
                           this.ensureSurroundNotNull(var4, var3, var8);
                        }
                     }
                  }

                  var5.RecalcProperties();
               }
            }
         }
      }

      assert chunkGetter.chunk == null;

      chunkGetter.chunk = this;

      for(var2 = 0; var2 < 10; ++var2) {
         label136:
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = this.maxLevel; var4 > 0; --var4) {
               var5 = this.getGridSquare(var3, var2, var4);
               if (var5 != null && var5.Is(IsoFlagType.solidfloor)) {
                  --var4;

                  while(true) {
                     if (var4 < 0) {
                        continue label136;
                     }

                     var5 = this.getGridSquare(var3, var2, var4);
                     if (var5 != null && !var5.haveRoof) {
                        var5.haveRoof = true;
                        var5.getProperties().UnSet(IsoFlagType.exterior);
                     }

                     --var4;
                  }
               }
            }
         }
      }

      for(var2 = 0; var2 <= this.maxLevel; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 10; ++var4) {
               var5 = this.getGridSquare(var4, var3, var2);
               if (var5 != null) {
                  var5.RecalcAllWithNeighbours(true, chunkGetter);
               }
            }
         }
      }

      chunkGetter.chunk = null;

      for(var2 = 0; var2 <= this.maxLevel; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 10; ++var4) {
               var5 = this.getGridSquare(var4, var3, var2);
               if (var5 != null) {
                  var5.propertiesDirty = true;
               }
            }
         }
      }

   }

   private void RecalcAllWithNeighbour(IsoGridSquare var1, IsoDirections var2, int var3) {
      byte var4 = 0;
      byte var5 = 0;
      if (var2 != IsoDirections.W && var2 != IsoDirections.NW && var2 != IsoDirections.SW) {
         if (var2 == IsoDirections.E || var2 == IsoDirections.NE || var2 == IsoDirections.SE) {
            var4 = 1;
         }
      } else {
         var4 = -1;
      }

      if (var2 != IsoDirections.N && var2 != IsoDirections.NW && var2 != IsoDirections.NE) {
         if (var2 == IsoDirections.S || var2 == IsoDirections.SW || var2 == IsoDirections.SE) {
            var5 = 1;
         }
      } else {
         var5 = -1;
      }

      int var6 = var1.getX() + var4;
      int var7 = var1.getY() + var5;
      int var8 = var1.getZ() + var3;
      IsoGridSquare var9 = var3 == 0 ? var1.nav[var2.index()] : IsoWorld.instance.CurrentCell.getGridSquare(var6, var7, var8);
      if (var9 != null) {
         var1.ReCalculateCollide(var9);
         var9.ReCalculateCollide(var1);
         var1.ReCalculatePathFind(var9);
         var9.ReCalculatePathFind(var1);
         var1.ReCalculateVisionBlocked(var9);
         var9.ReCalculateVisionBlocked(var1);
      }

      if (var3 == 0) {
         switch(var2) {
         case E:
            if (var9 == null) {
               var1.e = null;
            } else {
               var1.e = var1.testPathFindAdjacent((IsoMovingObject)null, 1, 0, 0) ? null : var9;
               var9.w = var9.testPathFindAdjacent((IsoMovingObject)null, -1, 0, 0) ? null : var1;
            }
            break;
         case W:
            if (var9 == null) {
               var1.w = null;
            } else {
               var1.w = var1.testPathFindAdjacent((IsoMovingObject)null, -1, 0, 0) ? null : var9;
               var9.e = var9.testPathFindAdjacent((IsoMovingObject)null, 1, 0, 0) ? null : var1;
            }
            break;
         case N:
            if (var9 == null) {
               var1.n = null;
            } else {
               var1.n = var1.testPathFindAdjacent((IsoMovingObject)null, 0, -1, 0) ? null : var9;
               var9.s = var9.testPathFindAdjacent((IsoMovingObject)null, 0, 1, 0) ? null : var1;
            }
            break;
         case S:
            if (var9 == null) {
               var1.s = null;
            } else {
               var1.s = var1.testPathFindAdjacent((IsoMovingObject)null, 0, 1, 0) ? null : var9;
               var9.n = var9.testPathFindAdjacent((IsoMovingObject)null, 0, -1, 0) ? null : var1;
            }
            break;
         case NW:
            if (var9 == null) {
               var1.nw = null;
            } else {
               var1.nw = var1.testPathFindAdjacent((IsoMovingObject)null, -1, -1, 0) ? null : var9;
               var9.se = var9.testPathFindAdjacent((IsoMovingObject)null, 1, 1, 0) ? null : var1;
            }
            break;
         case NE:
            if (var9 == null) {
               var1.ne = null;
            } else {
               var1.ne = var1.testPathFindAdjacent((IsoMovingObject)null, 1, -1, 0) ? null : var9;
               var9.sw = var9.testPathFindAdjacent((IsoMovingObject)null, -1, 1, 0) ? null : var1;
            }
            break;
         case SE:
            if (var9 == null) {
               var1.se = null;
            } else {
               var1.se = var1.testPathFindAdjacent((IsoMovingObject)null, 1, 1, 0) ? null : var9;
               var9.nw = var9.testPathFindAdjacent((IsoMovingObject)null, -1, -1, 0) ? null : var1;
            }
            break;
         case SW:
            if (var9 == null) {
               var1.sw = null;
            } else {
               var1.sw = var1.testPathFindAdjacent((IsoMovingObject)null, -1, 1, 0) ? null : var9;
               var9.ne = var9.testPathFindAdjacent((IsoMovingObject)null, 1, -1, 0) ? null : var1;
            }
         }
      }

   }

   private void EnsureSurroundNotNullX(int var1, int var2, int var3) {
      IsoCell var4 = IsoWorld.instance.CurrentCell;

      for(int var5 = var1 - 1; var5 <= var1 + 1; ++var5) {
         if (var5 >= 0 && var5 < 10) {
            IsoGridSquare var6 = this.getGridSquare(var5, var2, var3);
            if (var6 == null) {
               var6 = IsoGridSquare.getNew(var4, (SliceY)null, this.wx * 10 + var5, this.wy * 10 + var2, var3);
               var4.ConnectNewSquare(var6, false);
            }
         }
      }

   }

   private void EnsureSurroundNotNullY(int var1, int var2, int var3) {
      IsoCell var4 = IsoWorld.instance.CurrentCell;

      for(int var5 = var2 - 1; var5 <= var2 + 1; ++var5) {
         if (var5 >= 0 && var5 < 10) {
            IsoGridSquare var6 = this.getGridSquare(var1, var5, var3);
            if (var6 == null) {
               var6 = IsoGridSquare.getNew(var4, (SliceY)null, this.wx * 10 + var1, this.wy * 10 + var5, var3);
               var4.ConnectNewSquare(var6, false);
            }
         }
      }

   }

   private void EnsureSurroundNotNull(int var1, int var2, int var3) {
      IsoCell var4 = IsoWorld.instance.CurrentCell;
      IsoGridSquare var5 = this.getGridSquare(var1, var2, var3);
      if (var5 == null) {
         var5 = IsoGridSquare.getNew(var4, (SliceY)null, this.wx * 10 + var1, this.wy * 10 + var2, var3);
         var4.ConnectNewSquare(var5, false);
      }
   }

   public void loadInMainThread() {
      IsoCell var1 = IsoWorld.instance.CurrentCell;
      IsoChunk var2 = var1.getChunk(this.wx - 1, this.wy);
      IsoChunk var3 = var1.getChunk(this.wx, this.wy - 1);
      IsoChunk var4 = var1.getChunk(this.wx + 1, this.wy);
      IsoChunk var5 = var1.getChunk(this.wx, this.wy + 1);
      IsoChunk var6 = var1.getChunk(this.wx - 1, this.wy - 1);
      IsoChunk var7 = var1.getChunk(this.wx + 1, this.wy - 1);
      IsoChunk var8 = var1.getChunk(this.wx + 1, this.wy + 1);
      IsoChunk var9 = var1.getChunk(this.wx - 1, this.wy + 1);

      IsoGridSquare var14;
      int var15;
      int var16;
      for(var15 = 1; var15 < 8; ++var15) {
         for(var16 = 0; var16 < 10; ++var16) {
            if (var3 != null) {
               var14 = var3.getGridSquare(var16, 9, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  this.EnsureSurroundNotNullX(var16, 0, var15);
               }
            }

            if (var5 != null) {
               var14 = var5.getGridSquare(var16, 0, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  this.EnsureSurroundNotNullX(var16, 9, var15);
               }
            }
         }

         for(var16 = 0; var16 < 10; ++var16) {
            if (var2 != null) {
               var14 = var2.getGridSquare(9, var16, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  this.EnsureSurroundNotNullY(0, var16, var15);
               }
            }

            if (var4 != null) {
               var14 = var4.getGridSquare(0, var16, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  this.EnsureSurroundNotNullY(9, var16, var15);
               }
            }
         }

         if (var6 != null) {
            var14 = var6.getGridSquare(9, 9, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               this.EnsureSurroundNotNull(0, 0, var15);
            }
         }

         if (var7 != null) {
            var14 = var7.getGridSquare(0, 9, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               this.EnsureSurroundNotNull(9, 0, var15);
            }
         }

         if (var8 != null) {
            var14 = var8.getGridSquare(0, 0, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               this.EnsureSurroundNotNull(9, 9, var15);
            }
         }

         if (var9 != null) {
            var14 = var9.getGridSquare(9, 0, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               this.EnsureSurroundNotNull(0, 9, var15);
            }
         }
      }

      for(var15 = 1; var15 < 8; ++var15) {
         for(var16 = 0; var16 < 10; ++var16) {
            if (var3 != null) {
               var14 = this.getGridSquare(var16, 0, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  var3.EnsureSurroundNotNullX(var16, 9, var15);
               }
            }

            if (var5 != null) {
               var14 = this.getGridSquare(var16, 9, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  var5.EnsureSurroundNotNullX(var16, 0, var15);
               }
            }
         }

         for(var16 = 0; var16 < 10; ++var16) {
            if (var2 != null) {
               var14 = this.getGridSquare(0, var16, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  var2.EnsureSurroundNotNullY(9, var16, var15);
               }
            }

            if (var4 != null) {
               var14 = this.getGridSquare(9, var16, var15);
               if (var14 != null && !var14.getObjects().isEmpty()) {
                  var4.EnsureSurroundNotNullY(0, var16, var15);
               }
            }
         }

         if (var6 != null) {
            var14 = this.getGridSquare(0, 0, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               var6.EnsureSurroundNotNull(9, 9, var15);
            }
         }

         if (var7 != null) {
            var14 = this.getGridSquare(9, 0, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               var7.EnsureSurroundNotNull(0, 9, var15);
            }
         }

         if (var8 != null) {
            var14 = this.getGridSquare(9, 9, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               var8.EnsureSurroundNotNull(0, 0, var15);
            }
         }

         if (var9 != null) {
            var14 = this.getGridSquare(0, 9, var15);
            if (var14 != null && !var14.getObjects().isEmpty()) {
               var9.EnsureSurroundNotNull(9, 0, var15);
            }
         }
      }

      for(var15 = 0; var15 <= this.maxLevel; ++var15) {
         for(var16 = 0; var16 < 10; ++var16) {
            for(int var17 = 0; var17 < 10; ++var17) {
               var14 = this.getGridSquare(var17, var16, var15);
               if (var14 != null) {
                  if (var17 == 0 || var17 == 9 || var16 == 0 || var16 == 9) {
                     IsoWorld.instance.CurrentCell.DoGridNav(var14, IsoGridSquare.cellGetSquare);

                     for(int var18 = -1; var18 <= 1; ++var18) {
                        if (var17 == 0) {
                           this.RecalcAllWithNeighbour(var14, IsoDirections.W, var18);
                           this.RecalcAllWithNeighbour(var14, IsoDirections.NW, var18);
                           this.RecalcAllWithNeighbour(var14, IsoDirections.SW, var18);
                        } else if (var17 == 9) {
                           this.RecalcAllWithNeighbour(var14, IsoDirections.E, var18);
                           this.RecalcAllWithNeighbour(var14, IsoDirections.NE, var18);
                           this.RecalcAllWithNeighbour(var14, IsoDirections.SE, var18);
                        }

                        if (var16 == 0) {
                           this.RecalcAllWithNeighbour(var14, IsoDirections.N, var18);
                           if (var17 != 0) {
                              this.RecalcAllWithNeighbour(var14, IsoDirections.NW, var18);
                           }

                           if (var17 != 9) {
                              this.RecalcAllWithNeighbour(var14, IsoDirections.NE, var18);
                           }
                        } else if (var16 == 9) {
                           this.RecalcAllWithNeighbour(var14, IsoDirections.S, var18);
                           if (var17 != 0) {
                              this.RecalcAllWithNeighbour(var14, IsoDirections.SW, var18);
                           }

                           if (var17 != 9) {
                              this.RecalcAllWithNeighbour(var14, IsoDirections.SE, var18);
                           }
                        }
                     }

                     IsoGridSquare var22 = var14.nav[IsoDirections.N.index()];
                     IsoGridSquare var19 = var14.nav[IsoDirections.S.index()];
                     IsoGridSquare var20 = var14.nav[IsoDirections.W.index()];
                     IsoGridSquare var21 = var14.nav[IsoDirections.E.index()];
                     if (var22 != null && var20 != null && (var17 == 0 || var16 == 0)) {
                        this.RecalcAllWithNeighbour(var22, IsoDirections.W, 0);
                     }

                     if (var22 != null && var21 != null && (var17 == 9 || var16 == 0)) {
                        this.RecalcAllWithNeighbour(var22, IsoDirections.E, 0);
                     }

                     if (var19 != null && var20 != null && (var17 == 0 || var16 == 9)) {
                        this.RecalcAllWithNeighbour(var19, IsoDirections.W, 0);
                     }

                     if (var19 != null && var21 != null && (var17 == 9 || var16 == 9)) {
                        this.RecalcAllWithNeighbour(var19, IsoDirections.E, 0);
                     }
                  }

                  IsoRoom var23 = var14.getRoom();
                  if (var23 != null) {
                     var23.addSquare(var14);
                  }
               }
            }
         }
      }

      for(var15 = 0; var15 < 4; ++var15) {
         if (var2 != null) {
            var2.lightCheck[var15] = true;
         }

         if (var3 != null) {
            var3.lightCheck[var15] = true;
         }

         if (var4 != null) {
            var4.lightCheck[var15] = true;
         }

         if (var5 != null) {
            var5.lightCheck[var15] = true;
         }

         if (var6 != null) {
            var6.lightCheck[var15] = true;
         }

         if (var7 != null) {
            var7.lightCheck[var15] = true;
         }

         if (var8 != null) {
            var8.lightCheck[var15] = true;
         }

         if (var9 != null) {
            var9.lightCheck[var15] = true;
         }
      }

      IsoLightSwitch.chunkLoaded(this);
   }

   /** @deprecated */
   @Deprecated
   public void recalcNeighboursNow() {
      IsoCell var1 = IsoWorld.instance.CurrentCell;

      int var2;
      int var3;
      int var4;
      IsoGridSquare var5;
      for(var2 = 0; var2 < 10; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 8; ++var4) {
               var5 = this.getGridSquare(var2, var3, var4);
               if (var5 != null) {
                  if (var4 > 0 && !var5.getObjects().isEmpty()) {
                     var5.EnsureSurroundNotNull();

                     for(int var6 = var4 - 1; var6 > 0; --var6) {
                        IsoGridSquare var7 = this.getGridSquare(var2, var3, var6);
                        if (var7 == null) {
                           var7 = IsoGridSquare.getNew(var1, (SliceY)null, this.wx * 10 + var2, this.wy * 10 + var3, var6);
                           var1.ConnectNewSquare(var7, false);
                        }
                     }
                  }

                  var5.RecalcProperties();
               }
            }
         }
      }

      for(var2 = 1; var2 < 8; ++var2) {
         IsoGridSquare var8;
         for(var3 = -1; var3 < 11; ++var3) {
            var8 = var1.getGridSquare(this.wx * 10 + var3, this.wy * 10 - 1, var2);
            if (var8 != null && !var8.getObjects().isEmpty()) {
               var8.EnsureSurroundNotNull();
            }

            var8 = var1.getGridSquare(this.wx * 10 + var3, this.wy * 10 + 10, var2);
            if (var8 != null && !var8.getObjects().isEmpty()) {
               var8.EnsureSurroundNotNull();
            }
         }

         for(var3 = 0; var3 < 10; ++var3) {
            var8 = var1.getGridSquare(this.wx * 10 - 1, this.wy * 10 + var3, var2);
            if (var8 != null && !var8.getObjects().isEmpty()) {
               var8.EnsureSurroundNotNull();
            }

            var8 = var1.getGridSquare(this.wx * 10 + 10, this.wy * 10 + var3, var2);
            if (var8 != null && !var8.getObjects().isEmpty()) {
               var8.EnsureSurroundNotNull();
            }
         }
      }

      for(var2 = 0; var2 < 10; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 8; ++var4) {
               var5 = this.getGridSquare(var2, var3, var4);
               if (var5 != null) {
                  var5.RecalcAllWithNeighbours(true);
                  IsoRoom var9 = var5.getRoom();
                  if (var9 != null) {
                     var9.addSquare(var5);
                  }
               }
            }
         }
      }

      for(var2 = 0; var2 < 10; ++var2) {
         for(var3 = 0; var3 < 10; ++var3) {
            for(var4 = 0; var4 < 8; ++var4) {
               var5 = this.getGridSquare(var2, var3, var4);
               if (var5 != null) {
                  var5.propertiesDirty = true;
               }
            }
         }
      }

      IsoLightSwitch.chunkLoaded(this);
   }

   public void updateBuildings() {
   }

   public static void updatePlayerInBullet() {
      ArrayList var0 = GameServer.getPlayers();
      Bullet.updatePlayerList(var0);
   }

   public void update() {
      this.checkPhysics();
      if (!this.loadedPhysics) {
         this.loadedPhysics = true;

         for(int var1 = 0; var1 < this.vehicles.size(); ++var1) {
            ((BaseVehicle)this.vehicles.get(var1)).chunk = this;
         }
      }

      if (this.vehiclesForAddToWorld != null) {
         synchronized(this.vehiclesForAddToWorldLock) {
            for(int var2 = 0; var2 < this.vehiclesForAddToWorld.size(); ++var2) {
               ((BaseVehicle)this.vehiclesForAddToWorld.get(var2)).addToWorld();
            }

            this.vehiclesForAddToWorld.clear();
            this.vehiclesForAddToWorld = null;
         }
      }

   }

   public void setSquare(int var1, int var2, int var3, IsoGridSquare var4) {
      assert var4 == null || var4.x - this.wx * 10 == var1 && var4.y - this.wy * 10 == var2 && var4.z == var3;

      this.squares[var3][var2 * 10 + var1] = var4;
      if (var4 != null) {
         var4.chunk = this;
         if (var4.z > this.maxLevel) {
            this.maxLevel = var4.z;
         }
      }

   }

   public IsoGridSquare getGridSquare(int var1, int var2, int var3) {
      return var1 >= 0 && var1 < 10 && var2 >= 0 && var2 < 10 && var3 < 8 && var3 >= 0 ? this.squares[var3][var2 * 10 + var1] : null;
   }

   public IsoRoom getRoom(int var1) {
      return this.lotheader.getRoom(var1);
   }

   public void removeFromWorld() {
      loadGridSquare.remove(this);

      try {
         MapCollisionData.instance.removeChunkFromWorld(this);
         ZombiePopulationManager.instance.removeChunkFromWorld(this);
         PolygonalMap2.instance.removeChunkFromWorld(this);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      byte var1 = 100;

      int var2;
      for(var2 = 0; var2 < 8; ++var2) {
         for(int var3 = 0; var3 < var1; ++var3) {
            IsoGridSquare var4 = this.squares[var2][var3];
            if (var4 != null) {
               RainManager.RemoveAllOn(var4);
               var4.clearWater();
               var4.clearPuddles();
               if (var4.getRoom() != null) {
                  var4.getRoom().removeSquare(var4);
               }

               if (var4.zone != null) {
                  var4.zone.removeSquare(var4);
               }

               ArrayList var5 = var4.getMovingObjects();

               int var6;
               IsoMovingObject var7;
               for(var6 = 0; var6 < var5.size(); ++var6) {
                  var7 = (IsoMovingObject)var5.get(var6);
                  if (var7 instanceof IsoSurvivor) {
                     IsoWorld.instance.CurrentCell.getSurvivorList().remove(var7);
                     var7.Despawn();
                  }

                  var7.removeFromWorld();
                  var7.current = var7.last = null;
                  if (!var5.contains(var7)) {
                     --var6;
                  }
               }

               var5.clear();

               for(var6 = 0; var6 < var4.getObjects().size(); ++var6) {
                  IsoObject var10 = (IsoObject)var4.getObjects().get(var6);
                  var10.removeFromWorld();
               }

               for(var6 = 0; var6 < var4.getStaticMovingObjects().size(); ++var6) {
                  var7 = (IsoMovingObject)var4.getStaticMovingObjects().get(var6);
                  var7.removeFromWorld();
               }

               var6 = IsoDirections.N.index();
               int var11 = IsoDirections.S.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].s = null;
               }

               var6 = IsoDirections.NW.index();
               var11 = IsoDirections.SE.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].se = null;
               }

               var6 = IsoDirections.W.index();
               var11 = IsoDirections.E.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].e = null;
               }

               var6 = IsoDirections.SW.index();
               var11 = IsoDirections.NE.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].ne = null;
               }

               var6 = IsoDirections.S.index();
               var11 = IsoDirections.N.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].n = null;
               }

               var6 = IsoDirections.SE.index();
               var11 = IsoDirections.NW.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].nw = null;
               }

               var6 = IsoDirections.E.index();
               var11 = IsoDirections.W.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].w = null;
               }

               var6 = IsoDirections.NE.index();
               var11 = IsoDirections.SW.index();
               if (var4.nav[var6] != null && var4.nav[var6].chunk != var4.chunk) {
                  var4.nav[var6].nav[var11] = null;
                  var4.nav[var6].sw = null;
               }
            }
         }
      }

      for(var2 = 0; var2 < this.vehicles.size(); ++var2) {
         BaseVehicle var9 = (BaseVehicle)this.vehicles.get(var2);
         if (IsoWorld.instance.CurrentCell.getVehicles().contains(var9) || IsoWorld.instance.CurrentCell.addVehicles.contains(var9)) {
            DebugLog.log("IsoChunk.removeFromWorld: vehicle wasn't removed from world id=" + var9.VehicleID);
            var9.removeFromWorld();
         }
      }

   }

   public void doReuseGridsquares() {
      byte var1 = 100;

      for(int var2 = 0; var2 < 8; ++var2) {
         for(int var3 = 0; var3 < var1; ++var3) {
            IsoGridSquare var4 = this.squares[var2][var3];
            if (var4 != null) {
               LuaEventManager.triggerEvent("ReuseGridsquare", var4);

               for(int var5 = 0; var5 < var4.getObjects().size(); ++var5) {
                  IsoObject var6 = (IsoObject)var4.getObjects().get(var5);
                  if (var6 instanceof IsoTree) {
                     var6.reset();
                     CellLoader.isoTreeCache.add((IsoTree)var6);
                  } else if (var6 instanceof IsoObject && var6.getObjectName().equals("IsoObject")) {
                     var6.reset();
                     CellLoader.isoObjectCache.add(var6);
                  } else {
                     var6.reuseGridSquare();
                  }
               }

               var4.discard();
               this.squares[var2][var3] = null;
            }
         }
      }

      this.resetForStore();

      assert !IsoChunkMap.chunkStore.contains(this);

      IsoChunkMap.chunkStore.add(this);
   }

   private static int bufferSize(int var0) {
      return (var0 + 65536 - 1) / 65536 * 65536;
   }

   private static ByteBuffer ensureCapacity(ByteBuffer var0, int var1) {
      if (var0 == null || var0.capacity() < var1) {
         var0 = ByteBuffer.allocate(bufferSize(var1));
      }

      return var0;
   }

   private static ByteBuffer ensureCapacity(ByteBuffer var0) {
      if (var0 == null) {
         return ByteBuffer.allocate(65536);
      } else if (var0.capacity() - var0.position() < 65536) {
         ByteBuffer var1 = ensureCapacity((ByteBuffer)null, var0.position() + 65536);
         return var1.put(var0.array(), 0, var0.position());
      } else {
         return var0;
      }
   }

   public void LoadFromDisk() throws IOException {
      this.LoadFromDiskOrBuffer((ByteBuffer)null);
   }

   public void LoadFromDiskOrBuffer(ByteBuffer var1) throws IOException {
      sanityCheck.beginLoad(this);

      try {
         ByteBuffer var2;
         if (var1 == null) {
            SliceBufferLoad = SafeRead(prefix, this.wx, this.wy, SliceBufferLoad);
            var2 = SliceBufferLoad;
         } else {
            var2 = var1;
         }

         int var3 = this.wx * 10;
         int var4 = this.wy * 10;
         var3 /= 300;
         var4 /= 300;
         String var5 = ChunkMapFilenames.instance.getHeader(var3, var4);
         if (IsoLot.InfoHeaders.containsKey(var5)) {
            this.lotheader = (LotHeader)IsoLot.InfoHeaders.get(var5);
         }

         IsoCell.wx = this.wx;
         IsoCell.wy = this.wy;
         int var6 = var2.getInt();
         if (var6 > 175) {
            throw new RuntimeException("unknown world version " + var6 + " while reading chunk " + this.wx + "," + this.wy);
         }

         this.bFixed2x = var6 >= 85;
         int var7;
         if (var6 >= 61) {
            var7 = var2.getInt();
            sanityCheck.checkLength((long)var7, (long)var2.limit());
            long var8 = var2.getLong();
            crcLoad.reset();
            crcLoad.update(var2.array(), 16, var2.limit() - 4 - 4 - 8);
            sanityCheck.checkCRC(var8, crcLoad.getValue());
         }

         var7 = 0;
         if (GameClient.bClient || GameServer.bServer) {
            var7 = ServerOptions.getInstance().BloodSplatLifespanDays.getValue();
         }

         float var24 = (float)GameTime.getInstance().getWorldAgeHours();
         int var9 = var2.getInt();

         for(int var10 = 0; var10 < var9; ++var10) {
            IsoFloorBloodSplat var11 = new IsoFloorBloodSplat();
            var11.load(var2, var6);
            if (var11.worldAge > var24) {
               var11.worldAge = var24;
            }

            if (var7 <= 0 || !(var24 - var11.worldAge >= (float)(var7 * 24))) {
               if (var6 < 73 && var11.Type < 8) {
                  var11.index = ++this.nextSplatIndex;
               }

               if (var11.Type < 8) {
                  this.nextSplatIndex = var11.index % 10;
               }

               this.FloorBloodSplats.add(var11);
            }
         }

         IsoMetaGrid var25 = IsoWorld.instance.getMetaGrid();
         int var26 = 0;

         int var12;
         int var13;
         byte var15;
         while(true) {
            if (var26 >= 8) {
               if (var6 >= 45) {
                  this.getErosionData().load(var2, var6);
                  this.getErosionData().set(this);
               }
               break;
            }

            for(var12 = 0; var12 < 10; ++var12) {
               for(var13 = 0; var13 < 10; ++var13) {
                  IsoGridSquare var14 = null;
                  var15 = var2.get();
                  if (var15 == 1) {
                     if (var14 == null) {
                        var14 = IsoGridSquare.getNew(IsoWorld.instance.CurrentCell, (SliceY)null, var12 + this.wx * 10, var13 + this.wy * 10, var26);
                     }

                     var14.chunk = this;
                     if (this.lotheader != null) {
                        RoomDef var16 = var25.getRoomAt(var14.x, var14.y, var14.z);
                        int var17 = var16 != null ? var16.ID : -1;
                        var14.setRoomID(var17);
                        var16 = var25.getEmptyOutsideAt(var14.x, var14.y, var14.z);
                        if (var16 != null) {
                           IsoRoom var18 = this.getRoom(var16.ID);
                           var14.roofHideBuilding = var18 == null ? null : var18.building;
                        }
                     }

                     var14.ResetMasterRegion();
                     this.setSquare(var12, var13, var26, var14);
                  }

                  if (var15 == 1 && var14 != null) {
                     var14.load(var2, var6);
                     var14.FixStackableObjects();
                     if (this.jobType == IsoChunk.JobType.SoftReset) {
                        if (!var14.getStaticMovingObjects().isEmpty()) {
                           var14.getStaticMovingObjects().clear();
                        }

                        for(int var32 = 0; var32 < var14.getObjects().size(); ++var32) {
                           IsoObject var33 = (IsoObject)var14.getObjects().get(var32);
                           var33.softReset();
                           if (var33.getObjectIndex() == -1) {
                              --var32;
                           }
                        }

                        var14.setOverlayDone(false);
                     }
                  }
               }
            }

            ++var26;
         }

         short var27;
         if (var6 >= 127) {
            var27 = var2.getShort();
            if (var27 > 0 && this.generatorsTouchingThisChunk == null) {
               this.generatorsTouchingThisChunk = new ArrayList();
            }

            if (this.generatorsTouchingThisChunk != null) {
               this.generatorsTouchingThisChunk.clear();
            }

            for(var12 = 0; var12 < var27; ++var12) {
               var13 = var2.getInt();
               int var30 = var2.getInt();
               var15 = var2.get();
               IsoGameCharacter.Location var35 = new IsoGameCharacter.Location(var13, var30, var15);
               this.generatorsTouchingThisChunk.add(var35);
            }
         }

         this.vehicles.clear();
         if (!GameClient.bClient) {
            if (var6 >= 91) {
               var27 = var2.getShort();

               for(var12 = 0; var12 < var27; ++var12) {
                  byte var29 = var2.get();
                  byte var31 = var2.get();
                  var15 = var2.get();
                  IsoObject var36 = IsoObject.factoryFromFileInput(IsoWorld.instance.CurrentCell, var2);
                  if (var36 != null && var36 instanceof BaseVehicle) {
                     IsoGridSquare var34 = this.getGridSquare(var29, var31, var15);
                     var36.square = var34;
                     ((IsoMovingObject)var36).current = var34;

                     try {
                        var36.load(var2, var6);
                        this.vehicles.add((BaseVehicle)var36);
                        addFromCheckedVehicles((BaseVehicle)var36);
                        if (this.jobType == IsoChunk.JobType.SoftReset) {
                           var36.softReset();
                        }
                     } catch (Exception var22) {
                        throw new RuntimeException(var22);
                     }
                  }
               }
            }

            if (var6 >= 125) {
               this.lootRespawnHour = var2.getInt();
            }

            if (var6 >= 160) {
               byte var28 = var2.get();

               for(var12 = 0; var12 < var28; ++var12) {
                  var13 = var2.getInt();
                  this.addSpawnedRoom(var13);
               }
            }
         }
      } finally {
         sanityCheck.endLoad(this);
         this.bFixed2x = true;
      }

      if (this.getGridSquare(0, 0, 0) == null && this.getGridSquare(9, 9, 0) == null) {
         throw new RuntimeException("black chunk " + this.wx + "," + this.wy);
      }
   }

   public void doLoadGridsquare() {
      if (!GameServer.bServer) {
         this.loadInMainThread();
      }

      if (this.addZombies && !VehiclesDB2.instance.isChunkSeen(this.wx, this.wy)) {
         this.AddVehicles();
      }

      this.AddZombieZoneStory();
      VehiclesDB2.instance.setChunkSeen(this.wx, this.wy);
      if (this.addZombies) {
         if (IsoWorld.instance.getTimeSinceLastSurvivorInHorde() > 0) {
            IsoWorld.instance.setTimeSinceLastSurvivorInHorde(IsoWorld.instance.getTimeSinceLastSurvivorInHorde() - 1);
         }

         this.addSurvivorInHorde(false);
      }

      this.update();
      if (!GameServer.bServer) {
         FliesSound.instance.chunkLoaded(this);
      }

      if (this.addZombies) {
         int var1 = 5 + SandboxOptions.instance.TimeSinceApo.getValue();
         var1 = Math.min(20, var1);
         if (Rand.Next(var1) == 0) {
            this.AddCorpses(this.wx, this.wy);
         }

         if (Rand.Next(var1 * 2) == 0) {
            this.AddBlood(this.wx, this.wy);
         }
      }

      LoadGridsquarePerformanceWorkaround.init(this.wx, this.wy);
      tempBuildings.clear();
      int var2;
      int var6;
      if (!GameClient.bClient) {
         for(var2 = 0; var2 < this.vehicles.size(); ++var2) {
            BaseVehicle var3 = (BaseVehicle)this.vehicles.get(var2);
            if (!var3.addedToWorld && VehiclesDB2.instance.isVehicleLoaded(var3)) {
               var3.removeFromSquare();
               this.vehicles.remove(var2);
               --var2;
            } else {
               if (!var3.addedToWorld) {
                  var3.addToWorld();
               }

               if (var3.sqlID == -1) {
                  assert false;

                  if (var3.square == null) {
                     float var4 = 5.0E-4F;
                     int var5 = this.wx * 10;
                     var6 = this.wy * 10;
                     int var7 = var5 + 10;
                     int var8 = var6 + 10;
                     float var9 = PZMath.clamp(var3.x, (float)var5 + var4, (float)var7 - var4);
                     float var10 = PZMath.clamp(var3.y, (float)var6 + var4, (float)var8 - var4);
                     var3.square = this.getGridSquare((int)var9 - this.wx * 10, (int)var10 - this.wy * 10, 0);
                  }

                  VehiclesDB2.instance.addVehicle(var3);
               }
            }
         }
      }

      this.m_treeCount = 0;
      this.m_scavengeZone = null;

      int var13;
      int var15;
      for(var2 = 0; var2 <= this.maxLevel; ++var2) {
         for(var13 = 0; var13 < 10; ++var13) {
            for(var15 = 0; var15 < 10; ++var15) {
               IsoGridSquare var18 = this.getGridSquare(var13, var15, var2);
               if (var18 != null && !var18.getObjects().isEmpty()) {
                  for(var6 = 0; var6 < var18.getObjects().size(); ++var6) {
                     IsoObject var23 = (IsoObject)var18.getObjects().get(var6);
                     var23.addToWorld();
                  }

                  if (var18.HasTree()) {
                     ++this.m_treeCount;
                  }

                  if (this.jobType != IsoChunk.JobType.SoftReset) {
                     ErosionMain.LoadGridsquare(var18);
                  }

                  if (this.addZombies) {
                     MapObjects.newGridSquare(var18);
                  }

                  MapObjects.loadGridSquare(var18);
                  LuaEventManager.triggerEvent("LoadGridsquare", var18);
                  LoadGridsquarePerformanceWorkaround.LoadGridsquare(var18);
               }

               if (var18 != null && !var18.getStaticMovingObjects().isEmpty()) {
                  for(var6 = 0; var6 < var18.getStaticMovingObjects().size(); ++var6) {
                     IsoMovingObject var24 = (IsoMovingObject)var18.getStaticMovingObjects().get(var6);
                     var24.addToWorld();
                  }
               }

               if (var18 != null && var18.getBuilding() != null && !tempBuildings.contains(var18.getBuilding())) {
                  tempBuildings.add(var18.getBuilding());
               }
            }
         }
      }

      if (this.jobType != IsoChunk.JobType.SoftReset) {
         ErosionMain.ChunkLoaded(this);
      }

      if (this.jobType != IsoChunk.JobType.SoftReset) {
         SGlobalObjects.chunkLoaded(this.wx, this.wy);
      }

      ReanimatedPlayers.instance.addReanimatedPlayersToChunk(this);
      if (this.jobType != IsoChunk.JobType.SoftReset) {
         MapCollisionData.instance.addChunkToWorld(this);
         ZombiePopulationManager.instance.addChunkToWorld(this);
         PolygonalMap2.instance.addChunkToWorld(this);
         IsoGenerator.chunkLoaded(this);
         LootRespawn.chunkLoaded(this);
      }

      if (!GameServer.bServer) {
         ArrayList var14 = IsoWorld.instance.CurrentCell.roomLights;

         for(var13 = 0; var13 < this.roomLights.size(); ++var13) {
            IsoRoomLight var17 = (IsoRoomLight)this.roomLights.get(var13);
            if (!var14.contains(var17)) {
               var14.add(var17);
            }
         }
      }

      this.roomLights.clear();

      IsoBuilding var16;
      for(var2 = 0; var2 < tempBuildings.size(); ++var2) {
         var16 = (IsoBuilding)tempBuildings.get(var2);
         if (!GameClient.bClient && var16.def != null && var16.def.isFullyStreamedIn()) {
            StashSystem.doBuildingStash(var16.def);
         }

         RandomizedBuildingBase.ChunkLoaded(var16);
      }

      if (!GameClient.bClient && !GameServer.bServer && !tempBuildings.isEmpty()) {
         for(var2 = 0; var2 < tempBuildings.size(); ++var2) {
            var16 = (IsoBuilding)tempBuildings.get(var2);

            for(var15 = 0; var15 < var16.Rooms.size(); ++var15) {
               IsoRoom var20 = (IsoRoom)var16.Rooms.get(var15);
               if (var20.def.bDoneSpawn && !this.isSpawnedRoom(var20.def.ID) && var20.def.intersects(this.wx * 10, this.wy * 10, 10, 10)) {
                  this.addSpawnedRoom(var20.def.ID);
                  VirtualZombieManager.instance.addIndoorZombiesToChunk(this, var20);
               }
            }
         }
      }

      this.checkAdjacentChunks();

      try {
         if (GameServer.bServer) {
            for(var2 = 0; var2 < GameServer.udpEngine.connections.size(); ++var2) {
               UdpConnection var19 = (UdpConnection)GameServer.udpEngine.connections.get(var2);
               if (!var19.chunkObjectState.isEmpty()) {
                  for(var15 = 0; var15 < var19.chunkObjectState.size(); var15 += 2) {
                     short var21 = var19.chunkObjectState.get(var15);
                     short var25 = var19.chunkObjectState.get(var15 + 1);
                     if (var21 == this.wx && var25 == this.wy) {
                        var19.chunkObjectState.remove(var15, 2);
                        var15 -= 2;
                        ByteBufferWriter var26 = var19.startPacket();
                        PacketTypes.doPacket((short)151, var26);
                        var26.putShort((short)this.wx);
                        var26.putShort((short)this.wy);

                        try {
                           if (this.saveObjectState(var26.bb)) {
                              var19.endPacket();
                           } else {
                              var19.cancelPacket();
                           }
                        } catch (Throwable var11) {
                           var11.printStackTrace();
                           var19.cancelPacket();
                        }
                     }
                  }
               }
            }
         }

         if (GameClient.bClient) {
            ByteBufferWriter var22 = GameClient.connection.startPacket();
            PacketTypes.doPacket((short)151, var22);
            var22.putShort((short)this.wx);
            var22.putShort((short)this.wy);
            GameClient.connection.endPacket();
         }
      } catch (Throwable var12) {
         ExceptionLogger.logException(var12);
      }

   }

   private void checkAdjacentChunks() {
      IsoCell var1 = IsoWorld.instance.CurrentCell;

      for(int var2 = -1; var2 <= 1; ++var2) {
         for(int var3 = -1; var3 <= 1; ++var3) {
            if (var3 != 0 || var2 != 0) {
               IsoChunk var4 = var1.getChunk(this.wx + var3, this.wy + var2);
               if (var4 != null) {
                  ++var4.m_adjacentChunkLoadedCounter;
               }
            }
         }
      }

   }

   private void AddZombieZoneStory() {
      IsoMetaChunk var1 = IsoWorld.instance.getMetaChunk(this.wx, this.wy);
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.numZones(); ++var2) {
            IsoMetaGrid.Zone var3 = var1.getZone(var2);
            RandomizedZoneStoryBase.isValidForStory(var3, false);
         }

      }
   }

   public void setCache() {
      IsoWorld.instance.CurrentCell.setCacheChunk(this);
   }

   private static IsoChunk.ChunkLock acquireLock(int var0, int var1) {
      synchronized(Locks) {
         for(int var3 = 0; var3 < Locks.size(); ++var3) {
            if (((IsoChunk.ChunkLock)Locks.get(var3)).wx == var0 && ((IsoChunk.ChunkLock)Locks.get(var3)).wy == var1) {
               return ((IsoChunk.ChunkLock)Locks.get(var3)).ref();
            }
         }

         IsoChunk.ChunkLock var6 = FreeLocks.isEmpty() ? new IsoChunk.ChunkLock(var0, var1) : ((IsoChunk.ChunkLock)FreeLocks.pop()).set(var0, var1);
         Locks.add(var6);
         return var6.ref();
      }
   }

   private static void releaseLock(IsoChunk.ChunkLock var0) {
      synchronized(Locks) {
         if (var0.deref() == 0) {
            Locks.remove(var0);
            FreeLocks.push(var0);
         }

      }
   }

   public void setCacheIncludingNull() {
      if (IsoCell.ENABLE_SQUARE_CACHE) {
         for(int var1 = 0; var1 < 8; ++var1) {
            for(int var2 = 0; var2 < 10; ++var2) {
               for(int var3 = 0; var3 < 10; ++var3) {
                  IsoGridSquare var4 = this.getGridSquare(var2, var3, var1);
                  IsoWorld.instance.CurrentCell.setCacheGridSquare(this.wx * 10 + var2, this.wy * 10 + var3, var1, var4);
               }
            }
         }
      }

   }

   public void Save(boolean var1) throws IOException {
      if (Core.getInstance().isNoSave()) {
         if (!var1 && !GameServer.bServer && this.jobType != IsoChunk.JobType.Convert) {
            WorldReuserThread.instance.addReuseChunk(this);
         }

      } else {
         synchronized(WriteLock) {
            sanityCheck.beginSave(this);

            try {
               File var3 = ChunkMapFilenames.instance.getDir(Core.GameSaveWorld);
               if (!var3.exists()) {
                  var3.mkdir();
               }

               SliceBuffer = this.Save(SliceBuffer, crcSave);
               if (!GameClient.bClient && !GameServer.bServer) {
                  SafeWrite(prefix, this.wx, this.wy, SliceBuffer);
               } else {
                  long var4 = ChunkChecksum.getChecksumIfExists(this.wx, this.wy);
                  crcSave.reset();
                  crcSave.update(SliceBuffer.array(), 0, SliceBuffer.position());
                  if (var4 != crcSave.getValue()) {
                     ChunkChecksum.setChecksum(this.wx, this.wy, crcSave.getValue());
                     SafeWrite(prefix, this.wx, this.wy, SliceBuffer);
                  }
               }

               if (!var1 && !GameServer.bServer) {
                  if (this.jobType != IsoChunk.JobType.Convert) {
                     WorldReuserThread.instance.addReuseChunk(this);
                  } else {
                     this.doReuseGridsquares();
                  }
               }
            } finally {
               sanityCheck.endSave(this);
            }

         }
      }
   }

   public static void SafeWrite(String var0, int var1, int var2, ByteBuffer var3) throws IOException {
      IsoChunk.ChunkLock var4 = acquireLock(var1, var2);
      var4.lockForWriting();

      try {
         File var5 = ChunkMapFilenames.instance.getFilename(var1, var2);
         sanityCheck.beginSaveFile(var5.getAbsolutePath());

         try {
            FileOutputStream var6 = new FileOutputStream(var5);
            var6.getChannel().truncate(0L);
            var6.write(var3.array(), 0, var3.position());
            var6.flush();
            var6.close();
         } finally {
            sanityCheck.endSaveFile();
         }
      } finally {
         var4.unlockForWriting();
         releaseLock(var4);
      }

   }

   public static ByteBuffer SafeRead(String var0, int var1, int var2, ByteBuffer var3) throws IOException {
      IsoChunk.ChunkLock var4 = acquireLock(var1, var2);
      var4.lockForReading();

      try {
         File var5 = ChunkMapFilenames.instance.getFilename(var1, var2);
         if (var5 == null) {
            var5 = ZomboidFileSystem.instance.getFileInCurrentSave(var0 + var1 + "_" + var2 + ".bin");
         }

         sanityCheck.beginLoadFile(var5.getAbsolutePath());

         try {
            FileInputStream var6 = new FileInputStream(var5);
            Throwable var7 = null;

            try {
               var3 = ensureCapacity(var3, (int)var5.length());
               var3.clear();
               int var8 = var6.read(var3.array());
               var3.limit(var8);
            } catch (Throwable var32) {
               var7 = var32;
               throw var32;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var31) {
                        var7.addSuppressed(var31);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } finally {
            sanityCheck.endLoadFile(var5.getAbsolutePath());
         }
      } finally {
         var4.unlockForReading();
         releaseLock(var4);
      }

      return var3;
   }

   public void SaveLoadedChunk(ClientChunkRequest.Chunk var1, CRC32 var2) throws IOException {
      var1.bb = this.Save(var1.bb, var2);
   }

   public ByteBuffer Save(ByteBuffer var1, CRC32 var2) throws IOException {
      var1.rewind();
      var1 = ensureCapacity(var1);
      var1.rewind();
      var1.putInt(175);
      var1.putInt(0);
      var1.putLong(0L);
      int var3 = Math.min(1000, this.FloorBloodSplats.size());
      int var4 = this.FloorBloodSplats.size() - var3;
      var1.putInt(var3);

      int var5;
      for(var5 = var4; var5 < this.FloorBloodSplats.size(); ++var5) {
         IsoFloorBloodSplat var6 = (IsoFloorBloodSplat)this.FloorBloodSplats.get(var5);
         var6.save(var1);
      }

      for(var5 = 0; var5 < 8; ++var5) {
         for(int var12 = 0; var12 < 10; ++var12) {
            for(int var7 = 0; var7 < 10; ++var7) {
               IsoGridSquare var8 = this.getGridSquare(var12, var7, var5);
               var1 = ensureCapacity(var1);
               if (var8 != null && var8.shouldSave()) {
                  var1.put((byte)1);
                  int var9 = var1.position();

                  while(true) {
                     try {
                        var8.save(var1, (ObjectOutputStream)null);
                        break;
                     } catch (BufferOverflowException var11) {
                        DebugLog.log("IsoChunk.Save: BufferOverflowException, growing ByteBuffer");
                        var1 = ensureCapacity(var1);
                        var1.position(var9);
                     }
                  }
               } else {
                  var1.put((byte)0);
               }
            }
         }
      }

      var1 = ensureCapacity(var1);
      this.getErosionData().save(var1);
      if (this.generatorsTouchingThisChunk == null) {
         var1.putShort((short)0);
      } else {
         var1.putShort((short)this.generatorsTouchingThisChunk.size());

         for(var5 = 0; var5 < this.generatorsTouchingThisChunk.size(); ++var5) {
            IsoGameCharacter.Location var13 = (IsoGameCharacter.Location)this.generatorsTouchingThisChunk.get(var5);
            var1.putInt(var13.x);
            var1.putInt(var13.y);
            var1.put((byte)var13.z);
         }
      }

      var1.putShort((short)0);
      if (!GameClient.bClient && !GameWindow.bLoadedAsClient) {
         VehiclesDB2.instance.unloadChunk(this);
      }

      if (GameClient.bClient) {
         var5 = ServerOptions.instance.HoursForLootRespawn.getValue();
         if (var5 > 0 && !(GameTime.getInstance().getWorldAgeHours() < (double)var5)) {
            this.lootRespawnHour = 7 + (int)(GameTime.getInstance().getWorldAgeHours() / (double)var5) * var5;
         } else {
            this.lootRespawnHour = -1;
         }
      }

      var1.putInt(this.lootRespawnHour);

      assert this.m_spawnedRooms.size() <= 127;

      var1.put((byte)this.m_spawnedRooms.size());

      for(var5 = 0; var5 < this.m_spawnedRooms.size(); ++var5) {
         var1.putInt(this.m_spawnedRooms.get(var5));
      }

      var5 = var1.position();
      var2.reset();
      var2.update(var1.array(), 16, var5 - 4 - 4 - 8);
      var1.position(4);
      var1.putInt(var5);
      var1.putLong(var2.getValue());
      var1.position(var5);
      return var1;
   }

   public boolean saveObjectState(ByteBuffer var1) throws IOException {
      boolean var3 = true;

      for(int var4 = 0; var4 < 8; ++var4) {
         for(int var5 = 0; var5 < 10; ++var5) {
            for(int var6 = 0; var6 < 10; ++var6) {
               IsoGridSquare var7 = this.getGridSquare(var6, var5, var4);
               if (var7 != null) {
                  int var8 = var7.getObjects().size();
                  IsoObject[] var9 = (IsoObject[])var7.getObjects().getElements();

                  for(int var10 = 0; var10 < var8; ++var10) {
                     IsoObject var11 = var9[var10];
                     int var12 = var1.position();
                     var1.position(var12 + 2 + 2 + 4 + 2);
                     int var13 = var1.position();
                     var11.saveState(var1);
                     int var14 = var1.position();
                     if (var14 > var13) {
                        var1.position(var12);
                        var1.putShort((short)(var6 + var5 * 10 + var4 * 10 * 10));
                        var1.putShort((short)var10);
                        var1.putInt(var11.getObjectName().hashCode());
                        var1.putShort((short)(var14 - var13));
                        var1.position(var14);
                        var3 = false;
                     } else {
                        var1.position(var12);
                     }
                  }
               }
            }
         }
      }

      if (var3) {
         return false;
      } else {
         var1.putShort((short)-1);
         return true;
      }
   }

   public void loadObjectState(ByteBuffer var1) throws IOException {
      for(short var3 = var1.getShort(); var3 != -1; var3 = var1.getShort()) {
         int var4 = var3 % 10;
         int var5 = var3 / 100;
         int var6 = (var3 - var5 * 10 * 10) / 10;
         short var7 = var1.getShort();
         int var8 = var1.getInt();
         short var9 = var1.getShort();
         int var10 = var1.position();
         IsoGridSquare var11 = this.getGridSquare(var4, var6, var5);
         if (var11 != null && var7 >= 0 && var7 < var11.getObjects().size()) {
            IsoObject var12 = (IsoObject)var11.getObjects().get(var7);
            if (var8 == var12.getObjectName().hashCode()) {
               var12.loadState(var1);

               assert var1.position() == var10 + var9;
            } else {
               var1.position(var10 + var9);
            }
         } else {
            var1.position(var10 + var9);
         }
      }

   }

   public void Blam(int var1, int var2) {
      for(int var3 = 0; var3 < 8; ++var3) {
         for(int var4 = 0; var4 < 10; ++var4) {
            for(int var5 = 0; var5 < 10; ++var5) {
               this.setSquare(var4, var5, var3, (IsoGridSquare)null);
            }
         }
      }

      this.blam = true;
   }

   private void BackupBlam(int var1, int var2, Exception var3) {
      File var4 = ZomboidFileSystem.instance.getFileInCurrentSave("blam");
      var4.mkdirs();

      File var5;
      try {
         var5 = new File(var4 + File.separator + "map_" + var1 + "_" + var2 + "_error.txt");
         FileOutputStream var6 = new FileOutputStream(var5);
         PrintStream var7 = new PrintStream(var6);
         var3.printStackTrace(var7);
         var7.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      var5 = ZomboidFileSystem.instance.getFileInCurrentSave("map_" + var1 + "_" + var2 + ".bin");
      if (var5.exists()) {
         File var10 = new File(var4.getPath() + File.separator + "map_" + var1 + "_" + var2 + ".bin");

         try {
            copyFile(var5, var10);
         } catch (Exception var8) {
            var8.printStackTrace();
         }

      }
   }

   private static void copyFile(File var0, File var1) throws IOException {
      if (!var1.exists()) {
         var1.createNewFile();
      }

      FileChannel var2 = null;
      FileChannel var3 = null;

      try {
         var2 = (new FileInputStream(var0)).getChannel();
         var3 = (new FileOutputStream(var1)).getChannel();
         var3.transferFrom(var2, 0L, var2.size());
      } finally {
         if (var2 != null) {
            var2.close();
         }

         if (var3 != null) {
            var3.close();
         }

      }

   }

   public ErosionData.Chunk getErosionData() {
      if (this.erosion == null) {
         this.erosion = new ErosionData.Chunk();
      }

      return this.erosion;
   }

   private static int newtiledefinitions(int var0, int var1) {
      byte var2 = 1;
      return var2 * 100 * 1000 + 10000 + var0 * 1000 + var1;
   }

   public static int Fix2x(IsoGridSquare var0, int var1) {
      if (var0 != null && var0.chunk != null) {
         if (var0.chunk.bFixed2x) {
            return var1;
         } else {
            HashMap var2 = IsoSpriteManager.instance.NamedMap;
            if (var1 >= newtiledefinitions(140, 48) && var1 <= newtiledefinitions(140, 51)) {
               return -1;
            } else if (var1 >= newtiledefinitions(8, 14) && var1 <= newtiledefinitions(8, 71) && var1 % 8 >= 6) {
               return -1;
            } else if (var1 == newtiledefinitions(92, 2)) {
               return var1 + 20;
            } else if (var1 == newtiledefinitions(92, 20)) {
               return var1 + 1;
            } else if (var1 == newtiledefinitions(92, 21)) {
               return var1 - 1;
            } else if (var1 >= newtiledefinitions(92, 26) && var1 <= newtiledefinitions(92, 29)) {
               return var1 + 6;
            } else if (var1 == newtiledefinitions(11, 16)) {
               return newtiledefinitions(11, 45);
            } else if (var1 == newtiledefinitions(11, 17)) {
               return newtiledefinitions(11, 43);
            } else if (var1 == newtiledefinitions(11, 18)) {
               return newtiledefinitions(11, 41);
            } else if (var1 == newtiledefinitions(11, 19)) {
               return newtiledefinitions(11, 47);
            } else if (var1 == newtiledefinitions(11, 24)) {
               return newtiledefinitions(11, 26);
            } else if (var1 == newtiledefinitions(11, 25)) {
               return newtiledefinitions(11, 27);
            } else if (var1 == newtiledefinitions(27, 42)) {
               return var1 + 1;
            } else if (var1 == newtiledefinitions(27, 43)) {
               return var1 - 1;
            } else if (var1 == newtiledefinitions(27, 44)) {
               return var1 + 3;
            } else if (var1 == newtiledefinitions(27, 47)) {
               return var1 - 2;
            } else if (var1 == newtiledefinitions(27, 45)) {
               return var1 + 1;
            } else if (var1 == newtiledefinitions(27, 46)) {
               return var1 - 2;
            } else if (var1 == newtiledefinitions(34, 4)) {
               return var1 + 1;
            } else if (var1 == newtiledefinitions(34, 5)) {
               return var1 - 1;
            } else if (var1 >= newtiledefinitions(14, 0) && var1 <= newtiledefinitions(14, 7)) {
               return -1;
            } else if (var1 >= newtiledefinitions(14, 8) && var1 <= newtiledefinitions(14, 12)) {
               return var1 + 72;
            } else if (var1 == newtiledefinitions(14, 13)) {
               return var1 + 71;
            } else if (var1 >= newtiledefinitions(14, 16) && var1 <= newtiledefinitions(14, 17)) {
               return var1 + 72;
            } else if (var1 == newtiledefinitions(14, 18)) {
               return var1 + 73;
            } else if (var1 == newtiledefinitions(14, 19)) {
               return var1 + 66;
            } else if (var1 == newtiledefinitions(14, 20)) {
               return -1;
            } else if (var1 == newtiledefinitions(14, 21)) {
               return newtiledefinitions(14, 89);
            } else if (var1 == newtiledefinitions(21, 0)) {
               return newtiledefinitions(125, 16);
            } else if (var1 == newtiledefinitions(21, 1)) {
               return newtiledefinitions(125, 32);
            } else if (var1 == newtiledefinitions(21, 2)) {
               return newtiledefinitions(125, 48);
            } else if (var1 == newtiledefinitions(26, 0)) {
               return newtiledefinitions(26, 6);
            } else if (var1 == newtiledefinitions(26, 6)) {
               return newtiledefinitions(26, 0);
            } else if (var1 == newtiledefinitions(26, 1)) {
               return newtiledefinitions(26, 7);
            } else if (var1 == newtiledefinitions(26, 7)) {
               return newtiledefinitions(26, 1);
            } else if (var1 == newtiledefinitions(26, 8)) {
               return newtiledefinitions(26, 14);
            } else if (var1 == newtiledefinitions(26, 14)) {
               return newtiledefinitions(26, 8);
            } else if (var1 == newtiledefinitions(26, 9)) {
               return newtiledefinitions(26, 15);
            } else if (var1 == newtiledefinitions(26, 15)) {
               return newtiledefinitions(26, 9);
            } else if (var1 == newtiledefinitions(26, 16)) {
               return newtiledefinitions(26, 22);
            } else if (var1 == newtiledefinitions(26, 22)) {
               return newtiledefinitions(26, 16);
            } else if (var1 == newtiledefinitions(26, 17)) {
               return newtiledefinitions(26, 23);
            } else if (var1 == newtiledefinitions(26, 23)) {
               return newtiledefinitions(26, 17);
            } else {
               int var3;
               if (var1 >= newtiledefinitions(148, 0) && var1 <= newtiledefinitions(148, 16)) {
                  var3 = var1 - newtiledefinitions(148, 0);
                  return newtiledefinitions(160, var3);
               } else if (var1 >= newtiledefinitions(42, 44) && var1 <= newtiledefinitions(42, 47) || var1 >= newtiledefinitions(42, 52) && var1 <= newtiledefinitions(42, 55)) {
                  return -1;
               } else if (var1 == newtiledefinitions(43, 24)) {
                  return var1 + 4;
               } else if (var1 == newtiledefinitions(43, 26)) {
                  return var1 + 2;
               } else if (var1 == newtiledefinitions(43, 33)) {
                  return var1 - 4;
               } else if (var1 == newtiledefinitions(44, 0)) {
                  return newtiledefinitions(44, 1);
               } else if (var1 == newtiledefinitions(44, 1)) {
                  return newtiledefinitions(44, 0);
               } else if (var1 == newtiledefinitions(44, 2)) {
                  return newtiledefinitions(44, 7);
               } else if (var1 == newtiledefinitions(44, 3)) {
                  return newtiledefinitions(44, 6);
               } else if (var1 == newtiledefinitions(44, 4)) {
                  return newtiledefinitions(44, 5);
               } else if (var1 == newtiledefinitions(44, 5)) {
                  return newtiledefinitions(44, 4);
               } else if (var1 == newtiledefinitions(44, 6)) {
                  return newtiledefinitions(44, 3);
               } else if (var1 == newtiledefinitions(44, 7)) {
                  return newtiledefinitions(44, 2);
               } else if (var1 == newtiledefinitions(44, 16)) {
                  return newtiledefinitions(44, 45);
               } else if (var1 == newtiledefinitions(44, 17)) {
                  return newtiledefinitions(44, 44);
               } else if (var1 == newtiledefinitions(44, 18)) {
                  return newtiledefinitions(44, 46);
               } else if (var1 >= newtiledefinitions(44, 19) && var1 <= newtiledefinitions(44, 22)) {
                  return var1 + 33;
               } else if (var1 == newtiledefinitions(44, 23)) {
                  return newtiledefinitions(44, 47);
               } else if (var1 == newtiledefinitions(46, 8)) {
                  return newtiledefinitions(46, 5);
               } else if (var1 == newtiledefinitions(46, 14)) {
                  return newtiledefinitions(46, 10);
               } else if (var1 == newtiledefinitions(46, 15)) {
                  return newtiledefinitions(46, 11);
               } else if (var1 == newtiledefinitions(46, 22)) {
                  return newtiledefinitions(46, 14);
               } else if (var1 == newtiledefinitions(46, 23)) {
                  return newtiledefinitions(46, 15);
               } else if (var1 == newtiledefinitions(46, 54)) {
                  return newtiledefinitions(46, 55);
               } else if (var1 == newtiledefinitions(46, 55)) {
                  return newtiledefinitions(46, 54);
               } else if (var1 == newtiledefinitions(106, 32)) {
                  return newtiledefinitions(106, 34);
               } else if (var1 == newtiledefinitions(106, 34)) {
                  return newtiledefinitions(106, 32);
               } else if (var1 != newtiledefinitions(47, 0) && var1 != newtiledefinitions(47, 4)) {
                  if (var1 != newtiledefinitions(47, 1) && var1 != newtiledefinitions(47, 5)) {
                     if (var1 >= newtiledefinitions(47, 8) && var1 <= newtiledefinitions(47, 13)) {
                        return var1 + 8;
                     } else if (var1 >= newtiledefinitions(47, 22) && var1 <= newtiledefinitions(47, 23)) {
                        return var1 - 12;
                     } else if (var1 >= newtiledefinitions(47, 44) && var1 <= newtiledefinitions(47, 47)) {
                        return var1 + 4;
                     } else if (var1 >= newtiledefinitions(47, 48) && var1 <= newtiledefinitions(47, 51)) {
                        return var1 - 4;
                     } else if (var1 == newtiledefinitions(48, 56)) {
                        return newtiledefinitions(48, 58);
                     } else if (var1 == newtiledefinitions(48, 58)) {
                        return newtiledefinitions(48, 56);
                     } else if (var1 == newtiledefinitions(52, 57)) {
                        return newtiledefinitions(52, 58);
                     } else if (var1 == newtiledefinitions(52, 58)) {
                        return newtiledefinitions(52, 59);
                     } else if (var1 == newtiledefinitions(52, 45)) {
                        return newtiledefinitions(52, 44);
                     } else if (var1 == newtiledefinitions(52, 46)) {
                        return newtiledefinitions(52, 45);
                     } else if (var1 == newtiledefinitions(54, 13)) {
                        return newtiledefinitions(54, 18);
                     } else if (var1 == newtiledefinitions(54, 15)) {
                        return newtiledefinitions(54, 19);
                     } else if (var1 == newtiledefinitions(54, 21)) {
                        return newtiledefinitions(54, 16);
                     } else if (var1 == newtiledefinitions(54, 22)) {
                        return newtiledefinitions(54, 13);
                     } else if (var1 == newtiledefinitions(54, 23)) {
                        return newtiledefinitions(54, 17);
                     } else if (var1 >= newtiledefinitions(67, 0) && var1 <= newtiledefinitions(67, 16)) {
                        var3 = 64 + Rand.Next(16);
                        return ((IsoSprite)var2.get("f_bushes_1_" + var3)).ID;
                     } else if (var1 == newtiledefinitions(68, 6)) {
                        return -1;
                     } else if (var1 >= newtiledefinitions(68, 16) && var1 <= newtiledefinitions(68, 17)) {
                        return ((IsoSprite)var2.get("d_plants_1_53")).ID;
                     } else if (var1 >= newtiledefinitions(68, 18) && var1 <= newtiledefinitions(68, 23)) {
                        var3 = Rand.Next(4) * 16 + Rand.Next(8);
                        return ((IsoSprite)var2.get("d_plants_1_" + var3)).ID;
                     } else {
                        return var1 >= newtiledefinitions(79, 24) && var1 <= newtiledefinitions(79, 41) ? newtiledefinitions(81, var1 - newtiledefinitions(79, 24)) : var1;
                     }
                  } else {
                     return var1 - 1;
                  }
               } else {
                  return var1 + 1;
               }
            }
         }
      } else {
         return var1;
      }
   }

   public static String Fix2x(String var0) {
      int var2;
      if (Fix2xMap.isEmpty()) {
         HashMap var1 = Fix2xMap;

         for(var2 = 48; var2 <= 51; ++var2) {
            var1.put("blends_streetoverlays_01_" + var2, "");
         }

         var1.put("fencing_01_14", "");
         var1.put("fencing_01_15", "");
         var1.put("fencing_01_22", "");
         var1.put("fencing_01_23", "");
         var1.put("fencing_01_30", "");
         var1.put("fencing_01_31", "");
         var1.put("fencing_01_38", "");
         var1.put("fencing_01_39", "");
         var1.put("fencing_01_46", "");
         var1.put("fencing_01_47", "");
         var1.put("fencing_01_62", "");
         var1.put("fencing_01_63", "");
         var1.put("fencing_01_70", "");
         var1.put("fencing_01_71", "");
         var1.put("fixtures_bathroom_02_2", "fixtures_bathroom_02_22");
         var1.put("fixtures_bathroom_02_20", "fixtures_bathroom_02_21");
         var1.put("fixtures_bathroom_02_21", "fixtures_bathroom_02_20");

         for(var2 = 26; var2 <= 29; ++var2) {
            var1.put("fixtures_bathroom_02_" + var2, "fixtures_bathroom_02_" + (var2 + 6));
         }

         var1.put("fixtures_counters_01_16", "fixtures_counters_01_45");
         var1.put("fixtures_counters_01_17", "fixtures_counters_01_43");
         var1.put("fixtures_counters_01_18", "fixtures_counters_01_41");
         var1.put("fixtures_counters_01_19", "fixtures_counters_01_47");
         var1.put("fixtures_counters_01_24", "fixtures_counters_01_26");
         var1.put("fixtures_counters_01_25", "fixtures_counters_01_27");

         for(var2 = 0; var2 <= 7; ++var2) {
            var1.put("fixtures_railings_01_" + var2, "");
         }

         for(var2 = 8; var2 <= 12; ++var2) {
            var1.put("fixtures_railings_01_" + var2, "fixtures_railings_01_" + (var2 + 72));
         }

         var1.put("fixtures_railings_01_13", "fixtures_railings_01_84");

         for(var2 = 16; var2 <= 17; ++var2) {
            var1.put("fixtures_railings_01_" + var2, "fixtures_railings_01_" + (var2 + 72));
         }

         var1.put("fixtures_railings_01_18", "fixtures_railings_01_91");
         var1.put("fixtures_railings_01_19", "fixtures_railings_01_85");
         var1.put("fixtures_railings_01_20", "");
         var1.put("fixtures_railings_01_21", "fixtures_railings_01_89");
         var1.put("floors_exterior_natural_01_0", "blends_natural_01_16");
         var1.put("floors_exterior_natural_01_1", "blends_natural_01_32");
         var1.put("floors_exterior_natural_01_2", "blends_natural_01_48");
         var1.put("floors_rugs_01_0", "floors_rugs_01_6");
         var1.put("floors_rugs_01_6", "floors_rugs_01_0");
         var1.put("floors_rugs_01_1", "floors_rugs_01_7");
         var1.put("floors_rugs_01_7", "floors_rugs_01_1");
         var1.put("floors_rugs_01_8", "floors_rugs_01_14");
         var1.put("floors_rugs_01_14", "floors_rugs_01_8");
         var1.put("floors_rugs_01_9", "floors_rugs_01_15");
         var1.put("floors_rugs_01_15", "floors_rugs_01_9");
         var1.put("floors_rugs_01_16", "floors_rugs_01_22");
         var1.put("floors_rugs_01_22", "floors_rugs_01_16");
         var1.put("floors_rugs_01_17", "floors_rugs_01_23");
         var1.put("floors_rugs_01_23", "floors_rugs_01_17");
         var1.put("furniture_bedding_01_42", "furniture_bedding_01_43");
         var1.put("furniture_bedding_01_43", "furniture_bedding_01_42");
         var1.put("furniture_bedding_01_44", "furniture_bedding_01_47");
         var1.put("furniture_bedding_01_47", "furniture_bedding_01_45");
         var1.put("furniture_bedding_01_45", "furniture_bedding_01_46");
         var1.put("furniture_bedding_01_46", "furniture_bedding_01_44");
         var1.put("furniture_tables_low_01_4", "furniture_tables_low_01_5");
         var1.put("furniture_tables_low_01_5", "furniture_tables_low_01_4");

         for(var2 = 0; var2 <= 5; ++var2) {
            var1.put("location_business_machinery_" + var2, "location_business_machinery_01_" + var2);
            var1.put("location_business_machinery_" + (var2 + 8), "location_business_machinery_01_" + (var2 + 8));
            var1.put("location_ business_machinery_" + var2, "location_business_machinery_01_" + var2);
            var1.put("location_ business_machinery_" + (var2 + 8), "location_business_machinery_01_" + (var2 + 8));
         }

         for(var2 = 44; var2 <= 47; ++var2) {
            var1.put("location_hospitality_sunstarmotel_01_" + var2, "");
         }

         for(var2 = 52; var2 <= 55; ++var2) {
            var1.put("location_hospitality_sunstarmotel_01_" + var2, "");
         }

         var1.put("location_hospitality_sunstarmotel_02_24", "location_hospitality_sunstarmotel_02_28");
         var1.put("location_hospitality_sunstarmotel_02_26", "location_hospitality_sunstarmotel_02_28");
         var1.put("location_hospitality_sunstarmotel_02_33", "location_hospitality_sunstarmotel_02_29");
         var1.put("location_restaurant_bar_01_0", "location_restaurant_bar_01_1");
         var1.put("location_restaurant_bar_01_1", "location_restaurant_bar_01_0");
         var1.put("location_restaurant_bar_01_2", "location_restaurant_bar_01_7");
         var1.put("location_restaurant_bar_01_3", "location_restaurant_bar_01_6");
         var1.put("location_restaurant_bar_01_4", "location_restaurant_bar_01_5");
         var1.put("location_restaurant_bar_01_5", "location_restaurant_bar_01_4");
         var1.put("location_restaurant_bar_01_6", "location_restaurant_bar_01_3");
         var1.put("location_restaurant_bar_01_7", "location_restaurant_bar_01_2");
         var1.put("location_restaurant_bar_01_16", "location_restaurant_bar_01_45");
         var1.put("location_restaurant_bar_01_17", "location_restaurant_bar_01_44");
         var1.put("location_restaurant_bar_01_18", "location_restaurant_bar_01_46");

         for(var2 = 19; var2 <= 22; ++var2) {
            var1.put("location_restaurant_bar_01_" + var2, "location_restaurant_bar_01_" + (var2 + 33));
         }

         var1.put("location_restaurant_bar_01_23", "location_restaurant_bar_01_47");
         var1.put("location_restaurant_pie_01_8", "location_restaurant_pie_01_5");
         var1.put("location_restaurant_pie_01_14", "location_restaurant_pie_01_10");
         var1.put("location_restaurant_pie_01_15", "location_restaurant_pie_01_11");
         var1.put("location_restaurant_pie_01_22", "location_restaurant_pie_01_14");
         var1.put("location_restaurant_pie_01_23", "location_restaurant_pie_01_15");
         var1.put("location_restaurant_pie_01_54", "location_restaurant_pie_01_55");
         var1.put("location_restaurant_pie_01_55", "location_restaurant_pie_01_54");
         var1.put("location_pizzawhirled_01_32", "location_pizzawhirled_01_34");
         var1.put("location_pizzawhirled_01_34", "location_pizzawhirled_01_32");
         var1.put("location_restaurant_seahorse_01_0", "location_restaurant_seahorse_01_1");
         var1.put("location_restaurant_seahorse_01_1", "location_restaurant_seahorse_01_0");
         var1.put("location_restaurant_seahorse_01_4", "location_restaurant_seahorse_01_5");
         var1.put("location_restaurant_seahorse_01_5", "location_restaurant_seahorse_01_4");

         for(var2 = 8; var2 <= 13; ++var2) {
            var1.put("location_restaurant_seahorse_01_" + var2, "location_restaurant_seahorse_01_" + (var2 + 8));
         }

         for(var2 = 22; var2 <= 23; ++var2) {
            var1.put("location_restaurant_seahorse_01_" + var2, "location_restaurant_seahorse_01_" + (var2 - 12));
         }

         for(var2 = 44; var2 <= 47; ++var2) {
            var1.put("location_restaurant_seahorse_01_" + var2, "location_restaurant_seahorse_01_" + (var2 + 4));
         }

         for(var2 = 48; var2 <= 51; ++var2) {
            var1.put("location_restaurant_seahorse_01_" + var2, "location_restaurant_seahorse_01_" + (var2 - 4));
         }

         var1.put("location_restaurant_spiffos_01_56", "location_restaurant_spiffos_01_58");
         var1.put("location_restaurant_spiffos_01_58", "location_restaurant_spiffos_01_56");
         var1.put("location_shop_fossoil_01_45", "location_shop_fossoil_01_44");
         var1.put("location_shop_fossoil_01_46", "location_shop_fossoil_01_45");
         var1.put("location_shop_fossoil_01_57", "location_shop_fossoil_01_58");
         var1.put("location_shop_fossoil_01_58", "location_shop_fossoil_01_59");
         var1.put("location_shop_greenes_01_13", "location_shop_greenes_01_18");
         var1.put("location_shop_greenes_01_15", "location_shop_greenes_01_19");
         var1.put("location_shop_greenes_01_21", "location_shop_greenes_01_16");
         var1.put("location_shop_greenes_01_22", "location_shop_greenes_01_13");
         var1.put("location_shop_greenes_01_23", "location_shop_greenes_01_17");
         var1.put("location_shop_greenes_01_67", "location_shop_greenes_01_70");
         var1.put("location_shop_greenes_01_68", "location_shop_greenes_01_67");
         var1.put("location_shop_greenes_01_70", "location_shop_greenes_01_71");
         var1.put("location_shop_greenes_01_75", "location_shop_greenes_01_78");
         var1.put("location_shop_greenes_01_76", "location_shop_greenes_01_75");
         var1.put("location_shop_greenes_01_78", "location_shop_greenes_01_79");

         for(var2 = 0; var2 <= 16; ++var2) {
            var1.put("vegetation_foliage_01_" + var2, "randBush");
         }

         var1.put("vegetation_groundcover_01_0", "blends_grassoverlays_01_16");
         var1.put("vegetation_groundcover_01_1", "blends_grassoverlays_01_8");
         var1.put("vegetation_groundcover_01_2", "blends_grassoverlays_01_0");
         var1.put("vegetation_groundcover_01_3", "blends_grassoverlays_01_64");
         var1.put("vegetation_groundcover_01_4", "blends_grassoverlays_01_56");
         var1.put("vegetation_groundcover_01_5", "blends_grassoverlays_01_48");
         var1.put("vegetation_groundcover_01_6", "");
         var1.put("vegetation_groundcover_01_44", "blends_grassoverlays_01_40");
         var1.put("vegetation_groundcover_01_45", "blends_grassoverlays_01_32");
         var1.put("vegetation_groundcover_01_46", "blends_grassoverlays_01_24");
         var1.put("vegetation_groundcover_01_16", "d_plants_1_53");
         var1.put("vegetation_groundcover_01_17", "d_plants_1_53");

         for(var2 = 18; var2 <= 23; ++var2) {
            var1.put("vegetation_groundcover_01_" + var2, "randPlant");
         }

         for(var2 = 20; var2 <= 23; ++var2) {
            var1.put("walls_exterior_house_01_" + var2, "walls_exterior_house_01_" + (var2 + 12));
            var1.put("walls_exterior_house_01_" + (var2 + 8), "walls_exterior_house_01_" + (var2 + 8 + 12));
         }

         for(var2 = 24; var2 <= 41; ++var2) {
            var1.put("walls_exterior_roofs_01_" + var2, "walls_exterior_roofs_03_" + var2);
         }
      }

      String var3 = (String)Fix2xMap.get(var0);
      if (var3 == null) {
         return var0;
      } else if ("randBush".equals(var3)) {
         var2 = 64 + Rand.Next(16);
         return "f_bushes_1_" + var2;
      } else if ("randPlant".equals(var3)) {
         var2 = Rand.Next(4) * 16 + Rand.Next(8);
         return "d_plants_1_" + var2;
      } else {
         return var3;
      }
   }

   public void addGeneratorPos(int var1, int var2, int var3) {
      if (this.generatorsTouchingThisChunk == null) {
         this.generatorsTouchingThisChunk = new ArrayList();
      }

      for(int var4 = 0; var4 < this.generatorsTouchingThisChunk.size(); ++var4) {
         IsoGameCharacter.Location var5 = (IsoGameCharacter.Location)this.generatorsTouchingThisChunk.get(var4);
         if (var5.x == var1 && var5.y == var2 && var5.z == var3) {
            return;
         }
      }

      IsoGameCharacter.Location var6 = new IsoGameCharacter.Location(var1, var2, var3);
      this.generatorsTouchingThisChunk.add(var6);
   }

   public void removeGeneratorPos(int var1, int var2, int var3) {
      if (this.generatorsTouchingThisChunk != null) {
         for(int var4 = 0; var4 < this.generatorsTouchingThisChunk.size(); ++var4) {
            IsoGameCharacter.Location var5 = (IsoGameCharacter.Location)this.generatorsTouchingThisChunk.get(var4);
            if (var5.x == var1 && var5.y == var2 && var5.z == var3) {
               this.generatorsTouchingThisChunk.remove(var4);
               --var4;
            }
         }

      }
   }

   public boolean isGeneratorPoweringSquare(int var1, int var2, int var3) {
      if (this.generatorsTouchingThisChunk == null) {
         return false;
      } else {
         for(int var4 = 0; var4 < this.generatorsTouchingThisChunk.size(); ++var4) {
            IsoGameCharacter.Location var5 = (IsoGameCharacter.Location)this.generatorsTouchingThisChunk.get(var4);
            if (IsoGenerator.isPoweringSquare(var5.x, var5.y, var5.z, var1, var2, var3)) {
               return true;
            }
         }

         return false;
      }
   }

   public void checkForMissingGenerators() {
      if (this.generatorsTouchingThisChunk != null) {
         for(int var1 = 0; var1 < this.generatorsTouchingThisChunk.size(); ++var1) {
            IsoGameCharacter.Location var2 = (IsoGameCharacter.Location)this.generatorsTouchingThisChunk.get(var1);
            IsoGridSquare var3 = IsoWorld.instance.CurrentCell.getGridSquare(var2.x, var2.y, var2.z);
            if (var3 != null) {
               IsoGenerator var4 = var3.getGenerator();
               if (var4 == null || !var4.isActivated()) {
                  this.generatorsTouchingThisChunk.remove(var1);
                  --var1;
               }
            }
         }

      }
   }

   public boolean isNewChunk() {
      return this.addZombies;
   }

   public void addSpawnedRoom(int var1) {
      if (!this.m_spawnedRooms.contains(var1)) {
         this.m_spawnedRooms.add(var1);
      }

   }

   public boolean isSpawnedRoom(int var1) {
      return this.m_spawnedRooms.contains(var1);
   }

   public IsoMetaGrid.Zone getScavengeZone() {
      if (this.m_scavengeZone != null) {
         return this.m_scavengeZone;
      } else {
         IsoMetaChunk var1 = IsoWorld.instance.getMetaGrid().getChunkData(this.wx, this.wy);
         if (var1 != null && var1.numZones() > 0) {
            for(int var2 = 0; var2 < var1.numZones(); ++var2) {
               IsoMetaGrid.Zone var3 = var1.getZone(var2);
               if ("DeepForest".equals(var3.type) || "Forest".equals(var3.type)) {
                  this.m_scavengeZone = var3;
                  return var3;
               }

               if ("Nav".equals(var3.type) || "Town".equals(var3.type)) {
                  return null;
               }
            }
         }

         byte var8 = 5;
         if (this.m_treeCount < var8) {
            return null;
         } else {
            int var9 = 0;

            for(int var4 = -1; var4 <= 1; ++var4) {
               for(int var5 = -1; var5 <= 1; ++var5) {
                  if (var5 != 0 || var4 != 0) {
                     IsoChunk var6 = GameServer.bServer ? ServerMap.instance.getChunk(this.wx + var5, this.wy + var4) : IsoWorld.instance.CurrentCell.getChunk(this.wx + var5, this.wy + var4);
                     if (var6 != null && var6.m_treeCount >= var8) {
                        ++var9;
                        if (var9 == 8) {
                           byte var7 = 10;
                           this.m_scavengeZone = new IsoMetaGrid.Zone("", "Forest", this.wx * var7, this.wy * var7, 0, var7, var7);
                           return this.m_scavengeZone;
                        }
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   public void resetForStore() {
      this.randomID = 0;
      this.revision = 0L;
      this.nextSplatIndex = 0;
      this.FloorBloodSplats.clear();
      this.FloorBloodSplatsFade.clear();
      this.jobType = IsoChunk.JobType.None;
      this.maxLevel = -1;
      this.bFixed2x = false;
      this.vehicles.clear();
      this.roomLights.clear();
      this.blam = false;
      this.lotheader = null;
      this.bLoaded = false;
      this.addZombies = false;
      this.roomLights.clear();
      this.physicsCheck = false;
      this.loadedPhysics = false;
      this.wx = 0;
      this.wy = 0;
      this.erosion = null;
      this.lootRespawnHour = -1;
      if (this.generatorsTouchingThisChunk != null) {
         this.generatorsTouchingThisChunk.clear();
      }

      this.m_treeCount = 0;
      this.m_scavengeZone = null;
      this.m_spawnedRooms.resetQuick();
      this.m_adjacentChunkLoadedCounter = 0;

      int var1;
      for(var1 = 0; var1 < this.squares.length; ++var1) {
         for(int var2 = 0; var2 < this.squares[0].length; ++var2) {
            this.squares[var1][var2] = null;
         }
      }

      for(var1 = 0; var1 < 4; ++var1) {
         this.lightCheck[var1] = true;
         this.bLightingNeverDone[var1] = true;
      }

      this.refs.clear();
   }

   private static class SanityCheck {
      public IsoChunk saveChunk;
      public String saveThread;
      public IsoChunk loadChunk;
      public String loadThread;
      public final ArrayList loadFile;
      public String saveFile;

      private SanityCheck() {
         this.loadFile = new ArrayList();
      }

      public synchronized void beginSave(IsoChunk var1) {
         if (this.saveChunk != null) {
            this.log("trying to save while already saving, wx,wy=" + var1.wx + "," + var1.wy);
         }

         if (this.loadChunk == var1) {
            this.log("trying to save the same IsoChunk being loaded");
         }

         this.saveChunk = var1;
         this.saveThread = Thread.currentThread().getName();
      }

      public synchronized void endSave(IsoChunk var1) {
         this.saveChunk = null;
         this.saveThread = null;
      }

      public synchronized void beginLoad(IsoChunk var1) {
         if (this.loadChunk != null) {
            this.log("trying to load while already loading, wx,wy=" + var1.wx + "," + var1.wy);
         }

         if (this.saveChunk == var1) {
            this.log("trying to load the same IsoChunk being saved");
         }

         this.loadChunk = var1;
         this.loadThread = Thread.currentThread().getName();
      }

      public synchronized void endLoad(IsoChunk var1) {
         this.loadChunk = null;
         this.loadThread = null;
      }

      public synchronized void checkCRC(long var1, long var3) {
         if (var1 != var3) {
            this.log("CRC mismatch save=" + var1 + " load=" + var3);
         }

      }

      public synchronized void checkLength(long var1, long var3) {
         if (var1 != var3) {
            this.log("LENGTH mismatch save=" + var1 + " load=" + var3);
         }

      }

      public synchronized void beginLoadFile(String var1) {
         if (var1.equals(this.saveFile)) {
            this.log("attempted to load file being saved " + var1);
         }

         this.loadFile.add(var1);
      }

      public synchronized void endLoadFile(String var1) {
         this.loadFile.remove(var1);
      }

      public synchronized void beginSaveFile(String var1) {
         if (this.loadFile.contains(var1)) {
            this.log("attempted to save file being loaded " + var1);
         }

         this.saveFile = var1;
      }

      public synchronized void endSaveFile() {
         this.saveFile = null;
      }

      public synchronized void log(String var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("SANITY CHECK FAIL! thread=\"" + Thread.currentThread().getName() + "\"\n");
         if (var1 != null) {
            var2.append(var1 + "\n");
         }

         if (this.saveChunk != null && this.saveChunk == this.loadChunk) {
            var2.append("exact same IsoChunk being saved + loaded\n");
         }

         if (this.saveChunk != null) {
            var2.append("save wx,wy=" + this.saveChunk.wx + "," + this.saveChunk.wy + " thread=\"" + this.saveThread + "\"\n");
         } else {
            var2.append("save chunk=null\n");
         }

         if (this.loadChunk != null) {
            var2.append("load wx,wy=" + this.loadChunk.wx + "," + this.loadChunk.wy + " thread=\"" + this.loadThread + "\"\n");
         } else {
            var2.append("load chunk=null\n");
         }

         String var3 = var2.toString();
         throw new RuntimeException(var3);
      }

      // $FF: synthetic method
      SanityCheck(Object var1) {
         this();
      }
   }

   private static class ChunkLock {
      public int wx;
      public int wy;
      public int count;
      public ReentrantReadWriteLock rw = new ReentrantReadWriteLock(true);

      public ChunkLock(int var1, int var2) {
         this.wx = var1;
         this.wy = var2;
      }

      public IsoChunk.ChunkLock set(int var1, int var2) {
         assert this.count == 0;

         this.wx = var1;
         this.wy = var2;
         return this;
      }

      public IsoChunk.ChunkLock ref() {
         ++this.count;
         return this;
      }

      public int deref() {
         assert this.count > 0;

         return --this.count;
      }

      public void lockForReading() {
         this.rw.readLock().lock();
      }

      public void unlockForReading() {
         this.rw.readLock().unlock();
      }

      public void lockForWriting() {
         this.rw.writeLock().lock();
      }

      public void unlockForWriting() {
         this.rw.writeLock().unlock();
      }
   }

   private static class ChunkGetter implements IsoGridSquare.GetSquare {
      IsoChunk chunk;

      private ChunkGetter() {
      }

      public IsoGridSquare getGridSquare(int var1, int var2, int var3) {
         var1 -= this.chunk.wx * 10;
         var2 -= this.chunk.wy * 10;
         return var1 >= 0 && var1 < 10 && var2 >= 0 && var2 < 10 && var3 >= 0 && var3 < 8 ? this.chunk.getGridSquare(var1, var2, var3) : null;
      }

      // $FF: synthetic method
      ChunkGetter(Object var1) {
         this();
      }
   }

   private static enum PhysicsShapes {
      Solid,
      WallN,
      WallW,
      WallS,
      WallE,
      Tree,
      Floor;
   }

   public static enum JobType {
      None,
      Convert,
      SoftReset;
   }
}
