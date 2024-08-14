package no.games.chess.planning;

import java.util.List;

import aima.core.logic.planning.ActionSchema;
import no.games.chess.ChessAction;
import no.games.chess.ChessPlayer;
import no.games.chess.ChessState;
import no.games.chess.GameBoard;
import no.games.chess.GamePiece;
import no.games.chess.PieceMove;

/**
 * AbstractPlannerGame
 * The implementation of this class must create an initial state containing available action schemas and lifted action schemas 
 * from the chess planning process (See chapter 10 and 11 of the AIMA book).
 * The initial state is also called a situation (p. 388).
 * The lifted action schemas are generated based on the current PlannerState, the move number in the game, 
 * and information available from the KB.
 * The information from the KB is of the form of relations or fluents.
 * These fluents are of the form: 
 * 	PIECETYPE(BlackPawn4,PAWN)
 *	THREATENEDBY(BlackPawn4,c4)
 *	THREATENEDBY(BlackPawn4,e4)
 *	occupies(BlackQueen,d8)
 * The result of an action is also a situation.	
 * @author oluf
 *
 */
public abstract class AbstractPlannerGame implements PlannerGame<PlannerState,ChessPlannerAction, ChessPlayer> {

	protected ChessPlayer<GamePiece<?>, PieceMove<?,?>> whitePlayer;
	protected ChessPlayer<GamePiece<?>, PieceMove<?,?>> blackPlayer;
	
	public abstract PlannerState getInitialState();
	public abstract ChessPlayer<GamePiece, PieceMove>[] getPlayers();
	public abstract ChessPlayer<GamePiece, PieceMove> getPlayer(PlannerState state);
	public abstract List<ChessPlannerAction> getActions(PlannerState state);
	/**
	 * getResult
	 * This method returns a new state based on the current state and the
	 * action chosen by the search algorithm
	 * @param state The current state
	 * @param action The action chosen by the search algorithm
	 * @return
	 */
	public abstract PlannerState getResult(PlannerState plannerState,ChessPlannerAction action);	
	public abstract boolean isTerminal(PlannerState state);
	public abstract double getUtility(PlannerState state, ChessPlayer<GamePiece, PieceMove> player);
	public abstract double analyzePieceandPosition(ChessPlannerAction action); 
	
}
