package me.Ulrich.clickablemessage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Heal implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("Doctor")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe console is healed now!"));
				return true;
			}
			
			Player player = (Player) sender;
			if (!player.hasPermission("Doctor.use")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou can't use this command!"));
				return true;
			}
			
			if (args.length == 0) {
					TextComponent message = new TextComponent("Would you like to be healed?");
					message.setColor(ChatColor.GOLD);
					message.setBold(true);
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/doctor healme")); //Runs from the player (using /heal is a bad idea cause usually the players dont have acces to that command)
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
							new ComponentBuilder("Click here to be healed!").color(ChatColor.GRAY).italic(true).create())); // When the cursor is on the text.
					player.spigot().sendMessage(message);
					return true;
			}
			
			if (args[0].equalsIgnoreCase("healme")) { // Two ways:
				player.setHealth(20.0);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + player.getName());
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lYou've been healed!"));
				return true;
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUsage /Doctor"));
			return true;
		}
		return false;
	}
	
}
