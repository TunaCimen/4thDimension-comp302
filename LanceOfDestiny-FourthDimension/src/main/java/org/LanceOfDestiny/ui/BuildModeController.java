package org.LanceOfDestiny.ui;

import java.util.Collections;

public class BuildModeController {
    private static BuildModeController Instance = null;
    //private final ObstacleFactory obstacleFac = new ObstacleFactory();

    private BuildModeController() {
        //initiate the obstacle factory
        //obstacleFac = new Obstaclefac();

    }

    public static BuildModeController getInstance() {
        if (Instance == null) Instance = new BuildModeController();
        return Instance;
    }


    //Creates obstacles(from given input) and places them in a list where they will be stored and shuffled for randomization
    //todo: obstacle factory createObstacle methodu oluşturulmalı
    //todo: obstacleları ekleyebileceğimiz bir listeye ihtiyacımız var gameobjesinde olabilir
    public void setObstacles(int simpNum, int firmNum, int expNum, int giftNum) {

        int i;
        for (i = 0; i < simpNum; i++) {
            Obstacle obstacle = obstacleFac.createObstacle("SimpleBarrier");
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < firmNum; i++) {
            Obstacle obstacle = obstacleFac.createObstacle("FirmBarrier");
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < expNum; i++) {
            Obstacle obstacle = obstacleFac.createObstacle("ExplosiveBarrier");
            //add(obstacle)to_somewhere;
        }
        for (i = 0; i < giftNum; i++) {
            Obstacle obstacle = obstacleFac.createObstacle("GiftBarrier");
            //add(obstacle)to_somewhere;
        }

        Collections.shuffle(obstacleList);
    }

    // todo: barrier sayısı için upper limit belirlenmeli
    public boolean checkObstacleCriteria(int simpNum, int firmNum, int expNum, int giftNum) {
        return ((75 <= simpNum) && (100 >= simpNum)) &&
                ((10 <= firmNum) && (20 >= firmNum)) &&
                ((5 <= expNum) && (10 >= expNum)) &&
                ((10 <= giftNum) && (20 >= giftNum));
    }

    public Obstacle getObstacleAt(int x, int y) {
        // Code for getting obstacle at given coordinates
    }

    public void clearObstacles() {
        // Code for clearing all obstacles
    }

    public void createNewObstacle(int x, int y, String type, ObstacleAnimator obstacleAnimator) {
        // Code for creating a new obstacle at given coordinates
        Obstacle obstacle = new ObstacleFactory().createObstacle(type);
        //add(obstacle)to_gameobject;
        obstacle.setLocation(x, y);
        obstacleAnimator.drawObstacle(obstacle);
    }


    public void removeObstacleAt(int x, int y, Obstacle obstacle, ObstacleAnimator obstacleAnimator) {
        // Code for removing obstacle
        //graphics.clearRect(); //todo: obstacle cordinatları ve boyutlarına göre clearRect çağrılmalı
        //remove(obstacle)from_gameobject;
    }


}