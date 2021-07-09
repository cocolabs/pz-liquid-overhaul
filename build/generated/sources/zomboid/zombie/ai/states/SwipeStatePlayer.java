package zombie.ai.states;

import fmod.javafmod;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.joml.Vector4f;
import org.lwjgl.input.Keyboard;
import se.krka.kahlua.vm.KahluaTable;
import zombie.GameTime;
import zombie.SandboxOptions;
import zombie.Lua.LuaEventManager;
import zombie.Lua.LuaHookManager;
import zombie.ai.State;
import zombie.characterTextures.BloodBodyPartType;
import zombie.characters.Faction;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoLivingCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.IsoZombie;
import zombie.characters.Stats;
import zombie.characters.BodyDamage.BodyPart;
import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.Moodles.MoodleType;
import zombie.characters.skills.PerkFactory;
import zombie.core.Core;
import zombie.core.Rand;
import zombie.core.math.PZMath;
import zombie.core.network.ByteBufferWriter;
import zombie.core.skinnedmodel.advancedanimation.AnimEvent;
import zombie.core.skinnedmodel.animation.AnimationPlayer;
import zombie.core.skinnedmodel.model.Model;
import zombie.debug.DebugLog;
import zombie.debug.DebugType;
import zombie.inventory.InventoryItem;
import zombie.inventory.InventoryItemFactory;
import zombie.inventory.types.Clothing;
import zombie.inventory.types.HandWeapon;
import zombie.inventory.types.WeaponType;
import zombie.iso.IsoCell;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMovingObject;
import zombie.iso.IsoObject;
import zombie.iso.IsoUtils;
import zombie.iso.IsoWorld;
import zombie.iso.LosUtil;
import zombie.iso.Vector2;
import zombie.iso.Vector3;
import zombie.iso.areas.NonPvpZone;
import zombie.iso.objects.IsoDoor;
import zombie.iso.objects.IsoThumpable;
import zombie.iso.objects.IsoTree;
import zombie.iso.objects.IsoWindow;
import zombie.iso.objects.IsoZombieGiblets;
import zombie.iso.objects.interfaces.Thumpable;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.PacketTypes;
import zombie.network.ServerOptions;
import zombie.popman.ObjectPool;
import zombie.ui.MoodlesUI;
import zombie.ui.UIManager;
import zombie.util.StringUtils;
import zombie.util.Type;
import zombie.vehicles.BaseVehicle;
import zombie.vehicles.PolygonalMap2;
import zombie.vehicles.VehiclePart;
import zombie.vehicles.VehicleWindow;

public final class SwipeStatePlayer extends State {
   private static final SwipeStatePlayer _instance = new SwipeStatePlayer();
   static final Integer PARAM_LOWER_CONDITION = 0;
   static final Integer PARAM_ATTACKED = 1;
   public static final ArrayList HitList = new ArrayList();
   private static final ArrayList HitList2 = new ArrayList();
   private static final Vector2 tempVector2_1 = new Vector2();
   private static final Vector2 tempVector2_2 = new Vector2();
   private final ArrayList dotList = new ArrayList();
   private boolean bHitOnlyTree;
   public final ObjectPool hitInfoPool = new ObjectPool(SwipeStatePlayer.HitInfo::new);
   private static final SwipeStatePlayer.CustomComparator Comparator = new SwipeStatePlayer.CustomComparator();
   static final Vector3 tempVector3_1 = new Vector3();
   static final Vector3 tempVector3_2 = new Vector3();
   static final Vector3 tempVectorBonePos = new Vector3();
   static final ArrayList movingStatic = new ArrayList();
   public final SwipeStatePlayer.AttackVars attackVars = new SwipeStatePlayer.AttackVars();
   private final Vector4f tempVector4f = new Vector4f();
   private final SwipeStatePlayer.WindowVisitor windowVisitor = new SwipeStatePlayer.WindowVisitor();

   public static SwipeStatePlayer instance() {
      return _instance;
   }

   public static void WeaponLowerCondition(HandWeapon var0, IsoGameCharacter var1) {
      if (var0.getUses() > 1) {
         var0.Use();
         InventoryItem var2 = InventoryItemFactory.CreateItem(var0.getFullType());
         var2.setCondition(var0.getCondition() - 1);
         var0.getContainer().AddItem(var2);
         var1.setPrimaryHandItem(var2);
      } else {
         var0.setCondition(var0.getCondition() - 1);
      }

   }

   private static HandWeapon GetWeapon(IsoGameCharacter var0) {
      HandWeapon var1 = var0.getUseHandWeapon();
      if (((IsoLivingCharacter)var0).bDoShove || var0.isForceShove()) {
         var1 = ((IsoLivingCharacter)var0).bareHands;
      }

      return var1;
   }

   private void doAttack(IsoPlayer var1, float var2, boolean var3, String var4, SwipeStatePlayer.AttackVars var5) {
      var1.setForceShove(var3);
      var1.setClickSound(var4);
      if (var3) {
         var2 *= 2.0F;
      }

      if (var2 > 90.0F) {
         var2 = 90.0F;
      }

      var2 /= 25.0F;
      var1.useChargeDelta = var2;
      Object var6 = var1.getPrimaryHandItem();
      if (var6 == null || !(var6 instanceof HandWeapon) || var3 || var5.bDoShove) {
         var6 = var1.bareHands;
      }

      if (var6 instanceof HandWeapon) {
         var1.setUseHandWeapon((HandWeapon)var6);
         if (var1.PlayerIndex == 0 && var1.JoypadBind == -1 && UIManager.getPicked() != null) {
            if (UIManager.getPicked().tile instanceof IsoMovingObject) {
               var1.setAttackTargetSquare(((IsoMovingObject)UIManager.getPicked().tile).getCurrentSquare());
            } else {
               var1.setAttackTargetSquare(UIManager.getPicked().square);
            }
         }

         var1.setRecoilDelay((float)var5.recoilDelay);
         if (var3) {
            var1.setRecoilDelay(10.0F);
         }
      }

   }

   public void enter(IsoGameCharacter var1) {
      UIManager.speedControls.SetCurrentGameSpeed(1);
      HashMap var2 = var1.getStateMachineParams(this);
      var2.put(PARAM_LOWER_CONDITION, Boolean.FALSE);
      var2.put(PARAM_ATTACKED, Boolean.FALSE);
      var1.updateRecoilVar();
      if ("Auto".equals(var1.getVariableString("FireMode"))) {
         var1.setVariable("autoShootSpeed", 4.0F * GameTime.getAnimSpeedFix());
         var1.setVariable("autoShootVarY", 0.0F);
         if (System.currentTimeMillis() - var1.lastAutomaticShoot < 600L) {
            ++var1.shootInARow;
            float var3 = Math.max(0.0F, 1.0F - (float)var1.shootInARow / 20.0F);
            var1.setVariable("autoShootVarX", var3);
            var1.setVariable("autoShootSpeed", (4.0F - (float)var1.shootInARow / 10.0F) * GameTime.getAnimSpeedFix());
         } else {
            var1.setVariable("autoShootVarX", 1.0F);
            var1.shootInARow = 0;
         }

         var1.lastAutomaticShoot = System.currentTimeMillis();
      }

      IsoPlayer var6 = (IsoPlayer)var1;
      var1.setVariable("ShotDone", false);
      var1.setVariable("ShoveAnim", false);
      this.CalcAttackVars((IsoLivingCharacter)var1, this.attackVars);
      this.doAttack(var6, 2.0F, var1.isForceShove(), var1.getClickSound(), this.attackVars);
      HandWeapon var4 = var1.getUseHandWeapon();
      var1.setVariable("AimFloorAnim", this.attackVars.bAimAtFloor);
      LuaEventManager.triggerEvent("OnWeaponSwing", var1, var4);
      if (LuaHookManager.TriggerHook("WeaponSwing", var1, var4)) {
         var1.getStateMachine().revertToPreviousState(this);
      }

      var1.StopAllActionQueue();
      if (((IsoPlayer)var1).isLocalPlayer()) {
         IsoWorld.instance.CurrentCell.setDrag((KahluaTable)null, ((IsoPlayer)var1).PlayerIndex);
      }

      var4 = this.attackVars.weapon;
      var6.setAimAtFloor(this.attackVars.bAimAtFloor);
      boolean var5 = var6.bDoShove;
      var6.bDoShove = this.attackVars.bDoShove;
      var6.useChargeDelta = this.attackVars.useChargeDelta;
      var6.targetOnGround = this.attackVars.targetOnGround;
      if (!var6.bDoShove && !var5 && var6.getClickSound() == null && var4.getPhysicsObject() == null && !var4.isRanged()) {
      }

      if (GameClient.bClient && var1 == IsoPlayer.getInstance()) {
         GameClient.instance.sendPlayer((IsoPlayer)var1);
      }

      this.DoHitSound(var1, var4);
      if (!var6.bDoShove && !var5 && !var4.isRanged()) {
         var1.playSound(var4.getSwingSound());
      }

   }

   public void execute(IsoGameCharacter var1) {
      var1.StopAllActionQueue();
   }

   private int DoSwingCollisionBoneCheck(IsoGameCharacter var1, HandWeapon var2, IsoGameCharacter var3, int var4, float var5) {
      movingStatic.clear();
      float var8 = var2.WeaponLength;
      var8 += 0.5F;
      if (var1.isAimAtFloor() && ((IsoLivingCharacter)var1).bDoShove) {
         var8 = 0.3F;
      }

      Model.BoneToWorldCoords(var3, var4, tempVectorBonePos);

      for(int var9 = 1; var9 <= 10; ++var9) {
         float var10 = (float)var9 / 10.0F;
         tempVector3_1.x = var1.x;
         tempVector3_1.y = var1.y;
         tempVector3_1.z = var1.z;
         Vector3 var10000 = tempVector3_1;
         var10000.x += var1.getForwardDirection().x * var8 * var10;
         var10000 = tempVector3_1;
         var10000.y += var1.getForwardDirection().y * var8 * var10;
         tempVector3_1.x = tempVectorBonePos.x - tempVector3_1.x;
         tempVector3_1.y = tempVectorBonePos.y - tempVector3_1.y;
         tempVector3_1.z = 0.0F;
         boolean var11 = tempVector3_1.getLength() < var5;
         if (var11) {
            return var4;
         }
      }

      return -1;
   }

