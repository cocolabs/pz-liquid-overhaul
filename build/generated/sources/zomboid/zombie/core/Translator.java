package zombie.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import se.krka.kahlua.vm.KahluaTable;
import zombie.ZomboidFileSystem;
import zombie.Lua.LuaManager;
import zombie.characters.skills.PerkFactory;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.scripting.ScriptManager;
import zombie.scripting.objects.Item;
import zombie.util.StringUtils;

public final class Translator {
   private static ArrayList availableLanguage = null;
   public static boolean debug = false;
   private static FileWriter debugFile = null;
   private static boolean debugErrors = false;
   private static final HashSet debugItemEvolvedRecipeName = new HashSet();
   private static final HashSet debugItem = new HashSet();
   private static final HashSet debugMultiStageBuild = new HashSet();
   private static final HashSet debugRecipe = new HashSet();
   private static final HashMap moodles = new HashMap();
   private static final HashMap ui = new HashMap();
   private static final HashMap survivalGuide = new HashMap();
   private static final HashMap contextMenu = new HashMap();
   private static final HashMap farming = new HashMap();
   private static final HashMap recipe = new HashMap();
   private static final HashMap igui = new HashMap();
   private static final HashMap sandbox = new HashMap();
   private static final HashMap tooltip = new HashMap();
   private static final HashMap challenge = new HashMap();
   private static final HashSet missing = new HashSet();
   private static ArrayList azertyLanguages = null;
   private static final HashMap news = new HashMap();
   private static final HashMap stash = new HashMap();
   private static final HashMap multiStageBuild = new HashMap();
   private static final HashMap moveables = new HashMap();
   private static final HashMap makeup = new HashMap();
   private static final HashMap gameSound = new HashMap();
   private static final HashMap dynamicRadio = new HashMap();
   private static final HashMap items = new HashMap();
   private static final HashMap itemName = new HashMap();
   private static final HashMap itemEvolvedRecipeName = new HashMap();
   public static Language language = null;
   private static String newsHeader = "<IMAGE:media/ui/dot.png> <SIZE:small> ";

