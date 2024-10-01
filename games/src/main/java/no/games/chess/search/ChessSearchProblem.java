package no.games.chess.search;

import java.util.List;

import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.problem.ResultFunction;
import aima.core.search.framework.problem.StepCostFunction;
import no.games.chess.planning.ChessPlannerAction;
import no.games.chess.planning.PlannerState;

/**
 * ChessSearchProblem
 * This class implements the search Problem interface as described in section 3.1.1 p. 66 in the AIMA textbook.
 * The ActionFunction is a Function interface with signature: Function<S,A> S -> A;
 * The ResultFunction is a BiFunction interface with signature: BiFunction<S,A,S> (S,A) -> S
 * @author oluf
 *
 */
public class ChessSearchProblem extends GeneralProblem<PlannerState, ChessPlannerAction> implements Problem<PlannerState, ChessPlannerAction> {
	private PlannerState state;
	private ChessPlannerAction action;
	private List<ChessPlannerAction> actions;
	
	public ChessSearchProblem(PlannerState initialState, ActionsFunction<PlannerState, ChessPlannerAction> actionsFn, ResultFunction<PlannerState, ChessPlannerAction> resultFn,
			GoalTest<?> goalTest) {
		super(initialState, (ActionsFunction<PlannerState, ChessPlannerAction>) actionsFn, (ResultFunction<PlannerState,
				ChessPlannerAction>) resultFn, (GoalTest<PlannerState>) goalTest);
		// TODO Auto-generated constructor stub
	}


	public ChessSearchProblem(PlannerState initialState, ActionsFunction<PlannerState, ChessPlannerAction> actionsFn,
			ResultFunction<PlannerState, ChessPlannerAction> resultFn, GoalTest<PlannerState> goalTest,
			StepCostFunction<PlannerState, ChessPlannerAction> stepCostFn, PlannerState state,
			ChessPlannerAction action, List<ChessPlannerAction> actions) {
		super(initialState, actionsFn, resultFn, goalTest, stepCostFn);
		this.state = state;
		this.action = action;
		this.actions = actions;
	}


	@Override
	public PlannerState getInitialState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public List<ChessPlannerAction> getActions(PlannerState state) {
		// TODO Auto-generated method stub
		return state.getActions();
	}

	@Override
	public PlannerState getResult(PlannerState state, ChessPlannerAction action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testGoal(PlannerState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getStepCosts(PlannerState state, ChessPlannerAction action, PlannerState stateDelta) {
		// TODO Auto-generated method stub
		return 0;
	}

}
