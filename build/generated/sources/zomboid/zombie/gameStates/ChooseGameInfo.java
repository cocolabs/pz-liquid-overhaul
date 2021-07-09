package zombie.gameStates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import zombie.ZomboidFileSystem;
import zombie.core.Core;
import zombie.core.GameVersion;
import zombie.core.IndieFileLoader;
import zombie.core.Language;
import zombie.core.Translator;
import zombie.core.logger.ExceptionLogger;
import zombie.core.textures.Texture;
import zombie.core.znet.SteamWorkshop;
import zombie.debug.DebugLog;
import zombie.util.StringUtils;

public final class ChooseGameInfo {
   private static final HashMap Maps = new HashMap();
   private static final HashMap Mods = new HashMap();
   private static final HashSet MissingMods = new HashSet();
   private static final ArrayList tempStrings = new ArrayList();

   private ChooseGameInfo() {
   }

   public static void Reset() {
      Maps.clear();
      Mods.clear();
      MissingMods.clear();
   }

   private static void readTitleDotTxt(ChooseGameInfo.Map var0, String var1, Language var2) throws IOException {
      String var3 = "media/lua/shared/Translate/" + var2.toString() + "/" + var1 + "/title.txt";
      File var4 = new File(ZomboidFileSystem.instance.getString(var3));

      try {
         FileInputStream var5 = new FileInputStream(var4);
         Throwable var6 = null;

         try {
            InputStreamReader var7 = new InputStreamReader(var5, Charset.forName(var2.charset()));
            Throwable var8 = null;

            try {
               BufferedReader var9 = new BufferedReader(var7);
               Throwable var10 = null;

               try {
                  String var11 = var9.readLine();
                  var11 = StringUtils.stripBOM(var11);
                  if (!StringUtils.isNullOrWhitespace(var11)) {
                     var0.title = var11.trim();
                  }
               } catch (Throwable var57) {
                  var10 = var57;
                  throw var57;
               } finally {
                  if (var9 != null) {
                     if (var10 != null) {
                        try {
                           var9.close();
                        } catch (Throwable var56) {
                           var10.addSuppressed(var56);
                        }
                     } else {
                        var9.close();
                     }
                  }

               }
            } catch (Throwable var59) {
               var8 = var59;
               throw var59;
            } finally {
               if (var7 != null) {
                  if (var8 != null) {
                     try {
                        var7.close();
                     } catch (Throwable var55) {
                        var8.addSuppressed(var55);
                     }
                  } else {
                     var7.close();
                  }
               }

            }
         } catch (Throwable var61) {
            var6 = var61;
            throw var61;
         } finally {
            if (var5 != null) {
               if (var6 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var54) {
                     var6.addSuppressed(var54);
                  }
               } else {
                  var5.close();
               }
            }

         }
      } catch (FileNotFoundException var63) {
      }

   }

   private static void readDescriptionDotTxt(ChooseGameInfo.Map var0, String var1, Language var2) throws IOException {
      String var3 = "media/lua/shared/Translate/" + var2.toString() + "/" + var1 + "/description.txt";
      File var4 = new File(ZomboidFileSystem.instance.getString(var3));

      try {
         FileInputStream var5 = new FileInputStream(var4);
         Throwable var6 = null;

         try {
            InputStreamReader var7 = new InputStreamReader(var5, Charset.forName(var2.charset()));
            Throwable var8 = null;

            try {
               BufferedReader var9 = new BufferedReader(var7);
               Throwable var10 = null;

               try {
                  var0.desc = "";

                  String var11;
                  for(boolean var12 = true; (var11 = var9.readLine()) != null; var0.desc = var0.desc + var11) {
                     if (var12) {
                        var11 = StringUtils.stripBOM(var11);
                        var12 = false;
                     }
                  }
               } catch (Throwable var59) {
                  var10 = var59;
                  throw var59;
               } finally {
                  if (var9 != null) {
                     if (var10 != null) {
                        try {
                           var9.close();
                        } catch (Throwable var58) {
                           var10.addSuppressed(var58);
                        }
                     } else {
                        var9.close();
                     }
                  }

               }
            } catch (Throwable var61) {
               var8 = var61;
               throw var61;
            } finally {
               if (var7 != null) {
                  if (var8 != null) {
                     try {
                        var7.close();
                     } catch (Throwable var57) {
                        var8.addSuppressed(var57);
                     }
                  } else {
                     var7.close();
                  }
               }

            }
         } catch (Throwable var63) {
            var6 = var63;
            throw var63;
         } finally {
            if (var5 != null) {
               if (var6 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var56) {
                     var6.addSuppressed(var56);
                  }
               } else {
                  var5.close();
               }
            }

         }
      } catch (FileNotFoundException var65) {
      }

   }

   public static ChooseGameInfo.Map getMapDetails(String var0) {
      if (Maps.containsKey(var0)) {
         return (ChooseGameInfo.Map)Maps.get(var0);
      } else {
         File var1 = new File(ZomboidFileSystem.instance.getString("media/maps/" + var0 + "/map.info"));
         if (!var1.exists()) {
            return null;
         } else {
            ChooseGameInfo.Map var2 = new ChooseGameInfo.Map();
            var2.dir = (new File(var1.getParent())).getAbsolutePath();
            var2.title = var0;
            var2.lotsDir = new ArrayList();

            try {
               FileReader var3 = new FileReader(var1.getAbsolutePath());
               BufferedReader var4 = new BufferedReader(var3);
               String var5 = null;

               try {
                  while((var5 = var4.readLine()) != null) {
                     var5 = var5.trim();
                     if (var5.startsWith("title=")) {
                        var2.title = var5.replace("title=", "");
                     } else if (var5.startsWith("lots=")) {
                        var2.lotsDir.add(var5.replace("lots=", "").trim());
                     } else if (var5.startsWith("description=")) {
                        if (var2.desc == null) {
                           var2.desc = "";
                        }

                        var2.desc = var2.desc + var5.replace("description=", "");
                     } else if (var5.startsWith("fixed2x=")) {
                        var2.bFixed2x = Boolean.parseBoolean(var5.replace("fixed2x=", "").trim());
                     }
                  }
               } catch (IOException var9) {
                  Logger.getLogger(ChooseGameInfo.class.getName()).log(Level.SEVERE, (String)null, var9);
               }

               var4.close();
               var2.thumb = Texture.getSharedTexture(var2.dir + "/thumb.png");
               ArrayList var6 = new ArrayList();
               Translator.addLanguageToList(Translator.getLanguage(), var6);
               Translator.addLanguageToList(Translator.getDefaultLanguage(), var6);

               for(int var7 = var6.size() - 1; var7 >= 0; --var7) {
                  Language var8 = (Language)var6.get(var7);
                  readTitleDotTxt(var2, var0, var8);
                  readDescriptionDotTxt(var2, var0, var8);
               }
            } catch (Exception var10) {
               ExceptionLogger.logException(var10);
               return null;
            }

            Maps.put(var0, var2);
            return var2;
         }
      }
   }

   public static ChooseGameInfo.Mod getModDetails(String var0) {
      if (MissingMods.contains(var0)) {
         return null;
      } else if (Mods.containsKey(var0)) {
         return (ChooseGameInfo.Mod)Mods.get(var0);
      } else {
         String var1 = ZomboidFileSystem.instance.getModDir(var0);
         if (var1 == null) {
            ArrayList var2 = tempStrings;
            ZomboidFileSystem.instance.getAllModFolders(var2);
            ArrayList var3 = new ArrayList();

            for(int var4 = 0; var4 < var2.size(); ++var4) {
               File var5 = new File((String)var2.get(var4), "mod.info");
               var3.clear();
               ChooseGameInfo.Mod var6 = ZomboidFileSystem.instance.searchForModInfo(var5, var0, var3);

               for(int var7 = 0; var7 < var3.size(); ++var7) {
                  ChooseGameInfo.Mod var8 = (ChooseGameInfo.Mod)var3.get(var7);
                  Mods.putIfAbsent(var8.getId(), var8);
               }

               if (var6 != null) {
                  return var6;
               }
            }
         }

         ChooseGameInfo.Mod var9 = readModInfo(var1);
         if (var9 == null) {
            MissingMods.add(var0);
         }

         return var9;
      }
   }

   public static ChooseGameInfo.Mod getAvailableModDetails(String var0) {
      ChooseGameInfo.Mod var1 = getModDetails(var0);
      return var1 != null && var1.isAvailable() ? var1 : null;
   }

   public static ChooseGameInfo.Mod readModInfo(String var0) {
      ChooseGameInfo.Mod var1 = readModInfoAux(var0);
      if (var1 != null) {
         ChooseGameInfo.Mod var2 = (ChooseGameInfo.Mod)Mods.get(var1.getId());
         if (var2 == null) {
            Mods.put(var1.getId(), var1);
         } else if (var2 != var1) {
            ZomboidFileSystem.instance.getAllModFolders(tempStrings);
            int var3 = tempStrings.indexOf(var1.getDir());
            int var4 = tempStrings.indexOf(var2.getDir());
            if (var3 < var4) {
               Mods.put(var1.getId(), var1);
            }
         }
      }

      return var1;
   }

   private static ChooseGameInfo.Mod readModInfoAux(String var0) {
      if (var0 != null) {
         ChooseGameInfo.Mod var1 = ZomboidFileSystem.instance.getModInfoForDir(var0);
         if (var1.bRead) {
            return var1.bValid ? var1 : null;
         }

         var1.bRead = true;
         String var2 = var0 + File.separator + "mod.info";
         File var3 = new File(var2);
         if (!var3.exists()) {
            DebugLog.Mod.warn("can't find \"" + var2 + "\"");
            return null;
         }

         var1.setId(var3.getParentFile().getName());

         try {
            InputStreamReader var4 = IndieFileLoader.getStreamReader(var2);
            Throwable var5 = null;

            try {
               BufferedReader var6 = new BufferedReader(var4);
               Throwable var7 = null;

               try {
                  Object var67;
                  String var8;
                  while((var8 = var6.readLine()) != null) {
                     if (var8.contains("name=")) {
                        var1.name = var8.replace("name=", "");
                     } else if (var8.contains("poster=")) {
                        String var9 = var8.replace("poster=", "");
                        if (!StringUtils.isNullOrWhitespace(var9)) {
                           var1.posters.add(var0 + File.separator + var9);
                        }
                     } else if (var8.contains("description=")) {
                        var1.desc = var1.desc + var8.replace("description=", "");
                     } else if (var8.contains("require=")) {
                        var1.setRequire(new ArrayList(Arrays.asList(var8.replace("require=", "").split(","))));
                     } else if (var8.contains("id=")) {
                        var1.setId(var8.replace("id=", ""));
                     } else if (var8.contains("url=")) {
                        var1.setUrl(var8.replace("url=", ""));
                     } else {
                        Object var10;
                        if (var8.contains("pack=")) {
                           var67 = var8.replace("pack=", "").trim();
                           if (((String)var67).isEmpty()) {
                              DebugLog.Mod.error("pack= line requires a file name");
                              var10 = null;
                              return (ChooseGameInfo.Mod)var10;
                           }

                           if (Core.TileScale == 2) {
                              var10 = new File(var0 + File.separator + "media" + File.separator + "texturepacks" + File.separator + var67 + "2x.pack");
                              if (((File)var10).isFile()) {
                                 DebugLog.Mod.printf("2x version of %s.pack found.\n", var67);
                                 var67 = var67 + "2x";
                              } else {
                                 DebugLog.Mod.printf("2x version of %s.pack not found.\n", var67);
                              }
                           }

                           var1.addPack((String)var67);
                        } else {
                           Object var11;
                           if (var8.contains("tiledef=")) {
                              var67 = var8.replace("tiledef=", "").trim().split("\\s+");
                              if (((Object[])var67).length != 2) {
                                 DebugLog.Mod.error("tiledef= line requires file name and file number");
                                 var10 = null;
                                 return (ChooseGameInfo.Mod)var10;
                              }

                              var10 = ((Object[])var67)[0];

                              try {
                                 var11 = Integer.parseInt((String)((Object[])var67)[1]);
                              } catch (NumberFormatException var61) {
                                 DebugLog.Mod.error("tiledef= line requires file name and file number");
                                 Object var13 = null;
                                 return (ChooseGameInfo.Mod)var13;
                              }

                              if (!(var11 >= 100) || var11 > 1000) {
                                 DebugLog.Mod.error("tiledef=%s %d file number must be from 100 to 1000", var10, Integer.valueOf((int)var11));
                              }

                              var1.addTileDef((String)var10, (int)var11);
                           } else if (var8.startsWith("versionMax=")) {
                              var67 = var8.replace("versionMax=", "").trim();
                              if (!((String)var67).isEmpty()) {
                                 try {
                                    var1.versionMax = GameVersion.parse((String)var67);
                                 } catch (Exception var59) {
                                    var10 = var59;
                                    DebugLog.Mod.error("invalid versionMax: " + var59.getMessage());
                                    var11 = null;
                                    return (ChooseGameInfo.Mod)var11;
                                 }
                              }
                           } else if (var8.startsWith("versionMin=")) {
                              var67 = var8.replace("versionMin=", "").trim();
                              if (!((String)var67).isEmpty()) {
                                 try {
                                    var1.versionMin = GameVersion.parse((String)var67);
                                 } catch (Exception var60) {
                                    var10 = var60;
                                    DebugLog.Mod.error("invalid versionMin: " + var60.getMessage());
                                    var11 = null;
                                    return (ChooseGameInfo.Mod)var11;
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (var1.getUrl() == null) {
                     var1.setUrl("");
                  }

                  var1.bValid = true;
                  var67 = var1;
                  return (ChooseGameInfo.Mod)var67;
               } catch (Throwable var62) {
                  var7 = var62;
                  throw var62;
               } finally {
                  if (var6 != null) {
                     if (var7 != null) {
                        try {
                           var6.close();
                        } catch (Throwable var58) {
                           var7.addSuppressed(var58);
                        }
                     } else {
                        var6.close();
                     }
                  }

               }
            } catch (Throwable var64) {
               var5 = var64;
               throw var64;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var57) {
                        var5.addSuppressed(var57);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Exception var66) {
            ExceptionLogger.logException(var66);
         }
      }

      return null;
   }

   public static final class Mod {
      public String dir;
      private final ArrayList posters = new ArrayList();
      public Texture tex;
      private ArrayList require;
      private String name = "Unnamed Mod";
      private String desc = "";
      private String id;
      private String url;
      private String workshopID;
      private boolean bAvailableDone = false;
      private boolean available = true;
      private GameVersion versionMin;
      private GameVersion versionMax;
      private final ArrayList packs = new ArrayList();
      private final ArrayList tileDefs = new ArrayList();
      private boolean bRead = false;
      private boolean bValid = false;

      public Mod(String var1) {
         this.dir = var1;
         File var2 = new File(var1);
         var2 = var2.getParentFile();
         if (var2 != null) {
            var2 = var2.getParentFile();
            if (var2 != null) {
               this.workshopID = SteamWorkshop.instance.getIDFromItemInstallFolder(var2.getAbsolutePath());
            }
         }

      }

      public Texture getTexture() {
         if (this.tex == null) {
            String var1 = this.posters.isEmpty() ? null : (String)this.posters.get(0);
            if (!StringUtils.isNullOrWhitespace(var1)) {
               this.tex = Texture.getSharedTexture(var1);
            }

            if (this.tex == null || this.tex.isFailure()) {
               if (Core.bDebug && this.tex == null) {
                  DebugLog.Mod.println("failed to load poster " + (var1 == null ? this.id : var1));
               }

               this.tex = Texture.getWhite();
            }
         }

         return this.tex;
      }

      public void setTexture(Texture var1) {
         this.tex = var1;
      }

      public int getPosterCount() {
         return this.posters.size();
      }

      public String getPoster(int var1) {
         return var1 >= 0 && var1 < this.posters.size() ? (String)this.posters.get(var1) : null;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String var1) {
         this.name = var1;
      }

      public String getDir() {
         return this.dir;
      }

      public String getDescription() {
         return this.desc;
      }

      public ArrayList getRequire() {
         return this.require;
      }

      public void setRequire(ArrayList var1) {
         this.require = var1;
      }

      public String getId() {
         return this.id;
      }

      public void setId(String var1) {
         this.id = var1;
      }

      public boolean isAvailable() {
         if (this.bAvailableDone) {
            return this.available;
         } else {
            this.bAvailableDone = true;
            if (!this.isAvailableSelf()) {
               this.available = false;
               return false;
            } else {
               ChooseGameInfo.tempStrings.clear();
               ChooseGameInfo.tempStrings.add(this.getId());
               if (!this.isAvailableRequired(ChooseGameInfo.tempStrings)) {
                  this.available = false;
                  return false;
               } else {
                  this.available = true;
                  return true;
               }
            }
         }
      }

      private boolean isAvailableSelf() {
         GameVersion var1 = Core.getInstance().getGameVersion();
         if (this.versionMin != null && this.versionMin.isGreaterThan(var1)) {
            return false;
         } else {
            return this.versionMax == null || !this.versionMax.isLessThan(var1);
         }
      }

      private boolean isAvailableRequired(ArrayList var1) {
         if (this.require != null && !this.require.isEmpty()) {
            for(int var2 = 0; var2 < this.require.size(); ++var2) {
               String var3 = ((String)this.require.get(var2)).trim();
               if (!var1.contains(var3)) {
                  var1.add(var3);
                  ChooseGameInfo.Mod var4 = ChooseGameInfo.getModDetails(var3);
                  if (var4 == null) {
                     return false;
                  }

                  if (!var4.isAvailableSelf()) {
                     return false;
                  }

                  if (!var4.isAvailableRequired(var1)) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return true;
         }
      }

      /** @deprecated */
      @Deprecated
      public void setAvailable(boolean var1) {
      }

      public String getUrl() {
         return this.url == null ? "" : this.url;
      }

      public void setUrl(String var1) {
         if (var1.startsWith("http://theindiestone.com") || var1.startsWith("http://www.theindiestone.com") || var1.startsWith("http://pz-mods.net") || var1.startsWith("http://www.pz-mods.net")) {
            this.url = var1;
         }

      }

      public GameVersion getVersionMin() {
         return this.versionMin;
      }

      public GameVersion getVersionMax() {
         return this.versionMax;
      }

      public void addPack(String var1) {
         this.packs.add(var1);
      }

      public void addTileDef(String var1, int var2) {
         this.tileDefs.add(new ChooseGameInfo.TileDef(var1, var2));
      }

      public ArrayList getPacks() {
         return this.packs;
      }

      public ArrayList getTileDefs() {
         return this.tileDefs;
      }

      public String getWorkshopID() {
         return this.workshopID;
      }
   }

   public static final class TileDef {
      public String name;
      public int fileNumber;

      public TileDef(String var1, int var2) {
         this.name = var1;
         this.fileNumber = var2;
      }
   }

   public static final class Map {
      private String dir;
      private Texture thumb;
      private String title;
      private ArrayList lotsDir;
      private String desc;
      private boolean bFixed2x;

      public String getDirectory() {
         return this.dir;
      }

      public void setDirectory(String var1) {
         this.dir = var1;
      }

      public Texture getThumbnail() {
         return this.thumb;
      }

      public void setThumbnail(Texture var1) {
         this.thumb = var1;
      }

      public String getTitle() {
         return this.title;
      }

      public void setTitle(String var1) {
         this.title = var1;
      }

      public ArrayList getLotDirectories() {
         return this.lotsDir;
      }

      public String getDescription() {
         return this.desc;
      }

      public void setDescription(String var1) {
         this.desc = var1;
      }

      public boolean isFixed2x() {
         return this.bFixed2x;
      }

      public void setFixed2x(boolean var1) {
         this.bFixed2x = var1;
      }
   }
}
