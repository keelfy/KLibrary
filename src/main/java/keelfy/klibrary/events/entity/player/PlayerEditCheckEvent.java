package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerEditCheckEvent extends KPlayerEvent {

	private final KLocation checkLocation;
	private final ForgeDirection face;
	private final ItemStack itemStack;

	public PlayerEditCheckEvent(EntityPlayer player, int x, int y, int z, int sideOfHit, ItemStack itemStack) {
		super(player);

		this.checkLocation = new KLocation(player.worldObj, x, y, z);
		this.face = ForgeDirection.getOrientation(sideOfHit);
		this.itemStack = itemStack;
	}

	public KLocation getCheckLocation() {
		return checkLocation;
	}

	public ForgeDirection getFace() {
		return face;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}
}
