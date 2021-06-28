package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.dao.HomeDAO;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;

import java.sql.SQLException;

public class TeleportSetHomeCommand extends VanillaCommand {

	public TeleportSetHomeCommand(String name) {
		super(name, "/sethome <home name>设置的家", "/sethome 设置名称为home的家");
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
		HomePosition homePosition = GeneralUtil.homeBaseInfo(sender, homeName);
		if (homePosition != null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "家" + homePosition.getHomeName() + "(" + homePosition.getLevelName() + ")已经存在"));
			return true;
		}
		String playerName = sender.getName();
		Server server = sender.getServer();
		Player player = server.getPlayerExact(playerName);
		Location location = player.getLocation();
		try {
			new HomeDAO().setHome(playerName, homeName, location);
		} catch (SQLException e) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "设置家失败"));
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer("设置家:" + homeName + "(");
		String levelName = location.getLevel().getName();
		sb.append(levelName)
				.append(", x:")
				.append(location.getFloorX())
				.append(", y:")
				.append(location.getFloorY())
				.append(", z:")
				.append(location.getFloorZ());
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + sb.toString() + ") 成功"));
		return true;
	}
}
