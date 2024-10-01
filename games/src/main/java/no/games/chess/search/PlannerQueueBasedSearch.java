package no.games.chess.search;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.QueueBasedSearch;
import aima.core.search.framework.QueueFactory;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchForStates;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.search.informed.BestFirstSearch;
import aima.core.search.informed.HeuristicEvaluationFunction;
import aima.core.search.informed.GreedyBestFirstSearch.EvalFunction;
import no.games.chess.planning.ChessPlannerAction;
import no.games.chess.planning.PlannerState;

/**
 * This is the chess game subclass of QueueBasedSearch.
 * It uses the subclass PlannerQueueSearch as the implementation for QueueSearch
 * @author oluf
 *
 */
public class PlannerQueueBasedSearch extends BestFirstSearch<PlannerState, ChessPlannerAction>
		implements SearchForActions<PlannerState, ChessPlannerAction>, SearchForStates<PlannerState, ChessPlannerAction> {

	private PlannerState state;
	private ChessPlannerAction action;
	private PlannerQueueSearch impl;
	public PlannerQueueBasedSearch(PlannerQueueSearch impl,
			ToDoubleFunction<Node<PlannerState, ChessPlannerAction>> h) {
		super(impl,new EvalFunction<>(h));

	}

	/**
	 * PlannerQueueBasedSearch
	 * This constructor creates a priority queue and a root node
	 * @param impl The implementation of the queue search
	 * @param queue A queue of nodes initially empty
	 * @param state a planner state
	 * @param action a chess planner action
	 */
	public PlannerQueueBasedSearch(PlannerQueueSearch impl,
			PlannerState state, ChessPlannerAction action,ToDoubleFunction<Node<PlannerState, ChessPlannerAction>> h) {
		super(impl,new EvalFunction<PlannerState, ChessPlannerAction>(h)); // This call makes the following call: QueueFactory.createPriorityQueue(Comparator.comparing(evalFn::applyAsDouble)))
		this.state = state;
		this.action = action;
		NodeExpander exp = impl.getNodeExpander().useParentLinks(true);
		ChessNode node = (ChessNode) exp.createRootNode(state);
		this.impl =  impl;
		this.impl.addToFrontier(node);
//		queue.add(node);
		
	}

	public PlannerState getState() {
		return state;
	}

	public void setState(PlannerState state) {
		this.state = state;
	}

	public ChessPlannerAction getAction() {
		return action;
	}

	public void setAction(ChessPlannerAction action) {
		this.action = action;
	}

	@Override
	public Optional<List<ChessPlannerAction>> findActions(Problem<PlannerState, ChessPlannerAction> p) {
		// TODO Auto-generated method stub
		return super.findActions(p);
	}

	@Override
	public Optional<PlannerState> findState(Problem<PlannerState, ChessPlannerAction> p) {
		// TODO Auto-generated method stub
		return super.findState(p);
	}

	@Override
	public void addNodeListener(Consumer<Node<PlannerState, ChessPlannerAction>> listener) {
		// TODO Auto-generated method stub
		super.addNodeListener(listener);
	}
	
    /**
     * internal class EvalFunction is used for evaluation of a node in the search
     * @author oluf
     *
     * @param <PlannerState>
     * @param <ChessPlannerAction>
     */
    public static class EvalFunction<PlannerState, ChessPlannerAction> extends HeuristicEvaluationFunction<PlannerState, ChessPlannerAction> {
        private ToDoubleFunction<ChessNode> g;

        public EvalFunction(ToDoubleFunction<Node<PlannerState, ChessPlannerAction>> h) {
            this.h = h;
            this.g = ChessNode::getPathCost; // Thge  definition of the functional interface
        }

        /**
         * Returns <em>g(n)</em> the cost to reach the node, plus <em>h(n)</em> the
         * heuristic cost to get from the specified node to the goal.
         *
         * @param n a node
         * @return g(n) + h(n)
         */
        @Override
        public double applyAsDouble(Node<PlannerState, ChessPlannerAction> n) {
            // f(n) = g(n) + h(n)
            return g.applyAsDouble((ChessNode) n) + h.applyAsDouble(n);
        }
    }
}
