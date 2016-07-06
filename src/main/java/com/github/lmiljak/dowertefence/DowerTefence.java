package com.github.lmiljak.dowertefence;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.lmiljak.dowertefence.commands.DTCommand;

import nl.taico.taeirlib.commands.CommandFramework;

import lombok.Getter;

/**
 * Represents the Dower Tefence plugin. This class should never be be
 * instantiated apart from the Server running the Plugin.
 */
public final class DowerTefence extends JavaPlugin {
	public static DowerTefence instance;
	public static Logger logger;
	
	@Getter
	private CommandFramework commandFramework;
	
	@Override
	public void onLoad() {
		instance = this;
		logger = getLogger();
	}
	
	@Override
	public void onEnable() {
		//Register commands
		commandFramework = new CommandFramework(this);
		commandFramework.registerCommands(new DTCommand());
		commandFramework.registerHelp();
		
		getLogger().info("The plugin has been successfully enabled");
	}

	@Override
	public void onDisable() {
		//Properly unset the static fields
		logger = null;
		instance = null;
		getLogger().info("The plugin has been successfully disabled");
	}
}
