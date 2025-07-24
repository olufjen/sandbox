package no.games.chess.search.nondeterministic;

import java.util.List;

import aima.core.search.framework.problem.ActionsFunction;

/**
 * NonDetermineChessActionFunction
 * This class is an implementation of the aima.core.search.framework.problem.ActionsFunction.
 * As described in the interface:
 * Given a particular state s, ACTIONS(s) returns the set of actions that can be
 * executed in s. We say that each of these actions is <b>applicable</b> in s.
 * 
 * @param <GameState> The type used to represent states
 * @param <A> The type of the actions to be used to navigate through the state space
 * @author oluf
 *
 * @param <GameState> 
 * @param <GameAction>
 */
public class NonDetermineChessActionFunction implements ActionsFunction<GameState, GameAction> {

	@Override
	public List<GameAction> apply(GameState state) {
		
		 GameState localState = state;
		return (List<GameAction>) localState.getActions();
	}

}
