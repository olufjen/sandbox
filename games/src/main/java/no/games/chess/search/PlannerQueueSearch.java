package no.games.chess.search;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import aima.core.logic.planning.ActionSchema;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.util.Tasks;
import no.games.chess.planning.ChessPlannerAction;
import no.games.chess.planning.PlannerState;

/**
 * PlannerQueueSearch
 * This is a subclass of the Base class for queue-based search implementations.
 * It is used as the implementation for the PlannerQueueBasedSearch
 * @author oluf
 *
 */
public class PlannerQueueSearch extends QueueSearch<PlannerState, ChessPlannerAction> {

	/**
	 * This constructor was protected ??
	 * @param nodeExpander
	 */
	public PlannerQueueSearch(NodeExpander<PlannerState, ChessPlannerAction> nodeExpander) {
		super(nodeExpander); // This call adds a Consumer functional interface as a node listener.
//		nodeExpander.addNodeListener((node) -> metrics.incrementInt(METRIC_NODES_EXPANDED)); Create an alternative nodelistener. !!! ???
// See page 51 Java 8 for examples.		
		frontier = new LinkedList<Node<PlannerState, ChessPlannerAction>>();
		
	}

	@Override
	protected void addToFrontier(Node<PlannerState, ChessPlannerAction> node) {
		super.frontier.add(node);
		
	}

	@Override
	protected Node<PlannerState, ChessPlannerAction> removeFromFrontier() {
//		cleanUpFrontier(); // not really necessary because isFrontierEmpty
		// should be called before...
		Node<PlannerState, ChessPlannerAction> result = null;
		result = frontier.remove();
		updateMetrics(frontier.size());
		// add the node to the explored set of the corresponding problem
//		setExplored(result);
		return result;

	}

	private void setExplored(Node<PlannerState, ChessPlannerAction> result) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Receives a problem and a queue implementing the search strategy and
	 * computes a node referencing a goal state, if such a state was found.
	 * This is a customised version of the template method for tree and graph search
	 * through nodes containing PlannerState and PlannerActions.
	 * 
	 * The primitive operations {@link #addToFrontier(Node)},
	 * {@link #removeFromFrontier()}, and {@link #isFrontierEmpty()}
	 * are investigated to improve the search.
	 * 
	 * Also the Problem testSolution method and the local getSolution method are investigated.
	 * How to investigate the root node such that all available action schemas are explored !!!
	 * @param problem
	 *            the search problem
	 * @param frontier
	 *            the data structure for nodes that are waiting to be expanded
	 * 
	 * @return a node referencing a goal state, if the goal was found, otherwise empty;
	 */
	@Override
	public Optional<Node<PlannerState, ChessPlannerAction>> findNode(Problem<PlannerState, ChessPlannerAction> problem,
			Queue<Node<PlannerState, ChessPlannerAction>> frontier) {
		this.frontier = frontier;
		clearMetrics();
		earlyGoalTest = true;
		// initialize the frontier using the initial state of the problem
		Node<PlannerState, ChessPlannerAction> root = nodeExpander.createRootNode(problem.getInitialState());
		addToFrontier(root);
		if (earlyGoalTest && problem.testSolution(root))
			return getSolution(root);

		while (!isFrontierEmpty() && !Tasks.currIsCancelled()) {
			// choose a leaf node and remove it from the frontier
			Node<PlannerState, ChessPlannerAction> nodeToExpand = removeFromFrontier();
			// only need to check the nodeToExpand if have not already
			// checked before adding to the frontier
			// THe node to expand contains an action but no state OJN 01.11 2024 ???
			if (!earlyGoalTest && problem.testSolution(nodeToExpand))
				// if the node contains a goal state then return the
				// corresponding solution
				return getSolution(nodeToExpand);

			// expand the chosen node, adding the resulting nodes to the
			// frontier
			// This process only add successors that contain actions but no state
			// The expand method notifies NodeListeners. They are Consumer functional interface with an .accept method. One implementation is:
			// (node) -> metrics.incrementInt(METRIC_NODES_EXPANDED)
			for (Node<PlannerState, ChessPlannerAction> successor : nodeExpander.expand(nodeToExpand, problem)) {
				addToFrontier(successor);
				if (earlyGoalTest && problem.testSolution(successor))
					return getSolution(successor);
			}
		}
		// if the frontier is empty then return failure
		return Optional.empty();
	}
		
	private Optional<Node<PlannerState, ChessPlannerAction>> getSolution(Node<PlannerState, ChessPlannerAction> node) {
		metrics.set(METRIC_PATH_COST, node.getPathCost());
		return Optional.of(node);
	}


	@Override
	protected boolean isFrontierEmpty() {
		boolean empty = frontier.isEmpty();
		return empty;
	}

	public Queue<Node<PlannerState, ChessPlannerAction>> getFrontier() {
		return frontier;
	}

	public void setFrontier(Queue<Node<PlannerState, ChessPlannerAction>> frontier) {
		this.frontier = frontier;
	}
}
