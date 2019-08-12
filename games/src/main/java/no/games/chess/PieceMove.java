package no.games.chess;

/**
 * This interface represent a PieceMove made by a Player.
 * It contains a fromPosition and
 * a toPosition
 * @author oluf
 *
 * @param <F>
 * @param <T>
 */
public interface PieceMove<F,T> {

	public F getFromPosition();
	public T getToPosition();
	
}
