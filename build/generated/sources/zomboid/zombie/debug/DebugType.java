package zombie.debug;

public enum DebugType {
   NetworkPacketDebug,
   NetworkFileDebug,
   Network,
   General,
   Lua,
   Mod,
   Sound,
   Zombie,
   Combat,
   Objects,
   Fireplace,
   Radio,
   MapLoading,
   Clothing,
   Animation,
   Asset,
   Script,
   Shader,
   Input,
   Recipe,
   ActionSystem,
   IsoRegion,
   UnitTests,
   FileIO;

   public static boolean Do(DebugType var0) {
      return DebugLog.isEnabled(var0);
   }
}
