package no.games.chess.search.nondeterministic;

import java.util.LinkedList;
import java.util.List;

/**
 * ChessPlan
 * This class is the chess version of the Plan class as described:
 * Represents a solution plan for an AND-OR search; according to page 135
 * AIMA3e, the plan must be "a subtree that (1) has a goal node at every leaf,
 * (2) specifies one action at each of its OR nodes, and (3) includes every
 * outcome branch at each of its AND nodes." As demonstrated on page 136, this
 * plan can be implemented as a sequence of two steps where the first
 * is an action (corresponding to one OR node) and the second is a list
 * of if-state-then-plan statements (corresponding to an AND node). Here, we use a
 * list of action steps instead of just one action. This allows to simplify conditioned
 * steps with just one if-statement and supports a clean representation of empty plans.
 * 
 * @author Ruediger Lunde
 * @author Andrew Brown
 * 
 * The OR node: The player chooses a move.
 * All states but one remain the same.
 * The outcome of the move is determined by the opponent's move. This is the AND node
 * This opponent move produces a new set of states. All states but one remain the same.
 * 
 */
public class ChessPlan {

	private static final long serialVersionUID = 1L;

	private List<GameAction> actionSteps = new LinkedList<>();
	private List<IfStatement> ifStatements = new LinkedList<>();


	public boolean isEmpty() {
		return actionSteps.isEmpty() && ifStatements.isEmpty();
	}

	/** Returns the number of steps of this ChessPlan. */
	public int size() {
		return ifStatements.isEmpty() ? actionSteps.size() : actionSteps.size() + 1;
	}

	/**
	 * Checks whether the specified step (between 0 and size()-1) is an action step or
	 * a conditional step.
	 */
	public boolean isActionStep(int step) {
		return step < actionSteps.size();
	}

	/** Returns the corresponding action for the given action step. */
	public GameAction getAction(int step) {
		return actionSteps.get(step);
	}

	/**
	 * Evaluates the specified conditional step and returns a plan of the first if-statement which matches
	 * the given state.
	 * @param step A conditional step (last step in the plan).
	 * @param state The state to be matched.
	 * @return A plan or null if no match was found.
	 */
	public ChessPlan getChessPlan(int step, GameState state) {
		if (isActionStep(step) || step != actionSteps.size())
			throw new IllegalArgumentException("Specified step is not conditional.");
		for (IfStatement ifStatement : ifStatements) {
			if (ifStatement.testCondition(state))
				return ifStatement.getChessPlan();
		}
		return null; // no matching plan found for the given state.
	}

	/**
	 * Prepend an action to the plan and return itself.
	 * 
	 * @param action
	 *            the action to be prepended to this plan.
	 * @return this plan with action prepended to it.
	 */
	public ChessPlan prepend(GameAction action) {
		actionSteps.add(0, action);
		return this;
	}

	/** Adds an if-state-then-plan statement at the end of the plan. */
	public void addIfStatement(GameState state, ChessPlan plan) {
		ifStatements.add(new IfStatement(state, plan));
	}

	/**
	 * Returns a string representation of this plan.
	 * 
	 * @return A string representation of this plan.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		int count = 0;
		for (GameAction step : actionSteps) {
			if (count++ > 0)
				s.append(", ");
			s.append(step);
		}
		for (IfStatement ifStatement : ifStatements) {
			if (count++ > 0)
				s.append(", ");
			s.append(ifStatement);
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Represents an if-state-then-plan statement for use with AND-OR search;
	 * explanation given on page 135 of AIMA3e.
	 *
	 * @author Ruediger Lunde
	 */
	private static class IfStatement {

		GameState state;
		ChessPlan plan;

		IfStatement(GameState state, ChessPlan plan) {
			this.state = state;
			this.plan = plan;
		}

		boolean testCondition(GameState state) {
			return this.state.equals(state);
		}

		ChessPlan getChessPlan() {
			return plan;
		}

		/**
		 * Return string representation of this if-state-then-plan statement.
		 *
		 * @return A string representation of this if-state-then-plan statement.
		 */
		@Override
		public String toString() {
			return "if " + state + " then " + plan;
		}
	}
}
