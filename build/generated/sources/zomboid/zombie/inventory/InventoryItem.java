package zombie.inventory;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import se.krka.kahlua.vm.KahluaTable;
import se.krka.kahlua.vm.KahluaTableIterator;
import zombie.GameTime;
import zombie.GameWindow;
import zombie.Lua.LuaEventManager;
import zombie.Lua.LuaManager;
import zombie.audio.BaseSoundEmitter;
import zombie.characterTextures.BloodBodyPartType;
import zombie.characterTextures.BloodClothingType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.characters.SurvivorDesc;
import zombie.core.Color;
import zombie.core.Core;
import zombie.core.Translator;
import zombie.core.skinnedmodel.population.ClothingItem;
import zombie.core.skinnedmodel.visual.ItemVisual;
import zombie.core.stash.StashSystem;
import zombie.core.textures.ColorInfo;
import zombie.core.textures.Texture;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.inventory.types.Clothing;
import zombie.inventory.types.Drainable;
import zombie.inventory.types.DrainableComboItem;
import zombie.inventory.types.Food;
import zombie.inventory.types.HandWeapon;
import zombie.inventory.types.InventoryContainer;
import zombie.inventory.types.Key;
import zombie.iso.IsoDirections;
import zombie.iso.IsoGridSquare;
import zombie.iso.IsoObject;
import zombie.iso.IsoWorld;
import zombie.iso.objects.IsoWorldInventoryObject;
import zombie.iso.objects.RainManager;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.scripting.ScriptManager;
import zombie.scripting.objects.Item;
import zombie.scripting.objects.ItemReplacement;
import zombie.ui.ObjectTooltip;
import zombie.ui.TextManager;
import zombie.ui.UIFont;
import zombie.util.StringUtils;
import zombie.util.Type;
import zombie.vehicles.VehiclePart;

public class InventoryItem {
   protected IsoGameCharacter previousOwner = null;
   protected Item ScriptItem = null;
   protected ItemType cat;
   protected ItemContainer container;
   protected int containerX;
   protected int containerY;
   protected String name;
   protected String replaceOnUse;
   protected String replaceOnUseFullType;
   protected int ConditionMax;
   protected ItemContainer rightClickContainer;
   protected Texture texture;
   protected Texture texturerotten;
   protected Texture textureCooked;
   protected Texture textureBurnt;
   protected String type;
   protected String fullType;
   protected int uses;
   protected float Age;
   protected float LastAged;
   protected boolean IsCookable;
   protected float CookingTime;
   protected float MinutesToCook;
   protected float MinutesToBurn;
   public boolean Cooked;
   protected boolean Burnt;
   protected int OffAge;
   protected int OffAgeMax;
   protected float Weight;
   protected float ActualWeight;
   protected String WorldTexture;
   protected String Description;
   protected int Condition;
   protected String OffString;
   protected String FreshString;
   protected String CookedString;
   protected String UnCookedString;
   protected String FrozenString;
   protected String BurntString;
   private String brokenString;
   protected String module;
   protected float boredomChange;
   protected float unhappyChange;
   protected float stressChange;
   protected ArrayList Taken;
   protected IsoDirections placeDir;
   protected IsoDirections newPlaceDir;
   private KahluaTable table;
   public String ReplaceOnUseOn;
   public Color col;
   public boolean IsWaterSource;
   public boolean CanStoreWater;
   public boolean CanStack;
   private boolean activated;
   private boolean isTorchCone;
   private int lightDistance;
   private int Count;
   public float fatigueChange;
   public IsoWorldInventoryObject worldItem;
   private String customMenuOption;
   private String tooltip;
   private String displayCategory;
   private int haveBeenRepaired;
   private boolean broken;
   private String originalName;
   public long id;
   public boolean RequiresEquippedBothHands;
   public ByteBuffer byteData;
   public ArrayList extraItems;
   private boolean customName;
   private String breakSound;
   protected boolean alcoholic;
   private float alcoholPower;
   private float bandagePower;
   private float ReduceInfectionPower;
   private boolean customWeight;
   private boolean customColor;
   private int keyId;
   private boolean taintedWater;
   private boolean remoteController;
   private boolean canBeRemote;
   private int remoteControlID;
   private int remoteRange;
   private float colorRed;
   private float colorGreen;
   private float colorBlue;
   private String countDownSound;
   private String explosionSound;
   private IsoGameCharacter equipParent;
   private String evolvedRecipeName;
   private float metalValue;
   private float itemHeat;
   private float meltingTime;
   private String worker;
   private boolean isWet;
   private float wetCooldown;
   private String itemWhenDry;
   private boolean favorite;
   protected ArrayList requireInHandOrInventory;
   private String map;
   private String stashMap;
   public boolean keepOnDeplete;
   private boolean zombieInfected;
   private boolean rainFactorZero;
   private float itemCapacity;
   private int maxCapacity;
   private float brakeForce;
   private int chanceToSpawnDamaged;
   private float conditionLowerNormal;
   private float conditionLowerOffroad;
   private float wheelFriction;
   private float suspensionDamping;
   private float suspensionCompression;
   private float engineLoudness;
   protected ItemVisual visual;
   protected String staticModel;
   private ArrayList iconsForTexture;
   private ArrayList bloodClothingType;
   private int stashChance;
   private String ammoType;
   private int maxAmmo;
   private int currentAmmoCount;
   private String attachmentType;
   private ArrayList attachmentsProvided;
   private int attachedSlot;
   private String attachedSlotType;
   private String attachmentReplacement;
   private String attachedToModel;
   private String m_alternateModelName;
   public float jobDelta;
   public String jobType;
   static ByteBuffer tempBuffer = ByteBuffer.allocate(20000);
   public String mainCategory;
   private boolean canBeActivated;
   private float lightStrength;
   public String CloseKillMove;
   private boolean beingFilled;

   public int getSaveType() {
      throw new RuntimeException("InventoryItem.getSaveType() not implemented for " + this.getClass().getName());
   }

   public IsoWorldInventoryObject getWorldItem() {
      return this.worldItem;
   }

   public void setEquipParent(IsoGameCharacter var1) {
      this.equipParent = var1;
   }

   public IsoGameCharacter getEquipParent() {
      return this.equipParent == null || this.equipParent.getPrimaryHandItem() != this && this.equipParent.getSecondaryHandItem() != this ? null : this.equipParent;
   }

   public void setWorldItem(IsoWorldInventoryObject var1) {
      this.worldItem = var1;
   }

   public void setJobDelta(float var1) {
      this.jobDelta = var1;
   }

   public float getJobDelta() {
      return this.jobDelta;
   }

   public void setJobType(String var1) {
      this.jobType = var1;
   }

   public String getJobType() {
      return this.jobType;
   }

   public boolean hasModData() {
      return this.table != null && !this.table.isEmpty();
   }

   public KahluaTable getModData() {
      if (this.table == null) {
         this.table = LuaManager.platform.newTable();
      }

      return this.table;
   }

