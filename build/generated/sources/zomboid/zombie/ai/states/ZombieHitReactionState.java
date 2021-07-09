package zombie.ai.states;

import java.util.HashMap;
import zombie.GameTime;
import zombie.Lua.LuaEventManager;
import zombie.ai.State;
import zombie.characterTextures.BloodBodyPartType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.core.Rand;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.inventory.types.HandWeapon;
import zombie.iso.IsoDirections;
import zombie.iso.IsoMovingObject;
import zombie.iso.objects.IsoZombieGiblets;

public final class ZombieHitReactionState extends State {
   private static final ZombieHitReactionState _instance = new ZombieHitReactionState();

   public static ZombieHitReactionState instance() {
      return _instance;
   }

   public void enter(IsoGameCharacter var1) {
      ((IsoZombie)var1).collideWhileHit = true;
      HashMap var2 = var1.getStateMachineParams(this);
      var2.put(1, Boolean.FALSE);
      var2.put(2, 0.0F);
      var1.clearVariable("onknees");
      if (((IsoZombie)var1).isSitAgainstWall()) {
         var1.setHitReaction((String)null);
      }

   }

   public void execute(IsoGameCharacter var1) {
      HashMap var2 = var1.getStateMachineParams(this);
      var1.setOnFloor(((IsoZombie)var1).bKnockedDown);
      var2.put(2, (Float)var2.get(2) + GameTime.getInstance().getMultiplier());
      if (var2.get(1) == Boolean.TRUE) {
         if (!var1.isHitFromBehind()) {
            var1.setDir(IsoDirections.reverse(IsoDirections.fromAngle(var1.getHitDir())));
         } else {
            var1.setDir(IsoDirections.fromAngle(var1.getHitDir()));
         }
      }

   }

   public void exit(IsoGameCharacter var1) {
      IsoZombie var2 = (IsoZombie)var1;
      var2.collideWhileHit = true;
      if (var2.target != null) {
         var2.AllowRepathDelay = 0.0F;
         var2.spotted(var2.target, true);
      }

      var2.bStaggerBack = false;
      var2.setHitReaction("");
      var2.setEatBodyTarget((IsoMovingObject)null, false);
      var2.setSitAgainstWall(false);
      var2.setShootable(true);
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
      HashMap var3 = var1.getStateMachineParams(this);
      IsoZombie var4 = (IsoZombie)var1;
      if (var2.m_EventName.equalsIgnoreCase("DoDeath") && Boolean.parseBoolean(var2.m_ParameterValue) && !var4.isOnDeathDone()) {
         var4.setOnDeathDone(true);
         var4.DoZombieInventory();
         var1.setHealth(0.0F);
         LuaEventManager.triggerEvent("OnZombieDead", var1);
         var1.setDoDeathSound(false);
         var1.DoDeath((HandWeapon)null, (IsoGameCharacter)null, false);
      }

      if (var2.m_EventName.equalsIgnoreCase("FallOnFront")) {
         var1.setFallOnFront(Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("ActiveAnimFinishing")) {
      }

      if (var2.m_EventName.equalsIgnoreCase("Collide") && ((IsoZombie)var1).speedType == 1) {
         ((IsoZombie)var1).collideWhileHit = false;
      }

      boolean var5;
      if (var2.m_EventName.equalsIgnoreCase("ZombieTurnToPlayer")) {
         var5 = Boolean.parseBoolean(var2.m_ParameterValue);
         var3.put(1, var5 ? Boolean.TRUE : Boolean.FALSE);
      }

      if (var2.m_EventName.equalsIgnoreCase("CancelKnockDown")) {
         var5 = Boolean.parseBoolean(var2.m_ParameterValue);
         if (var5) {
            ((IsoZombie)var1).bKnockedDown = false;
         }
      }

      if (var2.m_EventName.equalsIgnoreCase("KnockDown")) {
         var1.setOnFloor(true);
         ((IsoZombie)var1).bKnockedDown = true;
      }

      if (var2.m_EventName.equalsIgnoreCase("SplatBlood")) {
         var4.addBlood((BloodBodyPartType)null, true, false, false);
         var4.addBlood((BloodBodyPartType)null, true, false, false);
         var4.addBlood((BloodBodyPartType)null, true, false, false);

         for(int var6 = 0; var6 < 10; ++var6) {
            var4.getCurrentSquare().getChunk().addBloodSplat(var4.x + Rand.Next(-0.5F, 0.5F), var4.y + Rand.Next(-0.5F, 0.5F), var4.z, Rand.Next(8));
            if (Rand.Next(5) == 0) {
               new IsoZombieGiblets(IsoZombieGiblets.GibletType.B, var4.getCell(), var4.getX(), var4.getY(), var4.getZ() + 0.3F, Rand.Next(-0.2F, 0.2F) * 1.5F, Rand.Next(-0.2F, 0.2F) * 1.5F);
            } else {
               new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, var4.getCell(), var4.getX(), var4.getY(), var4.getZ() + 0.3F, Rand.Next(-0.2F, 0.2F) * 1.5F, Rand.Next(-0.2F, 0.2F) * 1.5F);
            }
         }
      }

   }
}
