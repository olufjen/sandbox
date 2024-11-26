package no.games.chess.planning;

import java.util.List;
import aima.core.logic.planning.ActionSchema;
import no.games.chess.ChessPlayer;


/**
 * PlannerState 
 * This interface represent the Planner state of the game.
 * It contains a set of Planner Actions, which again contains
 * ground or lifted action schemas
 * @author oluf
 *
 */
public interface PlannerState {
	public ChessPlannerAction getAction();
	public List<ChessPlannerAction> getActions();
	public ActionSchema getActionSchema();
	public List<ActionSchema> getActionSchemas();
	public void setAction(ChessPlannerAction action);
	public double getUtility();
	public void setUtility(double utility);
	public ChessPlayer getPlayerTomove();
	public boolean testEnd(ChessPlannerAction a);
	public List<ActionSchema> getOtherSchemaList();
}
