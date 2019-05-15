package keelfy.klibrary.server.commands;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * @author keelfy
 */
public class KBaseCommand extends CommandBase {

	private KCommandInfo commandInfo;

	public KBaseCommand(@Nonnull KCommandInfo info) {
		this.commandInfo = info;
	}

	public KCommandInfo getCommandInfo() {
		return commandInfo;
	}

	@Override
	public List getCommandAliases() {
		return commandInfo.getAliases();
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return args.length > 0 ? CommandBase.getListOfStringsMatchingLastWord(args, this.commandInfo.getTabCompleter().getCompletion(args.length - 1)) : null;
	}

	@Override
	public final String getCommandName() {
		return commandInfo.getName();
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return (commandInfo.isUsableFromConsole() && sender instanceof MinecraftServer) || sender instanceof EntityPlayerMP;
	}

	@Override
	public final String getCommandUsage(ICommandSender sender) {
		return commandInfo.getUsage();
	}

	@Override
	public void processCommand(ICommandSender sender, final String[] args) {}
}