package no.games.samples;
import java.util.List;
import no.games.minimax.Minimax.Algorithm;

public class PlayTicTacToe {

	public static void main(String args[]) throws Exception{
		Algorithm algo = Algorithm.MINIMAX;
		
		TicTacToeMinimax tictacToegame = new TicTacToeMinimax(algo);
		List<TicTacToeMove>  moves = tictacToegame.getPossibleMoves();

		TicTacToeMove movex = moves.get(0);
		System.out.println("The game \n");
		for (TicTacToeMove move : moves) {
			int player = move.getPlayer();
			int xpos = move.getX();
			int ypos = move.getY();
			System.out.println("Moves "+move.toString());
			
		}
		int ct = 0;
		while (!tictacToegame.isOver()){
			ct++;
			TicTacToeMove bestmove = tictacToegame.getBestMove(8);
			int player = bestmove.getPlayer();
			int xpos = bestmove.getX();
			int ypos = bestmove.getY();
			System.out.println("Move "+ct+" Player "+player+ " x "+xpos+" y "+ypos);
			tictacToegame.makeMove(bestmove);
			System.out.println("\n"+tictacToegame.toString());
		}

		
		System.out.println("END  : \n"+tictacToegame.toString());
	}
}
