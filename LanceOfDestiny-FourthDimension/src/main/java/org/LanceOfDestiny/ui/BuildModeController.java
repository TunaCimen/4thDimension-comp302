package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;

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
    //todo: obstacle factory createObstacle methodu oluşturulmalı
    //todo: obstacleları ekleyebileceğimiz bir listeye ihtiyacımız var gameobjesinde olabilir
    public void setObstacles(int simpNum, int firmNum, int expNum, int giftNum) {

        int i;
        for (i = 0; i < simpNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(0, 0, BarrierTypes.SIMPLE);
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < firmNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(0, 0, BarrierTypes.REINFORCED);
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < expNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(0, 0, BarrierTypes.EXPLOSIVE);
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < giftNum; i++) {
            Barrier barrier = barrierFactory.createBarrier(0, 0, BarrierTypes.REWARDING);
            //add(obstacle)to_somewhere;
        }

//        Collections.shuffle(list-of-obstacles-where-they-are-stored);
    }

    // todo: barrier sayısı için upper limit belirlenmeli
    public boolean checkObstacleCriteria(int simpNum, int firmNum, int expNum, int giftNum) {
        return ((75 <= simpNum) && (100 >= simpNum)) &&
                ((10 <= firmNum) && (20 >= firmNum)) &&
                ((5 <= expNum) && (10 >= expNum)) &&
                ((10 <= giftNum) && (20 >= giftNum));
    }

    public Barrier getObstacleAt(int x, int y) {
        // Code for getting obstacle at given coordinates
        return null;
    }

    public void clearObstacles() {
        // Code for clearing all obstacles
    }

//    public void createNewBarrier(int x, int y, String type, ObstacleAnimator obstacleAnimator) {
//        // Code for creating a new obstacle at given coordinates
//        Obstacle obstacle = new ObstacleFactory().createObstacle(type);
//        //add(obstacle)to_gameobject;
//        obstacle.setLocation(x, y);
//        obstacleAnimator.drawObstacle(obstacle);
//    }


//    public void removeObstacleAt(int x, int y, Barrier barrier, ObstacleAnimator obstacleAnimator) {
//        // Code for removing obstacle
//        //graphics.clearRect(); //todo: obstacle cordinatları ve boyutlarına göre clearRect çağrılmalı
//        //remove(obstacle)from_gameobject;
//    }


}