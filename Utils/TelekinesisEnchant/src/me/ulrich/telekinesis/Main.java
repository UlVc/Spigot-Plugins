package me.ulrich.telekinesis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.ulrich.telekinesis.events.BlockBreak;


public class Main extends JavaPlugin {

	public void onEnable() {
		CustomEnchants.register();
		
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player))
			return false;
		
		if (label.equalsIgnoreCase("telekinesis")) {
			
			Player player = (Player) sender;
			
			if (!player.hasPermission("telekinesis.use")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou can't execute this command!"));
				return false;
			}
			
			ItemStack item = player.getItemInHand();
			
			if (checkIfItsTool(item.getType())) {
				player.getInventory().removeItem(item);
				item.addUnsafeEnchantment(CustomEnchants.TELEKINESIS, 1);
				
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				
				lore.add(ChatColor.GOLD + "Telekinesis I");
				
				if (meta.hasLore())
					for (String l : meta.getLore())
						lore.add(l);
				
				meta.setLore(lore);
				item.setItemMeta(meta);
				
				player.getInventory().addItem(item);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEnchantment applied!"));
				
				return true;
			}
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou must have a tool in your main hand!"));
			return false;
		}
		
		return true;
	}
	
	private boolean checkIfItsTool(Material material) {
		return material.equals(Material.WOOD_AXE) || material.equals(Material.WOOD_SWORD) || material.equals(Material.WOOD_SPADE) || material.equals(Material.WOOD_PICKAXE) || material.equals(Material.WOOD_HOE) ||
				material.equals(Material.STONE_AXE) || material.equals(Material.STONE_SWORD) || material.equals(Material.STONE_SPADE) || material.equals(Material.STONE_PICKAXE) || material.equals(Material.STONE_HOE) ||
				material.equals(Material.GOLD_AXE) || material.equals(Material.GOLD_SWORD) || material.equals(Material.GOLD_SPADE) || material.equals(Material.GOLD_PICKAXE) || material.equals(Material.GOLD_HOE) ||
				material.equals(Material.IRON_AXE) || material.equals(Material.IRON_SWORD) || material.equals(Material.IRON_SPADE) || material.equals(Material.IRON_PICKAXE) || material.equals(Material.IRON_HOE) ||
				material.equals(Material.DIAMOND_AXE) || material.equals(Material.DIAMOND_SWORD) || material.equals(Material.DIAMOND_SPADE) || material.equals(Material.DIAMOND_PICKAXE) || material.equals(Material.DIAMOND_HOE);
	}
	
}
