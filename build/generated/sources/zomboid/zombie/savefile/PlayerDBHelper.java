package zombie.savefile;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import zombie.ZomboidFileSystem;
import zombie.core.BoxedStaticValues;
import zombie.core.Core;
import zombie.core.logger.ExceptionLogger;
import zombie.debug.DebugLog;
import zombie.util.PZSQLUtils;
import zombie.vehicles.VehicleDBHelper;

public final class PlayerDBHelper {
   public static Connection create() {
      Connection var0 = null;
      String var1 = ZomboidFileSystem.instance.getGameModeCacheDir() + Core.GameSaveWorld;
      File var2 = new File(var1);
      if (!var2.exists()) {
         var2.mkdirs();
      }

      File var3 = new File(var1 + File.separator + "players.db");
      var3.setReadable(true, false);
      var3.setExecutable(true, false);
      var3.setWritable(true, false);
      Statement var4;
      if (!var3.exists()) {
         try {
            var3.createNewFile();
            var0 = PZSQLUtils.getConnection(var3.getAbsolutePath());
            var4 = var0.createStatement();
            var4.executeUpdate("CREATE TABLE localPlayers (id   INTEGER PRIMARY KEY NOT NULL,name STRING,wx    INTEGER,wy    INTEGER,x    FLOAT,y    FLOAT,z    FLOAT,worldversion    INTEGER,data BLOB,isDead BOOLEAN);");
            var4.executeUpdate("CREATE TABLE networkPlayers (id   INTEGER PRIMARY KEY NOT NULL,world TEXT,username TEXT,playerIndex   INTEGER,name STRING,x    FLOAT,y    FLOAT,z    FLOAT,worldversion    INTEGER,data BLOB,isDead BOOLEAN);");
            var4.executeUpdate("CREATE INDEX inpusername ON networkPlayers (username);");
            var4.close();
         } catch (Exception var8) {
            ExceptionLogger.logException(var8);
            DebugLog.log("failed to create players database");
            System.exit(1);
         }
      }

      if (var0 == null) {
         try {
            var0 = PZSQLUtils.getConnection(var3.getAbsolutePath());
         } catch (Exception var7) {
            ExceptionLogger.logException(var7);
            DebugLog.log("failed to create players database");
            System.exit(1);
         }
      }

      try {
         var4 = var0.createStatement();
         var4.executeQuery("PRAGMA JOURNAL_MODE=TRUNCATE;");
         var4.close();
      } catch (Exception var6) {
         ExceptionLogger.logException(var6);
         DebugLog.log("failed to config players.db");
         System.exit(1);
      }

      try {
         var0.setAutoCommit(false);
      } catch (SQLException var5) {
         DebugLog.log("failed to setAutoCommit for players.db");
      }

      return var0;
   }

   public static void rollback(Connection var0) {
      if (var0 != null) {
         try {
            var0.rollback();
         } catch (SQLException var2) {
            ExceptionLogger.logException(var2);
         }

      }
   }

   public static boolean isPlayerAlive(String var0, int var1) {
      if (Core.getInstance().isNoSave()) {
         return false;
      } else {
         File var2 = new File(var0 + File.separator + "map_p.bin");
         if (var2.exists()) {
            return true;
         } else if (VehicleDBHelper.isPlayerAlive(var0, var1)) {
            return true;
         } else if (var1 == -1) {
            return false;
         } else {
            try {
               File var3 = new File(var0 + File.separator + "players.db");
               if (!var3.exists()) {
                  return false;
               } else {
                  var3.setReadable(true, false);
                  Connection var4 = PZSQLUtils.getConnection(var3.getAbsolutePath());
                  Throwable var5 = null;

                  boolean var10;
                  try {
                     String var6 = "SELECT isDead FROM localPlayers WHERE id=?";
                     PreparedStatement var7 = var4.prepareStatement(var6);
                     Throwable var8 = null;

                     try {
                        var7.setInt(1, var1);
                        ResultSet var9 = var7.executeQuery();
                        if (!var9.next()) {
                           return false;
                        }

                        var10 = !var9.getBoolean(1);
                     } catch (Throwable var38) {
                        var8 = var38;
                        throw var38;
                     } finally {
                        if (var7 != null) {
                           if (var8 != null) {
                              try {
                                 var7.close();
                              } catch (Throwable var37) {
                                 var8.addSuppressed(var37);
                              }
                           } else {
                              var7.close();
                           }
                        }

                     }
                  } catch (Throwable var40) {
                     var5 = var40;
                     throw var40;
                  } finally {
                     if (var4 != null) {
                        if (var5 != null) {
                           try {
                              var4.close();
                           } catch (Throwable var36) {
                              var5.addSuppressed(var36);
                           }
                        } else {
                           var4.close();
                        }
                     }

                  }

                  return var10;
               }
            } catch (Throwable var42) {
               ExceptionLogger.logException(var42);
               return false;
            }
         }
      }
   }

