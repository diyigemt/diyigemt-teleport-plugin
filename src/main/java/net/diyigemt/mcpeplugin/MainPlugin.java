package net.diyigemt.mcpeplugin;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.diyigemt.mcpeplugin.commands.TransportCommand;
import net.diyigemt.mcpeplugin.listeners.EntityDeathEventListener;

public class MainPlugin extends PluginBase {

  @Override
  public void onLoad() {

  }

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    Server server = this.getServer();
    server.getPluginManager().registerEvents(new EntityDeathEventListener(), this);
    server.getCommandMap().register("", new TransportCommand("tpa"), "tpa");
  }
}
