package com.company.heroes;

import com.company.tiles.BattleFieldTile;
import com.company.tiles.Tile;

import java.awt.*;
import java.util.Random;

import static com.company.globalConstants.Constants.ELF;

public class Elf extends Hero {

    public final String TYPE = ELF;

    private final int ATTACK = 5;
    private int armor = 1;
    private int health = 10;

    @Override
    public void render(Graphics g, int x, int y) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.setColor(new Color(0, 255, 32));
        g.drawString("E", x + 40, y + 55);
    }

    public String getType() {
        return TYPE;
    }

    @Override
    public void showPossibleMoves(Tile[][] board, Graphics g, int currentRowOfHero, int currentColOfHero) {
        for (int i = 1; i <= 3; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero - i, currentColOfHero)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero - i) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 3; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero + i, currentColOfHero)) {
                drawPossibleMoveCircle(g, (currentColOfHero) * 100, ((currentRowOfHero + i) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 3; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero, currentColOfHero - i)) {
                drawPossibleMoveCircle(g, (currentColOfHero - i) * 100, ((currentRowOfHero) * 100) + 30);
            } else break;
        }

        for ( int i = 1; i <= 3; i++ ) {
            if (isWantedPositionPossible(board, currentRowOfHero, currentColOfHero + i)) {
                drawPossibleMoveCircle(g, (currentColOfHero + i) * 100, ((currentRowOfHero) * 100) + 30);
            } else break;
        }
        // start checking G (Г) possible moves +/- 1 row
        if (isWantedPositionPossible(board, currentRowOfHero + 1, currentColOfHero + 2)) {
            drawPossibleMoveCircle(g, (currentColOfHero + 2) * 100, ((currentRowOfHero + 1)* 100 ) + 30);
        }

        if (isWantedPositionPossible(board, currentRowOfHero - 1, currentColOfHero + 2)) {
            drawPossibleMoveCircle(g, (currentColOfHero + 2) * 100, ((currentRowOfHero - 1) * 100 ) + 30);
        }

        if (isWantedPositionPossible(board, currentRowOfHero + 1, currentColOfHero - 2)) {
            drawPossibleMoveCircle(g, (currentColOfHero - 2) * 100, ((currentRowOfHero + 1) * 100 ) + 30);
        }
        if (isWantedPositionPossible(board, currentRowOfHero - 1, currentColOfHero - 2)) {
            drawPossibleMoveCircle(g, (currentColOfHero - 2) * 100, ((currentRowOfHero - 1) * 100 ) + 30);
        }

        // second type of checking for G(Г) moves +/- 2 rows
        if (isWantedPositionPossible(board, currentRowOfHero + 2, currentColOfHero + 1)) {
            drawPossibleMoveCircle(g, (currentColOfHero + 1) * 100, (currentRowOfHero + 2) * 100 + 30);
        }
        if (isWantedPositionPossible(board, currentRowOfHero + 2, currentColOfHero - 1)) {
            drawPossibleMoveCircle(g, (currentColOfHero - 1) * 100, (currentRowOfHero + 2) * 100 + 30);
        }
        if (isWantedPositionPossible(board, currentRowOfHero - 2, currentColOfHero + 1)) {
            drawPossibleMoveCircle(g, (currentColOfHero + 1) * 100, (currentRowOfHero - 2) * 100 + 30);
        }
        if (isWantedPositionPossible(board, currentRowOfHero - 2, currentColOfHero - 1)) {
            drawPossibleMoveCircle(g, (currentColOfHero - 1) * 100, (currentRowOfHero - 2) * 100 + 30);
        }
    }

    private void drawPossibleMoveCircle(Graphics g, int x, int y) {
        g.setColor(new Color(127, 255, 0));
        g.drawOval(x + 40, y + 35, 20, 20);
        g.fillOval(x + 40, y + 35, 20, 20);
    }

    @Override
    public boolean isMovePossible(Tile[][] board, int wantedRow, int wantedCol) {
        if (board[wantedRow][wantedCol] instanceof BattleFieldTile && ((BattleFieldTile) board[wantedRow][wantedCol]).isBarrier()) {
            return false;
        }
        if (wantedRow > getCurrentRow() && getCurrentCol() == wantedCol) {
            for ( int i = 1; i <= 3; i++ ) {
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

        if (wantedRow < getCurrentRow() && getCurrentCol() == wantedCol) {
            for ( int i = 1; i <= 3; i++ ) {
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

        if (wantedCol < getCurrentCol() && getCurrentRow() == wantedRow) {
            for ( int i = 1; i <= 3; i++ ) {
                if (board[getCurrentRow()][getCurrentCol() - i] instanceof Hero) {
                    return false;
                }
                if (getCurrentCol() - i == wantedCol) {
                    return true;
                }
            }
            return true;
        }

        if (wantedCol > getCurrentCol() && getCurrentRow() == wantedRow) {
            for ( int i = 1; i <= 3; i++ ) {
                if (board[getCurrentRow()][getCurrentCol() + i] instanceof Hero) {
                    return false;
                }
                if (getCurrentCol() + i == wantedCol) {
                    return true;
                }
            }
            return true;
        }

        if (wantedRow == getCurrentRow() + 2 && wantedCol == getCurrentCol() + 1) {
            if(isWantedPositionPossible(board, getCurrentRow() + 2, getCurrentCol() + 1)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() + 2 && wantedCol == getCurrentCol() - 1) {
            if(isWantedPositionPossible(board, getCurrentRow() + 2, getCurrentCol() - 1)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() - 2 && wantedCol == getCurrentCol() + 1) {
            if(isWantedPositionPossible(board, getCurrentRow() - 2, getCurrentCol() + 1)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() - 2 && wantedCol == getCurrentCol() - 1) {
            if(isWantedPositionPossible(board, getCurrentRow() - 2, getCurrentCol() - 1)) {
                return true;
            }
        }

        // second type of checking for +/- 1 row
        if (wantedRow == getCurrentRow() + 1 && wantedCol == getCurrentCol() + 2) {
            if (isWantedPositionPossible(board, getCurrentRow() + 1, getCurrentCol() + 2)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() + 1 && wantedCol == getCurrentCol() - 2) {
            if (isWantedPositionPossible(board, getCurrentRow() + 1, getCurrentCol() - 2)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() - 1 && wantedCol == getCurrentCol() + 2) {
            if (isWantedPositionPossible(board, getCurrentRow() - 1, getCurrentCol() + 2)) {
                return true;
            }
        }

        if (wantedRow == getCurrentRow() - 1 && wantedCol == getCurrentCol() - 2) {
            if (isWantedPositionPossible(board, getCurrentRow() - 1, getCurrentCol() - 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWantedPositionPossible(Tile[][] board, int wantedRowOfHero, int wantedColOfHero) {
        if ((wantedRowOfHero >= 0 && wantedRowOfHero < board.length) && (wantedColOfHero < board[wantedRowOfHero].length && wantedColOfHero >= 0)
                && !(board[wantedRowOfHero][wantedColOfHero] instanceof Hero)) {
            if ((board[wantedRowOfHero][wantedColOfHero] instanceof BattleFieldTile
                    && !((BattleFieldTile) board[wantedRowOfHero][wantedColOfHero]).isBarrier()) || !(board[wantedRowOfHero][wantedColOfHero] instanceof BattleFieldTile)) {
                if (board[wantedRowOfHero][wantedColOfHero] instanceof Hero) return false;
                if (board[wantedRowOfHero][wantedColOfHero] instanceof BattleFieldTile && (((BattleFieldTile) board[wantedRowOfHero][wantedColOfHero]).isBarrier())) return false;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAttackPossible(Tile[][] board, int wantedRow, int wantedCol) {
        if (Math.abs(wantedRow - getCurrentRow()) > 3 || Math.abs(wantedCol - getCurrentCol()) > 3) {
            return false;
        }
        if (wantedCol != getCurrentCol() && wantedRow != getCurrentRow()) {
            return false;
        }

        if (getCurrentRow() > wantedRow) {
            if (getCurrentRow() - wantedRow > 1) {
                if (getCurrentRow() - wantedRow == 2) {
                    if (board[getCurrentRow() - 1][wantedCol] instanceof Hero) {
                        return false;
                    }
                } else if (getCurrentRow() - wantedRow == 3) {
                    if (board[getCurrentRow() - 2][wantedCol] instanceof Hero) {
                        return false;
                    }
                }
            }
            return board[wantedRow][wantedCol] instanceof Hero || (board[wantedRow][wantedCol] instanceof BattleFieldTile
                    && ((BattleFieldTile)(board[wantedRow][wantedCol])).isBarrier());

        } else if (getCurrentRow() < wantedRow) {
            if (wantedRow - getCurrentRow() > 1) {
                if (wantedRow - getCurrentRow() == 2) {
                    if (board[getCurrentRow() + 1][wantedCol] instanceof Hero) {
                        return false;
                    }
                }else if (wantedRow - getCurrentRow() == 3) {
                    if (board[getCurrentRow() + 2][wantedCol] instanceof Hero) {
                        return false;
                    }
                }
            }
            return board[wantedRow][wantedCol] instanceof Hero || (board[wantedRow][wantedCol] instanceof BattleFieldTile
                    && ((BattleFieldTile)(board[wantedRow][wantedCol])).isBarrier());

        }else if (getCurrentCol() < wantedCol) {
            if (wantedCol - getCurrentCol() > 1) {
                if (wantedCol - getCurrentCol() == 2) {
                    if (board[wantedRow][getCurrentCol() + 1] instanceof Hero) {
                        return false;
                    }
                } else if (wantedCol - getCurrentCol() == 3) {
                    if (board[wantedRow][getCurrentCol() + 2] instanceof Hero) {
                        return false;
                    }
                }
            }
            return board[wantedRow][wantedCol] instanceof Hero || (board[wantedRow][wantedCol] instanceof BattleFieldTile
                    && ((BattleFieldTile)(board[wantedRow][wantedCol])).isBarrier());

        } else {
            if (getCurrentCol() - wantedCol > 1) {
                if (board[wantedRow][getCurrentCol() - 1] instanceof Hero) {
                    if (getCurrentCol() - wantedCol == 2) {
                        if (board[wantedRow][getCurrentCol() - 1] instanceof Hero) {
                            return false;
                        }
                    } else if (getCurrentCol() - wantedCol == 3) {
                        if (board[wantedRow][getCurrentCol() - 2] instanceof Hero) {
                            return false;
                        }
                    }
                }
            }
            return board[wantedRow][wantedCol] instanceof Hero || (board[wantedRow][wantedCol] instanceof BattleFieldTile
                    && ((BattleFieldTile)(board[wantedRow][wantedCol])).isBarrier());
        }
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

    public int getAttack() {
        return ATTACK;
    }

    public int getArmor() {
        return armor;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void setArmor(int ARMOR) {
        this.armor = ARMOR;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
