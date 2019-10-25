package no.games.chess;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.Game;
import aima.core.util.datastructure.XYLocation;

/**
 *  The implementation of this class must create an initial state containing board positions and their pieces
 *  based on a a chosen ontology game.
 *  The current state also determines who's turn it is to play.
 *  It implements a getResult method that returns a new state based on the current 
 *  state and the action chosen by the search algorithm.
 *  A description of what a particular action does is a transition model (p. 67 AIMA)
 *  It is also a successor state from the current state after performing action a.
 */
public abstract class AbstractChessGame implements ChessGame<ChessState<GameBoard>, ChessAction, ChessPlayer<GamePiece, PieceMove>>{


	/**
	 * X---> increases left to right with zero based index Y increases top to
	 * bottom with zero based index | | V
	 * This is a simplified version of the chessboard
	 */
	protected int[][] squares;
	protected String[][] piecePosition;
	protected String movedPiece = null; //Name of piece that has been moved
	protected ChessState<GameBoard> chessState; 
	protected ChessAction<?, ?, ?,  GamePiece<?>, ?> chessAction;
	protected ChessPlayer<GamePiece<?>, PieceMove<?,?>> whitePlayer;
	protected ChessPlayer<GamePiece<?>, PieceMove<?,?>> blackPlayer;
	
