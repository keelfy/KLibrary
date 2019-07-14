package keelfy.klibrary.utils;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

/**
 * @author keelfy
 * @date 8 июн. 2019 г.
 */
public class KServerPlayer {

	protected final EntityPlayerMP player;
	protected final KLocation location;
	protected final String name;
	protected final boolean silenced;

	public KServerPlayer(EntityPlayerMP player) {
		this(player, false);
	}

	KServerPlayer(EntityPlayerMP player, boolean silenced) {
		this.player = player;
		this.location = new KLocation(player);
		this.name = player.getDisplayName();
		this.silenced = silenced;
	}

	public String getName() {
		return name;
	}

	public UUID getUniqueId() {
		return player.getUniqueID();
	}

	public boolean hasGroup(String group) {
		return false;// MPWarden.inGroup(player, group);
	}

	public KVector getPosition() {
		return location.getPosition();
	}

	public void kick(String msg) {
		if (!silenced) {
			player.playerNetServerHandler.kickPlayerFromServer(msg);
		}
	}

	public void ban(String msg) {
		if (!silenced) {
			player.playerNetServerHandler.kickPlayerFromServer(msg);
		}
	}

	public void printRaw(String msg) {
		if (!silenced) {
			player.addChatMessage(new ChatComponentText(msg));
		}
	}

	public EntityPlayerMP getPlayer() {
		return player;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof KServerPlayer && ((KServerPlayer) obj).getName().equals(getName());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
