package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;

public class TeleportBackCommand extends VanillaCommand {

	public TeleportBackCommand(String name) {
		super(name, "/back 返回到上一次传送的位置", "/back 返回到上一次传送的位置");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!GeneralUtil.checkPlayer(sender)) return true;
		if (args.length != 0) return false;
		String senderName = sender.getName();
		Player player = sender.getServer().getPlayerExact(senderName);
		if (player == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "未找到目标"));
			return true;
		}
		Location lastPos = PlayerTeleportEventListener.teleportMap.get(senderName);
		if (lastPos == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "没有上一次传送地点记录"));
			return true;
		}
		player.teleport(lastPos);
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "返回上一次传送点... level:" + lastPos.getLevel().getName() + " x:" + lastPos.getX() + " y:" + lastPos.getY() + " z:" + lastPos.getZ()));
		return true;
	}
}
