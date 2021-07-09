package zombie.globalObjects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import se.krka.kahlua.vm.KahluaTable;
import se.krka.kahlua.vm.KahluaTableIterator;
import zombie.ZomboidFileSystem;
import zombie.Lua.LuaManager;
import zombie.characters.IsoPlayer;
import zombie.core.BoxedStaticValues;
import zombie.core.Core;
import zombie.core.logger.ExceptionLogger;
import zombie.iso.SliceY;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.spnetwork.SinglePlayerServer;

public final class SGlobalObjectSystem {
   protected final String name;
   protected int loadedWorldVersion = -1;
   protected final ArrayList objects = new ArrayList();
   protected final GlobalObjectLookup lookup = new GlobalObjectLookup(this);
   protected final KahluaTable modData;
   protected final HashSet modDataKeys = new HashSet();
   protected final HashSet objectModDataKeys = new HashSet();
   private static KahluaTable tempTable;
   private static final ArrayDeque objectListPool = new ArrayDeque();

   public SGlobalObjectSystem(String var1) {
      this.name = var1;
      this.modData = LuaManager.platform.newTable();
   }

   public void setModDataKeys(KahluaTable var1) {
      this.modDataKeys.clear();
      if (var1 != null) {
         KahluaTableIterator var2 = var1.iterator();

         while(var2.advance()) {
            Object var3 = var2.getValue();
            if (!(var3 instanceof String)) {
               throw new IllegalArgumentException("expected string but got \"" + var3 + "\"");
            }

            this.modDataKeys.add((String)var3);
         }

      }
   }

   public void setObjectModDataKeys(KahluaTable var1) {
      this.objectModDataKeys.clear();
      if (var1 != null) {
         KahluaTableIterator var2 = var1.iterator();

         while(var2.advance()) {
            Object var3 = var2.getValue();
            if (!(var3 instanceof String)) {
               throw new IllegalArgumentException("expected string but got \"" + var3 + "\"");
            }

            this.objectModDataKeys.add((String)var3);
         }

      }
   }

   public KahluaTable getModData() {
      return this.modData;
   }

   public GlobalObject newObject(int var1, int var2, int var3) {
      if (this.getObjectAt(var1, var2, var3) != null) {
         throw new IllegalStateException("already an object at " + var1 + "," + var2 + "," + var3);
      } else {
         GlobalObject var4 = new GlobalObject(this, var1, var2, var3);
         this.objects.add(var4);
         this.lookup.addObject(var4);
         return var4;
      }
   }

   public void removeObject(GlobalObject var1) throws IllegalArgumentException, IllegalStateException {
      if (var1 == null) {
         throw new NullPointerException("object is null");
      } else if (var1.system != this) {
         throw new IllegalStateException("object not in this system");
      } else {
         this.objects.remove(var1);
         this.lookup.removeObject(var1);
         var1.Reset();
      }
   }

   public GlobalObject getObjectAt(int var1, int var2, int var3) {
      return this.lookup.getObjectAt(var1, var2, var3);
   }

   public boolean hasObjectsInChunk(int var1, int var2) {
      return this.lookup.hasObjectsInChunk(var1, var2);
   }

   public ArrayList getObjectsInChunk(int var1, int var2) {
      return this.lookup.getObjectsInChunk(var1, var2, this.allocList());
   }

   public ArrayList getObjectsAdjacentTo(int var1, int var2, int var3) {
      return this.lookup.getObjectsAdjacentTo(var1, var2, var3, this.allocList());
   }

   public ArrayList allocList() {
      return objectListPool.isEmpty() ? new ArrayList() : (ArrayList)objectListPool.pop();
   }

   public void finishedWithList(ArrayList var1) {
      if (var1 != null && !objectListPool.contains(var1)) {
         var1.clear();
         objectListPool.add(var1);
      }

   }

   public int getObjectCount() {
      return this.objects.size();
   }

   public GlobalObject getObjectByIndex(int var1) {
      return var1 >= 0 && var1 < this.objects.size() ? (GlobalObject)this.objects.get(var1) : null;
   }

   public void update() {
   }

   public void chunkLoaded(int var1, int var2) {
      if (this.hasObjectsInChunk(var1, var2)) {
         Object var3 = this.modData.rawget("OnChunkLoaded");
         if (var3 == null) {
            throw new IllegalStateException("OnChunkLoaded method undefined for system '" + this.name + "'");
         } else {
            Double var4 = BoxedStaticValues.toDouble((double)var1);
            Double var5 = BoxedStaticValues.toDouble((double)var2);
            LuaManager.caller.pcall(LuaManager.thread, var3, this.modData, var4, var5);
         }
      }
   }

   public void sendCommand(String var1, KahluaTable var2) {
      if (GameServer.bServer) {
         GameServer.sendServerCommand("gos_" + this.name, var1, var2);
      } else {
         if (GameClient.bClient) {
            throw new IllegalStateException("can't call this method on the client");
         }

         SinglePlayerServer.sendServerCommand("gos_" + this.name, var1, var2);
      }

   }

   public void receiveClientCommand(String var1, IsoPlayer var2, KahluaTable var3) {
      Object var4 = this.modData.rawget("OnClientCommand");
      if (var4 == null) {
         throw new IllegalStateException("OnClientCommand method undefined for system '" + this.name + "'");
      } else {
         LuaManager.caller.pcall(LuaManager.thread, var4, this.modData, var1, var2, var3);
      }
   }

   private String getFileName() {
      return ZomboidFileSystem.instance.getFileNameInCurrentSave("gos_" + this.name + ".bin");
   }

