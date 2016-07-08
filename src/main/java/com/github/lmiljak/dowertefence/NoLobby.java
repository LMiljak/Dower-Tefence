package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a fake lobby. Used by PlayerData to mark players that currently
 * are in no lobby at all.
 */
public class NoLobby implements Lobby {

	@Override
	public Set<Player> getPlayers() {
		return new HashSet<>();
	}

	@Override
	public boolean isFake() {
		return true;
	}

	@Override
	public boolean hasPermissionToStart(Player player) {
		return false;
	}

	@Override
	public String invitePlayer(Player player, String message) {
		return "Can't invite a player to a fake lobby.";
	}

	@Override
	public String addPlayer(Player player, String message) {
		return "Can't add a player to a fake lobby.";
	}

	@Override
	public boolean registerLobbyListener(LobbyListener lobbyListener) {
		return false;
	}

	@Override
	public boolean unregisterLobbyListener(LobbyListener lobbyListener) {
		return false;
	}

}
