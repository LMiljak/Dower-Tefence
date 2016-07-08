package com.github.lmiljak.dowertefence;

/**
 * An interface for classes that create lobbies and notifies all new lobby
 * listeners that a lobby has been created.
 */
public interface LobbyCreator {

	/**
	 * Registers a new lobby listener to this lobby creator. Registered
	 * listeners should be notified when a new lobby has been created.
	 * 
	 * @param newLobbyListener
	 *            The new lobby listener that should be added.
	 * @return True iff the new lobby listener has been registered successfully.
	 * @throws NullPointerException
	 *             if the given new lobby listener is null.
	 */
	boolean registerNewLobbyListener(NewLobbyListener newLobbyListener) throws NullPointerException;

	/**
	 * Unregisters a new lobby listener from this lobby creator.
	 * 
	 * @param newLobbyListener
	 *            The new lobby listener to unregister.
	 * @return True iff the given new lobby listener has been unregistered
	 *         successfully.
	 */
	boolean unregisterNewLobbyListener(NewLobbyListener newLobbyListener);

}
