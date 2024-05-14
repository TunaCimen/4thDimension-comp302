package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;

public enum SpellType {
    CANON {
        @Override
        public void activate() {
            Events.ActivateCanons.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateCanons.invoke(false);
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    EXPANSION {
        @Override
        public void activate() {
            Events.ActivateExpansion.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateExpansion.invoke(false);
        }

        @Override
        public boolean isGood() {
            return true;
        }
    },
    OVERWHELMING {
        @Override
        public void activate() {
            Events.ActivateOverwhelming.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateOverwhelming.invoke(false);
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
            Events.ActivateInfiniteVoid.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateInfiniteVoid.invoke(false);
        }

        @Override
        public boolean isGood() {
            return false;
        }
    },
    DOUBLE_ACCEL {
        @Override
        public void activate() {
            Events.ActivateDoubleAccel.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateDoubleAccel.invoke(false);
        }

        @Override
        public boolean isGood() {
            return false;
        }
    },
    HOLLOW_PURPLE {
        @Override
        public void activate() {
            Events.ActivateHollowPurple.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateHollowPurple.invoke(false);
        }

        @Override
        public boolean isGood() {
            return false;
        }
    };

    public abstract void activate();
    public abstract void deactivate();
    public abstract boolean isGood();
}
