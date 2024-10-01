package no.games.chess.search;

import aima.core.search.framework.problem.GoalTest;
import no.games.chess.planning.PlannerState;

/**
 * ChessGoalTest
 * This interface extends the GoalTest interface
 * which determines whether a given state is a goal state.
 * @author oluf
 *
 * @param <PlannerState>
 */
public interface ChessGoalTest<PlannerState> extends GoalTest<PlannerState> {

}
