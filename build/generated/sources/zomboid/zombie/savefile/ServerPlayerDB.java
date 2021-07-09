package zombie.savefile;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import zombie.characters.IsoPlayer;
import zombie.core.Core;
import zombie.core.logger.ExceptionLogger;
import zombie.core.network.ByteBufferWriter;
import zombie.core.raknet.UdpConnection;
import zombie.iso.IsoCell;
import zombie.network.PacketTypes;

public final class ServerPlayerDB {
   private static ServerPlayerDB instance = null;
   private static boolean allow = false;
   private Connection conn = null;
   private ConcurrentLinkedQueue CharactersToSave;

   public static void setAllow(boolean var0) {
      allow = var0;
   }

   public static boolean isAllow() {
      return allow;
   }

   public static synchronized ServerPlayerDB getInstance() {
      if (instance == null && allow) {
         instance = new ServerPlayerDB();
      }

      return instance;
   }

   public static boolean isAvailable() {
      return instance != null;
   }

   public ServerPlayerDB() {
      if (!Core.getInstance().isNoSave()) {
         this.create();
      }
   }

   public void close() {
      instance = null;
      allow = false;
   }

   private void create() {
      this.conn = PlayerDBHelper.create();
      this.CharactersToSave = new ConcurrentLinkedQueue();
   }

   public void process() {
      if (!this.CharactersToSave.isEmpty()) {
         for(ServerPlayerDB.NetworkCharacterData var1 = (ServerPlayerDB.NetworkCharacterData)this.CharactersToSave.poll(); var1 != null; var1 = (ServerPlayerDB.NetworkCharacterData)this.CharactersToSave.poll()) {
            this.serverUpdateNetworkCharacterInt(var1);
         }
      }

   }

   public void serverUpdateNetworkCharacter(ByteBuffer var1, UdpConnection var2) {
      this.CharactersToSave.add(new ServerPlayerDB.NetworkCharacterData(var1, var2));
   }

   private void serverUpdateNetworkCharacterInt(ServerPlayerDB.NetworkCharacterData var1) {
      if (var1.playerIndex >= 0 && var1.playerIndex < 4) {
         if (this.conn != null) {
            String var2 = "SELECT id FROM networkPlayers WHERE username=? AND world=? AND playerIndex=?";
            String var3 = "INSERT INTO networkPlayers(world,username,playerIndex,name,x,y,z,worldversion,isDead,data) VALUES(?,?,?,?,?,?,?,?,?,?)";
            String var4 = "UPDATE networkPlayers SET x=?, y=?, z=?, worldversion = ?, isDead = ?, data = ? WHERE id=?";

            try {
               PreparedStatement var5 = this.conn.prepareStatement(var2);
               Throwable var6 = null;

               label437: {
                  try {
                     var5.setString(1, var1.username);
                     var5.setString(2, Core.GameSaveWorld);
                     var5.setInt(3, var1.playerIndex);
                     ResultSet var7 = var5.executeQuery();
                     if (!var7.next()) {
                        break label437;
                     }

                     int var8 = var7.getInt(1);
                     PreparedStatement var9 = this.conn.prepareStatement(var4);
                     Throwable var10 = null;

                     try {
                        var9.setFloat(1, var1.x);
                        var9.setFloat(2, var1.y);
                        var9.setFloat(3, var1.z);
                        var9.setInt(4, var1.worldVersion);
                        var9.setBoolean(5, var1.isDead);
                        var9.setBytes(6, var1.buffer);
                        var9.setInt(7, var8);
                        int var11 = var9.executeUpdate();
                        this.conn.commit();
                     } catch (Throwable var59) {
                        var10 = var59;
                        throw var59;
                     } finally {
                        if (var9 != null) {
                           if (var10 != null) {
                              try {
                                 var9.close();
                              } catch (Throwable var58) {
                                 var10.addSuppressed(var58);
                              }
                           } else {
                              var9.close();
                           }
                        }

                     }
                  } catch (Throwable var63) {
                     var6 = var63;
                     throw var63;
                  } finally {
                     if (var5 != null) {
                        if (var6 != null) {
                           try {
                              var5.close();
                           } catch (Throwable var57) {
                              var6.addSuppressed(var57);
                           }
                        } else {
                           var5.close();
                        }
                     }

                  }

                  return;
               }

               ByteBuffer var66 = ByteBuffer.allocate(var1.buffer.length);
               var66.rewind();
               var66.put(var1.buffer);
               var66.rewind();
               IsoPlayer var67 = new IsoPlayer(IsoCell.getInstance());
               var67.load(var66, var1.worldVersion);
               String var68 = var67.getDescriptor().getForename() + " " + var67.getDescriptor().getSurname();
               PreparedStatement var69 = this.conn.prepareStatement(var3);
               Throwable var70 = null;

               try {
                  var69.setString(1, Core.GameSaveWorld);
                  var69.setString(2, var1.username);
                  var69.setInt(3, var1.playerIndex);
                  var69.setString(4, var68);
                  var69.setFloat(5, var1.x);
                  var69.setFloat(6, var1.y);
                  var69.setFloat(7, var1.z);
                  var69.setInt(8, var1.worldVersion);
                  var69.setBoolean(9, var1.isDead);
                  var69.setBytes(10, var1.buffer);
                  int var71 = var69.executeUpdate();
                  this.conn.commit();
               } catch (Throwable var60) {
                  var70 = var60;
                  throw var60;
               } finally {
                  if (var69 != null) {
                     if (var70 != null) {
                        try {
                           var69.close();
                        } catch (Throwable var56) {
                           var70.addSuppressed(var56);
                        }
                     } else {
                        var69.close();
                     }
                  }

               }
            } catch (Exception var65) {
               ExceptionLogger.logException(var65);
               PlayerDBHelper.rollback(this.conn);
            }

         }
      }
   }

