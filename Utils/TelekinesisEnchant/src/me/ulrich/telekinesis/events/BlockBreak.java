package me.ulrich.telekinesis.events;

import java.util.Collection;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {

		if (event.getPlayer().getInventory().getItemInHand() == null || 
				!event.getPlayer().getInventory().getItemInHand().hasItemMeta() ||
				!event.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().toString().contains("Telekinesis") ||
				event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR ||
				event.getPlayer().getInventory().firstEmpty() == -1) //No room in their inventory (No need to send the block to their inventory)
			return;

		if (!event.isCancelled()) { // World Guard protection
			Player player = event.getPlayer();
			Block block = event.getBlock();
			
			Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInHand()); //Possible drops (if i have a wooden pickaxe it will not drop diamonds after breaking diamonds ores, but if i have a diamond pickaxe it will drop)

			if (event.getPlayer().getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) &&
					checkIfItsOre(block.getType())) {
				int fortuneLevel = event.getPlayer().getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				int dropCount = getDropCount(block.getType(), fortuneLevel, new Random());
				for (int i = 0; i <= dropCount; i++) {
					if (drops.isEmpty())
						return;
					
					player.getInventory().addItem(drops.iterator().next());
				}
				event.getBlock().setType(Material.AIR);
				return;
			}

			if (drops.isEmpty())
				return;
			
			player.getInventory().addItem(drops.iterator().next());
			event.getBlock().setType(Material.AIR);
		}
		
	}
	
	private boolean checkIfItsOre(Material material) {
		return material.equals(Material.DIAMOND_ORE) || material.equals(Material.COAL_ORE) ||
				material.equals(Material.REDSTONE_ORE) || material.equals(Material.LAPIS_ORE);
	}
	
	private int a(Material material, Random random) {
	    return material == Material.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
	}

	private int getDropCount(Material mat, int fortuneLevel, Random random) {
	    if (fortuneLevel > 0) {
	    	
	        int drops = random.nextInt(fortuneLevel + 2) - 1;
	        
	        if (drops < 0)
	            drops = 0;
	        
	        return a(mat, random) * (drops + 1);
	    }
	    
	    return a(mat, random);
	}
	
}
