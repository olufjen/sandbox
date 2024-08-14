package no.games.chess.planning;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import aima.core.logic.planning.ActionSchema;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.framework.Metrics;
import no.games.chess.ChessAction;
import no.games.chess.ChessAlphaBetaSearch;
import no.games.chess.ChessGame;
import no.games.chess.ChessPlayer;
import no.games.chess.ChessState;
import no.games.chess.GameBoard;
import no.games.chess.GamePiece;
import no.games.chess.PieceMove;


/**
 * ChessPlannerSearch is a subclass of IterativeDeepeningAlphaBetaSearch
 * and performs, through its makeDecision method, an iterative deepening Minimax search with alpha-beta pruning and
 * action ordering of Planner states and Action schemas. Maximal computation time is specified in seconds.
 * This object is created and called from the chess Problem solver
 * 
 * @author Oluf
 *
 * @param <S> The type representing chess states
 * @param <A> The type representing chess actions
 * @param <P> The type representing chess players
 */

public class ChessPlannerSearch extends IterativeDeepeningAlphaBetaSearch<PlannerState, ChessPlannerAction, ChessPlayer> {

	private ChessPlayer<GamePiece, PieceMove> player;
	private PlannerState plannerState;
	private ChessPlannerAction<ActionSchema> plannerAction;
	private String outputFileName = "C:\\Users\\bruker\\Google Drive\\privat\\ontologies\\analysis\\plannerlogs.txt";
	private PrintWriter writer = null;
	private FileWriter fw = null;
	private int timecount = 0;
	private Timer timer;
	public ChessPlannerSearch(PlannerGame<PlannerState,ChessPlannerAction,ChessPlayer> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
		
	}
    public static <PlannerState,ChessPlannerAction,ChessPlayer> ChessPlannerSearch createFor(
            PlannerGame<PlannerState,ChessPlannerAction,ChessPlayer> game, double utilMin, double utilMax, int time) {
    	
        return new ChessPlannerSearch((PlannerGame<no.games.chess.planning.PlannerState, no.games.chess.planning.ChessPlannerAction, no.games.chess.ChessPlayer>) game, utilMin, utilMax, time);
    }
	   /**
     * makeDecision
     * This method is controlling the search. It is based on iterative
     * deepening and tries to make to a good decision in limited time. 
     * The type S for state is casted to the correct interface
 
     * @param <PlannerState> The state of the game
     * @return A ChessPlannerAction
     */
    public ChessPlannerAction  makeDecision(PlannerState state) {
    	PlannerState mystate = (PlannerState)state;
    	
        metrics = new Metrics();
        StringBuffer logText = new StringBuffer("Log\n");
        StringBuffer store = new StringBuffer("Ordered actions");
        ChessPlayer player =  (ChessPlayer) game.getPlayer(mystate);
        PlannerGame plannerGame = (PlannerGame) game;
        List<ChessPlannerAction> actions = plannerGame.getActions(mystate);
        List<ChessPlannerAction> results =  orderActions(mystate, actions, player, 0); // Analysis of ChessPLannerAction and the Action Schemas
        timer.start();
        currDepthLimit = 1;
        do {
            incrementDepthLimit();
            if (logEnabled)
                logText = new StringBuffer("New while with depth " + currDepthLimit + ": \n");
            heuristicEvaluationUsed = false;
            ActionStore<ChessPlannerAction> newResults = new ActionStore<>();
            for (ChessPlannerAction action : results) { // Do a minimax search on ordered actions
                double value = minValue(game.getResult(state, action), player, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, 1);
/*                if (timer.timeOutOccurred())
                    break; // exit from action loop
*/                newResults.add(action, value);
                if (logEnabled)
                    logText.append(action).append("\n -> ").append(value).append(" Metrics ").append(metrics).append("\n").append("From action store:\n");
            }
            if (logEnabled) {
            	 if (newResults.size() > 0) {
            		 logText.append(newResults.actions.get(0)).append(" Utilvalue ").append(newResults.utilValues.get(0)).append("\n");
            	 }
            }
            logText.append("new depth " + currDepthLimit + ": \n");

//           System.out.println(newResults.toString());
//            writer.println(newResults.toString());
            if (newResults.size() > 0) {
                results = newResults.actions;
                logText.append("Checking ordered actions \n");
/*
 * Use this structure to ensure that the makeDecision method always return actions belonging to the active player, not the opponent  
 * The minimax search has run through all actions.  See above!            
 */
//                if (!timer.timeOutOccurred()) {
                   if (hasSafeWinner(newResults.utilValues.get(0))) {
                	   logText.append(" Has a safe winner "+newResults.actions.get(0).toString()+"\n");
                       break; // exit from iterative deepening loop
                   }else if (newResults.size() > 1
                           && isSignificantlyBetter(newResults.utilValues.get(0), newResults.utilValues.get(1))) {
                	   logText.append(" Is better " +newResults.actions.get(0).toString()+"\n");
                       break; // exit from iterative deepening loop
                   }
//                }
            }
            store.append(newResults.toString());
 //           writer.println(logText);
        } while (!timer.timeOutOccurred() && heuristicEvaluationUsed ); // Added test of depthlimit removed 
        writer.println(logText);
        writer.println(store);
        writer.close();
        return results.get(0); // Returns the best ChessPLannerAction
    }

