package me.NinetyNine.gcchangelog.commands;

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

public class GCChangelogCommand implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = (Player) sender;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		String entered = "";

		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (player.hasPermission("changelog.open")) {
				BookMeta bookmeta = (BookMeta) book.getItemMeta();
				bookmeta.setTitle("§dGuild" + "§7Craft §fChangelog");
				bookmeta.setAuthor("§cNinetyNine");
				bookmeta.addPage(entered);

				book.setItemMeta(bookmeta);

				((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
			} else {
				player.sendMessage("§[§dGuild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("gcchangelog")) {
			if (player.hasPermission("changelog.admin")) {
				player.sendMessage("hi");
			} else {
				player.sendMessage("§[§dGuild§7Craft§8] §cYou do not have permissions!");
				return false;
			}
		}
		return true;
	}
}
