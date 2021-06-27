package net.diyigemt.mcpeplugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.diyigemt.mcpeplugin.listener.PlayerTeleportEventListener;

public class TeleportHomeCommand extends VanillaCommand {

	public TeleportHomeCommand(String name) {
		super(name, "/home 返回设置的家", "/home 返回到默认的家");
		this.commandParameters.clear();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		return true;
	}
}
