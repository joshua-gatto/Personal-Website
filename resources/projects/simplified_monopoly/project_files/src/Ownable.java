import java.util.*;

/**
 * The Property Class
 *
 * @author Joshua Gatto
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public class Ownable extends Tile {
    /**
     * The price of the ownable tile.
     */
    private int price;

    /**
     * Boolean to show if ownable tile is owned or not.
     */
    private boolean owned;

    /**
     * The Piece that owns the property.
     */
    private Piece owner;

    /**
     * Default constructor for a new ownable tile, sets owned to false and price to 0.
     */
    public Ownable() {
        super();
        this.price = 0;
        this.owned = false;
    }

    /**
     * Constructor that sets the ownable tile's name, price and if it is owned or not.
     * @param name String, the ownable tile's name.
     * @param price int, the price of the ownable tile.
     * @param owned boolean, true if the ownable tile is owned, otherwise false.
     */
    public Ownable(String name, int price, boolean owned) {
        super(name);
        this.price = price;
        this.owned = owned;
    }

    /**
     * Sets the price of the ownable tile.
     * @param price int, the price of the ownable tile.
     */
    public void setPrice(int price){
        this.price = price;
    }

    /**
     *  Get the price of this ownable tile.
     * @return int, price of ownable tile
     */
    public int getPrice(){
        return this.price;
    }

    /**
     * Check to see if this property is owned.
     * @return boolean, True if property is owned, otherwise false.
     */
    public boolean getOwned(){return this.owned;}

    /**
     * Get the owner of the property.
     * @return Piece, the owner of this property.
     */
    public Piece getOwner(){
        return this.owner;
    }

    /**
     * Switches the ownership from owned to unowned or vice versa.
     */
    public void changeOwnership(){
        this.owned = !owned;
    }

    /**
     * Changes the owner to the passed Piece value and sets the owned boolean to true.
     * @param p Piece, piece that bought the property.
     */
    public void sold(Piece p){
        this.owner = p;
        this.owned = true;
    }

    /**
     * Checks to see if this ownable tile is the same as the ownable tile passed through the parameter.
     * @param o Object, to check if this is equivalent to this ownable tile.
     * @return boolean, return true if ownable tile equivalent to o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ownable ownable = (Ownable) o;
        return price == ownable.price && owned == ownable.owned;
    }
    /**
     * This method returns the hashCode of an object.
     * @return int, generated by the hash algorithm
     */
    @Override
    public int hashCode() {
        return Objects.hash(price, owned);
    }
}
