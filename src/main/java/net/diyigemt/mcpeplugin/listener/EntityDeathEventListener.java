package net.diyigemt.mcpeplugin.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.passive.EntityAnimal;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import cn.nukkit.item.Item;

public class EntityDeathEventListener implements Listener {

  @EventHandler
  public void onPlayerKillAnimal(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityAnimal)) return;
    EntityDamageEvent cause = entity.getLastDamageCause();
    if (cause instanceof EntityDamageByEntityEvent) {
      Entity damager = ((EntityDamageByEntityEvent) cause).getDamager();
      if (damager instanceof Player) {
        Item[] drops = event.getDrops();
        for (Item item : drops) {
          item.setCount(item.getCount() * 2);
        }
      }
    }
  }
}
