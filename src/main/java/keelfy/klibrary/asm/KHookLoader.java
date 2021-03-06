package keelfy.klibrary.asm;

import gloomyfolken.hooklib.minecraft.*;

/**
 * @author keelfy
 */
public final class KHookLoader extends HookLoader {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { PrimaryClassTransformer.class.getName() };
	}

	@Override
	public void registerHooks() {
		registerHookContainer(KCommonHooks.class.getName());
		registerHookContainer(KClientHooks.class.getName());
		registerHookContainer(KServerHooks.class.getName());
	}
}
