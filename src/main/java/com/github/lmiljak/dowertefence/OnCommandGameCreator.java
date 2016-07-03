package com.github.lmiljak.dowertefence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * A GameCreator that creates a game that may contain multiple people, when certain commands have been executed by a user.
 */
public class OnCommandGameCreator implements GameCreator, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerNewGameListener(NewGameListener newGameListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregsiterNewGameListener(NewGameListener newGameListener) {
		// TODO Auto-generated method stub
		
	}

}
