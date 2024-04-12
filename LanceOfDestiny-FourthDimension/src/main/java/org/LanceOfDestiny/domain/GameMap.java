package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.barriers.Barrier;

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
     * @param barriers A list of barriers to be placed on the map.
     */
    public GameMap(List<Barrier> barriers) {
        this.barriers = barriers;
    }

    /**
     * This method is used to get the list of barriers on the map.
     * @return List<Barrier> This returns the list of barriers.
     */
    public List<Barrier> getBarriers() {
        return barriers;
    }
}