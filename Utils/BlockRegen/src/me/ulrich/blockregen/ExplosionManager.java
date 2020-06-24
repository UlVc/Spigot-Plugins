package me.ulrich.blockregen;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionManager implements Listener {
	
	Main plugin;
	
	public ExplosionManager(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onExplodeEntity(EntityExplodeEvent event) {
		List<Block> blocks = event.blockList();
		System.out.println(blocks);
		new RegenRun(blocks).runTaskTimer(plugin, 1, 1);
		event.setYield(0); //Not dropping blocks
		
	}
	
	@EventHandler
	public void onExplodeBlock(BlockExplodeEvent event) {
		List<Block> blocks = event.blockList();
		System.out.println(blocks);
		new RegenRun(blocks).runTaskTimer(plugin, 1, 1);
		event.setYield(0); //Not dropping blocks
		
	}
	
}
