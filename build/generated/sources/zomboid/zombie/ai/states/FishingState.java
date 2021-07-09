package zombie.ai.states;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.core.Rand;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.debug.DebugLog;

public final class FishingState extends State {
   private static final FishingState _instance = new FishingState();
   float pauseTime = 0.0F;

   public static FishingState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      DebugLog.log("FISHINGSTATE - ENTER");
      var1.setVariable("FishingFinished", false);
      this.pauseTime = Rand.Next(60.0F, 120.0F);
   }

   public void execute(IsoGameCharacter var1) {
   }

   public void exit(IsoGameCharacter var1) {
      DebugLog.log("FISHINGSTATE - EXIT");
      var1.clearVariable("FishingStage");
      var1.clearVariable("FishingFinished");
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
   }
}
