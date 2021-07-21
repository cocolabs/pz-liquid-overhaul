---@class Distributions
Distributions = Distributions or {};

local distributionTable = {

-- =====================
--    Room List (A-Z)   
-- =====================

    aesthetic = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="SalonHairTools", min=0, max=99},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="SalonTowels", min=0, max=99},
                {name="SalonHairCare", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="SalonHairCare", min=0, max=99},
            }
        }
    },
    
    aestheticstorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="SalonHairCare", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="SalonHairCare", min=0, max=99},
            }
        }
    },
    
    all = {
        displaycasebakery = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=12},
                {name="BakeryPie", min=1, max=12},
                {name="BakeryCake", min=1, max=12},
                {name="BakeryMisc", min=0, max=99},
            }
        },
        restaurantdisplay = {
            procedural = true,
            procList = {
                {name="ServingTrayBurgers", min=1, max=4},
                {name="ServingTrayChicken", min=1, max=4},
                {name="ServingTrayFries", min=1, max=4},
                {name="ServingTrayHotdogs", min=1, max=4},
                {name="ServingTrayPie", min=1, max=2},
                {name="ServingTrayPizza", min=1, max=4},
            }
        },
        plankstash = {
            procedural = true,
            procList = {
                {name="PlankStashMoney", min=0, max=1},
                {name="PlankStashWeapon", min=0, max=1},
            }
        },
        cashregister = {
            rolls = 5,
            items = {
                "Money", 2,
                "Money", 100,
                "Money", 100,
            }
        },
        campfire = {
            rolls = 0,
            items = {
                
            }
        },
        clothingdryer = {
            rolls = 0,
            items = {
                
            },
        },
        clothingwasher = {
            rolls = 0,
            items = {
                
            },
        },
        clothingrack = {
            procedural = true,
            procList = {
                {name="ClothingStoresDress", min=0, max=1},
                {name="ClothingStoresMan", min=0, max=3},
                {name="ClothingStoresWoman", min=0, max=10},
                {name="ClothingStoresShirts", min=0, max=3},
                {name="ClothingStoresPants", min=0, max=2},
                {name="ClothingStoresJumpers", min=0, max=2},
                {name="ClothingStoresJackets", min=0, max=1},
                {name="ClothingPoor", min=0, max=20, forceForZones="Poor"},
            }
        },
        freezer = {
            rolls = 5,
            items = {
                "Icecream", 5,
            }
        },
        dishescabinet = {
            rolls = 6,
            items = {
                "ButterKnife", 5,
                "BreadKnife", 5,
                "Spoon", 10,
                "Fork", 10,
                "Bowl", 10,
            }
        },
        dresser = { 
            rolls = 3,
            items = {
                "Lipstick", 4,
                "MakeupEyeshadow", 4,
                "MakeupFoundation", 4,
                "Necklace_Silver", 1,
                "NecklaceLong_Silver", 1,
                "Earring_LoopLrg_Silver", 1,
                "Earring_LoopMed_Silver", 1,
                "Earring_LoopSmall_Silver_Both", 1,
                "Earring_Stud_Silver", 1,
                "Ring_Left_RingFinger_Silver", 1,
                "Necklace_SilverCrucifix", 1,
                "Necklace_Gold", 0.5,
                "NecklaceLong_Gold", 0.5,
                "NoseStud_Gold", 0.5,
                "Earring_LoopLrg_Gold", 0.5,
                "Earring_LoopMed_Gold", 0.5,
                "Earring_LoopSmall_Gold_Both", 0.5,
                "Earring_Stud_Gold", 0.5,
                "Bracelet_ChainRightSilver", 1,
                "Bracelet_BangleRightSilver", 1,
                "Bracelet_ChainRightGold", 0.5,
                "Bracelet_BangleRightGold", 0.5,
                "Ring_Left_RingFinger_Gold", 0.5,
                "Locket", 1.5,
                "Comb", 4,
                "Doll", 1,
                "Pills", 0.3,
                "PillsBeta", 0.3,
                "PillsAntiDep", 0.3,
                "PillsVitamins", 0.3,
                "Cigarettes", 1,
                "Perfume", 4,
            }
        },
        postbox = {
            rolls = 3,
            items = {
                "Newspaper", 2,
                "Magazine", 2,
                "FishingMag1", 0.2,
                "FishingMag2", 0.2,
                "HuntingMag1", 0.2,
                "HuntingMag2", 0.2,
                "HuntingMag3", 0.2,
                "HerbalistMag", 0.2,
                "CookingMag1", 0.2,
                "CookingMag2", 0.2,
                "ElectronicsMag1", 0.2,
                "ElectronicsMag2", 0.2,
                "ElectronicsMag3", 0.2,
                "ElectronicsMag4", 0.2,
                "ElectronicsMag5", 0.2,
                "MechanicMag1", 0.2,
                "MechanicMag2", 0.2,
                "MechanicMag3", 0.2,
                "EngineerMagazine1", 0.2,
                "EngineerMagazine2", 0.2,
                "MetalworkMag1", 0.2,
                "MetalworkMag2", 0.2,
                "MetalworkMag3", 0.2,
                "MetalworkMag4", 0.2,
            }
        },
        corn = {
            rolls = 2,
            items = {
                "Corn", 1,
                "Corn", 1,
                "Corn", 1,
            }
        },
        logs = {
            rolls = 1,
            items = {
                "Log", 100,
                "Log", 100,
                "Log", 7,
                "Log", 7,
                "Log", 7,
                "Log", 7,
                "Log", 7,
            }
        },
        locker = {
            procedural = true,
            procList = {
                {name="Locker", min=0, max=99},
                {name="LockerClassy", min=0, max=99, forceForZones="Rich"},
            }
        },
        fruitbusha = {
            rolls = 10,
            items = {
                "BerryBlack", 50,
            },
            noAutoAge = true,
        },
        fruitbushb = {
            rolls = 10,
            items = {
                "BerryBlue", 50,
            },
            noAutoAge = true,
        },
        fruitbushc = {
            rolls = 10,
            items = {
                "BerryGeneric1", 50,
            },
            noAutoAge = true,
        },
        fruitbushd = {
            rolls = 10,
            items = {
                "BerryGeneric2", 25,
                "BerryGeneric5", 25,
            },
            noAutoAge = true,
        },
        fruitbushe = {
            rolls = 10,
            items = {
                "BerryGeneric3", 25,
                "BerryGeneric4", 25,
            },
            noAutoAge = true,
        },
        inventorymale = {
            rolls = 1,
            items = {
                "MuldraughMap", 0.2,
                "WestpointMap", 0.2,
                "MarchRidgeMap",0.1,
                "RosewoodMap",0.1,
                "RiversideMap",0.1,
                "Wallet", 1,
                "Wallet2", 1,
                "Wallet3", 1,
                "Wallet4", 1,
                "Locket", 1,
                "Comb", 1,
                "Magazine", 1,
                "Newspaper", 1,
                "Notebook", 1,
                "Pencil", 1,
                "Pen", 0.1,
                "BluePen", 0.1,
                "RedPen", 0.1,
                "Pills", 0.1,
                "PillsBeta", 0.1,
                "PillsAntiDep", 0.1,
                "PillsVitamins", 0.1,
                "Pistol", 0.05,
                "Pistol2", 0.02,
                "Revolver_Short", 0.05,
                "Cologne", 0.2,
                "CreditCard", 1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
                "Cigarettes", 0.3,
                "Matches", 0.4,
                "Lighter", 0.4,
            }
        },
        inventoryfemale = {
            rolls = 1,
            items = {
                "MuldraughMap", 0.2,
                "WestpointMap", 0.2,
                "MarchRidgeMap",0.1,
                "RosewoodMap",0.1,
                "RiversideMap",0.1,
                "Lipstick", 1,
                "MakeupEyeshadow", 1,
                "MakeupFoundation", 1,
                "Earbuds", 1,
                "Locket", 1,
                "Comb", 1,
                "Magazine", 1,
                "Newspaper", 1,
                "Notebook", 1,
                "Pencil", 1,
                "Pen", 1,
                "BluePen", 1,
                "RedPen", 1,
                "Doll", 0.5,
                "Pills", 0.1,
                "PillsBeta", 0.1,
                "PillsAntiDep", 0.1,
                "PillsVitamins", 0.1,
                "Pistol", 0.05,
                "Pistol2", 0.02,
                "Revolver_Short", 0.05,
                "CreditCard", 1,
                "Perfume", 0.5,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
                "Cigarettes", 0.3,
                "Matches", 0.4,
                "Lighter", 0.4,
            }
        },
        shelvesmag = {
            procedural = true,
            procList = {
                {name="MagazineRackMaps", min=1, max=1},
                {name="MagazineRackNewspaper", min=1, max=1},
                {name="MagazineRackMixed", min=0, max=99},
            }
        },
        desk = {
            rolls = 1,
            items = {
                "PillsVitamins", 1,
                "Magazine", 2,
                "Newspaper", 2,
                "Book", 2,
                "ComicBook", 1,
                "Cigarettes", 1,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
            },
            junk = {
                rolls = 1,
                items = {
                    "MakeupEyeshadow", 1,
                    "MakeupFoundation", 1,
                    "Razor", 1,
                    "CardDeck", 1,
                    "Comb", 2,
                    "Paperclip", 1,
                    "Disc", 1,
                    "CDplayer", 0.4,
                    "Doll", 1,
                    "Toothbrush",1,
                    "Lipstick", 1,
                    "SheetPaper2", 20,
                    "Notebook", 2,
                    "Pencil", 15,
                    "RubberBand", 7,
                    "Eraser", 7,
                    "Pen", 30,
                    "BluePen", 10,
                    "RedPen", 10,
                    "Scissors", 3,
                    "Cube", 1,
                    "Cologne", 1,
                    "Perfume", 1,
                }
            }
        },
        filingcabinet = {
            rolls = 1,
            items = {
                "Magazine", 4,
                "Newspaper", 4,
                "Book", 4,
                "ComicBook", 2,
            },
            junk = {
                rolls = 1,
                items = {
                    "SheetPaper2", 20,
                    "SheetPaper2", 20,
                    "SheetPaper2", 20,
                    "SheetPaper2", 20,
                    "Notebook", 4,
                }
            }
        },
        stove = {
            rolls = 0,
            items = {
            }
        },
        microwave = {
            rolls = 0,
            items = {
            }
        },
        medicine = {
            rolls = 3,
            items = {
                "Bandaid", 7,
                "Pills", 7,
                "PillsBeta", 7,
                "PillsAntiDep", 7,
                "PillsSleepingTablets", 7,
                "PillsVitamins", 7,
                "Tweezers", 5,
                "Antibiotics", 5,
            }
        },
        wardrobe = {
            procedural = true,
            procList = {
                {name="WardrobeMan", min=0, max=2},
                {name="WardrobeManClassy", min=0, max=1},
                {name="WardrobeWoman", min=0, max=2},
            }
        },
        crate = {
            rolls = 1,
            items = {
                "NailsBox", 1,
                "PaperclipBox", 0.5,
                "DuctTape", 0.8,
                "Glue", 0.8,
                "Scotchtape", 0.8,
                "Twine", 0.8,
                "Thread", 1.5,
                "Needle", 0.8,
                "Woodglue", 0.8,
                "Rope", 0.8,
                "NailsBox", 4,
                "NailsBox", 4,
                "NailsBox", 4,
                "Hammer", 4,
                "Wire", 2,
                "Crowbar", 1,
                "Tarp", 1,
                "Saw", 1,
                "GardenSaw", 1,
                "Plank", 3,
                "Plank", 3,
                "Battery", 4,
                "Lighter", 4,
                "camping.TentPeg", 7,
                "Sledgehammer", 0.4,
                "Sledgehammer2", 0.4,
                "Paperclip", 0.8,
                "Axe", 0.4,
                "WoodAxe", 0.4,
                "farming.CarrotBagSeed", 1.5,
                "farming.BroccoliBagSeed", 1.5,
                "farming.RedRadishBagSeed", 1.5,
                "farming.StrewberrieBagSeed", 1.5,
                "farming.TomatoBagSeed", 1.5,
                "farming.PotatoBagSeed", 1.5,
                "farming.CabbageBagSeed", 1.5,
                "farming.HandShovel", 2.7,
                "HandScythe", 0.5,
                "HandFork", 0.5,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "SnowShovel", 0.5,
                "farming.WateredCan", 1,
                "Paintbrush", 1.5,
                "PaintBlue", 0.8,
                "PaintBlack", 0.8,
                "PaintRed", 0.8,
                "PaintBrown", 0.8,
                "PaintCyan", 0.8,
                "PaintGreen", 0.8,
                "PaintGrey", 0.8,
                "PaintLightBlue", 0.8,
                "PaintLightBrown", 0.8,
                "PaintOrange", 0.8,
                "PaintPink", 0.8,
                "PaintPurple", 0.8,
                "PaintTurquoise",0.8,
                "PaintWhite", 0.8,
                "PaintYellow", 0.8,
                "PlasterPowder", 2,
                "ConcretePowder", 1,
                "BucketEmpty", 2,
                "Shotgun", 0.5,
                "DoubleBarrelShotgun", 0.2,
                "ShotgunShells", 1,
                "ShotgunShells", 1,
                "ShotgunShells", 1,
                "ShotgunShells", 0.5,
                "ShotgunShells", 0.5,
                "Torch", 1,
                "HandTorch", 1,
                "BarbedWire", 1,
                "Sandbag", 0.5,
                "Gravelbag", 0.5,
                "EmptySandbag", 2.5,
                "Fertilizer", 0.5,
                "Charcoal", 6,
                --                "BallPeenHammer", 0.01,
                --                "Tongs", 0.01,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
                "Radio.HamRadio1",0.005,
                "BlowTorch", 0.8,
                "WeldingRods", 2,
                "SheetMetal", 2,
                "SmallSheetMetal", 2.4,
                "MetalPipe", 2,
                "MetalBar", 1.2,
                "WeldingMask",0.7,
                "Wrench", 0.5,
                "LugWrench",0.4,
                "Jack", 0.2,
                "TirePump", 0.2,
                "LeadPipe", 0.4,
                "HandAxe", 0.2,
                "PipeWrench", 0.4,
                "Plunger", 0.5,
                "ClubHammer", 0.3,
                "WoodenMallet", 0.3,
            }
        },
        counter = {
            rolls = 1,
            items = {
                "Battery", 2,
                "Lighter", 2,
                "Torch", 2,
                "HandTorch", 3,
                "HuntingKnife", 0.5,
                "DeadRat", 0.5,
                "DeadMouse", 0.5,
                "farming.CarrotBagSeed", 1,
                "farming.BroccoliBagSeed", 1,
                "farming.RedRadishBagSeed", 1,
                "farming.StrewberrieBagSeed", 1,
                "farming.TomatoBagSeed", 1,
                "farming.PotatoBagSeed", 1,
                "farming.CabbageBagSeed", 1,
                "Pistol", 0.3,
                "Revolver_Short", 0.3,
                "DoubleBarrelShotgun", 0.5,
                "ShotgunShells", 0.6,
                "BaseballBat", 0.3,
                "Bullets9mm", 0.6,
                "Bullets38", 0.6,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
            },
            junk = {
                rolls = 1,
                items = {
                    "Pen", 4,
                    "BluePen", 2,
                    "RedPen", 2,
                    "Pencil", 4,
                    "RubberBand", 2,
                    "Tissue", 4,
                    "Candle", 4,
                    "Matches", 4,
                    "Mugl", 7,
                    "EmptyJar", 1,
                    "JarLid", 1,
                    "Vinegar", 1,
                }
            }
        },
        sidetable = {
            rolls = 1,
            items = {
                "Battery", 2,
                "Lighter", 2,
                "Torch", 1,
                "HandTorch", 1.5,
                "Journal", 2,
                "Magazine", 4,
                "Newspaper", 4,
                "Book", 4,
                "ComicBook", 2,
                "Cigarettes", 4,
                "BookCarpentry1", 1,
                --                "BookBlacksmith1", 1,
                "BookFirstAid1", 1,
                "BookMetalWelding1", 1,
                "BookMetalWelding1", 1,
                "BookElectrician1", 1,
                "BookMechanic1" , 1,
                "BookFarming1", 1,
                "BookForaging1", 1,
                "BookCooking1", 1,
                "BookFishing1", 1,
                "BookTrapping1", 1,
                "BookCarpentry2", 0.5,
                "BookFarming2", 0.5,
                "BookForaging2", 0.5,
                "BookCooking2", 0.5,
                "BookFishing2", 0.5,
                "BookTrapping2", 0.5,
                --                "BookBlacksmith2", 0.5,
                "BookFirstAid2", 0.5,
                "BookMetalWelding2", 0.5,
                "BookElectrician2", 0.5,
                "BookMechanic2", 0.5,
                "Key1", 1,
                "Key2", 1,
                "Key3", 1,
                "Key4", 1,
                "Key5", 1,
                "HomeAlarm", 1,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
                "Remote", 8,
                "MuldraughMap", 0.05,
                "WestpointMap", 0.05,
                "MarchRidgeMap",0.01,
                "RosewoodMap",0.01,
                "RiversideMap",0.01,
                "FishingMag1", 0.7,
                "FishingMag2", 0.7,
                "HuntingMag1", 0.7,
                "HuntingMag2", 0.7,
                "HuntingMag3", 0.7,
                "HerbalistMag", 0.7,
                "CookingMag1", 0.7,
                "CookingMag2", 0.7,
                "ElectronicsMag1", 0.7,
                "ElectronicsMag2", 0.7,
                "ElectronicsMag3", 0.7,
                "ElectronicsMag4", 0.7,
                "ElectronicsMag5", 0.7,
                "MechanicMag1", 0.7,
                "MechanicMag2", 0.7,
                "MechanicMag3", 0.7,
                "EngineerMagazine1", 0.7,
                "EngineerMagazine2", 0.7,
                "MetalworkMag1", 0.7,
                "MetalworkMag2", 0.7,
                "MetalworkMag3", 0.7,
                "MetalworkMag4", 0.7,
            },
            junk = {
                rolls = 1,
                items = {
                    "Pen", 4,
                    "BluePen", 2,
                    "RedPen", 2,
                    "Paperclip", 1,
                    "Pencil", 4,
                    "RubberBand", 2,
                    "Eraser", 4,
                    "Tissue", 9,
                    "Candle", 4,
                    "Matches", 3,
                    "SheetPaper2", 4,
                    "Notebook", 4,
                }
            }
        },
        freezer = {
            rolls = 3,
            items = {
                "Icecream", 3,
                "Icecream", 3,
                "Icecream", 3,
                "Pizza", 1,
                "BurgerRecipe", 1,
                "Peas", 3,
                "Pie", 1,
                "Steak", 1,
                "Chicken", 1,
                "Salmon", 1,
                "Coldpack",2,
                "Coldpack",2,
                "PorkChop", 1,
                "MuttonChop", 1,
                "IcePick", 0.2,
            }
        },
        fridge = {
            rolls = 1,
            items = {
                "Milk", 8,
                "Milk", 4,
                "BeefJerky", 3,
                "Bread", 4,
                "Carrots", 4,
                "Steak", 3,
                "MeatPatty", 3,
                "Chicken", 3,
                "Ham", 3,
                "Salmon", 3,
                "Cheese", 4,
                "Watermelon", 4,
                "Broccoli", 4,
                "Pie", 3,
                "PopBottle", 3,
                "PopBottle", 3,
                "Butter", 3,
                "EggCarton", 4,
                "EggCarton", 2,
                "Apple", 4,
                "Orange", 4,
                "Banana", 4,
                "farming.RedRadish", 4,
                "farming.Strewberrie", 4,
                "Cherry", 4,
                "farming.Tomato", 4,
                "farming.Cabbage", 4,
                "Lettuce", 3,
                "Pickles", 3,
                "BellPepper", 3,
                "Peach", 3,
                "CakeSlice", 3,
                "Mustard", 2,
                "Ketchup", 2,
                "Processedcheese", 5,
                "Corndog", 2,
                "PorkChop", 3,
                "MuttonChop", 3,
                "Onion", 3,
                "Lemon", 3,
                "WaterBottleFull", 3,
                "WaterBottleFull", 3,
                "WaterBottleFull", 3,
                "Wine", 2,
                "Corn", 4,
                "Eggplant", 4,
                "Leek", 4,
                "Grapes", 4,
                "farming.Bacon", 4,
                "farming.MayonnaiseFull", 2,
                "farming.RemouladeFull", 0.5,
                "Worm", 0.3,
                "Avocado", 3,
                "Pineapple", 3,
                "Zucchini", 3,
                "Tofu", 2,
                "Yoghurt", 3,
                "JuiceBox", 1,
                "BeerCan", 1,
                "BeerCan", 1,
                "BeerCan", 1,
                "BeerBottle", 1,
            }
        },
        vendingsnack = {
            rolls = 4,
            items = {
                "Crisps", 4,
                "Crisps2", 4,
                "Crisps3", 4,
                "Crisps", 4,
                "Crisps2", 4,
                "Crisps3", 4,
                "Crisps", 4,
                "Crisps2", 4,
                "Crisps3", 4,
            }
        },
        vendingpop = {
            rolls = 4,
            items = {
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "PopBottle", 3,
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "PopBottle", 3,
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "PopBottle", 3,
            }
        },
        bin = {
            rolls = 1,
            items = {
                "Cockroach", 6,
                "Cockroach", 6,
                "Cockroach", 4,
                "Cockroach", 4,
                "DeadRat", 4,
                "DeadMouse", 2,
                "TinCanEmpty", 4,
                "TinCanEmpty", 4,
                "TinCanEmpty", 4,
                "TinCanEmpty", 4,
                "PopEmpty", 4,
                "PopEmpty", 4,
                "PopEmpty", 4,
                "PopEmpty", 4,
                "WhiskeyEmpty", 1,
                "BeerEmpty", 1,
                "WineEmpty", 1,
                "WineEmpty2", 1,
                "BandageDirty", 2,
                "BandageDirty", 2,
                "ElectronicsScrap", 2,
                "ScrapMetal", 2,
                "PopBottleEmpty", 2,
                "PopBottleEmpty", 2,
                "WaterBottleEmpty", 2,
                "WaterBottleEmpty", 2,
                "SmashedBottle", 1,
                "SmashedBottle", 1,
                "Garbagebag", 100,
            }
        },
        officedrawers = {
            rolls = 1,
            items = {
                "Battery", 3,
                "Nails", 3,
                "Nails", 3,
                "Nails", 2,
                "Lighter", 3,
                "Torch", 1,
                "HandTorch", 3,
                "WhiskeyFull", 2,
                "Chocolate", 3,
                "Pills", 1,
                "PillsBeta", 1,
                "PillsAntiDep", 1,
                "PillsSleepingTablets", 1,
                "Crisps", 2,
                "Crisps2", 2,
                "Crisps3", 2,
                "Pop", 2,
                "Pop2", 2,
                "Pop3", 2,
                "Magazine", 3,
                "Newspaper", 3,
                "Book", 3,
                "ComicBook", 1,
                "Lollipop", 2,
                "MintCandy", 2,
                "SheetPaper2", 3,
                "Matches", 2,
                "PillsVitamins", 1,
                "Magazine", 2,
                "Newspaper", 2,
                "Book", 2,
                "Cigarettes", 1,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "LetterOpener",1,
            },
            junk = {
                rolls = 1,
                items = {
                    "SheetPaper2", 2,
                    "Notebook", 2,
                    "Pencil", 3,
                    "Pen", 3,
                    "BluePen", 1,
                    "RedPen", 1,
                    "Scissors", 3,
                    "Cologne", 1,
                    "Perfume", 1,
                    "CardDeck", 1,
                    "Comb", 2,
                    "Toothbrush", 1,
                    "Notebook", 3,
                    "Razor", 1,
                    "Lipstick", 1,
                    "MakeupEyeshadow", 1,
                    "MakeupFoundation", 1,
                    "Pen", 4,
                    "BluePen", 2,
                    "RedPen", 2,
                    "Pencil", 4,
                    "RubberBand", 4,
                    "Eraser", 4,
                    "Paperclip", 4,
                    "Paperclip", 4,
                    "Tissue", 3,
                }
            }
        },
        metal_shelves = {
            rolls = 3,
            items = {
                "NailsBox", 1,
                "PaperclipBox", 0.8,
                "DuctTape", 0.8,
                "Glue", 0.8,
                "Scotchtape", 0.8,
                "Twine", 0.8,
                "Thread", 1.5,
                "Needle", 0.8,
                "Woodglue", 0.8,
                "Rope", 0.8,
                "Nails", 3,
                "Nails", 3,
                "Poolcue", 2,
                "Hammer", 3,
                "Wire", 1.5,
                "Saw", 1,
                "GardenSaw", 1,
                "Torch", 2,
                "HandTorch", 3,
                "Battery", 3,
                "Screwdriver", 1,
                "Toolbox", 0.5,
                "Radio.ElectricWire", 2,
                "Golfclub", 2,
                "Crowbar", 2,
                "Paperclip", 2,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.01,
                "Radio.WalkieTalkie4",0.005,
                "Radio.WalkieTalkie5",0.001,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "ClubHammer", 1,
                "WoodenMallet", 1,
            }
        },
        shelves = {
            rolls = 3,
            items = {
                "Magazine", 20,
                "Newspaper", 20,
                "Book", 20,
                "SheetPaper2", 20,
                "Notebook", 20,
                "BookTailoring1", 2,
                "BookTailoring2", 1,
                "BookTailoring3", 0.7,
                "BookTailoring4", 0.5,
                "BookTailoring5", 0.3,
                "BookCarpentry1", 2,
                "BookCarpentry2", 1,
                "BookCarpentry3", 0.7,
                "BookCarpentry4", 0.5,
                "BookCarpentry5", 0.3,
                "BookCooking1", 2,
                "BookCooking2", 1,
                "BookCooking3", 0.5,
                "BookCooking4", 0.3,
                "BookForaging1", 2,
                "BookForaging2", 1,
                "BookForaging3", 0.7,
                "BookForaging4", 0.5,
                "BookForaging5", 0.3,
                "BookFarming1", 2,
                "BookFarming2", 1,
                "BookFarming3", 0.7,
                "BookFarming4", 0.5,
                "BookFarming5", 0.3,
                "BookFishing1", 2,
                "BookFishing2", 1,
                "BookFishing3", 0.7,
                "BookFishing4", 0.5,
                "BookFishing5", 0.3,
                "BookTrapping1", 2,
                "BookTrapping2", 1,
                "BookTrapping3", 0.7,
                "BookTrapping4", 0.5,
                "BookTrapping5", 0.3,
                "BookFirstAid1", 2,
                "BookFirstAid2", 1,
                "BookFirstAid3", 0.7,
                "BookFirstAid4", 0.5,
                "BookFirstAid5", 0.3,
                "BookMetalWelding1", 2,
                "BookMetalWelding2", 1,
                "BookMetalWelding3", 0.7,
                "BookMetalWelding4", 0.5,
                "BookMetalWelding5", 0.3,
                "BookElectrician1", 2,
                "BookElectrician2", 1,
                "BookElectrician3", 0.7,
                "BookElectrician4", 0.5,
                "BookElectrician5", 0.3,
                "BookMechanic1", 2,
                "BookMechanic2", 1,
                "BookMechanic3", 0.7,
                "BookMechanic4", 0.5,
                "BookMechanic5", 0.3,
                "FishingMag1", 1,
                "FishingMag2", 1,
                "HuntingMag1", 1,
                "HuntingMag2", 1,
                "HuntingMag3", 1,
                "HerbalistMag", 1,
                "FarmingMag1", 1,
                "CookingMag1", 1,
                "CookingMag2", 1,
                "ElectronicsMag1", 1,
                "ElectronicsMag2", 1,
                "ElectronicsMag3", 1,
                "ElectronicsMag4", 1,
                "ElectronicsMag5", 1,
                "MechanicMag1", 1,
                "MechanicMag2", 1,
                "MechanicMag3", 1,
                "EngineerMagazine1", 1,
                "EngineerMagazine2", 1,
                "MetalworkMag1", 1,
                "MetalworkMag2", 1,
                "MetalworkMag3", 1,
                "MetalworkMag4", 1,
                "Journal", 2,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
            }
        },
        other = {
            rolls = 1,
            items = {
                "NailsBox", 1,
                "Nails", 20,
                "Nails", 20,
                "Nails", 20,
                "Nails", 20,
                "Nails", 20,
                "Battery", 2,
                "Lighter", 1,
                "Torch", 0.5,
                "HandTorch", 1,
                "Pen", 2,
                "Pencil", 2,
                "Tissue", 2,
                "Candle", 2,
                "Candle", 1,
                "Matches", 1,
                "Tissue", 1,
                "Magazine", 1,
                "Newspaper", 1,
                "Book", 1,
                "SheetPaper2", 1,
                "Notebook", 1,
                "Cigarettes", 1,
                "EmptyJar", 0.5,
                "JarLid", 0.5,
                "BoxOfJars", 0.01,
                "ConcretePowder", 10,
                "PlasterPowder", 10,
            }
        }
    },
    
    armyhanger = {
        metal_shelves = {
            rolls = 3,
            items = {
                "Hat_Army", 3,
                "Hat_SPHhelmet", 3,
                "Boilersuit_Flying", 2,
                "Radio.WalkieTalkie5", 2,
                "Glasses_Aviators", 2,
                "Glasses_SafetyGoggles", 3,
                "Glasses_Sun", 2,
                "Hat_DustMask", 4,
                "WeldingMask", 3,
                "Hat_EarMuff_Protectors", 3,
                "Hat_HardHat", 3,
                "Vest_HighViz", 3,
                "Extinguisher", 3,
                "Torch", 2,
                "HandTorch", 3,
                "Hammer", 3,
                "BlowTorch", 4,
                "Screwdriver", 3,
                "Toolbox", 1.5,
                "Wrench", 3,
                "Battery", 2,
                "Radio.ElectricWire", 2,
                "ElectronicsScrap", 2,
                "LightBulb", 2,
                "Screws", 3,
                "MetalPipe", 3,
                "ScrapMetal", 3,
                "WeldingRods", 3,
                "Wire", 3,
                "DuctTape", 3,
                "PetrolCan", 2,
                "PropaneTank", 3,
                "Tarp", 3,
                "Crowbar", 3,
                "PipeWrench", 3,
                "ClubHammer", 3,
                "BallPeenHammer", 3,
                "Sledgehammer", 3,
                "LeadPipe", 2,
            }
        },
        
        counter = {
            rolls = 3,
            items = {
                "Hat_Army", 3,
                "Hat_SPHhelmet", 3,
                "Boilersuit_Flying", 2,
                "Radio.WalkieTalkie5", 2,
                "Glasses_Aviators", 2,
                "Glasses_SafetyGoggles", 3,
                "Glasses_Sun", 2,
                "Hat_DustMask", 4,
                "WeldingMask", 3,
                "Hat_EarMuff_Protectors", 3,
                "Hat_HardHat", 3,
                "Vest_HighViz", 3,
                "Extinguisher", 3,
                "Torch", 2,
                "HandTorch", 2,
                "Hammer", 3,
                "BlowTorch", 4,
                "Screwdriver", 3,
                "Toolbox", 1.5,
                "Wrench", 3,
                "Battery", 2,
                "Radio.ElectricWire", 2,
                "ElectronicsScrap", 2,
                "LightBulb", 2,
                "Screws", 3,
                "MetalPipe", 3,
                "ScrapMetal", 3,
                "WeldingRods", 3,
                "Wire", 3,
                "DuctTape", 3,
                "PetrolCan", 2,
                "PropaneTank", 3,
                "Tarp", 3,
                "Crowbar", 3,
                "PipeWrench", 3,
                "ClubHammer", 3,
                "BallPeenHammer", 3,
                "Sledgehammer", 3,
                "LeadPipe", 2,
            }
        },
        
        locker = {
            rolls = 3,
            items = {
                "Hat_Army", 3,
                "Hat_SPHhelmet", 3,
                "Boilersuit_Flying", 2,
                "Radio.WalkieTalkie5", 2,
                "Glasses_Aviators", 2,
                "Glasses_SafetyGoggles", 3,
                "Glasses_Sun", 2,
                "Hat_DustMask", 4,
                "WeldingMask", 3,
                "Hat_EarMuff_Protectors", 3,
                "Hat_HardHat", 3,
                "Vest_HighViz", 3,
                "Extinguisher", 3,
                "Torch", 2,
                "HandTorch", 2,
                "Hammer", 3,
                "BlowTorch", 4,
                "Screwdriver", 3,
                "Toolbox", 1,
                "Wrench", 3,
                "Battery", 2,
                "Radio.ElectricWire", 2,
                "ElectronicsScrap", 2,
                "LightBulb", 2,
                "Screws", 3,
                "MetalPipe", 3,
                "ScrapMetal", 3,
                "WeldingRods", 3,
                "Wire", 3,
                "DuctTape", 3,
                "PetrolCan", 2,
                "PropaneTank", 3,
                "Tarp", 3,
                "Crowbar", 3,
                "PipeWrench", 3,
                "ClubHammer", 3,
                "BallPeenHammer", 3,
                "Sledgehammer", 3,
                "LeadPipe", 2,
            }
        }
    },
    
    armystorage = {
        locker = {
            rolls = 2,
            items = {
                "HolsterSimple", 3,
                "HolsterDouble", 0.8,
                "Nightstick", 4,
                "Shotgun", 3,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "GunPowder", 3,
                "ShotgunShellsBox", 3,
                "223Box", 3,
                "308Box", 3,
                "Bullets9mmBox", 3,
                "ShotgunShellsBox", 3,
                "Bullets38Box", 3,
                "Bullets44Box", 3,
                "Bullets45Box", 3,
                "Bag_ALICEpack_Army", 1,
                "HuntingKnife", 3,
                "Radio.WalkieTalkie4",10,
                "Radio.WalkieTalkie5",1,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",1,
                "AssaultRifle", 0.5,
                "AssaultRifle2", 0.8,
                "556Box", 2,
                "556Clip", 0.7,
                "Ghillie_Top", 0.1,
                "Ghillie_Trousers", 0.1,
                "Vest_BulletArmy", 0.5,
            },
        },
        
        metal_shelves =
        {
            rolls = 2,
            items = {
                "HolsterSimple", 3,
                "HolsterDouble", 0.8,
                "Nightstick", 4,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "Bullets9mmBox", 3,
                "GunPowder", 2,
                "ShotgunShellsBox", 3,
                "Bullets38Box", 3,
                "Bullets44Box", 3,
                "Bullets45Box", 3,
                "223Box", 3,
                "308Box", 3,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "Radio.WalkieTalkie4",10,
                "Radio.WalkieTalkie5",2,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",2,
            }
        },
    },
    
    armysurplus = {
        isShop = true,
        shelves = {
            rolls = 3,
            items = {
                "Hat_Army", 4,
                "Hat_BeretArmy", 4,
                "Hat_PeakedCapArmy", 2,
                "Hat_SPHhelmet", 1,
                "Hat_BonnieHat", 3,
                "Hat_BonnieHat_CamoGreen", 3,
                "Hat_BalaclavaFull", 1,
                "Hat_BalaclavaFace", 1,
                "Hat_GasMask", 1,
                "Hat_Beany", 2,
                "Ghillie_Trousers", 1,
                "Ghillie_Top", 1,
                "Boilersuit_Flying", 2,
                "camping.CampingTentKit", 3,
                "Radio.WalkieTalkie5", 2,
                "Radio.HamRadio2", 1,
                "HuntingKnife", 4,
                "Machete", 4,
                "Bag_ALICEpack", 1,
                "Bag_ALICEpack_Army", 1,
                "Lighter", 2,
                "Matches", 2,
            }
        },
        
        metal_shelves = {
            rolls = 3,
            items = {
                "Hat_Army", 4,
                "Hat_BeretArmy", 4,
                "Hat_PeakedCapArmy", 2,
                "Hat_SPHhelmet", 1,
                "Hat_BonnieHat", 3,
                "Hat_BonnieHat_CamoGreen", 3,
                "Hat_BalaclavaFull", 1,
                "Hat_BalaclavaFace", 1,
                "Hat_GasMask", 1,
                "Hat_Beany", 2,
                "Ghillie_Trousers", 1,
                "Ghillie_Top", 1,
                "Boilersuit_Flying", 2,
                "camping.CampingTentKit", 3,
                "Radio.WalkieTalkie5", 2,
                "Radio.HamRadio2", 1,
                "HuntingKnife", 4,
                "Machete", 4,
                "Bag_ALICEpack", 1,
                "Bag_ALICEpack_Army", 1,
                "Lighter", 2,
                "Matches", 2,
            }
        },
        
        
        clothingrack = {
            rolls = 3,
            items = {
                "Ghillie_Trousers", 1,
                "Ghillie_Top", 1,
                "Boilersuit_Flying", 2,
                "Shirt_CamoDesert", 4,
                "Shirt_CamoGreen", 4,
                "Shirt_CamoUrban", 4,
                "Tshirt_ArmyGreen", 4,
                "Tshirt_CamoDesert", 4,
                "Tshirt_CamoGreen", 4,
                "Tshirt_CamoUrban", 4,
                "Vest_BulletArmy", 1,
            }
        }
    },
    
    bakery = {
        isShop = true,
        displaycase = {
            procedural = true,
            procList = {
                {name="BakeryMisc", min=1, max=99},
            }
        },
        displaycasebakery = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=2},
                {name="BakeryPie", min=1, max=2},
                {name="BakeryCake", min=1, max=2},
                {name="BakeryMisc", min=0, max=4},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreKitchenBaking", min=1, max=99},
            }
        },
        grocerstand = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=2},
                {name="BakeryPie", min=1, max=2},
                {name="BakeryCake", min=1, max=2},
                {name="BakeryMisc", min=0, max=4},
            }
        }
    },
    
    bar = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="BarShelfLiquor", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="BarCounterMisc", min=1, max=4},
                {name="BarCounterWeapon", min=1, max=1},
                {name="BarCounterLiquor", min=0, max=99},
            }
        },
        bin = {
            procedural = true,
            procList = {
                {name="BinBar", min=0, max=99},
            }
        }
    },
    
    barkitchen = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=0, max=99},
            }
        },
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="BarShelfLiquor", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="BarCounterMisc", min=1, max=4},
                {name="BarCounterWeapon", min=1, max=1},
                {name="BarCounterLiquor", min=0, max=99},
            }
        },
        bin = {
            procedural = true,
            procList = {
                {name="BinBar", min=0, max=99},
            }
        }
    },
    
    barstorage = {
        crate = {
            procedural = true,
            procList = {
                {name="BarCratePool", min=0, max=99},
                {name="BarCrateDarts", min=0, max=99},
            }
        },
        bin = {
            procedural = true,
            procList = {
                {name="BinBar", min=0, max=99},
            }
        }
    },
    
    bathroom = {
        counter = {
            procedural = true,
            procList = {
                {name="BathroomCounter", min=0, max=99},
                {name="BathroomCounterEmpty", min=0, max=99, forceForItems="location_community_church_small_01_5"},
                {name="BathroomCounterNoMeds", min=0, max=99, forceForItems="fixtures_bathroom_01_28;fixtures_bathroom_01_29;fixtures_bathroom_01_37;fixtures_bathroom_01_38"},
            }
        },
        medicine = {
            procedural = true,
            procList = {
                {name="BathroomCabinet", min=0, max=99},
            }
        }
    },
    
    bedroom = {
        wardrobe = {
            procedural = true,
            procList = {
                {name="WardrobeMan", min=0, max=2},
                {name="WardrobeManClassy", min=0, max=1, forceForZones="Rich"},
                {name="WardrobeWomanClassy", min=0, max=1, forceForZones="Rich"},
                -- If we find the items listed in forceForItems we gonna force this particular loot definition
                {name="WardrobeChild", min=0, max=2, forceForItems="furniture_bedding_01_36;furniture_bedding_01_38;furniture_bedding_01_1;furniture_bedding_01_2;furniture_bedding_01_9;furniture_bedding_01_10;furniture_bedding_01_68;furniture_bedding_01_71;furniture_bedding_01_32;furniture_bedding_01_34"},
                {name="WardrobeWoman", min=0, max=2},
                {name="WardrobeRedneck", min=0, max=2, forceForZones="TrailerPark"},
            }
        },
        plankstash = {
            rolls = 7,
            items = {
                "Magazine", 30,
                "HottieZ", 30,
            }
        },
        sidetable = {
            rolls = 3,
            items = {
                "AlarmClock2", 4,
                "WristWatch_Left_DigitalRed", 2.5,
                "WristWatch_Left_DigitalBlack", 2.5,
                "CordlessPhone", 2,
                "Radio.RadioBlack", 2,
                "Radio.RadioRed", 1,
                "Radio.WalkieTalkie1", 0.05,
                "Radio.WalkieTalkie2", 0.03,
                "Radio.WalkieTalkie3", 0.001,
                "Pistol", 1,
                "Pistol2", 0.2,
                "Revolver_Short", 1,
            },
            junk = {
                rolls = 3,
                items = {
                    "Doll", 0.5,
                    "Disc", 0.5,
                    "CDplayer", 0.5,
                    "Earbuds", 0.5,
                    "Headphones", 0.5,
                    "VideoGame", 0.5,
                    "Magazine", 2,
                    "Socks_Ankle", 1,
                    "Socks_Long", 1,
                    "Pen", 7,
                    "BluePen", 7,
                    "RedPen", 7,
                    "Pencil", 7,
                },
            },
        },
        locker = {
            procedural = true,
            procList = {
                {name="LockerArmyBedroom", min=0, max=99, forceForZones="Army"},
            }
        },
    },
    
    bookstore = {
        shelves = {
            procedural = true,
            procList = {
                {name="BookstoreCarpentry", min=1, max=1},
                {name="BookstoreMetalwork", min=1, max=1},
                {name="BookstoreMechanic", min=1, max=1},
                {name="BookstoreElectronics", min=1, max=1},
                {name="BookstoreFarming", min=1, max=1},
                {name="BookstoreForaging", min=1, max=1},
                {name="BookstoreTrapping", min=1, max=1},
                {name="BookstoreFishing", min=1, max=1},
                {name="BookstoreCooking", min=1, max=1},
                {name="BookstoreFirstAid", min=1, max=1},
                {name="BookstoreTailoring", min=1, max=1},
                {name="BookstoreMisc", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="BookstoreBags", min=0, max=2},
                {name="BookstoreStationery", min=0, max=4},
                {name="MagazineRackMixed", min=0, max=99},
            }
        }
    },
    
    breakroom = {
        counter = {
            procedural = true,
            procList = {
                {name="BreakRoomCounter", min=0, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeBreakRoom", min=0, max=99},
            }
        },
        overhead = {
            procedural = true,
            procList = {
                {name="BreakRoomShelves", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="BreakRoomShelves", min=0, max=99},
            }
        }
    },
    
    burgerstorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreKitchenDishes", min=0, max=2},
                {name="StoreKitchenPots", min=0, max=2},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="StoreKitchenBaking", min=0, max=12},
                {name="StoreKitchenSauce", min=0, max=12},
            }
        }
    },
    
    burgerkitchen = {
        isShop = true,
        freezer = {
            procedural = true,
            procList = {
                {name="BurgerKitchenFreezer", min=0, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="BurgerKitchenFridge", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=1},
                {name="BurgerKitchenButcher", min=1, max=1},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPotatoes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenSauce", min=1, max=2},
            }
        }
    },
    
    butcher = {
        displaycasebutcher = {
            procedural = true,
            procList = {
                {name="ButcherChops", min=1, max=4},
                {name="ButcherGround", min=1, max=2},
                {name="ButcherChicken", min=1, max=1},
                {name="ButcherSmoked", min=1, max=4},
                {name="ButcherFish", min=0, max=1},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="Meat", min=0, max=99},
            }
        },
        freezer = {
            procedural = true,
            procList = {
                {name="Meat", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfSpices", min=1, max=6},
                {name="StoreKitchenButcher", min=0, max=2},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreKitchenButcher", min=1, max=6},
                {name="StoreCounterCleaning", min=1, max=2},
            }
        }
    },
    
    cafe = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreKitchenBaking", min=1, max=99},
            }
        },
        displaycase = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=2},
                {name="BakeryPie", min=1, max=2},
                {name="BakeryCake", min=1, max=2},
                {name="BakeryMisc", min=0, max=4},
            }
        }
    },
    
    cafekitchen = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="CafeKitchenFridge", min=0, max=99},
            }
        },
        freezer = {
            rolls = 1,
            items = {
            
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreKitchenBaking", min=1, max=2},
                {name="StoreKitchenCafe", min=1, max=4},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreKitchenBaking", min=1, max=2},
                {name="StoreKitchenDishes", min=0, max=4},
                {name="StoreKitchenPots", min=0, max=4},
            }
        }
    },
    
    camping = {
        isShop = true,
        clothingrack = {
            procedural = true,
            procList = {
                {name="CampingStoreClothes", min=1, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="CampingStoreBooks", min=1, max=4},
                {name="CampingStoreLegwear", min=0, max=1},
                {name="CampingStoreBackpacks", min=0, max=1},
                {name="CampingStoreGear", min=0, max=1},
                {name="FishingStoreGear", min=0, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="CampingStoreLegwear", min=1, max=2},
                {name="CampingStoreBackpacks", min=1, max=2},
                {name="CampingStoreGear", min=1, max=4},
                {name="FishingStoreGear", min=1, max=2},
            }
        }
    },
    
    campingstorage = {
        crate = {
            procedural = true,
            procList = {
                {name="CampingStoreClothes", min=0, max=1},
                {name="CampingStoreBooks", min=0, max=1},
                {name="CampingStoreLegwear", min=0, max=1},
                {name="CampingStoreBackpacks", min=0, max=1},
                {name="CampingStoreGear", min=0, max=1},
                {name="FishingStoreGear", min=0, max=1},
            }
        }
    },
    
    candystorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreKitchenDishes", min=0, max=2},
                {name="StoreKitchenPots", min=0, max=2},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="GigamartCandy", min=0, max=12},
                {name="CandyStoreSugar", min=0, max=12},
            }
        }
    },
    
    candystore = {
        isShop = true,
        displaycase = {
            procedural = true,
            procList = {
                {name="CandyStoreSnacks", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="StoreKitchenDishes", min=1, max=1},
                {name="StoreKitchenPots", min=1, max=1},
                {name="CandyStoreSugar", min=0, max=12},
            }
        }
    },
    
    changeroom = {
        locker = {
            rolls = 2,
            items = {
                "DumbBell", 0.3,
                "BarBell", 0.3,
                "Tshirt_DefaultTEXTURE_TINT", 3,
                "Jumper_RoundNeck", 1,
                "TrousersMesh_DenimLight", 1,
                "Trousers_DefaultTEXTURE_TINT", 2,
                "Vest_DefaultTEXTURE_TINT", 1,
                "Skirt_Knees", 0.3,
                "Skirt_Long", 0.3,
                "Skirt_Normal", 0.3,
                "Socks_Ankle", 4,
                "Sheet", 9,
                "Pillow", 9,
                "Paperclip", 0.5,
                "Belt2", 5,
                "BaseballBat", 2,
                "Pistol", 0.2,
                "Revolver_Short", 0.2,
                "Doll", 0.5,
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Bag_DuffelBagTINT", 1,
                "Bag_Schoolbag", 3,
                "Bag_NormalHikingBag", 0.8,
                "ClosedUmbrellaRed", 0.3,
                "ClosedUmbrellaBlue", 0.3,
                "ClosedUmbrellaBlack", 0.3,
                "ClosedUmbrellaWhite", 0.3,
                "Bag_BigHikingBag", 0.5,
            }
        },
        
        counter = {
            rolls = 2,
            items = {
                "Sheet", 4,
                "Sheet", 4,
                "Mirror", 3,
                "Soap2", 10,
                "FirstAidKit", 0.5,
            }
        }
    },
    
    classroom = {
        counter = {
            procedural = true,
            procList = {
                {name="ClassroomMisc", min=0, max=99},
            }
        },
        desk = {
            procedural = true,
            procList = {
                {name="ClassroomDesk", min=0, max=99},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="ClassroomShelves", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="ClassroomShelves", min=0, max=99},
            }
        }
    },
    
    clothingstorage = {
        clothingrack = {
            procedural = true,
            procList = {
                {name="ClothingStorageAllShirts", min=1, max=4},
                {name="ClothingStorageAllJackets", min=1, max=4},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="ClothingStorageFootwear", min=1, max=4},
                {name="ClothingStorageHeadwear", min=0, max=2},
                {name="ClothingStorageLegwear", min=0, max=2},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="ClothingStorageWinter", min=1, max=4},
                {name="ClothingStorageHeadwear", min=0, max=1},
                {name="ClothingStorageFootwear", min=0, max=1},
                {name="ClothingStorageAllJackets", min=0, max=1},
                {name="ClothingStorageAllShirts", min=0, max=1},
            }
        }
    },
    
    clothingstore = {
        isShop = true,
        displaycase = {
            procedural = true,
            procList = {
                {name="StoreDisplayWatches", min=1, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="ClothingStoresBoots", min=1, max=12},
                {name="ClothingStoresShoes", min=1, max=24},
            }
        },
        clothingrack = {
            procedural = true,
            procList = {
                {name="ClothingStoresDress", min=1, max=2},
                {name="ClothingStoresJackets", min=1, max=6},
                {name="ClothingStoresJacketsFormal", min=0, max=12, forceForZones="Rich"},
                {name="ClothingStoresJumpers", min=1, max=12},
                {name="ClothingStoresOvershirts", min=1, max=12},
                {name="ClothingStoresPants", min=1, max=12},
                {name="ClothingStoresPantsFormal", min=0, max=12, forceForZones="Rich"},
                {name="ClothingStoresShirts", min=1, max=12},
                {name="ClothingStoresShirtsFormal", min=0, max=12, forceForZones="Rich"},
                {name="ClothingStoresSport", min=1, max=8},
                {name="ClothingStoresSummer", min=1, max=8},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="ClothingStoresGloves", min=0, max=4},
                {name="ClothingStoresEyewear", min=0, max=2},
                {name="ClothingStoresHats", min=0, max=2},
                {name="ClothingStoresHatsFormal", min=0, max=2},
                {name="ClothingStoresHatsSport", min=0, max=2},
                {name="ClothingStoresSocks", min=0, max=2},
                {name="ClothingStoresUnderwear", min=0, max=2},
            }
        }
    },
    
    conveniencestore = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfDrinks", min=1, max=4},
                {name="StoreShelfSnacks", min=1, max=4},
                {name="StoreShelfMedical", min=0, max=1},
                {name="StoreShelfMechanics", min=0, max=1},
            }
        }
    },
    
    cornerstore = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 1,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfDrinks", min=1, max=4},
                {name="StoreShelfSnacks", min=1, max=4},
                {name="StoreShelfMedical", min=0, max=1},
                {name="StoreShelfMechanics", min=0, max=1},
            }
        }
    },
    
    dentiststorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        }
    },
    
    departmentstore = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=4},
                {name="StoreCounterBags", min=1, max=4},
            }
        },
        displaycase = {
            procedural = true,
            procList = {
                {name="JewellerySilver", min=1, max=7},
                {name="JewelleryGold", min=1, max=3},
                {name="JewelleryGems", min=1, max=2},
                {name="JewelleryWeddingRings", min=1, max=2},
                {name="JewelleryWrist", min=1, max=3},
                {name="JewelleryOthers", min=1, max=50},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="ClothingStoresBoots", min=1, max=12},
                {name="ClothingStoresShoes", min=1, max=24},
            }
        },
        wardrobe = {
            rolls = 0,
            items = {
            
            }
        },
        sidetable = {
            rolls = 0,
            items = {
            
            }
        }
    },
    
    dining = {
        counter = {
            rolls = 2,
            items = {
                "Book", 4,
                "Book", 4,
                "Pen", 4,
                "Pencil", 4,
                "BluePen", 3,
                "RedPen", 3,
                "Sparklers", 3,
                "Aluminum", 1,
            }
        },
        
        shelves = {
            rolls = 2,
            items = {
                "PopBottle", 2,
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "Crisps", 2,
                "Crisps2", 2,
                "Crisps3", 2,
                "Crisps4", 2,
                "WhiskeyFull", 3,
            }
        }
    },
    
    dinerkitchen = {
        isShop = true,
        freezer = {
            procedural = true,
            procList = {
                {name="DinerKitchenFreezer", min=0, max=99},
            },
        },
        fridge = {
            procedural = true,
            procList = {
                {name="DinerKitchenFridge", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=2},
                {name="StoreKitchenButcher", min=1, max=1},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPotatoes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenSauce", min=1, max=2},
            }
        }
    },
    
    electronicsstorage = {
        isShop = true,
        crate = {
            procedural = true,
            procList = {
                {name="ElectronicStoreComputer", min=0, max=2},
                {name="CrateTV", min=0, max=4},
                {name="CrateTVWide", min=0, max=4},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="ElectronicStoreMusic", min=0, max=2},
                {name="ElectronicStoreLights", min=0, max=2},
                {name="ElectronicStoreMagazines", min=0, max=2},
                {name="ElectronicStoreMisc", min=0, max=99},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="ElectronicStoreComputer", min=0, max=2},
                {name="ElectronicStoreHAMRadio", min=0, max=1},
                {name="ElectronicStoreMisc", min=0, max=99},
            }
        }
    },
    
    electronicsstore = {
        isShop = true,
        displaycase = {
            procedural = true,
            procList = {
                {name="StoreDisplayWatches", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="ElectronicStoreHAMRadio", min=0, max=1},
                {name="ElectronicStoreComputer", min=0, max=4},
                {name="ElectronicStoreMusic", min=0, max=2},
                {name="ElectronicStoreLights", min=0, max=1},
                {name="ElectronicStoreMagazines", min=1, max=1},
                {name="ElectronicStoreMisc", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="ElectronicStoreHAMRadio", min=0, max=1},
                {name="ElectronicStoreComputer", min=0, max=2},
                {name="ElectronicStoreMusic", min=0, max=1},
                {name="ElectronicStoreLights", min=0, max=1},
                {name="ElectronicStoreMagazines", min=1, max=1},
                {name="ElectronicStoreMisc", min=0, max=99},
            }
        }
    },
    
    empty = {
        crate = {
            procedural = true,
            procList = {
                {name="RandomFiller", min=0, max=99},
            }
        },
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        fridge = {
            rolls = 0,
            items = {
            
            }
        },
        counter = {
            rolls = 0,
            items = {
            
            }
        },
        shelves = {
            rolls = 0,
            items = {
            
            }
        },
        metal_shelves = {
            rolls = 0,
            items = {
            
            }
        },
        wardrobe = {
            rolls = 0,
            items = {
            
            }
        }
    },
    
    farmstorage = {
        all = {
            rolls = 3,
            items = {
                "Axe", 0.3,
                "WoodAxe", 0.3,
                "farming.CarrotBagSeed", 1.5,
                "farming.BroccoliBagSeed", 1.5,
                "farming.RedRadishBagSeed", 1.5,
                "farming.StrewberrieBagSeed", 1.5,
                "farming.TomatoBagSeed", 1.5,
                "farming.PotatoBagSeed", 1.5,
                "farming.CabbageBagSeed", 1.5,
                "farming.HandShovel", 4,
                "HandScythe", 3,
                "HandFork", 3,
                "LeafRake", 2,
                "GardenFork", 3,
                "Rake", 2,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "farming.WateredCan", 0.7,
                "BookFarming1", 2,
                "BookFarming2", 1.3,
                "BookFarming3", 0.9,
                "BookFarming4", 0.6,
                "BookFarming5", 0.3,
                "BookForaging1", 2,
                "BookForaging2", 1.3,
                "BookForaging3", 0.9,
                "BookForaging4", 0.6,
                "BookForaging5", 0.3,
                "FarmingMag1", 3,
                "Paintbrush", 1.5,
                "PaintBlue", 0.8,
                "PaintBlack", 0.8,
                "PaintRed", 0.8,
                "PaintBrown", 0.8,
                "PaintCyan", 0.8,
                "PaintGreen", 0.8,
                "PaintGrey", 0.8,
                "PaintLightBlue", 0.8,
                "PaintLightBrown", 0.8,
                "PaintOrange", 0.8,
                "PaintPink", 0.8,
                "PaintPurple", 0.8,
                "PaintTurquoise",0.8,
                "PaintWhite", 0.8,
                "PaintYellow", 0.8,
                "PlasterPowder", 2,
                "ConcretePowder", 0.8,
                "BucketEmpty", 2,
                "EmptySandbag", 3,
            }
        }
    },
    
    fishingstorage = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="CampingStoreBooks", min=0, max=1},
                {name="CampingStoreLegwear", min=0, max=1},
                {name="CampingStoreBackpacks", min=0, max=1},
                {name="CampingStoreGear", min=0, max=1},
                {name="FishingStoreGear", min=1, max=12},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="FishingStoreGear", min=1, max=12},
            }
        }
    },
    
    fossoil = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfMechanics", min=1, max=4},
                {name="StoreShelfDrinks", min=1, max=2},
                {name="StoreShelfSnacks", min=1, max=2},
                {name="StoreShelfMedical", min=0, max=1},
            }
        }
    },
    
    furniturestorage = {
        isShop = true,
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        fridge = {
            rolls = 0,
            items = {
            
            }
        },
        wardrobe = {
            rolls = 0,
            items = {
            
            }
        },
        sidetable = {
            rolls = 0,
            items = {
            
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="CrateBlueComfyChair", min=0, max=1},
                {name="CrateBluePlasticChairs", min=0, max=1},
                {name="CrateBlueRattanChair", min=0, max=1},
                {name="CrateBrownComfyChair", min=0, max=1},
                {name="CrateBrownLowTables", min=0, max=1},
                {name="CrateChromeSinks", min=0, max=1},
                {name="CrateDarkBlueChairs", min=0, max=1},
                {name="CrateDarkWoodenChairs", min=0, max=1},
                {name="CrateFancyBlackChairs", min=0, max=1},
                {name="CrateFancyDarkTables", min=0, max=1},
                {name="CrateFancyLowTables", min=0, max=1},
                {name="CrateFancyToilets", min=0, max=1},
                {name="CrateFancyWhiteChairs", min=0, max=1},
                {name="CrateFoldingChairs", min=0, max=1},
                {name="CrateGreenChairs", min=0, max=1},
                {name="CrateGreenComfyChair", min=0, max=1},
                {name="CrateGreenOven", min=0, max=1},
                {name="CrateGreyChairs", min=0, max=1},
                {name="CrateGreyComfyChair", min=0, max=1},
                {name="CrateGreyOven", min=0, max=1},
                {name="CrateIndustrialSinks", min=0, max=1},
                {name="CrateLightRoundTable", min=0, max=1},
                {name="CrateMetalLockers", min=0, max=1},
                {name="CrateModernOven", min=0, max=1},
                {name="CrateOakRoundTable", min=0, max=1},
                {name="CrateOfficeChairs", min=0, max=1},
                {name="CrateOrangeModernChair", min=0, max=1},
                {name="CratePlasticChairs", min=0, max=1},
                {name="CratePurpleRattanChair", min=0, max=1},
                {name="CratePurpleWoodenChairs", min=0, max=1},
                {name="CrateRedBBQs", min=0, max=1},
                {name="CrateRedChairs", min=0, max=1},
                {name="CrateRedOven", min=0, max=1},
                {name="CrateRedWoodenChairs", min=0, max=1},
                {name="CrateRoundTable", min=0, max=1},
                {name="CrateWhiteComfyChair", min=0, max=1},
                {name="CrateWhiteSimpleChairs", min=0, max=1},
                {name="CrateWhiteSinks", min=0, max=1},
                {name="CrateWhiteWoodenChairs", min=0, max=1},
                {name="CrateWoodenChairs", min=0, max=1},
                {name="CrateWoodenStools", min=0, max=1},
                {name="CrateYellowModernChair", min=0, max=1},
            }
        }
    },
    
    furniturestore = {
        isShop = true,
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        fridge = {
            rolls = 0,
            items = {
            
            }
        },
        wardrobe = {
            rolls = 0,
            items = {
            
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="RandomFiller", min=0, max=99},
            }
        }
    },
    
    garage = {
        metal_shelves = {
            rolls = 2,
            items = {
                "HuntingRifle", 0.5,
                "VarmintRifle", 0.5,
                "Shotgun", 0.8,
                "DoubleBarrelShotgun", 1,
                "ShotgunShellsBox", 2,
                "223Box", 2,
                "308Box", 2,
                "NailsBox", 1,
                "FishingRod", 2,
                "FishingMag1", 0.5,
                "FishingMag2", 0.5,
                "HuntingMag1", 0.5,
                "HuntingMag2", 0.5,
                "HuntingMag3", 0.5,
                "HerbalistMag", 0.5,
                "ElectronicsMag1", 0.5,
                "ElectronicsMag2", 0.5,
                "ElectronicsMag3", 0.5,
                "ElectronicsMag4", 0.5,
                "ElectronicsMag5", 0.5,
                "MechanicMag1", 0.5,
                "MechanicMag2", 0.4,
                "MechanicMag3", 0.3,
                "EngineerMagazine1", 0.5,
                "EngineerMagazine2", 0.5,
                "MetalworkMag1", 0.5,
                "MetalworkMag2", 0.5,
                "MetalworkMag3", 0.5,
                "MetalworkMag4", 0.5,
                "HuntingKnife", 1,
                "FishingNet", 1,
                "FishingTackle", 1,
                "FishingTackle2", 1,
                "FishingLine", 1.5,
                "PaperclipBox", 0.5,
                "DuctTape", 2,
                "Glue", 2,
                "Scotchtape", 2,
                "Twine", 2,
                "Thread", 4,
                "Woodglue", 2,
                "Rope", 2,
                "NailsBox", 1,
                "NailsBox", 1,
                "Nails", 3,
                "Hammer", 2,
                "Wire", 7,
                "Saw", 0.5,
                "GardenSaw", 0.5,
                "Plank", 1,
                "Plank", 1,
                "Screwdriver", 2,
                "Toolbox", 1,
                "Radio.ElectricWire", 2,
                "Charcoal", 6,
                "Charcoal", 6,
                "BallPeenHammer", 1,
                --                "Tongs", 0.5,
                "PropaneTank", 3,
                "LightBulb", 1,
                "LightBulbRed", 0.01,
                "LightBulbGreen", 0.01,
                "LightBulbBlue", 0.01,
                "LightBulbYellow", 0.06,
                "LightBulbCyan", 0.03,
                "LightBulbMagenta", 0.01,
                "LightBulbOrange", 0.006,
                "LightBulbPurple", 0.003,
                "LightBulbPink", 0.001,
                "Extinguisher", 0.5,
                "Radio.RadioMag1", 0.2,
                "Radio.RadioMag2", 0.1,
                "Radio.RadioMag3", 0.05,
                "EmptyPetrolCan", 4,
                "PetrolCan", 3,
                "ElectronicsScrap", 2,
                "ElectronicsScrap", 2,
                "ScrapMetal", 2,
                "ScrapMetal", 2,
                "Radio.RadioBlack",4,
                "Radio.RadioRed",2,
                "Radio.WalkieTalkie1",1,
                "Radio.WalkieTalkie2",0.8,
                "Radio.WalkieTalkie3",0.6,
                "Radio.WalkieTalkie4",0.1,
                "Radio.WalkieTalkie5",0.02,
                "Radio.HamRadio1",0.1,
                "Radio.HamRadio2",0.01,
                "BlowTorch", 2,
                "BlowTorch", 2,
                "WeldingRods", 5,
                "SmallSheetMetal", 5,
                "SheetMetal", 4,
                "MetalPipe", 4,
                "MetalBar", 4,
                "WeldingMask",2,
                "Wrench", 1,
                "LugWrench",0.7,
                "Jack", 0.5,
                "TirePump", 0.5,
                "OldTire1", 0.2,
                "NormalTire1", 0.1,
                "OldTire2", 0.2,
                "NormalTire2", 0.1,
                "OldTire3", 0.2,
                "NormalTire3", 0.1,
                "CarBatteryCharger", 1,
                "CarBattery1", 0.3,
                "CarBattery2", 0.3,
                "CarBattery3", 0.3,
                "BarbedWire", 1,
                "EmptySandbag", 2,
                "Cigarettes", 5,
                "Lighter", 2,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "Plunger", 2,
                "ClubHammer", 1,
                "WoodenMallet", 1,
                "farming.GardeningSprayEmpty", 0.4,
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="CratePaint", min=0, max=50},
                {name="CrateFarming", min=0, max=50},
                {name="CrateWoods", min=0, max=50},
                {name="CrateHardware", min=0, max=50},
                {name="CrateTools", min=0, max=50},
                {name="CrateMetal", min=0, max=50},
            }
        }
    },
    
    garagestorage = {
        other = {
            rolls = 2,
            items = {
                "HuntingRifle", 0.5,
                "VarmintRifle", 0.5,
                "Shotgun", 0.8,
                "DoubleBarrelShotgun", 1,
                "ShotgunShellsBox", 2,
                "223Box", 2,
                "308Box", 2,
                "NailsBox", 1,
                "FishingRod", 2,
                "FishingMag1", 0.5,
                "FishingMag2", 0.5,
                "HuntingMag1", 0.5,
                "HuntingMag2", 0.5,
                "HuntingMag3", 0.5,
                "HerbalistMag", 0.5,
                "ElectronicsMag1", 0.5,
                "ElectronicsMag2", 0.5,
                "ElectronicsMag3", 0.5,
                "ElectronicsMag4", 0.5,
                "ElectronicsMag5", 0.5,
                "MechanicMag1", 0.5,
                "MechanicMag2", 0.4,
                "MechanicMag3", 0.3,
                "EngineerMagazine1", 0.5,
                "EngineerMagazine2", 0.5,
                "MetalworkMag1", 0.5,
                "MetalworkMag2", 0.5,
                "MetalworkMag3", 0.5,
                "MetalworkMag4", 0.5,
                "HuntingKnife", 1,
                "FishingNet", 1,
                "FishingTackle", 1,
                "FishingTackle2", 1,
                "FishingLine", 1.5,
                "PaperclipBox", 0.5,
                "DuctTape", 2,
                "Glue", 2,
                "Scotchtape", 2,
                "Twine", 2,
                "Thread", 4,
                "Woodglue", 2,
                "Rope", 2,
                "NailsBox", 1,
                "NailsBox", 1,
                "Nails", 3,
                "Hammer", 2,
                "Wire", 7,
                "Saw", 2,
                "GardenSaw", 1,
                "Plank", 1,
                "Plank", 1,
                "Screwdriver", 2,
                "Toolbox", 1,
                "Radio.ElectricWire", 2,
                "Charcoal", 6,
                "Charcoal", 6,
                "BallPeenHammer", 1,
                --                "Tongs", 0.5,
                "PropaneTank", 3,
                "Extinguisher", 0.5,
                "Radio.RadioMag1", 0.2,
                "Radio.RadioMag2", 0.1,
                "Radio.RadioMag3", 0.05,
                "EmptyPetrolCan", 4,
                "PetrolCan", 3,
                "ElectronicsScrap", 2,
                "ElectronicsScrap", 2,
                "ScrapMetal", 2,
                "ScrapMetal", 2,
                "Radio.RadioBlack",4,
                "Radio.RadioRed",2,
                "Radio.WalkieTalkie1",1,
                "Radio.WalkieTalkie2",0.8,
                "Radio.WalkieTalkie3",0.6,
                "Radio.WalkieTalkie4",0.1,
                "Radio.WalkieTalkie5",0.02,
                "Radio.HamRadio1",0.1,
                "Radio.HamRadio2",0.01,
                "BlowTorch", 2,
                "WeldingRods", 5,
                "SmallSheetMetal", 5,
                "SheetMetal", 4,
                "MetalPipe", 4,
                "MetalBar", 4,
                "WeldingMask",2,
                "Wrench", 1,
                "LugWrench",1,
                "Jack", 1,
                "TirePump", 1,
                "OldTire1", 0.7,
                "NormalTire1", 0.5,
                "OldTire2", 0.7,
                "NormalTire2", 0.5,
                "OldTire3", 0.7,
                "NormalTire3", 0.5,
                "OldBrake1", 0.5,
                "NormalBrake1", 0.2,
                "OldBrake2", 0.5,
                "NormalBrake2", 0.2,
                "OldBrake3", 0.5,
                "NormalBrake3", 0.2,
                "NormalSuspension1", 0.2,
                "NormalSuspension2", 0.2,
                "NormalSuspension3", 0.2,
                "SmallGasTank1", 0.5,
                "NormalGasTank1", 0.2,
                "SmallGasTank2", 0.5,
                "NormalGasTank2", 0.2,
                "SmallGasTank3", 0.5,
                "NormalGasTank3", 0.2,
                "Windshield1", 0.2,
                "RearWindshield1", 0.2,
                "FrontWindow1", 0.2,
                "RearWindow1", 0.2,
                "Windshield2", 0.2,
                "RearWindshield2", 0.2,
                "FrontWindow2", 0.2,
                "RearWindow2", 0.2,
                "Windshield3", 0.2,
                "RearWindshield3", 0.2,
                "FrontWindow3", 0.2,
                "RearWindow3", 0.2,
                "FrontCarDoor1", 0.2,
                "RearCarDoor1", 0.2,
                "RearCarDoorDouble1", 0.2,
                "FrontCarDoor2", 0.2,
                "RearCarDoor2", 0.2,
                "RearCarDoorDouble2", 0.2,
                "FrontCarDoor3", 0.2,
                "RearCarDoor3", 0.2,
                "RearCarDoorDouble3", 0.2,
                "EngineDoor1", 0.2,
                "EngineDoor2", 0.2,
                "EngineDoor3", 0.2,
                "TrunkDoor1", 0.2,
                "TrunkDoor2", 0.2,
                "TrunkDoor3", 0.2,
                "OldCarMuffler1", 0.5,
                "NormalCarMuffler1", 0.2,
                "OldCarMuffler2", 0.5,
                "NormalCarMuffler2", 0.2,
                "OldCarMuffler3", 0.5,
                "NormalCarMuffler3", 0.2,
                "CarBatteryCharger", 1,
                "CarBattery1", 0.3,
                "CarBattery2", 0.3,
                "CarBattery3", 0.3,
                "BarbedWire", 1,
                "EmptySandbag", 2,
                "Cigarettes", 5,
                "Lighter", 2,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "Plunger", 2,
                "ClubHammer", 1,
                "WoodenMallet", 1,
                "farming.GardeningSprayEmpty", 0.4,
            },
            
            junk = {
                rolls = 5,
                items = {
                    "EmptySandbag", 2,
                    "Cigarettes", 5,
                    "Lighter", 2,
                    "farming.GardeningSprayEmpty", 2,
                    "BarbedWire", 1,
                    "LightBulb", 1,
                    "LightBulbRed", 0.01,
                    "LightBulbGreen", 0.01,
                    "LightBulbBlue", 0.01,
                    "LightBulbYellow", 0.06,
                    "LightBulbCyan", 0.03,
                    "LightBulbMagenta", 0.01,
                    "LightBulbOrange", 0.006,
                    "LightBulbPurple", 0.003,
                    "LightBulbPink", 0.001,
                },
            },
        },
        
        bin = {
            rolls = 1,
            items = {
                "Tissue", 2,
                "Lighter", 2,
                "Battery", 2,
                "Pen", 3,
                "BluePen", 1,
                "RedPen", 1,
                "Pencil", 3,
                "Eraser", 2,
                "Magazine", 2,
                "Newspaper", 2,
                "Paperclip", 1,
                "Socks_Ankle", 1,
                "Socks_Long", 1,
                "SheetPaper2", 3,
                "WaterBottleEmpty", 3,
                "Garbagebag", 15,
                "Garbagebag", 15,
                "Garbagebag", 15,
            }
        },
        
        crate = {
            procedural = true,
            procList = {
                {name="CratePaint", min=0, max=50},
                {name="CrateFarming", min=0, max=50},
                {name="CrateWoods", min=0, max=50},
                {name="CrateHardware", min=0, max=50},
                {name="CrateTools", min=0, max=50},
                {name="CrateMetal", min=0, max=50},
            }
        }
    },
    
    gardenstore = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="GardenStoreMisc", min=0, max=99},
            }
        },
        clothingrack = {
            procedural = true,
            procList = {
                {name="CampingStoreClothes", min=1, max=99},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="GardenStoreTools", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="GardenStoreTools", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="CrateFertilizer", min=1, max=12},
                {name="CrateGravelBags", min=0, max=4},
                {name="CrateSandBags", min=0, max=4},
            }
        }
    },
    
    gasstorage = {
        isShop = true,
        crate = {
            procedural = true,
            procList = {
                {name="StoreShelfSnacks", min=0, max=1},
                {name="StoreShelfDrinks", min=0, max=1},
                {name="StoreShelfMechanics", min=1, max=12},
                {name="StoreShelfMedical", min=0, max=1},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfSnacks", min=0, max=1},
                {name="StoreShelfDrinks", min=0, max=1},
                {name="StoreShelfMechanics", min=1, max=12},
                {name="StoreShelfMedical", min=0, max=1},
            }
        }
    },
    
    gasstore = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfMechanics", min=1, max=4},
                {name="StoreShelfDrinks", min=1, max=2},
                {name="StoreShelfSnacks", min=1, max=2},
                {name="StoreShelfMedical", min=0, max=1},
            }
        }
    },
    
    generalstore = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 1,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartBottles", min=1, max=6},
                {name="GigamartCrisps", min=1, max=4},
                {name="GigamartCandy", min=1, max=4},
                {name="StoreShelfMechanics", min=1, max=2},
                {name="StoreShelfMedical", min=1, max=2},
                {name="GigamartBakingMisc", min=0, max=2},
                {name="GigamartDryGoods", min=1, max=8},
                {name="GigamartHousewares", min=0, max=2},
                {name="GigamartCannedFood", min=1, max=8},
                {name="GigamartSauce", min=0, max=1},
                {name="GigamartToys", min=0, max=1},
                {name="GigamartSchool", min=0, max=1},
                {name="GigamartBedding", min=0, max=1},
                {name="GigamartPots", min=0, max=2},
                {name="GigamartLightbulb", min=0, max=1},
                {name="GigamartHouseElectronics", min=0, max=1},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="GigamartTools", min=1, max=6},
                {name="GigamartFarming", min=1, max=4},
            }
        }
    },
    
    generalstorestorage = {
        isShop = true,
        other = {
            rolls = 5,
            items = {
                "Mugl", 2,
                "KitchenKnife", 1,
                "MeatCleaver", 1,
                "TinnedSoup", 1,
                "TinnedBeans", 1,
                "CannedCornedBeef", 1,
                "Macandcheese", 1,
                "CannedChili", 1,
                "CannedBolognese", 1,
                "CannedCarrots2", 1,
                "CannedCorn", 1,
                "CannedMushroomSoup", 1,
                "CannedPeas", 1,
                "CannedPotato2", 1,
                "CannedSardines", 1,
                "CannedTomato2", 1,
                "Corkscrew", 1,
                "Disc", 1,
                "CDplayer", 0.7,
                "RollingPin", 1,
                "Dogfood", 1,
                "TinOpener", 2,
                "Pot", 1,
                "Saucepan", 1,
                "ButterKnife", 2,
                "BreadKnife", 2,
                "Spoon", 2,
                "Spoon", 2,
                "Fork", 2,
                "Fork", 2,
                "Bowl", 2,
                "Bowl", 2,
                "DishCloth", 2,
                "Kettle", 3,
                "Coffee2", 3,
                "Sugar", 1,
                "Teabag2", 2,
                "TunaTin", 1,
                "Flour", 1,
                "PeanutButter", 2,
                "Pan", 2,
                "GridlePan", 2,
                "RoastingPan", 2,
                "BakingPan", 2,
                "Yeast", 2,
                "Popcorn", 2,
                "Scissors", 2,
                "Cigarettes", 2,
                "Lighter", 1,
                "Pen", 2,
                "BluePen", 1,
                "RedPen", 1,
                "Pencil", 2,
                "CleaningLiquid2", 2,
                "Charcoal", 6,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "OatsRaw", 2,
                "Cigarettes", 5,
                "Cigarettes", 5,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "SnowShovel", 0.5,
                "farming.HandShovel", 1,
                "HandScythe", 0.5,
                "HandFork", 0.5,
                "LeafRake", 0.5,
                "Rake", 0.5,
                "Broom", 0.5,
                "GardenFork", 0.5,
                "BoxOfJars", 0.1,
            }
        }
    },
    
    giftstorage = {
        isShop = true,
        crate = {
            procedural = true,
            procList = {
                {name="GigamartToys", min=1, max=99},
            }
        }
    },
    
    giftstore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartToys", min=1, max=99},
            }
        },
        displaycase = {
            procedural = true,
            procList = {
                {name="StoreDisplayWatches", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="GigamartToys", min=1, max=99},
            }
        }
    },
    
    gigamart = {
        isShop = true,
        grocerstand = {
            procedural = true,
            procList = {
                {name="GroceryStandVegetables1", min=1, max=10},
                {name="GroceryStandVegetables2", min=1, max=10},
                {name="GroceryStandFruits1", min=1, max=10},
                {name="GroceryStandFruits2", min=1, max=10},
                {name="GroceryStandFruits3", min=1, max=10},
                {name="GroceryStandLettuce", min=1, max=4},
            }
        },
        displaycasebutcher = {
            procedural = true,
            procList = {
                {name="ButcherChops", min=1, max=4},
                {name="ButcherGround", min=1, max=2},
                {name="ButcherChicken", min=1, max=1},
                {name="ButcherSmoked", min=1, max=4},
                {name="ButcherFish", min=0, max=1},
            }
        },
        displaycasebakery = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=2},
                {name="BakeryPie", min=1, max=2},
                {name="BakeryCake", min=1, max=2},
                {name="BakeryMisc", min=0, max=4},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=4},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=0, max=4},
                {name="FridgeOther", min=0, max=8},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreCounterBags", min=1, max=4},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartBottles", min=2, max=6},
                {name="GigamartCrisps", min=2, max=4},
                {name="GigamartCandy", min=1, max=4},
                {name="GigamartBakingMisc", min=1, max=4},
                {name="GigamartDryGoods", min=2, max=16},
                {name="GigamartHousewares", min=1, max=4},
                {name="GigamartCannedFood", min=2, max=16},
                {name="GigamartSauce", min=1, max=2},
                {name="GigamartToys", min=0, max=2},
                {name="GigamartTools", min=1, max=2},
                {name="GigamartSchool", min=0, max=2},
                {name="GigamartBedding", min=0, max=2},
                {name="GigamartPots", min=1, max=3},
                {name="GigamartFarming", min=1, max=3},
                {name="GigamartLightbulb", min=1, max=1},
                {name="GigamartHouseElectronics", min=1, max=2},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="GigamartCrisps", min=0, max=4},
                {name="GigamartCandy", min=0, max=4},
                {name="GigamartCannedFood", min=0, max=16},
                {name="GigamartSauce", min=0, max=2},
            }
        }
    },
    
    gigamartkitchen = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=4},
                {name="StoreKitchenButcher", min=1, max=2},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                
            }
        }
    },
    
    grocery = {
        isShop = true,
        grocerstand = {
            procedural = true,
            procList = {
                {name="GroceryStandVegetables1", min=1, max=10},
                {name="GroceryStandVegetables2", min=1, max=10},
                {name="GroceryStandFruits1", min=1, max=10},
                {name="GroceryStandFruits2", min=1, max=10},
                {name="GroceryStandFruits3", min=1, max=10},
                {name="GroceryStandLettuce", min=1, max=2},
            }
        },
        displaycasebakery = {
            procedural = true,
            procList = {
                {name="BakeryBread", min=1, max=2},
                {name="BakeryPie", min=1, max=2},
                {name="BakeryCake", min=1, max=2},
                {name="BakeryMisc", min=0, max=4},
            },
        },
        displaycasebutcher = {
            procedural = true,
            procList = {
                {name="ButcherChops", min=1, max=4},
                {name="ButcherGround", min=1, max=2},
                {name="ButcherChicken", min=1, max=1},
                {name="ButcherSmoked", min=1, max=4},
                {name="ButcherFish", min=0, max=1},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartBottles", min=1, max=6},
                {name="GigamartCrisps", min=1, max=4},
                {name="GigamartCandy", min=1, max=4},
                {name="GigamartBakingMisc", min=1, max=6},
                {name="GigamartDryGoods", min=1, max=16},
                {name="GigamartCannedFood", min=1, max=16},
                {name="GigamartSauce", min=1, max=2},
            }
        },
        smallcrate = {
            procedural = true,
            procList = {
                {name="GigamartCannedFood", min=0, max=16},
                {name="GigamartBakingMisc", min=0, max=6},
                {name="GigamartDryGoods", min=0, max=16},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="GigamartCrisps", min=0, max=4},
                {name="GigamartCandy", min=0, max=4},
                {name="GigamartCannedFood", min=0, max=16},
                {name="GigamartSauce", min=0, max=2},
            }
        },
    },
    
    grocerystorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="GigamartBakingMisc", min=0, max=16},
                {name="GigamartCannedFood", min=0, max=16},
                {name="GigamartDryGoods", min=0, max=16},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeWater", min=1, max=12},
                {name="FridgeOther", min=1, max=12},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="GigamartBakingMisc", min=0, max=16},
                {name="GigamartCannedFood", min=0, max=16},
                {name="GigamartDryGoods", min=0, max=16},
            }
        }
    },
    
    gunstore = {
        isShop = true,
        counter = {
            rolls = 3,
            items = {
                "x2Scope", 3,
                "x4Scope", 2,
                "x8Scope", 1,
                "AmmoStraps", 3,
                "Sling", 4,
                "FiberglassStock", 3,
                "RecoilPad",  4,
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "HuntingKnife", 5,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "223Box", 10,
                "308Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Axe", 2,
                "WoodAxe", 3,
                "HuntingKnife", 3,
                "HuntingMag1", 0.2,
                "HuntingMag2", 0.2,
                "HuntingMag3", 0.2,
                "Radio.WalkieTalkie4",10,
                "Radio.WalkieTalkie5",5,
                "Radio.HamRadio1",10,
                "Radio.HamRadio2",5,
                "Glasses_Shooting", 2,
                "9mmClip", 2,
                "45Clip", 2,
                "44Clip", 2,
                "223Clip", 2,
                "308Clip", 2,
                "M14Clip", 2,
                "556Clip", 2,
            },
            dontSpawnAmmo = true,
        },
        
        displaycase = {
            rolls = 3,
            items = {
                "x2Scope", 3,
                "x4Scope", 2,
                "x8Scope", 1,
                "AmmoStrap_Bullets", 3,
                "AmmoStrap_Shells", 3,
                "Sling", 4,
                "FiberglassStock", 3,
                "RecoilPad",  4,
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "HuntingKnife", 5,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "223Box", 10,
                "308Box", 10,
                "Axe", 3,
                "Glasses_Shooting", 2,
                "9mmClip", 2,
                "45Clip", 2,
                "44Clip", 2,
                "223Clip", 2,
                "308Clip", 2,
                "M14Clip", 2,
                "556Clip", 2,
            },
            dontSpawnAmmo = true,
        },
        
        locker = {
            rolls = 3,
            items = {
                "x2Scope", 3,
                "x4Scope", 2,
                "x8Scope", 1,
                "AmmoStrap_Bullets", 3,
                "AmmoStrap_Shells", 3,
                "Sling", 4,
                "FiberglassStock", 3,
                "RecoilPad",  4,
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "HuntingKnife", 5,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "223Box", 10,
                "308Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Axe", 2,
                "WoodAxe", 2,
                "Machete", 1,
                "Katana", 0.5,
                "Radio.WalkieTalkie4",5,
                "Radio.WalkieTalkie5",3,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",3,
                "Glasses_Shooting", 2,
                "Vest_Hunting_Grey", 1,
                "Vest_Hunting_Orange", 1,
                "Vest_Hunting_Camo", 1,
                "Vest_Hunting_CamoGreen", 1,
            },
            dontSpawnAmmo = true,
        },
        
        metal_shelves = {
            rolls = 3,
            items = {
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 1.5,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "HuntingKnife", 3,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Axe", 3,
                "Katana", 0.5,
                "Machete", 1,
                "Radio.WalkieTalkie4",5,
                "Radio.WalkieTalkie5",3,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",3,
                "Glasses_Shooting", 2,
            },
            dontSpawnAmmo = true,
        },
    },
    
    gunstorestorage = {
        isShop = true,
        all= {
            rolls = 3,
            items = {
                "x2Scope", 3,
                "x4Scope", 2,
                "x8Scope", 1,
                "AmmoStrap_Bullets", 3,
                "AmmoStrap_Shells", 3,
                "Sling", 4,
                "FiberglassStock", 3,
                "RecoilPad",  4,
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "223Box", 10,
                "308Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Axe", 2,
                "WoodAxe", 2,
                "Katana", 0.5,
                "Machete", 1,
                "Radio.WalkieTalkie4",5,
                "Radio.WalkieTalkie5",3,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",3,
            },
            
            dontSpawnAmmo = true,
        },
    },
    
    hall = {
        counter = {
            rolls = 3,
            items = {
                "Pen", 10,
                "BluePen", 7,
                "RedPen", 7,
                "Pencil", 10,
                "RubberBand", 2,
                "Magazine", 8,
                "Magazine", 8,
                "Magazine", 8,
                "Tote", 6,
                "Tote", 6,
                "Tote", 6,
                "Tote", 6,
            }
        }
    },
    
    housewarestore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartHousewares", min=1, max=12},
                {name="GigamartBedding", min=0, max=2},
                {name="GigamartPots", min=1, max=6},
                {name="GigamartLightbulb", min=1, max=2},
                {name="GigamartHouseElectronics", min=1, max=2},
            }
        }
    },
    
    hunting = {
        locker = {
            rolls = 2,
            items = {
                "HuntingRifle", 2,
                "VarmintRifle", 2,
                "Shotgun", 2,
                "DoubleBarrelShotgun", 3,
                "ShotgunShellsBox", 5,
                "223Box", 5,
                "308Box", 5,
                "ShotgunShellsBox", 5,
                "223Box", 5,
                "308Box", 5,
                "Vest_Hunting_Grey", 2,
                "Vest_Hunting_Orange", 2,
                "Vest_Hunting_Camo", 2,
                "Vest_Hunting_CamoGreen", 2,
            }
        },
        
        metal_shelves = {
            rolls = 3,
            items = {
                "Bullets9mmBox", 4,
                "ShotgunShellsBox", 4,
                "223Box", 4,
                "308Box", 4,
                "GunPowder", 1,
            }
        },
        
        other = {
            rolls = 1,
            items = {
                "Pen", 10,
                "BluePen", 7,
                "RedPen", 7,
                "Pencil", 10,
                "RubberBand", 2,
            }
        }
    },
    
    icecreamkitchen = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
            }
        },
        freezer = {
            procedural = true,
            procList = {
                {name="IceCreamKitchenFreezer", min=1, max=99},
            }
        },
        fridge = {
            rolls = 0,
            items = {

            }
        },
        displaycasebakery = {
            procedural = true,
            procList = {
                {name="IceCreamKitchenFreezer", min=1, max=99},
            }
        },
        restaurantdisplay = {
            procedural = true,
            procList = {
                {name="IceCreamKitchenFreezer", min=1, max=99},
            }
        }
    },
    
    janitor = {
        metal_shelves = {
            procedural = true,
            procList = {
                {name="JanitorTools", min=1, max=1},
                {name="JanitorCleaning", min=1, max=1},
                {name="JanitorChemicals", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="JanitorMisc", min=1, max=1},
                {name="JanitorTools", min=0, max=1},
                {name="JanitorCleaning", min=0, max=1},
                {name="JanitorChemicals", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="JanitorChemicals", min=0, max=99},
            }
        }
    },
    
    jayschicken_kitchen = {
        isShop = true,
        restaurantdisplay = {
            procedural = true,
            procList = {
                {name="ServingTrayChicken", min=1, max=99},
                {name="ServingTrayFries", min=1, max=2},
            }
        },
        freezer = {
            procedural = true,
            procList = {
                {name="JaysKitchenFreezer", min=0, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="JaysKitchenFridge", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="JaysKitchenButcher", min=1, max=1},
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenSauce", min=1, max=2},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenPotatoes", min=1, max=1},
                {name="StoreKitchenBaking", min=1, max=1},
            }
        }
    },
    
    jewelrystore = {
        isShop = true,
        displaycase = {
            procedural = true,
            procList = {
                {name="JewellerySilver", min=1, max=7},
                {name="JewelleryGold", min=1, max=3},
                {name="JewelleryGems", min=1, max=2},
                {name="JewelleryWeddingRings", min=1, max=2},
                {name="JewelleryWrist", min=1, max=3},
                {name="JewelleryOthers", min=1, max=50},
            }
        }
    },
    
    kitchen = {
        counter = {
            procedural = true,
            procList = {
                {name="KitchenDishes", min=1, max=1},
                {name="KitchenPots", min=1, max=1},
                {name="KitchenCannedFood", min=1, max=1},
                {name="KitchenDryFood", min=0, max=1},
                {name="KitchenBreakfast", min=0, max=1},
                {name="KitchenBottles", min=0, max=1},
                {name="KitchenRandom", min=0, max=1},
            }
        },
    
        overhead = {
            procedural = true,
            procList = {
                {name="KitchenDishes", min=1, max=1},
                {name="KitchenCannedFood", min=1, max=1},
                {name="KitchenDryFood", min=0, max=1},
                {name="KitchenBreakfast", min=0, max=1},
                {name="KitchenBottles", min=0, max=1},
                {name="KitchenBook", min=0, max=1},
            }
        },
        
        shelves = {
            procedural = true,
            procList = {
                {name="KitchenDishes", min=1, max=1},
                {name="KitchenDryFood", min=1, max=1},
                {name="KitchenBook", min=0, max=1},
                {name="KitchenBottles", min=0, max=1},
            }
        },
    },
    
    kitchen_crepe = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="CrepeKitchenFridge", min=1, max=99},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=4},
            }
        }
    },
    
    laundry = {
        counter = {
            rolls = 3,
            items = {
                "Tshirt_DefaultTEXTURE_TINT", 3,
                "Jumper_RoundNeck", 1,
                "Jumper_PoloNeck", 1,
                "TrousersMesh_DenimLight", 1,
                "Trousers_DefaultTEXTURE_TINT", 2,
                "Belt2", 3,
                "Bag_Schoolbag", 2,
                "Purse", 3,
                "Handbag", 3,
                "Bleach", 5,
                "Bleach", 5,
                "Bleach", 5,
                "Bleach", 5,
            }
        }
    },
    
    laumdromat = { -- typo in room name, keep it like this for now
        counter = {
            rolls = 3,
            items = {
                "Tshirt_DefaultTEXTURE_TINT", 3,
                "Jumper_RoundNeck", 1,
                "Jumper_PoloNeck", 1,
                "TrousersMesh_DenimLight", 1,
                "Trousers_DefaultTEXTURE_TINT", 2,
                "Belt2", 3,
                "Bag_Schoolbag", 2,
                "Purse", 3,
                "Handbag", 3,
                "Bleach", 5,
                "Bleach", 5,
                "Bleach", 5,
                "Bleach", 5,
            }
        }
    },
    
    library = {
        counter = {
            rolls = 4,
            items = {
                "CardDeck", 1,
                "Comb", 2,
                "Magazine", 2,
                "Newspaper", 2,
                "Book", 2,
                "ComicBook", 2,
                "SheetPaper2", 20,
                "Notebook", 2,
                "Pencil", 15,
                "RubberBand", 4,
                "Eraser", 7,
                "Pen", 30,
                "BluePen", 10,
                "RedPen", 10,
                "Scissors", 3,
                "Cigarettes", 1,
            }
        }
    },
    
    liquorstore = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterTobacco", min=1, max=2},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfWhiskey", min=1, max=12},
                {name="StoreShelfWine", min=1, max=12},
                {name="StoreShelfBeer", min=1, max=24},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeBeer", min=0, max=99},
            }
        },
        freezer = {
            rolls = 0,
            items = {
                
            }
        },
    },
    
    livingroom = {
        shelves = {
            procedural = true,
            procList = {
                {name="LivingRoomShelf", min=0, max=99},
            }
        },
        counter ={
            procedural = true,
            procList = {
                {name="KitchenDishes", min=1, max=1},
                {name="KitchenPots", min=1, max=1},
                {name="KitchenCannedFood", min=1, max=1},
                {name="KitchenDryFood", min=0, max=1},
                {name="KitchenBreakfast", min=0, max=1},
                {name="KitchenBottles", min=0, max=1},
                {name="KitchenRandom", min=0, max=1},
            }
        },
        overhead = {
            procedural = true,
            procList = {
                {name="KitchenDishes", min=1, max=1},
                {name="KitchenCannedFood", min=1, max=1},
                {name="KitchenDryFood", min=0, max=1},
                {name="KitchenBreakfast", min=0, max=1},
                {name="KitchenBottles", min=0, max=1},
                {name="KitchenBook", min=0, max=1},
            }
        }
    },
    
    lobby = {
        counter = {
            rolls = 1,
            items = {
                "Sheet", 3,
                "DishCloth", 3,
                "Book", 3,
                "Pen", 3,
                "BluePen", 1,
                "RedPen", 1,
                "Pencil", 3,
                "SheetPaper2", 2,
                "Notebook", 2,
            }
        }
    },
    
    loggingfactory = {
        crate = {
            rolls = 1,
            items = {
                "NailsBox", 1,
                "DuctTape", 0.8,
                "Glue", 0.8,
                "Scotchtape", 0.8,
                "Woodglue", 0.8,
                "Rope", 0.8,
                "NailsBox", 4,
                "NailsBox", 4,
                "NailsBox", 4,
                "Hammer", 4,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "ClubHammer", 1,
                "WoodenMallet", 1,
                "Tarp", 1,
                "Saw", 1,
                "GardenSaw", 1,
                "Plank", 3,
                "Plank", 3,
                "Axe", 0.8,
                "WoodAxe", 0.8,
                "Sandbag", 0.5,
                "Gravelbag", 0.5,
                "EmptySandbag", 2.5,
                "Fertilizer", 0.5,
                --                "BallPeenHammer", 0.01,
                --                "Tongs", 0.01,
                "BlowTorch", 2,
                "WeldingRods", 3,
                "SheetMetal", 4,
                "SmallSheetMetal", 6,
                "MetalPipe", 4,
                "MetalBar", 3,
                "WeldingMask",2,
                "BarbedWire", 1,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "Glasses_SafetyGoggles", 0.3,
            }
        }
    },
    
    mechanic = {
        isShop = true,
        wardrobe = {
            procedural = true,
            procList = {
                {name="MechanicShelfOutfit", min=1, max=2},
                {name="MechanicShelfMisc", min=1, max=2},
                {name="MechanicShelfBooks", min=0, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MechanicShelfOutfit", min=1, max=2},
                {name="MechanicShelfTools", min=1, max=4},
                {name="MechanicShelfElectric", min=1, max=2},
                {name="MechanicShelfMufflers", min=0, max=2},
                {name="MechanicShelfBrakes", min=0, max=2},
                {name="MechanicShelfSuspension", min=0, max=2},
                {name="MechanicShelfWheels", min=1, max=6},
                {name="MechanicShelfBooks", min=1, max=1},
            }
        }
    },
    
    medclinic = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="MedicalClinicDrugs", min=1, max=4},
                {name="MedicalClinicTools", min=1, max=2},
                {name="MedicalClinicOutfit", min=1, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        }
    },
    
    medical = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="MedicalClinicDrugs", min=1, max=4},
                {name="MedicalClinicTools", min=1, max=2},
                {name="MedicalClinicOutfit", min=1, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        }
    },
    
    medicaloffice = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="MedicalClinicDrugs", min=1, max=4},
                {name="MedicalClinicTools", min=1, max=2},
                {name="MedicalClinicOutfit", min=1, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        }
    },
    
    medicalstorage = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="MedicalClinicDrugs", min=1, max=4},
                {name="MedicalClinicTools", min=1, max=2},
                {name="MedicalClinicOutfit", min=1, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        }
    },
    
    motelroom = {
        fridge = {
            rolls = 0,
            items = {
            },
        },
        freezer = {
            rolls = 0,
            items = {
            },
        },
        bin = {
            rolls = 0,
            items = {
            },
        },
        dresser = {
            rolls = 0,
            items = {
            },
        },
        wardrobe = {
            procedural = true,
            procList = {
                {name="MotelLinens", min=1, max=1},
                {name="MotelTowels", min=1, max=1},
            }
        },
        sidetable = {
            rolls = 1,
            items = {
                "Book", 200,
            },
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MotelLinens", min=1, max=99},
                {name="MotelTowels", min=1, max=99},
            }
        },
    },
    
    motelroomoccupied = {
        freezer = {
            rolls = 1,
            items = {
                "IcePick", 0.01,
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="MotelFridge", min=1, max=1},
            }
        },
        bin = {
            procedural = true,
            procList = {
                {name="BinGeneric", min=0, max=99},
            }
        },
        dresser = {
            rolls = 1,
            items = {
                "Bag_DuffelBagTINT", 0.5,
                "Bag_Schoolbag", 0.5,
                "Bag_NormalHikingBag", 0.2,
                "Bag_BigHikingBag", 0.2,
            }
        },
        wardrobe = {
            procedural = true,
            procList = {
                {name="MotelLinens", min=1, max=1},
                {name="MotelTowels", min=1, max=1},
            }
        },
        sidetable = {
            rolls = 1,
            items = {
                "Book", 100,
                "Earbuds", 2,
                "Comb", 2,
                "Magazine", 2,
                "Newspaper", 2,
                "Notebook", 2,
                "ComicBook", 2,
                "Pencil", 2,
                "Pen", 2,
                "BluePen", 1,
                "RedPen", 1,
                "Pills", 1,
                "PillsBeta", 1,
                "PillsAntiDep", 1,
                "PillsVitamins", 1,
            }
        }
    },
    
    musicstore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="MusicStoreCDs", min=1, max=4},
                {name="MusicStoreCases", min=1, max=2},
                {name="MusicStoreAcoustic", min=1, max=6},
                {name="MusicStoreBass", min=1, max=2},
                {name="MusicStoreOthers", min=1, max=4},
                {name="MusicStoreSpeaker", min=1, max=6},
            }
        }
    },

    optometrist = {
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="OptometristGlasses", min=1, max=99},
            }
        }
    },
    
    pharmacy = {
        isShop = true,
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=0, max=2},
                {name="FridgeSoda", min=0, max=6},
                {name="FridgeWater", min=0, max=4},
                {name="FridgeOther", min=0, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfDrinks", min=1, max=8},
                {name="StoreShelfSnacks", min=1, max=8},
                {name="StoreShelfMedical", min=4, max=24},
                {name="StoreShelfMechanics", min=0, max=1},
            }
        }
    },
    
    pharmacystorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="MedicalStorageDrugs", min=1, max=6},
                {name="MedicalStorageTools", min=1, max=4},
                {name="MedicalStorageOutfit", min=1, max=2},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeWater", min=0, max=12},
            }
        },
        freezer = {
            rolls = 0,
            items = {
            
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="MedicalClinicDrugs", min=1, max=4},
                {name="MedicalClinicTools", min=1, max=2},
                {name="MedicalClinicOutfit", min=1, max=2},
            }
        }
    },
    
    pizzakitchen = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="PizzaKitchenFridge", min=1, max=99},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=4},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="PizzaKitchenSauce", min=1, max=2},
                {name="PizzaKitchenCheese", min=1, max=2},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreKitchenBaking", min=1, max=12},
            }
        },
        displaycase = {
            procedural = true,
            procList = {
                {name="ServingTrayPizza", min=1, max=99},
            }
        },
        restaurantdisplay = {
            procedural = true,
            procList = {
                {name="ServingTrayPizza", min=1, max=99},
            }
        },
    },
    
    pizzawhirled = {
        isShop = true,
        wardrobe = {
            rolls = 0,
            items = {

            }
        },
        displaycase = {
            procedural = true,
            procList = {
                {name="ServingTrayPizza", min=0, max=99},
            }
        }
    },
    
    plazastore1 = {
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
                {name="RandomFiller", min=0, max=99},
            }
        }
    },
    
    policestorage = {
        locker = {
            rolls = 4,
            items = {
                "DumbBell", 0.3,
                "BarBell", 0.3,
                "HolsterSimple", 3,
                "HolsterDouble", 0.8,
                "Nightstick", 4,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "GunPowder", 3,
                "ShotgunShellsBox", 3,
                "223Box", 3,
                "308Box", 3,
                "Bullets9mmBox", 3,
                "ShotgunShellsBox", 3,
                "Bullets38Box", 3,
                "Bullets44Box", 3,
                "Bullets45Box", 3,
                "Bag_NormalHikingBag", 1,
                "HuntingKnife", 3,
                "Radio.WalkieTalkie4",10,
                "Radio.WalkieTalkie5",1,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",1,
            },
        },
        
        metal_shelves =
        {
            rolls = 4,
            items = {
                "HolsterSimple", 3,
                "HolsterDouble", 0.8,
                "Nightstick", 4,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "Bullets9mmBox", 3,
                "GunPowder", 2,
                "ShotgunShellsBox", 3,
                "Bullets38Box", 3,
                "Bullets44Box", 3,
                "Bullets45Box", 3,
                "223Box", 3,
                "308Box", 3,
                "PistolCase1", 1.5,
                "PistolCase2", 1.5,
                "PistolCase3", 0.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "Radio.WalkieTalkie4",10,
                "Radio.WalkieTalkie5",2,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",2,
            }
        },
    },
    
    post = {
        counter = {
            rolls = 2,
            items = {
                "DoubleBarrelShotgun", 2,
                "Pistol", 1,
                "Book", 4,
                "Book", 4,
                "Pen", 4,
                "Pen", 4,
                "Pencil", 4,
                "Pencil", 4,
                "BluePen", 3,
                "RedPen", 3,
                "BluePen", 3,
                "RedPen", 3,
                "Scissors", 3,
                "Cigarettes", 1,
                "Lighter", 1,
            }
        }
    },
    
    poststorage = {
        all = {
            rolls = 3,
            items = {
                "Book", 10,
                "Newspaper", 30,
                "Newspaper", 20,
                "Newspaper", 20,
                "Newspaper", 20,
                "Magazine", 30,
                "Magazine", 20,
                "Magazine", 20,
                "Magazine", 20,
                "Journal", 20,
                "Journal", 20,
                "Journal", 20,
                "ComicBook", 10,
                "BookCarpentry1", 1,
                "BookFarming1", 1,
                "BookForaging1", 1,
                "BookCooking1", 1,
                "BookFishing1", 1,
                "BookTrapping1", 1,
                "BookTailoring1", 1,
                "BookCarpentry2", 0.5,
                "BookFarming2", 0.5,
                "BookForaging2", 0.5,
                "BookCooking2", 0.5,
                "BookFishing2", 0.5,
                "BookTrapping2", 0.5,
                "BookTailoring2", 0.5,
                "BookCarpentry3", 0.3,
                "BookFarming3", 0.3,
                "BookForaging3", 0.3,
                "BookCooking3", 0.3,
                "BookFishing3", 0.3,
                "BookTrapping3", 0.3,
                "BookTailoring3", 0.3,
                --                "BookBlacksmith1", 1,
                --                "BookBlacksmith2", 0.5,
                --                "BookBlacksmith3", 0.3,
                "BookFirstAid1", 1,
                "BookFirstAid2", 0.5,
                "BookFirstAid3", 0.3,
                "BookMetalWelding1", 1,
                "BookMetalWelding2", 0.5,
                "BookMetalWelding3", 0.3,
                "BookElectrician1", 1,
                "BookElectrician2", 0.5,
                "BookElectrician3", 0.3,
                "BookMechanic1", 1,
                "BookMechanic2", 0.5,
                "BookMechanic3", 0.3,
                "FishingMag1", 0.5,
                "FishingMag2", 0.5,
                "HuntingMag1", 0.5,
                "HuntingMag2", 0.5,
                "HuntingMag3", 0.5,
                "HerbalistMag", 0.5,
                "CookingMag1", 0.5,
                "CookingMag2", 0.5,
                "ElectronicsMag1", 0.5,
                "ElectronicsMag2", 0.5,
                "ElectronicsMag3", 0.5,
                "ElectronicsMag4", 0.5,
                "ElectronicsMag5", 0.5,
                "MechanicMag1", 0.5,
                "MechanicMag2", 0.5,
                "MechanicMag3", 0.5,
                "EngineerMagazine1", 0.5,
                "EngineerMagazine2", 0.5,
                "MetalworkMag1", 0.5,
                "MetalworkMag2", 0.5,
                "MetalworkMag3", 0.5,
                "MetalworkMag4", 0.5,
            }
        }
    },
    
    restaurant = {
        counter = {
            rolls = 2,
            items = {
                "Book", 4,
                "Book", 4,
                "Pen", 4,
                "Pencil", 4,
                "BluePen", 3,
                "RedPen", 3,
                "Sparklers", 3,
                "Aluminum", 1,
            }
        },
        
        shelves = {
            rolls = 2,
            items = {
                "PopBottle", 2,
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "Crisps", 2,
                "Crisps2", 2,
                "Crisps3", 2,
                "Crisps4", 2,
                "WhiskeyFull", 3,
            }
        }
    },
    
    restaurantkitchen = {
        isShop = true,
        freezer = {
            procedural = true,
            procList = {
                {name="RestaurantKitchenFreezer", min=1, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="RestaurantKitchenFridge", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=2},
                {name="StoreKitchenButcher", min=1, max=1},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPotatoes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenSauce", min=1, max=2},
            }
        }
    },
    
    security = {
        locker = {
            rolls = 3,
            items = {
                "DumbBell", 0.5,
                "BarBell", 0.5,
                "HuntingRifle", 3,
                "VarmintRifle", 3,
                "Shotgun", 3,
                "DoubleBarrelShotgun", 2,
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Pistol", 1.5,
                "Pistol2", 1.5,
                "Pistol3", 0.5,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
                "HuntingKnife", 5,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "GunPowder", 10,
                "GunPowder", 10,
                "223Box", 10,
                "308Box", 10,
                "223Box", 10,
                "308Box", 10,
                "Axe", 3,
                "Radio.WalkieTalkie4",5,
                "Radio.WalkieTalkie5",3,
                "Radio.HamRadio1",5,
                "Radio.HamRadio2",3,
                "Glasses_Shooting", 2,
            },
            dontSpawnAmmo = true,
        }
    },
    
    sewingstorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="SewingStoreTools", min=0, max=99},
                {name="SewingStoreFabric", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="SewingStoreTools", min=0, max=99},
                {name="SewingStoreFabric", min=0, max=99},
            }
        }
    },
    
    sewingstore = {
        isShop = true,
        counter ={
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="BookstoreTailoring", min=1, max=2},
                {name="SewingStoreTools", min=1, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="SewingStoreTools", min=1, max=99},
                {name="SewingStoreFabric", min=1, max=99},
            }
        }
    },
    
    shed = {
        other = {
            rolls = 2,
            items = {
                "HuntingRifle", 0.5,
                "VarmintRifle", 0.5,
                "Shotgun", 0.8,
                "DoubleBarrelShotgun", 1,
                "ShotgunShellsBox", 2,
                "223Box", 2,
                "308Box", 2,
                "NailsBox", 1,
                "FishingRod", 2,
                "FishingMag1", 0.5,
                "FishingMag2", 0.5,
                "HuntingMag1", 0.5,
                "HuntingMag2", 0.5,
                "HuntingMag3", 0.5,
                "HerbalistMag", 0.5,
                "ElectronicsMag1", 0.5,
                "ElectronicsMag2", 0.5,
                "ElectronicsMag3", 0.5,
                "ElectronicsMag4", 0.5,
                "ElectronicsMag5", 0.5,
                "MechanicMag1", 0.5,
                "MechanicMag2", 0.4,
                "MechanicMag3", 0.3,
                "EngineerMagazine1", 0.5,
                "EngineerMagazine2", 0.5,
                "MetalworkMag1", 0.5,
                "MetalworkMag2", 0.5,
                "MetalworkMag3", 0.5,
                "MetalworkMag4", 0.5,
                "HuntingKnife", 1,
                "FishingNet", 1,
                "FishingTackle", 1,
                "FishingTackle2", 1,
                "FishingLine", 1.5,
                "PaperclipBox", 0.5,
                "DuctTape", 2,
                "Glue", 2,
                "Scotchtape", 2,
                "Twine", 2,
                "Thread", 4,
                "Woodglue", 2,
                "Rope", 2,
                "NailsBox", 1,
                "NailsBox", 1,
                "Nails", 3,
                "Hammer", 2,
                "Wire", 7,
                "Saw", 2,
                "GardenSaw", 2,
                "Plank", 1,
                "Plank", 1,
                "Screwdriver", 2,
                "Toolbox", 1,
                "Radio.ElectricWire", 2,
                "Charcoal", 6,
                "Charcoal", 6,
                --                "BallPeenHammer", 0.5,
                --                "Tongs", 0.5,
                "PropaneTank", 3,
                "Extinguisher", 0.5,
                "Radio.RadioMag1", 0.2,
                "Radio.RadioMag2", 0.1,
                "Radio.RadioMag3", 0.05,
                "EmptyPetrolCan", 4,
                "PetrolCan", 3,
                "ElectronicsScrap", 2,
                "ElectronicsScrap", 2,
                "ScrapMetal", 2,
                "ScrapMetal", 2,
                "Radio.RadioBlack",4,
                "Radio.RadioRed",2,
                "Radio.WalkieTalkie1",1,
                "Radio.WalkieTalkie2",0.8,
                "Radio.WalkieTalkie3",0.6,
                "Radio.WalkieTalkie4",0.1,
                "Radio.WalkieTalkie5",0.02,
                "Radio.HamRadio1",0.1,
                "Radio.HamRadio2",0.01,
                "BlowTorch", 2,
                "WeldingRods", 5,
                "SmallSheetMetal", 5,
                "SheetMetal", 4,
                "MetalPipe", 4,
                "MetalBar", 4,
                "WeldingMask",2,
                "Wrench", 1,
                "LugWrench",1,
                "Jack", 1,
                "TirePump", 1,
                "OldTire1", 0.7,
                "NormalTire1", 0.5,
                "OldTire2", 0.7,
                "NormalTire2", 0.5,
                "OldTire3", 0.7,
                "NormalTire3", 0.5,
                "OldBrake1", 0.5,
                "NormalBrake1", 0.2,
                "OldBrake2", 0.5,
                "NormalBrake2", 0.2,
                "OldBrake3", 0.5,
                "NormalBrake3", 0.2,
                "NormalSuspension1", 0.2,
                "NormalSuspension2", 0.2,
                "NormalSuspension3", 0.2,
                "SmallGasTank1", 0.5,
                "NormalGasTank1", 0.2,
                "SmallGasTank2", 0.5,
                "NormalGasTank2", 0.2,
                "SmallGasTank3", 0.5,
                "NormalGasTank3", 0.2,
                "Windshield1", 0.2,
                "RearWindshield1", 0.2,
                "FrontWindow1", 0.2,
                "RearWindow1", 0.2,
                "Windshield2", 0.2,
                "RearWindshield2", 0.2,
                "FrontWindow2", 0.2,
                "RearWindow2", 0.2,
                "Windshield3", 0.2,
                "RearWindshield3", 0.2,
                "FrontWindow3", 0.2,
                "RearWindow3", 0.2,
                "FrontCarDoor1", 0.2,
                "RearCarDoor1", 0.2,
                "RearCarDoorDouble1", 0.2,
                "FrontCarDoor2", 0.2,
                "RearCarDoor2", 0.2,
                "RearCarDoorDouble2", 0.2,
                "FrontCarDoor3", 0.2,
                "RearCarDoor3", 0.2,
                "RearCarDoorDouble3", 0.2,
                "EngineDoor1", 0.2,
                "EngineDoor2", 0.2,
                "EngineDoor3", 0.2,
                "TrunkDoor1", 0.2,
                "TrunkDoor2", 0.2,
                "TrunkDoor3", 0.2,
                "OldCarMuffler1", 0.5,
                "NormalCarMuffler1", 0.2,
                "OldCarMuffler2", 0.5,
                "NormalCarMuffler2", 0.2,
                "OldCarMuffler3", 0.5,
                "NormalCarMuffler3", 0.2,
                "CarBatteryCharger", 1,
                "CarBattery1", 0.3,
                "CarBattery2", 0.3,
                "CarBattery3", 0.3,
                "BarbedWire", 1,
                "EmptySandbag", 2,
                "Cigarettes", 5,
                "Lighter", 2,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "Plunger", 2,
                "ClubHammer", 1,
                "WoodenMallet", 1,
                "farming.GardeningSprayEmpty", 0.4,
            },
            
            junk = {
                rolls = 5,
                items = {
                    "EmptySandbag", 2,
                    "Cigarettes", 5,
                    "Lighter", 2,
                    "farming.GardeningSprayEmpty", 2,
                    "BarbedWire", 1,
                    "LightBulb", 1,
                    "LightBulbRed", 0.01,
                    "LightBulbGreen", 0.01,
                    "LightBulbBlue", 0.01,
                    "LightBulbYellow", 0.06,
                    "LightBulbCyan", 0.03,
                    "LightBulbMagenta", 0.01,
                    "LightBulbOrange", 0.006,
                    "LightBulbPurple", 0.003,
                    "LightBulbPink", 0.001,
                },
            },
        },
        
        logs = {
            rolls = 4,
            items = {
                "Log", 7,
                "Log", 7,
                "Log", 7,
            }
        },
    },
    
    shoestore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="ClothingStoresBoots", min=1, max=12},
                {name="ClothingStoresShoes", min=1, max=24},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="ClothingStoresSocks", min=0, max=12},
            }
        }
    },
    
    spiffoskitchen = {
        isShop = true,
        freezer = {
            procedural = true,
            procList = {
                {name="SpiffosKitchenFreezer", min=1, max=99},
            }
        },
        fridge = {
            procedural = true,
            procList = {
                {name="SpiffosKitchenFridge", min=1, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenBaking", min=1, max=1},
                {name="BurgerKitchenButcher", min=1, max=1},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPotatoes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
                {name="StoreKitchenSauce", min=1, max=2},
            }
        }
    },
    
    spiffosstorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreKitchenDishes", min=0, max=2},
                {name="StoreKitchenPots", min=0, max=2},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="StoreKitchenBaking", min=0, max=12},
                {name="StoreKitchenSauce", min=0, max=12},
            }
        }
    },
    
    sportstorage = {
        isShop = true,
        metal_shelves = {
            procedural = true,
            procList = {
                {name="SportStorageBats", min=0, max=99},
                {name="SportStorageHelmets", min=0, max=99},
                {name="SportStoragePaddles", min=0, max=99},
                {name="SportStorageRacquets", min=0, max=99},
                {name="SportStorageSticks", min=0, max=99},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="SportsStoreSneakers", min=0, max=99},
                {name="SportStorageBats", min=0, max=99},
                {name="SportStorageHelmets", min=0, max=99},
                {name="SportStoragePaddles", min=0, max=99},
                {name="SportStorageRacquets", min=0, max=99},
                {name="SportStorageSticks", min=0, max=99},
            }
        },
        crate = {
            procedural = true,
            procList = {
                {name="SportStorageBats", min=0, max=99},
                {name="SportStorageBalls", min=0, max=99},
                {name="SportStorageHelmets", min=0, max=99},
                {name="SportStoragePaddles", min=0, max=99},
                {name="SportStorageRacquets", min=0, max=99},
                {name="SportStorageSticks", min=0, max=99},
            }
        },
        clothingrack = {
            procedural = true,
            procList = {
                {name="ClothingStoresSport", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="ClothingStoresEyewear", min=0, max=2},
                {name="ClothingStoresHatsSport", min=0, max=4},
                {name="SportsStoreSneakers", min=0, max=99},
            }
        }
    },
    
    sportstore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="SportsStoreSneakers", min=0, max=99},
                {name="SportStorageBats", min=0, max=99},
                {name="SportStoragePaddles", min=0, max=99},
                {name="SportStorageRacquets", min=0, max=99},
                {name="SportStorageSticks", min=0, max=99},
            }
        },
        clothingrack = {
            procedural = true,
            procList = {
                {name="ClothingStoresSport", min=0, max=99},
            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBagsFancy", min=1, max=1},
                {name="ClothingStoresEyewear", min=0, max=2},
                {name="ClothingStoresHatsSport", min=0, max=4},
                {name="SportsStoreSneakers", min=0, max=99},
            }
        }
    },
    
    storageunit = {
        all = {
            rolls = 3,
            items = {
                "Magazine", 2,
                "Newspaper", 2,
                "Lighter", 2,
                "BaseballBat", 5,
                "Cigarettes", 5,
                "Cigarettes", 5,
                "farming.HandShovel", 3,
                "HandScythe", 1,
                "HandFork", 1,
                "LeafRake", 2,
                "GardenFork", 1,
                "Rake", 2,
                "Tshirt_DefaultTEXTURE_TINT", 2,
                "Extinguisher", 0.5,
                "Trousers", 2,
                "Skirt_Knees", 0.3,
                "Skirt_Long", 0.3,
                "Skirt_Normal", 0.3,
                "Shoes_Random", 2,
                "camping.CampingTentKit", 0.2,
                "DishCloth", 2,
                "Kettle", 3,
                "farming.HandShovel", 2,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "SnowShovel", 0.5,
                "farming.WateredCan", 1,
                "Paintbrush", 1,
                "PaintBlue", 0.8,
                "PaintBrown", 0.8,
                "PaintBlack", 0.8,
                "PaintRed", 0.8,
                "PaintCyan", 0.8,
                "PaintGreen", 0.8,
                "PaintGrey", 0.8,
                "PaintLightBlue", 0.8,
                "PaintLightBrown", 0.8,
                "PaintOrange", 0.8,
                "PaintPink", 0.8,
                "PaintPurple", 0.8,
                "PaintTurquoise",0.8,
                "PaintWhite", 0.8,
                "PaintYellow", 0.8,
                "PlasterPowder", 0.8,
                "BucketEmpty", 1,
                "EmptyPetrolCan", 4,
                "PetrolCan", 3,
                "ElectronicsScrap", 2,
                "ElectronicsScrap", 2,
                "ScrapMetal", 2,
                "ScrapMetal", 2,
                "Radio.RadioBlack",2,
                "Radio.RadioRed",1,
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.01,
                "Radio.HamRadio1",0.05,
                "BlowTorch", 2,
                "BlowTorch", 2,
                "WeldingRods", 5,
                "SmallSheetMetal", 5,
                "SheetMetal", 4,
                "MetalPipe", 4,
                "MetalBar", 4,
                "WeldingMask",2,
                "Wrench", 1,
                "LugWrench",0.7,
                "Jack", 0.5,
                "TirePump", 0.5,
                "EmptySandbag", 2,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "SnowShovel", 0.5,
                "farming.HandShovel", 1,
                "LeadPipe", 1,
                "HandAxe", 0.8,
                "PipeWrench", 1,
                "Plunger", 2,
                "ClubHammer", 1,
                "WoodenMallet", 1,
            }
        }
    },
    
    theatre = {
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="RandomFiller", min=0, max=99},
            }
        }
    },
    
    theatrekitchen = {
        isShop = true,
        freezer = {
            procedural = true,
            procList = {
                {name="TheatreKitchenFreezer", min=1, max=99},
            }
        },
        fridge = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterCleaning", min=1, max=2},
                {name="StoreKitchenDishes", min=1, max=2},
                {name="StoreKitchenPots", min=1, max=2},
            }
        }
    },
    
    theatrestorage = {
        crate = {
            procedural = true,
            procList = {
                {name="TheatrePopcorn", min=1, max=99},
                {name="TheatreSnacks", min=0, max=99},
                {name="TheatreDrinks", min=0, max=99},
            }
        }
    },
    
    toolstore = {
        isShop = true,
        shelves = {
            rolls = 3,
            items = {
                "NailsBox", 3,
                "NailsBox", 3,
                "ScrewsBox", 7,
                "ScrewsBox", 7,
                "SheetMetal", 5,
                "SmallSheetMetal", 7,
                "SheetMetal", 5,
                "SmallSheetMetal", 7,
                "SheetMetal", 5,
                "SmallSheetMetal", 7,
                "MetalPipe", 7,
                "BlowTorch", 5,
                "WeldingRods", 9,
                "MetalBar", 5,
                "WeldingMask",3,
                "ScrapMetal", 3,
                "ScrapMetal", 3,
                "ScrapMetal", 3,
                "MetalworkMag1", 0.2,
                "MetalworkMag2", 0.2,
                "MetalworkMag3", 0.1,
                "MetalworkMag4", 0.1,
                "MechanicMag1", 0.2,
                "MechanicMag1", 0.2,
                "MechanicMag1", 0.2,
                "BookMechanic1",0.5,
                "BookMechanic2",0.4,
                "BookMechanic3",0.3,
                "BookMechanic4",0.2,
                "BookMechanic5",0.1,
                "DuctTape", 8,
                "Glue", 8,
                "Scotchtape", 8,
                "Twine", 8,
                "Thread",8,
                "Woodglue", 8,
                "Rope", 8,
                "Hammer", 10,
                "Wire", 10,
                "Saw", 7,
                "GardenSaw", 7,
                "HuntingKnife", 7,
                "Torch", 7,
                "HandTorch", 10,
                "Battery", 10,
                "Screwdriver", 7,
                "Toolbox", 3,
                "Radio.ElectricWire", 5,
                "Crowbar", 6,
                "Saw", 6,
                "GardenSaw", 6,
                "Axe", 3,
                "WoodAxe", 3,
                "camping.TentPeg", 7,
                "FishingRod", 7,
                "FishingNet", 3,
                "FishingTackle", 5,
                "FishingTackle2", 5,
                "FishingLine", 7,
                "Tarp", 3,
                "Padlock", 3,
                "BarbedWire", 2,
                "EmptySandbag", 2,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "SnowShovel", 0.5,
                "farming.HandShovel", 3,
                "HandScythe", 3,
                "HandFork", 3,
                "LeadPipe", 5,
                "HandAxe", 5,
                "PipeWrench", 5,
                "Plunger", 5,
                "ClubHammer", 8,
                "WoodenMallet", 5,
                "Plunger", 5,
                "Wrench", 5,
                "PickAxe", 2,
                "Machete", 2,
                "Hat_HardHat", 1,
                "Hat_HardHat_Miner", 0.5,
            }
        },
        
        counter = {
            rolls = 2,
            items = {
                "NailsBox", 3,
                "NailsBox", 3,
                "NailsBox", 3,
                "ScrewsBox", 7,
                "ScrewsBox", 7,
                "ScrewsBox", 7,
                "MetalworkMag1", 0.2,
                "MetalworkMag2", 0.2,
                "MetalworkMag3", 0.1,
                "MetalworkMag4", 0.1,
                "MechanicMag1", 0.2,
                "MechanicMag2", 0.2,
                "MechanicMag3", 0.2,
                "ScrapMetal", 3,
                "ScrapMetal", 3,
                "ScrapMetal", 3,
                "SheetMetal", 5,
                "SmallSheetMetal", 7,
                "SheetMetal", 5,
                "SmallSheetMetal", 7,
                "MetalPipe", 7,
                "BlowTorch", 5,
                "WeldingRods", 9,
                "MetalBar", 5,
                "WeldingMask",3,
                "DuctTape", 8,
                "Glue", 8,
                "Scotchtape", 8,
                "Twine", 8,
                "Thread", 8,
                "Woodglue", 8,
                "Rope", 8,
                "Hammer", 10,
                "Wire", 9,
                "Saw", 7,
                "GardenSaw", 7,
                "Torch", 7,
                "HandTorch", 10,
                "Battery", 10,
                "Screwdriver", 7,
                "Toolbox", 3,
                "Radio.ElectricWire", 5,
                "Crowbar", 6,
                "Saw", 6,
                "GardenSaw", 6,
                "Axe", 3,
                "WoodAxe", 3,
                "camping.TentPeg", 7,
                "FishingRod", 6,
                "HuntingKnife", 6,
                "FishingNet", 2,
                "FishingTackle", 4,
                "FishingTackle2", 4,
                "FishingLine", 7,
                "Tarp", 3,
                "Padlock", 3,
                "EmptyJar", 1,
                "JarLid", 1,
                "LeadPipe", 5,
                "HandAxe", 5,
                "PipeWrench", 5,
                "Plunger", 5,
                "ClubHammer", 8,
                "WoodenMallet", 5,
                "Plunger", 5,
                "Wrench", 5,
                "PickAxe", 2,
                "Machete", 2,
                "Hat_HardHat", 1,
                "Hat_HardHat_Miner", 0.5,
            }
        },
    },
    
    toystore = {
        isShop = true,
        shelves = {
            procedural = true,
            procList = {
                {name="GigamartToys", min=1, max=99},
            }
        }
    },
    
    warehouse = {
        crate = {
            procedural = true,
            procList = {
                {name="CratePaint", min=0, max=50},
                {name="CrateFarming", min=0, max=50},
                {name="CrateWoods", min=0, max=50},
                {name="CrateHardware", min=0, max=50},
                {name="CrateTools", min=0, max=50},
                {name="CrateMetal", min=0, max=50},
                {name="CrateAntiqueStove", min=0, max=2},
            }
        },
    },
    
    zippeestorage = {
        isShop = true,
        crate = {
            procedural = true,
            procList = {
                {name="StoreShelfSnacks", min=1, max=12},
                {name="StoreShelfDrinks", min=1, max=12},
                {name="StoreShelfMechanics", min=0, max=1},
                {name="StoreShelfMedical", min=0, max=1},
            }
        },
        metal_shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfSnacks", min=1, max=12},
                {name="StoreShelfDrinks", min=1, max=12},
                {name="StoreShelfMechanics", min=0, max=1},
                {name="StoreShelfMedical", min=0, max=1},
            }
        }
    },
    
    zippeestore = {
        isShop = true,
        fridge = {
            procedural = true,
            procList = {
                {name="FridgeSnacks", min=1, max=2},
                {name="FridgeSoda", min=1, max=4},
                {name="FridgeWater", min=1, max=4},
                {name="FridgeOther", min=1, max=2},
            }
        },
        freezer = {
            rolls = 0,
            items = {

            }
        },
        counter = {
            procedural = true,
            procList = {
                {name="StoreCounterTobacco", min=1, max=2},
                {name="StoreCounterCleaning", min=1, max=1},
                {name="StoreCounterBags", min=1, max=1},
            }
        },
        shelves = {
            procedural = true,
            procList = {
                {name="StoreShelfDrinks", min=1, max=4},
                {name="StoreShelfSnacks", min=1, max=4},
                {name="StoreShelfMedical", min=0, max=1},
                {name="StoreShelfMechanics", min=0, max=1},
            }
        }
    },

