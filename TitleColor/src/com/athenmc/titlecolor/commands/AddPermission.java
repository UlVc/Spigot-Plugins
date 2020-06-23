package com.athenmc.titlecolor.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.athenmc.titlecolor.Main;

public class AddPermission implements CommandExecutor {
	
	private Main plugin;
	
	public AddPermission(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("addtitle")) {
			String noPermission = this.plugin.getConfig().getString("dont-have-permission");
			
			if (!sender.hasPermission("titlecolor.addtitle")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return false;
			}
			
			String usage = this.plugin.getConfig().getString("addtitle-usage");
			
			if (args.length != 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return false;
			}
			
			try {
				String player = args[0];
				String id = args[1];
				FileConfiguration file = Main.getData();
		    	
		    	if (file.contains("users")) {
		    		boolean detecter = true;
		    		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		    		
		    		for (int i = 1; i <= var; i++)
		    			if (player.equalsIgnoreCase(file.getString("users." + i + ".username"))) {	    				
		    				file.set("users." + i + ".titles-can-use", file.getString("users." + i + ".titles-can-use") + ", " + id);
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
