package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;

public class SpellInventory extends JPanel {

    JButton canonSpell;
    JLabel overwhelmingSpell;
    JLabel expansionSpell;

    ImageIcon canonIcon,overwhelmingIcon, expansionIcon;
    ImageIcon reducedCanonIcon, reducedOverwhelmingIcon, reducedExpansionIcon;

    public SpellInventory(){
        canonSpell = new JButton();
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
        Events.ActivateSpellUI.addListener(e-> loseSpell((SpellType) e));

        add(canonSpell);
        add(overwhelmingSpell);
        add(expansionSpell);
        setVisible(true);

    }

    private void loseSpell(SpellType spellType) {
        switch ((spellType)) {
            case CANON -> canonSpell.setIcon(reducedCanonIcon);
            case EXPANSION -> expansionSpell.setIcon(reducedExpansionIcon);
            case OVERWHELMING -> overwhelmingSpell.setIcon(reducedOverwhelmingIcon);
        }
    }

    private void gainSpell(SpellType spellType) {
         switch(spellType){
             case CANON ->  canonSpell.setIcon(canonIcon);
             case EXPANSION -> expansionSpell.setIcon(expansionIcon);
             case OVERWHELMING -> overwhelmingSpell.setIcon(overwhelmingIcon);
         }
    }
}