   public void serverLoadNetworkCharacter(ByteBuffer var1, UdpConnection var2) {
      byte var3 = var1.get();
      if (var3 >= 0 && var3 < 4) {
         if (this.conn != null) {
            String var4 = "SELECT id, x, y, z, data, worldversion, isDead FROM networkPlayers WHERE username=? AND world=? AND playerIndex=?";

            try {
               PreparedStatement var5 = this.conn.prepareStatement(var4);
               Throwable var6 = null;

               try {
                  var5.setString(1, var2.username);
                  var5.setString(2, Core.GameSaveWorld);
                  var5.setInt(3, var3);
                  ResultSet var7 = var5.executeQuery();
                  if (var7.next()) {
                     int var8 = var7.getInt(1);
                     float var9 = var7.getFloat(2);
                     float var10 = var7.getFloat(3);
                     float var11 = var7.getFloat(4);
                     byte[] var12 = var7.getBytes(5);
                     int var13 = var7.getInt(6);
                     boolean var14 = var7.getBoolean(7);
                     ByteBufferWriter var15 = var2.startPacket();
                     PacketTypes.doPacket((short)167, var15);
                     var15.putByte((byte)1);
                     var15.putInt(var3);
                     var15.putFloat(var9);
                     var15.putFloat(var10);
                     var15.putFloat(var11);
                     var15.putInt(var13);
                     var15.putByte((byte)(var14 ? 1 : 0));
                     var15.putInt(var12.length);
                     var15.bb.put(var12);
                     var2.endPacketImmediate();
                  } else {
                     ByteBufferWriter var27 = var2.startPacket();
                     PacketTypes.doPacket((short)167, var27);
                     var27.putByte((byte)0);
                     var27.putInt(var3);
                     var2.endPacketImmediate();
                  }
               } catch (Throwable var24) {
                  var6 = var24;
                  throw var24;
               } finally {
                  if (var5 != null) {
                     if (var6 != null) {
                        try {
                           var5.close();
                        } catch (Throwable var23) {
                           var6.addSuppressed(var23);
                        }
                     } else {
                        var5.close();
                     }
                  }

               }
            } catch (SQLException var26) {
               ExceptionLogger.logException(var26);
            }

         }
      }
   }

   private static final class NetworkCharacterData {
      byte[] buffer;
      String username;
      int playerIndex;
      float x;
      float y;
      float z;
      boolean isDead;
      int worldVersion;

      public NetworkCharacterData(ByteBuffer var1, UdpConnection var2) {
         this.playerIndex = var1.get();
         int var3 = var1.getInt();
         this.x = var1.getFloat();
         this.y = var1.getFloat();
         this.z = var1.getFloat();
         this.isDead = var1.get() == 1;
         this.worldVersion = var1.getInt();
         int var4 = var1.getInt();
         this.buffer = new byte[var4];
         var1.get(this.buffer);
         this.username = var2.username;
      }
   }
}
