package zombie.network.tables;

import java.util.ArrayList;
import java.util.HashMap;
import zombie.util.Pool;
import zombie.util.map.IntKeyOpenHashMap;

public class GenericNameTable {
   private final ArrayList m_entries = new ArrayList();
   private final HashMap m_nameTable = new HashMap();
   private final IntKeyOpenHashMap m_indexTable = new IntKeyOpenHashMap();
   private static final Pool s_pool = new Pool(NameTableEntry::new);
   private final Object m_threadLock = "GenericNameTable ThreadLock";

   public void clear() {
      synchronized(this.m_threadLock) {
         this.m_entries.forEach(NameTableEntry::onParentTableClearing);
         this.m_entries.clear();
         this.m_nameTable.clear();
         this.m_indexTable.clear();
      }
   }

   public final NameTableEntry get(int var1) {
      synchronized(this.m_threadLock) {
         return (NameTableEntry)this.m_indexTable.get(var1);
      }
   }

   public final NameTableEntry get(String var1) {
      String var2 = NameTableEntry.getCanonicalName(var1);
      synchronized(this.m_threadLock) {
         return (NameTableEntry)this.m_nameTable.get(var2);
      }
   }

   protected static NameTableEntry allocEntry() {
      return (NameTableEntry)s_pool.alloc();
   }

   public final NameTableEntry getOrCreate(String var1) {
      String var2 = NameTableEntry.getCanonicalName(var1);
      if (!NameTableEntry.isNameValid(var2)) {
         return null;
      } else {
         synchronized(this.m_threadLock) {
            NameTableEntry var4 = (NameTableEntry)this.m_nameTable.get(var2);
            if (var4 == null) {
               var4 = (NameTableEntry)this.m_nameTable.get(var2);
               if (var4 == null) {
                  var4 = allocEntry();
                  var4.setName(var2);
                  var4.setIndex(-1);
                  this.add(var4);
               }
            }

            return var4;
         }
      }
   }

   protected final void add(NameTableEntry var1) {
      synchronized(this.m_threadLock) {
         this.m_entries.add(var1);
         if (var1.hasName()) {
            this.m_nameTable.put(var1.getName(), var1);
         }

         if (var1.hasIndex()) {
            this.m_indexTable.put(var1.getIndex(), var1);
         }

         var1.setParentTable(this);
      }
   }

   protected final void remove(NameTableEntry var1) {
      synchronized(this.m_threadLock) {
         this.m_entries.remove(var1);
         if (var1.hasName()) {
            this.m_nameTable.remove(var1.getName());
         }

         if (var1.hasIndex()) {
            this.m_indexTable.remove(var1.getIndex());
         }

         var1.detachFromParentTable();
      }
   }

   protected final void onEntryNameChanged(String var1, NameTableEntry var2) {
      synchronized(this.m_threadLock) {
         if (NameTableEntry.isNameValid(var1)) {
            this.m_nameTable.remove(var1);
         }

         if (var2.hasName()) {
            this.m_nameTable.put(var2.getName(), var2);
         }

      }
   }

   protected final void onEntryIndexChanged(int var1, NameTableEntry var2) {
      synchronized(this.m_threadLock) {
         if (NameTableEntry.isIndexValid(var1)) {
            this.m_indexTable.remove(var1);
         }

         if (var2.hasIndex()) {
            this.m_indexTable.put(var2.getIndex(), var2);
         }

      }
   }
}
