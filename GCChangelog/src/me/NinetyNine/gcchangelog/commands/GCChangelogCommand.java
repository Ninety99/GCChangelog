package me.NinetyNine.gcchangelog.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class GCChangelogCommand implements Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String[] args) {

		Player player = (Player) sender;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				BookMeta bookmeta = (BookMeta) book.getItemMeta();
				bookmeta.setTitle("§dGuild" + "§7Craft §fChangelog");
				bookmeta.setAuthor("§cNinetyNine");
				bookmeta.addPage("§l§08th of March\n" + "§2✔ §0Fixed issue with boosters.\n​ "
						+ "§2✔ §0Fixed issue with kits.\n\n" + "§l§07th of March\n"
						+ "§2✔ §0Fixed bug with items from supply drops.\n"
						+ "§2✔ §0Fixed issue with items from crates.");

				book.setItemMeta(bookmeta);
				
				((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
			} else {
				player.sendMessage("§[§dGuild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}

		return true;
	}

}
