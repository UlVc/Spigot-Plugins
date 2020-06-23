package me.ulrich.animatedtab;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public TabManager tab;
	
	@Override
	public void onEnable() {
		this.tab = new TabManager(this);
		tab.addHeader("&6&lUlrich\n&c&l:)");
		tab.addHeader("&6&lUlrich\n&c&l:))");
		tab.addHeader("&6&lUlrich\n&c&l:D");
		
		tab.addFooter("&b&lPlayers online: &5" + Bukkit.getOnlinePlayers().size());
		tab.showTab();
	}
	
}
