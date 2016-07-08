package com.github.lmiljak.dowertefence;

import org.bukkit.entity.Player;

/**
 * Interface for classes that want to listen to changes made in certain lobbies.
 */
public interface LobbyListener {

	/**
	 * Called when a player joined a lobby.
	 * 
	 * @param player
	 *            The player that joined the lobby.
	 * @param lobby
	 *            The lobby in which the player joined.
	 */
	void onPlayerJoined(Player player, Lobby lobby);

	/**
	 * Called when a player leaves a lobby.
	 * 
	 * @param player
	 *            The player that left the lobby.
	 * @param lobby
	 *            The lobby in which the player left.
	 */
	void onPlayerLeft(Player player, Lobby lobby);
}
