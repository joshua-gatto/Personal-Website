import java.util.*;

/**
 * SYSC 3110 Monopoly - Milestone 2 Board Class
 *
 * @author Gilles Myny - 101145477
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class Board implements java.io.Serializable {

    /**
     * The list for all the tiles on the board.
     */
    private List<Tile>  tileList;
    /**
     * The 8 different sets of properties with the same color.
     */
    private List<Tile>  set1,set2,set3, set4,set5,set6,set7,set8;

    /**
     * Constructor that calls helper function to setup the board.
     */
    public Board() {
        initTiles();
    }

    /**
     * Initializes all board spaces and adds them to the tileList ArrayList and to their respective sets.
     */
    private void initTiles() {
        Go goTile = new Go(200);
        Property Mediterranean = new Property("Mediterranean Avenue", false, 60,50);
        Property Baltic = new Property("Baltic Avenue", false, 60,50);
        Railroad Reading = new Railroad("Reading Railroad", 200, false);
        Property Oriental = new Property("Oriental Avenue", false, 100,50);
        Property Vermont = new Property("Vermont Avenue", false, 100,50);
        Property Connecticut = new Property("Connecticut Avenue", false, 120,50);
        Jail jailTile = new Jail();
        Property Charles = new Property("St. Charles Place", false, 140,100);
        Utility Electric = new Utility("Electric Company", 150, false);
        Property States = new Property("States Avenue", false, 140,100);
        Property Virginia = new Property("Virginia Avenue", false, 160,100);
        Railroad PennRailroad = new Railroad("Pennsylvania Railroad", 200, false);
        Property James = new Property("St. James Place", false, 180,100);
        Property Tennessee = new Property("Tennessee Avenue", false, 180,100);
        Property NewYork = new Property("New York Avenue", false, 200,100);
        Parking freeParking = new Parking();
        Property Kentucky = new Property("Kentucky Avenue", false, 220,150);
        Property Indiana = new Property("Indiana Avenue", false, 220,150);
        Property Illinois = new Property("Illinois Avenue", false, 240,150);
        Railroad BnORail = new Railroad("B. & O. Railroad", 200, false);
        Property Atlantic = new Property("Atlantic Avenue", false, 260,150);
        Property Ventnor = new Property("Ventnor Avenue", false, 260,150);
        Utility Water = new Utility("Water Works", 150, false);
        Property Marvin = new Property("Marvin Gardens", false, 280,150);
        SendJail goToJail = new SendJail();
        Property Pacific = new Property("Pacific Avenue", false, 300,200);
        Property Carolina = new Property("North Carolina Avenue", false, 300,200);
        Property Pennsylvania = new Property("Pennsylvania Avenue", false, 320,200);
        Railroad ShortLine = new Railroad("Short Line", 200, false);
        Property ParkPlace = new Property("Park Place", false, 350,200);
        Property Boardwalk = new Property("Boardwalk", false, 400,200);
        tileList = new ArrayList<>(Arrays.asList(goTile, Mediterranean, Baltic, Reading, Oriental, Vermont, Connecticut,
                jailTile, Charles, Electric, States, Virginia, PennRailroad, James, Tennessee, NewYork, freeParking,
                Kentucky, Indiana, Illinois, BnORail, Atlantic, Ventnor, Water, Marvin, goToJail, Pacific, Carolina,
                Pennsylvania, ShortLine, ParkPlace, Boardwalk));
        set1 = new ArrayList<>(Arrays.asList(Mediterranean, Baltic));
        set2 = new ArrayList<>(Arrays.asList(Oriental, Vermont, Connecticut));
        set3 = new ArrayList<>(Arrays.asList(States, Charles, Virginia));
        set4 = new ArrayList<>(Arrays.asList(James, Tennessee, NewYork));
        set5 = new ArrayList<>(Arrays.asList(Indiana, Kentucky, Illinois));
        set6 = new ArrayList<>(Arrays.asList(Atlantic, Ventnor, Marvin));
        set7 = new ArrayList<>(Arrays.asList(Pacific, Carolina, Pennsylvania));
        set8 = new ArrayList<>(Arrays.asList(ParkPlace, Boardwalk));
    }

    /**
     * Clears all tiles on board.
     */
    public void clearBoard() {
        this.tileList.clear();
        this.set1.clear();
        this.set2.clear();
        this.set3.clear();
        this.set4.clear();
        this.set5.clear();
        this.set6.clear();
        this.set7.clear();
        this.set8.clear();
    }

    /**
     * Returns the Tile object of the given spaceNumber the player is on.
     * @param spaceNumber an Integer representing the tile number the player is on.
     * @return a Tile object of the tile the player is on.
     */
    public Tile getTile(int spaceNumber) {
        return tileList.get(spaceNumber);
    }

    /**
     * Returns the tile's space number.
     * @param tile Tile, the tile to get the space number of.
     * @return int, the space number. -1 is returned if tile is not found.
     */
    public int getTilePosition(Tile tile) {
        if (tileList.contains(tile)) {
            return tileList.indexOf(tile);
        }
        return -1;
    }

    /**
     * Returns the number of tiles in the tile list.
     * @return Int, the number of tiles in tile list.
     */
    public int numberOfTiles(){
        return tileList.size();
    }

    /**
     * Add a tile to the tile list for the Board.
     * @param t Tile object representing any type of tile
     */
    public void addTile(Tile t) {
        tileList.add(t);
    }

    /**
     * Create the sets for houses and hotels for the Tiles.
     */
    public void createSets() {
        set1 = Arrays.asList(tileList.get(1), tileList.get(2));
        set2 = Arrays.asList(tileList.get(4), tileList.get(5), tileList.get(6));
        set3 = Arrays.asList(tileList.get(8), tileList.get(10), tileList.get(11));
        set4 = Arrays.asList(tileList.get(13), tileList.get(14), tileList.get(15));
        set5 = Arrays.asList(tileList.get(17), tileList.get(18), tileList.get(19));
        set6 = Arrays.asList(tileList.get(21), tileList.get(22), tileList.get(24));
        set7 = Arrays.asList(tileList.get(26), tileList.get(27), tileList.get(28));
        set8 = Arrays.asList(tileList.get(30), tileList.get(31));
    }

    /**
     * Gets a list of purple properties.
     * @return List of purple properties.
     */
    public List<Tile> getSet1(){
        return set1;
    }

    /**
     * Gets a list of light blue properties.
     * @return List of light blue properties.
     */
    public List<Tile> getSet2(){
        return set2;
    }

    /**
     * Gets a list of pink properties.
     * @return List of pink properties.
     */
    public List<Tile> getSet3(){
        return set3;
    }

    /**
     * Gets a list of orange properties.
     * @return List of orange properties.
     */
    public List<Tile> getSet4(){
        return set4;
    }

    /**
     * Gets a list of red properties.
     * @return List of red properties.
     */
    public List<Tile> getSet5(){
        return set5;
    }

    /**
     * Gets a list of yellow properties.
     * @return List of yellow properties.
     */
    public List<Tile> getSet6(){
        return set6;
    }

    /**
     * Gets a list of green properties.
     * @return List of green properties.
     */
    public List<Tile> getSet7(){
        return set7;
    }

    /**
     * Gets a list of dark blue properties.
     * @return List of dark blue properties.
     */
    public List<Tile> getSet8(){
        return set8;
    }

    /**
     * Gets jail tile.
     * @return Tile object representing Jail tile.
     */
    public Tile getJailTile() {
        return tileList.get(7);
    }
}