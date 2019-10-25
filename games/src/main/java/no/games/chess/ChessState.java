package no.games.chess;

import java.util.List;



/**
 * @author oluf
 *
 * This interface represent the state current state in the game of chess
 * @param <GameBoard> Represent the chess board in a game of chess
 */
public interface ChessState<GameBoard> {
	public GameBoard getBoard();
	public List<ChessAction> getActions();
	public ChessAction getAction();
	public ChessPlayer getPlayerTomove();
	public void returnMyplayer();
	public void emptyMovements();

	public double getUtility();

	public void setUtility(double utility);

}