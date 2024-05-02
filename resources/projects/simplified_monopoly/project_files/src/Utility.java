/**
 * The Utility Class
 *
 * @author Joshua Gatto
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public class Utility extends Ownable {
    /**
     * Default constructor that sets price to 0 and owned to false.
     */
    public Utility() {
        super();
    }

    /**
     * Constructor that sets the utility's name, price and if it is owned or not.
     * @param name String, the utility's name.
     * @param price int, the price of the utility.
     * @param owned boolean, true if the utility is owned, otherwise false.
     */
    public Utility(String name, int price, boolean owned) {
        super(name, price, owned);
    }
}
