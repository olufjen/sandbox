package no.games.chess.search.nondeterministic;

import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.StepCostFunction;
import aima.core.search.nondeterministic.ResultsFunction;

import java.util.List;

/**
 * NondeterministicChessProblem
 * This class is the chess version of the NondeterministicProblem class as described:
 * Non-deterministic problems may have multiple results for a given state and
 * action; this class handles these results by mimicking Problem and replacing
 * ResultFunction (one result) with ResultsFunction (a set of results).
 * 
 * @author Andrew Brown
 * @author Ruediger Lunde
 * 
 */
public class NondeterministicChessProblem<GameState,GameAction> {

	protected GameState initialState;
	protected ActionsFunction<GameState, GameAction> actionsFn;
	protected GoalTest<GameState> goalTest;
	protected StepCostFunction<GameState, GameAction> stepCostFn;
	protected ResultsFunction<GameState, GameAction> resultsFn;

	/**
	 * Constructor
	 */
	public NondeterministicChessProblem(GameState initialState,
			ActionsFunction<GameState,GameAction> actionsFn, ResultsFunction<GameState,GameAction> resultsFn,
			GoalTest<GameState> goalTest) {
		this(initialState, actionsFn, resultsFn, goalTest, (s, a, sPrimed) -> 1.0);
	}

	/**
	 * Constructor
	 */
	public NondeterministicChessProblem(GameState initialState,
			ActionsFunction<GameState,GameAction> actionsFn, ResultsFunction<GameState,GameAction> resultsFn,
			GoalTest<GameState> goalTest, StepCostFunction<GameState,GameAction> stepCostFn) {
		this.initialState = initialState;
		this.actionsFn = actionsFn;
		this.resultsFn = resultsFn;
		this.goalTest = goalTest;
		this.stepCostFn = stepCostFn;
	}


	/**
	 * Returns the initial state of the agent.
	 * 
	 * @return the initial state of the agent.
	 */
	public GameState getInitialState() {
		return initialState;
	}

	/**
	 * Returns <code>true</code> if the given state is a goal state.
	 * The goalTest interface is a predicate functional interface
	 * @return <code>true</code> if the given state is a goal state.
	 */
	public boolean testGoal(GameState state) {
		return goalTest.test(state);
	}

	/**
	 * Returns the description of the possible actions available to the agent.
	 * The actionsFn is a functional interface that extends the Function interface (p. 53 Java 8)
	 * The interface has one method: apply
	 */
	List<GameAction> getActions(GameState state) {
		return actionsFn.apply(state);
	}

	/**
	 * Return the description of what each action does.
	 * The resultsFn is an ordinary interface with one method: results(state,action)
	 * @return the description of what each action does - a list of possible outcome states.
	 */
	public List<GameState> getResults(GameState state, GameAction action) {
		return this.resultsFn.results(state, action);
	}

	/**
	 * Returns the <b>step cost</b> of taking action <code>action</code> in state <code>state</code> to reach state
	 * <code>stateDelta</code> denoted by c(s, a, s').
	 * The steCostFn is an ordinary interface with one method: applyAsDouble(state, action, stateDelta)
	 */
	double getStepCosts(GameState state, GameAction action, GameState stateDelta) {
		return stepCostFn.applyAsDouble(state, action, stateDelta);
	}
}
