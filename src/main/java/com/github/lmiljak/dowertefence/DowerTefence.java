package com.github.lmiljak.dowertefence;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents the Dower Tefence plugin. This class should never be be
 * instantiated apart from the Server running the Plugin.
 */
public final class DowerTefence extends JavaPlugin {

	/**
	 * The default "parent" command of all commands associated with this plugin.
	 * This means that every command starts with this String, followed by a sub
	 * command. /[PARENT_COMMAND] [SUB_COMMAND] [arguments]. This command should
	 * be equivalent to the one specified in the plugin.yml file.
	 */
	public static final String PARENT_COMMAND = "dt";

	private OnCommandGameCreator gameCreator;

	@Override
	public void onEnable() {
		registerCommandExecutors();

		getLogger().info("The plugin has been successfully enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("The plugin has been successfully disabled");
	}

	/**
	 * Registers all classes that implement the CommandExecutor interface.
	 */
	private void registerCommandExecutors() {
		this.gameCreator = new OnCommandGameCreator();
		getCommand(PARENT_COMMAND).setExecutor(gameCreator);
	}

}
