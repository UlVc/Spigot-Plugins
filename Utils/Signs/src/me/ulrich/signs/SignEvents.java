package me.ulrich.signs;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvents implements Listener {
	
	@EventHandler
	public void signClick(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		
		if (clickedBlock == null)
			return;
		
		BlockState state = clickedBlock.getState();
		
		Player player = event.getPlayer();
		
		if (state instanceof Sign) {
			Sign sign = (Sign) state;
			String line0 = sign.getLine(0); //First line of the sign
			if (line0.equalsIgnoreCase("> Creative <")) {
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGamemode changed to &c&lcreative"));
			} else if (line0.equalsIgnoreCase("> Survival <")) {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGamemode changed to &c&lsurvival"));
			}
		}
	}
	
}
