package com.github.lmiljak.dowertefence.commands;

import nl.taico.taeirlib.commands.CommandArgs;
import nl.taico.taeirlib.commands.TCommand;

/**
 * Class for handling the /dt command and subcommands.
 */
public class DTCommand {
	/**
	 * Handles /dt and all subcommands that are not handled elsewhere.
	 * 
	 * @param cargs
	 * 		the command arguments
	 */
	@TCommand(name = "dt", usage = "/dt", description = "Main Dower Tefence command", showInHelp = true)
	public void dt(CommandArgs cargs) {
		String[] args = cargs.getArgs();
		if (args.length == 0) {
			cargs.sendMessage("PLACEHOLDER for help");
		} else {
			cargs.sendMessage("&cUnknown subcommand &6'" + args[0] + "'&c! &7Use /dt help for help.");
		}
	}
}
