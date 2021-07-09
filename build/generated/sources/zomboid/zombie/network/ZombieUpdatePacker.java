package zombie.network;

import java.nio.ByteBuffer;
import zombie.characters.IsoZombie;
import zombie.network.packets.ZombieUpdateInfoPacket;

public class ZombieUpdatePacker {
   public static final ZombieUpdatePacker instance = new ZombieUpdatePacker();
   private final ZombieUpdateInfoPacket m_packet = new ZombieUpdateInfoPacket();

   public void receivePacket(ByteBuffer var1) {
      this.m_packet.receive(var1);
   }

   public void sendPacket() {
      this.m_packet.send();
   }

   public void clearPacket() {
      this.m_packet.clear();
   }

   public void addZombieToPacket(IsoZombie var1) {
      this.m_packet.addZombie(var1);
   }
}
