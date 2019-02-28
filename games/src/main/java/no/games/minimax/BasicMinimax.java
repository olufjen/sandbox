/*
 * This file is part of minimax4j. 
 * <https://github.com/avianey/minimax4j> 
 *   
 * The MIT License (MIT) 
 
 * Copyright (c) 2015 Antoine Vianey 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions: 
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE. 
 */
package no.games.minimax; 
 
import java.util.Collection; 
import java.util.List;

import fr.pixelprose.minimax4j.Move; 
 
/**
 * Abstract class implementing minimax and derivated decision rules for two-person  
 * <a href="http://en.wikipedia.org/wiki/Zero-sum_game">zero-sum</a> games of perfect information. 
 * Extend this class to implement IA for several games, such as : 
 * <ul> 
 * <li>Chess</li> 
 * <li>Reversi</li> 
 * <li>Checkers</li> 
 * <li>Go</li> 
 * <li>Connect Four</li> 
 * <li>Tic Tac Toe</li> 
 * <li>...</li> 
 * </ul> 
 *  
 * @author antoine vianey 
 * 
 * @param <M> Implementation of the Move interface to use 
 */ 
public abstract class BasicMinimax<M extends Move> implements Minimax<M> { 
 
    private final Algorithm algo; 
    private int step = 0;
 
    static final class MoveWrapper<M extends Move> { 
        public M move; 
    } 
 
    /**
     * Creates a new IA using the {@link Algorithm#NEGAMAX} algorithm<br/> 
     * {@link Algorithm#NEGASCOUT} performs slowly in case of a weak move ordering... 
     */ 
    public BasicMinimax() { 
        this(Algorithm.NEGAMAX); 
    } 
 
    /**
     * Creates a new IA using the provided algorithm 
     * @param algo The decision rule to use 
     * @see Algorithm 
     */ 
    public BasicMinimax(Algorithm algo) { 
        this.algo = algo; 
    } 
     
    @Override 
    public M getBestMove(final int depth) { 
        if (depth <= 0) { 
            throw new IllegalArgumentException("Search depth MUST be > 0"); 
        } 
        MoveWrapper<M> wrapper = new MoveWrapper<>(); 
        switch (algo) { 
        case MINIMAX: 
            minimax(wrapper, depth, 1); 
            break; 
        case ALPHA_BETA: 
            alphabeta(wrapper, depth, 1, -maxEvaluateValue(), maxEvaluateValue()); 
            break; 
        case NEGAMAX: 
            negamax(wrapper, depth, -maxEvaluateValue(), maxEvaluateValue()); 
            break; 
        case NEGASCOUT: 
        default: 
            negascout(wrapper, depth, -maxEvaluateValue(), maxEvaluateValue()); 
            break; 
        } 
        return wrapper.move; 
    } 
     
