package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.barriers.Barrier;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameMap class represents the map of the game.
 * It contains a list of barriers that are present on the map.
 */
public class GameMap {
    private static GameMap instance;
    // List of barriers present on the map
    private  ArrayList<Barrier> barriers;


    /**
     * Constructor for the GameMap class.
     */
    public GameMap() {
        this.barriers = new ArrayList<>();
    }

    public static GameMap getInstance() {
        if (instance == null) instance = new GameMap();
        return instance;
    }

    /**
     * This method is used to get the list of barriers on the map.
     *
     * @return List<Barrier> This returns the list of barriers.
     */
    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }

    public void setBarriers(ArrayList<Barrier> barriers) {
        this.barriers = barriers;
    }

    public void clearBarriers() {
        barriers.clear();
    }
}