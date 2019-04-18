import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static JFrame frame = new JFrame("Minesweeper");
    static Container contentPane = frame.getContentPane();

    public static void main(String [] args){
        //Main Frame of the application.

        int xCells = 20;
        int yCells = 20;

        //MODEL
        Model model = new Model(xCells,yCells, 40);

        //BOARD VIEW
        BoardView boardView = new BoardView(model);
        model.addView(boardView);


        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(boardView);

        frame.setMinimumSize(new Dimension(model.CELL_SIZE * xCells, model.CELL_SIZE * yCells));
        frame.setPreferredSize(new Dimension(model.CELL_SIZE * xCells, model.CELL_SIZE * yCells));

        /*
        -----------------------------------MENU BAR--------------------------------------------------
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("New Game");
        menuBar.add(newGameMenu);
        JMenuItem easyItem = new JMenuItem("Easy (5 x 5 tiles, 5 bombs)");
        easyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.createBoard(5 , 5, 5);
            }
        });

        JMenuItem mediumItem = new JMenuItem("Medium (10 x 10 tiles, 20 bombs)");
        mediumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.createBoard(10,10, 20);
            }
        });


        JMenuItem hardItem = new JMenuItem("Hard (20 x 20 tiles, 40 bombs)");
        hardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.createBoard(20, 20, 40);
            }
        });

        newGameMenu.add(easyItem);
        newGameMenu.add(mediumItem);
        newGameMenu.add(hardItem);


        frame.setJMenuBar(menuBar);

        /*
        ----------------------------------END OF MENU BAR----------------------------------------------
         */


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setVisible(true);

    }


    /*
    Destroy board
    Create new Model
    Create new BoardView
    Recreate board.
     */
    public static void createBoard(int x, int y, int numOfBombs){
        frame.setVisible(false);
        Model newModel = new Model(x, y, numOfBombs);
        BoardView newBoardView = new BoardView(newModel);
        newModel.addView(newBoardView);
        contentPane.remove(0);
        contentPane.add(newBoardView);
        frame.setMinimumSize(new Dimension(newModel.CELL_SIZE * x, newModel.CELL_SIZE * y));
        frame.setPreferredSize(new Dimension(newModel.CELL_SIZE * x, newModel.CELL_SIZE * y));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

}
