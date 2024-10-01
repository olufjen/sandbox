package no.games.chess.search;

import aima.core.search.framework.problem.StepCostFunction;
import no.games.chess.planning.ChessPlannerAction;

/**
 * ChessStepCostImpl
 * This class is an implementation of the StepCostFunction interface
 * The <b>step cost</b> of taking action a in state s to reach state s' is
 * denoted by c(s, a, s').
 *
 * @author oluf
 *
 * @param <PlannerState>
 */
public class ChessStepCostImpl<PlannerState> implements StepCostFunction<PlannerState,ChessPlannerAction > {

	@Override
	public double applyAsDouble(PlannerState s, ChessPlannerAction a, PlannerState sDelta) {
		// TODO Auto-generated method stub
		return 0;
	}

}
