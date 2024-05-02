import java.util.EventObject;

/**
 * The PieceEvent Class. Contains the current piece being acted on.
 *
 * @author Jeremy Trendoff - 101160306
 * @version 1.0
 * @since Nov 21st, 2021
 */
public class PieceEvent extends EventObject {

    /**
     * The model the piece is in.
     */
    private final Game model;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PieceEvent(Game source) {
        super(source);
        this.model = source;
    }

    /**
     * Get the currently active piece.
     * @return Piece, the currently active piece object.
     */
    public Piece getCurrentlyActivePiece() {
        return model.getPieces(model.getCurrentPlayer());
    }

    /**
     * Get the winning player.
     * @return Piece, the winner.
     */
    public Piece getWinningPiece() {
        return model.getPieces(0);
    }
}
