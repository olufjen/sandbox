package no.games.chess;

import java.util.List;

import aima.core.agent.Percept;



/**
 * @author oluf
 *
 * This interface represent the state current state in the game of chess
 * The implementations contains all available chess actions.
 * @param <GameBoard> Represent the chess board in a game of chess
 */
public interface ChessState<GameBoard> extends Percept {
	public GameBoard getBoard();
	public List<ChessAction> getActions();
	public ChessAction getAction();
	public ChessPlayer getPlayerTomove();
	public void returnMyplayer();
	public void emptyMovements();
	public void clearMovements(GamePiece piece);
	public void setAction(ChessAction action);
	public double getUtility();

	public void setUtility(double utility);

}