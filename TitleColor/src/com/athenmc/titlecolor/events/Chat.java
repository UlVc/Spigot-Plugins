package com.athenmc.titlecolor.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.athenmc.titlecolor.Main;

public class Chat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		FileConfiguration file = Main.getData();
		
		if (!file.contains("users"))
			return;
		
		String titleActivated = "";
		String colorFormat = "";
		
		int var = Main.getData().getConfigurationSection("users").getKeys(false).size();
		boolean detecter = true;
		
		for (int i = 1; i <= var; i++)
			if (file.getString("users." + i + ".username").equalsIgnoreCase(event.getPlayer().getName())) {
				titleActivated = file.getString("users." + i + ".title-activated");
				if (file.getString("users." + i + ".color") != null)
					colorFormat = file.getString("users." + i + ".color");
				if (titleActivated == null)
					break;
				detecter = false;
				break;
			}

		if (detecter)
			return;
		
		int var1 = Main.getData().getConfigurationSection("titles").getKeys(false).size();
		String title = "";
		
		for (int i = 1; i <= var1; i++)
			if (file.getString("titles." + i + ".id").equalsIgnoreCase(titleActivated)) {
				title = file.getString("titles." + i + ".display-name");
				break;
			}

		if (!title.equalsIgnoreCase("")) {
			String newString = "";
			if (colorFormat.equalsIgnoreCase(""))
				newString = event.getFormat().replace("%1$s", format("&8[&r" + colorFormat + title + "&8]&r ") + "%1$s");
			else {
				String[] splitStr = title.split("");
				StringBuilder builder = new StringBuilder();
				
				for (int i = 0; i < splitStr.length; i++) {
					if (splitStr[i].equalsIgnoreCase("&")) {
						i++;
						continue;
					}	
					builder.append(splitStr[i]);
				}
				newString = event.getFormat().replace("%1$s", format("&8[&r" + colorFormat + builder + "&8]&r ") + "%1$s");
			}
			event.setFormat(newString);
		}
			
	}
	
	private String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}
