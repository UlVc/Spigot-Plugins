package com.athenmc.titlecolor.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.athenmc.titlecolor.Main;

public class CreateTitle implements CommandExecutor {

	private Main plugin;
	
	public CreateTitle(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("titlecreate")) {
			String noPermission = this.plugin.getConfig().getString("dont-have-permission");
			if (!sender.hasPermission("titlecolor.create")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return false;
			}
			String usage = this.plugin.getConfig().getString("titlecreate-usage");
			if (args.length != 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}

			try {
				String player = args[0];
				String titleName = args[1].replace("[", "").replace("]", "");
				String[] splitStr = titleName.split("");
				
				int countColors = 0;
				StringBuilder builder = new StringBuilder();
				
				for (int i = 0; i < splitStr.length; i++) {
					if (splitStr[i].equalsIgnoreCase("&")) {
						countColors += 2;
						i++;
						continue;
					}	
					builder.append(splitStr[i]);
				}
					
				if (splitStr.length - countColors > 9) {
					sender.sendMessage(ChatColor.RED + "The title display must be 10 characters or less");
					return false;
				}
				String id = player + "." + builder.toString().toLowerCase();
				FileConfiguration file = Main.getData();
		    	
		    	if (file.contains("users")) {
		    		boolean detecter = true;
		    		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		    		
		    		for (int i = 1; i <= var; i++)
		    			if (player.equalsIgnoreCase(file.getString("users." + i + ".username"))) {	    				
		    				file.set("users." + i + ".titles-can-use", file.getString("users." + i + ".titles-can-use") + ", " + id);
		    				file.set("users." + i + ".display-name", titleName);
		    				int lengthTitles = Main.getData().getConfigurationSection("titles").getKeys(false).size() + 1;
		    				file.set("titles." + lengthTitles + ".id", id);
		    				file.set("titles." + lengthTitles + ".display-name", titleName);
		    				Main.saveData();
		    				detecter = false;
		    				checkPlayers(player, id);
		    				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSuccessfully gave &r" + player + "&c the &6Title Color " + id));
		    				return true;
		    			}
							
		    		if (detecter) {
		    			file.set("users." + (var+1) + ".username", player);
		    			file.set("users." + (var+1) + ".titles-can-use", id);
		        		Main.saveData();
		        		checkPlayers(player, id);
		        		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSuccessfully gave &r" + player + "&c the &6Title Color " + id));
		        		return true;
		    		}
		    			
		        } else {
					file.set("users." + 1 + ".username", player);
					file.set("users." + 1 + ".titles-can-use", id);
		    		Main.saveData();
		    		checkPlayers(player, id);
		    		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSuccessfully gave &r" + player + "&c the &6Title Color " + id));
		    		return true;
				}
		    	
				return false;
			} catch (Exception e) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
		}
		
		return false;
	}
	
	private void checkPlayers(String name, String id) {
		String succes = this.plugin.getConfig().getString("succes");
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getName().equalsIgnoreCase(name)) {
				if (succes.contains("<id>"))
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', succes.replace("<id>", id)));
				else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
				return;
			}
	}
	
}
