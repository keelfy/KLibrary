package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.events.entity.KEntityEvent;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 */
public class HangingEvent extends KEntityEvent {

	public HangingEvent(Entity entity) {
		super(entity);
	}

	@Cancelable
	public static class Break extends HangingEvent {

		private DamageSource source;

		public Break(Entity entity, DamageSource sourceOfDamage) {
			super(entity);

			this.source = sourceOfDamage;
		}

		public DamageSource getSource() {
			return source;
		}
	}

	@Cancelable
	public static class Place extends HangingEvent {

		private KBlock block;
		private ForgeDirection direction;
		private EntityPlayer player;

		public Place(Entity entity, KBlock block, ForgeDirection direction, EntityPlayer player) {
			super(entity);

			this.block = block;
			this.direction = direction;
			this.player = player;
		}

		public EntityPlayer getPlayer() {
			return player;
		}

		public KBlock getLocatedBlock() {
			return block;
		}

		public ForgeDirection getDirection() {
			return direction;
		}
	}

}