   public static void loadFiles() {
      language = null;
      availableLanguage = null;
      File var0 = new File(ZomboidFileSystem.instance.getCacheDir() + File.separator + "translationProblems.txt");
      if (debug) {
         try {
            if (debugFile != null) {
               debugFile.close();
            }

            debugFile = new FileWriter(var0);
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

      moodles.clear();
      ui.clear();
      survivalGuide.clear();
      items.clear();
      itemName.clear();
      contextMenu.clear();
      farming.clear();
      recipe.clear();
      igui.clear();
      sandbox.clear();
      tooltip.clear();
      challenge.clear();
      news.clear();
      missing.clear();
      stash.clear();
      multiStageBuild.clear();
      moveables.clear();
      makeup.clear();
      gameSound.clear();
      dynamicRadio.clear();
      itemEvolvedRecipeName.clear();
      DebugLog.log("translator: language is " + getLanguage());
      debugErrors = false;
      fillMapFromFile("Tooltip", tooltip);
      fillMapFromFile("IG_UI", igui);
      fillMapFromFile("Recipes", recipe);
      fillMapFromFile("Farming", farming);
      fillMapFromFile("ContextMenu", contextMenu);
      fillMapFromFile("SurvivalGuide", survivalGuide);
      fillMapFromFile("UI", ui);
      fillMapFromFile("Items", items);
      fillMapFromFile("ItemName", itemName);
      fillMapFromFile("Moodles", moodles);
      fillMapFromFile("Sandbox", sandbox);
      fillMapFromFile("Challenge", challenge);
      fillMapFromFile("Stash", stash);
      fillMapFromFile("MultiStageBuild", multiStageBuild);
      fillMapFromFile("Moveables", moveables);
      fillMapFromFile("MakeUp", makeup);
      fillMapFromFile("GameSound", gameSound);
      fillMapFromFile("DynamicRadio", dynamicRadio);
      fillMapFromFile("EvolvedRecipeName", itemEvolvedRecipeName);
      fillNewsFromFile(news);
      if (debug) {
         if (debugErrors) {
            DebugLog.log("translator: errors detected, please see " + var0.getAbsolutePath());
         }

         debugItemEvolvedRecipeName.clear();
         debugItem.clear();
         debugMultiStageBuild.clear();
         debugRecipe.clear();
      }

      PerkFactory.initTranslations();
   }

   private static void fillNewsFromFile(HashMap var0) {
      HashMap var1 = new HashMap();
      ArrayList var2 = ZomboidFileSystem.instance.getModIDs();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = ZomboidFileSystem.instance.getModDir((String)var2.get(var3));
         if (var4 != null) {
            tryFillNewsFromFile(var4, var0, var1, getLanguage());
            if (getLanguage() != getDefaultLanguage()) {
               tryFillNewsFromFile(var4, var0, var1, getDefaultLanguage());
            }
         }
      }

      tryFillNewsFromFile(".", var0, var1, getLanguage());
      if (getLanguage() != getDefaultLanguage()) {
         tryFillNewsFromFile(".", var0, var1, getDefaultLanguage());
      }

      Iterator var5 = var1.values().iterator();

      while(var5.hasNext()) {
         Translator.News var6 = (Translator.News)var5.next();
         var0.put("News_" + var6.version + "_Disclaimer", var6.toRichText());
      }

      var1.clear();
   }

   private static void tryFillNewsFromFile(String var0, HashMap var1, HashMap var2, Language var3) {
      File var4 = new File(var0 + File.separator + "media" + File.separator + "lua" + File.separator + "shared" + File.separator + "Translate" + File.separator + var3 + File.separator + "News_" + var3 + ".txt");
      if (var4.exists()) {
         doNews(var4, var2, var3);
      }

   }

   private static void doNews(File var0, HashMap var1, Language var2) {
      try {
         FileInputStream var3 = new FileInputStream(var0);
         Throwable var4 = null;

         try {
            InputStreamReader var5 = new InputStreamReader(var3, Charset.forName(var2.charset()));
            Throwable var6 = null;

            try {
               BufferedReader var7 = new BufferedReader(var5);
               Throwable var8 = null;

               try {
                  Translator.News var9 = null;
                  String var10 = "";

                  String var11;
                  while((var11 = var7.readLine()) != null) {
                     if (!var11.trim().isEmpty()) {
                        if (var11.startsWith("[VERSION]")) {
                           String var12 = var11.replaceFirst("\\[VERSION\\]", "").trim();
                           if (var1.containsKey(var12)) {
                              var9 = null;
                           } else {
                              var1.put(var12, var9 = new Translator.News(var12));
                           }
                        } else if (var11.startsWith("[NEWS]")) {
                           var10 = "news";
                        } else if (var11.startsWith("[BALANCE]")) {
                           var10 = "balance";
                        } else if (var11.startsWith("[BUG FIX]")) {
                           var10 = "bugfix";
                        } else if (var9 != null) {
                           if ("news".equals(var10)) {
                              addNewsLine(var11, var9.newsList);
                           } else if ("balance".equals(var10)) {
                              addNewsLine(var11, var9.balance);
                           } else if ("bugfix".equals(var10)) {
                              addNewsLine(var11, var9.bugfix);
                           } else {
                              addNewsLine(var11, var9.other);
                           }
                        }
                     }
                  }
               } catch (Throwable var58) {
                  var8 = var58;
                  throw var58;
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
            } catch (Throwable var60) {
               var6 = var60;
               throw var60;
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
         } catch (Throwable var62) {
            var4 = var62;
            throw var62;
         } finally {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var55) {
                     var4.addSuppressed(var55);
                  }
               } else {
                  var3.close();
               }
            }

         }
      } catch (Exception var64) {
         var64.printStackTrace();
      }

   }

   private static void addNewsLine(String var0, ArrayList var1) {
      if (var0.startsWith("[BOLD]")) {
         var0 = var0.replaceFirst("\\[BOLD\\]", "<IMAGE:media/ui/dot.png> <SIZE:medium>");
         var1.add(var0 + " <LINE> ");
      } else if (var0.startsWith("[DOT2]")) {
         var0 = var0.replaceFirst("\\[DOT2\\]", "<IMAGE:media/ui/dot2.png> <SIZE:small>");
         var1.add(var0 + " <LINE> ");
      } else {
         var1.add(newsHeader + var0 + " <LINE> ");
      }
   }

