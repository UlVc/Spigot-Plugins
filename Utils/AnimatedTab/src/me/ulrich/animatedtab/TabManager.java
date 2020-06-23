package me.ulrich.animatedtab;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;

public class TabManager {
	
	private List<ChatComponentText> headers = new ArrayList<>();
	private List<ChatComponentText> footers = new ArrayList<>();
	private Main plugin;
	
	public TabManager(Main plugin) {
		this.plugin = plugin;
	}
	
	public void showTab() {
		if (headers.isEmpty() & footers.isEmpty())
			return;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(); //Plays out the header and footer
			int countHeaders = 0;
			int countFooters = 0;
			
			@Override
			public void run() {
				try {
					Field a = packet.getClass().getDeclaredField("a"); // Header (Footer) for < 1.13: a (b), and for 1.13 >: header (footer)
					a.setAccessible(true);
					Field b = packet.getClass().getDeclaredField("b"); // Header (Footer) for < 1.13: a (b), and for 1.13 >: header (footer)
					b.setAccessible(true);
					
					if (countHeaders >= headers.size())
						countHeaders = 0;
					if (countFooters >= footers.size())
						countFooters = 0;
					
					a.set(packet, headers.get(countHeaders));
					b.set(packet, footers.get(countFooters));
					
					if (Bukkit.getOnlinePlayers().size() != 0)
						for(Player player : Bukkit.getOnlinePlayers())
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
					
					countHeaders++;
					countFooters++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}, 10, 40);
	}
	
	public void addHeader(String header) {
		headers.add(new ChatComponentText(format(header)));
	}
	
	public void addFooter(String footer) {
		footers.add(new ChatComponentText(format(footer)));
	}
	
	private String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}
