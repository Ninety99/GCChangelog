package me.NinetyNine.gcchangelog.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
				// ((CraftPlayer) player).getHandle().openBook(CraftItemStack.asNMSCopy(book));
				BookMeta bookmeta = (BookMeta) book.getItemMeta();
				bookmeta.setTitle("§dGuild" + "§7Craft §fChangelog");
				bookmeta.setAuthor("§cNinetyNine");
				bookmeta.addPage(ChatColor.GREEN + "PogoStick29Dev on " + ChatColor.GRAY + "You" + ChatColor.RED
						+ "Tube" + ChatColor.GREEN + "!\n" + ChatColor.LIGHT_PURPLE
						+ "http://www.youtube.com/user/pogostick29dev\n" + ChatColor.AQUA + "Check him out!");
				
				book.setItemMeta(bookmeta);
			}

		}

		return true;
	}

}
