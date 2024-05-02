import java.util.EventObject;

/**
 * The PropertyEvent Class. Contains the current properties being acted on.
 *
 * @author Jeremy Trendoff - 101160306
 * @version 1.0
 * @since Nov 21st, 2021
 */
public class PropertyEvent extends EventObject {

    /**
     * The model.
     */
    private final Game model;

    /**
     * A specified property.
     */
    private Property specifiedProperty = null;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PropertyEvent(Game source) {
        super(source);
        this.model = source;
    }

    /**
     * Overloaded Constructor.
     *
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PropertyEvent(Game source, Property specifiedProperty) {
        super(source);
        this.model = source;
        this.specifiedProperty = specifiedProperty;
    }

    /**
     * Get the current tile being acted on.
     * @return Tile, the current tile.
     */
    public Tile getCurrentTile() {
        return model.getPieces(model.getCurrentPlayer()).getPosition();
    }

    /**
     * Get the current Piece.
     * @return Piece, the current piece.
     */
    public Piece getCurrentPiece() {
        return model.getPieces(model.getCurrentPlayer());
    }

    /**
     * Get the specified property passed to the event.
     * @return Property, the specified property.
     */
    public Property getSpecifiedProperty() {
        return specifiedProperty;
    }
}
