package io.diamondserver.deluxetagsseller.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.diamondserver.deluxetagsseller.Main;

public class AddPermission implements CommandExecutor {

	private Main plugin;

	public AddPermission(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("addtag").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("deluxetagsseller.addtag")) {
			String noPermission = this.plugin.getConfig().getString("no-permission");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
			return false;
		}

		if (args.length != 2) {
			String usage = this.plugin.getConfig().getString("addtag-usage");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return true;
		}

		try {
			int amount = Integer.parseInt(args[1]);
			String name = args[0];
			FileConfiguration file = Main.getData();

			if (file.contains("users")) {
				boolean detecter = true;
				int var = Main.getData().getConfigurationSection("users").getKeys(false).size();

				for (int i = 1; i <= var; i++)
					if (name.equalsIgnoreCase(file.getString("users." + i + ".username"))) {
						file.set("users." + i + ".amount", file.getInt("users." + i + ".amount", amount) + amount);
						Main.saveData();
						detecter = false;
						checkPlayers(name, amount);
						String succes = this.plugin.getConfig().getString("addtag-succes");

						if (succes.contains("<PLAYER>") && succes.contains("<AMOUNT>"))
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									succes.replace("<PLAYER>", name).replace("<AMOUNT>", amount + "")));
						else if (succes.contains("<PLAYER>"))
							sender.sendMessage(
									ChatColor.translateAlternateColorCodes('&', succes.replace("<PLAYER>", name)));
						else if (succes.contains("<AMOUNT>"))
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									succes.replace("<AMOUNT>", amount + "")));
						else
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
						return true;
					}

				if (detecter) {
					file.set("users." + (var + 1) + ".username", name);
					file.set("users." + (var + 1) + ".amount", amount);
					Main.saveData();
					checkPlayers(name, amount);
					String succes = this.plugin.getConfig().getString("addtag-succes");

					if (succes.contains("<PLAYER>") && succes.contains("<AMOUNT>"))
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								succes.replace("<PLAYER>", name).replace("<AMOUNT>", amount + "")));
					else if (succes.contains("<PLAYER>"))
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', succes.replace("<PLAYER>", name)));
					else if (succes.contains("<AMOUNT>"))
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', succes.replace("<AMOUNT>", amount + "")));
					else
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
					return true;
				}

			} else {
				file.set("users." + 1 + ".username", name);
				file.set("users." + 1 + ".amount", amount);
				Main.saveData();
				checkPlayers(name, amount);

				String succes = this.plugin.getConfig().getString("addtag-succes");

				if (succes.contains("<PLAYER>") && succes.contains("<AMOUNT>"))
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							succes.replace("<PLAYER>", name).replace("<AMOUNT>", amount + "")));
				else if (succes.contains("<PLAYER>"))
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', succes.replace("<PLAYER>", name)));
				else if (succes.contains("<AMOUNT>"))
					sender.sendMessage(
							ChatColor.translateAlternateColorCodes('&', succes.replace("<AMOUNT>", amount + "")));
				else
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));

				return true;
			}

			return false;
		} catch (Exception e) {
			String usage = this.plugin.getConfig().getString("addtag-usage");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return true;
		}

	}

	private void checkPlayers(String name, int amount) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getName().equalsIgnoreCase(name)) {
				String recieved = this.plugin.getConfig().getString("addtag-recieved");
				if (recieved.contains("<AMOUNT>"))
					p.sendMessage(
							ChatColor.translateAlternateColorCodes('&', recieved.replace("<AMOUNT>", amount + "")));
				else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', recieved));
				return;
			}
	}

}
