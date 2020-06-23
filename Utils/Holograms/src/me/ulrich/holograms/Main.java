package me.ulrich.holograms;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("hologram").setExecutor(new Hologram());
	}
	
}
