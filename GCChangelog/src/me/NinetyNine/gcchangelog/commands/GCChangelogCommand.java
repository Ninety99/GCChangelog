package me.NinetyNine.gcchangelog.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private ArrayList<Player> gccl = new ArrayList<Player>();
	private ArrayList<String> inb = new ArrayList<String>();
	public HashMap<String, String> in = new HashMap<String, String>();
	public HashMap<Player, ItemStack[]> itemhash = new HashMap<>();

	private GCChangelog plugin;
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		String message = "";

		for (int i = 2; i < args.length; i++) {
			message += args[i] + " ";
		}

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
		Player player = (Player) sender;

		
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookmeta = (BookMeta) book.getItemMeta();
		bookmeta.setTitle("§6Guild§7Craft §0Changelog");
		bookmeta.addPage(" ");
		bookmeta.setAuthor("§caXed");
		book.setItemMeta(bookmeta);
		
		
		ItemStack b = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta) b.getItemMeta();
		bm.addPage(" ");
		
		String aa = bm.getPage(1);
		aa += bookmeta.getPage(1);

		ItemStack[] playerinv = player.getInventory().getContents();

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				if (!this.gccl.contains(player)) {
					gccl.add(player);
					itemhash.put(player, playerinv);
					
					bookmeta.setPage(1, aa);
					book.setItemMeta(bookmeta);
					player.setItemInHand(book);
					player.sendMessage(
							"§8[§6Guild§7Craft§8] §2You have recieved the changelog book! Type /changelog again to get your item(s) back.");

					if (player.getInventory().contains(book) && book.hasItemMeta()) {
						((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
						return true;
					}
				} else {
					this.gccl.remove(player);
					player.getInventory().clear();
					if (this.itemhash.containsKey(player)) {
						ItemStack[] arg18 = (ItemStack[]) this.itemhash.get(player);
						player.getInventory().setContents(arg18);
					}
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

					/*
					 * if (args[0].equalsIgnoreCase("page")) { if (args.length == 1) {
					 * player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog page <number>"
					 * ); return true; } }
					 */

					if (args[0].equalsIgnoreCase("add")) {
						if (args.length == 1) {
							player.sendMessage(
									"§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <type> <message> or /gcchangelog remove <number>");
							return true;
						}
					}
					
					/*if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog set <text>");
							return true;
						}
						
						
						String test = "";

						for (int itest = 2; itest < args.length; itest++) {
							test += args[itest] + " ";
						}
						
						//plugin.getConfig().set("message", test);
						bookmeta.addPage(plugin.getConfig().getString("message", test));
						plugin.saveConfig();
						book.setItemMeta(bookmeta);
						
					}
					*/

					if (args[0].equalsIgnoreCase("undo")) {
						msg.remove(message);
						inb.remove(message);
						player.sendMessage("§8[§6Guild§7Craft§8] §aSuccesfull undo!");
						return true;
					}

					if (args[0].equalsIgnoreCase("reload")) {
						if (args.length == 1) {
							plugin.reloadConfig();
							player.sendMessage("§8[§6Guild§7Craft§8] §6Reloaded!");
						}
					}
					/*
					if (args[1].equalsIgnoreCase("all")) {
						if (args.length == 1) {
							// /first text is for fixed, 2nd for removed, 3rd for changed.
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <text>(fixed) <text>(removed) <text>(changed)");
							return true;
						}
						
						String test = "";

						for (int itest = 2; itest < args.length; itest++) {
							test = args[itest] + " ";
						}
						
						
						/*String test1a = "";

						for (int itest1a = 3;;) {
							test1a = args[3] + " ";
						}
						*/
						/*String test2 = "";

						for (int itest2 = 4; itest2 < args.length; itest2++) {
							test2 += args[itest2] + " ";
						}
						
						
						bm.addPage(" ");
						bm.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GREEN + "✔ "
								+ ChatColor.BLACK + test + "\n" + ChatColor.RED + "✘ " + ChatColor.BLACK + test + "\n" + ChatColor.GOLD + "▶ "
								+ ChatColor.BLACK + test2 + "\n\n");
						book.setItemMeta(bm);
						player.setItemInHand(book);
					}
				/*
				*/

					if (args[1].equalsIgnoreCase("fixed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add fixed <message>");
						} else {
							bm.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GREEN + "✔ "
									+ ChatColor.BLACK + message + "\n" + ChatColor.RED + "✘ " + ChatColor.BLACK + "\n" + ChatColor.GOLD + "▶ "
									+ ChatColor.BLACK + "\n\n" + aa);
							book.setItemMeta(bm);
							player.setItemInHand(book);
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

							msg.add(message);
							inb.add(message);

						}
					}

					else if (args[1].equalsIgnoreCase("removed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add removed <message>");
						} else {
							bm.setPage(1 ,ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GREEN + "✔ "
									+ ChatColor.BLACK + "\n" + ChatColor.RED + "✘ " + ChatColor.BLACK + message + "\n" + ChatColor.GOLD + "▶ "
									+ ChatColor.BLACK + "\n\n" + aa);
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");
							book.setItemMeta(bm);
							player.setItemInHand(book);

							msg.add(message);
							inb.add(message);
						}
					}

					else if (args[1].equalsIgnoreCase("changed")) {
						if (args.length == 2) {
							player.sendMessage("§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add changed <message>");
						} else {
							bm.setPage(1, ChatColor.BOLD + format.format(now) + "\n" + ChatColor.GREEN + "✔ "
									+ ChatColor.BLACK + "\n" + ChatColor.RED + "✘ " + ChatColor.BLACK + "\n" + ChatColor.GOLD + "▶ "
									+ ChatColor.BLACK + message + "\n\n" + aa);
							book.setItemMeta(bm);
							player.setItemInHand(book);
							player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");

							msg.add(message);
							inb.add(message);
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
