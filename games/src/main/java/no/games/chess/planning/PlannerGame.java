package no.games.chess.planning;

import aima.core.search.adversarial.Game;
/**
 * This interface represent a PlannerGame
 * It extends the Game interface
 * The Game interface has three type parameters:
 * <S> A type representing the states of the game
 * <A> A type representing the actions of the game
 * <P> A type representing the players of the game
 * In the PlannerGame interface these types are represented by the
 * PlannerState, 
 * ChessActionSchema, and
 * ChessPlayer interfaces
 * The <PlannerState, ChessActionSchema, ChessPlayer> type definition for the ChessGame interface is
 * necessary for defining the PlannerState, ChessActionSchema, ChessPlayer interfaces in the Game extend definition.
 * @author oluf
 *
 */
public interface PlannerGame<PlannerState, ChessActionSchema, ChessPlayer> extends Game<PlannerState, ChessActionSchema, ChessPlayer> {

	double analyzeState(no.games.chess.planning.PlannerState state);

	double analyzePieceandPosition(ChessPlannerAction action);

}
