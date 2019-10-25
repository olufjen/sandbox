package no.games.chess;

import java.util.ArrayList;
import java.util.HashMap;

import no.games.chess.AbstractPlayer.player;

/**
 * @author oluf
 * This interface represent a chessPlayer in a game of chess
 * @param <GamePiece> Contains the ChessPieces owned by the player
 * @param <PieceMove> Represent a Move made by a Piece
 */
public interface ChessPlayer<GamePiece, PieceMove> {

	public HashMap<String,GamePiece> getPieces();
	public HashMap<String,GamePiece> getMyPieces();

	public void setMyPieces(HashMap<String, GamePiece> myPieces);

	public HashMap<String, PieceMove> getMyMoves();

	public void setMyMoves(HashMap<String,PieceMove> myMoves);

	public PieceMove getCurrentMove();

	public void setCurrentMove(PieceMove currentMove);

	public ArrayList<GamePiece> getMygamePieces();

	public void setMygamePieces(ArrayList<GamePiece> mygamePieces);

	public player getPlayerName();

	public void setPlayerName(player playerName);
	public player getWhitePlayer();

	public void setWhitePlayer(player whitePlayer);

	public player getBlackPlayer();

	public void setBlackPlayer(player blackPlayer);
}