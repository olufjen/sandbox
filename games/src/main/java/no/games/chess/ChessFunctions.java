package no.games.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import aima.core.util.datastructure.XYLocation;

/**
 * ChessFunctions
 * This class contains useful functional interfaces used in lambda expressions
 * A functional interface is an interface with one abstract method.
 * This method describes the signature of the lambda expression (see p. 53 in Java 8)
 * @author oluf
 *
 */
public class ChessFunctions {

	public static <P,Q> P findpieceType(Q p,Predicate<Q>pr){
		if (pr.test(p))
			return (P) p;
		return null;
	}
	
	/**
	 * filterMove
	 * A filter method to be used to return:
	 * 1. What pieces protect a given position?
	 * 2. What pieces attack a given position
	 * @param l - a list of pieces?
	 * @param p - a given position?
	 * @param pr - the predicate
	 * @return
	 */
	public static <T,P> List<T> filterMove(List<T> l, P p,Predicate<P>pr){
		List<T> result = new ArrayList<>();
		for (T e: l) {
			if (pr.test(p)) {
				result.add(e);
			}
		}
	
		return result;
	}
	/**
	 * filterPiece
	 * A filter method to be used to return:
	 * 1. What pieces protect a given position?
	 * 2. What pieces attack a given position
	 * @param l - a list of pieces?
	 * @param pr - the predicate
	 * @return
	 */
	public static <T> List<T> filterPiece(List<T> list,Predicate<T>pr){
		List<T> result = new ArrayList<>();
		for (T e: list) {	
			if (pr.test(e)) {
				result.add(e);
			}
		}
	
		return result;
	}
	/**
	 * processChessgame
	 * This is a chess game processor
	 * The parameters are chess game objects and the gameprocessor is of type 
	 * ChessProcessor
	 * The function descriptor is (P,Q) -> R
	 * @param p
	 * @param q
	 * @param processor
	 * @return A modified chess object
	 */
	public static <P, Q, R> R processChessgame(P p,Q q,ChessProcessor processor) {
		return (R) processor.processChessObject(p, q);
	}
	/**
	 * createType
	 * This functional interface creates a new possible Type
	 * based on the Types P and A
	 * The function descriptor is (P,A) -> M
	 * @param p Any type of chess game object
	 * @param a Any type of chess game object
	 * @param f The create function
	 * @return THe chess object m must have a constructor of type m(P,A)
	 */
	public static <P,A,M> M createType(P p,A a,BiFunction<P,A,M> f) {
		
		M m = f.apply(p, a);
		return m;
		
	}
	
	/**
	 * moveRule
	 * This is a moveRuler. Based on chess piece type it calculates a list of available
	 * Positions
	 * @param t The chess piece
	 * @param r The functional interface
	 * @return a List<P> of positions.
	 */
	
	public static <T,L> List<XYLocation> moveRule(T t,MoveRule<T,L> r) {
		return (List<XYLocation>) r.calculateRule(t);
	}
	public static <T,P> List<P> moveCalculation(T t,Predicate<T> pr,MoveCalculator<T,P> r) {
		return (List<P>) r.calculatePositions(t,pr);
	}
	
	public static <P,T> List<T> calculateMoves(P p,Predicate<P> pr){
		List<T> result = new ArrayList<>();
		
		if (pr.test(p)) {
			
		}
		return null;
	}
/*	
*/
}

