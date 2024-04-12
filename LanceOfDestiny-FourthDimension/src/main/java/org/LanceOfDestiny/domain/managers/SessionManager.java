package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;

public class SessionManager {

    private static SessionManager instance;
    private Player player;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initializeSession() {
        this.player = new Player();
        this.magicalStaff = new MagicalStaff(Constants.STAFF_POSITION);
        this.fireBall = new FireBall(Constants.FIREBALL_POSITION);
        magicalStaff.setFireBall(fireBall);
        player.setMagicalStaff(magicalStaff);
        ManagerHub.getInstance().initDependencies(magicalStaff, player, fireBall, this);
    }
}
