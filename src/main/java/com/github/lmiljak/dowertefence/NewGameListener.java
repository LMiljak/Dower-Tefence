package com.github.lmiljak.dowertefence;

/**
 * An interface for classes that want to know when a new Dower Tefence game has
 * been created. It can register itself to a GameCreator, which then notifies
 * this listener when a game has been created.
 */
public interface NewGameListener {

	/**
	 * Called when a game has been created by a GameCreator that has this
	 * listener registered to it.
	 * 
	 * @param newGame
	 *            The created game.
	 * @param creator
	 *            The instance that created the new game.
	 */
	void onNewGame(Game newGame, GameCreator creator);

}
