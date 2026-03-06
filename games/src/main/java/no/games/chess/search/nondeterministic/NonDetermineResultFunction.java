package no.games.chess.search.nondeterministic;

import java.util.LinkedList;
import java.util.List;

import aima.core.search.nondeterministic.ResultsFunction;

/**
 * NonDetermineResultFunction
 * This class is an implementation of the aima.core.search.nondeterministic.ResultsFunction
 * as described in chapter 4 (page 134) of the AIMA book.
 * implements ResultsFunction<GameState,GameAction> This implementation is removed
 * @author oluf
 *
 */
public class NonDetermineResultFunction {
	private GameState state;
	private GameAction action;


	/**
	 * NonDetermineResultFunction
	 * The constructor of the NonDetermineResultFunction
	 * @param state
	*/

	public NonDetermineResultFunction(GameState state) {
		super();
		this.state = state;
	}


	/**
	 * NonDetermineResultFunction
	 * The constructor of the NonDetermineResultFunction
	 * @param state
	 * @param action
	 */
	public NonDetermineResultFunction(GameState state, GameAction action ) {
		super();
		this.state = state;
		this.action = action;
		
	}


	public GameState getState() {
		return state;
	}


	public void setState(GameState state) {
		this.state = state;
	}


	public GameAction getAction() {
		return action;
	}


	public void setAction(GameAction action) {
		this.action = action;
	}
	/**
	 * Return the description of what each action does.
	 * The resultsFn is an ordinary interface with one method: results(state,action)
	 * It is called from the orSearch method when the problem testGoal function for a chosen state returns false.
	 * The call is carried out for every state in the population of states, and every action belonging to this state 
	 * @return the description of what each action does - a list of possible outcome states.
	 */

	public List<GameState> results(GameState state, GameAction action) {
/*
 * What gamestates are the result of this action?
 * The gameaction needs a method to return a set of states as a result of this action		
 */
		return action.performAcion();
	}



}
