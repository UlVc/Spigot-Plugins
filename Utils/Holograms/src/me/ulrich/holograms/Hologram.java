package me.ulrich.holograms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Hologram implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 2, 0), 
					EntityType.ARMOR_STAND); //An armor stand is an entity
			hologram.setVisible(false);
			hologram.setCustomNameVisible(true);
			hologram.setGravity(false);
			hologram.setCustomName(ChatColor.RED + "Hello there");
			//Second line: 
			ArmorStand hologram2 = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1.6, 0), 
					EntityType.ARMOR_STAND); //An armor stand is an entity
			hologram2.setVisible(false);
			hologram2.setCustomNameVisible(true);
			hologram2.setGravity(false);
			hologram2.setCustomName(ChatColor.BLUE + "Obi wan Kenobi");
			//Third line:
			ArmorStand hologram3 = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1.2, 0), 
					EntityType.ARMOR_STAND); //An armor stand is an entity
			hologram3.setVisible(false);
			hologram3.setCustomNameVisible(true);
			hologram3.setGravity(false);
			hologram3.setCustomName(ChatColor.GOLD + "Prepare to die");
			
		}
		
		return true;
		
	}
}
