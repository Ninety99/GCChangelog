package me.NinetyNine.gcchangelog;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GCChangelog extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new GCChangelogHandler(), this);
	}
	
	@Override
	public void onDisable() {
		
	}

}
