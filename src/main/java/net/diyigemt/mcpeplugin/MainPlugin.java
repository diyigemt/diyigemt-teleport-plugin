package net.diyigemt.mcpeplugin;

import cn.nukkit.plugin.PluginBase;
import net.diyigemt.mcpeplugin.listeners.EntityDeathEventListener;

public class MainPlugin extends PluginBase {

  @Override
  public void onLoad() {

  }

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    this.getServer().getPluginManager().registerEvents(new EntityDeathEventListener(), this);
  }
}
