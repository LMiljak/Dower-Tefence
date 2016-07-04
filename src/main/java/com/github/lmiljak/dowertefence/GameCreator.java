package com.github.lmiljak.dowertefence;

/**
 * An interface for classes that create Dower Tefence games and notifies all new
 * game listeners that a game has been created.
 */
public interface GameCreator {

	/**
	 * Registers a NewGameListener to this GameCreator. A registered listener
	 * should be notified when this creator creates a new Game. If the listener
	 * has already been registered, it should not do so again.
	 * 
	 * @param newGameListener
	 *            The listener that should be registered.
	 * @return true if and only if the listener has been successfully
	 *         registered.
	 * @throws NullPointerException
	 *             If the given listener is null.
	 */
	boolean registerNewGameListener(NewGameListener newGameListener) throws NullPointerException;

	/**
	 * Unregisters an already registered listener from this creator. If this
	 * creator does not have the listener registered, nothing should happen.
	 * 
	 * @param newGameListener
	 *            The listener that should be unregistered.
	 * @return true if and only if the listener has been successfully removed.
	 */
	boolean unregsiterNewGameListener(NewGameListener newGameListener);

}
