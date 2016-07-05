package com.github.lmiljak.dowertefence;

import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Represents a game of Dower Tefence containing a number of players.
 */
public interface Game {

	/**
	 * Gets the players currently in this game.
	 * 
	 * @return The players currently in this game.
	 */
	Set<Player> getPlayers();

	/**
	 * Starts the game.
	 */
	void start();

}
