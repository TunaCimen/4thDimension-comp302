package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.awt.*;

public class SpellInventory extends JPanel {

    SpellUIElement canonSpell, overwhelmingSpell, expansionSpell, hollowSpell, infiniteVoidSpell, doubleAccelSpell;
    ImageIcon canonIcon, overwhelmingIcon, expansionIcon, hollowSpellIcon, doubleAccelIcon, infVoidIcon;

    public SpellInventory() {
        //Progress Bars
        canonIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.CannonSpell.getImage(), 40, 40));
        overwhelmingIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.OverWhelmingSpell.getImage(), 40, 40));
        expansionIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.ExpansionSpell.getImage(), 40, 40));
        hollowSpellIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.HollowSpell.getImage(), 40, 40));
        doubleAccelIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.DoubleAccelSpell.getImage(), 40, 40));
        infVoidIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.InfVoidSpell.getImage(), 40, 40));

        canonSpell = new SpellUIElement(canonIcon, new Dimension(50, 50));
        overwhelmingSpell = new SpellUIElement(overwhelmingIcon, new Dimension(50, 50));
        expansionSpell = new SpellUIElement(expansionIcon, new Dimension(50, 50));
        infiniteVoidSpell = new SpellUIElement(infVoidIcon, new Dimension(50, 50), Constants.CURSE_DURATION);
        doubleAccelSpell = new SpellUIElement(doubleAccelIcon, new Dimension(50, 50), Constants.CURSE_DURATION);
        hollowSpell = new SpellUIElement(hollowSpellIcon, new Dimension(50, 50), Constants.CURSE_DURATION);

        Event.GainSpell.addListener(e -> gainSpell((SpellType) e));
        Event.ResetSpells.addListener(e -> resetSpellUI());
        activateSpellByClickEvents();
        loseGoodSpellEvents();
        loseSpellEventsMulti();
        loseSpellEventsSingle();

        add(canonSpell);
        add(overwhelmingSpell);
        add(expansionSpell);
        add(infiniteVoidSpell);
        add(doubleAccelSpell);
        add(hollowSpell);
        setVisible(true);
    }

    private void activateSpellByClickEvents() {
        canonSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.CANON);
        });

        overwhelmingSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.OVERWHELMING);
        });

        expansionSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.EXPANSION);
        });

        hollowSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.HOLLOW_PURPLE);
        });

        doubleAccelSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.DOUBLE_ACCEL);
        });

        infiniteVoidSpell.addClickEvent(e -> {
            Event.ActivateSpell.invoke(SpellType.INFINITE_VOID);
        });
    }

    private void loseSpellEventsMulti() {
        Event.SendDoubleAccelUpdate.addRunnableListener(() -> {if (SessionManager.getInstance().isMultiPlayer())doubleAccelSpell.disableSpell();});

        Event.SendHollowPurpleUpdate.addRunnableListener(() -> {if (SessionManager.getInstance().isMultiPlayer())hollowSpell.disableSpell();});

        Event.SendInfiniteVoidUpdate.addRunnableListener(() -> {if (SessionManager.getInstance().isMultiPlayer())infiniteVoidSpell.disableSpell();});
    }

    public void loseSpellEventsSingle() {
        Event.ActivateDoubleAccel.addListener(e -> {
            if ((boolean) e && SessionManager.getInstance().isSinglePlayer()) doubleAccelSpell.disableSpell();
        });

        Event.ActivateHollowPurple.addListener(e -> {
            if ((boolean) e && SessionManager.getInstance().isSinglePlayer()) hollowSpell.disableSpell();
        });

        Event.ActivateInfiniteVoid.addListener(e -> {
            if ((boolean) e && SessionManager.getInstance().isSinglePlayer()) infiniteVoidSpell.disableSpell();
        });
    }

    private void loseGoodSpellEvents() {
        Event.ActivateCanons.addListener(e -> {
            if ((boolean) e) canonSpell.disableSpell();
        });

        Event.ActivateExpansion.addListener(e -> {
            if ((boolean) e) expansionSpell.disableSpell();
        });

        Event.ActivateOverwhelming.addListener(e -> {
            if ((boolean) e) overwhelmingSpell.disableSpell();
        });
    }

    private void gainSpell(SpellType spellType) {
        switch (spellType) {
            case CANON -> canonSpell.enableSpell();
            case EXPANSION -> expansionSpell.enableSpell();
            case OVERWHELMING -> overwhelmingSpell.enableSpell();
            case INFINITE_VOID -> infiniteVoidSpell.enableSpell();
            case HOLLOW_PURPLE -> hollowSpell.enableSpell();
            case DOUBLE_ACCEL -> doubleAccelSpell.enableSpell();
        }
    }

    public void resetSpellUI() {
        canonSpell.resetSpellUI();
        overwhelmingSpell.resetSpellUI();
        expansionSpell.resetSpellUI();
        hollowSpell.resetSpellUI();
        doubleAccelSpell.resetSpellUI();
        infiniteVoidSpell.resetSpellUI();
    }
}

