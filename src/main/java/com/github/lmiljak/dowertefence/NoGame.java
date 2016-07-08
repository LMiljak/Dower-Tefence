package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a fake game. Used by PlayerData to mark players that currently are
 * in no game at all.
 */
public class NoGame implements Game {

	@Override
	public Set<Player> getPlayers() {
		return new HashSet<>();
	}

	@Override
	public void start() {
	}

	@Override
	public boolean isFake() {
		return true;
	}

}
