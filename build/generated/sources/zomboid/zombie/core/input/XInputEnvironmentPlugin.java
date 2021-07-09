package zombie.core.input;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.util.plugins.Plugin;

public final class XInputEnvironmentPlugin extends ControllerEnvironment implements Plugin {
   public Controller[] getControllers() {
      Controller[] var1 = new Controller[]{XInputController.create(0, "XInputController0"), XInputController.create(1, "XInputController1"), XInputController.create(2, "XInputController2"), XInputController.create(3, "XInputController3")};
      return var1;
   }

   public boolean isSupported() {
      return true;
   }
}
