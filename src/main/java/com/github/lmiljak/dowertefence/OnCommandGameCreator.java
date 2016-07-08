package com.github.lmiljak.dowertefence;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import nl.taico.taeirlib.commands.CommandArgs;
import nl.taico.taeirlib.commands.TCommand;

/**
 * A GameCreator that creates a game that may contain multiple people, when
 * certain commands have been executed by a user.
 */
public class OnCommandGameCreator implements GameCreator, LobbyCreator {

	/**
	 * The description of the "/dt start" command.
	 */
	public static final String START_DESCRIPTION = "Starts a game of Dower Tefence. "
			+ "If you are not in a lobby a single player game will be started. "
			+ "If you are in a lobby and you are the host of that lobby, "
			+ "you will start the game with all the players in it.";

	/**
	 * The description of the "/dt invite" command.
	 */
	public static final String INVITE_DESCRIPTION = "Invites a player to join your "
			+ "lobby. If you are currently not in a lobby, the lobby is " + "automatically created.";

	public static final String JOIN_DESCRIPTION = "Makes you join a lobby you have been invited to.";

	private final PlayerDataManager playerDataManager;
	private Set<NewGameListener> newGameListeners;
	private Set<NewLobbyListener> newLobbyListeners;

	/**
	 * Constructor for OnCommandGameCreator.
	 */
	public OnCommandGameCreator() {
		this.playerDataManager = DowerTefence.getPlugin().getPlayerDataManager();

		this.newGameListeners = new HashSet<>();
		this.newLobbyListeners = new HashSet<>();
	}

	/**
	 * Called when the /dt start command is executed. It creates a standard game
	 * of Dower Tefence depending on whether the player that executed it is in a
	 * HostedLobby or not. If the player is not in a lobby, a single player game
	 * is started. Otherwise, if the player is in a lobby and he is the host, a
	 * game is started with all the players in the lobby.
	 * 
	 * @param commandArgs
	 *            The wrapper for different arguments of the command.
	 * @return True iff the command was correctly used.
	 */
	@TCommand(name = "dt.start", usage = "/dt start", description = START_DESCRIPTION, showInHelp = true, console = false)
	public boolean onStartCommand(CommandArgs commandArgs) {
		if (commandArgs.getArgs().length != 0) {
			return false;
		}

		Player commandSender = commandArgs.getPlayer();

		if (!isBusy(commandSender)) {
			createSinglePlayerGame(commandSender);
			return true;
		}

		Lobby lobby = getLobby(commandSender);
		if (lobby.hasPermissionToStart(commandSender)) {
			createMultiplayerGame(lobby);
		} else {
			commandSender.sendMessage("You do not have permission in the lobby to start this game.");
		}

		return true;
	}

	/**
	 * Called when the /dt invite [player] command is executed. It invites the
	 * given player to the lobby of the command sender. If the command sender is
	 * currently not in a lobby, one is automatically created. If the invited
	 * player is already in a lobby or in a game, the invite is cancelled.
	 * 
	 * @param commandArgs
	 *            The wrapper for different arguments of the command.
	 * @return True iff the command was correctly used.
	 */
	@TCommand(name = "dt.invite", usage = "/dt invite <player>", description = INVITE_DESCRIPTION, showInHelp = true, console = false)
	public boolean onInviteCommand(CommandArgs commandArgs) {
		String[] args = commandArgs.getArgs();
		if (args.length != 1) {
			return false;
		}

		Player commandSender = commandArgs.getPlayer();
		PlayerData senderInfo = playerDataManager.getData(commandSender);
		if (senderInfo.isInGame()) {
			commandSender.sendMessage("You can't invite players while already in a game.");
			return true;
		}

		Player toInvite = Bukkit.getPlayer(args[0]);
		if (toInvite == null) {
			commandSender.sendMessage("The player " + args[0] + " could not be found.");
			return false;
		}

		Lobby lobby;
		boolean newLobby = false;
		if (!senderInfo.isInLobby()) {
			lobby = new HostedLobby(commandSender);
			newLobby = true;
		} else {
			lobby = getLobby(commandSender);
		}

		String error = lobby.invitePlayer(toInvite, "You have been invited by " + commandSender.getName()
				+ " to join his lobby. Type /dt join " + commandSender.getName() + " to join him.");
		if (!error.isEmpty()) {
			commandSender.sendMessage("Failed to invite player " + toInvite.getName() + ", reason: " + error);
		} else {
			commandSender.sendMessage("Successfully invited player " + toInvite.getName() + ".");
			if (newLobby) {
				notifyNewLobbyListeners(lobby);
			}
		}
		return true;
	}

