package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;

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
        Event.GainSpell.addListener(this::addSpell);
    }

    public void addSpell(Object spellObject) {
        var spellType = (SpellType) spellObject;
        if (spellType.equals(SpellType.CHANCE)) {
            Event.UpdateChance.invoke(1);
            return;
        }
        if (spellExists(spellType)) {
            return;
        }
        spellMap.put(spellType, true);
    }

    public void activateSpell(SpellType spellType) {
        if (!spellExists(spellType)) return;

        if (spellType.isGood()) {
            if(spellType.isActive) return;
            new SpellActivation(spellType, Constants.SPELL_DURATION).activate();
        }
        else {
            // This will only work if it is multiplayer, no need for a check
            switch (spellType) {
                case HOLLOW_PURPLE -> Event.SendHollowPurpleUpdate.invoke();
                case INFINITE_VOID -> Event.SendInfiniteVoidUpdate.invoke();
                case DOUBLE_ACCEL -> Event.SendDoubleAccelUpdate.invoke();
            }
        }
        removeSpell(spellType);
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
