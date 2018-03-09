package me.NinetyNine.gcchangelog;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.NinetyNine.gcchangelog.commands.GCChangelogCommand;

public class GCChangelog extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getCommand("changelog").setExecutor(new GCChangelogCommand());
		getCommand("gcchangelog").setExecutor(new GCChangelogCommand());
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
	}
	
	@Override
	public void onDisable() {
		
	}

}
