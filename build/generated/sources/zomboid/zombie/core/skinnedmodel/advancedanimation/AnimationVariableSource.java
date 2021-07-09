package zombie.core.skinnedmodel.advancedanimation;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import zombie.util.StringUtils;

public class AnimationVariableSource implements IAnimationVariableMap {
   private final Map m_GameVariables;

   public AnimationVariableSource() {
      this.m_GameVariables = new TreeMap(String.CASE_INSENSITIVE_ORDER);
   }

   public IAnimationVariableSlot getVariable(String var1) {
      if (StringUtils.isNullOrWhitespace(var1)) {
         return null;
      } else {
         String var2 = var1.trim();
         return (IAnimationVariableSlot)this.m_GameVariables.get(var2);
      }
   }

   public IAnimationVariableSlot getOrCreateVariable(String var1) {
      if (StringUtils.isNullOrWhitespace(var1)) {
         return null;
      } else {
         String var2 = var1.trim();
         Object var3 = (IAnimationVariableSlot)this.m_GameVariables.get(var2);
         if (var3 == null) {
            var3 = new AnimationVariableGenericSlot(var2.toLowerCase());
            this.setVariable((IAnimationVariableSlot)var3);
         }

         return (IAnimationVariableSlot)var3;
      }
   }

   public void setVariable(IAnimationVariableSlot var1) {
      this.m_GameVariables.put(var1.getKey(), var1);
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackBool.CallbackGetStrongTyped var2) {
      this.setVariable(new AnimationVariableSlotCallbackBool(var1, var2));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackBool.CallbackGetStrongTyped var2, AnimationVariableSlotCallbackBool.CallbackSetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackBool(var1, var2, var3));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackString.CallbackGetStrongTyped var2) {
      this.setVariable(new AnimationVariableSlotCallbackString(var1, var2));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackString.CallbackGetStrongTyped var2, AnimationVariableSlotCallbackString.CallbackSetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackString(var1, var2, var3));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackFloat.CallbackGetStrongTyped var2) {
      this.setVariable(new AnimationVariableSlotCallbackFloat(var1, var2));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackFloat.CallbackGetStrongTyped var2, AnimationVariableSlotCallbackFloat.CallbackSetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackFloat(var1, var2, var3));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackInt.CallbackGetStrongTyped var2) {
      this.setVariable(new AnimationVariableSlotCallbackInt(var1, var2));
   }

   public void setVariable(String var1, AnimationVariableSlotCallbackInt.CallbackGetStrongTyped var2, AnimationVariableSlotCallbackInt.CallbackSetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackInt(var1, var2, var3));
   }

   public void setVariable(String var1, boolean var2, AnimationVariableSlotCallbackBool.CallbackGetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackBool(var1, var2, var3));
   }

   public void setVariable(String var1, boolean var2, AnimationVariableSlotCallbackBool.CallbackGetStrongTyped var3, AnimationVariableSlotCallbackBool.CallbackSetStrongTyped var4) {
      this.setVariable(new AnimationVariableSlotCallbackBool(var1, var2, var3, var4));
   }

   public void setVariable(String var1, String var2, AnimationVariableSlotCallbackString.CallbackGetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackString(var1, var2, var3));
   }

   public void setVariable(String var1, String var2, AnimationVariableSlotCallbackString.CallbackGetStrongTyped var3, AnimationVariableSlotCallbackString.CallbackSetStrongTyped var4) {
      this.setVariable(new AnimationVariableSlotCallbackString(var1, var2, var3, var4));
   }

   public void setVariable(String var1, float var2, AnimationVariableSlotCallbackFloat.CallbackGetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackFloat(var1, var2, var3));
   }

   public void setVariable(String var1, float var2, AnimationVariableSlotCallbackFloat.CallbackGetStrongTyped var3, AnimationVariableSlotCallbackFloat.CallbackSetStrongTyped var4) {
      this.setVariable(new AnimationVariableSlotCallbackFloat(var1, var2, var3, var4));
   }

   public void setVariable(String var1, int var2, AnimationVariableSlotCallbackInt.CallbackGetStrongTyped var3) {
      this.setVariable(new AnimationVariableSlotCallbackInt(var1, var2, var3));
   }

   public void setVariable(String var1, int var2, AnimationVariableSlotCallbackInt.CallbackGetStrongTyped var3, AnimationVariableSlotCallbackInt.CallbackSetStrongTyped var4) {
      this.setVariable(new AnimationVariableSlotCallbackInt(var1, var2, var3, var4));
   }

   public void setVariable(String var1, String var2) {
      this.getOrCreateVariable(var1).setValue(var2);
   }

   public void setVariable(String var1, boolean var2) {
      this.getOrCreateVariable(var1).setValue(var2);
   }

   public void setVariable(String var1, float var2) {
      this.getOrCreateVariable(var1).setValue(var2);
   }

   public void clearVariable(String var1) {
      IAnimationVariableSlot var2 = this.getVariable(var1);
      if (var2 != null) {
         var2.clear();
      }

   }

   public void clearVariables() {
      Iterator var1 = this.getGameVariables().iterator();

      while(var1.hasNext()) {
         IAnimationVariableSlot var2 = (IAnimationVariableSlot)var1.next();
         var2.clear();
      }

   }

   public String getVariableString(String var1) {
      IAnimationVariableSlot var2 = this.getVariable(var1);
      return var2 != null ? var2.getValueString() : "";
   }

   public float getVariableFloat(String var1, float var2) {
      IAnimationVariableSlot var3 = this.getVariable(var1);
      return var3 != null ? var3.getValueFloat() : var2;
   }

   public boolean getVariableBoolean(String var1) {
      IAnimationVariableSlot var2 = this.getVariable(var1);
      return var2 != null && var2.getValueBool();
   }

   public Iterable getGameVariables() {
      return this.m_GameVariables.values();
   }

   public boolean isVariable(String var1, String var2) {
      return StringUtils.equalsIgnoreCase(this.getVariableString(var1), var2);
   }

   public boolean containsVariable(String var1) {
      if (StringUtils.isNullOrWhitespace(var1)) {
         return false;
      } else {
         String var2 = var1.trim();
         return this.m_GameVariables.containsKey(var2);
      }
   }
}
