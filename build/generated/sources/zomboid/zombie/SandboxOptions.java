package zombie;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import se.krka.kahlua.vm.KahluaTable;
import se.krka.kahlua.vm.KahluaTableIterator;
import zombie.Lua.LuaManager;
import zombie.config.BooleanConfigOption;
import zombie.config.ConfigFile;
import zombie.config.ConfigOption;
import zombie.config.DoubleConfigOption;
import zombie.config.EnumConfigOption;
import zombie.config.IntegerConfigOption;
import zombie.core.Core;
import zombie.core.Rand;
import zombie.core.Translator;
import zombie.core.logger.ExceptionLogger;
import zombie.debug.DebugLog;
import zombie.iso.SliceY;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.network.ServerSettingsManager;

public final class SandboxOptions {
   public static final SandboxOptions instance = new SandboxOptions();
   public int Speed = 3;
   public final SandboxOptions.EnumSandboxOption Zombies = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Zombies", 6, 4).setTranslation("ZombieCount");
   public final SandboxOptions.EnumSandboxOption Distribution = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Distribution", 2, 1).setTranslation("ZombieDistribution");
   public final SandboxOptions.EnumSandboxOption DayLength = this.newEnumOption("DayLength", 26, 2);
   public final SandboxOptions.EnumSandboxOption StartYear = this.newEnumOption("StartYear", 100, 1);
   public final SandboxOptions.EnumSandboxOption StartMonth = this.newEnumOption("StartMonth", 12, 7);
   public final SandboxOptions.EnumSandboxOption StartDay = this.newEnumOption("StartDay", 31, 23);
   public final SandboxOptions.EnumSandboxOption StartTime = this.newEnumOption("StartTime", 9, 2);
   public final SandboxOptions.EnumSandboxOption WaterShut = this.newEnumOption("WaterShut", 8, 2).setValueTranslation("Shutoff");
   public final SandboxOptions.EnumSandboxOption ElecShut = this.newEnumOption("ElecShut", 8, 2).setValueTranslation("Shutoff");
   public final SandboxOptions.IntegerSandboxOption WaterShutModifier = (SandboxOptions.IntegerSandboxOption)this.newIntegerOption("WaterShutModifier", -1, Integer.MAX_VALUE, 14).setTranslation("WaterShut");
   public final SandboxOptions.IntegerSandboxOption ElecShutModifier = (SandboxOptions.IntegerSandboxOption)this.newIntegerOption("ElecShutModifier", -1, Integer.MAX_VALUE, 14).setTranslation("ElecShut");
   public final SandboxOptions.EnumSandboxOption FoodLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("FoodLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootFood");
   public final SandboxOptions.EnumSandboxOption LiteratureLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("LiteratureLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootLiterature");
   public final SandboxOptions.EnumSandboxOption MedicalLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("MedicalLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootMedical");
   public final SandboxOptions.EnumSandboxOption SurvivalGearsLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("SurvivalGearsLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootSurvivalGears");
   public final SandboxOptions.EnumSandboxOption CannedFoodLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("CannedFoodLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootCannedFood");
   public final SandboxOptions.EnumSandboxOption WeaponLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("WeaponLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootWeapon");
   public final SandboxOptions.EnumSandboxOption RangedWeaponLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("RangedWeaponLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootRangedWeapon");
   public final SandboxOptions.EnumSandboxOption AmmoLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("AmmoLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootAmmo");
   public final SandboxOptions.EnumSandboxOption MechanicsLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("MechanicsLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootMechanics");
   public final SandboxOptions.EnumSandboxOption OtherLoot = (SandboxOptions.EnumSandboxOption)this.newEnumOption("OtherLoot", 5, 2).setValueTranslation("Rarity").setTranslation("LootOther");
   public final SandboxOptions.EnumSandboxOption Temperature = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Temperature", 5, 3).setTranslation("WorldTemperature");
   public final SandboxOptions.EnumSandboxOption Rain = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Rain", 5, 3).setTranslation("RainAmount");
   public final SandboxOptions.EnumSandboxOption ErosionSpeed = this.newEnumOption("ErosionSpeed", 5, 3);
   public final SandboxOptions.IntegerSandboxOption ErosionDays = this.newIntegerOption("ErosionDays", -1, 36500, 0);
   public final SandboxOptions.DoubleSandboxOption XpMultiplier = this.newDoubleOption("XpMultiplier", 0.001D, 1000.0D, 1.0D);
   public final SandboxOptions.EnumSandboxOption Farming = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Farming", 5, 3).setTranslation("FarmingSpeed");
   public final SandboxOptions.EnumSandboxOption CompostTime = this.newEnumOption("CompostTime", 8, 2);
   public final SandboxOptions.EnumSandboxOption StatsDecrease = (SandboxOptions.EnumSandboxOption)this.newEnumOption("StatsDecrease", 5, 3).setTranslation("StatDecrease");
   public final SandboxOptions.EnumSandboxOption NatureAbundance = (SandboxOptions.EnumSandboxOption)this.newEnumOption("NatureAbundance", 5, 3).setTranslation("NatureAmount");
   public final SandboxOptions.EnumSandboxOption Alarm = (SandboxOptions.EnumSandboxOption)this.newEnumOption("Alarm", 6, 4).setTranslation("HouseAlarmFrequency");
   public final SandboxOptions.EnumSandboxOption LockedHouses = (SandboxOptions.EnumSandboxOption)this.newEnumOption("LockedHouses", 6, 4).setTranslation("LockedHouseFrequency");
   public final SandboxOptions.BooleanSandboxOption StarterKit = this.newBooleanOption("StarterKit", false);
   public final SandboxOptions.BooleanSandboxOption Nutrition = this.newBooleanOption("Nutrition", false);
   public final SandboxOptions.EnumSandboxOption FoodRotSpeed = (SandboxOptions.EnumSandboxOption)this.newEnumOption("FoodRotSpeed", 5, 3).setTranslation("FoodSpoil");
   public final SandboxOptions.EnumSandboxOption FridgeFactor = (SandboxOptions.EnumSandboxOption)this.newEnumOption("FridgeFactor", 5, 3).setTranslation("FridgeEffect");
   public final SandboxOptions.EnumSandboxOption LootRespawn = this.newEnumOption("LootRespawn", 5, 1).setValueTranslation("Respawn");
   public final SandboxOptions.IntegerSandboxOption SeenHoursPreventLootRespawn = this.newIntegerOption("SeenHoursPreventLootRespawn", 0, Integer.MAX_VALUE, 0);
   public final SandboxOptions.EnumSandboxOption TimeSinceApo = this.newEnumOption("TimeSinceApo", 13, 1);
   public final SandboxOptions.EnumSandboxOption PlantResilience = this.newEnumOption("PlantResilience", 5, 3);
   public final SandboxOptions.EnumSandboxOption PlantAbundance = this.newEnumOption("PlantAbundance", 5, 3).setValueTranslation("NatureAmount");
   public final SandboxOptions.EnumSandboxOption EndRegen = (SandboxOptions.EnumSandboxOption)this.newEnumOption("EndRegen", 5, 3).setTranslation("EnduranceRegen");
   public final SandboxOptions.EnumSandboxOption Helicopter = this.newEnumOption("Helicopter", 4, 2).setValueTranslation("HelicopterFreq");
   public final SandboxOptions.EnumSandboxOption MetaEvent = this.newEnumOption("MetaEvent", 3, 2).setValueTranslation("MetaEventFreq");
   public final SandboxOptions.EnumSandboxOption SleepingEvent = this.newEnumOption("SleepingEvent", 3, 1).setValueTranslation("MetaEventFreq");
   public final SandboxOptions.DoubleSandboxOption GeneratorFuelConsumption = this.newDoubleOption("GeneratorFuelConsumption", 0.0D, 100.0D, 1.0D);
   public final SandboxOptions.EnumSandboxOption GeneratorSpawning = this.newEnumOption("GeneratorSpawning", 5, 3);
   public final SandboxOptions.EnumSandboxOption SurvivorHouseChance = this.newEnumOption("SurvivorHouseChance", 6, 3);
   public final SandboxOptions.EnumSandboxOption AnnotatedMapChance = this.newEnumOption("AnnotatedMapChance", 6, 4);
   public final SandboxOptions.IntegerSandboxOption CharacterFreePoints = this.newIntegerOption("CharacterFreePoints", -100, 100, 0);
   public final SandboxOptions.EnumSandboxOption ConstructionBonusPoints = this.newEnumOption("ConstructionBonusPoints", 5, 3);
   public final SandboxOptions.EnumSandboxOption NightDarkness = this.newEnumOption("NightDarkness", 4, 3);
   public final SandboxOptions.BooleanSandboxOption BoneFracture = this.newBooleanOption("BoneFracture", true);
   public final SandboxOptions.EnumSandboxOption InjurySeverity = this.newEnumOption("InjurySeverity", 3, 2);
   public final SandboxOptions.IntegerSandboxOption HoursForCorpseRemoval = this.newIntegerOption("HoursForCorpseRemoval", -1, Integer.MAX_VALUE, -1);
   public final SandboxOptions.EnumSandboxOption DecayingCorpseHealthImpact = this.newEnumOption("DecayingCorpseHealthImpact", 4, 3);
   public final SandboxOptions.EnumSandboxOption BloodLevel = this.newEnumOption("BloodLevel", 5, 3);
   public final SandboxOptions.EnumSandboxOption ClothingDegradation = this.newEnumOption("ClothingDegradation", 4, 3);
   public final SandboxOptions.BooleanSandboxOption FireSpread = this.newBooleanOption("FireSpread", true);
   public final SandboxOptions.IntegerSandboxOption DaysForRottenFoodRemoval = this.newIntegerOption("DaysForRottenFoodRemoval", -1, Integer.MAX_VALUE, -1);
   public final SandboxOptions.BooleanSandboxOption AllowExteriorGenerator = this.newBooleanOption("AllowExteriorGenerator", true);
   public final SandboxOptions.EnumSandboxOption MaxFogIntensity = this.newEnumOption("MaxFogIntensity", 3, 1);
   public final SandboxOptions.EnumSandboxOption MaxRainFxIntensity = this.newEnumOption("MaxRainFxIntensity", 3, 1);
   public final SandboxOptions.BooleanSandboxOption EnableSnowOnGround = this.newBooleanOption("EnableSnowOnGround", true);
   public final SandboxOptions.BooleanSandboxOption AttackBlockMovements = this.newBooleanOption("AttackBlockMovements", true);
   public final SandboxOptions.EnumSandboxOption VehicleStoryChance = this.newEnumOption("VehicleStoryChance", 6, 3).setValueTranslation("SurvivorHouseChance");
   public final SandboxOptions.EnumSandboxOption ZoneStoryChance = this.newEnumOption("ZoneStoryChance", 6, 3).setValueTranslation("SurvivorHouseChance");
   public final SandboxOptions.BooleanSandboxOption EnableVehicles = this.newBooleanOption("EnableVehicles", true);
   public final SandboxOptions.EnumSandboxOption CarSpawnRate = this.newEnumOption("CarSpawnRate", 5, 4);
   public final SandboxOptions.DoubleSandboxOption ZombieAttractionMultiplier = this.newDoubleOption("ZombieAttractionMultiplier", 0.0D, 100.0D, 1.0D);
   public final SandboxOptions.BooleanSandboxOption VehicleEasyUse = this.newBooleanOption("VehicleEasyUse", false);
   public final SandboxOptions.EnumSandboxOption InitialGas = this.newEnumOption("InitialGas", 6, 3);
   public final SandboxOptions.EnumSandboxOption LockedCar = this.newEnumOption("LockedCar", 6, 4);
   public final SandboxOptions.DoubleSandboxOption CarGasConsumption = this.newDoubleOption("CarGasConsumption", 0.0D, 100.0D, 1.0D);
   public final SandboxOptions.EnumSandboxOption CarGeneralCondition = this.newEnumOption("CarGeneralCondition", 5, 3);
   public final SandboxOptions.EnumSandboxOption CarDamageOnImpact = this.newEnumOption("CarDamageOnImpact", 5, 3);
   public final SandboxOptions.EnumSandboxOption DamageToPlayerFromHitByACar = this.newEnumOption("DamageToPlayerFromHitByACar", 5, 1);
   public final SandboxOptions.BooleanSandboxOption TrafficJam = this.newBooleanOption("TrafficJam", true);
   public final SandboxOptions.EnumSandboxOption CarAlarm = (SandboxOptions.EnumSandboxOption)this.newEnumOption("CarAlarm", 6, 4).setTranslation("CarAlarmFrequency");
   public final SandboxOptions.BooleanSandboxOption PlayerDamageFromCrash = this.newBooleanOption("PlayerDamageFromCrash", true);
   public final SandboxOptions.DoubleSandboxOption SirenShutoffHours = this.newDoubleOption("SirenShutoffHours", 0.0D, 168.0D, 0.0D);
   public final SandboxOptions.EnumSandboxOption ChanceHasGas = this.newEnumOption("ChanceHasGas", 3, 2);
   public final SandboxOptions.EnumSandboxOption RecentlySurvivorVehicles = this.newEnumOption("RecentlySurvivorVehicles", 3, 2);
   public final SandboxOptions.BooleanSandboxOption MultiHitZombies = this.newBooleanOption("MultiHitZombies", false);
   public final SandboxOptions.EnumSandboxOption RearVulnerability = this.newEnumOption("RearVulnerability", 3, 3);
   protected final ArrayList options = new ArrayList();
   protected final HashMap optionByName = new HashMap();
   public final SandboxOptions.ZombieLore Lore = new SandboxOptions.ZombieLore();
   public final SandboxOptions.ZombieConfig zombieConfig = new SandboxOptions.ZombieConfig();
   public final int FIRST_YEAR = 1993;
   private final int SANDBOX_VERSION = 4;

