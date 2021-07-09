package zombie.util;

import zombie.util.list.PZArrayUtil;

public final class PooledFloatArrayObject extends PooledObject {
   private float[] m_array;
   private static final Pool s_pool = new Pool(PooledFloatArrayObject::new);

   public PooledFloatArrayObject() {
      this.m_array = PZArrayUtil.emptyFloatArray;
   }

   private void initCapacity(int var1) {
      if (this.m_array.length != var1) {
         this.m_array = new float[var1];
      }

   }

   public float[] array() {
      return this.m_array;
   }

   public static PooledFloatArrayObject alloc(int var0) {
      PooledFloatArrayObject var1 = (PooledFloatArrayObject)s_pool.alloc();
      var1.initCapacity(var0);
      return var1;
   }

   public float get(int var1) {
      return this.m_array[var1];
   }

   public void set(int var1, float var2) {
      this.m_array[var1] = var2;
   }
}
