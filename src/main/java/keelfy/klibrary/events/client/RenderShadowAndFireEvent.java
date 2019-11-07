package keelfy.klibrary.events.client;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.events.entity.KEntityEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

/**
 * Called when minecraft draws fire and shadow on entity.
 * 
 * @author keelfy
 */
public class RenderShadowAndFireEvent extends KEntityEvent {

	private final Render render;

	public RenderShadowAndFireEvent(Render render, Entity entity) {
		super(entity);

		this.render = render;
	}

	/**
	 * Called before drawing shadow and fire.
	 */
	@Cancelable
	public static class Pre extends RenderShadowAndFireEvent {

		public Pre(Render render, Entity entity) {
			super(render, entity);
		}

	}

	/**
	 * Called after drawing shadow and fire.
	 */
	public static class Post extends RenderShadowAndFireEvent {

		public Post(Render render, Entity entity) {
			super(render, entity);
		}
	}
}
