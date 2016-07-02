package com.github.LMiljak.DowerTefence;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents the Dower Tefence plugin. This class should never be be
 * instantiated apart from the Server running the Plugin.
 */
public final class DowerTefence extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("The plugin has been successfully enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("The plugin has been successfully disabled");
	}

}
