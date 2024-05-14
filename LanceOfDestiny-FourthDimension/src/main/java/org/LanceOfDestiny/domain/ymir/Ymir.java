package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.spells.curses.CurseType;

import java.util.ArrayList;

public class Ymir extends MonoBehaviour {

    private ArrayList<CurseType> recentCurses = new ArrayList<>();

     public Ymir() {
        super();
    }

    private void addCurseToRecent(CurseType curseType){
         recentCurses.add(curseType);
    }

    public void activateRandomCurse() {
         return;
    }

    public CurseType getRandomCurse() {
         return CurseType.DOUBLE_ACCEL;
    }








}
