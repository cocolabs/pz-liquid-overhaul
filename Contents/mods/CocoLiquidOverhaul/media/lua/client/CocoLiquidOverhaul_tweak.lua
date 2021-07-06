if getActivatedMods():contains("ItemTweakerAPI") then
	require("ItemTweaker_Core");
else return end;

-- Coco_WaterGallonEmpty
TweakItem("CocoLiquidOverhaulItems.Coco_WaterGallonEmpty","DisplayCategory","Coco_Liquid_Container");

-- Coco_WaterGallonFull
TweakItem("CocoLiquidOverhaulItems.Coco_WaterGallonFull","DisplayCategory","Coco_Liquid_Container");

-- Coco_WaterGallonPetrol
TweakItem("CocoLiquidOverhaulItems.Coco_WaterGallonPetrol","DisplayCategory","Coco_Liquid_Container");
