package keelfy.klibrary.server.test;

import keelfy.klibrary.server.commands.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

/**
 * @author keelfy
 */
public class KTestSubCmds {

	@KCommand(aliases = { "lol" }, desc = "Test of klibrary", canUseFromConsole = true)
	@KChildCommands({ KTestSubSubCmds.class })
	public void test(KCommandArguments args, ICommandSender sender) {
		if (!(sender instanceof EntityPlayerMP))
			return;

		sender.addChatMessage(new ChatComponentText("LOLLL1"));
	}

}
