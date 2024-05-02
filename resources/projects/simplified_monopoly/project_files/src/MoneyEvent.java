
import java.util.EventObject;

/**
 * The MoneyEvent Class. Contains the interaction to process.
 *
 * @author Jeremy Trendoff - 101160306
 * @version 1.0
 * @since Nov 21st, 2021
 */
public class MoneyEvent extends EventObject {

    /**
     * The model.
     */
    private final Game model;

    /**
     * The payRate for the interaction.
     */
    private final double payRate;

    /**
     * The tile owner.
     */
    private final String tileOwner;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MoneyEvent(Game source, String tileOwner, double payRate) {
        super(source);
        this.model = source;
        this.tileOwner = tileOwner;
        this.payRate = payRate;
    }

    /**
     * Get the current piece.
     * @return Piece, the current piece.
     */
    public Piece getCurrentPiece() {
        return model.getPieces(model.getCurrentPlayer());
    }

    /**
     * Get tile owner.
     * @return String, Tile owners name.
     */
    public String getTileOwner() {
        return tileOwner;
    }

    /**
     * Get pay rate.
     * @return double, the payRate.
     */
    public double getPayRate() {
        return payRate;
    }
}
