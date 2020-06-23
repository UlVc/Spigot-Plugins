package me.Ulrich.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiInventory implements Listener {
	
	private Player p;
	private String name;
	private Inventory inv;
	private ItemStack healIS, giveDiamondStack;
	
	public GuiInventory(Player p, String name, int slots) {
		this.p = p;
		this.name = name;
		this.inv = Bukkit.createInventory(p, slots, color(name));
		Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Inventory clicked = event.getClickedInventory();
		
		if (clicked == null || !event.getWhoClicked().equals(p) || 
				!clicked.equals(inv)) {
			event.setCancelled(true);
			return;
		}
		
		if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || clicked.equals(p.getInventory())) {
			event.setCancelled(true);
			return;
		}
		
		ItemStack is = event.getCurrentItem();
		
		if (is == null || is.getType().equals(Material.AIR))
			return;
		
		event.setCancelled(true);
		
		if (is.equals(healIS)) {
			p.setHealth(20);
			p.setSaturation(20);
		}
		
		if (is.equals(giveDiamondStack)) {
			p.getInventory().addItem(new ItemStack(Material.DIAMOND));
		}
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		HandlerList.unregisterAll(this);
	}
	
	public void setItems() {
		
		healIS = new ItemStack(Material.CAKE);
		ItemMeta im = healIS.getItemMeta();
		im.setDisplayName(color("&cHeal"));
		healIS.setItemMeta(im);
		inv.setItem(22, healIS);
		
		giveDiamondStack = new ItemStack(Material.DIAMOND);
		ItemMeta im2 = giveDiamondStack.getItemMeta();
		im2.setDisplayName(color("&bFree Diamonds!"));
		healIS.setItemMeta(im2);
		inv.setItem(24, giveDiamondStack);
		
	}
	
	public void open() { 
		p.openInventory(inv);
	}
	
	private String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	
}