	/**
	 * Creates a aima chessboard with <code>size</code> rows and size columns. Column and
	 * row indices start with 0.
	 */
	public AbstractChessGame(int size) {
	
		squares = new int[size][size];
		piecePosition = new String[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				squares[i][j] = 0;
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				piecePosition[i][j] = null;
			}
		}
	
	}


	public String getMovedPiece() {
		return movedPiece;
	}


	public void setMovedPiece(String movedPiece) {
		this.movedPiece = movedPiece;
	}


	public ChessAction getChessAction() {
		return chessAction;
	}


	public void setChessAction(ChessAction chessAction) {
		this.chessAction = chessAction;
	}


	public ChessPlayer<GamePiece<?>, PieceMove<?, ?>> getWhitePlayer() {
		return whitePlayer;
	}


	public void setWhitePlayer(ChessPlayer<GamePiece<?>, PieceMove<?,?>> whitePlayer) {
		this.whitePlayer = whitePlayer;
	}


	public ChessPlayer<GamePiece<?>, PieceMove<?, ?>> getBlackPlayer() {
		return blackPlayer;
	}


	public void setBlackPlayer(ChessPlayer<GamePiece<?>, PieceMove<?,?>> blackPlayer) {
		this.blackPlayer = blackPlayer;
	}


	/**
	 * The following methods are used to create and manipulate the aima chessboard.
	 * @return
	 */
	public int getSize() {
		return squares.length;
	}

	/**
	 * clear
	 * This method clears the aima chessboard
	 */
	public void clear() {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				squares[i][j] = 0;
			}
		}
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				piecePosition[i][j] = null;
			}
		}
	}

	public int[][] getSquares() {
		return squares;
	}


	public void setSquares(int[][] squares) {
		this.squares = squares;
	}


	public String[][] getPiecePosition() {
		return piecePosition;
	}


	public void setPiecePosition(String[][] piecePosition) {
		this.piecePosition = piecePosition;
	}


	public void setPiecesAt(List<XYLocation> locations,String name) {
		locations.forEach(this::addPieceAt);
		for (XYLocation loc:locations) {
			addPieceAtPos(loc, name);
		}
	}

	/** Column and row indices start with 0! */
	public void addPieceAt(XYLocation l) {
		if (!(PieceExistsAt(l)))
			squares[l.getXCoOrdinate()][l.getYCoOrdinate()] = 1; 
	}

	public void addPieceAtPos(XYLocation l,String name) {
		if (piecePosition[l.getXCoOrdinate()][l.getYCoOrdinate()] == null)
			piecePosition[l.getXCoOrdinate()][l.getYCoOrdinate()] = name;
	}
	public void removePieceFrom(XYLocation l) {
		if (squares[l.getXCoOrdinate()][l.getYCoOrdinate()] == 1) {
			squares[l.getXCoOrdinate()][l.getYCoOrdinate()] = 0;
		}
		if (piecePosition[l.getXCoOrdinate()][l.getYCoOrdinate()] != null) {
			movedPiece = piecePosition[l.getXCoOrdinate()][l.getYCoOrdinate()];
			piecePosition[l.getXCoOrdinate()][l.getYCoOrdinate()] = null;
		}
	}

	/**
	 * Moves the Piece in the specified column (x-value of <code>l</code>) to
	 * the specified row (y-value of <code>l</code>). 
	 */
	public void movePieceTo(XYLocation l) {
			squares[l.getXCoOrdinate()][l.getYCoOrdinate()] = 1;
	}

	public void movePiece(XYLocation from, XYLocation to) {
		if ((PieceExistsAt(from)) && (!(PieceExistsAt(to)))) {
			removePieceFrom(from);
			addPieceAt(to);
			addPieceAtPos(to,movedPiece);
		}
	}

	public boolean PieceExistsAt(XYLocation l) {
		return (PieceExistsAt(l.getXCoOrdinate(), l.getYCoOrdinate()));
	}

	private boolean PieceExistsAt(int x, int y) {
		return (squares[x][y] > 0);
	}
	private String pieceAt(int x, int y) {
		return piecePosition[x][y];
	}

	public int getNumberOfPiecesOnBoard() {
		int count = 0;
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (squares[i][j] == 1)
					count++;
			}
		}
		return count;
	}

	public List<XYLocation> getPiecePositions() {
		ArrayList<XYLocation> result = new ArrayList<>();
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (PieceExistsAt(i, j))
					result.add(new XYLocation(i, j));
			}
		}
		return result;

	}

	public int getNumberOfAttackingPairs() {
		int result = 0;
		for (XYLocation location : getPiecePositions()) {
			result += getNumberOfAttacksOn(location);
		}
		return result / 2;
	}

	public int getNumberOfAttacksOn(XYLocation l) {
		int x = l.getXCoOrdinate();
		int y = l.getYCoOrdinate();
		return numberOfHorizontalAttacksOn(x, y) + numberOfVerticalAttacksOn(x, y) + numberOfDiagonalAttacksOn(x, y);
	}

	public boolean isSquareUnderAttack(XYLocation l) {
		int x = l.getXCoOrdinate();
		int y = l.getYCoOrdinate();
		return (isSquareHorizontallyAttacked(x, y) || isSquareVerticallyAttacked(x, y)
				|| isSquareDiagonallyAttacked(x, y));
	}

	private boolean isSquareHorizontallyAttacked(int x, int y) {
		return numberOfHorizontalAttacksOn(x, y) > 0;
	}

	private boolean isSquareVerticallyAttacked(int x, int y) {
		return numberOfVerticalAttacksOn(x, y) > 0;
	}

	private boolean isSquareDiagonallyAttacked(int x, int y) {
		return numberOfDiagonalAttacksOn(x, y) > 0;
	}

	private int numberOfHorizontalAttacksOn(int x, int y) {
		int retVal = 0;
		for (int i = 0; i < getSize(); i++) {
			if ((PieceExistsAt(i, y)))
				if (i != x)
					retVal++;
		}
		return retVal;
	}

	private int numberOfVerticalAttacksOn(int x, int y) {
		int retVal = 0;
		for (int j = 0; j < getSize(); j++) {
			if ((PieceExistsAt(x, j)))
				if (j != y)
					retVal++;
		}
		return retVal;
	}

	private int numberOfDiagonalAttacksOn(int x, int y) {
		int retVal = 0;
		int i;
		int j;
		// forward up diagonal
		for (i = (x + 1), j = (y - 1); (i < getSize() && (j > -1)); i++, j--) {
			if (PieceExistsAt(i, j))
				retVal++;
		}
		// forward down diagonal
		for (i = (x + 1), j = (y + 1); ((i < getSize()) && (j < getSize())); i++, j++) {
			if (PieceExistsAt(i, j))
				retVal++;
		}
		// backward up diagonal
		for (i = (x - 1), j = (y - 1); ((i > -1) && (j > -1)); i--, j--) {
			if (PieceExistsAt(i, j))
				retVal++;
		}

		// backward down diagonal
		for (i = (x - 1), j = (y + 1); ((i > -1) && (j < getSize())); i--, j++) {
			if (PieceExistsAt(i, j))
				retVal++;
		}

		return retVal;
	}

	@Override
	public int hashCode() {
		List<XYLocation> locs = getPiecePositions();
		int result = 17;
		for (XYLocation loc : locs) {
			result = 37 * loc.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o != null && getClass() == o.getClass()) {
			AbstractChessGame aBoard = (AbstractChessGame) o;
			if (aBoard.getPiecePositions().size() != getPiecePositions().size())
				return false;
			for (int i = 0; i < getSize(); i++) {
				for (int j = 0; j < getSize(); j++) {
					if (PieceExistsAt(i, j) != aBoard.PieceExistsAt(i, j))
						return false;
				}
			}
			return true;
		}
		return false;
	}

	public void print() {
		System.out.println(getBoardPic());
	}

	public String getBoardPic() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; (row < getSize()); row++) { // row
			for (int col = 0; (col < getSize()); col++) { // col
				if (PieceExistsAt(col, row))
					builder.append(pieceAt(col,row));
				else
					builder.append("-");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
/*
 * Implemented methods from Game
 * (non-Javadoc)
 * @see java.lang.Object#toString()
 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < getSize(); row++) { // rows
			for (int col = 0; col < getSize(); col++) { // columns
				if (PieceExistsAt(col, row))
					builder.append(pieceAt(col,row));
				else
					builder.append('-');
			}
			builder.append("\n");
		}
		return builder.toString();
	}


	public abstract ChessState<GameBoard> getInitialState();


	public abstract ChessPlayer<GamePiece, PieceMove>[] getPlayers();


	public abstract ChessPlayer<GamePiece, PieceMove> getPlayer(ChessState<GameBoard> state);

	
	public abstract List<ChessAction> getActions(ChessState<GameBoard> state);

	/**
	 * getResult
	 * This method returns a new state based on the current state and the
	 * action chosen by the search algorithm
	 * @param state The current state
	 * @param action The action chosen by the search algorithm
	 * @return
	 */
	public abstract ChessState<GameBoard> getResult(ChessState<GameBoard> chessState, ChessAction<?, ?, ?, GamePiece<?>, ?> action);	
	

	
	public abstract boolean isTerminal(ChessState<GameBoard> state);


	public abstract double getUtility(ChessState<?> state, ChessPlayer<GamePiece, PieceMove> player);


	
	public abstract double analyzePieceandPosition(ChessAction<?, ?, ?,GamePiece<?>, ?> action); 
}