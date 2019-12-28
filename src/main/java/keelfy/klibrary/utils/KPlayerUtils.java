package keelfy.klibrary.utils;

import java.io.*;

import javax.annotation.*;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.authlib.GameProfile;

import keelfy.klibrary.KLibrary;
import keelfy.klibrary.client.KClientPackets;
import keelfy.klibrary.network.EnumDataType;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;

/**
 * @author keelfy
 */
public class KPlayerUtils {

	/**
	 * Sends packet to player with message that will be added to chat on client in
	 * localized and formatted form.
	 * 
	 * @param player           Recipient
	 * @param localizationCode Code that I18n will use to get message
	 * @param args             Variables used in message. See {@link EnumDataType}
	 *                         to find out supported data types.
	 */
	public static void sendLocalizedMessage(EntityPlayerMP player, String localizationCode, Object... args) {
		KLibrary.getNetwork().sendTo(true, player, KClientPackets.MESSAGE, ArrayUtils.add(args, 0, localizationCode));
	}

	/**
	 * Kicks player with {@link IChatComponent} message.
	 * 
	 * @param player    the Player
	 * @param component the Message
	 */
	public static void kick(@Nonnull EntityPlayerMP player, @Nonnull IChatComponent component) {
		player.playerNetServerHandler.netManager.scheduleOutboundPacket(new S40PacketDisconnect(component));
		player.playerNetServerHandler.netManager.closeChannel(component);
	}

	/**
	 * Checks whether player has OP.
	 * 
	 * @param player Player to check.
	 * @return Result of checking.
	 */
	public static boolean isOp(EntityPlayerMP player) {
		return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
	}

	/**
	 * Loads offline player's {@link NBTTagCompound}.
	 * 
	 * @param playerName Name of offline player.
	 * @return {@link NBTTagCompound} with properties.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Nullable
	public static NBTTagCompound loadOfflinePlayer(final String playerName) throws FileNotFoundException, IOException {
		NBTTagCompound playerNBT = null;
		File playerFile = null;
		SaveHandler saveHandler = (SaveHandler) DimensionManager.getWorld(0).getSaveHandler();

		File playersDirectory = new File(saveHandler.getWorldDirectory(), "playerdata");
		if ((playerFile = new File(playersDirectory, EntityPlayer.func_146094_a(new GameProfile(null, playerName)).toString() + ".dat")).exists()) {
			playerNBT = CompressedStreamTools.readCompressed(new FileInputStream(playerFile));
		}

		return playerNBT;
	}

	/**
	 * Saves offline player's {@link NBTTagCompound}.
	 * 
	 * @param playerName Name of offline player.
	 * @param playerNBT  {@link NBTTagCompound} to save.
	 * @return Whether is successfully completed.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static boolean saveOfflinePlayer(final String playerName, final NBTTagCompound playerNBT) throws FileNotFoundException, IOException {
		File playerFile = null;
		SaveHandler saveHandler = (SaveHandler) DimensionManager.getWorld(0).getSaveHandler();
		File playersDirectory = new File(saveHandler.getWorldDirectory(), "playerdata");
		if ((playerFile = new File(playersDirectory, EntityPlayer.func_146094_a(new GameProfile(null, playerName)).toString() + ".dat")).exists()) {
			CompressedStreamTools.writeCompressed(playerNBT, new FileOutputStream(playerFile));
			return true;
		}
		return false;
	}

	/**
	 * Get player's looking-at spot.
	 *
	 * @param player
	 * @return The position as a MovingObjectPosition Null if not existent.
	 */
	public static MovingObjectPosition getPlayerLookingSpot(EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			return getPlayerLookingSpot(player, ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance());
		else
			return getPlayerLookingSpot(player, 5);
	}

	/**
	 * Get player's looking spot.
	 *
	 * @param player
	 * @param maxDistance Keep max distance to 5.
	 * @return The position as a MovingObjectPosition Null if not existent.
	 */
	public static MovingObjectPosition getPlayerLookingSpot(EntityPlayer player, double maxDistance) {
		Vec3 lookAt = player.getLook(1);
		Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
		Vec3 pos1 = playerPos.addVector(0, player.getEyeHeight(), 0);
		Vec3 pos2 = pos1.addVector(lookAt.xCoord * maxDistance, lookAt.yCoord * maxDistance, lookAt.zCoord * maxDistance);
		return player.worldObj.rayTraceBlocks(pos1, pos2);
	}

}
