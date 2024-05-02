/**
 * The Go Class
 *
 * @author Joshua Gatto & Gilles Myny
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class Go extends Tile{

    /**
     * The reward for passing GO.
     */
    private static int reward;

    /**
     * Constructor that calls super Tile class and sets reward to 200 for passing/landing on Go.
     */
    public Go(){
        super("Go");
        reward = 200;
    }

    /**
     * Constructor that calls super Tile class and sets given reward for passing/landing on Go.
     * @param reward Integer representing reward for passing/landing on Go tile.
     */
    public  Go(int reward){
        super("Go");
        Go.reward = reward;
    }

    public static int getReward(){
        return reward;
    }
}