package no.games.chess.search.nondeterministic;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.fol.parsing.ast.Sentence;
import no.games.chess.GamePiece;

/**
 * GameAction
 * Objects of this class perform a chess action.
 * The following actions are available:
 * CAPTURE Position (any own piece to this position. This may involve several MOVEs)
 * MOVE piece to Position (any piece to Position)
 * ATTACK opponent Piece -  This may involve several MOVEs
 * THREATEN opponent piece - This may involve several MOVEs
 * CAPTURE opponent Piece - This may involve several MOVEs
 * PROTECT own Piece - This may involve several MOVEs
 * PROTECT Position - This may involve several MOVEs
 * Each such action is performed by a chess piece.
 * @author oluf
 *
 */
public class GameAction {
	protected List<Sentence> pieceSentences = new ArrayList<Sentence>();
	protected GamePiece<?> gamePiece;
	protected GameState gameState; // The state to which this action belongs to
	protected String[] notations;// of the form:  {startpos,piecename,endpos,piecetype}
	
	public GameAction(List<Sentence> pieceSentences, GamePiece<?> gamePiece) {
		super();
		this.pieceSentences = pieceSentences;
		this.gamePiece = gamePiece;
	}
	
	public GameAction(GamePiece<?> gamePiece, GameState gameState) {
		super();
		this.gamePiece = gamePiece;
		this.gameState = gameState;
	}

	public GameAction(GamePiece<?> gamePiece) {
		super();
		this.gamePiece = gamePiece;
	}

	public List<Sentence> getPieceSentences() {
		return pieceSentences;
	}
	public void setPieceSentences(List<Sentence> pieceSentences) {
		this.pieceSentences = pieceSentences;
	}
	public GamePiece<?> getGamePiece() {
		return gamePiece;
	}
	public void setGamePiece(GamePiece<?> gamePiece) {
		this.gamePiece = gamePiece;
	}
	
	public String[] getNotations() {
		return notations;
	}

	public void setNotations(String[] notations) {
		this.notations = notations;
	}

	/**
	 * getStates
	 * This method returns a set of states as result of this action
	 * @return
	 */
	public List<GameState> getStates(){
		return null;
	}
}
