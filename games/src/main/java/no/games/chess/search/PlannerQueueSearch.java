package no.games.chess.search;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addToFrontier(Node<PlannerState, ChessPlannerAction> node) {
		super.frontier.add(node);
		
	}

	@Override
	protected Node<PlannerState, ChessPlannerAction> removeFromFrontier() {
		
		return null;
	}

	@Override
	protected boolean isFrontierEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
