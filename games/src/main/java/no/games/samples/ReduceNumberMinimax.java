package no.games.samples;

import java.util.ArrayList;
import java.util.List;

import no.games.minimax.BasicMinimax;

/**
 * @author oluf
 * You start with the number (state = 21)
 * Two players can reduce this number by the value 3, 4 or 5
 * The number can’t go down below 0
 * A player that can’t make a move loses
 * For player 1  to win, then the game must reach the winning position in an odd number of moves
 * How to make this minimax work?
 * 
 *
 */
public class ReduceNumberMinimax extends BasicMinimax<ReduceNumberMove> {

	private int state = 21;
	private int startState= 21;
	static final int firstPlayer = 1; // The two players must always have value 1 and 2. Check BasicMinimax
	static final int secondPlayer = 2;
    private int currentPlayer;

    private int[] gamePieces = {5,4,3};
    private int noPerm = 0; // Number of permutations
    private int size = 0;
    private int turn = 0;
    private int chosenPiece;
	public ReduceNumberMinimax() {
		super();
		size = gamePieces.length;
		noPerm = factorial(size) + size;
		newGame();
	}

	public ReduceNumberMinimax(Algorithm algo) {
		super(algo);
		size = gamePieces.length;
		noPerm = factorial(size) + size;
		newGame();
	}
	public void newGame() {
      
        // X start to play
        currentPlayer = firstPlayer;
        state = startState;
    }
	

	@Override
	public void makeMove(ReduceNumberMove move) {
		state = state - move.getPiece();
        turn++;
        next();
		
	}

	@Override
	public void unmakeMove(ReduceNumberMove move) {
	   turn--;
	   state = state + move.getPiece();
	   if (state > startState)
		   state = startState;
	   previous();
		
	}

	@Override
	public List<ReduceNumberMove> getPossibleMoves() {
		List<ReduceNumberMove> moves = new ArrayList<>();
//		int pNo = 0;
		for ( int pNo = 0;pNo< 3; pNo++) {
			do {
				int piece = gamePieces[pNo];
				ReduceNumberMove move = new ReduceNumberMove(currentPlayer,state - piece,piece);
				moves.add(move);
				next();
				state = state - piece;
			}while (!isOver());
			state = startState;
		}

		return moves;
	}

	@Override
	public double evaluate() {
		int eval = 0; // Undecided?
        if (hasWon(currentPlayer)) {
            // 2 for the win
            eval = 2;
        } else if (hasWon(3 - currentPlayer)) {
            // -2 for loosing
            eval = -2;
        }    
/*        if(state > 4){
           state = gamePieces[0];
        }
        if(state > 3){
        	  state = gamePieces[0];
        }
        if(state > 2){
        	  state = gamePieces[0];
        }
*/
		return eval;
	}

	@Override
	public double maxEvaluateValue() {
		// What is the max value of evaluate?
		return 3;
	}

	@Override
	public void next() {
		currentPlayer = 3 - currentPlayer;
		
		
	}

	@Override
	public void previous() {
		 currentPlayer = 3 - currentPlayer;
		
	

	}
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("State: "+Integer.toString(state)+" Player "+Integer.toString(currentPlayer));
        sb.append("\n");
        return sb.toString();
    }
	@Override
	public boolean isOver() {
		// TODO Auto-generated method stub
		return state < 3 ; // If state less than 3 no more moves can be made.
	}

   private boolean hasWon(int player) {
		  if (currentPlayer == player && isOver())
			  return false; // Current player loses when he cannot make a move
		 if (player == 3 - currentPlayer && isOver()) 
			 return true; //Current player wins if opposing player cannot make a move
		 return false;
		  
  }
  private int factorial(int n) {
		  if (n == 0)
			  return 1;
		  return n*factorial(n-1);
			  
  }

}
