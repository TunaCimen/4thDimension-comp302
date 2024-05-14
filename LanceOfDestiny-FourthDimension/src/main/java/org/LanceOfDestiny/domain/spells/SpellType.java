package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;

public enum SpellType {
    CANON {
        boolean isGood = true;
        @Override
        public void activate() {
            Events.ActivateCanons.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateCanons.invoke(false);
        }
    },
    EXPANSION {
        boolean isGood = true;
        @Override
        public void activate() {
            Events.ActivateExpansion.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateExpansion.invoke(false);
        }
    },
    OVERWHELMING {
        boolean isGood = true;
        @Override
        public void activate() {
            Events.ActivateOverwhelming.invoke(true);
        }

        @Override
        public void deactivate() {
            Events.ActivateOverwhelming.invoke(false);
        }
    },
    CHANCE {
        boolean isGood = true;
        // CHANCE spell does not have activation/deactivation logic
        @Override
        public void activate() {
            // No action needed
        }

        @Override
        public void deactivate() {
            // No action needed
        }
    },
    INFINITE_VOID {
        boolean isGood = false;
        @Override
        public void activate() {
            // Implement the activation logic for Infinite Void
            Events.ActivateInfiniteVoid.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Infinite Void
            Events.ActivateInfiniteVoid.invoke(false);
        }
    },
    DOUBLE_ACCEL {
        boolean isGood = false;
        @Override
        public void activate() {
            // Implement the activation logic for Double Accel
            Events.ActivateDoubleAccel.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Double Accel
            Events.ActivateDoubleAccel.invoke(false);
        }
    },
    HOLLOW_PURPLE {
        boolean isGood = false;
        @Override
        public void activate() {
            // Implement the activation logic for Hollow Purple
            Events.ActivateHollowPurple.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Hollow Purple
            Events.ActivateHollowPurple.invoke(false);
        }
    };;

    public abstract void activate();
    public abstract void deactivate();
}

