package no.games.chess;

import java.util.List;

import aima.core.util.datastructure.XYLocation;

/**
 * MoveRule
 * This functional interface creates a List of XYlocations 
 * that determine available positions for the piece t.
 * They are calculated as XYLocations
 * @author oluf
 *
 */
public interface MoveRule<T,L> {
	List<XYLocation> calculateRule(T t);

}
