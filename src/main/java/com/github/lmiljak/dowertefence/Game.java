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

	/**
	 * Checks whether this game is representing an actual game, or a fake one
	 * that does not really represent anything.
	 * 
	 * @return True iff this game is a fake one.
	 */
	default boolean isFake() {
		return false;
	}

}
