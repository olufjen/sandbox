package no.games.chess;

import java.util.List;








/**
 * @author oluf
 * This interface represent the ChessAction in a game of chess
 *
 * @param <P> Contains the positions of the game and whether they are occupied or not
 * @param <A> Contains available positions
 * @param <PR> Contains positions removed
 * @param <GamePiece> Represent a ChessPiece
 * @param <PP> Represent the preferred position
 */
public interface ChessAction<P, A, PR, GamePiece,PP> {
	
	public P getPositions();
	public A getAvailablePositions();
	public PR getPositionRemoved();
	public GamePiece getChessPiece();
	public PP getPreferredPosition();
	public void setPreferredPosition(PP p);
}