package keelfy.klibrary.server;

import java.io.*;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.authlib.GameProfile;

import keelfy.klibrary.KLibrary;
import keelfy.klibrary.client.*;
import keelfy.klibrary.network.EnumDataType;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;

/**
 * Diffirent server utilities.
 * 
 * @author keelfy
 */
public final class KServerUtils {

	/**
	 * Checks {@link ICommandSender} is instance of {@link EntityPlayerMP}.
	 * 
	 * @param sender Sender to check.
	 * @deprecated Will be removed because of useless.
	 * @return player instance or null if it is not a player
	 */
	@Deprecated
	@Nullable
	public static EntityPlayerMP checkPlayer(ICommandSender sender) {
		if (sender instanceof EntityPlayerMP) {
			return (EntityPlayerMP) sender;
		}
		return null;
	}

	/**
	 * Sends packet to player with message that will be added to chat on client in
	 * localized and formatted form.
	 * 
	 * @see {@link KClientPacketHandler} to find out receiving proccess.
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
	 * Checks whether player has OP.
	 * 
	 * @param player Player to check.
	 * @return Result of checking.
	 */
	public static boolean isOp(EntityPlayerMP player) {
		return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
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

	@Nullable
	public static MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityPlayer player, boolean checkLiquids) {
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + (world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		if (player instanceof EntityPlayerMP) {
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.func_147447_a(vec3, vec31, checkLiquids, !checkLiquids, false);
	}
}
