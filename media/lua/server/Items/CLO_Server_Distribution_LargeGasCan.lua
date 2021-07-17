require "Items/SuburbsDistributions"

-- ITEMS
--- CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan
--- CocoLiquidOverhaulItems.Coco_LargePetrolCan

-- LOCATIONS
--- all
--- shed
--- garagestorage
--- garage
--- mechanic
--- storageunit
--- gasstore
--- gasstorage
--- armyhanger
--- armyhanger

-- all
table.insert(SuburbsDistributions["all"]["bin"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["all"]["bin"].items, 0.5)

-- shed
table.insert(SuburbsDistributions["shed"]["other"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["shed"]["other"].items, 2)
table.insert(SuburbsDistributions["shed"]["other"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["shed"]["other"].items, 1.5)

-- garagestorage
table.insert(SuburbsDistributions["garagestorage"]["other"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["garagestorage"]["other"].items, 2)
table.insert(SuburbsDistributions["garagestorage"]["other"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["garagestorage"]["other"].items, 1.5)

-- garage
table.insert(SuburbsDistributions["garage"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["garage"]["metal_shelves"].items, 2)
table.insert(SuburbsDistributions["garage"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["garage"]["metal_shelves"].items, 1.5)

-- mechanic
table.insert(SuburbsDistributions["mechanic"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["mechanic"]["metal_shelves"].items, 1.5)
table.insert(SuburbsDistributions["mechanic"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["mechanic"]["metal_shelves"].items, 0.5)

-- storageunit
table.insert(SuburbsDistributions["storageunit"]["all"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["storageunit"]["all"].items, 2)
table.insert(SuburbsDistributions["storageunit"]["all"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["storageunit"]["all"].items, 1.5)

-- gasstore
table.insert(SuburbsDistributions["gasstore"]["shelves"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["gasstore"]["shelves"].items, 7.5)
table.insert(SuburbsDistributions["gasstore"]["shelves"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["gasstore"]["shelves"].items, 5)
table.insert(SuburbsDistributions["gasstore"]["counter"].items, "CocoLiquidOverhaulItems.Coco_LargeEmptyPetrolCan")
table.insert(SuburbsDistributions["gasstore"]["counter"].items, 1)

-- gasstorage
table.insert(SuburbsDistributions["gasstorage"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["gasstorage"]["metal_shelves"].items, 3)

-- armyhanger
table.insert(SuburbsDistributions["armyhanger"]["metal_shelves"].items, "CocoLiquidOverhaulItems.Coco_LargePetrolCan")
table.insert(SuburbsDistributions["armyhanger"]["metal_shelves"].items, 1)