   public KahluaTable getInitialStateForClient() {
      Object var1 = this.modData.rawget("getInitialStateForClient");
      if (var1 == null) {
         throw new IllegalStateException("getInitialStateForClient method undefined for system '" + this.name + "'");
      } else {
         Object[] var2 = LuaManager.caller.pcall(LuaManager.thread, var1, (Object)this.modData);
         return var2 != null && var2[0].equals(Boolean.TRUE) && var2[1] instanceof KahluaTable ? (KahluaTable)var2[1] : null;
      }
   }

   public int loadedWorldVersion() {
      return this.loadedWorldVersion;
   }

   public void load(ByteBuffer var1, int var2) throws IOException {
      boolean var3 = var1.get() == 0;
      if (!var3) {
         this.modData.load(var1, var2);
      }

      int var4 = var1.getInt();

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var1.getInt();
         int var7 = var1.getInt();
         byte var8 = var1.get();
         GlobalObject var9 = this.newObject(var6, var7, var8);
         var9.load(var1, var2);
      }

      this.loadedWorldVersion = var2;
   }

   public void save(ByteBuffer var1) throws IOException {
      if (tempTable == null) {
         tempTable = LuaManager.platform.newTable();
      }

      tempTable.wipe();
      KahluaTableIterator var2 = this.modData.iterator();

      while(var2.advance()) {
         Object var3 = var2.getKey();
         if (this.modDataKeys.contains(var3)) {
            tempTable.rawset(var3, this.modData.rawget(var3));
         }
      }

      if (tempTable.isEmpty()) {
         var1.put((byte)0);
      } else {
         var1.put((byte)1);
         tempTable.save(var1);
      }

      var1.putInt(this.objects.size());

      for(int var5 = 0; var5 < this.objects.size(); ++var5) {
         GlobalObject var4 = (GlobalObject)this.objects.get(var5);
         var4.save(var1);
      }

   }

   public void load() {
      File var1 = new File(this.getFileName());

      try {
         FileInputStream var2 = new FileInputStream(var1);
         Throwable var3 = null;

         try {
            BufferedInputStream var4 = new BufferedInputStream(var2);
            Throwable var5 = null;

            try {
               synchronized(SliceY.SliceBufferLock) {
                  ByteBuffer var7 = SliceY.SliceBuffer;
                  var7.clear();
                  int var8 = var4.read(var7.array());
                  var7.limit(var8);
                  byte var9 = var7.get();
                  byte var10 = var7.get();
                  byte var11 = var7.get();
                  byte var12 = var7.get();
                  if (var9 != 71 || var10 != 76 || var11 != 79 || var12 != 83) {
                     throw new IOException("doesn't appear to be a GlobalObjectSystem file:" + var1.getAbsolutePath());
                  }

                  int var13 = var7.getInt();
                  if (var13 < 134) {
                     throw new IOException("invalid WorldVersion " + var13 + ": " + var1.getAbsolutePath());
                  }

                  if (var13 > 175) {
                     throw new IOException("file is from a newer version " + var13 + " of the game: " + var1.getAbsolutePath());
                  }

                  this.load(var7, var13);
               }
            } catch (Throwable var43) {
               var5 = var43;
               throw var43;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var41) {
                        var5.addSuppressed(var41);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Throwable var45) {
            var3 = var45;
            throw var45;
         } finally {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var40) {
                     var3.addSuppressed(var40);
                  }
               } else {
                  var2.close();
               }
            }

         }
      } catch (FileNotFoundException var47) {
      } catch (Throwable var48) {
         ExceptionLogger.logException(var48);
      }

   }

   public void save() {
      if (!Core.getInstance().isNoSave()) {
         if (!GameClient.bClient) {
            File var1 = new File(this.getFileName());

            try {
               FileOutputStream var2 = new FileOutputStream(var1);
               Throwable var3 = null;

               try {
                  BufferedOutputStream var4 = new BufferedOutputStream(var2);
                  Throwable var5 = null;

                  try {
                     synchronized(SliceY.SliceBufferLock) {
                        ByteBuffer var7 = SliceY.SliceBuffer;
                        var7.clear();
                        var7.put((byte)71);
                        var7.put((byte)76);
                        var7.put((byte)79);
                        var7.put((byte)83);
                        var7.putInt(175);
                        this.save(var7);
                        var4.write(var7.array(), 0, var7.position());
                     }
                  } catch (Throwable var35) {
                     var5 = var35;
                     throw var35;
                  } finally {
                     if (var4 != null) {
                        if (var5 != null) {
                           try {
                              var4.close();
                           } catch (Throwable var33) {
                              var5.addSuppressed(var33);
                           }
                        } else {
                           var4.close();
                        }
                     }

                  }
               } catch (Throwable var37) {
                  var3 = var37;
                  throw var37;
               } finally {
                  if (var2 != null) {
                     if (var3 != null) {
                        try {
                           var2.close();
                        } catch (Throwable var32) {
                           var3.addSuppressed(var32);
                        }
                     } else {
                        var2.close();
                     }
                  }

               }
            } catch (Throwable var39) {
               ExceptionLogger.logException(var39);
            }

         }
      }
   }

   public void Reset() {
      for(int var1 = 0; var1 < this.objects.size(); ++var1) {
         GlobalObject var2 = (GlobalObject)this.objects.get(var1);
         var2.Reset();
      }

      this.modData.wipe();
      this.modDataKeys.clear();
      this.objectModDataKeys.clear();
      this.objects.clear();
   }
}
