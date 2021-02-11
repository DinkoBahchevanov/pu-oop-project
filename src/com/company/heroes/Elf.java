package com.company.heroes;

import com.company.tiles.Tile;

import java.awt.*;

import static com.company.globalConstants.Constants.ELF;

public class Elf extends Hero {

    public final String TYPE = ELF;

    private final int ATTACK = 5;
    private int ARMOR = 1;
    private int health = 10;
    private final int MOVABLE_TILES = 3;

    @Override
    public void render(Graphics g, int x, int y) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.setColor(new Color(66, 232, 87));
        g.drawString("E", x + 40, y + 55);
    }

    public String getType() {
        return TYPE;
    }

    @Override
    public void showPossibleMoves(Tile[][] board, Graphics g, int currentRowOfHero, int currentColOfHero) {

    }

    @Override
    public boolean isMovePossible(Tile[][] board, int wantedRow, int wantedCol) {
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
    public int getAttack() {
        return ATTACK;
    }

    public int getArmor() {
        return ARMOR;
    }

    public int getHealth() {
        return health;
    }


    @Override
    public void setArmor(int ARMOR) {
        this.ARMOR = ARMOR;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
