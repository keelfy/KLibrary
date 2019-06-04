package keelfy.klibrary.server.commands;

import java.lang.reflect.*;
import java.util.*;

import keelfy.klibrary.KLibrary;
import keelfy.klibrary.server.commands.KCommandCompletions.Completion;
import keelfy.klibrary.server.commands.exceptions.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

/**
 * @author keelfy
 */
public enum KCommandManager {
	INSTANCE;

	public void registerCommandContainer(Object handler, CommandHandler commandManager) {
		for (final Method method : handler.getClass().getMethods()) {
			if (method.isAnnotationPresent(KCommand.class)) {
				commandManager.registerCommand(this.initializeAnnotatedMethod(handler, method));
			}
		}
	}

	private KBaseCommand initializeAnnotatedMethod(Object toInvoke, Method method) {
		KCommand cmd = method.getAnnotation(KCommand.class);
		KCommandInfo info = new KCommandInfo(cmd.aliases(), cmd.usage());

		if (!cmd.canUseFromConsole())
			info.disallowConsoleUsing();

		if (method.isAnnotationPresent(KCommandCompletions.class)) {
			initTabCompletions(method.getAnnotation(KCommandCompletions.class), info);
		}

		KCommandChilds childs = getChildCommands(toInvoke, method, info);

		final KBaseCommand command = new KBaseCommand(info, childs) {
			@Override
			public void processCommand(final ICommandSender sender, final String[] args) {
				if ((cmd.max() != -1 && args.length > cmd.max()) || args.length < cmd.min())
					return;

				try {
					final Set<Character> valueFlags = new HashSet<Character>();

					char[] flags = cmd.flags().toCharArray();
					Set<Character> newFlags = new HashSet<Character>();
					for (int i = 0; i < flags.length; ++i) {
						if (flags.length > i + 1 && flags[i + 1] == ':') {
							valueFlags.add(flags[i]);
							++i;
						}
						newFlags.add(flags[i]);
					}

					KCommandArguments arguments = new KCommandArguments(args, valueFlags);

					if (!cmd.anyFlags()) {
						for (char flag : arguments.getFlags()) {
							if (!newFlags.contains(flag)) {
								return;
							}
						}
					}
					method.invoke(toInvoke, arguments, sender);
					this.callChilds(getChilds(), arguments, sender, 0);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (CommandException e) {
					e.printStackTrace();
				} catch (KPermissionsException e) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "You do not have enought permissions for this command."));
				} catch (KCommandException e) {
					sender.addChatMessage(new ChatComponentText(e.getMessage()));
				}
			}
		};
		return command;
	}

	private KCommandChilds getChildCommands(Object object, Method method, KCommandInfo infoBase) {
		KCommandChilds childs = new KCommandChilds(infoBase, object, method);
		if (method.isAnnotationPresent(KChildCommands.class)) {
			Class[] classes = method.getAnnotation(KChildCommands.class).value();

			for (Class clazz : classes) {
				if (object.getClass() == clazz) {
					KLibrary.logger.error("Remove self-directed command childs annotation in " + method.getName());
					continue;
				}

				try {
					Object obj = clazz.newInstance();
					KLibrary.logger.info("Loading object " + obj.toString() + "...");
					for (Method childMethod : clazz.getMethods()) {
						if (childMethod.isAnnotationPresent(KCommand.class)) {
							KCommand cmd = childMethod.getAnnotation(KCommand.class);
							KCommandInfo info = new KCommandInfo(cmd.aliases(), cmd.usage());
							childs.addChild(getChildCommands(obj, childMethod, info));
							KLibrary.logger.info(childs.getObject());
						}
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return childs;
	}

	private void initTabCompletions(KCommandCompletions commandCompletions, KCommandInfo info) {
		Map<Integer, String[]> values = new HashMap<Integer, String[]>();
		int maxArg = 0;

		for (Completion completion : commandCompletions.value()) {
			if (completion.num() > maxArg)
				maxArg = completion.num();

			values.put(completion.num(), completion.variants());
		}

		Object[] objects = new Object[maxArg + 1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = values.containsKey(i) ? values.get(i) : null;
		}

		info.createTabCompleter(objects);
	}
}
