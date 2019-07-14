package keelfy.klibrary.events.block;

import java.util.List;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 * @date 5 июн. 2019 г.
 */
public class PistonEvent extends KBlockEvent {

	protected boolean sticky;
	protected ForgeDirection direction;

	public PistonEvent(KBlock data, ForgeDirection direction, boolean sticky) {
		super(data);

		this.direction = direction;
		this.sticky = sticky;
	}

	public ForgeDirection getDirection() {
		return direction;
	}

	public boolean isSticky() {
		return sticky;
	}

	@Cancelable
	public static class Extend extends PistonEvent {

		protected List<KBlock> pushedBlocks;

		public Extend(KBlock data, ForgeDirection direction, boolean sticky, List<KBlock> blocks) {
			super(data, direction, sticky);

			this.pushedBlocks = blocks;
		}

		public List<KBlock> getPushedBlocks() {
			return pushedBlocks;
		}
	}

	@Cancelable
	public static class Retract extends PistonEvent {

		protected KBlock retractBlock;

		public Retract(KBlock data, ForgeDirection direction, boolean sticky, KBlock retractLocation) {
			super(data, direction, sticky);

			this.retractBlock = retractLocation;
		}

		public KBlock getRetractBlock() {
			return retractBlock;
		}
	}
}
