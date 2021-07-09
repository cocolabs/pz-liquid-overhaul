package zombie.randomizedWorld.randomizedZoneStory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import zombie.SandboxOptions;
import zombie.core.Rand;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMetaGrid;
import zombie.iso.IsoObject;
import zombie.iso.IsoWorld;
import zombie.iso.objects.IsoDeadBody;
import zombie.randomizedWorld.RandomizedWorldBase;

public class RandomizedZoneStoryBase extends RandomizedWorldBase {
   public static final int baseChance = 15;
   public static int totalChance = 0;
   public static final String zoneStory = "ZoneStory";
   public int chance = 0;
   protected int minZoneWidth = 0;
   protected int minZoneHeight = 0;
   public ArrayList zoneType = new ArrayList();
   private static HashMap rzsMap = new HashMap();

   public static boolean isValidForStory(IsoMetaGrid.Zone var0, boolean var1) {
      if (var0.pickedXForZoneStory > 0 && var0.pickedYForZoneStory > 0 && var0.pickedRZStory != null) {
         System.out.println("picked story, is it streamed enough?");
         if (checkCanSpawnStory(var0, var1)) {
            var0.pickedRZStory.randomizeZoneStory(var0);
            var0.pickedRZStory = null;
            var0.pickedXForZoneStory = 0;
            var0.pickedYForZoneStory = 0;
            System.out.println("should be done");
         } else {
            System.out.println("not streamed yet");
         }
      }

      if (!var1 && var0.hourLastSeen != 0) {
         return false;
      } else if (!var1 && var0.haveConstruction) {
         return false;
      } else if ("ZoneStory".equals(var0.type)) {
         doRandomStory(var0);
         return true;
      } else {
         return false;
      }
   }

   public static void initAllRZSMapChance(IsoMetaGrid.Zone var0) {
      totalChance = 0;
      rzsMap.clear();

      for(int var1 = 0; var1 < IsoWorld.instance.getRandomizedZoneList().size(); ++var1) {
         RandomizedZoneStoryBase var2 = (RandomizedZoneStoryBase)IsoWorld.instance.getRandomizedZoneList().get(var1);
         if (var2.isValid(var0, false) && var2.isTimeValid(false)) {
            totalChance += ((RandomizedZoneStoryBase)IsoWorld.instance.getRandomizedZoneList().get(var1)).chance;
            rzsMap.put(IsoWorld.instance.getRandomizedZoneList().get(var1), ((RandomizedZoneStoryBase)IsoWorld.instance.getRandomizedZoneList().get(var1)).chance);
         }
      }

   }

   public boolean isValid(IsoMetaGrid.Zone var1, boolean var2) {
      boolean var3 = false;

      for(int var4 = 0; var4 < this.zoneType.size(); ++var4) {
         if (((String)this.zoneType.get(var4)).equals(var1.name)) {
            var3 = true;
            break;
         }
      }

      return var3 && var1.w >= this.minZoneWidth && var1.h >= this.minZoneHeight;
   }

   private static boolean doRandomStory(IsoMetaGrid.Zone var0) {
      ++var0.hourLastSeen;
      byte var1 = 6;
      switch(SandboxOptions.instance.ZoneStoryChance.getValue()) {
      case 1:
         return false;
      case 2:
         var1 = 2;
      case 3:
      default:
         break;
      case 4:
         var1 = 12;
         break;
      case 5:
         var1 = 20;
         break;
      case 6:
         var1 = 40;
      }

      if (Rand.Next(100) < var1) {
         RandomizedZoneStoryBase var2 = null;
         initAllRZSMapChance(var0);
         var2 = getRandomStory();
         if (var2 == null) {
            return false;
         } else {
            int var3 = var0.x;
            int var4 = var0.y;
            int var5 = var0.x + var0.w - var2.minZoneWidth / 2;
            int var6 = var0.y + var0.h - var2.minZoneHeight / 2;
            var0.pickedXForZoneStory = Rand.Next(var3, var5 + 1);
            var0.pickedYForZoneStory = Rand.Next(var4, var6 + 1);
            var0.pickedRZStory = var2;
            return true;
         }
      } else {
         return false;
      }
   }