-- =====================    
--    Bags/Containers   
-- =====================    

    Bag_WeaponBag = {
        rolls = 3,
        items = {
            "Shotgun", 5,
            "DoubleBarrelShotgun", 3,
            "ShotgunShellsBox", 10,
            "ShotgunShellsBox", 10,
            "ShotgunShellsBox", 10,
            "Machete", 5,
            "Pistol", 2,
            "Pistol2", 2,
            "Pistol3", 1,
            "Revolver_Short", 1.5,
            "Revolver", 1,
            "Revolver_Long", 0.5,
            "Bullets9mmBox", 10,
            "Bullets9mmBox", 10,
            "Bullets9mmBox", 10,
            "Bullets38Box", 10,
            "Bullets44Box", 10,
            "Bullets45Box", 10,
            "BaseballBat", 8,
            "Crowbar", 7,
        },
        fillRand = 0,
    },
    
    Bag_MoneyBag = {
        rolls = 10,
        items = {
            "Money", 100,
            "Money", 100,
            "Money", 100,
            "Money", 100,
            "Money", 100,
            "Money", 100,
            "Money", 100,
            "Money", 100,
        },
        fillRand = 0,
    },
    
    Bag_InmateEscapedBag = {
        rolls = 5,
        items = {
            "Rope", 15,
            "RippedSheets", 15,
            "Screwdriver", 3,
            "Hammer", 3,
            "DuctTape", 10,
        },
        fillRand = 0,
    },
    
    Bag_GolfBag = {
        rolls = 1,
        items = {
            "Golfclub", 100,
            "Golfclub", 2,
            "Golfclub", 2,
            "Golfclub", 2,
            "GolfBall", 100,
            "GolfBall", 10,
            "GolfBall", 10,
            "GolfBall", 10,
            "GolfBall", 10,
            "GolfBall", 10,
            "Gloves_LeatherGloves", 7,
            "Hat_GolfHatTINT", 3,
            "Hat_VisorBlack", 3,
            "Hat_VisorRed", 3,
            "Tshirt_PoloTINT", 10,
            "Shoes_Random", 10,
            "Trousers_SuitWhite", 10,
        },
        fillRand = 0,
    },
    
    Bag_SurvivorBag = {
        rolls = 5,
        items = {
            "MuldraughMap", 30,
            "WestpointMap", 30,
            "MarchRidgeMap",30,
            "RosewoodMap",30,
            "RiversideMap",30,
            "Crisps",25,
            "Crisps2", 25,
            "Crisps3", 25,
            "Crisps4", 25,
            "Cereal", 25,
            "Dogfood", 25,
            "TVDinner",25,
            "TinnedSoup", 25,
            "TinnedBeans", 25,
            "CannedCornedBeef", 25,
            "Macandcheese", 25,
            "CannedChili", 25,
            "CannedBolognese", 25,
            "CannedCarrots2", 25,
            "CannedCorn", 25,
            "CannedMushroomSoup", 25,
            "CannedPeas", 25,
            "CannedPotato2", 25,
            "CannedSardines", 25,
            "CannedTomato2", 25,
            "Shotgun", 3,
            "DoubleBarrelShotgun", 1.5,
            "ShotgunShellsBox", 4,
            "ShotgunShellsBox", 4,
            "Machete", 4,
            "Bandage", 10,
            "Bandaid", 10,
            "FirstAidKit", 3,
            "SewingKit", 2,
        },
        -- only two map allowed
        maxMap = 2,
        -- this mean 90% chance on normal sandbox settings to have an annoted map
        stashChance = 10,
        fillRand = 0,
    },
    
    SeedBag = {
        rolls = 2,
        items = {
            "farming.GardeningSprayEmpty", 1,
            "TrapMouse", 0.5,
            "farming.HandShovel", 0.9,
            "FarmingMag1", 1,
            "BookFarming1", 1,
            "BookFarming2", 0.7,
            "farming.CarrotBagSeed", 5,
            "farming.BroccoliBagSeed", 5,
            "farming.RedRadishBagSeed", 5,
            "farming.StrewberrieBagSeed", 5,
            "farming.TomatoBagSeed", 5,
            "farming.PotatoBagSeed", 5,
            "farming.CabbageBagSeed", 5,
        },
        fillRand = 0,
    },
    
    SewingKit = {
        rolls = 5,
        items = {
            "Thread", 10,
            "Thread", 10,
            "Thread", 10,
            "Thread", 10,
            "Needle", 4,
            "Needle", 4,
            "RippedSheets", 2,
            "LeatherStrips", 1,
            "DenimStrips", 1,
        },
        fillRand = 0,
    },
    
    Plasticbag = {
        rolls = 0,
        items = {

        }
    },
    
    Suitcase = {
        rolls = 4,
        items = {
            "Tshirt_DefaultTEXTURE_TINT", 3,
            "Jumper_RoundNeck", 1,
            "TrousersMesh_DenimLight", 1,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "Skirt_Knees", 0.3,
            "Skirt_Long", 0.3,
            "Skirt_Normal", 0.3,
        },
        fillRand = 1,
    },
    
    Briefcase = {
        rolls = 4,
        items = {
            "Tshirt_DefaultTEXTURE_TINT", 3,
            "Jumper_RoundNeck", 1,
            "TrousersMesh_DenimLight", 1,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "Skirt_Knees", 0.3,
            "Skirt_Long", 0.3,
            "Skirt_Normal", 0.3,
        },
        fillRand = 1,
    },
    
    PistolCase1 = {
        rolls = 1,
        items = {
            "Pistol", 100,
            "9mmClip", 100,
        },
        fillRand = 0,
    },
    
    PistolCase2 = {
        rolls = 1,
        items = {
            "Pistol2", 100,
            "45Clip", 100,
        },
        fillRand = 0,
    },
    
    PistolCase3 = {
        rolls = 1,
        items = {
            "Pistol3", 100,
            "44Clip", 100,
        },
        fillRand = 0,
    },
    
    Lunchbox = {
        rolls = 2,
        items = {
            "Apple", 17,
            "Banana", 17,
            "PeanutButterSandwich", 12,
            "Pop", 12,
            "WaterBottleFull", 15,
            "Crisps", 7,
        },
        fillRand = 0,
    },
    
    Satchel = {
        rolls = 1,
        items = {
            "Apple", 17,
            "Banana", 17,
            "PeanutButterSandwich", 12,
            "Pop", 12,
            "WaterBottleFull", 15,
            "Crisps", 7,
            "Notebook", 10,
            "Doodle", 7,
            "Journal", 7,
            "Magazine", 7,
            "Pencil", 15,
        },
        fillRand = 0,
    },
    
    Lunchbox2 = {
        rolls = 2,
        items = {
            "Apple", 17,
            "Banana", 17,
            "PeanutButterSandwich", 12,
            "Pop", 12,
            "WaterBottleFull", 15,
            "Crisps", 7,
        },
        fillRand = 0,
    },
    
    Bag_Schoolbag = {
        rolls = 3,
        items = {
            "Pen", 3,
            "BluePen", 3,
            "RedPen", 3,
            "Pen", 3,
            "Pencil", 3,
            "RubberBand", 3,
            "Pencil", 3,
            "Scissors", 2,
            "Cigarettes", 1,
            "Cube", 0.2,
            "Book", 3,
            "Crisps", 0.3,
            "Crisps2", 0.3,
            "Crisps3", 0.3,
            "Pop", 1,
        },
        fillRand = 0,
    },
    
    Bag_WorkerBag = {
        rolls = 3,
        items = {
            "Pen", 3,
            "BluePen", 3,
            "RedPen", 3,
            "Pen", 3,
            "Pencil", 3,
            "RubberBand", 3,
            "Pencil", 3,
            "Scissors", 2,
            "Cigarettes", 1,
            "GrilledCheese", 2,
            "PeanutButterSandwich", 2,
            "Pop", 1,
            "WaterBottleFull", 1,
        },
        fillRand = 0,
    },
    
    Toolbox = {
        rolls = 3,
        items = {
            "NailsBox", 1,
            "PaperclipBox", 0.8,
            "DuctTape", 0.8,
            "Glue", 0.8,
            "Scotchtape", 0.8,
            "Twine", 0.8,
            "Thread", 1.5,
            "Needle", 0.8,
            "Woodglue", 0.8,
            "Nails", 3,
            "Nails", 3,
            "Hammer", 3,
            "Wire", 1.5,
            "Saw", 1,
            "GardenSaw", 1,
            "HandTorch", 3,
            "Battery", 5,
            "Screwdriver", 2,
            "BlowTorch", 1,
        },
        fillRand = 0,
    },
    
    Purse = {
        rolls = 2,
        items = {
            "Lipstick", 3,
            "Perfume", 2,
            "MakeupEyeshadow", 1,
            "MakeupFoundation", 1,
            "Earbuds", 3,
            "Necklace_Silver", 1,
            "NecklaceLong_Silver", 1,
            "Earring_LoopLrg_Silver", 1,
            "Earring_LoopMed_Silver", 1,
            "Earring_LoopSmall_Silver_Both", 1,
            "Earring_Stud_Silver", 1,
            "Ring_Left_RingFinger_Silver", 1,
            "Necklace_SilverCrucifix", 1,
            "Necklace_Gold", 0.5,
            "NecklaceLong_Gold", 0.5,
            "NoseStud_Gold", 0.5,
            "Earring_LoopLrg_Gold", 0.5,
            "Earring_LoopMed_Gold", 0.5,
            "Earring_LoopSmall_Gold_Both", 0.5,
            "Earring_Stud_Gold", 0.5,
            "Ring_Left_RingFinger_Gold", 0.5,
            "Locket", 3,
            "Comb", 3,
            "Magazine", 3,
            "CreditCard", 3,
        },
        fillRand = 0,
    },
    
    Flightcase = {
        rolls = 1,
        items = {
            "GuitarAcoustic", 5,
            "GuitarElectricBlack", 5,
            "GuitarElectricBlue", 5,
            "GuitarElectricRed", 5,
            "GuitarElectricBassBlue", 5,
            "GuitarElectricBassBlack", 5,
            "GuitarElectricBassRed", 5,
        },
        fillRand = 0,
    },
    
    Guitarcase = {
        rolls = 1,
        items = {
            "GuitarAcoustic", 5,
            "GuitarElectricBlack", 5,
            "GuitarElectricBlue", 5,
            "GuitarElectricRed", 5,
            "GuitarElectricBassBlue", 5,
            "GuitarElectricBassBlack", 5,
            "GuitarElectricBassRed", 5,
        },
        fillRand = 0,
    },
    
    Handbag = {
        rolls = 1,
        items = {
            "Lipstick", 3,
            "Perfume", 2,
            "MakeupEyeshadow", 1,
            "MakeupFoundation", 1,
            "Necklace_Silver", 1,
            "NecklaceLong_Silver", 1,
            "Earring_LoopLrg_Silver", 1,
            "Earring_LoopMed_Silver", 1,
            "Earring_LoopSmall_Silver_Both", 1,
            "Earring_Stud_Silver", 1,
            "Ring_Left_RingFinger_Silver", 1,
            "Necklace_SilverCrucifix", 1,
            "Necklace_Gold", 0.5,
            "NecklaceLong_Gold", 0.5,
            "NoseStud_Gold", 0.5,
            "Earring_LoopLrg_Gold", 0.5,
            "Earring_LoopMed_Gold", 0.5,
            "Earring_LoopSmall_Gold_Both", 0.5,
            "Earring_Stud_Gold", 0.5,
            "Ring_Left_RingFinger_Gold", 0.5,
            "Earbuds", 3,
            "Locket", 3,
            "Comb", 3,
            "Magazine", 3,
            "CreditCard", 3,
        },
        fillRand = 0,
    },
    
    Bag_DuffelBag = {
        rolls = 2,
        items = {
            "Hat_Sweatband",1,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Socks_Ankle", 2,
            "Hat_BaseballCap", 1,
        },
        fillRand = 1,
    },
    
    Bag_DuffelBagTINT = {
        rolls = 2,
        items = {
            "Hat_Sweatband",1,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Socks_Ankle", 2,
            "Hat_BaseballCap", 1,
        },
        fillRand = 1,
    },
    
    Bag_NormalHikingBag = {
        rolls = 2,
        items = {
            "Vest_DefaultTEXTURE_TINT", 2,
            "Tshirt_DefaultTEXTURE_TINT", 2,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Skirt_Knees", 0.6,
            "Skirt_Long", 0.6,
            "Skirt_Normal", 0.6,
            "Socks_Ankle", 2,
        },
        fillRand = 1,
    },
    
    Bag_BigHikingBag = {
        rolls = 2,
        items = {
            "Vest_DefaultTEXTURE_TINT", 2,
            "Tshirt_DefaultTEXTURE_TINT", 2,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Skirt_Knees", 0.6,
            "Skirt_Long", 0.6,
            "Skirt_Normal", 0.6,
            "Socks_Ankle", 2,
            "camping.CampingTentKit", 0.2,
        },
        fillRand = 1,
    },
    
    Garbagebag = {
        rolls = 0,
        items = {

        }
    },
    
    Tote = {
        rolls = 0,
        items = {

        }
    },
    
    FirstAidKit = {
        rolls = 1,
        items = {
            "Bandaid", 200,
            "Bandaid", 200,
            "Bandaid", 200,
            "Bandaid", 70,
            "Bandage", 200,
            "Bandage", 20,
            "Bandage", 10,
            "CottonBalls", 200,
            "CottonBalls", 70,
            "CottonBalls", 30,
            "Disinfectant", 100,
            "AlcoholWipes", 200,
            "AlcoholWipes", 70,
            "AlcoholWipes", 30,
            "Tweezers", 100,
            "SutureNeedle", 100,
            "SutureNeedle", 50,
            "SutureNeedleHolder", 50,
            "Scalpel", 5,
            "Gloves_Surgical", 5,
            "Scissors", 5,
        },
        fillRand = 0,
    },
    
    Bag_JanitorToolbox = {
        rolls = 1,
        items = {
            "PipeWrench", 50,
            "Wrench", 50,
            "HandTorch", 50,
            "Hammer", 50,
            "Saw", 50,
            "Screwdriver", 50,
            "Crowbar", 25,
            "DuctTape", 10,
            "Scotchtape", 10,
            "RippedSheets", 10,
            "RippedSheets", 10,
            "RippedSheetsDirty", 25,
            "RippedSheetsDirty", 25,
        },
        fillRand = 0,
    },
    
    Bag_ShotgunBag = {
        rolls = 1,
        items = {
            "Shotgun", 100,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
        },
        fillRand = 0,
    },
    
    Bag_ShotgunSawnoffBag = {
        rolls = 1,
        items = {
            "ShotgunSawnoff", 100,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
        },
        fillRand = 0,
    },
    
    Bag_ShotgunDblBag = {
        rolls = 1,
        items = {
            "DoubleBarrelShotgun", 100,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
        },
        fillRand = 0,
    },
    
    Bag_ShotgunDblSawnoffBag = {
        rolls = 1,
        items = {
            "DoubleBarrelShotgunSawnoff", 100,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
            "ShotgunShellsBox", 25,
        },
        fillRand = 0,
    },

