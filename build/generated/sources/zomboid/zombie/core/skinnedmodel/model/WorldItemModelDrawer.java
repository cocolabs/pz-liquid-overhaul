package zombie.core.skinnedmodel.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import zombie.core.Core;
import zombie.core.ImmutableColor;
import zombie.core.SpriteRenderer;
import zombie.core.skinnedmodel.ModelManager;
import zombie.core.skinnedmodel.population.ClothingItem;
import zombie.core.skinnedmodel.shader.Shader;
import zombie.core.skinnedmodel.visual.ItemVisual;
import zombie.core.textures.ColorInfo;
import zombie.core.textures.Texture;
import zombie.core.textures.TextureDraw;
import zombie.debug.DebugOptions;
import zombie.inventory.InventoryItem;
import zombie.inventory.types.Clothing;
import zombie.inventory.types.HandWeapon;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoMovingObject;
import zombie.popman.ObjectPool;
import zombie.scripting.ScriptManager;
import zombie.scripting.objects.ModelAttachment;
import zombie.scripting.objects.ModelScript;
import zombie.util.StringUtils;

public final class WorldItemModelDrawer extends TextureDraw.GenericDrawer {
   private static final ObjectPool s_modelDrawerPool = new ObjectPool(WorldItemModelDrawer::new);
   private static final ColorInfo tempColorInfo = new ColorInfo();
   private static final Matrix4f s_attachmentXfrm = new Matrix4f();
   private Model m_model;
   private float m_hue;
   private float m_tintR;
   private float m_tintG;
   private float m_tintB;
   private float m_x;
   private float m_y;
   private float m_z;
   private final Vector3f m_angle = new Vector3f();
   private final Matrix4f m_transform = new Matrix4f();
   private float m_ambientR;
   private float m_ambientG;
   private float m_ambientB;

