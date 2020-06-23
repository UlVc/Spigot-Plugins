package io.diamondserver.deluxetagsseller.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.PermissionAttachment;

import io.diamondserver.deluxetagsseller.Main;

public class CommandDetecter implements Listener {
	
	private Main plugin;
	
	public CommandDetecter(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void commandMessage(PlayerCommandPreprocessEvent event) {		
		if (event.getMessage().equalsIgnoreCase("/tags create") || !event.getMessage().contains("/tags create"))
			return;
		
		FileConfiguration file = Main.getData();
		Player player = event.getPlayer();
		PermissionAttachment attachment = player.addAttachment(plugin);
		
		String str = event.getMessage();
		String[] splitStr = str.split("\\s+");
		
		if (!(splitStr.length > 3))
			return;
		
		String id = splitStr[2];	
		int countSpaces = 0;
		StringBuilder builder = new StringBuilder();
		
		for (String s : splitStr) {
			if (s.equalsIgnoreCase("/tags") || s.equalsIgnoreCase("create") || s.equalsIgnoreCase(id))
				continue;
			builder.append(s);
			countSpaces += 1;
		}
		
		String newStr = builder.toString();
		int count = 0;
		
		char[] charArray = newStr.toCharArray();
		
		for (int i = 0; i < charArray.length; i++)
			if (charArray[i] == '&')
				count += 1;
		
		if (charArray.length + countSpaces - 2*count > 11) {
			
			String max = this.plugin.getConfig().getString("creating-tag-max");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', max));
			event.setCancelled(true);
			return;
		}

		if (file.contains("users")) {
			
			int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
    		
			boolean isPlayer = false;
			
    		for (int i = 1; i <= var; i++)
    			if (file.getString("users." + i + ".username").equalsIgnoreCase(player.getName())) {
    				isPlayer = true;
    				break;
    			}
    		
    		if (!isPlayer) {
    			String buy = this.plugin.getConfig().getString("creating-tag-buy");
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', buy));
    			event.setCancelled(true);
    			return;
    		}
			
			file.getConfigurationSection("users").getKeys(false).forEach(key -> {
				
				if (file.getInt("users." + key + ".amount") > 0 && 
						file.getString("users." + key + ".username").equalsIgnoreCase(player.getName())) {
					
					attachment.setPermission("deluxetags.create", true);
					attachment.setPermission("deluxetags.gui", true);
					attachment.setPermission("deluxetags.tag." + id, true);
					
					Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
						
						@Override
						public void run() {
							attachment.setPermission("deluxetags.create", false);
						}
						
					}, 0, 2);
					
					int amount = file.getInt("users." + key + ".amount") - 1;
					
					if (amount == 0) {
						file.set("users." + key, null);
						Main.saveData();
						
						return;
					} else {
						file.set("users." + key + ".amount", amount);
						Main.saveData();
						
						return;					
					}
					
				}
				
			});
		} else {
			String buy = this.plugin.getConfig().getString("creating-tag-buy");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', buy));
			event.setCancelled(true);
			return;
		}
	}

}
