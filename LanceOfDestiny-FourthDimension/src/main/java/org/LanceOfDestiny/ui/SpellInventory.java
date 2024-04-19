package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpellInventory extends JPanel {

    JLabel canonSpell;
    JLabel overwhelmingSpell;
    JLabel expansionSpell;

    ImageIcon canonIcon,overwhelmingIcon, expansionIcon;
    ImageIcon reducedCanonIcon, reducedOverwhelmingIcon, reducedExpansionIcon;

    public SpellInventory(){
        canonSpell = new JLabel();
        overwhelmingSpell = new JLabel();
        expansionSpell = new JLabel();
        canonIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.CannonSpell.getImage(), 40,40));
        overwhelmingIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.OverWhelmingSpell.getImage(), 30,30));
        expansionIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.ExpansionSpell.getImage(),30,30));

        reducedCanonIcon = ImageOperations.reducedTransparencyImageIcon(canonIcon);
        reducedOverwhelmingIcon =ImageOperations.reducedTransparencyImageIcon(overwhelmingIcon);
        reducedExpansionIcon = ImageOperations.reducedTransparencyImageIcon(expansionIcon);

        canonSpell.setIcon(reducedCanonIcon);
        expansionSpell.setIcon(reducedExpansionIcon);
        overwhelmingSpell.setIcon(reducedOverwhelmingIcon);

        Events.GainSpell.addListener(e->gainSpell((SpellType) e));


        add(canonSpell);
        add(overwhelmingSpell);
        add(expansionSpell);
        setVisible(true);

    }

    private void gainSpell(SpellType spellType) {
         switch(spellType){
             case CANON -> gainCanonSpell();
             case EXPANSION -> gainExpansionSpell();
             case OVERWHELMING -> gainOverwhelmingSpell();
         }
    }


    public void gainCanonSpell(){
            canonSpell.setIcon(canonIcon);
    }
    public void gainOverwhelmingSpell(){
        overwhelmingSpell.setIcon(overwhelmingIcon);
    }
    public void gainExpansionSpell(){
        expansionSpell.setIcon(expansionIcon);
    }
}
