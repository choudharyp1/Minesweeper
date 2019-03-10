import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: need to add bombs to model and the board.
//TODO: Fix the image displays.

public class Main {
    public static void main(String [] args){
        //Main Frame of the application.
        JFrame frame = new JFrame("Minesweeper");

        int xCells = 20;
        int yCells = 20;

        //MODEL
        Model model = new Model(xCells,yCells, 40);

        //BOARD VIEW
        BoardView boardView = new BoardView(model);
        model.addView(boardView);

        /*
        -----------------------------------MENU BAR--------------------------------------------------
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("New Game");
        JMenu loadGameMenu = new JMenu("Load Game");
        menuBar.add(newGameMenu);
        menuBar.add(loadGameMenu);
        JMenuItem easyItem = new JMenuItem("Easy (5 x 5 tiles, 4 bombs)");
        JMenuItem mediumItem = new JMenuItem("Medium (10 x 10 tiles, 20 bombs)");
        JMenuItem hardItem = new JMenuItem("Hard (13 x 13 tiles, 60 bombs)");
        JMenuItem expertItem = new JMenuItem("Hard (16 x 30 tiles, 120 bombs)");
        newGameMenu.add(easyItem);
        newGameMenu.add(mediumItem);
        newGameMenu.add(hardItem);
        newGameMenu.add(expertItem);
        frame.setJMenuBar(menuBar);

        /*
        // TODO: ACTION LISTENERS NEEDED TO BE CONFIGURED
        ----------------------------------END OF MENU BAR----------------------------------------------
         */

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(boardView);

        frame.setMinimumSize(new Dimension(model.CELL_SIZE * xCells, model.CELL_SIZE * yCells));
        frame.setPreferredSize(new Dimension(model.CELL_SIZE * xCells, model.CELL_SIZE * yCells));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setVisible(true);

    }

}
