package no.games.chess.search.nondeterministic;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.fol.parsing.ast.Sentence;
import aima.core.logic.planning.ActionSchema;
import no.games.chess.GamePiece;

/**
 * GameState 
 * This class contains an ActionSchema. This Action Schema is linked to its AgamePiece.
 * The action schema is available for the game piece.
 * It also contains all FOL sentences belonging to this gamepiece.
 * The state of the game contains a set of chess actions and a generated set of action schemas.
 * Each action schema can be viewed as part of an individual state, and all the action schemas then represent a population of states.
 * This population is represented by a set of objects of this class.
 * Each such GameState also contains a set of predefined actions:
 * CAPTURE Position (any own piece to this position. This may involve several MOVEs)
 * MOVE piece to Position (any piece to Position)
 * ATTACK opponent Piece This may involve several MOVEs
 * THREATEN opponent piece - This may involve several MOVEs
 * CAPTURE opponent Piece -This may involve several MOVEs
 * PROTECT own Piece - This may involve several MOVEs
 * PROTECT Position - This may involve several MOVEs
 * 
 * The choice of action is determined by an evaluation function. This evaluation function is calculating various features of the
 * population of GameState objects. (See section on harmony in chess positions.)
 * (page 172 AIMA book).
 * Each individual state is rated by the fitnes function.   (see p. 127 of the AIMA book)
 * This class then is used as a wrapper for the action schema representing an individual of the population of states.
 * The fitnes function returns a higher value for a better state.
 * Objects of this class is also generated as part of a search mechanism.
 * Then the generated action schema is a lifted action schema or a proposed chess move represented in algebraic notation or
 * as a string array of the form:
 *  {startpos,piecename,endpos,piecetype} 

 * @author ojn
 *
 */
public class GameState {

	protected List<Sentence> pieceSentences = new ArrayList<Sentence>();
	protected GamePiece<?> gamePiece;
	protected ActionSchema actionSchema;
	protected String[] notations;// of the form:  {startpos,piecename,endpos,piecetype}
	protected String algebraicNotation; // algebraic notation for possible move
	
	public GameState(GamePiece<?> gamePiece, ActionSchema actionSchema) {
		super();
		this.gamePiece = gamePiece;
		this.actionSchema = actionSchema;
	}
	public List<GameAction> getActions(){
		return null;
	};
}
