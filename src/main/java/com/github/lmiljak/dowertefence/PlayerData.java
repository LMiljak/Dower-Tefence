package com.github.lmiljak.dowertefence;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains information of a player regarding the Dower Tefence plugin.
 */
public class PlayerData {

	/**
	 * Represents the lobby all players are in right when they join the server.
	 */
	public static final Lobby DEFAULT_LOBBY = new NoLobby();

	/**
	 * Represents the game all players are in right when the join the server.
	 */
	public static final Game DEFAULT_GAME = new NoGame();

	@Getter
	@Setter
	private Lobby currentLobby;
	@Getter
	@Setter
	private Game currentGame;

	/**
	 * Initialises data for a player.
	 */
	public PlayerData() {
		this.currentLobby = DEFAULT_LOBBY;
		this.currentGame = DEFAULT_GAME;
	}

	/**
	 * Checks if the player is currently playing a game of Dower Tefence.
	 * 
	 * @return True iff the player is currently playing a game of Dower Tefence.
	 */
	public boolean isInGame() {
		return !currentGame.isFake();
	}

	/**
	 * Checks if the player is currently in a lobby.
	 * 
	 * @return True iff the player is currently in a lobby.
	 */
	public boolean isInLobby() {
		return !currentLobby.isFake();
	}

}
