/**
 * The Jail Class
 *
 * @author Joshua Gatto & Gilles Myny
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class Jail extends Tile{

    /**
     * The bail amount.
     */
    private final int bail;

    /**
     * Constructor that calls super Tile class and sets the bail to 50.
     */
    public Jail(){
        super("Jail");
        bail = 50;
    }

    /**
     * Constructor that calls super Tile class and sets the bail to given amount.
     * @param bail Integer value representing necessary bail to pay.
     */
    public Jail(int bail){
        super("Jail");
        this.bail = bail;
    }

    /**
     * Gets the value of bail
     * @return the price of bail
     */
    public int getBail(){
        return bail;
    }
}