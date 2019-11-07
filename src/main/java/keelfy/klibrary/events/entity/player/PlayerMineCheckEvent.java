package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerMineCheckEvent extends KPlayerEvent {

	private final KLocation checkLocation;

	public PlayerMineCheckEvent(EntityPlayer player, int x, int y, int z) {
		super(player);

		this.checkLocation = new KLocation(player.worldObj, x, y, z);
	}

	public KLocation getCheckLocation() {
		return checkLocation;
	}

}
