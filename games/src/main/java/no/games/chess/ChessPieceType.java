package no.games.chess;

import java.util.HashMap;


/**
 * This interface is used to determine what type of chesspiece
 * the implemented chesspiece represent
 * <P> The type argument has been removed
 * @author oluf
 *
 */
public interface ChessPieceType {

	boolean test (GamePiece piece);
	boolean checkName (String name);
//	public HashMap<String, P> getNewPositions();
//	public void setMyPosition(P myPosition);
//	public void setNewPosition(P myPosition);
}
