package me.ulrich.staffchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ulrich.staffchat.Main;
import net.md_5.bungee.api.ChatColor;

public class staffChat implements CommandExecutor {

	public staffChat(Main plugin) {
		plugin.getCommand("tc").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You can't chat, console!");
			return false;
		}
		
		Player player = (Player) sender;
		
		if (!(player.hasPermission("staffchat.use"))) {
			sender.sendMessage(ChatColor.RED + "You can't chat there!");
			return false;
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Message can't be empty!");
			return false;
		}
		
		String format = "&bTC" + player.getDisplayName() + " &6>> ";
		
		for (String s : args)
			format += s + " ";
		
		for (Player p : Bukkit.getOnlinePlayers())		
			if (p.hasPermission("staffchat.use"))
				p.sendMessage(format);

		
		return false;
	}

}