   public SandboxOptions() {
      this.loadGameFile("Apocalypse");
      this.setDefaultsToCurrentValues();
   }

   public static SandboxOptions getInstance() {
      return instance;
   }

   public void toLua() {
      KahluaTable var1 = (KahluaTable)LuaManager.env.rawget("SandboxVars");

      for(int var2 = 0; var2 < this.options.size(); ++var2) {
         ((SandboxOptions.SandboxOption)this.options.get(var2)).toTable(var1);
      }

   }

   public void updateFromLua() {
      if (Core.GameMode.equals("LastStand")) {
         GameTime.instance.multiplierBias = 1.2F;
      }

      KahluaTable var1 = (KahluaTable)LuaManager.env.rawget("SandboxVars");

      for(int var2 = 0; var2 < this.options.size(); ++var2) {
         ((SandboxOptions.SandboxOption)this.options.get(var2)).fromTable(var1);
      }

      switch(this.Speed) {
      case 1:
         GameTime.instance.multiplierBias = 0.8F;
         break;
      case 2:
         GameTime.instance.multiplierBias = 0.9F;
         break;
      case 3:
         GameTime.instance.multiplierBias = 1.0F;
         break;
      case 4:
         GameTime.instance.multiplierBias = 1.1F;
         break;
      case 5:
         GameTime.instance.multiplierBias = 1.2F;
      }

      if (this.Zombies.getValue() == 1) {
         VirtualZombieManager.instance.MaxRealZombies = 400;
      }

      if (this.Zombies.getValue() == 2) {
         VirtualZombieManager.instance.MaxRealZombies = 350;
      }

      if (this.Zombies.getValue() == 3) {
         VirtualZombieManager.instance.MaxRealZombies = 300;
      }

      if (this.Zombies.getValue() == 4) {
         VirtualZombieManager.instance.MaxRealZombies = 200;
      }

      if (this.Zombies.getValue() == 5) {
         VirtualZombieManager.instance.MaxRealZombies = 100;
      }

      if (this.Zombies.getValue() == 6) {
         VirtualZombieManager.instance.MaxRealZombies = 0;
      }

      VirtualZombieManager.instance.MaxRealZombies = 1;
      this.applySettings();
   }

