package com.github.lmiljak.dowertefence;

/**
 * Interface for classes that want to be notified when a new lobby has been
 * created. Works in a similar way to NewGameListener.
 */
public interface NewLobbyListener {

	/**
	 * Called when a new lobby has been created.
	 * 
	 * @param newLobby
	 *            The lobby that has been created.
	 * @param creator
	 *            The lobby creator that created the lobby.
	 */
	void onNewLobby(Lobby newLobby, LobbyCreator creator);

}
