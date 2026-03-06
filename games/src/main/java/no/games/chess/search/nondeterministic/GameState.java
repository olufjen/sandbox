package no.games.chess.search.nondeterministic;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.fol.parsing.ast.Sentence;
import aima.core.logic.planning.ActionSchema;
import no.games.chess.GamePiece;

/**
 * GameState 
 *  Revised 26.01.26:
 * A GameState contains all FOL sentences representing the state of the game.
 * The state of the game contains a set of chess actions and a generated set of action schemas.
 * A GameState also contains the set of predefined actions:
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
	protected ActionSchema actionSchema;
	protected String[] notations;// // These are keys for the moves in algebraic notation.
	protected String[] liftedKey = new String[5]; // A String array used as a parameter set for a lifted action of the form:  {startpos,piecename,endpos,piecetype}
	protected String algebraicNotation; // algebraic notation for possible move
	protected GameAction action;
	protected List<GameAction> actions;
	protected ChessPercept thePerceptor;
	protected List<ActionSchema> actionSchemas;
	
	public GameState() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pieceSentences - can be null
	 * @param thePerceptor
	 */
	public GameState(List<Sentence> pieceSentences, 
						ChessPercept thePerceptor) {
		super();
		actions = new ArrayList<GameAction>();
		this.pieceSentences = pieceSentences;
		this.thePerceptor = thePerceptor;
		actionSchemas = new ArrayList<ActionSchema>();

	}
	

	/**
	 * THe preferred constructor
	 * @param thePerceptor
	 * @param actionSchemas
	 */
	public GameState(ChessPercept thePerceptor, List<ActionSchema> actionSchemas) {
		super();
		this.thePerceptor = thePerceptor;
		this.actionSchemas = actionSchemas;
	}

	public ActionSchema getActionSchema() {
		return actionSchema;
	}

	public void setActionSchema(ActionSchema actionSchema) {
		this.actionSchema = actionSchema;
	}

	public List<ActionSchema> getActionSchemas() {
		return actionSchemas;
	}

	public void setActionSchemas(List<ActionSchema> actionSchemas) {
		this.actionSchemas = actionSchemas;
	}

	public void setActions(List<GameAction> actions) {
		this.actions = actions;
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
		String theState = "No State"; //To be changed
		return theState;
		
	}
}
