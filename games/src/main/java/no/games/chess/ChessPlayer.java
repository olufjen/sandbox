package no.games.chess;

import java.util.ArrayList;
import java.util.HashMap;

import no.games.chess.AbstractPlayer.player;

/**
 * @author oluf
 * This interface represent a chessPlayer in a game of chess
 * @param <P> Contains the ChessPieces owned by the player
 * @param <M> Represent a Move made by a Piece
 */
public interface ChessPlayer<P, M> {

	public HashMap<String,P> getPieces();
	public HashMap<String,P> getMyPieces();

	public void setMyPieces(HashMap<String, P> myPieces);

	public HashMap<String, M> getMyMoves();

	public void setMyMoves(HashMap<String,M> myMoves);

	public M getCurrentMove();

	public void setCurrentMove(M currentMove);

	public ArrayList<P> getMygamePieces();

	public void setMygamePieces(ArrayList<P> mygamePieces);

	public player getPlayerName();

	public void setPlayerName(player playerName);
	public player getWhitePlayer();

	public void setWhitePlayer(player whitePlayer);

	public player getBlackPlayer();

	public void setBlackPlayer(player blackPlayer);
}