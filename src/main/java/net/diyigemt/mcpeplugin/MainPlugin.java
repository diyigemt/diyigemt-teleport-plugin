package net.diyigemt.mcpeplugin;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.diyigemt.mcpeplugin.command.TeleportBackCommand;
import net.diyigemt.mcpeplugin.command.TeleportHomeCommand;
import net.diyigemt.mcpeplugin.command.TeleportRequestCommand;
import net.diyigemt.mcpeplugin.command.TeleportSetHomeCommand;
import net.diyigemt.mcpeplugin.dao.HomeDAO;
import net.diyigemt.mcpeplugin.listener.EntityDeathEventListener;
import net.diyigemt.mcpeplugin.listener.PlayerDeathEventListener;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;
import net.diyigemt.mcpeplugin.sql.DatabaseHelper;

import java.io.File;

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
    File dataFolder = getDataFolder();
    new DatabaseHelper(dataFolder.getPath());
    HomeDAO.init();
    server.getPluginManager().registerEvents(new EntityDeathEventListener(), this);
    server.getPluginManager().registerEvents(new PlayerTeleportEventListener(), this);
    server.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
    server.getCommandMap().register("", new TeleportRequestCommand("tpa"), "tpa");
    server.getCommandMap().register("", new TeleportBackCommand("back"), "back");
    server.getCommandMap().register("", new TeleportSetHomeCommand("sethome"), "sethome");
    server.getCommandMap().register("", new TeleportHomeCommand("home"), "home");
  }
}
