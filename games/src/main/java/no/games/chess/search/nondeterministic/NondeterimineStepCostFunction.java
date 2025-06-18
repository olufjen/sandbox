package no.games.chess.search.nondeterministic;

import aima.core.search.framework.problem.StepCostFunction;
import no.games.chess.planning.ChessPlannerAction;

/**
 * NondeteriminStepCostImpl
 * This class is an implementation of the aima.core.search.framework.problem.StepCostFunction interface.
 * It is an ordinary interface with defined method
 * 
 * The <b>step cost</b> of taking action a in state s to reach state s' is
 * denoted by c(s, a, s').
 * It is the implementation of a stepcost function as described in chapter 4.
 * @author oluf
 *
 * @param <GameState>
 */
public class NondeterimineStepCostFunction<GameState,GameAction> implements StepCostFunction<GameState,GameAction > {

	@Override
	public double applyAsDouble(GameState s, GameAction a, GameState sDelta) {
		// TODO Auto-generated method stub
		return 0;
	}

}
