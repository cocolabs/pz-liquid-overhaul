package zombie.ai.states;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.core.Core;
import zombie.core.Rand;
import zombie.iso.objects.RainManager;

public final class ZombieIdleState extends State {
   private static final ZombieIdleState _instance = new ZombieIdleState();

   public static ZombieIdleState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      ((IsoZombie)var1).soundSourceTarget = null;
      ((IsoZombie)var1).soundAttract = 0.0F;
      ((IsoZombie)var1).movex = 0.0F;
      ((IsoZombie)var1).movey = 0.0F;
      var1.setStateEventDelayTimer(this.pickRandomWanderInterval());
   }

   public void execute(IsoGameCharacter var1) {
      IsoZombie var2 = (IsoZombie)var1;
      var2.NetRemoteState = 1;
      var2.setRemoteMoveX(0.0F);
      var2.setRemoteMoveY(0.0F);
      var2.movex = 0.0F;
      var2.movey = 0.0F;
      if (Core.bLastStand) {
         IsoPlayer var6 = null;
         float var7 = 1000000.0F;

         for(int var5 = 0; var5 < IsoPlayer.numPlayers; ++var5) {
            if (IsoPlayer.players[var5] != null && IsoPlayer.players[var5].DistTo(var1) < var7 && !IsoPlayer.players[var5].isDead()) {
               var7 = IsoPlayer.players[var5].DistTo(var1);
               var6 = IsoPlayer.players[var5];
            }
         }

         if (var6 != null) {
            var2.pathToCharacter(var6);
         }

      } else {
         if (((IsoZombie)var1).bCrawling) {
            var1.setOnFloor(true);
         } else {
            var1.setOnFloor(false);
         }

         if (!var2.bIndoorZombie) {
            if (!var2.isUseless()) {
               if (var2.getStateEventDelayTimer() <= 0.0F) {
                  var1.setStateEventDelayTimer(this.pickRandomWanderInterval());
                  int var3 = (int)var1.getX() + (Rand.Next(8) - 4);
                  int var4 = (int)var1.getY() + (Rand.Next(8) - 4);
                  if (var1.getCell().getGridSquare((double)var3, (double)var4, (double)var1.getZ()) != null && var1.getCell().getGridSquare((double)var3, (double)var4, (double)var1.getZ()).isFree(true)) {
                     var1.pathToLocation(var3, var4, (int)var1.getZ());
                     var2.AllowRepathDelay = 200.0F;
                  }
               }

            }
         }
      }
   }

   public void exit(IsoGameCharacter var1) {
   }

   private float pickRandomWanderInterval() {
      float var1 = (float)Rand.Next(400, 1000);
      if (!RainManager.isRaining()) {
         var1 *= 1.5F;
      }

      return var1;
   }
}