   public int randomWaterShut(int var1) {
      switch(var1) {
      case 2:
         return Rand.Next(0, 30);
      case 3:
         return Rand.Next(0, 60);
      case 4:
         return Rand.Next(0, 180);
      case 5:
         return Rand.Next(0, 360);
      case 6:
         return Rand.Next(0, 1800);
      case 7:
         return Rand.Next(60, 180);
      case 8:
         return Rand.Next(180, 360);
      default:
         return -1;
      }
   }

   public int randomElectricityShut(int var1) {
      switch(var1) {
      case 2:
         return Rand.Next(14, 30);
      case 3:
         return Rand.Next(14, 60);
      case 4:
         return Rand.Next(14, 180);
      case 5:
         return Rand.Next(14, 360);
      case 6:
         return Rand.Next(14, 1800);
      case 7:
         return Rand.Next(60, 180);
      case 8:
         return Rand.Next(180, 360);
      default:
         return -1;
      }
   }

   public int getTemperatureModifier() {
      return this.Temperature.getValue();
   }

   public int getRainModifier() {
      return this.Rain.getValue();
   }

   public int getErosionSpeed() {
      return this.ErosionSpeed.getValue();
   }

   public int getFoodLootModifier() {
      return this.FoodLoot.getValue();
   }

   public int getWeaponLootModifier() {
      return this.WeaponLoot.getValue();
   }

   public int getOtherLootModifier() {
      return this.OtherLoot.getValue();
   }

   public int getWaterShutModifier() {
      return this.WaterShutModifier.getValue();
   }

   public int getElecShutModifier() {
      return this.ElecShutModifier.getValue();
   }

   public int getTimeSinceApo() {
      return this.TimeSinceApo.getValue();
   }

   public double getEnduranceRegenMultiplier() {
      switch(this.EndRegen.getValue()) {
      case 1:
         return 1.8D;
      case 2:
         return 1.3D;
      case 3:
      default:
         return 1.0D;
      case 4:
         return 0.7D;
      case 5:
         return 0.4D;
      }
   }

   public double getStatsDecreaseMultiplier() {
      switch(this.StatsDecrease.getValue()) {
      case 1:
         return 2.0D;
      case 2:
         return 1.6D;
      case 3:
      default:
         return 1.0D;
      case 4:
         return 0.8D;
      case 5:
         return 0.65D;
      }
   }

   public int getDayLengthMinutes() {
      switch(this.DayLength.getValue()) {
      case 1:
         return 15;
      case 2:
         return 30;
      default:
         return (this.DayLength.getValue() - 2) * 60;
      }
   }

   public int getDayLengthMinutesDefault() {
      switch(this.DayLength.getDefaultValue()) {
      case 1:
         return 15;
      case 2:
         return 30;
      default:
         return (this.DayLength.getDefaultValue() - 2) * 60;
      }
   }

   public int getCompostHours() {
      switch(this.CompostTime.getValue()) {
      case 1:
         return 168;
      case 2:
         return 336;
      case 3:
         return 504;
      case 4:
         return 672;
      case 5:
         return 1008;
      case 6:
         return 1344;
      case 7:
         return 1680;
      case 8:
         return 2016;
      default:
         return 336;
      }
   }

   public void applySettings() {
      GameTime.instance.setStartYear(this.getFirstYear() + this.StartYear.getValue() - 1);
      GameTime.instance.setStartMonth(this.StartMonth.getValue() - 1);
      GameTime.instance.setStartDay(this.StartDay.getValue() - 1);
      GameTime.instance.setMinutesPerDay((float)this.getDayLengthMinutes());
      if (this.StartTime.getValue() == 1) {
         GameTime.instance.setStartTimeOfDay(7.0F);
      } else if (this.StartTime.getValue() == 2) {
         GameTime.instance.setStartTimeOfDay(9.0F);
      } else if (this.StartTime.getValue() == 3) {
         GameTime.instance.setStartTimeOfDay(12.0F);
      } else if (this.StartTime.getValue() == 4) {
         GameTime.instance.setStartTimeOfDay(14.0F);
      } else if (this.StartTime.getValue() == 5) {
         GameTime.instance.setStartTimeOfDay(17.0F);
      } else if (this.StartTime.getValue() == 6) {
         GameTime.instance.setStartTimeOfDay(21.0F);
      } else if (this.StartTime.getValue() == 7) {
         GameTime.instance.setStartTimeOfDay(0.0F);
      } else if (this.StartTime.getValue() == 8) {
         GameTime.instance.setStartTimeOfDay(2.0F);
      } else if (this.StartTime.getValue() == 9) {
         GameTime.instance.setStartTimeOfDay(5.0F);
      }

   }

   public void save(ByteBuffer var1) throws IOException {
      var1.put((byte)83);
      var1.put((byte)65);
      var1.put((byte)78);
      var1.put((byte)68);
      var1.putInt(175);
      var1.putInt(4);
      var1.putInt(this.options.size());

      for(int var2 = 0; var2 < this.options.size(); ++var2) {
         SandboxOptions.SandboxOption var3 = (SandboxOptions.SandboxOption)this.options.get(var2);
         GameWindow.WriteStringUTF(var1, var3.asConfigOption().getName());
         GameWindow.WriteStringUTF(var1, var3.asConfigOption().getValueAsString());
      }

   }

