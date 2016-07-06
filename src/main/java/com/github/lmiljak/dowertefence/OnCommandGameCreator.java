package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

/**
 * A GameCreator that creates a game that may contain multiple people, when
 * certain commands have been executed by a user.
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
