package me.ulrich.skull;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("skull")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You can't do this!");
				return true;
			}
			Player player = (Player) sender;
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lYou've been given the skull of &c&l" + player.getName()));
				player.getInventory().addItem(getPlayerHead(player.getName()));
				return true;
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lYou've been given the skull of &c&l" + args[0]));
			player.getInventory().addItem(getPlayerHead(args[0]));
			return true;
		}
		
		return false;
	} 
	
	@SuppressWarnings("deprecation")
	public ItemStack getPlayerHead(String playerName) {
		//SKULL_ITEM for versions < 1.13 and PLAYER_HEAD for versions >= 1.13
		//Material.SKULL_ITEM;
		
		//if (this.getServer().getVersion().contains("1.15") || 1.13) {
			
		//}
		
		boolean isNewVersion = Arrays.stream(Material.values())
				.map(Material::name).collect(Collectors.toList())
				.contains("PLAYER_HEAD"); // With that we check what version is running the player
		
		Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
		ItemStack item = new ItemStack(type, 1);	
		
		if (!isNewVersion) // 1.12 or below
			item.setDurability((short) 3);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playerName);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