   public void load(ByteBuffer var1) throws IOException {
      var1.mark();
      byte var3 = var1.get();
      byte var4 = var1.get();
      byte var5 = var1.get();
      byte var6 = var1.get();
      int var2;
      if (var3 == 83 && var4 == 65 && var5 == 78 && var6 == 68) {
         var2 = var1.getInt();
      } else {
         var2 = 41;
         var1.reset();
      }

      if (var2 >= 88) {
         int var7 = 2;
         if (var2 >= 131) {
            var7 = var1.getInt();
         }

         int var8 = var1.getInt();

         for(int var9 = 0; var9 < var8; ++var9) {
            String var10 = GameWindow.ReadStringUTF(var1);
            String var11 = GameWindow.ReadStringUTF(var1);
            var10 = this.upgradeOptionName(var10, var7);
            var11 = this.upgradeOptionValue(var10, var11, var7);
            SandboxOptions.SandboxOption var12 = (SandboxOptions.SandboxOption)this.optionByName.get(var10);
            if (var12 == null) {
               DebugLog.log("ERROR unknown SandboxOption \"" + var10 + "\"");
            } else {
               var12.asConfigOption().parse(var11);
            }
         }

         if (var2 < 157) {
            instance.CannedFoodLoot.setValue(instance.FoodLoot.getValue());
            instance.AmmoLoot.setValue(instance.WeaponLoot.getValue());
            instance.RangedWeaponLoot.setValue(instance.WeaponLoot.getValue());
            instance.MedicalLoot.setValue(instance.OtherLoot.getValue());
            instance.LiteratureLoot.setValue(instance.OtherLoot.getValue());
            instance.SurvivalGearsLoot.setValue(instance.OtherLoot.getValue());
            instance.MechanicsLoot.setValue(instance.OtherLoot.getValue());
         }

      }
   }

   public int getFirstYear() {
      return 1993;
   }

   private static String[] parseName(String var0) {
      String[] var1 = new String[]{null, var0};
      if (var0.contains(".")) {
         String[] var2 = var0.split("\\.");
         if (var2.length == 2) {
            var1[0] = var2[0];
            var1[1] = var2[1];
         }
      }

      return var1;
   }

   private SandboxOptions.BooleanSandboxOption newBooleanOption(String var1, boolean var2) {
      return new SandboxOptions.BooleanSandboxOption(this, var1, var2);
   }

   private SandboxOptions.DoubleSandboxOption newDoubleOption(String var1, double var2, double var4, double var6) {
      return new SandboxOptions.DoubleSandboxOption(this, var1, var2, var4, var6);
   }

   private SandboxOptions.EnumSandboxOption newEnumOption(String var1, int var2, int var3) {
      return new SandboxOptions.EnumSandboxOption(this, var1, var2, var3);
   }

   private SandboxOptions.IntegerSandboxOption newIntegerOption(String var1, int var2, int var3, int var4) {
      return new SandboxOptions.IntegerSandboxOption(this, var1, var2, var3, var4);
   }

   protected SandboxOptions addOption(SandboxOptions.SandboxOption var1) {
      this.options.add(var1);
      this.optionByName.put(var1.asConfigOption().getName(), var1);
      return this;
   }

   public int getNumOptions() {
      return this.options.size();
   }

   public SandboxOptions.SandboxOption getOptionByIndex(int var1) {
      return (SandboxOptions.SandboxOption)this.options.get(var1);
   }

   public SandboxOptions.SandboxOption getOptionByName(String var1) {
      return (SandboxOptions.SandboxOption)this.optionByName.get(var1);
   }

   public void set(String var1, Object var2) {
      if (var1 != null && var2 != null) {
         SandboxOptions.SandboxOption var3 = (SandboxOptions.SandboxOption)this.optionByName.get(var1);
         if (var3 == null) {
            throw new IllegalArgumentException("unknown SandboxOption \"" + var1 + "\"");
         } else {
            var3.asConfigOption().setValueFromObject(var2);
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void copyValuesFrom(SandboxOptions var1) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         for(int var2 = 0; var2 < this.options.size(); ++var2) {
            ((SandboxOptions.SandboxOption)this.options.get(var2)).asConfigOption().setValueFromObject(((SandboxOptions.SandboxOption)var1.options.get(var2)).asConfigOption().getValueAsObject());
         }

      }
   }

   public void resetToDefault() {
      for(int var1 = 0; var1 < this.options.size(); ++var1) {
         ((SandboxOptions.SandboxOption)this.options.get(var1)).asConfigOption().resetToDefault();
      }

   }

   public void setDefaultsToCurrentValues() {
      for(int var1 = 0; var1 < this.options.size(); ++var1) {
         ((SandboxOptions.SandboxOption)this.options.get(var1)).asConfigOption().setDefaultToCurrentValue();
      }

   }

   public SandboxOptions newCopy() {
      SandboxOptions var1 = new SandboxOptions();
      var1.copyValuesFrom(this);
      return var1;
   }

   public static boolean isValidPresetName(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         return !var0.contains("/") && !var0.contains("\\") && !var0.contains(":") && !var0.contains(";") && !var0.contains("\"") && !var0.contains(".");
      } else {
         return false;
      }
   }

   private boolean readTextFile(String var1, boolean var2) {
      ConfigFile var3 = new ConfigFile();
      if (!var3.read(var1)) {
         return false;
      } else {
         int var4 = var3.getVersion();
         HashSet var5 = null;
         int var6;
         if (var2 && var4 == 1) {
            var5 = new HashSet();

            for(var6 = 0; var6 < this.options.size(); ++var6) {
               if ("ZombieLore".equals(((SandboxOptions.SandboxOption)this.options.get(var6)).getTableName())) {
                  var5.add(((SandboxOptions.SandboxOption)this.options.get(var6)).getShortName());
               }
            }
         }

         for(var6 = 0; var6 < var3.getOptions().size(); ++var6) {
            ConfigOption var7 = (ConfigOption)var3.getOptions().get(var6);
            String var8 = var7.getName();
            String var9 = var7.getValueAsString();
            if (var5 != null && var5.contains(var8)) {
               var8 = "ZombieLore." + var8;
            }

            if (var2 && var4 == 1) {
               if ("WaterShutModifier".equals(var8)) {
                  var8 = "WaterShut";
               } else if ("ElecShutModifier".equals(var8)) {
                  var8 = "ElecShut";
               }
            }

            var8 = this.upgradeOptionName(var8, var4);
            var9 = this.upgradeOptionValue(var8, var9, var4);
            SandboxOptions.SandboxOption var10 = (SandboxOptions.SandboxOption)this.optionByName.get(var8);
            if (var10 != null) {
               var10.asConfigOption().parse(var9);
            }
         }

         return true;
      }
   }

   private boolean writeTextFile(String var1, int var2) {
      ConfigFile var3 = new ConfigFile();
      ArrayList var4 = new ArrayList();
      Iterator var5 = this.options.iterator();

      while(var5.hasNext()) {
         SandboxOptions.SandboxOption var6 = (SandboxOptions.SandboxOption)var5.next();
         var4.add(var6.asConfigOption());
      }

      return var3.write(var1, var2, var4);
   }

   public boolean loadServerTextFile(String var1) {
      return this.readTextFile(ServerSettingsManager.instance.getNameInSettingsFolder(var1 + "_sandbox.ini"), false);
   }

