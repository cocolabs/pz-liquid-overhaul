package zombie.core;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import zombie.ZomboidFileSystem;
import zombie.core.logger.ExceptionLogger;
import zombie.gameStates.ChooseGameInfo;
import zombie.util.Lambda;
import zombie.util.list.PZArrayUtil;

public final class Languages {
   public static final Languages instance = new Languages();
   private final ArrayList m_languages = new ArrayList();
   private Language m_defaultLanguage = new Language(0, "EN", "English", "UTF-8", (String)null, false);

   public Languages() {
      this.m_languages.add(this.m_defaultLanguage);
   }

   public void init() {
      this.m_languages.clear();
      this.m_defaultLanguage = new Language(0, "EN", "English", "UTF-8", (String)null, false);
      this.m_languages.add(this.m_defaultLanguage);
      this.loadTranslateDirectory("media/lua/shared/Translate");
      Iterator var1 = ZomboidFileSystem.instance.getModIDs().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         ChooseGameInfo.Mod var3 = ChooseGameInfo.getAvailableModDetails(var2);
         if (var3 != null) {
            File var4 = new File(var3.getDir(), "media/lua/shared/Translate");
            if (var4.isDirectory()) {
               this.loadTranslateDirectory(var4.getAbsolutePath());
            }
         }
      }

   }

   public Language getDefaultLanguage() {
      return this.m_defaultLanguage;
   }

   public int getNumLanguages() {
      return this.m_languages.size();
   }

   public Language getByIndex(int var1) {
      return var1 >= 0 && var1 < this.m_languages.size() ? (Language)this.m_languages.get(var1) : null;
   }

   public Language getByName(String var1) {
      return (Language)PZArrayUtil.find((List)this.m_languages, Lambda.predicate(var1, (var0, var1x) -> {
         return var0.name().equalsIgnoreCase(var1x);
      }));
   }

   public int getIndexByName(String var1) {
      return PZArrayUtil.indexOf((List)this.m_languages, Lambda.predicate(var1, (var0, var1x) -> {
         return var0.name().equalsIgnoreCase(var1x);
      }));
   }

   private void loadTranslateDirectory(String var1) {
      Filter var2 = (var0) -> {
         return Files.isDirectory(var0, new LinkOption[0]) && Files.exists(var0.resolve("language.txt"), new LinkOption[0]);
      };
      Path var3 = FileSystems.getDefault().getPath(var1);
      if (Files.exists(var3, new LinkOption[0])) {
         try {
            DirectoryStream var4 = Files.newDirectoryStream(var3, var2);
            Throwable var5 = null;

            try {
               Iterator var6 = var4.iterator();

               while(var6.hasNext()) {
                  Path var7 = (Path)var6.next();
                  LanguageFileData var8 = this.loadLanguageDirectory(var7.toAbsolutePath());
                  if (var8 != null) {
                     int var9 = this.getIndexByName(var8.name);
                     Language var10;
                     if (var9 == -1) {
                        var10 = new Language(this.m_languages.size(), var8.name, var8.text, var8.charset, var8.base, var8.azerty);
                        this.m_languages.add(var10);
                     } else {
                        var10 = new Language(var9, var8.name, var8.text, var8.charset, var8.base, var8.azerty);
                        this.m_languages.set(var9, var10);
                        if (var8.name.equals(this.m_defaultLanguage.name())) {
                           this.m_defaultLanguage = var10;
                        }
                     }
                  }
               }
            } catch (Throwable var19) {
               var5 = var19;
               throw var19;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var18) {
                        var5.addSuppressed(var18);
                     }
                  } else {
                     var4.close();
                  }
               }

            }
         } catch (Exception var21) {
            ExceptionLogger.logException(var21);
         }

      }
   }

   private LanguageFileData loadLanguageDirectory(Path var1) {
      String var2 = var1.getFileName().toString();
      LanguageFileData var3 = new LanguageFileData();
      var3.name = var2;
      LanguageFile var4 = new LanguageFile();
      String var5 = var1.resolve("language.txt").toString();
      return !var4.read(var5, var3) ? null : var3;
   }
}
