package zombie.ai.states;

import java.util.HashMap;
import zombie.GameTime;
import zombie.ZomboidGlobals;
import zombie.ai.State;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.characters.MoveDeltaModifiers;
import zombie.characters.Stats;
import zombie.characters.BodyDamage.BodyPart;
import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.Moodles.MoodleType;
import zombie.characters.skills.PerkFactory;
import zombie.core.Rand;
import zombie.core.math.PZMath;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;
import zombie.iso.IsoWorld;
import zombie.iso.Vector2;
import zombie.iso.SpriteDetails.IsoFlagType;
import zombie.util.Type;

public final class ClimbOverFenceState extends State {
   private static final ClimbOverFenceState _instance = new ClimbOverFenceState();
   static final Integer PARAM_START_X = 0;
   static final Integer PARAM_START_Y = 1;
   static final Integer PARAM_Z = 2;
   static final Integer PARAM_END_X = 3;
   static final Integer PARAM_END_Y = 4;
   static final Integer PARAM_DIR = 5;
   static final Integer PARAM_ZOMBIE_ON_FLOOR = 6;
   static final Integer PARAM_PREV_STATE = 7;
   static final Integer PARAM_SCRATCH = 8;
   static final Integer PARAM_COUNTER = 9;
   static final Integer PARAM_SOLID_FLOOR = 10;
   static final Integer PARAM_SHEET_ROPE = 11;
   static final Integer PARAM_RUN = 12;
   static final Integer PARAM_SPRINT = 13;
   static final Integer PARAM_COLLIDABLE = 14;

   public static ClimbOverFenceState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      var1.setVariable("FenceLungeX", 0.0F);
      var1.setVariable("FenceLungeY", 0.0F);
      HashMap var2 = var1.getStateMachineParams(this);
      var1.setIgnoreMovement(true);
      Stats var10000;
      if (var2.get(PARAM_RUN) == Boolean.TRUE) {
         var1.setVariable("VaultOverRun", true);
         var10000 = var1.getStats();
         var10000.endurance = (float)((double)var10000.endurance - ZomboidGlobals.RunningEnduranceReduce * 300.0D);
      } else if (var2.get(PARAM_SPRINT) == Boolean.TRUE) {
         var1.setVariable("VaultOverSprint", true);
         var10000 = var1.getStats();
         var10000.endurance = (float)((double)var10000.endurance - ZomboidGlobals.RunningEnduranceReduce * 700.0D);
      }

      boolean var3 = var2.get(PARAM_COUNTER) == Boolean.TRUE;
      var1.setVariable("ClimbingFence", true);
      var1.setVariable("ClimbFenceStarted", false);
      var1.setVariable("ClimbFenceFinished", false);
      var1.setVariable("ClimbFenceOutcome", var3 ? "obstacle" : "success");
      var1.clearVariable("ClimbFenceFlopped");
      IsoZombie var4 = (IsoZombie)Type.tryCastTo(var1, IsoZombie.class);
      if (!var3 && var4 != null && var4.shouldDoFenceLunge()) {
         var1.setVariable("ClimbFenceOutcome", "lunge");
         this.setLungeXVars(var4);
      }

      if (var2.get(PARAM_SOLID_FLOOR) == Boolean.FALSE) {
         var1.setVariable("ClimbFenceOutcome", "falling");
      }

      if (!(var1 instanceof IsoZombie) && var2.get(PARAM_SHEET_ROPE) == Boolean.TRUE) {
         var1.setVariable("ClimbFenceOutcome", "rope");
      }

