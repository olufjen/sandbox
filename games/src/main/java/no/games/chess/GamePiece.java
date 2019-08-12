package no.games.chess;

import java.util.HashMap;

import no.games.chess.AbstractGamePiece.pieceType;

/**
 * THis interface represent a general chesspiece capable of determining its own legal moves
 * @author oluf
 *
 */
public interface GamePiece<P> {

	public  HashMap<String,P> getLegalmoves();
	public pieceType getPieceType();
}
