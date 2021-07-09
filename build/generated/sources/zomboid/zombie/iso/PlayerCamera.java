package zombie.iso;

import zombie.GameWindow;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.core.Core;
import zombie.core.math.PZMath;
import zombie.input.GameKeyboard;
import zombie.input.JoypadManager;
import zombie.input.Mouse;
import zombie.iso.sprite.IsoSprite;
import zombie.network.GameServer;
import zombie.ui.UIManager;

public final class PlayerCamera {
   public final int playerIndex;
   public float OffX;
   public float OffY;
   public float TOffX;
   public float TOffY;
   public float lastOffX;
   public float lastOffY;
   public float RightClickTargetX;
   public float RightClickTargetY;
   public float RightClickX;
   public float RightClickY;
   public float DeferedX;
   public float DeferedY;
   public float zoom;
   public int OffscreenWidth;
   public int OffscreenHeight;
   private static final Vector2 offVec = new Vector2();
   private static float PAN_SPEED = 1.0F;
   private long panTime = -1L;

   public PlayerCamera(int var1) {
      this.playerIndex = var1;
   }

   public void center() {
      float var1 = this.OffX;
      float var2 = this.OffY;
      if (IsoCamera.CamCharacter != null) {
         IsoGameCharacter var3 = IsoCamera.CamCharacter;
         var1 = IsoUtils.XToScreen(var3.x + this.DeferedX, var3.y + this.DeferedY, var3.z, 0);
         var2 = IsoUtils.YToScreen(var3.x + this.DeferedX, var3.y + this.DeferedY, var3.z, 0);
         var1 -= (float)(IsoCamera.getOffscreenWidth(this.playerIndex) / 2);
         var2 -= (float)(IsoCamera.getOffscreenHeight(this.playerIndex) / 2);
         var2 -= var3.getOffsetY() * 1.5F;
         var1 += (float)IsoCamera.PLAYER_OFFSET_X;
         var2 += (float)IsoCamera.PLAYER_OFFSET_Y;
      }

      this.OffX = this.TOffX = var1;
      this.OffY = this.TOffY = var2;
   }

