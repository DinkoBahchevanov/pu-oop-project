package com.company.heroes;

import com.company.tiles.BattleFieldTile;
import com.company.tiles.Tile;

import java.awt.*;

import static com.company.globalConstants.Constants.DWARF;

public class Dwarf extends Hero {

    public final String TYPE = DWARF;

    private final int ATTACK = 6;
    private int ARMOR = 2;
    private int health = 12;
    private final int MOVABLE_TILES = 2;

    @Override
    public void render(Graphics g, int x, int y) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.setColor(new Color(141, 83, 255));
        g.drawString("Dw", x + 27, y + 55);
    }

    public String getType() {
        return this.TYPE;
    }

    @Override
    public void showPossibleMoves(Tile[][] board, Graphics g, int currentRowOfHero, int currentColOfHero) {
        //upper moving
        for ( int i = 1; i <= 2; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero - i, currentColOfHero)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero - i) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 2; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero + i, currentColOfHero)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero + i) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 2; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero, currentColOfHero - i)) {
                drawPossibleMoveCircle(g, (currentColOfHero - i) * 100, ((currentRowOfHero) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 2; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero, currentColOfHero + i)) {
                drawPossibleMoveCircle(g, (currentColOfHero + i) * 100, ((currentRowOfHero) * 100) + 30);
            } else break;
        }
    }

    private void drawPossibleMoveCircle(Graphics g, int x, int y) {
        g.setColor(new Color(155, 56, 255));
        g.drawOval(x + 40, y + 35, 20, 20);
        g.fillOval(x + 40, y + 35, 20, 20);
    }

    @Override
    public boolean isMovePossible(Tile[][] board, int wantedRow, int wantedCol) {
        if (board[wantedRow][wantedCol] instanceof BattleFieldTile && ((BattleFieldTile) board[wantedRow][wantedCol]).isBarrier()) {
            return false;
        }

        if (wantedCol != getCurrentCol() && wantedRow != getCurrentRow()) {
            return false;
        }

        if (wantedRow > getCurrentRow()) {
            for ( int i = 1; i <= 2; i++ ) {
                if (getCurrentRow() + i < board.length) {
                    if (board[getCurrentRow() + i][getCurrentCol()] instanceof Hero) {
                        return false;
                    }
                    if (getCurrentRow() + i == wantedRow) {
                        return true;
                    }
                }
            }
            return true;
        }
        if (wantedRow < getCurrentRow()) {
            for ( int i = 1; i <= 2; i++ ) {
                if (getCurrentRow() - i < board.length) {
                    if (board[getCurrentRow() - i][getCurrentCol()] instanceof Hero) {
                        return false;
                    }
                    if (getCurrentRow() - i == wantedRow) {
                        return true;
                    }
                }
            }
            return true;
        }
        if (wantedCol < getCurrentCol()) {
            for ( int i = 1; i <= 2; i++ ) {
                if (board[getCurrentRow()][getCurrentCol() - i] instanceof Hero) {
                    return false;
                }
                if (getCurrentCol() - i == wantedCol) {
                    return true;
                }
            }
            return true;
        }
        if (wantedCol > getCurrentCol()) {
            for ( int i = 1; i <= 2; i++ ) {
                if (board[getCurrentRow()][getCurrentCol() + i] instanceof Hero) {
                    return false;
                }
                if (getCurrentCol() + i == wantedCol) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isAttackPossible(Tile[][] board, int wantedRow, int wantedCol) {
        return false;
    }

    @Override
    public int attack(Tile[][] board, int wantedRow, int wantedCol) {
        return 0;
    }

    private boolean isWantedPositionPossible(Tile[][] board, int currentRowOfHero, int currentColOfHero) {
        if ((currentRowOfHero >= 0 && currentRowOfHero < board.length) && (currentColOfHero < board[currentRowOfHero].length && currentColOfHero >= 0)
                && !(board[currentRowOfHero][currentColOfHero] instanceof Hero)) {
            if ((board[currentRowOfHero][currentColOfHero] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[currentRowOfHero][currentColOfHero]).isBarrier()) || !(board[currentRowOfHero][currentColOfHero] instanceof BattleFieldTile)) {
                return true;
            }
        }
        return false;
    }

    public int getAttack() {
        return ATTACK;
    }

    public int getArmor() {
        return ARMOR;
    }

    public int getHealth() {
        return health;
    }

    public void setArmor(int ARMOR) {
        this.ARMOR = ARMOR;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
