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
import org.bukkit.plugin.Plugin;

import me.NinetyNine.gcchangelog.GCChangelog;

public class GCChangelogCommand implements Listener, CommandExecutor {

	private HashSet<String> msg = new HashSet<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = (Player) sender;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bookmeta = (BookMeta) book.getItemMeta();

		String message = "";
		for (int i = 1; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				BookMeta bookmeta1 = (BookMeta) book.getItemMeta();
				bookmeta1.setTitle("§dGuild§7Craft §fChangelog");
				bookmeta1.setAuthor("§4aXed");

				book.setItemMeta(bookmeta);

				((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
			} else {
				player.sendMessage("§8[§6Guild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}

		if (cmd.getName().equalsIgnoreCase("gcchangelog")) {
			if (args.length == 0) {
				if (player.hasPermission("changelog.admin")) {
					player.sendMessage(
							"§8[§6Guild§7Craft§8] §cUsage: /gcchangelog add <type> <message> or /gcchangelog remove <number>");
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
				msg.remove(message);
				return true;
			}

			if (args[1].equalsIgnoreCase("fixed")) {
				bookmeta.addPage("§2✔​ §f" + message);
				book.setItemMeta(bookmeta);
				player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");
				
				msg.add(message);
			}

			else if (args[1].equalsIgnoreCase("removed")) {
				bookmeta.addPage("§c╳ §f" + message);
				book.setItemMeta(bookmeta);
				player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");
				
				msg.add(message);
			}

			else if (args[1].equalsIgnoreCase("changed")) {
				bookmeta.addPage("§f▶ " + message);
				book.setItemMeta(bookmeta);
				player.sendMessage("§8[§6Guild§7Craft§8] §2Added!");
				
				msg.add(message);
			}

			else if (args[1].equalsIgnoreCase("page")) {
				if (bookmeta.getPageCount() < ((Plugin) new GCChangelog()).getConfig().getInt("maxPage")) {
					bookmeta.addPage();
				} else {
					player.sendMessage("§8[§6Guild§7Craft§8] §4Reached the maximum level of pages!");
					return false;
				}
				
				msg.add(message);
			}

			else {
				player.sendMessage("§[§dGuild§7Craft§8] §cInvalid operation.");
			}
		}
		return true;
	}
}