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
 * This population is represented by a set of objects of this GameState class.
 * Each such GameState also contains the set of predefined actions:
 * CAPTURE Position (any own piece to this position. This may involve several MOVEs)
 * MOVE piece to Position (any piece to Position)
 * ATTACK opponent Piece This may involve several MOVEs
 * THREATEN opponent piece - This may involve several MOVEs
 * CAPTURE opponent Piece -This may involve several MOVEs
 * PROTECT own Piece - This may involve several MOVEs
 * PROTECT Position - This may involve several MOVEs
 * CASTLING- normal exchange of king and castle.
 * 
 * These predefined actions represent the set of GameActions available to this game state.
 * This set of game actions are created when the game state is created. (See constructor).
 * The GamePiece belonging to this game state is used when creating these game actions. 
 * 
 * The choice of action is determined by an evaluation function. This evaluation function is calculating various features of the
 * population of GameState objects. (page 172 AIMA book).
 * (See also section on harmony in chess positions.)
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
	protected String[] notations;// // These are keys for the moves in algebraic notation.
	protected String[] liftedKey = new String[5]; // A String array used as a parameter set for a lifted action of the form:  {startpos,piecename,endpos,piecetype}
	protected String algebraicNotation; // algebraic notation for possible move
	protected GameAction action;
	protected List<GameAction> actions;
	protected ChessPercept thePerceptor;
	enum Myaction{CAPTUREPOS,MOVE,ATTACK,CAPTUREPIECE,PROTECTPOS,PROTECTPIECE,CASTLING;} // The type of actions available to GameActions
	
	public GameState(GamePiece<?> gamePiece, ActionSchema actionSchema) {
		super();
		actions = new ArrayList<GameAction>();
		this.gamePiece = gamePiece;
		this.actionSchema = actionSchema;
		for (Myaction allaction:Myaction.values()) {
			action = new GameAction(gamePiece,this,allaction);
			actions.add(action);
		}
	}
	
	/**
	 * This is the preferred contructor
	 * @param pieceSentences - can be null
	 * @param gamePiece  - The game piece involved in this state
	 * @param actionSchema - can be null
	 * @param thePerceptor
	 */
	public GameState(List<Sentence> pieceSentences, GamePiece<?> gamePiece, ActionSchema actionSchema,
						ChessPercept thePerceptor) {
		super();
		actions = new ArrayList<GameAction>();
		this.pieceSentences = pieceSentences;
		this.gamePiece = gamePiece;
		this.actionSchema = actionSchema;
		this.thePerceptor = thePerceptor;
		for (Myaction allaction:Myaction.values()) {
			action = new GameAction(gamePiece,this,allaction);
			actions.add(action);
		}

	}

	public ChessPercept getThePerceptor() {
		return thePerceptor;
	}

	public void setThePerceptor(ChessPercept thePerceptor) {
		this.thePerceptor = thePerceptor;
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
	public ActionSchema getActionSchema() {
		return actionSchema;
	}
	public void setActionSchema(ActionSchema actionSchema) {
		this.actionSchema = actionSchema;
	}
	public String getAlgebraicNotation() {
		return algebraicNotation;
	}
	public void setAlgebraicNotation(String algebraicNotation) {
		this.algebraicNotation = algebraicNotation;
	}
	public GameAction getAction() {
		return action;
	}
	public void setAction(GameAction action) {
		this.action = action;
	}
	
	public String[] getNotations() {
		return notations;
	}

	public void setNotations(String[] notations) {
		this.notations = notations;
	}

	public String[] getLiftedKey() {
		return liftedKey;
	}

	public void setLiftedKey(String[] liftedKey) {
		this.liftedKey = liftedKey;
	}

	/**
	 * getActions
	 * This method returns all GameActions applicable in this GameState
	 * It is called by the Nondeterministic action function through the .apply function
	 * @return
	 */
	public List<GameAction> getActions(){
/*
 * Which actions are applicable in this state?		
 */
		return actions;
	};
	/**
	 * testEnd
	 * This is the method used by the ChessGoalTest functional interface 
	 * the testGoal method
	 * A Game Action may return a set of GameStates, each of these states have a value.
	 * 
	 * @param action Based on this action, is this the goal state?
	 * @return true if this is the goal state. This results in an empty plan in the search tree
	 */
	public boolean testEnd(GameAction action) {
	
		return true;
	}
	public String toString() {
		String theState = actionSchema.toString() + "\n" + gamePiece.toString() + "\n" + algebraicNotation ;
		return theState;
		
	}
}
