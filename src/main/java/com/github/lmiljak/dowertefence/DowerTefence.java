package com.github.lmiljak.dowertefence;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import nl.taico.taeirlib.commands.CommandArgs;
import nl.taico.taeirlib.commands.CommandFramework;
import nl.taico.taeirlib.commands.TCommand;

/**
 * Represents the Dower Tefence plugin. This class should never be be
 * instantiated apart from the Server running the Plugin.
 */
public final class DowerTefence extends JavaPlugin {

	@Getter
	private PlayerDataManager playerDataManager;
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
	 * Initialises and registers all important components of this plugin.
	 */
	private void createPluginComponents() {
		CommandFramework commandFramework = new CommandFramework(this);
		commandFramework.registerCommands(this);

		this.playerDataManager = new PlayerDataManager();
		getServer().getPluginManager().registerEvents(playerDataManager, this);
		
		this.gameCreator = new OnCommandGameCreator();
		commandFramework.registerCommands(gameCreator);

		this.gameStarter = new AutoGameStarter();
		gameCreator.registerNewGameListener(gameStarter);

		gameCreator.registerNewGameListener(playerDataManager);
		gameCreator.registerNewLobbyListener(playerDataManager);
		
		commandFramework.registerHelp();
	}

	/**
	 * Gets the instance of this plugin.
	 * 
	 * @return The instance of this plugin.
	 */
	public static DowerTefence getPlugin() {
		return getPlugin(DowerTefence.class);
	}

	/**
	 * Called when the /dt command is executed with possible unrecognised sub
	 * commands.
	 * 
	 * @param commandArgs
	 *            The wrapper for different arguments of the command.
	 */
	@TCommand(name = "dt", usage = "/dt", description = "Main Dower Tefence command", showInHelp = true)
	public void onDTCommand(CommandArgs commandArgs) {
		String[] args = commandArgs.getArgs();
		if (args.length == 0) {
			commandArgs.sendMessage("PLACEHOLDER for help");
		} else {
			commandArgs.sendMessage("&cUnknown subcommand &6'" + args[0] + "'&c! &7Use /dt help for help.");
		}
	}

}
