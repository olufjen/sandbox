package no.games.chess.search;

import java.util.List;

import aima.core.logic.planning.ActionSchema;
import aima.core.search.framework.Node;
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
	private GoalTest<PlannerState> goalTest; // GoalTest is a predicate
	
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
	
		return state;
	}

	@Override
	public List<ChessPlannerAction> getActions(PlannerState state) {
		List<ChessPlannerAction> actions = state.getActions();
		return state.getActions();
	}
    /**
     * getResult
     * This method applies the result function.
     * The resultfunction extends the BIFunction<S,A,S> interface with the signature (S,A) -> S (p. 53 Java 8)
     * A description of what each action does; the formal name for this is the
     * transition model, specified by a function RESULT(s, a) that returns the state
     * that results from doing action a in state s. We also use the term successor
     * to refer to any state reachable from a given state by a single action.
     * The PlannerAction has a findPlannerState method
     */
	@Override
	public PlannerState getResult(PlannerState state, ChessPlannerAction action) {
	
	    return resultFn.apply(state, action);
		
	}

	@Override
	public boolean testGoal(PlannerState state){
		if(state == null)
			return true;
		boolean t = goalTest.test(state); //Using the goaltest predicate function
		return t;
	}

	@Override
	public double getStepCosts(PlannerState state, ChessPlannerAction action, PlannerState stateDelta) {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * Tests whether a node represents an acceptable solution. The default implementation
     * delegates the check to the goal test. Other implementations could make use of the additional
     * information given by the node (e.g. the sequence of actions leading to the node). A
     * solution tester implementation could for example always return false and internally collect
     * the paths of all nodes whose state passes the goal test. Search implementations should always
     * access the goal test via this method to support solution acceptance testing.
     * 
     * This method is called from the PlannerQueueSearch findNode method.
     * implementation
     */
	@Override
	public boolean testSolution(Node<PlannerState, ChessPlannerAction> node) {
		PlannerState state = node.getState();
/*		List<ActionSchema> schemas = state.getActionSchemas();
		List<ActionSchema> otherschemas = state.getOtherSchemaList();
		System.out.println("SearchProblem other action schemas");
		for (ActionSchema schema: otherschemas) {
			System.out.println(schema.toString());
		}*/
		return testGoal(node.getState());
		//		return super.testSolution(node);
	}

}