      if (var1 instanceof IsoPlayer && ((IsoPlayer)var1).isLocalPlayer()) {
         ((IsoPlayer)var1).dirtyRecalcGridStackTime = 20.0F;
      }

   }

   private void setLungeXVars(IsoZombie var1) {
      IsoMovingObject var2 = var1.getTarget();
      if (var2 != null) {
         var1.setVariable("FenceLungeX", 0.0F);
         var1.setVariable("FenceLungeY", 0.0F);
         float var3 = 0.0F;
         Vector2 var4 = var1.getForwardDirection();
         PZMath.SideOfLine var5 = PZMath.testSideOfLine(var1.x, var1.y, var1.x + var4.x, var1.y + var4.y, var2.x, var2.y);
         float var6 = (float)Math.acos((double)var1.getDotWithForwardDirection(var2.x, var2.y));
         float var7 = PZMath.clamp(PZMath.radToDeg(var6), 0.0F, 90.0F);
         switch(var5) {
         case Left:
            var3 = -var7 / 90.0F;
            break;
         case OnLine:
            var3 = 0.0F;
            break;
         case Right:
            var3 = var7 / 90.0F;
         }

         var1.setVariable("FenceLungeX", var3);
      }
   }

   public void execute(IsoGameCharacter var1) {
      HashMap var2 = var1.getStateMachineParams(this);
      IsoDirections var3 = (IsoDirections)Type.tryCastTo(var2.get(PARAM_DIR), IsoDirections.class);
      int var4 = (Integer)var2.get(PARAM_END_X);
      int var5 = (Integer)var2.get(PARAM_END_Y);
      boolean var6 = ClimbThroughWindowState.instance().isPastInnerEdgeOfSquare(var1, var4, var5, var3);
      var1.setCollidable(var6);
      var1.setAnimated(true);
      if (var3 == IsoDirections.N) {
         var1.setDir(IsoDirections.N);
      } else if (var3 == IsoDirections.S) {
         var1.setDir(IsoDirections.S);
      } else if (var3 == IsoDirections.W) {
         var1.setDir(IsoDirections.W);
      } else if (var3 == IsoDirections.E) {
         var1.setDir(IsoDirections.E);
      }

      String var7 = var1.getVariableString("ClimbFenceOutcome");
      float var8;
      if (!"lunge".equals(var7)) {
         var8 = 0.05F;
         if (var3 != IsoDirections.N && var3 != IsoDirections.S) {
            if (var3 == IsoDirections.W || var3 == IsoDirections.E) {
               var1.y = var1.ny = PZMath.clamp(var1.y, (float)var5 + var8, (float)(var5 + 1) - var8);
            }
         } else {
            var1.x = var1.nx = PZMath.clamp(var1.x, (float)var4 + var8, (float)(var4 + 1) - var8);
         }
      }

      if (var1.getVariableBoolean("ClimbFenceStarted") && !"back".equals(var7) && !"fallback".equals(var7) && !"lunge".equalsIgnoreCase(var7) && !"obstacle".equals(var7) && !"obstacleEnd".equals(var7)) {
         var8 = (float)(Integer)var2.get(PARAM_START_X);
         float var9 = (float)(Integer)var2.get(PARAM_START_Y);
         switch(var3) {
         case N:
            var9 -= 0.1F;
            break;
         case S:
            ++var9;
            break;
         case W:
            var8 -= 0.1F;
            break;
         case E:
            ++var8;
         }

         if ((int)var1.x != (int)var8 && (var3 == IsoDirections.W || var3 == IsoDirections.E)) {
            this.slideX(var1, var8);
         }

         if ((int)var1.y != (int)var9 && (var3 == IsoDirections.N || var3 == IsoDirections.S)) {
            this.slideY(var1, var9);
         }
      }

      if (var1 instanceof IsoZombie) {
         boolean var10 = var2.get(PARAM_ZOMBIE_ON_FLOOR) == Boolean.TRUE;
         var1.setOnFloor(var10);
         ((IsoZombie)var1).bKnockedDown = var10;
         var1.setFallOnFront(var10);
      }

   }

   public void exit(IsoGameCharacter var1) {
      HashMap var2 = var1.getStateMachineParams(this);
      if (var1 instanceof IsoPlayer && "fall".equals(var1.getVariableString("ClimbFenceOutcome"))) {
         var1.setSprinting(false);
      }

      var1.clearVariable("ClimbingFence");
      var1.clearVariable("ClimbFenceFinished");
      var1.clearVariable("ClimbFenceOutcome");
      var1.clearVariable("ClimbFenceStarted");
      var1.clearVariable("ClimbFenceFlopped");
      var1.ClearVariable("VaultOverSprint");
      var1.ClearVariable("VaultOverRun");
      var1.setIgnoreMovement(false);
      IsoZombie var3 = (IsoZombie)Type.tryCastTo(var1, IsoZombie.class);
      if (var3 != null) {
         var3.AllowRepathDelay = 0.0F;
         if (var2.get(PARAM_PREV_STATE) == PathFindState.instance()) {
            if (var1.getPathFindBehavior2().getTargetChar() == null) {
               var1.setVariable("bPathfind", true);
               var1.setVariable("bMoving", false);
            } else if (var3.isTargetLocationKnown()) {
               var1.pathToCharacter(var1.getPathFindBehavior2().getTargetChar());
            } else if (var3.LastTargetSeenX != -1) {
               var1.pathToLocation(var3.LastTargetSeenX, var3.LastTargetSeenY, var3.LastTargetSeenZ);
            }
         } else if (var2.get(PARAM_PREV_STATE) == WalkTowardState.instance()) {
            var1.setVariable("bPathFind", false);
            var1.setVariable("bMoving", true);
         }
      }

   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
      HashMap var3 = var1.getStateMachineParams(this);
      if (var2.m_EventName.equalsIgnoreCase("CheckAttack")) {
         IsoZombie var4 = (IsoZombie)Type.tryCastTo(var1, IsoZombie.class);
         if (var4 != null && var4.target instanceof IsoGameCharacter) {
            ((IsoGameCharacter)var4.target).attackFromWindowsLunge(var4);
         }
      }

      if (var2.m_EventName.equalsIgnoreCase("ActiveAnimFinishing")) {
      }

      if (var2.m_EventName.equalsIgnoreCase("VaultSprintFallLanded")) {
         var1.dropHandItems();
         var1.fallenOnKnees();
      }

      if (var2.m_EventName.equalsIgnoreCase("FallenOnKnees")) {
         var1.fallenOnKnees();
      }

      if (var2.m_EventName.equalsIgnoreCase("OnFloor")) {
         var3.put(PARAM_ZOMBIE_ON_FLOOR, Boolean.parseBoolean(var2.m_ParameterValue));
         if (Boolean.parseBoolean(var2.m_ParameterValue)) {
            this.setLungeXVars((IsoZombie)var1);
            IsoObject var6 = this.getFence(var1);
            if (var6 != null && var6.getSquare() != null && ((IsoZombie)var1).target != null) {
               var6.Damage = (short)(var6.Damage - Rand.Next(15, 30));
               if (var6.Damage <= 0) {
                  IsoDirections var5 = (IsoDirections)Type.tryCastTo(var3.get(PARAM_DIR), IsoDirections.class);
                  var6.destroyFence(var5);
               }
            }

            var1.setVariable("ClimbFenceFlopped", true);
         }
      }

      if (var2.m_EventName.equalsIgnoreCase("SetCollidable")) {
         var3.put(PARAM_COLLIDABLE, Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("VaultOverStarted")) {
         float var7 = 0.0F;
         if (var1.getVariableBoolean("VaultOverSprint")) {
            var7 = 10.0F;
         }

         if (var1.getMoodles() != null) {
            var7 += (float)(var1.getMoodles().getMoodleLevel(MoodleType.Endurance) * 10);
            var7 += (float)(var1.getMoodles().getMoodleLevel(MoodleType.HeavyLoad) * 13);
            var7 += (float)(var1.getMoodles().getMoodleLevel(MoodleType.Pain) * 5);
         }

         BodyPart var8 = var1.getBodyDamage().getBodyPart(BodyPartType.Torso_Lower);
         if (var8.getAdditionalPain(true) > 20.0F) {
            var7 += (var8.getAdditionalPain(true) - 20.0F) / 10.0F;
         }

         if (var1.Traits.Clumsy.isSet()) {
            var7 += 10.0F;
         }

         if (var1.Traits.Graceful.isSet()) {
            var7 -= 10.0F;
         }

         if (var1.Traits.VeryUnderweight.isSet()) {
            var7 += 20.0F;
         }

         if (var1.Traits.Underweight.isSet()) {
            var7 += 10.0F;
         }

         if (var1.Traits.Obese.isSet()) {
            var7 += 20.0F;
         }

         if (var1.Traits.Overweight.isSet()) {
            var7 += 10.0F;
         }

         var7 -= (float)var1.getPerkLevel(PerkFactory.Perks.Fitness);
         if ((float)Rand.Next(100) < var7) {
            var1.setVariable("ClimbFenceOutcome", "fall");
            var1.setVariable("BumpDone", true);
            var1.setFallOnFront(true);
         }
      }

   }

   public void getDeltaModifiers(IsoGameCharacter var1, MoveDeltaModifiers var2) {
      boolean var3 = var1.getPath2() != null;
      boolean var4 = var1 instanceof IsoPlayer;
      if (var3 && var4) {
         var2.turnDelta = Math.max(var2.turnDelta, 10.0F);
      }

   }

   private void slideX(IsoGameCharacter var1, float var2) {
      float var3 = 0.05F * GameTime.getInstance().getMultiplier() / 1.6F;
      var3 = var2 > var1.x ? Math.min(var3, var2 - var1.x) : Math.max(-var3, var2 - var1.x);
      var1.x += var3;
      var1.nx = var1.x;
   }

   private void slideY(IsoGameCharacter var1, float var2) {
      float var3 = 0.05F * GameTime.getInstance().getMultiplier() / 1.6F;
      var3 = var2 > var1.y ? Math.min(var3, var2 - var1.y) : Math.max(-var3, var2 - var1.y);
      var1.y += var3;
      var1.ny = var1.y;
   }

   private IsoObject getFence(IsoGameCharacter var1) {
      HashMap var2 = var1.getStateMachineParams(this);
      int var3 = (Integer)var2.get(PARAM_START_X);
      int var4 = (Integer)var2.get(PARAM_START_Y);
      int var5 = (Integer)var2.get(PARAM_Z);
      IsoGridSquare var6 = IsoWorld.instance.CurrentCell.getGridSquare(var3, var4, var5);
      int var7 = (Integer)var2.get(PARAM_END_X);
      int var8 = (Integer)var2.get(PARAM_END_Y);
      IsoGridSquare var9 = IsoWorld.instance.CurrentCell.getGridSquare(var7, var8, var5);
      return var6 != null && var9 != null ? var6.getHoppableTo(var9) : null;
   }

   public void setParams(IsoGameCharacter var1, IsoDirections var2) {
      HashMap var3 = var1.getStateMachineParams(this);
      int var4 = var1.getSquare().getX();
      int var5 = var1.getSquare().getY();
      int var6 = var1.getSquare().getZ();
      int var9 = var4;
      int var10 = var5;
      switch(var2) {
      case N:
         var10 = var5 - 1;
         break;
      case S:
         var10 = var5 + 1;
         break;
      case W:
         var9 = var4 - 1;
         break;
      case E:
         var9 = var4 + 1;
         break;
      default:
         throw new IllegalArgumentException("invalid direction");
      }

      IsoGridSquare var11 = IsoWorld.instance.CurrentCell.getGridSquare(var9, var10, var6);
      boolean var12 = false;
      boolean var13 = var11 != null && var11.Is(IsoFlagType.solidtrans);
      boolean var14 = var11 != null && var11.TreatAsSolidFloor();
      boolean var15 = var11 != null && var1.canClimbDownSheetRope(var11);
      var3.put(PARAM_START_X, var4);
      var3.put(PARAM_START_Y, var5);
      var3.put(PARAM_Z, var6);
      var3.put(PARAM_END_X, var9);
      var3.put(PARAM_END_Y, var10);
      var3.put(PARAM_DIR, var2);
      var3.put(PARAM_ZOMBIE_ON_FLOOR, Boolean.FALSE);
      var3.put(PARAM_PREV_STATE, var1.getCurrentState());
      var3.put(PARAM_SCRATCH, var12 ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_COUNTER, var13 ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_SOLID_FLOOR, var14 ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_SHEET_ROPE, var15 ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_RUN, var1.isRunning() ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_SPRINT, var1.isSprinting() ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_COLLIDABLE, Boolean.FALSE);
   }
}
