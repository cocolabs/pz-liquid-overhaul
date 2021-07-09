package zombie.vehicles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.joml.Vector3f;
import se.krka.kahlua.converter.KahluaConverterManager;
import se.krka.kahlua.integration.LuaCaller;
import se.krka.kahlua.j2se.J2SEPlatform;
import se.krka.kahlua.vm.KahluaTable;
import se.krka.kahlua.vm.KahluaThread;
import zombie.ZomboidFileSystem;
import zombie.Lua.LuaManager;
import zombie.core.Core;
import zombie.core.logger.ExceptionLogger;
import zombie.core.skinnedmodel.ModelManager;
import zombie.debug.DebugLog;
import zombie.debug.DebugOptions;
import zombie.gameStates.GameState;
import zombie.gameStates.GameStateMachine;
import zombie.input.GameKeyboard;
import zombie.scripting.ScriptManager;
import zombie.scripting.ScriptParser;
import zombie.scripting.objects.ModelAttachment;
import zombie.scripting.objects.VehicleScript;
import zombie.ui.UIManager;
import zombie.util.list.PZArrayUtil;

public final class EditVehicleState extends GameState {
   public static EditVehicleState instance;
   private EditVehicleState.LuaEnvironment m_luaEnv;
   private boolean bExit = false;
   private String m_initialScript = null;
   private final ArrayList m_gameUI = new ArrayList();
   private final ArrayList m_selfUI = new ArrayList();
   private boolean m_bSuspendUI;
   private KahluaTable m_table = null;

   public EditVehicleState() {
      instance = this;
   }

   public void enter() {
      instance = this;
      if (this.m_luaEnv == null) {
         this.m_luaEnv = new EditVehicleState.LuaEnvironment(LuaManager.platform, LuaManager.converterManager, LuaManager.env);
      }

      this.saveGameUI();
      if (this.m_selfUI.size() == 0) {
         this.m_luaEnv.caller.pcall(this.m_luaEnv.thread, this.m_luaEnv.env.rawget("EditVehicleState_InitUI"));
         if (this.m_table != null && this.m_table.getMetatable() != null) {
            this.m_table.getMetatable().rawset("_LUA_RELOADED_CHECK", Boolean.FALSE);
         }
      } else {
         UIManager.UI.addAll(this.m_selfUI);
         this.m_luaEnv.caller.pcall(this.m_luaEnv.thread, this.m_table.rawget("showUI"), (Object)this.m_table);
      }

      this.bExit = false;
   }

   public void yield() {
      this.restoreGameUI();
   }

   public void reenter() {
      this.saveGameUI();
   }

   public void exit() {
      this.restoreGameUI();
   }

   public void render() {
      byte var1 = 0;
      Core.getInstance().StartFrame(var1, true);
      this.renderScene();
      Core.getInstance().EndFrame(var1);
      Core.getInstance().RenderOffScreenBuffer();
      if (Core.getInstance().StartFrameUI()) {
         this.renderUI();
      }

      Core.getInstance().EndFrameUI();
   }

   public GameStateMachine.StateAction update() {
      if (!this.bExit && !GameKeyboard.isKeyPressed(65)) {
         this.updateScene();
         return GameStateMachine.StateAction.Remain;
      } else {
         return GameStateMachine.StateAction.Continue;
      }
   }

   public static EditVehicleState checkInstance() {
      if (instance != null) {
         if (instance.m_table != null && instance.m_table.getMetatable() != null) {
            if (instance.m_table.getMetatable().rawget("_LUA_RELOADED_CHECK") == null) {
               instance = null;
            }
         } else {
            instance = null;
         }
      }

      return instance == null ? new EditVehicleState() : instance;
   }

   private void saveGameUI() {
      this.m_gameUI.clear();
      this.m_gameUI.addAll(UIManager.UI);
      UIManager.UI.clear();
      this.m_bSuspendUI = UIManager.bSuspend;
      UIManager.bSuspend = false;
      UIManager.setShowPausedMessage(false);
      UIManager.defaultthread = this.m_luaEnv.thread;
   }

   private void restoreGameUI() {
      this.m_selfUI.clear();
      this.m_selfUI.addAll(UIManager.UI);
      UIManager.UI.clear();
      UIManager.UI.addAll(this.m_gameUI);
      UIManager.bSuspend = this.m_bSuspendUI;
      UIManager.setShowPausedMessage(true);
      UIManager.defaultthread = LuaManager.thread;
   }

