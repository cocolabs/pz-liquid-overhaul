package zombie.ai.states;

import zombie.GameTime;
import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.iso.Vector2;
import zombie.network.GameServer;
import zombie.util.Type;

public final class LungeState extends State {
   private static final LungeState _instance = new LungeState();
   private final Vector2 temp = new Vector2();
   int turnTimer = 0;

   public static LungeState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      IsoZombie var2 = (IsoZombie)var1;
      if (System.currentTimeMillis() - var2.LungeSoundTime > 5000L) {
         String var3 = "MaleZombieAttack";
         if (var2.isFemale()) {
            var3 = "FemaleZombieAttack";
         }

         if (GameServer.bServer) {
            GameServer.sendZombieSound(IsoZombie.ZombieSound.Lunge, var2);
         }

         var2.LungeSoundTime = System.currentTimeMillis();
      }

      var2.LungeTimer = 180.0F;
   }

   public void execute(IsoGameCharacter var1) {
      IsoZombie var2 = (IsoZombie)var1;
      var1.setOnFloor(false);
      var1.setShootable(true);
      if (var2.bLunger) {
         var2.walkVariantUse = "ZombieWalk3";
      }

      var2.LungeTimer -= GameTime.getInstance().getMultiplier() / 1.6F;
      IsoPlayer var3 = (IsoPlayer)Type.tryCastTo(var2.getTarget(), IsoPlayer.class);
      if (var3 != null && var3.isGhostMode()) {
         var2.LungeTimer = 0.0F;
      }

      if (var2.LungeTimer < 0.0F) {
         var2.LungeTimer = 0.0F;
      }

      if (var2.LungeTimer <= 0.0F) {
         var2.AllowRepathDelay = 0.0F;
      }

      this.temp.x = var2.vectorToTarget.x;
      this.temp.y = var2.vectorToTarget.y;
      var2.getZombieLungeSpeed(this.temp);
      this.temp.normalize();
      var2.setForwardDirection(this.temp);
      var2.DirectionFromVector(this.temp);
      var2.getVectorFromDirection(var2.getForwardDirection());
      var2.setForwardDirection(this.temp);
      boolean var4 = false;
      if (var2.NetRemoteState != 4) {
         var2.NetRemoteState = 4;
         var4 = true;
      }

      if (GameServer.bServer && var4) {
         GameServer.sendZombie((IsoZombie)var1);
      }

      if (!var2.isTargetLocationKnown() && var2.LastTargetSeenX != -1 && !var1.getPathFindBehavior2().isTargetLocation((float)var2.LastTargetSeenX + 0.5F, (float)var2.LastTargetSeenY + 0.5F, (float)var2.LastTargetSeenZ)) {
         var2.LungeTimer = 0.0F;
         var1.pathToLocation(var2.LastTargetSeenX, var2.LastTargetSeenY, var2.LastTargetSeenZ);
      }

   }

   public void exit(IsoGameCharacter var1) {
   }

   public boolean isMoving(IsoGameCharacter var1) {
      return true;
   }
}
