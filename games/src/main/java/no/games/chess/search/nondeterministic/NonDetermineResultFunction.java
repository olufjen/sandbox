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


	public NonDetermineResultFunction(GameState state, GameAction action) {
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


	@Override
	public List<GameState> results(GameState state, GameAction action) {
		LinkedList<GameState> states = new LinkedList<GameState>();
		return states;
	}

}
