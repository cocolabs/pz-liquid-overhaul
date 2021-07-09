package zombie.ai.states;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.iso.IsoDirections;

public final class CollideWithWallState extends State {
   private static final CollideWithWallState _instance = new CollideWithWallState();

   public static CollideWithWallState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      var1.setIgnoreMovement(true);
      if (var1 instanceof IsoPlayer) {
         ((IsoPlayer)var1).setIsAiming(false);
      }

      if (var1.isCollidedN()) {
         var1.setDir(IsoDirections.N);
      }

      if (var1.isCollidedS()) {
         var1.setDir(IsoDirections.S);
      }

      if (var1.isCollidedE()) {
         var1.setDir(IsoDirections.E);
      }

      if (var1.isCollidedW()) {
         var1.setDir(IsoDirections.W);
      }

   }

   public void execute(IsoGameCharacter var1) {
      var1.setLastCollideTime(70.0F);
   }

   public void exit(IsoGameCharacter var1) {
      var1.setCollideType((String)null);
      var1.setIgnoreMovement(false);
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
   }
}
