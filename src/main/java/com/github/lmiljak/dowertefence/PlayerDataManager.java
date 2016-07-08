package com.github.lmiljak.dowertefence;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Placeholder for data for many players.
 */
public class PlayerDataManager implements Listener, NewGameListener, NewLobbyListener, LobbyListener {

	private Map<Player, PlayerData> playerData;

	/**
	 * Creates a new player data manager.
	 */
	public PlayerDataManager() {
		this.playerData = new HashMap<>();
	}

	/**
	 * Gets the (modifiable) data for each player.
	 * 
	 * @param player
	 *            The player to get the data of.
	 * @return The data of the player. Null if the player does not exist or is
	 *         offline.w
	 */
	public PlayerData getData(Player player) {
		return playerData.get(player);
	}

	/**
	 * Called when a player logs into the server. The player gets registered by
	 * this data manager.
	 * 
	 * @param event
	 *            The event thrown by the server.
	 */
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		playerData.put(event.getPlayer(), new PlayerData());
	}

	/**
	 * Called when a player leaves the server. The player gets unregistered by
	 * this data manager.
	 * 
	 * @param event
	 *            The event thrown by the server.
	 */
	@EventHandler
	public void onLogout(PlayerQuitEvent event) {
		playerData.remove(event.getPlayer());
	}

	/**
	 * Called when a new game has been created. It updates the info in the
	 * player data by resetting the lobby to the default and setting the game to
	 * the new game for each player in the game.
	 */
	@Override
	public void onNewGame(Game newGame, GameCreator creator) {
		for (Player player : newGame.getPlayers()) {
			PlayerData playerInfo = getData(player);

			playerInfo.setCurrentLobby(PlayerData.DEFAULT_LOBBY);
			playerInfo.setCurrentGame(newGame);
		}
	}

	@Override
	public void onPlayerJoined(Player player, Lobby lobby) {
		playerData.get(player).setCurrentLobby(lobby);
	}

	@Override
	public void onPlayerLeft(Player player, Lobby lobby) {
		playerData.get(player).setCurrentLobby(PlayerData.DEFAULT_LOBBY);
	}

	@Override
	public void onNewLobby(Lobby newLobby, LobbyCreator creator) {
		for (Player player : newLobby.getPlayers()) {
			playerData.get(player).setCurrentLobby(newLobby);
		}
		newLobby.registerLobbyListener(this);
	}

}
