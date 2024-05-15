package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.managers.Status;
import org.LanceOfDestiny.domain.physics.Vector;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildModeMouseHandler extends MouseHandler {

    public BuildModeMouseHandler(JPanel panel){
        super(panel);
    }



    @Override
    public void handleMousePress(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) != null) {
            BarrierManager.getInstance().setClickedBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y));
            BarrierManager.getInstance().setOldLocationOfBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y).getPosition());
            System.out.println("Mouse Press: Barrier is clicked"+ BarrierManager.getInstance().getBarrierByLocation(x, y).toString());
        }
        else {
            System.out.println("Mouse Press: No barrier is clicked");
        }

    }

    @Override
    protected void handleMouseRelease(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        BarrierManager barrierManager = BarrierManager.getInstance();
        Barrier clickedBarrier = barrierManager.getClickedBarrier();
        Barrier barrierAtLocation = barrierManager.getBarrierByLocation(x, y);

        // Check if there's no barrier at the mouse release location
        if (!(barrierManager.isBarrierColliding(x, y))) {
            // Validate placement and check for collisions
            if (barrierManager.validateBarrierPlacement(x, y)) {
                // Check if a barrier was previously selected
                if (clickedBarrier != null) {
                    // Move the barrier to the new location
                    clickedBarrier.setPosition(new Vector(x, y));

                    // Update the game view

                    parentPanel.repaint();

                    // Log the barrier movement
                    System.out.println(String.format("Barrier moved to new location: %s from %s to %s",
                            clickedBarrier,
                            barrierManager.getOldLocationOfBarrier(),
                            clickedBarrier.getPosition()));

                    // Reset the clicked barrier reference
                    barrierManager.setClickedBarrier(null);
                } else {
                    System.out.println("No barrier was clicked at : " + x + " " + y);
                }
            }
        } else {
            if (SwingUtilities.isRightMouseButton(e)) {
                // do something for right click press and release
            }
        }
    }

    @Override
    protected void handleMouseClick(MouseEvent e) {
        //debug
        System.out.println("Mouse Clicked");

        int x = e.getX();
        int y = e.getY();

        if (e.getClickCount() == 2) {

            handleDoubleClick(x, y);

        } else if (SwingUtilities.isRightMouseButton(e)) {

            handleRightClick(x, y);
        }
    }


    @Override
    protected void handleDoubleClick(int x, int y) {
        //debug
        System.out.println("Double Click is detected ");

        if (BarrierManager.getInstance().getBarrierByLocation(x, y) == null) {
            if (BarrierManager.getInstance().validateBarrierPlacement(x, y) && !(BarrierManager.getInstance().isBarrierColliding(x, y))) {

                BarrierFactory.createBarrier(new Vector(x, y), BarrierManager.getInstance().getSelectedBarrierType());

                //debug
                //print last element in the list
                System.out.println("Barrier selected: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
                System.out.println("Last Barrier added: " + BarrierManager.getInstance().getBarriers().get(BarrierManager.getInstance().getBarriers().size()-1).toString());
                BarrierManager.getInstance().getBarrierByLocation(x, y).getSprite().setVisible(true);
                parentPanel.repaint();
            }

        }
    }

    @Override
    protected void handleRightClick(int x, int y) {
        //debug
        System.out.println("Right Click is detected ");

        if (BarrierManager.getInstance().getBarrierByLocation(x, y) != null) {
            BarrierManager.getInstance().deleteBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y));

            //debug
            System.out.println("Barrier removed: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
            parentPanel.repaint();

        }
    }
}
