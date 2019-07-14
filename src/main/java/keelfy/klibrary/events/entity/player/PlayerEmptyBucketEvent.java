package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerEmptyBucketEvent extends KPlayerEvent {

	private KBlock blockClicked;
	private Block bucket;

	public PlayerEmptyBucketEvent(EntityPlayer player, KBlock blockClicked, Block bucket) {
		super(player);

		this.bucket = bucket;
		this.blockClicked = blockClicked;
	}

	public KBlock getBlockClicked() {
		return blockClicked;
	}

	public Block getBucketOf() {
		return bucket;
	}
}
