package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.TextFormat;
import com.google.common.collect.ImmutableList;
import net.diyigemt.mcpeplugin.MainPlugin;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;
import net.diyigemt.mcpeplugin.task.RemoveListTask;
import net.diyigemt.mcpeplugin.utils.GeneralUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TeleportRequestCommand extends VanillaCommand {
	private static final Map<String, List<String>> teleportRequestMap = new HashMap<>();

	public TeleportRequestCommand(String name) {
		super(name, "/tpa <name>|<ac|re> 请求传送到玩家|接受/拒绝请求", "/tpa aaa 请求传送到玩家aaa处");
		this.commandParameters.clear();
		this.commandParameters.put("->player", new CommandParameter[]{CommandParameter.newType("player", CommandParamType.TARGET)});
		this.commandParameters.put("->accept", new CommandParameter[]{CommandParameter.newEnum("operate", new CommandEnum("ac接受rj拒绝", ImmutableList.of("ac", "rj")))});
	}

	private void handleTeleportRequest(CommandSender sender, String targetName) {
		String senderName = sender.getName();
		if (senderName.equals(targetName)) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "不能向自己发送请求"));
			return;
		}
		Server server = sender.getServer();
		Player targetPlayer = server.getPlayerExact(targetName);
		if (targetPlayer == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "玩家:" + targetName + " 不存在或不在线"));
			return;
		}

		String infoString = "玩家:" + senderName + " 想要传送到你的位置";
		targetPlayer.sendMessage(new TranslationContainer(TextFormat.YELLOW + infoString));
		targetPlayer.sendMessage(new TranslationContainer(TextFormat.YELLOW + "请在15s内, 输入/tpa ac 接受, /tpa rj 拒绝"));
		List<String> targetNames = teleportRequestMap.get(targetName);
		if (targetNames == null) targetNames = new ArrayList<>();
		targetNames.add(senderName);
		teleportRequestMap.put(targetName, targetNames);
		server.getScheduler().scheduleDelayedTask(new RemoveListTask<>(MainPlugin.INSTANCE, targetNames, targetName, (list, value) -> {
			if (list.isEmpty()) {
				teleportRequestMap.remove(value);
			}
			sender.sendMessage(new TranslationContainer(TextFormat.YELLOW + "传送请求没有回应"));
		}), 20 * 15);
	}

	private boolean handleTeleportAccept(CommandSender sender) {
		List<String> targetNames = beforeAction(sender);
		if (targetNames == null) {
			return true;
		}
		String senderName = sender.getName();
		Server server = sender.getServer();
		Player senderPlayer = server.getPlayerExact(senderName);
		for (String targetName : targetNames) {
			Player targetPlayer = server.getPlayerExact(targetName);
			if (targetPlayer == null) {
				sender.sendMessage(new TranslationContainer(TextFormat.RED + "发送请求的目标不存在或已下线"));
				continue;
			}
			GeneralUtil.setBackLocation(targetName, targetPlayer.getLocation());
			targetPlayer.teleport(senderPlayer.getLocation());
		}
		teleportRequestMap.remove(senderName);
		return true;
	}

	private boolean handleTeleportReject(CommandSender sender) {
		List<String> targetNames = beforeAction(sender);
		if (targetNames == null) {
			return true;
		}
		String senderName = sender.getName();
		Server server = sender.getServer();
		for (String targetName : targetNames) {
			Player targetPlayer = server.getPlayerExact(targetName);
			if (targetPlayer == null) {
				sender.sendMessage(new TranslationContainer(TextFormat.RED + "发送请求的目标不存在或已下线"));
				continue;
			}
			targetPlayer.sendMessage(new TranslationContainer(TextFormat.RED + "目标拒绝了你的传送请求"));
		}
		teleportRequestMap.remove(senderName);
		return true;
	}

	private List<String> beforeAction(CommandSender sender) {
		String senderName = sender.getName();
		List<String> targetNames = teleportRequestMap.get(senderName);
		if (targetNames == null) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "传送请求不存在或已过期"));
		}
		return targetNames;
	}

	@Override
	public boolean execute(CommandSender sender, String s, String[] args) {
		if (!GeneralUtil.checkPlayer(sender)) return true;
		if (args.length != 1) {
			sender.sendMessage(new TranslationContainer(TextFormat.RED + "参数不能为空"));
			return false;
		}
		String target = args[0];
		switch (target) {
			case "ac": {
				return handleTeleportAccept(sender);
			}
			case "rj": {
				return handleTeleportReject(sender);
			}
			default: {
				handleTeleportRequest(sender, target);
			}
		}
		return true;
	}
}
