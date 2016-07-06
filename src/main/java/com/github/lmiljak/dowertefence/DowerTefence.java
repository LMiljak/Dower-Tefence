package com.github.lmiljak.dowertefence;

import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents the Dower Tefence plugin. This class should never be be
 * instantiated apart from the Server running the Plugin.
 */
public final class DowerTefence extends JavaPlugin {

	/**
	 * The default "parent" commands of all commands associated with this
	 * plugin. This means that every command starts with either one of these
	 * String, followed by a sub command. /[PARENT_COMMAND] [SUB_COMMAND]
	 * [arguments]. These commands should be equivalent to the ones specified in
	 * the plugin.yml file.
	 */
	static final List<String> PARENT_COMMANDS = Arrays.asList("dt", "dowertefence");

	private OnCommandGameCreator gameCreator;
	private AutoGameStarter gameStarter;

	@Override
	public void onEnable() {
		createPluginComponents();

		getLogger().info("The plugin has been successfully enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("The plugin has been successfully disabled");
	}

	/**
	 * Registers all classes that implement the CommandExecutor interface.
	 */
	private void createPluginComponents() {
		this.gameCreator = new OnCommandGameCreator();

		this.gameStarter = new AutoGameStarter();
		gameCreator.registerNewGameListener(gameStarter);
	}

}
