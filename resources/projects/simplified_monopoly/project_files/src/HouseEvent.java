import java.util.EventObject;

/**
 * The HouseEvent Class. Contains the properties where houses can be purchased.
 *
 * @author Jeremy Trendoff - 101160306
 * @version 1.0
 * @since Nov 21st, 2021
 */
public class HouseEvent extends EventObject {

    /**
     * The model.
     */
    private final Game model;

    /**
     * The first tile.
     */
    private Tile tile1;

    /**
     * The second tile.
     */
    private Tile tile2;

    /**
     * The third tile.
     */
    private Tile tile3 = null;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public HouseEvent(Game source, Tile tile1, Tile tile2) {
        super(source);
        this.model = source;
        this.tile1 = tile1;
        this.tile2 = tile2;
    }

    /**
     * Overloaded Constructor.
     *
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public HouseEvent(Game source, Tile tile1, Tile tile2, Tile tile3) {
        super(source);
        this.model = source;
        this.tile1 = tile1;
        this.tile2 = tile2;
        this.tile3 = tile3;
    }

    /**
     * Get tile 1.
     * @return Tile, tile 1.
     */
    public Tile getTile1() {
        return tile1;
    }

    /**
     * Get tile2.
     * @return Tile, tile2.
     */
    public Tile getTile2() {
        return tile2;
    }

    /**
     * Get tile3.
     * @return Tile, tile3.
     */
    public Tile getTile3() {
        return tile3;
    }
}
