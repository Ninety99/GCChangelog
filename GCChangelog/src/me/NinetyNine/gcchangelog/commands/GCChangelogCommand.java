package me.NinetyNine.gcchangelog.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.bukkit.ChatColor;
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
	// private String nm;
	private GCChangelog plugin;

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		String message = "";

		for (int i = 2; i < args.length; i++) {
			message += args[i] + " ";
		}

		Player player = (Player) sender;

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookmeta = (BookMeta) book.getItemMeta();
		BookMeta bm = (BookMeta) book.getItemMeta();
		BookMeta bm1 = (BookMeta) book.getItemMeta();
		BookMeta bm2= (BookMeta) book.getItemMeta();
		bookmeta.setTitle("§6Guild§7Craft §0Changelog");
		bookmeta.addPage(message);
		bookmeta.setAuthor("§caXed");
		book.setItemMeta(bookmeta);
		book.setItemMeta(bm);
		book.setItemMeta(bm1);
		book.setItemMeta(bm2);

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				player.getInventory().setHeldItemSlot(0);
				player.getInventory().setItem(0, book);
				book.setItemMeta(bookmeta);

				if (player.getInventory().contains(book) && book.hasItemMeta()) {
					((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
					return true;
				}

			} else {
				player.sendMessage("§8[§6Guild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}

		if (cmd.getName().equalsIgnoreCase("gcchangelog")) {
			if (player.getItemInHand().getType().equals(Material.WRITTEN_BOOK)
					&& player.getItemInHand().hasItemMeta()) {
				if (player.hasPermission("changelog.admin")) {
					if (args.length == 0) {
						player.sendMessage(
								"§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <type> <message> or /gcchangelog remove <number> or /gcchangelog undo");
						return true;
					}

					if (args[0].equalsIgnoreCase("page")) {
						if (args.length == 1) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog page <number>");
							return true;
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
						msg.remove(message);
						player.sendMessage("§8[§6Guild§7Craft§8] §aSuccesfull undo!");
						return true;
					}

					if (args[0].equalsIgnoreCase("reload")) {
						if (args.length == 1) {
							plugin.reloadConfig();
							player.sendMessage("§8[§6Guild§7Craft§8] §6Reloaded!");
						}
					}

					if (args[1].equalsIgnoreCase("fixed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add fixed <message>");
						} else {
							bm.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GREEN + "✔ "
									+ ChatColor.BLACK + message + "\n");
							book.setItemMeta(bm);
							player.setItemInHand(book);
							player.updateInventory();
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

							msg.add(message);
						}
					}

					else if (args[1].equalsIgnoreCase("removed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add removed <message>");
						} else {
							bm1.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.RED + "✘ "
									+ ChatColor.BLACK + message + "\n");
							book.setItemMeta(bm1);
							player.setItemInHand(book);
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

							msg.add(message);
						}
					}

					else if (args[1].equalsIgnoreCase("changed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add changed <message>");
						} else {
							bm2.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GOLD + "▶ "
									+ ChatColor.BLACK + message + "\n");
							book.setItemMeta(bm2);
							player.setItemInHand(book);
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

							msg.add(message);
						}
					}

					else if (args[1].equalsIgnoreCase("page")) {
						if (bookmeta.getPageCount() < 6) {
							bookmeta.addPage("§8[§6Guild§7Craft§8] §2New page!");
							book.setItemMeta(bookmeta);
							player.setItemInHand(book);

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
					player.sendMessage("§8[§6Guild§7Craft§8] §cYou do not have permissions!");
					return false;
				}
			} else {
				player.sendMessage("§8[§6Guild§7Craft§8] §cYou must hold the changelog!");
			}
		}
		return true;
	}
}