package me.NinetyNine.gcchangelog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NBTTagString;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;

public final class GCChangelogBook {
	
	private String title;
	private String author;
	private List<String> pages = new ArrayList();

	public GCChangelogBook(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public ItemStack build() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("author", this.author);
		tag.setString("title", this.title);
		NBTTagList pages = new NBTTagList();
		Iterator arg4 = this.pages.iterator();

		while (arg4.hasNext()) {
			String page = (String) arg4.next();
			pages.add(new NBTTagString(page));
		}

		tag.set("pages", pages);
		book.setItemMeta((ItemMeta) tag);
		return book;
	}

	public static void open(Player p, ItemStack book, boolean addStats) {
		EntityPlayer player = ((CraftPlayer) p).getHandle();
		ItemStack hand = p.getItemInHand();

		try {
			p.setItemInHand(book);
			player.playerConnection.sendPacket(
					new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(Unpooled.buffer())));
		} catch (Exception arg8) {
			arg8.printStackTrace();
		} finally {
			p.setItemInHand(hand);
		}
	}
}