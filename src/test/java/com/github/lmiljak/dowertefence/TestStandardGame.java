package com.github.lmiljak.dowertefence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.junit.Test;

/**
 * Test suite for the StandardGame class.
 */
public class TestStandardGame {

	/**
	 * Asserts that the getPlayers method will return the one player that has
	 * been added through the constructor.
	 */
	@Test
	public void testGetOnePlayer() {
		Player onlinePlayer = mockPlayer(true);
		HashSet<Player> expectedPlayers = new HashSet<>();
		expectedPlayers.add(onlinePlayer);

		StandardGame game = new StandardGame(expectedPlayers);

		Set<Player> actualPlayers = game.getPlayers();

		assertEquals(1, actualPlayers.size());
		assertTrue(actualPlayers.containsAll(expectedPlayers));
	}

	/**
	 * Asserts that the getPlayers method will return all players that have been
	 * added through the constructor.
	 */
	@Test
	public void testGetMorePlayers() {
		final int playerCount = 10;

		HashSet<Player> expectedPlayers = new HashSet<>(playerCount);
		for (int i = 0; i < playerCount; i++) {
			expectedPlayers.add(mockPlayer(true));
		}

		StandardGame game = new StandardGame(expectedPlayers);

		Set<Player> actualPlayers = game.getPlayers();

		assertEquals(playerCount, actualPlayers.size());
		assertTrue(actualPlayers.containsAll(expectedPlayers));
	}

	/**
	 * Asserts that the getPlayers method will return all players that have been
	 * added through the constructor, except for the ones that were offline or
	 * null.
	 */
	@Test
	public void testGetMorePlayerSomeOffline() {
		final int nullCount = 3;
		final int offlineCount = 4;
		final int onlineCount = 5;

		HashSet<Player> players = new HashSet<>(nullCount + offlineCount + onlineCount);
		HashSet<Player> expectedPlayers = new HashSet<>(onlineCount);
		for (int i = 0; i < nullCount; i++) {
			players.add(null);
		}
		for (int i = 0; i < offlineCount; i++) {
			players.add(mockPlayer(false));
		}
		for (int i = 0; i < onlineCount; i++) {
			Player onlinePlayer = mockPlayer(true);
			players.add(onlinePlayer);
			expectedPlayers.add(onlinePlayer);
		}

		StandardGame game = new StandardGame(players);

		Set<Player> actualPlayers = game.getPlayers();

		assertEquals(onlineCount, actualPlayers.size());
		assertTrue(actualPlayers.containsAll(expectedPlayers));
	}

	/**
	 * Asserts that the constructor throws an IllegalArgumentException when it
	 * is invoked with only offline or null players.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNoPlayers() {
		final int nullCount = 3;
		final int offlineCount = 4;

		HashSet<Player> players = new HashSet<>(nullCount + offlineCount);

		for (int i = 0; i < nullCount; i++) {
			players.add(null);
		}
		for (int i = 0; i < offlineCount; i++) {
			players.add(mockPlayer(false));
		}

		new StandardGame(players);
	}

	/**
	 * Creates a mocked Player.
	 * 
	 * @param online
	 *            Whether the player should be online.
	 * @return The created player.
	 */
	private Player mockPlayer(boolean online) {
		Player player = mock(Player.class);
		when(player.isOnline()).thenReturn(online);
		return player;
	}
}