   private void updateScene() {
      ModelManager.instance.update();
      if (GameKeyboard.isKeyPressed(17)) {
         DebugOptions.instance.ModelRenderWireframe.setValue(!DebugOptions.instance.ModelRenderWireframe.getValue());
      }

   }

   private void renderScene() {
   }

   private void renderUI() {
      UIManager.render();
   }

   public void setTable(KahluaTable var1) {
      this.m_table = var1;
   }

   public void setScript(String var1) {
      if (this.m_table == null) {
         this.m_initialScript = var1;
      } else {
         this.m_luaEnv.caller.pcall(this.m_luaEnv.thread, this.m_table.rawget("setScript"), this.m_table, var1);
      }

   }

   public Object fromLua0(String var1) {
      byte var3 = -1;
      switch(var1.hashCode()) {
      case -1286189703:
         if (var1.equals("getInitialScript")) {
            var3 = 1;
         }
         break;
      case 3127582:
         if (var1.equals("exit")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         this.bExit = true;
         return null;
      case 1:
         return this.m_initialScript;
      default:
         throw new IllegalArgumentException("unhandled \"" + var1 + "\"");
      }
   }

   public Object fromLua1(String var1, Object var2) {
      byte var4 = -1;
      switch(var1.hashCode()) {
      case 1396535690:
         if (var1.equals("writeScript")) {
            var4 = 0;
         }
      default:
         switch(var4) {
         case 0:
            VehicleScript var5 = ScriptManager.instance.getVehicle((String)var2);
            if (var5 == null) {
               throw new NullPointerException("vehicle script \"" + var2 + "\" not found");
            }

            ArrayList var6 = this.readScript(var5.getFileName());
            if (var6 != null) {
               this.updateScript(var5.getFileName(), var6, var5);
            }

            return null;
         default:
            throw new IllegalArgumentException(String.format("unhandled \"%s\" \"%s\"", var1, var2));
         }
      }
   }

   private ArrayList readScript(String var1) {
      StringBuilder var2 = new StringBuilder();
      var1 = ZomboidFileSystem.instance.getString(var1);
      File var3 = new File(var1);

      try {
         FileReader var4 = new FileReader(var3);
         Throwable var5 = null;

         try {
            BufferedReader var6 = new BufferedReader(var4);
            Throwable var7 = null;

            try {
               String var8 = System.lineSeparator();

               String var9;
               while((var9 = var6.readLine()) != null) {
                  var2.append(var9);
                  var2.append(var8);
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
            var5 = var35;
            throw var35;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var31) {
                     var5.addSuppressed(var31);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Throwable var37) {
         ExceptionLogger.logException(var37);
         return null;
      }

      String var38 = ScriptParser.stripComments(var2.toString());
      return ScriptParser.parseTokens(var38);
   }

   private void updateScript(String var1, ArrayList var2, VehicleScript var3) {
      var1 = ZomboidFileSystem.instance.getString(var1);

      for(int var4 = var2.size() - 1; var4 >= 0; --var4) {
         String var5 = ((String)var2.get(var4)).trim();
         int var6 = var5.indexOf("{");
         int var7 = var5.lastIndexOf("}");
         String var8 = var5.substring(0, var6);
         if (var8.startsWith("module")) {
            var8 = var5.substring(0, var6).trim();
            String[] var9 = var8.split("\\s+");
            String var10 = var9.length > 1 ? var9[1].trim() : "";
            if (var10.equals(var3.getModule().getName())) {
               String var11 = var5.substring(var6 + 1, var7).trim();
               ArrayList var12 = ScriptParser.parseTokens(var11);

               for(int var13 = var12.size() - 1; var13 >= 0; --var13) {
                  String var14 = ((String)var12.get(var13)).trim();
                  if (var14.startsWith("vehicle")) {
                     var6 = var14.indexOf("{");
                     var8 = var14.substring(0, var6).trim();
                     var9 = var8.split("\\s+");
                     String var15 = var9.length > 1 ? var9[1].trim() : "";
                     if (var15.equals(var3.getName())) {
                        var14 = this.vehicleScriptToText(var3, var14).trim();
                        var12.set(var13, var14);
                        String var16 = System.lineSeparator();
                        String var17 = String.join(var16 + '\t', var12);
                        var17 = "module " + var10 + var16 + "{" + var16 + '\t' + var17 + var16 + "}" + var16;
                        var2.set(var4, var17);
                        this.writeScript(var1, var2);
                        return;
                     }
                  }
               }
            }
         }
      }

   }

   private String vehicleScriptToText(VehicleScript var1, String var2) {
      float var3 = var1.getModelScale();
      ScriptParser.Block var4 = ScriptParser.parse(var2);
      var4 = (ScriptParser.Block)var4.children.get(0);
      VehicleScript.Model var5 = var1.getModel();
      ScriptParser.Block var6 = var4.getBlock("model", (String)null);
      if (var5 != null && var6 != null) {
         float var7 = var1.getModelScale();
         var6.setValue("scale", String.format(Locale.US, "%.4f", var7));
         Vector3f var8 = var1.getModel().getOffset();
         var6.setValue("offset", String.format(Locale.US, "%.4f %.4f %.4f", var8.x / var3, var8.y / var3, var8.z / var3));
      }

      ArrayList var12 = new ArrayList();

      int var13;
      ScriptParser.Block var14;
      for(var13 = 0; var13 < var4.children.size(); ++var13) {
         var14 = (ScriptParser.Block)var4.children.get(var13);
         if ("physics".equals(var14.type)) {
            if (var12.size() == var1.getPhysicsShapeCount()) {
               var4.elements.remove(var14);
               var4.children.remove(var13);
               --var13;
            } else {
               var12.add(var14);
            }
         }
      }

      for(var13 = 0; var13 < var1.getPhysicsShapeCount(); ++var13) {
         VehicleScript.PhysicsShape var15 = var1.getPhysicsShape(var13);
         boolean var16 = var13 < var12.size();
         ScriptParser.Block var9 = var16 ? (ScriptParser.Block)var12.get(var13) : new ScriptParser.Block();
         var9.type = "physics";
         var9.id = var15.getTypeString();
         if (var16) {
            var9.elements.clear();
            var9.children.clear();
            var9.values.clear();
         }

         var9.setValue("offset", String.format(Locale.US, "%.4f %.4f %.4f", var15.getOffset().x() / var3, var15.getOffset().y() / var3, var15.getOffset().z() / var3));
         if (var15.type == 1) {
            var9.setValue("extents", String.format(Locale.US, "%.4f %.4f %.4f", var15.getExtents().x() / var3, var15.getExtents().y() / var3, var15.getExtents().z() / var3));
            var9.setValue("rotate", String.format(Locale.US, "%.4f %.4f %.4f", var15.getRotate().x(), var15.getRotate().y(), var15.getRotate().z()));
         }

         if (var15.type == 2) {
            var9.setValue("radius", String.format(Locale.US, "%.4f", var15.getRadius() / var3));
         }

         if (!var16) {
            var4.elements.add(var9);
            var4.children.add(var9);
         }
      }

      for(var13 = var4.children.size() - 1; var13 >= 0; --var13) {
         var14 = (ScriptParser.Block)var4.children.get(var13);
         if ("attachment".equals(var14.type)) {
            var4.elements.remove(var14);
            var4.children.remove(var13);
         }
      }

      ScriptParser.Block var18;
      for(var13 = 0; var13 < var1.getAttachmentCount(); ++var13) {
         ModelAttachment var17 = var1.getAttachment(var13);
         var18 = var4.getBlock("attachment", var17.getId());
         if (var18 == null) {
            var18 = new ScriptParser.Block();
            var18.type = "attachment";
            var18.id = var17.getId();
            var4.elements.add(var18);
            var4.children.add(var18);
         }

         var18.setValue("offset", String.format(Locale.US, "%.4f %.4f %.4f", var17.getOffset().x() / var3, var17.getOffset().y() / var3, var17.getOffset().z() / var3));
         var18.setValue("rotate", String.format(Locale.US, "%.4f %.4f %.4f", var17.getRotate().x(), var17.getRotate().y(), var17.getRotate().z()));
         if (var17.getBone() != null) {
            var18.setValue("bone", var17.getBone());
         }

         if (var17.getCanAttach() != null) {
            var18.setValue("canAttach", PZArrayUtil.arrayToString((Iterable)var17.getCanAttach(), "", "", ","));
         }

         if (var17.getZOffset() != 0.0F) {
            var18.setValue("zoffset", String.format(Locale.US, "%.4f", var17.getZOffset()));
         }

         if (!var17.isUpdateConstraint()) {
            var18.setValue("updateconstraint", "false");
         }
      }

      Vector3f var23 = var1.getExtents();
      var4.setValue("extents", String.format(Locale.US, "%.4f %.4f %.4f", var23.x / var3, var23.y / var3, var23.z / var3));
      var23 = var1.getPhysicsChassisShape();
      var4.setValue("physicsChassisShape", String.format(Locale.US, "%.4f %.4f %.4f", var23.x / var3, var23.y / var3, var23.z / var3));
      var23 = var1.getCenterOfMassOffset();
      var4.setValue("centerOfMassOffset", String.format(Locale.US, "%.4f %.4f %.4f", var23.x / var3, var23.y / var3, var23.z / var3));

      for(var13 = 0; var13 < var1.getAreaCount(); ++var13) {
         VehicleScript.Area var19 = var1.getArea(var13);
         var18 = var4.getBlock("area", var19.getId());
         if (var18 != null) {
            var18.setValue("xywh", String.format(Locale.US, "%.4f %.4f %.4f %.4f", var19.getX() / (double)var3, var19.getY() / (double)var3, var19.getW() / (double)var3, var19.getH() / (double)var3));
         }
      }

      for(var13 = 0; var13 < var1.getPassengerCount(); ++var13) {
         VehicleScript.Passenger var20 = var1.getPassenger(var13);
         var18 = var4.getBlock("passenger", var20.getId());
         if (var18 != null) {
            Iterator var21 = var20.positions.iterator();

            while(var21.hasNext()) {
               VehicleScript.Position var10 = (VehicleScript.Position)var21.next();
               ScriptParser.Block var11 = var18.getBlock("position", var10.id);
               if (var11 != null) {
                  var11.setValue("offset", String.format(Locale.US, "%.4f %.4f %.4f", var10.offset.x / var3, var10.offset.y / var3, var10.offset.z / var3));
                  var11.setValue("rotate", String.format(Locale.US, "%.4f %.4f %.4f", var10.rotate.x / var3, var10.rotate.y / var3, var10.rotate.z / var3));
               }
            }
         }
      }

      for(var13 = 0; var13 < var1.getWheelCount(); ++var13) {
         VehicleScript.Wheel var22 = var1.getWheel(var13);
         var18 = var4.getBlock("wheel", var22.getId());
         if (var18 != null) {
            var18.setValue("offset", String.format(Locale.US, "%.4f %.4f %.4f", var22.offset.x / var3, var22.offset.y / var3, var22.offset.z / var3));
         }
      }

      StringBuilder var25 = new StringBuilder();
      String var24 = System.lineSeparator();
      var4.prettyPrint(1, var25, var24);
      return var25.toString();
   }

   private void writeScript(String var1, ArrayList var2) {
      String var3 = ZomboidFileSystem.instance.getString(var1);
      File var4 = new File(var3);

      try {
         FileWriter var5 = new FileWriter(var4);
         Throwable var6 = null;

         try {
            BufferedWriter var7 = new BufferedWriter(var5);
            Throwable var8 = null;

            try {
               DebugLog.General.printf("writing %s\n", var1);
               Iterator var9 = var2.iterator();

               while(var9.hasNext()) {
                  String var10 = (String)var9.next();
                  var7.write(var10);
               }

               this.m_luaEnv.caller.pcall(this.m_luaEnv.thread, this.m_table.rawget("wroteScript"), this.m_table, var3);
            } catch (Throwable var34) {
               var8 = var34;
               throw var34;
            } finally {
               if (var7 != null) {
                  if (var8 != null) {
                     try {
                        var7.close();
                     } catch (Throwable var33) {
                        var8.addSuppressed(var33);
                     }
                  } else {
                     var7.close();
                  }
               }

            }
         } catch (Throwable var36) {
            var6 = var36;
            throw var36;
         } finally {
            if (var5 != null) {
               if (var6 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var32) {
                     var6.addSuppressed(var32);
                  }
               } else {
                  var5.close();
               }
            }

         }
      } catch (Throwable var38) {
         ExceptionLogger.logException(var38);
      }

   }

   public static final class LuaEnvironment {
      public J2SEPlatform platform;
      public KahluaTable env;
      public KahluaThread thread;
      public LuaCaller caller;

      public LuaEnvironment(J2SEPlatform var1, KahluaConverterManager var2, KahluaTable var3) {
         this.platform = var1;
         this.env = var3;
         this.thread = LuaManager.thread;
         this.caller = LuaManager.caller;
      }
   }
}
