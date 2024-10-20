package no.games.chess.search;

import java.util.LinkedList;
import java.util.Queue;

import aima.core.logic.planning.ActionSchema;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.qsearch.QueueSearch;
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
		super(nodeExpander);
		frontier = new LinkedList<Node<PlannerState, ChessPlannerAction>>();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addToFrontier(Node<PlannerState, ChessPlannerAction> node) {
		super.frontier.add(node);
		
	}

	@Override
	protected Node<PlannerState, ChessPlannerAction> removeFromFrontier() {
//		cleanUpFrontier(); // not really necessary because isFrontierEmpty
		// should be called before...
		Node<PlannerState, ChessPlannerAction> result = frontier.remove();
		updateMetrics(frontier.size());
		// add the node to the explored set of the corresponding problem
//		setExplored(result);
		return result;

	}

	private void setExplored(Node<PlannerState, ChessPlannerAction> result) {
		// TODO Auto-generated method stub
		
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
