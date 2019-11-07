package keelfy.klibrary.asm;

import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.entity.player.PlayerGameModeChangeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public final class KServerHooks {

	/**
	 * Calling {@link PlayerGameModeChangeEvent} from {@link EntityPlayerMP}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean setGameType(EntityPlayerMP player, WorldSettings.GameType newGameType) {
		return player.theItemInWorldManager.getGameType() != newGameType ? MinecraftForge.EVENT_BUS.post(new PlayerGameModeChangeEvent(player, player.theItemInWorldManager.getGameType(), newGameType)) : false;
	}

	/**
	 * Calling {@link KBlockEvent.PushTest}.
	 */
//	@Hook(returnCondition = ReturnCondition.ALWAYS)
//	public static boolean canPushBlock(BlockPistonBase piston, Block block, World world, int x, int y, int z, boolean flag) {
//		if (block == Blocks.obsidian) {
//			return false;
//		} else {
//			if (block != Blocks.piston && block != Blocks.sticky_piston) {
//				if (block.getBlockHardness(world, x, y, z) == -1.0F) {
//					return false;
//				}
//
//				if (block.getMobilityFlag() == 2) {
//					return false;
//				}
//
//				if (block.getMobilityFlag() == 1) {
//					if (!flag) {
//						return false;
//					}
//
//					KBlock interactedBlock = new KBlock(new KLocation(world, x, y, z), block);
//					KBlockEvent.PushTest call = new KBlockEvent.PushTest(interactedBlock);
//					MinecraftForge.EVENT_BUS.post(call);
//					return call.getResult() != Result.DENY;
//				}
//			} else if (piston.isExtended(world.getBlockMetadata(x, y, z))) {
//				return false;
//			}
//
//			if (!(world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z)))) {
//				KBlock interactedBlock = new KBlock(new KLocation(world, x, y, z), block);
//				KBlockEvent.PushTest call = new KBlockEvent.PushTest(interactedBlock);
//				MinecraftForge.EVENT_BUS.post(call);
//				return call.getResult() != Result.DENY;
//			}
//			return false;
//		}
//	}

}
