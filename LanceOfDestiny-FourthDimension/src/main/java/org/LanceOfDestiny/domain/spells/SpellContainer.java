package org.LanceOfDestiny.domain.abilities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// abilities gained by the player will be held here
public class SpellContainer {
    private List<Spell> spells;
    private HashMap<SpellType, Boolean> spellMap = new HashMap<>(); // for saving to database

    public SpellContainer() {
        spells = new LinkedList<>();
        for (SpellType spellType : SpellType.values()) {
            spellMap.put(spellType, false);
        }
    }

    public void addSpell(Spell spell) {
        if(spellExists(spell.getSpellType()))
            return;

        spells.add(spell);
        spellMap.put(spell.getSpellType(), true);
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
