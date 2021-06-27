package net.diyigemt.mcpeplugin.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;

public class PlayerDeathEventListener implements Listener {

  @EventHandler
  public void onPlayerKillAnimal(PlayerDeathEvent event) {
    Player player = event.getEntity();
    GeneralUtil.setBackLocation(player.getName(), player.getLocation());
  }
}
