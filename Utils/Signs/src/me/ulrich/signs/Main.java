package me.ulrich.signs;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new SignEvents(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
