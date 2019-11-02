package keelfy.klibrary.asm;

import cpw.mods.fml.relauncher.*;
import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.client.render.RenderShadowAndFireEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public final class KClientHooks {

	/**
	 * Calling {@link RenderShadowAndFireEvent.Pre}.
	 */
	@SideOnly(Side.CLIENT)
	@Hook(isMandatory = true, returnCondition = ReturnCondition.ON_TRUE)
	public static boolean doRenderShadowAndFire(Render render, Entity entity, double x, double y, double z, float yaw, float partialTicks) {
		return MinecraftForge.EVENT_BUS.post(new RenderShadowAndFireEvent.Pre(render, entity));
	}

	/**
	 * Calling {@link RenderShadowAndFireEvent.Post}.
	 */
	@SideOnly(Side.CLIENT)
	@Hook(isMandatory = true, injectOnExit = true, targetMethod = "doRenderShadowAndFire")
	public static void doRenderShadowAndFire1(Render render, Entity entity, double x, double y, double z, float yaw, float partialTicks) {
		MinecraftForge.EVENT_BUS.post(new RenderShadowAndFireEvent.Post(render, entity));
	}

}