   public boolean loadServerLuaFile(String var1) {
      boolean var2 = this.readLuaFile(ServerSettingsManager.instance.getNameInSettingsFolder(var1 + "_SandboxVars.lua"));
      if (this.Lore.Speed.getValue() == 1) {
         this.Lore.Speed.setValue(2);
      }

      return var2;
   }

   public boolean saveServerLuaFile(String var1) {
      return this.writeLuaFile(ServerSettingsManager.instance.getNameInSettingsFolder(var1 + "_SandboxVars.lua"), false);
   }

   public boolean loadPresetFile(String var1) {
      return this.readTextFile(LuaManager.getSandboxCacheDir() + File.separator + var1 + ".cfg", true);
   }

   public boolean savePresetFile(String var1) {
      return !isValidPresetName(var1) ? false : this.writeTextFile(LuaManager.getSandboxCacheDir() + File.separator + var1 + ".cfg", 4);
   }

   public boolean loadGameFile(String var1) {
      File var2 = new File("media/lua/shared/Sandbox/" + var1 + ".lua");
      if (!var2.exists()) {
         throw new RuntimeException("media/lua/shared/Sandbox/" + var1 + ".lua not found");
      } else {
         try {
            LuaManager.loaded.remove(var2.getAbsolutePath().replace("\\", "/"));
            Object var3 = LuaManager.RunLua(var2.getAbsolutePath());
            if (!(var3 instanceof KahluaTable)) {
               throw new RuntimeException(var2.getName() + " must return a SandboxVars table");
            } else {
               for(int var4 = 0; var4 < this.options.size(); ++var4) {
                  ((SandboxOptions.SandboxOption)this.options.get(var4)).fromTable((KahluaTable)var3);
               }

               return true;
            }
         } catch (Exception var5) {
            ExceptionLogger.logException(var5);
            return false;
         }
      }
   }

   public boolean saveGameFile(String var1) {
      return !Core.bDebug ? false : this.writeLuaFile("media/lua/shared/Sandbox/" + var1 + ".lua", true);
   }

