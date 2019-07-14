package keelfy.klibrary.server.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.*;

import org.apache.commons.lang3.ArrayUtils;

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

	public static void callChilds(KCommandChilds baseChilds, KCommandArguments arguments, ICommandSender sender, int deep) throws Throwable {
		if (arguments.argsLength() == 0)
			return;

		KCommandArguments args = new KCommandArguments(ArrayUtils.remove(arguments.originalArgs, 0), arguments.getFlags());

		for (int i = 0; i < baseChilds.getChilds().size(); i++) {
			KCommandChilds childs = baseChilds.getChilds().get(i);

			if (childs.getInfo().isCall() && childs.getInfo().getName().equals(arguments.getString(0))) {
				try {
					childs.getMethod().invoke(childs.getObject(), args, sender);
				} catch (IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					throw e.getCause();
				}
			}

			if (arguments.argsLength() != 0)
				callChilds(childs, args, sender, deep + 1);
		}
	}

	@Override
	public void processCommand(ICommandSender sender, final String[] args) {}
}