   public static ArrayList getPlayers(String var0) throws SQLException {
      ArrayList var1 = new ArrayList();
      if (Core.getInstance().isNoSave()) {
         return var1;
      } else {
         File var2 = new File(var0 + File.separator + "players.db");
         if (!var2.exists()) {
            return var1;
         } else {
            var2.setReadable(true, false);
            Connection var3 = PZSQLUtils.getConnection(var2.getAbsolutePath());
            Throwable var4 = null;

            try {
               String var5 = "SELECT id, name, isDead FROM localPlayers";
               PreparedStatement var6 = var3.prepareStatement(var5);
               Throwable var7 = null;

               try {
                  ResultSet var8 = var6.executeQuery();

                  while(var8.next()) {
                     int var9 = var8.getInt(1);
                     String var10 = var8.getString(2);
                     boolean var11 = var8.getBoolean(3);
                     var1.add(BoxedStaticValues.toDouble((double)var9));
                     var1.add(var10);
                     var1.add(var11 ? Boolean.TRUE : Boolean.FALSE);
                  }
               } catch (Throwable var33) {
                  var7 = var33;
                  throw var33;
               } finally {
                  if (var6 != null) {
                     if (var7 != null) {
                        try {
                           var6.close();
                        } catch (Throwable var32) {
                           var7.addSuppressed(var32);
                        }
                     } else {
                        var6.close();
                     }
                  }

               }
            } catch (Throwable var35) {
               var4 = var35;
               throw var35;
            } finally {
               if (var3 != null) {
                  if (var4 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var31) {
                        var4.addSuppressed(var31);
                     }
                  } else {
                     var3.close();
                  }
               }

            }

            return var1;
         }
      }
   }

   public static void setPlayer1(String var0, int var1) throws SQLException {
      if (!Core.getInstance().isNoSave()) {
         if (var1 != 1) {
            File var2 = new File(var0 + File.separator + "players.db");
            if (var2.exists()) {
               var2.setReadable(true, false);
               Connection var3 = PZSQLUtils.getConnection(var2.getAbsolutePath());
               Throwable var4 = null;

               try {
                  boolean var5 = false;
                  boolean var6 = false;
                  int var7 = -1;
                  int var8 = -1;
                  String var9 = "SELECT id FROM localPlayers";
                  PreparedStatement var10 = var3.prepareStatement(var9);
                  Throwable var11 = null;

                  try {
                     int var13;
                     try {
                        for(ResultSet var12 = var10.executeQuery(); var12.next(); var8 = Math.max(var8, var13)) {
                           var13 = var12.getInt(1);
                           if (var13 == 1) {
                              var5 = true;
                           } else if (var7 == -1 || var7 > var13) {
                              var7 = var13;
                           }

                           if (var13 == var1) {
                              var6 = true;
                           }
                        }
                     } catch (Throwable var128) {
                        var11 = var128;
                        throw var128;
                     }
                  } finally {
                     if (var10 != null) {
                        if (var11 != null) {
                           try {
                              var10.close();
                           } catch (Throwable var119) {
                              var11.addSuppressed(var119);
                           }
                        } else {
                           var10.close();
                        }
                     }

                  }

                  if (var1 > 0) {
                     if (!var6) {
                        return;
                     }

                     if (var5) {
                        var9 = "UPDATE localPlayers SET id=? WHERE id=?";
                        var10 = var3.prepareStatement(var9);
                        var11 = null;

                        try {
                           var10.setInt(1, var8 + 1);
                           var10.setInt(2, 1);
                           var10.executeUpdate();
                           var10.setInt(1, 1);
                           var10.setInt(2, var1);
                           var10.executeUpdate();
                           var10.setInt(1, var1);
                           var10.setInt(2, var8 + 1);
                           var10.executeUpdate();
                           return;
                        } catch (Throwable var124) {
                           var11 = var124;
                           throw var124;
                        } finally {
                           if (var10 != null) {
                              if (var11 != null) {
                                 try {
                                    var10.close();
                                 } catch (Throwable var120) {
                                    var11.addSuppressed(var120);
                                 }
                              } else {
                                 var10.close();
                              }
                           }

                        }
                     } else {
                        var9 = "UPDATE localPlayers SET id=? WHERE id=?";
                        var10 = var3.prepareStatement(var9);
                        var11 = null;

                        try {
                           var10.setInt(1, 1);
                           var10.setInt(2, var1);
                           var10.executeUpdate();
                           return;
                        } catch (Throwable var123) {
                           var11 = var123;
                           throw var123;
                        } finally {
                           if (var10 != null) {
                              if (var11 != null) {
                                 try {
                                    var10.close();
                                 } catch (Throwable var121) {
                                    var11.addSuppressed(var121);
                                 }
                              } else {
                                 var10.close();
                              }
                           }

                        }
                     }
                  }

                  if (!var5) {
                     return;
                  }

                  var9 = "UPDATE localPlayers SET id=? WHERE id=?";
                  var10 = var3.prepareStatement(var9);
                  var11 = null;

                  try {
                     var10.setInt(1, var8 + 1);
                     var10.setInt(2, 1);
                     var10.executeUpdate();
                  } catch (Throwable var122) {
                     var11 = var122;
                     throw var122;
                  } finally {
                     if (var10 != null) {
                        if (var11 != null) {
                           try {
                              var10.close();
                           } catch (Throwable var118) {
                              var11.addSuppressed(var118);
                           }
                        } else {
                           var10.close();
                        }
                     }

                  }
               } catch (Throwable var130) {
                  var4 = var130;
                  throw var130;
               } finally {
                  if (var3 != null) {
                     if (var4 != null) {
                        try {
                           var3.close();
                        } catch (Throwable var117) {
                           var4.addSuppressed(var117);
                        }
                     } else {
                        var3.close();
                     }
                  }

               }

            }
         }
      }
   }
}
