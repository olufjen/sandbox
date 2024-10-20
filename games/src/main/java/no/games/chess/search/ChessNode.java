package no.games.chess.search;

import aima.core.search.framework.Node;
import no.games.chess.planning.ChessPlannerAction;
import no.games.chess.planning.PlannerState;

/**
 * ChessNode
 * This is the subclass of the aima.core.search.framework Node
 * 
 * @author oluf
 *
 */
public class ChessNode extends Node<PlannerState, ChessPlannerAction> {

	public ChessNode(PlannerState state) {
		super(state);
		// TODO Auto-generated constructor stub
	}
	public ChessNode(PlannerState state, ChessNode parent, ChessPlannerAction action, double pathCost) {
		super(state,parent,action,pathCost);

	}
}
