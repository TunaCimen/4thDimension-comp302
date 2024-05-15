package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.*;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.LanceOfDestiny.domain.Constants.*;

public class BarrierManager {

    private static BarrierManager instance;

    public static ArrayList<Barrier> barriers;
    public static ArrayList<HollowBarrier> hollowBarriers;
    private BarrierTypes selectedBarrierType;
    private Barrier clickedBarrier;
    private Vector oldLocationOfBarrier;

    private BarrierManager() {
        barriers = new ArrayList<>();
        hollowBarriers = new ArrayList<>();
        selectedBarrierType = BarrierTypes.SIMPLE;
        Events.EndGame.addRunnableListener(this::removeAllBarriers);
    }

    public static BarrierManager getInstance() {
        if (instance == null) instance = new BarrierManager();
        return instance;
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public void addHollowBarrier(HollowBarrier hollowBarrier) {
        hollowBarriers.add(hollowBarrier);
    }

    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }

    public ArrayList<HollowBarrier> getHollowBarriers() {
        return hollowBarriers;
    }

    public void removeBarrier(Barrier barrier) {
        barriers.remove(barrier);
        //if(barriers.isEmpty())//this is not pretty
    }

    public void deleteBarrier(Barrier barrier) {
        barrier.destroy();
    }

    public void removeAllBarriers() {
        for (int i = barriers.size()-1; i >= 0; i--) {
            var barrier = barriers.get(i);
            barrier.destroy();
        }
        barriers.clear();

        for (int i = hollowBarriers.size()-1; i >= 0; i--) {
            var barrier = hollowBarriers.get(i);
            barrier.destroy();
        }
        hollowBarriers.clear();
    }

    public BarrierTypes getSelectedBarrierType() {
        return selectedBarrierType;
    }

    public void setSelectedBarrierType(BarrierTypes selectedBarrierType) {
        this.selectedBarrierType = selectedBarrierType;
    }

    public Barrier getClickedBarrier() {
        return clickedBarrier;
    }

    public void setClickedBarrier(Barrier clickedBarrier) {
        this.clickedBarrier = clickedBarrier;
    }

    public Vector getOldLocationOfBarrier() {
        return oldLocationOfBarrier;
    }

    public void setOldLocationOfBarrier(Vector oldLocationOfBarrier) {
        this.oldLocationOfBarrier = oldLocationOfBarrier;
    }

    public Barrier getBarrierByLocation(int x, int y) {
        for (Barrier barrier : barriers) {
            double barrierX = barrier.getPosition().getX();
            double barrierY = barrier.getPosition().getY();
            double barrierWidth = BARRIER_WIDTH;
            double barrierHeight = BARRIER_HEIGHT;

            if (barrier.getBarrierType() == BarrierTypes.EXPLOSIVE) {
                double distanceX = x - barrierX;
                double distanceY = y - barrierY;
                double distanceSquared = distanceX * distanceX + distanceY * distanceY;
                double radiusSquared = EXPLOSIVE_RADIUS * EXPLOSIVE_RADIUS;

                if (distanceSquared <= radiusSquared) {
                    return barrier;
                }
            } else {
                if (x >= barrierX && x <= barrierX + barrierWidth &&
                        y >= barrierY && y <= barrierY + barrierHeight) {
                    return barrier;
                }
            }
        }
        return null;
    }
    public boolean validateBarrierPlacement(int x, int y) {
        // this method allows 6 rows of barriers to be placed
        return (y <= Constants.SCREEN_HEIGHT-300);
    }

