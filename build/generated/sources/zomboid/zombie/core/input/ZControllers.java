package zombie.core.input;

import java.util.ArrayList;
import java.util.Iterator;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Controller.Type;
import zombie.core.utils.UpdateLimit;
import zombie.debug.DebugLog;

public final class ZControllers {
   private static ZControllers instance;
   private boolean created;
   private int controllerCount;
   private int controllerCountConnected;
   private ArrayList controllers = new ArrayList();
   private ArrayList events = new ArrayList();
   UpdateLimit limitRecheck = new UpdateLimit(2000L);

   public static synchronized ZControllers getInstance() {
      if (instance == null) {
         instance = new ZControllers();
      }

      return instance;
   }

   public void create() {
      if (!this.created) {
         this.controllerCount = 0;

         try {
            ControllerEnvironment var1 = ControllerEnvironment.getDefaultEnvironment();
            Controller[] var2 = var1.getControllers();
            ArrayList var3 = new ArrayList();
            Controller[] var4 = var2;
            int var5 = var2.length;

            Controller var7;
            for(int var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (!var7.getType().equals(Type.KEYBOARD) && !var7.getType().equals(Type.MOUSE)) {
                  var3.add(var7);

                  try {
                     var7.poll();
                  } catch (Throwable var9) {
                  }
               }
            }

            Iterator var11 = var3.iterator();

            while(var11.hasNext()) {
               var7 = (Controller)var11.next();
               this.createController(var7);
            }

            this.created = true;
         } catch (Throwable var10) {
         }
      }

   }

   private void createController(Controller var1) {
      Controller[] var2 = var1.getControllers();
      if (var2.length == 0) {
         ZInputController var3 = new ZInputController(this.controllerCount, var1);
         this.controllers.add(new ZControllers.ZController(var3));
         ++this.controllerCount;
         ++this.controllerCountConnected;
      } else {
         Controller[] var7 = var2;
         int var4 = var2.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Controller var6 = var7[var5];
            this.createController(var6);
         }
      }

   }

   public ZInputController getController(int var1) {
      return ((ZControllers.ZController)this.controllers.get(var1)).controller;
   }

   public boolean isControllerConnect(int var1) {
      return ((ZControllers.ZController)this.controllers.get(var1)).connected;
   }

   public void poll() {
      int var1;
      for(var1 = 0; var1 < this.controllers.size(); ++var1) {
         if (((ZControllers.ZController)this.controllers.get(var1)).connected && !((ZControllers.ZController)this.controllers.get(var1)).controller.pollReal()) {
            ((ZControllers.ZController)this.controllers.get(var1)).connected = false;
            --this.controllerCountConnected;
            DebugLog.log("Controller " + ((ZControllers.ZController)this.controllers.get(var1)).controller.getName() + " disconnected");
         }
      }

      for(var1 = 0; var1 < this.controllers.size(); ++var1) {
         if (((ZControllers.ZController)this.controllers.get(var1)).connected) {
            ((ZControllers.ZController)this.controllers.get(var1)).controller.poll();
         }
      }

   }

   public int getControllerCount() {
      return this.controllers.size();
   }

   void addEvent(ZControllerEvent var1) {
   }

   public boolean isAnyControllerDisconnect() {
      return this.controllerCountConnected != this.controllerCount;
   }

   class ZController {
      public ZInputController controller;
      public boolean connected;

      public ZController(ZInputController var2) {
         this.controller = var2;
         this.connected = true;
      }
   }
}
