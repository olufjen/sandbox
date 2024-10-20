package no.games.chess.planning;

import java.util.List;

import aima.core.agent.Action;
import aima.core.logic.planning.ActionSchema;

/**
 * ChessPlannerAction
 * This interface represent the chess planner action
 * It contains ground action schemas or lifted Action schemas.
 * Ground action schemas are based on available chess actions.
 * The action schemas contains preconditions and effects.
 * To each chess action schema there is an initial state and a goal state.
 * Lifted action schemas are produced based on the current planner state and its available ground action schemas, 
 * the move number, and information from the two FOL KB.
 * 
 * 
 * @author oluf
 *
 */
public interface ChessPlannerAction<ActionSchema> extends Action {
	public ActionSchema getActionSchema();
	public List<ActionSchema> getActionSchemas();
	public PlannerState findPlannerState(PlannerState s);
	public PlannerState findPlannerState(ChessPlannerAction a);
	
}
