local items = {
	Coco_WaterGallonEmpty = {"CocoLiquidOverhaulItems.Coco_WaterGallonEmpty", "Coco_Liquid_Container"},
	Coco_WaterGallonFull = {"CocoLiquidOverhaulItems.Coco_WaterGallonFull", "Coco_Liquid_Container"},
	Coco_WaterGallonPetrol = {"CocoLiquidOverhaulItems.Coco_WaterGallonPetrol", "Coco_Liquid_Container"}
}

local function CocoLiquidOverhaul_TweakItems()
	for k, v in pairs(items) do
		local item = ScriptManager.instance:getItem(v[1])
		if item ~= nil then
			item:DoParam("displaycategory" .. " = " .. v[2])
		end
	end
end

-- OnGameBoot instead on OnLoad does fix the category name reset
Events.OnGameBoot.Add(CocoLiquidOverhaul_TweakItems)
--Events.OnLoad.Add(CocoLiquidOverhaul_TweakItems);
