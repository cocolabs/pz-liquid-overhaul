package zombie.iso.objects;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.core.Rand;
import zombie.core.opengl.Shader;
import zombie.core.skinnedmodel.model.WorldItemModelDrawer;
import zombie.core.textures.ColorInfo;
import zombie.core.textures.Texture;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.debug.LineDrawer;
import zombie.input.Mouse;
import zombie.inventory.InventoryItem;
import zombie.inventory.InventoryItemFactory;
import zombie.inventory.ItemContainer;
import zombie.inventory.ItemSoundManager;
import zombie.inventory.types.DrainableComboItem;
import zombie.inventory.types.InventoryContainer;
import zombie.iso.IsoCamera;
import zombie.iso.IsoCell;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;
import zombie.iso.IsoUtils;
import zombie.iso.IsoWorld;
import zombie.iso.PlayerCamera;
import zombie.iso.sprite.IsoDirectionFrame;
import zombie.iso.sprite.IsoSprite;
import zombie.iso.sprite.IsoSpriteManager;
import zombie.network.GameServer;
import zombie.network.ServerGUI;
import zombie.ui.ObjectTooltip;

public class IsoWorldInventoryObject extends IsoObject {
   public InventoryItem item;
   public float xoff;
   public float yoff;
   public float zoff;
   public boolean removeProcess = false;
   public double dropTime = -1.0D;

   public IsoWorldInventoryObject(InventoryItem var1, IsoGridSquare var2, float var3, float var4, float var5) {
      this.OutlineOnMouseover = true;
      var1.setContainer((ItemContainer)null);
      this.xoff = var3;
      this.yoff = var4;
      this.zoff = var5;
      if (this.xoff == 0.0F) {
         this.xoff = (float)Rand.Next(1000) / 1000.0F;
      }

      if (this.yoff == 0.0F) {
         this.yoff = (float)Rand.Next(1000) / 1000.0F;
      }

      this.item = var1;
      this.sprite = IsoSprite.CreateSprite(IsoSpriteManager.instance);
      this.updateSprite();
      this.square = var2;
      this.offsetY = 0.0F;
      this.offsetX = 0.0F;
      this.dropTime = GameTime.getInstance().getWorldAgeHours();
   }

   public IsoWorldInventoryObject(IsoCell var1) {
      super(var1);
      this.offsetY = 0.0F;
      this.offsetX = 0.0F;
   }

   public int getWaterAmount() {
      return !(this.item instanceof DrainableComboItem) || !this.item.IsWaterSource || !this.item.CanStoreWater || !this.item.getType().equals("BucketWaterFull") && !this.item.getType().equals("WaterPot") ? super.getWaterAmount() : ((DrainableComboItem)this.item).getRemainingUses();
   }

   public void setWaterAmount(int var1) {
      if (!(this.item instanceof DrainableComboItem) || !this.item.IsWaterSource || !this.item.CanStoreWater || !this.item.getType().equals("BucketWaterFull") && !this.item.getType().equals("WaterPot")) {
         super.setWaterAmount(var1);
      } else {
         ((DrainableComboItem)this.item).setUsedDelta((float)var1 * ((DrainableComboItem)this.item).getUseDelta());
      }

   }

   public void update() {
      IsoCell var1 = IsoWorld.instance.getCell();
      if (!this.removeProcess && this.item != null && this.item.shouldUpdateInWorld()) {
         var1.addToProcessItems(this.item);
      }

   }

   public void updateSprite() {
      this.sprite.setTintMod(new ColorInfo(this.item.col.r, this.item.col.g, this.item.col.b, this.item.col.a));
      if (!GameServer.bServer || ServerGUI.isCreated()) {
         String var1 = this.item.getTex().getName();
         if (this.item.isUseWorldItem()) {
            var1 = this.item.getWorldTexture();
         }

         Texture var2;
         try {
            var2 = Texture.getSharedTexture(var1);
            if (var2 == null) {
               var1 = this.item.getTex().getName();
            }
         } catch (Exception var4) {
            var1 = "media/inventory/world/WItem_Sack.png";
         }

         var2 = this.sprite.LoadFrameExplicit(var1);
         if (this.item.getScriptItem() == null) {
            this.sprite.def.scaleAspect((float)var2.getWidthOrig(), (float)var2.getHeightOrig(), (float)(16 * Core.TileScale), (float)(16 * Core.TileScale));
         } else {
            float var10001 = (float)Core.TileScale;
            float var3 = this.item.getScriptItem().ScaleWorldIcon * (var10001 / 2.0F);
            this.sprite.def.setScale(var3, var3);
         }

      }
   }

   public boolean finishupdate() {
      return this.removeProcess || this.item == null || !this.item.shouldUpdateInWorld();
   }