   public void update() {
      this.center();
      float var1 = (this.TOffX - this.OffX) / 15.0F;
      float var2 = (this.TOffY - this.OffY) / 15.0F;
      this.OffX += var1;
      this.OffY += var2;
      if (this.lastOffX == 0.0F && this.lastOffY == 0.0F) {
         this.lastOffX = this.OffX;
         this.lastOffY = this.OffY;
      }

      long var3 = System.currentTimeMillis();
      PAN_SPEED = 110.0F;
      float var5 = this.panTime < 0L ? 1.0F : (float)(var3 - this.panTime) / 1000.0F * PAN_SPEED;
      var5 = 1.0F / var5;
      this.panTime = var3;
      IsoPlayer var6 = IsoPlayer.players[this.playerIndex];
      boolean var7 = GameWindow.ActivatedJoyPad != null && var6 != null && var6.JoypadBind != -1;
      if (var6 == null) {
         Object var10000 = null;
      } else {
         var6.getVehicle();
      }

      if (var7 && var6 != null) {
         if ((var6.IsAiming() || var6.isLookingWhileInVehicle()) && JoypadManager.instance.isRBPressed(var6.JoypadBind) && !var6.bJoypadIgnoreAimUntilCentered) {
            this.RightClickTargetX = JoypadManager.instance.getAimingAxisX(var6.JoypadBind) * 1500.0F;
            this.RightClickTargetY = JoypadManager.instance.getAimingAxisY(var6.JoypadBind) * 1500.0F;
            var5 /= 0.5F * Core.getInstance().getZoom(this.playerIndex);
            this.RightClickX = PZMath.step(this.RightClickX, this.RightClickTargetX, (this.RightClickTargetX - this.RightClickX) / (80.0F * var5));
            this.RightClickY = PZMath.step(this.RightClickY, this.RightClickTargetY, (this.RightClickTargetY - this.RightClickY) / (80.0F * var5));
            var6.dirtyRecalcGridStackTime = 2.0F;
         } else {
            this.returnToCenter(1.0F / (16.0F * var5));
         }
      } else {
         int var12;
         if (this.playerIndex == 0 && var6 != null && !var6.isBlockMovement() && GameKeyboard.isKeyDown(Core.getInstance().getKey("PanCamera"))) {
            int var21 = IsoCamera.getScreenWidth(this.playerIndex);
            int var22 = IsoCamera.getScreenHeight(this.playerIndex);
            int var23 = IsoCamera.getScreenLeft(this.playerIndex);
            var12 = IsoCamera.getScreenTop(this.playerIndex);
            float var24 = (float)Mouse.getXA() - ((float)var23 + (float)var21 / 2.0F);
            float var25 = (float)Mouse.getYA() - ((float)var12 + (float)var22 / 2.0F);
            float var26;
            if (var21 > var22) {
               var26 = (float)var22 / (float)var21;
               var24 *= var26;
            } else {
               var26 = (float)var21 / (float)var22;
               var25 *= var26;
            }

            var26 *= (float)var21 / 1366.0F;
            offVec.set(var24, var25);
            offVec.setLength(Math.min(offVec.getLength(), (float)Math.min(var21, var22) / 2.0F));
            var24 = offVec.x / var26;
            var25 = offVec.y / var26;
            this.RightClickTargetX = var24 * 2.0F;
            this.RightClickTargetY = var25 * 2.0F;
            var5 /= 0.5F * Core.getInstance().getZoom(this.playerIndex);
            this.RightClickX = PZMath.step(this.RightClickX, this.RightClickTargetX, (this.RightClickTargetX - this.RightClickX) / (80.0F * var5));
            this.RightClickY = PZMath.step(this.RightClickY, this.RightClickTargetY, (this.RightClickTargetY - this.RightClickY) / (80.0F * var5));
            var6.dirtyRecalcGridStackTime = 2.0F;
            IsoSprite.globalOffsetX = -1.0F;
         } else if (this.playerIndex == 0 && Core.getInstance().getOptionPanCameraWhileAiming()) {
            boolean var9 = !GameServer.bServer;
            boolean var10 = !UIManager.isMouseOverInventory() && var6 != null && var6.isAiming();
            boolean var11 = !var7 && var6 != null && !var6.isDead();
            if (var9 && var10 && var11) {
               var12 = IsoCamera.getScreenWidth(this.playerIndex);
               int var13 = IsoCamera.getScreenHeight(this.playerIndex);
               int var14 = IsoCamera.getScreenLeft(this.playerIndex);
               int var15 = IsoCamera.getScreenTop(this.playerIndex);
               float var16 = (float)Mouse.getXA() - ((float)var14 + (float)var12 / 2.0F);
               float var17 = (float)Mouse.getYA() - ((float)var15 + (float)var13 / 2.0F);
               float var18;
               if (var12 > var13) {
                  var18 = (float)var13 / (float)var12;
                  var16 *= var18;
               } else {
                  var18 = (float)var12 / (float)var13;
                  var17 *= var18;
               }

               var18 *= (float)var12 / 1366.0F;
               float var19 = (float)Math.min(var12, var13) / 6.0F;
               float var20 = (float)Math.min(var12, var13) / 2.0F - var19;
               offVec.set(var16, var17);
               if (offVec.getLength() < var20) {
                  var17 = 0.0F;
                  var16 = 0.0F;
               } else {
                  offVec.setLength(Math.min(offVec.getLength(), (float)Math.min(var12, var13) / 2.0F) - var20);
                  var16 = offVec.x / var18;
                  var17 = offVec.y / var18;
               }

               this.RightClickTargetX = var16 * 7.0F;
               this.RightClickTargetY = var17 * 7.0F;
               var5 /= 0.5F * Core.getInstance().getZoom(this.playerIndex);
               this.RightClickX = PZMath.step(this.RightClickX, this.RightClickTargetX, (this.RightClickTargetX - this.RightClickX) / (80.0F * var5));
               this.RightClickY = PZMath.step(this.RightClickY, this.RightClickTargetY, (this.RightClickTargetY - this.RightClickY) / (80.0F * var5));
               var6.dirtyRecalcGridStackTime = 2.0F;
            } else {
               this.returnToCenter(1.0F / (16.0F * var5));
            }

            IsoSprite.globalOffsetX = -1.0F;
         } else {
            this.returnToCenter(1.0F / (16.0F * var5));
         }
      }

      this.zoom = Core.getInstance().getZoom(this.playerIndex);
   }

