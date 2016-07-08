package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a lobby that is being hosted by a single player.
 */
public class HostedLobby implements Lobby {

	private final PlayerDataManager playerDataManager;

	private Player host;
	private Set<Player> players;
	private Set<Player> invitedPlayers;
	private Set<LobbyListener> lobbyListeners;

	/**
	 * Creates a new lobby.
	 * 
	 * @param host
	 *            The host (the creator) of the lobby.
	 * @throws NullPointerException
	 *             Iff the host is null.
	 */
	public HostedLobby(Player host) throws NullPointerException {
		if (host == null) {
			throw new NullPointerException();
		}

		this.playerDataManager = DowerTefence.getPlugin().getPlayerDataManager();

		this.host = host;
		this.players = new HashSet<>();
		players.add(host);
		this.invitedPlayers = new HashSet<>();
		this.lobbyListeners = new HashSet<>();
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
	@Override
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
	@Override
	public String addPlayer(Player player, String message) throws NullPointerException {
		if (player == null) {
			throw new NullPointerException();
		}

		if (isBusy(player)) {
			return "This player is already in a game or in a lobby.";
		}

		if (isInvited(player)) {
			return "This player has not been invited to the lobby.";
		}

		invitedPlayers.remove(player);
		player.sendMessage(message);
		notifyLobbyListenersNewPlayer(player);
		return "";
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
	@Override
	public String invitePlayer(Player player, String message) throws NullPointerException {
		if (player == null) {
			throw new NullPointerException();
		}

		if (players.contains(player)) {
			return "This player is already in the lobby.";
		}

		if (isBusy(player)) {
			return "This player is already in a game or in a lobby.";
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

	/**
	 * Checks if a player is currently too busy to play.
	 * 
	 * @param player
	 *            The player to check.
	 * @return True iff the player is already in a lobby or in a game.
	 */
	private boolean isBusy(Player player) {
		PlayerData playerInfo = playerDataManager.getData(player);
		return playerInfo.isInGame() || playerInfo.isInLobby();
	}

	@Override
	public boolean hasPermissionToStart(Player player) {
		return player == host; // Only the host is allowed to start the game.
	}

	@Override
	public boolean registerLobbyListener(LobbyListener lobbyListener) {
		return lobbyListeners.add(lobbyListener);
	}

	@Override
	public boolean unregisterLobbyListener(LobbyListener lobbyListener) {
		return lobbyListeners.remove(lobbyListener);
	}

	/**
	 * Notifies all lobby listeners that a new player has been added.
	 * 
	 * @param player
	 *            The player that has been added.
	 */
	private void notifyLobbyListenersNewPlayer(Player player) {
		for (LobbyListener listener : lobbyListeners) {
			listener.onPlayerJoined(player, this);
		}
	}
}
