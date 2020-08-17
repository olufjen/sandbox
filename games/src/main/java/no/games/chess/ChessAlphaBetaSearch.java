package no.games.chess;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

import aima.core.search.framework.Metrics;
import no.games.chess.ChessAction;
import no.games.chess.ChessState;
import no.games.chess.ChessPlayer;
/**
 * ChessAlphaBetaSearch is a subclass of IterativeDeepeningAlphaBetaSearch
 * and performs, through its makeDecision method, an iterative deepening Minimax search with alpha-beta pruning and
 * action ordering of Chess states and Chess actions. Maximal computation time is specified in seconds.
 * This object is created and called for every new call to the proposemove method of PlayGame
 * 
 * @author Oluf
 *
 * @param <S> The type representing chess states
 * @param <A> The type representing chess actions
 * @param <P> The type representing chess players
 */


public class ChessAlphaBetaSearch extends IterativeDeepeningAlphaBetaSearch<ChessState<GameBoard>,ChessAction<?, ?, ?, GamePiece<?>, ?>,ChessPlayer<GamePiece, PieceMove>> implements AdversarialSearch<ChessState<GameBoard>,ChessAction<?, ?, ?,  GamePiece<?>, ?>> {
	private ChessPlayer<GamePiece, PieceMove> player;
	private ChessState<GameBoard> xstate;
	private ChessAction<?, ?, ?, GamePiece, ?> action;
	private String outputFileName = "C:\\Users\\bruker\\Google Drive\\privat\\ontologies\\analysis\\logs.txt";
	private PrintWriter writer = null;
	private FileWriter fw = null;
	private int timecount = 0;
	
	 private Timer timer;
	public ChessAlphaBetaSearch(Game<ChessState<GameBoard>, ChessAction<?, ?, ?, GamePiece<?>, ?>, ChessPlayer<GamePiece, PieceMove>> game, double utilMin, double utilMax,
			int time) {
	
		super(game, utilMin, utilMax, time);
		this.timecount = time;
	      this.timer = new Timer(time);
			try {
				fw = new FileWriter(outputFileName, true);
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		      writer = new PrintWriter(new BufferedWriter(fw));		
	      
	      
/*	      try 
	      {
	         writer = new PrintWriter(outputFileName);
	      } catch (FileNotFoundException e) {
	         System.err.println("'" + outputFileName 
	            + "' is an invalid output file.");
	      }	*/
	}

	
    public static <ChessState,ChessAction,ChessPlayer> ChessAlphaBetaSearch createFor(
            ChessGame<ChessState,ChessAction,ChessPlayer> game, double utilMin, double utilMax, int time) {
    	
        return new ChessAlphaBetaSearch((Game<no.games.chess.ChessState<GameBoard>, no.games.chess.ChessAction<?, ?, ?, GamePiece<?>, ?>, no.games.chess.ChessPlayer<GamePiece, PieceMove>>) game, utilMin, utilMax, time);
    }
    
    /**
     * makeDecision
     * This method is controlling the search. It is based on iterative
     * deepening and tries to make to a good decision in limited time. 
     * The type S for state is casted to the correct interface
 
     * @param <ChessState> The state of the game
     * @return A ChessAction
     */
   
    public ChessAction  makeDecision(ChessState state) {
    	ChessState mystate = (ChessState)state;
    	
        metrics = new Metrics();
        StringBuffer logText = null;
        StringBuffer store = new StringBuffer("Ordered actions");
        ChessPlayer player =  (ChessPlayer) game.getPlayer(mystate);
        ChessGame chessGame = (ChessGame) game;
        List<ChessAction> actions = chessGame.getActions(mystate);
        List<ChessAction> results =  orderActions(mystate, actions, player, 0);
        timer.start();
        currDepthLimit = 1;
        do {
            incrementDepthLimit();
            if (logEnabled)
                logText = new StringBuffer("New while with depth " + currDepthLimit + ": \n");
            heuristicEvaluationUsed = false;
            ActionStore<ChessAction> newResults = new ActionStore<>();
            for (ChessAction action : results) { // Do a minimax search on ordered actions
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
        } while (!timer.timeOutOccurred() && heuristicEvaluationUsed);
        writer.println(logText);
        writer.println(store);
        writer.close();
        return results.get(0);
    }

    /**
     * orderActions
     * This method orders the actions such that actions containing preferred positions comes first.
     * This method should order the actions the same way as chessGame.analyzePieceandPosition(action);
     * The type A for action is casted to ChessAction interface
     * @author Oluf
     */
    
	public List<ChessAction> orderActions(ChessState state,  List<ChessAction> list, ChessPlayer player, int depth) {
    	ActionStore<ChessAction> newResults = new ActionStore<>();
        ChessGame chessGame = (ChessGame) game;
//        System.out.println("From orderAction\n");
    	for (ChessAction action:list) {
    		double rank = chessGame.analyzePieceandPosition(action);
    		newResults.add(action, rank);
    	}
//    	System.out.println(newResults.toString());
        return  newResults.actions;
    }
    public double maxValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.NEGATIVE_INFINITY;
            List<ChessAction> localActions = game.getActions(state);
            for (ChessAction action : orderActions(state, localActions, player, depth)) {
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
    public double minValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.POSITIVE_INFINITY;
            List<ChessAction> localActions = game.getActions(state);
            for (ChessAction action : orderActions(state,localActions, player, depth)) {
                value = Math.min(value, maxValue(game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
            }
            return value;
        }
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
    protected double eval(ChessState state, ChessPlayer player) {
//    	double primValue = super.eval(state, player);
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        } else {
//          heuristicEvaluationUsed = true; Set in comment olj 10.08.20
        ChessGame chessGame = (ChessGame) game;
        double returnValue = chessGame.analyzeState(state);
        if (returnValue == 0) {
        	 heuristicEvaluationUsed = true; // indicates opponent to move
        }
        return chessGame.analyzeState(state);
          
/*          List<ChessAction> chessActions = chessState.getActions();
          ChessAction action = chessState.getAction();
          ChessGame chessGame = (ChessGame) game;
          if (action != null)
        	  return chessGame.analyzePieceandPosition(action);
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
        private List<A> actions = new ArrayList<>();
        private List<Double> utilValues = new ArrayList<>();

        void add(A action, double utilValue) {
            int idx = 0;
            while (idx < actions.size() && utilValue <= utilValues.get(idx))
                idx++;
            actions.add(idx, action);
            utilValues.add(idx, utilValue);
        }

        int size() {
            return actions.size();
        }
        public String toString() {
        	 StringBuffer logText = new StringBuffer("Ordered actions: \n");
        	 for (A action:actions) {
        		 ChessAction localAction = (ChessAction) action;
        		 logText.append(localAction.toString()+"\n");
        	 }
        	return logText.toString();
        }
    }
    /**
     * Primitive operation which is called at the beginning of one depth limited
     * search step. This implementation increments the current depth limit by
     * one.
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
