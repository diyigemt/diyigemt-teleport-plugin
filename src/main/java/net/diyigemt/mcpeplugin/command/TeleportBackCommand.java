package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;

public class TeleportBackCommand extends VanillaCommand {

	public TeleportBackCommand(String name) {
		super(name, "/back 返回到上一次传送的位置", "/back 返回到上一次传送的位置");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "请以玩家身份调用"));
			return true;
		}
		if (args.length != 0) return false;
		String senderName = sender.getName();
		Player player = sender.getServer().getPlayerExact(senderName);
		if (player == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "未找到目标"));
			return true;
		}
		Vector3 lastPos = PlayerTeleportEventListener.teleportMap.get(senderName);
		if (lastPos == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "没有上一次传送地点记录"));
			return true;
		}
		sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "返回上一次传送点... x:" + lastPos.getX() + " y:" + lastPos.getY() + " z:" + lastPos.getZ()));
		player.sendPosition(lastPos);
		return true;
	}
}
