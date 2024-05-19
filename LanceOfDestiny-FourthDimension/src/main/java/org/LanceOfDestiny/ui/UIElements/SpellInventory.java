package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;
import org.LanceOfDestiny.ui.UIElements.SpellUIElement;

import javax.swing.*;
import java.awt.*;

public class SpellInventory extends JPanel {

    SpellUIElement canonSpell;
    SpellUIElement overwhelmingSpell;
    SpellUIElement expansionSpell;

    JLayeredPane cannonPane,overwhelmPane, expansionPane;

    JLabel hollowSpell;

    JLabel doubleAccel;

    ImageIcon canonIcon,overwhelmingIcon, expansionIcon,hollowSpellIcon, doubleAccelIcon;
    ImageIcon reducedCanonIcon, reducedOverwhelmingIcon, reducedExpansionIcon;

    JProgressBar activeGoodSpellBar, activeBadSpellBar;


    public SpellInventory(){

        //Progress Bars
        hollowSpell = new JLabel();
        doubleAccel = new JLabel();




        canonIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.CannonSpell.getImage(), 40,40));

        overwhelmingIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.OverWhelmingSpell.getImage(), 40,40));
        expansionIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.ExpansionSpell.getImage(),40,40));
        hollowSpellIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.HollowSpell.getImage(), 40,40));
        doubleAccelIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.DoubleAccelSpell.getImage(), 40,40));

        reducedOverwhelmingIcon =ImageOperations.reducedTransparencyImageIcon(overwhelmingIcon);
        reducedExpansionIcon = ImageOperations.reducedTransparencyImageIcon(expansionIcon);

        canonSpell = new SpellUIElement(canonIcon, new Dimension(50,50));
        overwhelmingSpell = new SpellUIElement(overwhelmingIcon, new Dimension(50,50));
        expansionSpell = new SpellUIElement(expansionIcon, new Dimension(50,50));

        canonSpell.addClickEvent(e->Events.TryUsingSpell.invoke(SpellType.CANON));
        overwhelmingSpell.addClickEvent(e->Events.TryUsingSpell.invoke(SpellType.OVERWHELMING));
        expansionSpell.addClickEvent(e->Events.TryUsingSpell.invoke(SpellType.EXPANSION));

        hollowSpell.setIcon(hollowSpellIcon);
        doubleAccel.setIcon(doubleAccelIcon);

        Events.GainSpell.addListener(e->gainSpell((SpellType) e));
        Events.ActivateSpellUI.addListener(e-> loseSpell((SpellType) e));
        Events.ResetSpells.addListener(e->resetSpellUI());



        add(canonSpell);
        add(overwhelmingSpell);
        add(expansionSpell);
        add(hollowSpell);
        add(doubleAccel);
        setVisible(true);

    }

    private void loseSpell(SpellType spellType) {
        switch ((spellType)) {
            case CANON -> canonSpell.disableSpell();
            case EXPANSION -> expansionSpell.disableSpell();
            case OVERWHELMING -> overwhelmingSpell.disableSpell();
        }
    }

    private void gainSpell(SpellType spellType) {
         switch(spellType){
             case CANON ->  canonSpell.enableSpell();
             case EXPANSION -> expansionSpell.enableSpell();
             case OVERWHELMING -> overwhelmingSpell.enableSpell();
         }
    }

    public void resetSpellUI(){
        canonSpell.resetSpellUI();
        overwhelmingSpell.resetSpellUI();
        expansionSpell.resetSpellUI();
    }




}
