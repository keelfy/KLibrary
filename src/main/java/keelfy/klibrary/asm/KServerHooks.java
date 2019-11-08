package keelfy.klibrary.asm;

import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.block.KBlockEvent;
import keelfy.klibrary.events.entity.player.PlayerGameModeChangeEvent;
import keelfy.klibrary.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.*;
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
	@Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
	public static boolean canPushBlock(BlockPistonBase piston, Block block, World world, int x, int y, int z, boolean flag, @Hook.ReturnValue boolean testResult) {
		if (testResult) {
			KBlock interactedBlock = new KBlock(new KLocation(world, x, y, z), block);
			return !MinecraftForge.EVENT_BUS.post(new KBlockEvent.PushTest(interactedBlock));
		}
		return testResult;
	}

}
