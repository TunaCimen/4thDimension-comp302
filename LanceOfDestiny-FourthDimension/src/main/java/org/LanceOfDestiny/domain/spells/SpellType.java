package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Event;

import java.util.ArrayList;

public enum SpellType {
    CANON {
        @Override
        public void activate() {
            Event.ActivateCanons.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateCanons.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    EXPANSION {
        @Override
        public void activate() {
            Event.ActivateExpansion.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateExpansion.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    OVERWHELMING {
        @Override
        public void activate() {
            Event.ActivateOverwhelming.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateOverwhelming.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    CHANCE {
        // CHANCE spell does not have activation/deactivation logic
        @Override
        public void activate() {
            // No action needed
        }

        @Override
        public void deactivate() {
            // No action needed
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    INFINITE_VOID {
        @Override
        public void activate() {
            Event.ActivateInfiniteVoid.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateInfiniteVoid.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return false;
        }
    },
    DOUBLE_ACCEL {
        @Override
        public void activate() {
            Event.ActivateDoubleAccel.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateDoubleAccel.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return false;
        }
    },
    HOLLOW_PURPLE {
        @Override
        public void activate() {
            Event.ActivateHollowPurple.invoke(true);
            isActive = true;
        }

        @Override
        public void deactivate() {
            Event.ActivateHollowPurple.invoke(false);
            isActive = false;
        }

        @Override
        public boolean isGood() {
            return false;
        }
    };

    public boolean isActive = false;
    public abstract void activate();
    public abstract void deactivate();
    public abstract boolean isGood();

    public static ArrayList<SpellType> getGoodSpells() {
        ArrayList<SpellType> goodSpells = new ArrayList<>();
        for (SpellType spell : SpellType.values()) {
            if (spell.isGood()) {
                goodSpells.add(spell);
            }
        }
        return goodSpells;
    }

    public static ArrayList<SpellType> getBadSpells() {
        ArrayList<SpellType> badSpells = new ArrayList<>();
        for (SpellType spell : SpellType.values()) {
            if (!spell.isGood()) {
                badSpells.add(spell);
            }
        }
        return badSpells;
    }
}
