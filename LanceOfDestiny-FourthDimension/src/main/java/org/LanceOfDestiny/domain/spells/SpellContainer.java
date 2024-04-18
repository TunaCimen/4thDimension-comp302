package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Spells gained by the Player will be held here.
 **/
public class SpellContainer extends MonoBehaviour {
    private List<Spell> spells;
    private HashMap<SpellType, Boolean> spellMap = new HashMap<>();
    private final LoopExecutor loopExecutor = SessionManager.getInstance().getLoopExecutor();
    private boolean isSpellActive = false;
    private int spellEndSecond;
    private final int spellDurationSecond = 30;
    private SpellType activeSpellType;


    public SpellContainer() {
        spells = new LinkedList<>();
        for (SpellType spellType : SpellType.values()) {
            if (spellType.equals(SpellType.CHANCE)) continue;
            spellMap.put(spellType, false);
        }
        Events.GainSpell.addListener(this::addSpell);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        super.update();
        if (isSpellActive) {
            if (loopExecutor.getSecondsPassed() >= spellEndSecond ) {
                deactivateSpell(activeSpellType);
            }
        }
    }

    public void addSpell(Object spellObject) {
        var spellType = (SpellType) spellObject;
        var spell = SpellFactory.createSpell(spellType);

        if (spellType.equals(SpellType.CHANCE)) {
            Events.UpdateChance.invoke(1);
            return;
        }

        if (spellExists(spellType)){
            return;
        }

        spells.add(spell);
        spellMap.put(spellType, true);
    }

    public void activateSpell(SpellType spellType) {
        if (isSpellActive) return;
        if (!spellExists(spellType)) return;

        var spell = getSpell(spellType);
        spell.activateSpell();

        isSpellActive = true;
        activeSpellType = spellType;
        spellEndSecond = loopExecutor.getSecondsPassed() + spellDurationSecond;
    }

    public void deactivateSpell(SpellType spellType) {
        var spell = getSpell(spellType);
        spell.deactivateSpell();
        removeSpell(spell);
        isSpellActive = false;
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
