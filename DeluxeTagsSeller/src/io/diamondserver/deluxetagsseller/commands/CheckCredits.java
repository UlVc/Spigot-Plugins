package io.diamondserver.deluxetagsseller.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.diamondserver.deluxetagsseller.Main;

public class CheckCredits implements CommandExecutor {

	private Main plugin;

	public CheckCredits(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("credits").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("deluxetagsseller.credits.others")) {

			if (args.length == 0)
				return credits(sender);

			if (args.length != 1) {
				String usage = this.plugin.getConfig().getString("credits-usage-others");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return true;
			}
			// /credits [PLAYER]
			try {
				FileConfiguration file = Main.getData();
				String name = args[0];

				if (file.contains("users")) {
					int var = Main.getData().getConfigurationSection("users").getKeys(false).size();

					if (var == 0) {
						String str = this.plugin.getConfig().getString("credits-player-doesnt-have-others");
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("<PLAYER>", name)));
						return true;
					}

					for (int i = 1; i <= var; i++)
						if (file.getString("users." + i + ".username").equalsIgnoreCase(name)) {
							String credits = this.plugin.getConfig().getString("credits-check-others");
							if (credits.contains("<PLAYER>") && credits.contains("<AMOUNT>"))
								sender.sendMessage(
										ChatColor.translateAlternateColorCodes('&', credits.replace("<PLAYER>", name)
												.replace("<AMOUNT>", file.getInt("users." + i + ".amount") + "")));
							else if (credits.contains("<PLAYER>"))
								sender.sendMessage(
										ChatColor.translateAlternateColorCodes('&', credits.replace("<PLAYER>", name)));
							else if (credits.contains("<AMOUNT>"))
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
										credits.replace("<AMOUNT>", file.getInt("users." + i + ".amount") + "")));
							else
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', credits));
							break;
						}

					return true;
				} else {
					String str = this.plugin.getConfig().getString("credits-player-doesnt-have-others");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.replace("<PLAYER>", name)));
					return true;
				}

			} catch (Exception e) {
				String usage = this.plugin.getConfig().getString("credits-usage");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return true;
			}
		} else if (sender.hasPermission("deluxetagsseller.credits")) {

			if (args.length != 0) {
				String usage = this.plugin.getConfig().getString("credits-usage");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
				return true;
			}

			return credits(sender);

		} else {
			String noPermission = this.plugin.getConfig().getString("no-permission");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
			return false;
		}

	}

	private boolean credits(CommandSender sender) {
		try {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You have unlimited credits, Console!");
				return false;
			}

			FileConfiguration file = Main.getData();
			Player player = (Player) sender;
			String name = player.getName();

			if (file.contains("users")) {
				boolean detecter = true;
				int var = Main.getData().getConfigurationSection("users").getKeys(false).size();

				for (int i = 1; i <= var; i++)
					if (name.equalsIgnoreCase(file.getString("users." + i + ".username"))) {
						detecter = false;
						String credits = this.plugin.getConfig().getString("credits-check-credits-of-player");
						if (credits.contains("<CREDITS>"))
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									credits.replace("<CREDITS>", file.getInt("users." + i + ".amount") + "")));
						else
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', credits));
						return true;
					}

				if (detecter) {
					String str = this.plugin.getConfig().getString("credits-player-doesnt-have");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
					return true;
				}

			} else {
				String str = this.plugin.getConfig().getString("credits-player-doesnt-have");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
				return true;
			}

			return false;
		} catch (Exception e) {
			String usage = this.plugin.getConfig().getString("credits-usage");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return true;
		}
	}

}
