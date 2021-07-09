package zombie.randomizedWorld.randomizedVehicleStory;

import java.util.ArrayList;
import zombie.characters.IsoZombie;
import zombie.core.Rand;
import zombie.iso.IsoChunk;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMetaGrid;
import zombie.iso.objects.IsoDeadBody;
import zombie.vehicles.BaseVehicle;

public final class RVSBurntCar extends RandomizedVehicleStoryBase {
   public RVSBurntCar() {
      this.name = "Burnt Car";
      this.minZoneWidth = 5;
      this.minZoneHeight = 3;
      this.setChance(13);
   }

   public void randomizeVehicleStory(IsoMetaGrid.Zone var1, IsoChunk var2) {
      IsoGridSquare var3 = this.getCenterOfChunk(var1, var2);
      if (var3 != null) {
         BaseVehicle var4 = this.addVehicle(var1, var3, var2, "normalburnt", (String)null, IsoDirections.getRandom());
         if (Rand.Next(10) < 5) {
            ArrayList var5 = this.addZombiesOnVehicle(Rand.Next(0, 3), (String)null, (Integer)null, var4);
            if (var5 == null) {
               return;
            }

            for(int var6 = 0; var6 < var5.size(); ++var6) {
               IsoZombie var7 = (IsoZombie)var5.get(var6);
               var7.setSkeleton(true);
               var7.getHumanVisual().setSkinTextureIndex(0);
               new IsoDeadBody(var7, false);
            }
         }

      }
   }
}