   public void storeInByteData(IsoObject var1) {
      tempBuffer.clear();

      try {
         var1.save(tempBuffer);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      tempBuffer.flip();
      if (this.byteData == null || this.byteData.capacity() < tempBuffer.limit() - 5 + 8) {
         this.byteData = ByteBuffer.allocate(tempBuffer.limit() - 5 + 8);
      }

      tempBuffer.get();
      tempBuffer.getInt();
      this.byteData.rewind();
      this.byteData.put((byte)87);
      this.byteData.put((byte)86);
      this.byteData.put((byte)69);
      this.byteData.put((byte)82);
      this.byteData.putInt(175);
      this.byteData.put(tempBuffer);
      this.byteData.flip();
   }

   public ByteBuffer getByteData() {
      return this.byteData;
   }

   public boolean isRequiresEquippedBothHands() {
      return this.RequiresEquippedBothHands;
   }

   public float getA() {
      return this.col.a;
   }

   public float getR() {
      return this.col.r;
   }

   public float getG() {
      return this.col.g;
   }

   public float getB() {
      return this.col.b;
   }

   public InventoryItem(String var1, String var2, String var3, String var4) {
      this.cat = ItemType.None;
      this.containerX = 0;
      this.containerY = 0;
      this.replaceOnUse = null;
      this.replaceOnUseFullType = null;
      this.ConditionMax = 10;
      this.rightClickContainer = null;
      this.uses = 1;
      this.Age = 0.0F;
      this.LastAged = -1.0F;
      this.IsCookable = false;
      this.CookingTime = 0.0F;
      this.MinutesToCook = 60.0F;
      this.MinutesToBurn = 120.0F;
      this.Cooked = false;
      this.Burnt = false;
      this.OffAge = 1000000000;
      this.OffAgeMax = 1000000000;
      this.Weight = 1.0F;
      this.ActualWeight = 1.0F;
      this.Condition = 10;
      this.OffString = Translator.getText("Tooltip_food_Rotten");
      this.FreshString = Translator.getText("Tooltip_food_Fresh");
      this.CookedString = Translator.getText("Tooltip_food_Cooked");
      this.UnCookedString = Translator.getText("Tooltip_food_Uncooked");
      this.FrozenString = Translator.getText("Tooltip_food_Frozen");
      this.BurntString = Translator.getText("Tooltip_food_Burnt");
      this.brokenString = Translator.getText("Tooltip_broken");
      this.module = "Base";
      this.boredomChange = 0.0F;
      this.unhappyChange = 0.0F;
      this.stressChange = 0.0F;
      this.Taken = new ArrayList();
      this.placeDir = IsoDirections.Max;
      this.newPlaceDir = IsoDirections.Max;
      this.table = null;
      this.ReplaceOnUseOn = null;
      this.col = Color.white;
      this.IsWaterSource = false;
      this.CanStoreWater = false;
      this.CanStack = false;
      this.activated = false;
      this.isTorchCone = false;
      this.lightDistance = 0;
      this.Count = 1;
      this.fatigueChange = 0.0F;
      this.worldItem = null;
      this.customMenuOption = null;
      this.tooltip = null;
      this.displayCategory = null;
      this.haveBeenRepaired = 1;
      this.broken = false;
      this.originalName = null;
      this.id = 0L;
      this.extraItems = null;
      this.customName = false;
      this.breakSound = null;
      this.alcoholic = false;
      this.alcoholPower = 0.0F;
      this.bandagePower = 0.0F;
      this.ReduceInfectionPower = 0.0F;
      this.customWeight = false;
      this.customColor = false;
      this.keyId = -1;
      this.taintedWater = false;
      this.remoteController = false;
      this.canBeRemote = false;
      this.remoteControlID = -1;
      this.remoteRange = 0;
      this.colorRed = 1.0F;
      this.colorGreen = 1.0F;
      this.colorBlue = 1.0F;
      this.countDownSound = null;
      this.explosionSound = null;
      this.equipParent = null;
      this.evolvedRecipeName = null;
      this.metalValue = 0.0F;
      this.itemHeat = 1.0F;
      this.meltingTime = 0.0F;
      this.isWet = false;
      this.wetCooldown = -1.0F;
      this.itemWhenDry = null;
      this.favorite = false;
      this.requireInHandOrInventory = null;
      this.map = null;
      this.stashMap = null;
      this.keepOnDeplete = false;
      this.zombieInfected = false;
      this.rainFactorZero = false;
      this.itemCapacity = -1.0F;
      this.maxCapacity = -1;
      this.brakeForce = 0.0F;
      this.chanceToSpawnDamaged = 0;
      this.conditionLowerNormal = 0.0F;
      this.conditionLowerOffroad = 0.0F;
      this.wheelFriction = 0.0F;
      this.suspensionDamping = 0.0F;
      this.suspensionCompression = 0.0F;
      this.engineLoudness = 0.0F;
      this.visual = null;
      this.staticModel = null;
      this.iconsForTexture = null;
      this.bloodClothingType = new ArrayList();
      this.stashChance = 80;
      this.ammoType = null;
      this.maxAmmo = 0;
      this.currentAmmoCount = 0;
      this.attachmentType = null;
      this.attachmentsProvided = null;
      this.attachedSlot = -1;
      this.attachedSlotType = null;
      this.attachmentReplacement = null;
      this.attachedToModel = null;
      this.m_alternateModelName = null;
      this.jobDelta = 0.0F;
      this.jobType = null;
      this.mainCategory = null;
      this.CloseKillMove = null;
      this.beingFilled = false;
      this.col = Color.white;
      this.texture = Texture.trygetTexture(var4);
      if (this.texture == null) {
         this.texture = Texture.getSharedTexture("media/inventory/Question_On.png");
      }

      this.module = var1;
      this.name = var2;
      this.originalName = var2;
      this.type = var3;
      this.fullType = var1 + "." + var3;
      this.WorldTexture = var4.replace("Item_", "media/inventory/world/WItem_");
      this.WorldTexture = this.WorldTexture + ".png";
   }

   public InventoryItem(String var1, String var2, String var3, Item var4) {
      this.cat = ItemType.None;
      this.containerX = 0;
      this.containerY = 0;
      this.replaceOnUse = null;
      this.replaceOnUseFullType = null;
      this.ConditionMax = 10;
      this.rightClickContainer = null;
      this.uses = 1;
      this.Age = 0.0F;
      this.LastAged = -1.0F;
      this.IsCookable = false;
      this.CookingTime = 0.0F;
      this.MinutesToCook = 60.0F;
      this.MinutesToBurn = 120.0F;
      this.Cooked = false;
      this.Burnt = false;
      this.OffAge = 1000000000;
      this.OffAgeMax = 1000000000;
      this.Weight = 1.0F;
      this.ActualWeight = 1.0F;
      this.Condition = 10;
      this.OffString = Translator.getText("Tooltip_food_Rotten");
      this.FreshString = Translator.getText("Tooltip_food_Fresh");
      this.CookedString = Translator.getText("Tooltip_food_Cooked");
      this.UnCookedString = Translator.getText("Tooltip_food_Uncooked");
      this.FrozenString = Translator.getText("Tooltip_food_Frozen");
      this.BurntString = Translator.getText("Tooltip_food_Burnt");
      this.brokenString = Translator.getText("Tooltip_broken");
      this.module = "Base";
      this.boredomChange = 0.0F;
      this.unhappyChange = 0.0F;
      this.stressChange = 0.0F;
      this.Taken = new ArrayList();
      this.placeDir = IsoDirections.Max;
      this.newPlaceDir = IsoDirections.Max;
      this.table = null;
      this.ReplaceOnUseOn = null;
      this.col = Color.white;
      this.IsWaterSource = false;
      this.CanStoreWater = false;
      this.CanStack = false;
      this.activated = false;
      this.isTorchCone = false;
      this.lightDistance = 0;
      this.Count = 1;
      this.fatigueChange = 0.0F;
      this.worldItem = null;
      this.customMenuOption = null;
      this.tooltip = null;
      this.displayCategory = null;
      this.haveBeenRepaired = 1;
      this.broken = false;
      this.originalName = null;
      this.id = 0L;
      this.extraItems = null;
      this.customName = false;
      this.breakSound = null;
      this.alcoholic = false;
      this.alcoholPower = 0.0F;
      this.bandagePower = 0.0F;
      this.ReduceInfectionPower = 0.0F;
      this.customWeight = false;
      this.customColor = false;
      this.keyId = -1;
      this.taintedWater = false;
      this.remoteController = false;
      this.canBeRemote = false;
      this.remoteControlID = -1;
      this.remoteRange = 0;
      this.colorRed = 1.0F;
      this.colorGreen = 1.0F;
      this.colorBlue = 1.0F;
      this.countDownSound = null;
      this.explosionSound = null;
      this.equipParent = null;
      this.evolvedRecipeName = null;
      this.metalValue = 0.0F;
      this.itemHeat = 1.0F;
      this.meltingTime = 0.0F;
      this.isWet = false;
      this.wetCooldown = -1.0F;
      this.itemWhenDry = null;
      this.favorite = false;
      this.requireInHandOrInventory = null;
      this.map = null;
      this.stashMap = null;
      this.keepOnDeplete = false;
      this.zombieInfected = false;
      this.rainFactorZero = false;
      this.itemCapacity = -1.0F;
      this.maxCapacity = -1;
      this.brakeForce = 0.0F;
      this.chanceToSpawnDamaged = 0;
      this.conditionLowerNormal = 0.0F;
      this.conditionLowerOffroad = 0.0F;
      this.wheelFriction = 0.0F;
      this.suspensionDamping = 0.0F;
      this.suspensionCompression = 0.0F;
      this.engineLoudness = 0.0F;
      this.visual = null;
      this.staticModel = null;
      this.iconsForTexture = null;
      this.bloodClothingType = new ArrayList();
      this.stashChance = 80;
      this.ammoType = null;
      this.maxAmmo = 0;
      this.currentAmmoCount = 0;
      this.attachmentType = null;
      this.attachmentsProvided = null;
      this.attachedSlot = -1;
      this.attachedSlotType = null;
      this.attachmentReplacement = null;
      this.attachedToModel = null;
      this.m_alternateModelName = null;
      this.jobDelta = 0.0F;
      this.jobType = null;
      this.mainCategory = null;
      this.CloseKillMove = null;
      this.beingFilled = false;
      this.col = Color.white;
      this.texture = var4.NormalTexture;
      this.module = var1;
      this.name = var2;
      this.originalName = var2;
      this.type = var3;
      this.fullType = var1 + "." + var3;
      this.WorldTexture = var4.WorldTextureName;
   }

   public String getType() {
      return this.type;
   }

   public Texture getTex() {
      return this.texture;
   }

   public String getCategory() {
      return this.mainCategory != null ? this.mainCategory : "Item";
   }

   public boolean IsRotten() {
      return this.Age > (float)this.OffAge;
   }

   public float HowRotten() {
      if (this.OffAgeMax - this.OffAge == 0) {
         return this.Age > (float)this.OffAge ? 1.0F : 0.0F;
      } else {
         float var1 = (this.Age - (float)this.OffAge) / (float)(this.OffAgeMax - this.OffAge);
         return var1;
      }
   }

   public boolean CanStack(InventoryItem var1) {
      return false;
   }

   public boolean ModDataMatches(InventoryItem var1) {
      KahluaTable var2 = var1.getModData();
      KahluaTable var3 = var1.getModData();
      if (var2 == null && var3 == null) {
         return true;
      } else if (var2 == null) {
         return false;
      } else if (var3 == null) {
         return false;
      } else if (var2.len() != var3.len()) {
         return false;
      } else {
         KahluaTableIterator var4 = var2.iterator();

         Object var5;
         Object var6;
         do {
            if (!var4.advance()) {
               return true;
            }

            var5 = var3.rawget(var4.getKey());
            var6 = var4.getValue();
         } while(var5.equals(var6));

         return false;
      }
   }

   public void DoTooltip(ObjectTooltip var1) {
      var1.render();
      UIFont var2 = var1.getFont();
      int var3 = var1.getLineSpacing();
      byte var4 = 5;
      String var5 = "";
      if (this.Burnt) {
         var5 = var5 + this.BurntString + " ";
      } else if (this.OffAge < 1000000000 && this.Age < (float)this.OffAge) {
         var5 = var5 + this.FreshString + " ";
      } else if (this.OffAgeMax < 1000000000 && this.Age >= (float)this.OffAgeMax) {
         var5 = var5 + this.OffString + " ";
      }

      if (this.isCooked() && !this.Burnt) {
         var5 = var5 + this.CookedString + " ";
      } else if (this.IsCookable && !this.Burnt && !(this instanceof DrainableComboItem)) {
         var5 = var5 + this.UnCookedString + " ";
      }

      if (this instanceof Food && ((Food)this).isFrozen()) {
         var5 = var5 + this.FrozenString + " ";
      }

      var5 = var5.trim();
      String var6;
      if (var5.isEmpty()) {
         var1.DrawText(var2, var6 = this.getName(), 5.0D, (double)var4, 1.0D, 1.0D, 0.800000011920929D, 1.0D);
      } else if (this.OffAgeMax < 1000000000 && this.Age >= (float)this.OffAgeMax) {
         var1.DrawText(var2, var6 = Translator.getText("IGUI_FoodNaming", var5, this.name), 5.0D, (double)var4, 1.0D, 0.10000000149011612D, 0.10000000149011612D, 1.0D);
      } else {
         var1.DrawText(var2, var6 = Translator.getText("IGUI_FoodNaming", var5, this.name), 5.0D, (double)var4, 1.0D, 1.0D, 0.800000011920929D, 1.0D);
      }

      var1.adjustWidth(5, var6);
      int var11 = var4 + var3 + 5;
      int var7;
      int var8;
      int var9;
      InventoryItem var10;
      if (this.extraItems != null) {
         var1.DrawText(var2, Translator.getText("Tooltip_item_Contains"), 5.0D, (double)var11, 1.0D, 1.0D, 0.800000011920929D, 1.0D);
         var7 = 5 + TextManager.instance.MeasureStringX(var2, Translator.getText("Tooltip_item_Contains")) + 4;
         var8 = (var3 - 10) / 2;

         for(var9 = 0; var9 < this.extraItems.size(); ++var9) {
            var10 = InventoryItemFactory.CreateItem((String)this.extraItems.get(var9));
            var1.DrawTextureScaled(var10.getTex(), (double)var7, (double)(var11 + var8), 10.0D, 10.0D, 1.0D);
            var7 += 11;
         }

         var11 = var11 + var3 + 5;
      }

      if (this instanceof Food && ((Food)this).spices != null) {
         var1.DrawText(var2, Translator.getText("Tooltip_item_Spices"), 5.0D, (double)var11, 1.0D, 1.0D, 0.800000011920929D, 1.0D);
         var7 = 5 + TextManager.instance.MeasureStringX(var2, Translator.getText("Tooltip_item_Spices")) + 4;
         var8 = (var3 - 10) / 2;

         for(var9 = 0; var9 < ((Food)this).spices.size(); ++var9) {
            var10 = InventoryItemFactory.CreateItem((String)((Food)this).spices.get(var9));
            var1.DrawTextureScaled(var10.getTex(), (double)var7, (double)(var11 + var8), 10.0D, 10.0D, 1.0D);
            var7 += 11;
         }

         var11 = var11 + var3 + 5;
      }

      ObjectTooltip.Layout var12 = var1.beginLayout();
      var12.setMinLabelWidth(80);
      ObjectTooltip.LayoutItem var13 = var12.addItem();
      var13.setLabel(Translator.getText("Tooltip_item_Weight") + ":", 1.0F, 1.0F, 0.8F, 1.0F);
      boolean var14 = this.isEquipped();
      float var15;
      if (!(this instanceof HandWeapon) && !(this instanceof Clothing) && !(this instanceof DrainableComboItem)) {
         var15 = this.getUnequippedWeight();
         if (var15 > 0.0F && var15 < 0.01F) {
            var15 = 0.01F;
         }

         var13.setValueRightNoPlus(var15);
      } else if (var14) {
         var13.setValue(this.getCleanString(this.getEquippedWeight()) + "    (" + this.getCleanString(this.getUnequippedWeight()) + " " + Translator.getText("Tooltip_item_Unequipped") + ")", 1.0F, 1.0F, 1.0F, 1.0F);
      } else if (this.getAttachedSlot() > -1) {
         var13.setValue(this.getCleanString(this.getHotbarEquippedWeight()) + "    (" + this.getCleanString(this.getUnequippedWeight()) + " " + Translator.getText("Tooltip_item_Unequipped") + ")", 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         var13.setValue(this.getCleanString(this.getUnequippedWeight()) + "    (" + this.getCleanString(this.getEquippedWeight()) + " " + Translator.getText("Tooltip_item_Equipped") + ")", 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.getMaxAmmo() > 0 && !(this instanceof HandWeapon)) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_weapon_AmmoCount") + ":", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValue(this.getCurrentAmmoCount() + " / " + this.getMaxAmmo(), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (Core.bDebug && DebugOptions.instance.TooltipInfo.getValue()) {
         var13 = var12.addItem();
         var13.setLabel("getActualWeight()", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus(this.getActualWeight());
         var13 = var12.addItem();
         var13.setLabel("getWeight()", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus(this.getWeight());
         var13 = var12.addItem();
         var13.setLabel("getEquippedWeight()", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus(this.getEquippedWeight());
         var13 = var12.addItem();
         var13.setLabel("getUnequippedWeight()", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus(this.getUnequippedWeight());
         var13 = var12.addItem();
         var13.setLabel("getContentsWeight()", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus(this.getContentsWeight());
         if (this instanceof Key || "Doorknob".equals(this.type)) {
            var13 = var12.addItem();
            var13.setLabel("DBG: keyId", 1.0F, 1.0F, 0.8F, 1.0F);
            var13.setValueRightNoPlus(this.getKeyId());
         }

         var13 = var12.addItem();
         var13.setLabel("ID", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRightNoPlus((int)this.id);
         ClothingItem var16 = this.getClothingItem();
         if (var16 != null) {
            var13 = var12.addItem();
            var13.setLabel("ClothingItem", 1.0F, 1.0F, 1.0F, 1.0F);
            var13.setValue(this.getClothingItem().m_Name, 1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      if (this.getFatigueChange() != 0.0F) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_item_Fatigue") + ": ", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValueRight((int)(this.getFatigueChange() * 100.0F), false);
      }

      if (this instanceof DrainableComboItem) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("IGUI_invpanel_Remaining") + ": ", 1.0F, 1.0F, 0.8F, 1.0F);
         var15 = ((DrainableComboItem)this).getUsedDelta();
         var13.setProgress(var15, 0.0F, 0.6F, 0.0F, 0.7F);
      }

      if (this.isTaintedWater()) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_item_TaintedWater"), 1.0F, 0.5F, 0.5F, 1.0F);
      }

      this.DoTooltip(var1, var12);
      if (this.getRemoteControlID() != -1) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_TrapControllerID"), 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValue(Integer.toString(this.getRemoteControlID()), 1.0F, 1.0F, 0.8F, 1.0F);
      }

      if (!FixingManager.getFixes(this).isEmpty()) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_weapon_Repaired") + ":", 1.0F, 1.0F, 0.8F, 1.0F);
         if (this.getHaveBeenRepaired() == 1) {
            var13.setValue(Translator.getText("Tooltip_never"), 1.0F, 1.0F, 1.0F, 1.0F);
         } else {
            var13.setValue(this.getHaveBeenRepaired() - 1 + "x", 1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      if (this.isEquippedNoSprint()) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_CantSprintEquipped"), 1.0F, 0.1F, 0.1F, 1.0F);
      }

      if (this.isWet()) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_Wetness") + ": ", 1.0F, 1.0F, 0.8F, 1.0F);
         var15 = this.getWetCooldown() / 10000.0F;
         var13.setProgress(var15, 0.0F, 0.6F, 0.0F, 0.7F);
      }

      if (this.getMaxCapacity() > 0) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_container_Capacity") + ":", 1.0F, 1.0F, 0.8F, 1.0F);
         var15 = (float)this.getMaxCapacity();
         if (this.isConditionAffectsCapacity()) {
            var15 = VehiclePart.getNumberByCondition((float)this.getMaxCapacity(), (float)this.getCondition(), 5.0F);
         }

         if (this.getItemCapacity() > -1.0F) {
            var13.setValue(this.getItemCapacity() + " / " + var15, 1.0F, 1.0F, 0.8F, 1.0F);
         } else {
            var13.setValue("0 / " + var15, 1.0F, 1.0F, 0.8F, 1.0F);
         }
      }

      if (this.getConditionMax() > 0 && this.getMechanicType() > 0) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText("Tooltip_weapon_Condition") + ":", 1.0F, 1.0F, 0.8F, 1.0F);
         var13.setValue(this.getCondition() + " / " + this.getConditionMax(), 1.0F, 1.0F, 0.8F, 1.0F);
      }

      if (this.getTooltip() != null) {
         var13 = var12.addItem();
         var13.setLabel(Translator.getText(this.tooltip), 1.0F, 1.0F, 0.8F, 1.0F);
      }

      var11 = var12.render(5, var11, var1);
      var1.endLayout(var12);
      var11 += var1.padBottom;
      var1.setHeight((double)var11);
      if (var1.getWidth() < 150.0D) {
         var1.setWidth(150.0D);
      }

   }

   public String getCleanString(float var1) {
      float var2 = (float)((int)(((double)var1 + 0.005D) * 100.0D)) / 100.0F;
      String var3 = Float.toString(var2);
      return var3;
   }

   public void DoTooltip(ObjectTooltip var1, ObjectTooltip.Layout var2) {
   }

   public void SetContainerPosition(int var1, int var2) {
      this.containerX = var1;
      this.containerY = var2;
   }

   public void Use() {
      this.Use(false);
   }

   public void UseItem() {
      this.Use(false);
   }

   public void Use(boolean var1) {
      this.Use(var1, false);
   }

   public void Use(boolean var1, boolean var2) {
      if (this.isDisappearOnUse() || var1) {
         --this.uses;
         if (this.replaceOnUse != null && !var2 && !var1 && this.container != null) {
            String var3 = this.replaceOnUse;
            if (!this.replaceOnUse.contains(".")) {
               var3 = this.module + "." + var3;
            }

            InventoryItem var4 = this.container.AddItem(var3);
            if (var4 != null) {
               var4.setConditionFromModData(this);
            }

            this.container.setDrawDirty(true);
            this.container.setDirty(true);
            var4.setFavorite(this.isFavorite());
         }

         if (this.uses <= 0) {
            if (this.keepOnDeplete) {
               return;
            }

            if (this.container != null) {
               if (this.container.parent instanceof IsoGameCharacter && !(this instanceof HandWeapon)) {
                  IsoGameCharacter var5 = (IsoGameCharacter)this.container.parent;
                  var5.removeFromHands(this);
               }

               this.container.Items.remove(this);
               this.container.setDirty(true);
               this.container.setDrawDirty(true);
               this.container = null;
            }
         }

      }
   }

   public boolean shouldUpdateInWorld() {
      if (!GameServer.bServer && !this.rainFactorZero && this.canStoreWater() && this.getReplaceOnUseOn() != null && this.getReplaceOnUseOnString() != null) {
         IsoGridSquare var1 = this.getWorldItem().getSquare();
         return var1 != null && var1.isOutside();
      } else {
         return false;
      }
   }

   public void update() {
      if (this.isWet()) {
         this.wetCooldown -= 1.0F * GameTime.instance.getMultiplier();
         if (this.wetCooldown <= 0.0F) {
            InventoryItem var1 = InventoryItemFactory.CreateItem(this.itemWhenDry);
            if (this.getWorldItem() != null) {
               this.getWorldItem().getSquare().AddWorldInventoryItem(var1, this.getWorldItem().getX(), this.getWorldItem().getY(), this.getWorldItem().getZ());
               this.getWorldItem().getSquare().transmitRemoveItemFromSquare(this.getWorldItem());
               this.getWorldItem().getSquare().getWorldObjects().remove(this.getWorldItem());
               this.getWorldItem().getSquare().getObjects().remove(this.getWorldItem());
               if (this.getContainer() != null) {
                  this.getContainer().setDirty(true);
                  this.getContainer().setDrawDirty(true);
               }

               this.getWorldItem().getSquare().chunk.recalcHashCodeObjects();
               this.setWorldItem((IsoWorldInventoryObject)null);
            } else if (this.getContainer() != null) {
               this.getContainer().addItem(var1);
               this.getContainer().Remove(this);
            }

            this.setWet(false);
            IsoWorld.instance.CurrentCell.addToProcessItemsRemove(this);
         }
      }

      if (!GameServer.bServer && !this.rainFactorZero && this.getWorldItem() != null && this.canStoreWater() && this.getReplaceOnUseOn() != null && this.getReplaceOnUseOnString() != null && RainManager.isRaining()) {
         IsoWorldInventoryObject var4 = this.getWorldItem();
         IsoGridSquare var2 = var4.getSquare();
         if (var2 != null && var2.isOutside()) {
            InventoryItem var3 = InventoryItemFactory.CreateItem(this.getReplaceOnUseOnString());
            if (var3 instanceof DrainableComboItem && var3.canStoreWater()) {
               if (((DrainableComboItem)var3).getRainFactor() == 0.0F) {
                  this.rainFactorZero = true;
                  return;
               }

               ((DrainableComboItem)var3).setUsedDelta(0.0F);
               if (GameClient.bClient) {
                  var4.removeFromWorld();
                  var4.removeFromSquare();
                  IsoWorld.instance.CurrentCell.addToProcessItemsRemove(this);
                  var2.AddWorldInventoryItem(var3, var4.xoff, var4.yoff, var4.zoff, false);
               } else {
                  var4.item = var3;
                  var3.setWorldItem(var4);
                  this.setWorldItem((IsoWorldInventoryObject)null);
                  var4.updateSprite();
               }

               LuaEventManager.triggerEvent("OnContainerUpdate");
            }
         }
      }

   }

   public boolean finishupdate() {
      if (!GameServer.bServer && this.canStoreWater() && this.getReplaceOnUseOn() != null && this.getReplaceOnUseOnString() != null && this.getWorldItem() != null && this.getWorldItem().getSquare() != null) {
         return false;
      } else {
         return !this.isWet();
      }
   }

   public void updateSound(BaseSoundEmitter var1) {
   }

   public String getFullType() {
      assert this.fullType != null && this.fullType.equals(this.module + "." + this.type);

      return this.fullType;
   }

   public void save(ByteBuffer var1, boolean var2) throws IOException {
      var2 = false;
      if (GameWindow.DEBUG_SAVE) {
         DebugLog.log(this.getFullType());
      }

      if (!var2) {
         GameWindow.WriteString(var1, this.getFullType());
      } else {
         var1.putInt((Integer)Item.NetItemToID.get(this.getFullType()));
      }

      var1.put((byte)this.getSaveType());
      var1.putInt(this.uses);
      var1.putLong(this.id);
      if (this.table != null && !this.table.isEmpty()) {
         var1.put((byte)1);
         this.table.save(var1);
      } else {
         var1.put((byte)0);
      }

      if (this.IsDrainable() && ((DrainableComboItem)this).getUsedDelta() < 1.0F) {
         var1.put((byte)1);
         var1.putFloat(((DrainableComboItem)this).getUsedDelta());
      } else {
         var1.put((byte)0);
      }

      var1.put((byte)this.getCondition());
      var1.put((byte)(this.isActivated() ? 1 : 0));
      var1.putShort((short)this.getHaveBeenRepaired());
      if (this.name != null && !this.name.equals(this.originalName)) {
         var1.put((byte)1);
         GameWindow.WriteString(var1, this.name);
      } else {
         var1.put((byte)0);
      }

      if (this.byteData == null) {
         var1.put((byte)0);
      } else {
         var1.put((byte)1);
         this.byteData.rewind();
         var1.putInt(this.byteData.limit());
         var1.put(this.byteData);
         this.byteData.flip();
      }

      if (this.extraItems != null) {
         var1.putInt(this.extraItems.size());

         for(int var3 = 0; var3 < this.extraItems.size(); ++var3) {
            GameWindow.WriteString(var1, (String)this.extraItems.get(var3));
         }
      } else {
         var1.putInt(0);
      }

      var1.put((byte)(this.isCustomName() ? 1 : 0));
      var1.putFloat(this.isCustomWeight() ? this.getActualWeight() : -1.0F);
      var1.putInt(this.getKeyId());
      var1.put((byte)(this.isTaintedWater() ? 1 : 0));
      var1.putInt(this.getRemoteControlID());
      var1.putInt(this.getRemoteRange());
      var1.putFloat(this.colorRed);
      var1.putFloat(this.colorGreen);
      var1.putFloat(this.colorBlue);
      GameWindow.WriteString(var1, this.getWorker());
      var1.putFloat(this.wetCooldown);
      var1.put((byte)(this.isFavorite() ? 1 : 0));
      if (this.isCustomColor()) {
         var1.put((byte)1);
         var1.putFloat(this.getColor().r);
         var1.putFloat(this.getColor().g);
         var1.putFloat(this.getColor().b);
         var1.putFloat(this.getColor().a);
      } else {
         var1.put((byte)0);
      }

      GameWindow.WriteString(var1, this.stashMap);
      var1.putFloat(this.itemCapacity);
      var1.put((byte)(this.isInfected() ? 1 : 0));
      if (this.visual == null) {
         var1.put((byte)0);
      } else {
         var1.put((byte)1);
         this.visual.save(var1);
      }

      var1.putInt(this.currentAmmoCount);
      var1.putInt(this.attachedSlot);
      GameWindow.WriteString(var1, this.attachedSlotType);
      GameWindow.WriteString(var1, this.attachedToModel);
      var1.putInt(this.maxCapacity);
   }

   public void load(ByteBuffer var1, int var2, boolean var3) throws IOException {
      var3 = false;
      this.uses = var1.getInt();
      this.id = var1.getLong();
      if (var1.get() == 1) {
         if (this.table == null) {
            this.table = LuaManager.platform.newTable();
         }

         this.table.load(var1, var2);
      }

      if (var1.get() == 1) {
         ((DrainableComboItem)this).setUsedDelta(var1.getFloat());
      }

      this.setCondition(var1.get(), false);
      this.activated = var1.get() == 1;
      this.setHaveBeenRepaired(var1.getShort());
      if (var1.get() != 0) {
         this.name = GameWindow.ReadString(var1);
      }

      int var4;
      int var5;
      if (var1.get() == 1) {
         var4 = var1.getInt();
         this.byteData = ByteBuffer.allocate(var4);

         for(var5 = 0; var5 < var4; ++var5) {
            this.byteData.put(var1.get());
         }

         this.byteData.flip();
      }

      var4 = var1.getInt();
      if (var4 > 0) {
         this.extraItems = new ArrayList();

         for(var5 = 0; var5 < var4; ++var5) {
            this.extraItems.add(GameWindow.ReadString(var1));
         }
      }

      this.setCustomName(var1.get() == 1);
      float var6 = var1.getFloat();
      if (var6 >= 0.0F) {
         this.setActualWeight(var6);
         this.setWeight(var6);
         this.setCustomWeight(true);
      }

      this.setKeyId(var1.getInt());
      this.setTaintedWater(var1.get() == 1);
      this.setRemoteControlID(var1.getInt());
      this.setRemoteRange(var1.getInt());
      this.setColorRed(var1.getFloat());
      this.setColorGreen(var1.getFloat());
      this.setColorBlue(var1.getFloat());
      this.setColor(new Color(this.colorRed, this.colorGreen, this.colorBlue));
      this.setWorker(GameWindow.ReadString(var1));
      this.setWetCooldown(var1.getFloat());
      this.setFavorite(var1.get() == 1);
      if (var1.get() == 1) {
         this.setColor(new Color(var1.getFloat(), var1.getFloat(), var1.getFloat(), var1.getFloat()));
      }

      this.stashMap = GameWindow.ReadString(var1);
      this.itemCapacity = var1.getFloat();
      this.setInfected(var1.get() == 1);
      if (var1.get() == 1) {
         this.visual = new ItemVisual();
         this.visual.load(var1, var2);
      }

      this.setCurrentAmmoCount(var1.getInt());
      if (var2 >= 151) {
         this.attachedSlot = var1.getInt();
         this.attachedSlotType = GameWindow.ReadString(var1);
         this.attachedToModel = GameWindow.ReadString(var1);
      }

      if (var2 >= 175) {
         this.maxCapacity = var1.getInt();
      }

   }

   public boolean IsFood() {
      return false;
   }

   public boolean IsWeapon() {
      return false;
   }

   public boolean IsDrainable() {
      return false;
   }

   public boolean IsLiterature() {
      return false;
   }

   public boolean IsClothing() {
      return false;
   }

   public boolean IsInventoryContainer() {
      return false;
   }

   static InventoryItem LoadFromFile(DataInputStream var0) throws IOException {
      GameWindow.ReadString(var0);
      return null;
   }

   public ItemContainer getOutermostContainer() {
      if (this.container != null && !"floor".equals(this.container.type)) {
         ItemContainer var1;
         for(var1 = this.container; var1.getContainingItem() != null && var1.getContainingItem().getContainer() != null && !"floor".equals(var1.getContainingItem().getContainer().type); var1 = var1.getContainingItem().getContainer()) {
         }

         return var1;
      } else {
         return null;
      }
   }

   public boolean isInLocalPlayerInventory() {
      if (!GameClient.bClient) {
         return false;
      } else {
         ItemContainer var1 = this.getOutermostContainer();
         if (var1 == null) {
            return false;
         } else {
            return var1.getParent() instanceof IsoPlayer ? ((IsoPlayer)var1.getParent()).isLocalPlayer() : false;
         }
      }
   }

   public boolean isInPlayerInventory() {
      ItemContainer var1 = this.getOutermostContainer();
      return var1 == null ? false : var1.getParent() instanceof IsoPlayer;
   }

   public ItemReplacement getItemReplacementPrimaryHand() {
      return this.ScriptItem.replacePrimaryHand;
   }

   public ItemReplacement getItemReplacementSecondHand() {
      return this.ScriptItem.replaceSecondHand;
   }

   public ClothingItem getClothingItem() {
      if ("RightHand".equalsIgnoreCase(this.getAlternateModelName())) {
         return this.getItemReplacementPrimaryHand().clothingItem;
      } else {
         return "LeftHand".equalsIgnoreCase(this.getAlternateModelName()) ? this.getItemReplacementSecondHand().clothingItem : this.ScriptItem.getClothingItemAsset();
      }
   }

   public String getAlternateModelName() {
      if (this.getContainer() != null && this.getContainer().getParent() instanceof IsoGameCharacter) {
         IsoGameCharacter var1 = (IsoGameCharacter)this.getContainer().getParent();
         if (var1.getPrimaryHandItem() == this && this.getItemReplacementPrimaryHand() != null) {
            return "RightHand";
         }

         if (var1.getSecondaryHandItem() == this && this.getItemReplacementSecondHand() != null) {
            return "LeftHand";
         }
      }

      return this.m_alternateModelName;
   }

   public ItemVisual getVisual() {
      ClothingItem var1 = this.getClothingItem();
      if (var1 != null && var1.isReady()) {
         if (this.visual == null) {
            this.visual = new ItemVisual();
            this.visual.setItemType(this.getFullType());
            this.visual.pickUninitializedValues(var1);
         }

         this.visual.setClothingItemName(var1.m_Name);
         this.visual.setAlternateModelName(this.getAlternateModelName());
         return this.visual;
      } else {
         this.visual = null;
         return null;
      }
   }

   public void synchWithVisual() {
      if (this instanceof Clothing || this instanceof InventoryContainer) {
         ItemVisual var1 = this.getVisual();
         if (var1 != null) {
            if (this instanceof Clothing && this.getBloodClothingType() != null) {
               BloodClothingType.calcTotalBloodLevel((Clothing)this);
            }

            ClothingItem var2 = this.getClothingItem();
            if (var2.m_AllowRandomTint) {
               this.setColor(new Color(var1.m_Tint.r, var1.m_Tint.g, var1.m_Tint.b));
            } else {
               this.setColor(new Color(this.getColorRed(), this.getColorGreen(), this.getColorBlue()));
            }

            if ((var2.m_BaseTextures.size() > 1 || var1.m_TextureChoice > -1) && this.getIconsForTexture() != null) {
               String var3 = null;
               if (var1.m_BaseTexture > -1 && this.getIconsForTexture().size() > var1.m_BaseTexture) {
                  var3 = (String)this.getIconsForTexture().get(var1.m_BaseTexture);
               } else if (var1.m_TextureChoice > -1 && this.getIconsForTexture().size() > var1.m_TextureChoice) {
                  var3 = (String)this.getIconsForTexture().get(var1.m_TextureChoice);
               }

               if (!StringUtils.isNullOrWhitespace(var3)) {
                  this.texture = Texture.trygetTexture("Item_" + var3);
                  if (this.texture == null) {
                     this.texture = Texture.getSharedTexture("media/inventory/Question_On.png");
                  }

               }
            }
         }
      }
   }

   public int getContainerX() {
      return this.containerX;
   }

   public void setContainerX(int var1) {
      this.containerX = var1;
   }

   public int getContainerY() {
      return this.containerY;
   }

   public void setContainerY(int var1) {
      this.containerY = var1;
   }

   public boolean isDisappearOnUse() {
      return this.getScriptItem().isDisappearOnUse();
   }

   public String getName() {
      if (this.isBroken()) {
         return Translator.getText("IGUI_ItemNaming", this.brokenString, this.name);
      } else if (this.isTaintedWater()) {
         return Translator.getText("IGUI_ItemNameTaintedWater", this.name);
      } else if (this.getRemoteControlID() != -1) {
         return Translator.getText("IGUI_ItemNameControllerLinked", this.name);
      } else {
         return this.getMechanicType() > 0 ? Translator.getText("IGUI_ItemNameMechanicalType", this.name, Translator.getText("IGUI_VehicleType_" + this.getMechanicType())) : this.name;
      }
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String getReplaceOnUse() {
      return this.replaceOnUse;
   }

   public void setReplaceOnUse(String var1) {
      this.replaceOnUse = var1;
      this.replaceOnUseFullType = StringUtils.moduleDotType(this.getModule(), var1);
   }

   public String getReplaceOnUseFullType() {
      return this.replaceOnUseFullType;
   }

   public int getConditionMax() {
      return this.ConditionMax;
   }

   public void setConditionMax(int var1) {
      this.ConditionMax = var1;
   }

   public ItemContainer getRightClickContainer() {
      return this.rightClickContainer;
   }

   public void setRightClickContainer(ItemContainer var1) {
      this.rightClickContainer = var1;
   }

   public String getSwingAnim() {
      return this.getScriptItem().SwingAnim;
   }

   public Texture getTexture() {
      return this.texture;
   }

   public void setTexture(Texture var1) {
      this.texture = var1;
   }

   public Texture getTexturerotten() {
      return this.texturerotten;
   }

   public void setTexturerotten(Texture var1) {
      this.texturerotten = var1;
   }

   public Texture getTextureCooked() {
      return this.textureCooked;
   }

   public void setTextureCooked(Texture var1) {
      this.textureCooked = var1;
   }

   public Texture getTextureBurnt() {
      return this.textureBurnt;
   }

   public void setTextureBurnt(Texture var1) {
      this.textureBurnt = var1;
   }

   public void setType(String var1) {
      this.type = var1;
      this.fullType = this.module + "." + var1;
   }

   public int getUses() {
      return 1;
   }

   public void setUses(int var1) {
   }

   public float getAge() {
      return this.Age;
   }

   public void setAge(float var1) {
      this.Age = var1;
   }

   public float getLastAged() {
      return this.LastAged;
   }

   public void setLastAged(float var1) {
      this.LastAged = var1;
   }

   public void updateAge() {
   }

   public void setAutoAge() {
   }

   public boolean isIsCookable() {
      return this.IsCookable;
   }

   public boolean isCookable() {
      return this.IsCookable;
   }

   public void setIsCookable(boolean var1) {
      this.IsCookable = var1;
   }

   public float getCookingTime() {
      return this.CookingTime;
   }

   public void setCookingTime(float var1) {
      this.CookingTime = var1;
   }

   public float getMinutesToCook() {
      return this.MinutesToCook;
   }

   public void setMinutesToCook(float var1) {
      this.MinutesToCook = var1;
   }

   public float getMinutesToBurn() {
      return this.MinutesToBurn;
   }

   public void setMinutesToBurn(float var1) {
      this.MinutesToBurn = var1;
   }

   public boolean isCooked() {
      return this.Cooked;
   }

   public void setCooked(boolean var1) {
      this.Cooked = var1;
   }

   public boolean isBurnt() {
      return this.Burnt;
   }

   public void setBurnt(boolean var1) {
      this.Burnt = var1;
   }

   public int getOffAge() {
      return this.OffAge;
   }

   public void setOffAge(int var1) {
      this.OffAge = var1;
   }

   public int getOffAgeMax() {
      return this.OffAgeMax;
   }

   public void setOffAgeMax(int var1) {
      this.OffAgeMax = var1;
   }

   public float getWeight() {
      return this.Weight;
   }

   public void setWeight(float var1) {
      this.Weight = var1;
   }

   public float getActualWeight() {
      return this.getDisplayName().equals(this.getFullType()) ? 0.0F : this.ActualWeight;
   }

   public void setActualWeight(float var1) {
      this.ActualWeight = var1;
   }

   public String getWorldTexture() {
      return this.WorldTexture;
   }

   public void setWorldTexture(String var1) {
      this.WorldTexture = var1;
   }

   public String getDescription() {
      return this.Description;
   }

   public void setDescription(String var1) {
      this.Description = var1;
   }

   public int getCondition() {
      return this.Condition;
   }

   public void setCondition(int var1, boolean var2) {
      var1 = Math.max(0, var1);
      if (this.Condition > 0 && var1 <= 0 && var2 && this.getBreakSound() != null && !this.getBreakSound().isEmpty() && IsoPlayer.getInstance() != null) {
         IsoPlayer.getInstance().playSound(this.getBreakSound());
      }

      this.Condition = var1;
      this.setBroken(var1 <= 0);
   }

   public void setCondition(int var1) {
      this.setCondition(var1, true);
   }

   public String getOffString() {
      return this.OffString;
   }

   public void setOffString(String var1) {
      this.OffString = var1;
   }

   public String getCookedString() {
      return this.CookedString;
   }

   public void setCookedString(String var1) {
      this.CookedString = var1;
   }

   public String getUnCookedString() {
      return this.UnCookedString;
   }

   public void setUnCookedString(String var1) {
      this.UnCookedString = var1;
   }

   public String getBurntString() {
      return this.BurntString;
   }

   public void setBurntString(String var1) {
      this.BurntString = var1;
   }

   public String getModule() {
      return this.module;
   }

   public void setModule(String var1) {
      this.module = var1;
      this.fullType = var1 + "." + this.type;
   }

   public boolean isAlwaysWelcomeGift() {
      return this.getScriptItem().isAlwaysWelcomeGift();
   }

   public boolean isCanBandage() {
      return this.getScriptItem().isCanBandage();
   }

   public float getBoredomChange() {
      return this.boredomChange;
   }

   public void setBoredomChange(float var1) {
      this.boredomChange = var1;
   }

   public float getUnhappyChange() {
      return this.unhappyChange;
   }

   public void setUnhappyChange(float var1) {
      this.unhappyChange = var1;
   }

   public float getStressChange() {
      return this.stressChange;
   }

   public void setStressChange(float var1) {
      this.stressChange = var1;
   }

   public ArrayList getTags() {
      return this.ScriptItem.getTags();
   }

   public boolean hasTag(String var1) {
      ArrayList var2 = this.getTags();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         if (((String)var2.get(var3)).equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }

   public ArrayList getTaken() {
      return this.Taken;
   }

   public void setTaken(ArrayList var1) {
      this.Taken = var1;
   }

   public IsoDirections getPlaceDir() {
      return this.placeDir;
   }

   public void setPlaceDir(IsoDirections var1) {
      this.placeDir = var1;
   }

   public IsoDirections getNewPlaceDir() {
      return this.newPlaceDir;
   }

   public void setNewPlaceDir(IsoDirections var1) {
      this.newPlaceDir = var1;
   }

   public void setReplaceOnUseOn(String var1) {
      this.ReplaceOnUseOn = var1;
   }

   public String getReplaceOnUseOn() {
      return this.ReplaceOnUseOn;
   }

   public String getReplaceOnUseOnString() {
      String var1 = this.getReplaceOnUseOn();
      if (var1.split("-")[0].trim().contains("WaterSource")) {
         var1 = var1.split("-")[1];
         if (!var1.contains(".")) {
            var1 = this.getModule() + "." + var1;
         }
      }

      return var1;
   }

   public void setIsWaterSource(boolean var1) {
      this.IsWaterSource = var1;
   }

   public boolean isWaterSource() {
      return this.IsWaterSource;
   }

   boolean CanStackNoTemp(InventoryItem var1) {
      return false;
   }

   public void CopyModData(KahluaTable var1) {
      this.copyModData(var1);
   }

   public void copyModData(KahluaTable var1) {
      if (this.table != null) {
         this.table.wipe();
      }

      if (var1 != null) {
         LuaManager.copyTable(this.getModData(), var1);
      }
   }

   public int getCount() {
      return this.Count;
   }

   public void setCount(int var1) {
      this.Count = var1;
   }

   public boolean isActivated() {
      return this.activated;
   }

   public void setActivated(boolean var1) {
      this.activated = var1;
   }

   public void setCanBeActivated(boolean var1) {
      this.canBeActivated = var1;
   }

   public boolean canBeActivated() {
      return this.canBeActivated;
   }

   public void setLightStrength(float var1) {
      this.lightStrength = var1;
   }

   public float getLightStrength() {
      return this.lightStrength;
   }

   public boolean isTorchCone() {
      return this.isTorchCone;
   }

   public void setTorchCone(boolean var1) {
      this.isTorchCone = var1;
   }

   public float getTorchDot() {
      return this.getScriptItem().torchDot;
   }

   public int getLightDistance() {
      return this.lightDistance;
   }

   public void setLightDistance(int var1) {
      this.lightDistance = var1;
   }

   public boolean canEmitLight() {
      if (this.getLightStrength() <= 0.0F) {
         return false;
      } else {
         Drainable var1 = (Drainable)Type.tryCastTo(this, Drainable.class);
         return var1 == null || !(var1.getUsedDelta() <= 0.0F);
      }
   }

   public boolean isEmittingLight() {
      if (!this.canEmitLight()) {
         return false;
      } else {
         return !this.canBeActivated() || this.isActivated();
      }
   }

   public boolean canStoreWater() {
      return this.CanStoreWater;
   }

   public float getFatigueChange() {
      return this.fatigueChange;
   }

   public void setFatigueChange(float var1) {
      this.fatigueChange = var1;
   }

   public float getCurrentCondition() {
      Float var1 = (float)this.Condition / (float)this.ConditionMax;
      return Float.valueOf(var1 * 100.0F);
   }

   public void setColor(Color var1) {
      this.col = var1;
   }

   public Color getColor() {
      return this.col;
   }

   public ColorInfo getColorInfo() {
      return new ColorInfo(this.col.getRedFloat(), this.col.getGreenFloat(), this.col.getBlueFloat(), this.col.getAlphaFloat());
   }

   public boolean isTwoHandWeapon() {
      return this.getScriptItem().TwoHandWeapon;
   }

   public String getCustomMenuOption() {
      return this.customMenuOption;
   }

   public void setCustomMenuOption(String var1) {
      this.customMenuOption = var1;
   }

   public void setTooltip(String var1) {
      this.tooltip = var1;
   }

   public String getTooltip() {
      return this.tooltip;
   }

   public String getDisplayCategory() {
      return this.displayCategory;
   }

   public void setDisplayCategory(String var1) {
      this.displayCategory = var1;
   }

   public int getHaveBeenRepaired() {
      return this.haveBeenRepaired;
   }

   public void setHaveBeenRepaired(int var1) {
      this.haveBeenRepaired = var1;
   }

   public boolean isBroken() {
      return this.broken;
   }

   public void setBroken(boolean var1) {
      this.broken = var1;
   }

   public String getDisplayName() {
      return this.name;
   }

   public boolean isTrap() {
      return this.getScriptItem().Trap;
   }

   public void addExtraItem(String var1) {
      if (this.extraItems == null) {
         this.extraItems = new ArrayList();
      }

      this.extraItems.add(var1);
   }

   public boolean haveExtraItems() {
      return this.extraItems != null;
   }

   public ArrayList getExtraItems() {
      return this.extraItems;
   }

   public boolean isCustomName() {
      return this.customName;
   }

   public void setCustomName(boolean var1) {
      this.customName = var1;
   }

   public boolean isFishingLure() {
      return this.getScriptItem().FishingLure;
   }

   public void copyConditionModData(InventoryItem var1) {
      if (var1.hasModData()) {
         KahluaTableIterator var2 = var1.getModData().iterator();

         while(var2.advance()) {
            if (var2.getKey() instanceof String && ((String)var2.getKey()).startsWith("condition:")) {
               this.getModData().rawset(var2.getKey(), var2.getValue());
            }
         }
      }

   }

   public void setConditionFromModData(InventoryItem var1) {
      if (var1.hasModData()) {
         Object var2 = var1.getModData().rawget("condition:" + this.getType());
         if (var2 != null && var2 instanceof Double) {
            this.setCondition((int)Math.round((Double)var2 * (double)this.getConditionMax()));
         }
      }

   }

   public String getBreakSound() {
      return this.breakSound;
   }

   public void setBreakSound(String var1) {
      this.breakSound = var1;
   }

   public void setBeingFilled(boolean var1) {
      this.beingFilled = var1;
   }

   public boolean isBeingFilled() {
      return this.beingFilled;
   }

   public boolean isAlcoholic() {
      return this.alcoholic;
   }

   public void setAlcoholic(boolean var1) {
      this.alcoholic = var1;
   }

   public float getAlcoholPower() {
      return this.alcoholPower;
   }

   public void setAlcoholPower(float var1) {
      this.alcoholPower = var1;
   }

   public float getBandagePower() {
      return this.bandagePower;
   }

   public void setBandagePower(float var1) {
      this.bandagePower = var1;
   }

   public float getReduceInfectionPower() {
      return this.ReduceInfectionPower;
   }

   public void setReduceInfectionPower(float var1) {
      this.ReduceInfectionPower = var1;
   }

   public final void saveWithSize(ByteBuffer var1, boolean var2) throws IOException {
      int var3 = var1.position();
      var1.putInt(0);
      int var4 = var1.position();
      this.save(var1, var2);
      int var5 = var1.position();
      var1.position(var3);
      var1.putInt(var5 - var4);
      var1.position(var5);
   }

   public boolean isCustomWeight() {
      return this.customWeight;
   }

   public void setCustomWeight(boolean var1) {
      this.customWeight = var1;
   }

   public float getContentsWeight() {
      if (!StringUtils.isNullOrEmpty(this.getAmmoType())) {
         Item var1 = ScriptManager.instance.FindItem(this.getAmmoType());
         if (var1 != null) {
            return var1.getActualWeight() * (float)this.getCurrentAmmoCount();
         }
      }

      return 0.0F;
   }

   public float getHotbarEquippedWeight() {
      return (this.getActualWeight() + this.getContentsWeight()) * 0.7F;
   }

   public float getEquippedWeight() {
      return (this.getActualWeight() + this.getContentsWeight()) * 0.3F;
   }

   public float getUnequippedWeight() {
      return this.getActualWeight() + this.getContentsWeight();
   }

   public boolean isEquipped() {
      return this.getContainer() != null && this.getContainer().getParent() instanceof IsoGameCharacter ? ((IsoGameCharacter)this.getContainer().getParent()).isEquipped(this) : false;
   }

   public int getKeyId() {
      return this.keyId;
   }

   public void setKeyId(int var1) {
      this.keyId = var1;
   }

   public boolean isTaintedWater() {
      return this.taintedWater;
   }

   public void setTaintedWater(boolean var1) {
      this.taintedWater = var1;
   }

   public boolean isRemoteController() {
      return this.remoteController;
   }

   public void setRemoteController(boolean var1) {
      this.remoteController = var1;
   }

   public boolean canBeRemote() {
      return this.canBeRemote;
   }

   public void setCanBeRemote(boolean var1) {
      this.canBeRemote = var1;
   }

   public int getRemoteControlID() {
      return this.remoteControlID;
   }

   public void setRemoteControlID(int var1) {
      this.remoteControlID = var1;
   }

   public int getRemoteRange() {
      return this.remoteRange;
   }

   public void setRemoteRange(int var1) {
      this.remoteRange = var1;
   }

   public String getExplosionSound() {
      return this.explosionSound;
   }

   public void setExplosionSound(String var1) {
      this.explosionSound = var1;
   }

   public String getCountDownSound() {
      return this.countDownSound;
   }

   public void setCountDownSound(String var1) {
      this.countDownSound = var1;
   }

   public float getColorRed() {
      return this.colorRed;
   }

   public void setColorRed(float var1) {
      this.colorRed = var1;
   }

   public float getColorGreen() {
      return this.colorGreen;
   }

   public void setColorGreen(float var1) {
      this.colorGreen = var1;
   }

   public float getColorBlue() {
      return this.colorBlue;
   }

   public void setColorBlue(float var1) {
      this.colorBlue = var1;
   }

   public String getEvolvedRecipeName() {
      return this.evolvedRecipeName;
   }

   public void setEvolvedRecipeName(String var1) {
      this.evolvedRecipeName = var1;
   }

   public float getMetalValue() {
      return this.metalValue;
   }

   public void setMetalValue(float var1) {
      this.metalValue = var1;
   }

   public float getItemHeat() {
      return this.itemHeat;
   }

   public void setItemHeat(float var1) {
      if (var1 > 2.0F) {
         var1 = 2.0F;
      }

      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.itemHeat = var1;
   }

   public float getInvHeat() {
      return 1.0F - this.itemHeat;
   }

   public float getMeltingTime() {
      return this.meltingTime;
   }

   public void setMeltingTime(float var1) {
      if (var1 > 100.0F) {
         var1 = 100.0F;
      }

      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.meltingTime = var1;
   }

   public String getWorker() {
      return this.worker;
   }

   public void setWorker(String var1) {
      this.worker = var1;
   }

   public long getID() {
      return this.id;
   }

   public void setID(long var1) {
      this.id = var1;
   }

   public boolean isWet() {
      return this.isWet;
   }

   public void setWet(boolean var1) {
      this.isWet = var1;
   }

   public float getWetCooldown() {
      return this.wetCooldown;
   }

   public void setWetCooldown(float var1) {
      this.wetCooldown = var1;
   }

   public String getItemWhenDry() {
      return this.itemWhenDry;
   }

   public void setItemWhenDry(String var1) {
      this.itemWhenDry = var1;
   }

   public boolean isFavorite() {
      return this.favorite;
   }

   public void setFavorite(boolean var1) {
      this.favorite = var1;
   }

   public ArrayList getRequireInHandOrInventory() {
      return this.requireInHandOrInventory;
   }

   public void setRequireInHandOrInventory(ArrayList var1) {
      this.requireInHandOrInventory = var1;
   }

   public boolean isCustomColor() {
      return this.customColor;
   }

   public void setCustomColor(boolean var1) {
      this.customColor = var1;
   }

   public String getMap() {
      return this.map;
   }

   public void setMap(String var1) {
      this.map = var1;
   }

   public void doBuildingtStash() {
      if (this.stashMap != null) {
         if (GameClient.bClient) {
            GameClient.sendBuildingStashToDo(this.stashMap);
         } else {
            StashSystem.prepareBuildingStash(this.stashMap);
         }
      }

   }

   public void setStashMap(String var1) {
      this.stashMap = var1;
   }

   public int getMechanicType() {
      return this.getScriptItem().vehicleType;
   }

   public float getItemCapacity() {
      return this.itemCapacity;
   }

   public void setItemCapacity(float var1) {
      this.itemCapacity = var1;
   }

   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   public void setMaxCapacity(int var1) {
      this.maxCapacity = var1;
   }

   public boolean isConditionAffectsCapacity() {
      return this.ScriptItem != null && this.ScriptItem.isConditionAffectsCapacity();
   }

   public float getBrakeForce() {
      return this.brakeForce;
   }

   public void setBrakeForce(float var1) {
      this.brakeForce = var1;
   }

   public int getChanceToSpawnDamaged() {
      return this.chanceToSpawnDamaged;
   }

   public void setChanceToSpawnDamaged(int var1) {
      this.chanceToSpawnDamaged = var1;
   }

   public float getConditionLowerNormal() {
      return this.conditionLowerNormal;
   }

   public void setConditionLowerNormal(float var1) {
      this.conditionLowerNormal = var1;
   }

   public float getConditionLowerOffroad() {
      return this.conditionLowerOffroad;
   }

   public void setConditionLowerOffroad(float var1) {
      this.conditionLowerOffroad = var1;
   }

   public float getWheelFriction() {
      return this.wheelFriction;
   }

   public void setWheelFriction(float var1) {
      this.wheelFriction = var1;
   }

   public float getSuspensionDamping() {
      return this.suspensionDamping;
   }

   public void setSuspensionDamping(float var1) {
      this.suspensionDamping = var1;
   }

   public float getSuspensionCompression() {
      return this.suspensionCompression;
   }

   public void setSuspensionCompression(float var1) {
      this.suspensionCompression = var1;
   }

   public void setInfected(boolean var1) {
      this.zombieInfected = var1;
   }

   public boolean isInfected() {
      return this.zombieInfected;
   }

   public float getEngineLoudness() {
      return this.engineLoudness;
   }

   public void setEngineLoudness(float var1) {
      this.engineLoudness = var1;
   }

   public String getStaticModel() {
      return this.getScriptItem().getStaticModel();
   }

   public ArrayList getIconsForTexture() {
      return this.iconsForTexture;
   }

   public void setIconsForTexture(ArrayList var1) {
      this.iconsForTexture = var1;
   }

   public float getScore(SurvivorDesc var1) {
      return 0.0F;
   }

   public IsoGameCharacter getPreviousOwner() {
      return this.previousOwner;
   }

   public void setPreviousOwner(IsoGameCharacter var1) {
      this.previousOwner = var1;
   }

   public Item getScriptItem() {
      return this.ScriptItem;
   }

   public void setScriptItem(Item var1) {
      this.ScriptItem = var1;
   }

   public ItemType getCat() {
      return this.cat;
   }

   public void setCat(ItemType var1) {
      this.cat = var1;
   }

   public ItemContainer getContainer() {
      return this.container;
   }

   public void setContainer(ItemContainer var1) {
      this.container = var1;
   }

   public ArrayList getBloodClothingType() {
      return this.bloodClothingType;
   }

   public void setBloodClothingType(ArrayList var1) {
      this.bloodClothingType = var1;
   }

   public void setBlood(BloodBodyPartType var1, float var2) {
      ItemVisual var3 = this.getVisual();
      if (var3 != null) {
         var3.setBlood(var1, var2);
      }

   }

   public float getBlood(BloodBodyPartType var1) {
      ItemVisual var2 = this.getVisual();
      return var2 != null ? var2.getBlood(var1) : 0.0F;
   }

   public void setDirt(BloodBodyPartType var1, float var2) {
      ItemVisual var3 = this.getVisual();
      if (var3 != null) {
         var3.setDirt(var1, var2);
      }

   }

   public float getDirt(BloodBodyPartType var1) {
      ItemVisual var2 = this.getVisual();
      return var2 != null ? var2.getDirt(var1) : 0.0F;
   }

   public String getClothingItemName() {
      return this.getScriptItem().ClothingItem;
   }

   public int getStashChance() {
      return this.stashChance;
   }

   public void setStashChance(int var1) {
      this.stashChance = var1;
   }

   public String getEatType() {
      return this.getScriptItem().eatType;
   }

   public boolean isUseWorldItem() {
      return this.getScriptItem().UseWorldItem;
   }

   public boolean isHairDye() {
      return this.getScriptItem().hairDye;
   }

   public String getAmmoType() {
      return this.ammoType;
   }

   public void setAmmoType(String var1) {
      this.ammoType = var1;
   }

   public int getMaxAmmo() {
      return this.maxAmmo;
   }

   public void setMaxAmmo(int var1) {
      this.maxAmmo = var1;
   }

   public int getCurrentAmmoCount() {
      return this.currentAmmoCount;
   }

   public void setCurrentAmmoCount(int var1) {
      this.currentAmmoCount = var1;
   }

   public boolean hasBlood() {
      if (this instanceof Clothing) {
         if (this.getBloodClothingType() == null || this.getBloodClothingType().isEmpty()) {
            return false;
         }

         ArrayList var1 = BloodClothingType.getCoveredParts(this.getBloodClothingType());
         if (var1 == null) {
            return false;
         }

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            if (this.getBlood((BloodBodyPartType)var1.get(var2)) > 0.0F) {
               return true;
            }
         }
      } else {
         if (this instanceof HandWeapon) {
            return ((HandWeapon)this).getBloodLevel() > 0.0F;
         }

         if (this instanceof InventoryContainer) {
            return ((InventoryContainer)this).getBloodLevel() > 0.0F;
         }
      }

      return false;
   }

   public boolean hasDirt() {
      if (this instanceof Clothing) {
         if (this.getBloodClothingType() == null || this.getBloodClothingType().isEmpty()) {
            return false;
         }

         ArrayList var1 = BloodClothingType.getCoveredParts(this.getBloodClothingType());
         if (var1 == null) {
            return false;
         }

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            if (this.getDirt((BloodBodyPartType)var1.get(var2)) > 0.0F) {
               return true;
            }
         }
      }

      return false;
   }

   public String getAttachmentType() {
      return this.attachmentType;
   }

   public void setAttachmentType(String var1) {
      this.attachmentType = var1;
   }

   public int getAttachedSlot() {
      return this.attachedSlot;
   }

   public void setAttachedSlot(int var1) {
      this.attachedSlot = var1;
   }

   public ArrayList getAttachmentsProvided() {
      return this.attachmentsProvided;
   }

   public void setAttachmentsProvided(ArrayList var1) {
      this.attachmentsProvided = var1;
   }

   public String getAttachedSlotType() {
      return this.attachedSlotType;
   }

   public void setAttachedSlotType(String var1) {
      this.attachedSlotType = var1;
   }

   public String getAttachmentReplacement() {
      return this.attachmentReplacement;
   }

   public void setAttachmentReplacement(String var1) {
      this.attachmentReplacement = var1;
   }

   public String getAttachedToModel() {
      return this.attachedToModel;
   }

   public void setAttachedToModel(String var1) {
      this.attachedToModel = var1;
   }

   public String getFabricType() {
      return this.getScriptItem().fabricType;
   }

   public String getStringItemType() {
      Item var1 = ScriptManager.instance.FindItem(this.getFullType());
      if (var1 != null && var1.getType() != null) {
         if (var1.getType() == Item.Type.Food) {
            return var1.CannedFood ? "CannedFood" : "Food";
         } else if ("Ammo".equals(var1.getDisplayCategory())) {
            return "Ammo";
         } else if (var1.getType() == Item.Type.Weapon && !var1.isRanged()) {
            return "MeleeWeapon";
         } else if (var1.getType() != Item.Type.WeaponPart && (var1.getType() != Item.Type.Weapon || !var1.isRanged()) && (var1.getType() != Item.Type.Normal || StringUtils.isNullOrEmpty(var1.getAmmoType()))) {
            if (var1.getType() == Item.Type.Literature) {
               return "Literature";
            } else if (var1.Medical) {
               return "Medical";
            } else if (var1.SurvivalGear) {
               return "SurvivalGear";
            } else {
               return var1.MechanicsItem ? "Mechanic" : "Other";
            }
         } else {
            return "RangedWeapon";
         }
      } else {
         return "Other";
      }
   }

   public boolean isProtectFromRainWhileEquipped() {
      return this.getScriptItem().ProtectFromRainWhenEquipped;
   }

   public boolean isEquippedNoSprint() {
      return this.getScriptItem().equippedNoSprint;
   }

   public String getBodyLocation() {
      return this.getScriptItem().BodyLocation;
   }

   public String getMakeUpType() {
      return this.getScriptItem().makeUpType;
   }

   public boolean isHidden() {
      return this.getScriptItem().isHidden();
   }

   public String getConsolidateOption() {
      return this.getScriptItem().consolidateOption;
   }

   public ArrayList getClothingItemExtra() {
      return this.getScriptItem().clothingItemExtra;
   }

   public ArrayList getClothingItemExtraOption() {
      return this.getScriptItem().clothingItemExtraOption;
   }

   public String getWorldStaticItem() {
      return this.getScriptItem().worldStaticModel;
   }
}
