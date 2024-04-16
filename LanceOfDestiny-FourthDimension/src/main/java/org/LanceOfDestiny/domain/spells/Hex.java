package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;

public class Hex extends GameObject {

    protected Vector position;

    public static final int HEX_WIDTH = Constants.HEX_LENGTH;
    public static final int HEX_HEIGHT = Constants.HEX_LENGTH;
    public static final double HEX_SPEED = Constants.HEX_SPEED;

    public static ArrayList<Hex> hexes = new ArrayList<>();

    public Hex(Vector position) {
        super();
        this.position = position;
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
