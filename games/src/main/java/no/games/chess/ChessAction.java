package no.games.chess;

import java.util.List;








/**
 * @author oluf
 * This interface represent the ChessAction in a game of chess
 *
 * @param <P> Contains the positions of the game and whether they are occupied or not
 * @param <A>
 */
public interface ChessAction<P, A, PR, CP,PP> {
	
	public P getPositions();
	public A getAvailablePositions();
	public PR getPositionRemoved();
	public CP getChessPiece();
	public PP getPreferredPosition();

}