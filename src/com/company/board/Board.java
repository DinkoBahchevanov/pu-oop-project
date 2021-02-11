package com.company.board;

import com.company.heroes.Dwarf;
import com.company.heroes.Elf;
import com.company.heroes.Hero;
import com.company.heroes.Knight;
import com.company.player.Player;
import com.company.tiles.BattleFieldTile;
import com.company.tiles.DarkGreyTile;
import com.company.tiles.LightGreyTile;
import com.company.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Random;

import static com.company.globalConstants.Constants.*;

public class Board extends JFrame implements MouseListener {


    private Tile[][] board;
    private boolean playerATurn;
    private boolean playerBTurn;
    private boolean stillPlacingFigures;
    private boolean hasPlacedChoosingSection = false;
    private boolean hasPlacedInnerGameChoiceSection = false;
    private boolean gameHasWinner;

    //attack/move/heal
    private String innerGameChoice;

    private JButton knightButton;
    private JButton dwarfButton;
    private JButton elfButton;

    private JButton attackButton;
    private JButton healButton;
    private JButton moveButton;

    private Player playerA = new Player();
    private Player playerB = new Player();

    private Hero chosenHeroToPlace;
    private Hero chosenHeroToPlayWith;

    public Board() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(1500, 1030));

        board = new Tile[7][9];
        playerATurn = true;
        playerBTurn = false;
        stillPlacingFigures = true;
        gameHasWinner = false;

        setBoardTiles();

        // setting the barriers on the battlefield
        setBarriers();

        this.addMouseListener(this);
    }

    private void setBoardTiles() {
        for ( int i = 0; i < board.length; i++ ) {
            for ( int j = 0; j < board[i].length; j++ ) {
                if (board[i][j] == null) {
                    if (i < 2 || i > 4) {
                        if (i % 2 == 0) {
                            if (j % 2 == 0) {
                                board[i][j] = new LightGreyTile(new Color(121, 121, 121), j * 100, i * 100 + 30);
                            } else board[i][j] = new DarkGreyTile(new Color(75, 75, 75), j * 100, i * 100 + 30);
                        } else {
                            if (j % 2 == 0) {
                                board[i][j] = new DarkGreyTile(new Color(75, 75, 75), j * 100, i * 100 + 30);
                            } else board[i][j] = new LightGreyTile(new Color(121, 121, 121), j * 100, i * 100 + 30);
                        }
                    } else {
                        board[i][j] = new BattleFieldTile(new Color(180, 180, 180), j * 100, i * 100 + 30);
                    }
                }
            }
        }
        System.out.println();
    }

    private void setBarriers() {
        Random random = new Random();

        for ( int i = 0; i < random.nextInt(5) + 1; i++ ) {
            int row = random.nextInt(3) + 2;
            int col = random.nextInt(9);

            if (board[row][col] instanceof BattleFieldTile && !((BattleFieldTile) board[row][col]).isBarrier()) {
                board[row][col] = new BattleFieldTile(new Color(255, 200, 3), col * 100, row * 100 + 30);
                ((BattleFieldTile) board[row][col]).setBarrier(true);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //painting the grid(tiles)
        visualizeComponents(getGraphics());
    }

    private void visualizeComponents(Graphics g) {
        for ( int i = 0; i < board.length; i++ ) {
            for ( int j = 0; j < board[i].length; j++ ) {
                board[i][j].render(getGraphics(), j * 100, i * 100 + 30);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int row = (e.getY() - 30) / 100;
        int col = e.getX() / 100;
        ;

//        setting the figures and choice window
        if (stillPlacingFigures) {
            if (!hasPlacedChoosingSection) {
                placeFigureChoice(getGraphics());
                return;
            }

            if (e.getX() <= 900 && e.getY() <= 730) {
                putFigureOnBoard(getGraphics(), col, row);
            }
            return;
        }


        if (!hasPlacedInnerGameChoiceSection) {
            drawPlayingGameWindow(getGraphics());
            playerATurn = true;
            playerBTurn = false;
            return;
        }

        if (e.getX() <= 900 && e.getY() <= 730) {
            if (chosenHeroToPlayWith == null) {
                //switching the button choice move/attack/heal
                switch (innerGameChoice) {
                    case MOVE:
                        //checking the order
                        if (board[row][col] instanceof Hero) {
                            Hero currentHero = (Hero) board[row][col];
                            if ((playerATurn && currentHero.belongsTo().equals(BELONGS_TO_PLAYER_A))
                                    || (playerBTurn && currentHero.belongsTo().equals(BELONGS_TO_PLAYER_B))) {
                                chosenHeroToPlayWith = (Hero) board[row][col];
                                //showing the possible moves
                                chosenHeroToPlayWith.showPossibleMoves(board, getGraphics(), row, col);
                                chosenHeroToPlayWith.setCurrentRow(row);
                                chosenHeroToPlayWith.setCurrentCol(col);
                            } else chosenHeroToPlace = null;
                        }
                        break;
                    case ATTACK:
                        Hero currentHero = (Hero) board[row][col];

                        if ((playerATurn && currentHero.belongsTo().equals(BELONGS_TO_PLAYER_A))
                                || (playerBTurn && currentHero.belongsTo().equals(BELONGS_TO_PLAYER_B))) {
                            chosenHeroToPlayWith = (Hero) board[row][col];
                            chosenHeroToPlayWith.setCurrentRow(row);
                            chosenHeroToPlayWith.setCurrentCol(col);
                        } else chosenHeroToPlayWith = null;

                        break;
                    case HEAL:

                        break;
                }
            } else {
                switch (innerGameChoice) {
                    case MOVE:
                        moveTheFigure(row, col);
                        break;
                    case ATTACK:
                        attackFigure(getGraphics(), row, col);
                        break;
                    case HEAL:

                        break;
                }
            }
        } else {
            chosenHeroToPlayWith = null;
            visualizeComponents(getGraphics());
        }
    }

    private void attackFigure(Graphics g, int row, int col) {
        Hero chosenHeroToAttack = (Hero) board[row][col];

        if ((chosenHeroToAttack.belongsTo().equals(BELONGS_TO_PLAYER_A) && playerATurn)
                || chosenHeroToAttack.belongsTo().equals(BELONGS_TO_PLAYER_B) && playerBTurn) {
            JOptionPane.showMessageDialog(this, "Cannot attack your warriors");
            return;
        }
        if (chosenHeroToPlayWith.isAttackPossible(board, row, col)) {
            System.out.println("Attack is possible");

            g.setColor(new Color(246, 0, 0));
            g.setFont(new Font("Serif", Font.PLAIN, 50));
            int damage = chosenHeroToAttack.attack(board, row, col);
            if (playerATurn) {
                g.drawString("Player A attacked " + ((Hero) board[row][col]).getType().toLowerCase(), 950 + 27, 430);
//                Thread.sleep(500);
            } else {
                g.drawString("Player B attacked " + ((Hero) board[row][col]).getType().toLowerCase(), 950 + 27, 430);
            }
            g.drawString(String.format("with %d damage", damage),950 + 27, 480);


            changeTurnOfPlayer();
            drawLabelForChoice(getGraphics());
            if (chosenHeroToAttack.getHealth() <= 0) {
                board[row][col] = null;
            }
            chosenHeroToPlayWith = null;
            setBoardTiles();
            visualizeComponents(g);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.setColor(getBackground());
            g.drawRect(950, 370, 550,400);
            g.fillRect(950, 370, 550,400);
        } else {
            JOptionPane.showMessageDialog(this, "Not Possible attack!");
            chosenHeroToPlayWith = null;
        }
    }

    private void moveTheFigure(int row, int col) {
        if (board[row][col] instanceof Hero) {
            if ((((Hero) chosenHeroToPlayWith).belongsTo()).equals(((Hero) board[row][col]).belongsTo())) {
                chosenHeroToPlayWith = null;
                visualizeComponents(getGraphics());
                return;
            }
        }
        if (chosenHeroToPlayWith.isMovePossible(board, row, col)) {
            //draw the heroes over the board in order to hide the showed possible moves
            visualizeComponents(getGraphics());
            board[row][col] = chosenHeroToPlayWith;
            board[chosenHeroToPlayWith.getCurrentRow()][chosenHeroToPlayWith.getCurrentCol()] = null;

            //here we do draw all the board tiles(the board itself without figures on it)
            setBoardTiles();
            chosenHeroToPlayWith.setCurrentRow(row);
            chosenHeroToPlayWith.setCurrentCol(col);

            //draw the heroes over the board
            visualizeComponents(getGraphics());
            chosenHeroToPlayWith = null;

            //setting playerATurn or playerBTurn true
            changeTurnOfPlayer();
            drawLabelForChoice(getGraphics());
        } else {
            chosenHeroToPlayWith = null;
            visualizeComponents(getGraphics());
        }
    }

    private void changeTurnOfPlayer() {
        if (playerATurn) {
            playerBTurn = true;
            playerATurn = false;
        } else {
            playerBTurn = false;
            playerATurn = true;
        }
    }

    private void drawPlayingGameWindow(Graphics g) {
        g.setColor(new Color(18, 248, 248));
        g.drawRect(1000, 100, 300, 200);
        g.fillRect(1000, 100, 300, 200);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(1005, 104, 290, 191);
        g.fillRect(1005, 104, 290, 191);

        drawLabelForChoice(g);

        drawButtonsForPlaying(g);
        hasPlacedInnerGameChoiceSection = true;
    }

    private void drawButtonsForPlaying(Graphics g) {
        //drawing attackButton
        attackButton = new JButton("Attack");
        attackButton.setBackground(new Color(255, 126, 37));
        attackButton.setBounds(1015, 130, 80, 80);
        attackButton.setFont(new Font("Arial", Font.PLAIN, 15));
        attackButton.setVisible(true);
        attackButton.setFocusPainted(false);
        attackButton.addActionListener(new AttackButtonAction());
        this.add(attackButton);

        //drawing moveButton
        moveButton = new JButton("Move");
        moveButton.setBackground(new Color(0, 194, 96));
        moveButton.setBounds(1105, 130, 80, 80);
        moveButton.setFont(new Font("Arial", Font.PLAIN, 17));
        moveButton.setFocusPainted(false);
        moveButton.setVisible(true);
        moveButton.addActionListener(new MoveButtonAction());
        this.add(moveButton);

        //draw healButton
        healButton = new JButton("Heal");
        healButton.setBackground(new Color(0, 255, 236));
        healButton.setBounds(1195, 130, 80, 80);
        healButton.setFont(new Font("Arial", Font.PLAIN, 17));
        healButton.setFocusPainted(false);
        healButton.setVisible(true);
        healButton.addActionListener(new HealButtonAction());
        this.add(healButton);
    }

    private void putFigureOnBoard(Graphics g, int col, int row) {
        if (playerATurn) {
            //check if currentPlayer has more placable heroes of that type
            if (playerA.getHeroesForPlacing().get(chosenHeroToPlace.getType()) > 0) {

                if (row < 2 && !(board[row][col] instanceof Hero)) {
                    chosenHeroToPlace.render(g, col * 100, row * 100 + 30);
                } else return;

                board[row][col] = chosenHeroToPlace;
                chosenHeroToPlace.setBelongsTo(BELONGS_TO_PLAYER_A);

                //lowering the remaining heroes of that type that playerA can put
                playerA.getHeroesForPlacing().put(chosenHeroToPlace.getType(),
                        playerA.getHeroesForPlacing().get(chosenHeroToPlace.getType()) - 1);
                playerATurn = false;
                playerBTurn = true;
            } else System.out.printf("No more heroes of %s type%n", chosenHeroToPlace.getType());
        } else {
            if (playerB.getHeroesForPlacing().get(chosenHeroToPlace.getType()) > 0) {

                if (row > 4 && !(board[row][col] instanceof Hero)) {
                    chosenHeroToPlace.render(g, col * 100, row * 100 + 30);
                } else return;

                board[row][col] = chosenHeroToPlace;
                chosenHeroToPlace.setBelongsTo(BELONGS_TO_PLAYER_B);

                //lowering the remaining heroes of that type that playerB can put
                playerB.getHeroesForPlacing().put(chosenHeroToPlace.getType(),
                        playerB.getHeroesForPlacing().get(chosenHeroToPlace.getType()) - 1);

                playerATurn = true;
                playerBTurn = false;

            } else {
                System.out.printf("No more heroes of %s type%n", chosenHeroToPlace.getType());
            }
        }

        //checking if in the both players there are any heroes still not placed
        checkIfThereAreStillHeroesToPlace(g);
        if (stillPlacingFigures) {
            drawLabelForChoice(g);
        }
        chosenHeroToPlace = null;
    }

    private void checkIfThereAreStillHeroesToPlace(Graphics g) {
        boolean stillHasHeroes = false;

        for ( Map.Entry<String, Integer> entry : playerA.getHeroesForPlacing().entrySet() ) {
            if (entry.getValue() != 0) {
                stillHasHeroes = true;
                break;
            }
        }
        for ( Map.Entry<String, Integer> entry : playerB.getHeroesForPlacing().entrySet() ) {
            if (entry.getValue() != 0) {
                stillHasHeroes = true;
                break;
            }
        }

        if (!stillHasHeroes) {
            stillPlacingFigures = false;
            hideChoosingSection(g);
        }
    }

    private void hideChoosingSection(Graphics g) {
        knightButton.setVisible(false);
        dwarfButton.setVisible(false);
        elfButton.setVisible(false);
        this.remove(knightButton);
        this.remove(dwarfButton);
        this.remove(elfButton);

        g.setColor(getBackground());
        g.drawRect(1000, 50, 400, 350);
        g.fillRect(1000, 50, 400, 350);
    }

    private void placeFigureChoice(Graphics g) {
        drawRectForPlaceFigureChoice(g);

        drawLabelForChoice(g);

        drawChoices(g);
        hasPlacedChoosingSection = true;
    }

    private void drawLabelForChoice(Graphics g) {
        g.setColor(this.getBackground());
        g.drawRect(1050, 50, 177, 38);
        g.fillRect(1050, 50, 177, 38);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 30));

        if (playerATurn) {
            g.setColor(new Color(255, 0, 248));
            g.drawString("Player A turn!", 1055, 80);
        } else {
            g.setColor(new Color(0, 172, 138));
            g.drawString("Player B turn!", 1055, 80);
        }
    }

    private void drawChoices(Graphics g) {
        //drawing knightButton
        knightButton = new JButton("K");
        knightButton.setBackground(new Color(250, 58, 113));
        knightButton.setBounds(1015, 130, 80, 80);
        knightButton.setFont(new Font("Arial", Font.PLAIN, 30));
        knightButton.setVisible(true);
        knightButton.setFocusPainted(false);
        knightButton.addActionListener(new KnightButtonAction());
        this.add(knightButton);

        //drawing dwarfButton
        dwarfButton = new JButton("Dw");
        dwarfButton.setBackground(new Color(153, 120, 120));
        dwarfButton.setBounds(1105, 130, 80, 80);
        dwarfButton.setFont(new Font("Arial", Font.PLAIN, 30));
        dwarfButton.setFocusPainted(false);
        dwarfButton.setVisible(true);
        dwarfButton.addActionListener(new DwarfButtonAction());
        this.add(dwarfButton);

        //draw elfButton
        elfButton = new JButton("E");
        elfButton.setBackground(new Color(192, 243, 126));
        elfButton.setBounds(1195, 130, 80, 80);
        elfButton.setFont(new Font("Arial", Font.PLAIN, 30));
        elfButton.setFocusPainted(false);
        elfButton.setVisible(true);
        elfButton.addActionListener(new ElfButtonAction());
        this.add(elfButton);
//        g.setColor(new Color(193, 241, 121));
//        g.drawRect(1035, 150, 80, 80);
//        g.fillRect(1035, 150, 81, 80);
    }

    private void drawRectForPlaceFigureChoice(Graphics g) {
        g.setColor(new Color(173, 75, 243));
        g.drawRect(1000, 100, 300, 200);
        g.fillRect(1000, 100, 300, 200);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(1005, 104, 290, 191);
        g.fillRect(1005, 104, 290, 191);

//        visualizeComponents(getGraphics());
//        super.paintComponents(getGraphics());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class KnightButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chosenHeroToPlace = new Knight();
        }
    }

    class DwarfButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chosenHeroToPlace = new Dwarf();
        }
    }

    class ElfButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chosenHeroToPlace = new Elf();
        }
    }

    private class HealButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            innerGameChoice = HEAL;
        }
    }

    private class MoveButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            innerGameChoice = MOVE;
        }
    }

    private class AttackButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            innerGameChoice = ATTACK;
        }
    }
}