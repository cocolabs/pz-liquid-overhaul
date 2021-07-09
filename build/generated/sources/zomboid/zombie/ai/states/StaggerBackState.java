package zombie.ai.states;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;

public final class StaggerBackState extends State {
   private static final StaggerBackState _instance = new StaggerBackState();

   public static StaggerBackState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      var1.setFallOnFront(false);
      var1.setStateEventDelayTimer(this.getMaxStaggerTime(var1));
   }

   public void execute(IsoGameCharacter var1) {
      var1.getVectorFromDirection(var1.getForwardDirection());
   }

   public void exit(IsoGameCharacter var1) {
      if (var1.isZombie()) {
         ((IsoZombie)var1).bStaggerBack = false;
      }

      var1.setShootable(true);
   }

   private float getMaxStaggerTime(IsoGameCharacter var1) {
      float var2 = 35.0F * var1.getHitForce() * var1.getStaggerTimeMod();
      if (var2 < 20.0F) {
         return 20.0F;
      } else {
         return var2 > 30.0F ? 30.0F : var2;
      }
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
      if (var2.m_EventName.equalsIgnoreCase("FallOnFront")) {
         var1.setFallOnFront(Boolean.parseBoolean(var2.m_ParameterValue));
      }

   }
}
