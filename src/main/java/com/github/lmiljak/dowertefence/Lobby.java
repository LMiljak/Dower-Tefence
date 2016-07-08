package com.github.lmiljak.dowertefence;

import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a lobby, a placeholder for certain players that may end up playing
 * together.
 */
public interface Lobby {

	/**
	 * Gets the players in the lobby.
	 * 
	 * @return The players in the lobby.
	 */
	Set<Player> getPlayers();

	/**
	 * Adds a player to this lobby.
	 * 
	 * @param player
	 *            The player to add.
	 * @param message
	 *            The message to send to the player if he has been successfully
	 *            added.
	 * @return An empty string if the player was successfully added, otherwise a
	 *         message as to why the player was not added successfully.
	 */
	String addPlayer(Player player, String message);

	/**
	 * Invites a player to this lobby.
	 * 
	 * @param player
	 *            The player to invite.
	 * @param message
	 *            The message to send to the player if he has been successfully
	 *            invited.
	 * @return An empty string if the player was successfully invited, otherwise
	 *         a message as to why the player was not invited successfully.
	 */
	String invitePlayer(Player player, String message);

	/**
	 * Checks if a player has permission to start a game with all the players in
	 * the lobby in it.
	 * 
	 * @param player
	 *            The player to check.
	 * @return True iff the player has that permission.
	 */
	boolean hasPermissionToStart(Player player);

	/**
	 * Registers a lobby listener to this lobby. Successfully added listeners
	 * should be notified when a player enters or leaves a lobby.
	 * 
	 * @param lobbyListener
	 *            The lobby listener that should be registered.
	 * @return True iff the lobby listener was successfully registered.
	 */
	boolean registerLobbyListener(LobbyListener lobbyListener);

	/**
	 * unregisters a lobby listener from this lobby.
	 * 
	 * @param lobbyListener
	 *            The lobby listener that should be unregistered.
	 * @return True iff the lobby listener was successfully unregistered.
	 */
	boolean unregisterLobbyListener(LobbyListener lobbyListener);

	/**
	 * Checks whether this lobby is representing an actual lobby, or a fake one
	 * that does not really represent anything.
	 * 
	 * @return True iff this lobby is a fake one.
	 */
	default boolean isFake() {
		return false;
	}

}
