package net.diyigemt.mcpeplugin.commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.VanillaCommand;


public class TransportCommand extends VanillaCommand {

	public TransportCommand(String name) {
		super(name, "/tpa <name>|<ac|re> 请求传送到玩家|接受/拒绝请求", "/tpa aaa 请求传送到玩家aaa处");
		this.commandParameters.clear();
		this.commandParameters.put("->Player", new CommandParameter[]{CommandParameter.newType("target", CommandParamType.TARGET)});
		this.commandParameters.put("->accept", new CommandParameter[]{CommandParameter.newType("ac", CommandParamType.OPERATOR)});
		this.commandParameters.put("->reject", new CommandParameter[]{CommandParameter.newType("rj", CommandParamType.OPERATOR)});
	}

	private void handleTransportRequest(Server server, String targetName, String senderName) {
		Player targetPlayer = server.getPlayerExact(targetName);
		targetPlayer.sendMessage(senderName + "想要传送到你的位置");
	}

	@Override
	public boolean execute(CommandSender sender, String s, String[] args) {
		if (args.length != 1) return false;
		String senderName = sender.getName();
		String target = args[0];
		switch (target) {
			case "ac": {
				break;
			}
			case "rj": {
				break;
			}
			default: {
				handleTransportRequest(sender.getServer(), target, senderName);
			}
		}
		return true;
	}
}
