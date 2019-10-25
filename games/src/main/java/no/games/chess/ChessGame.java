package no.games.chess;

import java.util.List;

import aima.core.search.adversarial.Game;

/**
 * This interface represent a ChessGame
 * It extends the Game interface
 * The Game intrerface has three type parameters:
 * <S> A type representing the states of the game
 * <A> A type representing the actions of the game
 * <P> A type representing the playeers of the game
 * In the ChessGame interface these types are represented by the
 * ChessState, 
 * ChessAction, and
 * ChessPlayer interfaces
 * The <ChessState, ChessAction, ChessPlayer> type definition for the ChessGame interface is
 * necessary for defining the ChessState, ChessAction, ChessPlayer interfaces in the Game extend definition.
 * @author oluf
 *
 */
public interface ChessGame<ChessState, ChessAction, ChessPlayer> extends Game<ChessState, ChessAction, ChessPlayer> {

	public List<ChessAction> getActions(ChessState state);
	public abstract ChessState getResult(ChessState chessState, ChessAction action);
	public double analyzePieceandPosition(ChessAction action);
  
}
