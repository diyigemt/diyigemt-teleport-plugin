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
import net.diyigemt.mcpeplugin.dao.SpawnDAO;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.entity.SpawnPosition;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;

import java.sql.SQLException;

import static net.diyigemt.mcpeplugin.command.TeleportSetSpawnPointCommand.MAIN_WORLD_SPAWN_NAME;

public class TeleportSpawnCommand extends VanillaCommand {

	public TeleportSpawnCommand(String name) {
		super(name, "/spawn 返回设置本世界的重生点", "/spawn 返回设置本世界的重生点");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!GeneralUtil.checkPlayer(sender)) return true;
		if (args.length > 1) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "多余参数将被忽略"));
		}
		SpawnPosition position = null;
		if (args.length == 1 && args[0].equals("main")) {
			handleTeleportMain(sender);
			return true;
		}
		String playerName = sender.getName();
		Server server = sender.getServer();
		Player player = server.getPlayerExact(playerName);
		try {
			position = new SpawnDAO().getSpawnLocation(player.getLevel().getName());
		} catch (SQLException e) {
			e.printStackTrace();
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "传送失败"));
			return true;
		}
		if (position == null) {
			handleTeleportMain(sender);
			return true;
		}
		Level level = server.getLevelByName(position.getLevelName());
		Position p = level.getSpawnLocation().setComponents(position.getPosX(), position.getPosY(), position.getPosZ());
		player.teleport(p);
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "传送至重生点:" + level +"成功"));
		return true;
	}

	private void handleTeleportMain(CommandSender sender) {
		SpawnPosition position = null;
		try {
			position = new SpawnDAO().getSpawnLocation(MAIN_WORLD_SPAWN_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "传送失败"));
			return;
		}
		if (position == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "传送失败, 没有找到重生点"));
			return;
		}
		String playerName = sender.getName();
		Server server = sender.getServer();
		Player player = server.getPlayerExact(playerName);
		Level level = server.getLevelByName(position.getLevelName());
		Position p = level.getSpawnLocation().setComponents(position.getPosX(), position.getPosY(), position.getPosZ());
		player.teleport(p);
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "传送至重生点:" + level +"成功"));
	}
}
