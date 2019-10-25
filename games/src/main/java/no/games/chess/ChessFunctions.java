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
	public static <T,P> List<T> filterMove(List<T> l, P p,Predicate<P>pr){
		List result = new ArrayList<>();
		if (pr.test(p)) {
			
		}
		return null;
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
	 * XYLocations
	 * @param t The chess piece
	 * @param r The functional interface
	 * @return a List<XYLocation>
	 */
	public static <T,L> List<XYLocation> moveRule(T t,MoveRule<T,L> r) {
		return (List<XYLocation>) r.calculateRule(t);
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

