/**
 * The GameView Interface to implement the boilerplate for handling game events.
 * 
 * @author Jeremy Trendoff - 101160306
 * @version 3.0
 * @since Nov 22nd, 2021
 */
public interface GameView {
    /**
     * Prints welcome message given each Piece object (player).
     * @param e PieceEvent object representing the player event.
     */
    void handleWelcomeMessage(PieceEvent e);

    /**
     * Prints Piece object (player) state including current position, funds, and owned tiles.
     * @param e PieceEvent object representing the player event.
     */
    void handlePlayerState(PieceEvent e);

    /**
     * Prints winner message for the decided Piece object (player).
     * @param e PieceEvent object representing the player event.
     */
    void handleWinner(PieceEvent e);

    /**
     * Prints Piece object (player) turn message and adds necessary frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    void handlePlayerTurn(PieceEvent e);

    /**
     * Prints Piece object (player) re-roll turn message for a double roll and configures frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    void handleDoublesRolled(PieceEvent e);

    /**
     * Prints message when Piece object (player) lands on self-owned property and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handleAlreadyOwnedProperty(PropertyEvent e);

    /**
     * Prints message when Piece object (player) lands on another Piece object (player) owned tile and configures frame for user control.
     * @param e MoneyEvent, the object representing the current game event.
     */
    void handleMustPayRent(MoneyEvent e);

    /**
     * Prints message when Piece object (player) has gone bankrupt and configures frame for user control.
     * @param e MoneyEvent, the object representing the current game event.
     */
    void handleBankruptPlayer(MoneyEvent e);

    /**
     * Prints message when Piece object (player) lands on a tile that is not owned by another Piece object (player) and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handleUnownedProperty(PropertyEvent e);

    /**
     * Prints message when Piece object (player) does not have the necessary funds to purchase the tile landed on.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handleNotEnoughFundsProperty(PropertyEvent e);

    /**
     * Prints message when Piece object (player) does not have the necessary funds to purchase a house on the tile.
     * @param e PieceEvent object representing the player event.
     */
    void handleNotEnoughFundsHouse(PieceEvent e);

    /**
     * Prints message when Piece object (player) purchases the property they have landed on and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handlePurchasedProperty(PropertyEvent e);

    /**
     * Prints message when Piece object (player) decides not to purchase the property they have landed on and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handleDoNotPurchase(PropertyEvent e);

    /**
     * Prints a message to the player saying they are able to purchase a house for a set of two properties
     * and configures frame for user control.
     * @param e HouseEvent, object representing the current game event.
     */
    void handleCanBuyHouse1(HouseEvent e);

    /**
     * Prints a message to the player saying they are able to purchase a house for a set of two properties
     *      * and configures frame for user control.
     * @param e HouseEvent, object representing the current game event.
     */
    void handleCanBuyHouse2(HouseEvent e);

    /**
     * If a player does not have a matching set and attempt to buy houses this message will be output.
     * Configures the frame for user control.
     */
    void handleCannotBuyHouse();

    /**
     * Prints message when the game is called to quit by any user.
     */
    void handleEndGame();

    /**
     * Prints Piece object (player) triple doubles message and configures frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    void handleTripleDoubles(PieceEvent e);

    /**
     * When a player successfully purchases a house this outputs a message to the player.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handlePurchasedHouse(PropertyEvent e);

    /**
     * When a player successfully purchases a hotel this outputs a message to the player.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handlePurchasedHotel(PropertyEvent e);

    /**
     * If a player tries to build on a property where there is already a hotel, this will output a message.
     * @param e PropertyEvent, the object representing the current game event.
     */
    void handleHotel(PropertyEvent e);

    /**
     * Prints message when a player is sent to Jail and telling them the options on how to get out of Jail and configures frame for user control
     * @param e PieceEvent object representing the player event.
     */
    void handleSentToJail(PieceEvent e);

    /**
     * Tells the player that they are still in jail and how many more chances they have to roll doubles and configures frame for user control.
     * Configures the
     * @param e PieceEvent object representing the player event.
     */
    void handleInJail(PieceEvent e);

    /**
     * When a player tries to roll doubles and is unsuccessful and configures frame for user control.
     * However, if a player is successful their turn continues as normal.
     * @param e PieceEvent object representing the player event.
     */
    void handleStillInJail(PieceEvent e);

    /**
     * When a player must pay a fine.
     * @param e PieceEvent object representing the player event.
     */
    void handleMustPayFine(PieceEvent e);

    /**
     * Sends a message to the player saying that the have landed on the jail space, but are "just visiting" and configures frame for user control.
     */
    void handleVisitingJail();

    /**
     * Gives a player a message when they pay their bail and are released from jail and configures frame for user control
     * @param e PieceEvent object representing the player event.
     */
    void handleReleasedFromJail(PieceEvent e);

    /**
     * Sends a message to the user that they have landed on go and configures frame for user control.
     */
    void handleLandOnGo();

    /**
     * When a player passes GO, there will be a pop up box to show them they have earned 200$ for passing GO.
     */
    void handlePassedGo();

    /**
     * Handle an AI turn finishing.
     * @param e PieceEvent object representing the player event.
     */
    void handleAITurnEnd(PieceEvent e);
    void handleSaveGameButton();
}
