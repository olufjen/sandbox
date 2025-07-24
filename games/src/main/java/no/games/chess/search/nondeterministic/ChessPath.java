package no.games.chess.search.nondeterministic;

import java.util.LinkedList;

/**
 * ChessPath
 * This class is the chess version of the Path class as described:
 * Represents the path the agent travels through the AND-OR tree (see figure
 * 4.10, page 135, AIMA3e).
 * 
 * @author Andrew Brown
 * @author Ruediger Lunde
 */
public class ChessPath extends LinkedList<GameState> {

	/**
	 * Create a new path containing this path's current states followed by the provided additional state.
	 *
	 * @param state
	 *            the state to be prepended.
	 * @return a new Path that contains the passed in state along with this
	 *         path's current states.
	 */
	public ChessPath append(GameState state) {
		ChessPath appendedPath = new ChessPath();
		appendedPath.addAll(this);
		appendedPath.add(state);
		return appendedPath;
	}

	/**
	 * Create a new path containing the provided additional state followed by this path's current states.
	 * 
	 * @param state
	 *            the state to be prepended.
	 * @return a new Path that contains the passed in state along with this
	 *         path's current states.
	 */
	public ChessPath prepend(GameState state) {
		ChessPath prependedPath = new ChessPath();
		prependedPath.add(state);
		prependedPath.addAll(this);
		return prependedPath;
	}
}
