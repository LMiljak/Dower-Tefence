package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a lobby, a placeholder for certain players that may end up playing
 * together.
 */
public class Lobby {

	private Player host;
	private Set<Player> players;
	private Set<Player> invitedPlayers;

	/**
	 * Creates a new lobby.
	 * 
	 * @param host
	 *            The host (the creator) of the lobby.
	 * @throws NullPointerException
	 *             Iff the host is null.
	 */
	public Lobby(Player host) throws NullPointerException {
		if (host == null) {
			throw new NullPointerException();
		}

		this.host = host;
		this.players = new HashSet<>();
		this.invitedPlayers = new HashSet<>();
	}

	/**
	 * Gets the host of this lobby.
	 * 
	 * @return The host of this lobby.
	 */
	public Player getHost() {
		return host;
	}

	/**
	 * Gets the players currently in this lobby.
	 * 
	 * @return The players currently in this lobby.
	 */
	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}

	/**
	 * Gets the players that have been invited to join this lobby.
	 * 
	 * @return The players that have been invited to join this lobby.
	 */
	public Set<Player> getInvitedPlayers() {
		return new HashSet<>(invitedPlayers);
	}

	/**
	 * Adds a player to this lobby. If the player was invited, he is removed
	 * from the set of invited players.
	 * 
	 * @param player
	 *            The player to add.
	 * @param message
	 *            The message to send to the player if he has been successfully
	 *            added.
	 * @return An empty string if the player was successfully added, otherwise a
	 *         message as to why the player was not added successfully.
	 * @throws NullPointerException
	 *             Iff the player to add is null.
	 */
	public String addPlayer(Player player, String message) throws NullPointerException {
		if (player == null) {
			throw new NullPointerException();
		}

		invitedPlayers.remove(player);
		if (players.add(player)) {
			return "";
		} else {
			return "This player is already in the lobby.";
		}
	}

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
	 * @throws NullPointerException
	 *             Iff the player to invite is null.
	 */
	public String invitePlayer(Player player, String message) throws NullPointerException {
		if (player == null) {
			throw new NullPointerException();
		}

		if (players.contains(player)) {
			return "This player is already in the lobby.";
		}

		if (invitedPlayers.add(player)) {
			player.sendMessage(message);
			return "";
		} else {
			return "This player is already invited.";
		}
	}

	/**
	 * Checks if a player has been invited to this lobby.
	 * 
	 * @param player
	 *            The player to check.
	 * @return True iff the player has been invited to this lobby.
	 */
	public boolean isInvited(Player player) {
		return invitedPlayers.contains(player);
	}

}
