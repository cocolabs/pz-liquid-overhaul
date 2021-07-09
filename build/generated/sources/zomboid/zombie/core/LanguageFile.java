package zombie.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import zombie.core.logger.ExceptionLogger;
import zombie.core.math.PZMath;
import zombie.scripting.ScriptParser;
import zombie.util.StringUtils;

public final class LanguageFile {
   private static final int VERSION1 = 1;
   private static final int VERSION = 1;

   public boolean read(String var1, LanguageFileData var2) {
      try {
         FileReader var3 = new FileReader(var1);
         Throwable var4 = null;

         try {
            BufferedReader var5 = new BufferedReader(var3);
            Throwable var6 = null;

            try {
               StringBuilder var7 = new StringBuilder();

               for(String var8 = var5.readLine(); var8 != null; var8 = var5.readLine()) {
                  var7.append(var8);
               }

               this.fromString(var7.toString(), var2);
               boolean var9 = true;
               return var9;
            } catch (Throwable var36) {
               var6 = var36;
               throw var36;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var35) {
                        var6.addSuppressed(var35);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (Throwable var38) {
            var4 = var38;
            throw var38;
         } finally {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var34) {
                     var4.addSuppressed(var34);
                  }
               } else {
                  var3.close();
               }
            }

         }
      } catch (FileNotFoundException var40) {
         return false;
      } catch (Exception var41) {
         ExceptionLogger.logException(var41);
         return false;
      }
   }

   private void fromString(String var1, LanguageFileData var2) {
      var1 = ScriptParser.stripComments(var1);
      ScriptParser.Block var3 = ScriptParser.parse(var1);
      int var4 = -1;
      ScriptParser.Value var5 = var3.getValue("VERSION");
      if (var5 != null) {
         var4 = PZMath.tryParseInt(var5.getValue(), -1);
      }

      if (var4 >= 1 && var4 <= 1) {
         ScriptParser.Value var6 = var3.getValue("text");
         if (var6 != null && !StringUtils.isNullOrWhitespace(var6.getValue())) {
            ScriptParser.Value var7 = var3.getValue("charset");
            if (var7 != null && !StringUtils.isNullOrWhitespace(var7.getValue())) {
               var2.text = var6.getValue().trim();
               var2.charset = var7.getValue().trim();
               ScriptParser.Value var8 = var3.getValue("base");
               if (var8 != null && !StringUtils.isNullOrWhitespace(var8.getValue())) {
                  var2.base = var8.getValue().trim();
               }

               ScriptParser.Value var9 = var3.getValue("azerty");
               if (var9 != null) {
                  var2.azerty = StringUtils.tryParseBoolean(var9.getValue());
               }

            } else {
               throw new RuntimeException("missing or empty value \"charset\"");
            }
         } else {
            throw new RuntimeException("missing or empty value \"text\"");
         }
      } else {
         throw new RuntimeException("invalid or missing VERSION");
      }
   }
}
