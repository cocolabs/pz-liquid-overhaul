package zombie.iso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import se.krka.kahlua.vm.KahluaTable;
import zombie.SandboxOptions;
import zombie.Lua.LuaManager;
import zombie.characters.IsoGameCharacter;
import zombie.characters.skills.PerkFactory;
import zombie.core.Translator;
import zombie.inventory.InventoryItem;
import zombie.inventory.ItemContainer;
import zombie.inventory.types.DrainableComboItem;
import zombie.iso.objects.IsoThumpable;
import zombie.scripting.ScriptManager;
import zombie.scripting.objects.Item;

public final class MultiStageBuilding {
   public static final ArrayList stages = new ArrayList();

   public static ArrayList getStages(IsoGameCharacter var0, IsoObject var1, boolean var2) {
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < stages.size(); ++var4) {
         MultiStageBuilding.Stage var5 = (MultiStageBuilding.Stage)stages.get(var4);
         if (var5.canBeDone(var0, var1, var2) && !var3.contains(var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public static void addStage(MultiStageBuilding.Stage var0) {
      for(int var1 = 0; var1 < stages.size(); ++var1) {
         if (((MultiStageBuilding.Stage)stages.get(var1)).ID.equals(var0.ID)) {
            return;
         }
      }

      stages.add(var0);
   }

   public class Stage {
      public String name;
      public ArrayList previousStage = new ArrayList();
      public String recipeName;
      public String sprite;
      public String northSprite;
      public int timeNeeded;
      public int bonusHealth;
      public HashMap xp = new HashMap();
      public HashMap perks = new HashMap();
      public HashMap items = new HashMap();
      public ArrayList itemsToKeep = new ArrayList();
      public String knownRecipe;
      public String thumpSound = "ZombieThumpGeneric";
      public String wallType;
      public boolean canBePlastered;
      public String craftingSound;
      public String ID;
      public boolean canBarricade = false;

      public String getName() {
         return this.name;
      }

      public String getDisplayName() {
         return Translator.getMultiStageBuild(this.recipeName);
      }

      public String getSprite() {
         return this.sprite;
      }

      public String getThumpSound() {
         return this.thumpSound;
      }

      public String getRecipeName() {
         return this.recipeName;
      }

      public String getKnownRecipe() {
         return this.knownRecipe;
      }

      public int getTimeNeeded(IsoGameCharacter var1) {
         int var2 = this.timeNeeded;

         Entry var4;
         for(Iterator var3 = this.xp.entrySet().iterator(); var3.hasNext(); var2 -= var1.getPerkLevel(PerkFactory.Perks.FromString((String)var4.getKey())) * 10) {
            var4 = (Entry)var3.next();
         }

         return var2;
      }

      public ArrayList getItemsToKeep() {
         return this.itemsToKeep;
      }

      public ArrayList getPreviousStages() {
         return this.previousStage;
      }

      public String getCraftingSound() {
         return this.craftingSound;
      }

      public KahluaTable getItemsLua() {
         KahluaTable var1 = LuaManager.platform.newTable();
         Iterator var2 = this.items.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.rawset(var3.getKey(), ((Integer)var3.getValue()).toString());
         }

         return var1;
      }

      public KahluaTable getPerksLua() {
         KahluaTable var1 = LuaManager.platform.newTable();
         Iterator var2 = this.perks.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.rawset(PerkFactory.Perks.FromString((String)var3.getKey()), ((Integer)var3.getValue()).toString());
         }

         return var1;
      }

      public void doStage(IsoGameCharacter var1, IsoThumpable var2, boolean var3) {
         int var4 = var2.getHealth();
         int var5 = var2.getMaxHealth();
         String var6 = this.sprite;
         if (var2.north) {
            var6 = this.northSprite;
         }

         IsoThumpable var7 = new IsoThumpable(IsoWorld.instance.getCell(), var2.square, var6, var2.north, var2.getTable());
         var7.setCanBePlastered(this.canBePlastered);
         if ("doorframe".equals(this.wallType)) {
            var7.setIsDoorFrame(true);
            var7.setCanPassThrough(true);
         }

         int var8 = this.bonusHealth;
         switch(SandboxOptions.instance.ConstructionBonusPoints.getValue()) {
         case 1:
            var8 = (int)((double)var8 * 0.5D);
            break;
         case 2:
            var8 = (int)((double)var8 * 0.7D);
         case 3:
         default:
            break;
         case 4:
            var8 = (int)((double)var8 * 1.3D);
            break;
         case 5:
            var8 = (int)((double)var8 * 1.5D);
         }

         Iterator var9 = this.perks.keySet().iterator();
         byte var11 = 40;
         switch(SandboxOptions.instance.ConstructionBonusPoints.getValue()) {
         case 1:
            var11 = 5;
            break;
         case 2:
            var11 = 20;
         case 3:
         default:
            break;
         case 4:
            var11 = 55;
            break;
         case 5:
            var11 = 70;
         }

         while(var9.hasNext()) {
            String var10 = (String)var9.next();
            var8 += (var1.getPerkLevel(PerkFactory.Perks.FromString(var10)) - (Integer)this.perks.get(var10)) * var11;
         }

         var7.setMaxHealth(var5 + var8);
         var7.setHealth(var4 + var8);
         var7.setName(this.name);
         var7.setThumpSound(this.getThumpSound());
         var7.setCanBarricade(this.canBarricade);
         var7.setModData(var2.getModData());
         if (this.wallType != null) {
            var7.getModData().rawset("wallType", this.wallType);
         }

         if (var3) {
            ItemContainer var12 = var1.getInventory();
            Iterator var13 = this.items.keySet().iterator();

            label88:
            while(true) {
               int var15;
               InventoryItem var22;
               do {
                  while(true) {
                     String var14;
                     Item var16;
                     do {
                        if (!var13.hasNext()) {
                           break label88;
                        }

                        var14 = (String)var13.next();
                        var15 = (Integer)this.items.get(var14);
                        var16 = ScriptManager.instance.getItem(var14);
                     } while(var16 == null);

                     if (var16.getType() == Item.Type.Drainable) {
                        var22 = var12.getFirstRecurse((var2x) -> {
                           return var2x.getFullType().equals(var16.getFullName()) && ((DrainableComboItem)var2x).getDrainableUsesInt() >= var15;
                        });
                        break;
                     }

                     for(int var17 = 0; var17 < var15; ++var17) {
                        InventoryItem var18 = var12.getFirstTypeRecurse(var14);
                        if (var18 != null) {
                           var18.Use();
                        }
                     }
                  }
               } while(var22 == null);

               for(int var23 = 0; var23 < var15; ++var23) {
                  var22.Use();
               }
            }
         }

         Iterator var19 = this.xp.keySet().iterator();

         while(var19.hasNext()) {
            String var20 = (String)var19.next();
            var1.getXp().AddXP(PerkFactory.Perks.FromString(var20), (float)(Integer)this.xp.get(var20));
         }

         int var21 = var2.getSquare().transmitRemoveItemFromSquare(var2);
         var7.getSquare().AddSpecialObject(var7, var21);
         var7.getSquare().RecalcAllWithNeighbours(true);
         var7.transmitCompleteItemToServer();
      }

      public boolean canBeDone(IsoGameCharacter var1, IsoObject var2, boolean var3) {
         ItemContainer var4 = var1.getInventory();
         boolean var5 = false;

         for(int var6 = 0; var6 < this.previousStage.size(); ++var6) {
            if (((String)this.previousStage.get(var6)).equalsIgnoreCase(var2.getName())) {
               var5 = true;
               break;
            }
         }

         return var5;
      }

      public void Load(String var1, String[] var2) {
         this.recipeName = var1;

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (!var2[var3].trim().isEmpty() && var2[var3].contains(":")) {
               String[] var4 = var2[var3].split(":");
               String var5 = var4[0].trim();
               String var6 = var4[1].trim();
               if (var5.equalsIgnoreCase("Name")) {
                  this.name = var6.trim();
               }

               if (var5.equalsIgnoreCase("TimeNeeded")) {
                  this.timeNeeded = Integer.parseInt(var6.trim());
               }

               if (var5.equalsIgnoreCase("BonusHealth")) {
                  this.bonusHealth = Integer.parseInt(var6.trim());
               }

               if (var5.equalsIgnoreCase("Sprite")) {
                  this.sprite = var6.trim();
               }

               if (var5.equalsIgnoreCase("NorthSprite")) {
                  this.northSprite = var6.trim();
               }

               if (var5.equalsIgnoreCase("KnownRecipe")) {
                  this.knownRecipe = var6.trim();
               }

               if (var5.equalsIgnoreCase("ThumpSound")) {
                  this.thumpSound = var6.trim();
               }

               if (var5.equalsIgnoreCase("WallType")) {
                  this.wallType = var6.trim();
               }

               if (var5.equalsIgnoreCase("CraftingSound")) {
                  this.craftingSound = var6.trim();
               }

               if (var5.equalsIgnoreCase("ID")) {
                  this.ID = var6.trim();
               }

               if (var5.equalsIgnoreCase("CanBePlastered")) {
                  this.canBePlastered = Boolean.parseBoolean(var6.trim());
               }

               if (var5.equalsIgnoreCase("CanBarricade")) {
                  this.canBarricade = Boolean.parseBoolean(var6.trim());
               }

               String[] var7;
               int var8;
               String[] var9;
               if (var5.equalsIgnoreCase("XP")) {
                  var7 = var6.split(";");

                  for(var8 = 0; var8 < var7.length; ++var8) {
                     var9 = var7[var8].split("=");
                     this.xp.put(var9[0], Integer.parseInt(var9[1]));
                  }
               }

               if (var5.equalsIgnoreCase("PreviousStage")) {
                  var7 = var6.split(";");

                  for(var8 = 0; var8 < var7.length; ++var8) {
                     this.previousStage.add(var7[var8]);
                  }
               }

               if (var5.equalsIgnoreCase("SkillRequired")) {
                  var7 = var6.split(";");

                  for(var8 = 0; var8 < var7.length; ++var8) {
                     var9 = var7[var8].split("=");
                     this.perks.put(var9[0], Integer.parseInt(var9[1]));
                  }
               }

               if (var5.equalsIgnoreCase("ItemsRequired")) {
                  var7 = var6.split(";");

                  for(var8 = 0; var8 < var7.length; ++var8) {
                     var9 = var7[var8].split("=");
                     this.items.put(var9[0], Integer.parseInt(var9[1]));
                  }
               }

               if (var5.equalsIgnoreCase("ItemsToKeep")) {
                  var7 = var6.split(";");

                  for(var8 = 0; var8 < var7.length; ++var8) {
                     this.itemsToKeep.add(var7[var8]);
                  }
               }
            }
         }

      }
   }
}
