package keelfy.klibrary.server;

import keelfy.klibrary.server.commands.*;
import keelfy.klibrary.server.commands.KCommandCompletions.Completion;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * @author keelfy
 */
public enum KTestCommands {
	INSTANCE;

	public static void register(MinecraftServer server) {
		KCommandManager.INSTANCE.registerCommandHandler(INSTANCE, (CommandHandler) server.getCommandManager());
	}

	@KCommand(aliases = { "test", "test1" }, desc = "Test of klibrary", canUseFromConsole = false)
	@KCommandCompletions({ @Completion(num = 0, variants = { "<player>" }), @Completion(num = 2, variants = { "gui", "gui12" }) })
	public void test(KCommandArguments args, ICommandSender sender) {
		EntityPlayerMP player = KServerUtils.checkPlayer(sender);

		if (player == null)
			return;

		KServerUtils.sendLocalizedMessage(player, "test.message", "str1", 1, 1.4F);
	}

}
