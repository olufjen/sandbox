package no.games.chess;

import java.util.HashMap;

/**
 * This class represent the abstract gamepiece of a game of chess.
 * 
 * @author oluf
 * @param <P> REpresent the position for the piece
 *
 */
public abstract class AbstractGamePiece<P> implements GamePiece<P> {

	protected String name; // Name of piece
	public static enum pieceType {
		PAWN,
		ROOK,
		BISHOP,
		KNIGHT,
		KING,
		QUEEN
	}
	public static enum pieceColor{
		BLACK,
		WHITE
	}
	protected int value;
	
	public abstract P getmyPosition();
	public abstract void getLegalmoves(P position);
	public abstract void setMyPosition(P myPosition);
	public abstract void produceLegalmoves(P position);

//	public abstract void setNewPosition(P myPosition);
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
