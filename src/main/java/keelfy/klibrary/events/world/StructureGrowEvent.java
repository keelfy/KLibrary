package keelfy.klibrary.events.world;

import java.util.List;

import cpw.mods.fml.common.eventhandler.*;
import keelfy.klibrary.utils.*;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
@Cancelable
public class StructureGrowEvent extends Event {

	protected KLocation location;
	protected boolean bonemeal;
	protected EntityPlayer player;
	protected List<KBlock> blocks;
	protected String type;

	public StructureGrowEvent(KLocation location, EntityPlayer player, List<KBlock> blocks, boolean bonemeal, String type) {
		this.location = location;
		this.player = player;
		this.blocks = blocks;
		this.bonemeal = bonemeal;
		this.type = type;
	}

	public KLocation getLocation() {
		return location;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public List<KBlock> getBlocks() {
		return blocks;
	}

	public boolean isFromBonemeal() {
		return bonemeal;
	}

	public String getTreeType() {
		return type;
	}

}
