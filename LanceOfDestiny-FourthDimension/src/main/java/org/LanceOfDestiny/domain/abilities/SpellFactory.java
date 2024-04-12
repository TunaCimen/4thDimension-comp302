package org.LanceOfDestiny.domain.abilities;

public class SpellFactory {

    private static SpellFactory instance;

    private SpellFactory() {

    }

    public static SpellFactory getInstance() {
        if (instance == null) {
            instance = new SpellFactory();
        }
        return instance;
    }

    public Spell createSpell(SpellType spellType) {
        Spell spell = switch (spellType) {
            case CANON -> new CanonSpell();
            case CHANCE -> new ChanceSpell();
            case EXPANSION -> new ExpansionSpell();
            case OVERWHELMING -> new OverwhelmingFireBallSpell();
        };

        return spell;
    }
}
