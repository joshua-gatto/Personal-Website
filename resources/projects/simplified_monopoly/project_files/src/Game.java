import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

/**
 * Game class that will create the board, player pieces and have a text based version of the game
 * outputted to the console where the user can type their commands to the game.
 *
 * @author Frank Dow 101140402, Jeremy Trendoff - 101160306
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class Game extends DefaultHandler implements java.io.Serializable {
    /**
     * The rent for a railroad.
     */
    private static final int RAILROAD_RENT = 25;

    /**
     * Rent of Utility of type 1.
     */
    private static final int UTILITY_RENT_ONE = 4;

    /**
     * Rent fo Utility of type 2.
     */
    private static final int UTILITY_RENT_TWO = 10;

    /**
     * Percentage of property price to be rent.
     */
    private static final double PROPERTY_RENT = 0.2;

    /**
     * File name of Canada version
     */
    private static final String CANADA_XML = "canada.xml";

    /**
     * File name of England version
     */
    private static final String UK_XML = "uk.xml";

    /**
     * The Monopoly Game Board.
     */
    private Board board;

    /**
     * The current ownable tile the game is working with.
     */
    private Ownable currentOwnable;
    /**
     * The player whose turn it is.
     */
    private int currentPlayer;

    /**
        The properties a player may be buying houses for.
     */
    private Property prop1, prop2, prop3;

    /**
     * The list of pieces/players playing the game.
     */
    private final ArrayList<Piece> pieces;

    /**
     * The result of a dice rolls and the combined result.
     */
    private int diceRoll;

    /**
     * Integer representing the bail amount.
     */
    private final int BAIL = 50;

    /**
     * Counts how many doubles have been rolled.
     */
    private int doubleCounter;

    /**
     * The amount of properties currently in the game.
     */
    private final int totalProperties;

    /**
     * Represents if doubles have been rolled or not.
     */
    private boolean doublesRolled;

    /**
     * The maximum players in the game.
     */
    private int maxPlayers;

    /**
     * Price of the currentProperty space.
     */
    private int price;

    /**
     * The main view that the game will be communicating with.
     */
    private final List<GameView> views;

    public enum State {state, pass, roll, yes, no, quit, purchaseHouse, buyingHouse1, buyingHouse2, buyingHouse3, payFine, saveGame}

    /**
     * The Game method initializes the board and the players pieces.
     * It allows each player to enter their name and starts the game by letting player 1 go first.
     * Players have three options on their turn : ROLL STATE QUIT.
     * @param players represents how many players will be playing.
     * @param AIPlayers represents how many AI players will be playing
     */
    public Game(int players, int AIPlayers) {
        board = new Board();
        pieces = new ArrayList<>();
        views = new ArrayList<>();
        totalProperties = board.numberOfTiles();
        doubleCounter = 0;
        int i;
        for (i = 1; i <= players; i++) {
            String name = JOptionPane.showInputDialog("Enter the name for player " + i + ":");
            Piece newPlayer = new Piece(name, 1500, board.getTile(0));
            pieces.add(newPlayer);
        }

        int j;
        for (j = 1; j <= AIPlayers; j++) {
            AIPiece newPlayer = new AIPiece("AIPlayer" + j, 1500, board.getTile(0));
            pieces.add(newPlayer);
        }

        maxPlayers = pieces.size();
        currentPlayer = 0;
    }

    /**
     * Add a view to the game.
     * @param view the view that will be showing the game in a GUI.
     */
    public void addGameView(GameFrame view) {
        this.views.add(view);
    }

    /**
     * This method tells the view to send the welcome message for the game.
     */
    public void welcomeMessage() {
        PieceEvent e = new PieceEvent(this);
        for(GameView view: views) {
            view.handleWelcomeMessage(e);
        }
    }

    /**
     * The roll method rolls two six sided dice and sets the diceRoll to that number.
     */
    public void roll() {
            int diceRoll1 = (int) (Math.random() * 6) + 1;
            int diceRoll2 = (int) (Math.random() * 6) + 1;
            diceRoll = diceRoll1 + diceRoll2;

            if(pieces.get(currentPlayer).isInJail()) {
                if(diceRoll1==diceRoll2) {
                    if (pieces.get(currentPlayer).isAIPlayer())
                        ((AIPiece) pieces.get(currentPlayer)).addTurnEvent("Rolled Doubles and got out of Jail.");
                    pieces.get(currentPlayer).setInJail(false);
                    pieces.get(currentPlayer).setTurnsInJail(0);
                    this.nextTurn();
                }else {
                    pieces.get(currentPlayer).incrementTurnsInJail();
                    if(pieces.get(currentPlayer).getTurnsInJail()==3){
                        for (GameView view : views) {
                            PieceEvent e = new PieceEvent(this);
                            view.handleMustPayFine(e);
                        }
                    }else {
                        if (!pieces.get(currentPlayer).isAIPlayer()) {
                            for (GameView view : views) {
                                PieceEvent e = new PieceEvent(this);
                                view.handleStillInJail(e);
                            }
                        } else {
                            payFine();
                        }
                    }

                }
            }else {
                if (doubleCounter == 3) {
                    doubleCounter = 0;
                    doublesRolled = false;
                    pieces.get(currentPlayer).setInJail(true);
                    pieces.get(currentPlayer).setTurnsInJail(0);//just in case
                    pieces.get(currentPlayer).updatePosition(board.getJailTile());

                    PieceEvent e = new PieceEvent(this);

                    if (!pieces.get(currentPlayer).isAIPlayer()) {
                        for (GameView view : views) {
                            view.handleTripleDoubles(e);
                        }
                    } else {
                        for (GameView view : views) {
                            view.handleAITurnEnd(e);
                        }
                    }
                } else if (diceRoll1 == diceRoll2) {
                    doubleCounter += 1;
                    doublesRolled = true;

                    if (pieces.get(currentPlayer).isAIPlayer())
                        ((AIPiece) pieces.get(currentPlayer)).addTurnEvent("Rolled Doubles");

                    this.nextTurn();
                } else {
                    doubleCounter = 0;
                    doublesRolled = false;
                    this.nextTurn();
                }
            }
    }

    /**
     * The movePiece method adds the diceRoll to the current space index the piece is on.
     * Then updates the pieces position to the result.
     * @param movedPiece The piece that is currently moving.
     */
    public void movePiece(Piece movedPiece) {
        int currentSpace = board.getTilePosition(movedPiece.getPosition()) + diceRoll;
        if (currentSpace>totalProperties-1) {
            currentSpace = currentSpace - totalProperties;
            pieces.get(currentPlayer).addFunds(Go.getReward());
            if (!pieces.get(currentPlayer).isAIPlayer()) {
                for (GameView view : views) view.handlePassedGo();
            } else {
                ((AIPiece) pieces.get(currentPlayer)).addTurnEvent("Passed Go!");
            }
        }
        movedPiece.updatePosition(board.getTile(currentSpace));
    }

    /**
     * The print state method prints the players position on the board, the money they have and the properties they own.
     *
     */
    public void printState() {
        PieceEvent e = new PieceEvent(this);
        for (GameView view : views) {
            view.handlePlayerState(e);
        }
    }

    /**
     * The update player method updates the current player to the next player. However, if the previous player rolled
     * doubles they get to roll again.
     */
    public void updatePlayer() {
        PieceEvent e = new PieceEvent(this);

        if(maxPlayers == 1) {
            for(GameView view: views){view.handleWinner(e);}
        }
        if(!doublesRolled || pieces.get(currentPlayer).isInJail()) {

           currentPlayer++;
           if (currentPlayer == maxPlayers) {
               currentPlayer = 0;
           }
           if(pieces.get(currentPlayer).isInJail()) {
               if (!pieces.get(currentPlayer).isAIPlayer()) {
                   for (GameView view : views) view.handleInJail(e);
               } else {
                   payFine();
               }
           } else {
                if (!pieces.get(currentPlayer).isAIPlayer()) {
                    for (GameView view : views) {
                        view.handlePlayerTurn(e);
                    }
                } else {
                    roll();
                }
            }
        } else {
            if (!pieces.get(currentPlayer).isAIPlayer()) {
                for (GameView view : views) {
                    view.handleDoublesRolled(e);
                }
            } else {
                roll();
            }
        }
    }

    /**
     * The nextTurn method goes through a turn of a player by rolling the dice moving their piece.
     * If they land on a property/railroad/utility that is unowned the player has the option to buy the property, but
     * if the property is owned they must pay rent to the owner.
     * At the end of their turn they must type the command pass to pass it to the next player.
     */
    public void nextTurn() {
        Piece currentPiece = pieces.get(currentPlayer);
        movePiece(currentPiece);
        String tileName = currentPiece.getPosition().getName();
        Tile currentTile = (pieces.get(currentPlayer).getPosition());
        int currentPlayerFunds = currentPiece.getFunds();
        if((currentTile instanceof Go)) {
            currentPiece.addFunds(Go.getReward());

            if (!currentPiece.isAIPlayer()) {
                for (GameView view : views) view.handleLandOnGo();
            } else {
                ((AIPiece) currentPiece).addTurnEvent("Landed on Go!");

                PieceEvent e = new PieceEvent(this);
                for (GameView view : views) view.handleAITurnEnd(e);
            }
        } else if(currentTile instanceof Jail) {
            if(!currentPiece.isInJail()) {
                if(board.getTilePosition(currentTile) - diceRoll < 0) {
                    currentPiece.addFunds(Go.getReward());

                    if (!currentPiece.isAIPlayer()) {
                        for (GameView view : views) view.handlePassedGo();
                    } else {
                        ((AIPiece) currentPiece).addTurnEvent("Passed Go!");
                    }
                }

                if (!currentPiece.isAIPlayer()) {
                    for (GameView view : views) view.handleVisitingJail();
                } else {
                    PieceEvent e = new PieceEvent(this);
                    ((AIPiece) pieces.get(currentPlayer)).addTurnEvent("Visiting Jail");
                    for (GameView view : views) view.handleAITurnEnd(e);
                }
            }
        } else if(currentTile instanceof SendJail) {
            currentPiece.setInJail(true);
            currentPiece.setTurnsInJail(0);
            currentPiece.updatePosition(board.getJailTile());

            if (!currentPiece.isAIPlayer()) {
                PieceEvent e = new PieceEvent(this);
                for (GameView view : views) view.handleSentToJail(e);
            } else {
                ((AIPiece) currentPiece).addTurnEvent("Sent to Jail");

                PieceEvent e = new PieceEvent(this);
                for (GameView view : views) view.handleAITurnEnd(e);
            }
        } else if(currentTile instanceof Ownable) {
            currentOwnable = (Ownable)pieces.get(currentPlayer).getPosition();
            boolean isTileOwned = currentOwnable.getOwned();
            int tilePrice = currentOwnable.getPrice();
            Piece tileOwner = currentOwnable.getOwner();
            double payRate = 0;
            if(tileOwner == currentPiece) {
                if (!currentPiece.isAIPlayer()) {
                    PropertyEvent e = new PropertyEvent(this);
                    for (GameView view : views) view.handleAlreadyOwnedProperty(e);
                } else {
                    ((AIPiece) currentPiece).addTurnEvent("Landed on own property: " + currentTile.getName());

                    PieceEvent e = new PieceEvent(this);
                    for (GameView view : views) view.handleAITurnEnd(e);
                }
            } else if(isTileOwned) {
                if(currentOwnable instanceof Property) {
                    payRate = currentOwnable.getPrice() * (PROPERTY_RENT + (((Property) currentOwnable).getHouses() * PROPERTY_RENT));
                } else if(currentOwnable instanceof Railroad) {
                    payRate = RAILROAD_RENT * tileOwner.getRailroadsOwned();
                } else if(currentOwnable instanceof Utility) {
                    if(tileOwner.getUtilitiesOwned() == 1) {
                        payRate = diceRoll * UTILITY_RENT_ONE;
                    } else if(tileOwner.getUtilitiesOwned() == 2) {
                        payRate = diceRoll * UTILITY_RENT_TWO;
                    }
                }
                if (!currentPiece.isAIPlayer()) {
                    MoneyEvent e = new MoneyEvent(this, tileOwner.getName(), payRate);
                    for (GameView view : views) view.handleMustPayRent(e);
                } else {
                    ((AIPiece) currentPiece).addTurnEvent("Paid rent to: " + tileOwner.getName());

                    PieceEvent e = new PieceEvent(this);
                    for (GameView view : views) view.handleAITurnEnd(e);
                }
                pay(currentPiece, tileOwner, payRate);
                if(currentPiece.isBankrupt()) {
                    if (!currentPiece.isAIPlayer()) {
                        MoneyEvent e = new MoneyEvent(this, tileOwner.getName(), payRate);
                        for (GameView view : views)
                            view.handleBankruptPlayer(e);
                    } else {
                        ((AIPiece) currentPiece).addTurnEvent("Went Bankrupt");

                        PieceEvent e = new PieceEvent(this);
                        for (GameView view : views) view.handleAITurnEnd(e);
                    }
                    pieces.get(currentPlayer).deallocateBankruptPiece();
                    int index = pieces.indexOf(pieces.get(currentPlayer));
                    pieces.remove(currentPiece);
                    maxPlayers -= 1;
                    if (index < currentPlayer)
                        currentPlayer--;
                }
            } else if(tilePrice <= currentPlayerFunds) {
                price = tilePrice;

                if (!currentPiece.isAIPlayer()) {
                    PropertyEvent e = new PropertyEvent(this);
                    for (GameView view : views)
                        view.handleUnownedProperty(e);
                } else {
                    ((AIPiece) currentPiece).addTurnEvent("Bought the property: " + tileName);
                    purchaseProperty();
                }

            } else {
                if (!currentPiece.isAIPlayer()) {
                    PropertyEvent e = new PropertyEvent(this);
                    for (GameView view : views) view.handleNotEnoughFundsProperty(e);
                } else {
                    ((AIPiece) currentPiece).addTurnEvent("Landed on: " + tileName);

                    PieceEvent e = new PieceEvent(this);
                    for (GameView view : views) view.handleAITurnEnd(e);
                }
            }
        }
    }

    /**
     * The purchaseProperty is called when a player decides to purchase a property, it calls on methods deductFunds
     * and addProperty from the piece class.
     * It also updates the property from unowned to owned.
     */
    public void purchaseProperty() {
        pieces.get(currentPlayer).deductFunds(price);
        pieces.get(currentPlayer).addTile(pieces.get(currentPlayer).getPosition());
        ((Ownable)pieces.get(currentPlayer).getPosition()).sold(pieces.get(currentPlayer));
        if(!pieces.get(currentPlayer).isAIPlayer()) {
            PropertyEvent e = new PropertyEvent(this);
            for (GameView view : views) {
                view.handlePurchasedProperty(e);
            }
        } else {
            for(GameView view : views) {
                PieceEvent e = new PieceEvent(this);
                view.handleAITurnEnd(e);
            }
        }
    }

    /**
     * The doNotPurchaseProperty is called when a player chooses to not buy a property.
     * It updates the game view.
     */
    public void doNotPurchaseProperty() {
        PropertyEvent e = new PropertyEvent(this);
        for(GameView view: views) view.handleDoNotPurchase(e);
    }

    /**
     * This method checks whether a player owns all the properties of the same color and if so gives them the
     * ability to buys houses or hotels on them.
     */
    public void purchaseHouse(){
        Piece currentPiece = pieces.get(currentPlayer);
        if(currentPiece.getOwnedTileList().contains(board.getSet1().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet1().get(1))){
            HouseEvent e = new HouseEvent(this, board.getSet1().get(0), board.getSet1().get(1));
            for(GameView view: views){view.handleCanBuyHouse1(e);}
            prop1 = (Property) board.getSet1().get(0);
            prop2 = (Property) board.getSet1().get(1);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet2().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet2().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet2().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet2().get(0),board.getSet2().get(1),board.getSet2().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
            prop1 = (Property) board.getSet2().get(0);
            prop2 = (Property) board.getSet2().get(1);
            prop3 = (Property) board.getSet2().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet3().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet3().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet3().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet3().get(0),board.getSet3().get(1),board.getSet3().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
            prop1 = (Property) board.getSet3().get(0);
            prop2 = (Property) board.getSet3().get(1);
            prop3 = (Property) board.getSet3().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet4().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet4().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet4().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet4().get(0),board.getSet4().get(1),board.getSet4().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
                prop1 = (Property) board.getSet4().get(0);
                prop2 = (Property) board.getSet4().get(1);
                prop3 = (Property) board.getSet4().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet5().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet5().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet5().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet5().get(0),board.getSet5().get(1),board.getSet5().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
            prop1 = (Property) board.getSet5().get(0);
            prop2 = (Property) board.getSet5().get(1);
            prop3 = (Property) board.getSet5().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet6().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet6().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet6().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet6().get(0),board.getSet6().get(1),board.getSet6().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
            prop1 = (Property) board.getSet6().get(0);
            prop2 = (Property) board.getSet6().get(1);
            prop3 = (Property) board.getSet6().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet7().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet7().get(1))&&
                currentPiece.getOwnedTileList().contains(board.getSet7().get(2))){
            HouseEvent e = new HouseEvent(this, board.getSet7().get(0),board.getSet7().get(1),board.getSet7().get(2));
            for(GameView view: views){view.handleCanBuyHouse2(e);}
            prop1 = (Property) board.getSet7().get(0);
            prop2 = (Property) board.getSet7().get(1);
            prop3 = (Property) board.getSet7().get(2);
        }else if(currentPiece.getOwnedTileList().contains(board.getSet8().get(0))&&currentPiece.getOwnedTileList().contains(board.getSet8().get(1))){
            HouseEvent e = new HouseEvent(this, board.getSet8().get(0), board.getSet8().get(1));
            for(GameView view: views){view.handleCanBuyHouse1(e);}
            prop1 = (Property) board.getSet8().get(0);
            prop2 = (Property) board.getSet8().get(1);
        } else{
            for(GameView view: views){view.handleCannotBuyHouse();}
        }
    }

    /**
     * Player is buying a house on the first property of a set.
     * They can buy up to 5 houses max which is equal to a hotel.
     * They cannot build after a hotel has been built.
     */
    public void buyHouse1(){
        checkHouse(prop1);
    }

    /**
     * Player is buying a house on the second property of a set.
     * They can buy up to 5 houses max which is equal to a hotel.
     * They cannot build after a hotel has been built.
     */
    public void buyHouse2(){
        checkHouse(prop2);
    }

    /**
     * Player is buying a house on the third property of a set.
     * They can buy up to 5 houses max which is equal to a hotel.
     * They cannot build after a hotel has been built.
     */
    public void buyHouse3() {
        checkHouse(prop3);
    }

    /**
     * Checks if player has enough funds to purchase a house on a set that they own.
     * Checks if there is already a hotel on the property because if so they cannot build more.
     * Checks if they successfully buy a house or a hotel (hotel == 5 houses).
     * @param prop The property the player is buying a house for.
     */
    private void checkHouse(Property prop) {
        if(pieces.get(currentPlayer).getFunds() < prop.getHousePrice()) {
            for(GameView view : views) {
                PieceEvent e = new PieceEvent(this);
                view.handleNotEnoughFundsHouse(e);
            }
        } else if(prop.getHouses() == 5) {
            PropertyEvent e = new PropertyEvent(this, prop);
            for (GameView view : views) {
                view.handleHotel(e);
            }
        } else {
            pieces.get(currentPlayer).deductFunds(prop.getHousePrice());
            prop.addHouse();
            PropertyEvent e = new PropertyEvent(this, prop);
            if(prop.getHouses() == 5) {
                for(GameView view : views) {
                    view.handlePurchasedHotel(e);
                }
            } else {
                for(GameView view : views) {
                    view.handlePurchasedHouse(e);
                }
            }
        }
    }

    /**
     * Subtracts 50M from the players funds and releases them from jail.
     */
    public void payFine(){
        pieces.get(currentPlayer).deductFunds(BAIL);
        pieces.get(currentPlayer).setInJail(false);
        pieces.get(currentPlayer).setTurnsInJail(0);

        if (!pieces.get(currentPlayer).isAIPlayer()) {
            for (GameView view : views) {
                PieceEvent e = new PieceEvent(this);
                view.handleReleasedFromJail(e);
            }
        } else {
            ((AIPiece) pieces.get(currentPlayer)).addTurnEvent("Paid to Get out of Jail");
            roll();
        }
    }

    /**
     * The pay method is called when a player lands on an owned property.
     * They must pay the owner the rent of the property.
     *
     * @param payee The player that must pay rent.
     * @param recipient The player that is taking the rent.
     * @param rent How much the rent costs.
     */
    public void pay(Piece payee, Piece recipient, double rent) {
        payee.deductFunds(rent);
        recipient.addFunds(rent);
    }

    /**
     * Called when save game button is pressed.
     */
    public void saveGameButton(){
        for(GameView view:views){view.handleSaveGameButton();}
    }

    /**
     * Saves an instance of Game using Serialization.
     * @param name String representing file name.
     */
    public void saveGame(String name){
        try {
            FileOutputStream fileOut = new FileOutputStream(name + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }catch (IOException i){
            i.printStackTrace();
        }
        endGame();
    }

    /**
     * Loads a new instance of Game using Serialization.
     * @param name String representing file name
     * @return Game object
     */
    public static Game loadGame(String name){
        Game g = null;
        try {
            FileInputStream fileIn = new FileInputStream(name + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            g = (Game) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException | ClassNotFoundException i){
            i.printStackTrace();
        }
        return g;
    }

    /**
     * Deals with creating a board object from XML based on version selected.
     * @param versionNum Integer representing version selected
     * @return Board object
     * @throws Exception in case of parser error
     */
    public static Board importFromXmlFile(Integer versionNum) throws Exception {
        File f = null;
        Board b = new Board();
        if(versionNum == 0) {
            //US Original Version
            return(b);
        } else if(versionNum == 1) {
            //Canada Version
            f = new File(CANADA_XML);
            b.clearBoard();
        } else if(versionNum == 2) {
            //UK Version
            f = new File(UK_XML);
            b.clearBoard();
        }
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();

        DefaultHandler dh = new DefaultHandler() {
            boolean isProperty, isPName, isPOwned, isPPrice, isPHousePrice, isGo, isGReward, isRailroad, isRName, isRPrice,
            isROwned, isUtility, isUName, isUPrice, isUOwned;
            String nameP, nameR, nameU;
            boolean ownedP, ownedR, ownedU;
            Integer priceP, pricePH, priceR, priceU, rewardG;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if(qName.equals("Property")) {
                    isProperty = true;
                    nameP = null;
                    priceP = null;
                    pricePH = null;
                }
                if(qName.equals("Railroad")) {
                    isRailroad = true;
                    nameR = null;
                    priceR = null;
                }
                if(qName.equals("Utility")) {
                    isUtility = true;
                    nameU = null;
                    priceU = null;
                }
                if(qName.equals("Go")) {
                    isGo = true;
                    rewardG = null;
                }
                if(qName.equals("SendJail")) {
                    SendJail s = new SendJail();
                    b.addTile(s);
                }
                if (qName.equals("Jail")) {
                    Jail j = new Jail();
                    b.addTile(j);
                }
                if(qName.equals("Parking")) {
                    Parking pa = new Parking();
                    b.addTile(pa);
                }
                if(qName.equals("PropertyName")) { isPName = true; }
                if(qName.equals("PropertyOwned")) { isPOwned = true; }
                if(qName.equals("PropertyPrice")) { isPPrice = true; }
                if(qName.equals("HousePrice")) { isPHousePrice = true; }
                if(qName.equals("RailroadName")) { isRName = true; }
                if(qName.equals("RailroadOwned")) { isROwned = true; }
                if(qName.equals("RailroadPrice")) { isRPrice = true; }
                if(qName.equals("UtilityName")) { isUName = true; }
                if(qName.equals("UtilityOwned")) { isUOwned = true; }
                if(qName.equals("UtilityPrice")) { isUPrice = true; }
                if(qName.equals("Reward")) { isGReward = true; }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if(qName.equals("Property")) {
                    Property p = new Property(nameP, ownedP, priceP, pricePH);
                    b.addTile(p);
                }
                if(qName.equals("Railroad")) {
                    Railroad r = new Railroad(nameR, priceR, ownedR);
                    b.addTile(r);
                }
                if(qName.equals("Utility")) {
                    Utility u = new Utility(nameU, priceU, ownedU);
                    b.addTile(u);
                }
                if(qName.equals("Go")) {
                    Go g = new Go(rewardG);
                    b.addTile(g);
                }
                if(qName.equals("PropertyName")) { isPName = false; }
                if(qName.equals("PropertyOwned")) { isPOwned = false; }
                if(qName.equals("PropertyPrice")) { isPPrice = false; }
                if(qName.equals("HousePrice")) { isPHousePrice = false; }
                if(qName.equals("RailroadName")) { isRName = false; }
                if(qName.equals("RailroadOwned")) { isROwned = false; }
                if(qName.equals("RailroadPrice")) { isRPrice = false; }
                if(qName.equals("UtilityName")) { isUName = false; }
                if(qName.equals("UtilityOwned")) { isUOwned = false; }
                if(qName.equals("UtilityPrice")) { isUPrice = false; }
                if(qName.equals("Reward")) { isGReward = false; }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                if(isPName) { nameP = new String(ch, start, length); }
                if(isRName) { nameR = new String(ch, start, length); }
                if(isUName) { nameU = new String(ch, start, length); }
                if(isPOwned) { ownedP = Boolean.parseBoolean(new String(ch, start, length)); }
                if(isROwned) { ownedR = Boolean.parseBoolean(new String(ch, start, length)); }
                if(isUOwned) { ownedU = Boolean.parseBoolean(new String(ch, start, length)); }
                if(isPPrice) { this.priceP = Integer.parseInt(new String(ch, start, length)); }
                if(isPHousePrice) { pricePH = Integer.parseInt(new String(ch, start, length)); }
                if(isRPrice) { priceR = Integer.parseInt(new String(ch, start, length)); }
                if(isUPrice) { priceU = Integer.parseInt(new String(ch, start, length)); }
                if(isGReward) { rewardG = Integer.parseInt(new String(ch, start, length)); }
            }
        };
        s.parse(f, dh);
        b.createSets();
        return(b);
    }

    /**
     * This method tells the view to end the game.
     */
    public void endGame() {
        for(GameView view: views){view.handleEndGame();}
    }

    /**
     * Getter method for Board object.
     * @return Board object.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Setter method for Board object.
     * @param b Board object
     */
    public void setBoard(Board b) {
        this.board = b;
    }

    /**
     * Getter method for current property object.
     * @return Property object.
     */
    public Ownable getCurrentProperty() {
        return this.currentOwnable;
    }

    /**
     * Getter for current player integer.
     * @return Integer representing current player (piece).
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Getter for Piece object in list.
     * @param pieceNumber Integer representing index of piece object in list.
     * @return Piece object.
     */
    public Piece getPieces(int pieceNumber) {
        return this.pieces.get(pieceNumber);
    }

    /**
     * Getter for dice roll integer.
     * @return Integer representing the total dice roll.
     */
    public int getDiceRoll() {
        return this.diceRoll;
    }

    /**
     * Getter for double rolled boolean.
     * @return Boolean value if doubles have been rolled.
     */
    public boolean getDoublesRolled(){
        return this.doublesRolled;
    }

    /**
     * Getter for double rolled counter integer.
     * @return Integer representing how many doubles have been rolled.
     */
    public int getDoubleCounter(){
        return this.doubleCounter;
    }

    /**
     * Getter for price integer.
     * @return Integer representing price of a specific tile.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Getter for property one of a set.
     * @return property one of a set.
     */
    public Tile getProp1(){return this.prop1;}

    /**
     * Getter for property two of a set.
     * @return property two of a set.
     */
    public Tile getProp2(){return this.prop2;}

    /**
     * Getter for property three of a set.
     * @return property three of a set.
     */
    public Tile getProp3(){return this.prop3;}

    /**
     * Getter for the price of bailing out of Jail.
     * @return the price to bail out of jail.
     */
    public int getBail(){return this.BAIL;}
}
