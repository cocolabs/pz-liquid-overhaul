package zombie.network.packets;

import java.nio.ByteBuffer;
import zombie.core.network.ByteBufferWriter;

public abstract class AbstractPacket {
   public abstract void parse(ByteBuffer var1);

   public abstract void write(ByteBufferWriter var1);

   public abstract int getPacketSizeBytes();
}