    /**
     * Minimax algorithm implementation : 
     * <pre> 
     * function minimax(node, depth, maximizingPlayer) 
     *     if depth = 0 or node is a terminal node 
     *         return the heuristic value of node 
     *     if maximizingPlayer 
     *         bestValue := -8 
     *         for each child of node 
     *             val := minimax(child, depth - 1, FALSE) 
     *             bestValue := max(bestValue, val) 
     *         return bestValue 
     *     else 
     *         bestValue := +8 
     *         for each child of node 
     *             val := minimax(child, depth - 1, TRUE) 
     *             bestValue := min(bestValue, val) 
     *         return bestValue 
     * </pre> 
     *  
     * Initial call for maximizing player 
     * <pre>minimax(origin, depth, TRUE)</pre> 
     *  
     * @param wrapper 
     * @param depth 
     * @param who : Value first call = 1 
     * @return 
     */ 
    private final double minimax(final MoveWrapper<M> wrapper, final int depth, final int who) { 
        if (depth == 0 || isOver()) {
        	if (isOver()) {
        		System.out.println("Exit "+who+" "+who * evaluate()+" "+isOver()+" Depth "+depth+" Step "+step);
        		step = 0;
        	}
        	System.out.println("Exit "+who+" "+who * evaluate()+" Depth "+depth+" Step "+step);
            return who * evaluate(); 
        } 
        M bestMove = null; 
        Collection<M> moves = getPossibleMoves(); 
        if (moves.isEmpty()) { 
        	System.out.println("Moves is empty ");
         next(); 
            double score = minimaxScore(depth, who); 
            previous(); 
            return score; 
        } 
        if (who > 0) { 
            double score = -maxEvaluateValue(); 
            double bestScore = -maxEvaluateValue(); 
            for (M move : moves) { 
//            	System.out.println("Calling minimax "+who+" "+score+" "+depth+" Move "+move+" Bestscore "+bestScore);
                makeMove(move); 
                score = minimaxScore(depth, who); 
                unmakeMove(move); 
                if (score > bestScore) { 
                    bestScore = score; 
                    bestMove = move; 
                } 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return bestScore; 
        } else { 
            double score = maxEvaluateValue(); 
            double bestScore = maxEvaluateValue(); 
            for (M move : moves) { 
//            	System.out.println("Calling minimax else "+who+" "+score+" "+depth+" Move "+move+" Bestscore "+bestScore);
                makeMove(move); 
                score = minimaxScore(depth, who); 
                unmakeMove(move); 
                if (score < bestScore) { 
                    bestScore = score; 
                    bestMove = move; 
                } 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return bestScore; 
        } 
    } 
     
    protected double minimaxScore(final int depth, final int who) { 
    	step++;
    	return minimax(null, depth - 1, -who); 
 } 
 
 /**
     * Minimax with alpha beta algorithm : 
     * <pre> 
     * function alphabeta(node, depth, a, �, maximizingPlayer) 
  *     if depth = 0 or node is a terminal node 
  *         return the heuristic value of node 
  *     if maximizingPlayer 
  *         for each child of node 
  *             a := max(a, alphabeta(child, depth - 1, a, �, FALSE)) 
  *             if � <= a 
  *                 break (* � cut-off *) 
  *         return a 
  *     else 
  *         for each child of node 
  *             � := min(�, alphabeta(child, depth - 1, a, �, TRUE)) 
  *             if � <= a 
  *                 break (* a cut-off *) 
  *         return � 
  * </pre> 
  * Initial call for maximizing player 
     * <pre>alphabeta(origin, depth, -8, +8, TRUE)</pre> 
     *  
     * @param wrapper 
     * @param depth 
     * @param who 
     * @param alpha 
     * @param beta 
     * @return 
     */ 
    private final double alphabeta(final MoveWrapper<M> wrapper, final int depth, final int who, double alpha, double beta) { 
        if (depth == 0 || isOver()) { 
            return who * evaluate(); 
        } 
        M bestMove = null; 
        double score; 
        Collection<M> moves = getPossibleMoves(); 
        if (moves.isEmpty()) { 
         next(); 
            score = alphabetaScore(depth, who, alpha, beta); 
            previous(); 
            return score; 
        } 
        if (who > 0) { 
            for (M move : moves) { 
                makeMove(move); 
                score = alphabetaScore(depth, who, alpha, beta); 
                unmakeMove(move); 
                if (score > alpha) { 
                    alpha = score; 
                    bestMove = move; 
                    if (alpha >= beta) { 
                        break; 
                    } 
                } 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return alpha; 
        } else { 
            for (M move : moves) { 
                makeMove(move); 
                score = alphabetaScore(depth, who, alpha, beta); 
                unmakeMove(move); 
                if (score < beta) { 
                    beta = score; 
                    bestMove = move; 
                    if (alpha >= beta) { 
                        break; 
                    } 
                } 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return beta; 
        } 
    } 
 
    protected double alphabetaScore(final int depth, final int who, final double alpha, final double beta) { 
  return alphabeta(null, depth - 1, -who, alpha, beta); 
 } 
     
    /**
     * Negamax algorithm : 
     * <pre> 
     * function negamax(node, depth, color) 
     *     if depth = 0 or node is a terminal node 
     *         return color * the heuristic value of node 
     *     bestValue := -8 
     *     foreach child of node 
     *         val := -negamax(child, depth - 1, -color) 
     *         bestValue := max( bestValue, val ) 
     *     return bestValue 
     * </pre> 
     *  
     * Initial call for Player A's root node 
     * <pre> 
     * rootNegamaxValue := negamax( rootNode, depth, 1) 
     * rootMinimaxValue := rootNegamaxValue 
     * </pre> 
     *  
     * Initial call for Player B's root node 
     * <pre> 
     * rootNegamaxValue := negamax( rootNode, depth, -1) 
     * rootMinimaxValue := -rootNegamaxValue 
     * </pre> 
     *  
     * This implementation use alpha-beta cut-offs. 
     *  
     * @param wrapper 
     * @param depth 
     * @param alpha 
     * @param beta 
     * @return 
     */ 
    private double negamax(final MoveWrapper<M> wrapper, final int depth, double alpha, double beta) { 
        if (depth == 0 || isOver()) { 
            return evaluate(); 
        } 
        M bestMove = null; 
        Collection<M> moves = getPossibleMoves(); 
        if (moves.isEmpty()) { 
         next(); 
         double score = negamaxScore(depth, alpha, beta); 
         previous(); 
         return score; 
        } else { 
            double score = -maxEvaluateValue(); 
            for (M move : moves) { 
                makeMove(move); 
                score = negamaxScore(depth, alpha, beta); 
                unmakeMove(move); 
                if (score > alpha) { 
                    alpha = score; 
                    bestMove = move; 
                    if (alpha >= beta) { 
                        break; 
                    } 
                } 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return alpha; 
        } 
    } 
 
    protected double negamaxScore(final int depth, final double alpha, final double beta) { 
  return -negamax(null, depth - 1, -beta, -alpha); 
 } 
     
    /**
     * Negascout PVS algorithm : 
     * <pre> 
     * function pvs(node, depth, a, �, color) 
     *     if node is a terminal node or depth = 0 
     *         return color x the heuristic value of node 
     *     for each child of node 
     *         if child is not first child 
     *             score := -pvs(child, depth-1, -a-1, -a, -color)       (* search with a null window *) 
     *             if a < score < �                                      (* if it failed high, 
     *                 score := -pvs(child, depth-1, -�, -score, -color)         do a full re-search *) 
     *         else 
     *             score := -pvs(child, depth-1, -�, -a, -color) 
     *         a := max(a, score) 
     *         if a >= � 
     *             break                                            (* beta cut-off *) 
     *     return a 
     * </pre> 
     *  
     * @param wrapper 
     * @param depth 
     * @param alpha 
     * @param beta 
     * @return 
     */ 
    private double negascout(final MoveWrapper<M> wrapper, final int depth, double alpha, double beta) { 
        if (depth == 0 || isOver()) { 
            return evaluate(); 
        } 
        List<M> moves = getPossibleMoves(); 
        double b = beta; 
        M bestMove = null; 
        if (moves.isEmpty()) { 
         next(); 
            double score = negascoutScore(true, depth, alpha, beta, b); 
            previous(); 
            return score; 
        } else { 
            double score; 
            boolean first = true; 
            for (M move : moves) { 
                makeMove(move); 
                score = negascoutScore(first, depth, alpha, beta, b); 
                unmakeMove(move); 
                if (score > alpha) { 
                    alpha = score; 
                    bestMove = move; 
                    if (alpha >= beta) { 
                        break; 
                    } 
                } 
                b = alpha + 1; 
                first = false; 
            } 
            if (wrapper != null) { 
                wrapper.move = bestMove; 
            } 
            return alpha; 
        } 
    } 
 
    protected double negascoutScore(final boolean first, final int depth, final double alpha, final double beta, final double b) { 
     double score = -negascout(null, depth - 1, -b, -alpha); 
        if (!first && alpha < score && score < beta) { 
            // fails high... full re-search 
            score = -negascout(null, depth - 1, -beta, -alpha); 
        } 
        return score; 
 } 
 
    /**
     * Get the implementation used for tree search. 
     * @return the {@link Algorithm} used. 
     */ 
    public Algorithm getAlgorithm() { 
        return algo; 
    } 
     
}