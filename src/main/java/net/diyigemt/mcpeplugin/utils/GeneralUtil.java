package net.diyigemt.mcpeplugin.utils;

import cn.nukkit.level.Location;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;

public class GeneralUtil {
	public static void setBackLocation(String playerName, Location location) {
		PlayerTeleportEventListener.teleportMap.put(playerName, location);
	}
}
