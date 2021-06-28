package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import com.google.common.collect.ImmutableList;
import net.diyigemt.mcpeplugin.dao.SpawnDAO;

import java.sql.SQLException;

public class TeleportSetSpawnPointCommand extends VanillaCommand {

  public static final String MAIN_WORLD_SPAWN_NAME = "main_world_spawn_point";

  public TeleportSetSpawnPointCommand(String name) {
    super(name, "/setspawn 设置该世界的出生点", "/setspawn 设置该世界的出生点");
    this.commandParameters.clear();
    this.commandParameters.put("main", new CommandParameter[]{CommandParameter.newEnum("main", new CommandEnum("main", ImmutableList.of("main")))});
    this.commandParameters.put("pos", new CommandParameter[]{CommandParameter.newType("pos", CommandParamType.POSITION)});
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    if (!sender.isPlayer()) {
      sender.sendMessage(new TranslationContainer(TextFormat.RED + "请以玩家身份调用"));
      return true;
    }
    String playerName = sender.getName();
    Server server = sender.getServer();
    Player player = server.getPlayerExact(playerName);
    if (!player.isOp()) {
      sender.sendMessage(new TranslationContainer(TextFormat.RED + "无权调用"));
    }
    Location location = player.getLocation();
    switch (args.length) {
      case 0: {
        try {
          new SpawnDAO().setSpawnLocation(location);
        } catch (SQLException e) {
          e.printStackTrace();
          sender.sendMessage(new TranslationContainer(TextFormat.RED + "设置失败, 数据库出错"));
        }
        sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "设置重生点 " + location.getLevel().getName() + " 成功"));
        break;
      }
      case 1: {
        if (!args[0].equals("main")) break;
        try {
          new SpawnDAO().setSpawnLocation(location, MAIN_WORLD_SPAWN_NAME);
        } catch (SQLException e) {
          e.printStackTrace();
          sender.sendMessage(new TranslationContainer(TextFormat.RED + "设置失败, 数据库出错"));
        }
        sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "设置重生点 main 成功"));
        break;
      }
      case 3: {
        Location loc = new Location(Math.round(Integer.parseInt(args[0])),
            Math.round(Integer.parseInt(args[1])),
            Math.round(Integer.parseInt(args[2])));
        String levelName = player.getLevel().getName();
        try {
          new SpawnDAO().setSpawnLocation(loc, levelName);
        } catch (SQLException e) {
          e.printStackTrace();
          sender.sendMessage(new TranslationContainer(TextFormat.RED + "设置失败, 数据库出错"));
        }
        sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "设置重生点 " + levelName + " 成功"));
        break;
      }
      default: {
        sender.sendMessage(new TranslationContainer(TextFormat.RED + "参数不正确"));
      }
    }
    return true;
  }
}
