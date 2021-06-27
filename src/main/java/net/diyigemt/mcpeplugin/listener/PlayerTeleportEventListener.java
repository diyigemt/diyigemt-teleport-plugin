package net.diyigemt.mcpeplugin.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Location;

import java.util.HashMap;
import java.util.Map;

public class PlayerTeleportEventListener implements Listener {

  public static final Map<String, Location> teleportMap = new HashMap<>();


  @EventHandler
  public void onPlayerTeleport(PlayerTeleportEvent event) {
    Location from = event.getFrom();
    String name = event.getPlayer().getName();
    teleportMap.put(name, from);
  }
}
