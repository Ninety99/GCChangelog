package me.NinetyNine.gcchangelog.commands;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import me.NinetyNine.gcchangelog.GCChangelog;

public class GCChangelogCommand implements Listener, CommandExecutor {

	private HashSet<String> msg = new HashSet<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		String message = "";

		for (int i = 1; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();

		String down = "\n";

		Player player = (Player) sender;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookmeta = (BookMeta) book.getItemMeta();
		bookmeta.setTitle("§6Guild§7Craft §0Changelog");
		bookmeta.setAuthor("§caXed");
		bookmeta.addPage("§2✔​ §f message" + msg);
		book.setItemMeta(bookmeta);

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				player.getInventory().setItem(0, book);

				((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
			} else {
				player.sendMessage("§8[§6Guild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}

		if (cmd.getName().equalsIgnoreCase("gcchangelog")) {
			if (player.getItemInHand().getType().equals(Material.WRITTEN_BOOK)
					&& player.getItemInHand().hasItemMeta()) {
				if (args.length == 0) {
					if (player.hasPermission("changelog.admin")) {
						player.sendMessage(
								"§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <type> <message> or /gcchangelog remove <number> or /gcchangelog undo");
						return true;
					} else {
						player.sendMessage("§8[§6Guild§7Craft§8] §cYou do not have permissions!");
						return false;
					}
				}

				if (args[0].equalsIgnoreCase("add")) {
					if (args.length == 1) {
						player.sendMessage(
								"§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <type> <message> or /gcchangelog remove <number>");
						return true;
					}
				}

				if (args[0].equalsIgnoreCase("undo")) {
					player.sendMessage("§8[§6Guild§7Craft§8] §aSuccesfully undo!");
					msg.remove(message);
					return true;
				}

				if (args[0].equalsIgnoreCase("reload")) {
					if (args.length == 1) {
						new GCChangelog().reloadConfig();

						player.sendMessage("§8[§6Guild§7Craft§8] §6Reloaded!");
					}
				}

				if (args[1].equalsIgnoreCase("fixed")) {
					if (args.length == 1) {
						player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add fixed <message>");
					}

					bookmeta.setTitle("§6Guild§7Craft §0Changelog");
					bookmeta.setAuthor("§caXed");
					bookmeta.addPage(down + "§2✔​  §l§0" + message);
					book.setItemMeta(bookmeta);
					player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

					msg.add(message);
				}

				else if (args[1].equalsIgnoreCase("removed")) {
					if (args.length == 1) {
						player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add removed <message>");
					} else {
						bookmeta.addPage("§c╳ §f");
						book.setItemMeta(bookmeta);
						player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

						msg.add(message);
					}
				}

				else if (args[1].equalsIgnoreCase("changed")) {
					if (args.length == 1) {
						player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add changed <message>");
					} else {
						bookmeta.addPage("§f▶ " + message);
						book.setItemMeta(bookmeta);
						player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

						msg.add(message);
					}
				}

				else if (args[1].equalsIgnoreCase("page")) {
					if (bookmeta.getPageCount() < (new GCChangelog().getConfig().getInt("maxPages"))) {
						bookmeta.addPage("§8[§6Guild§7Craft§8] §2New page!");

						player.sendMessage("§8[§6Guild§7Craft§8] §7+1 page");
					} else {
						player.sendMessage("§8[§6Guild§7Craft§8] §4Reached the maximum level of pages!");
						return false;
					}

					msg.add(message);
				}

				else {
					player.sendMessage("§8[§6Guild§7Craft§8] §cInvalid operation.");
				}
			} else {
				player.sendMessage("§8[§6Guild§7Craft§8] §cYou must hold the changelog!");
			}
		}
		return true;
	}
}