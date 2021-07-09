package zombie.commands.serverCommands;

import zombie.commands.CommandArgs;
import zombie.commands.CommandBase;
import zombie.commands.CommandHelp;
import zombie.commands.CommandName;
import zombie.commands.CommandNames;
import zombie.commands.RequiredRight;
import zombie.core.logger.LoggerManager;
import zombie.core.network.ByteBufferWriter;
import zombie.core.raknet.UdpConnection;
import zombie.network.GameServer;
import zombie.network.PacketTypes;
import zombie.network.ServerOptions;
import zombie.network.ServerWorldDatabase;
import zombie.network.Userlog;

@CommandNames({@CommandName(
   name = "kickuser"
), @CommandName(
   name = "disconnect"
)})
@CommandArgs(
   required = {"(.+)"}
)
@CommandHelp(
   helpText = "UI_ServerOptionDesc_Kick"
)
@RequiredRight(
   requiredRights = 44
)
public class KickUserCommand extends CommandBase {
   public KickUserCommand(String var1, String var2, String var3, UdpConnection var4) {
      super(var1, var2, var3, var4);
   }

   protected String Command() {
      String var1 = this.getCommandArg(0);
      LoggerManager.getLogger("admin").write(this.getExecutorUsername() + " kicked user " + var1);
      ServerWorldDatabase.instance.addUserlog(var1, Userlog.UserlogType.Kicked, "", "server", 1);
      boolean var2 = false;

      for(int var3 = 0; var3 < GameServer.udpEngine.connections.size(); ++var3) {
         UdpConnection var4 = (UdpConnection)GameServer.udpEngine.connections.get(var3);

         for(int var5 = 0; var5 < 4; ++var5) {
            if (var1.equals(var4.usernames[var5])) {
               var2 = true;
               ByteBufferWriter var6 = var4.startPacket();
               PacketTypes.doPacket((short)83, var6);
               var6.putUTF("You have been kicked from this server.");
               var4.endPacketImmediate();
               var4.forceDisconnect();
               GameServer.addDisconnect(var4);
               break;
            }
         }
      }

      if (var2 && ServerOptions.instance.BanKickGlobalSound.getValue()) {
         GameServer.PlaySoundAtEveryPlayer("RumbleThunder");
      }

      if (var2) {
         return "User " + var1 + " kicked.";
      } else {
         return "User " + var1 + " doesn't exist.";
      }
   }
}
