package keelfy.klibrary.asm;

import java.util.Random;

import gloomyfolken.hooklib.asm.Hook;
import keelfy.klibrary.events.KBlockEvent;
import keelfy.klibrary.events.KBlockEvent.*;
import keelfy.klibrary.utils.*;
import net.minecraft.block.BlockFire;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 */
public final class KHooks {

	@Hook
	public static void tryCatchFire(BlockFire block, World world, int x, int y, int z, int par1, Random rand, int par2, ForgeDirection face) {
		BlockIgniteEvent event = new BlockIgniteEvent(new KBlock(new KLocation(world, x, y, z), block));
		if (!MinecraftForge.EVENT_BUS.post(event)) {
			return;
		}
		System.out.println("das");
	}

	@Hook(injectOnExit = true)
	public static void updateTick(BlockFire block, World world, int x, int y, int z, Random rand) {
		KBlockEvent event = new BlockBurnEvent(new KBlock(new KLocation(world, x, y, z), block));

		if (!MinecraftForge.EVENT_BUS.post(event)) {
			world.setBlockToAir(x, y, z);
			return;
		}
	}
}
