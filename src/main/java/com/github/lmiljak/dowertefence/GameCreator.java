package com.github.lmiljak.dowertefence;

/**
 * An interface for classes that create Dower Tefence games and notifies all new
 * game listeners that a game has been created.
 */
public interface GameCreator {

	/**
	 * Registers a NewGameListener to this GameCreator. A registered listener
	 * should be notified when this creator creates a new Game.
	 * 
	 * @param newGameListener
	 *            The listener that should be registered.
	 */
	void registerNewGameListener(NewGameListener newGameListener);

	/**
	 * Unregisters an already registered listener from this creator. If this
	 * creator does not have the listener registered, nothing should happen.
	 * 
	 * @param newGameListener
	 *            The listener that should be unregistered.
	 */
	void unregsiterNewGameListener(NewGameListener newGameListener);

}
