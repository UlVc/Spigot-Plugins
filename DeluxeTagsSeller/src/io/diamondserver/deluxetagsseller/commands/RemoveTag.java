package io.diamondserver.deluxetagsseller.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.diamondserver.deluxetagsseller.Main;

public class RemoveTag implements CommandExecutor {

	private Main plugin;

	public RemoveTag(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("removetag").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("deluxetagsseller.removetag")) {
			String noPermission = this.plugin.getConfig().getString("no-permission");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
			return true;
		}

		if (args.length != 2) {
			String usage = this.plugin.getConfig().getString("removetag-usage");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return true;
		}

		try {
			String name = args[0];
			int amountRemoved = Integer.parseInt(args[1]);
			FileConfiguration file = Main.getData();

			if (file.contains("users")) {
				boolean detecter = true;
				int var = Main.getData().getConfigurationSection("users").getKeys(false).size();

				for (int i = 1; i <= var; i++)
					if (name.equalsIgnoreCase(file.getString("users." + i + ".username"))) {
						int amount = file.getInt("users." + i + ".amount") - amountRemoved;

						if (amount <= 0) {
							file.set("users." + i, null);
							Main.saveData();
						} else {
							file.set("users." + i + ".amount", amount);
							Main.saveData();
						}

						detecter = false;
						checkPlayers(name, amountRemoved);
						String succes = this.plugin.getConfig().getString("removetag-succes");

						if (succes.contains("<PLAYER>") && succes.contains("<AMOUNT>"))
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									succes.replace("<PLAYER>", name).replace("<AMOUNT>", amountRemoved + "")));
						else if (succes.contains("<PLAYER>"))
							sender.sendMessage(
									ChatColor.translateAlternateColorCodes('&', succes.replace("<PLAYER>", name)));
						else if (succes.contains("<AMOUNT>"))
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									succes.replace("<AMOUNT>", amountRemoved + "")));
						else
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
						return true;
					}

				if (detecter) {
					String doesntHasTags = this.plugin.getConfig().getString("removetag-doesnt-has-tags");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', doesntHasTags));
					return true;
				}

			} else {
				String doesntHasTags = this.plugin.getConfig().getString("removetag-doesnt-has-tags");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', doesntHasTags));
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			String usage = this.plugin.getConfig().getString("removetag-usage");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return true;
		}
	}

	private void checkPlayers(String name, int amountRemoved) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getName().equalsIgnoreCase(name)) {
				String removed = this.plugin.getConfig().getString("removetag-removed-message");
				if (removed.contains("<AMOUNT>"))
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							removed.replace("<AMOUNT>", amountRemoved + "")));
				else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', removed));
				return;
			}
	}

}
