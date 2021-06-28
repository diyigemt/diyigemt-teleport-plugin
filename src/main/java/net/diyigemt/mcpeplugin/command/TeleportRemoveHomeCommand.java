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

import java.sql.SQLException;
import java.util.List;

public class TeleportRemoveHomeCommand extends VanillaCommand {

	public TeleportRemoveHomeCommand(String name) {
		super(name, "/removehome <home name>删除一个家", "/removehome home 删除默认的家");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!GeneralUtil.checkPlayer(sender)) return true;
		if (args.length == 0) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "请输入家的名称"));
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "多余参数将被忽略"));
		}
		String homeName = args[0];
		HomePosition homePosition = GeneralUtil.homeBaseInfo(sender, homeName);
		if (homePosition == null) return true;
		try {
			int i = new HomeDAO().removeHome(homePosition);
			if (i != 1) {
				sender.sendMessage(new TranslationContainer(TextFormat.RED + "删除失败"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "删除失败"));
		}
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "家:" + homeName + "(" + homePosition.getLevelName() + ")删除成功"));
		return true;
	}
}
