package no.games.chess;

import java.util.HashMap;



/**
 * This interface is used to determine what type of chesspiece
 * the implemented chesspiece represent
 * @author oluf
 *
 */
public interface ChessPieceType<P> {

	boolean test (GamePiece piece);
	boolean checkName (String name);
	public HashMap<String, P> getNewPositions();

}
