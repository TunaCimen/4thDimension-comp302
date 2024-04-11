package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;

import java.util.ArrayList;

public class Hex extends GameObject {

    protected int x;
    protected int y;

    public static final int HEX_WIDTH = Constants.HEX_LENGTH;
    public static final int HEX_HEIGHT = Constants.HEX_LENGTH;
    public static final double HEX_SPEED = Constants.HEX_SPEED;

    public static ArrayList<Hex> hexes = new ArrayList<>();

    public Hex(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        addHex(this);


    }

    public static ArrayList<Hex> getHexes() {
        return hexes;
    }
    public static void setHexes(ArrayList<Hex> hexes) {
        Hex.hexes = hexes;
    }

    public void addHex(Hex hex) {
        hexes.add(hex);
    }
}