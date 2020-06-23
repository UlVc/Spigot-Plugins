package io.diamondserver.deluxetagsseller;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.diamondserver.deluxetagsseller.commands.*;
import io.diamondserver.deluxetagsseller.event.CommandDetecter;
import io.diamondserver.deluxetagsseller.files.DataManager;

public class Main extends JavaPlugin {

	private static DataManager data;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		data = new DataManager(this);
		
		this.getServer().getPluginManager().registerEvents(new CommandDetecter(this), this);
		
		new AddPermission(this);
		new CheckCredits(this);
		new RemoveTag(this);
		
		getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
				"\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n Thanks for using DeluxeTagsSeller v1.1.0! \n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
	}
	
	public static FileConfiguration getData() {
		return data.getConfig();
	}
	
	public static void saveData() {
		data.saveConfig();
	}
	
}
