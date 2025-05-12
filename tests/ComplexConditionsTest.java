// IMPORTANT!!!!!!! Run with the flag -ea, and ensure the "assert false" fails
// Author: Hodaya Kashkash
import java.util.HashMap;
import java.util.Map;

public class ComplexConditionsTest {

    private static final Var x = new Var("x");
    private static final Var y = new Var("y");
    private static final Var z = new Var("z");
    private static final Var w = new Var("w");
    private static final Val T = new Val(true);
    private static final Val F = new Val(false);

    public static void main(String[] args) throws Exception {
        // Enable assertions when running this! (java -ea ComplexConditionsTest)

        // x ∧ 1 = x
        assert new And(x, T).simplify().toString().equals(x.toString()) : "x ∧ 1 failed";
        // x ∧ 0 = 0
        assert new And(x, F).simplify().toString().equals(F.toString()) : "x ∧ 0 failed";
        // x ∧ x = x
        assert new And(x, x).simplify().toString().equals(x.toString()) : "x ∧ x failed";

        // x ∨ 1 = 1
        assert new Or(x, T).simplify().toString().equals(T.toString()) : "x ∨ 1 failed";
        // x ∨ 0 = x
        assert new Or(x, F).simplify().toString().equals(x.toString()) : "x ∨ 0 failed";
        // x ∨ x = x
        assert new Or(x, x).simplify().toString().equals(x.toString()) : "x ∨ x failed";

        // x ⊕ 1 = ¬x
        assert new Xor(x, T).simplify().toString().equals(new Not(x).toString()) : "x ⊕ 1 failed";
        // x ⊕ 0 = x
        assert new Xor(x, F).simplify().toString().equals(x.toString()) : "x ⊕ 0 failed";
        // x ⊕ x = 0
        assert new Xor(x, x).simplify().toString().equals(F.toString()) : "x ⊕ x failed";

        // x A 1 = ¬x
        assert new Nand(x, T).simplify().toString().equals(new Not(x).toString()) : "x A 1 failed";
        // x A 0 = 1
        assert new Nand(x, F).simplify().toString().equals(T.toString()) : "x A 0 failed";
        // x A x = ¬x
        assert new Nand(x, x).simplify().toString().equals(new Not(x).toString()) : "x A x failed";

        // x V 1 = 0
        assert new Nor(x, T).simplify().toString().equals(F.toString()) : "x V 1 failed";
        // x V 0 = ¬x
        assert new Nor(x, F).simplify().toString().equals(new Not(x).toString()) : "x V 0 failed";
        // x V x = ¬x
        assert new Nor(x, x).simplify().toString().equals(new Not(x).toString()) : "x V x failed";

        // x # x = 1
        assert new Xnor(x, x).simplify().toString().equals(T.toString()) : "x # x failed";

        // Expression without variables: ((T ∧ F) ∨ T) ⊕ T = F
        Expression expr = new Xor(new Or(new And(T, F), T), T);
        assert expr.evaluate().equals(F.evaluate()) : "((T ∧ F) ∨ T) ⊕ T failed";

        // Recursive simplification: And(Xnor(x, x), y) = y
        Expression complexExpr = new And(new Xnor(x, x), y);
        assert complexExpr.simplify().toString().equals(y.toString()) : "And(Xnor(x, x), y) simplification failed";

        // Adding more complex expressions
        for (int i = 0; i < 100; i++) {
            try {
                Expression testExpr = new Or(
                        new And(new Var("A" + i), new Not(new Var("B" + i))),
                        new Xor(new Var("C" + i), new Var("D" + i))
                );
                assert testExpr.simplify().toString() != null : "Simplify returned null at i=" + i;
            } catch (Exception e) {
                throw new AssertionError("Exception in iteration: " + i + " with message: " + e.getMessage());
            }
        }

        // Additional complex tests
        for (int i = 0; i < 100; i++) {
            try {
                Map<String, Boolean> assignment = new HashMap<>();
                assignment.put("x", i % 2 == 0);
                assignment.put("y", i % 3 == 0);
                assignment.put("z", i % 5 == 0);
                assignment.put("w", i % 7 == 0);

                Expression expr1 = new And(new Or(x, new Not(y)), new Xor(z, w));
                Expression expr2 = new Or(new And(new Not(x), y), new Xnor(z, new Not(w)));
                Expression expr3 = new Xnor(new And(x, new Not(z)), new Or(y, w));
                Expression expr4 = new Xor(new Nor(x, y), new Nand(z, w));

                assert expr1.simplify().toString() != null : "Simplify failed at expr1, i=" + i;
                assert expr2.simplify().toString() != null : "Simplify failed at expr2, i=" + i;
                assert expr3.simplify().toString() != null : "Simplify failed at expr3, i=" + i;
                assert expr4.simplify().toString() != null : "Simplify failed at expr4, i=" + i;

                assert expr1.evaluate(assignment) != null : "Evaluate failed at expr1, i=" + i;
                assert expr2.evaluate(assignment) != null : "Evaluate failed at expr2, i=" + i;
                assert expr3.evaluate(assignment) != null : "Evaluate failed at expr3, i=" + i;
                assert expr4.evaluate(assignment) != null : "Evaluate failed at expr4, i=" + i;

            } catch (Exception e) {
                throw new AssertionError("Exception in complex iteration: " + i + " with message: " + e.getMessage());
            }
        }

        System.out.println("All tests passed!");
    }
}