   public void load(ByteBuffer var1, int var2) throws IOException {
      this.xoff = var1.getFloat();
      this.yoff = var1.getFloat();
      this.zoff = var1.getFloat();
      float var3 = var1.getFloat();
      float var4 = var1.getFloat();
      this.sprite = IsoSprite.CreateSprite(IsoSpriteManager.instance);
      int var5 = -1;
      if (var2 >= 54) {
         var5 = var2 >= 72 ? var1.getInt() : var1.getShort();
      }

      int var6 = var1.position();
      String var7 = GameWindow.ReadString(var1);
      byte var8 = -1;
      if (var2 >= 70) {
         var8 = var1.get();
         if (var8 < 0) {
            throw new IOException("invalid item save-type " + var8);
         }
      }

      if (var7.contains("..")) {
         var7 = var7.replace("..", ".");
      }

      byte var9 = 0;
      if (var2 >= 108) {
         var9 = 8;
      }

      this.item = InventoryItemFactory.CreateItem(var7);
      if (this.item == null && var5 != -1) {
         if (var7.length() > 40) {
            var7 = "<unknown>";
         }

         DebugLog.log("Cannot load \"" + var7 + "\" item. Make sure all mods used in save are installed.");

         while(var1.position() < var6 + var5 + var9) {
            var1.get();
         }

      } else if (this.item == null) {
         if (var7.length() > 40) {
            var7 = "<unknown>";
         }

         Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, "Cannot load \"" + var7 + "\" item. Make sure all mods used in save are installed.", (Object)null);
         throw new RuntimeException("Cannot load \"" + var7 + "\" item");
      } else if (var8 != -1 && this.item.getSaveType() != var8) {
         DebugLog.log("ignoring \"" + var7 + "\" because type changed from " + var8 + " to " + this.item.getSaveType());

         while(var1.position() < var6 + var5 + var9) {
            var1.get();
         }

         this.item = null;
      } else {
         this.item.load(var1, var2, false);
         if (var5 != -1 && var1.position() != var6 + var5) {
            throw new IOException("item load() read more data than save() wrote (" + var7 + ")");
         } else {
            this.item.setWorldItem(this);
            this.sprite.getTintMod().r = this.item.getR();
            this.sprite.getTintMod().g = this.item.getG();
            this.sprite.getTintMod().b = this.item.getB();
            if (var2 >= 108) {
               this.dropTime = var1.getDouble();
            } else {
               this.dropTime = GameTime.getInstance().getWorldAgeHours();
            }

            if (!GameServer.bServer || ServerGUI.isCreated()) {
               String var10 = this.item.getTex().getName();
               if (this.item.isUseWorldItem()) {
                  var10 = this.item.getWorldTexture();
               }

               Texture var11;
               try {
                  var11 = Texture.getSharedTexture(var10);
                  if (var11 == null) {
                     var10 = this.item.getTex().getName();
                  }
               } catch (Exception var13) {
                  var10 = "media/inventory/world/WItem_Sack.png";
               }

               var11 = this.sprite.LoadFrameExplicit(var10);
               if (var11 != null) {
                  if (var2 < 33) {
                     float var10000 = var3 - (float)(var11.getWidthOrig() / 2);
                     var10000 = var4 - (float)var11.getHeightOrig();
                  }

                  if (this.item.getScriptItem() == null) {
                     this.sprite.def.scaleAspect((float)var11.getWidthOrig(), (float)var11.getHeightOrig(), (float)(16 * Core.TileScale), (float)(16 * Core.TileScale));
                  } else {
                     float var10001 = (float)Core.TileScale;
                     float var12 = this.item.getScriptItem().ScaleWorldIcon * (var10001 / 2.0F);
                     this.sprite.def.setScale(var12, var12);
                  }

               }
            }
         }
      }
   }

   public boolean Serialize() {
      return true;
   }

   public void save(ByteBuffer var1) throws IOException {
      var1.put((byte)(this.Serialize() ? 1 : 0));
      if (this.Serialize()) {
         var1.putInt(this.getObjectName().hashCode());
         var1.putFloat(this.xoff);
         var1.putFloat(this.yoff);
         var1.putFloat(this.zoff);
         var1.putFloat(this.offsetX);
         var1.putFloat(this.offsetY);
         this.item.saveWithSize(var1, false);
         var1.putDouble(this.dropTime);
      }
   }

   public void softReset() {
      this.square.removeWorldObject(this);
   }

   public String getObjectName() {
      return "WorldInventoryItem";
   }

   public void DoTooltip(ObjectTooltip var1) {
      this.item.DoTooltip(var1);
   }

   public boolean HasTooltip() {
      return false;
   }

   public boolean onMouseLeftClick(int var1, int var2) {
      return false;
   }

   private void debugDrawLocation(float var1, float var2, float var3) {
      if (Core.bDebug && DebugOptions.instance.ModelRenderAxis.getValue()) {
         var1 += this.xoff;
         var2 += this.yoff;
         var3 += this.zoff;
         LineDrawer.DrawIsoLine(var1 - 0.25F, var2, var3, var1 + 0.25F, var2, var3, 1.0F, 1.0F, 1.0F, 0.5F, 1);
         LineDrawer.DrawIsoLine(var1, var2 - 0.25F, var3, var1, var2 + 0.25F, var3, 1.0F, 1.0F, 1.0F, 0.5F, 1);
      }

   }

   private void debugHitTest() {
      int var1 = IsoCamera.frameState.playerIndex;
      float var2 = Core.getInstance().getZoom(var1);
      float var3 = (float)Mouse.getXA();
      float var4 = (float)Mouse.getYA();
      var3 -= (float)IsoCamera.getScreenLeft(var1);
      var4 -= (float)IsoCamera.getScreenTop(var1);
      var3 *= var2;
      var4 *= var2;
      float var5 = this.getScreenPosX(var1) * var2;
      float var6 = this.getScreenPosY(var1) * var2;
      float var7 = IsoUtils.DistanceTo2D(var5, var6, var3, var4);
      byte var8 = 48;
      if (var7 < (float)var8) {
         LineDrawer.drawCircle(var5, var6, (float)var8, 16, 1.0F, 1.0F, 1.0F);
      }

   }

   public void render(float var1, float var2, float var3, ColorInfo var4, boolean var5, boolean var6, Shader var7) {
      if (Core.bDebug) {
      }

      if (WorldItemModelDrawer.renderMain(this.getItem(), this.getSquare(), this.getX() + this.xoff, this.getY() + this.yoff, this.getZ() + this.zoff, 0.0F)) {
         this.debugDrawLocation(var1, var2, var3);
      } else if (this.sprite.CurrentAnim != null && !this.sprite.CurrentAnim.Frames.isEmpty()) {
         Texture var8 = ((IsoDirectionFrame)this.sprite.CurrentAnim.Frames.get(0)).getTexture(this.dir);
         if (var8 != null) {
            float var9 = (float)var8.getWidthOrig() * this.sprite.def.getScaleX() / 2.0F;
            float var10 = (float)var8.getHeightOrig() * this.sprite.def.getScaleY() * 3.0F / 4.0F;
            this.sprite.render(this, var1 + this.xoff, var2 + this.yoff, var3 + this.zoff, this.dir, this.offsetX + var9, this.offsetY + var10, var4, true);
            this.debugDrawLocation(var1, var2, var3);
         }
      }
   }

   public void renderObjectPicker(float var1, float var2, float var3, ColorInfo var4) {
      if (this.sprite != null) {
         if (this.sprite.CurrentAnim != null && !this.sprite.CurrentAnim.Frames.isEmpty()) {
            Texture var5 = ((IsoDirectionFrame)this.sprite.CurrentAnim.Frames.get(0)).getTexture(this.dir);
            if (var5 != null) {
               float var6 = (float)(var5.getWidthOrig() / 2);
               float var7 = (float)var5.getHeightOrig();
               this.sprite.renderObjectPicker(this.sprite.def, this, this.dir);
            }
         }
      }
   }

   public InventoryItem getItem() {
      return this.item;
   }

   public void addToWorld() {
      if (this.item != null && this.item.shouldUpdateInWorld() && !IsoWorld.instance.CurrentCell.getProcessWorldItems().contains(this)) {
         IsoWorld.instance.CurrentCell.getProcessWorldItems().add(this);
      }

      if (this.item instanceof InventoryContainer) {
         ItemContainer var1 = ((InventoryContainer)this.item).getInventory();
         if (var1 != null) {
            var1.addItemsToProcessItems();
         }
      }

      super.addToWorld();
   }

   public void removeFromWorld() {
      this.removeProcess = true;
      IsoWorld.instance.getCell().getProcessWorldItems().remove(this);
      if (this.item != null) {
         IsoWorld.instance.CurrentCell.addToProcessItemsRemove(this.item);
         ItemSoundManager.removeItem(this.item);
      }

      if (this.item instanceof InventoryContainer) {
         ItemContainer var1 = ((InventoryContainer)this.item).getInventory();
         if (var1 != null) {
            var1.removeItemsFromProcessItems();
         }
      }

      super.removeFromWorld();
   }

   public void removeFromSquare() {
      if (this.square != null) {
         this.square.getWorldObjects().remove(this);
         this.square.chunk.recalcHashCodeObjects();
      }

      super.removeFromSquare();
   }

   public float getScreenPosX(int var1) {
      float var2 = IsoUtils.XToScreen(this.getX() + this.xoff, this.getY() + this.yoff, this.getZ() + this.zoff, 0);
      PlayerCamera var3 = IsoCamera.cameras[var1];
      return (var2 - var3.getOffX()) / Core.getInstance().getZoom(var1);
   }

   public float getScreenPosY(int var1) {
      Texture var2 = this.sprite == null ? null : this.sprite.getTextureForCurrentFrame(this.dir);
      float var3 = var2 == null ? 0.0F : (float)var2.getHeightOrig() * this.sprite.def.getScaleY() * 1.0F / 4.0F;
      float var4 = IsoUtils.YToScreen(this.getX() + this.xoff, this.getY() + this.yoff, this.getZ() + this.zoff, 0);
      PlayerCamera var5 = IsoCamera.cameras[var1];
      return (var4 - var5.getOffY() - var3) / Core.getInstance().getZoom(var1);
   }
}
