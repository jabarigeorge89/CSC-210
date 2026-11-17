
package com.example.math;

/**
 * Provides basic Boolean logic operations.
 */
public class BooleanLogic {

    /** Returns true when both values are true. */
    public static boolean And(boolean a, boolean b) {
        return a && b;
    }

    /** Returns true when at least one value is true. */
    public static boolean Or(boolean a, boolean b) {
        return a || b;
    }

    /** Returns the opposite of the given value. */
    public static boolean Not(boolean a) {
        return !a;
    }

    /** Returns true when the values are different. */
    public static boolean Xor(boolean a, boolean b) {
        return a ^ b;
    }
}
