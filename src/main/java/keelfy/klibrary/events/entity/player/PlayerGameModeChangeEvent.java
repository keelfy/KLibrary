package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings.GameType;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerGameModeChangeEvent extends KPlayerEvent {

	private GameType oldGameMode;
	private GameType newGameMode;

	public PlayerGameModeChangeEvent(EntityPlayer player, GameType oldGameType, GameType newGameType) {
		super(player);

		this.oldGameMode = oldGameType;
		this.newGameMode = newGameType;
	}

	public GameType getOldGameMode() {
		return oldGameMode;
	}

	public GameType getNewGameMode() {
		return newGameMode;
	}
}