    public double maxValue(PlannerState state, ChessPlayer player, double alpha, double beta, int depth) {
    	     updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
        	writer.println("maxValue Calling eval "+"depth/depthlimit "+depth+"/"+currDepthLimit+"\n");
            return eval(state, player);
        } else {
            double value = Double.NEGATIVE_INFINITY;
            List<ChessPlannerAction> localActions = game.getActions(state); //Produces a new set of actions for this state
            if(player.getPlayerName() == player.getBlackPlayer()) {
            	writer.println("Maxvalue Opponent to play "+player.getPlayerName()+"\n");
            }
            for (ChessPlannerAction action : orderActions(state, localActions, player, depth)) {
                value = Math.max(value, minValue(game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
            }
            return value;
        }
    }

    // returns an utility value
    public double minValue(PlannerState state, ChessPlayer player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
        	writer.println("minValue Calling eval "+"depth/depthlimit "+depth+"/"+currDepthLimit+"\n");
            return eval(state, player);
        } else {
            double value = Double.POSITIVE_INFINITY;
            List<ChessPlannerAction> localActions = game.getActions(state);//Produces a new set of actions for this state
            if(player.getPlayerName() == player.getBlackPlayer()) {
            	writer.println("Minvalue Opponent to play "+player.getPlayerName()+"\n");
            }
            for (ChessPlannerAction action : orderActions(state,localActions, player, depth)) {
                value = Math.min(value, maxValue(game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
            }
            return value;
        }
    }
    
    /**
     * orderActions
     * This method orders the actions such that actions containing preferred positions comes first.
     * This method should order the actions the same way as chessGame.analyzePieceandPosition(action);
     * The type A for action is casted to ChessAction interface
     * @author Oluf
     */
    public List<ChessPlannerAction> orderActions(PlannerState state, List<ChessPlannerAction<ActionSchema>> actions, ChessPlayer player, int depth) {
    	ActionStore<ChessPlannerAction> newResults = new ActionStore<>();
        PlannerGame plannerGame = (PlannerGame)game;
    	for (ChessPlannerAction action:actions) {
  
    		double rank = plannerGame.analyzePieceandPosition(action);
    		newResults.add(action, rank);
    	}
  
        return  newResults.actions;
    }
    private void updateMetrics(int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        metrics.set(METRICS_MAX_DEPTH, Math.max(metrics.getInt(METRICS_MAX_DEPTH), depth));
    }

    /**
     * When overriding, first call the super implementation???!
     * This overided version of eval must produce:
     * - When the state is not terminal:
     * A utility value that is max for a central position on the board + the value (rank) 
     * of the piece
     * @since January 2020
     * This method attempts to analyze the various features of the current state
     * This method can only be used for active player, not the opponent player
     * 
    
     */
    protected double eval(PlannerState state, ChessPlayer player) {
//    	double primValue = super.eval(state, player);
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        } else {
//          heuristicEvaluationUsed = true; Set in comment olj 10.08.20
        PlannerGame plannerGame = (PlannerGame) game;
        double returnValue = plannerGame.analyzeState(state);
 /*       if (returnValue == 0) {
        	 heuristicEvaluationUsed = true; // indicates opponent to move
        }

        return PlannerGame.analyzeState(state);*/
        heuristicEvaluationUsed = false;
        return returnValue;
          
/*          List<ChessPlannerAction> ChessPlannerActions = PlannerState.getActions();
          ChessPlannerAction action = PlannerState.getAction();
          PlannerGame chessGame = (PlannerGame) game;
          if (action != null)
        	  return PlannerGame.analyzePieceandPosition(action);
          else
        	  return 0;
*/
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    // nested helper classes

    private static class Timer {
        private long duration;
        private long startTime;

        Timer(int maxSeconds) {
            this.duration = 1000 * maxSeconds;
        }

        void start() {
            startTime = System.currentTimeMillis();
        }

        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }
    }

    /**
     * Orders actions by utility.
     */
    private static class ActionStore<A> {
        private List<A> actions = new ArrayList<>(); // OBS !!
        private List<Double> utilValues = new ArrayList<>();

        void add(no.games.chess.planning.ChessPlannerAction action, double utilValue) { // OBS !!
            int idx = 0;
            while (idx < actions.size() && utilValue <= utilValues.get(idx))
                idx++;
            actions.add(idx, (A) action);
            utilValues.add(idx, utilValue);
        }

        int size() {
            return actions.size();
        }
        public String toString() {
        	 StringBuffer logText = new StringBuffer("Ordered actions: \n");
        	 for (A action:actions) {
        		 ChessPlannerAction localAction = (ChessPlannerAction) action;
        		 logText.append(localAction.toString()+"\n");
        	 }
        	return logText.toString();
        }
    }
    /**
     * Primitive operation which is called at the beginning of one depth limited
     * search step. This implementation increments the current depth limit by
     * one.
     * @since 2.10.20 Max depth is 1 OJN
     */
    protected void incrementDepthLimit() {
        currDepthLimit++;

    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a clear best action exists. This implementation returns
     * false if both utilities are 0.
     * Otherwise it returns true. This makes sure that the returned action belongs to the gameplayer and not the opponent
     */
    protected boolean isSignificantlyBetter(double newUtility, double utility) {
    	if (newUtility == utility && newUtility == 0)
    		return false;
        return newUtility >= utility;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result is the highest or lowest utility value possible.
     * @since 05.05.20
     * The resultutlity is divided by 100 since utilMax  is 1 and utilmin is 0.
     */
    protected boolean hasSafeWinner(double resultUtility) {
    	if (resultUtility == 0)
    		return false;
    	double localUtil = resultUtility/100;
    	return localUtil <= utilMin || localUtil >= utilMax;
//        return resultUtility <= utilMin || resultUtility >= utilMax;
    }

}
