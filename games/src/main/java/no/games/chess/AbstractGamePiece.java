package no.games.chess;

import java.util.HashMap;

/**
 * This class represent the abstract gamepiece of a game of chess.
 * 
 * @author oluf
 *
 */
public abstract class AbstractGamePiece<P> implements GamePiece {

	protected String name; // Name of piece
	public static enum pieceType {
		PAWN,
		ROOK,
		BISHOP,
		KNIGHT,
		KING,
		QUEEN
	}
	protected int value;
	
	public abstract P getmyPosition();

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
