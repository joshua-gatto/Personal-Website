/**
 * The Property Class
 *
 * @author Joshua Gatto
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public class Railroad extends Ownable {

    /**
     * Default constructor that sets price to 0 and owned to false.
     */
    public Railroad() {
        super();
    }

    /**
     * Constructor that sets the railroads name, price and if it is owned or not.
     * @param name String, the railroad's name.
     * @param price int, the price of the railroad.
     * @param owned boolean, true if the railroad is owned, otherwise false.
     */
    public Railroad(String name, int price, boolean owned) {
        super(name, price, owned);
    }
}
