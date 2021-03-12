package com.company.heroes;

import com.company.tiles.Tile;

import java.awt.*;
import java.util.Random;

public abstract class Hero extends Tile {

    private int currentRow;
    private int currentCol;
    private String belongsTo;

    public abstract void render(Graphics g, int x, int y);

    public abstract void showPossibleMoves(Tile[][] board, Graphics g, int currentRowOfHero, int currentColOfHero);

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public abstract boolean isMovePossible(Tile[][] board, int wantedRow, int wantedCol);

    public abstract boolean isAttackPossible(Tile[][] board, int wantedRow, int wantedCol);

    public abstract String getType();

    public abstract int attack(Tile[][] board, int row, int col);

    public String belongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public abstract int getAttack();

    public abstract int getArmor();

    public abstract int getHealth();

    public abstract void setHealth(int health);

    public abstract void setArmor(int armor);

    public int heal() {
        Random random = new Random();

        int heal = random.nextInt(6) + 1;

        setHealth(getHealth() + heal);
        return heal;
    }
}
