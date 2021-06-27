package net.diyigemt.mcpeplugin;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.diyigemt.mcpeplugin.command.TeleportBackCommand;
import net.diyigemt.mcpeplugin.command.TeleportRequestCommand;
import net.diyigemt.mcpeplugin.listener.EntityDeathEventListener;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;

public class MainPlugin extends PluginBase {

  public static MainPlugin INSTANCE;

  @Override
  public void onLoad() {
    INSTANCE = this;
  }

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    Server server = this.getServer();
    server.getPluginManager().registerEvents(new EntityDeathEventListener(), this);
    server.getPluginManager().registerEvents(new PlayerTeleportEventListener(), this);
    server.getCommandMap().register("", new TeleportRequestCommand("tpa"), "tpa");
    server.getCommandMap().register("", new TeleportBackCommand("back"), "back");
  }
}
