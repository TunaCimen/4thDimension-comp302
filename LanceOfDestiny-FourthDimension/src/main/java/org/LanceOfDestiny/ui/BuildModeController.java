package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.LanceOfDestiny;
import org.LanceOfDestiny.domain.GameMap;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildModeController {
    private static BuildModeController Instance = null;
    BarrierFactory barrierFactory;
    private List<Barrier> barrierList = new ArrayList<>();

    public List<Barrier> getBarrierList() {
        return barrierList;
    }

    private BuildModeController() {
        barrierFactory = BarrierFactory.getInstance();

    }

    public static synchronized BuildModeController getInstance() {
        if (Instance == null) Instance = new BuildModeController();
        return Instance;
    }


    //Creates obstacles(from given input) and places them in a list where they will be stored and shuffled for randomization
    //todo: obstacleları ekleyebileceğimiz bir listeye ihtiyacımız var gameobjesinde olabilir
    public void addBarriers(int simpNum, int firmNum, int expNum, int giftNum) {

        int i;
        for (i = 0; i < simpNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(new Vector(0,0), BarrierTypes.SIMPLE);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < firmNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REINFORCED);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < expNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(new Vector(0,0), BarrierTypes.EXPLOSIVE);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < giftNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REWARDING);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        // everyday i'm shuffling
        // shuffling for randomization
        Collections.shuffle(LanceOfDestiny.getInstance().getGameMap().getBarriers());
    }


    public void initializeMap() {
        LanceOfDestiny.getInstance().setGameMap(new GameMap());
    }

    public void intializeBarrierCoordinates() {
        barrierList = LanceOfDestiny.getInstance().getGameMap().getBarriers();
        if (barrierList.isEmpty()) return;
        Barrier barrierZero = barrierList.get(0);
        barrierZero.setCoordinate(new Vector(60.0F, 30.0F));
        float barrierAndGapWidth = 55.6F; // 25.6 is the width of the barrier and 30 is the gap between barriers
        int barrierAndGapPairs = 1;
        float verticalGap = 0.0F;
        for(int i =1; i < barrierList.size(); i++) {
            double widthCheck = barrierAndGapWidth * barrierAndGapPairs + 40;
            Barrier nextBarrier = barrierList.get(i);
            if (widthCheck + barrierAndGapWidth > 1280) {
                barrierAndGapPairs = 1;
                verticalGap += 40F;
                barrierAndGapWidth = 55.6F;
            }
            else {
                barrierAndGapPairs++;
            }
            nextBarrier.setCoordinate(new Vector(barrierAndGapWidth, 30.0F + verticalGap));
        }
    }

    // todo: barrier sayısı için limitleri gameview'da belirledim bunu kullanmaya gerek kalmadı
//    public boolean checkObstacleCriteria(int simpNum, int firmNum, int expNum, int giftNum) {
//        return ((75 <= simpNum) && (100 >= simpNum)) &&
//                ((10 <= firmNum) && (20 >= firmNum)) &&
//                ((5 <= expNum) && (10 >= expNum)) &&
//                ((10 <= giftNum) && (20 >= giftNum));
//    }

    public Barrier getObstacleAt(int x, int y) {
        // Code for getting obstacle at given coordinates
        return null;
    }

    public void clearObstacles() {
        // Code for clearing all obstacles
    }

//    public void createNewBarrierAt(int x, int y, String type, BarrierAnimator barrierAnimator) {
//        // Code for creating a new obstacle at given coordinates
//        Barrier barrier = new BarrierFactory().createBarrier(type);
//        //add(obstacle)to_gameobject;
//        barrier.setLocation(x, y);
//        barrierAnimator.drawObstacle(obstacle);
//    }


//    public void removeBarrierAt(int x, int y, Barrier barrier, BarrierAnimator barrierAnimator) {
//        // Code for removing obstacle
//        //graphics.clearRect(); //todo: obstacle cordinatları ve boyutlarına göre clearRect çağrılmalı
//        //remove(obstacle)from_gameobject;
//    }


}