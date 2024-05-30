package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.*;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.LanceOfDestiny.domain.Constants.*;

public class BarrierManager {

    private static BarrierManager instance;
    public ArrayList<Barrier> barriers;
    private BarrierTypes selectedBarrierType;
    private Barrier clickedBarrier;
    private Vector oldLocationOfBarrier;

    private BarrierManager() {
        barriers = new ArrayList<>();
        selectedBarrierType = BarrierTypes.SIMPLE;
        Event.EndGame.addRunnableListener(this::deleteAllBarriers);
        Event.Reset.addRunnableListener(this::deleteAllBarriers);
    }

    public static BarrierManager getInstance() {
        if (instance == null) instance = new BarrierManager();
        return instance;
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }
    /**
     * This method removes the given barrier from the barriers list. <p>
     * Note: It does not ensure that the barrier is destroyed.
     * @param barrier barrier to remove from the barriers list.
     */
    public void removeBarrier(Barrier barrier) {
        barriers.remove(barrier);
    }
    /**
     * This method successfully destroys the given barrier and
     * deletes it from both barriers and game objects list.
     * @param barrier barrier to be destroyed and deleted from the game.
     */
    public void deleteBarrier(Barrier barrier) {
        barrier.destroy();
    }
    /**
     * This method successfully destroys all barriers and
     * deletes them from both barriers and game objects list.
     */
    public void deleteAllBarriers() {
        for (int i = barriers.size()-1; i >= 0; i--) {
            var barrier = barriers.get(i);
            barrier.destroy();
        }
        barriers.clear();
    }

    public Barrier getBarrierByLocation(int x, int y) {
        for (Barrier barrier : barriers) {
            double barrierX = barrier.getPosition().getX();
            double barrierY = barrier.getPosition().getY();

            if (barrier.getBarrierType() == BarrierTypes.EXPLOSIVE) {
                double distanceX = x - barrierX;
                double distanceY = y - barrierY;
                double distanceSquared = distanceX * distanceX + distanceY * distanceY;
                double radiusSquared = EXPLOSIVE_RADIUS * EXPLOSIVE_RADIUS;

                if (distanceSquared <= radiusSquared) {
                    return barrier;
                }
            } else {
                if (x >= barrierX && x <= barrierX + BARRIER_WIDTH &&
                        y >= barrierY && y <= barrierY + BARRIER_HEIGHT) {
                    return barrier;
                }
            }
        }
        return null;
    }

    public boolean isBarrierColliding(int x, int y) {
        for (Barrier barrier : barriers) {
            double barrierX = barrier.getPosition().getX();
            double barrierY = barrier.getPosition().getY();

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
                if ((x + BARRIER_WIDTH > barrierX && x < barrierX + BARRIER_WIDTH) &&
                        (y + BARRIER_HEIGHT > barrierY && y < barrierY + BARRIER_HEIGHT)) {
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
    /**
     * Method for getting random barriers from barriers list according to the given amount.
     * @param amount integer specifying the amount of random barriers to get
     * @return Arraylist of size amount that contains random Barrier instances from the barriers list.
     */
    public ArrayList<Barrier> getRandomBarriersWithAmount(int amount) {
        if (barriers.size() <= amount) {
            return new ArrayList<>(barriers);
        }

        ArrayList<Barrier> shuffledBarriers = new ArrayList<>(barriers);
        Collections.shuffle(shuffledBarriers);

        return new ArrayList<>(shuffledBarriers.subList(0, amount));
    }

    /**
     * Method for finding valid barrier placements for Hollow Purple Spell.
     * @param amount integer specifying the amount of random possible barrier locations to get
     * @return Arraylist of size amount containing possible position Vectors for new barriers.
     */
    public List<Vector> getPossibleHollowBarrierLocations(int amount) {
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
        return allPossibleBarrierLocations.subList(0,amount);
    }

    public void displayBarrierInfo() {
        System.out.println("Barrier Manager Info");
        System.out.println("Explosive Barrier Count: " + barriers.stream().filter(e -> e instanceof ExplosiveBarrier).toList().size());
        System.out.println("Reinforced Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof ReinforcedBarrier;
        }).toList().size());
        System.out.println("Simple Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof SimpleBarrier;
        }).toList().size());
        System.out.println("Rewarding Barrier Count: " + barriers.stream().filter(e -> {
            return e instanceof RewardingBarrier;
        }).toList().size());

        System.out.println("IsMovingBarrierCount: " + barriers.stream().filter(Barrier::isMoving).toList().size());
    }

    /**
     * This method allows 6 rows of barriers to be placed
     **/
    public boolean validateBarrierPlacement(int x, int y) {
        return (y <= Constants.SCREEN_HEIGHT-300);
    }

    public ArrayList<Barrier> getBarriers() {
        return barriers;
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

    public String serializeAllBarriers() {
        // use Barrier::toSerializedString for all barriers, join them using ;
        StringBuilder serializedData = new StringBuilder();
        for (Barrier barrier : barriers) {
            serializedData.append(barrier.toSerializedString()).append(";");
        }
        return serializedData.toString();
    }

    public Barrier loadBarrierFromString(String barrierInfo) {
        String[] parts = barrierInfo.split(",");
        if (parts.length < 4) return null;

        String barrierType = parts[0].trim();
        int hitsLeft = Integer.parseInt(parts[1].trim());
        String[] coordinateParts = {parts[2], parts[3]};
        boolean moving = Boolean.parseBoolean(parts[4].trim());
        Vector position = new Vector(Float.parseFloat(coordinateParts[0]), Float.parseFloat(coordinateParts[1]));

        return BarrierFactory.createBarrier(position, barrierType, hitsLeft, moving);
    }

    public void loadBarriersFromString(String barriersData) {
        String[] barriersArray = barriersData.split(";");
        for (String barrierInfo : barriersArray) {
           loadBarrierFromString(barrierInfo);
        }
    }

    public int[] generateRandomValidBarrierCounts() {
        Random random = new Random();
        int numOfSimple = random.nextInt(MAX_SIMPLE - MIN_SIMPLE + 1) + MIN_SIMPLE;
        int numOfReinforced = random.nextInt(MAX_REINFORCED - MIN_REINFORCED + 1) + MIN_REINFORCED;
        int numOfExplosive = random.nextInt(MAX_EXPLOSIVE - MIN_EXPLOSIVE + 1) + MIN_EXPLOSIVE;
        int numOfRewarding = random.nextInt(MAX_REWARDING - MIN_REWARDING + 1) + MIN_REWARDING;
        String validationError = validateBarrierCounts(numOfSimple, numOfReinforced, numOfExplosive, numOfRewarding);
        if (validationError != null) {
            return generateRandomValidBarrierCounts();
        }
        return new int[]{numOfSimple, numOfReinforced, numOfExplosive, numOfRewarding};
    }

    public int[] getBarrierCounts() {
        int numOfSimple = (int) barriers.stream().filter(barrier -> barrier instanceof SimpleBarrier).count();
        int numOfReinforced = (int) barriers.stream().filter(barrier -> barrier instanceof ReinforcedBarrier).count();
        int numOfExplosive = (int) barriers.stream().filter(barrier -> barrier instanceof ExplosiveBarrier).count();
        int numOfRewarding = (int) barriers.stream().filter(barrier -> barrier instanceof RewardingBarrier).count();
        return new int[]{numOfSimple, numOfReinforced, numOfExplosive, numOfRewarding};
    }

}