   private void returnToCenter(float var1) {
      this.RightClickTargetX = 0.0F;
      this.RightClickTargetY = 0.0F;
      if (this.RightClickTargetX != this.RightClickX || this.RightClickTargetY != this.RightClickY) {
         this.RightClickX = PZMath.step(this.RightClickX, this.RightClickTargetX, (this.RightClickTargetX - this.RightClickX) * var1);
         this.RightClickY = PZMath.step(this.RightClickY, this.RightClickTargetY, (this.RightClickTargetY - this.RightClickY) * var1);
         if (Math.abs(this.RightClickTargetX - this.RightClickX) < 0.001F) {
            this.RightClickX = this.RightClickTargetX;
         }

         if (Math.abs(this.RightClickTargetY - this.RightClickY) < 0.001F) {
            this.RightClickY = this.RightClickTargetY;
         }

         IsoPlayer var2 = IsoPlayer.players[this.playerIndex];
         var2.dirtyRecalcGridStackTime = 2.0F;
      }

   }

   public float getOffX() {
      return (float)((int)(this.OffX + this.RightClickX));
   }

   public float getOffY() {
      return (float)((int)(this.OffY + this.RightClickY));
   }

   public float getTOffX() {
      float var1 = this.TOffX - this.OffX;
      return (float)((int)(this.OffX + this.RightClickX - var1));
   }

   public float getTOffY() {
      float var1 = this.TOffY - this.OffY;
      return (float)((int)(this.OffY + this.RightClickY - var1));
   }

   public float getLastOffX() {
      return (float)((int)(this.lastOffX + this.RightClickX));
   }

   public float getLastOffY() {
      return (float)((int)(this.lastOffY + this.RightClickY));
   }

   public float XToIso(float var1, float var2, float var3) {
      float var4 = var1 + this.getOffX();
      float var5 = var2 + this.getOffY();
      float var6 = (var4 + 2.0F * var5) / (64.0F * (float)Core.TileScale);
      var6 += 3.0F * var3;
      return var6;
   }

   public float YToIso(float var1, float var2, float var3) {
      float var4 = var1 + this.getOffX();
      float var5 = var2 + this.getOffY();
      float var6 = (var4 - 2.0F * var5) / (-64.0F * (float)Core.TileScale);
      var6 += 3.0F * var3;
      return var6;
   }

   public void copyFrom(PlayerCamera var1) {
      this.OffX = var1.OffX;
      this.OffY = var1.OffY;
      this.TOffX = var1.TOffX;
      this.TOffY = var1.TOffY;
      this.lastOffX = var1.lastOffX;
      this.lastOffY = var1.lastOffY;
      this.RightClickTargetX = var1.RightClickTargetX;
      this.RightClickTargetY = var1.RightClickTargetY;
      this.RightClickX = var1.RightClickX;
      this.RightClickY = var1.RightClickY;
      this.DeferedX = var1.DeferedX;
      this.DeferedY = var1.DeferedY;
      this.zoom = var1.zoom;
      this.OffscreenWidth = var1.OffscreenWidth;
      this.OffscreenHeight = var1.OffscreenHeight;
   }

   public void initFromIsoCamera(int var1) {
      this.copyFrom(IsoCamera.cameras[var1]);
      this.zoom = Core.getInstance().getZoom(var1);
      this.OffscreenWidth = IsoCamera.getOffscreenWidth(var1);
      this.OffscreenHeight = IsoCamera.getOffscreenHeight(var1);
   }
}
