/**
 * The Property Class
 *
 * @author Joshua Gatto
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public class Property extends Ownable {

    /**
     * The price to buy a house for this property.
     */
    private int housePrice;

    /**
     * The number of houses on this property.
     */
    private int houses;

    /**
     * Default constructor for a new property, sets owned to false and price to 0.
     */
    public Property() {
        super();
    }

    /**
     * The constructor to create a property. Initialize the property's name price and set its owned boolean.
     * @param name String, The name of the property
     * @param owned boolean, Owned status of the property
     * @param price int, The price of the property
     */
    public Property(String name, boolean owned, int price, int housePrice) {
        super(name, price, owned);
        this.housePrice = housePrice;
        this.houses = 0;
    }

    /**
     * Gets the price of a house for this property.
     * @return the price of a house.
     */
    public int getHousePrice(){return this.housePrice;}

    /**
     * Adds a house to the property.
     */
    public void addHouse(){
        houses += 1;
    }

    /**
     * Gets the number of house on this property.
     * @return The number of houses.
     */
    public int getHouses(){
        return houses;
    }
}
