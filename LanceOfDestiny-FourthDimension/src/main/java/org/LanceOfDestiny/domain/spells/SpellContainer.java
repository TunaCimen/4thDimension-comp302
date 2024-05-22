package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;

import java.util.HashMap;
/**
 * Spells gained by the Player will be held here.
 **/
public class SpellContainer {
    private HashMap<SpellType, Boolean> spellMap = new HashMap<>();

    public SpellContainer() {
        for (SpellType spellType : SpellType.values()) {
            if (spellType.equals(SpellType.CHANCE)) continue;
            spellMap.put(spellType, false);
        }
        Events.GainSpell.addListener(this::addSpell);
    }

    public void addSpell(Object spellObject) {
        var spellType = (SpellType) spellObject;
        if (spellType.equals(SpellType.CHANCE)) {
            Events.UpdateChance.invoke(1);
            return;
        }
        if (spellExists(spellType)) {
            return;
        }
        spellMap.put(spellType, true);
    }

    public void activateSpell(SpellType spellType) {
        if (!spellExists(spellType)) return;
        if (spellType.isGood())
            new SpellActivation(spellType, Constants.SPELL_DURATION).activate();
        else {
            //Events.ActivateCurse.invoke(spellType);
            // todo: ege bad spell karşı tarafa gönderme
        }
        removeSpell(spellType);
        Events.ActivateSpellUI.invoke(spellType);
    }

    public void removeSpell(SpellType spellType) {
        spellMap.put(spellType, false);
    }

    private boolean spellExists(SpellType spellType) {
        return spellMap.get(spellType);
    }

    public HashMap<SpellType, Boolean> getSpellMap() {
        return spellMap;
    }
}
