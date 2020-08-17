package no.games.chess;

import java.util.List;
import java.util.function.Predicate;

import aima.core.util.datastructure.XYLocation;

/**
 * MoveRule
 * This functional interface creates a List of XYLocations
 * that determine available positions for the piece t.
 * 
 * @author oluf
 *
 */
public interface MoveRule<T,L> {
	List<XYLocation> calculateRule(T t);


}
