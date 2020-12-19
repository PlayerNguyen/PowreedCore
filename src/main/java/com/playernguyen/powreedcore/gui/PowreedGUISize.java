package com.playernguyen.powreedcore.gui;

/**
 * The size of GUI to display the GUI in the precision way. <br>
 * The size amount of GUI must be multiply by 9
 */
public enum PowreedGUISize {

    SMALLEST {
        @Override
        public int getSize() {
            return 9;
        }
    },
    SMALL {
        @Override
        public int getSize() {
            return 18;
        }
    },
    MEDIUM {
        @Override
        public int getSize() {
            return 27;
        }
    },
    LARGE {
        @Override
        public int getSize() {
            return 36;
        }
    },
    LARGEST {
        @Override
        public int getSize() {
            return 45;
        }
    }
    ;

    public abstract int getSize();
}
