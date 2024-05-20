package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.util.HashMap;

/**
 * Spells gained by the Player will be held here.
 **/
public class SpellContainer extends MonoBehaviour {
    private HashMap<SpellType, Boolean> spellMap = new HashMap<>();
    private final LoopExecutor loopExecutor = SessionManager.getInstance().getLoopExecutor();
    private boolean isSpellActive = false;
    private int spellEndSecond;
    private final int spellDurationSecond = Constants.SPELL_DURATION;
    private SpellType activeSpellType;

    public SpellContainer() {
        for (SpellType spellType : SpellType.values()) {
            if (spellType.equals(SpellType.CHANCE)) continue;
            spellMap.put(spellType, false);
        }
        Events.GainSpell.addListener(this::addSpell);
        Events.LoadGame.addRunnableListener(this::deactivateActiveSpell);
        Events.Reset.addRunnableListener(this::deactivateActiveSpell);
    }

    @Override
    public void update() {
        super.update();
        if (isSpellActive) {

            if (loopExecutor.getSecondsPassed() >= spellEndSecond) {
                deactivateActiveSpell();
            }
        }
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
        if (isSpellActive) return;
        isSpellActive = true;
        activeSpellType = spellType;
        spellType.activate();
        removeSpell(spellType);
        spellEndSecond = loopExecutor.getSecondsPassed() + spellDurationSecond;
        Events.ActivateSpellUI.invoke(spellType);
    }

    public void deactivateActiveSpell() {
        isSpellActive = false;
        if (activeSpellType != null) activeSpellType.deactivate();
        activeSpellType = null;
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
