package zombie.core.skinnedmodel.advancedanimation;

import java.util.List;

public final class AnimCondition {
   public String m_Name = "";
   public AnimCondition.Type m_Type;
   public float m_FloatValue;
   public boolean m_BoolValue;
   public String m_StringValue;

   public AnimCondition() {
      this.m_Type = AnimCondition.Type.STRING;
      this.m_FloatValue = 0.0F;
      this.m_BoolValue = false;
      this.m_StringValue = "";
   }

   public String toString() {
      return String.format("AnimCondition{name:%s type:%s value:%s }", this.m_Name, this.m_Type.toString(), this.getValueString());
   }

   public String getConditionString() {
      return this.m_Type == AnimCondition.Type.OR ? "OR" : String.format("( %s %s %s )", this.m_Name, this.m_Type.toString(), this.getValueString());
   }

   public String getValueString() {
      switch(this.m_Type) {
      case EQU:
      case NEQ:
      case LESS:
      case GTR:
         return String.valueOf(this.m_FloatValue);
      case BOOL:
         return this.m_BoolValue ? "true" : "false";
      case STRING:
         return this.m_StringValue;
      case OR:
         return " -- OR -- ";
      default:
         throw new RuntimeException("Unexpected internal type:" + this.m_Type);
      }
   }

   public boolean check(IAnimationVariableSource var1) {
      String var2 = this.m_Name;
      float var5;
      switch(this.m_Type) {
      case EQU:
         var5 = var1.getVariableFloat(var2, Float.MAX_VALUE);
         return var5 != Float.MAX_VALUE && this.m_FloatValue == var5;
      case NEQ:
         var5 = var1.getVariableFloat(var2, Float.MAX_VALUE);
         return var5 != Float.MAX_VALUE && this.m_FloatValue != var5;
      case LESS:
         var5 = var1.getVariableFloat(var2, Float.MAX_VALUE);
         return var5 != Float.MAX_VALUE && var5 < this.m_FloatValue;
      case GTR:
         var5 = var1.getVariableFloat(var2, Float.MAX_VALUE);
         return var5 != Float.MAX_VALUE && var5 > this.m_FloatValue;
      case BOOL:
         boolean var4 = var1.getVariableBoolean(var2);
         return var4 == this.m_BoolValue;
      case STRING:
         String var3 = var1.getVariableString(var2);
         return this.m_StringValue.equalsIgnoreCase(var3);
      case OR:
         return false;
      default:
         throw new RuntimeException("Unexpected internal type:" + this.m_Type);
      }
   }

   public static boolean pass(IAnimationVariableSource var0, List var1) {
      boolean var2 = true;

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         AnimCondition var4 = (AnimCondition)var1.get(var3);
         if (var4.m_Type == AnimCondition.Type.OR) {
            if (var2) {
               break;
            }

            var2 = true;
         } else {
            var2 = var2 && var4.check(var0);
         }
      }

      return var2;
   }

   public static enum Type {
      STRING,
      BOOL,
      EQU,
      NEQ,
      LESS,
      GTR,
      OR;
   }
}
