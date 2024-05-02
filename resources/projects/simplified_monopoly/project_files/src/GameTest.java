import org.junit.*;
import static org.junit.Assert.*;

/**
 * SYSC 3110 Monopoly - Milestone 3 GameTest Test Model Class
 *
 * Test Class for Game Model
 *
 * @author Gilles Myny - 101145477, Frank Dow -101140402
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class GameTest {
    Game game;

    /**
     * Tests to make sure when roll() is called that the diceroll variable isn't equal to 0.
     */
    @Test
    public void testRoll() {
        game = new Game(2, 0);
        game.roll();
        assertNotEquals(0, game.getDiceRoll());
    }

    /**
     * Tests that the double counter goes up after a double is rolled.
     */
    @Test
    public void testDoubleCounter(){
        game = new Game(2, 0);
        while(!game.getDoublesRolled()){
            game.roll();
        }
        assertEquals(1, game.getDoubleCounter());
    }

    /**
     * Tests that we can get up to 2 doubles, but the next roll resets it.
     */
    @Test
    public void testTripleDoubles(){
        game = new Game(2, 0);
        while(game.getDoubleCounter()!=3){
            game.roll();
            game.updatePlayer();
        }
        assertEquals(3, game.getDoubleCounter());
        game.roll();
        assertEquals(0, game.getDoubleCounter());
    }

    /**
     * Tests to see if the players current position is equal to the dice they rolled.
     */
    @Test
    public void testMovePiece() {
        game = new Game(2, 0);
        game.roll();
        assertEquals(game.getBoard().getTile(game.getDiceRoll()).getName(), game.getPieces(0).getPosition().getName());
    }

    /**
     * Tests if the game moves onto the next player when updatePlayer() is called.
     */
    @Test
    public void testUpdatePlayer() {
        game = new Game(2, 0);
        game.roll();
        game.movePiece(game.getPieces(0));
        game.updatePlayer();
        assertEquals(1, game.getCurrentPlayer());
    }

    @Test
    public void testNextTurn() {
        game = new Game(2, 0);
        game.nextTurn();
    }

    /**
     * Tests when player decides to purchase property, players funds should reflect the purchase, and players owned tiles should be up to date.
     */
    @Test
    public void testPurchaseProperty() {
        game = new Game(2, 0);
        game.roll();
        game.purchaseProperty();
        assertEquals((1500 - game.getPrice()), game.getPieces(0).getFunds());
        assertEquals(game.getCurrentProperty().getName(), game.getPieces(0).getOwnedTile(0).getName());
    }

    /**
     * Tests when player decides not to purchase property, players funds should be equal to the starting amount.
     */
    @Test
    public void testDoNotPurchaseProperty() {
        game = new Game(2, 0);
        game.roll();
        game.movePiece(game.getPieces(0));
        game.doNotPurchaseProperty();
        assertEquals(1500, game.getPieces(0).getFunds());
    }

    /**
     * Tests if funds are properly removed from payee to the recipient.
     */
    @Test
    public void testPay() {
        game = new Game(2, 0);
        int playerOneFundsBefore = game.getPieces(0).getFunds();
        int playerTwoFundsBefore = game.getPieces(1).getFunds();
        int payRate = (int)((game.getPrice())*0.1);
        game.pay(game.getPieces(0), game.getPieces(1), payRate);
        int playerOneFundsAfter = game.getPieces(0).getFunds();
        int playerTwoFundsAfter = game.getPieces(1).getFunds();
        assertEquals(playerOneFundsBefore - payRate, playerOneFundsAfter);
        assertEquals(playerTwoFundsBefore + payRate, playerTwoFundsAfter);
    }

    /**
     * Tests if a player goes bankrupt when their money is negative.
     */
    @Test
    public void testPieceGoingBankrupt(){
        game = new Game(2, 0);
        game.pay(game.getPieces(0), game.getPieces(1), 1600);
        assertTrue(game.getPieces(0).isBankrupt());
    }

    /**
     * Tests if a player is not bankrupt when they have 0$.
     */
    @Test
    public void testPieceNotBankrupt(){
        game = new Game(2, 0);
        game.pay(game.getPieces(0), game.getPieces(1), 1500);
        assertFalse(game.getPieces(0).isBankrupt());
    }

    /**
     * Tests if a player owns a set and enough money that they are able to purchases houses
     */
    @Test
    public void testPurchaseHouse(){
        game = new Game(2,0);
        game.getPieces(0).addTile(game.getBoard().getTile(1));
        game.getPieces(0).addTile(game.getBoard().getTile(2));
        game.purchaseHouse();
        assertEquals(game.getBoard().getTile(1),game.getProp1());
        assertEquals(game.getBoard().getTile(2),game.getProp2());
    }

    /**
     * Tests if a player is successfully sent to Jail.
     */
    @Test
    public void testSentToJail(){
        game = new Game(2,0);
        game.getPieces(0).setInJail(true);
        assertTrue(game.getPieces(0).isInJail());
    }

    /**
     * Tests if a player gets money deducted when paying a fine.
     */
    @Test
    public void testPayFine(){
        game = new Game(2,0);
        int initialFunds = game.getPieces(0).getFunds();
        game.payFine();
        assertEquals(initialFunds-game.getBail(),game.getPieces(0).getFunds());
    }

    /**
     * Tests if the player is released from jail after paying the fine.
     */
    @Test
    public void testReleasedFromJail(){
        game = new Game(2,0);
        game.getPieces(0).setInJail(true);
        game.getPieces(0).setTurnsInJail(0);
        game.payFine();
        assertFalse(game.getPieces(0).isInJail());
    }

    /**
     * Tests if the player gains 200$ for passing GO.
     */
    @Test
    public void testPassingGo(){
        game = new Game(2,0);
        int initialFunds = game.getPieces(0).getFunds();
        game.getPieces(0).updatePosition(game.getBoard().getTile(game.getBoard().numberOfTiles()-1));
        game.roll();
        assertEquals(initialFunds+200, game.getPieces(0).getFunds());
    }
}