package com.athenmc.titlecolor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.athenmc.titlecolor.Main;

import net.md_5.bungee.api.ChatColor;

public class ClearTitle implements CommandExecutor {
	
	private Main plugin;
	
	public ClearTitle(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("titleclear")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry Console, you can't use titles. D:");
				return false;
			}
			String usage = this.plugin.getConfig().getString("titleclear-usage");
			if (args.length != 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
			
			Player player = (Player) sender;
			String noPermission = this.plugin.getConfig().getString("dont-have-permission");
			if (!player.hasPermission("titlecolor.clear")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return false;
			}
			
			try {
				FileConfiguration file = Main.getData();
		    	
		    	if (file.contains("users")) {
		    		boolean detecter = true;
		    		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		    		
		    		for (int i = 1; i <= var; i++)
		    			if (player.getName().equalsIgnoreCase(file.getString("users." + i + ".username"))) {
		    				if (file.getString("users." + i + ".title-activated") == null) {
		    					String notPossible = this.plugin.getConfig().getString("titleclear-not-titles-activated");
				    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', notPossible));
		    					return false;
		    				}
		    				file.set("users." + i + ".title-activated", null);
		    				Main.saveData();
		    				String cleared = this.plugin.getConfig().getString("titleclear-cleared");
		    				player.sendMessage(ChatColor.translateAlternateColorCodes('&', cleared));
		    				return true;
		    			}
							
		    		if (detecter) {
		    			String notPossible = this.plugin.getConfig().getString("titleclear-not-titles-activated");
		    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', notPossible));
		        		return false;
		    		}
		    			
		        } else {
		        	String notPossible = this.plugin.getConfig().getString("titleclear-not-titles-activated");
	    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', notPossible));
	        		return false;
				}
		    	
				return false;
			} catch (Exception e) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
		}
		
		return false;
	}
	
}
