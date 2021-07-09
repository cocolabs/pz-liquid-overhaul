package zombie.randomizedWorld.randomizedVehicleStory;

import java.util.ArrayList;
import zombie.characters.IsoZombie;
import zombie.core.Rand;
import zombie.iso.IsoCell;
import zombie.iso.IsoChunk;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMetaGrid;
import zombie.iso.objects.IsoDeadBody;
import zombie.vehicles.BaseVehicle;

public final class RVSFlippedCrash extends RandomizedVehicleStoryBase {
   public RVSFlippedCrash() {
      this.name = "Flipped Crash";
      this.setChance(4);
   }

   public void randomizeVehicleStory(IsoMetaGrid.Zone var1, IsoChunk var2) {
      boolean var3 = Rand.NextBool(5);
      IsoGridSquare var4 = IsoCell.getInstance().getGridSquare(this.minX, this.minY, var1.z);
      if (var4 != null) {
         IsoDirections var5 = Rand.NextBool(2) ? IsoDirections.N : IsoDirections.S;
         if (this.horizontalZone) {
            var5 = Rand.NextBool(2) ? IsoDirections.E : IsoDirections.W;
         }

         BaseVehicle var6 = this.addVehicleFlipped(var1, var4, var2, var3 ? "normalburnt" : "bad", (String)null, (Integer)null, var5, (String)null);
         ArrayList var7 = this.addZombiesOnVehicle(Rand.Next(2, 4), (String)null, (Integer)null, var6);
         if (var7 != null) {
            for(int var8 = 0; var8 < var7.size(); ++var8) {
               IsoZombie var9 = (IsoZombie)var7.get(var8);
               var9.upKillCount = false;
               this.addBloodSplat(var9.getSquare(), Rand.Next(10, 20));
               if (var3) {
                  var9.setSkeleton(true);
                  var9.getHumanVisual().setSkinTextureIndex(0);
               } else {
                  var9.DoZombieInventory();
                  if (Rand.NextBool(10)) {
                     var9.setFakeDead(true);
                     var9.bCrawling = true;
                     var9.setCanWalk(false);
                     var9.setCrawlerType(1);
                  }
               }

               new IsoDeadBody(var9, false);
            }

         }
      }
   }
}
