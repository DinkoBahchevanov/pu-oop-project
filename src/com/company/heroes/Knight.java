package com.company.heroes;

import com.company.tiles.BattleFieldTile;
import com.company.tiles.Tile;

import java.awt.*;
import java.util.Random;

import static com.company.globalConstants.Constants.KNIGHT;

public class Knight extends Hero {

    public final String TYPE = KNIGHT;

    private final int ATTACK = 8;
    private int ARMOR = 3;
    private int health = 15;
    private final int MOVABLE_ATACKABLE_TILES = 1;

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(new Color(248, 53, 96));
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString("K", x + 40, y + 55);
    }

    public String getType() {
        return TYPE;
    }

    @Override
    public void showPossibleMoves(Tile[][] board, Graphics g, int currentRowOfHero, int currentColOfHero) {
        if (currentRowOfHero - 1 >= 0 && !(board[currentRowOfHero - 1][currentColOfHero] instanceof Hero)) {
            if ((board[currentRowOfHero - 1][currentColOfHero] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[currentRowOfHero - 1][currentColOfHero]).isBarrier()) || !(board[currentRowOfHero - 1][currentColOfHero] instanceof BattleFieldTile)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero - 1) * 100) + 30);
            }
        }

        if (currentRowOfHero + 1 < board.length && !(board[currentRowOfHero + 1][currentColOfHero] instanceof Hero)) {
            if ((board[currentRowOfHero + 1][currentColOfHero] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[currentRowOfHero + 1][currentColOfHero]).isBarrier()) || !(board[currentRowOfHero + 1][currentColOfHero] instanceof BattleFieldTile)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero + 1) * 100) + 30);
            }
        }

        if (currentColOfHero - 1 >= 0 && !(board[currentRowOfHero][currentColOfHero - 1] instanceof Hero)) {
            if ((board[currentRowOfHero][currentColOfHero - 1] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[currentRowOfHero][currentColOfHero - 1]).isBarrier()) || !(board[currentRowOfHero][currentColOfHero - 1] instanceof BattleFieldTile)) {
                drawPossibleMoveCircle(g, (currentColOfHero - 1) * 100, ((currentRowOfHero) * 100) + 30);
            }
        }

        if (currentColOfHero + 1 < 9 && !(board[currentRowOfHero][currentColOfHero + 1] instanceof Hero)) {
            if ((board[currentRowOfHero][currentColOfHero + 1] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[currentRowOfHero][currentColOfHero + 1]).isBarrier()) || !(board[currentRowOfHero][currentColOfHero + 1] instanceof BattleFieldTile)) {
                drawPossibleMoveCircle(g, (currentColOfHero + 1) * 100, ((currentRowOfHero) * 100) + 30);
            }
        }
    }

    @Override
    public boolean isMovePossible(Tile[][] board, int wantedRow, int wantedCol) {

        if ((wantedRow == getCurrentRow() + 1 && getCurrentCol() == wantedCol)
                || ((wantedRow == getCurrentRow() - 1 && getCurrentCol() == wantedCol)
                || ((wantedRow == getCurrentRow() && getCurrentCol() == wantedCol - 1)
                || ((wantedRow == getCurrentRow() && getCurrentCol() == wantedCol + 1))))) {
            if (board[wantedRow][wantedCol] instanceof BattleFieldTile && ((BattleFieldTile) board[wantedRow][wantedCol]).isBarrier()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isAttackPossible(Tile[][] board, int wantedRow, int wantedCol) {
        if (wantedCol != getCurrentCol() && wantedRow != getCurrentRow()) {
            return false;
        }
        if ((wantedRow != getCurrentRow() && Math.abs(wantedRow - getCurrentRow()) > MOVABLE_ATACKABLE_TILES)
                || wantedCol != getCurrentCol() && Math.abs(wantedCol - getCurrentCol()) > MOVABLE_ATACKABLE_TILES) {
            return false;
        }

        return board[wantedRow][wantedCol] instanceof Hero;
    }

    @Override
    public int attack(Tile[][] board, int wantedRow, int wantedCol) {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        int sum = dice1 + dice2 + dice3;

        Hero attackedHero = (Hero) board[wantedRow][wantedCol];

        int damage = getAttack() - attackedHero.getArmor();

        if (sum == attackedHero.getHealth()) {
            damage = 0;
        } else if (sum == 3) {
            damage /= 2;
        }
        attackedHero.setHealth(attackedHero.getHealth() - damage);
        return damage;
    }

    private void drawPossibleMoveCircle(Graphics g, int x, int y) {
        g.setColor(new Color(253, 58, 98));
        g.drawOval(x + 40, y + 35, 20, 20);
        g.fillOval(x + 40, y + 35, 20, 20);
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
