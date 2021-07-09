package zombie.characters.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import zombie.core.Translator;

public final class PerkFactory {
   public static final HashMap PerkMap = new HashMap();
   public static final ArrayList PerkList = new ArrayList();
   static float PerkXPReqMultiplier = 1.5F;

   public static String getPerkName(PerkFactory.Perks var0) {
      for(int var1 = 0; var1 < PerkList.size(); ++var1) {
         PerkFactory.Perk var2 = (PerkFactory.Perk)PerkList.get(var1);
         if (var2.getType() == var0) {
            return var2.getName();
         }
      }

      return var0.toString();
   }

   public static PerkFactory.Perks getPerkFromName(String var0) {
      Iterator var1 = PerkMap.entrySet().iterator();

      Entry var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Entry)var1.next();
      } while(!((PerkFactory.Perk)var2.getValue()).name.equals(var0));

      return (PerkFactory.Perks)var2.getKey();
   }

   public static PerkFactory.Perk getPerk(PerkFactory.Perks var0) {
      return (PerkFactory.Perk)PerkMap.get(var0);
   }

   public static PerkFactory.Perk AddPerk(PerkFactory.Perks var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      return AddPerk(var0, var1, PerkFactory.Perks.None, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, false);
   }

   public static PerkFactory.Perk AddPerk(PerkFactory.Perks var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, boolean var12) {
      return AddPerk(var0, var1, PerkFactory.Perks.None, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   public static PerkFactory.Perk AddPerk(PerkFactory.Perks var0, String var1, PerkFactory.Perks var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
      return AddPerk(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, false);
   }

   public static PerkFactory.Perk AddPerk(PerkFactory.Perks var0, String var1, PerkFactory.Perks var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, boolean var13) {
      PerkFactory.Perk var14 = new PerkFactory.Perk(var1, var2);
      var14.passiv = var13;
      var14.type = var0;
      var14.xp1 = (int)((float)var3 * PerkXPReqMultiplier);
      var14.xp2 = (int)((float)var4 * PerkXPReqMultiplier);
      var14.xp3 = (int)((float)var5 * PerkXPReqMultiplier);
      var14.xp4 = (int)((float)var6 * PerkXPReqMultiplier);
      var14.xp5 = (int)((float)var7 * PerkXPReqMultiplier);
      var14.xp6 = (int)((float)var8 * PerkXPReqMultiplier);
      var14.xp7 = (int)((float)var9 * PerkXPReqMultiplier);
      var14.xp8 = (int)((float)var10 * PerkXPReqMultiplier);
      var14.xp9 = (int)((float)var11 * PerkXPReqMultiplier);
      var14.xp10 = (int)((float)var12 * PerkXPReqMultiplier);
      PerkMap.put(var0, var14);
      PerkList.add(var14);
      return var14;
   }

   public static void init() {
      AddPerk(PerkFactory.Perks.Combat, "Combat", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Axe, "Axe", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Blunt, "Blunt", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.SmallBlunt, "SmallBlunt", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.LongBlade, "LongBlade", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.SmallBlade, "SmallBlade", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Spear, "Spear", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Maintenance, "Maintenance", PerkFactory.Perks.Combat, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Firearm, "Firearm", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Aiming, "Aiming", PerkFactory.Perks.Firearm, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Reloading, "Reloading", PerkFactory.Perks.Firearm, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Crafting, "Crafting", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Woodwork, "Carpentry", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Cooking, "Cooking", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Farming, "Farming", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Doctor, "Doctor", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Electricity, "Electricity", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.MetalWelding, "MetalWelding", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Mechanics, "Mechanics", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Tailoring, "Tailoring", PerkFactory.Perks.Crafting, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Survivalist, "Survivalist", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Fishing, "Fishing", PerkFactory.Perks.Survivalist, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Trapping, "Trapping", PerkFactory.Perks.Survivalist, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.PlantScavenging, "Foraging", PerkFactory.Perks.Survivalist, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Passiv, "Passive", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000, true);
      AddPerk(PerkFactory.Perks.Fitness, "Fitness", PerkFactory.Perks.Passiv, 1000, 2000, 4000, 6000, 12000, 20000, 40000, 60000, 80000, 100000, true);
      AddPerk(PerkFactory.Perks.Strength, "Strength", PerkFactory.Perks.Passiv, 1000, 2000, 4000, 6000, 12000, 20000, 40000, 60000, 80000, 100000, true);
      AddPerk(PerkFactory.Perks.Agility, "Agility", 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Sprinting, "Sprinting", PerkFactory.Perks.Agility, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Lightfoot, "Lightfooted", PerkFactory.Perks.Agility, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Nimble, "Nimble", PerkFactory.Perks.Agility, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
      AddPerk(PerkFactory.Perks.Sneak, "Sneaking", PerkFactory.Perks.Agility, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000);
   }

   public static void initTranslations() {
      PerkFactory.Perk var1;
      for(Iterator var0 = PerkList.iterator(); var0.hasNext(); var1.name = Translator.getText("IGUI_perks_" + var1.id)) {
         var1 = (PerkFactory.Perk)var0.next();
      }

   }

   public static class Perk {
      public String id;
      public String name;
      public boolean passiv = false;
      public int xp1;
      public int xp2;
      public int xp3;
      public int xp4;
      public int xp5;
      public int xp6;
      public int xp7;
      public int xp8;
      public int xp9;
      public int xp10;
      public PerkFactory.Perks parent;
      public PerkFactory.Perks type;

      public Perk(String var1) {
         this.parent = PerkFactory.Perks.None;
         this.type = PerkFactory.Perks.None;
         this.id = var1;
         this.name = Translator.getText("IGUI_perks_" + var1);
      }

      public Perk(String var1, PerkFactory.Perks var2) {
         this.parent = PerkFactory.Perks.None;
         this.type = PerkFactory.Perks.None;
         this.id = var1;
         this.name = Translator.getText("IGUI_perks_" + var1);
         this.parent = var2;
      }

      public boolean isPassiv() {
         return this.passiv;
      }

      public PerkFactory.Perks getParent() {
         return this.parent;
      }

      public String getName() {
         return this.name;
      }

      public PerkFactory.Perks getType() {
         return this.type;
      }

      public int getXp1() {
         return this.xp1;
      }

      public int getXp2() {
         return this.xp2;
      }

      public int getXp3() {
         return this.xp3;
      }

      public int getXp4() {
         return this.xp4;
      }

      public int getXp5() {
         return this.xp5;
      }

      public int getXp6() {
         return this.xp6;
      }

      public int getXp7() {
         return this.xp7;
      }

      public int getXp8() {
         return this.xp8;
      }

      public int getXp9() {
         return this.xp9;
      }

      public int getXp10() {
         return this.xp10;
      }

      public float getXpForLevel(int var1) {
         if (var1 == 1) {
            return (float)this.xp1;
         } else if (var1 == 2) {
            return (float)this.xp2;
         } else if (var1 == 3) {
            return (float)this.xp3;
         } else if (var1 == 4) {
            return (float)this.xp4;
         } else if (var1 == 5) {
            return (float)this.xp5;
         } else if (var1 == 6) {
            return (float)this.xp6;
         } else if (var1 == 7) {
            return (float)this.xp7;
         } else if (var1 == 8) {
            return (float)this.xp8;
         } else if (var1 == 9) {
            return (float)this.xp9;
         } else {
            return var1 == 10 ? (float)this.xp10 : -1.0F;
         }
      }

      public float getTotalXpForLevel(int var1) {
         int var2 = 0;

         for(int var3 = 1; var3 <= var1; ++var3) {
            float var4 = this.getXpForLevel(var3);
            if (var4 != -1.0F) {
               var2 = (int)((float)var2 + var4);
            }
         }

         return (float)var2;
      }
   }

   public static enum Perks {
      None(0),
      Agility(1),
      Cooking(2),
      Melee(3),
      Crafting(4),
      Fitness(5),
      Strength(6),
      Blunt(7),
      Axe(8),
      Sprinting(9),
      Lightfoot(10),
      Nimble(11),
      Sneak(12),
      Woodwork(13),
      Aiming(14),
      Reloading(15),
      Farming(16),
      Survivalist(17),
      Fishing(18),
      Trapping(19),
      Passiv(20),
      Firearm(21),
      PlantScavenging(22),
      Doctor(23),
      Electricity(24),
      Blacksmith(25),
      MetalWelding(26),
      Melting(27),
      Mechanics(28),
      Spear(29),
      Maintenance(30),
      SmallBlade(31),
      LongBlade(32),
      SmallBlunt(33),
      Combat(34),
      Tailoring(35),
      MAX(36);

      private final int index;

      private Perks(int var3) {
         this.index = var3;
      }

      public int index() {
         return this.index;
      }

      public static int getMaxIndex() {
         return MAX.index();
      }

      public static PerkFactory.Perks fromIndex(int var0) {
         return ((PerkFactory.Perks[])PerkFactory.Perks.class.getEnumConstants())[var0];
      }

      public static PerkFactory.Perks FromString(String var0) {
         try {
            return valueOf(var0);
         } catch (Exception var2) {
            return MAX;
         }
      }
   }
}
