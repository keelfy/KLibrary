package keelfy.klibrary.asm;

import cpw.mods.fml.common.eventhandler.Event.Result;
import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.block.KBlockEvent;
import keelfy.klibrary.utils.*;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public final class KServerHooks {

	/**
	 * Calling {@link KBlockEvent.PushTest}.
	 */
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static boolean canPushBlock(BlockPistonBase piston, Block block, World world, int x, int y, int z, boolean flag) {
		if (block == Blocks.obsidian) {
			return false;
		} else {
			if (block != Blocks.piston && block != Blocks.sticky_piston) {
				if (block.getBlockHardness(world, x, y, z) == -1.0F) {
					return false;
				}

				if (block.getMobilityFlag() == 2) {
					return false;
				}

				if (block.getMobilityFlag() == 1) {
					if (!flag) {
						return false;
					}

					KBlock interactedBlock = new KBlock(new KLocation(world, x, y, z), block);
					KBlockEvent.PushTest call = new KBlockEvent.PushTest(interactedBlock);
					MinecraftForge.EVENT_BUS.post(call);
					return call.getResult() != Result.DENY;
				}
			} else if (piston.isExtended(world.getBlockMetadata(x, y, z))) {
				return false;
			}

			if (!(world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z)))) {
				KBlock interactedBlock = new KBlock(new KLocation(world, x, y, z), block);
				KBlockEvent.PushTest call = new KBlockEvent.PushTest(interactedBlock);
				MinecraftForge.EVENT_BUS.post(call);
				return call.getResult() != Result.DENY;
			}
			return false;
		}
	}

}
