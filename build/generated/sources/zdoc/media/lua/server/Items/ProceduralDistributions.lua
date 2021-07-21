---@class ProceduralDistributions
ProceduralDistributions = {};

ProceduralDistributions.list = {
    Bakery = {
        rolls = 6,
        items = {
            "Pancakes", 7,
            "Pancakes", 7,
            "Waffles", 7,
            "Waffles", 7,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "CheeseSandwich", 7,
            "CheeseSandwich", 7,
            "PeanutButterSandwich", 7,
            "PeanutButterSandwich", 7,
            "Pie", 5,
            "Pie", 5,
            "PiePumpkin", 5,
            "PiePumpkin", 5,
            "CakeSlice", 5,
            "CakeSlice", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Waffles", 10,
                "Bread", 10,
                "Bread", 10,
                "Pie", 5,
                "PiePumpkin", 5,
                "CakeSlice", 5,
                "Pancakes", 7,
            }
        }
    },
    
    BakeryBread = {
        rolls = 6,
        items = {
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
            "Bread", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Bread", 10,
                "Bread", 10,
            }
        }
    },
    
    BakeryCake = {
        rolls = 6,
        items = {
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
            "CakeSlice", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "CakeSlice", 10,
                "CakeSlice", 10,
            }
        }
    },
    
    BakeryMisc = {
        rolls = 6,
        items = {
            "CookieChocolateChip", 10,
            "CookieChocolateChip", 10,
            "CookieChocolateChip", 10,
            "CookieJelly", 10,
            "CookieJelly", 10,
            "CookieJelly", 10,
            "Cupcake", 10,
            "Cupcake", 10,
            "Cupcake", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "CookieChocolateChip", 10,
                "CookieJelly", 10,
                "Cupcake", 10,
            }
        }
    },
    
    BakeryPie = {
        rolls = 6,
        items = {
            "Pie", 10,
            "Pie", 10,
            "Pie", 10,
            "Pie", 10,
            "Pie", 10,
            "Pie", 10,
            "PiePumpkin", 10,
            "PiePumpkin", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Pie", 10,
                "Pie", 10,
            }
        }
    },
    
    BarCounterLiquor = {
        rolls = 4,
        items = {
            "WhiskeyFull", 3,
            "WhiskeyFull", 3,
            "Wine", 3,
            "Wine", 3,
            "Wine", 3,
            "Wine2", 3,
            "Wine2", 3,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "WhiskeyFull", 3,
                "Wine", 3,
                "Wine2", 3,
                "BeerCan", 7,
                "BeerBottle", 5,
            }
        }
    },
    
    BarCounterMisc = {
        rolls = 4,
        items = {
            "Lighter", 5,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "PopBottle", 5,
            "PopBottle", 5,
            "Pop", 7,
            "Pop", 7,
            "Pop2", 7,
            "Pop2", 7,
            "Pop3", 7,
            "Pop3", 7,
            "Peanuts", 7,
            "Peanuts", 7,
            "Pickles", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Lighter", 5,
                "Cigarettes", 10,
                "Peanuts", 7,
                "Pop", 7,
            }
        }
    },
    
    BarCounterWeapon = {
        rolls = 4,
        items = {
            "WhiskeyFull", 3,
            "WhiskeyFull", 3,
            "WhiskeyFull", 3,
            "WhiskeyFull", 3,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerCan", 7,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
            "BeerBottle", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Bag_ShotgunDblSawnoffBag", 0.1,
                "BeerCan", 10,
                "BeerCan", 10,
                "BaseballBat", 25,
            }
        }
    },
    
    BarCrateDarts = {
        rolls = 6,
        items = {
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
            "Dart", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Dart", 10,
                "Dart", 10,
                "Dart", 10,
                "Dart", 10,
            }
        }
    },
    
    BarCratePool = {
        rolls = 6,
        items = {
            "Poolcue", 10,
            "Poolcue", 10,
            "PoolBall", 10,
            "PoolBall", 10,
            "PoolBall", 10,
            "PoolBall", 10,
            "PoolBall", 10,
            "PoolBall", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Poolcue", 10,
                "Poolcue", 10,
                "Poolcue", 10,
                "Poolcue", 10,
            }
        }
    },
    
    BarShelfLiquor = {
        rolls = 6,
        items = {
            "BeerCan", 10,
            "BeerCan", 10,
            "BeerCan", 10,
            "BeerCan", 10,
            "BeerCan", 10,
            "BeerCan", 10,
            "BeerBottle", 10,
            "BeerBottle", 10,
            "BeerBottle", 10,
            "BeerBottle", 10,
            "BeerBottle", 10,
            "BeerBottle", 10,
            "Wine", 7,
            "Wine", 7,
            "Wine", 7,
            "Wine", 7,
            "Wine2", 7,
            "Wine2", 7,
            "Wine2", 7,
            "Wine2", 7,
            "WhiskeyFull", 5,
            "WhiskeyFull", 5,
            "WhiskeyFull", 5,
            "WhiskeyFull", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "BeerCan", 10,
                "BeerBottle", 10,
                "Wine", 7,
                "Wine2", 7,
                "WhiskeyFull", 5,
            }
        }
    },
    
    BathroomCabinet = {
        rolls = 4,
        items = {
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandage", 7,
            "Pills", 7,
            "PillsBeta", 3,
            "PillsAntiDep", 3,
            "PillsSleepingTablets", 5,
            "PillsVitamins", 10,
            "Antibiotics", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Toothbrush", 7,
                "Toothpaste", 7,
                "Mirror", 5,
                "Cologne", 3,
                "Perfume", 3,
                "MakeupFoundation", 3,
                "MakeupEyeshadow", 3,
                "Lipstick", 3,
                "Razor", 3,
                "Comb", 5,
                "Tweezers", 10,
                "Disinfectant", 5,
            }
        }
    },
    
    BathroomCounter = {
        rolls = 4,
        items = {
            "Sheet", 10,
            "BathTowel", 20,
            "Soap2", 10,
            "Hairspray", 5,
            "Hairgel", 1,
            "HairDyeBlonde", 1,
            "HairDyeBlack", 1,
            "HairDyeWhite", 1,
            "HairDyeGinger", 1,
            "HairDyeLightBrown", 1,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandage", 7,
            "Pills", 7,
            "PillsBeta", 3,
            "PillsAntiDep", 3,
            "PillsSleepingTablets", 5,
            "PillsVitamins", 10,
            "Antibiotics", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Toothbrush", 7,
                "Toothpaste", 7,
                "Cologne", 3,
                "Perfume", 3,
                "Lipstick", 3,
                "MakeupEyeshadow", 3,
                "MakeupFoundation", 3,
                "Mirror", 5,
                "Razor", 3,
                "Comb", 5,
                "DeadMouse", 0.5,
                "FirstAidKit", 3,
                "Plunger", 10,
                "PipeWrench", 3,
                "Tweezers", 10,
                "Disinfectant", 5,
            }
        }
    },
    
    BathroomCounterEmpty = {
        rolls = 0,
        items = {

        },
        junk = {
            rolls = 1,
            items = {
                "DeadMouse", 0.5,
            }
        }
    },
    
    BathroomCounterNoMeds = {
        rolls = 4,
        items = {
            "Sheet", 10,
            "BathTowel", 20,
            "Soap2", 10,
            "Hairspray", 5,
            "Hairgel", 1,
            "HairDyeBlonde", 1,
            "HairDyeBlack", 1,
            "HairDyeWhite", 1,
            "HairDyeGinger", 1,
            "HairDyeLightBrown", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "DeadMouse", 0.5,
                "Plunger", 10,
                "PipeWrench", 3,
            }
        }
    },
    
    BinBar = {
        rolls = 1,
        items = {
            "Cockroach", 4,
            "Cockroach", 4,
            "Cockroach", 2,
            "Cockroach", 2,
            "DeadRat", 2,
            "DeadMouse", 1,
            "WhiskeyEmpty", 4,
            "WhiskeyEmpty", 4,
            "WineEmpty", 4,
            "WineEmpty", 4,
            "WineEmpty2", 4,
            "WineEmpty2", 4,
            "BeerEmpty", 4,
            "BeerEmpty", 4,
            "BeerEmpty", 4,
            "BeerEmpty", 4,
            "BeerEmpty", 4,
            "BeerEmpty", 4,
            "BandageDirty", 2,
            "BandageDirty", 2,
            "PopBottleEmpty", 1,
            "PopBottleEmpty", 1,
            "WaterBottleEmpty", 1,
            "WaterBottleEmpty", 1,
            "SmashedBottle", 4,
            "SmashedBottle", 4,
            "Garbagebag", 100,
        }
    },
    
    BinGeneric = {
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
    
    BookstoreBags = {
        rolls = 6,
        items = {
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Bag_Satchel", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Plasticbag", 25,
                "Tote", 20,
                "Bag_Satchel", 7,
            }
        }
    },

    BookstoreCarpentry = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookCarpentry1", 10,
            "BookCarpentry2", 7,
            "BookCarpentry3", 5,
            "BookCarpentry4", 3,
            "BookCarpentry5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookCarpentry1", 10,
                "BookCarpentry2", 7,
                "BookCarpentry3", 5,
                "BookCarpentry4", 3,
                "BookCarpentry5", 1,
            }
        }
    },
    
    BookstoreCooking = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookCooking1", 10,
            "BookCooking2", 7,
            "BookCooking3", 5,
            "BookCooking4", 3,
            "BookCooking5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookCooking1", 10,
                "BookCooking2", 7,
                "BookCooking3", 5,
                "BookCooking4", 3,
                "BookCooking5", 1,
            }
        }
    },
    
    BookstoreElectronics = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookElectrician1", 10,
            "BookElectrician2", 7,
            "BookElectrician3", 5,
            "BookElectrician4", 3,
            "BookElectrician5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookElectrician1", 10,
                "BookElectrician2", 7,
                "BookElectrician3", 5,
                "BookElectrician4", 3,
                "BookElectrician5", 1,
            }
        }
    },
    
    BookstoreFarming = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookFarming1", 10,
            "BookFarming2", 7,
            "BookFarming3", 5,
            "BookFarming4", 3,
            "BookFarming5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookFarming1", 10,
                "BookFarming2", 7,
                "BookFarming3", 5,
                "BookFarming4", 3,
                "BookFarming5", 1,
            }
        }
    },
    
    BookstoreFirstAid = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookFirstAid1", 10,
            "BookFirstAid2", 7,
            "BookFirstAid3", 5,
            "BookFirstAid4", 3,
            "BookFirstAid5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookFirstAid1", 10,
                "BookFirstAid2", 7,
                "BookFirstAid3", 5,
                "BookFirstAid4", 3,
                "BookFirstAid5", 1,
            }
        }
    },
    
    BookstoreFishing = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookFishing1", 10,
            "BookFishing2", 7,
            "BookFishing3", 5,
            "BookFishing4", 3,
            "BookFishing5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookFishing1", 10,
                "BookFishing2", 7,
                "BookFishing3", 5,
                "BookFishing4", 3,
                "BookFishing5", 1,
            }
        }
    },
    
    BookstoreForaging = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookForaging1", 10,
            "BookForaging2", 7,
            "BookForaging3", 5,
            "BookForaging4", 3,
            "BookForaging5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookForaging1", 10,
                "BookForaging2", 7,
                "BookForaging3", 5,
                "BookForaging4", 3,
                "BookForaging5", 1,
            }
        }
    },
    
    BookstoreMechanic = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookMechanic1", 10,
            "BookMechanic2", 7,
            "BookMechanic3", 5,
            "BookMechanic4", 3,
            "BookMechanic5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookMechanic1", 10,
                "BookMechanic2", 7,
                "BookMechanic3", 5,
                "BookMechanic4", 3,
                "BookMechanic5", 1,
            }
        }
    },
    
    BookstoreMetalwork = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookMetalWelding1", 10,
            "BookMetalWelding2", 7,
            "BookMetalWelding3", 5,
            "BookMetalWelding4", 3,
            "BookMetalWelding5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookMetalWelding1", 10,
                "BookMetalWelding2", 7,
                "BookMetalWelding3", 5,
                "BookMetalWelding4", 3,
                "BookMetalWelding5", 1,
            }
        }
    },
    
    BookstoreMisc = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Book", 20,
                "Book", 20,
                "Book", 20,
                "Book", 20,
            }
        }
    },
    
    BookstoreStationery = {
        rolls = 6,
        items = {
            "Notebook", 20,
            "Notebook", 20,
            "Notebook", 20,
            "Notebook", 20,
            "Pencil", 10,
            "Pencil", 10,
            "Eraser", 10,
            "BluePen", 7,
            "RedPen", 7,
            "Pen", 5,
            "Scissors", 5,
            "Scotchtape", 5,
            "PaperclipBox", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Notebook", 20,
                "Pencil", 10,
                "BluePen", 7,
                "RedPen", 7,
                "Pen", 5,
                "Scissors", 5,
                "Scotchtape", 5,
            }
        }
    },
    
    BookstoreTailoring    = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookTailoring1", 10,
            "BookTailoring2", 7,
            "BookTailoring3", 5,
            "BookTailoring4", 3,
            "BookTailoring5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookTailoring1", 10,
                "BookTailoring2", 7,
                "BookTailoring3", 5,
                "BookTailoring4", 3,
                "BookTailoring5", 1,
            }
        }
    },
    
    BookstoreTrapping = {
        rolls = 6,
        items = {
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "Book", 20,
            "BookTrapping1", 10,
            "BookTrapping2", 7,
            "BookTrapping3", 5,
            "BookTrapping4", 3,
            "BookTrapping5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "BookTrapping1", 10,
                "BookTrapping2", 7,
                "BookTrapping3", 5,
                "BookTrapping4", 3,
                "BookTrapping5", 1,
            }
        }
    },
    
    BreakRoomCounter = {
        rolls = 4,
        items = {
            "Spoon", 20,
            "Mugl", 20,
            "Bowl", 20,
            "Cereal", 10,
            "Coffee2", 20,
            "Teabag2", 10,
            "Honey", 5,
            "Sugar", 7,
            "OatsRaw", 10,
            "Popcorn", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Kettle", 75,
                "TinOpener", 25,
                "Saucepan", 25,
                "DishCloth", 50,
            }
        }
    },
    
    BreakRoomShelves = {
        rolls = 4,
        items = {
            "Cereal", 10,
            "Coffee2", 20,
            "Teabag2", 10,
            "Honey", 5,
            "Sugar", 7,
            "OatsRaw", 10,
            "Popcorn", 10,
            "SheetPaper2", 10,
            "Magazine", 10,
            "Newspaper", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Radio.RadioBlack", 25,
                "Radio.RadioRed", 25,
            }
        }
    },
    
    BurgerKitchenButcher = {
        rolls = 4,
        items = {
            "Pepper", 5,
            "Salt", 5,
            "Twine", 7,
            "Steak", 10,
            "Steak", 10,
            "Steak", 10,
            "Steak", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
        },
        junk = {
            rolls = 3,
            items = {
                "Apron_White", 10,
                "Apron_Black", 10,
                "KitchenKnife", 7,
                "MeatCleaver", 5,
                "Gloves_LeatherGlovesBlack", 5,
            }
        }
    },
    
    BurgerKitchenFreezer = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "farming.Bacon", 7,
            "farming.Bacon", 7,
            "Steak", 5,
            "Steak", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
                "MeatPatty", 10,
                "farming.Bacon", 7,
                "Steak", 5,
            }
        }
    },
    
    BurgerKitchenFridge = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "Processedcheese", 7,
            "Processedcheese", 7,
            "farming.Bacon", 7,
            "farming.Bacon", 7,
            "farming.Tomato", 7,
            "farming.Tomato", 7,
            "Lettuce", 7,
            "Lettuce", 7,
            "Onion", 7,
            "Onion", 7,
            "Pickles", 5,
            "Pickles", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
                "Processedcheese", 7,
                "farming.Bacon", 7,
                "farming.Tomato", 7,
                "Lettuce", 7,
                "Onion", 7,
                "Pickles", 5,
            }
        }
    },
    
    ButcherChicken = {
        rolls = 6,
        items = {
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Chicken", 10,
            }
        }
    },
    
    ButcherChops = {
        rolls = 6,
        items = {
            "Steak", 7,
            "Steak", 7,
            "Steak", 7,
            "Steak", 7,
            "PorkChop", 7,
            "PorkChop", 7,
            "PorkChop", 7,
            "PorkChop", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Steak", 7,
                "PorkChop", 7,
            }
        }
    },
    
    ButcherFish = {
        rolls = 6,
        items = {
            "Salmon", 7,
            "Salmon", 7,
            "Salmon", 7,
            "Salmon", 7,
            "Catfish", 10,
            "Pike", 10,
            "Bass", 10,
            "Trout", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Salmon", 7,
                "Catfish", 10,
                "Pike", 10,
                "Bass", 10,
                "Trout", 10,
            }
        }
    },
    
    ButcherGround = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
            }
        }
    },
    
    ButcherSmoked = {
        rolls = 6,
        items = {
            "Ham", 7,
            "Ham", 7,
            "Ham", 7,
            "Ham", 7,
            "farming.Bacon", 10,
            "farming.Bacon", 10,
            "farming.Bacon", 10,
            "farming.Bacon", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Ham", 7,
                "farming.Bacon", 10,
            }
        }
    },
    
    CafeKitchenFridge = {
        rolls = 6,
        items = {
            "Milk", 10,
            "Milk", 10,
            "Milk", 10,
            "Milk", 10,
            "EggCarton", 7,
            "EggCarton", 7,
            "Butter", 5,
            "Butter", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Milk", 10,
                "EggCarton", 7,
                "Butter", 5,
            }
        }
    },

    CandyStoreSnacks = {
        rolls = 6,
        items = {
            "Candycane", 5,
            "Candycane", 5,
            "Chocolate", 10,
            "Chocolate", 10,
            "Chocolate", 10,
            "Chocolate", 10,
            "CookieChocolateChip", 10,
            "CookieChocolateChip", 10,
            "CookieJelly", 7,
            "CookieJelly", 7,
            "Cupcake", 5,
            "Cupcake", 5,
            "MintCandy", 5,
            "MintCandy", 5,
            "Modjeska", 7,
            "Modjeska", 7,
            "Modjeska", 7,
            "Modjeska", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Candycane", 5,
                "Chocolate", 10,
                "CookieChocolateChip", 10,
                "CookieJelly", 7,
                "Cupcake", 5,
                "MintCandy", 5,
                "Modjeska", 7,
            }
        }
    },

    CandyStoreSugar = {
        rolls = 6,
        items = {
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Sugar", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Sugar", 10,
                "Sugar", 10,
            }
        }
    },
    
    CampingStoreBackpacks = {
        rolls = 6,
        items = {
            "Bag_Schoolbag", 10,
            "Bag_Schoolbag", 10,
            "Bag_DuffelBag", 7,
            "Bag_DuffelBag", 7,
            "Bag_NormalHikingBag", 5,
            "Bag_NormalHikingBag", 5,
            "Bag_BigHikingBag", 3,
            "Bag_BigHikingBag", 3,
            "Bag_ALICEpack", 1,
            "Bag_ALICEpack", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "Bag_Schoolbag", 10,
                "Bag_DuffelBag", 7,
                "Bag_NormalHikingBag", 5,
                "Bag_BigHikingBag", 3,
                "Bag_ALICEpack", 1,
            }
        }
    },
    
    CampingStoreBooks = {
        rolls = 6,
        items = {
            "Magazine", 20,
            "Magazine", 20,
            "Magazine", 20,
            "Magazine", 20,
            "Book", 10,
            "Book", 10,
            "HuntingMag1", 5,
            "HuntingMag2", 5,
            "HuntingMag3", 5,
            "FishingMag1", 5,
            "FishingMag2", 5,
            "BookFishing1", 10,
            "BookFishing2", 7,
            "BookFishing3", 5,
            "BookFishing4", 3,
            "BookFishing5", 1,
            "BookTrapping1", 10,
            "BookTrapping2", 7,
            "BookTrapping3", 5,
            "BookTrapping4", 3,
            "BookTrapping5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "Magazine", 20,
                "Book", 10,
                "HuntingMag1", 5,
                "HuntingMag2", 5,
                "HuntingMag3", 5,
                "FishingMag1", 5,
                "FishingMag2", 5,
                "BookFishing1", 10,
                "BookFishing2", 7,
                "BookFishing3", 5,
                "BookFishing4", 3,
                "BookFishing5", 1,
                "BookTrapping1", 10,
                "BookTrapping2", 7,
                "BookTrapping3", 5,
                "BookTrapping4", 3,
                "BookTrapping5", 1,
            }
        }
    },
    
    CampingStoreClothes = {
        rolls = 6,
        items = {
            "Jacket_ArmyCamoGreen", 7,
            "PonchoGreenDOWN", 7,
            "Shirt_CamoGreen", 7,
            "Shirt_CamoGreen", 7,
            "Shirt_Lumberjack", 7,
            "Shirt_Lumberjack", 7,
            "Tshirt_ArmyGreen", 10,
            "Tshirt_ArmyGreen", 10,
            "Tshirt_CamoGreen", 10,
            "Tshirt_CamoGreen", 10,
            "Vest_Hunting_Camo", 7,
            "Vest_Hunting_Camo", 7,
            "Vest_Hunting_CamoGreen", 7,
            "Vest_Hunting_CamoGreen", 7,
            "Vest_Hunting_Orange", 10,
            "Vest_Hunting_Orange", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Jacket_ArmyCamoGreen", 7,
                "PonchoGreenDOWN", 7,
                "Shirt_CamoGreen", 7,
                "Shirt_Lumberjack", 7,
                "Tshirt_ArmyGreen", 10,
                "Tshirt_CamoGreen", 10,
                "Vest_Hunting_Camo", 7,
                "Vest_Hunting_CamoGreen", 7,
                "Vest_Hunting_Orange", 10,
            }
        }
    },
    
    CampingStoreGear = {
        rolls = 6,
        items = {
            "camping.TentPeg", 10,
            "camping.TentPeg", 10,
            "camping.TentPeg", 10,
            "camping.TentPeg", 10,
            "Tarp", 7,
            "Tarp", 7,
            "Tarp", 7,
            "Tarp", 7,
            "camping.CampingTentKit", 5,
            "camping.CampingTentKit", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "camping.TentPeg", 10,
                "camping.TentPeg", 10,
                "Tarp", 7,
                "camping.CampingTentKit", 5,
            }
        }
    },
    
    CampingStoreLegwear = {
        rolls = 6,
        items = {
            "Shorts_CamoGreenLong", 10,
            "Shorts_CamoGreenLong", 10,
            "Trousers_CamoGreen", 7,
            "Trousers_CamoGreen", 7,
            "Trousers_CamoGreen", 7,
            "Trousers_CamoGreen", 7,
            "Shoes_ArmyBoots", 7,
            "Shoes_ArmyBoots", 7,
            "Shoes_ArmyBootsDesert", 7,
            "Shoes_ArmyBootsDesert", 7,
            "Shoes_Wellies", 5,
            "Shoes_Wellies", 5,
            "Dungarees", 3,
            "Dungarees", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Shorts_CamoGreenLong", 10,
                "Trousers_CamoGreen", 7,
                "Shoes_ArmyBoots", 7,
                "Shoes_ArmyBootsDesert", 7,
                "Shoes_Wellies", 5,
                "Dungarees", 3,
            }
        }
    },
    
    ClassroomDesk = {
        rolls = 4,
        items = {
            "BluePen", 10,
            "Book", 20,
            "ComicBook", 1,
            "Eraser", 7,
            "Magazine", 7,
            "Notebook", 10,
            "Paperclip", 10,
            "Pen", 10,
            "Pencil", 10,
            "RedPen", 10,
            "Scotchtape", 5,
            "SheetPaper2", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Bracelet_LeftFriendshipTINT", 1,
                "CardDeck", 1,
                "CDplayer", 1,
                "Comb", 1,
                "Cube", 1,
                "Radio.WalkieTalkie1",0.05,
                "Scissors", 5,
                "VideoGame", 1,
                "Yoyo", 1,
            }
        }
    },
    
    ClassroomMisc = {
        rolls = 4,
        items = {
            "Bandaid", 10,
            "BluePen", 10,
            "Book", 20,
            "Book", 20,
            "BookCarpentry1", 2,
            "BookCarpentry2", 1,
            "BookCooking1", 2,
            "BookCooking2", 1,
            "BookElectrician1", 2,
            "BookElectrician2", 1,
            "BookFarming1", 2,
            "BookFarming2", 1,
            "BookFirstAid1", 2,
            "BookFirstAid2", 1,
            "BookFishing1", 2,
            "BookFishing2", 1,
            "BookForaging1", 2,
            "BookForaging2", 1,
            "BookMechanic1", 2,
            "BookMechanic2", 1,
            "BookMetalWelding1", 2,
            "BookMetalWelding2", 1,
            "Pen", 30,
            "Pencil", 15,
            "RedPen", 10,
            "RubberBand", 7,
            "SheetPaper2", 20,
            "Soap2", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Scissors", 3,
                "BucketEmpty", 10,
                "Sponge", 25,
                "DeadMouse", 0.5,
            }
        }
    },
    
    ClassroomShelves = {
        rolls = 4,
        items = {
            "Book", 20,
            "Book", 20,
            "BluePen", 10,
            "BookCarpentry1", 2,
            "BookCarpentry2", 1,
            "BookCooking1", 2,
            "BookCooking2", 1,
            "BookElectrician1", 2,
            "BookElectrician2", 1,
            "BookFarming1", 2,
            "BookFarming2", 1,
            "BookFirstAid1", 2,
            "BookFirstAid2", 1,
            "BookFishing1", 2,
            "BookFishing2", 1,
            "BookForaging1", 2,
            "BookForaging2", 1,
            "BookMechanic1", 2,
            "BookMechanic2", 1,
            "BookMetalWelding1", 2,
            "BookMetalWelding2", 1,
            "Pen", 30,
            "Pencil", 15,
            "RedPen", 10,
            "RubberBand", 7,
            "SheetPaper2", 20,
            "Notebook", 10,
            "Notebook", 10,
            
        },
        junk = {
            rolls = 1,
            items = {
                "Scissors", 3,
                "PaperclipBox", 25,
                "Book", 100,
                "Notebook", 100,
                "Radio.RadioBlack", 2,
                "Radio.RadioRed", 1,
            }
        }
    },
    
    ClothingPoor = {
        rolls = 3,
        items = {
            "Tshirt_DefaultTEXTURE_TINT", 5,
            "TrousersMesh_DenimLight", 2,
            "Trousers_DefaultTEXTURE_TINT", 3,
            "Shirt_Denim", 2,
            "Shirt_Lumberjack", 2,
            "Tshirt_WhiteLongSleeveTINT", 2,
            "Tshirt_WhiteTINT", 2,
            "Tshirt_Rock", 1,
            "Vest_DefaultTEXTURE_TINT", 2,
            "Shorts_CamoGreenLong", 1,
            "Shorts_LongDenim", 2,
            "Shorts_ShortDenim", 2,
            "Skirt_Knees", 2,
            "Skirt_Normal", 2,
            "Trousers_Denim", 2,
            "Trousers_JeanBaggy", 2,
            "Dungarees", 1,
            "Hat_BaseballCap", 1,
            "Hat_BaseballCapKY", 1,
        }
    },
    
    ClothingStorageAllJackets = {
        rolls = 6,
        items = {
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "Jacket_Black", 7,
            "Jacket_Black", 7,
            "Jacket_Black", 7,
            "Jacket_Black", 7,
            "Jacket_Varsity", 5,
            "Jacket_Varsity", 5,
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "JacketLong_Random", 10,
                "JacketLong_Random", 10,
                "Jacket_Black", 7,
                "Jacket_Black", 7,
                "Jacket_Varsity", 5,
                "Jacket_WhiteTINT", 10,
                "Jacket_WhiteTINT", 10,
            }
        }
    },
    
    ClothingStorageAllShirts = {
        rolls = 6,
        items = {
            "HoodieDOWN_WhiteTINT", 10,
            "HoodieDOWN_WhiteTINT", 10,
            "Jumper_DiamondPatternTINT", 3,
            "Jumper_PoloNeck", 5,
            "Jumper_RoundNeck", 7,
            "Jumper_VNeck", 7,
            "Shirt_Baseball_KY", 5,
            "Shirt_Baseball_Rangers", 5,
            "Shirt_Baseball_Z", 5,
            "Shirt_Denim", 7,
            "Shirt_FormalWhite", 10,
            "Shirt_FormalWhite", 10,
            "Shirt_FormalTINT", 7,
            "Shirt_FormalWhite_ShortSleeve", 7,
            "Shirt_FormalWhite_ShortSleeveTINT", 7,
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Tshirt_DefaultDECAL_TINT", 7,
            "Tshirt_DefaultTEXTURE_TINT", 7,
            "Tshirt_IndieStoneDECAL", 3,
            "Tshirt_PoloStripedTINT", 5,
            "Tshirt_PoloTINT", 5,
            "Tshirt_Sport", 10,
            "Tshirt_SportDECAL", 7,
            "Tshirt_WhiteLongSleeveTINT", 10,
            "Tshirt_WhiteTINT", 10,
            "Vest_DefaultTEXTURE_TINT", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "HoodieDOWN_WhiteTINT", 10,
                "HoodieDOWN_WhiteTINT", 10,
                "Jumper_DiamondPatternTINT", 3,
                "Jumper_PoloNeck", 5,
                "Jumper_RoundNeck", 7,
                "Jumper_VNeck", 7,
                "Shirt_Baseball_KY", 5,
                "Shirt_Baseball_Rangers", 5,
                "Shirt_Baseball_Z", 5,
                "Shirt_Denim", 7,
                "Shirt_FormalWhite", 10,
                "Shirt_FormalWhite", 10,
                "Shirt_FormalTINT", 7,
                "Shirt_FormalWhite_ShortSleeve", 7,
                "Shirt_FormalWhite_ShortSleeveTINT", 7,
                "Shirt_Lumberjack", 10,
                "Shirt_Lumberjack", 10,
                "Tshirt_DefaultDECAL_TINT", 7,
                "Tshirt_DefaultTEXTURE_TINT", 7,
                "Tshirt_IndieStoneDECAL", 3,
                "Tshirt_PoloStripedTINT", 5,
                "Tshirt_PoloTINT", 5,
                "Tshirt_Sport", 10,
                "Tshirt_SportDECAL", 7,
                "Tshirt_WhiteLongSleeveTINT", 10,
                "Tshirt_WhiteTINT", 10,
                "Vest_DefaultTEXTURE_TINT", 10,
            }
        }
    },
    
    ClothingStorageFootwear = {
        rolls = 6,
        items = {
            "Shoes_ArmyBoots", 7,
            "Shoes_ArmyBootsDesert", 7,
            "Shoes_Black", 7,
            "Shoes_BlackBoots", 10,
            "Shoes_BlueTrainers", 5,
            "Shoes_Brown", 7,
            "Shoes_FlipFlop", 5,
            "Shoes_RedTrainers", 5,
            "Shoes_RidingBoots", 5,
            "Shoes_TrainerTINT", 10,
            "Shoes_TrainerTINT", 10,
            "Shoes_Wellies", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Shoes_ArmyBoots", 7,
                "Shoes_ArmyBootsDesert", 7,
                "Shoes_Black", 7,
                "Shoes_BlackBoots", 10,
                "Shoes_BlueTrainers", 5,
                "Shoes_Brown", 7,
                "Shoes_FlipFlop", 5,
                "Shoes_RedTrainers", 5,
                "Shoes_RidingBoots", 5,
                "Shoes_TrainerTINT", 10,
                "Shoes_TrainerTINT", 10,
                "Shoes_Wellies", 5,
            }
        }
    },
    
    ClothingStorageHeadwear = {
        rolls = 6,
        items = {
            "Hat_BaseballCap", 10,
            "Hat_BaseballCapBlue", 10,
            "Hat_BaseballCapGreen", 10,
            "Hat_BaseballCapKY", 10,
            "Hat_BaseballCapKY_Red", 10,
            "Hat_BaseballCapRed", 10,
            "Hat_Beany", 10,
            "Hat_Beret", 7,
            "Hat_BucketHat", 7,
            "Hat_Cowboy", 5,
            "Hat_Fedora", 10,
            "Hat_Fedora_Delmonte", 7,
            "Hat_GolfHat", 5,
            "Hat_GolfHatTINT", 5,
            "Hat_SummerHat", 5,
            "Hat_SummerHat", 5,
            "Hat_Sweatband", 10,
            "Hat_VisorBlack", 7,
            "Hat_VisorRed", 7,
            "Hat_Visor_WhiteTINT", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_BaseballCap", 10,
                "Hat_BaseballCapBlue", 10,
                "Hat_BaseballCapGreen", 10,
                "Hat_BaseballCapKY", 10,
                "Hat_BaseballCapKY_Red", 10,
                "Hat_BaseballCapRed", 10,
                "Hat_Beany", 10,
                "Hat_Beret", 7,
                "Hat_BucketHat", 7,
                "Hat_Cowboy", 5,
                "Hat_Fedora", 10,
                "Hat_Fedora_Delmonte", 7,
                "Hat_GolfHat", 5,
                "Hat_GolfHatTINT", 5,
                "Hat_SummerHat", 5,
                "Hat_SummerHat", 5,
                "Hat_Sweatband", 10,
                "Hat_VisorBlack", 7,
                "Hat_VisorRed", 7,
                "Hat_Visor_WhiteTINT", 7,
            }
        }
    },
    
    ClothingStorageLegwear = {
        rolls = 6,
        items = {
            "Shorts_LongSport", 7,
            "Shorts_ShortSport", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "Trousers_DefaultTEXTURE_TINT", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_Suit", 5,
            "Trousers_SuitTEXTURE", 7,
            "Trousers_SuitWhite", 7,
            "Trousers_WhiteTINT", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Shorts_LongSport", 7,
                "Shorts_ShortSport", 10,
                "TrousersMesh_DenimLight", 10,
                "TrousersMesh_DenimLight", 10,
                "Trousers_DefaultTEXTURE_TINT", 10,
                "Trousers_Denim", 10,
                "Trousers_Denim", 10,
                "Trousers_JeanBaggy", 10,
                "Trousers_JeanBaggy", 10,
                "Trousers_Suit", 5,
                "Trousers_SuitTEXTURE", 7,
                "Trousers_SuitWhite", 7,
                "Trousers_WhiteTINT", 10,
            }
        }
    },
    
    ClothingStorageWinter = {
        rolls = 6,
        items = {
            "Glasses_SkiGoggles", 5,
            "Hat_BalaclavaFace", 7,
            "Hat_BalaclavaFull", 7,
            "Hat_EarMuffs", 7,
            "Hat_WinterHat", 10,
            "Hat_WoolyHat", 10,
            "Jacket_PaddedDOWN", 7,
            "Jacket_PaddedDOWN", 7,
            "LongJohns", 5,
            "LongJohns_Bottoms", 7,
            "Scarf_StripeBlackWhite", 10,
            "Scarf_StripeBlueWhite", 10,
            "Scarf_StripeRedWhite", 10,
            "Scarf_White", 10,
            "Trousers_Padded", 7,
            "Trousers_Padded", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Glasses_SkiGoggles", 5,
                "Hat_BalaclavaFace", 7,
                "Hat_BalaclavaFull", 7,
                "Hat_EarMuffs", 7,
                "Hat_WinterHat", 10,
                "Hat_WoolyHat", 10,
                "Jacket_PaddedDOWN", 7,
                "LongJohns", 5,
                "LongJohns_Bottoms", 7,
                "Scarf_StripeBlackWhite", 10,
                "Scarf_StripeBlueWhite", 10,
                "Scarf_StripeRedWhite", 10,
                "Scarf_White", 10,
                "Trousers_Padded", 7,
            }
        }
    },
    
    ClothingStoresBoots = {
        rolls = 6,
        items = {
            "Shoes_BlackBoots", 10,
            "Shoes_BlackBoots", 10,
            "Shoes_ArmyBoots", 7,
            "Shoes_ArmyBoots", 7,
            "Shoes_ArmyBootsDesert", 7,
            "Shoes_ArmyBootsDesert", 7,
            "Shoes_RidingBoots", 5,
            "Shoes_RidingBoots", 5,
            "Shoes_Wellies", 5,
            "Shoes_Wellies", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Shoes_BlackBoots", 10,
                "Shoes_ArmyBoots", 7,
                "Shoes_ArmyBootsDesert", 7,
                "Shoes_RidingBoots", 5,
                "Shoes_Wellies", 5,
            }
        }
    },
    
    ClothingStoresDress = {
        rolls = 6,
        items = {
            "Skirt_Knees", 10,
            "Skirt_Knees", 10,
            "Skirt_Normal", 10,
            "Skirt_Normal", 10,
            "Skirt_Long", 7,
            "Skirt_Long", 7,
            "Dress_Knees", 10,
            "Dress_Knees", 10,
            "Dress_Short", 10,
            "Dress_Short", 10,
            "Dress_Normal", 7,
            "Dress_Normal", 7,
            "Dress_Long", 5,
            "Dress_Long", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Skirt_Knees", 10,
                "Skirt_Normal", 10,
                "Skirt_Long", 7,
                "Dress_Knees", 10,
                "Dress_Short", 10,
                "Dress_Normal", 7,
                "Dress_Long", 5,
            }
        }
    },
    
    ClothingStoresEyewear = {
        rolls = 6,
        items = {
            "Glasses_Sun", 20,
            "Glasses_Sun", 20,
            "Glasses_Aviators", 10,
            "Glasses_Aviators", 10,
            "Glasses_SwimmingGoggles", 7,
            "Glasses_SkiGoggles", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Glasses_Sun", 20,
                "Glasses_Aviators", 10,
                "Glasses_SwimmingGoggles", 7,
                "Glasses_SkiGoggles", 7,
            }
        }
    },
    
    ClothingStoresGloves = {
        rolls = 6,
        items = {
            "Gloves_WhiteTINT", 10,
            "Gloves_WhiteTINT", 10,
            "Gloves_WhiteTINT", 10,
            "Gloves_WhiteTINT", 10,
            "Gloves_FingerlessGloves", 10,
            "Gloves_FingerlessGloves", 10,
            "Gloves_LeatherGloves", 7,
            "Gloves_LeatherGloves", 7,
            "Gloves_LeatherGlovesBlack", 5,
            "Gloves_LongWomenGloves", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Gloves_WhiteTINT", 10,
                "Gloves_FingerlessGloves", 10,
                "Gloves_LeatherGloves", 7,
                "Gloves_LeatherGlovesBlack", 5,
                "Gloves_LongWomenGloves", 5,
            }
        }
    },
    
    ClothingStoresHats = {
        rolls = 6,
        items = {
            "Hat_Beany", 10,
            "Hat_Beany", 10,
            "Hat_Beany", 10,
            "Hat_Beany", 10,
            "Hat_Beret", 7,
            "Hat_Beret", 7,
            "Hat_BucketHat", 7,
            "Hat_BucketHat", 7,
            "Hat_SummerHat", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_Beany", 10,
                "Hat_Beret", 7,
                "Hat_BucketHat", 7,
                "Hat_SummerHat", 5,
            }
        }
    },
    
    ClothingStoresHatsFormal = {
        rolls = 6,
        items = {
            "Hat_Fedora", 10,
            "Hat_Fedora", 10,
            "Hat_Fedora", 10,
            "Hat_Fedora", 10,
            "Hat_Fedora_Delmonte", 7,
            "Hat_Fedora_Delmonte", 7,
            "Hat_Cowboy", 5,
            "Hat_Cowboy", 5,
            "Hat_SummerHat", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_Fedora", 10,
                "Hat_Fedora_Delmonte", 7,
                "Hat_Cowboy", 5,
                "Hat_SummerHat", 5,
            }
        }
    },
    
    ClothingStoresHatsSport = {
        rolls = 6,
        items = {
            "Hat_Sweatband", 10,
            "Hat_BaseballCap", 10,
            "Hat_BaseballCapBlue", 10,
            "Hat_BaseballCapRed", 10,
            "Hat_BaseballCapGreen", 10,
            "Hat_BaseballCapKY", 10,
            "Hat_BaseballCapKY_Red", 10,
            "Hat_VisorBlack", 7,
            "Hat_VisorRed", 7,
            "Hat_Visor_WhiteTINT", 7,
            "Hat_GolfHat", 5,
            "Hat_GolfHatTINT", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_Sweatband", 10,
                "Hat_BaseballCap", 10,
                "Hat_BaseballCapBlue", 10,
                "Hat_BaseballCapRed", 10,
                "Hat_BaseballCapGreen", 10,
                "Hat_BaseballCapKY", 10,
                "Hat_BaseballCapKY_Red", 10,
                "Hat_VisorBlack", 7,
                "Hat_VisorRed", 7,
                "Hat_Visor_WhiteTINT", 7,
                "Hat_GolfHat", 5,
                "Hat_GolfHatTINT", 5,
            }
        }
    },
    
    ClothingStoresJackets = {
        rolls = 6,
        items = {
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
            "Jacket_WhiteTINT", 10,
            "JacketLong_Random", 7,
            "JacketLong_Random", 7,
            "Jacket_Black", 5,
            "Jacket_Varsity", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Jacket_WhiteTINT", 10,
                "JacketLong_Random", 7,
                "Jacket_Black", 5,
                "Jacket_Varsity", 5,
            }
        }
    },
    
    ClothingStoresJacketsFormal = {
        rolls = 6,
        items = {
            "Suit_Jacket", 10,
            "Suit_Jacket", 10,
            "Suit_Jacket", 10,
            "Suit_Jacket", 10,
            "Suit_Jacket", 10,
            "Suit_Jacket", 10,
            "Suit_JacketTINT", 7,
            "Suit_JacketTINT", 7,
            "Suit_JacketTINT", 7,
            "Suit_JacketTINT", 7,
            "WeddingJacket", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Suit_Jacket", 10,
                "Suit_JacketTINT", 7,
            }
        }
    },
    
    ClothingStoresJeans = {
        rolls = 6,
        items = {
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "TrousersMesh_DenimLight", 10,
                "Trousers_Denim", 10,
                "Trousers_JeanBaggy", 10,
            }
        }
    },
    
    ClothingStoresJumpers = {
        rolls = 6,
        items = {
            "HoodieDOWN_WhiteTINT", 7,
            "HoodieDOWN_WhiteTINT", 7,
            "Jumper_RoundNeck", 10,
            "Jumper_RoundNeck", 10,
            "Jumper_RoundNeck", 10,
            "Jumper_RoundNeck", 10,
            "Jumper_VNeck", 7,
            "Jumper_VNeck", 7,
            "Jumper_PoloNeck", 7,
            "Jumper_PoloNeck", 7,
            "Jumper_DiamondPatternTINT", 5,
            "Jumper_DiamondPatternTINT", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "HoodieDOWN_WhiteTINT", 7,
                "Jumper_RoundNeck", 10,
                "Jumper_VNeck", 7,
                "Jumper_PoloNeck", 7,
                "Jumper_DiamondPatternTINT", 5,
            }
        }
    },
    
    ClothingStoresLeather = {
        rolls = 6,
        items = {
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "JacketLong_Random", 10,
            "Jacket_Black", 7,
            "Jacket_Black", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "JacketLong_Random", 10,
                "JacketLong_Random", 10,
                "Jacket_Black", 7,
            }
        }
    },
    
    ClothingStoresMan = {
        rolls = 6,
        items = {
            "Tshirt_DefaultTEXTURE_TINT", 5,
            "Jumper_RoundNeck", 2,
            "Jumper_PoloNeck", 2,
            "Jumper_DiamondPatternTINT", 2,
            "TrousersMesh_DenimLight", 2,
            "Trousers_DefaultTEXTURE_TINT", 3,
        }
    },
    
    ClothingStoresOvershirts = {
        rolls = 6,
        items = {
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Shirt_Lumberjack", 10,
            "Shirt_Denim", 7,
            "Shirt_Denim", 7,
            "Shirt_Denim", 7,
            "Shirt_Denim", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Shirt_Lumberjack", 10,
                "Shirt_Denim", 7,
            }
        }
    },
    
    ClothingStoresPants = {
        rolls = 6,
        items = {
            "Trousers_DefaultTEXTURE_TINT", 10,
            "Trousers_DefaultTEXTURE_TINT", 10,
            "Trousers_WhiteTINT", 10,
            "Trousers_WhiteTINT", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "TrousersMesh_DenimLight", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_Denim", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
            "Trousers_JeanBaggy", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Trousers_DefaultTEXTURE_TINT", 10,
                "Trousers_WhiteTINT", 10,
                "TrousersMesh_DenimLight", 10,
                "Trousers_Denim", 10,
                "Trousers_JeanBaggy", 10,
            }
        }
    },
    
    ClothingStoresPantsFormal = {
        rolls = 6,
        items = {
            "Trousers_SuitTEXTURE", 5,
            "Trousers_SuitTEXTURE", 5,
            "Trousers_SuitTEXTURE", 5,
            "Trousers_SuitTEXTURE", 5,
            "Trousers_SuitWhite", 7,
            "Trousers_SuitWhite", 7,
            "Trousers_SuitWhite", 7,
            "Trousers_SuitWhite", 7,
            "Trousers_Suit", 10,
            "Trousers_Suit", 10,
            "Trousers_Suit", 10,
            "Trousers_Suit", 10,
            "Trousers_Suit", 10,
            "Trousers_Suit", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Trousers_SuitTEXTURE", 5,
                "Trousers_SuitWhite", 7,
                "Trousers_Suit", 10,
            }
        }
    },
    
    ClothingStoresShirts = {
        rolls = 6,
        items = {
            "Tshirt_WhiteTINT", 10,
            "Tshirt_WhiteTINT", 10,
            "Tshirt_WhiteLongSleeveTINT", 10,
            "Tshirt_WhiteLongSleeveTINT", 10,
            "Tshirt_DefaultTEXTURE_TINT", 7,
            "Tshirt_DefaultTEXTURE_TINT", 7,
            "Tshirt_DefaultDECAL_TINT", 7,
            "Tshirt_DefaultDECAL_TINT", 7,
            "Tshirt_PoloTINT", 5,
            "Tshirt_PoloTINT", 5,
            "Tshirt_PoloStripedTINT", 5,
            "Tshirt_PoloStripedTINT", 5,
            "Tshirt_IndieStoneDECAL", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Tshirt_WhiteTINT", 10,
                "Tshirt_WhiteLongSleeveTINT", 10,
                "Tshirt_DefaultTEXTURE_TINT", 7,
                "Tshirt_DefaultDECAL_TINT", 7,
                "Tshirt_PoloTINT", 5,
                "Tshirt_PoloStripedTINT", 5,
            }
        }
    },
    
    ClothingStoresShirtsFormal = {
        rolls = 6,
        items = {
            "Shirt_FormalWhite", 10,
            "Shirt_FormalWhite", 10,
            "Shirt_FormalWhite", 10,
            "Shirt_FormalWhite", 10,
            "Shirt_FormalWhite_ShortSleeve", 7,
            "Shirt_FormalWhite_ShortSleeve", 7,
            "Shirt_FormalWhite_ShortSleeve", 7,
            "Shirt_FormalWhite_ShortSleeve", 7,
            "Shirt_FormalWhite_ShortSleeveTINT", 5,
            "Shirt_FormalWhite_ShortSleeveTINT", 5,
            "Shirt_FormalTINT", 5,
            "Shirt_FormalTINT", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Shirt_FormalWhite", 10,
                "Shirt_FormalWhite_ShortSleeve", 7,
                "Shirt_FormalWhite_ShortSleeveTINT", 5,
                "Shirt_FormalTINT", 5,
            }
        }
    },
    
    ClothingStoresShoes = {
        rolls = 6,
        items = {
            "Shoes_TrainerTINT", 10,
            "Shoes_TrainerTINT", 10,
            "Shoes_TrainerTINT", 10,
            "Shoes_TrainerTINT", 10,
            "Shoes_Black", 7,
            "Shoes_Black", 7,
            "Shoes_Black", 7,
            "Shoes_Black", 7,
            "Shoes_Brown", 7,
            "Shoes_Brown", 7,
            "Shoes_Brown", 7,
            "Shoes_Brown", 7,
            "Shoes_BlueTrainers", 5,
            "Shoes_RedTrainers", 5,
            "Shoes_FlipFlop", 5,
            "Shoes_FlipFlop", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Shoes_TrainerTINT", 10,
                "Shoes_Black", 7,
                "Shoes_Brown", 7,
                "Shoes_BlueTrainers", 5,
                "Shoes_RedTrainers", 5,
                "Shoes_FlipFlop", 5,
            }
        }
    },
    
    ClothingStoresSocks = {
        rolls = 6,
        items = {
            "Socks_Ankle", 10,
            "Socks_Ankle", 10,
            "Socks_Ankle", 10,
            "Socks_Ankle", 10,
            "Socks_Ankle", 10,
            "Socks_Ankle", 10,
            "Socks_Long", 7,
            "Socks_Long", 7,
            "Socks_Long", 7,
            "Socks_Long", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Socks_Ankle", 10,
                "Socks_Long", 7,
            }
        }
    },
    
    ClothingStoresSport = {
        rolls = 6,
        items = {
            "Vest_DefaultTEXTURE_TINT", 10,
            "Vest_DefaultTEXTURE_TINT", 10,
            "Tshirt_Sport", 10,
            "Tshirt_Sport", 10,
            "Shorts_ShortSport", 10,
            "Shorts_ShortSport", 10,
            "Shorts_LongSport", 7,
            "Shorts_LongSport", 7,
            "Tshirt_SportDECAL", 7,
            "Tshirt_SportDECAL", 7,
            "Shirt_Baseball_Z", 5,
            "Shirt_Baseball_KY", 5,
            "Shirt_Baseball_KY", 5,
            "Shirt_Baseball_Rangers", 5,
            "Shirt_Baseball_Rangers", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Vest_DefaultTEXTURE_TINT", 10,
                "Tshirt_Sport", 10,
                "Shorts_ShortSport", 10,
                "Tshirt_SportDECAL", 7,
                "Shirt_Baseball_KY", 5,
                "Shirt_Baseball_Rangers", 5,
                "Shirt_Baseball_Z", 5,
                "Shorts_LongSport", 5,
            }
        }
    },
    
    ClothingStoresSummer = {
        rolls = 6,
        items = {
            "Shirt_HawaiianTINT", 10,
            "Shirt_HawaiianTINT", 10,
            "Shirt_HawaiianTINT", 10,
            "Shirt_HawaiianTINT", 10,
            "SwimTrunks_Blue", 7,
            "SwimTrunks_Green", 7,
            "SwimTrunks_Red", 7,
            "SwimTrunks_Yellow", 7,
            "Bikini_TINT", 5,
            "Bikini_Pattern01", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Shirt_HawaiianTINT", 10,
                "SwimTrunks_Blue", 7,
                "SwimTrunks_Green", 7,
                "SwimTrunks_Red", 7,
                "SwimTrunks_Yellow", 7,
                "Bikini_TINT", 5,
                "Bikini_Pattern01", 5,
            }
        }
    },
    
    ClothingStoresUnderwear = {
        rolls = 6,
        items = {
            "Socks_Ankle", 7,
            "Socks_Ankle", 7,
            "Socks_Ankle", 7,
            "Socks_Ankle", 7,
            "Socks_Long", 5,
            "Socks_Long", 5,
            "Socks_Long", 5,
            "Socks_Long", 5,
            "LongJohns_Bottoms", 10,
            "LongJohns_Bottoms", 10,
            "LongJohns", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Socks_Ankle", 7,
                "Socks_Long", 5,
                "LongJohns_Bottoms", 10,
                "LongJohns", 7,
            }
        }
    },
    
    ClothingStoresWoman = {
        rolls = 6,
        items = {
            "Dress_Knees", 2,
            "Dress_Long", 2,
            "Dress_Normal", 2,
            "Dress_Short", 2,
            "Purse", 2,
            "Handbag", 2,
            "Shoes_Random", 1.5,
            "Skirt_Knees", 3,
            "Skirt_Long", 3,
            "Skirt_Normal", 3,
            "Tshirt_DefaultTEXTURE_TINT", 5,
            "Jumper_RoundNeck", 2,
            "Jumper_PoloNeck", 2,
            "Jumper_DiamondPatternTINT", 2,
            "Hat_SummerHat", 1,
        }
    },
    
    CrateAntiqueStove = {
        rolls = 1,
        items = {
            "Mov_AntiqueStove", 100000,
        }
    },
    
    CrateBlueComfyChair = {
        rolls = 1,
        items = {
            "Mov_BlueComfyChair", 10000,
        }
    },
    
    CrateBluePlasticChairs = {
        rolls = 5,
        items = {
            "Mov_BluePlasticChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_BluePlasticChair", 10000,
            }
        }
    },
    
    CrateBlueRattanChair = {
        rolls = 1,
        items = {
            "Mov_BlueRattanChair", 10000,
        }
    },
    
    CrateBrownComfyChair = {
        rolls = 1,
        items = {
            "Mov_BrownComfyChair", 10000,
        }
    },
    
    CrateBrownLowTables = {
        rolls = 3,
        items = {
            "Mov_BrownLowTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_BrownLowTable", 10000,
            }
        }
    },
    
    CrateChromeSinks = {
        rolls = 3,
        items = {
            "Mov_ChromeSink", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_ChromeSink", 10000,
            }
        }
    },
    
    CrateConcrete = {
        rolls = 4,
        items = {
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
            "ConcretePowder", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "ConcretePowder", 100,
                "BucketEmpty", 25,
            }
        }
    },
    
    CrateDarkBlueChairs = {
        rolls = 3,
        items = {
            "Mov_DarkBlueChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_DarkBlueChair", 10000,
            }
        }
    },
    
    CrateDarkWoodenChairs = {
        rolls = 5,
        items = {
            "Mov_DarkWoodenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_DarkWoodenChair", 10000,
            }
        }
    },
    
    CrateFancyBlackChairs = {
        rolls = 5,
        items = {
            "Mov_FancyBlackChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FancyBlackChair", 10000,
            }
        }
    },
    
    CrateFancyDarkTables = {
        rolls = 3,
        items = {
            "Mov_FancyDarkTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FancyDarkTable", 10000,
            }
        }
    },
    
    CrateFancyLowTables = {
        rolls = 3,
        items = {
            "Mov_FancyLowTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FancyLowTable", 10000,
            }
        }
    },
    
    CrateFancyToilets = {
        rolls = 3,
        items = {
            "Mov_FancyToilet", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FancyToilet", 10000,
            }
        }
    },
    
    CrateFancyWhiteChairs = {
        rolls = 5,
        items = {
            "Mov_FancyWhiteChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FancyWhiteChair", 10000,
            }
        }
    },
    
    CrateFarming = {
        rolls = 2,
        items = {
            "farming.HandShovel", 2.7,
            "farming.Shovel", 1,
            "farming.WateredCan", 1,
            "BarbedWire", 1,
            "Sandbag", 0.5,
            "Gravelbag", 0.5,
            "EmptySandbag", 2.5,
            "Fertilizer", 0.5,
            "Charcoal", 6,
        },
        junk = {
            rolls = 2,
            items = {
                "farming.CarrotBagSeed", 1.5,
                "farming.BroccoliBagSeed", 1.5,
                "farming.RedRadishBagSeed", 1.5,
                "farming.StrewberrieBagSeed", 1.5,
                "farming.TomatoBagSeed", 1.5,
                "farming.PotatoBagSeed", 1.5,
                "farming.CabbageBagSeed", 1.5,
                "Base.SeedBag", 0.7,
            }
        }
    },
    
    CrateFertilizer = {
        rolls = 6,
        items = {
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
            "Fertilizer", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Fertilizer", 100,
            }
        }
    },
    
    CrateFoldingChairs = {
        rolls = 5,
        items = {
            "Mov_FoldingChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_FoldingChair", 10000,
            }
        }
    },
    
    CrateGravelBags = {
        rolls = 6,
        items = {
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
            "Gravelbag", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Gravelbag", 100,
            }
        }
    },
    
    CrateGreenChairs = {
        rolls = 5,
        items = {
            "Mov_GreenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_GreenChair", 10000,
            }
        }
    },
    
    CrateGreenComfyChair = {
        rolls = 1,
        items = {
            "Mov_GreenComfyChair", 10000,
        }
    },
    
    CrateGreenOven = {
        rolls = 1,
        items = {
            "Mov_GreenOven", 10000,
        }
    },
    
    CrateGreyChairs = {
        rolls = 5,
        items = {
            "Mov_GreyChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_GreyChair", 10000,
            }
        }
    },
    
    CrateGreyComfyChair = {
        rolls = 1,
        items = {
            "Mov_GreyComfyChair", 10000,
        }
    },
    
    CrateGreyOven = {
        rolls = 1,
        items = {
            "Mov_GreyOven", 10000,
        }
    },
    
    CrateHardware = {
        rolls = 3,
        items = {
            "DuctTape", 0.8,
            "Glue", 0.8,
            "Scotchtape", 0.8,
            "Twine", 0.8,
            "Thread", 2,
            "Needle", 0.8,
            "Woodglue", 0.8,
            "Rope", 0.8,
            "Wire", 2,
            "Glasses_SafetyGoggles", 0.3,
        },
        junk = {
            rolls = 2,
            items = {
                "PaperclipBox", 0.5,
                "camping.TentPeg", 7,
                "Tarp", 1,
                "Battery", 4,
                "Lighter", 4,
            }
        }
    },
    
    CrateIndustrialSinks = {
        rolls = 3,
        items = {
            "Mov_IndustrialSink", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_IndustrialSink", 10000,
            }
        }
    },
    
    CrateLightRoundTable = {
        rolls = 1,
        items = {
            "Mov_LightRoundTable", 10000,
        }
    },
    
    CrateLongTables = {
        rolls = 3,
        items = {
            "Mov_LongTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_LongTable", 10000,
            }
        }
    },
    
    CrateMetal = {
        rolls = 2,
        items = {
            "BlowTorch", 0.5,
            "WeldingRods", 1,
            "SheetMetal", 1.2,
            "SmallSheetMetal", 1.8,
            "MetalPipe", 1.4,
            "LeadPipe", 1.2,
            "MetalBar", 0.7,
            "WeldingMask",0.5,
            "Glasses_SafetyGoggles", 0.3,
        }
    },
    
    CrateMetalLockers = {
        rolls = 3,
        items = {
            "Mov_MetalLocker", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_MetalLocker", 10000,
            }
        }
    },
    
    CrateModernOven = {
        rolls = 1,
        items = {
            "Mov_ModernOven", 10000,
        }
    },
    
    CrateOakRoundTable = {
        rolls = 1,
        items = {
            "Mov_OakRoundTable", 10000,
        }
    },
    
    CrateOfficeChairs = {
        rolls = 5,
        items = {
            "Mov_OfficeChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_OfficeChair", 10000,
            }
        }
    },
    
    CrateOrangeModernChair = {
        rolls = 1,
        items = {
            "Mov_OrangeModernChair", 10000,
            "Mov_OrangeFuton", 10000,
        }
    },
    
    CratePaint = {
        rolls = 4,
        items = {
            "PaintBlue", 5,
            "PaintBlack", 5,
            "PaintRed", 5,
            "PaintBrown", 5,
            "PaintCyan", 5,
            "PaintGreen", 5,
            "PaintGrey", 5,
            "PaintLightBlue", 5,
            "PaintLightBrown", 5,
            "PaintOrange", 5,
            "PaintPink", 5,
            "PaintPurple", 5,
            "PaintTurquoise", 5,
            "PaintWhite", 5,
            "PaintYellow", 5,
            "Paintbrush", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Paintbrush", 75,
                "BucketEmpty", 25,
            }
        }
    },
    
    CratePlasticChairs = {
        rolls = 5,
        items = {
            "Mov_PlasticChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_PlasticChair", 10000,
            }
        }
    },
    
    CratePlasticLowTables = {
        rolls = 3,
        items = {
            "Mov_PlasticLowTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_PlasticLowTable", 10000,
            }
        }
    },
    
    CratePlaster = {
        rolls = 4,
        items = {
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
            "PlasterPowder", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "PlasterPowder", 100,
                "BucketEmpty", 25,
            }
        }
    },
    
    CratePurpleRattanChair = {
        rolls = 1,
        items = {
            "Mov_PurpleRattanChair", 10000,
        }
    },
    
    CratePurpleWoodenChairs = {
        rolls = 5,
        items = {
            "Mov_PurpleWoodenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_PurpleWoodenChair", 10000,
            }
        }
    },
    
    CrateRedBBQs = {
        rolls = 3,
        items = {
            "Mov_RedBBQ", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_RedBBQ", 10000,
            }
        }
    },
    
    CrateRedChairs = {
        rolls = 5,
        items = {
            "Mov_RedChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_RedChair", 10000,
            }
        }
    },
    
    CrateRedOven = {
        rolls = 1,
        items = {
            "Mov_RedOven", 10000,
        }
    },
    
    CrateRedWoodenChairs = {
        rolls = 5,
        items = {
            "Mov_RedWoodenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_RedWoodenChair", 10000,
            }
        }
    },
    
    CrateRoundTable = {
        rolls = 1,
        items = {
            "Mov_RoundTable", 10000,
        }
    },
    
    CrateSandBags = {
        rolls = 6,
        items = {
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
            "Sandbag", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Sandbag", 100,
            }
        }
    },
    
    CrateSmallTables = {
        rolls = 3,
        items = {
            "Mov_SmallTable", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_SmallTable", 10000,
            }
        }
    },
    
    CrateTools = {
        rolls = 3,
        items = {
            "Crowbar", 0.8,
            "Sledgehammer", 0.4,
            "Sledgehammer2", 0.4,
            "Wrench", 1,
            "LugWrench",1,
            "Jack", 1,
            "TirePump", 1,
            "LeadPipe", 1,
            "HandAxe", 1,
            "PipeWrench", 1,
            "Plunger", 2,
            "ClubHammer", 1,
            "WoodenMallet", 1.5,
            "Torch", 1,
            "HandTorch", 1,
            "Glasses_SafetyGoggles", 0.5,
            "Shovel", 0.3,
            "Shovel2", 0.3,
            "SnowShovel", 0.3,
            "farming.HandShovel", 1,
            "HandScythe", 1,
            "HandFork", 1,
            "LeafRake", 2,
            "Rake", 2,
            "Broom", 2,
            "GardenFork", 1,
        },
        junk = {
            rolls = 2,
            items = {
                "Radio.WalkieTalkie1",0.05,
                "Radio.WalkieTalkie2",0.03,
                "Radio.WalkieTalkie3",0.001,
                "Radio.HamRadio1",0.005,
            }
        }
    },
    
    CrateTV = {
        rolls = 1,
        items = {
            "Radio.TvBlack", 100000,
        }
    },
    
    CrateTVWide = {
        rolls = 1,
        items = {
            "Radio.TvWideScreen", 100000,
        }
    },
    
    CrateWhiteComfyChair = {
        rolls = 1,
        items = {
            "Mov_WhiteComfyChair", 10000,
        }
    },
    
    CrateWhiteSimpleChairs = {
        rolls = 5,
        items = {
            "Mov_WhiteSimpleChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_WhiteSimpleChair", 10000,
            }
        }
    },
    
    CrateWhiteSinks = {
        rolls = 3,
        items = {
            "Mov_WhiteSink", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_WhiteSink", 10000,
            }
        }
    },
    
    CrateWhiteWoodenChairs = {
        rolls = 5,
        items = {
            "Mov_WhiteWoodenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_WhiteWoodenChair", 10000,
            }
        }
    },
    
    CrateWoodenChairs = {
        rolls = 5,
        items = {
            "Mov_WoodenChair", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_WoodenChair", 10000,
            }
        }
    },
    
    CrateWoodenStools = {
        rolls = 5,
        items = {
            "Mov_WoodenStool", 75,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_WoodenStool", 10000,
            }
        }
    },
    
    CrateWoods = {
        rolls = 4,
        items = {
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
            "Plank", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "NailsBox", 10,
                "NailsBox", 10,
                "Hammer", 7,
                "Saw", 5,
                "Axe", 0.5,
                "Glasses_SafetyGoggles", 0.3,
                "WoodAxe", 0.5,
            }
        }
    },
    
    CrateYellowModernChair = {
        rolls = 1,
        items = {
            "Mov_YellowModernChair", 10000,
        }
    },
    
    CrepeKitchenFridge = {
        rolls = 6,
        items = {
            "Milk", 10,
            "Milk", 10,
            "Milk", 10,
            "Milk", 10,
            "EggCarton", 7,
            "EggCarton", 7,
            "Butter", 5,
            "Butter", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Milk", 10,
                "EggCarton", 7,
                "Butter", 5,
            }
        }
    },
    
    DepartmentStoreJewelry = {
        rolls = 6,
        items = {
            "Bracelet_BangleRightGold", 3,
            "Bracelet_BangleRightSilver", 5,
            "Bracelet_ChainRightGold", 3,
            "Bracelet_ChainRightSilver", 5,
            "Bracelet_LeftFriendshipTINT", 10,
            "Earring_Dangly_Pearl", 7,
            "Earring_Pearl", 7,
            "Earring_Stud_Gold", 3,
            "Earring_Stud_Silver", 5,
            "NecklaceLong_Gold", 3,
            "NecklaceLong_Silver", 5,
            "Necklace_Crucifix", 10,
            "Necklace_Gold", 3,
            "Necklace_Pearl", 7,
            "Necklace_Silver", 5,
            "Necklace_SilverCrucifix", 7,
            "Necklace_YingYang", 10,
            "Ring_Left_RingFinger_Gold", 3,
            "Ring_Left_RingFinger_Silver", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Bracelet_LeftFriendshipTINT", 10,
                "Earring_Pearl", 7,
                "Necklace_Crucifix", 10,
                "Necklace_Pearl", 7,
                "Necklace_YingYang", 10,
            }
        }
    },
    
    DepartmentStoreWatches = {
        rolls = 6,
        items = {
            "WristWatch_Right_ClassicBlack", 7,
            "WristWatch_Right_ClassicBlack", 7,
            "WristWatch_Right_ClassicBrown", 7,
            "WristWatch_Right_ClassicBrown", 7,
            "WristWatch_Right_ClassicGold", 3,
            "WristWatch_Right_ClassicGold", 3,
            "WristWatch_Right_DigitalBlack", 10,
            "WristWatch_Right_DigitalBlack", 10,
            "WristWatch_Right_DigitalDress", 5,
            "WristWatch_Right_DigitalRed", 10,
            "WristWatch_Right_DigitalRed", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "WristWatch_Right_ClassicBlack", 7,
                "WristWatch_Right_ClassicBrown", 7,
                "WristWatch_Right_DigitalBlack", 10,
                "WristWatch_Right_DigitalRed", 10,
            }
        }
    },
    
    DinerKitchenFreezer = {
        rolls = 6,
        items = {
            "Chicken", 10,
            "Chicken", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "PorkChop", 10,
            "PorkChop", 10,
            "Peas", 7,
            "Peas", 7,
            "farming.Bacon", 7,
            "farming.Bacon", 7,
            "Ham", 7,
            "Ham", 7,
            "Steak", 5,
            "Steak", 5,
            "Salmon", 3,
            "Salmon", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Chicken", 10,
                "MeatPatty", 10,
                "PorkChop", 10,
                "Peas", 7,
                "farming.Bacon", 7,
                "Ham", 7,
                "Steak", 5,
                "Salmon", 3,
            }
        }
    },
    
    DinerKitchenFridge = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Milk", 7,
            "EggCarton", 7,
            "Processedcheese", 7,
            "Broccoli", 7,
            "Cheese", 7,
            "Corn", 7,
            "Eggplant", 7,
            "Leek", 7,
            "Lettuce", 7,
            "Onion", 7,
            "farming.Tomato", 7,
            "farming.Bacon", 7,
            "Ham", 7,
            "PorkChop", 7,
            "Butter", 5,
            "Steak", 5,
            "Pickles", 5,
            "Salmon", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
                "Chicken", 10,
                "Milk", 7,
                "EggCarton", 7,
                "farming.Bacon", 7,
                "Ham", 7,
                "PorkChop", 7,
                "Butter", 5,
                "Steak", 5,
            }
        }
    },
    
    ElectronicStoreAppliances = {
        rolls = 1,
        items = {
            "Mov_Microwave2", 25,
            "Mov_Microwave", 25,
            "Mov_Toaster", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_Microwave2", 100,
                "Mov_Microwave", 100,
                "Mov_Toaster", 100,
            }
        }
    },
    
    ElectronicStoreComputers = {
        rolls = 1,
        items = {
            "Mov_DesktopComputer", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Mov_DesktopComputer", 100,
            }
        }
    },
    
    ElectronicStoreHAMRadio = {
        rolls = 1,
        items = {
            "Radio.HamRadio1", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Radio.HamRadio1", 100,
            }
        }
    },
    
    ElectronicStoreLights = {
        rolls = 6,
        items = {
            "LightBulb", 7,
            "LightBulb", 7,
            "LightBulb", 7,
            "LightBulb", 7,
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
        junk = {
            rolls = 1,
            items = {
                "Mov_Lamp1", 100,
                "Mov_Lamp2", 100,
                "Mov_Lamp3", 100,
                "Mov_Lamp4", 100,
                "Mov_Lamp5", 100,
                "Mov_Lamp6", 100,
            }
        }
    },
    
    ElectronicStoreMagazines = {
        rolls = 6,
        items = {
            "Magazine", 20,
            "ElectronicsMag1", 7,
            "ElectronicsMag2", 7,
            "ElectronicsMag3", 7,
            "ElectronicsMag4", 7,
            "ElectronicsMag5", 7,
            "EngineerMagazine1", 7,
            "EngineerMagazine2", 7,
            "Radio.RadioMag1", 7,
            "Radio.RadioMag2", 7,
            "Radio.RadioMag3", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "ElectronicsMag1", 7,
                "ElectronicsMag2", 7,
                "ElectronicsMag3", 7,
                "ElectronicsMag4", 7,
                "ElectronicsMag5", 7,
                "EngineerMagazine1", 7,
                "EngineerMagazine2", 7,
                "Radio.RadioMag1", 7,
                "Radio.RadioMag2", 7,
                "Radio.RadioMag3", 7,
            }
        }
    },
    
    ElectronicStoreMisc = {
        rolls = 6,
        items = {
            "Battery", 10,
            "Battery", 10,
            "Battery", 10,
            "Battery", 10,
            "CordlessPhone", 7,
            "HandTorch", 10,
            "Radio.RadioBlack", 5,
            "Radio.RadioRed", 3,
            "Radio.WalkieTalkie1", 5,
            "Radio.WalkieTalkie2", 3,
            "Radio.WalkieTalkie3", 1,
            "Remote", 10,
            "Torch", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Battery", 10,
                "CordlessPhone", 7,
                "HandTorch", 10,
                "Remote", 10,
                "Torch", 7,
            }
        }
    },
    
    ElectronicStoreMusic = {
        rolls = 6,
        items = {
            "Magazine", 20,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Headphones", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Headphones", 100,
                "Disc", 100,
                "Speaker", 100,
                "Speaker", 100,
            }
        }
    },
    
    FishingStoreGear = {
        rolls = 6,
        items = {
            "FishingTackle", 20,
            "FishingTackle", 20,
            "FishingTackle2", 20,
            "FishingTackle2", 20,
            "FishingLine", 20,
            "FishingLine", 20,
            "FishingLine", 20,
            "FishingLine", 20,
            "FishingRod", 10,
            "FishingRod", 10,
            "FishingNet", 10,
            "FishingNet", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "FishingTackle", 20,
                "FishingTackle2", 20,
                "FishingLine", 20,
                "FishingRod", 10,
                "FishingNet", 10,
            }
        }
    },
    
    FirearmWeapons = {
        rolls = 2,
        items = {
            "DoubleBarrelShotgun", 10,
            "Shotgun", 7,
            "ShotgunShellsBox", 10,
            "ShotgunShellsBox", 10,
            "Pistol", 7,
            "Pistol2", 5,
            "Pistol3", 3,
            "PistolCase1", 5,
            "PistolCase2", 3,
            "PistolCase3", 1,
            "Revolver_Short", 7,
            "Revolver", 5,
            "Revolver_Long", 3,
            "Bullets9mmBox", 10,
            "Bullets9mmBox", 10,
            "Bag_WeaponBag", 7,
            "HuntingRifle", 5,
            "VarmintRifle", 5,
            "Bullets45Box", 10,
            "Bullets44Box", 10,
            "Bullets38Box", 10,
            "Bullets45Box", 10,
            "Bullets44Box", 10,
            "Bullets38Box", 10,
            "223Box", 10,
            "223Box", 10,
            "308Box", 10,
            "308Box", 10,
        },
        junk = {
            rolls = 3,
            items = {
                "ShotgunShellsBox", 10,
                "ShotgunShellsBox", 10,
                "Bullets9mmBox", 10,
                "Bullets9mmBox", 10,
                "223Box", 10,
                "223Box", 10,
                "308Box", 10,
                "308Box", 10,
            }
        }
    },
    
    FridgeBeer = {
        rolls = 6,
        items = {
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "BeerCan", 20,
                "BeerCan", 20,
                "BeerBottle", 20,
                "BeerBottle", 20,
            }
        }
    },
    
    FridgeBottles = {
        rolls = 6,
        items = {
            "WaterBottleFull", 3,
            "WaterBottleFull", 3,
            "WaterBottleFull", 3,
            "WaterBottleFull", 3,
            "PopBottle", 3,
            "PopBottle", 3,
            "Pop", 4,
            "Pop", 4,
            "Pop2", 4,
            "Pop2", 4,
            "Pop3", 4,
            "Pop3", 4,
            "JuiceBox", 4,
            "JuiceBox", 4,
        },
        junk = {
            rolls = 1,
            items = {
                "WaterBottleFull", 3,
                "PopBottle", 3,
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "JuiceBox", 4,
            }
        }
    },
    
    FridgeBreakRoom = {
        rolls = 6,
        items = {
            "Apple", 7,
            "Banana", 7,
            "Orange", 7,
            "Hotdog", 5,
            "BurgerRecipe", 5,
            "Pie", 5,
            "CakeSlice", 5,
            "PopBottle", 5,
            "WaterBottleFull", 5,
            "Pop", 7,
            "Pop2", 7,
            "Pop3", 7,
            "Lunchbox", 10,
            "Lunchbox2", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Apple", 7,
                "Banana", 7,
                "Orange", 7,
                "Hotdog", 5,
                "BurgerRecipe", 5,
                "Pie", 5,
                "CakeSlice", 5,
                "PopBottle", 5,
                "WaterBottleFull", 5,
                "Pop", 7,
                "Pop2", 7,
                "Pop3", 7,
            }
        }
    },
    
    FridgeOther = {
        rolls = 6,
        items = {
            "Milk", 4,
            "Milk", 4,
            "EggCarton", 2,
            "EggCarton", 2,
            "Butter", 3,
            "Processedcheese", 4,
            "Processedcheese", 4,
            "Yoghurt", 4,
            "Yoghurt", 4,
            "JuiceBox", 4,
            "JuiceBox", 4,
            "Mustard", 3,
            "Ketchup", 3,
            "farming.MayonnaiseFull", 3,
            "farming.RemouladeFull", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "Milk", 4,
                "EggCarton", 2,
                "Butter", 3,
                "Processedcheese", 4,
                "Yoghurt", 4,
                "JuiceBox", 4,
            }
        }
    },
    
    FridgeSnacks = {
        rolls = 6,
        items = {
            "BurgerRecipe", 4,
            "BurgerRecipe", 4,
            "Pizza", 4,
            "Pizza", 4,
            "ChickenFried", 4,
            "ChickenFried", 4,
            "Corndog", 4,
            "Corndog", 4,
            "Hotdog", 4,
            "Hotdog", 4,
            "Pie", 4,
            "Pie", 4,
            "CakeSlice", 4,
            "CakeSlice", 4,
        },
        junk = {
            rolls = 1,
            items = {
                "BurgerRecipe", 4,
                "Pizza", 4,
                "ChickenFried", 4,
                "Corndog", 4,
                "Hotdog", 4,
                "Pie", 4,
                "CakeSlice", 4,
            }
        }
    },
    
    FridgeSoda = {
        rolls = 6,
        items = {
            "Pop", 4,
            "Pop", 4,
            "Pop2", 4,
            "Pop2", 4,
            "Pop3", 4,
            "Pop3", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "PopBottle", 4,
        },
        junk = {
            rolls = 1,
            items = {
                "Pop", 4,
                "Pop2", 4,
                "Pop3", 4,
                "PopBottle", 4,
            }
        }
    },
    
    FridgeWater = {
        rolls = 6,
        items = {
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
        },
        junk = {
            rolls = 1,
            items = {
                "WaterBottleFull", 4,
                "WaterBottleFull", 4,
                "WaterBottleFull", 4,
                "WaterBottleFull", 4,
            }
        }
    },
    
    GardenStoreMisc = {
        rolls = 6,
        items = {
            "TrapMouse", 5,
            "farming.CarrotBagSeed", 7,
            "farming.BroccoliBagSeed", 7,
            "farming.RedRadishBagSeed", 7,
            "farming.StrewberrieBagSeed", 7,
            "farming.TomatoBagSeed", 7,
            "farming.PotatoBagSeed", 7,
            "farming.CabbageBagSeed", 7,
            "farming.GardeningSprayEmpty", 10,
            "farming.WateredCan", 10,
            "farming.HandShovel", 10,
            "FarmingMag1", 10,
            "BookFarming1", 10,
            "BookFarming2", 7,
            "BookFarming3", 5,
            "BookFarming4", 3,
            "BookFarming5", 1,
            "BoxOfJars", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "farming.CarrotBagSeed", 7,
                "farming.BroccoliBagSeed", 7,
                "farming.RedRadishBagSeed", 7,
                "farming.StrewberrieBagSeed", 7,
                "farming.TomatoBagSeed", 7,
                "farming.PotatoBagSeed", 7,
                "farming.CabbageBagSeed", 7,
            }
        }
    },
    
    GardenStoreTools = {
        rolls = 6,
        items = {
            "Axe", 3,
            "BarbedWire", 10,
            "EmptySandbag", 10,
            "farming.HandShovel", 10,
            "farming.WateredCan", 10,
            "farming.GardeningSprayEmpty", 10,
            "GardenFork", 3,
            "GardenSaw", 10,
            "HandAxe", 7,
            "HandFork", 7,
            "HandScythe", 7,
            "LeafRake", 10,
            "Machete", 1,
            "Rake", 10,
            "Rope", 10,
            "Shovel", 5,
            "Shovel2", 5,
            "WoodAxe", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "farming.HandShovel", 10,
                "farming.WateredCan", 10,
                "HandAxe", 7,
                "HandFork", 7,
                "HandScythe", 7,
            }
        }
    },
    
    GigamartBakingMisc = {
        rolls = 6,
        items = {
            "Chocolate", 7,
            "Chocolate", 7,
            "Flour", 5,
            "Flour", 5,
            "Honey", 5,
            "Honey", 5,
            "OatsRaw", 7,
            "OatsRaw", 7,
            "PeanutButter", 10,
            "PeanutButter", 10,
            "Sugar", 10,
            "Sugar", 10,
            "Yeast", 5,
            "Yeast", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Chocolate", 7,
                "Flour", 5,
                "Honey", 5,
                "OatsRaw", 7,
                "PeanutButter", 10,
                "Sugar", 10,
                "Yeast", 5,
            }
        }
    },
    
    GigamartBedding = {
        rolls = 6,
        items = {
            "BathTowel", 7,
            "BathTowel", 7,
            "Button", 10,
            "DishCloth", 7,
            "DishCloth", 7,
            "KnittingNeedles", 10,
            "Needle", 10,
            "Pillow", 5,
            "Pillow", 5,
            "Sheet", 10,
            "Sheet", 10,
            "Sheet", 10,
            "Sheet", 10,
            "Thread", 10,
            "Thread", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "BathTowel", 7,
                "Button", 10,
                "KnittingNeedles", 10,
                "Needle", 10,
                "Pillow", 5,
                "Sheet", 10,
                "Thread", 10,
            }
        }
    },
    
    GigamartBottles = {
        rolls = 6,
        items = {
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "WaterBottleFull", 7,
            "PopBottle", 10,
            "PopBottle", 10,
            "PopBottle", 10,
            "PopBottle", 10,
            "PopBottle", 10,
            "PopBottle", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "WaterBottleFull", 7,
                "WaterBottleFull", 7,
                "PopBottle", 10,
                "PopBottle", 10,
            }
        }
    },
    
    GigamartCandy = {
        rolls = 6,
        items = {
            "CandyPackage", 7,
            "CandyPackage", 7,
            "CandyPackage", 7,
            "CandyPackage", 7,
            "Chocolate", 10,
            "Chocolate", 10,
            "CookieChocolateChip", 7,
            "CookieJelly", 7,
            "Cupcake", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "CandyPackage", 7,
                "Chocolate", 10,
                "CookieChocolateChip", 7,
                "CookieJelly", 7,
                "Cupcake", 7,
            }
        }
    },
    
    GigamartCannedFood = {
        rolls = 6,
        items = {
            "CannedBolognese", 10,
            "CannedCarrots2", 7,
            "CannedChili", 10,
            "CannedCorn", 7,
            "CannedCornedBeef", 10,
            "CannedMushroomSoup", 10,
            "CannedPeas", 7,
            "CannedPotato2", 7,
            "CannedSardines", 10,
            "CannedTomato2", 7,
            "TinnedBeans", 10,
            "TinnedSoup", 10,
            "TunaTin", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "CannedBolognese", 10,
                "CannedCarrots2", 7,
                "CannedChili", 10,
                "CannedCorn", 7,
                "CannedCornedBeef", 10,
                "CannedMushroomSoup", 10,
                "CannedPeas", 7,
                "CannedPotato2", 7,
                "CannedSardines", 10,
                "CannedTomato2", 7,
                "TinnedBeans", 10,
                "TinnedSoup", 10,
                "TunaTin", 10,
            }
        }
    },
    
    GigamartCrisps = {
        rolls = 6,
        items = {
            "Crisps", 10,
            "Crisps", 10,
            "Crisps2", 10,
            "Crisps2", 10,
            "Crisps3", 10,
            "Crisps3", 10,
            "Crisps4", 10,
            "Crisps4", 10,
            "Peanuts", 7,
            "Popcorn", 7,
            "SunflowerSeeds", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Crisps", 10,
                "Crisps2", 10,
                "Crisps3", 10,
                "Crisps4", 10,
                "Peanuts", 7,
                "Popcorn", 7,
                "SunflowerSeeds", 7,
            }
        }
    },
    
    GigamartDryGoods = {
        rolls = 6,
        items = {
            "Cereal", 10,
            "Cereal", 10,
            "Coffee2", 5,
            "Coffee2", 5,
            "Macandcheese", 10,
            "Macandcheese", 10,
            "Pasta", 7,
            "Pasta", 7,
            "Ramen", 10,
            "Ramen", 10,
            "Rice", 7,
            "Rice", 7,
            "Teabag2", 5,
            "Teabag2", 5,
            "TVDinner", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "Cereal", 10,
                "Coffee2", 5,
                "Macandcheese", 10,
                "Pasta", 7,
                "Ramen", 10,
                "Rice", 7,
                "Teabag2", 5,
                "TVDinner", 3,
            }
        }
    },
    
    GigamartFarming = {
        rolls = 6,
        items = {
            "BarbedWire", 7,
            "Broom", 10,
            "EmptySandbag", 5,
            "farming.HandShovel", 10,
            "GardenFork", 3,
            "GardenSaw", 10,
            "HandFork", 5,
            "HandScythe", 5,
            "LeafRake", 10,
            "Rake", 10,
            "Rope", 10,
            "Saw", 7,
            "Shovel", 7,
            "Shovel2", 7,
            "SnowShovel", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "BarbedWire", 7,
                "Broom", 10,
                "EmptySandbag", 5,
                "farming.HandShovel", 10,
                "GardenFork", 3,
                "GardenSaw", 10,
                "HandFork", 5,
                "HandScythe", 5,
                "LeafRake", 10,
                "Rake", 10,
                "Rope", 10,
                "Saw", 7,
                "Shovel", 7,
                "Shovel2", 7,
                "SnowShovel", 5,
            }
        }
    },
    
    GigamartHouseElectronics = {
        rolls = 6,
        items = {
            "Battery", 20,
            "Battery", 20,
            "CDplayer", 7,
            "CordlessPhone", 7,
            "HandTorch", 10,
            "Mov_Microwave", 3,
            "Mov_Microwave2", 3,
            "Mov_Toaster", 5,
            "Radio.ElectricWire", 5,
            "Radio.RadioBlack", 7,
            "Radio.RadioRed", 5,
            "Radio.WalkieTalkie2", 5,
            "Radio.WalkieTalkie3", 3,
            "Remote", 7,
            "Torch", 7,
            "VideoGame", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Battery", 20,
                "CDplayer", 7,
                "CordlessPhone", 7,
                "HandTorch", 10,
                "Mov_Microwave", 3,
                "Mov_Microwave2", 3,
                "Mov_Toaster", 5,
                "Radio.ElectricWire", 5,
                "Radio.RadioBlack", 7,
                "Radio.RadioRed", 5,
                "Radio.WalkieTalkie2", 5,
                "Radio.WalkieTalkie3", 3,
                "Remote", 7,
                "Torch", 7,
                "VideoGame", 7,
            }
        }
    },
    
    GigamartHousewares = {
        rolls = 6,
        items = {
            "ButterKnife", 7,
            "ButterKnife", 7,
            "BreadKnife", 7,
            "BreadKnife", 7,
            "Spoon", 10,
            "Spoon", 10,
            "Fork", 10,
            "Fork", 10,
            "Bowl", 10,
            "Bowl", 10,
            "Bowl", 10,
            "Bowl", 10,
            "Mugl", 10,
            "Mugl", 10,
            "Mugl", 10,
            "Mugl", 10,
            "Timer", 7,
            "BoxOfJars", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "ButterKnife", 7,
                "ButterKnife", 7,
                "BreadKnife", 7,
                "BreadKnife", 7,
                "Spoon", 10,
                "Spoon", 10,
                "Fork", 10,
                "Fork", 10,
                "Bowl", 10,
                "Bowl", 10,
                "Bowl", 10,
                "Bowl", 10,
                "Mugl", 10,
                "Mugl", 10,
                "Mugl", 10,
                "Mugl", 10,
                "Timer", 7,
                "BoxOfJars", 5,
            }
        }
    },
    
    GigamartPots = {
        rolls = 6,
        items = {
            "BakingPan", 10,
            "BakingPan", 10,
            "GridlePan", 7,
            "GridlePan", 7,
            "Kettle", 5,
            "Kettle", 5,
            "Pan", 7,
            "Pan", 7,
            "Pot", 7,
            "Pot", 7,
            "RoastingPan", 10,
            "RoastingPan", 10,
            "Saucepan", 7,
            "Saucepan", 7,
            "BoxOfJars", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "BakingPan", 10,
                "BakingPan", 10,
                "GridlePan", 7,
                "GridlePan", 7,
                "Kettle", 5,
                "Kettle", 5,
                "Pan", 7,
                "Pan", 7,
                "Pot", 7,
                "Pot", 7,
                "RoastingPan", 10,
                "RoastingPan", 10,
                "Saucepan", 7,
                "Saucepan", 7,
                "BoxOfJars", 5,
            }
        }
    },
    
    GigamartSauce = {
        rolls = 6,
        items = {
            "Ketchup", 7,
            "Ketchup", 7,
            "Marinara", 10,
            "Marinara", 10,
            "Marinara", 10,
            "Marinara", 10,
            "Mustard", 7,
            "Mustard", 7,
            "Vinegar", 10,
            "Vinegar", 10,
            "Vinegar", 10,
            "Vinegar", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Ketchup", 7,
                "Marinara", 10,
                "Mustard", 7,
                "Vinegar", 10,
            }
        }
    },
    
    GigamartSchool = {
        rolls = 6,
        items = {
            "Pen", 5,
            "Pencil", 10,
            "Pencil", 10,
            "BluePen", 7,
            "RedPen", 7,
            "RubberBand", 5,
            "Eraser", 7,
            "Scissors", 7,
            "PaperclipBox", 10,
            "Scotchtape", 7,
            "Bag_Schoolbag", 10,
            "Bag_Schoolbag", 10,
            "Bag_Schoolbag", 10,
            "Bag_Schoolbag", 10,
            "Bag_Satchel", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Pen", 5,
                "Pencil", 10,
                "BluePen", 7,
                "RedPen", 7,
                "RubberBand", 5,
                "Eraser", 7,
                "Scissors", 7,
                "PaperclipBox", 10,
                "Scotchtape", 7,
                "Bag_Schoolbag", 10,
                "Bag_Satchel", 5,
            }
        }
    },
    
    GigamartLightbulb = {
        rolls = 6,
        items = {
            "LightBulb", 7,
            "LightBulb", 7,
            "LightBulb", 7,
            "LightBulb", 7,
            "LightBulbRed", 0.01,
            "LightBulbGreen", 0.01,
            "LightBulbBlue", 0.01,
            "LightBulbYellow", 0.06,
            "LightBulbCyan", 0.03,
            "LightBulbMagenta", 0.01,
            "LightBulbOrange", 0.006,
            "LightBulbPurple", 0.003,
            "LightBulbPink", 0.001,
            "Mov_Lamp1", 5,
            "Mov_Lamp2", 5,
            "Mov_Lamp3", 5,
            "Mov_Lamp4", 5,
            "Mov_Lamp5", 5,
            "Mov_Lamp6", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "LightBulb", 7,
                "LightBulb", 7,
                "LightBulbRed", 0.01,
                "LightBulbGreen", 0.01,
                "LightBulbBlue", 0.01,
                "LightBulbYellow", 0.06,
                "LightBulbCyan", 0.03,
                "LightBulbMagenta", 0.01,
                "LightBulbOrange", 0.006,
                "LightBulbPurple", 0.003,
                "LightBulbPink", 0.001,
                "Mov_Lamp1", 5,
                "Mov_Lamp2", 5,
                "Mov_Lamp3", 5,
                "Mov_Lamp4", 5,
                "Mov_Lamp5", 5,
                "Mov_Lamp6", 5,
            }
        }
    },
    
    GigamartTools = {
        rolls = 6,
        items = {
            "ClubHammer", 5,
            "Crowbar", 7,
            "DuctTape", 10,
            "Glue", 10,
            "Hammer", 7,
            "NailsBox", 10,
            "PipeWrench", 7,
            "Plunger", 10,
            "Scotchtape", 10,
            "Screwdriver", 7,
            "ScrewsBox", 7,
            "Twine", 10,
            "Wire", 10,    
            "WoodenMallet", 7,
            "Woodglue", 5,
            "Wrench", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "ClubHammer", 5,
                "Crowbar", 7,
                "DuctTape", 10,
                "Glue", 10,
                "Hammer", 7,
                "NailsBox", 10,
                "PipeWrench", 7,
                "Plunger", 10,
                "Scotchtape", 10,
                "Screwdriver", 7,
                "ScrewsBox", 7,
                "Twine", 10,
                "Wire", 10,    
                "WoodenMallet", 7,
                "Woodglue", 5,
                "Wrench", 10,
            }
        }
    },
    
    GigamartToys = {
        rolls = 6,
        items = {
            "Bricktoys", 10,
            "Bricktoys", 10,
            "CardDeck", 7,
            "CardDeck", 7,
            "Crayons", 10,
            "Crayons", 10,
            "Cube", 7,
            "Cube", 7,
            "Doll", 10,
            "Doll", 10,
            "Spiffo", 0.001,
            "ToyBear", 10,
            "ToyBear", 10,
            "ToyCar", 10,
            "ToyCar", 10,
            "Yoyo", 7,
            "Yoyo", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Bricktoys", 10,
                "CardDeck", 7,
                "Crayons", 10,
                "Cube", 7,
                "Doll", 10,
                "Spiffo", 0.001,
                "ToyBear", 10,
                "ToyCar", 10,
                "Yoyo", 7,
            }
        }
    },
    
    GroceryStandFruits1 = {
        rolls = 6,
        items = {
            "Apple", 7,
            "Apple", 7,
            "Apple", 7,
            "Apple", 7,
            "Banana", 7,
            "Banana", 7,
            "Banana", 7,
            "Banana", 7,
            "Orange", 7,
            "Orange", 7,
            "Orange", 7,
            "Orange", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Apple", 7,
                "Banana", 7,
                "Orange", 7,
                "Watermelon", 3,
            }
        }
    },
    
    GroceryStandFruits2 = {
        rolls = 6,
        items = {
            "Cherry", 7,
            "Cherry", 7,
            "Cherry", 7,
            "Cherry", 7,
            "farming.Strewberrie", 7,
            "farming.Strewberrie", 7,
            "farming.Strewberrie", 7,
            "farming.Strewberrie", 7,
            "Peach", 7,
            "Peach", 7,
            "Peach", 7,
            "Peach", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Cherry", 7,
                "farming.Strewberrie", 7,
                "Peach", 7,
                "Watermelon", 3,
            }
        }
    },
    
    GroceryStandFruits3 = {
        rolls = 6,
        items = {
            "Grapes", 7,
            "Grapes", 7,
            "Grapes", 7,
            "Grapes", 7,
            "Lemon", 7,
            "Lemon", 7,
            "Lemon", 7,
            "Lemon", 7,
            "Pineapple", 7,
            "Pineapple", 7,
            "Pineapple", 7,
            "Pineapple", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Grapes", 7,
                "Lemon", 7,
                "Pineapple", 7,
                "Watermelon", 3,
            }
        }
    },
    
    GroceryStandLettuce = {
        rolls = 6,
        items = {
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
            "Lettuce", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Lettuce", 10,
                "Lettuce", 10,
            }
        }
    },
    
    GroceryStandVegetables1 = {
        rolls = 4,
        items = {
            "Carrots", 7,
            "Carrots", 7,
            "Carrots", 7,
            "Carrots", 7,
            "farming.Cabbage", 7,
            "farming.Cabbage", 7,
            "farming.Cabbage", 7,
            "farming.Cabbage", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "Onion", 7,
            "Onion", 7,
            "Onion", 7,
            "Onion", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Carrots", 7,
                "farming.Cabbage", 7,
                "farming.Potato", 7,
                "Onion", 7,
            }
        }
    },
    
    GroceryStandVegetables2 = {
        rolls = 6,
        items = {
            "Broccoli", 7,
            "Broccoli", 7,
            "Broccoli", 7,
            "Broccoli", 7,
            "Corn", 7,
            "Corn", 7,
            "Corn", 7,
            "Corn", 7,
            "farming.Tomato", 7,
            "farming.Tomato", 7,
            "farming.Tomato", 7,
            "farming.Tomato", 7,
            "Leek", 7,
            "Leek", 7,
            "Leek", 7,
            "Leek", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Broccoli", 7,
                "Corn", 7,
                "farming.Tomato", 7,
                "Leek", 7,
            }
        }
    },
    
    GroceryStandVegetables3 = {
        rolls = 6,
        items = {
            "Avocado", 3,
            "Avocado", 3,
            "Avocado", 3,
            "Avocado", 3,
            "BellPepper", 7,
            "BellPepper", 7,
            "BellPepper", 7,
            "BellPepper", 7,
            "Eggplant", 7,
            "Eggplant", 7,
            "Eggplant", 7,
            "Eggplant", 7,
            "farming.RedRadish", 7,
            "farming.RedRadish", 7,
            "farming.RedRadish", 7,
            "farming.RedRadish", 7,
            "Zucchini", 7,
            "Zucchini", 7,
            "Zucchini", 7,
            "Zucchini", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Avocado", 3,
                "BellPepper", 7,
                "Eggplant", 7,
                "farming.RedRadish", 7,
                "Zucchini", 7,
            }
        }
    },
    
    IceCreamKitchenFreezer = {
        rolls = 6,
        items = {
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Icecream", 25,
                "Icecream", 25,
            }
        }
    },
    
    JanitorChemicals = {
        rolls = 4,
        items = {
            "Bleach", 20,
            "Bleach", 20,
            "Bleach", 20,
            "Bleach", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Bleach", 20,
                "Bleach", 20,
            }
        }
    },
    
    JanitorCleaning = {
        rolls = 4,
        items = {
            "Bleach", 7,
            "Bleach", 7,
            "CleaningLiquid2", 7,
            "RippedSheets", 5,
            "RippedSheets", 5,
            "RippedSheetsDirty", 7,
            "RippedSheetsDirty", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Mop", 25,
                "Broom", 25,
                "Sponge", 25,
                "BucketEmpty", 10,
                "BucketEmpty", 5,
            }
        }
    },
    
    JanitorMisc = {
        rolls = 4,
        items = {
            "Bleach", 7,
            "CleaningLiquid2", 7,
            "RippedSheets", 5,
            "RippedSheets", 5,
            "RippedSheetsDirty", 7,
            "RippedSheetsDirty", 7,
            "Magazine", 7,
            "Magazine", 7,
            "Newspaper", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Mugl", 50,
                "Lighter", 25,
                "Cigarettes", 25,
                "Lunchbox", 10,
                "WhiskeyFull", 10,
                "Glasses_SafetyGoggles", 10,
                "Radio.WalkieTalkie2", 10,
                "HottieZ", 0.1,
            }
        }
    },
    
    JanitorTools = {
        rolls = 4,
        items = {
            "DuctTape", 10,
            "ScrewsBox", 10,
            "NailsBox", 10,
            "Padlock", 7,
            "Padlock", 7,
            "TrapMouse", 7,
            "TrapMouse", 7,
            "LightBulb", 5,
            "LightBulb", 5,
            "LightBulb", 5,
            "LightBulb", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Plunger", 25,
                "PipeWrench", 10,
                "Wrench", 10,
                "Bag_JanitorToolbox", 5,
            }
        }
    },
    
    JaysKitchenButcher = {
        rolls = 4,
        items = {
            "Pepper", 5,
            "Salt", 5,
            "Twine", 7,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
        },
        junk = {
            rolls = 3,
            items = {
                "Apron_White", 10,
                "Apron_Black", 10,
                "WoodenMallet", 10,
                "KitchenKnife", 7,
                "MeatCleaver", 5,
                "Gloves_LeatherGlovesBlack", 5,
            }
        }
    },
    
    JaysKitchenFreezer = {
        rolls = 6,
        items = {
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Chicken", 10,
                "Chicken", 10,
                "Chicken", 10,
                "Chicken", 10,
            }
        }
    },
    
    JaysKitchenFridge = {
        rolls = 6,
        items = {
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Chicken", 10,
            "Milk", 7,
            "Milk", 7,
            "EggCarton", 5,
            "EggCarton", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Chicken", 10,
                "Milk", 7,
                "EggCarton", 5,
            }
        }
    },
    
    JewelleryGems = {
        rolls = 5,
        items = {
            "Earring_Stone_Sapphire", 5,
            "Earring_Stone_Ruby", 5,
            "Earring_Stone_Emerald", 5,
            "Earring_Dangly_Sapphire", 5,
            "Earring_Dangly_Emerald", 5,
            "Earring_Dangly_Ruby", 5,
            "Earring_Dangly_Diamond", 3,
            "Earring_Dangly_Pearl", 8,
        }
    },
    
    JewelleryGold = {
        rolls = 6,
        items = {
            "Necklace_Gold", 10,
            "NecklaceLong_Gold", 10,
            "NoseRing_Gold", 10,
            "NoseStud_Gold", 10,
            "Earring_LoopLrg_Gold", 10,
            "Earring_LoopMed_Gold", 10,
            "Earring_LoopSmall_Gold_Both", 10,
            "Earring_LoopSmall_Gold_Top", 10,
            "Earring_Stud_Gold", 10,
            "Ring_Left_RingFinger_Gold", 10,
            "Necklace_GoldRuby", 5,
            "Necklace_GoldDiamond", 3,
            "NecklaceLong_GoldDiamond", 3,
        }
    },
    
    JewelleryOthers = {
        rolls = 6,
        items = {
            "Necklace_YingYang", 5,
            "Necklace_Choker", 5,
            "Necklace_Choker_Sapphire", 5,
            "Necklace_Choker_Amber", 5,
            "Necklace_Choker_Diamond", 5,
            "Earring_Dangly_Pearl", 8,
            "Earring_Pearl", 8,
            "Necklace_Pearl", 8,
        }
    },
    
    JewellerySilver = {
        rolls = 6,
        items = {
            "Necklace_Silver", 10,
            "NecklaceLong_Silver", 10,
            "NoseRing_Silver", 10,
            "Earring_LoopLrg_Silver", 10,
            "Earring_LoopMed_Silver", 10,
            "Earring_LoopSmall_Silver_Both", 10,
            "Earring_LoopSmall_Silver_Top", 10,
            "Earring_Stud_Silver", 10,
            "Ring_Left_RingFinger_Silver", 10,
            "Necklace_SilverCrucifix", 10,
            "Necklace_SilverSapphire", 5,
            "Necklace_SilverDiamond", 4,
            "NecklaceLong_SilverEmerald", 5,
            "NecklaceLong_SilverSapphire", 5,
            "NecklaceLong_SilverDiamond", 4,
        },
    },
    
    JewelleryWeddingRings = {
        rolls = 5,
        items = {
            "Ring_Left_RingFinger_Gold", 10,
            "Ring_Left_RingFinger_Gold", 10,
            "Ring_Left_RingFinger_Gold", 10,
            "Ring_Left_RingFinger_Silver", 10,
            "Ring_Left_RingFinger_Silver", 10,
            "Ring_Left_RingFinger_Silver", 10,
            "Ring_Right_RingFinger_SilverDiamond", 7,
            "Ring_Right_RingFinger_GoldRuby", 7,
            "Ring_Right_RingFinger_GoldDiamond", 7,
        }
    },
    
    JewelleryWrist = {
        rolls = 6,
        items = {
            "Bracelet_ChainRightSilver", 10,
            "Bracelet_BangleRightSilver", 10,
            "Bracelet_ChainRightGold", 5,
            "Bracelet_BangleRightGold", 5,
            "WristWatch_Right_DigitalDress", 5,
            "WristWatch_Right_DigitalRed", 10,
            "WristWatch_Right_DigitalBlack", 10,
            "WristWatch_Right_ClassicGold", 7,
            "WristWatch_Right_ClassicBrown", 10,
            "WristWatch_Right_ClassicBlack", 10,
        }
    },
    
    KitchenBook = {
        rolls = 3,
        items = {
            "BookFarming1", 2,
            "BookFarming2", 1.5,
            "BookFarming3", 1.2,
            "BookFarming4", 0.8,
            "BookFarming5", 0.6,
            "FarmingMag1", 2,
            "BookForaging1", 2,
            "BookForaging2", 1.5,
            "BookForaging3", 1.2,
            "BookCooking1", 2,
            "BookCooking2", 1.5,
            "BookCooking3", 1.2,
            "BookCooking4", 0.8,
            "BookCooking5", 0.6,
            "CookingMag1", 3,
            "CookingMag2", 3,
            "BookTailoring1", 1.5,
            "BookTailoring2", 1.2,
            "BookTailoring3", 0.8,
            "BookTailoring4", 0.6,
            "BookTailoring5", 0.4,
            "SheetPaper2", 10,
            "Radio.RadioBlack",2,
            "Radio.RadioRed",1,
        },
        junk = {
            rolls = 4,
            items = {
                "Magazine", 10,
                "Newspaper", 10,
                "Book", 10,
                "Notebook", 10,
            }
        }
    },
    
    KitchenBottles = {
        rolls = 4,
        items = {
            "WhiskeyFull", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "Pop", 2,
            "Pop2", 2,
            "Pop3", 2,
            "Wine2", 4,
            "Wine", 4,
            "Vinegar", 3,
            "Crisps2", 4,
            "Crisps3", 4,
            "Crisps", 4,
            "Peanuts", 4,
        },
        junk = {
            rolls = 4,
            items = {
                "Bleach", 3,
                "Peanuts", 4,
                "Wine2", 3,
                "Wine", 3,
                "WaterBottleFull", 4,
                "Mugl", 10,
                "TinOpener", 15,
            }
        }
    },
    
    KitchenBreakfast = {
        rolls = 4,
        items = {
            "Bread", 4,
            "OatsRaw", 3,
            "Cereal", 3,
            "Chocolate", 4,
            "Flour", 3,
            "PeanutButter", 3,
            "Yeast", 2,
            "Honey", 2,
        },
        junk = {
            rolls = 4,
            items = {
                "Candycane", 4,
                "Sugar", 6,
                "Coffee2", 6,
                "Teabag2", 6,
                "OatsRaw", 5,
                "Bowl", 10,
                "Mugl", 10,
                "TinOpener", 15,
                "Spoon", 20,
                "Spoon", 20,
            }
        }
    },
    
    KitchenCannedFood = {
        rolls = 4,
        items = {
            "TinnedSoup", 3,
            "TinnedBeans", 3,
            "CannedCornedBeef", 2,
            "Macandcheese", 2,
            "CannedChili", 2,
            "CannedBolognese", 2,
            "CannedCarrots2", 2,
            "CannedCorn", 2,
            "CannedMushroomSoup", 2,
            "CannedPeas", 2,
            "CannedPotato2", 2,
            "CannedSardines", 2,
            "CannedTomato2", 2,
            "Dogfood", 4,
            "TunaTin", 3,
        },
        junk = {
            rolls = 3,
            items = {
                "TinOpener", 10,
                "Salt", 7,
                "Pepper", 7,
                "DeadRat", 1,
                "DeadMouse", 1,
                "Vinegar", 5,
                "Sugar", 6,
            }
        }
    },
    
    KitchenDishes = {
        rolls = 2,
        items = {
            "RollingPin", 7,
            "KitchenKnife", 6,
            "MeatCleaver", 2,
            "ButterKnife", 7,
            "BreadKnife", 5,
            "Spoon", 10,
            "Fork", 10,
            "Bowl", 10,
            "MortarPestle", 0.5,
            "Scissors", 3,
            "JarLid", 2,
            "EmptyJar", 2,
            "BoxOfJars", 0.4,
            "Lunchbox", 1.3,
            "Lunchbox2", 0.5,
        },
        junk = {
            rolls = 4,
            items = {
                "Mugl", 10,
                "TinOpener", 15,
                "Spoon", 20,
                "Spoon", 20,
                "Fork", 20,
                "Fork", 20,
                "ButterKnife", 5,
                "BreadKnife", 5,
                "Bowl", 10,
                "DishCloth", 10,
                "Matches", 5,
                "Lighter", 2,
                "Cigarettes", 1,
            }
        }
    },
    
    KitchenDryFood = {
        rolls = 4,
        items = {
            "Pasta", 1,
            "Marinara", 1,
            "Rice", 1,
            "EmptyJar", 1,
            "JarLid", 1,
            "TunaTin", 3,
            "Ramen", 3,
            "Popcorn", 3,
        },
        junk = {
            rolls = 3,
            items = {
                "Marinara", 1,
            }
        }
    },
    
    KitchenPots = {
        rolls = 2,
        items = {
            "Pot", 7,
            "Saucepan", 10,
            "Pan", 9,
            "GridlePan", 5,
            "RoastingPan", 3,
            "BakingPan", 3,
            "Cooler", 1,
        },
    
        junk = {
            rolls = 4,
            items = {
                "Pot", 2,
                "Timer", 10,
                "RoastingPan", 7,
                "BakingPan", 10,
                "Kettle", 10,
                "DishCloth", 20,
            },
        },
    },
    
    KitchenRandom = {
        rolls = 3,
        items = {
            "Key1", 0.5,
            "Key2", 0.5,
            "Key3", 0.5,
            "Key4", 0.5,
            "Key5", 0.5,
            "TrapMouse", 0.5,
            "Thread", 0.5,
            "Needle", 0.5,
            "SewingKit", 1.5,
            "Hammer", 1,
            "Wire", 1,
            "Sparklers", 1,
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
            "Aluminum", 1,
            "Radio.RadioBlack",2,
            "Radio.RadioRed",1,
            "Scissors", 1,
            "Magazine", 1,
            "MintCandy", 0.5,
            "CleaningLiquid2", 2,
            "Pistol", 0.4,
            "Pistol2", 0.15,
            "PistolCase1", 0.3,
            "PistolCase2", 0.1,
            "Revolver_Short", 0.5,
            "Revolver", 0.2,
            "9mmClip", 0.3,
            "Bullets9mmBox", 0.3,
            "Bullets9mm", 0.7,
            "ShotgunShells", 0.7,
            "ShotgunShellsBox", 0.3,
            "Bullets38Box", 0.3,
            "Bullets38", 0.7,
            "farming.GardeningSprayEmpty", 0.3,
            "HandTorch", 1,
            "Torch", 0.5,
            "Toolbox", 0.5,
        },
    
        junk = {
            rolls = 5,
            items = {
                "Matches", 4,
                "Lighter", 2,
                "Mop", 4,
                "Broom", 4,
                "Sponge", 20,
                "Plasticbag", 8,
                "Garbagebag", 12,
                "Tote", 2,
                "SunflowerSeeds", 3,
                "CleaningLiquid2", 10,
                "Cigarettes", 0.7,
                "Pen", 10,
                "BluePen", 10,
                "RedPen", 10,
                "Pencil", 10,
                "Eraser", 10,
                "Paperclip", 10,
                "SeedBag", 1,
                "Battery", 2.5,
                "WaterDish", 1,
                "Leash", 1,
                "Yarn", 0.5,
            },
        },
    },
    
    LivingRoomShelf = {
        rolls = 4,
        items = {
            "Book", 20,
            "BookCarpentry1", 3,
            "BookCarpentry2", 1,
            "BookCarpentry3", 0.5,
            "BookCarpentry4", 0.3,
            "BookCooking1", 3,
            "BookCooking2", 1,
            "BookCooking3", 0.5,
            "BookCooking4", 0.3,
            "BookElectrician1", 3,
            "BookElectrician2", 1,
            "BookElectrician3", 0.5,
            "BookElectrician4", 0.3,
            "BookFarming1", 3,
            "BookFarming2", 1,
            "BookFarming3", 0.5,
            "BookFarming4", 0.3,
            "BookFirstAid1", 3,
            "BookFirstAid2", 1,
            "BookFirstAid3", 0.5,
            "BookFirstAid4", 0.3,
            "BookFishing1", 3,
            "BookFishing2", 1,
            "BookFishing3", 0.5,
            "BookFishing4", 0.3,
            "BookForaging1", 3,
            "BookForaging2", 1,
            "BookForaging3", 0.5,
            "BookForaging4", 0.3,
            "BookMechanic1", 3,
            "BookMechanic2", 1,
            "BookMechanic3", 0.5,
            "BookMechanic4", 0.3,
            "BookMetalWelding1", 3,
            "BookMetalWelding2", 1,
            "BookMetalWelding3", 0.5,
            "BookMetalWelding4", 0.3,
            "BookTailoring1", 3,
            "BookTailoring2", 1,
            "BookTailoring3", 0.5,
            "BookTailoring4", 0.3,
            "BookTrapping1", 3,
            "BookTrapping2", 1,
            "BookTrapping3", 0.5,
            "BookTrapping4", 0.3,
            "ComicBook", 3,
            "CookingMag1", 0.5,
            "CookingMag2", 0.5,
            "ElectronicsMag1", 0.5,
            "ElectronicsMag2", 0.5,
            "ElectronicsMag3", 0.5,
            "ElectronicsMag4", 0.5,
            "ElectronicsMag5", 0.5,
            "EngineerMagazine1", 0.5,
            "EngineerMagazine2", 0.5,
            "FarmingMag1", 0.5,
            "FishingMag1", 0.5,
            "FishingMag2", 0.5,
            "HerbalistMag", 0.5,
            "HuntingMag1", 0.5,
            "HuntingMag2", 0.5,
            "HuntingMag3", 0.5,
            "Journal", 3,
            "Magazine", 20,
            "MechanicMag1", 0.5,
            "MechanicMag2", 0.5,
            "MechanicMag3", 0.5,
            "MetalworkMag1", 0.5,
            "MetalworkMag2", 0.5,
            "MetalworkMag3", 0.5,
            "MetalworkMag4", 0.5,
            "Newspaper", 20,
            "Notebook", 20,
            "SheetPaper2", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Radio.RadioBlack", 5,
                "Radio.RadioRed", 3,
            }
        }
    },
    
    Locker = {
        rolls = 2,
        items = {
            "Tshirt_DefaultTEXTURE_TINT", 3,
            "Jumper_RoundNeck", 1,
            "TrousersMesh_DenimLight", 1,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "Skirt_Knees", 0.3,
            "Skirt_Long", 0.3,
            "Skirt_Normal", 0.3,
            "Bag_Schoolbag", 2,
            "Bag_Satchel", 0.5,
            "Cigarettes", 1,
            "Book", 3,
            "ComicBook", 1,
            "Baseball", 1,
            "Radio.RadioBlack",2,
            "Radio.RadioRed",1,
            "Radio.WalkieTalkie1",0.05,
            "Radio.WalkieTalkie2",0.03,
            "Radio.WalkieTalkie3",0.001,
            "Hat_Sweatband",1,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Bag_DuffelBagTINT", 1.5,
            "Bag_NormalHikingBag", 1.2,
            "Bag_BigHikingBag", 1,
            "Glasses_SwimmingGoggles", 0.5,
            "DumbBell", 0.3,
            "BarBell", 0.3,
            "Lunchbox", 3,
            "Lunchbox2", 1,
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
                "Pencil", 7,
                "RubberBand", 7,
                "Scissors", 3,
                "Cube", 1,
                "Basketball", 1,
                "Football", 1,
                "GolfBall", 1,
                "TennisBall", 1,
            }
        }
    },
    
    LockerArmyBedroom = {
        rolls = 4,
        items = {
            "Pistol", 1.5,
            "PistolCase1", 0.5,
            "Bullets9mmBox", 3,
            "HolsterSimple", 3,
            "HuntingKnife", 3,
            "Bag_ALICEpack_Army", 1,
            "Trousers_ArmyService", 1,
            "Shorts_CamoGreenLong", 1,
            "Trousers_CamoDesert", 1,
            "Trousers_CamoGreen", 1,
            "Trousers_CamoUrban", 1,
            "Shirt_CamoDesert", 1,
            "Shirt_CamoGreen", 1,
            "Shirt_CamoUrban", 1,
            "Tshirt_CamoDesert", 1,
            "Tshirt_CamoGreen", 1,
            "Tshirt_CamoUrban", 1,
            "Vest_BulletArmy", 0.5,
            "Jacket_CoatArmy", 1,
            "Jacket_ArmyCamoDesert", 1,
            "Jacket_ArmyCamoGreen", 1,
            "Shoes_ArmyBoots", 1,
            "Shoes_ArmyBootsDesert", 1,
        }
    },
    
    LockerClassy = {
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
            "Bag_Schoolbag", 1,
            "Bag_Satchel", 0.4,
            "Cigarettes", 1,
            "Book", 3,
            "Baseball", 1,
            "Hat_Sweatband",1,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Tshirt_Sport", 2,
            "Shorts_LongSport",2,
            "Shorts_ShortSport",2,
            "Bag_DuffelBagTINT", 0.8,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "Glasses_SwimmingGoggles", 0.5,
            "Trousers_Suit", 0.8,
            "Trousers_SuitTEXTURE", 0.8,
            "Trousers_SuitWhite", 1,
            "Suit_Jacket", 0.5,
            "Suit_JacketTINT", 0.5,
            "Jacket_WhiteTINT", 0.6,
            "Jacket_Black", 0.6,
            "Dress_Knees", 1,
            "Dress_Long", 1,
            "Dress_Normal", 1,
            "Skirt_Knees", 1,
            "Skirt_Long", 1,
            "Skirt_Normal", 1,
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
                "Pencil", 7,
                "RubberBand", 7,
                "Scissors", 3,
                "Cube", 1,
                "Basketball", 1,
                "Football", 1,
                "GolfBall", 1,
                "TennisBall", 1,
                
            },
        },
    },
    
    MagazineRackMaps = {
        rolls = 4,
        items = {
            "MuldraughMap", 5,
            "MuldraughMap", 5,
            "WestpointMap", 5,
            "WestpointMap", 5,
            "MarchRidgeMap", 5,
            "MarchRidgeMap", 5,
            "RosewoodMap", 5,
            "RosewoodMap", 5,
            "RiversideMap", 5,
            "RiversideMap", 5,
        }
    },
    
    MagazineRackMixed = {
        rolls = 6,
        items = {
            "Magazine", 7,
            "Magazine", 7,
            "Magazine", 7,
            "Magazine", 7,
            "Magazine", 7,
            "Magazine", 7,
            "ComicBook", 5,
            "ComicBook", 5,
            "ComicBook", 5,
            "ComicBook", 5,
            "FishingMag1", 1,
            "FishingMag2", 1,
            "HuntingMag1", 1,
            "HuntingMag2", 1,
            "HuntingMag3", 1,
            "HerbalistMag", 1,
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
            "HottieZ", 0.1,
        },
        junk = {
            rolls = 1,
            items = {
                "Magazine", 7,
                "Magazine", 7,
                "ComicBook", 5,
            }
        }
    },
    
    MagazineRackNewspaper = {
        rolls = 4,
        items = {
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
            "Newspaper", 5,
        }
    },
    
    Meat = {
        rolls = 6,
        items = {
            "Steak", 7,
            "Steak", 7,
            "Chicken", 10,
            "Chicken", 10,
            "PorkChop", 7,
            "PorkChop", 7,
            "MuttonChop", 7,
            "MuttonChop", 7,
            "Ham", 7,
            "Ham", 7,
            "Ham", 7,
            "Ham", 7,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "Salmon", 7,
            "Salmon", 7,
            "farming.Bacon", 10,
            "farming.Bacon", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Steak", 7,
                "Chicken", 10,
                "PorkChop", 7,
                "MuttonChop", 7,
                "MeatPatty", 10,
                "Salmon", 7,
                "farming.Bacon", 10,
            }
        }
    },
    
    MechanicShelfBooks = {
        rolls = 6,
        items = {
            "Book", 20,
            "Magazine", 20,
            "Magazine", 20,
            "Newspaper", 10,
            "MechanicMag1", 7,
            "MechanicMag2", 7,
            "MechanicMag3", 7,
            "BookMechanic1", 10,
            "BookMechanic2", 7,
            "BookMechanic3", 5,
            "BookMechanic4", 3,
            "BookMechanic5", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "MechanicMag1", 7,
                "MechanicMag2", 7,
                "MechanicMag3", 7,
                "BookMechanic1", 10,
                "BookMechanic2", 7,
                "BookMechanic3", 5,
                "BookMechanic4", 3,
                "BookMechanic5", 1,
            }
        }
    },
    
    MechanicShelfBrakes = {
        rolls = 6,
        items = {
            "EngineParts", 20,
            "EngineParts", 20,
            "NormalBrake1", 10,
            "NormalBrake2", 7,
            "NormalBrake3", 5,
            "ModernBrake1", 7,
            "ModernBrake2", 5,
            "ModernBrake3", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "NormalBrake1", 10,
                "NormalBrake2", 7,
                "NormalBrake3", 5,
                "ModernBrake1", 7,
                "ModernBrake2", 5,
                "ModernBrake3", 3,
            }
        }
    },
    
    MechanicShelfElectric = {
        rolls = 6,
        items = {
            "Screwdriver", 10,
            "CarBattery1", 10,
            "CarBattery2", 7,
            "CarBattery3", 5,
            "CarBatteryCharger", 5,
            "Radio.ElectricWire", 10,
            "Radio.RadioBlack", 7,
            "Radio.RadioRed", 5,
            "ElectronicsScrap", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Screwdriver", 75,
                "ElectronicsScrap", 75,
                "CarBatteryCharger", 50,
                "Radio.RadioBlack", 25,
                "Radio.RadioRed", 25,
            }
        }
    },
    
    MechanicShelfMisc = {
        rolls = 6,
        items = {
            "Magazine", 20,
            "Magazine", 20,
            "Newspaper", 20,
            "Tarp", 10,
            "Garbagebag", 10,
            "Bleach", 7,
            "CleaningLiquid2", 7,
            "RippedSheets", 5,
            "RippedSheets", 5,
            "RippedSheetsDirty", 7,
            "RippedSheetsDirty", 7,
            "HottieZ", 0.1,
        },
        junk = {
            rolls = 1,
            items = {
                "Broom", 75,
                "Mop", 75,
                "Tarp", 75,
                "BucketEmpty", 50,
            }
        }
    },
    
    MechanicShelfMufflers = {
        rolls = 6,
        items = {
            "EngineParts", 20,
            "EngineParts", 20,
            "NormalCarMuffler1", 10,
            "NormalCarMuffler2", 7,
            "NormalCarMuffler3", 5,
            "ModernCarMuffler1", 7,
            "ModernCarMuffler2", 5,
            "ModernCarMuffler3", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "NormalCarMuffler1", 10,
                "NormalCarMuffler2", 7,
                "NormalCarMuffler3", 5,
                "ModernCarMuffler1", 7,
                "ModernCarMuffler2", 5,
                "ModernCarMuffler3", 3,
            }
        }
    },
    
    MechanicShelfOutfit = {
        rolls = 6,
        items = {
            "WeldingMask", 7,
            "Hat_DustMask", 7,
            "Hat_DustMask", 7,
            "Hat_EarMuff_Protectors", 7,
            "Hat_EarMuff_Protectors", 7,
            "Boilersuit_BlueRed", 10,
            "Boilersuit_BlueRed", 10,
            "Boilersuit_BlueRed", 10,
            "Boilersuit_BlueRed", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "WeldingMask", 25,
                "Hat_DustMask", 50,
                "Hat_EarMuff_Protectors", 25,
                "Boilersuit_BlueRed", 75,
            }
        }
    },
    
    MechanicShelfSuspension = {
        rolls = 6,
        items = {
            "EngineParts", 20,
            "EngineParts", 20,
            "NormalSuspension1", 10,
            "NormalSuspension2", 7,
            "NormalSuspension3", 5,
            "ModernSuspension1", 7,
            "ModernSuspension2", 5,
            "ModernSuspension3", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "NormalSuspension1", 10,
                "NormalSuspension2", 7,
                "NormalSuspension3", 5,
                "ModernSuspension1", 7,
                "ModernSuspension2", 5,
                "ModernSuspension3", 3,
            }
        }
    },
    
    MechanicShelfTools = {
        rolls = 6,
        items = {
            "LugWrench", 10,
            "Wrench", 10,
            "HandTorch", 10,
            "Screwdriver", 10,
            "TirePump", 10,
            "Torch", 10,
            "Jack", 10,
            "BlowTorch", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "TirePump", 50,
                "Jack", 75,
                "Toolbox", 100,
            }
        }
    },
    
    MechanicShelfWheels = {
        rolls = 6,
        items = {
            "NormalTire1", 10,
            "NormalTire2", 7,
            "NormalTire3", 5,
            "ModernTire1", 7,
            "ModernTire2", 5,
            "ModernTire3", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "NormalTire1", 10,
                "NormalTire2", 7,
                "NormalTire3", 5,
                "ModernTire1", 7,
                "ModernTire2", 5,
                "ModernTire3", 3,
            }
        }
    },
    
    MedicalClinicTools = {
        rolls = 4,
        items = {
            "FirstAidKit", 3,
            "Scalpel", 5,
            "Scalpel", 5,
            "Scissors", 10,
            "Scissors", 10,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedleHolder", 7,
            "SutureNeedleHolder", 7,
            "Tweezers", 10,
            "Tweezers", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Scissors", 10,
                "Scalpel", 5,
                "Tweezers", 10,
                "SutureNeedleHolder", 7,
                "SutureNeedle", 7,
            }
        }
    },
    
    MedicalClinicOutfit = {
        rolls = 4,
        items = {
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Shirt_Scrubs", 7,
            "Trousers_Scrubs", 7,
            "JacketLong_Doctor", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Gloves_Surgical", 10,
                "Hat_SurgicalMask_Blue", 10,
                "Shirt_Scrubs", 7,
                "Trousers_Scrubs", 7,
                "JacketLong_Doctor", 5,
            }
        }
    },
    
    MedicalClinicTools = {
        rolls = 4,
        items = {
            "FirstAidKit", 3,
            "Scalpel", 5,
            "Scalpel", 5,
            "Scissors", 10,
            "Scissors", 10,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedleHolder", 7,
            "SutureNeedleHolder", 7,
            "Tweezers", 10,
            "Tweezers", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Scissors", 10,
                "Scalpel", 5,
                "Tweezers", 10,
                "SutureNeedleHolder", 7,
                "SutureNeedle", 7,
            }
        }
    },
    
    MedicalStorageDrugs = {
        rolls = 6,
        items = {
            "AlcoholWipes", 10,
            "AlcoholWipes", 10,
            "AlcoholWipes", 10,
            "AlcoholWipes", 10,
            "Antibiotics", 3,
            "Antibiotics", 3,
            "Bandage", 10,
            "Bandage", 10,
            "Bandage", 10,
            "Bandage", 10,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandaid", 20,
            "Bandaid", 20,
            "CottonBalls", 7,
            "CottonBalls", 7,
            "Disinfectant", 5,
            "Disinfectant", 5,
            "Pills", 7,
            "Pills", 7,
            "Pills", 7,
            "Pills", 7,
            "PillsAntiDep", 5,
            "PillsAntiDep", 5,
            "PillsBeta", 5,
            "PillsBeta", 5,
            "PillsSleepingTablets", 5,
            "PillsSleepingTablets", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "AlcoholWipes", 10,
                "Antibiotics", 3,
                "Bandage", 10,
                "Bandaid", 10,
                "CottonBalls", 7,
                "Pills", 7,
                "PillsAntiDep", 5,
                "PillsBeta", 5,
                "PillsSleepingTablets", 5,
            }
        }
    },
    
    MedicalStorageOutfit = {
        rolls = 6,
        items = {
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Gloves_Surgical", 10,
            "Hat_SurgicalCap_Blue", 10,
            "Hat_SurgicalCap_Blue", 10,
            "Hat_SurgicalCap_Blue", 10,
            "Hat_SurgicalCap_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "Hat_SurgicalMask_Blue", 10,
            "HospitalGown", 5,
            "HospitalGown", 5,
            "HospitalGown", 5,
            "HospitalGown", 5,
            "JacketLong_Doctor", 5,
            "JacketLong_Doctor", 5,
            "Shirt_Scrubs", 7,
            "Shirt_Scrubs", 7,
            "Trousers_Scrubs", 7,
            "Trousers_Scrubs", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Gloves_Surgical", 10,
                "Hat_SurgicalMask_Blue", 10,
                "HospitalGown", 5,
                "JacketLong_Doctor", 5,
                "Shirt_Scrubs", 7,
                "Trousers_Scrubs", 7,
            }
        }
    },
    
    MedicalStorageTools = {
        rolls = 6,
        items = {
            "FirstAidKit", 3,
            "FirstAidKit", 3,
            "Scalpel", 5,
            "Scalpel", 5,
            "Scalpel", 5,
            "Scalpel", 5,
            "Scissors", 10,
            "Scissors", 10,
            "Scissors", 10,
            "Scissors", 10,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedle", 7,
            "SutureNeedleHolder", 7,
            "SutureNeedleHolder", 7,
            "SutureNeedleHolder", 7,
            "SutureNeedleHolder", 7,
            "Tweezers", 10,
            "Tweezers", 10,
            "Tweezers", 10,
            "Tweezers", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "FirstAidKit", 3,
                "Scissors", 10,
                "Scalpel", 5,
                "Tweezers", 10,
                "SutureNeedleHolder", 7,
                "SutureNeedle", 7,
            }
        }
    },
    
    MeleeWeapons = {
        rolls = 2,
        items = {
            "Machete", 6,
            "Bag_WeaponBag", 7,
            "BaseballBat", 10,
            "Crowbar", 7,
            "Axe", 4,
            "Nightstick", 6,
            "HandAxe", 8,
            "Golfclub", 7,
            "Katana", 0.5,
            "BaseballBatNails", 5,
            "WoodAxe", 3,
            "Sledgehammer", 4,
            "Sledgehammer2", 4,
            "WoodAxe", 4,
            "HuntingKnife", 10,
            "SpearCrafted", 10,
            "WoodenLance", 10,
        },
        junk = {
            rolls = 3,
            items = {
                "LeadPipe", 10,
                "MetalBar", 10,
                "MetalPipe", 10,
                "IcePick", 10,
                "ButterKnife", 10,
                "KitchenKnife", 10,
            }
        }
    },
    
    MotelFridge = {
        rolls = 1,
        items = {
            "Pop", 5,
            "Pop2", 5,
            "Pop3", 5,
            "PopBottle", 5,
            "PopBottle", 5,
            "WhiskeyFull", 1,
            "Pizza", 5,
            "Fries", 5,
            "Burger", 5,
            "Hotdog", 5,
        }
    },
    
    MotelLinens = {
        rolls = 4,
        items = {
            "Sheet", 25,
            "Sheet", 25,
            "Sheet", 25,
            "Sheet", 25,
            "Sheet", 25,
            "Sheet", 25,
            "Pillow", 25,
            "Pillow", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Sheet", 25,
                "Sheet", 25,
                "Pillow", 100,
            }
        }
    },
    
    MotelTowels = {
        rolls = 4,
        items = {
            "BathTowel", 25,
            "BathTowel", 25,
            "BathTowel", 25,
            "BathTowel", 25,
            "BathTowel", 25,
            "BathTowel", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "BathTowel", 25,
                "BathTowel", 25,
            }
        }
    },
    
    MusicStoreBass = {
        rolls = 6,
        items = {
            "GuitarElectricBassBlue", 20,
            "GuitarElectricBassBlack", 20,
            "GuitarElectricBassRed", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "GuitarElectricBassBlue", 20,
                "GuitarElectricBassBlack", 20,
                "GuitarElectricBassRed", 20,
            }
        }
    },
    
    MusicStoreCases = {
        rolls = 4,
        items = {
            "Flightcase", 20,
            "Flightcase", 20,
            "Flightcase", 20,
            "Flightcase", 20,
            "Guitarcase", 20,
            "Guitarcase", 20,
            "Guitarcase", 20,
            "Guitarcase", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Flightcase", 20,
                "Guitarcase", 20,
            }
        }
    },
    
    MusicStoreCDs = {
        rolls = 6,
        items = {
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
            "Disc", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Disc", 25,
                "Disc", 25,
            }
        }
    },
    
    MusicStoreGuitar = {
        rolls = 6,
        items = {
            "Keytar", 3,
            "GuitarAcoustic", 10,
            "GuitarElectricBlack", 7,
            "GuitarElectricBlue", 7,
            "GuitarElectricRed", 7,
            "Banjo", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "Keytar", 3,
                "GuitarAcoustic", 10,
                "GuitarElectricBlack", 7,
                "GuitarElectricBlue", 7,
                "GuitarElectricRed", 7,
                "Banjo", 5,
            }
        }
    },
    
    MusicStoreOthers = {
        rolls = 6,
        items = {
            "Violin", 7,
            "Trumpet", 7,
            "Saxophone", 5,
            "Drumstick", 25,
            "Flute", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Violin", 7,
                "Trumpet", 7,
                "Saxophone", 5,
                "Drumstick", 25,
                "Flute", 10,
            }
        }
    },
    
    MusicStoreSpeaker = {
        rolls = 6,
        items = {
            "Magazine", 20,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Disc", 10,
            "Headphones", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Headphones", 100,
                "Disc", 100,
                "Speaker", 100,
                "Speaker", 100,
            }
        }
    },
    
    OptometristGlasses = {
        rolls = 6,
        items = {
            "Glasses", 20,
            "Glasses", 20,
            "Glasses_Normal", 20,
            "Glasses_Normal", 20,
            "Glasses_Reading", 20,
            "Glasses_Reading", 20,
            "Glasses_Aviators", 7,
            "Glasses_Sun", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Glasses", 20,
                "Glasses_Normal", 20,
                "Glasses_Reading", 20,
                "Mirror", 100,
            }
        }
    },
    
    PizzaKitchenCheese = {
        rolls = 4,
        items = {
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
            "Cheese", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Cheese", 25,
                "Cheese", 25,
            }
        }
    },
    
    PizzaKitchenFridge = {
        rolls = 6,
        items = {
            "Dough", 10,
            "Dough", 10,
            "Dough", 10,
            "Dough", 10,
            "Dough", 10,
            "Dough", 10,
            "Cheese", 7,
            "Cheese", 7,
            "Cheese", 7,
            "Cheese", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Dough", 10,
                "Dough", 10,
                "Cheese", 7,
            }
        }
    },
    
    PizzaKitchenSauce = {
        rolls = 4,
        items = {
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
            "Marinara", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Marinara", 25,
                "Marinara", 25,
            }
        }
    },
    
    PlankStashGun = {
        rolls = 5,
        items = {
            "PistolCase1", 15,
            "PistolCase2", 10,
            "PistolCase3", 5,
        }
    },
    
    PlankStashMagazine = {
        rolls = 7,
        items = {
            "Magazine", 30,
            "HottieZ", 30,
        }
    },
    
    PlankStashMoney = {
        rolls = 6,
        items = {
            "Money", 100,
            "Money", 100,
            "Wallet", 5,
            "Wallet2", 5,
            "Pistol", 5,
            "PistolCase1", 1,
            "PistolCase2", 0.4,
            "PistolCase3", 0.1,
        }
    },
    
    RestaurantKitchenFreezer = {
        rolls = 4,
        items = {
            "Chicken", 5,
            "Peas", 5,
            "farming.Bacon", 5,
            "Ham", 4,
            "MeatPatty", 5,
            "PorkChop", 5,
            "Steak", 3,
            "Salmon", 3,
        }
    },
    
    RestaurantKitchenFridge = {
        rolls = 4,
        items = {
            "Milk", 7,
            "Processedcheese", 7,
            "Broccoli", 7,
            "Cheese", 7,
            "Corn", 7,
            "Eggplant", 7,
            "Leek", 7,
            "Lettuce", 7,
            "Onion", 7,
            "farming.Tomato", 7,
            "Chicken", 5,
            "EggCarton", 5,
            "farming.Bacon", 5,
            "Ham", 5,
            "Ham", 5,
            "MeatPatty", 5,
            "Pickles", 5,
            "PorkChop", 5,
            "Steak", 3,
            "Salmon", 3,
            "Butter", 3,
        }
    },
    
    SalonHairCare = {
        rolls = 6,
        items = {
            "Hairspray", 10,
            "Hairspray", 10,
            "HairDyeBlonde", 7,
            "HairDyeBlack", 7,
            "HairDyeGinger", 7,
            "HairDyeLightBrown", 7,
            "Hairgel", 5,
            "Hairgel", 5,
            "HairDyeBlue", 1,
            "HairDyeGreen", 1,
            "HairDyeRed", 1,
            "HairDyeYellow", 1,
            "HairDyeWhite", 1,
        },
        junk = {
            rolls = 1,
            items = {
                "Hairspray", 10,
                "Hairgel", 5,
                "HairDyeBlue", 1,
                "HairDyeGreen", 1,
                "HairDyeRed", 1,
                "HairDyeYellow", 1,
                "HairDyeWhite", 1,
            }
        }
    },
    
    SalonHairTools = {
        rolls = 6,
        items = {
            "BathTowel", 10,
            "BathTowel", 10,
            "Comb", 7,
            "Hairspray", 10,
            "Hairspray", 10,
            "Hairgel", 5,
            "Razor", 7,
            "Scissors", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "BathTowel", 10,
                "Comb", 7,
                "Hairspray", 10,
                "Hairgel", 5,
                "Razor", 7,
                "Scissors", 7,
            }
        }
    },
    
    SalonTowels = {
        rolls = 6,
        items = {
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
            "BathTowel", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "BathTowel", 10,
                "BathTowel", 10,
            }
        }
    },
    
    ServingTrayBurgers = {
        rolls = 6,
        items = {
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
            "BurgerRecipe", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "BurgerRecipe", 20,
                "BurgerRecipe", 20,
            }
        }
    },
    
    ServingTrayChicken = {
        rolls = 6,
        items = {
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
            "ChickenFried", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "ChickenFried", 20,
                "ChickenFried", 20,
            }
        }
    },
    
    ServingTrayFries = {
        rolls = 6,
        items = {
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
            "Fries", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Fries", 20,
                "Fries", 20,
            }
        }
    },
    
    ServingTrayHotdogs = {
        rolls = 6,
        items = {
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
            "Hotdog", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Hotdog", 20,
                "Hotdog", 20,
            }
        }
    },
    
    ServingTrayPie = {
        rolls = 6,
        items = {
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
            "Pie", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Pie", 20,
                "Pie", 20,
            }
        }
    },
    
    ServingTrayPizza = {
        rolls = 6,
        items = {
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
            "Pizza", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Pizza", 20,
                "Pizza", 20,
            }
        }
    },
    
    SpiffosKitchenFreezer = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "farming.Bacon", 7,
            "farming.Bacon", 7,
            "Steak", 5,
            "Steak", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
                "MeatPatty", 10,
                "farming.Bacon", 7,
                "Steak", 5,
            }
        }
    },
    
    SpiffosKitchenFridge = {
        rolls = 6,
        items = {
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "MeatPatty", 10,
            "Processedcheese", 7,
            "Processedcheese", 7,
            "farming.Bacon", 7,
            "farming.Bacon", 7,
            "farming.Tomato", 7,
            "farming.Tomato", 7,
            "Lettuce", 7,
            "Lettuce", 7,
            "Onion", 7,
            "Onion", 7,
            "Pickles", 5,
            "Pickles", 5,
        },
        junk = {
            rolls = 1,
            items = {
                "MeatPatty", 10,
                "Processedcheese", 7,
                "farming.Bacon", 7,
                "farming.Tomato", 7,
                "Lettuce", 7,
                "Onion", 7,
                "Pickles", 5,
            }
        }
    },
    
    SportStoreSneakers = {
        rolls = 6,
        items = {
            "Shoes_TrainerTINT", 20,
            "Shoes_TrainerTINT", 20,
            "Shoes_TrainerTINT", 20,
            "Shoes_TrainerTINT", 20,
            "Shoes_BlueTrainers", 20,
            "Shoes_RedTrainers", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Shoes_TrainerTINT", 20,
                "Shoes_BlueTrainers", 20,
                "Shoes_RedTrainers", 20,
            }
        }
    },

    SportStorageBats = {
        rolls = 6,
        items = {
            "BaseballBat", 20,
            "BaseballBat", 20,
            "BaseballBat", 20,
            "BaseballBat", 20,
            "BaseballBat", 20,
            "BaseballBat", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "BaseballBat", 20,
            }
        }
    },
    
    SportStorageBalls = {
        rolls = 4,
        items = {
            "Baseball", 20,
            "Baseball", 20,
            "Football", 20,
            "Basketball", 20,
            "SoccerBall", 20,
            "TennisBall", 10,
            "TennisBall", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Baseball", 20,
                "Football", 20,
                "Basketball", 20,
                "SoccerBall", 20,
                "TennisBall", 10,
            }
        }
    },
    
    SportStorageHelmets = {
        rolls = 6,
        items = {
            "Hat_BicycleHelmet", 20,
            "Hat_BicycleHelmet", 20,
            "Hat_HockeyHelmet", 20,
            "Hat_HockeyHelmet", 20,
            "Hat_RidingHelmet", 20,
            "Hat_RidingHelmet", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_BicycleHelmet", 20,
                "Hat_HockeyHelmet", 20,
                "Hat_RidingHelmet", 20,
            }
        }
    },
    
    SportStoragePaddles = {
        rolls = 6,
        items = {
            "CanoePadel", 20,
            "CanoePadel", 20,
            "CanoePadel", 20,
            "CanoePadel", 20,
            "CanoePadelX2", 20,
            "CanoePadelX2", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "CanoePadel", 20,
                "CanoePadelX2", 20,
            }
        }
    },
    
    SportStorageRacquets = {
        rolls = 6,
        items = {
            "TennisRacket", 20,
            "TennisRacket", 20,
            "TennisRacket", 20,
            "TennisRacket", 20,
            "BadmintonRacket", 20,
            "BadmintonRacket", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "TennisRacket", 20,
                "BadmintonRacket", 20,
            }
        }
    },
    
    SportStorageSticks = {
        rolls = 6,
        items = {
            "HockeyStick", 10,
            "IceHockeyStick", 20,
            "IceHockeyStick", 20,
            "IceHockeyStick", 20,
            "LaCrosseStick", 20,
            "LaCrosseStick", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "HockeyStick", 10,
                "IceHockeyStick", 20,
                "LaCrosseStick", 20,
            }
        }
    },

    StorageUnitBooks = {
        rolls = 4,
        items = {
            "Book", 50,
            "Book", 50,
            "Book", 50,
            "Book", 50,
            "BookCarpentry1", 2,
            "BookCarpentry2", 1,
            "BookCarpentry3", 0.7,
            "BookCarpentry4", 0.5,
            "BookCarpentry5", 0.3,
            "BookCooking1", 2,
            "BookCooking2", 1,
            "BookCooking3", 0.5,
            "BookCooking4", 0.3,
            "BookElectrician1", 2,
            "BookElectrician2", 1,
            "BookElectrician3", 0.7,
            "BookElectrician4", 0.5,
            "BookElectrician5", 0.3,
            "BookFarming1", 2,
            "BookFarming2", 1,
            "BookFarming3", 0.7,
            "BookFarming4", 0.5,
            "BookFarming5", 0.3,
            "BookFirstAid1", 2,
            "BookFirstAid2", 1,
            "BookFirstAid3", 0.7,
            "BookFirstAid4", 0.5,
            "BookFirstAid5", 0.3,
            "BookFishing1", 2,
            "BookFishing2", 1,
            "BookFishing3", 0.7,
            "BookFishing4", 0.5,
            "BookFishing5", 0.3,
            "BookForaging1", 2,
            "BookForaging2", 1,
            "BookForaging3", 0.7,
            "BookForaging4", 0.5,
            "BookForaging5", 0.3,
            "BookMechanic1", 2,
            "BookMechanic2", 1,
            "BookMechanic3", 0.7,
            "BookMechanic4", 0.5,
            "BookMechanic5", 0.3,
            "BookMetalWelding1", 2,
            "BookMetalWelding2", 1,
            "BookMetalWelding3", 0.7,
            "BookMetalWelding4", 0.5,
            "BookMetalWelding5", 0.3,
            "BookTailoring1", 2,
            "BookTailoring2", 1,
            "BookTailoring3", 0.7,
            "BookTailoring4", 0.5,
            "BookTailoring5", 0.3,
            "BookTrapping1", 2,
            "BookTrapping2", 1,
            "BookTrapping3", 0.7,
            "BookTrapping4", 0.5,
            "BookTrapping5", 0.3,
        },
        junk = {
            rolls = 1,
            items = {
                "Book", 50,
            }
        }
    },
    
    StorageUnitCanning = {
        rolls = 4,
        items = {
            "BoxOfJars", 20,
            "EmptyJar", 50,
            "JarLid", 50,
        },
        junk = {
            rolls = 1,
            items = {
                "BoxOfJars", 100,
            }
        }
    },
    
    StorageUnitInstruments = {
        rolls = 4,
        items = {
            "Banjo", 5,
            "Drumstick", 10,
            "Flute", 7,
            "GuitarAcoustic", 10,
            "GuitarElectricBassBlack", 7,
            "GuitarElectricBassBlue", 7,
            "GuitarElectricBassRed", 7,
            "GuitarElectricBlack", 7,
            "GuitarElectricBlue", 7,
            "GuitarElectricRed", 7,
            "Keytar", 3,
            "Saxophone", 5,
            "Trumpet", 7,
            "Violin", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "GuitarAcoustic", 100,
            }
        }
    },
    
    StorageUnitMagazines = {
        rolls = 4,
        items = {
            "Magazine", 10,
            "Magazine", 10,
            "Magazine", 10,
            "Magazine", 10,
            "ComicBook", 5,
            "ComicBook", 5,
            "CookingMag1", 1,
            "CookingMag2", 1,
            "ElectronicsMag1", 1,
            "ElectronicsMag2", 1,
            "ElectronicsMag3", 1,
            "ElectronicsMag4", 1,
            "ElectronicsMag5", 1,
            "EngineerMagazine1", 1,
            "EngineerMagazine2", 1,
            "FarmingMag1", 1,
            "FishingMag1", 1,
            "FishingMag2", 1,
            "HerbalistMag", 1,
            "HuntingMag1", 1,
            "HuntingMag2", 1,
            "HuntingMag3", 1,
            "MechanicMag1", 1,
            "MechanicMag2", 1,
            "MechanicMag3", 1,
            "MetalworkMag1", 1,
            "MetalworkMag2", 1,
            "MetalworkMag3", 1,
            "MetalworkMag4", 1,
            "HottieZ", 0.1,
        },
        junk = {
            rolls = 1,
            items = {
                "Magazine", 50,
                "ComicBook", 5,
                "HottieZ", 0.1,
            }
        }
    },
    
    StorageUnitSports = {
        rolls = 4,
        items = {
            "BadmintonRacket", 7,
            "Baseball", 10,
            "BaseballBat", 10,
            "Basketball", 7,
            "CanoePadel", 5,
            "CanoePadelX2", 5,
            "Football", 7,
            "Hat_BicycleHelmet", 10,
            "Hat_HockeyHelmet", 7,
            "Hat_RidingHelmet", 3,
            "HockeyStick", 5,
            "IceHockeyStick", 10,
            "LaCrosseStick", 7,
            "SoccerBall", 7,
            "TennisBall", 10,
            "TennisRacket", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Hat_BicycleHelmet", 100,
                "IceHockeyStick", 75,
                "BaseballBat", 75,
            }
        }
    },
    
    StorageUnitTools = {
        rolls = 4,
        items = {
            "ClubHammer", 3,
            "Crowbar", 5,
            "farming.HandShovel", 10,
            "GardenFork", 1,
            "GardenSaw", 10,
            "Hammer", 7,
            "HandAxe", 3,
            "HandFork", 3,
            "HandScythe", 3,
            "Jack", 3,
            "LeafRake", 10,
            "LugWrench", 7,
            "Machete", 0.5,
            "PickAxe", 1,
            "PipeWrench", 7,
            "Rake", 10,
            "Saw", 7,
            "Screwdriver", 10,
            "Shovel", 5,
            "Shovel2", 5,
            "SnowShovel", 3,
            "TirePump", 5,
            "Toolbox", 3,
            "WoodenMallet", 3,
            "Wrench", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Toolbox", 100,
                "Axe", 1,
                "WoodAxe", 0.5,
                "Sledgehammer", 0.1,
                "Sledgehammer2", 0.1,
            }
        }
    },
    
    StorageUnitWelding = {
        rolls = 4,
        items = {
            "BlowTorch", 5,
            "MetalBar", 7,
            "MetalBar", 7,
            "MetalPipe", 7,
            "MetalPipe", 7,
            "MetalworkMag1", 10,
            "MetalworkMag2", 10,
            "MetalworkMag3", 7,
            "MetalworkMag4", 7,
            "PropaneTank", 3,
            "SheetMetal", 10,
            "SheetMetal", 10,
            "SheetMetal", 10,
            "SheetMetal", 10,
            "SmallSheetMetal", 10,
            "SmallSheetMetal", 10,
            "WeldingRods", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "BlowTorch", 100,
                "WeldingMask", 75,
                "PropaneTank", 75,
            }
        }
    },
    
    StoreCounterBags = {
        rolls = 4,
        items = {
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
            "Plasticbag", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Plasticbag", 25,
                "Plasticbag", 25,
            }
        }
    },
    
    StoreCounterBagsFancy = {
        rolls = 4,
        items = {
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
            "Tote", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Tote", 20,
                "Tote", 20,
            }
        }
    },
    
    StoreCounterCleaning = {
        rolls = 4,
        items = {
            "Bleach", 20,
            "CleaningLiquid2", 20,
            "Sponge", 10,
            "DishCloth", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Sponge", 75,
                "DishCloth", 75,
                "Extinguisher", 50,
                "BucketEmpty", 50,
            }
        }
    },
    
    StoreCounterTobacco = {
        rolls = 6,
        items = {
            "Lighter", 20,
            "Lighter", 20,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
            "Cigarettes", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Lighter", 100,
                "Cigarettes", 100,
            }
        }
    },
    
    StoreDisplayWatches = {
        rolls = 6,
        items = {
            "WristWatch_Right_ClassicBlack", 7,
            "WristWatch_Right_ClassicBlack", 7,
            "WristWatch_Right_ClassicBrown", 7,
            "WristWatch_Right_ClassicBrown", 7,
            "WristWatch_Right_ClassicGold", 3,
            "WristWatch_Right_ClassicGold", 3,
            "WristWatch_Right_DigitalBlack", 10,
            "WristWatch_Right_DigitalBlack", 10,
            "WristWatch_Right_DigitalDress", 5,
            "WristWatch_Right_DigitalRed", 10,
            "WristWatch_Right_DigitalRed", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "WristWatch_Right_ClassicBlack", 7,
                "WristWatch_Right_ClassicBrown", 7,
                "WristWatch_Right_DigitalBlack", 10,
                "WristWatch_Right_DigitalRed", 10,
            }
        }
    },
    
    StoreKitchenBaking = {
        rolls = 4,
        items = {
            "Flour", 25,
            "Flour", 25,
            "Yeast", 20,
            "Yeast", 20,
            "Sugar", 10,
            "Sugar", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Flour", 25,
                "Yeast", 20,
                "DishCloth", 20,
                "RollingPin", 50,
                "Apron_White", 75,
            }
        }
    },
    
    StoreKitchenButcher = {
        rolls = 4,
        items = {
            "Pepper", 10,
            "Salt", 10,
            "Twine", 10,
            "Steak", 5,
            "Steak", 5,
            "PorkChop", 7,
            "PorkChop", 7,
            "Chicken", 10,
            "Chicken", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Apron_White", 75,
                "WoodenMallet", 25,
                "KitchenKnife", 10,
                "MeatCleaver", 5,
            }
        }
    },
    
    StoreKitchenCafe = {
        rolls = 4,
        items = {
            "Coffee2", 20,
            "Kettle", 7,
            "Mugl", 10,
            "Mugl", 10,
            "Mugl", 10,
            "Mugl", 10,
            "Sugar", 10,
            "Teabag2", 20,
            "Teabag2", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Apron_White", 75,
                "DishCloth", 75,
                "Kettle", 100,
            }
        }
    },
    
    StoreKitchenDishes = {
        rolls = 4,
        items = {
            "Bowl", 20,
            "Bowl", 20,
            "BreadKnife", 10,
            "ButterKnife", 20,
            "ButterKnife", 20,
            "DishCloth", 20,
            "Fork", 20,
            "Fork", 20,
            "KitchenKnife", 10,
            "MeatCleaver", 5,
            "Spoon", 20,
            "Spoon", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Bowl", 20,
                "BreadKnife", 10,
                "ButterKnife", 20,
                "DishCloth", 20,
                "Fork", 20,
                "Spoon", 20,
            }
        }
    },
    
    StoreKitchenPotatoes = {
        rolls = 6,
        items = {
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
            "farming.Potato", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "EmptySandbag", 10,
            }
        }
    },
    
    StoreKitchenPots = {
        rolls = 4,
        items = {
            "Pot", 20,
            "Pot", 20,
            "Pan", 20,
            "Pan", 20,
            "GridlePan", 20,
            "GridlePan", 20,
            "RoastingPan", 20,
            "RoastingPan", 20,
            "BakingPan", 20,
            "BakingPan", 20,
            "DishCloth", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Pot", 20,
                "Pan", 20,
                "GridlePan", 20,
                "RoastingPan", 20,
                "BakingPan", 20,
                "DishCloth", 10,
            }
        }
    },
    
    StoreKitchenSauce = {
        rolls = 4,
        items = {
            "Ketchup", 20,
            "Ketchup", 20,
            "Mustard", 20,
            "Mustard", 20,
            "Vinegar", 20,
            "Vinegar", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Ketchup", 20,
                "Mustard", 20,
                "Vinegar", 20,
            }
        }
    },
    
    StoreShelfBeer = {
        rolls = 6,
        items = {
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerCan", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
            "BeerBottle", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "BeerCan", 20,
                "BeerBottle", 20,
            }
        }
    },
    
    StoreShelfDrinks = {
        rolls = 4,
        items = {
            "Pop", 4,
            "Pop", 4,
            "Pop2", 4,
            "Pop2", 4,
            "Pop3", 4,
            "Pop3", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "PopBottle", 4,
            "WaterBottleFull", 4,
            "WaterBottleFull", 4,
        }
    },
    
    StoreShelfElectronics = {
        rolls = 6,
        items = {
            "Battery", 10,
            "Battery", 10,
            "Battery", 10,
            "Battery", 10,
            "CordlessPhone", 7,
            "HandTorch", 10,
            "Radio.RadioBlack", 5,
            "Radio.RadioRed", 3,
            "Radio.WalkieTalkie1", 5,
            "Radio.WalkieTalkie2", 3,
            "Radio.WalkieTalkie3", 1,
            "Remote", 10,
            "Torch", 7,
        },
        junk = {
            rolls = 1,
            items = {
                "Battery", 10,
                "CordlessPhone", 7,
                "HandTorch", 10,
                "Remote", 10,
                "Torch", 7,
            }
        }
    },
    
    StoreShelfMechanics = {
        rolls = 4,
        items = {
            "EmptyPetrolCan", 10,
            "EmptyPetrolCan", 10,
            "TirePump", 10,
            "Wrench", 7,
            "LugWrench", 7,
            "Jack", 5,
            "MechanicMag1", 7,
            "MechanicMag2", 7,
            "MechanicMag3", 7,
            "MuldraughMap", 5,
            "WestpointMap", 5,
            "MarchRidgeMap", 5,
            "RosewoodMap", 5,
            "RiversideMap", 5,
            "BookMechanic1", 5,
            "BookMechanic2", 5,
            "CarBatteryCharger", 3,
        },
        junk = {
            rolls = 1,
            items = {
                "EmptyPetrolCan", 10,
                "TirePump", 10,
                "Wrench", 7,
                "LugWrench", 7,
                "Jack", 5,
                "CarBatteryCharger", 3,
            }
        }
    },
    
    StoreShelfMedical = {
        rolls = 4,
        items = {
            "AlcoholWipes", 10,
            "AlcoholWipes", 10,
            "Bandage", 7,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandaid", 10,
            "Bandaid", 10,
            "Disinfectant", 5,
            "Pills", 7,
            "Pills", 7,
            "PillsVitamins", 10,
            "PillsVitamins", 10,
            "PillsVitamins", 10,
            "PillsVitamins", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "AlcoholWipes", 10,
                "Bandage", 7,
                "Bandaid", 10,
                "Disinfectant", 5,
                "Pills", 7,
                "PillsVitamins", 10,
            }
        }
    },
    
    StoreShelfSnacks = {
        rolls = 4,
        items = {
            "CandyPackage", 7,
            "Crisps", 5,
            "Crisps2", 5,
            "Crisps3", 5,
            "Crisps4", 5,
            "Chocolate", 5,
            "Chocolate", 5,
            "BeefJerky", 5,
            "BeefJerky", 5,
            "MintCandy", 3,
            "Modjeska", 3,
            "Candycane", 3,
            "Popcorn", 2,
            "Peanuts", 2,
            "SunflowerSeeds", 2,
            "Cupcake", 2,
            "CookieJelly", 2,
            "CookieChocolateChip", 2,
        }
    },
    
    StoreShelfSpices = {
        rolls = 6,
        items = {
            "Salt", 10,
            "Salt", 10,
            "Salt", 10,
            "Salt", 10,
            "Pepper", 10,
            "Pepper", 10,
            "Pepper", 10,
            "Pepper", 10,
        },
        junk = {
            rolls = 1,
            items = {
                "Salt", 10,
                "Pepper", 10,
            }
        }
    },
    
    StoreShelfWhiskey = {
        rolls = 6,
        items = {
            "WhiskeyFull", 20,
            "WhiskeyFull", 20,
            "WhiskeyFull", 20,
            "WhiskeyFull", 20,
            "WhiskeyFull", 20,
            "WhiskeyFull", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "WhiskeyFull", 20,
            }
        }
    },
    
    StoreShelfWine = {
        rolls = 6,
        items = {
            "Wine", 20,
            "Wine", 20,
            "Wine", 20,
            "Wine", 20,
            "Wine2", 20,
            "Wine2", 20,
            "Wine2", 20,
            "Wine2", 20,
        },
        junk = {
            rolls = 1,
            items = {
                "Wine", 20,
                "Wine2", 20,
            }
        }
    },
    
    TheatreDrinks = {
        rolls = 6,
        items = {
            "Pop", 10,
            "Pop", 10,
            "Pop2", 10,
            "Pop2", 10,
            "Pop3", 10,
            "Pop3", 10,
            "PopBottle", 7,
            "PopBottle", 7,
            "PopBottle", 7,
            "PopBottle", 7,
        }
    },
    
    TheatreKitchenFreezer = {
        rolls = 6,
        items = {
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
            "Icecream", 25,
        },
        junk = {
            rolls = 1,
            items = {
                "Icecream", 25,
                "Icecream", 25,
            }
        }
    },
    
    TheatreSnacks = {
        rolls = 6,
        items = {
            "CandyPackage", 10,
            "Crisps", 10,
            "Crisps2", 10,
            "Crisps3", 10,
            "Crisps4", 10,
            "Chocolate", 7,
            "Chocolate", 7,
            "BeefJerky", 7,
            "BeefJerky", 7,
            "MintCandy", 5,
            "Modjeska", 5,
            "Candycane", 5,
            "Peanuts", 5,
            "SunflowerSeeds", 5,
        }
    },
    
    TheatrePopcorn = {
        rolls = 6,
        items = {
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
                "Popcorn", 20,
        }
    },
    
    WardrobeChild = {
        rolls = 3,
        items = {
            "Hat_BucketHat", 0.6,
            "Shoes_FlipFlop", 0.6,
            "Bag_FannyPackFront", 0.8,
            "Shoes_TrainerTINT", 2,
            "Bag_Schoolbag", 0.5,
            "Bag_Satchel", 0.3,
            "AlarmClock2", 2,
            "Skirt_Knees", 2,
            "Skirt_Long", 2,
            "Skirt_Normal", 2,
            "Skirt_Short", 2,
            "Trousers", 2,
            "Trousers_DefaultTEXTURE", 2,
            "Trousers_DefaultTEXTURE_HUE", 2,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Trousers_Denim", 2,
            "Trousers_WhiteTINT", 2,
            "Tshirt_DefaultTEXTURE_TINT",4,
            "Tshirt_WhiteLongSleeveTINT",2,
            "Tshirt_WhiteTINT", 4,
            "Vest_DefaultTEXTURE_TINT", 2,
            "Jumper_PoloNeck", 0.5,
            "Jumper_RoundNeck", 0.5,
            "Jumper_TankTopTINT", 0.3,
            "Jumper_VNeck", 0.5,
            "Hat_EarMuffs", 0.2,
            "Hat_WoolyHat", 0.2,
            "SwimTrunks_Blue", 0.1,
            "SwimTrunks_Green", 0.1,
            "SwimTrunks_Red", 0.1,
            "SwimTrunks_Yellow", 0.1,
            "Spiffo", 0.001,
            "Flute", 0.05,
            "GuitarAcoustic", 0.07,
            "GuitarElectricBlack", 0.02,
            "GuitarElectricBlue", 0.02,
            "GuitarElectricRed", 0.02,
            "GuitarElectricBassBlue", 0.02,
            "GuitarElectricBassBlack", 0.02,
            "GuitarElectricBassRed", 0.02,
            "Flightcase", 0.03,
            "Guitarcase", 0.03,
            "Keytar", 0.01,
            "Lunchbox", 1.3,
            "Lunchbox2", 0.6,
        },
        junk = {
            rolls = 3,
            items = {
                "Disc", 3,
                "CDplayer", 2,
                "Earbuds", 2,
                "Headphones", 2,
                "VideoGame", 2,
                "Pillow", 9,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
                "Yoyo", 2,
                "CardDeck", 1,
                "Bricktoys", 4,
                "Bricktoys", 4,
                "Bricktoys", 4,
                "ToyCar", 2,
                "ToyCar", 2,
                "Crayons", 4,
                "Crayons", 4,
                "Doll", 4,
                "Pen", 7,
                "BluePen", 7,
                "RedPen", 7,
                "Pencil", 7,
                "Cube", 3,
                "ToyBear",2,
                "Cube", 2,
                "Bracelet_LeftFriendshipTINT", 1,
                "ChessBlack", 1,
                "ChessWhite", 1,
            }
        }
    },
    
    WardrobeMan = {
        rolls = 2,
        items = {
            "PistolCase1", 0.3,
            "PistolCase2", 0.2,
            "Bag_Satchel", 0.3,
            "DumbBell", 0.2,
            "BarBell", 0.2,
            "Hat_BucketHat", 0.6,
            "Shoes_FlipFlop", 0.6,
            "Bag_FannyPackFront", 0.8,
            "Shoes_Random", 1.5,
            "Shoes_TrainerTINT", 1.5,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "Bag_DuffelBagTINT", 0.8,
            "LongJohns_Bottoms", 1,
            "LongJohns", 1,
            "Shorts_LongDenim", 1,
            "Trousers", 2,
            "TrousersMesh_DenimLight", 2,
            "Trousers_DefaultTEXTURE", 2,
            "Trousers_DefaultTEXTURE_HUE", 2,
            "Trousers_DefaultTEXTURE_TINT", 2,
            "Trousers_Denim", 2,
            "Trousers_Padded", 0.3,
            "Trousers_Suit", 0.3,
            "Trousers_SuitTEXTURE", 0.3,
            "Trousers_WhiteTINT", 2,
            "Shirt_FormalWhite", 1,
            "Shirt_FormalTINT", 1,
            "Shirt_FormalWhite_ShortSleeve", 1,
            "Shirt_FormalWhite_ShortSleeveTINT", 1,
            "Tshirt_DefaultTEXTURE_TINT",2.5,
            "Tshirt_PoloTINT",0.7,
            "Tshirt_WhiteLongSleeveTINT",1.5,
            "Tshirt_WhiteTINT", 2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "JacketLong_Random", 0.3,
            "Jacket_Black", 0.3,
            "Jacket_Padded", 0.3,
            "Jacket_WhiteTINT", 0.5,
            "Jumper_DiamondPatternTINT", 0.3,
            "Jumper_PoloNeck", 0.5,
            "Jumper_RoundNeck", 0.5,
            "Jumper_TankTopTINT", 0.3,
            "HoodieDOWN_WhiteTINT", 0.5,
            "Jumper_VNeck", 0.5,
            "Hat_EarMuffs", 0.1,
            "Hat_Fedora", 0.2,
            "Hat_WoolyHat", 0.1,
            "Gloves_WhiteTINT", 0.1,
            "Gloves_FingerlessGloves", 0.6,
            "Gloves_LeatherGloves", 0.05,
            "Gloves_LeatherGlovesBlack", 0.05,
            "SwimTrunks_Blue", 0.1,
            "SwimTrunks_Green", 0.1,
            "SwimTrunks_Red", 0.1,
            "SwimTrunks_Yellow", 0.1,
            "BaseballBat", 0.08,
            "Scarf_White", 0.02,
            "Scarf_StripeBlackWhite", 0.02,
            "Scarf_StripeBlueWhite", 0.02,
            "Scarf_StripeRedWhite", 0.02,
            "Saxophone", 0.02,
            "Trumpet", 0.02,
            "GuitarAcoustic", 0.03,
            "GuitarElectricBlack", 0.03,
            "GuitarElectricBlue", 0.03,
            "GuitarElectricRed", 0.03,
            "GuitarElectricBassBlue", 0.03,
            "GuitarElectricBassBlack", 0.03,
            "GuitarElectricBassRed", 0.03,
            "Flightcase", 0.05,
            "Guitarcase", 0.05,
            "Keytar", 0.02,
            "DoubleBarrelShotgun", 0.5,
            "Suitcase", 0.3,
            "Briefcase", 0.3,
        },
        junk = {
            rolls = 2,
            items = {
                "ClosedUmbrellaRed", 0.3,
                "ClosedUmbrellaBlue", 0.3,
                "ClosedUmbrellaBlack", 0.3,
                "ClosedUmbrellaWhite", 0.3,
                "AlarmClock2", 2,
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Pillow", 9,
                "Belt2", 5,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
            }
        }
    },
    
    WardrobeManClassy = {
        rolls = 2,
        items = {
            "PistolCase1", 0.3,
            "PistolCase2", 0.2,
            "Bag_Satchel", 0.3,
            "DumbBell", 0.2,
            "BarBell", 0.2,
            "Shoes_Random", 2.2,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "Bag_DuffelBagTINT", 0.8,
            "Trousers", 2,
            "TrousersMesh_DenimLight", 1,
            "Trousers_DefaultTEXTURE", 1.5,
            "Trousers_DefaultTEXTURE_HUE", 1.5,
            "Trousers_DefaultTEXTURE_TINT", 1.5,
            "Trousers_Padded", 0.3,
            "Trousers_Suit", 0.8,
            "Trousers_SuitTEXTURE", 0.8,
            "Trousers_SuitWhite", 1,
            "Trousers_WhiteTINT", 2,
            "Suit_Jacket", 0.5,
            "Suit_JacketTINT", 0.5,
            "Shirt_FormalWhite", 3,
            "Shirt_FormalTINT", 3,
            "Shirt_FormalWhite_ShortSleeve", 3,
            "Shirt_FormalWhite_ShortSleeveTINT", 3,
            "Tshirt_DefaultTEXTURE_TINT",1.5,
            "Tshirt_PoloTINT",1.7,
            "Tshirt_WhiteLongSleeveTINT",1,
            "Tshirt_WhiteTINT", 1.2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "Jacket_Padded", 0.3,
            "Jacket_WhiteTINT", 0.6,
            "Jacket_Black", 0.6,
            "Jumper_DiamondPatternTINT", 1,
            "Jumper_PoloNeck", 1,
            "Jumper_RoundNeck", 1,
            "Jumper_TankTopTINT", 1,
            "Jumper_VNeck", 1,
            "Hat_Fedora_Delmonte", 0.5,
            "Hat_GolfHatTINT", 0.2,
            "Gloves_LeatherGloves", 0.2,
            "Tie_BowTieFull", 0.5,
            "Tie_BowTieWorn", 0.5,
            "Tie_Full", 0.5,
            "Tie_Worn", 0.5,
            "BaseballBat", 0.05,
            "SwimTrunks_Blue", 0.1,
            "SwimTrunks_Green", 0.1,
            "SwimTrunks_Red", 0.1,
            "SwimTrunks_Yellow", 0.1,
            "Scarf_White", 0.02,
            "Scarf_StripeBlackWhite", 0.02,
            "Scarf_StripeBlueWhite", 0.02,
            "Scarf_StripeRedWhite", 0.02,
            "Saxophone", 0.03,
            "Violin", 0.04,
            "Bag_GolfBag", 0.04,
            "Bag_MoneyBag", 0.01,
            "Suitcase", 0.3,
            "Briefcase", 0.3,
        },
        junk = {
            rolls = 2,
            items = {
                "ClosedUmbrellaRed", 0.3,
                "ClosedUmbrellaBlue", 0.3,
                "ClosedUmbrellaBlack", 0.3,
                "ClosedUmbrellaWhite", 0.3,
                "AlarmClock2", 2,
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Pillow", 9,
                "Belt2", 5,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
            }
        }
    },
    
    WardrobeRedneck = {
        rolls = 2,
        items = {
            "DumbBell", 0.3,
            "BarBell", 0.3,
            "Hat_BucketHat", 1,
            "Shoes_FlipFlop", 0.7,
            "Bag_FannyPackFront", 0.9,
            "Shoes_Random", 1.5,
            "Shoes_TrainerTINT", 2,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "ClosedUmbrellaRed", 0.3,
            "ClosedUmbrellaBlue", 0.3,
            "ClosedUmbrellaBlack", 0.3,
            "ClosedUmbrellaWhite", 0.3,
            "AlarmClock2", 2,
            "LongJohns_Bottoms", 1,
            "LongJohns", 1,
            "Shorts_LongDenim", 2,
            "Shorts_ShortDenim", 2,
            "Trousers", 1,
            "TrousersMesh_DenimLight", 3,
            "Trousers_DefaultTEXTURE", 1,
            "Trousers_DefaultTEXTURE_HUE", 1,
            "Trousers_DefaultTEXTURE_TINT", 1,
            "Trousers_Denim", 3,
            "Trousers_Padded", 0.3,
            "Trousers_WhiteTINT", 2,
            "Shorts_CamoGreenLong", 0.7,
            "Trousers_JeanBaggy", 2,
            "Tshirt_DefaultTEXTURE_TINT",2.5,
            "Tshirt_PoloTINT",0.7,
            "Tshirt_WhiteLongSleeveTINT",1.5,
            "Tshirt_WhiteTINT", 2,
            "Tshirt_Rock", 1,
            "Shirt_Lumberjack", 2,
            "Shirt_Denim", 3,
            "Vest_DefaultTEXTURE_TINT", 3,
            "JacketLong_Random", 0.3,
            "Jacket_Black", 0.3,
            "Jacket_Padded", 0.3,
            "Jacket_WhiteTINT", 0.5,
            "Jumper_RoundNeck", 0.5,
            "Jumper_TankTopTINT", 0.7,
            "HoodieDOWN_WhiteTINT", 0.7,
            "Hat_Bandana", 0.6,
            "Hat_BaseballCap", 0.6,
            "Hat_BaseballCapKY", 0.6,
            "Hat_Beany", 0.6,
            "Gloves_FingerlessGloves", 0.7,
            "Gloves_WhiteTINT", 0.1,
            "SwimTrunks_Blue", 0.1,
            "SwimTrunks_Green", 0.1,
            "SwimTrunks_Red", 0.1,
            "SwimTrunks_Yellow", 0.1,
            "Bikini_Pattern01", 0.2,
            "Bikini_TINT", 0.2,
            "Swimsuit_TINT", 0.2,
            "BaseballBat", 0.1,
            "Trumpet", 0.02,
            "Banjo", 0.03,
            "GuitarAcoustic", 0.03,
            "GuitarElectricBlack", 0.03,
            "GuitarElectricBlue", 0.03,
            "GuitarElectricRed", 0.03,
            "GuitarElectricBassBlue", 0.03,
            "GuitarElectricBassBlack", 0.03,
            "GuitarElectricBassRed", 0.03,
            "Flightcase", 0.05,
            "Guitarcase", 0.05,
            "Keytar", 0.02,
            "HockeyStick", 0.04,
            "Shotgun", 0.3,
            "DoubleBarrelShotgun", 0.5,
        },
        junk = {
            rolls = 2,
            items = {
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Pillow", 9,
                "Belt2", 5,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
            }
        }
    },
    
    WardrobeWoman = {
        rolls = 2,
        items = {
            "PistolCase1", 0.3,
            "PistolCase2", 0.2,
            "Bag_Satchel", 0.3,
            "Hat_BucketHat", 0.6,
            "Shoes_FlipFlop", 0.6,
            "Bag_FannyPackFront", 0.8,
            "Shoes_Random", 1.5,
            "Shoes_TrainerTINT", 1.5,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "Bag_DuffelBagTINT", 0.8,
            "LongJohns_Bottoms", 1,
            "LongJohns", 1,
            "Shorts_LongDenim", 1,
            "Trousers", 1.8,
            "TrousersMesh_DenimLight", 1.3,
            "Trousers_DefaultTEXTURE", 1.3,
            "Trousers_DefaultTEXTURE_HUE", 1.3,
            "Trousers_DefaultTEXTURE_TINT", 1.3,
            "Trousers_Denim", 1.3,
            "Trousers_Padded", 0.3,
            "Trousers_WhiteTINT", 1.8,
            "Skirt_Knees", 2,
            "Skirt_Long", 2,
            "Skirt_Normal", 2,
            "Skirt_Short", 2,
            "Dress_Knees", 1,
            "Dress_Long", 1,
            "Dress_Normal", 1,
            "Dress_Short", 1,
            "Shirt_FormalWhite", 1,
            "Shirt_FormalTINT", 1,
            "Shirt_FormalWhite_ShortSleeve", 1,
            "Shirt_FormalWhite_ShortSleeveTINT", 1,
            "Tshirt_DefaultTEXTURE_TINT",2.5,
            "Tshirt_PoloTINT",0.7,
            "Tshirt_WhiteLongSleeveTINT",1.5,
            "Tshirt_WhiteTINT", 2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "JacketLong_Random", 0.3,
            "Jacket_Black", 0.3,
            "Jacket_Padded", 0.3,
            "Jacket_WhiteTINT", 0.5,
            "Jumper_DiamondPatternTINT", 0.3,
            "Jumper_PoloNeck", 0.5,
            "Jumper_RoundNeck", 0.5,
            "Jumper_TankTopTINT", 0.3,
            "Jumper_VNeck", 0.5,
            "Hat_EarMuffs", 0.1,
            "Hat_SummerHat", 0.2,
            "Hat_WoolyHat", 0.1,
            "Gloves_WhiteTINT", 0.1,
            "Gloves_LongWomenGloves", 0.1,
            "Gloves_FingerlessGloves", 0.6,
            "Gloves_LeatherGloves", 0.05,
            "Gloves_LeatherGlovesBlack", 0.05,
            "BaseballBat", 0.08,
            "Bikini_Pattern01", 0.2,
            "Bikini_TINT", 0.2,
            "Swimsuit_TINT", 0.2,
            "Scarf_White", 0.02,
            "Scarf_StripeBlackWhite", 0.02,
            "Scarf_StripeBlueWhite", 0.02,
            "Scarf_StripeRedWhite", 0.02,
            "Saxophone", 0.02,
            "Trumpet", 0.02,
            "GuitarAcoustic", 0.03,
            "GuitarElectricBlack", 0.03,
            "GuitarElectricBlue", 0.03,
            "GuitarElectricRed", 0.03,
            "GuitarElectricBassBlue", 0.03,
            "GuitarElectricBassBlack", 0.03,
            "GuitarElectricBassRed", 0.03,
            "Flightcase", 0.05,
            "Guitarcase", 0.05,
            "Keytar", 0.02,
            "Suitcase", 0.3,
            "Briefcase", 0.3,
        },
        junk = {
            rolls = 2,
            items = {
                "Purse", 2,
                "Handbag", 2,
                "ClosedUmbrellaRed", 0.3,
                "ClosedUmbrellaBlue", 0.3,
                "ClosedUmbrellaBlack", 0.3,
                "ClosedUmbrellaWhite", 0.3,
                "AlarmClock2", 2,
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Pillow", 9,
                "Belt2", 5,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
            }
        }
    },
    
    WardrobeWomanClassy = {
        rolls = 2,
        items = {
            "PistolCase1", 0.3,
            "PistolCase2", 0.2,
            "Bag_Satchel", 0.3,
            "Shoes_Random", 2.2,
            "Bag_NormalHikingBag", 0.8,
            "Bag_BigHikingBag", 0.5,
            "Bag_DuffelBagTINT", 0.8,
            "Dress_Knees", 1,
            "Dress_Long", 1,
            "Dress_Normal", 1,
            "Skirt_Knees", 1,
            "Skirt_Long", 1,
            "Skirt_Normal", 1,
            "Suit_Jacket", 0.5,
            "Suit_JacketTINT", 0.5,
            "Trousers", 2,
            "TrousersMesh_DenimLight", 1,
            "Trousers_DefaultTEXTURE", 1.5,
            "Trousers_DefaultTEXTURE_HUE", 1.5,
            "Trousers_DefaultTEXTURE_TINT", 1.5,
            "Trousers_Padded", 0.3,
            "Trousers_Suit", 0.8,
            "Trousers_SuitTEXTURE", 0.8,
            "Trousers_SuitWhite", 1,
            "Trousers_WhiteTINT", 2,
            "Shirt_FormalWhite", 3,
            "Shirt_FormalTINT", 3,
            "Shirt_FormalWhite_ShortSleeve", 3,
            "Shirt_FormalWhite_ShortSleeveTINT", 3,
            "Tshirt_DefaultTEXTURE_TINT",1.5,
            "Tshirt_PoloTINT",1.7,
            "Tshirt_WhiteLongSleeveTINT",1,
            "Tshirt_WhiteTINT", 1.2,
            "Vest_DefaultTEXTURE_TINT", 1,
            "Jacket_Padded", 0.3,
            "Jumper_DiamondPatternTINT", 1,
            "Jumper_PoloNeck", 1,
            "Jumper_RoundNeck", 1,
            "Jumper_TankTopTINT", 1,
            "Jumper_VNeck", 1,
            "Hat_SummerHat", 0.3,
            "Hat_Beret", 0.3,
            "Gloves_LeatherGloves", 0.2,
            "Gloves_LongWomenGloves", 0.2,
            "BaseballBat", 0.05,
            "Bikini_Pattern01", 0.2,
            "Bikini_TINT", 0.2,
            "Swimsuit_TINT", 0.2,
            "Scarf_White", 0.02,
            "Scarf_StripeBlackWhite", 0.02,
            "Scarf_StripeBlueWhite", 0.02,
            "Scarf_StripeRedWhite", 0.02,
            "Saxophone", 0.03,
            "Violin", 0.04,
            "Golfclub", 0.05,
            "Bag_GolfBag", 0.04,
            "Bag_MoneyBag", 0.01,
            "Suitcase", 0.3,
            "Briefcase", 0.3,
        },
        junk = {
            rolls = 2,
            items = {
                "ClosedUmbrellaRed", 0.3,
                "ClosedUmbrellaBlue", 0.3,
                "ClosedUmbrellaBlack", 0.3,
                "ClosedUmbrellaWhite", 0.3,
                "AlarmClock2", 2,
                "Disc", 0.5,
                "CDplayer", 0.5,
                "Earbuds", 0.5,
                "Headphones", 0.5,
                "VideoGame", 0.5,
                "Pillow", 9,
                "Belt2", 5,
                "Socks_Ankle", 4,
                "Socks_Ankle", 4,
                "Socks_Long", 2,
            }
        }
    },
}
--
--    all = clothingStores;
--
--    clothesstore = clothingStores;


--table.insert(ProceduralDistributions.list, distributionTable);

--for mod compat:
--ProceduralDistributions = distributionTable;
