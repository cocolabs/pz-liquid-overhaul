package zombie.modding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import zombie.core.Core;
import zombie.core.logger.ExceptionLogger;
import zombie.core.math.PZMath;
import zombie.scripting.ScriptParser;
import zombie.util.StringUtils;

public final class ActiveModsFile {
   private static final int VERSION1 = 1;
   private static final int VERSION = 1;

   public boolean write(String var1, ActiveMods var2) {
      if (Core.getInstance().isNoSave()) {
         return false;
      } else {
         File var3 = new File(var1);

         try {
            FileWriter var4 = new FileWriter(var3);
            Throwable var5 = null;

            try {
               BufferedWriter var6 = new BufferedWriter(var4);
               Throwable var7 = null;

               try {
                  String var8 = this.toString(var2);
                  var6.write(var8);
               } catch (Throwable var32) {
                  var7 = var32;
                  throw var32;
               } finally {
                  if (var6 != null) {
                     if (var7 != null) {
                        try {
                           var6.close();
                        } catch (Throwable var31) {
                           var7.addSuppressed(var31);
                        }
                     } else {
                        var6.close();
                     }
                  }

               }
            } catch (Throwable var34) {
               var5 = var34;
               throw var34;
            } finally {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var30) {
                        var5.addSuppressed(var30);
                     }
                  } else {
                     var4.close();
                  }
               }

            }

            return true;
         } catch (Exception var36) {
            ExceptionLogger.logException(var36);
            return false;
         }
      }
   }

   private String toString(ActiveMods var1) {
      ScriptParser.Block var2 = new ScriptParser.Block();
      var2.setValue("VERSION", String.valueOf(1));
      ScriptParser.Block var3 = var2.addBlock("mods", (String)null);
      ArrayList var4 = var1.getMods();

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         var3.addValue("mod", (String)var4.get(var5));
      }

      ScriptParser.Block var9 = var2.addBlock("maps", (String)null);
      ArrayList var6 = var1.getMapOrder();

      for(int var7 = 0; var7 < var6.size(); ++var7) {
         var9.addValue("map", (String)var6.get(var7));
      }

      StringBuilder var10 = new StringBuilder();
      String var8 = System.lineSeparator();
      var2.prettyPrintElements(0, var10, var8);
      return var10.toString();
   }

   public boolean read(String var1, ActiveMods var2) {
      var2.clear();

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
               return true;
            } catch (Throwable var34) {
               var6 = var34;
               throw var34;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var33) {
                        var6.addSuppressed(var33);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (Throwable var36) {
            var4 = var36;
            throw var36;
         } finally {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var32) {
                     var4.addSuppressed(var32);
                  }
               } else {
                  var3.close();
               }
            }

         }
      } catch (FileNotFoundException var38) {
         return false;
      } catch (Exception var39) {
         ExceptionLogger.logException(var39);
         return false;
      }
   }

   private void fromString(String var1, ActiveMods var2) {
      var1 = ScriptParser.stripComments(var1);
      ScriptParser.Block var3 = ScriptParser.parse(var1);
      int var4 = -1;
      ScriptParser.Value var5 = var3.getValue("VERSION");
      if (var5 != null) {
         var4 = PZMath.tryParseInt(var5.getValue(), -1);
      }

      if (var4 >= 1 && var4 <= 1) {
         ScriptParser.Block var6 = var3.getBlock("mods", (String)null);
         String var10;
         if (var6 != null) {
            Iterator var7 = var6.values.iterator();

            while(var7.hasNext()) {
               ScriptParser.Value var8 = (ScriptParser.Value)var7.next();
               String var9 = var8.getKey().trim();
               if (var9.equalsIgnoreCase("mod")) {
                  var10 = var8.getValue().trim();
                  if (!StringUtils.isNullOrWhitespace(var10)) {
                     var2.getMods().add(var10);
                  }
               }
            }
         }

         ScriptParser.Block var12 = var3.getBlock("maps", (String)null);
         if (var12 != null) {
            Iterator var13 = var12.values.iterator();

            while(var13.hasNext()) {
               ScriptParser.Value var14 = (ScriptParser.Value)var13.next();
               var10 = var14.getKey().trim();
               if (var10.equalsIgnoreCase("map")) {
                  String var11 = var14.getValue().trim();
                  if (!StringUtils.isNullOrWhitespace(var11)) {
                     var2.getMapOrder().add(var11);
                  }
               }
            }
         }

      }
   }
}
