package net.diyigemt.mcpeplugin.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class PlayerTeleportEventListener implements Listener {

  public static final Map<String, Vector3> teleportMap = new HashMap<>();


  @EventHandler
  public void onPlayerTeleport(PlayerTeleportEvent event) {
    String name = event.getPlayer().getName();
    Location from = event.getFrom();
    teleportMap.put(name, from);
  }
}
