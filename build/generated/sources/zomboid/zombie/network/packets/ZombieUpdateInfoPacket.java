package zombie.network.packets;

import java.nio.ByteBuffer;
import java.util.Stack;
import zombie.VirtualZombieManager;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.core.math.PZMath;
import zombie.core.network.ByteBufferWriter;
import zombie.core.raknet.UdpConnection;
import zombie.debug.DebugOptions;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoUtils;
import zombie.iso.IsoWorld;
import zombie.iso.Vector2;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.PacketTypes;
import zombie.network.ServerOptions;

public class ZombieUpdateInfoPacket {
   private static final float NEAR_DIST = 4.0F;
   private int ZombiePacketsSentThisTime = 0;
   private static final boolean SendZombieState = false;
   private final ZombieUpdateInfoPacket.PlayerZombiePackInfo[] packInfo = new ZombieUpdateInfoPacket.PlayerZombiePackInfo[512];

   public void clear() {
      int var1 = this.packInfo.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (this.packInfo[var2] != null) {
            this.packInfo[var2].zombies.clear();
         }
      }

   }

   public void send() {
      if (!GameServer.bFastForward) {
         this.addZombiesToPackInfo();
         this.ZombiePacketsSentThisTime = 0;
         int var1 = GameServer.udpEngine.getMaxConnections();

         for(int var2 = 0; var2 < var1; ++var2) {
            if (this.packInfo[var2] != null && !this.packInfo[var2].zombies.isEmpty()) {
               long var3 = this.packInfo[var2].guid;
               UdpConnection var5 = GameServer.udpEngine.getActiveConnection(var3);
               if (var5 != null) {
                  for(int var6 = this.packInfo[var2].zombies.size() - 1; var6 >= 0; --var6) {
                     if (((IsoZombie)this.packInfo[var2].zombies.get(var6)).OnlineID == -1) {
                        this.packInfo[var2].zombies.remove(var6);
                     }
                  }

                  while(!this.packInfo[var2].zombies.isEmpty()) {
                     ByteBufferWriter var7 = var5.startPacket();
                     PacketTypes.doPacket((short)10, var7);
                     this.writeZombies(var7, this.packInfo[var2]);
                     var5.endPacketSuperHighUnreliable();
                     ++this.ZombiePacketsSentThisTime;
                  }
               }
            }
         }

      }
   }

   public void receive(ByteBuffer var1) {
      if (DebugOptions.instance.Network.Client.UpdateZombiesFromPacket.getValue()) {
         short var2 = var1.getShort();

         for(short var3 = 0; var3 < var2; ++var3) {
            this.parseZombie(var1);
         }

      }
   }

   private void parseZombie(ByteBuffer var1) {
      ZombiePacket var2 = ZombieUpdateInfoPacket.l_receive.zombiePacket;
      var2.parse(var1);
      Object var3 = null;
      boolean var4 = false;

      try {
         IsoZombie var5 = (IsoZombie)GameClient.IDToZombieMap.get(var2.id);
         if (var5 == null) {
            IsoGridSquare var6 = IsoWorld.instance.CurrentCell.getGridSquare((double)var2.x, (double)var2.y, (double)var2.z);
            if (var6 != null) {
               VirtualZombieManager.instance.choices.clear();
               VirtualZombieManager.instance.choices.add(var6);
               var5 = VirtualZombieManager.instance.createRealZombieAlways(var2.descriptorID, 0, false);
               if (var5 != null) {
                  var5.setFakeDead(false);
                  var5.OnlineID = var2.id;
                  GameClient.IDToZombieMap.put(var2.id, var5);
                  var5.bx = var2.x;
                  var5.by = var2.y;

                  for(int var7 = 0; var7 < IsoPlayer.numPlayers; ++var7) {
                     IsoPlayer var8 = IsoPlayer.players[var7];
                     if (var6.isCanSee(var7)) {
                        var5.alpha[var7] = var5.targetAlpha[var7] = 1.0F;
                     }

                     if (var8 != null && var8.ReanimatedCorpseID == var2.id) {
                        var8.ReanimatedCorpseID = -1;
                        var8.ReanimatedCorpse = var5;
                     }
                  }
               }

               var5.serverState = (String)var3;
               var4 = true;
            }

            if (var5 == null) {
               return;
            }
         }

         var5.setDir(var2.dir);
         var5.setForwardDirection(var5.dir.ToVector());
         Vector2 var12 = ZombieUpdateInfoPacket.l_receive.diff;
         var12.x = var2.x - var5.bx;
         var12.y = var2.y - var5.by;
         var5.reqMovement.x = var12.x;
         var5.reqMovement.y = var12.y;
         var5.reqMovement.normalize();
         float var13 = var12.getLength() / 5.0F;
         var13 = Math.max(var13, 0.1F);

         for(int var14 = 0; var14 < IsoPlayer.numPlayers; ++var14) {
            IsoPlayer var9 = IsoPlayer.players[var14];
            if (var9 != null && !var9.isDead() && (int)var9.z == (int)var5.z) {
               float var10 = IsoUtils.DistanceToSquared(var2.x, var2.y, var9.x, var9.y);
               if (var10 < 16.0F) {
                  var13 *= Math.max((1.0F - var10 / 16.0F) * 4.5F, 1.0F);
                  break;
               }
            }
         }

         var5.setBlendSpeed(var13);
         var5.lastRemoteUpdate = 0;
         var5.setX(var2.x);
         var5.setY(var2.y);
         var5.setZ(var2.z);
         var5.setLx(var2.x);
         var5.setLy(var2.y);
         var5.def.Finished = var2.isFinished();
         var5.def.Frame = (float)var2.getFrame();
         var5.setOnFloor(var2.isOnFloor());
         var5.bCrawling = var2.isCrawling();
         var5.setIgnoreMovementForDirection(var2.isImfd());
         var5.def.AnimFrameIncrease = (float)var2.animFrameIncrease / 128.0F;
         var5.def.Looped = var2.isLooped();
         var5.thumpFlag = var2.getThump();
         var5.mpIdleSound = var2.isIdleSound();
         if (var2.isOnfire()) {
            var5.SetOnFire();
         } else {
            var5.StopBurning();
         }

         if (!IsoWorld.instance.CurrentCell.getZombieList().contains(var5)) {
            IsoWorld.instance.CurrentCell.getZombieList().add(var5);
         }

         if (!IsoWorld.instance.CurrentCell.getObjectList().contains(var5)) {
            IsoWorld.instance.CurrentCell.getObjectList().add(var5);
         }

         var5.serverState = (String)var3;
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   private void writeZombies(ByteBufferWriter var1, ZombieUpdateInfoPacket.PlayerZombiePackInfo var2) {
      int var3 = var1.bb.remaining() / 23;
      var3 = PZMath.clamp(var3, 0, var2.zombies.size());
      var1.putShort((short)var3);

      for(int var4 = 0; var4 < var3; ++var4) {
         IsoZombie var5 = (IsoZombie)var2.zombies.pop();
         this.writeZombie(var1, var5);
      }

   }

   private void writeZombie(ByteBufferWriter var1, IsoZombie var2) {
      ZombiePacket var3 = ZombieUpdateInfoPacket.l_send.zombiePacket;
      var3.set(var2);
      var3.write(var1);
   }

   private void addZombiesToPackInfo() {
      for(int var1 = 0; var1 < GameServer.Players.size(); ++var1) {
         IsoPlayer var2 = (IsoPlayer)GameServer.Players.get(var1);
         UdpConnection var3 = GameServer.getConnectionFromPlayer(var2);
         if (var3 != null && var3.isFullyConnected()) {
            var2.zombiesToSend.update();
            int var4 = var3.index;
            long var5 = var3.getConnectedGUID();

            for(int var7 = 0; var7 < 300; ++var7) {
               IsoZombie var8 = var2.zombiesToSend.getZombie(var7);
               if (var8 == null) {
                  break;
               }

               this.addZombieToPackInfo(var8, var4, var5);
            }
         }
      }

   }

   private void addZombieToPackInfo(IsoZombie var1, int var2, long var3) {
      if (this.packInfo[var2] == null) {
         this.packInfo[var2] = new ZombieUpdateInfoPacket.PlayerZombiePackInfo();
      }

      if (!this.packInfo[var2].zombies.contains(var1)) {
         this.packInfo[var2].zombies.add(var1);
         this.packInfo[var2].guid = var3;
      }
   }

   public void addZombie(IsoZombie var1) {
      if (var1.OnlineID != -1) {
         double var2 = ServerOptions.instance.ZombieUpdateRadiusLowPriority.getValue();

         for(int var4 = 0; var4 < GameServer.udpEngine.connections.size(); ++var4) {
            UdpConnection var5 = (UdpConnection)GameServer.udpEngine.connections.get(var4);
            if (var5.isFullyConnected()) {
               int var6 = var5.index;
               long var7 = var5.getConnectedGUID();
               if (var2 == 0.0D) {
                  if (var5.ReleventTo(var1.x, var1.y)) {
                     this.addZombieToPackInfo(var1, var6, var7);
                  }
               } else if (var5.ReleventToPlayers((double)var1.x, (double)var1.y, var2)) {
                  this.addZombieToPackInfo(var1, var6, var7);
               }
            }
         }

      }
   }

   private static class l_send {
      static final ZombiePacket zombiePacket = new ZombiePacket();
   }

   private static class l_receive {
      static final Vector2 diff = new Vector2();
      static final ZombiePacket zombiePacket = new ZombiePacket();
   }

   public static class PlayerZombiePackInfo {
      public final Stack zombies = new Stack();
      public long guid;
   }
}