   public static boolean renderMain(InventoryItem var0, IsoGridSquare var1, float var2, float var3, float var4, float var5) {
      if (var0 != null && var1 != null) {
         String var9;
         if (var0 instanceof Clothing) {
            ClothingItem var6 = var0.getClothingItem();
            ItemVisual var7 = var0.getVisual();
            if (var6 != null && var7 != null && "Bip01_Head".equalsIgnoreCase(var6.m_AttachBone) && (!((Clothing)var0).isCosmetic() || "Eyes".equals(var0.getBodyLocation()))) {
               boolean var8 = false;
               var9 = var6.getModel(var8);
               if (!StringUtils.isNullOrWhitespace(var9)) {
                  String var10 = var7.getTextureChoice(var6);
                  boolean var11 = var6.m_Static;
                  String var12 = var6.m_Shader;
                  Model var13 = ModelManager.instance.tryGetLoadedModel(var9, var10, var11, var12, false);
                  if (var13 == null) {
                     ModelManager.instance.loadAdditionalModel(var9, var10, var11, var12);
                  }

                  var13 = ModelManager.instance.getLoadedModel(var9, var10, var11, var12);
                  if (var13 != null && var13.isReady()) {
                     WorldItemModelDrawer var24 = (WorldItemModelDrawer)s_modelDrawerPool.alloc();
                     float var25 = var7.getHue(var6);
                     ImmutableColor var16 = var7.getTint(var6);
                     var24.init(var0, var1, var2, var3, var4, var13, var25, var16, var5);
                     SpriteRenderer.instance.drawGeneric(var24);
                     return true;
                  }
               }
            }
         }

         if (var0 instanceof HandWeapon) {
            ModelScript var17 = ScriptManager.instance.getModelScript(var0.getStaticModel());
            if (var17 != null) {
               String var18 = var17.getMeshName();
               String var19 = var17.getTextureName();
               var9 = var17.getShaderName();
               boolean var20 = var17.bStatic;
               Model var21 = ModelManager.instance.tryGetLoadedModel(var18, var19, var20, var9, false);
               if (var21 == null) {
                  ModelManager.instance.loadAdditionalModel(var18, var19, var20, var9);
               }

               var21 = ModelManager.instance.getLoadedModel(var18, var19, var20, var9);
               if (var21 != null && var21.isReady()) {
                  WorldItemModelDrawer var22 = (WorldItemModelDrawer)s_modelDrawerPool.alloc();
                  float var23 = 1.0F;
                  ImmutableColor var14 = ImmutableColor.white;
                  var22.init(var0, var1, var2, var3, var4, var21, var23, var14, var5);
                  if (var17.scale != 1.0F) {
                     var22.m_transform.scale(var17.scale);
                  }

                  var22.m_angle.x = 0.0F;
                  var22.m_angle.y = 180.0F;
                  ModelAttachment var15 = var17.getAttachmentById("world");
                  if (var15 != null) {
                     ModelInstanceRenderData.makeAttachmentTransform(var15, s_attachmentXfrm);
                     s_attachmentXfrm.invert();
                     var22.m_transform.mul(s_attachmentXfrm);
                  }

                  SpriteRenderer.instance.drawGeneric(var22);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void init(InventoryItem var1, IsoGridSquare var2, float var3, float var4, float var5, Model var6, float var7, ImmutableColor var8, float var9) {
      this.m_model = var6;
      this.m_tintR = var8.r;
      this.m_tintG = var8.g;
      this.m_tintB = var8.b;
      this.m_hue = var7;
      this.m_x = var3;
      this.m_y = var4;
      this.m_z = var5;
      this.m_transform.rotationZ((90.0F + var9) * 0.017453292F);
      if (var1 instanceof Clothing) {
         float var10 = -0.08F;
         float var11 = 0.05F;
         this.m_transform.translate(var10, 0.0F, var11);
      }

      this.m_angle.x = 0.0F;
      this.m_angle.y = 525.0F;
      this.m_angle.z = 0.0F;
      var2.interpolateLight(tempColorInfo, this.m_x % 1.0F, this.m_y % 1.0F);
      this.m_ambientR = tempColorInfo.r;
      this.m_ambientG = tempColorInfo.g;
      this.m_ambientB = tempColorInfo.b;
   }

   public void render() {
      if (this.m_model.bStatic) {
         Model var1 = this.m_model;
         if (var1.Effect == null) {
            var1.CreateShader("basicEffect");
         }

         Shader var2 = var1.Effect;
         if (var2 != null && var1.Mesh != null && var1.Mesh.isReady()) {
            GL11.glPushAttrib(1048575);
            GL11.glPushClientAttrib(-1);
            Core.getInstance().DoPushIsoStuff(this.m_x, this.m_y, this.m_z, 0.0F, false);
            GL11.glRotated(-180.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated((double)this.m_angle.x, 1.0D, 0.0D, 0.0D);
            GL11.glRotated((double)this.m_angle.y, 0.0D, 1.0D, 0.0D);
            GL11.glRotated((double)this.m_angle.z, 0.0D, 0.0D, 1.0D);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthFunc(513);
            GL11.glDepthMask(true);
            GL11.glDepthRange(0.0D, 1.0D);
            GL11.glEnable(2929);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            var2.Start();
            if (var1.tex != null) {
               var2.setTexture(var1.tex, "Texture", 0);
            }

            var2.setDepthBias(0.0F);
            var2.setAmbient(this.m_ambientR * 0.65F, this.m_ambientG * 0.65F, this.m_ambientB * 0.65F);
            var2.setLightingAmount(1.0F);
            var2.setHueShift(this.m_hue);
            var2.setTint(this.m_tintR, this.m_tintG, this.m_tintB);
            var2.setAlpha(1.0F);

            for(int var3 = 0; var3 < 5; ++var3) {
               var2.setLight(var3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Float.NaN, 0.0F, 0.0F, 0.0F, (IsoMovingObject)null);
            }

            float var4 = 0.7F;
            var2.setLight(3, this.m_x - 2.0F, this.m_y - 2.0F, this.m_z + 1.0F, this.m_ambientR / 4.0F * var4, this.m_ambientG / 4.0F * var4, this.m_ambientB / 4.0F * var4, 5000.0F, Float.NaN, this.m_x, this.m_y, this.m_z, (IsoMovingObject)null);
            var2.setLight(4, this.m_x + 2.0F, this.m_y + 2.0F, this.m_z + 1.0F, this.m_ambientR / 4.0F * var4, this.m_ambientG / 4.0F * var4, this.m_ambientB / 4.0F * var4, 5000.0F, Float.NaN, this.m_x, this.m_y, this.m_z, (IsoMovingObject)null);
            var2.setTransformMatrix(this.m_transform, false);
            var1.Mesh.Draw(var2);
            var2.End();
            if (Core.bDebug && DebugOptions.instance.ModelRenderAxis.getValue()) {
               Model.debugDrawAxis(0.0F, 0.0F, 0.0F, 0.5F, 1.0F);
            }

            Core.getInstance().DoPopIsoStuff();
            GL11.glPopAttrib();
            GL11.glPopClientAttrib();
            Texture.lastTextureID = -1;
            SpriteRenderer.ringBuffer.restoreBoundTextures = true;
            SpriteRenderer.ringBuffer.restoreVBOs = true;
         }
      }
   }

   public void postRender() {
      s_modelDrawerPool.release((Object)this);
   }
}
