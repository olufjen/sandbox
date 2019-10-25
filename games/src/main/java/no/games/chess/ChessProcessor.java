package no.games.chess;

/**
 * ChessProcessor
 * Implementations of this interface process chess objects
 * P,Q,R represent all kinds of chess objects in this structure.
 * @author oluf
 *
 */
public interface ChessProcessor<P,Q,R> {

	R processChessObject(P p,Q q);
}
