package no.games.samples;
import java.util.List;
import no.games.minimax.Minimax.Algorithm;

public class PlayReducedNumber {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		Algorithm algo = Algorithm.MINIMAX;
		
		ReduceNumberMinimax reduceNumbergame = new ReduceNumberMinimax(algo);
		List<ReduceNumberMove>  moves = reduceNumbergame.getPossibleMoves();

//		ReduceNumberMove movex = moves.get(0);
		System.out.println("The game \n");
		for (ReduceNumberMove move : moves) {
			int player = move.getPlayer();

			System.out.println("Moves "+move.toString());
			
		}
		int ct = 0;
		while (!reduceNumbergame.isOver()){
			ct++;
			ReduceNumberMove bestmove = reduceNumbergame.getBestMove(5);
			int player = bestmove.getPlayer();

			System.out.println("Move "+ct+" Player "+player);
			reduceNumbergame.makeMove(bestmove);
			System.out.println(reduceNumbergame.toString());
		}

		
		System.out.println("END  : \n"+reduceNumbergame.toString());
	}
}
