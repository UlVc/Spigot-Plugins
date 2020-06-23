package me.Ulrich.CustomRecipe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.addRecipe(getRecipe());
		Bukkit.addRecipe(getPickaxeRecipe());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public ShapedRecipe getRecipe() {	
		ItemStack item = new ItemStack(Material.NETHER_STAR);		
		NamespacedKey key = new NamespacedKey(this, "nether_star_lord");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape(" G ", "GEG", " G "); // 3 rows(3 spaces) n 3 columns(3 arguments)
		
		recipe.setIngredient('G', Material.GHAST_TEAR);
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		
		return recipe;
	}
	
	public ShapedRecipe getPickaxeRecipe() {	
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 3); // The second argument is for saying how many items it will create.
		
		ItemMeta meta = item.getItemMeta();
		List<String> lores = new ArrayList<String>();
		
		meta.setDisplayName(ChatColor.GREEN + "EMERALD PICKAXE");
		meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1000, true); // put true if the second argument is > 3
		lores.add("Fortune M");
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setLore(lores);
		item.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "emerald_pickaxe"); // its important the underscores
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape("EEE", " S ", " S "); // 3 rows(3 spaces) n 3 columns(3 arguments)
		
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		
		return recipe;
	}
	
}