	/**
	 * Called when the /dt join [player] command is executed. It adds the sender
	 * of the command to the lobby in which the given player is located, if the
	 * sender has been invited to that lobby.
	 * 
	 * @param commandArgs
	 *            The wrapper for different arguments of the command.
	 * @return True iff the command was correctly used.
	 */
	@TCommand(name = "dt.join", usage = "/dt join <player>", description = JOIN_DESCRIPTION, showInHelp = true, console = false)
	public boolean onJoinCommand(CommandArgs commandArgs) {
		String[] args = commandArgs.getArgs();
		if (args.length != 1) {
			return false;
		}

		Player commandSender = commandArgs.getPlayer();
		Player toJoin = Bukkit.getPlayer(args[0]);
		if (toJoin == null) {
			commandSender.sendMessage("The player " + args[0] + " could not be found.");
			return false;
		}

		Lobby lobby = getLobby(toJoin);
		String error = lobby.addPlayer(commandSender,
				"You've successfully joined the lobby of " + toJoin.getName() + ".");

		if (!error.isEmpty()) {
			commandSender.sendMessage("Failed to join the lobby of " + toJoin.getName() + ", reason: " + error);
		}

		return true;
	}

	/**
	 * Creates a standard game with one player in it.
	 * 
	 * @param player
	 *            The player in the standard game.
	 */
	private void createSinglePlayerGame(Player player) {
		HashSet<Player> players = new HashSet<>();
		players.add(player);
		notifyNewGameListeners(new StandardGame(players));
	}

	/**
	 * Creates a standard game with multiple players in it.
	 * 
	 * @param lobby
	 *            The lobby containing all the players.
	 */
	private void createMultiplayerGame(Lobby lobby) {
		notifyNewGameListeners(new StandardGame(lobby.getPlayers()));
	}

	/**
	 * Notifies all NewGameListeners about a newly created game.
	 * 
	 * @param createdGame
	 *            The new game about which the newGameListeners should be
	 *            notified.
	 */
	private void notifyNewGameListeners(Game createdGame) {
		for (NewGameListener listener : newGameListeners) {
			listener.onNewGame(createdGame, this);
		}
	}

	@Override
	public boolean registerNewGameListener(NewGameListener newGameListener) throws NullPointerException {
		if (newGameListener == null) {
			throw new NullPointerException();
		}

		return newGameListeners.add(newGameListener);
	}

	@Override
	public boolean unregisterNewGameListener(NewGameListener newGameListener) {
		return newGameListeners.remove(newGameListener);
	}

	/**
	 * Checks if a player is currently in a lobby or in a game.
	 * 
	 * @param player
	 *            The player to check.
	 * @return True iff the player is in a lobby or in a game.
	 */
	private boolean isBusy(Player player) {
		PlayerData playerInfo = playerDataManager.getData(player);

		return playerInfo.isInGame() || playerInfo.isInLobby();
	}

	/**
	 * Gets the lobby a certain player is currently in.
	 * 
	 * @param player
	 *            The player to get the lobby of.
	 * @return The lobby the player is currently in.
	 */
	private Lobby getLobby(Player player) {
		return playerDataManager.getData(player).getCurrentLobby();
	}

	/**
	 * Notifies all new lobby listeners that a new lobby has been added.
	 * 
	 * @param newLobby
	 *            The new lobby that has been added.
	 */
	private void notifyNewLobbyListeners(Lobby newLobby) {
		for (NewLobbyListener listener : newLobbyListeners) {
			listener.onNewLobby(newLobby, this);
		}
	}

	@Override
	public boolean registerNewLobbyListener(NewLobbyListener newLobbyListener) throws NullPointerException {
		if (newLobbyListener == null) {
			throw new NullPointerException();
		}

		return newLobbyListeners.add(newLobbyListener);
	}

	@Override
	public boolean unregisterNewLobbyListener(NewLobbyListener newLobbyListener) {
		return newLobbyListeners.remove(newLobbyListener);
	}

}
