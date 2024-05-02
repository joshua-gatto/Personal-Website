import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * SYSC 3110 Monopoly - Milestone 2 GameFrame View Class
 * GameFrame is the View class for the game; handles all visual GUI aspects.
 *
 * @author Frank Dow - 101140402, Jeremy Trendoff - 101160306
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class GameFrame extends JFrame implements GameView{

    /**
     * The text area to display the game.
     */
    private final JTextArea playerMessage;

    /**
     * JButtons for purchasing houses or hotels.
     */
    private final JButton purchaseHouse, prop1, prop2, prop3, pass2, saveGame;
    private Game newGame;
    /**
     * JPanel for purchasing houses or hotels.
     */
    private final JPanel propertyPanel;

    /**
     * JButtons for player behaviour.
     */
    private final JButton roll, checkState, passTurn, quit, payFine;

    /**
     * JPanels for player actions and buying actions.
     */
    private final JPanel playerPanel, buyPanel;

    /**
     * Constructor to initialize all components of the GUI.
     */
    public GameFrame() throws Exception {
        super("Monopoly!");
        String prevGame;
        this.setLayout(new BorderLayout());
        newGame = new Game(0,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);

        Object[] options = {"Load Game","Start a new Game"};
        JPanel newPanel = new JPanel();
        newPanel.add(new JLabel("Welcome to Monopoly! Would you like to load a previous game or start a new one?"));
        int result = JOptionPane.showOptionDialog(null, newPanel, "Monopoly",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        JTextField textField = new JTextField(20);
        if (result == JOptionPane.YES_OPTION){
            prevGame = JOptionPane.showInputDialog(null,"Please enter a file name you would like to load:");
            newGame = Game.loadGame(prevGame);
            newGame.addGameView(this);
            this.repaint();
        } else {
            String[] choices = new String[] {"US version", "Canada version", "UK version"};
            int versionNum = JOptionPane.showOptionDialog(null, "Select version of the game:", null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);


            String playerInput = JOptionPane.showInputDialog(this, "How many players will be playing?");

            if (playerInput == null) {
                JOptionPane.showMessageDialog(this, "Thanks for Playing!");
                System.exit(0);
            }

            int playerNumbers = 0;

            try {
                playerNumbers = Integer.parseInt(playerInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Inputs must be Integers!");
            }

            while (playerNumbers < 1 || playerNumbers > 6) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 1-6.");

                try {
                    playerNumbers = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players will be playing?"));
                } catch (NumberFormatException e) {
                    playerNumbers = 0;
                    JOptionPane.showMessageDialog(this, "Inputs must be Integers!");
                }
            }

            int AIPlayers = 0;

            if (playerNumbers < 6) {
                String AIPlayerMessage;

                if (playerNumbers == 1) {
                    AIPlayerMessage = "How many AI players would you like? Enter a number from 1 - 5";
                } else {
                    AIPlayerMessage = "How many AI players would you like? Enter a number from 0 - " + (6 - playerNumbers);
                }

                AIPlayers = -1;

                while (AIPlayers == -1) {
                    try {
                        playerInput = JOptionPane.showInputDialog(this, AIPlayerMessage);

                        if (playerInput == null) {
                            JOptionPane.showMessageDialog(this, "Thanks for Playing!");
                            System.exit(0);
                        }

                        AIPlayers = Integer.parseInt(playerInput);

                        if (AIPlayers < 1 && playerNumbers == 1) {
                            AIPlayers = -1;
                            JOptionPane.showMessageDialog(this, "Please enter a number between 1-5.");
                        } else if (AIPlayers > (6 - playerNumbers)) {
                            if (playerNumbers == 1) {
                                JOptionPane.showMessageDialog(this, "Please enter a number between 1-5.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Please enter a number between 0-" + (6 - playerNumbers) + ".");
                            }
                            AIPlayers = -1;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Inputs must be Integers!");
                    }
                }
            }
            newGame = new Game(playerNumbers, AIPlayers);
            newGame.setBoard(Game.importFromXmlFile(versionNum));
            newGame.addGameView(this);

        }

        JButton buyTile, doNotBuyTile;

        playerMessage = new JTextArea("");

        buyTile = new JButton("Buy Property");
        doNotBuyTile = new JButton("Do not buy Property");
        roll = new JButton("Roll");
        purchaseHouse = new JButton("Purchase House/Hotel");
        checkState = new JButton("Check Your State");
        passTurn = new JButton("Pass Turn");
        quit = new JButton("Quit Game");
        prop1 = new JButton("");
        prop2 = new JButton("");
        prop3 = new JButton("");
        saveGame = new JButton("Save Game");
        pass2 = new JButton("Pass Turn");
        payFine = new JButton("Pay Fine");
        propertyPanel = new JPanel();
        playerPanel = new JPanel();
        buyPanel = new JPanel();

        buyPanel.add(buyTile);
        buyPanel.add(doNotBuyTile);

        propertyPanel.add(prop1);
        propertyPanel.add(prop2);
        propertyPanel.add(prop3);
        propertyPanel.add(pass2);

        playerPanel.add(roll);
        playerPanel.add(checkState);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        playerPanel.add(purchaseHouse);

        this.add(playerMessage, BorderLayout.CENTER);
        this.add(playerPanel, BorderLayout.SOUTH);

        newGame.welcomeMessage();
        GameController gc = new GameController(newGame);

        buyTile.addActionListener(gc);
        doNotBuyTile.addActionListener(gc);
        roll.addActionListener(gc);
        quit.addActionListener(gc);
        checkState.addActionListener(gc);
        passTurn.addActionListener(gc);
        purchaseHouse.addActionListener(gc);
        prop1.addActionListener(gc);
        prop2.addActionListener(gc);
        prop3.addActionListener(gc);
        saveGame.addActionListener(gc);
        pass2.addActionListener(gc);
        payFine.addActionListener(gc);

        buyTile.setActionCommand(String.valueOf(Game.State.yes));
        doNotBuyTile.setActionCommand(String.valueOf(Game.State.no));
        roll.setActionCommand(String.valueOf(Game.State.roll));
        checkState.setActionCommand(String.valueOf(Game.State.state));
        passTurn.setActionCommand(String.valueOf(Game.State.pass));
        quit.setActionCommand(String.valueOf(Game.State.quit));
        purchaseHouse.setActionCommand(String.valueOf(Game.State.purchaseHouse));
        prop1.setActionCommand(String.valueOf(Game.State.buyingHouse1));
        prop2.setActionCommand(String.valueOf(Game.State.buyingHouse2));
        prop3.setActionCommand(String.valueOf(Game.State.buyingHouse3));
        saveGame.setActionCommand(String.valueOf(Game.State.saveGame));
        pass2.setActionCommand(String.valueOf(Game.State.pass));
        payFine.setActionCommand(String.valueOf(Game.State.payFine));

        this.setVisible(true);
    }

    /**
     * Prints welcome message given each Piece object (player).
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleWelcomeMessage(PieceEvent e) {
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText("Welcome to monopoly!\n"+p.getName()+" you are first! Please select an option.");
        playerPanel.remove(passTurn);
        playerPanel.remove(purchaseHouse);
    }
    @Override
    public void handleSaveGameButton(){
        String name = JOptionPane.showInputDialog("Please enter a name you would like to save your game as:");
        newGame.saveGame(name);
    }
    /**
     * Prints Piece object (player) state including current position, funds, and owned tiles.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handlePlayerState(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText("You are on "+p.getPosition().getName()+".\nYou have "+p.getFunds()+" dollars.\nYou own: " + p.printOwnedTiles());
    }

    /**
     * Prints winner message for the decided Piece object (player).
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleWinner(PieceEvent e){
        Piece p = e.getWinningPiece();
        JOptionPane.showMessageDialog(this,"Congratulations " + p.getName() + "!! You are the winner!");
        dispose();
    }

    /**
     * Prints Piece object (player) turn message and adds necessary frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handlePlayerTurn(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText("Hello " + p.getName() + " it is your turn, would you like to roll or check state?");
        this.remove(purchaseHouse);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.add(roll);
        playerPanel.add(checkState);
        playerPanel.remove(passTurn);
        playerPanel.remove(payFine);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints Piece object (player) re-roll turn message for a double roll and configures frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleDoublesRolled(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+ " since you have rolled doubles you may roll again!");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(saveGame);
        playerPanel.remove(purchaseHouse);
        playerPanel.add(roll);
        playerPanel.add(checkState);
        playerPanel.remove(passTurn);
        playerPanel.remove(payFine);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints Piece object (player) triple doubles message and configures frame for user control.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleTripleDoubles(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+ " since you have rolled doubles three times you will be sent to Jail!\nPlease pass your turn to the next player.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.remove(payFine);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.add(passTurn);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) lands on self-owned property and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handleAlreadyOwnedProperty(PropertyEvent e){
        String propertyName = e.getCurrentTile().getName();
        playerMessage.setText("You have landed on " + propertyName + "!\n You already own this property!\nNow you may check state, pass your turn to the next player\n or check if you can purchase a house.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(purchaseHouse);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) lands on another Piece object (player) owned tile and configures frame for user control.
     * @param e MoneyEvent, the object representing the current game event.
     */
    @Override
    public void handleMustPayRent(MoneyEvent e){
        String player = e.getTileOwner();
        double rent = e.getPayRate();

        playerMessage.setText("You must pay " + player + ", the owner of this property, " + rent + " dollars.\nNow you may check state or pass your turn to the next player\nor check if you can purchase a house.");
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(purchaseHouse);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) has gone bankrupt and configures frame for user control.
     * @param e MoneyEvent, the object representing the current game event.
     */
    @Override
    public void handleBankruptPlayer(MoneyEvent e){
        Piece p = e.getCurrentPiece();
        String owner = e.getTileOwner();
        double rent = e.getPayRate();

        playerMessage.setText("Sorry "+p.getName()+"! You have gone bankrupt because you have landed on "+owner+"'s property and owe "+rent+".\nYou have been removed from the game.");
        this.remove(propertyPanel);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(roll);
        playerPanel.remove(checkState);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) does not have the necessary funds to purchase the tile landed on.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handleNotEnoughFundsProperty(PropertyEvent e){
        Piece p = e.getCurrentPiece();
        Ownable prop = (Ownable) e.getCurrentTile();
        playerMessage.setText("Sorry "+p.getName()+"! You do not have enough funds to purchase "+prop.getName()+"!\nNow you may check state or pass your turn to the next player\nor check if you can purchase a house.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }
    /**
     * Prints message when Piece object (player) does not have the necessary funds to purchase a house on their set of properties.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleNotEnoughFundsHouse(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText("Sorry "+p.getName()+"! You do not have enough funds to buy another house!\nNow you may check state or pass your turn to the next player\nor check if you can purchase a house.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) lands on a tile that is not owned by another Piece object (player) and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handleUnownedProperty(PropertyEvent e){
        String p = e.getCurrentTile().getName();
        int price = ((Ownable) e.getCurrentTile()).getPrice();
        int funds = e.getCurrentPiece().getFunds();

        playerMessage.setText("Would you like to buy " + p + "? It costs M" + price + " and you have M"
                + funds + ".");
        this.remove(propertyPanel);
        this.remove(playerPanel);
        this.add(buyPanel, BorderLayout.SOUTH);
        this.repaint();

    }

    /**
     * Prints message when Piece object (player) purchases the property they have landed on and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handlePurchasedProperty(PropertyEvent e){
        Ownable p = (Ownable) e.getCurrentTile();
        playerMessage.setText("Congratulations! You have purchased "+p.getName()+"!\nNow you may check state, pass your turn to the next player \nor check to see if you can purchase a house.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(purchaseHouse);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();

    }

    /**
     * Prints a message to the player saying they are able to purchase a house for a set of two properties
     * and configures frame for user control.
     * @param e HouseEvent, object representing the current game event.
     */
    @Override
    public void handleCanBuyHouse1(HouseEvent e){
        Tile p1 = e.getTile1();
        Tile p2 = e.getTile2();
        playerMessage.setText("You are able to purchase a house/hotel!(5 houses = hotel)\n" +
                "The price of a house for these properties are "+((Property)(p1)).getHousePrice()+".\n"+
                "Which property would you like to buy a house for?");
        this.remove(playerPanel);
        this.add(propertyPanel,BorderLayout.SOUTH);
        prop1.setText(p1.getName());
        prop2.setText(p2.getName());
        propertyPanel.remove(prop3);
        this.repaint();
    }

    /**
     * Prints a message to the player saying they are able to purchase a house for a set of two properties
     *      * and configures frame for user control.
     * @param e HouseEvent, object representing the current game event.
     */
    @Override
    public void handleCanBuyHouse2(HouseEvent e){
        Tile p1 = e.getTile1();
        Tile p2 = e.getTile2();
        Tile p3 = e.getTile3();
        playerMessage.setText("You are able to purchase a house/hotel!(5 houses = hotel)\n" +
                "The price of a house for these properties are "+((Property)(p1)).getHousePrice()+".\n"+
                "Which property would you like to buy a house for?");
        this.remove(playerPanel);
        this.add(propertyPanel,BorderLayout.SOUTH);
        propertyPanel.remove(pass2);
        prop1.setText(p1.getName());
        prop2.setText(p2.getName());
        prop3.setText(p3.getName());
        propertyPanel.add(prop3);
        propertyPanel.add(pass2);
        this.repaint();
    }

    /**
     * When a player successfully purchases a house this outputs a message to the player.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handlePurchasedHouse(PropertyEvent e){
        Property p = e.getSpecifiedProperty();
        playerMessage.setText("Congratulations you have purchased a house on "+p.getName()+"!"+
                "\n"+p.getName()+" now has "+p.getHouses()+" on it!\nWould you like to buy another house or pass your turn?");
        this.repaint();
    }

    /**
     * When a player successfully purchases a hotel this outputs a message to the player.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handlePurchasedHotel(PropertyEvent e){
        Property p = e.getSpecifiedProperty();
        playerMessage.setText("Congratulations you have purchased a hotel on "+p.getName()+"!"+
                "\nWould you like to buy another house or pass your turn?");
        this.repaint();
    }

    /**
     * If a player tries to build on a property where there is already a hotel, this will output a message.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handleHotel(PropertyEvent e){
        Property p = e.getSpecifiedProperty();
        playerMessage.setText(p.getName()+" already has a hotel on it!\n You cannot build anymore here, would you like to buy another house elsewhere or pass your turn?");
        this.repaint();
    }

    /**
     * If a player does not have a matching set and attempt to buy houses this message will be output.
     * Configures the frame for user control.
     */
    @Override
    public void handleCannotBuyHouse(){
        playerMessage.setText("You are not able to buy houses yet!\nPlease check your state or pass your turn to the next player.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(payFine);
        playerPanel.remove(purchaseHouse);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints message when Piece object (player) decides not to purchase the property they have landed on and configures frame for user control.
     * @param e PropertyEvent, the object representing the current game event.
     */
    @Override
    public void handleDoNotPurchase(PropertyEvent e){
        Ownable p = (Ownable) e.getCurrentTile();
        playerMessage.setText("You have not purchased "+p.getName()+"!\nNow you may check state, pass your turn to the next player \nor check to see if you can purchase a house.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(saveGame);
        playerPanel.remove(quit);
        playerPanel.remove(payFine);
        playerPanel.add(passTurn);
        playerPanel.add(purchaseHouse);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Prints message when the game is called to quit by any user.
     */
    @Override
    public void handleEndGame(){
        JOptionPane.showMessageDialog(this,"You have decided to quit the game. Thanks for playing!");
        dispose();
    }

    /**
     * Prints message when a player is sent to Jail and telling them the options on how to get out of Jail and configures frame for user control
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleSentToJail(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+", you have been sent Jail! \nYou must roll doubles to be released, or pay a fine of 50M.\nIf you do not roll doubles within your next " + (3 - p.getTurnsInJail()) + "  turns you must pay the fine!");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.add(passTurn);
        playerPanel.remove(payFine);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Gives a player a message when they pay their bail and are released from jail and configures frame for user control
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleReleasedFromJail(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+" you have been released from jail! You may now roll or check your state.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.add(roll);
        playerPanel.add(checkState);
        playerPanel.add(purchaseHouse);
        playerPanel.remove(passTurn);
        playerPanel.remove(payFine);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Tells the player that they are still in jail and how many more chances they have to roll doubles and configures frame for user control.
     * Configures the
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleInJail(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+ ", you are in Jail.\n You have " + (3 - p.getTurnsInJail()) + " more turns to roll doubles or pay a fine of 50M.\nPlease Roll or pay the fine.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.add(roll);
        playerPanel.add(payFine);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.remove(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * If a player has failed to roll doubles three times while in jail this message will be output and configures frame for user control.
     * @param e PieceEvent object representing the player event..
     */
    public void handleMustPayFine(PieceEvent e){
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+ ", you have not rolled doubles three times and must a fine of 50M.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.add(payFine);
        playerPanel.remove(saveGame);
        playerPanel.remove(quit);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.remove(passTurn);
        playerPanel.add(saveGame);
        playerPanel.remove(quit);
        this.repaint();
    }

    /**
     * When a player tries to roll doubles and is unsuccessful and configures frame for user control.
     * However, if a player is successful their turn continues as normal.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleStillInJail(PieceEvent e) {
        Piece p = e.getCurrentlyActivePiece();
        playerMessage.setText(p.getName()+ ", you have not rolled doubles and are still in Jail!\n You have " + (3 - p.getTurnsInJail()) + " more turns to roll doubles or pay a fine of 50M.\nPlease pass your turn.");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(payFine);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.add(passTurn);
        playerPanel.remove(saveGame);
        playerPanel.remove(quit);
        this.repaint();
    }

    /**
     * Sends a message to the player saying that the have landed on the jail space, but are "just visiting" and configures frame for user control.
     */
    @Override
    public void handleVisitingJail(){
        playerMessage.setText("You are visiting Jail. You may pass your turn");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(quit);
        playerPanel.remove(saveGame);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * Sends a message to the user that they have landed on go and configures frame for user control.
     */
    @Override
    public void handleLandOnGo(){
        playerMessage.setText("You are on Go, collect M" + Go.getReward()+ ". You may pass your turn");
        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(saveGame);
        playerPanel.remove(quit);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * When a player passes GO, there will be a pop up box to show them they have earned 200M for passing GO.
     */
    @Override
    public void handlePassedGo() {
        JOptionPane.showMessageDialog(this,"You passed go, collect M" + Go.getReward());
    }

    /**
     * Handle an AI turn finishing.
     * @param e PieceEvent object representing the player event.
     */
    @Override
    public void handleAITurnEnd(PieceEvent e) {
        Piece piece = e.getCurrentlyActivePiece();
        StringBuilder sb = new StringBuilder();

        if (piece.isAIPlayer()) {
            sb.append(piece.getName()).append("'s turn has finished.");
            sb.append("\n");
            sb.append("\n").append(piece.getName()).append("'s turn events were:\n");
            sb.append(((AIPiece) piece).printTurnEvents());
            sb.append("\n");
            sb.append(piece.getName()).append(" is on ").append(piece.getPosition().getName()).append("\n");
            sb.append(piece.getName()).append(" has M").append(piece.getFunds()).append("\n\n");
            sb.append(piece.getName()).append("'s owned tiles are: ");
            sb.append(piece.printOwnedTiles());
            ((AIPiece) piece).clearTurnEvents();
        } else {
            sb.append("Something went wrong!");
        }

        playerMessage.setText(sb.toString());

        this.remove(buyPanel);
        this.remove(propertyPanel);
        this.add(playerPanel, BorderLayout.SOUTH);
        playerPanel.remove(roll);
        playerPanel.remove(saveGame);
        playerPanel.remove(quit);
        playerPanel.remove(purchaseHouse);
        playerPanel.remove(checkState);
        playerPanel.add(passTurn);
        playerPanel.add(saveGame);
        playerPanel.add(quit);
        this.repaint();
    }

    /**
     * The main method. Runs the program.
     */
    public static void main(String[]args) throws Exception {
        new GameFrame();
    }
}