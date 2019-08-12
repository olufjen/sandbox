package no.games.chess;

import java.util.List;



/**
 * @author oluf
 *
 * This interface represent the state current state in the game of chess
 * @param <B> Represent the chess board in a game of chess
 */
public interface ChessState<B> {
	public B getBoard();
	public List<ChessAction> getActions();

	public ChessPlayer getPlayerTomove();


	public double getUtility();

	public void setUtility(double utility);

}