package com.github.lmiljak.dowertefence.commands;

import org.bukkit.ChatColor;

import nl.taico.taeirlib.commands.CommandArgs;
import nl.taico.taeirlib.commands.TCommand;

/**
 * Class for handling the /dt command and subcommands.
 */
public class DTCommand {
	/**
	 * Handles /dt and /dt help.
	 * 
	 * @param args
	 * 		the command arguments
	 */
	@TCommand(name = "dt", usage = "/dt", description = "Main Dower Tefence command", aliases = {"dt.help"}, showInHelp = true)
	public void dtHelp(CommandArgs args) {
		args.sendMessage(ChatColor.GOLD + "----" + ChatColor.BLUE + " Dower Tefence Help " + ChatColor.GOLD + "----");
		args.sendMessage("PLACEHOLDER");
	}
}
