package keelfy.klibrary.server.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.*;

import org.apache.commons.lang3.ArrayUtils;

import keelfy.klibrary.server.commands.exceptions.KCommandException;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * @author keelfy
 */
public class KBaseCommand extends CommandBase {

	private KCommandChilds childs;
	private KCommandInfo commandInfo;

	public KBaseCommand(@Nonnull KCommandInfo info, @Nullable KCommandChilds childs) {
		this.commandInfo = info;
		this.childs = childs;
	}

	public KCommandInfo getCommandInfo() {
		return commandInfo;
	}

	public KCommandChilds getChilds() {
		return childs;
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

	public static void callChilds(KCommandChilds baseChilds, KCommandArguments arguments, ICommandSender sender, int deep) throws KCommandException {
		if (arguments.argsLength() == 0)
			return;

		if (deep > 0) {
			String[] originalArgs = arguments.originalArgs;
			ArrayUtils.remove(originalArgs, 0);
			arguments = new KCommandArguments(originalArgs, arguments.getFlags());
		}

		for (int i = 0; i < baseChilds.getChilds().size(); i++) {
			KCommandChilds childs = baseChilds.getChilds().get(i);

			if (childs.getInfo().isCall() && childs.getInfo().getName().equals(arguments.getString(0))) {
				try {
					childs.getMethod().invoke(childs.getObject(), arguments, sender);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

			if (arguments.argsLength() != 0)
				callChilds(childs, arguments, sender, deep + 1);
		}
	}

	@Override
	public void processCommand(ICommandSender sender, final String[] args) {}
}