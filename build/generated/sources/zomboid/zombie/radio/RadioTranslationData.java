package zombie.radio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import zombie.core.Language;
import zombie.core.Translator;

public final class RadioTranslationData {
   private String filePath;
   private String guid;
   private String language;
   private Language languageEnum;
   private int version = -1;
   private final ArrayList translators = new ArrayList();
   private final Map translations = new HashMap();

   public RadioTranslationData(String var1) {
      this.filePath = var1;
   }

   public String getFilePath() {
      return this.filePath;
   }

   public String getGuid() {
      return this.guid;
   }

   public String getLanguage() {
      return this.language;
   }

   public Language getLanguageEnum() {
      return this.languageEnum;
   }

   public int getVersion() {
      return this.version;
   }

   public int getTranslationCount() {
      return this.translations.size();
   }

   public ArrayList getTranslators() {
      return this.translators;
   }

   public boolean validate() {
      return this.guid != null && this.language != null && this.version >= 0;
   }

   public boolean loadTranslations() {
      boolean var1 = false;
      if (Translator.getLanguage() != this.languageEnum) {
         System.out.println("Radio translations trying to load language that is not the current language...");
         return false;
      } else {
         try {
            File var2 = new File(this.filePath);
            if (var2.exists() && !var2.isDirectory()) {
               BufferedReader var3 = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath), Charset.forName(this.languageEnum.charset())));
               String var4 = null;
               boolean var5 = false;
               ArrayList var6 = new ArrayList();

               while(true) {
                  while((var4 = var3.readLine()) != null) {
                     var4 = var4.trim();
                     if (var4.equals("[Translations]")) {
                        var5 = true;
                     } else if (var5) {
                        String var9;
                        if (!var4.equals("[Collection]")) {
                           if (var4.equals("[/Translations]")) {
                              var1 = true;
                              return var1;
                           }

                           String[] var12 = var4.split("=", 2);
                           if (var12.length == 2) {
                              String var14 = var12[0].trim();
                              var9 = var12[1].trim();
                              this.translations.put(var14, var9);
                           }
                        } else {
                           String var7 = null;

                           while((var4 = var3.readLine()) != null) {
                              var4 = var4.trim();
                              if (var4.equals("[/Collection]")) {
                                 break;
                              }

                              String[] var8 = var4.split("=", 2);
                              if (var8.length == 2) {
                                 var9 = var8[0].trim();
                                 String var10 = var8[1].trim();
                                 if (var9.equals("text")) {
                                    var7 = var10;
                                 } else if (var9.equals("member")) {
                                    var6.add(var10);
                                 }
                              }
                           }

                           if (var7 != null && var6.size() > 0) {
                              Iterator var13 = var6.iterator();

                              while(var13.hasNext()) {
                                 var9 = (String)var13.next();
                                 this.translations.put(var9, var7);
                              }
                           }

                           var6.clear();
                        }
                     }
                  }

                  return var1;
               }
            }
         } catch (Exception var11) {
            var11.printStackTrace();
            var1 = false;
         }

         return var1;
      }
   }

   public String getTranslation(String var1) {
      return this.translations.containsKey(var1) ? (String)this.translations.get(var1) : null;
   }

   public static RadioTranslationData ReadFile(String var0) {
      RadioTranslationData var1 = new RadioTranslationData(var0);
      File var2 = new File(var0);
      if (var2.exists() && !var2.isDirectory()) {
         try {
            FileInputStream var3 = new FileInputStream(var0);
            Throwable var4 = null;

            try {
               BufferedReader var5 = new BufferedReader(new InputStreamReader(var3));
               String var6 = null;

               while((var6 = var5.readLine()) != null) {
                  String[] var7 = var6.split("=");
                  if (var7.length > 1) {
                     String var8 = var7[0].trim();
                     String var9 = "";

                     for(int var10 = 1; var10 < var7.length; ++var10) {
                        var9 = var9 + var7[var10];
                     }

                     var9 = var9.trim();
                     if (var8.equals("guid")) {
                        var1.guid = var9;
                     } else if (var8.equals("language")) {
                        var1.language = var9;
                     } else if (var8.equals("version")) {
                        var1.version = Integer.parseInt(var9);
                     } else if (var8.equals("translator")) {
                        String[] var29 = var9.split(",");
                        if (var29.length > 0) {
                           String[] var11 = var29;
                           int var12 = var29.length;

                           for(int var13 = 0; var13 < var12; ++var13) {
                              String var14 = var11[var13];
                              var1.translators.add(var14);
                           }
                        }
                     }
                  }

                  var6 = var6.trim();
                  if (var6.equals("[/Info]")) {
                     break;
                  }
               }
            } catch (Throwable var23) {
               var4 = var23;
               throw var23;
            } finally {
               if (var3 != null) {
                  if (var4 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var22) {
                        var4.addSuppressed(var22);
                     }
                  } else {
                     var3.close();
                  }
               }

            }
         } catch (Exception var25) {
            var25.printStackTrace();
         }
      }

      boolean var26 = false;
      if (var1.language != null) {
         Iterator var27 = Translator.getAvailableLanguage().iterator();

         while(var27.hasNext()) {
            Language var28 = (Language)var27.next();
            if (var28.toString().equals(var1.language)) {
               var1.languageEnum = var28;
               var26 = true;
               break;
            }
         }
      }

      if (!var26 && var1.language != null) {
         System.out.println("Language " + var1.language + " not found");
         return null;
      } else {
         return var1.guid != null && var1.language != null && var1.version >= 0 ? var1 : null;
      }
   }
}
