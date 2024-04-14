package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.EventSystem.Events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// spells gained by the player will be held here
// needs a timer object to activate and deactivate spell
public class SpellContainer {
    private List<Spell> spells;
    private HashMap<SpellType, Boolean> spellMap = new HashMap<>(); // for saving to database


    public SpellContainer() {
        spells = new LinkedList<>();
        for (SpellType spellType : SpellType.values()) {
            if(spellType.equals(SpellType.CHANCE)) continue;
            spellMap.put(spellType, false);
        }
        Events.GainSpell.addListener(this::addSpell);
    }

    public void addSpell(Object spellObject) {
        var spell = (Spell) spellObject;
        if(spell.getSpellType().equals(SpellType.CHANCE)){
            Events.UpdateChance.invoke(1);
            return;
        }

        if(spellExists(spell.getSpellType()))
            return;
        spells.add(spell);
        spellMap.put(spell.getSpellType(), true);
    }

    public void activateSpell(SpellType spellType) {
        if(!spellExists(spellType)) return;

        var spell = getSpell(spellType);
        spell.activateSpell();

        spellMap.put(spellType, false);
        spells.remove(spell);

        //spell.deactivateSpell(); // after timer ends
    }

    public void removeSpell(Spell spell) {
        spells.remove(spell);
        spellMap.put(spell.getSpellType(), false);
    }

    public Spell getSpell(SpellType spellType) {
        if (!spellExists(spellType)) {
            return null;
        }

        for (Spell spell : spells) {
            if (spell.getSpellType() == spellType) {
                return spell;
            }
        }
        return null;
    }

    private boolean spellExists(SpellType spellType) {
        return spellMap.get(spellType);
    }

    public HashMap<SpellType, Boolean> getSpellMap() {
        return spellMap;
    }
}
