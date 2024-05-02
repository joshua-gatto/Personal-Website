import java.util.*;

/**
 * The AIPiece Class
 *
 * @author Jeremy Trendoff - 101160306
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public class AIPiece extends Piece {

    /**
     * A list of events the AIPiece has done during its turn.
     */
    private final List<String> turnEvents;

    /**
     * Constructor. Initialize name as a given String and balance as a given int.
     * @param name      String, the name of the piece.
     * @param balance   int, the balance the piece has.
     * @param startTile Tile, the tile the player will start on.
     */
    public AIPiece(String name, int balance, Tile startTile) {
        super(name, balance, startTile);
        this.setAIPlayer(true);
        turnEvents = new ArrayList<>();
    }

    /**
     * Add a new turn event to the list.
     * @param event String, the new event.
     */
    public void addTurnEvent(String event) {
        if (event != null) {
            turnEvents.add(event);
        }
    }

    /**
     * Clear all turn events.
     */
    public void clearTurnEvents() {
        turnEvents.removeAll(turnEvents);
    }

    /**
     * Print all turn events.
     * @return String, the turn events.
     */
    public String printTurnEvents() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= turnEvents.size(); i++) {
            sb.append(i).append(". ").append(turnEvents.get(i - 1));
            sb.append("\n");
        }
        return sb.toString();
    }
}