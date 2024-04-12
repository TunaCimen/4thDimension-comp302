package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuildModePanel extends JPanel {
    private static final int CELL_SIZE = 26;
    private static final int SPACING = 2;

    private BuildModeController controller;
    private List<Barrier> barriers;

    public BuildModePanel(BuildModeController controller) {
        this.controller = controller;
        barriers = controller.getBarrierList();
        setPreferredSize(calculatePanelSize()); // Set size dynamically
    }

    private Dimension calculatePanelSize() {
        return new Dimension(1280, 500);  // Example placeholder
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBarriers(g);
    }

    private void drawBarriers(Graphics g) {
        for (Barrier barrier : barriers) {
            Color color = getColorForBarrierType(barrier.barrierType);
            g.setColor(color);
            g.fillRect(barrier.x * CELL_SIZE + SPACING ,
                    barrier.y * CELL_SIZE + SPACING ,
                    CELL_SIZE - 2*SPACING , CELL_SIZE - 2*SPACING);
        }
    }

    private Color getColorForBarrierType(BarrierTypes type) {
        switch (type) { // random colored boxes for demo purposes
            case SIMPLE: return Color.GRAY;
            case REINFORCED: return Color.CYAN;
            case EXPLOSIVE: return Color.RED;
            case REWARDING: return Color.YELLOW;
            default: return Color.BLACK; //
        }
    }
}
