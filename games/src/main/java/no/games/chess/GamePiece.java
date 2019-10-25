package no.games.chess;

import java.util.HashMap;


import no.games.chess.AbstractGamePiece.pieceColor;
import no.games.chess.AbstractGamePiece.pieceType;

/**
 * This interface represent a general chesspiece capable of determining its own legal moves
 * <P> A type representing positions
 * @author oluf
 *
 */
public interface GamePiece<P> {

	public  HashMap<String,P> getLegalmoves();
	public void getLegalmoves(P position);
	public void produceLegalmoves(P position);
	public pieceType getPieceType();
	public pieceColor getPieceColor();
	public void setMyPosition(P myPosition);
	public HashMap<String, P> getNewPositions();
	public HashMap<String, P> getOntologyPositions();
	public void setOntologyPositions(HashMap<String, P> ontologyPositions);

}