   public static ArrayList getNewsVersions() {
      ArrayList var0 = new ArrayList();
      var0.addAll(news.keySet());

      for(int var1 = 0; var1 < var0.size(); ++var1) {
         String var2 = (String)var0.get(var1);
         var2 = var2.replace("News_", "");
         var2 = var2.replace("_Disclaimer", "");
         var0.set(var1, var2);
      }

      Collections.sort(var0);
      return var0;
   }

   private static void tryFillMapFromFile(String var0, String var1, HashMap var2, Language var3) {
      File var4 = new File(var0 + File.separator + "media" + File.separator + "lua" + File.separator + "shared" + File.separator + "Translate" + File.separator + var3 + File.separator + var1 + "_" + var3 + ".txt");
      if (var4.exists()) {
         parseFile(var4, var2, var3);
      }

   }

   private static void tryFillMapFromMods(String var0, HashMap var1, Language var2) {
      ArrayList var3 = ZomboidFileSystem.instance.getModIDs();

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         String var5 = ZomboidFileSystem.instance.getModDir((String)var3.get(var4));
         if (var5 != null) {
            tryFillMapFromFile(var5, var0, var1, var2);
         }
      }

   }

   public static void addLanguageToList(Language var0, ArrayList var1) {
      if (var0 != null) {
         if (!var1.contains(var0)) {
            var1.add(var0);
            if (var0.base() != null) {
               var0 = Languages.instance.getByName(var0.base());
               addLanguageToList(var0, var1);
            }
         }
      }
   }

   private static void fillMapFromFile(String var0, HashMap var1) {
      ArrayList var2 = new ArrayList();
      addLanguageToList(getLanguage(), var2);
      addLanguageToList(getDefaultLanguage(), var2);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         Language var4 = (Language)var2.get(var3);
         tryFillMapFromMods(var0, var1, var4);
         tryFillMapFromFile(".", var0, var1, var4);
      }

      var2.clear();
   }

   private static void parseFile(File var0, HashMap var1, Language var2) {
      String var3 = null;

      try {
         FileInputStream var4 = new FileInputStream(var0);
         Throwable var5 = null;

         try {
            InputStreamReader var6 = new InputStreamReader(var4, Charset.forName(var2.charset()));
            Throwable var7 = null;

            try {
               BufferedReader var8 = new BufferedReader(var6);
               Throwable var9 = null;

               try {
                  var8.readLine();
                  boolean var10 = false;
                  String var11 = "";
                  String var12 = "";
                  int var13 = 1;
                  String var14 = var0.getName().replace("_" + getDefaultLanguage(), "_" + getLanguage());

                  while((var3 = var8.readLine()) != null) {
                     ++var13;

                     try {
                        if (var3.contains("=") && var3.contains("\"")) {
                           if (var3.trim().startsWith("Recipe_")) {
                              var11 = var3.split("=")[0].replaceAll("Recipe_", "").replaceAll("_", " ").trim();
                              var12 = var3.split("=")[1];
                              var12 = var12.substring(var12.indexOf("\"") + 1, var12.lastIndexOf("\""));
                           } else {
                              String[] var15;
                              if (var3.trim().startsWith("DisplayName")) {
                                 var15 = var3.split("=");
                                 if (var3.trim().startsWith("DisplayName_")) {
                                    var11 = var15[0].replaceAll("DisplayName_", "").trim();
                                 } else {
                                    var11 = var15[0].replaceAll("DisplayName", "").trim();
                                 }

                                 if ("Anti_depressants".equals(var11)) {
                                    var11 = "Antidepressants";
                                 }

                                 var12 = var15[1];
                                 var12 = var12.substring(var12.indexOf("\"") + 1, var12.lastIndexOf("\""));
                              } else {
                                 int var16;
                                 int var17;
                                 if (var3.trim().startsWith("EvolvedRecipeName_")) {
                                    var15 = var3.split("=");
                                    var11 = var15[0].replaceAll("EvolvedRecipeName_", "").trim();
                                    var12 = var15[1];
                                    var16 = var12.indexOf("\"");
                                    var17 = var12.lastIndexOf("\"");
                                    var12 = var12.substring(var16 + 1, var17);
                                 } else if (var3.trim().startsWith("ItemName_")) {
                                    var15 = var3.split("=");
                                    var11 = var15[0].replaceAll("ItemName_", "").trim();
                                    var12 = var15[1];
                                    var16 = var12.indexOf("\"");
                                    var17 = var12.lastIndexOf("\"");
                                    var12 = var12.substring(var16 + 1, var17);
                                 } else {
                                    var11 = var3.split("=")[0].trim();
                                    var12 = var3.substring(var3.indexOf("=") + 1);
                                    var12 = var12.substring(var12.indexOf("\"") + 1, var12.lastIndexOf("\""));
                                    if (var3.contains("..")) {
                                       var10 = true;
                                    }
                                 }
                              }
                           }
                        } else if (var3.contains("--") || var3.trim().isEmpty() || !var3.trim().endsWith("..") && !var10) {
                           var10 = false;
                        } else {
                           var10 = true;
                           var12 = var12 + var3.substring(var3.indexOf("\"") + 1, var3.lastIndexOf("\""));
                        }

                        if (!var10 || !var3.trim().endsWith("..")) {
                           if (!var11.isEmpty()) {
                              if (!var1.containsKey(var11)) {
                                 var1.put(var11, var12);
                                 if (debug && var2 == getDefaultLanguage() && getLanguage() != getDefaultLanguage()) {
                                    if (var14 != null) {
                                       debugwrite(var14 + "\r\n");
                                       var14 = null;
                                    }

                                    debugwrite("\t" + var11 + " = \"" + var12 + "\",\r\n");
                                    debugErrors = true;
                                 }
                              } else if (debug && var2 == getDefaultLanguage() && getLanguage() != getDefaultLanguage()) {
                                 String var74 = (String)var1.get(var11);
                                 if (countSubstitutions(var74) != countSubstitutions(var12)) {
                                    debugwrite("wrong number of % substitutions in " + var11 + "    " + getDefaultLanguage() + "=\"" + var12 + "\"    " + getLanguage() + "=\"" + var74 + "\"\r\n");
                                    debugErrors = true;
                                 }
                              }
                           }

                           var10 = false;
                           var12 = "";
                           var11 = "";
                        }
                     } catch (Exception var66) {
                        if (debug) {
                           if (var14 != null) {
                              debugwrite(var14 + "\r\n");
                              var14 = null;
                           }

                           debugwrite("line " + var13 + ": " + var11 + " = " + var12 + "\r\n");
                           if (debugFile != null) {
                              var66.printStackTrace(new PrintWriter(debugFile));
                           }

                           debugwrite("\r\n");
                           debugErrors = true;
                        }
                     }
                  }
               } catch (Throwable var67) {
                  var9 = var67;
                  throw var67;
               } finally {
                  if (var8 != null) {
                     if (var9 != null) {
                        try {
                           var8.close();
                        } catch (Throwable var65) {
                           var9.addSuppressed(var65);
                        }
                     } else {
                        var8.close();
                     }
                  }

               }
            } catch (Throwable var69) {
               var7 = var69;
               throw var69;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var64) {
                        var7.addSuppressed(var64);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } catch (Throwable var71) {
            var5 = var71;
            throw var71;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var63) {
                     var5.addSuppressed(var63);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Exception var73) {
         var73.printStackTrace();
      }

   }

   public static String getText(String var0) {
      return getTextInternal(var0, false);
   }

   public static String getTextOrNull(String var0) {
      return getTextInternal(var0, true);
   }

   private static String getTextInternal(String var0, boolean var1) {
      if (ui == null) {
         loadFiles();
      }

      String var2 = null;
      if (var0.startsWith("UI_")) {
         var2 = (String)ui.get(var0);
      } else if (var0.startsWith("Moodles_")) {
         var2 = (String)moodles.get(var0);
      } else if (var0.startsWith("SurvivalGuide_")) {
         var2 = (String)survivalGuide.get(var0);
      } else if (var0.startsWith("Farming_")) {
         var2 = (String)farming.get(var0);
      } else if (var0.startsWith("IGUI_")) {
         var2 = (String)igui.get(var0);
      } else if (var0.startsWith("ContextMenu_")) {
         var2 = (String)contextMenu.get(var0);
      } else if (var0.startsWith("GameSound_")) {
         var2 = (String)gameSound.get(var0);
      } else if (var0.startsWith("Sandbox_")) {
         var2 = (String)sandbox.get(var0);
      } else if (var0.startsWith("Tooltip_")) {
         var2 = (String)tooltip.get(var0);
      } else if (var0.startsWith("Challenge_")) {
         var2 = (String)challenge.get(var0);
      } else if (var0.startsWith("MakeUp")) {
         var2 = (String)makeup.get(var0);
      } else if (var0.startsWith("News_")) {
         var2 = (String)news.get(var0);
      } else if (var0.startsWith("Stash_")) {
         var2 = (String)stash.get(var0);
      }

      String var3 = Core.bDebug && DebugOptions.instance.TranslationPrefix.getValue() ? "*" : null;
      if (var2 == null) {
         if (var1) {
            return null;
         }

         if (!missing.contains(var0)) {
            DebugLog.log("ERROR: Missing translation \"" + var0 + "\"");
            if (debug) {
               debugwrite("ERROR: Missing translation \"" + var0 + "\"\r\n");
            }

            missing.add(var0);
         }

         var2 = var0;
         var3 = Core.bDebug && DebugOptions.instance.TranslationPrefix.getValue() ? "!" : null;
      }

      if (var2.contains("<br>")) {
         return var2.replaceAll("<br>", "\n");
      } else {
         return var3 == null ? var2 : var3 + var2;
      }
   }

   private static int countSubstitutions(String var0) {
      int var1 = 0;
      if (var0.contains("%1")) {
         ++var1;
      }

      if (var0.contains("%2")) {
         ++var1;
      }

      if (var0.contains("%3")) {
         ++var1;
      }

      if (var0.contains("%4")) {
         ++var1;
      }

      return var1;
   }

   private static String subst(String var0, String var1, Object var2) {
      if (var2 != null) {
         if (var2 instanceof Double) {
            double var3 = (Double)var2;
            var0 = var0.replaceAll(var1, var3 == (double)((long)var3) ? Long.toString((long)var3) : var2.toString());
         } else {
            var0 = var0.replaceAll(var1, Matcher.quoteReplacement(var2.toString()));
         }
      }

      return var0;
   }

   public static String getText(String var0, Object var1) {
      String var2 = getText(var0);
      var2 = subst(var2, "%1", var1);
      return var2;
   }

   public static String getText(String var0, Object var1, Object var2) {
      String var3 = getText(var0);
      var3 = subst(var3, "%1", var1);
      var3 = subst(var3, "%2", var2);
      return var3;
   }

   public static String getText(String var0, Object var1, Object var2, Object var3) {
      String var4 = getText(var0);
      var4 = subst(var4, "%1", var1);
      var4 = subst(var4, "%2", var2);
      var4 = subst(var4, "%3", var3);
      return var4;
   }

   public static String getText(String var0, Object var1, Object var2, Object var3, Object var4) {
      String var5 = getText(var0);
      var5 = subst(var5, "%1", var1);
      var5 = subst(var5, "%2", var2);
      var5 = subst(var5, "%3", var3);
      var5 = subst(var5, "%4", var4);
      return var5;
   }

   public static String getTextOrNull(String var0, Object var1) {
      String var2 = getTextOrNull(var0);
      if (var2 == null) {
         return null;
      } else {
         var2 = subst(var2, "%1", var1);
         return var2;
      }
   }

   public static String getTextOrNull(String var0, Object var1, Object var2) {
      String var3 = getTextOrNull(var0);
      if (var3 == null) {
         return null;
      } else {
         var3 = subst(var3, "%1", var1);
         var3 = subst(var3, "%2", var2);
         return var3;
      }
   }

   public static String getTextOrNull(String var0, Object var1, Object var2, Object var3) {
      String var4 = getTextOrNull(var0);
      if (var4 == null) {
         return null;
      } else {
         var4 = subst(var4, "%1", var1);
         var4 = subst(var4, "%2", var2);
         var4 = subst(var4, "%3", var3);
         return var4;
      }
   }

   public static String getTextOrNull(String var0, Object var1, Object var2, Object var3, Object var4) {
      String var5 = getTextOrNull(var0);
      if (var5 == null) {
         return null;
      } else {
         var5 = subst(var5, "%1", var1);
         var5 = subst(var5, "%2", var2);
         var5 = subst(var5, "%3", var3);
         var5 = subst(var5, "%4", var4);
         return var5;
      }
   }

   private static String getDefaultText(String var0) {
      return changeSomeStuff((String)((KahluaTable)LuaManager.env.rawget(var0.split("_")[0] + "_" + getDefaultLanguage().name())).rawget(var0));
   }

   private static String changeSomeStuff(String var0) {
      return var0;
   }

   public static void setLanguage(Language var0) {
      if (var0 == null) {
         var0 = getDefaultLanguage();
      }

      language = var0;
   }

   public static void setLanguage(int var0) {
      Language var1 = Languages.instance.getByIndex(var0);
      setLanguage(var1);
   }

   public static Language getLanguage() {
      if (language == null) {
         String var0 = Core.getInstance().getOptionLanguageName();
         if (!StringUtils.isNullOrWhitespace(var0)) {
            language = Languages.instance.getByName(var0);
         }
      }

      if (language == null) {
         language = Languages.instance.getByName(System.getProperty("user.language").toUpperCase());
      }

      if (language == null) {
         language = getDefaultLanguage();
      }

      return language;
   }

   public static String getCharset() {
      return getLanguage().charset();
   }

   public static ArrayList getAvailableLanguage() {
      if (availableLanguage == null) {
         availableLanguage = new ArrayList();

         for(int var0 = 0; var0 < Languages.instance.getNumLanguages(); ++var0) {
            availableLanguage.add(Languages.instance.getByIndex(var0));
         }
      }

      return availableLanguage;
   }

   public static String getDisplayItemName(String var0) {
      String var1 = null;
      var1 = (String)items.get(var0.replaceAll(" ", "_").replaceAll("-", "_"));
      return var1 == null ? var0 : var1;
   }

   public static String getItemNameFromFullType(String var0) {
      if (!var0.contains(".")) {
         throw new IllegalArgumentException("fullType must contain \".\" i.e. module.type");
      } else {
         String var1 = (String)itemName.get(var0);
         if (var1 == null) {
            if (debug && getLanguage() != getDefaultLanguage() && !debugItem.contains(var0)) {
               debugItem.add(var0);
            }

            Item var2 = ScriptManager.instance.getItem(var0);
            if (var2 == null) {
               var1 = var0;
            } else {
               var1 = var2.getDisplayName();
            }

            itemName.put(var0, var1);
         }

         return var1;
      }
   }

   public static void setDefaultItemEvolvedRecipeName(String var0, String var1) {
      if (getLanguage() == getDefaultLanguage()) {
         if (!var0.contains(".")) {
            throw new IllegalArgumentException("fullType must contain \".\" i.e. module.type");
         } else if (!itemEvolvedRecipeName.containsKey(var0)) {
            itemEvolvedRecipeName.put(var0, var1);
         }
      }
   }

   public static String getItemEvolvedRecipeName(String var0) {
      if (!var0.contains(".")) {
         throw new IllegalArgumentException("fullType must contain \".\" i.e. module.type");
      } else {
         String var1 = (String)itemEvolvedRecipeName.get(var0);
         if (var1 == null) {
            if (debug && getLanguage() != getDefaultLanguage() && !debugItemEvolvedRecipeName.contains(var0)) {
               debugItemEvolvedRecipeName.add(var0);
            }

            Item var2 = ScriptManager.instance.getItem(var0);
            if (var2 == null) {
               var1 = var0;
            } else {
               var1 = var2.getDisplayName();
            }

            itemEvolvedRecipeName.put(var0, var1);
         }

         return var1;
      }
   }

   public static String getMoveableDisplayName(String var0) {
      String var1 = var0.replaceAll(" ", "_").replaceAll("-", "_").replaceAll("'", "").replaceAll("\\.", "");
      String var2 = (String)moveables.get(var1);
      if (var2 == null) {
         return Core.bDebug && DebugOptions.instance.TranslationPrefix.getValue() ? "!" + var0 : var0;
      } else {
         return Core.bDebug && DebugOptions.instance.TranslationPrefix.getValue() ? "*" + var2 : var2;
      }
   }

   public static String getMultiStageBuild(String var0) {
      String var1 = (String)multiStageBuild.get("MultiStageBuild_" + var0);
      if (var1 == null) {
         if (debug && getLanguage() != getDefaultLanguage() && !debugMultiStageBuild.contains(var0)) {
            debugMultiStageBuild.add(var0);
         }

         return var0;
      } else {
         return var1;
      }
   }

   public static String getRecipeName(String var0) {
      String var1 = null;
      var1 = (String)recipe.get(var0);
      if (var1 != null && !var1.isEmpty()) {
         return var1;
      } else {
         if (debug && getLanguage() != getDefaultLanguage() && !debugRecipe.contains(var0)) {
            debugRecipe.add(var0);
         }

         return var0;
      }
   }

   public static Language getDefaultLanguage() {
      return Languages.instance.getDefaultLanguage();
   }

   public static void debugItemEvolvedRecipeNames() {
      if (debug && !debugItemEvolvedRecipeName.isEmpty()) {
         debugwrite("EvolvedRecipeName_" + getLanguage() + ".txt\r\n");
         ArrayList var0 = new ArrayList();
         var0.addAll(debugItemEvolvedRecipeName);
         Collections.sort(var0);
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            debugwrite("\tEvolvedRecipeName_" + var2 + " = \"" + (String)itemEvolvedRecipeName.get(var2) + "\",\r\n");
         }

         debugItemEvolvedRecipeName.clear();
      }
   }

   public static void debugItemNames() {
      if (debug && !debugItem.isEmpty()) {
         debugwrite("ItemName_" + getLanguage() + ".txt\r\n");
         ArrayList var0 = new ArrayList();
         var0.addAll(debugItem);
         Collections.sort(var0);
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            debugwrite("\tItemName_" + var2 + " = \"" + (String)itemName.get(var2) + "\",\r\n");
         }

         debugItem.clear();
      }
   }

   public static void debugMultiStageBuildNames() {
      if (debug && !debugMultiStageBuild.isEmpty()) {
         debugwrite("MultiStageBuild_" + getLanguage() + ".txt\r\n");
         ArrayList var0 = new ArrayList();
         var0.addAll(debugMultiStageBuild);
         Collections.sort(var0);
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            debugwrite("\tMultiStageBuild_" + var2 + " = \"\",\r\n");
         }

         debugMultiStageBuild.clear();
      }
   }

   public static void debugRecipeNames() {
      if (debug && !debugRecipe.isEmpty()) {
         debugwrite("Recipes_" + getLanguage() + ".txt\r\n");
         ArrayList var0 = new ArrayList();
         var0.addAll(debugRecipe);
         Collections.sort(var0);
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            debugwrite("\tRecipe_" + var2.replace(" ", "_") + " = \"\",\r\n");
         }

         debugRecipe.clear();
      }
   }

   private static void debugwrite(String var0) {
      if (debugFile != null) {
         try {
            debugFile.write(var0);
            debugFile.flush();
         } catch (IOException var2) {
         }
      }

   }

   public static ArrayList getAzertyMap() {
      if (azertyLanguages == null) {
         azertyLanguages = new ArrayList();
         azertyLanguages.add("FR");
      }

      return azertyLanguages;
   }

   public static String getRadioText(String var0) {
      String var1 = (String)dynamicRadio.get(var0);
      return var1 == null ? var0 : var1;
   }

   private static final class News {
      String version;
      ArrayList newsList = new ArrayList();
      ArrayList balance = new ArrayList();
      ArrayList bugfix = new ArrayList();
      ArrayList other = new ArrayList();

      News(String var1) {
         this.version = var1;
      }

      String toRichText() {
         StringBuilder var1 = new StringBuilder("");
         Iterator var2;
         String var3;
         if (!this.newsList.isEmpty()) {
            var1.append("<LINE> <LEFT> <SIZE:medium> [New] <LINE> <LINE> ");
            var2 = this.newsList.iterator();

            while(var2.hasNext()) {
               var3 = (String)var2.next();
               var1.append(var3);
            }
         }

         if (!this.balance.isEmpty()) {
            var1.append("<LINE> <LEFT> <SIZE:medium> [Balance] <LINE> <LINE> ");
            var2 = this.balance.iterator();

            while(var2.hasNext()) {
               var3 = (String)var2.next();
               var1.append(var3);
            }
         }

         if (!this.bugfix.isEmpty()) {
            var1.append("<LINE> <LEFT> <SIZE:medium> [Bug Fix] <LINE> <LINE> ");
            var2 = this.bugfix.iterator();

            while(var2.hasNext()) {
               var3 = (String)var2.next();
               var1.append(var3);
            }
         }

         if (!this.other.isEmpty()) {
            var1.append("<LINE> <LEFT> <SIZE:medium> [Other] <LINE> <LINE> ");
            var2 = this.other.iterator();

            while(var2.hasNext()) {
               var3 = (String)var2.next();
               var1.append(var3);
            }
         }

         return var1.toString();
      }
   }
}
