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
 * The Goaltest function is Predicate interface, used to test for a goal state.
 * @author oluf
 *
 */
public class ChessSearchProblem extends GeneralProblem<PlannerState, ChessPlannerAction> implements Problem<PlannerState, ChessPlannerAction> {
	private PlannerState state;
	private ChessPlannerAction action;
	private List<ChessPlannerAction> actions;
	private ResultFunction<PlannerState, ChessPlannerAction> resultFn;
	private GoalTest<PlannerState> goalTest;
	
	public ChessSearchProblem(PlannerState initialState, ActionsFunction<PlannerState, ChessPlannerAction> actionsFn, ResultFunction<PlannerState, ChessPlannerAction> resultFn,
			GoalTest<?> goalTest) {
		super(initialState, (ActionsFunction<PlannerState, ChessPlannerAction>) actionsFn, (ResultFunction<PlannerState,
				ChessPlannerAction>) resultFn, (GoalTest<PlannerState>) goalTest);
		
	}


	/**
	 * The constructor of the chess search problem
	 * @param initialState
	 * @param actionsFn
	 * @param resultFn
	 * @param goalTest
	 * @param stepCostFn
	 * @param state
	 * @param action
	 * @param actions
	 */
	public ChessSearchProblem(PlannerState initialState, ActionsFunction<PlannerState, ChessPlannerAction> actionsFn,
			ResultFunction<PlannerState, ChessPlannerAction> resultFn, GoalTest<PlannerState> goalTest,
			StepCostFunction<PlannerState, ChessPlannerAction> stepCostFn, PlannerState state,
			ChessPlannerAction action, List<ChessPlannerAction> actions) {
		super(initialState, actionsFn, resultFn, goalTest, stepCostFn);
		this.state = state;
		this.action = action;
		this.actions = actions;
		this.resultFn = resultFn;
		this.goalTest = goalTest;
	}


	@Override
	public PlannerState getInitialState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public List<ChessPlannerAction> getActions(PlannerState state) {
		// TODO Auto-generated method stub
		List<ChessPlannerAction> actions = state.getActions();
		return state.getActions();
	}

	@Override
	public PlannerState getResult(PlannerState state, ChessPlannerAction action) {
	
	    return resultFn.apply(state, action);
		
	}

	@Override
	public boolean testGoal(PlannerState state){
		if(state == null)
			return true;
		boolean t = goalTest.test(state);
		return t;
	}

	@Override
	public double getStepCosts(PlannerState state, ChessPlannerAction action, PlannerState stateDelta) {
		// TODO Auto-generated method stub
		return 0;
	}

}
