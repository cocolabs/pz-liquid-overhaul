package zombie.core.skinnedmodel.animation;

import zombie.core.skinnedmodel.model.SkinningBone;
import zombie.core.skinnedmodel.model.SkinningData;
import zombie.util.StringUtils;

public class AnimationBoneBinding {
   public final String boneName;
   private SkinningBone m_bone = null;
   private SkinningData m_skinningData;

   public AnimationBoneBinding(String var1) {
      this.boneName = var1;
   }

   public SkinningData getSkinningData() {
      return this.m_skinningData;
   }

   public void setSkinningData(SkinningData var1) {
      if (this.m_skinningData != var1) {
         this.m_skinningData = var1;
         this.m_bone = null;
      }

   }

   public SkinningBone getBone() {
      if (this.m_bone == null) {
         this.initBone();
      }

      return this.m_bone;
   }

   private void initBone() {
      if (this.m_skinningData == null) {
         this.m_bone = null;
      } else {
         this.m_bone = this.m_skinningData.getBone(this.boneName);
      }
   }

   public String toString() {
      String var2 = System.lineSeparator();
      return this.getClass().getName() + var2 + "{" + var2 + "\t" + "boneName:\"" + this.boneName + "\"" + var2 + "\t" + "m_bone:" + StringUtils.indent(String.valueOf(this.m_bone)) + var2 + "}";
   }
}
