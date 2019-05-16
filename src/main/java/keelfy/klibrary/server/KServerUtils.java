package keelfy.klibrary.server;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.authlib.GameProfile;

import keelfy.klibrary.KLibrary;
import keelfy.klibrary.client.KClientPacketHandler;
import keelfy.klibrary.common.KClientPackets;
import keelfy.klibrary.network.EnumDataType;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * Diffirent server utilities.
 * 
 * @author keelfy
 */
public final class KServerUtils {

	/**
	 * Checks {@link ICommandSender} is instance of {@link EntityPlayerMP}.
	 * 
	 * @param sender sender to check
	 * @return player instance or null if it is not a player
	 */
	public static EntityPlayerMP checkPlayer(ICommandSender sender) {
		if (sender instanceof EntityPlayerMP) {
			return (EntityPlayerMP) sender;
		}
		return null;
	}

	/**
	 * Sends packet to player with message that will be send from client in
	 * localized form.
	 * 
	 * @see {@link KClientPacketHandler} to find out receiving proccess
	 * 
	 * @param player           recipient
	 * @param localizationCode code that I18n will use to get message
	 * @param args             variables used in message. See {@link EnumDataType}
	 *                         to find out supported data types
	 */
	public static void sendLocalizedMessage(EntityPlayerMP player, String localizationCode, Object... args) {
		KLibrary.network.sendTo(true, player, KClientPackets.MESSAGE, ArrayUtils.add(args, 0, localizationCode));
	}

	/**
	 * Checks if player has admin permissions.
	 * 
	 * @param player player to check
	 * @return result of checking
	 */
	public static boolean isOp(EntityPlayerMP player) {
		return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
	}

	/**
	 * Matches player by username except sender's username (if it is player).
	 * 
	 * @param sender   instance of sender
	 * @param username username of player
	 * @return instance of {@link EntityPlayerMP} or null if it does not found
	 */
	public static EntityPlayerMP matchPlayer(ICommandSender sender, String username) {
		EntityPlayerMP player = PlayerSelector.matchOnePlayer(sender, username);
		return player == null ? MinecraftServer.getServer().getConfigurationManager().func_152612_a(username) : player;
	}

	/**
	 * Gets profile instanceof of offline or online player by username.
	 * 
	 * @param username username of player
	 * @return {@link GameProfile} of player or null if it does not found
	 */
	public static GameProfile getOfflinePlayer(String username) {
		return MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
	}
}
