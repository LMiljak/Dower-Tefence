package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;

/**
 * A standard game of Dower Tefence. What this game should do on startup is
 * still very vague and will be implemented soon.
 */
public class StandardGame implements Game {

	private HashSet<Player> players;

	/**
	 * Creates a new StandardGame containing one or more players.
	 * 
	 * @param players
	 *            The players in this game. Null players or offline players are
	 *            ignored.
	 * @throws IllegalArgumentException
	 *             If there is not at least one player that isn't null or
	 *             offline.
	 */
	public StandardGame(Set<Player> players) throws IllegalArgumentException {
		this.players = new HashSet<>();

		for (Player player : players) {
			// Only add non-null players and online players
			if (player != null && player.isOnline()) {
				players.add(player);
			}
		}

		// Check if there is at least one player in the game
		if (players.isEmpty()) {
			throw new IllegalArgumentException("There are no online players in the set " + players);
		}
	}

	@Override
	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}

	@Override
	public void start() {
		// TODO It is not specified what should happen when a standard game has
		// been started
		throw new NotImplementedException("It is not specified what should happen when a standard game is started");
	}

}
