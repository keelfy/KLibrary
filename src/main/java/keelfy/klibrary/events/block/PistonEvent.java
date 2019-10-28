package keelfy.klibrary.events.block;

import java.util.List;

import cpw.mods.fml.common.eventhandler.*;
import keelfy.klibrary.utils.KBlock;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 * @date 5 июн. 2019 г.
 */
public class PistonEvent extends Event {

	protected boolean sticky;
	protected KBlock pistonBlock;
	protected ForgeDirection direction;

	public PistonEvent(KBlock pistonBlock, ForgeDirection direction, boolean sticky) {
		this.pistonBlock = pistonBlock;
		this.direction = direction;
		this.sticky = sticky;
	}

	public ForgeDirection getDirection() {
		return direction;
	}

	public boolean isSticky() {
		return sticky;
	}

	public KBlock getPiston() {
		return pistonBlock;
	}

	@Cancelable
	public static class Extend extends PistonEvent {

		protected List<KBlock> pushedBlocks;

		public Extend(KBlock pistonBlock, ForgeDirection direction, boolean sticky, List<KBlock> blocks) {
			super(pistonBlock, direction, sticky);

			this.pushedBlocks = blocks;
		}

		public List<KBlock> getPushedBlocks() {
			return pushedBlocks;
		}
	}

	@Cancelable
	public static class Retract extends PistonEvent {

		protected KBlock retractBlock;

		public Retract(KBlock pistonBlock, ForgeDirection direction, boolean sticky, KBlock retractLocation) {
			super(pistonBlock, direction, sticky);

			this.retractBlock = retractLocation;
		}

		/**
		 * Only for sticky pistons
		 */
		public KBlock getRetractBlock() {
			return retractBlock;
		}
	}

}
