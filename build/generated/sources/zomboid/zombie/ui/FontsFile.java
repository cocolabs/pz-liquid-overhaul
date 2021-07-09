package zombie.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import zombie.core.logger.ExceptionLogger;
import zombie.core.math.PZMath;
import zombie.debug.DebugLog;
import zombie.scripting.ScriptParser;
import zombie.util.StringUtils;

public final class FontsFile {
   private static final int VERSION1 = 1;
   private static final int VERSION = 1;

   public boolean read(String var1, HashMap var2) {
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

   private void fromString(String var1, HashMap var2) {
      var1 = ScriptParser.stripComments(var1);
      ScriptParser.Block var3 = ScriptParser.parse(var1);
      int var4 = -1;
      ScriptParser.Value var5 = var3.getValue("VERSION");
      if (var5 != null) {
         var4 = PZMath.tryParseInt(var5.getValue(), -1);
      }

      if (var4 >= 1 && var4 <= 1) {
         Iterator var6 = var3.children.iterator();

         while(true) {
            while(var6.hasNext()) {
               ScriptParser.Block var7 = (ScriptParser.Block)var6.next();
               if (!var7.type.equalsIgnoreCase("font")) {
                  throw new RuntimeException("unknown block type \"" + var7.type + "\"");
               }

               if (StringUtils.isNullOrWhitespace(var7.id)) {
                  DebugLog.General.warn("missing or empty font id");
               } else {
                  ScriptParser.Value var8 = var7.getValue("fnt");
                  ScriptParser.Value var9 = var7.getValue("img");
                  if (var8 != null && !StringUtils.isNullOrWhitespace(var8.getValue())) {
                     FontsFileFont var10 = new FontsFileFont();
                     var10.id = var7.id;
                     var10.fnt = var8.getValue().trim();
                     if (var9 != null && !StringUtils.isNullOrWhitespace(var9.getValue())) {
                        var10.img = var9.getValue().trim();
                     }

                     var2.put(var10.id, var10);
                  } else {
                     DebugLog.General.warn("missing or empty value \"fnt\"");
                  }
               }
            }

            return;
         }
      } else {
         throw new RuntimeException("invalid or missing VERSION");
      }
   }
}
