package zombie.vehicles;

import zombie.ai.State;
import zombie.ai.astar.AStarPathFinder;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.iso.IsoChunk;
import zombie.iso.IsoWorld;
import zombie.network.GameServer;
import zombie.network.ServerMap;

public final class PathFindState2 extends State {
   public void enter(IsoGameCharacter var1) {
      var1.setVariable("bPathfind", true);
      var1.setVariable("bMoving", false);
   }

   public void execute(IsoGameCharacter var1) {
      PathFindBehavior2.BehaviorResult var2 = var1.getPathFindBehavior2().update();
      if (var2 == PathFindBehavior2.BehaviorResult.Failed) {
         var1.setPathFindIndex(-1);
         var1.setVariable("bPathfind", false);
         var1.setVariable("bMoving", false);
      } else if (var2 == PathFindBehavior2.BehaviorResult.Succeeded) {
         int var3 = (int)var1.getPathFindBehavior2().getTargetX();
         int var4 = (int)var1.getPathFindBehavior2().getTargetY();
         IsoChunk var5 = GameServer.bServer ? ServerMap.instance.getChunk(var3 / 10, var4 / 10) : IsoWorld.instance.CurrentCell.getChunkForGridSquare(var3, var4, 0);
         if (var5 == null) {
            var1.setVariable("bPathfind", false);
            var1.setVariable("bMoving", true);
         } else {
            var1.setVariable("bPathfind", false);
            var1.setVariable("bMoving", false);
            var1.setPath2((PolygonalMap2.Path)null);
         }
      }
   }

   public void exit(IsoGameCharacter var1) {
      if (var1 instanceof IsoZombie) {
         ((IsoZombie)var1).AllowRepathDelay = 0.0F;
      }

      var1.setVariable("bPathfind", false);
      var1.setVariable("bMoving", false);
      var1.setVariable("ShouldBeCrawling", false);
      PolygonalMap2.instance.cancelRequest(var1);
      var1.getFinder().progress = AStarPathFinder.PathFindProgress.notrunning;
      var1.setPath2((PolygonalMap2.Path)null);
   }

   public boolean isMoving(IsoGameCharacter var1) {
      return var1.isMoving();
   }
}
