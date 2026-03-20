package no.games.chess.search.nondeterministic;

import java.util.Objects;

import aima.core.search.framework.problem.GoalTest;
import no.games.chess.planning.PlannerState;

/**
 * ChessNonDeterGoalTest
 *  * This interface extends the GoalTest interface
 * which determines whether a given state is a goal state.
 * The Goaltest interface extends the Predicate function interface
 * It is used with the AndOrChessSearch for the nondeterministic search problem
 * 
 * @author oluf
 *
 * @param <PlannerState>
 */
public interface ChessNonDeterGoalTest<GameState> extends GoalTest<GameState> {

    default ChessNonDeterGoalTest<GameState> or(ChessNonDeterGoalTest<? super GameState> other) {
        Objects.requireNonNull(other);
        return (state) -> test(state) || other.test(state);
    }
}
