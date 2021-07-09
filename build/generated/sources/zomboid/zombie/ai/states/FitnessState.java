package zombie.ai.states;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;

public final class FitnessState extends State {
   private static final FitnessState _instance = new FitnessState();

   public static FitnessState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      var1.setIgnoreMovement(true);
      var1.setVariable("FitnessFinished", false);
      var1.clearVariable("ExerciseStarted");
      var1.clearVariable("ExerciseEnded");
   }

   public void execute(IsoGameCharacter var1) {
   }

   public void exit(IsoGameCharacter var1) {
      var1.setIgnoreMovement(false);
      var1.clearVariable("FitnessFinished");
      var1.clearVariable("ExerciseStarted");
      var1.clearVariable("ExerciseHand");
      var1.clearVariable("FitnessStruggle");
      var1.setVariable("ExerciseEnded", true);
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
   }
}
