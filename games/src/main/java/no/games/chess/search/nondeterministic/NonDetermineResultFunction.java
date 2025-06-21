package no.games.chess.search.nondeterministic;

import java.util.LinkedList;
import java.util.List;

import aima.core.search.nondeterministic.ResultsFunction;

/**
 * NonDetermineResultFunction
 * This class is an implementation of the aima.core.search.nondeterministic.ResultsFunction
 * as described in chapter 4 of the AIMA book.
 * @author oluf
 *
 */
public class NonDetermineResultFunction<GameState,GameAction> implements ResultsFunction<GameState,GameAction>{
	private GameState state;
	private GameAction action;
	private List<GameState> gameStates; // The population of gamestates
	




	/**
	 * NonDetermineResultFunction
	 * THe constructor of the NonDetermineResultFunction
	 * @param state
	 * @param action
	 * @param gameStates The population of game states
	 */
	public NonDetermineResultFunction(GameState state, GameAction action, List<GameState> gameStates) {
		super();
		this.state = state;
		this.action = action;
		this.gameStates = gameStates;
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


	@Override
	public List<GameState> results(GameState state, GameAction action) {
		
		return gameStates;
	}

}
