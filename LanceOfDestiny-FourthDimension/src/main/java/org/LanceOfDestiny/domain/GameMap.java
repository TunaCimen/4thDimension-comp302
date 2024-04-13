package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.barriers.Barrier;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameMap class represents the map of the game.
 * It contains a list of barriers that are present on the map.
 */
public class GameMap {
    // List of barriers present on the map
    private List<Barrier> barriers;

    /**
     * Constructor for the GameMap class.
     */
    public GameMap() {
        this.barriers = new ArrayList<>();
    }

    /**
     * This method is used to get the list of barriers on the map.
     * @return List<Barrier> This returns the list of barriers.
     */
    public List<Barrier> getBarriers() {
        return barriers;
    }

    public void setBarriers(List<Barrier> barriers) {
        this.barriers = barriers;
    }
}