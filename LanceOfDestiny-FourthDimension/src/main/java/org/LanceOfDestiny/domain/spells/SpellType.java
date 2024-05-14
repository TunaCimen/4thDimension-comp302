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
            // Implement the activation logic for Infinite Void
            Events.ActivateInfiniteVoid.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Infinite Void
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
            // Implement the activation logic for Double Accel
            Events.ActivateDoubleAccel.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Double Accel
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
            // Implement the activation logic for Hollow Purple
            Events.ActivateHollowPurple.invoke(true);
        }

        @Override
        public void deactivate() {
            // Implement the deactivation logic for Hollow Purple
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
