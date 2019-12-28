package keelfy.klibrary.server;

import java.io.*;

import javax.annotation.*;

import org.apache.commons.lang3.ArrayUtils;

import keelfy.klibrary.KLibrary;
import keelfy.klibrary.client.KClientPackets;
import keelfy.klibrary.network.EnumDataType;
import keelfy.klibrary.utils.KPlayerUtils;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

/**
 * Diffirent server utilities.
 * 
 * @author keelfy
 */
public final class KServerUtils {

	/**
	 * Sends packet to player with message that will be added to chat on client in
	 * localized and formatted form.
	 * 
	 * @deprecated Use
	 *             {@link KPlayerUtils#sendLocalizedMessage(EntityPlayerMP, String, Object...)}
	 * 
	 * @param player           Recipient
	 * @param localizationCode Code that I18n will use to get message
	 * @param args             Variables used in message. See {@link EnumDataType}
	 *                         to find out supported data types.
	 */
	@Deprecated
	public static void sendLocalizedMessage(EntityPlayerMP player, String localizationCode, Object... args) {
		KPlayerUtils.sendLocalizedMessage(player, localizationCode, args);
	}

	/**
	 * Checks whether player has OP.
	 * 
	 * @deprecated Use {@link KPlayerUtils#isOp(EntityPlayerMP)}
	 * 
	 * @param player Player to check.
	 * @return Result of checking.
	 */
	@Deprecated
	public static boolean isOp(EntityPlayerMP player) {
		return KPlayerUtils.isOp(player);
	}

	/**
	 * Matches player by username except sender's username (if it is player).
	 * 
	 * @param sender   instance of sender.
	 * @param username username of player.
	 * @return{@link EntityPlayerMP}.
	 */
	@Nullable
	public static EntityPlayerMP matchPlayer(ICommandSender sender, String username) {
		EntityPlayerMP player = PlayerSelector.matchOnePlayer(sender, username);
		return player == null ? MinecraftServer.getServer().getConfigurationManager().func_152612_a(username) : player;
	}

	/**
	 * Loads offline player's {@link NBTTagCompound}.
	 * 
	 * @deprecated Use {@link KPlayerUtils#loadOfflinePlayer(String)}
	 * 
	 * @param playerName Name of offline player.
	 * @return {@link NBTTagCompound} with properties.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Deprecated
	@Nullable
	public static NBTTagCompound loadOfflinePlayer(final String playerName) throws FileNotFoundException, IOException {
		return KPlayerUtils.loadOfflinePlayer(playerName);
	}

	/**
	 * Saves offline player's {@link NBTTagCompound}.
	 * 
	 * @deprecated User
	 *             {@link KPlayerUtils#saveOfflinePlayer(String, NBTTagCompound)}
	 * 
	 * @param playerName Name of offline player.
	 * @param playerNBT  {@link NBTTagCompound} to save.
	 * @return Whether is successfully completed.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Deprecated
	public static boolean saveOfflinePlayer(final String playerName, final NBTTagCompound playerNBT) throws FileNotFoundException, IOException {
		return KPlayerUtils.saveOfflinePlayer(playerName, playerNBT);
	}

	/**
	 * Sends message to everybode on the server
	 * 
	 * @param message - the message
	 */
	public static void broadcast(final IChatComponent message) {
		MinecraftServer.getServer().addChatMessage(message);

		for (Object target : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			((ICommandSender) target).addChatMessage(message);
		}
	}

	/**
	 * Sends message to everybode on the server
	 * 
	 * @param message - the message
	 */
	public static void broadcast(final String message) {
		broadcast(new ChatComponentText(message));
	}

	/**
	 * Sends localized message to everybode on the server
	 * 
	 * @param defaultMessage   the message that will be send to non-player targets
	 * @param localizationCode localization code of message that clients will decode
	 *                         into message
	 * @param args             arguments for
	 *                         {@link String#format(String, Object...)}
	 */
	public static void broadcastLocalized(final String defaultMessage, final String localizationCode, final Object... args) {
		MinecraftServer.getServer().addChatMessage(new ChatComponentText(defaultMessage));
		KLibrary.getNetwork().sendToAll(true, KClientPackets.MESSAGE, ArrayUtils.add(args, 0, localizationCode));
	}

	/**
	 * Kicks player with {@link IChatComponent} message.
	 * 
	 * @deprecated Use {@link KPlayerUtils#kick(EntityPlayerMP, IChatComponent)}
	 * 
	 * @param player    the Player
	 * @param component the Message
	 */
	@Deprecated
	public static void kick(@Nonnull EntityPlayerMP player, @Nonnull IChatComponent component) {
		KPlayerUtils.kick(player, component);
	}
}
