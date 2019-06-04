package keelfy.klibrary.server.test;

import keelfy.klibrary.server.KServerUtils;
import keelfy.klibrary.server.commands.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

/**
 * @author keelfy
 */
public class KTestSubSubCmds {

	@KCommand(aliases = { "lol" }, desc = "Test of klibrary", canUseFromConsole = false)
	public void test(KCommandArguments args, ICommandSender sender) {
		EntityPlayerMP player = KServerUtils.checkPlayer(sender);

		if (player == null)
			return;

		player.addChatMessage(new ChatComponentText("LOLLL2"));
	}

}
