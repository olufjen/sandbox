package no.games.chess;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a abstract Game board in chess.
 * And make abstract methods to be implemented by subclasses
 * A game board is represented by a set of positions held in a HashMap
 * A position is either occupied by a chesspiece or vacant. 
 * @author oluf
 * @param <P> P represents a Position class
 *
 */
public abstract class AbstractgameBoard<P> implements GameBoard{

	protected HashMap<String,P> positions;
	protected List<P> usedPositionlist;
	protected List<P> notusedPositionlist;
	protected List<P> availablePositionlist;
	protected List<P> positionlist; // THe original HashMap of positions as a list
	public HashMap<String, P> getPositions() {
		return positions;
	}
	public void setPositions(HashMap<String, P> positions) {
		this.positions = positions;
	}
	public List<P> getUsedPositionlist() {
		return usedPositionlist;
	}
	public void setUsedPositionlist(List<P> usedPositionlist) {
		this.usedPositionlist = usedPositionlist;
	}
	public List<P> getNotusedPositionlist() {
		return notusedPositionlist;
	}
	public void setNotusedPositionlist(List<P> notusedPositionlist) {
		this.notusedPositionlist = notusedPositionlist;
	}
	public List<P> getAvailablePositionlist() {
		return availablePositionlist;
	}
	public void setAvailablePositionlist(List<P> availablePositionlist) {
		this.availablePositionlist = availablePositionlist;
	}
	public List<P> getPositionlist() {
		return positionlist;
	}
	public void setPositionlist(List<P> positionlist) {
		this.positionlist = positionlist;
	}
	public AbstractgameBoard(HashMap<String, P> positions) {
		super();
		this.positions = positions;
	}

	
	
}