   public IsoGridSquare getRandomFreeSquare(RandomizedZoneStoryBase var1, IsoMetaGrid.Zone var2) {
      IsoGridSquare var3 = null;

      for(int var4 = 0; var4 < 1000; ++var4) {
         int var5 = Rand.Next(var2.pickedXForZoneStory - var1.minZoneWidth / 2, var2.pickedXForZoneStory + var1.minZoneWidth / 2);
         int var6 = Rand.Next(var2.pickedYForZoneStory - var1.minZoneHeight / 2, var2.pickedYForZoneStory + var1.minZoneHeight / 2);
         var3 = this.getSq(var5, var6, var2.z);
         if (var3 != null && var3.isFree(false)) {
            return var3;
         }
      }

      return null;
   }

   private static RandomizedZoneStoryBase getRandomStory() {
      int var0 = Rand.Next(totalChance);
      Iterator var1 = rzsMap.keySet().iterator();
      int var2 = 0;

      RandomizedZoneStoryBase var3;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var3 = (RandomizedZoneStoryBase)var1.next();
         var2 += (Integer)rzsMap.get(var3);
      } while(var0 >= var2);

      return var3;
   }

   private static boolean checkCanSpawnStory(IsoMetaGrid.Zone var0, boolean var1) {
      IsoGridSquare var2 = IsoWorld.instance.getCell().getGridSquare(var0.pickedXForZoneStory - var0.pickedRZStory.minZoneWidth / 2 - 2, var0.pickedYForZoneStory - var0.pickedRZStory.minZoneHeight / 2 - 2, var0.z);
      if (var2 == null) {
         return false;
      } else {
         var2 = IsoWorld.instance.getCell().getGridSquare(var0.pickedXForZoneStory + var0.pickedRZStory.minZoneWidth / 2 + 2, var0.pickedYForZoneStory + var0.pickedRZStory.minZoneHeight / 2 + 2, var0.z);
         return var2 != null;
      }
   }

   public void randomizeZoneStory(IsoMetaGrid.Zone var1) {
   }

   public boolean isValid() {
      return true;
   }

   public void cleanAreaForStory(RandomizedZoneStoryBase var1, IsoMetaGrid.Zone var2) {
      int var3 = var2.pickedXForZoneStory - var1.minZoneWidth / 2 - 1;
      int var4 = var2.pickedYForZoneStory - var1.minZoneHeight / 2 - 1;
      int var5 = var2.pickedXForZoneStory + var1.minZoneWidth / 2 + 1;
      int var6 = var2.pickedYForZoneStory + var1.minZoneHeight / 2 + 1;

      for(int var7 = var3; var7 < var5; ++var7) {
         for(int var8 = var4; var8 < var6; ++var8) {
            IsoGridSquare var9 = IsoWorld.instance.getCell().getGridSquare(var7, var8, var2.z);
            if (var9 != null) {
               var9.removeBlood(false, false);

               int var10;
               for(var10 = 0; var10 < var9.getObjects().size(); ++var10) {
                  IsoObject var11 = (IsoObject)var9.getObjects().get(var10);
                  if (var9.getFloor() != var11) {
                     var9.RemoveTileObject(var11);
                  }
               }

               for(var10 = 0; var10 < var9.getSpecialObjects().size(); ++var10) {
                  var9.RemoveTileObject((IsoObject)var9.getSpecialObjects().get(var10));
               }

               for(var10 = 0; var10 < var9.getDeadBodys().size(); ++var10) {
                  var9.removeCorpse((IsoDeadBody)var9.getDeadBodys().get(var10), false);
               }

               var9.RecalcProperties();
               var9.RecalcAllWithNeighbours(true);
            }
         }
      }

   }

   public int getMinimumWidth() {
      return this.minZoneWidth;
   }

   public int getMinimumHeight() {
      return this.minZoneHeight;
   }

   public static enum ZoneType {
      Forest,
      Beach,
      Lake;
   }
}
