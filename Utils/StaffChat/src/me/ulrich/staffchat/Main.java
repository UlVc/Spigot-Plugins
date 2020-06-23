package me.ulrich.staffchat;

import org.bukkit.plugin.java.JavaPlugin;

import me.ulrich.staffchat.commands.staffChat;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		new staffChat(this);
	}
	
}
