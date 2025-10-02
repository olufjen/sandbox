package no.games.chess.search.nondeterministic;

import java.util.List;

import aima.core.logic.planning.ActionSchema;
import no.games.chess.GamePiece;

/**
 * ChessPercept
 * This interface is to be used by the GameState and represent the Aperceptor class:
 * Creates a set of propositionalized action schemas from the lifted action schema
 * These action schemas are used as a basis for problem solving see the method createLiftedActions
 * Interim changes again
 * @author oluf
 *
 */
public interface ChessPercept {

	public boolean createLiftedActions(String... names);
	public List<GamePiece> checkOpponentthreat(String name, String newPos, String fact);
	public List<ActionSchema> getOtherActions();
}