-- ===================
--  Profession Houses 
-- ===================    

    BandPractice = {
        crate = {
            rolls = 5,
            items = {
                "Disc", 10,
                "Disc", 10,
                "CDplayer", 2,
                "Earbuds", 4,
                "Speaker", 7,
                "Speaker", 7,
                "Headphones", 10,
                "Keytar", 2,
                "GuitarAcoustic", 2,
                "GuitarElectricBlack", 2,
                "GuitarElectricBlue", 2,
                "GuitarElectricRed", 2,
                "GuitarElectricBassBlue", 2,
                "GuitarElectricBassBlack", 2,
                "GuitarElectricBassRed", 2,
            }
        },
        
        metal_shelves = {
            rolls = 2,
            items = {
                "Disc", 10,
                "Disc", 10,
                "CDplayer", 2,
                "Earbuds", 4,
                "Speaker", 7,
                "Speaker", 7,
                "Headphones", 10,
                "Keytar", 2,
                "GuitarAcoustic", 2,
                "GuitarElectricBlack", 2,
                "GuitarElectricBlue", 2,
                "GuitarElectricRed", 2,
                "GuitarElectricBassBlue", 2,
                "GuitarElectricBassBlack", 2,
                "GuitarElectricBassRed", 2,
            }
        },
    },
    
    Carpenter = {
        counter = {
            rolls = 4,
            items = {
                "Hammer", 17,
                "NailsBox", 2,
                "NailsBox", 2,
                "Nails", 7,
                "Nails", 7,
                "Screwdriver", 5,
                "Toolbox", 3,
                "Saw", 10,
                "GardenSaw", 10,
                "Axe", 7,
                "WoodAxe", 7,
                "LeadPipe", 2,
                "HandAxe", 7,
                "PipeWrench", 2,
                "ClubHammer", 10,
                "WoodenMallet", 13,
                "DuctTape", 2,
                "Glue", 2,
                "Twine", 2,
                "Woodglue", 10,
                "BookCarpentry1", 4,
                "BookCarpentry2", 3,
                "BookCarpentry3", 2,
                "BookCarpentry4", 1,
                "BookCarpentry5", 0.6,
            }
        },
    },
    
    Chef = {
        counter = {
            rolls = 4,
            items = {
                "BookCooking1", 4,
                "BookCooking2", 3,
                "BookCooking3", 2,
                "BookCooking4", 1,
                "BookCooking5", 0.6,
            }
        },
    },
    
    Electrician = {
        counter = {
            rolls = 3,
            items = {
                "MetalPipe", 5,
                "ElectronicsScrap", 10,
                "ElectronicsScrap", 10,
                "ElectronicsScrap", 10,
                "ScrapMetal", 4,
                "Twine", 10,
                "Amplifier", 7,
                "Sparklers", 7,
                "Aluminum", 7,
                "TriggerCrafted", 7,
                "MotionSensor", 7,
                "DuctTape", 3,
                "TimerCrafted", 7,
                "RemoteCraftedV1", 7,
                "RemoteCraftedV2", 5,
                "RemoteCraftedV3", 3,
                "Screwdriver", 5,
                "Toolbox", 3,
                "ElectronicsMag1", 3,
                "ElectronicsMag2", 3,
                "ElectronicsMag3", 3,
                "ElectronicsMag4", 1.5,
                "ElectronicsMag5", 3,
                "EngineerMagazine1", 2,
                "EngineerMagazine2", 2,
                "Radio.RadioMag1", 3,
                "Radio.RadioMag2", 2,
                "Radio.RadioMag3", 1,
                "Radio.ElectricWire", 7,
                "BookElectrician1",0.9,
                "BookElectrician2",0.5,
            }
        },
    },
    
    Farmer = {
        counter = {
            rolls = 4,
            items = {
                "farming.CarrotBagSeed", 10,
                "farming.BroccoliBagSeed", 10,
                "farming.RedRadishBagSeed", 10,
                "farming.StrewberrieBagSeed", 10,
                "farming.TomatoBagSeed", 10,
                "farming.PotatoBagSeed", 10,
                "farming.CabbageBagSeed", 10,
                "farming.HandShovel", 6,
                "LeafRake", 2,
                "GardenFork", 2,
                "Rake", 2,
                "HandScythe", 3,
                "HandFork", 3,
                "Shovel", 3,
                "Shovel2", 3,
                "farming.WateredCan", 6,
                "BookFarming1", 4,
                "BookForaging1", 2,
                "BookForaging2", 1,
                "BookForaging3", 0.7,
                "BookForaging4", 0.5,
                "BookForaging5", 0.3,
                "FarmingMag1", 4,
                "BookFarming2", 3,
                "BookFarming3", 2,
                "BookFarming4", 1,
                "BookFarming5", 0.6,
                
            }
        },
    },
    
    Nurse = {
        medicine = {
            rolls = 3,
            items = {
                "Pills", 20,
                "PillsBeta", 20,
                "PillsAntiDep", 20,
                "PillsSleepingTablets", 20,
                "PillsVitamins", 20,
                "Bandage", 20,
                "Bandage", 20,
                "Bandaid", 20,
                "Bandaid", 20,
                "FirstAidKit", 2,
                "Tweezers", 10,
                "Disinfectant", 20,
                "AlcoholWipes", 20,
                "SutureNeedle", 10,
                "SutureNeedleHolder", 10,
                "Antibiotics", 10,
                "Scalpel", 10,
            }
        },
        
        counter = {
            rolls = 3,
            items = {
                "Pills", 20,
                "PillsBeta", 20,
                "PillsAntiDep", 20,
                "PillsSleepingTablets", 20,
                "PillsVitamins", 20,
                "Bandage", 20,
                "Bandage", 20,
                "Bandaid", 20,
                "Bandaid", 20,
                "FirstAidKit", 2,
                "Tweezers", 10,
                "Disinfectant", 20,
                "AlcoholWipes", 20,
                "SutureNeedle", 10,
                "SutureNeedleHolder", 10,
                "Antibiotics", 5,
                "Scalpel", 10,
            }
        },
    },

