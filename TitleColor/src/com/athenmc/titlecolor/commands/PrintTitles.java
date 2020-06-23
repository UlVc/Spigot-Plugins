package com.athenmc.titlecolor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.athenmc.titlecolor.Main;

import net.md_5.bungee.api.ChatColor;

public class PrintTitles implements CommandExecutor {
	
	private Main plugin;
	
	public PrintTitles(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("listtitles")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry Console, you can't use titles. D:");
				return false;
			}
			
			String usage = this.plugin.getConfig().getString("listtitles-usage");
			if (args.length != 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
			
			Player player = (Player) sender;
			
			if (!player.hasPermission("titlecolor.listtitles")) {
				String noPermission = this.plugin.getConfig().getString("dont-have-permission");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return false;
			}
			
			try {					
				FileConfiguration file = Main.getData();
		    	String buyTitle = this.plugin.getConfig().getString("buy-title");
				
		    	if (file.contains("users")) {
		    		boolean detecter = true;
		    		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		    		
		    		for (int i = 1; i <= var; i++)
		    			if (player.getName().equalsIgnoreCase(file.getString("users." + i + ".username"))) {
		    				String titles = file.getString("users." + i + ".titles-can-use");
		    				String availableTitles = this.plugin.getConfig().getString("available-titles");
		    				
		    				if (availableTitles.contains("<titles>"))
		    					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', availableTitles.replace("<titles>", titles)));
		    				else
		    					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', availableTitles));
		    				
		    				return true;
		    			}
							
		    		if (detecter) {
		        		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', buyTitle));
		        		return false;
		    		}
		    			
		        } else {
		        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', buyTitle));
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