   private void saveCurrentGameBinFile() {
      File var1 = ZomboidFileSystem.instance.getFileInCurrentSave("map_sand.bin");

      try {
         FileOutputStream var2 = new FileOutputStream(var1);
         Throwable var3 = null;

         try {
            BufferedOutputStream var4 = new BufferedOutputStream(var2);
            Throwable var5 = null;

            try {
               synchronized(SliceY.SliceBufferLock) {
                  SliceY.SliceBuffer.clear();
                  this.save(SliceY.SliceBuffer);
                  var4.write(SliceY.SliceBuffer.array(), 0, SliceY.SliceBuffer.position());
               }
            } catch (Throwable var34) {
               var5 = var34;
               throw var34;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var32) {
                        var5.addSuppressed(var32);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Throwable var36) {
            var3 = var36;
            throw var36;
         } finally {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var31) {
                     var3.addSuppressed(var31);
                  }
               } else {
                  var2.close();
               }
            }

         }
      } catch (Exception var38) {
         ExceptionLogger.logException(var38);
      }

   }

   public void handleOldZombiesFile1() {
      if (!GameServer.bServer) {
         String var1 = ZomboidFileSystem.instance.getFileNameInCurrentSave("zombies.ini");
         ConfigFile var2 = new ConfigFile();
         if (var2.read(var1)) {
            for(int var3 = 0; var3 < var2.getOptions().size(); ++var3) {
               ConfigOption var4 = (ConfigOption)var2.getOptions().get(var3);
               SandboxOptions.SandboxOption var5 = (SandboxOptions.SandboxOption)this.optionByName.get("ZombieConfig." + var4.getName());
               if (var5 != null) {
                  var5.asConfigOption().parse(var4.getValueAsString());
               }
            }
         }

      }
   }

   public void handleOldZombiesFile2() {
      if (!GameServer.bServer) {
         String var1 = ZomboidFileSystem.instance.getFileNameInCurrentSave("zombies.ini");
         File var2 = new File(var1);
         if (var2.exists()) {
            try {
               DebugLog.log("deleting " + var2.getAbsolutePath());
               var2.delete();
               this.saveCurrentGameBinFile();
            } catch (Exception var4) {
               ExceptionLogger.logException(var4);
            }

         }
      }
   }

   public void handleOldServerZombiesFile() {
      if (GameServer.bServer) {
         if (this.loadServerZombiesFile(GameServer.ServerName)) {
            String var1 = ServerSettingsManager.instance.getNameInSettingsFolder(GameServer.ServerName + "_zombies.ini");

            try {
               File var2 = new File(var1);
               DebugLog.log("deleting " + var2.getAbsolutePath());
               var2.delete();
               this.saveServerLuaFile(GameServer.ServerName);
            } catch (Exception var3) {
               ExceptionLogger.logException(var3);
            }
         }

      }
   }

   public boolean loadServerZombiesFile(String var1) {
      String var2 = ServerSettingsManager.instance.getNameInSettingsFolder(var1 + "_zombies.ini");
      ConfigFile var3 = new ConfigFile();
      if (var3.read(var2)) {
         for(int var4 = 0; var4 < var3.getOptions().size(); ++var4) {
            ConfigOption var5 = (ConfigOption)var3.getOptions().get(var4);
            SandboxOptions.SandboxOption var6 = (SandboxOptions.SandboxOption)this.optionByName.get("ZombieConfig." + var5.getName());
            if (var6 != null) {
               var6.asConfigOption().parse(var5.getValueAsString());
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean readLuaFile(String var1) {
      File var2 = new File(var1);
      if (!var2.exists()) {
         return false;
      } else {
         Object var3 = LuaManager.env.rawget("SandboxVars");
         KahluaTable var4 = null;
         if (var3 instanceof KahluaTable) {
            var4 = (KahluaTable)var3;
         }

         boolean var6;
         try {
            LuaManager.loaded.remove(var2.getAbsolutePath().replace("\\", "/"));
            Object var5 = LuaManager.RunLua(var2.getAbsolutePath());
            Object var16 = LuaManager.env.rawget("SandboxVars");
            if (var16 instanceof KahluaTable) {
               KahluaTable var7 = (KahluaTable)var16;
               int var8 = 0;
               Object var9 = var7.rawget("VERSION");
               if (var9 != null) {
                  if (var9 instanceof Double) {
                     var8 = ((Double)var9).intValue();
                  } else {
                     DebugLog.log("ERROR: VERSION=\"" + var9 + "\" in " + var1);
                  }

                  var7.rawset("VERSION", (Object)null);
               }

               var7 = this.upgradeLuaTable("", var7, var8);

               for(int var10 = 0; var10 < this.options.size(); ++var10) {
                  ((SandboxOptions.SandboxOption)this.options.get(var10)).fromTable(var7);
               }
            }

            boolean var17 = true;
            return var17;
         } catch (Exception var14) {
            ExceptionLogger.logException(var14);
            var6 = false;
         } finally {
            if (var4 != null) {
               LuaManager.env.rawset("SandboxVars", var4);
            }

         }

         return var6;
      }
   }

   private boolean writeLuaFile(String var1, boolean var2) {
      File var3 = new File(var1);
      DebugLog.log("writing " + var1);

      try {
         FileWriter var4 = new FileWriter(var3);
         Throwable var5 = null;

         try {
            HashMap var6 = new HashMap();
            ArrayList var7 = new ArrayList();
            var6.put("", new ArrayList());
            Iterator var8 = this.options.iterator();

            while(var8.hasNext()) {
               SandboxOptions.SandboxOption var9 = (SandboxOptions.SandboxOption)var8.next();
               if (var9.getTableName() == null) {
                  ((ArrayList)var6.get("")).add(var9);
               } else {
                  if (var6.get(var9.getTableName()) == null) {
                     var6.put(var9.getTableName(), new ArrayList());
                     var7.add(var9.getTableName());
                  }

                  ((ArrayList)var6.get(var9.getTableName())).add(var9);
               }
            }

            String var24 = System.lineSeparator();
            if (var2) {
               var4.write("return {" + var24);
            } else {
               var4.write("SandboxVars = {" + var24);
            }

            var4.write("    VERSION = 4," + var24);
            Iterator var25 = ((ArrayList)var6.get("")).iterator();

            while(var25.hasNext()) {
               SandboxOptions.SandboxOption var10 = (SandboxOptions.SandboxOption)var25.next();
               var4.write("    " + var10.asConfigOption().getName() + " = " + var10.asConfigOption().getValueAsString() + "," + var24);
            }

            var25 = var7.iterator();

            while(var25.hasNext()) {
               String var26 = (String)var25.next();
               var4.write("    " + var26 + " = {" + var24);
               Iterator var11 = ((ArrayList)var6.get(var26)).iterator();

               while(var11.hasNext()) {
                  SandboxOptions.SandboxOption var12 = (SandboxOptions.SandboxOption)var11.next();
                  var4.write("        " + var12.getShortName() + " = " + var12.asConfigOption().getValueAsString() + "," + var24);
               }

               var4.write("    }," + var24);
            }

            var4.write("}" + System.lineSeparator());
            return true;
         } catch (Throwable var21) {
            var5 = var21;
            throw var21;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var20) {
                     var5.addSuppressed(var20);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Exception var23) {
         ExceptionLogger.logException(var23);
         return false;
      }
   }

   public void load() {
      File var1 = ZomboidFileSystem.instance.getFileInCurrentSave("map_sand.bin");

      try {
         FileInputStream var2 = new FileInputStream(var1);
         Throwable var3 = null;

         try {
            BufferedInputStream var4 = new BufferedInputStream(var2);
            Throwable var5 = null;

            try {
               synchronized(SliceY.SliceBufferLock) {
                  SliceY.SliceBuffer.clear();
                  int var7 = var4.read(SliceY.SliceBuffer.array());
                  SliceY.SliceBuffer.limit(var7);
                  this.load(SliceY.SliceBuffer);
                  this.handleOldZombiesFile1();
                  this.applySettings();
                  this.toLua();
               }
            } catch (Throwable var37) {
               var5 = var37;
               throw var37;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var35) {
                        var5.addSuppressed(var35);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Throwable var39) {
            var3 = var39;
            throw var39;
         } finally {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var34) {
                     var3.addSuppressed(var34);
                  }
               } else {
                  var2.close();
               }
            }

         }

         return;
      } catch (FileNotFoundException var41) {
      } catch (Exception var42) {
         ExceptionLogger.logException(var42);
      }

      this.resetToDefault();
      this.updateFromLua();
   }

   public void loadCurrentGameBinFile() {
      File var1 = ZomboidFileSystem.instance.getFileInCurrentSave("map_sand.bin");

      try {
         FileInputStream var2 = new FileInputStream(var1);
         Throwable var3 = null;

         try {
            BufferedInputStream var4 = new BufferedInputStream(var2);
            Throwable var5 = null;

            try {
               synchronized(SliceY.SliceBufferLock) {
                  SliceY.SliceBuffer.clear();
                  int var7 = var4.read(SliceY.SliceBuffer.array());
                  SliceY.SliceBuffer.limit(var7);
                  this.load(SliceY.SliceBuffer);
               }

               this.toLua();
            } catch (Throwable var35) {
               var5 = var35;
               throw var35;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var33) {
                        var5.addSuppressed(var33);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Throwable var37) {
            var3 = var37;
            throw var37;
         } finally {
            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var32) {
                     var3.addSuppressed(var32);
                  }
               } else {
                  var2.close();
               }
            }

         }
      } catch (Exception var39) {
         ExceptionLogger.logException(var39);
      }

   }

   private String upgradeOptionName(String var1, int var2) {
      return var1;
   }

   private String upgradeOptionValue(String var1, String var2, int var3) {
      if (var3 < 3 && "DayLength".equals(var1)) {
         this.DayLength.parse(var2);
         if (this.DayLength.getValue() == 8) {
            this.DayLength.setValue(14);
         } else if (this.DayLength.getValue() == 9) {
            this.DayLength.setValue(26);
         }

         var2 = this.DayLength.getValueAsString();
      }

      if (var3 < 4 && "CarSpawnRate".equals(var1)) {
         try {
            int var4 = (int)Double.parseDouble(var2);
            if (var4 > 1) {
               var2 = Integer.toString(var4 + 1);
            }
         } catch (NumberFormatException var5) {
            var5.printStackTrace();
         }
      }

      return var2;
   }

   private KahluaTable upgradeLuaTable(String var1, KahluaTable var2, int var3) {
      KahluaTable var4 = LuaManager.platform.newTable();
      KahluaTableIterator var5 = var2.iterator();

      while(var5.advance()) {
         if (!(var5.getKey() instanceof String)) {
            throw new IllegalStateException("expected a String key");
         }

         if (var5.getValue() instanceof KahluaTable) {
            KahluaTable var6 = this.upgradeLuaTable(var1 + var5.getKey() + ".", (KahluaTable)var5.getValue(), var3);
            var4.rawset(var5.getKey(), var6);
         } else {
            String var8 = this.upgradeOptionName(var1 + var5.getKey(), var3);
            String var7 = this.upgradeOptionValue(var8, var5.getValue().toString(), var3);
            var4.rawset(var8.replace(var1, ""), var7);
         }
      }

      return var4;
   }

   public void sendToServer() {
      if (GameClient.bClient) {
         GameClient.instance.sendSandboxOptionsToServer(this);
      }

   }

   public static class EnumSandboxOption extends EnumConfigOption implements SandboxOptions.SandboxOption {
      protected String translation;
      protected String tableName;
      protected String shortName;
      protected String valueTranslation;

      public EnumSandboxOption(SandboxOptions var1, String var2, int var3, int var4) {
         super(var2, var3, var4);
         String[] var5 = SandboxOptions.parseName(var2);
         this.tableName = var5[0];
         this.shortName = var5[1];
         var1.addOption(this);
      }

      public ConfigOption asConfigOption() {
         return this;
      }

      public String getShortName() {
         return this.shortName;
      }

      public String getTableName() {
         return this.tableName;
      }

      public SandboxOptions.SandboxOption setTranslation(String var1) {
         this.translation = var1;
         return this;
      }

      public String getTranslatedName() {
         return Translator.getText("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation));
      }

      public String getTooltip() {
         return Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_tooltip");
      }

      public void fromTable(KahluaTable var1) {
         Object var2;
         if (this.tableName != null) {
            var2 = var1.rawget(this.tableName);
            if (!(var2 instanceof KahluaTable)) {
               return;
            }

            var1 = (KahluaTable)var2;
         }

         var2 = var1.rawget(this.getShortName());
         if (var2 != null) {
            this.setValueFromObject(var2);
         }

      }

      public void toTable(KahluaTable var1) {
         if (this.tableName != null) {
            Object var2 = var1.rawget(this.tableName);
            if (var2 instanceof KahluaTable) {
               var1 = (KahluaTable)var2;
            } else {
               KahluaTable var3 = LuaManager.platform.newTable();
               var1.rawset(this.tableName, var3);
               var1 = var3;
            }
         }

         var1.rawset(this.getShortName(), this.getValueAsObject());
      }

      public SandboxOptions.EnumSandboxOption setValueTranslation(String var1) {
         this.valueTranslation = var1;
         return this;
      }

      public String getValueTranslation() {
         return this.valueTranslation != null ? this.valueTranslation : (this.translation == null ? this.getShortName() : this.translation);
      }

      public String getValueTranslationByIndex(int var1) {
         if (var1 >= 1 && var1 <= this.getNumValues()) {
            return Translator.getText("Sandbox_" + this.getValueTranslation() + "_option" + var1);
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   public static class IntegerSandboxOption extends IntegerConfigOption implements SandboxOptions.SandboxOption {
      protected String translation;
      protected String tableName;
      protected String shortName;

      public IntegerSandboxOption(SandboxOptions var1, String var2, int var3, int var4, int var5) {
         super(var2, var3, var4, var5);
         String[] var6 = SandboxOptions.parseName(var2);
         this.tableName = var6[0];
         this.shortName = var6[1];
         var1.addOption(this);
      }

      public ConfigOption asConfigOption() {
         return this;
      }

      public String getShortName() {
         return this.shortName;
      }

      public String getTableName() {
         return this.tableName;
      }

      public SandboxOptions.SandboxOption setTranslation(String var1) {
         this.translation = var1;
         return this;
      }

      public String getTranslatedName() {
         return Translator.getText("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation));
      }

      public String getTooltip() {
         if ("ZombieConfig".equals(this.tableName)) {
            String var1 = Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_help");
            String var2 = Translator.getText("Sandbox_MinMaxDefault", this.min, this.max, this.defaultValue);
            if (var1 == null) {
               return var2;
            } else {
               return var2 == null ? var1 : var1 + "\\n" + var2;
            }
         } else {
            return Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_tooltip");
         }
      }

      public void fromTable(KahluaTable var1) {
         Object var2;
         if (this.tableName != null) {
            var2 = var1.rawget(this.tableName);
            if (!(var2 instanceof KahluaTable)) {
               return;
            }

            var1 = (KahluaTable)var2;
         }

         var2 = var1.rawget(this.getShortName());
         if (var2 != null) {
            this.setValueFromObject(var2);
         }

      }

      public void toTable(KahluaTable var1) {
         if (this.tableName != null) {
            Object var2 = var1.rawget(this.tableName);
            if (var2 instanceof KahluaTable) {
               var1 = (KahluaTable)var2;
            } else {
               KahluaTable var3 = LuaManager.platform.newTable();
               var1.rawset(this.tableName, var3);
               var1 = var3;
            }
         }

         var1.rawset(this.getShortName(), this.getValueAsObject());
      }
   }

   public static class DoubleSandboxOption extends DoubleConfigOption implements SandboxOptions.SandboxOption {
      protected String translation;
      protected String tableName;
      protected String shortName;

      public DoubleSandboxOption(SandboxOptions var1, String var2, double var3, double var5, double var7) {
         super(var2, var3, var5, var7);
         String[] var9 = SandboxOptions.parseName(var2);
         this.tableName = var9[0];
         this.shortName = var9[1];
         var1.addOption(this);
      }

      public ConfigOption asConfigOption() {
         return this;
      }

      public String getShortName() {
         return this.shortName;
      }

      public String getTableName() {
         return this.tableName;
      }

      public SandboxOptions.SandboxOption setTranslation(String var1) {
         this.translation = var1;
         return this;
      }

      public String getTranslatedName() {
         return Translator.getText("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation));
      }

      public String getTooltip() {
         String var1;
         if ("ZombieConfig".equals(this.tableName)) {
            var1 = Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_help");
         } else {
            var1 = Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_tooltip");
         }

         String var2 = Translator.getText("Sandbox_MinMaxDefault", this.min, this.max, this.defaultValue);
         if (var1 == null) {
            return var2;
         } else {
            return var2 == null ? var1 : var1 + "\\n" + var2;
         }
      }

      public void fromTable(KahluaTable var1) {
         Object var2;
         if (this.tableName != null) {
            var2 = var1.rawget(this.tableName);
            if (!(var2 instanceof KahluaTable)) {
               return;
            }

            var1 = (KahluaTable)var2;
         }

         var2 = var1.rawget(this.getShortName());
         if (var2 != null) {
            this.setValueFromObject(var2);
         }

      }

      public void toTable(KahluaTable var1) {
         if (this.tableName != null) {
            Object var2 = var1.rawget(this.tableName);
            if (var2 instanceof KahluaTable) {
               var1 = (KahluaTable)var2;
            } else {
               KahluaTable var3 = LuaManager.platform.newTable();
               var1.rawset(this.tableName, var3);
               var1 = var3;
            }
         }

         var1.rawset(this.getShortName(), this.getValueAsObject());
      }
   }

   public static class BooleanSandboxOption extends BooleanConfigOption implements SandboxOptions.SandboxOption {
      protected String translation;
      protected String tableName;
      protected String shortName;

      public BooleanSandboxOption(SandboxOptions var1, String var2, boolean var3) {
         super(var2, var3);
         String[] var4 = SandboxOptions.parseName(var2);
         this.tableName = var4[0];
         this.shortName = var4[1];
         var1.addOption(this);
      }

      public ConfigOption asConfigOption() {
         return this;
      }

      public String getShortName() {
         return this.shortName;
      }

      public String getTableName() {
         return this.tableName;
      }

      public SandboxOptions.SandboxOption setTranslation(String var1) {
         this.translation = var1;
         return this;
      }

      public String getTranslatedName() {
         return Translator.getText("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation));
      }

      public String getTooltip() {
         return Translator.getTextOrNull("Sandbox_" + (this.translation == null ? this.getShortName() : this.translation) + "_tooltip");
      }

      public void fromTable(KahluaTable var1) {
         Object var2;
         if (this.tableName != null) {
            var2 = var1.rawget(this.tableName);
            if (!(var2 instanceof KahluaTable)) {
               return;
            }

            var1 = (KahluaTable)var2;
         }

         var2 = var1.rawget(this.getShortName());
         if (var2 != null) {
            this.setValueFromObject(var2);
         }

      }

      public void toTable(KahluaTable var1) {
         if (this.tableName != null) {
            Object var2 = var1.rawget(this.tableName);
            if (var2 instanceof KahluaTable) {
               var1 = (KahluaTable)var2;
            } else {
               KahluaTable var3 = LuaManager.platform.newTable();
               var1.rawset(this.tableName, var3);
               var1 = var3;
            }
         }

         var1.rawset(this.getShortName(), this.getValueAsObject());
      }
   }

   public interface SandboxOption {
      ConfigOption asConfigOption();

      String getShortName();

      String getTableName();

      SandboxOptions.SandboxOption setTranslation(String var1);

      String getTranslatedName();

      String getTooltip();

      void fromTable(KahluaTable var1);

      void toTable(KahluaTable var1);
   }

   public final class ZombieConfig {
      public final SandboxOptions.DoubleSandboxOption PopulationMultiplier;
      public final SandboxOptions.DoubleSandboxOption PopulationStartMultiplier;
      public final SandboxOptions.DoubleSandboxOption PopulationPeakMultiplier;
      public final SandboxOptions.IntegerSandboxOption PopulationPeakDay;
      public final SandboxOptions.DoubleSandboxOption RespawnHours;
      public final SandboxOptions.DoubleSandboxOption RespawnUnseenHours;
      public final SandboxOptions.DoubleSandboxOption RespawnMultiplier;
      public final SandboxOptions.DoubleSandboxOption RedistributeHours;
      public final SandboxOptions.IntegerSandboxOption FollowSoundDistance;
      public final SandboxOptions.IntegerSandboxOption RallyGroupSize;
      public final SandboxOptions.IntegerSandboxOption RallyTravelDistance;
      public final SandboxOptions.IntegerSandboxOption RallyGroupSeparation;
      public final SandboxOptions.IntegerSandboxOption RallyGroupRadius;

      private ZombieConfig() {
         this.PopulationMultiplier = SandboxOptions.this.newDoubleOption("ZombieConfig.PopulationMultiplier", 0.0D, 4.0D, 1.0D);
         this.PopulationStartMultiplier = SandboxOptions.this.newDoubleOption("ZombieConfig.PopulationStartMultiplier", 0.0D, 4.0D, 1.0D);
         this.PopulationPeakMultiplier = SandboxOptions.this.newDoubleOption("ZombieConfig.PopulationPeakMultiplier", 0.0D, 4.0D, 1.5D);
         this.PopulationPeakDay = SandboxOptions.this.newIntegerOption("ZombieConfig.PopulationPeakDay", 1, 365, 28);
         this.RespawnHours = SandboxOptions.this.newDoubleOption("ZombieConfig.RespawnHours", 0.0D, 8760.0D, 72.0D);
         this.RespawnUnseenHours = SandboxOptions.this.newDoubleOption("ZombieConfig.RespawnUnseenHours", 0.0D, 8760.0D, 16.0D);
         this.RespawnMultiplier = SandboxOptions.this.newDoubleOption("ZombieConfig.RespawnMultiplier", 0.0D, 1.0D, 0.1D);
         this.RedistributeHours = SandboxOptions.this.newDoubleOption("ZombieConfig.RedistributeHours", 0.0D, 8760.0D, 12.0D);
         this.FollowSoundDistance = SandboxOptions.this.newIntegerOption("ZombieConfig.FollowSoundDistance", 10, 1000, 100);
         this.RallyGroupSize = SandboxOptions.this.newIntegerOption("ZombieConfig.RallyGroupSize", 0, 1000, 20);
         this.RallyTravelDistance = SandboxOptions.this.newIntegerOption("ZombieConfig.RallyTravelDistance", 5, 50, 20);
         this.RallyGroupSeparation = SandboxOptions.this.newIntegerOption("ZombieConfig.RallyGroupSeparation", 5, 25, 15);
         this.RallyGroupRadius = SandboxOptions.this.newIntegerOption("ZombieConfig.RallyGroupRadius", 1, 10, 3);
      }

      // $FF: synthetic method
      ZombieConfig(Object var2) {
         this();
      }
   }

   public final class ZombieLore {
      public final SandboxOptions.EnumSandboxOption Speed;
      public final SandboxOptions.EnumSandboxOption Strength;
      public final SandboxOptions.EnumSandboxOption Toughness;
      public final SandboxOptions.EnumSandboxOption Transmission;
      public final SandboxOptions.EnumSandboxOption Mortality;
      public final SandboxOptions.EnumSandboxOption Reanimate;
      public final SandboxOptions.EnumSandboxOption Cognition;
      public final SandboxOptions.EnumSandboxOption CrawlUnderVehicle;
      public final SandboxOptions.EnumSandboxOption Memory;
      public final SandboxOptions.EnumSandboxOption Decomp;
      public final SandboxOptions.EnumSandboxOption Sight;
      public final SandboxOptions.EnumSandboxOption Hearing;
      public final SandboxOptions.EnumSandboxOption Smell;
      public final SandboxOptions.BooleanSandboxOption ThumpNoChasing;
      public final SandboxOptions.BooleanSandboxOption ThumpOnConstruction;
      public final SandboxOptions.EnumSandboxOption ActiveOnly;
      public final SandboxOptions.BooleanSandboxOption TriggerHouseAlarm;
      public final SandboxOptions.BooleanSandboxOption ZombiesDragDown;
      public final SandboxOptions.BooleanSandboxOption ZombiesFenceLunge;

      private ZombieLore() {
         this.Speed = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Speed", 4, 2).setTranslation("ZSpeed");
         this.Strength = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Strength", 4, 2).setTranslation("ZStrength");
         this.Toughness = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Toughness", 4, 2).setTranslation("ZToughness");
         this.Transmission = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Transmission", 3, 1).setTranslation("ZTransmission");
         this.Mortality = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Mortality", 7, 5).setTranslation("ZInfectionMortality");
         this.Reanimate = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Reanimate", 6, 3).setTranslation("ZReanimateTime");
         this.Cognition = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Cognition", 4, 3).setTranslation("ZCognition");
         this.CrawlUnderVehicle = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.CrawlUnderVehicle", 7, 5).setTranslation("ZCrawlUnderVehicle");
         this.Memory = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Memory", 4, 2).setTranslation("ZMemory");
         this.Decomp = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Decomp", 4, 1).setTranslation("ZDecomposition");
         this.Sight = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Sight", 3, 2).setTranslation("ZSight");
         this.Hearing = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Hearing", 3, 2).setTranslation("ZHearing");
         this.Smell = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.Smell", 3, 2).setTranslation("ZSmell");
         this.ThumpNoChasing = SandboxOptions.this.newBooleanOption("ZombieLore.ThumpNoChasing", false);
         this.ThumpOnConstruction = SandboxOptions.this.newBooleanOption("ZombieLore.ThumpOnConstruction", true);
         this.ActiveOnly = (SandboxOptions.EnumSandboxOption)SandboxOptions.this.newEnumOption("ZombieLore.ActiveOnly", 3, 1).setTranslation("ActiveOnly");
         this.TriggerHouseAlarm = SandboxOptions.this.newBooleanOption("ZombieLore.TriggerHouseAlarm", false);
         this.ZombiesDragDown = SandboxOptions.this.newBooleanOption("ZombieLore.ZombiesDragDown", true);
         this.ZombiesFenceLunge = SandboxOptions.this.newBooleanOption("ZombieLore.ZombiesFenceLunge", true);
      }

      // $FF: synthetic method
      ZombieLore(Object var2) {
         this();
      }
   }
}
