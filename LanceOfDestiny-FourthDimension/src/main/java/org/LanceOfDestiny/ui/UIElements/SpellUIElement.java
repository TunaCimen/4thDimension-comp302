package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.TimedAction;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import org.LanceOfDestiny.domain.events.Event;

public class SpellUIElement extends JLayeredPane {

    private final ImageIcon imageIcon;
    private final ImageIcon reducedImageIcon;
    private final JButton spellButton;
    private final JProgressBar progressBar;
    TimedAction spellAction;


    public SpellUIElement(ImageIcon imageIcon, Dimension size) {
        this.imageIcon = imageIcon;
        reducedImageIcon = ImageOperations.reducedTransparencyImageIcon(imageIcon);
        this.setPreferredSize(size);
        progressBar = new JProgressBar(0, Constants.SPELL_DURATION);
        spellButton = new JButton();
        spellButton.setBorder(BorderFactory.createEmptyBorder());
        spellButton.setContentAreaFilled(false);
        spellButton.setFocusable(false);
        spellButton.setBounds(0, 0, size.width, size.height);
        progressBar.setBounds(5, 0, size.width - 10, 10);
        progressBar.setOpaque(false);
        add(spellButton, Integer.valueOf(1));
        add(progressBar, Integer.valueOf(2));
        spellButton.setIcon(reducedImageIcon);
    }

    public SpellUIElement(ImageIcon imageIcon, Dimension size, int duration) {
        this(imageIcon, size);
        this.progressBar.setMaximum(duration);
    }

    public void disableSpell() {
        spellButton.setIcon(reducedImageIcon);
        activateSpell();
    }

    public void enableSpell() {
        spellButton.setIcon(imageIcon);
    }

    public void setProgressBarValue(int i) {
        progressBar.setValue(i);
    }

    public void activateSpell() {
        System.out.println("Spell activated");
        spellAction = new MyTimedAction();
        spellAction.start();
    }

    public void resetSpellUI() {
        System.out.println("Spell UI is reset");
        spellButton.setIcon(reducedImageIcon);
        progressBar.setValue(0);
        if(spellAction!= null)spellAction.kill();

    }

    public void addClickEvent(ActionListener l) {
        spellButton.addActionListener(l);
    }

    private class MyTimedAction extends TimedAction {
        public MyTimedAction() {
            super(SpellUIElement.this.progressBar.getMaximum());
        }
        @Override
        public void onUpdate() {
            System.out.println("I am changing progress bar value and you can do nothing dumass");
            setProgressBarValue(progressBar.getMaximum() - getTimePassed());
        }
    }
}