-- ================
--      Caches     
-- ================

    SafehouseLoot = {
        counter = {
            procedural = true,
            procList = {
                {name="KitchenCannedFood", min=1, max=7},
                {name="KitchenDryFood", min=1, max=2},
                {name="MeleeWeapons", min=1, max=2},
                {name="FirearmWeapons", min=1, max=1},
            },
        },
        
        medicine = {
            rolls = 3,
            items = {
                "Pills", 10,
                "PillsBeta", 10,
                "PillsAntiDep", 10,
                "PillsSleepingTablets", 10,
                "PillsVitamins", 10,
                "Bandage", 10,
                "Bandage", 10,
                "Bandaid", 10,
                "Bandaid", 10,
                "FirstAidKit", 2,
                "Tweezers", 5,
                "Disinfectant", 10,
                "AlcoholWipes", 5,
                "SutureNeedle", 5,
                "SutureNeedleHolder", 5,
                "Antibiotics", 5,
                "Scalpel", 5,
            }
        },
    },
    
    ShotgunCache1 = {
        ShotgunBox = {
            rolls = 1,
            items = {
                "Shotgun", 500,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
            }
        },
        
        Bag_DuffelBagTINT = {
            rolls = 1,
            items = {
                "Shotgun", 500,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
            },
            fillRand=1,
        },
    },
    
    ShotgunCache2 = {
        ShotgunBox = {
            rolls = 1,
            items = {
                "Shotgun", 500,
                "Shotgun", 5,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShells", 30,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
                "ShotgunShellsBox", 20,
                "ShotgunShells", 10,
                "ShotgunShells", 10,
                "ShotgunShells", 10,
                "ShotgunShells", 10,
                "ShotgunShellsBox", 8,
                "ShotgunShellsBox", 8,
                "ShotgunShellsBox", 8,
            }
        },
        
        counter = {
            rolls = 1,
            items = {
                "Shotgun", 8,
                "ShotgunShells", 8,
                "ShotgunShells", 8,
                "ShotgunShellsBox", 5,
            }
        },
    },
    
    ToolsCache1 = {
        ToolsBox = {
            rolls = 1,
            items = {
                "Nails", 30,
                "Nails", 30,
                "Hammer", 10,
                "Tarp", 10,
                "Saw", 10,
                "GardenSaw", 10,
                "Plank", 10,
                "Plank", 10,
                "Axe", 3,
                "WoodAxe", 3,
                "NailsBox", 4,
                "NailsBox", 4,
                "DuctTape", 8,
                "Glue", 8,
                "Scotchtape", 8,
                "Woodglue", 8,
                "Rope", 8,
                "LeadPipe", 10,
                "HandAxe", 5,
                "PipeWrench", 7,
                "ClubHammer", 7,
                "WoodenMallet", 7,
            },
        },
        
        counter = {
            rolls = 1,
            items = {
                "Nails", 10,
                "Nails", 10,
                "Hammer", 5,
                "Tarp", 5,
                "Saw", 5,
                "GardenSaw", 5,
                "Plank", 5,
                "Plank", 5,
                "Axe", 2,
                "WoodAxe", 2,
                "NailsBox", 4,
                "NailsBox", 4,
                "DuctTape", 3,
                "Glue", 3,
                "Scotchtape", 3,
                "Woodglue", 3,
                "Rope", 3,
                "Shovel", 0.5,
                "Shovel2", 0.5,
                "farming.HandShovel", 3,
                "HandScythe", 3,
                "HandFork", 3,
                "LeadPipe", 10,
                "HandAxe", 5,
                "PipeWrench", 7,
                "ClubHammer", 7,
                "WoodenMallet", 7,
            },
        },
        
        Bag_DuffelBagTINT = {
            rolls = 1,
            items = {
                "NailsBox", 2,
                "NailsBox", 2,
                "Hammer", 10,
                "Tarp", 10,
                "Saw", 10,
                "GardenSaw", 10,
                "Plank", 10,
                "Plank", 10,
                "Axe", 3,
                "WoodAxe", 3,
                "NailsBox", 2,
                "NailsBox", 2,
                "DuctTape", 8,
                "Glue", 8,
                "Scotchtape", 8,
                "Woodglue", 8,
                "Rope", 8,
                "LeadPipe", 10,
                "HandAxe", 5,
                "PipeWrench", 7,
                "ClubHammer", 7,
                "WoodenMallet", 7,
            },
            fillRand = 1,
        },
    },
    
    GunCache1 = {
        GunBox = {
            rolls = 1,
            items = {
                "Pistol", 50,
                "Pistol2", 50,
                "Pistol3", 5,
                "Revolver_Short", 3,
                "Revolver", 2,
                "Revolver_Long", 1,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
            },
            dontSpawnAmmo = true,
        },
        
        counter = {
            rolls = 1,
            items = {
                "Pistol", 2,
                "Pistol2", 2,
                "Pistol3", 1,
                "Revolver_Short", 1.5,
                "Revolver", 1,
                "Revolver_Long", 0.5,
            },
        },
        
        Bag_DuffelBagTINT = {
            rolls = 1,
            items = {
                "Pistol", 50,
                "Pistol2", 50,
                "Pistol3", 5,
                "Revolver_Short", 5,
                "Revolver", 3,
                "Revolver_Long", 2,
                "Shotgun", 10,
                "DoubleBarrelShotgun", 7,
            },
            fillRand = 1,
        },
    },
    
    GunCache2 = {
        GunBox = {
            rolls = 1,
            items = {
                "Pistol", 50,
                "Pistol2", 50,
                "Pistol3", 5,
                "Revolver_Short", 3,
                "Revolver", 2,
                "Revolver_Long", 1,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mm", 10,
                "Bullets9mm", 10,
                "Bullets9mm", 10,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
            },
            dontSpawnAmmo = true,
        },
        
        Bag_DuffelBagTINT = {
            rolls = 1,
            items = {
                "Pistol", 50,
                "Pistol2", 50,
                "Pistol3", 5,
                "Revolver_Short", 3,
                "Revolver", 2,
                "Revolver_Long", 1,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mm", 30,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mmBox", 20,
                "Bullets9mm", 10,
                "Bullets9mm", 10,
                "Bullets9mm", 10,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
                "Bullets9mmBox", 8,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
                "Bullets38Box", 10,
                "Bullets44Box", 10,
                "Bullets45Box", 10,
            },
            fillRand=1,
        },
    },
    
    SurvivorCache1 = {
        counter = {
            procedural = true,
            procList = {
                {name="KitchenCannedFood", min=1, max=7},
                {name="KitchenDryFood", min=1, max=2},
                {name="MeleeWeapons", min=1, max=2},
                {name="FirearmWeapons", min=1, max=1},
            },
        },
        
        medicine = {
            rolls = 1,
            items = {
                "Pills", 10,
                "PillsBeta", 10,
                "PillsAntiDep", 10,
                "PillsSleepingTablets", 10,
                "PillsVitamins", 10,
                "Bandage", 10,
                "Bandage", 10,
                "Bandaid", 10,
                "Bandaid", 10,
                "FirstAidKit", 2,
                "Tweezers", 5,
                "Disinfectant", 10,
                "AlcoholWipes", 5,
                "SutureNeedle", 5,
                "SutureNeedleHolder", 5,
                "Antibiotics", 5,
            }
        },
        
        SurvivorCrate = {
            rolls = 1,
            items = {
                "Crisps",15,
                "Crisps2", 15,
                "Crisps3", 15,
                "Crisps4", 15,
                "Cereal", 15,
                "Dogfood", 15,
                "TVDinner",15,
                "TinnedSoup", 15,
                "TinnedBeans", 15,
                "CannedCornedBeef", 15,
                "Macandcheese", 15,
                "CannedChili", 15,
                "CannedBolognese", 15,
                "CannedCarrots2", 15,
                "CannedCorn", 15,
                "CannedMushroomSoup", 15,
                "CannedPeas", 15,
                "CannedPotato2", 15,
                "CannedSardines", 15,
                "CannedTomato2", 15,
                "Shotgun", 1,
                "DoubleBarrelShotgun", 0.5,
                "ShotgunShellsBox", 5,
                "ShotgunShellsBox", 5,
                "Machete", 1,
            }
        },
    },
    
    SurvivorCache2 = {
        counter = {
            procedural = true,
            procList = {
                {name="KitchenCannedFood", min=1, max=7},
                {name="KitchenDryFood", min=1, max=2},
                {name="MeleeWeapons", min=1, max=2},
                {name="FirearmWeapons", min=1, max=1},
            },
        },
        
        medicine = {
            rolls = 2,
            items = {
                "Pills", 10,
                "PillsBeta", 10,
                "PillsAntiDep", 10,
                "PillsSleepingTablets", 10,
                "PillsVitamins", 10,
                "Bandage", 10,
                "Bandage", 10,
                "Bandaid", 10,
                "Bandaid", 10,
                "FirstAidKit", 2,
                "Tweezers", 5,
                "Disinfectant", 10,
                "AlcoholWipes", 5,
                "SutureNeedle", 5,
                "SutureNeedleHolder", 5,
                "Antibiotics", 5,
            }
        },
        
        SurvivorCrate = {
            rolls = 1,
            items = {
                "Crisps",15,
                "Crisps2", 15,
                "Crisps3", 15,
                "Crisps4", 15,
                "Cereal", 15,
                "Dogfood", 15,
                "TVDinner",15,
                "TinnedSoup", 15,
                "TinnedBeans", 15,
                "CannedCornedBeef", 15,
                "Macandcheese", 15,
                "CannedChili", 15,
                "CannedBolognese", 15,
                "CannedCarrots2", 15,
                "CannedCorn", 15,
                "CannedMushroomSoup", 15,
                "CannedPeas", 15,
                "CannedPotato2", 15,
                "CannedSardines", 15,
                "CannedTomato2", 15,
                "Shotgun", 1,
                "DoubleBarrelShotgun", 0.5,
                "ShotgunShellsBox", 5,
                "ShotgunShellsBox", 5,
                "Machete", 1,
            }
        },
    },
}

table.insert(Distributions, 1, distributionTable);

--for mod compat:
SuburbsDistributions = distributionTable;
