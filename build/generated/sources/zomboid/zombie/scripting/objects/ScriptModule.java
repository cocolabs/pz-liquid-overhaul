package zombie.scripting.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import zombie.core.logger.ExceptionLogger;
import zombie.core.skinnedmodel.runtime.RuntimeAnimationScript;
import zombie.debug.DebugLog;
import zombie.iso.MultiStageBuilding;
import zombie.scripting.IScriptObjectStore;
import zombie.scripting.ScriptManager;
import zombie.scripting.ScriptParser;

public final class ScriptModule extends BaseScriptObject implements IScriptObjectStore {
   public String name;
   public String value;
   public final HashMap ItemMap = new HashMap();
   public final HashMap GameSoundMap = new HashMap();
   public final ArrayList GameSoundList = new ArrayList();
   public final TreeMap ModelScriptMap;
   public final HashMap RuntimeAnimationScriptMap;
   public final HashMap VehicleMap;
   public final HashMap VehicleTemplateMap;
   public final ArrayList RecipeMap;
   public final HashMap RecipeByName;
   public final HashMap RecipesWithDotInName;
   public final ArrayList EvolvedRecipeMap;
   public final ArrayList UniqueRecipeMap;
   public final HashMap FixingMap;
   public final ArrayList Imports;
   public boolean disabled;

   public ScriptModule() {
      this.ModelScriptMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      this.RuntimeAnimationScriptMap = new HashMap();
      this.VehicleMap = new HashMap();
      this.VehicleTemplateMap = new HashMap();
      this.RecipeMap = new ArrayList();
      this.RecipeByName = new HashMap();
      this.RecipesWithDotInName = new HashMap();
      this.EvolvedRecipeMap = new ArrayList();
      this.UniqueRecipeMap = new ArrayList();
      this.FixingMap = new HashMap();
      this.Imports = new ArrayList();
      this.disabled = false;
   }

   public void Load(String var1, String var2) {
      this.name = var1;
      this.value = var2.trim();
      ScriptManager.instance.CurrentLoadingModule = this;
      this.ParseScriptPP(this.value);
      this.ParseScript(this.value);
      this.value = "";
   }

   private void CreateFromTokenPP(String var1) {
      var1 = var1.trim();
      String[] var2;
      String var3;
      if (var1.indexOf("item") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("item", "");
         var3 = var3.trim();
         Item var4 = new Item();
         this.ItemMap.put(var3, var4);
      } else if (var1.indexOf("model") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("model", "");
         var3 = var3.trim();
         ModelScript var8;
         if (this.ModelScriptMap.containsKey(var3)) {
            var8 = (ModelScript)this.ModelScriptMap.get(var3);
            var8.reset();
         } else {
            var8 = new ModelScript();
            this.ModelScriptMap.put(var3, var8);
         }
      } else if (var1.indexOf("sound") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("sound", "");
         var3 = var3.trim();
         GameSoundScript var9;
         if (this.GameSoundMap.containsKey(var3)) {
            var9 = (GameSoundScript)this.GameSoundMap.get(var3);
            var9.reset();
         } else {
            var9 = new GameSoundScript();
            this.GameSoundMap.put(var3, var9);
            this.GameSoundList.add(var9);
         }
      } else if (var1.indexOf("vehicle") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("vehicle", "");
         var3 = var3.trim();
         VehicleScript var10 = new VehicleScript();
         this.VehicleMap.put(var3, var10);
      } else if (var1.indexOf("template") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("template", "");
         String[] var11 = var3.trim().split("\\s+");
         if (var11.length == 2) {
            String var5 = var11[0].trim();
            String var6 = var11[1].trim();
            if ("vehicle".equals(var5)) {
               VehicleTemplate var7 = new VehicleTemplate(this, var6, var1);
               var7.module = this;
               this.VehicleTemplateMap.put(var6, var7);
            }
         }
      } else if (var1.indexOf("animation") == 0) {
         var2 = var1.split("[{}]");
         var3 = var2[0];
         var3 = var3.replace("animation", "");
         var3 = var3.trim();
         RuntimeAnimationScript var12;
         if (this.RuntimeAnimationScriptMap.containsKey(var3)) {
            var12 = (RuntimeAnimationScript)this.RuntimeAnimationScriptMap.get(var3);
            var12.reset();
         } else {
            var12 = new RuntimeAnimationScript();
            this.RuntimeAnimationScriptMap.put(var3, var12);
         }
      }

   }

