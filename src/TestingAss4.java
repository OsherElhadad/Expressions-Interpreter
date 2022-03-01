import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Tom Ben-Dor.
 */
@SuppressWarnings("CheckStyle")
@DisplayName("Testing")
class TestingAss4 {
    static <E> void assertListEqualsNoOrder(List<E> expected, List<E> actual) {
        assertEquals(expected.size(), actual.size());
        for (E element : expected) {
            assertTrue(actual.contains(element));
        }
    }

    @Nested
    @DisplayName("Xor")
    class TestingXor {
        Expression expression = new Xor(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x ^ y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("(((x V x) V (y V y)) V (x V y))", expression.norify().toString());
            assertEquals("((x A (x A y)) A (y A (x A y)))", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("~(y)", expression.assign("x", new Val(true)).simplify().toString());
            assertEquals("~(x)", expression.assign("y", new Val(true)).simplify().toString());
            assertEquals("y", expression.assign("x", new Val(false)).simplify().toString());
            assertEquals("x", expression.assign("y", new Val(false)).simplify().toString());
            assertEquals("F", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("F", expression.assign("y", new Var("x")).simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);

            expression = new Xor(new Xor(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertTrue(expression.evaluate(Map.of("x", true, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", true)));
            assertTrue(expression.evaluate(Map.of("x", true, "y", true)));

            assertThrows(Exception.class, () -> expression.evaluate(Map.of("x", false)));
            assertThrows(Exception.class, () -> expression.evaluate(Map.of("y", false)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x ^ y) ^ y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new Xor(new Xor(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T ^ F) ^ F)", expression.toString());
            assertTrue(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("Nor")
    class TestingNor {
        Expression expression = new Nor(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x V y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("(x V y)", expression.norify().toString());
            assertEquals("(((x A x) A (y A y)) A ((x A x) A (y A y)))", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("F", expression.assign("x", new Val(true)).simplify().toString());
            assertEquals("F", expression.assign("y", new Val(true)).simplify().toString());
            assertEquals("~(y)", expression.assign("x", new Val(false)).simplify().toString());
            assertEquals("~(x)", expression.assign("y", new Val(false)).simplify().toString());
            assertEquals("~(y)", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("~(x)", expression.assign("y", new Var("x")).simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);

            expression = new Nor(new Nor(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertTrue(expression.evaluate(Map.of("x", true, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", true)));
            assertFalse(expression.evaluate(Map.of("x", true, "y", true)));

            assertThrows(Exception.class, () -> expression.evaluate(Map.of("x", false)));
            assertThrows(Exception.class, () -> expression.evaluate(Map.of("y", false)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x V y) V y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new Nor(new Nor(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T V F) V F)", expression.toString());
            assertTrue(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("Nand")
    class TestingNand {
        Expression expression = new Nand(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x A y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("(((x V x) V (y V y)) V ((x V x) V (y V y)))", expression.norify().toString());
            assertEquals("(x A y)", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("~(y)", expression.assign("x", new Val(true)).simplify().toString());
            assertEquals("~(x)", expression.assign("y", new Val(true)).simplify().toString());
            assertEquals("T", expression.assign("x", new Val(false)).simplify().toString());
            assertEquals("T", expression.assign("y", new Val(false)).simplify().toString());
            assertEquals("~(y)", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("~(x)", expression.assign("y", new Var("x")).simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);

            expression = new Nand(new Nand(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertTrue(expression.evaluate(Map.of("x", true, "y", false)));
            assertTrue(expression.evaluate(Map.of("x", false, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", true)));
            assertTrue(expression.evaluate(Map.of("x", true, "y", true)));

            assertThrows(Exception.class, () -> expression.evaluate(Map.of("x", false)));
            assertThrows(Exception.class, () -> expression.evaluate(Map.of("y", false)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x A y) A y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new Nand(new Nand(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T A F) A F)", expression.toString());
            assertTrue(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("Xnor")
    class TestingXnor {
        Expression expression = new Xnor(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x # y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("((x V (x V y)) V (y V (x V y)))", expression.norify().toString());
            assertEquals("(((x A x) A (y A y)) A (x A y))", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("T", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("T", expression.assign("y", new Var("x")).simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);

            expression = new Xnor(new Xnor(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertTrue(expression.evaluate(Map.of("x", true, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", true)));
            assertTrue(expression.evaluate(Map.of("x", true, "y", true)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x # y) # y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new Xnor(new Xnor(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T # F) # F)", expression.toString());
            assertTrue(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("And")
    class TestingAnd {
        Expression expression = new And(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x & y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("((x V x) V (y V y))", expression.norify().toString());
            assertEquals("((x A y) A (x A y))", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("y", expression.assign("x", new Val(true)).simplify().toString());
            assertEquals("x", expression.assign("y", new Val(true)).simplify().toString());
            assertEquals("F", expression.assign("x", new Val(false)).simplify().toString());
            assertEquals("F", expression.assign("y", new Val(false)).simplify().toString());
            assertEquals("y", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("x", expression.assign("y", new Var("x")).simplify().toString());

            expression = new And(new And(new Val(true), new Var("y")), new Var("y"));
            assertEquals("y", expression.simplify().toString());
            expression = new And(new And(new Val(false), new Var("y")), new Var("y"));
            assertEquals("F", expression.simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);

            expression = new And(new And(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertFalse(expression.evaluate(Map.of("x", true, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", true)));
            assertTrue(expression.evaluate(Map.of("x", true, "y", true)));

            assertThrows(Exception.class, () -> expression.evaluate(Map.of("x", false)));
            assertThrows(Exception.class, () -> expression.evaluate(Map.of("y", false)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x & y) & y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new And(new And(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T & F) & F)", expression.toString());
            assertFalse(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("Or")
    class TestingOr {
        Expression expression = new Or(new Var("x"), new Var("y"));

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("(x | y)", expression.toString());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(2)
        void testNandifyNorify() {
            assertEquals("((x V y) V (x V y))", expression.norify().toString());
            assertEquals("((x A x) A (y A y))", expression.nandify().toString());
        }

        @Test
        @DisplayName("simplify()")
        @Order(3)
        void testSimplify() {
            assertEquals(expression.toString(), expression.simplify().toString());

            assertEquals("T", expression.assign("x", new Val(true)).simplify().toString());
            assertEquals("T", expression.assign("y", new Val(true)).simplify().toString());
            assertEquals("y", expression.assign("x", new Val(false)).simplify().toString());
            assertEquals("x", expression.assign("y", new Val(false)).simplify().toString());
            assertEquals("y", expression.assign("x", new Var("y")).simplify().toString());
            assertEquals("x", expression.assign("y", new Var("x")).simplify().toString());

            expression = new Or(new Or(new Val(true), new Var("y")), new Var("y"));
            assertEquals("T", expression.simplify().toString());
            expression = new Or(new Or(new Val(false), new Var("y")), new Var("y"));
            assertEquals("y", expression.simplify().toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(4)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);
            expression = new Or(new Or(new Var("x"), new Var("y")), new Var("y"));
            assertThrows(Exception.class, expression::evaluate);

            assertTrue(expression.evaluate(Map.of("x", true, "y", false)));
            assertFalse(expression.evaluate(Map.of("x", false, "y", false)));
            assertTrue(expression.evaluate(Map.of("x", false, "y", true)));
            assertTrue(expression.evaluate(Map.of("x", true, "y", true)));

            assertThrows(Exception.class, () -> expression.evaluate(Map.of("x", true)));
            assertThrows(Exception.class, () -> expression.evaluate(Map.of("y", true)));
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            expression = expression.assign("x", expression);
            assertEquals("((x | y) | y)", expression.toString());
        }

        @Test
        @DisplayName("getVariables()")
        @Order(6)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
            expression = new Or(new Or(new Var("x"), new Var("y")), new Var("y"));
            assertListEqualsNoOrder(List.of("x", "y"), expression.getVariables());
        }

        @Test
        @DisplayName("all assigned")
        @Order(7)
        void testAllAssigned() throws Exception {
            expression = expression.assign("x", expression);
            expression = expression.assign("x", new Val(true));
            expression = expression.assign("y", new Val(false));

            assertEquals("((T | F) | F)", expression.toString());
            assertTrue(expression.evaluate());
        }
    }

    @Nested
    @DisplayName("Not")
    class TestingNot {
        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            Expression expression = new Not(new Var("x"));
            assertEquals("~(x)", expression.toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(2)
        void testEvaluate() throws Exception {
            Expression expression = new Not(new Var("x"));
            assertThrows(Exception.class, expression::evaluate);

            expression = new Not(new Not(new Var("x")));
            assertTrue(expression.evaluate(Map.of("x", true)));
            assertFalse(expression.evaluate(Map.of("x", false)));
        }

        @Test
        @DisplayName("getVariables()")
        @Order(3)
        void testGetVariables() {
            Expression expression = new Not(new Var("x"));
            assertListEqualsNoOrder(List.of("x"), expression.getVariables());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(4)
        void testNandifyNorify() {
            Expression expression = new Not(new Var("x"));
            assertEquals("(x V x)", expression.norify().toString());
            assertEquals("(x A x)", expression.nandify().toString());
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() {
            Expression expression = new Not(new Var("x"));
            expression = expression.assign("x", expression);
            assertEquals("~(~(x))", expression.toString());
        }

        @Test
        @DisplayName("all assigned")
        @Order(6)
        void testAllAssigned() throws Exception {
            Expression expression = new Not(new Not(new Var("x")));
            expression = expression.assign("x", new Val(true));

            assertEquals("~(~(T))", expression.toString());
            assertTrue(expression.evaluate());
        }

        @Test
        @DisplayName("simplify()")
        @Order(7)
        void testSimplify() {
            Expression expression = new Not(new Not(new And(new Var("x"), new Val(true))));
            assertEquals("~(~(x))", expression.simplify().toString(), "No need to simplify ~(~(x)).");

            assertEquals("F", new Not(new Val(true)).simplify().toString());
            assertEquals("T", new Not(new Val(false)).simplify().toString());
        }
    }

    @Nested
    @DisplayName("Val")
    class TestingVal {
        @Test
        @DisplayName("toString()")
        void testToString() {
            for (boolean bool : new boolean[] {true, false}) {
                Expression expression = new Val(bool);
                assertEquals(bool ? "T" : "F", expression.toString());
            }
        }

        @Test
        @DisplayName("evaluate()")
        void testEvaluate() throws Exception {
            for (boolean bool : new boolean[] {true, false}) {
                Expression expression = new Val(bool);
                assertEquals(bool, expression.evaluate());
            }
        }

        @Test
        @DisplayName("getVariables()")
        void testGetVariables() {
            for (boolean bool : new boolean[] {true, false}) {
                Expression expression = new Val(bool);
                assertListEqualsNoOrder(List.of(), expression.getVariables());
            }
        }

        @Test
        @DisplayName("nandify() & norify()")
        void testNandifyNorify() {
            for (boolean bool : new boolean[] {true, false}) {
                Expression expression = new Val(bool);
                assertEquals(expression.toString(), expression.norify().toString());
                assertEquals(expression.toString(), expression.nandify().toString());
            }
        }
    }

    @Nested
    @DisplayName("Var")
    class TestingVar {
        Expression expression = new Var("x");

        @Test
        @DisplayName("toString()")
        @Order(1)
        void testToString() {
            assertEquals("x", expression.toString());
            expression = expression.assign("x", expression);
            assertEquals("x", expression.toString());
        }

        @Test
        @DisplayName("evaluate()")
        @Order(2)
        void testEvaluate() throws Exception {
            assertThrows(Exception.class, expression::evaluate);
            assertTrue(expression.evaluate(Map.of("x", true)));
            assertFalse(expression.evaluate(Map.of("x", false)));
        }

        @Test
        @DisplayName("getVariables()")
        @Order(3)
        void testGetVariables() {
            assertListEqualsNoOrder(List.of("x"), expression.getVariables());
        }

        @Test
        @DisplayName("nandify() & norify()")
        @Order(4)
        void testNandifyNorify() {
            assertEquals(expression.toString(), expression.norify().toString());
            assertEquals(expression.toString(), expression.nandify().toString());
        }

        @Test
        @DisplayName("assign()")
        @Order(5)
        void testAssign() throws Exception {
            expression = expression.assign("x", new Val(true));
            assertEquals("T", expression.toString());
            assertTrue(expression.evaluate());
        }
    }
}