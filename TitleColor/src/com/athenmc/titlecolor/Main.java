package com.athenmc.titlecolor;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.athenmc.titlecolor.commands.*;
import com.athenmc.titlecolor.events.Chat;
import com.athenmc.titlecolor.files.DataManager;

public class Main extends JavaPlugin implements Listener {
	
	private static DataManager data;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		data = new DataManager(this);
		
		getServer().getPluginManager().registerEvents(new Chat(), this);
		this.getCommand("addtitle").setExecutor(new AddPermission(this));
		this.getCommand("titlecreate").setExecutor(new CreateTitle(this));
		this.getCommand("titlecolor").setExecutor(new Title(this));
		this.getCommand("titleclear").setExecutor(new ClearTitle(this));
		this.getCommand("listtitles").setExecutor(new PrintTitles(this));
		
		getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
				"\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n Thanks for using TitleColor! \n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
	}
	
	public static FileConfiguration getData() {
		return data.getConfig();
	}
	
	public static void saveData() {
		data.saveConfig();
	}
	
}
