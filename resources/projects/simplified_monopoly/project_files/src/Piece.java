import java.util.*;

/**
 * The Piece Class
 *
 * @author Jeremy Trendoff - 101160306
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class Piece implements java.io.Serializable {

    /**
     * The list of tiles the piece owns.
     */
    private final List<Tile> ownedTiles = new ArrayList<>();

    /**
     * True if the piece is in jail, false otherwise.
     */
    private boolean inJail = false;

    /**
     * Counts the number of turns a player has spent in jail.
     */
    private int turnsInJail = 0;

    /**
     * The number of railroads owned.
     */
    private int railroads = 0;

    /**
     * The number of utilities owned.
     */
    private int utilities = 0;

    /**
     * The name of the Piece.
     */
    private final String name;

    /**
     * The tile the Piece is currently on.
     */
    private Tile currentTile;

    /**
     * The balance the Piece has.
     */
    private int balance;

    /**
     * True if the piece is bankrupt, false otherwise.
     */
    private boolean isBankrupt = false;

    /**
     * True if player is AI, false otherwise.
     */
    private boolean isAIPlayer = false;

    /**
     * Constructor. Initialize name as a given String and balance as a given int.
     *
     * @param name String, the name of the piece.
     * @param balance int, the balance the piece has.
     */
    public Piece(String name, int balance, Tile startTile) {
        this.currentTile = startTile;
        this.name = name;
        this.balance = balance;
    }

    /**
     * Get the name of the Piece.
     * @return String, the name of the piece.
     */
    public String getName() {
        return name;
    }

    /**
     * Get if the piece is bankrupt.
     * @return Boolean, True if bankrupt, false otherwise.
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Get the current tile the piece is on.
     * @return Tile, the current tile the piece is on.
     */
    public Tile getPosition() {
        return currentTile;
    }

    /**
     * Set a new tile for the piece's position.
     * @param newTile Tile, the new tile.
     */
    public void updatePosition(Tile newTile) {
        this.currentTile = newTile;
    }

    /**
     * Add funds to the piece's balance.
     * @param funds double, the new funds.
     */
    public void addFunds(double funds) {
        balance += funds;
        if (balance > 0)
            isBankrupt = false;
    }

    /**
     * Deduct funds from the piece's balance.
     * @param funds double, the funds to remove.
     */
    public void deductFunds(double funds) {
        balance -= funds;
        if (balance < 0)
            isBankrupt = true;
    }

    /**
     * Get the balance of the piece.
     * @return int, the piece's balance.
     */
    public int getFunds() {
        return balance;
    }

    /**
     * Add a Tile to the piece.
     *
     * @param tile Tile, the new tile for the piece.
     * @return Boolean, True if tile was added, false otherwise.
     */
    public boolean addTile(Tile tile) {
        // Check tile by reference.
        if (ownedTiles.contains(tile)) {
            return false;
        }

        // Check tiles manually.
        for (Tile t : ownedTiles) {
            if (t.equals(tile)) {
                return false;
            }
        }

        // Add tiles
        ownedTiles.add(tile);
        if(tile instanceof Railroad){
            railroads += 1;
        }else if(tile instanceof Utility){
            utilities +=1;
        }
        return true;
    }

    /**
     * Remove a property from the piece.
     * @param tile Tile, the tile for the piece to remove.
     * @return Boolean, True if tile was removed, false otherwise
     */
    public boolean removeTile(Tile tile) {
        // Check tile by reference.
        if (ownedTiles.contains(tile)) {
            ownedTiles.remove(tile);
            return true;
        }

        // Check tiles manually.
        for (Tile t : ownedTiles) {
            if (t.equals(tile)) {
                ownedTiles.remove(t);
                return true;
            }
        }

        return false;
    }

    /**
     * Get an owned tile list from the piece.
     * @return List of tiles.
     */
    public List<Tile> getOwnedTileList() {
        return ownedTiles;
    }

    /**
     * Get an owned tile from the piece.
     * @param index int, the position of the tile.
     */
    public Tile getOwnedTile(int index) {
        if(ownedTiles.get(index) != null) {
            return ownedTiles.get(index);
        }
        return null;
    }

    /**
     * Deallocate a bankrupt piece and return its owned tiles to the bank.
     */
    public void deallocateBankruptPiece() {
        for (Tile t : ownedTiles) {
            if (t instanceof Property) {
                ((Property) t).changeOwnership();
            } else if (t instanceof Utility) {
                ((Utility) t).changeOwnership();
            } else if (t instanceof Railroad) {
                ((Railroad) t).changeOwnership();
            }
        }

        ownedTiles.removeAll(ownedTiles);
     }

    /**
     * Print out the tiles the piece owns.
     * @return String, the tiles the piece owns.
     */
    public String printOwnedTiles() {
        StringBuilder str = new StringBuilder();

        List<Property> propertiesOwned = new ArrayList<>();
        List<Utility> utilitiesOwned = new ArrayList<>();
        List<Railroad> railroadsOwned = new ArrayList<>();

        for (Tile t : ownedTiles) {
            if (t instanceof Property) {
                propertiesOwned.add((Property) t);
            } else if (t instanceof Utility) {
                utilitiesOwned.add((Utility) t);
            } else if (t instanceof Railroad) {
                railroadsOwned.add((Railroad) t);
            }
        }

        str.append("\n");
        str.append("Properties: ");

        for (int i = 0; i < propertiesOwned.size(); i++) {
            if (i < propertiesOwned.size() - 1) {
                if (i % 4 == 0 && i != 0) {
                    str.append("\n");
                    str.append("            ");
                }
                str.append(propertiesOwned.get(i).getName()).append(", ");
            } else
                str.append(propertiesOwned.get(i).getName());
        }

        str.append("\n");
        str.append("Utilities: ");

        for (int i = 0; i < utilitiesOwned.size(); i++) {
            if (i < utilitiesOwned.size() - 1) {
                if (i % 4 == 0 && i != 0) {
                    str.append("\n");
                    str.append("           ");
                }
                str.append(utilitiesOwned.get(i).getName()).append(", ");
            } else
                str.append(utilitiesOwned.get(i).getName());
        }

        str.append("\n");
        str.append("Railroads: ");

        for (int i = 0; i < railroadsOwned.size(); i++) {
            if (i < railroadsOwned.size() - 1) {
                if (i % 4 == 0 && i != 0) {
                    str.append("\n");
                    str.append("           ");
                }
                str.append(railroadsOwned.get(i).getName()).append(", ");
            } else
                str.append(railroadsOwned.get(i).getName());
        }

        return str.toString();
    }

    /**
     * returns the number of railroads owned.
     * @return number of railroads owned.
     */
    public int getRailroadsOwned(){return this.railroads;}

    /**
     * Returns the number of Utilities owned.
     * @return Integer representing Utilities owned.
     */
    public int getUtilitiesOwned(){return this.utilities;}

    /**
     * returns the number of turns a piece has spent in jail
     * @return number of turns spent in jail
     */
    public int getTurnsInJail() {
        return turnsInJail;
    }

    /**
     * sets or resets the number of turns a piece has spent in jail
     * @param turnsInJail number of turns a piece has spent in jail
     */
    public void setTurnsInJail(int turnsInJail) {
        this.turnsInJail = turnsInJail;
    }

    /**
     * increments the number of turns a piece has spent in jail
     */
    public void incrementTurnsInJail(){
        this.turnsInJail++;
    }

    /**
     * returns true if a piece is in jail, otherwise false
     * @return the incarceration status of a piece
     */
    public boolean isInJail() {
        return inJail;
    }

    /**
     * sets the incarceration status of a piece
     * @param inJail the piece's incarceration status
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * Get if the player is an AI player.
     * @return  boolean, true if AI player, false otherwise.
     */
    public boolean isAIPlayer() {
        return isAIPlayer;
    }

    /**
     * Set an AIPlayer.
     * @param AIPlayer boolean, true if AI Player, false otherwise.
     */
    protected void setAIPlayer(boolean AIPlayer) {
        isAIPlayer = AIPlayer;
    }
}
