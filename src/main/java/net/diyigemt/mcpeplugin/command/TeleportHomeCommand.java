package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.dao.HomeDAO;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;


public class TeleportHomeCommand extends VanillaCommand {

	public TeleportHomeCommand(String name) {
		super(name, "/home <home name>返回设置的家", "/home 返回到默认的家");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!GeneralUtil.checkPlayer(sender)) return true;
		if (args.length > 1) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "多余参数将被忽略"));
		}
		String homeName = HomeDAO.defaultHomeName;
		if (args.length == 1) {
			homeName = args[0];
		}
		String playerName = sender.getName();
		HomePosition homePosition = GeneralUtil.homeBaseInfo(sender, homeName);
		if (homePosition == null) return true;
		Server server = sender.getServer();
		Player player = server.getPlayerExact(playerName);
		if (player != null) {
			String levelName = homePosition.getLevelName();
			Level level = server.getLevelByName(levelName);
			if (level == null) {
				sender.sendMessage(new TranslationContainer(TextFormat.RED + "家所在的世界:" + levelName + " 已经不存在了"));
				return true;
			}
			Position p = level.getSpawnLocation().setComponents(homePosition.getPosX(), homePosition.getPosY(), homePosition.getPosZ());
			player.teleport(p);
			sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "传送至家:" + homeName + "(" + levelName + ")成功"));
		}
		return true;
	}
}
