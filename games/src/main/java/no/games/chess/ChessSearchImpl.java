package no.games.chess;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.Game;

import aima.core.search.framework.Metrics;




/**
 * @since Jan 2021 NOT USED
 * @author oluf
 *
 */
public class ChessSearchImpl implements ChessSearch {
	   public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
	    public final static String METRICS_MAX_DEPTH = "maxDepth";

	    protected ChessGame<ChessState<?>, ChessAction<?, ?, ?, ?, ?>, ChessPlayer<?,?>> game;
	    protected double utilMax;
	    protected double utilMin;
	    protected int currDepthLimit;
	    protected boolean heuristicEvaluationUsed; // indicates that non-terminal
	    // nodes
	    // have been evaluated.
	    private Timer timer;
	    protected boolean logEnabled;

	    protected Metrics metrics = new Metrics();
	    
	public ChessSearchImpl(ChessGame<ChessState<?>, ChessAction<?, ?, ?, ?, ?>, ChessPlayer<?,?>> game, double utilMax, double utilMin,int time) {
			super();
			this.game = game;
			this.utilMax = utilMax;
			this.utilMin = utilMin;
	      this.timer = new Timer(time);
		}

	  public static ChessSearchImpl createFor(
	            ChessGame<ChessState<?>, ChessAction<?, ?, ?, ?, ?>, ChessPlayer<?,?>> game, double utilMin, double utilMax, int time) {
	    	
	        return new ChessSearchImpl(game, utilMin, utilMax, time);
	  }
	@Override
	public ChessAction makeDecision(ChessState state) {
	      metrics = new Metrics();
	        StringBuffer logText = null;
	        ChessPlayer player =  (ChessPlayer) game.getPlayer(state);
	        List<ChessAction> results =  orderActions(state, game.getActions(state), player, 0);
	        timer.start();
	        currDepthLimit = 1;
	        do {
	            incrementDepthLimit();
	            if (logEnabled)
	                logText = new StringBuffer("depth " + currDepthLimit + ": \n");
	            heuristicEvaluationUsed = false;
	            ActionStore<ChessAction> newResults = new ActionStore<>();
	            for (ChessAction action : results) {
	                double value = minValue((ChessState) game.getResult(state, action), player, Double.NEGATIVE_INFINITY,
	                        Double.POSITIVE_INFINITY, 1);
	/*                if (timer.timeOutOccurred())
	                    break; // exit from action loop
	*/                newResults.add(action, value);
	                if (logEnabled)
	                    logText.append(action).append("-> ").append(value).append(" Metrics ").append(metrics).append("\n").append("From action store:\n");
	            }
	            if (logEnabled) {
	            	 if (newResults.size() > 0) {
	            		 logText.append(newResults.actions.get(0)).append(" Utilvalue ").append(newResults.utilValues.get(0)).append("\n");
	            	 }
	            }
	            System.out.println(logText);
	            if (newResults.size() > 0) {
	                results = newResults.actions;
//	                if (!timer.timeOutOccurred()) {
	                   if (hasSafeWinner(newResults.utilValues.get(0)))
	                       break; // exit from iterative deepening loop
	                   else if (newResults.size() > 1
	                           && isSignificantlyBetter(newResults.utilValues.get(0), newResults.utilValues.get(1)))
	                       break; // exit from iterative deepening loop
//	                }
	            }
	        } while (!timer.timeOutOccurred() && heuristicEvaluationUsed);
	        return results.get(0);

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
     * always false.
     */
    protected boolean isSignificantlyBetter(double newUtility, double utility) {
        return false;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result) is the highest or lowest utility value possible.
     */
    protected boolean hasSafeWinner(double resultUtility) {
        return resultUtility <= utilMin || resultUtility >= utilMax;
    }

	@Override
	public Metrics getMetrics() {
		
		return metrics;
	}

	@Override
	public void setLogEnabled(boolean b) {
		logEnabled = b;

	}
    /**
     * orderActions
     * This method orders the actions such that actions containing preferred positions comes first.
     * This method should order the actions the same way as chessGame.analyzePieceandPosition(action);
     * The type A for action is casted to ChessAction interface
     * @author Oluf
     */
    public List<ChessAction> orderActions(ChessState state, List<ChessAction<?, ?, ?, ?, ?>> actions, ChessPlayer player, int depth) {
    	ActionStore<ChessAction> newResults = new ActionStore<>();
        
    	for (ChessAction action:actions) {
  
    		double rank = game.analyzePieceandPosition(action);
    		newResults.add(action, rank);
    	}
  
        return  newResults.actions;
    }

    private void updateMetrics(int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        metrics.set(METRICS_MAX_DEPTH, Math.max(metrics.getInt(METRICS_MAX_DEPTH), depth));
    }
	@Override
	public double maxValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.NEGATIVE_INFINITY;
            List<ChessAction> actions = orderActions(state, (List<ChessAction<?, ?, ?, ?, ?>>) game.getActions(state), player, depth);
            for (ChessAction action :actions) {
                value = Math.max(value, minValue((ChessState) game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
            }
            return value;
        }

	}

	@Override
	public double minValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth) {
        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred()) {
            return eval(state, player);
        } else {
            double value = Double.POSITIVE_INFINITY;
            for (ChessAction action : orderActions(state,(List<ChessAction<?, ?, ?, ?, ?>>)  game.getActions(state), player, depth)) {
                value = Math.min(value, maxValue((ChessState) game.getResult(state, action), //
                        player, alpha, beta, depth + 1));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
            }
            return value;
        }

	}

	@Override
	public double eval(ChessState state, ChessPlayer player) {
	       if (game.isTerminal(state)) {
	            return game.getUtility(state, player);
	        } else {
	          heuristicEvaluationUsed = true;
	      
//	          List<ChessAction> chessActions = state.getActions();
	          ChessAction action = state.getAction();
	        
	          if (action != null)
	        	  return game.analyzePieceandPosition(action);
	          else
	        	  return 0;

	        }

	}
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
    private static class ActionStore<ChessAction> {
        private List<ChessAction> actions = new ArrayList<>();
        private List<Double> utilValues = new ArrayList<>();

        void add(ChessAction action, double utilValue) {
            int idx = 0;
            while (idx < actions.size() && utilValue <= utilValues.get(idx))
                idx++;
            actions.add(idx, action);
            utilValues.add(idx, utilValue);
        }

        int size() {
            return actions.size();
        }
    }


}
