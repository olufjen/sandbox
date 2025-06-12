package no.games.chess.search.online;

import java.util.List;

import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.OnlineSearchProblem;
import aima.core.search.nondeterministic.NondeterministicProblem;
import aima.core.search.nondeterministic.ResultsFunction;
import no.games.chess.planning.ChessPlannerAction;
import no.games.chess.planning.PlannerState;

/**
 * @author ojn
 * ChessOnlineSearchProblem
 * This class implements the Online search Problem interface as described in chapter for of AIMA. (p. 147).
 * From the AIMA book:
 * An online search problem must be solved by an agent executing actions. WE assume a deterministic and fully observable environment.
 * Non-deterministic problems may have multiple results for a given state and
 * action; this class handles these results by mimicking Problem and replacing
 * ResultFunction (one result) with ResultsFunction (a set of results).
 * @param <PlannerState>
 * @param <ChessPlannerAction>
 */
public class ChessOnlineSearchProblem<PlannerState, ChessPlannerAction> extends NondeterministicProblem implements OnlineSearchProblem<PlannerState, ChessPlannerAction> {

	public ChessOnlineSearchProblem(PlannerState initialState, ActionsFunction actionsFn, ResultsFunction resultsFn,
			GoalTest goalTest) {
		super(initialState, actionsFn, resultsFn, goalTest);
		// TODO Auto-generated constructor stub
	}


	@Override
	public PlannerState getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see aima.core.search.framework.problem.OnlineSearchProblem#getActions(java.lang.Object)
	 */
	/*
	 *Returns a List of actions allowed in state S
	 */
	@Override
	public List<ChessPlannerAction> getActions(PlannerState state) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see aima.core.search.framework.problem.OnlineSearchProblem#testGoal(java.lang.Object)
	 * A goal test function
	 */


	@Override
	public double getStepCosts(PlannerState state, ChessPlannerAction action, PlannerState stateDelta) {
		// TODO Auto-generated method stub
		return 0;
	}


}
