package com.github.lmiljak.dowertefence;

import org.bukkit.entity.Player;

/**
 * Listens for newly created games and then automatically starts them.
 */
public class AutoGameStarter implements NewGameListener {

	@Override
	public void onNewGame(Game newGame, GameCreator creator) {
		try {
			newGame.start();
		} catch (Exception e) {
			for (Player participant : newGame.getPlayers()) {
				participant.sendMessage("The DT game has been unsuccessfully started, reason: " + e.getMessage());
			}
		}
	}

}
