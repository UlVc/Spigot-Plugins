package me.ulrich.telekinesis;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;

public class CustomEnchants {

	public static final Enchantment TELEKINESIS = new EnchantmentWrapper(100, "Telekinesis", 1);
	
	public static void register() {
		boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.TELEKINESIS);
		if (!registered)
			registerEnchantment(TELEKINESIS);
	}
	
	// Must have:
	public static void registerEnchantment(Enchantment enchantment) {
		boolean registered = true;
		
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchantment);
		} catch (Exception e) {
			registered = false;
			e.printStackTrace();
		}
		
		if (registered) {
			// Send message to console
		}
		
	}
	
}
