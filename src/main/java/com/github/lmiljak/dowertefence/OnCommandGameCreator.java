package com.github.lmiljak.dowertefence;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A GameCreator that creates a game that may contain multiple people, when
 * certain commands have been executed by a user.
 * 
 * More specifically, a player can execute the start command, at which a new
 * StandardGame will be created. If the player invited others as well, those
 * other players must execute the accept command before the game is created.
 */
public class OnCommandGameCreator implements GameCreator {

	private Set<NewGameListener> listeners;

	/**
	 * Constructor for OnCommandGameCreator.
	 */
	public OnCommandGameCreator() {
		this.listeners = new HashSet<>();
	}

	/**
	 * Notifies all NewGameListeners about a newly created game.
	 * 
	 * @param createdGame
	 *            The new game about which the listeners should be notified.
	 */
	private void notifyListeners(Game createdGame) {
		for (NewGameListener listener : listeners) {
			listener.onNewGame(createdGame, this);
		}
	}

	@Override
	public boolean registerNewGameListener(NewGameListener newGameListener) throws NullPointerException {
		if (newGameListener == null) {
			throw new NullPointerException();
		}

		return listeners.add(newGameListener);
	}

	@Override
	public boolean unregisterNewGameListener(NewGameListener newGameListener) {
		return listeners.remove(newGameListener);
	}

}
