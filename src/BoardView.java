import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

enum cellState {
    MINE,
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    FLAG
}

public class BoardView extends JPanel implements IView {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private Model model;
    int tiles_x;
    int tiles_y;
    JButton[][] tiles;
    cellState[][] tileState;
    boolean[][] tileValue;
    HashMap<String, Integer> bombCoordinates;

    JPanel panel = this;

    public BoardView(Model model) {
        this.model = model;
        tiles_x = model.getTiles_x();
        tiles_y = model.getTiles_y();
        tiles = new JButton[tiles_x][tiles_y];
        tileState = new cellState[tiles_x][tiles_y];

        setPreferredSize(new Dimension(tiles_x * model.CELL_SIZE, tiles_y * model.CELL_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridLayout(tiles_x, tiles_y));

        bombCoordinates = generateRandomPairs(model.getNumberOfBombs());
        tileValue = new boolean[tiles_x][tiles_y];

        for (int i = 0; i < tiles_x; i++) {
            for (int j = 0; j < tiles_y; j++) {
                tileValue[i][j] = false;
                tileState[i][j] = cellState.ZERO;
                tiles[i][j] = createTileButton(i, j);
                add(tiles[i][j]);
            }
        }

        for (Map.Entry<String, Integer> entry : bombCoordinates.entrySet()) {
            String s = entry.getKey();
            String[] ss = s.split(",");
            int x = Integer.parseInt(ss[0]);
            int y = entry.getValue();
            System.out.println(s);
            tileState[x][y] = cellState.MINE;
        }

        calculateAdjacentTiles();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < tiles_x; i++) {
            for (int j = 0; j < tiles_y; j++) {
                if (tileValue[i][j] == false) {
                    ImageIcon icon = new ImageIcon("Images/MINESWEEPER_X.png");
                    tiles[i][j].setIcon(icon);
                } else {
                    if (tileState[i][j] == cellState.ZERO) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_0.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.MINE) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_M.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.ONE) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_1.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.TWO) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_2.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.THREE) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_3.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.FOUR) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_4.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.FIVE) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_5.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.SIX) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_6.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.SEVEN) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_7.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.EIGHT) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_8.png");
                        tiles[i][j].setIcon(icon);
                    } else if (tileState[i][j] == cellState.FLAG) {
                        ImageIcon icon = new ImageIcon("Images/MINESWEEPER_M.png");
                        tiles[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    private JButton createTileButton(int x, int y) {
        JButton button = new JButton();
        button.setName(x + "," + y);
        button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String s = button.getName();
                String[] ss = s.split(",");
                int x = Integer.parseInt(ss[0]);
                int y = Integer.parseInt(ss[1]);
                if (tileState[x][y] == cellState.MINE) {
                    tileValue[x][y] = true;
                    bombExploded();
                    int value = JOptionPane.showConfirmDialog(panel,
                            "You Lost! Do you want to play again?");
                } else {
                    openTiles(x, y);
                }
                model.notifyObservers();
            }
        });
        return button;
    }

    private void bombExploded() {
        for (int i = 0; i < tileValue.length; i++) {
            for (int j = 0; j < tileValue[i].length; j++) {
                if (tileState[i][j] == cellState.MINE) {
                    tileValue[i][j] = true;

                }
                tiles[i][j].removeMouseListener(tiles[i][j].getMouseListeners()[1]);
            }
        }
        model.notifyObservers();
    }

    private void openTiles(int i, int j) {
        if (i < 0 || i >= tiles_x || j < 0 || j >= tiles_y){
            return;
        }
        if (tileValue[i][j] == true) {
            return;
        }

        if (tileState[i][j] == cellState.ZERO) {
            tileValue[i][j] = true;

            if (i > 0) {
                openTiles(i - 1, j);
                if (j > 0) {
                    openTiles(i - 1, j - 1);
                }
                if (j < tiles_y - 1) {
                    openTiles(i - 1, j + 1);
                }
            }

            //If i < tiles_x - 1
            if (i < tiles_x - 1) {
                openTiles(i + 1, j);
                if (j > 0) {
                    openTiles(i + 1, j - 1);
                }
                if (j < tiles_y - 1) {
                    openTiles(i + 1, j - 1);
                }
            }


            //If j > 0 or j < tiles_y - 1
            if (j > 0) {
                openTiles(i, j - 1);

            }
            if (j < tiles_y - 1) {
                openTiles(i, j + 1);

            }
        } else {
            tileValue[i][j] = true;
        }
    }

    private HashMap<String, Integer> generateRandomPairs(int numberOfPairsNeeded) {
        Random random = new Random();
        HashMap<String, Integer> generated = new HashMap<String, Integer>();
        int numberOfPairs = 0;
        int x, y;

        while (numberOfPairs < numberOfPairsNeeded) {
            x = random.nextInt(tiles_x);
            y = random.nextInt(tiles_y);
            String s = x + ", " + y;
            if (generated.containsKey(s) && generated.get(s) == y) {
            } else {
                generated.put(s, y);
                numberOfPairs++;
            }
        }

        return generated;
    }

    private void calculateAdjacentTiles() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tileState[i][j] != cellState.MINE) {
                    int adjacentBombs = 0;
                    //If i > 0
                    if (i > 0) {
                        if (tileState[i - 1][j] == cellState.MINE) {
                            adjacentBombs++;
                        }
                        if (j > 0) {
                            if (tileState[i - 1][j - 1] == cellState.MINE) {
                                adjacentBombs++;
                            }
                        }
                        if (j < tiles_y - 1) {
                            if (tileState[i - 1][j + 1] == cellState.MINE) {
                                adjacentBombs++;
                            }
                        }
                    }
                    //If i < tiles_x - 1
                    if (i < tiles_x - 1) {
                        if (tileState[i + 1][j] == cellState.MINE) {
                            adjacentBombs++;
                        }
                        if (j > 0) {
                            if (tileState[i + 1][j - 1] == cellState.MINE) {
                                adjacentBombs++;
                            }
                        }
                        if (j < tiles_y - 1) {
                            if (tileState[i + 1][j + 1] == cellState.MINE) {
                                adjacentBombs++;
                            }
                        }
                    }
                    //If j > 0 or j < tiles_y - 1
                    if (j > 0) {
                        if (tileState[i][j - 1] == cellState.MINE) {
                            adjacentBombs++;
                        }
                    }
                    if (j < tiles_y - 1) {
                        if (tileState[i][j + 1] == cellState.MINE) {
                            adjacentBombs++;
                        }
                    }
                    if (adjacentBombs == 0) {
                        tileState[i][j] = cellState.ZERO;
                    } else if (adjacentBombs == 1) {
                        tileState[i][j] = cellState.ONE;
                    } else if (adjacentBombs == 2) {
                        tileState[i][j] = cellState.TWO;
                    } else if (adjacentBombs == 3) {
                        tileState[i][j] = cellState.THREE;
                    } else if (adjacentBombs == 4) {
                        tileState[i][j] = cellState.FOUR;
                    } else if (adjacentBombs == 5) {
                        tileState[i][j] = cellState.FIVE;
                    } else if (adjacentBombs == 6) {
                        tileState[i][j] = cellState.SIX;
                    } else if (adjacentBombs == 7) {
                        tileState[i][j] = cellState.SEVEN;
                    } else if (adjacentBombs == 8) {
                        tileState[i][j] = cellState.EIGHT;
                    }
                }
            }
        }
    }

    public void updateView() {
        repaint();
    }

}