    public boolean isBarrierColliding(int x, int y) {
        for (Barrier barrier : barriers) {
            double barrierX = barrier.getPosition().getX();
            double barrierY = barrier.getPosition().getY();

            // Define the width and height for a rectangular barrier
            double barrierWidth = BARRIER_WIDTH;
            double barrierHeight = BARRIER_HEIGHT;

            if (barrier.getBarrierType() == BarrierTypes.EXPLOSIVE) {
                // For explosive barriers, check collision as circles
                double dx = x - barrierX;
                double dy = y - barrierY;
                double distanceSquared = dx * dx + dy * dy;
                double combinedRadius = EXPLOSIVE_RADIUS + EXPLOSIVE_RADIUS; // If you are placing another explosive
                double combinedRadiusSquared = combinedRadius * combinedRadius;

                if (distanceSquared < combinedRadiusSquared) {
                    return true; // Collision detected
                }
            } else {
                // For non-explosive barriers, check collision as rectangles
                // Adjust x and y as if placing a new barrier's top left corner
                if ((x + BARRIER_WIDTH > barrierX && x < barrierX + barrierWidth) &&
                        (y + BARRIER_HEIGHT > barrierY && y < barrierY + barrierHeight)) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision detected
    }


    /**
     * Validates the number of barriers against defined criteria.
     * Returns an error message if criteria are not met, otherwise returns null.
     */
    public String validateBarrierCounts(int numOfSimple, int numOfReinforced, int numOfExplosive, int numOfRewarding) {
        StringBuilder errorMessage = new StringBuilder();

        // Check minimum requirements
        if (numOfSimple < MIN_SIMPLE || numOfReinforced < MIN_REINFORCED ||
                numOfExplosive < MIN_EXPLOSIVE || numOfRewarding < MIN_REWARDING) {
            errorMessage.append("Minimum required barriers not met:\n")
                    .append("Simple: ").append(MIN_SIMPLE).append("\n")
                    .append("Reinforced: ").append(MIN_REINFORCED).append("\n")
                    .append("Explosive: ").append(MIN_EXPLOSIVE).append("\n")
                    .append("Rewarding: ").append(MIN_REWARDING).append("\n");
        }

        // Check maximum limits
        if (numOfSimple > MAX_SIMPLE || numOfReinforced > MAX_REINFORCED ||
                numOfExplosive > MAX_EXPLOSIVE || numOfRewarding > MAX_REWARDING) {
            if (!errorMessage.isEmpty()) errorMessage.setLength(0);
            errorMessage.append("Maximum barrier limits exceeded:\n")
                    .append("Simple: ").append(MAX_SIMPLE).append("\n")
                    .append("Reinforced: ").append(MAX_REINFORCED).append("\n")
                    .append("Explosive: ").append(MAX_EXPLOSIVE).append("\n")
                    .append("Rewarding: ").append(MAX_REWARDING);
        }

        return errorMessage.length() == 0 ? null : errorMessage.toString();
    }
    public List<Barrier> getRandomBarriersToFreeze() {
        if(barriers.size() <= 8) return barriers;
        Collections.shuffle(barriers);
        return barriers.subList(0,8);
    }

    /**
     * Method for finding valid barrier placements for Hollow Purple Spell.
     * @return Arraylist of size 8 containing possible position Vectors for new barriers.
     * **/
    public List<Vector> getPossibleHollowBarrierLocations() {
        var allPossibleBarrierLocations = new ArrayList<Vector>();
        int maxX = Constants.SCREEN_WIDTH - 50;
        int minX = 40;
        int maxY = Constants.SCREEN_HEIGHT-300;
        int minY = 40;

        for (int x = minX; x <= maxX; x+= BARRIER_X_OFFSET) {
            for (int y = minY; y <= maxY; y+= BARRIER_Y_OFFSET) {
                if(isBarrierColliding(x, y)) continue;
                allPossibleBarrierLocations.add(new Vector(x, y));
            }
        }

        Collections.shuffle(allPossibleBarrierLocations);
        return allPossibleBarrierLocations.subList(0,8);
    }

    public static void displayBarrierInfo() {
        System.out.println("Barrier Manager Info");
        System.out.println("Explosive Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof ExplosiveBarrier;
        }).toList().size());
        System.out.println("Reinforced Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof ReinforcedBarrier;
        }).toList().size());
        System.out.println("Simple Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof SimpleBarrier;
        }).toList().size());
        System.out.println("Rewarding Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof RewardingBarrier;
        }).toList().size());
    }

}
