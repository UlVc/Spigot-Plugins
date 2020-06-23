package com.athenmc.titlecolor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.athenmc.titlecolor.Main;

import net.md_5.bungee.api.ChatColor;

public class Title implements CommandExecutor {
	
	private Main plugin;
	
	public Title(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("titlecolor")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry Console, you can't use titles. D:");
				return false;
			}
			
			if (args.length > 2 || args.length == 0) {
				String usage = this.plugin.getConfig().getString("titlecolor-usage");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
			
			Player player = (Player) sender;
			
			if (!player.hasPermission("titlecolor.use")) {
				String noPermission = this.plugin.getConfig().getString("dont-have-permission");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return false;
			}

			try {
				String title = args[0];
				String color = "";
				
				if (args.length == 2) {
					color = args[1];
					
					if (!color.contains("&")) {
						player.sendMessage(ChatColor.RED + "Use formatting colors!");
						return false;
					}
				}
				
				FileConfiguration file = Main.getData();
				String buyTitle = this.plugin.getConfig().getString("buy-title");
		    	
		    	if (file.contains("users")) {
		    		boolean detecter = true;
		    		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		    		
		    		for (int i = 1; i <= var; i++)
		    			if (player.getName().equalsIgnoreCase(file.getString("users." + i + ".username"))) {
		    				String titles = file.getString("users." + i + ".titles-can-use");
		    				
		    				if (!titles.contains(title)) {
		    					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', buyTitle));
				        		return false;
		    				}
		    				
		    				file.set("users." + i + ".title-activated", title);
		    				if (!color.equals(""))
		    					file.set("users." + i + ".color", color);
		    				Main.saveData();
		    				String activated = this.plugin.getConfig().getString("title-activated");
		    				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', activated));
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
				String usage = this.plugin.getConfig().getString("titlecolor-usage");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
		}
		
		return false;
	}
	
}
