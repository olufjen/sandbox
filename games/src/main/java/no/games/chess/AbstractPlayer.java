package no.games.chess;

/**
 * AbstractPlayer represent a general Chess player
 * The AbstractPlayer has an enumeration that indicate if the player is black or white
 * @author oluf
 *
 * @param <GamePiece>
 * @param <PieceMove>
 */
public abstract class AbstractPlayer<GamePiece, PieceMove> implements ChessPlayer<GamePiece, PieceMove> {

	protected static enum player {
		WHITE,
		BLACK
	}
	protected static player whitePlayer = player.WHITE;
	protected static player blackPlayer = player.BLACK;
	protected String playerId = null;
	
	public abstract void collectmyPieces();

	public player getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(player whitePlayer) {
		this.whitePlayer = whitePlayer;
		playerId = "WHITE";
	}

	public player getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(player blackPlayer) {
		this.blackPlayer = blackPlayer;
		playerId = "BLACK";
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}


	
	
}
