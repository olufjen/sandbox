package no.games.chess;

/**
 * AbstractPieceMove represent a move made by a player.
 * It contains a fromposition and a toposition.
 * 
 * @author oluf
 *
 * @param <F> The from position
 * @param <T> The to position
 */
public abstract class AbstractPieceMove<F,T> implements PieceMove<F, T> {
	protected int moveNumber;
	protected String moveNotation; // Contains the algebraic notation of the move
	
	public int getMoveNumber() {
		return moveNumber;
	}
	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}
	public String getMoveNotation() {
		return moveNotation;
	}
	public void setMoveNotation(String moveNotation) {
		this.moveNotation = moveNotation;
	}
	

}
