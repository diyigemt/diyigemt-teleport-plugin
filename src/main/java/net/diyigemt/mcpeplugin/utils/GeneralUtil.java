package net.diyigemt.mcpeplugin.utils;

import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.dao.HomeDAO;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;

import java.sql.SQLException;
import java.util.List;

public class GeneralUtil {
	public static void setBackLocation(String playerName, Location location) {
		PlayerTeleportEventListener.teleportMap.put(playerName, location);
	}

	public static HomePosition homeBaseInfo(CommandSender sender, String homeName) {
		String playerName = sender.getName();
		HomePosition homePosition = null;
		try {
			homePosition = new HomeDAO().getHome(playerName, homeName);
		} catch (SQLException e) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "获取家信息失败"));
			e.printStackTrace();
			return null;
		}
		if (homePosition == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "家:" + homeName + " 不存在"));
			try {
				List<HomePosition> allHome = new HomeDAO().getAllHome(playerName);
				if (allHome != null && allHome.size() > 0) {
					StringBuilder sb = new StringBuilder("已有的家:\n");
					for (HomePosition home : allHome) {
						sb.append(home.getHomeName())
								.append("(")
								.append(home.getLevelName())
								.append(")\n");
					}
					sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + sb.toString()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return homePosition;
	}
}
