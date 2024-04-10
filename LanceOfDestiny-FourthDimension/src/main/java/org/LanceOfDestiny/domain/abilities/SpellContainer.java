package org.LanceOfDestiny.domain.abilities;

import java.util.LinkedList;
import java.util.List;

// abilities gained by the player will be held here
public class SpellContainer {
    private List<Spell> spells;

    public SpellContainer() {
        spells = new LinkedList<>();
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
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
        for (Spell spell : spells) {
            if (spell.getSpellType() == spellType) {
                return true;
            }
        }
        return false;
    }
}