   private void CreateFromToken(String var1) {
      var1 = var1.trim();
      String[] var2;
      if (var1.indexOf("imports") == 0) {
         var2 = var1.split("[{}]");
         String[] var3 = var2[1].split(",");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var3[var4].trim().length() > 0) {
               String var5 = var3[var4].trim();
               if (var5.equals(this.getName())) {
                  DebugLog.log("ERROR: module \"" + this.getName() + "\" imports itself");
               } else {
                  this.Imports.add(var5);
               }
            }
         }
      } else {
         String var13;
         String[] var14;
         if (var1.indexOf("item") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("item", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            Item var15 = (Item)this.ItemMap.get(var13);
            var15.module = this;

            try {
               var15.Load(var13, var14);
            } catch (Exception var12) {
               DebugLog.log((Object)var12);
            }
         } else if (var1.indexOf("recipe") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("recipe", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            Recipe var16 = new Recipe();
            this.RecipeMap.add(var16);
            if (!this.RecipeByName.containsKey(var13)) {
               this.RecipeByName.put(var13, var16);
            }

            if (var13.contains(".")) {
               this.RecipesWithDotInName.put(var13, var16);
            }

            var16.module = this;
            var16.Load(var13, var14);
         } else if (var1.indexOf("uniquerecipe") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("uniquerecipe", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            UniqueRecipe var17 = new UniqueRecipe(var13);
            this.UniqueRecipeMap.add(var17);
            var17.module = this;
            var17.Load(var13, var14);
         } else if (var1.indexOf("evolvedrecipe") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("evolvedrecipe", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            boolean var18 = false;
            Iterator var6 = this.EvolvedRecipeMap.iterator();

            while(var6.hasNext()) {
               EvolvedRecipe var7 = (EvolvedRecipe)var6.next();
               if (var7.name.equals(var13)) {
                  var7.Load(var13, var14);
                  var7.module = this;
                  var18 = true;
               }
            }

            if (!var18) {
               EvolvedRecipe var19 = new EvolvedRecipe(var13);
               this.EvolvedRecipeMap.add(var19);
               var19.module = this;
               var19.Load(var13, var14);
            }
         } else if (var1.indexOf("fixing") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("fixing", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            Fixing var21 = new Fixing();
            var21.module = this;
            this.FixingMap.put(var13, var21);
            var21.Load(var13, var14);
         } else if (var1.indexOf("multistagebuild") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("multistagebuild", "");
            var13 = var13.trim();
            var14 = var2[1].split(",");
            MultiStageBuilding.Stage var23 = new MultiStageBuilding().new Stage();
            var23.Load(var13, var14);
            MultiStageBuilding.addStage(var23);
         } else if (var1.indexOf("model") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("model", "");
            var13 = var13.trim();
            ModelScript var20 = (ModelScript)this.ModelScriptMap.get(var13);
            var20.module = this;

            try {
               var20.Load(var13, var1);
            } catch (Throwable var11) {
               ExceptionLogger.logException(var11);
            }
         } else if (var1.indexOf("sound") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("sound", "");
            var13 = var13.trim();
            GameSoundScript var22 = (GameSoundScript)this.GameSoundMap.get(var13);
            var22.module = this;

            try {
               var22.Load(var13, var1);
            } catch (Throwable var10) {
               ExceptionLogger.logException(var10);
            }
         } else if (var1.indexOf("vehicle") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("vehicle", "");
            var13 = var13.trim();
            VehicleScript var24 = (VehicleScript)this.VehicleMap.get(var13);
            var24.module = this;

            try {
               var24.Load(var13, var1);
               var24.Loaded();
            } catch (Exception var9) {
               ExceptionLogger.logException(var9);
            }
         } else if (var1.indexOf("animation") == 0) {
            var2 = var1.split("[{}]");
            var13 = var2[0];
            var13 = var13.replace("animation", "");
            var13 = var13.trim();
            RuntimeAnimationScript var25 = (RuntimeAnimationScript)this.RuntimeAnimationScriptMap.get(var13);
            var25.module = this;

            try {
               var25.Load(var13, var1);
            } catch (Throwable var8) {
               ExceptionLogger.logException(var8);
            }
         }
      }

   }

   public void ParseScript(String var1) {
      ArrayList var2 = ScriptParser.parseTokens(var1);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.get(var3);
         this.CreateFromToken(var4);
      }

   }

   public void ParseScriptPP(String var1) {
      ArrayList var2 = ScriptParser.parseTokens(var1);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.get(var3);
         this.CreateFromTokenPP(var4);
      }

   }

   public Item getItem(String var1) {
      if (var1.contains(".")) {
         return ScriptManager.instance.getItem(var1);
      } else if (!this.ItemMap.containsKey(var1)) {
         for(int var2 = 0; var2 < this.Imports.size(); ++var2) {
            String var3 = (String)this.Imports.get(var2);
            ScriptModule var4 = ScriptManager.instance.getModule(var3);
            Item var5 = var4.getItem(var1);
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      } else {
         return (Item)this.ItemMap.get(var1);
      }
   }

   public Recipe getRecipe(String var1) {
      if (var1.contains(".") && !this.RecipesWithDotInName.containsKey(var1)) {
         return ScriptManager.instance.getRecipe(var1);
      } else {
         Recipe var2 = (Recipe)this.RecipeByName.get(var1);
         if (var2 != null) {
            return var2;
         } else {
            for(int var3 = 0; var3 < this.Imports.size(); ++var3) {
               ScriptModule var4 = ScriptManager.instance.getModule((String)this.Imports.get(var3));
               if (var4 != null) {
                  var2 = var4.getRecipe(var1);
                  if (var2 != null) {
                     return var2;
                  }
               }
            }

            return null;
         }
      }
   }

   public VehicleScript getVehicle(String var1) {
      if (var1.contains(".")) {
         return ScriptManager.instance.getVehicle(var1);
      } else if (!this.VehicleMap.containsKey(var1)) {
         for(int var2 = 0; var2 < this.Imports.size(); ++var2) {
            VehicleScript var3 = ScriptManager.instance.getModule((String)this.Imports.get(var2)).getVehicle(var1);
            if (var3 != null) {
               return var3;
            }
         }

         return null;
      } else {
         return (VehicleScript)this.VehicleMap.get(var1);
      }
   }

   public VehicleTemplate getVehicleTemplate(String var1) {
      if (var1.contains(".")) {
         return ScriptManager.instance.getVehicleTemplate(var1);
      } else if (!this.VehicleTemplateMap.containsKey(var1)) {
         for(int var2 = 0; var2 < this.Imports.size(); ++var2) {
            VehicleTemplate var3 = ScriptManager.instance.getModule((String)this.Imports.get(var2)).getVehicleTemplate(var1);
            if (var3 != null) {
               return var3;
            }
         }

         return null;
      } else {
         return (VehicleTemplate)this.VehicleTemplateMap.get(var1);
      }
   }

   public boolean CheckExitPoints() {
      return false;
   }

   public String getName() {
      return this.name;
   }
}
