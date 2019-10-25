package no.games.chess;

import aima.core.search.framework.Metrics;

public interface ChessSearch  {
	
	
	public  ChessAction makeDecision(ChessState<?> state);
    public Metrics getMetrics();
    public void setLogEnabled(boolean b);
    public double maxValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth);
    public double minValue(ChessState state, ChessPlayer player, double alpha, double beta, int depth); 
    public double eval(ChessState state, ChessPlayer player);
 
}
