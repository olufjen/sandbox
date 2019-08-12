package no.games.chess;

/**
 * AbstractPlayer represent a general Chess player
 * 
 * @author oluf
 *
 * @param <P>
 * @param <M>
 */
public abstract class AbstractPlayer<P, M> implements ChessPlayer<P,M> {

	protected static enum player {
		WHITE,
		BLACK
	}
	protected static player whitePlayer = player.WHITE;
	protected static player blackPlayer = player.BLACK;
	
	public abstract void collectmyPieces();

	public player getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(player whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public player getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(player blackPlayer) {
		this.blackPlayer = blackPlayer;
	}
	
}
