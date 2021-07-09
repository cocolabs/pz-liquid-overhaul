package zombie.network.packets;

import java.nio.ByteBuffer;
import zombie.characters.IsoZombie;
import zombie.core.network.ByteBufferWriter;

public class ZombiePacket extends AbstractPacket {
   public static final int PACKET_SIZE_BYTES = 23;
   public short id;
   public float x;
   public float y;
   public float z;
   public byte animIndex;
   public byte animInfo;
   public byte dir;
   public byte animFrameIncrease;
   public byte flags;
   public int descriptorID;

   public void parse(ByteBuffer var1) {
      this.id = var1.getShort();
      this.x = var1.getFloat();
      this.y = var1.getFloat();
      this.z = var1.getFloat();
      this.animIndex = var1.get();
      this.animInfo = var1.get();
      this.dir = var1.get();
      this.animFrameIncrease = var1.get();
      this.flags = var1.get();
      this.descriptorID = var1.getInt();
   }

   public void write(ByteBufferWriter var1) {
      long var2 = (long)var1.bb.position();
      var1.putShort(this.id);
      var1.putFloat(this.x);
      var1.putFloat(this.y);
      var1.putFloat(this.z);
      var1.putByte(this.animIndex);
      var1.putByte(this.animInfo);
      var1.putByte(this.dir);
      var1.putByte(this.animFrameIncrease);
      var1.putByte(this.flags);
      var1.putInt(this.descriptorID);

      assert (long)var1.bb.position() - var2 == 23L;

   }

   public int getPacketSizeBytes() {
      return 23;
   }

   public boolean isIdleSound() {
      return (this.flags & 128) != 0;
   }

   public int getThump() {
      return this.flags >> 4 & 7;
   }

   public boolean isOnfire() {
      return (this.flags & 8) != 0;
   }

   public boolean isImfd() {
      return (this.flags & 4) != 0;
   }

   public boolean isCrawling() {
      return (this.flags & 2) != 0;
   }

   public boolean isOnFloor() {
      return (this.flags & 1) != 0;
   }

   public boolean isFinished() {
      return (this.animInfo & 1) != 0;
   }

   public boolean isLooped() {
      return (this.animInfo & 2) != 0;
   }

   public byte getFrame() {
      return (byte)(this.animInfo >> 2);
   }

   public void set(IsoZombie var1) {
      byte var2 = (byte)((int)var1.def.Frame);
      byte var3 = (byte)(var1.def.Finished ? 1 : 0);
      byte var4 = (byte)(var1.def.Looped ? 1 : 0);
      byte var5 = (byte)(var1.isOnFloor() ? 1 : 0);
      byte var6 = (byte)(var1.bCrawling ? 1 : 0);
      byte var7 = (byte)(var1.isIgnoreMovementForDirection() ? 1 : 0);
      byte var8 = (byte)(var1.isOnFire() ? 1 : 0);
      byte var9 = (byte)var1.thumpFlag;
      byte var10 = (byte)(var1.mpIdleSound ? 1 : 0);
      this.id = var1.OnlineID;
      this.x = var1.x;
      this.y = var1.y;
      this.z = var1.z;
      this.animIndex = (byte)var1.legsSprite.AnimStack.indexOf(var1.legsSprite.CurrentAnim);
      this.animInfo = (byte)(var3 | var4 << 1 | var2 << 2);
      this.dir = (byte)var1.dir.index();
      this.animFrameIncrease = (byte)((int)(var1.def.AnimFrameIncrease * 128.0F));
      this.flags = (byte)(var5 | var6 << 1 | var7 << 2 | var8 << 3 | var9 << 4 | var10 << 7);
      this.descriptorID = var1.getPersistentOutfitID();
      var1.thumpSent = true;
   }
}
