package no.games.chess.planning;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.planning.ActionSchema;

/**
 * This is the ChessGraph specialization of Graph 
 * Oluf Jensen 8.3.23
 * Artificial Intelligence A Modern Approach (3rd Edition): page 379.<br>
 * <p>
 * A planning graph is a directed graph organized into levels: first a level S 0 for the initial
 * state, consisting of nodes representing each fluent that holds in S 0 ; then a level A 0 consisting
 * of nodes for each ground action that might be applicable in S 0 ; then alternating levels S i
 * followed by A i ; until we reach a termination condition.
 *
 * @author samagra
 */
public class ChessGraph {
    ArrayList<ChessLevel> levels;// ChessLevels
    ChessProblem problem;// The planning problem
    List<ActionSchema> propositionalisedActions;

    public ChessGraph(ChessProblem problem, ChessLevel initialLevel) {
        this.problem = problem;
        levels = new ArrayList<>();
        levels.add(initialLevel);
//        propositionalisedActions = problem.getPropositionalisedActions();
        propositionalisedActions = problem.getGroundActions();
    }

    public int numLevels() {
        return levels.size();
    }

    public ArrayList<ChessLevel> getLevels() {
        return levels;
    }

    public ChessProblem getProblem() {
        return problem;
    }

    public List<ActionSchema> getPropositionalisedActions() {
        return propositionalisedActions;
    }

    public ChessGraph addLevel() {
        ChessLevel lastLevel = levels.get(levels.size() - 1);
        ChessLevel level = new ChessLevel(lastLevel, this.problem);
        this.levels.add(level);
        return this;
    }
}