   public void animEvent(IsoGameCharacter var1, AnimEvent var2) {
      HashMap var3 = var1.getStateMachineParams(this);
      if (var2.m_EventName.equalsIgnoreCase("ActiveAnimFinishing") || var2.m_EventName.equalsIgnoreCase("NonLoopedAnimFadeOut")) {
         boolean var4 = var3.get(PARAM_LOWER_CONDITION) == Boolean.TRUE;
         if (var4 && !var1.isRangedWeaponEmpty()) {
            var3.put(PARAM_LOWER_CONDITION, Boolean.FALSE);
            HandWeapon var5 = GetWeapon(var1);
            int var6 = var5.getConditionLowerChance();
            if (var1 instanceof IsoPlayer && "charge".equals(((IsoPlayer)var1).getAttackType())) {
               var6 = (int)((double)var6 / 1.5D);
            }

            if (Rand.Next(var6 + var1.getMaintenanceMod() * 2) == 0) {
               WeaponLowerCondition(var5, var1);
            } else if (Rand.NextBool(2) && !var5.isRanged() && !var5.getName().contains("Bare Hands")) {
               if (var5.isTwoHandWeapon() && (var1.getPrimaryHandItem() != var5 || var1.getSecondaryHandItem() != var5) && Rand.NextBool(3)) {
                  return;
               }

               var1.getXp().AddXP(PerkFactory.Perks.Maintenance, 1.0F);
            }
         }
      }

      if (var2.m_EventName.equalsIgnoreCase("AttackAnim")) {
         var1.setVariable("AttackAnim", Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("BlockTurn")) {
         var1.setIgnoreMovement(Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("ShoveAnim")) {
         var1.setVariable("ShoveAnim", Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("StompAnim")) {
         var1.setVariable("StompAnim", Boolean.parseBoolean(var2.m_ParameterValue));
      }

      HandWeapon var7 = GetWeapon(var1);
      if (var2.m_EventName.equalsIgnoreCase("AttackCollisionCheck") && var3.get(PARAM_ATTACKED) == Boolean.FALSE) {
         this.ConnectSwing(var1, var7);
      }

      if (var2.m_EventName.equalsIgnoreCase("BlockMovement") && SandboxOptions.instance.AttackBlockMovements.getValue()) {
         var1.setVariable("SlowingMovement", Boolean.parseBoolean(var2.m_ParameterValue));
      }

      if (var2.m_EventName.equalsIgnoreCase("WeaponEmptyCheck") && var1.getClickSound() != null) {
         var1.playSound(var1.getClickSound());
         var1.setRecoilDelay(10.0F);
      }

      if (var2.m_EventName.equalsIgnoreCase("ShotDone") && var7 != null && var7.isRackAfterShoot()) {
         var1.setVariable("ShotDone", true);
      }

      if (var2.m_EventName.equalsIgnoreCase("SetVariable") && var2.m_ParameterValue.startsWith("ShotDone=")) {
         var1.setVariable("ShotDone", var1.getVariableBoolean("ShotDone") && var7 != null && var7.isRackAfterShoot());
      }

      if (var2.m_EventName.equalsIgnoreCase("playRackSound")) {
         var1.playSound(var7.getRackSound());
      }

      if (var2.m_EventName.equalsIgnoreCase("playClickSound")) {
         var1.playSound(var7.getClickSound());
      }

      if (var2.m_EventName.equalsIgnoreCase("SetMeleeDelay")) {
         var1.setMeleeDelay(PZMath.tryParseFloat(var2.m_ParameterValue, 0.0F));
      }

      if (var2.m_EventName.equalsIgnoreCase("SitGroundStarted")) {
         var1.setVariable("SitGroundAnim", "Idle");
      }

   }

   public void exit(IsoGameCharacter var1) {
      HashMap var2 = var1.getStateMachineParams(this);
      var1.setSprinting(false);
      ((IsoPlayer)var1).setForceSprint(false);
      var1.setIgnoreMovement(false);
      var1.setVariable("ShoveAnim", false);
      var1.setVariable("StompAnim", false);
      var1.setVariable("AttackAnim", false);
      var1.setVariable("AimFloorAnim", false);
      ((IsoPlayer)var1).setBlockMovement(false);
      if (var1.isAimAtFloor() && ((IsoLivingCharacter)var1).bDoShove) {
         Clothing var3 = (Clothing)var1.getWornItem("Shoes");
         byte var4 = 10;
         int var6;
         if (var3 == null) {
            var6 = 3;
         } else {
            var6 = var4 + var3.getConditionLowerChance() / 2;
            if (Rand.Next(var3.getConditionLowerChance()) == 0) {
               var3.setCondition(var3.getCondition() - 1);
            }
         }

         if (Rand.Next(var6) == 0) {
            if (var3 == null) {
               var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).AddDamage((float)Rand.Next(5, 10));
               var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).setAdditionalPain(var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).getAdditionalPain() + (float)Rand.Next(5, 10));
            } else {
               var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).AddDamage((float)Rand.Next(1, 5));
               var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).setAdditionalPain(var1.getBodyDamage().getBodyPart(BodyPartType.Foot_R).getAdditionalPain() + (float)Rand.Next(1, 5));
            }
         }
      }

      HandWeapon var5 = GetWeapon(var1);
      var1.clearVariable("ZombieHitReaction");
      ((IsoPlayer)var1).attackStarted = false;
      ((IsoPlayer)var1).setAttackType((String)null);
      ((IsoLivingCharacter)var1).bDoShove = false;
      var1.clearVariable("RackWeapon");
      var1.clearVariable("bShoveAiming");
      boolean var7 = var2.get(PARAM_ATTACKED) == Boolean.TRUE;
      if (var5 != null && (var5.getCondition() <= 0 || var7 && var5.isUseSelf())) {
         var1.removeFromHands(var5);
         var1.getInventory().setDrawDirty(true);
      }

      ((IsoPlayer)var1).NetRemoteState = 0;
      if (var1.isRangedWeaponEmpty()) {
         var1.setRecoilDelay(10.0F);
      }

      var1.setRangedWeaponEmpty(false);
      var1.setForceShove(false);
      var1.setClickSound((String)null);
      if (var7) {
         LuaEventManager.triggerEvent("OnPlayerAttackFinished", var1, var5);
      }

   }

   public void CalcAttackVars(IsoLivingCharacter var1, SwipeStatePlayer.AttackVars var2) {
      HandWeapon var3 = (HandWeapon)Type.tryCastTo(var1.getPrimaryHandItem(), HandWeapon.class);
      if (var3 != null && var3.getOtherHandRequire() != null) {
         InventoryItem var4 = var1.getSecondaryHandItem();
         if (var4 == null || !var4.getType().equals(var3.getOtherHandRequire())) {
            var3 = null;
         }
      }

      boolean var12 = var1.getVariableBoolean("AttackAnim") || var1.getVariableBoolean("ShoveAnim") || var1.getVariableBoolean("StompAnim");
      var2.weapon = var3 == null ? var1.bareHands : var3;
      var2.targetOnGround = null;
      var2.bAimAtFloor = false;
      var2.bCloseKill = false;
      var2.bDoShove = var1.bDoShove;
      if (!var12) {
         var1.setVariable("ShoveAimX", 0.5F);
         var1.setVariable("ShoveAimY", 1.0F);
         if (var2.bDoShove && var1.getVariableBoolean("isMoving")) {
            var1.setVariable("ShoveAim", true);
         } else {
            var1.setVariable("ShoveAim", false);
         }
      }

      var2.useChargeDelta = var1.useChargeDelta;
      var2.recoilDelay = 0;
      boolean var5 = false;
      if (var2.weapon == var1.bareHands || var2.bDoShove || var1.isForceShove()) {
         var2.bDoShove = true;
         var2.bAimAtFloor = false;
         var2.weapon = var1.bareHands;
      }

      this.calcValidTargets(var1, var2.weapon, true, var2.targetsProne, var2.targetsStanding);
      SwipeStatePlayer.HitInfo var7 = var2.targetsStanding.isEmpty() ? null : (SwipeStatePlayer.HitInfo)var2.targetsStanding.get(0);
      SwipeStatePlayer.HitInfo var8 = var2.targetsProne.isEmpty() ? null : (SwipeStatePlayer.HitInfo)var2.targetsProne.get(0);
      if (this.isProneTargetBetter(var1, var7, var8)) {
         var7 = null;
      }

      if (!var12) {
         var1.setAimAtFloor(false);
      }

      float var9 = Float.MAX_VALUE;
      if (var7 != null) {
         if (!var12) {
            var1.setAimAtFloor(false);
         }

         var2.bAimAtFloor = false;
         var2.targetOnGround = null;
         var9 = var7.distSq;
      } else if (var8 != null && (Core.OptionAutoProneAtk || var1.bDoShove)) {
         if (!var12) {
            var1.setAimAtFloor(true);
         }

         var2.bAimAtFloor = true;
         var2.targetOnGround = (IsoGameCharacter)var8.object;
      }

      if (!(var9 >= var2.weapon.getMinRange() * var2.weapon.getMinRange()) && (var7 == null || !this.isWindowBetween(var1, var7.object))) {
         if (var2.weapon.CloseKillMove == null) {
            var2.bDoShove = true;
            IsoPlayer var10 = (IsoPlayer)Type.tryCastTo(var1, IsoPlayer.class);
            if (var10 != null && !var10.isAuthorizeShoveStomp()) {
               var2.bDoShove = false;
            }

            var2.bAimAtFloor = false;
            if (var1.bareHands.getSwingAnim() != null) {
               var2.useChargeDelta = 3.0F;
            }
         } else if (var2.weapon.getSwingAnim() != null) {
            var2.bCloseKill = true;
         }
      }

      if (Keyboard.isKeyDown(Core.getInstance().getKey("ManualFloorAtk")) && !var1.isSprinting()) {
         var2.bAimAtFloor = true;
         var2.bDoShove = false;
         var1.bDoShove = false;
      }

      if (var2.weapon.isRanged()) {
         int var13 = var2.weapon.getRecoilDelay();
         Float var11 = (float)var13 * (1.0F - (float)var1.getPerkLevel(PerkFactory.Perks.Aiming) / 30.0F);
         var2.recoilDelay = var11.intValue();
         (float)var13 * (1.0F - (float)var1.getPerkLevel(PerkFactory.Perks.Aiming) / 15.0F);
         var11 = 1.0F;
         var1.setVariable("singleShootSpeed", (0.8F + (float)var1.getPerkLevel(PerkFactory.Perks.Aiming) / 10.0F) * GameTime.getAnimSpeedFix());
      }

   }

   public void calcValidTargets(IsoLivingCharacter var1, HandWeapon var2, boolean var3, ArrayList var4, ArrayList var5) {
      this.hitInfoPool.release((List)var4);
      this.hitInfoPool.release((List)var5);
      var4.clear();
      var5.clear();
      float var6 = Core.getInstance().getIgnoreProneZombieRange();
      float var7 = var2.getMaxRange() * var2.getRangeMod(var1);
      float var8 = Math.max(var6, var7 + (var3 ? 1.0F : 0.0F));
      ArrayList var9 = IsoWorld.instance.CurrentCell.getObjectList();

      for(int var10 = 0; var10 < var9.size(); ++var10) {
         IsoMovingObject var11 = (IsoMovingObject)var9.get(var10);
         SwipeStatePlayer.HitInfo var12 = this.calcValidTarget(var1, var2, var11, var8);
         if (var12 != null) {
            if (isStanding(var11)) {
               var5.add(var12);
            } else {
               var4.add(var12);
            }
         }
      }

      if (!var4.isEmpty() && this.shouldIgnoreProneZombies(var1, var5, var6)) {
         this.hitInfoPool.release((List)var4);
         var4.clear();
      }

      float var13 = var2.getMinAngle();
      float var14 = var2.getMaxAngle();
      if (var2.isRanged()) {
         var13 -= var2.getAimingPerkMinAngleModifier() * ((float)var1.getPerkLevel(PerkFactory.Perks.Aiming) / 2.0F);
      }

      this.removeUnhittableTargets(var1, var2, var13, var14, var3, var5);
      var13 = var2.getMinAngle();
      var13 = (float)((double)var13 / 1.5D);
      this.removeUnhittableTargets(var1, var2, var13, var14, var3, var4);
      var5.sort(Comparator);
      var4.sort(Comparator);
   }

   private boolean shouldIgnoreProneZombies(IsoGameCharacter var1, ArrayList var2, float var3) {
      if (var3 <= 0.0F) {
         return false;
      } else {
         boolean var4 = var1.isInvisible() || var1 instanceof IsoPlayer && ((IsoPlayer)var1).isGhostMode();

         for(int var5 = 0; var5 < var2.size(); ++var5) {
            SwipeStatePlayer.HitInfo var6 = (SwipeStatePlayer.HitInfo)var2.get(var5);
            IsoZombie var7 = (IsoZombie)Type.tryCastTo(var6.object, IsoZombie.class);
            if ((var7 == null || var7.target != null || var4) && !(var6.distSq > var3 * var3)) {
               boolean var8 = PolygonalMap2.instance.lineClearCollide(var1.x, var1.y, var6.object.x, var6.object.y, (int)var1.z, var1, false, true);
               if (!var8) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isUnhittableTarget(IsoGameCharacter var1, HandWeapon var2, float var3, float var4, SwipeStatePlayer.HitInfo var5, boolean var6) {
      if (!(var5.dot < var3) && !(var5.dot > var4)) {
         Vector3 var7 = tempVectorBonePos.set(var5.x, var5.y, var5.z);
         return !var1.IsAttackRange(var2, var5.object, var7, var6);
      } else {
         return true;
      }
   }

   private void removeUnhittableTargets(IsoGameCharacter var1, HandWeapon var2, float var3, float var4, boolean var5, ArrayList var6) {
      for(int var7 = var6.size() - 1; var7 >= 0; --var7) {
         SwipeStatePlayer.HitInfo var8 = (SwipeStatePlayer.HitInfo)var6.get(var7);
         if (this.isUnhittableTarget(var1, var2, var3, var4, var8, var5)) {
            this.hitInfoPool.release((Object)var8);
            var6.remove(var7);
         }
      }

   }

   private boolean getNearestTargetPosAndDot(IsoGameCharacter var1, HandWeapon var2, IsoMovingObject var3, boolean var4, Vector4f var5) {
      this.getNearestTargetPosAndDot(var1, var3, var5);
      float var6 = var5.w;
      float var7 = var2.getMinAngle();
      float var8 = var2.getMaxAngle();
      IsoGameCharacter var9 = (IsoGameCharacter)Type.tryCastTo(var3, IsoGameCharacter.class);
      if (var9 != null) {
         if (isStanding(var3)) {
            if (var2.isRanged()) {
               var7 -= var2.getAimingPerkMinAngleModifier() * ((float)var1.getPerkLevel(PerkFactory.Perks.Aiming) / 2.0F);
            }
         } else {
            var7 /= 1.5F;
         }
      }

      if (!(var6 < var7) && !(var6 > var8)) {
         Vector3 var10 = tempVectorBonePos.set(var5.x, var5.y, var5.z);
         return var1.IsAttackRange(var2, var3, var10, var4);
      } else {
         return false;
      }
   }

   private void getNearestTargetPosAndDot(IsoGameCharacter var1, Vector3 var2, Vector2 var3, Vector4f var4) {
      float var5 = var1.getDotWithForwardDirection(var2);
      var5 = PZMath.clamp(var5, -1.0F, 1.0F);
      var4.w = Math.max(var5, var4.w);
      float var6 = IsoUtils.DistanceToSquared(var1.x, var1.y, (float)((int)var1.z * 3), var2.x, var2.y, (float)((int)Math.max(var2.z, 0.0F) * 3));
      if (var6 < var3.x) {
         var3.x = var6;
         var4.set(var2.x, var2.y, var2.z, var4.w);
      }

   }

   private void getNearestTargetPosAndDot(IsoGameCharacter var1, IsoMovingObject var2, String var3, Vector2 var4, Vector4f var5) {
      Vector3 var6 = getBoneWorldPos(var2, var3, tempVectorBonePos);
      this.getNearestTargetPosAndDot(var1, var6, var4, var5);
   }

   private void getNearestTargetPosAndDot(IsoGameCharacter var1, IsoMovingObject var2, Vector4f var3) {
      Vector2 var4 = tempVector2_1.set(Float.MAX_VALUE, Float.NaN);
      var3.w = Float.NEGATIVE_INFINITY;
      IsoGameCharacter var5 = (IsoGameCharacter)Type.tryCastTo(var2, IsoGameCharacter.class);
      if (var5 == null) {
         this.getNearestTargetPosAndDot(var1, var2, (String)null, var4, var3);
      } else {
         getBoneWorldPos(var2, "Bip01_Head", tempVector3_1);
         getBoneWorldPos(var2, "Bip01_HeadNub", tempVector3_2);
         tempVector3_1.addToThis(tempVector3_2);
         tempVector3_1.div(2.0F);
         Vector3 var6 = tempVector3_1;
         if (isStanding(var2)) {
            this.getNearestTargetPosAndDot(var1, var6, var4, var3);
            this.getNearestTargetPosAndDot(var1, var2, "Bip01_Pelvis", var4, var3);
            Vector3 var7 = tempVectorBonePos.set(var2.getX(), var2.getY(), var2.getZ());
            this.getNearestTargetPosAndDot(var1, var7, var4, var3);
         } else {
            this.getNearestTargetPosAndDot(var1, var6, var4, var3);
            this.getNearestTargetPosAndDot(var1, var2, "Bip01_Pelvis", var4, var3);
            this.getNearestTargetPosAndDot(var1, var2, "Bip01_DressFrontNub", var4, var3);
         }

      }
   }

   private SwipeStatePlayer.HitInfo calcValidTarget(IsoLivingCharacter var1, HandWeapon var2, IsoMovingObject var3, float var4) {
      if (var3 == var1) {
         return null;
      } else {
         IsoGameCharacter var5 = (IsoGameCharacter)Type.tryCastTo(var3, IsoGameCharacter.class);
         if (var5 == null) {
            return null;
         } else if (var5.isGodMod()) {
            return null;
         } else if (!this.checkPVP(var1, var3)) {
            return null;
         } else {
            float var6 = Math.abs(var5.getZ() - var1.getZ());
            if (!var2.isRanged() && var6 >= 0.5F) {
               return null;
            } else if (var6 > 3.3F) {
               return null;
            } else if (!var5.isShootable()) {
               return null;
            } else if (var5.isCurrentState(FakeDeadZombieState.instance())) {
               return null;
            } else if (var5.isDead()) {
               return null;
            } else if (var5.getHitReaction() != null && var5.getHitReaction().contains("Death")) {
               return null;
            } else {
               Vector4f var7 = this.tempVector4f;
               this.getNearestTargetPosAndDot(var1, var5, var7);
               float var8 = var7.w;
               float var9 = IsoUtils.DistanceToSquared(var1.x, var1.y, (float)((int)var1.z * 3), var7.x, var7.y, (float)((int)var7.z * 3));
               if (var8 < 0.0F) {
                  return null;
               } else if (var9 > var4 * var4) {
                  return null;
               } else {
                  LosUtil.TestResults var10 = LosUtil.lineClear(var1.getCell(), (int)var1.getX(), (int)var1.getY(), (int)var1.getZ(), (int)var5.getX(), (int)var5.getY(), (int)var5.getZ(), false);
                  return var10 != LosUtil.TestResults.Blocked && var10 != LosUtil.TestResults.ClearThroughClosedDoor ? ((SwipeStatePlayer.HitInfo)this.hitInfoPool.alloc()).init(var5, var8, var9, var7.x, var7.y, var7.z) : null;
               }
            }
         }
      }
   }

   public static boolean isProne(IsoMovingObject var0) {
      IsoZombie var1 = (IsoZombie)Type.tryCastTo(var0, IsoZombie.class);
      if (var1 == null) {
         return var0.isOnFloor();
      } else if (var1.isOnFloor()) {
         return true;
      } else if (var1.isCurrentState(ZombieEatBodyState.instance())) {
         return true;
      } else if (var1.isDead()) {
         return true;
      } else if (var1.isSitAgainstWall()) {
         return true;
      } else {
         return var1.isCrawling();
      }
   }

   public static boolean isStanding(IsoMovingObject var0) {
      return !isProne(var0);
   }

   public boolean isProneTargetBetter(IsoGameCharacter var1, SwipeStatePlayer.HitInfo var2, SwipeStatePlayer.HitInfo var3) {
      if (var2 != null && var2.object != null) {
         if (var3 != null && var3.object != null) {
            if (var2.distSq <= var3.distSq) {
               return false;
            } else {
               boolean var4 = PolygonalMap2.instance.lineClearCollide(var1.x, var1.y, var2.object.x, var2.object.y, (int)var1.z, (IsoMovingObject)null, false, true);
               if (!var4) {
                  return false;
               } else {
                  boolean var5 = PolygonalMap2.instance.lineClearCollide(var1.x, var1.y, var3.object.x, var3.object.y, (int)var1.z, (IsoMovingObject)null, false, true);
                  return !var5;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean checkPVP(IsoGameCharacter var1, IsoMovingObject var2) {
      IsoPlayer var3 = (IsoPlayer)Type.tryCastTo(var1, IsoPlayer.class);
      IsoPlayer var4 = (IsoPlayer)Type.tryCastTo(var2, IsoPlayer.class);
      if (GameClient.bClient && var4 != null) {
         if (!var4.accessLevel.equals("") || !ServerOptions.instance.PVP.getValue() || ServerOptions.instance.SafetySystem.getValue() && var1.isSafety() && ((IsoGameCharacter)var2).isSafety()) {
            return false;
         }

         if (NonPvpZone.getNonPvpZone((int)var2.getX(), (int)var2.getY()) != null) {
            return false;
         }

         if (var3 != null && NonPvpZone.getNonPvpZone((int)var1.getX(), (int)var1.getY()) != null) {
            return false;
         }

         if (var3 != null && !var3.factionPvp) {
            Faction var5 = Faction.getPlayerFaction(var3);
            Faction var6 = Faction.getPlayerFaction(var4);
            if (var6 != null && var5 == var6) {
               return false;
            }
         }
      }

      if (!GameClient.bClient && var4 != null && !IsoPlayer.getCoopPVP()) {
         return false;
      } else {
         return true;
      }
   }

   private void CalcHitListShove(IsoGameCharacter var1, boolean var2, SwipeStatePlayer.AttackVars var3) {
      HandWeapon var4 = var3.weapon;
      ArrayList var5 = IsoWorld.instance.CurrentCell.getObjectList();

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         IsoMovingObject var7 = (IsoMovingObject)var5.get(var6);
         if (var7 != var1 && !(var7 instanceof BaseVehicle)) {
            IsoGameCharacter var8 = (IsoGameCharacter)Type.tryCastTo(var7, IsoGameCharacter.class);
            if (var8 != null && !var8.isGodMod() && !var8.isDead()) {
               IsoZombie var9 = (IsoZombie)Type.tryCastTo(var7, IsoZombie.class);
               if ((var9 == null || !var9.isCurrentState(FakeDeadZombieState.instance())) && this.checkPVP(var1, var7)) {
                  boolean var10 = var7 == var3.targetOnGround || var7.isShootable() && isStanding(var7) && !var3.bAimAtFloor || var7.isShootable() && isProne(var7) && var3.bAimAtFloor;
                  if (var10) {
                     Vector4f var11 = this.tempVector4f;
                     if (this.getNearestTargetPosAndDot(var1, var4, var7, var2, var11)) {
                        float var12 = var11.w;
                        float var13 = IsoUtils.DistanceToSquared(var1.x, var1.y, (float)((int)var1.z * 3), var11.x, var11.y, (float)((int)var11.z * 3));
                        LosUtil.TestResults var14 = LosUtil.lineClear(var1.getCell(), (int)var1.getX(), (int)var1.getY(), (int)var1.getZ(), (int)var7.getX(), (int)var7.getY(), (int)var7.getZ(), false);
                        if (var14 != LosUtil.TestResults.Blocked && var14 != LosUtil.TestResults.ClearThroughClosedDoor && (var7.getCurrentSquare() == null || var1.getCurrentSquare() == null || var7.getCurrentSquare() == var1.getCurrentSquare() || !var7.getCurrentSquare().isWindowBlockedTo(var1.getCurrentSquare())) && var7.getSquare().getTransparentWallTo(var1.getSquare()) == null) {
                           SwipeStatePlayer.HitInfo var15 = ((SwipeStatePlayer.HitInfo)this.hitInfoPool.alloc()).init(var7, var12, var13, var11.x, var11.y, var11.z);
                           if (var3.targetOnGround == var7) {
                              HitList.clear();
                              HitList.add(var15);
                              break;
                           }

                           HitList.add(var15);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void CalcHitListWeapon(IsoGameCharacter var1, boolean var2, SwipeStatePlayer.AttackVars var3) {
      HandWeapon var4 = var3.weapon;
      ArrayList var5 = IsoWorld.instance.CurrentCell.getObjectList();

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         IsoMovingObject var7 = (IsoMovingObject)var5.get(var6);
         if (var7 != var1) {
            IsoGameCharacter var8 = (IsoGameCharacter)Type.tryCastTo(var7, IsoGameCharacter.class);
            if ((var8 == null || !var8.isGodMod()) && (var8 == null || !var8.isDead())) {
               IsoZombie var9 = (IsoZombie)Type.tryCastTo(var7, IsoZombie.class);
               if ((var9 == null || !var9.isCurrentState(FakeDeadZombieState.instance())) && this.checkPVP(var1, var7)) {
                  boolean var10 = var7 == var3.targetOnGround || var7.isShootable() && isStanding(var7) && !var3.bAimAtFloor || var7.isShootable() && isProne(var7) && var3.bAimAtFloor;
                  if (var10) {
                     Vector4f var11 = this.tempVector4f;
                     float var13;
                     if (var7 instanceof BaseVehicle) {
                        VehiclePart var12 = ((BaseVehicle)var7).getNearestBodyworkPart(var1);
                        if (var12 == null) {
                           continue;
                        }

                        var13 = var1.getDotWithForwardDirection(var7.x, var7.y);
                        if (var13 < 0.8F) {
                           continue;
                        }

                        var11.set(var7.x, var7.y, var7.z, var13);
                     } else if (var8 == null || !this.getNearestTargetPosAndDot(var1, var4, var7, var2, var11)) {
                        continue;
                     }

                     LosUtil.TestResults var17 = LosUtil.lineClear(var1.getCell(), (int)var1.getX(), (int)var1.getY(), (int)var1.getZ(), (int)var7.getX(), (int)var7.getY(), (int)var7.getZ(), false);
                     if (var17 != LosUtil.TestResults.Blocked && var17 != LosUtil.TestResults.ClearThroughClosedDoor) {
                        var13 = var11.w;
                        float var14 = IsoUtils.DistanceToSquared(var1.x, var1.y, (float)((int)var1.z * 3), var11.x, var11.y, (float)((int)var11.z * 3));
                        if (var7.getSquare().getTransparentWallTo(var1.getSquare()) != null && var1 instanceof IsoPlayer) {
                           if (WeaponType.getWeaponType(var1) == WeaponType.spear) {
                              ((IsoPlayer)var1).setAttackType("spearStab");
                           } else if (WeaponType.getWeaponType(var1) != WeaponType.knife) {
                              continue;
                           }
                        }

                        IsoWindow var15 = this.getWindowBetween(var1, var7);
                        if (var15 == null || !var15.isBarricaded()) {
                           SwipeStatePlayer.HitInfo var16 = ((SwipeStatePlayer.HitInfo)this.hitInfoPool.alloc()).init(var7, var13, var14, var11.x, var11.y, var11.z);
                           var16.window = var15;
                           HitList.add(var16);
                        }
                     }
                  }
               }
            }
         }
      }

      if (HitList.isEmpty()) {
         this.CalcHitListWindow(var1, var4);
      }
   }

   private void CalcHitListWindow(IsoGameCharacter var1, HandWeapon var2) {
      Vector2 var3 = var1.getLookVector(tempVector2_1);
      var3.setLength(var2.getMaxRange() * var2.getRangeMod(var1));
      SwipeStatePlayer.HitInfo var4 = null;
      ArrayList var5 = IsoWorld.instance.CurrentCell.getWindowList();

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         IsoWindow var7 = (IsoWindow)var5.get(var6);
         if ((int)var7.getZ() == (int)var1.z && this.windowVisitor.isHittable(var7)) {
            float var8 = var7.getX();
            float var9 = var7.getY();
            float var10 = var8 + (var7.getNorth() ? 1.0F : 0.0F);
            float var11 = var9 + (var7.getNorth() ? 0.0F : 1.0F);
            if (Line2D.linesIntersect((double)var1.x, (double)var1.y, (double)(var1.x + var3.x), (double)(var1.y + var3.y), (double)var8, (double)var9, (double)var10, (double)var11)) {
               IsoGridSquare var12 = var7.getAddSheetSquare(var1);
               if (var12 != null && !LosUtil.lineClearCollide((int)var1.x, (int)var1.y, (int)var1.z, var12.x, var12.y, var12.z, false)) {
                  float var13 = IsoUtils.DistanceToSquared(var1.x, var1.y, var8 + (var10 - var8) / 2.0F, var9 + (var11 - var9) / 2.0F);
                  if (var4 == null || !(var4.distSq < var13)) {
                     float var14 = 1.0F;
                     if (var4 == null) {
                        var4 = (SwipeStatePlayer.HitInfo)this.hitInfoPool.alloc();
                     }

                     var4.init(var7, var14, var13);
                  }
               }
            }
         }
      }

      if (var4 != null) {
         HitList.add(var4);
      }

   }

   public void CalcHitList(IsoGameCharacter var1, boolean var2, SwipeStatePlayer.AttackVars var3) {
      this.hitInfoPool.release((List)HitList);
      HitList.clear();
      HandWeapon var4 = var3.weapon;
      int var5 = var4.getMaxHitCount();
      if (var3.bDoShove) {
         var5 = WeaponType.getWeaponType(var1) != WeaponType.barehand ? 3 : 1;
      }

      if (!var4.isRanged() && !SandboxOptions.instance.MultiHitZombies.getValue()) {
         var5 = 1;
      }

      if (var4 == ((IsoPlayer)var1).bareHands && !(var1.getPrimaryHandItem() instanceof HandWeapon)) {
         var5 = 1;
      }

      if (var4 == ((IsoPlayer)var1).bareHands && var3.targetOnGround != null) {
         var5 = 1;
      }

      if (0 < var5) {
         if (var3.bDoShove) {
            this.CalcHitListShove(var1, var2, var3);
         } else {
            this.CalcHitListWeapon(var1, var2, var3);
         }

         if (HitList.size() == 1 && ((SwipeStatePlayer.HitInfo)HitList.get(0)).object == null) {
            return;
         }

         this.filterTargetsByZ(var1);
         Collections.sort(HitList, Comparator);
         if (var4.isPiercingBullets()) {
            HitList2.clear();
            double var6 = 0.0D;

            for(int var8 = 0; var8 < HitList.size(); ++var8) {
               SwipeStatePlayer.HitInfo var9 = (SwipeStatePlayer.HitInfo)HitList.get(var8);
               IsoMovingObject var10 = var9.object;
               if (var10 != null) {
                  double var11 = (double)(var1.getX() - var10.getX());
                  double var13 = (double)(-(var1.getY() - var10.getY()));
                  double var15 = Math.atan2(var13, var11);
                  if (var15 < 0.0D) {
                     var15 = Math.abs(var15);
                  } else {
                     var15 = 6.283185307179586D - var15;
                  }

                  if (var8 == 0) {
                     var6 = Math.toDegrees(var15);
                     HitList2.add(var9);
                  } else {
                     double var17 = Math.toDegrees(var15);
                     if (Math.abs(var6 - var17) < 1.0D) {
                        HitList2.add(var9);
                        break;
                     }
                  }
               }
            }

            HitList.removeAll(HitList2);
            this.hitInfoPool.release((List)HitList);
            HitList.clear();
            HitList.addAll(HitList2);
         } else {
            while(HitList.size() > var5) {
               this.hitInfoPool.release(HitList.remove(HitList.size() - 1));
            }
         }
      }

      for(int var19 = 0; var19 < HitList.size(); ++var19) {
         SwipeStatePlayer.HitInfo var7 = (SwipeStatePlayer.HitInfo)HitList.get(var19);
         var7.chance = this.CalcHitChance(var1, var4, var7);
      }

   }

   private void filterTargetsByZ(IsoGameCharacter var1) {
      float var2 = Float.MAX_VALUE;
      SwipeStatePlayer.HitInfo var3 = null;

      int var4;
      SwipeStatePlayer.HitInfo var5;
      float var6;
      for(var4 = 0; var4 < HitList.size(); ++var4) {
         var5 = (SwipeStatePlayer.HitInfo)HitList.get(var4);
         var6 = Math.abs(var5.z - var1.getZ());
         if (var6 < var2) {
            var2 = var6;
            var3 = var5;
         }
      }

      if (var3 != null) {
         for(var4 = HitList.size() - 1; var4 >= 0; --var4) {
            var5 = (SwipeStatePlayer.HitInfo)HitList.get(var4);
            if (var5 != var3) {
               var6 = Math.abs(var5.z - var3.z);
               if (var6 > 0.5F) {
                  this.hitInfoPool.release((Object)var5);
                  HitList.remove(var4);
               }
            }
         }

      }
   }

   public int CalcHitChance(IsoGameCharacter var1, HandWeapon var2, SwipeStatePlayer.HitInfo var3) {
      IsoMovingObject var4 = var3.object;
      if (var4 == null) {
         return 0;
      } else {
         if (System.currentTimeMillis() - var1.lastAutomaticShoot > 600L) {
            var1.shootInARow = 0;
         }

         int var5 = var2.getHitChance();
         var5 = (int)((float)var5 + var2.getAimingPerkHitChanceModifier() * (float)var1.getPerkLevel(PerkFactory.Perks.Aiming));
         if (var5 > 95) {
            var5 = 95;
         }

         var5 -= var1.shootInARow * 2;
         float var6 = PZMath.sqrt(var3.distSq);
         var5 = (int)((double)var5 + (double)(var2.getMaxRange() * var2.getRangeMod(var1) - var6) * 1.3D);
         if (var2.getMinRangeRanged() > 0.0F) {
            if (var6 < var2.getMinRangeRanged()) {
               var5 -= 50;
            }
         } else if ((double)var6 < 1.7D && var2.isRanged()) {
            var5 += 35;
         }

         if (var2.isRanged() && var1.getBeenMovingFor() > (float)(var2.getAimingTime() + var1.getPerkLevel(PerkFactory.Perks.Aiming))) {
            var5 = (int)((float)var5 - (var1.getBeenMovingFor() - (float)(var2.getAimingTime() + var1.getPerkLevel(PerkFactory.Perks.Aiming))));
         }

         if (var2.isRanged() && var1.getVehicle() != null) {
            var5 = (int)((float)var5 - Math.abs(var1.getVehicle().getCurrentSpeedKmHour()) * 2.0F);
         }

         if (var1.Traits.Marksman.isSet()) {
            var5 += 20;
         }

         float var7 = 0.0F;

         for(int var8 = BodyPartType.ToIndex(BodyPartType.Hand_L); var8 <= BodyPartType.ToIndex(BodyPartType.UpperArm_R); ++var8) {
            var7 += ((BodyPart)var1.getBodyDamage().getBodyParts().get(var8)).getPain();
         }

         if (var7 > 0.0F) {
            var5 = (int)((float)var5 - var7 / 10.0F);
         }

         var5 -= var1.getMoodles().getMoodleLevel(MoodleType.Tired) * 5;
         if (var5 <= 10) {
            var5 = 10;
         }

         if (var5 > 100 || !var2.isRanged()) {
            var5 = 100;
         }

         return var5;
      }
   }

   private void DoHitSound(IsoGameCharacter var1, HandWeapon var2) {
      this.attackVars.weapon = var2;
      this.attackVars.targetOnGround = ((IsoLivingCharacter)var1).targetOnGround;
      this.attackVars.bAimAtFloor = var1.isAimAtFloor();
      this.attackVars.bDoShove = ((IsoLivingCharacter)var1).bDoShove;
      this.CalcHitList(var1, false, this.attackVars);
      if (!HitList.isEmpty()) {
         for(int var3 = 0; var3 < HitList.size(); ++var3) {
            SwipeStatePlayer.HitInfo var4 = (SwipeStatePlayer.HitInfo)HitList.get(var3);
            IsoMovingObject var5 = var4.object;
            if (var5 != null) {
               Vector2 var6 = tempVector2_1.set(var1.getX(), var1.getY());
               Vector2 var7 = tempVector2_2.set(var5.getX(), var5.getY());
               var7.x -= var6.x;
               var7.y -= var6.y;
               Vector2 var8 = tempVector2_1.set(var1.getForwardDirection().getX(), var1.getForwardDirection().getY());
               var8.tangent();
               var7.normalize();
               boolean var9 = true;
               float var10 = var8.dot(var7);

               for(int var11 = 0; var11 < this.dotList.size(); ++var11) {
                  float var12 = (Float)this.dotList.get(var11);
                  if ((double)Math.abs(var10 - var12) < 1.0E-4D) {
                     var9 = false;
                  }
               }

               if (var9 && ((SwipeStatePlayer.HitInfo)HitList.get(0)).object instanceof IsoZombie) {
                  IsoZombie var13 = (IsoZombie)((SwipeStatePlayer.HitInfo)HitList.get(0)).object;
                  if (var13.vocalEvent != 0L) {
                     javafmod.FMOD_Studio_SetParameter(var13.vocalEvent, "Hit", 1.0F);
                  }
               }
            }
         }

      }
   }

   public static Vector3 getBoneWorldPos(IsoMovingObject var0, String var1, Vector3 var2) {
      IsoGameCharacter var3 = (IsoGameCharacter)Type.tryCastTo(var0, IsoGameCharacter.class);
      if (var3 != null && var1 != null) {
         AnimationPlayer var4 = var3.getAnimationPlayer();
         if (var4 != null && var4.isReady()) {
            int var5 = var4.getSkinningBoneIndex(var1, -1);
            if (var5 == -1) {
               return var2.set(var0.x, var0.y, var0.z);
            } else {
               Model.BoneToWorldCoords(var3, var5, var2);
               return var2;
            }
         } else {
            return var2.set(var0.x, var0.y, var0.z);
         }
      } else {
         return var2.set(var0.x, var0.y, var0.z);
      }
   }

   public void ConnectSwing(IsoGameCharacter var1, HandWeapon var2) {
      HashMap var3 = var1.getStateMachineParams(this);
      IsoLivingCharacter var4 = (IsoLivingCharacter)var1;
      if (var1.getVariableBoolean("ShoveAnim")) {
         var4.bDoShove = true;
      }

      if (GameServer.bServer) {
         DebugLog.log(DebugType.Network, "Player swing connects.");
      }

      LuaEventManager.triggerEvent("OnWeaponSwingHitPoint", var1, var2);
      if (var2.getPhysicsObject() != null) {
         var1.Throw(var2);
      }

      if (var2.isUseSelf()) {
         var2.Use();
      }

      if (var2.isOtherHandUse() && var1.getSecondaryHandItem() != null) {
         var1.getSecondaryHandItem().Use();
      }

      boolean var5 = false;
      if (var4.bDoShove && !var1.isAimAtFloor()) {
         var5 = true;
      }

      boolean var6 = false;
      this.attackVars.weapon = var2;
      this.attackVars.targetOnGround = var4.targetOnGround;
      this.attackVars.bAimAtFloor = var1.isAimAtFloor();
      this.attackVars.bDoShove = var4.bDoShove;
      if (var1.getVariableBoolean("ShoveAnim")) {
         this.attackVars.bDoShove = true;
      }

      this.CalcHitList(var1, false, this.attackVars);
      int var7 = HitList.size();
      boolean var8 = false;
      if (var7 == 0) {
         var8 = this.CheckObjectHit(var1, var2);
      }

      Stats var10000;
      if (var2.isUseEndurance()) {
         float var9 = 0.0F;
         if (var2.isTwoHandWeapon() && (var1.getPrimaryHandItem() != var2 || var1.getSecondaryHandItem() != var2)) {
            var9 = var2.getWeight() / 1.5F / 10.0F;
         }

         if (var7 <= 0 && !var1.isForceShove()) {
            float var10 = (var2.getWeight() * 0.28F * var2.getFatigueMod(var1) * var1.getFatigueMod() * var2.getEnduranceMod() * 0.3F + var9) * 0.04F;
            float var11 = 1.0F;
            if (var1.Traits.Asthmatic.isSet()) {
               var11 = 1.3F;
            }

            var10000 = var1.getStats();
            var10000.endurance -= var10 * var11;
         }
      }

      var1.setLastHitCount(HitList.size());
      if (!var2.isMultipleHitConditionAffected()) {
         var6 = true;
      }

      int var40 = 1;
      this.dotList.clear();
      if (HitList.isEmpty() && var1.getClickSound() != null && !var4.bDoShove) {
         var1.getEmitter().playSound(var1.getClickSound());
         var1.setRecoilDelay(10.0F);
      }

      int var43;
      for(int var41 = 0; var41 < HitList.size(); ++var41) {
         var43 = 0;
         boolean var12 = false;
         SwipeStatePlayer.HitInfo var13 = (SwipeStatePlayer.HitInfo)HitList.get(var41);
         IsoMovingObject var14 = var13.object;
         BaseVehicle var15 = (BaseVehicle)Type.tryCastTo(var14, BaseVehicle.class);
         IsoZombie var16 = (IsoZombie)Type.tryCastTo(var14, IsoZombie.class);
         if (var13.object == null && var13.window != null) {
            var13.window.WeaponHit(var1, var2);
         } else {
            this.smashWindowBetween(var1, var14, var2);
            if (!this.isWindowBetween(var1, var14)) {
               int var17 = var13.chance;
               boolean var18 = Rand.Next(100) <= var17;
               if (var18) {
                  Vector2 var19 = tempVector2_1.set(var1.getX(), var1.getY());
                  Vector2 var20 = tempVector2_2.set(var14.getX(), var14.getY());
                  var20.x -= var19.x;
                  var20.y -= var19.y;
                  Vector2 var21 = var1.getLookVector(tempVector2_1);
                  var21.tangent();
                  var20.normalize();
                  boolean var22 = true;
                  float var23 = var21.dot(var20);

                  float var25;
                  for(int var24 = 0; var24 < this.dotList.size(); ++var24) {
                     var25 = (Float)this.dotList.get(var24);
                     if ((double)Math.abs(var23 - var25) < 1.0E-4D) {
                        var22 = false;
                     }
                  }

                  float var44 = var2.getMinDamage();
                  var25 = var2.getMaxDamage();
                  if (!var22) {
                     var44 /= 5.0F;
                     var25 /= 5.0F;
                  }

                  if (var1.isAimAtFloor() && !var2.isRanged() && var1.isNPC()) {
                     splash(var14, var2, var1);
                     var43 = Rand.Next(2);
                  } else if (var1.isAimAtFloor() && !var2.isRanged()) {
                     if (!StringUtils.isNullOrEmpty(var2.getHitFloorSound())) {
                        var1.playSound(var2.getHitFloorSound());
                     } else {
                        var1.playSound(var2.getZombieHitSound());
                     }

                     int var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_Head", -1), 0.28F);
                     if (var26 == -1) {
                        var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_Spine", -1), 0.28F);
                        if (var26 == -1) {
                           var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_L_Calf", -1), 0.13F);
                           if (var26 == -1) {
                              var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_R_Calf", -1), 0.13F);
                           }

                           if (var26 == -1) {
                              var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_L_Foot", -1), 0.23F);
                           }

                           if (var26 == -1) {
                              var26 = this.DoSwingCollisionBoneCheck(var1, GetWeapon(var1), (IsoGameCharacter)var14, ((IsoGameCharacter)var14).getAnimationPlayer().getSkinningBoneIndex("Bip01_R_Foot", -1), 0.23F);
                           }

                           if (var26 == -1) {
                              continue;
                           }

                           var12 = true;
                        }
                     } else {
                        splash(var14, var2, var1);
                        splash(var14, var2, var1);
                        var43 = Rand.Next(0, 3) + 1;
                     }
                  }

                  if (!this.attackVars.bAimAtFloor && !this.attackVars.bCloseKill && !var4.bDoShove && var14 instanceof IsoGameCharacter) {
                     var1.playSound(var2.getZombieHitSound());
                  }

                  float var29;
                  if (var2.isRanged() && var16 != null) {
                     Vector2 var47 = tempVector2_1.set(var1.getX(), var1.getY());
                     Vector2 var27 = tempVector2_2.set(var14.getX(), var14.getY());
                     var27.x -= var47.x;
                     var27.y -= var47.y;
                     Vector2 var28 = var16.getForwardDirection();
                     var27.normalize();
                     var28.normalize();
                     var29 = var27.dot(var28);
                     var16.setHitFromBehind((double)var29 > 0.5D);
                  }

                  if (this.dotList.isEmpty()) {
                     this.dotList.add(var23);
                  }

                  if (var16 != null && var16.isCurrentState(ZombieOnGroundState.instance())) {
                     var16.setReanimateTimer(var16.getReanimateTimer() + (float)Rand.Next(10));
                  }

                  if (var16 != null && var16.isCurrentState(ZombieGetUpState.instance())) {
                     var16.setReanimateTimer((float)(Rand.Next(60) + 30));
                  }

                  boolean var48 = false;
                  if (!var2.isTwoHandWeapon() || var1.isItemInBothHands(var2)) {
                     var48 = true;
                  }

                  float var46 = var25 - var44;
                  float var45;
                  if (var46 == 0.0F) {
                     var45 = var44 + 0.0F;
                  } else {
                     var45 = var44 + (float)Rand.Next((int)(var46 * 1000.0F)) / 1000.0F;
                  }

                  if (!var2.isRanged()) {
                     var45 *= var2.getDamageMod(var1) * var1.getHittingMod();
                  }

                  if (!var48 && !var2.isRanged() && var25 > var44) {
                     var45 -= var44;
                  }

                  int var30;
                  if (var1.isAimAtFloor() && var4.bDoShove) {
                     var29 = 0.0F;

                     for(var30 = BodyPartType.ToIndex(BodyPartType.UpperLeg_L); var30 <= BodyPartType.ToIndex(BodyPartType.Foot_R); ++var30) {
                        var29 += ((BodyPart)var1.getBodyDamage().getBodyParts().get(var30)).getPain();
                     }

                     if (var29 > 10.0F) {
                        var45 /= PZMath.clamp(var29 / 10.0F, 1.0F, 30.0F);
                        MoodlesUI.getInstance().wiggle(MoodleType.Pain);
                        MoodlesUI.getInstance().wiggle(MoodleType.Injured);
                     }
                  } else {
                     var29 = 0.0F;

                     for(var30 = BodyPartType.ToIndex(BodyPartType.Hand_L); var30 <= BodyPartType.ToIndex(BodyPartType.UpperArm_R); ++var30) {
                        var29 += ((BodyPart)var1.getBodyDamage().getBodyParts().get(var30)).getPain();
                     }

                     if (var29 > 10.0F) {
                        var45 /= PZMath.clamp(var29 / 10.0F, 1.0F, 30.0F);
                        MoodlesUI.getInstance().wiggle(MoodleType.Pain);
                        MoodlesUI.getInstance().wiggle(MoodleType.Injured);
                     }
                  }

                  if (var1.Traits.Underweight.isSet()) {
                     var45 *= 0.8F;
                  }

                  if (var1.Traits.VeryUnderweight.isSet()) {
                     var45 *= 0.6F;
                  }

                  if (var1.Traits.Emaciated.isSet()) {
                     var45 *= 0.4F;
                  }

                  var29 = var45 / ((float)var40 / 2.0F);
                  if (var1.isAttackWasSuperAttack()) {
                     var29 *= 5.0F;
                  }

                  ++var40;
                  if (var2.isMultipleHitConditionAffected()) {
                     var6 = true;
                  }

                  Vector2 var49 = tempVector2_1.set(var1.getX(), var1.getY());
                  Vector2 var31 = tempVector2_2.set(var14.getX(), var14.getY());
                  var31.x -= var49.x;
                  var31.y -= var49.y;
                  float var32 = var31.getLength();
                  float var33 = 1.0F;
                  if (!var2.isRangeFalloff()) {
                     var33 = var32 / var2.getMaxRange(var1);
                  }

                  var33 *= 2.0F;
                  if (var33 < 0.3F) {
                     var33 = 1.0F;
                  }

                  if (var2.isRanged() && var1.getPerkLevel(PerkFactory.Perks.Aiming) < 6 && var1.getMoodles().getMoodleLevel(MoodleType.Panic) > 2) {
                     var29 -= (float)var1.getMoodles().getMoodleLevel(MoodleType.Panic) * 0.2F;
                     MoodlesUI.getInstance().wiggle(MoodleType.Panic);
                  }

                  if (!var2.isRanged() && var1.getMoodles().getMoodleLevel(MoodleType.Panic) > 1) {
                     var29 -= (float)var1.getMoodles().getMoodleLevel(MoodleType.Panic) * 0.1F;
                     MoodlesUI.getInstance().wiggle(MoodleType.Panic);
                  }

                  if (var1.getMoodles().getMoodleLevel(MoodleType.Stress) > 1) {
                     var29 -= (float)var1.getMoodles().getMoodleLevel(MoodleType.Stress) * 0.1F;
                     MoodlesUI.getInstance().wiggle(MoodleType.Stress);
                  }

                  if (var29 < 0.0F) {
                     var29 = 0.1F;
                  }

                  if (var1.isAimAtFloor() && var4.bDoShove) {
                     var29 = Rand.Next(0.7F, 1.0F) + (float)var1.getPerkLevel(PerkFactory.Perks.Strength) * 0.2F;
                     Clothing var34 = (Clothing)var1.getWornItem("Shoes");
                     if (var34 == null) {
                        var29 *= 0.5F;
                     } else {
                        var29 *= var34.getStompPower();
                     }
                  }

                  if (!var2.isRanged()) {
                     switch(var1.getMoodles().getMoodleLevel(MoodleType.Endurance)) {
                     case 0:
                     default:
                        break;
                     case 1:
                        var29 *= 0.5F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Endurance);
                        break;
                     case 2:
                        var29 *= 0.2F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Endurance);
                        break;
                     case 3:
                        var29 *= 0.1F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Endurance);
                        break;
                     case 4:
                        var29 *= 0.05F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Endurance);
                     }

                     switch(var1.getMoodles().getMoodleLevel(MoodleType.Tired)) {
                     case 0:
                     default:
                        break;
                     case 1:
                        var29 *= 0.5F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Tired);
                        break;
                     case 2:
                        var29 *= 0.2F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Tired);
                        break;
                     case 3:
                        var29 *= 0.1F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Tired);
                        break;
                     case 4:
                        var29 *= 0.05F;
                        MoodlesUI.getInstance().wiggle(MoodleType.Tired);
                     }
                  }

                  var1.knockbackAttackMod = 1.0F;
                  if ("KnifeDeath".equals(var1.getVariableString("ZombieHitReaction"))) {
                     var33 *= 1000.0F;
                     var1.knockbackAttackMod = 0.0F;
                     var1.addWorldSoundUnlessInvisible(4, 4, false);
                     this.attackVars.bCloseKill = true;
                     var14.setCloseKilled(true);
                  } else {
                     this.attackVars.bCloseKill = false;
                     var14.setCloseKilled(false);
                     if (var15 == null && var2.getImpactSound() != null && (!var4.bDoShove || var1.isAimAtFloor())) {
                        var1.getEmitter().playSound(var2.getImpactSound());
                     }

                     var1.addWorldSoundUnlessInvisible(8, 8, false);
                     if (Rand.Next(3) != 0 && (!var1.isAimAtFloor() || !var4.bDoShove)) {
                        if (Rand.Next(7) == 0) {
                           var1.addWorldSoundUnlessInvisible(16, 16, false);
                        }
                     } else {
                        var1.addWorldSoundUnlessInvisible(10, 10, false);
                     }
                  }

                  var14.setHitFromAngle(var13.dot);
                  if (var16 != null) {
                     var16.setHitFromBehind(var1.isBehind(var16));
                     var16.setHitAngle(var16.getForwardDirection());
                     var16.setPlayerAttackPosition(var16.testDotSide(var1));
                     var16.setHitHeadWhileOnFloor(var43);
                     var16.setHitLegsWhileOnFloor(var12);
                     if (var43 > 0) {
                        var16.addBlood(BloodBodyPartType.Head, true, true, true);
                        var16.addBlood(BloodBodyPartType.Torso_Upper, true, false, false);
                        var16.addBlood(BloodBodyPartType.UpperArm_L, true, false, false);
                        var16.addBlood(BloodBodyPartType.UpperArm_R, true, false, false);
                        var29 *= 3.0F;
                     }

                     if (var12) {
                        var29 = 0.0F;
                     }

                     boolean var50 = false;
                     int var51;
                     if (var43 > 0) {
                        var51 = Rand.Next(BodyPartType.ToIndex(BodyPartType.Head), BodyPartType.ToIndex(BodyPartType.Neck) + 1);
                     } else if (var12) {
                        var51 = Rand.Next(BodyPartType.ToIndex(BodyPartType.Groin), BodyPartType.ToIndex(BodyPartType.Foot_R) + 1);
                     } else {
                        var51 = Rand.Next(BodyPartType.ToIndex(BodyPartType.Hand_L), BodyPartType.ToIndex(BodyPartType.Neck) + 1);
                     }

                     float var35 = var16.getBodyPartClothingDefense(var51, false) / 2.0F;
                     var35 += var16.getBodyPartClothingDefense(var51, true);
                     if (var35 > 70.0F) {
                        var35 = 70.0F;
                     }

                     float var36 = var29 * Math.abs(1.0F - var35 / 100.0F);
                     var29 = var36;
                     var16.helmetFall(var43 > 0);
                     if ("KnifeDeath".equals(var1.getVariableString("ZombieHitReaction")) && !"Tutorial".equals(Core.GameMode)) {
                        byte var37 = 8;
                        if (var16.isCurrentState(AttackState.instance())) {
                           var37 = 3;
                        }

                        int var38 = var1.getPerkLevel(PerkFactory.Perks.SmallBlade) + 1;
                        if (Rand.NextBool(var37 + var38 * 2)) {
                           InventoryItem var39 = var1.getPrimaryHandItem();
                           var1.getInventory().Remove(var39);
                           var1.removeFromHands(var39);
                           var16.setAttachedItem("JawStab", var39);
                        }

                        var16.setVariable("bKnifeDeath", true);
                     }
                  }

                  if (GameClient.bClient) {
                     ByteBufferWriter var53 = GameClient.connection.startPacket();
                     PacketTypes.doPacket((short)26, var53);
                     var53.putByte((byte)((IsoPlayer)var1).PlayerIndex);
                     if (var16 != null) {
                        var53.putInt(1);
                        var53.putShort(var16.OnlineID);
                     } else if (var14 instanceof IsoPlayer) {
                        var53.putInt(2);
                        var53.putShort((short)((IsoPlayer)var14).OnlineID);
                        var1.setSafetyCooldown(var1.getSafetyCooldown() + (float)ServerOptions.instance.SafetyCooldownTimer.getValue());
                     } else if (var15 != null) {
                        var53.putInt(3);
                        var53.putShort(var15.VehicleID);
                     }

                     var53.putUTF(var2.getFullType());
                     var53.putFloat(var29);
                     var53.putBoolean(var5);
                     var53.putBoolean(var14.isCloseKilled());
                     var53.putFloat(var33);
                     var53.putFloat(var14.getX());
                     var53.putFloat(var14.getY());
                     var53.putFloat(var14.getZ());
                     var53.putFloat(var1.getX());
                     var53.putFloat(var1.getY());
                     var53.putFloat(var1.getZ());
                     var53.putFloat(var14.getHitForce());
                     var53.putFloat(var14.getHitDir().x);
                     var53.putFloat(var14.getHitDir().y);
                     var53.putFloat(((IsoPlayer)var1).useChargeDelta);
                     GameClient.connection.endPacket();
                  } else if (var15 == null && var14.getSquare() != null && var1.getSquare() != null) {
                     var14.setCloseKilled(this.attackVars.bCloseKill);
                     var14.Hit(var2, var1, var29, var5, var33);
                     LuaEventManager.triggerEvent("OnWeaponHitXp", var1, var2, var14, var29);
                     if ((!var4.bDoShove || var1.isAimAtFloor()) && var1.DistToSquared(var14) < 2.0F && Math.abs(var1.z - var14.z) < 0.5F) {
                        var1.addBlood((BloodBodyPartType)null, false, false, false);
                     }

                     if (var14 instanceof IsoGameCharacter) {
                        if (((IsoGameCharacter)var14).isDead()) {
                           var10000 = var1.getStats();
                           var10000.stress -= 0.02F;
                        } else if (!var4.bDoShove || var1.isAimAtFloor()) {
                           splash(var14, var2, var1);
                        }
                     }
                  } else if (var15 != null) {
                     VehiclePart var52 = var15.getNearestBodyworkPart(var1);
                     if (var52 != null) {
                        VehicleWindow var54 = var52.getWindow();

                        int var55;
                        for(var55 = 0; var55 < var52.getChildCount(); ++var55) {
                           VehiclePart var56 = var52.getChild(var55);
                           if (var56.getWindow() != null) {
                              var54 = var56.getWindow();
                              break;
                           }
                        }

                        if (var54 != null && var54.isHittable()) {
                           var55 = this.calcDamageToVehicle((int)var29 * 10, var2.getDoorDamage(), true);
                           var54.damage(var55);
                           var1.playSound("HitVehicleWindowWithWeapon");
                        } else {
                           var55 = this.calcDamageToVehicle((int)var29 * 10, var2.getDoorDamage(), false);
                           var52.setCondition(var52.getCondition() - var55);
                           var1.playSound("HitVehiclePartWithWeapon");
                        }
                     }
                  }
               }
            }
         }
      }

      if (!var6 && var8) {
         boolean var42 = this.bHitOnlyTree && var2.getScriptItem().Categories.contains("Axe");
         var43 = var42 ? 2 : 1;
         if (Rand.Next(var2.getConditionLowerChance() * var43 + var1.getMaintenanceMod() * 2) == 0) {
            var6 = true;
         } else if (Rand.NextBool(2) && !var2.getName().contains("Bare Hands") && (!var2.isTwoHandWeapon() || var1.getPrimaryHandItem() == var2 || var1.getSecondaryHandItem() == var2 || !Rand.NextBool(3))) {
            var1.getXp().AddXP(PerkFactory.Perks.Maintenance, 1.0F);
         }
      }

      var3.put(PARAM_LOWER_CONDITION, var6 ? Boolean.TRUE : Boolean.FALSE);
      var3.put(PARAM_ATTACKED, Boolean.TRUE);
   }

   private int calcDamageToVehicle(int var1, int var2, boolean var3) {
      if (var1 <= 0) {
         return 0;
      } else {
         float var4 = (float)var1;
         float var5 = PZMath.clamp(var4 / (var3 ? 10.0F : 40.0F), 0.0F, 1.0F);
         int var6 = (int)((float)var2 * var5);
         return PZMath.clamp(var6, 1, var2);
      }
   }

   private static void splash(IsoMovingObject var0, HandWeapon var1, IsoGameCharacter var2) {
      IsoGameCharacter var3 = (IsoGameCharacter)var0;
      if (var1 != null && SandboxOptions.instance.BloodLevel.getValue() > 1) {
         int var4 = var1.getSplatNumber();
         if (var4 < 1) {
            var4 = 1;
         }

         if (Core.bLastStand) {
            var4 *= 3;
         }

         switch(SandboxOptions.instance.BloodLevel.getValue()) {
         case 2:
            var4 /= 2;
         case 3:
         default:
            break;
         case 4:
            var4 *= 2;
            break;
         case 5:
            var4 *= 5;
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            var3.splatBlood(3, 0.3F);
         }
      }

      byte var11 = 3;
      byte var10 = 7;
      switch(SandboxOptions.instance.BloodLevel.getValue()) {
      case 1:
         var10 = 0;
         break;
      case 2:
         var10 = 4;
         var11 = 5;
      case 3:
      default:
         break;
      case 4:
         var10 = 10;
         var11 = 2;
         break;
      case 5:
         var10 = 15;
         var11 = 0;
      }

      if (SandboxOptions.instance.BloodLevel.getValue() > 1) {
         var3.splatBloodFloorBig(0.3F);
      }

      float var6 = 0.5F;
      if (var3 instanceof IsoZombie && (((IsoZombie)var3).bCrawling || var3.getCurrentState() == ZombieOnGroundState.instance())) {
         var6 = 0.2F;
      }

      float var7 = Rand.Next(1.5F, 5.0F);
      float var8 = Rand.Next(1.5F, 5.0F);
      if (var2 instanceof IsoPlayer && ((IsoPlayer)var2).bDoShove) {
         var7 = Rand.Next(0.0F, 0.5F);
         var8 = Rand.Next(0.0F, 0.5F);
      }

      for(int var9 = 0; var9 < var10; ++var9) {
         if (Rand.Next(var11) == 0) {
            new IsoZombieGiblets(IsoZombieGiblets.GibletType.A, var3.getCell(), var3.getX(), var3.getY(), var3.getZ() + var6, var3.getHitDir().x * var7, var3.getHitDir().y * var8);
         }
      }

   }

   private boolean checkObjectHit(IsoGameCharacter var1, HandWeapon var2, IsoGridSquare var3, boolean var4, boolean var5) {
      if (var3 == null) {
         return false;
      } else {
         for(int var6 = var3.getSpecialObjects().size() - 1; var6 >= 0; --var6) {
            IsoObject var7 = (IsoObject)var3.getSpecialObjects().get(var6);
            IsoDoor var8 = (IsoDoor)Type.tryCastTo(var7, IsoDoor.class);
            IsoThumpable var9 = (IsoThumpable)Type.tryCastTo(var7, IsoThumpable.class);
            IsoWindow var10 = (IsoWindow)Type.tryCastTo(var7, IsoWindow.class);
            Thumpable var11;
            if (var8 != null && (var4 && var8.north || var5 && !var8.north)) {
               var11 = var8.getThumpableFor(var1);
               if (var11 != null) {
                  var11.WeaponHit(var1, var2);
                  return true;
               }
            }

            if (var9 != null) {
               if (!var9.isDoor() && !var9.isWindow() && var9.isBlockAllTheSquare()) {
                  var11 = var9.getThumpableFor(var1);
                  if (var11 != null) {
                     var11.WeaponHit(var1, var2);
                     return true;
                  }
               } else if (var4 && var9.north || var5 && !var9.north) {
                  var11 = var9.getThumpableFor(var1);
                  if (var11 != null) {
                     var11.WeaponHit(var1, var2);
                     return true;
                  }
               }
            }

            if (var10 != null && (var4 && var10.north || var5 && !var10.north)) {
               var11 = var10.getThumpableFor(var1);
               if (var11 != null) {
                  var11.WeaponHit(var1, var2);
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean CheckObjectHit(IsoGameCharacter var1, HandWeapon var2) {
      if (var1.isAimAtFloor()) {
         this.bHitOnlyTree = false;
         return false;
      } else {
         boolean var3 = false;
         int var4 = 0;
         int var5 = 0;
         IsoDirections var6 = IsoDirections.fromAngle(var1.getForwardDirection());
         int var7 = 0;
         int var8 = 0;
         if (var6 == IsoDirections.NE || var6 == IsoDirections.N || var6 == IsoDirections.NW) {
            --var8;
         }

         if (var6 == IsoDirections.SE || var6 == IsoDirections.S || var6 == IsoDirections.SW) {
            ++var8;
         }

         if (var6 == IsoDirections.NW || var6 == IsoDirections.W || var6 == IsoDirections.SW) {
            --var7;
         }

         if (var6 == IsoDirections.NE || var6 == IsoDirections.E || var6 == IsoDirections.SE) {
            ++var7;
         }

         IsoCell var9 = IsoWorld.instance.CurrentCell;
         IsoGridSquare var10 = var1.getCurrentSquare();
         IsoGridSquare var11 = var9.getGridSquare(var10.getX() + var7, var10.getY() + var8, var10.getZ());
         if (var11 != null) {
            if (this.checkObjectHit(var1, var2, var11, false, false)) {
               var3 = true;
               ++var4;
            }

            if (!var11.isBlockedTo(var10)) {
               for(int var12 = 0; var12 < var11.getObjects().size(); ++var12) {
                  IsoObject var13 = (IsoObject)var11.getObjects().get(var12);
                  if (var13 instanceof IsoTree) {
                     ((IsoTree)var13).WeaponHit(var1, var2);
                     var3 = true;
                     ++var4;
                     ++var5;
                     if (var13.getObjectIndex() == -1) {
                        --var12;
                     }
                  }
               }
            }
         }

         if ((var6 == IsoDirections.NE || var6 == IsoDirections.N || var6 == IsoDirections.NW) && this.checkObjectHit(var1, var2, var10, true, false)) {
            var3 = true;
            ++var4;
         }

         IsoGridSquare var14;
         if (var6 == IsoDirections.SE || var6 == IsoDirections.S || var6 == IsoDirections.SW) {
            var14 = var9.getGridSquare(var10.getX(), var10.getY() + 1, var10.getZ());
            if (this.checkObjectHit(var1, var2, var14, true, false)) {
               var3 = true;
               ++var4;
            }
         }

         if (var6 == IsoDirections.SE || var6 == IsoDirections.E || var6 == IsoDirections.NE) {
            var14 = var9.getGridSquare(var10.getX() + 1, var10.getY(), var10.getZ());
            if (this.checkObjectHit(var1, var2, var14, false, true)) {
               var3 = true;
               ++var4;
            }
         }

         if ((var6 == IsoDirections.NW || var6 == IsoDirections.W || var6 == IsoDirections.SW) && this.checkObjectHit(var1, var2, var10, false, true)) {
            var3 = true;
            ++var4;
         }

         this.bHitOnlyTree = var3 && var4 == var5;
         return var3;
      }
   }

   private LosUtil.TestResults los(int var1, int var2, int var3, int var4, int var5, SwipeStatePlayer.LOSVisitor var6) {
      IsoCell var7 = IsoWorld.instance.CurrentCell;
      int var10 = var4 - var2;
      int var11 = var3 - var1;
      int var12 = var5 - var5;
      float var13 = 0.5F;
      float var14 = 0.5F;
      IsoGridSquare var15 = var7.getGridSquare(var1, var2, var5);
      float var16;
      float var17;
      IsoGridSquare var18;
      if (Math.abs(var11) > Math.abs(var10)) {
         var16 = (float)var10 / (float)var11;
         var17 = (float)var12 / (float)var11;
         var13 += (float)var2;
         var14 += (float)var5;
         var11 = var11 < 0 ? -1 : 1;
         var16 *= (float)var11;

         for(var17 *= (float)var11; var1 != var3; var15 = var18) {
            var1 += var11;
            var13 += var16;
            var14 += var17;
            var18 = var7.getGridSquare(var1, (int)var13, (int)var14);
            if (var6.visit(var18, var15)) {
               return var6.getResult();
            }
         }
      } else {
         var16 = (float)var11 / (float)var10;
         var17 = (float)var12 / (float)var10;
         var13 += (float)var1;
         var14 += (float)var5;
         var10 = var10 < 0 ? -1 : 1;
         var16 *= (float)var10;

         for(var17 *= (float)var10; var2 != var4; var15 = var18) {
            var2 += var10;
            var13 += var16;
            var14 += var17;
            var18 = var7.getGridSquare((int)var13, var2, (int)var14);
            if (var6.visit(var18, var15)) {
               return var6.getResult();
            }
         }
      }

      return LosUtil.TestResults.Clear;
   }

   private IsoWindow getWindowBetween(int var1, int var2, int var3, int var4, int var5) {
      this.windowVisitor.init();
      this.los(var1, var2, var3, var4, var5, this.windowVisitor);
      return this.windowVisitor.window;
   }

   private IsoWindow getWindowBetween(IsoMovingObject var1, IsoMovingObject var2) {
      return this.getWindowBetween((int)var1.x, (int)var1.y, (int)var2.x, (int)var2.y, (int)var1.z);
   }

   private boolean isWindowBetween(IsoMovingObject var1, IsoMovingObject var2) {
      return this.getWindowBetween(var1, var2) != null;
   }

   private void smashWindowBetween(IsoGameCharacter var1, IsoMovingObject var2, HandWeapon var3) {
      IsoWindow var4 = this.getWindowBetween(var1, var2);
      if (var4 != null) {
         var4.WeaponHit(var1, var3);
      }
   }

   public void changeWeapon(HandWeapon var1, IsoGameCharacter var2) {
      if (var1 != null && var1.isUseSelf()) {
         var2.getInventory().setDrawDirty(true);
         Iterator var3 = var2.getInventory().getItems().iterator();

         while(var3.hasNext()) {
            InventoryItem var4 = (InventoryItem)var3.next();
            if (var4 != var1 && var4 instanceof HandWeapon && var4.getType() == var1.getType() && var4.getCondition() > 0) {
               if (var2.getPrimaryHandItem() == var1 && var2.getSecondaryHandItem() == var1) {
                  var2.setPrimaryHandItem(var4);
                  var2.setSecondaryHandItem(var4);
               } else if (var2.getPrimaryHandItem() == var1) {
                  var2.setPrimaryHandItem(var4);
               } else if (var2.getSecondaryHandItem() == var1) {
                  var2.setSecondaryHandItem(var4);
               }

               return;
            }
         }
      }

      if (var1 == null || var1.getCondition() <= 0 || var1.isUseSelf()) {
         HandWeapon var5 = (HandWeapon)var2.getInventory().getBestWeapon(var2.getDescriptor());
         var2.setPrimaryHandItem((InventoryItem)null);
         if (var2.getSecondaryHandItem() == var1) {
            var2.setSecondaryHandItem((InventoryItem)null);
         }

         if (var5 != null && var5 != var2.getPrimaryHandItem() && var5.getCondition() > 0) {
            var2.setPrimaryHandItem(var5);
            if (var5.isTwoHandWeapon() && var2.getSecondaryHandItem() == null) {
               var2.setSecondaryHandItem(var5);
            }
         }
      }

   }

   private static final class WindowVisitor implements SwipeStatePlayer.LOSVisitor {
      LosUtil.TestResults test;
      IsoWindow window;

      private WindowVisitor() {
      }

      void init() {
         this.test = LosUtil.TestResults.Clear;
         this.window = null;
      }

      public boolean visit(IsoGridSquare var1, IsoGridSquare var2) {
         if (var1 != null && var2 != null) {
            boolean var3 = true;
            boolean var4 = false;
            LosUtil.TestResults var5 = var1.testVisionAdjacent(var2.getX() - var1.getX(), var2.getY() - var1.getY(), var2.getZ() - var1.getZ(), var3, var4);
            if (var5 == LosUtil.TestResults.ClearThroughWindow) {
               IsoWindow var6 = var1.getWindowTo(var2);
               if (this.isHittable(var6) && var6.TestVision(var1, var2) == IsoObject.VisionResult.Unblocked) {
                  this.window = var6;
                  return true;
               }
            }

            if (var5 == LosUtil.TestResults.Blocked || this.test == LosUtil.TestResults.Clear || var5 == LosUtil.TestResults.ClearThroughWindow && this.test == LosUtil.TestResults.ClearThroughOpenDoor) {
               this.test = var5;
            } else if (var5 == LosUtil.TestResults.ClearThroughClosedDoor && this.test == LosUtil.TestResults.ClearThroughOpenDoor) {
               this.test = var5;
            }

            return this.test == LosUtil.TestResults.Blocked;
         } else {
            return false;
         }
      }

      public LosUtil.TestResults getResult() {
         return this.test;
      }

      boolean isHittable(IsoWindow var1) {
         if (var1 == null) {
            return false;
         } else if (var1.isBarricaded()) {
            return true;
         } else {
            return !var1.isDestroyed() && !var1.IsOpen();
         }
      }

      // $FF: synthetic method
      WindowVisitor(Object var1) {
         this();
      }
   }

   private interface LOSVisitor {
      boolean visit(IsoGridSquare var1, IsoGridSquare var2);

      LosUtil.TestResults getResult();
   }

   public static final class AttackVars {
      public HandWeapon weapon;
      public IsoGameCharacter targetOnGround;
      public boolean bAimAtFloor;
      public boolean bCloseKill;
      public boolean bDoShove;
      public float useChargeDelta;
      public int recoilDelay;
      public final ArrayList targetsStanding = new ArrayList();
      public final ArrayList targetsProne = new ArrayList();
   }

   public static class CustomComparator implements Comparator {
      public int compare(SwipeStatePlayer.HitInfo var1, SwipeStatePlayer.HitInfo var2) {
         float var3 = var1.distSq;
         float var4 = var2.distSq;
         IsoZombie var5 = (IsoZombie)Type.tryCastTo(var1.object, IsoZombie.class);
         IsoZombie var6 = (IsoZombie)Type.tryCastTo(var2.object, IsoZombie.class);
         if (var5 != null && var6 != null) {
            boolean var7 = SwipeStatePlayer.isProne(var5);
            boolean var8 = SwipeStatePlayer.isProne(var6);
            boolean var9 = var5.isCurrentState(ZombieGetUpState.instance());
            boolean var10 = var6.isCurrentState(ZombieGetUpState.instance());
            if (var9 && !var10 && var8) {
               return -1;
            }

            if (!var9 && var7 && var10) {
               return 1;
            }

            if (var7 && var8) {
               if (var5.isCrawling() && !var6.isCrawling()) {
                  return -1;
               }

               if (!var5.isCrawling() && var6.isCrawling()) {
                  return 1;
               }
            }
         }

         if (var3 > var4) {
            return 1;
         } else {
            return var4 > var3 ? -1 : 0;
         }
      }
   }

   public static final class HitInfo {
      public IsoMovingObject object;
      public IsoWindow window;
      public float x;
      public float y;
      public float z;
      public float dot;
      public float distSq;
      public int chance = 0;

      public SwipeStatePlayer.HitInfo init(IsoMovingObject var1, float var2, float var3, float var4, float var5, float var6) {
         this.window = null;
         this.object = var1;
         this.x = var4;
         this.y = var5;
         this.z = var6;
         this.dot = var2;
         this.distSq = var3;
         return this;
      }

      public SwipeStatePlayer.HitInfo init(IsoWindow var1, float var2, float var3) {
         this.object = null;
         this.window = var1;
         this.z = var1.getZ();
         this.dot = var2;
         this.distSq = var3;
         return this;
      }
   }
}
