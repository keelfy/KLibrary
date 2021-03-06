package keelfy.klibrary.server.test;

import keelfy.klibrary.server.commands.*;
import keelfy.klibrary.server.commands.KCommandCompletions.Completion;
import keelfy.klibrary.utils.KPlayerUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * @author keelfy
 */
public enum KTestCommands {
	INSTANCE;

	public static void register(MinecraftServer server) {
//		KLibrary.getCommandManager().registerContainer(INSTANCE);
	}

	@KCommand(aliases = { "test", "test1" }, desc = "Test of klibrary", canUseFromConsole = true)
	@KCommandCompletions({ @Completion(num = 0, variants = { "<player>" }), @Completion(num = 2, variants = { "gui", "gui12" }) })
	@KChildCommands({ KTestSubCmds.class })
	public void test(KCommandArguments args, ICommandSender sender) {
		if (!(sender instanceof EntityPlayerMP))
			return;

		KPlayerUtils.sendLocalizedMessage((EntityPlayerMP) sender, "test.message", "str1", 1, 1.4F);
	}

}
