package no.games.samples;

import fr.pixelprose.minimax4j.Move;

public class ReduceNumberMove implements Move {

	private int player; // Current player
	private int state; // current value of the game
	private int piece; // piece used for this move

	public ReduceNumberMove(int player, int state, int piece) {
		super();
		this.player = player;
		this.state = state;
		this.piece = piece;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	   public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
	}
	public String toString() {
	    	return (player == ReduceNumberMinimax.firstPlayer ? "1" : "2") + " (" + state + ";" + state + ")"+ " (" + piece + ";" + piece + ")";
	    }	
}
