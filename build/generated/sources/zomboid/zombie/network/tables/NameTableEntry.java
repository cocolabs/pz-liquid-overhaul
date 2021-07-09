package zombie.network.tables;

import zombie.util.PooledObject;
import zombie.util.StringUtils;

public class NameTableEntry extends PooledObject {
   private int m_index = -1;
   private String m_name = "";
   private GenericNameTable m_parentTable;

   protected NameTableEntry() {
   }

   protected void setParentTable(GenericNameTable var1) {
      if (this.m_parentTable != var1) {
         GenericNameTable var2 = this.m_parentTable;
         if (var2 != null) {
            this.m_parentTable = null;
            var2.remove(this);
         }

         this.m_parentTable = var1;
      }
   }

   protected void detachFromParentTable() {
      this.setParentTable((GenericNameTable)null);
   }

   protected void onParentTableClearing() {
      this.m_parentTable = null;
      this.release();
   }

   public void onReleased() {
      this.detachFromParentTable();
      this.m_index = -1;
      this.m_name = "";
   }

   public void setIndex(int var1) {
      if (this.m_index != var1) {
         int var2 = this.m_index;
         this.m_index = var1;
         this.onIndexChangedInternal(var2, this.m_index);
      }
   }

   public int getIndex() {
      return this.m_index;
   }

   public final void setName(String var1) {
      if (!StringUtils.equals(this.m_name, var1)) {
         String var2 = getCanonicalName(var1);
         if (!StringUtils.equals(this.m_name, var2)) {
            String var3 = this.m_name;
            this.m_name = var2;
            this.onNameChangedInternal(var3, this.m_name);
         }
      }
   }

   public String getName() {
      return this.m_name;
   }

   protected final void onNameChangedInternal(String var1, String var2) {
      if (this.m_parentTable != null) {
         this.m_parentTable.onEntryNameChanged(var1, this);
      }

      this.onNameChanged(var1, var2);
   }

   protected final void onIndexChangedInternal(int var1, int var2) {
      if (this.m_parentTable != null) {
         this.m_parentTable.onEntryIndexChanged(var1, this);
      }

      this.onIndexChanged(var1, var2);
   }

   protected void onNameChanged(String var1, String var2) {
   }

   protected void onIndexChanged(int var1, int var2) {
   }

   public final boolean hasName() {
      return isNameValid(this.getName());
   }

   public final boolean hasIndex() {
      return isIndexValid(this.getIndex());
   }

   public static boolean isNameValid(String var0) {
      return !StringUtils.isNullOrWhitespace(var0);
   }

   public static boolean isIndexValid(int var0) {
      return var0 > 0;
   }

   public static String getCanonicalName(String var0) {
      return var0.toLowerCase();
   }
}
