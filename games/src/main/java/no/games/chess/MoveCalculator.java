package no.games.chess;

import java.util.List;
import java.util.function.Predicate;

import aima.core.util.datastructure.XYLocation;

/**
 * MoveRule
 * This functional interface creates a List of positions 
 * that determine available positions for the piece t.
 * 
 * @author oluf
 *
 */
public interface MoveCalculator<T,P> {
	P calculatePositions(T t,Predicate<T>p);

}
