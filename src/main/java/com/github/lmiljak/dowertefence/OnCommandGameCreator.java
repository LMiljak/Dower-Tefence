package com.github.lmiljak.dowertefence;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A GameCreator that creates a game that may contain multiple people, when
 * certain commands have been executed by a user.
 * 
 * More specifically, a player can execute the start command, at which a new
 * StandardGame will be created. If the player invited others as well, those
 * other players must execute the accept command before the game is created.
 */
public class OnCommandGameCreator implements GameCreator, CommandExecutor {

	private Set<NewGameListener> listeners;
	// Maps each player to a potential game he wanted to start
	private Map<Player, PotentialGame> potentialGames;

	/**
	 * Represents a list of players that may become a game. This class is
	 * instantiated when a player wants to start a game, but must wait for other
	 * players to accept the invitation.
	 */
	private static class PotentialGame {

		// Maps each player that got an invite to whether that player accepted
		// the invite
		Map<Player, Boolean> potentialPlayers;

		/**
		 * Constructor for PotentialGame.
		 * 
		 * @param invitedPlayers
		 *            The players that the host invited.
		 */
		PotentialGame(Collection<Player> invitedPlayers) {
			this.potentialPlayers = new HashMap<>(invitedPlayers.size());

			for (Player invitedPlayer : invitedPlayers) {
				potentialPlayers.put(invitedPlayer, false);
			}
		}

		/**
		 * Checks if everybody has accepted their invite yet.
		 * 
		 * @return True if and only if everyone has accepted their invite.
		 */
		boolean hasEveryoneAccepted() {
			for (boolean accepted : potentialPlayers.values()) {
				if (!accepted) {
					return false;
				}
			}

			return true;
		}

	}

	/**
	 * Constructor for OnCommandGameCreator.
	 */
	public OnCommandGameCreator() {
		this.listeners = new HashSet<>();
		this.potentialGames = new HashMap<>();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Processes the start and accept sub commands

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a Player to send this message");
			return false;
		}
		if (args.length == 0) {
			// There should always be at least one argument such as "start" or
			// "accept".
			return false;
		}

		if (label.equals(DowerTefence.PARENT_COMMAND)) {
			switch (args[0]) {
			case "start":
				return processStartCommand((Player) sender, removeFirst(args));
			case "accept":
				return processAcceptCommand((Player) sender, removeFirst(args));
			default:
				return false;
			}
		}

		return false;
	}

	/**
	 * Takes a String array and removes the first String from it.
	 * 
	 * @param stringArray
	 *            The String array to remove the first element of.
	 * @return The new String array.
	 */
	private String[] removeFirst(String[] stringArray) {
		String[] result = new String[stringArray.length - 1];

		for (int i = 1; i < stringArray.length; i++) {
			result[i - 1] = stringArray[i];
		}

		return result;
	}

	/**
	 * Used to process the start command. If the sender of the command invited
	 * no people, the game is immediately created. In the case that the sender
	 * did invite people, a PotentialGame will be created until all players have
	 * accepted.
	 * 
	 * @param sender
	 *            The player that sent the command.
	 * @param invitedPlayers
	 *            The players that got invited by sender (the arguments of the
	 *            command).
	 * @return True if and only if the command was executed successfully.
	 */
	private boolean processStartCommand(Player sender, String[] invitedPlayers) {
		HashSet<Player> participants = new HashSet<>();

		// Check if the sender wants to play single player, if so then just
		// create the game with him in it
		if (invitedPlayers.length == 0) {
			participants.add(sender);
			notifyListeners(new StandardGame(participants));
			return true;
		}

		for (String arg : invitedPlayers) {
			Player invitedPlayer = Bukkit.getPlayer(arg);

			if (invitedPlayer == null) {
				sender.sendMessage("One or more of the specified players do not exist or are not online.");
				return true;
			}

			participants.add(invitedPlayer);
		}

		// Creating a new potential game.
		potentialGames.put(sender, new PotentialGame(participants));
		sender.sendMessage("Waiting for all invited players to accept.");
		return true;
	}

	/**
	 * Used to process the accept command. If a valid PotentialGame has been
	 * found for this player, he will be marked as accepted. If all players in
	 * that PotentialGame have accepted, the game is created.
	 * 
	 * @param sender
	 *            The player that sent the command.
	 * @param hostingPlayer
	 *            The player that invited the sender (the arguments of the
	 *            command).
	 * @return True if and only if the command was executed successfully.
	 */
	private boolean processAcceptCommand(Player sender, String[] hostingPlayer) {
		if (hostingPlayer.length != 1) {
			// The sender failed to follow the usage of this command.
			return false;
		}

		Player host = Bukkit.getPlayer(hostingPlayer[0]);
		if (host == null) {
			sender.sendMessage("The specified players does not exist or is not online.");
			return true;
		}

		PotentialGame potentialGame = potentialGames.get(host);
		if (potentialGame == null) {
			sender.sendMessage("That player is currently not hosting a game.");
			return true;
		}

		if (potentialGame.potentialPlayers.replace(sender, true) == null) {
			sender.sendMessage("You have not been invited to join that game.");
		} else {
			sender.sendMessage("Successfully accepted the invite.");
			if (potentialGame.hasEveryoneAccepted()) {
				// Everybody has accepted, let's create the game.
				potentialGame.potentialPlayers.put(host, true);
				notifyListeners(new StandardGame(potentialGame.potentialPlayers.keySet()));
				potentialGames.remove(host);
			}
		}

		return true;
	}

	/**
	 * Notifies all NewGameListeners about a newly created game.
	 * 
	 * @param createdGame
	 *            The new game about which the listeners should be notified.
	 */
	private void notifyListeners(Game createdGame) {
		for (NewGameListener listener : listeners) {
			listener.onNewGame(createdGame, this);
		}
	}

	@Override
	public boolean registerNewGameListener(NewGameListener newGameListener) throws NullPointerException {
		if (newGameListener == null) {
			throw new NullPointerException();
		}

		return listeners.add(newGameListener);
	}

	@Override
	public boolean unregsiterNewGameListener(NewGameListener newGameListener) {
		return listeners.remove(newGameListener);
	}

}
