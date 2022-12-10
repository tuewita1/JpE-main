package jpedevelopment.addon.jpeaddon.utils.misc;

public enum RenderMode {
        LINES,
        SIDES,
        BOTH;

        public boolean lines() {
            return this == LINES || this == BOTH;
        }

        public boolean sides() {
            return this == SIDES || this == BOTH;
        }
    }
