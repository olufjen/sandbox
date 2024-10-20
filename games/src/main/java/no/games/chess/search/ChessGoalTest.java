package no.games.chess.search;

import java.util.Objects;

import aima.core.search.framework.problem.GoalTest;
import no.games.chess.planning.PlannerState;

/**
 * ChessGoalTest
 * This interface extends the GoalTest interface
 * which determines whether a given state is a goal state.
 * The Goaltest interface extends the Predicate function interface
 * 
 * @author oluf
 *
 * @param <PlannerState>
 */
public interface ChessGoalTest<PlannerState> extends GoalTest<PlannerState> {

    default ChessGoalTest<PlannerState> or(ChessGoalTest<? super PlannerState> other) {
        Objects.requireNonNull(other);
        return (state) -> test(state) || other.test(state);
    }
}
