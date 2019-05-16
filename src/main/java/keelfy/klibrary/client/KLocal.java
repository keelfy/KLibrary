package keelfy.klibrary.client;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * Localization utilities.
 * 
 * @author keelfy
 */
@SideOnly(Side.CLIENT)
public final class KLocal {

	/**
	 * Gets localized string and format '&' symbols to color codes.
	 * 
	 * @param localizationCode localization string code {@link I18n#format}
	 * @param objects          variables used in message
	 * @return formatted string
	 */
	public static String format(String localizationCode, Object... objects) {
		return I18n.format(localizationCode, objects).replace('&', '§');
	}

	/**
	 * Sends message to player by localization code.
	 * 
	 * @see {@link KLocal#format}
	 * 
	 * @param player           recipient
	 * @param localizationCode localization string code {@link I18n#format}
	 * @param objects          variables used in message
	 */
	public static void sendLocalizedMessage(EntityPlayer player, String localizationCode, Object... objects) {
		player.addChatMessage(new ChatComponentText(format(localizationCode, objects)));
	}
}
