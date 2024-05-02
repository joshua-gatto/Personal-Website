import java.awt.event.*;

/**
 * SYSC 3110 Monopoly - Milestone 2 GameController Controller Class
 * GameController is the Controller class for the game; handles all user control action listeners.
 *
 * @author Frank Dow - 101140402
 * @version 3.0
 * @since Nov 22nd, 2021
 */

public class GameController implements ActionListener {

    /**
     * The model game.
     */
    private Game game;

    /**
     * Constructor to initialize game object.
     * @param game Game object representing the current game of monopoly.
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * Overrides all ActionEvents for user controls: Check State, Pass Turn, Roll Dice, Do or Do Not Purchase, and Quit.
     * @param e ActionEvent object for passed event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "state":
                game.printState();
                break;
            case "pass":
                game.updatePlayer();
                break;
            case "roll":
                game.roll();
                break;
            case "yes":
                game.purchaseProperty();
                break;
            case "no":
                game.doNotPurchaseProperty();
                break;
            case "quit":
                game.endGame();
                break;
            case "purchaseHouse":
                game.purchaseHouse();
                break;
            case "buyingHouse1":
                game.buyHouse1();
                break;
            case "buyingHouse2":
                game.buyHouse2();
                break;
            case "buyingHouse3":
                game.buyHouse3();
                break;
            case "payFine":
                game.payFine();
                break;
            case "saveGame":
                game.saveGameButton();
                break;
        }
    }
}
