package no.games.chess.search;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aima.core.logic.planning.ActionSchema;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.problem.Problem;
import aima.core.search.uninformed.IterativeDeepeningSearch;

public class PlannerIterativeSearch<PlannerState, ChessPlannerAction> extends IterativeDeepeningSearch<PlannerState, ChessPlannerAction> {
	private PlannerState state;
	private ChessPlannerAction action;
	private String outputFileName = "C:\\Users\\bruker\\Google Drive\\privat\\ontologies\\analysis\\plannerlogs.txt";
	private PrintWriter writer = null;
	private FileWriter fw = null;
	
	public PlannerIterativeSearch(PlannerState state, ChessPlannerAction action,NodeExpander<PlannerState, ChessPlannerAction> nodeExpander) {
		super(nodeExpander);
		this.state = state;
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
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return super.getMetrics();
	}

	@Override
	public void addNodeListener(Consumer<Node<PlannerState, ChessPlannerAction>> listener) {
		// TODO Auto-generated method stub
		super.addNodeListener(listener);
	}

	@Override
	public boolean removeNodeListener(Consumer<Node<PlannerState, ChessPlannerAction>> listener) {
		// TODO Auto-generated method stub
		return super.removeNodeListener(listener);
	}

}
