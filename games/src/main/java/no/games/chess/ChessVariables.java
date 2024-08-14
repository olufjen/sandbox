package no.games.chess;

/**
 * ChessVariables
 * This class contains constants used in the process of replacing Variable type elements
 * in the preconditions and effects of an action schema with constants or ground elements.
 * It is used in the game of chess.
 * (See chapter 10 in the AIMA book)
 * @author oluf
 *
 */
public class ChessVariables {
	
	private static String pieceVariable = "piecename";
	private static String posVariable = "pos";
	private static String typeVariable = "type";
	private static String playerName;
	private static String playerColor;
	private static final String PAWN =  "PAWN";
	private static final String KNIGHT =  "KNIGHT";
	private static final String BISHOP =  "BISHOP";
	private static final String ROOK =  "ROOK";
	private static final String KING =  "KING";
	private static final String QUEEN =  "QUEEN";

	
	public static String getPAWN() {
		return PAWN;
	}
	public static String getKNIGHT() {
		return KNIGHT;
	}
	public static String getBISHOP() {
		return BISHOP;
	}
	public static String getROOK() {
		return ROOK;
	}
	public static String getKING() {
		return KING;
	}
	public static String getQUEEN() {
		return QUEEN;
	}
	public static String getPieceVariable() {
		return pieceVariable;
	}
	public static void setPieceVariable(String pieceVariable) {
		ChessVariables.pieceVariable = pieceVariable;
	}
	public static String getPosVariable() {
		return posVariable;
	}
	public static void setPosVariable(String posVariable) {
		ChessVariables.posVariable = posVariable;
	}
	public static String getTypeVariable() {
		return typeVariable;
	}
	public static void setTypeVariable(String typeVariable) {
		ChessVariables.typeVariable = typeVariable;
	}
	public static String getPlayerName() {
		return playerName;
	}
	public static void setPlayerName(String playerName) {
		ChessVariables.playerName = playerName;
		if (playerName.contains("White")) {
			ChessVariables.playerColor = "White";
		}else {
			ChessVariables.playerColor = "Black";
		}
	}
	public static String getPlayerColor() {
		return playerColor;
	}
	public static void setPlayerColor(String playerColor) {
		ChessVariables.playerColor = playerColor;
	}
	
	public static String checkPiecetype(String type) {
		
		String returnType = null;
		switch (type) {
		case PAWN:
			returnType = PAWN;
			break;
		case BISHOP:
			returnType = BISHOP;
			break;
		case KNIGHT:
			returnType = KNIGHT;
			break;
		case ROOK:
			returnType = ROOK;
			break;
		case QUEEN:
			returnType = QUEEN;
			break;
		case KING:
			returnType = KING;
			break;
		default:
			return returnType; 
		}
		return returnType;
	}
}
