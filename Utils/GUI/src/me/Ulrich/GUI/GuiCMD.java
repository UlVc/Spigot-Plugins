package me.Ulrich.GUI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCMD implements CommandExecutor {
	
	private Main plugin;
	
	public GuiCMD(Main plugin) {
		plugin.getCommand("gui").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player))
			return false;
		
		Player player = (Player) sender;
		GuiInventory gui = new GuiInventory(player, "&c&lTest", 54);
		gui.setItems();
		gui.open();
		return true;
	}
	
	

}
