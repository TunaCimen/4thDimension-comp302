package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.spells.Canon;
import org.LanceOfDestiny.domain.spells.SpellActivation;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.awt.*;

public class SpellInventory extends JPanel {

    SpellUIElement canonSpell;
    SpellUIElement overwhelmingSpell;
    SpellUIElement expansionSpell;
    SpellUIElement hollowSpell;
    SpellUIElement infiniteVoidSpell;
    SpellUIElement doubleAccelSpell;



    ImageIcon canonIcon,overwhelmingIcon, expansionIcon,hollowSpellIcon, doubleAccelIcon,infVoidIcon;


    public SpellInventory(){

        //Progress Bars
        canonIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.CannonSpell.getImage(), 40,40));
        overwhelmingIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.OverWhelmingSpell.getImage(), 40,40));
        expansionIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.ExpansionSpell.getImage(),40,40));
        hollowSpellIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.HollowSpell.getImage(), 40,40));
        doubleAccelIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.DoubleAccelSpell.getImage(), 40,40));
        infVoidIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.InfVoidSpell.getImage(), 40,40));

        canonSpell = new SpellUIElement(canonIcon, new Dimension(50,50));
        overwhelmingSpell = new SpellUIElement(overwhelmingIcon, new Dimension(50,50));
        expansionSpell = new SpellUIElement(expansionIcon, new Dimension(50,50));
        infiniteVoidSpell = new SpellUIElement(infVoidIcon, new Dimension(50,50));
        doubleAccelSpell = new SpellUIElement(doubleAccelIcon, new Dimension(50,50));
        hollowSpell = new SpellUIElement(hollowSpellIcon, new Dimension(50,50));

        canonSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.CANON);
        });

        overwhelmingSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.OVERWHELMING);
        });

        expansionSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.EXPANSION);
        });

        hollowSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.HOLLOW_PURPLE);
        });

        doubleAccelSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.DOUBLE_ACCEL);
        });

        infiniteVoidSpell.addClickEvent(e -> {
            Events.ActivateSpell.invoke(SpellType.INFINITE_VOID);
        });

        Events.GainSpell.addListener(e->gainSpell((SpellType) e));
        Events.ActivateSpell.addListener(e->loseSpell((SpellType) e));
        Events.ResetSpells.addListener(e->resetSpellUI());

        add(canonSpell);
        add(overwhelmingSpell);
        add(expansionSpell);
        add(infiniteVoidSpell);
        add(doubleAccelSpell);
        add(hollowSpell);
        setVisible(true);

    }

    private void loseSpell(SpellType spellType) {
        switch ((spellType)) {
            case CANON -> canonSpell.disableSpell();
            case EXPANSION -> expansionSpell.disableSpell();
            case OVERWHELMING -> overwhelmingSpell.disableSpell();
            case INFINITE_VOID -> infiniteVoidSpell.disableSpell();
            case HOLLOW_PURPLE -> hollowSpell.disableSpell();
            case DOUBLE_ACCEL -> doubleAccelSpell.disableSpell();
        }
    }

    private void gainSpell(SpellType spellType) {
         switch(spellType){
             case CANON ->  canonSpell.enableSpell();
             case EXPANSION -> expansionSpell.enableSpell();
             case OVERWHELMING -> overwhelmingSpell.enableSpell();
             case INFINITE_VOID -> infiniteVoidSpell.enableSpell();
             case HOLLOW_PURPLE -> hollowSpell.enableSpell();
             case DOUBLE_ACCEL -> doubleAccelSpell.enableSpell();
         }
    }

    public void resetSpellUI(){
        canonSpell.resetSpellUI();
        overwhelmingSpell.resetSpellUI();
        expansionSpell.resetSpellUI();
        hollowSpell.resetSpellUI();
        doubleAccelSpell.resetSpellUI();
        infiniteVoidSpell.resetSpellUI();
    }




}
