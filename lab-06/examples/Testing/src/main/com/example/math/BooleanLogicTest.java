
package com.example.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BooleanLogic class.
 */
public class BooleanLogicTest {

    @Test
    public void testAnd() {
        assertTrue(BooleanLogic.And(true, true));
        assertFalse(BooleanLogic.And(true, false));
        assertFalse(BooleanLogic.And(false, true));
        assertFalse(BooleanLogic.And(false, false));
    }

    @Test
    public void testOr() {
        assertTrue(BooleanLogic.Or(true, false));
        assertTrue(BooleanLogic.Or(false, true));
        assertTrue(BooleanLogic.Or(true, true));
        assertFalse(BooleanLogic.Or(false, false));
    }

    @Test
    public void testNot() {
        assertFalse(BooleanLogic.Not(true));
        assertTrue(BooleanLogic.Not(false));
    }

    @Test
    public void testXor() {
        assertFalse(BooleanLogic.Xor(true, true));
        assertTrue(BooleanLogic.Xor(true, false));
        assertTrue(BooleanLogic.Xor(false, true));
        assertFalse(BooleanLogic.Xor(false, false));
    }
}
