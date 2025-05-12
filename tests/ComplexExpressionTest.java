// IMPORTANT!!!!!!! Run with the flag -ea, and ensure the "assert false" fails
// Author: Hodaya Kashkash
import java.util.HashMap;
import java.util.Map;

class ComplexExpressionTest {
    private static final Var A = new Var("A"),
            B = new Var("B"), C = new Var("C"), D = new Var("D");

    public static void main(String[] args) throws Exception {
        testComplexExpressions();
        testMissingVariableB();
        testComplexAssignments();
        testSimplifyExpressions();
        testEdgeCases();
        System.out.println("All tests passed!");
    }

    static void testMissingVariableB() {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("A", true);
        assignment.put("C", true);
        assignment.put("D", false);

        Expression expr = new Or(new And(A, B), C);

        try {
            expr.evaluate(assignment);
            // If no exception is thrown, fail the test
            assert false : "Expected exception was not thrown for missing variable B";
        } catch (Exception e) {
//            String expectedMessage = "B Error! variable not found!";
//            String actualMessage = e.getMessage();
//            assert actualMessage.contains(expectedMessage) : "Error message does not match for expression: " + expr.toString();
        }
    }

    static void testComplexExpressions() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("A", true);
        assignment.put("B", false);
        assignment.put("C", true);
        assignment.put("D", false);

        Expression[] expressions = {
                new Not(A), // Expression: ¬A, Expected: false
                new Not(B), // Expression: ¬B, Expected: true
                new Not(C), // Expression: ¬C, Expected: false
                new Not(D), // Expression: ¬D, Expected: true
                new Not(new Not(A)), // Expression: ¬¬A, Expected: true
                new Not(new Not(B)), // Expression: ¬¬B, Expected: false
                new Not(new Or(A, B)), // Expression: ¬(A ∨ B), Expected: false
                new Not(new Or(C, D)), // Expression: ¬(C ∨ D), Expected: false
                new Or(A, B), // Expression: A ∨ B, Expected: true
                new Or(B, C), // Expression: B ∨ C, Expected: true
                new Or(C, D), // Expression: C ∨ D, Expected: true
                new Or(D, A), // Expression: D ∨ A, Expected: true
                new Or(new Not(A), new Not(B)), // Expression: ¬A ∨ ¬B, Expected: true
                new Or(new Not(C), new Not(D)), // Expression: ¬C ∨ ¬D, Expected: true
                new Xnor(A, B), // Expression: A ⊙ B, Expected: false
                new Xnor(B, C), // Expression: B ⊙ C, Expected: false
                new Xnor(C, D), // Expression: C ⊙ D, Expected: false
                new Xnor(D, A), // Expression: D ⊙ A, Expected: false
                new Xnor(A, new Not(B)), // Expression: A ⊙ ¬B, Expected: true
                new Xnor(B, new Not(C)), // Expression: B ⊙ ¬C, Expected: true
                new Xnor(C, new Not(D)), // Expression: C ⊙ ¬D, Expected: false
                new Xnor(D, new Not(A)), // Expression: D ⊙ ¬A, Expected: true
                new Xnor(new Or(A, B), C), // Expression: (A ∨ B) ⊙ C, Expected: false
                new Xnor(new Or(C, D), A), // Expression: (C ∨ D) ⊙ A, Expected: false
                new Xor(A, B), // Expression: A ⊕ B, Expected: true
                new Xor(B, C), // Expression: B ⊕ C, Expected: true
                new Xor(C, D), // Expression: C ⊕ D, Expected: true
                new Xor(D, A), // Expression: D ⊕ A, Expected: true
                new Xor(A, new Not(B)), // Expression: A ⊕ ¬B, Expected: false
                new Xor(B, new Not(C)), // Expression: B ⊕ ¬C, Expected: false
                new Xor(C, new Not(D)), // Expression: C ⊕ ¬D, Expected: true
                new Xor(D, new Not(A)), // Expression: D ⊕ ¬A, Expected: false
                new Xor(new Or(A, B), C), // Expression: (A ∨ B) ⊕ C, Expected: true
                new Xor(new Or(C, D), A), // Expression: (C ∨ D) ⊕ A, Expected: true
                new Val(true), // Expression: T, Expected: true
                new Val(false), // Expression: F, Expected: false
                new Var("A"), // Expression: A, Expected: true
                new Var("B"), // Expression: B, Expected: false
                new Var("C"), // Expression: C, Expected: true
                new Var("D"), // Expression: D, Expected: false
                new Not(new Xnor(A, B)), // Expression: ¬(A ⊙ B), Expected: true
                new Not(new Xnor(C, D)), // Expression: ¬(C ⊙ D), Expected: true
                new Or(new Xor(A, B), C), // Expression: (A ⊕ B) ∨ C, Expected: true
                new Or(new Xor(C, D), A), // Expression: (C ⊕ D) ∨ A, Expected: true
                new Xnor(new Or(A, B), new Not(C)), // Expression: (A ∨ B) ⊙ ¬C, Expected: false
                new Xnor(new Or(C, D), new Not(A)), // Expression: (C ∨ D) ⊙ ¬A, Expected: false
                new Xor(new And(A, B), new Or(C, D)), // Expression: (A ∧ B) ⊕ (C ∨ D), Expected: true
                new Xor(new And(C, D), new Or(A, B)), // Expression: (C ∧ D) ⊕ (A ∨ B), Expected: true
                new Not(new And(A, new Or(B, C))), // Expression: ¬(A ∧ (B ∨ C)), Expected: false
                new Not(new And(C, new Or(D, A))), // Expression: ¬(C ∧ (D ∨ A)), Expected: false
                new Or(new And(A, new Xor(B, C)), new Not(D)), // Expression: (A ∧ (B ⊕ C)) ∨ ¬D, Expected: true
                new Or(new And(C, new Xor(D, A)), new Not(B)), // Expression: (C ∧ (D ⊕ A)) ∨ ¬B, Expected: true
                new Xnor(new And(A, B), new Or(C, D)), // Expression: (A ∧ B) ⊙ (C ∨ D), Expected: false
                new Xnor(new And(C, D), new Or(A, B)), // Expression: (C ∧ D) ⊙ (A ∨ B), Expected: false
                new Xor(new Or(new And(A, B), C), D), // Expression: ((A ∧ B) ∨ C) ⊕ D, Expected: true
                new Xor(new Or(new And(C, D), A), B), // Expression: ((C ∧ D) ∨ A) ⊕ B, Expected: true
                new And(A, B), // Expression: A ∧ B, Expected: false
                new And(B, C), // Expression: B ∧ C, Expected: false
                new And(C, D), // Expression: C ∧ D, Expected: false
                new And(D, A), // Expression: D ∧ A, Expected: false
                new And(new Not(A), new Not(B)), // Expression: ¬A ∧ ¬B, Expected: false
                new And(new Not(C), new Not(D)), // Expression: ¬C ∧ ¬D, Expected: false
                new And(new Or(A, B), C), // Expression: (A ∨ B) ∧ C, Expected: true
                new And(new Or(C, D), A), // Expression: (C ∨ D) ∧ A, Expected: true
                new And(new Xor(A, B), new Not(C)), // Expression: (A ⊕ B) ∧ ¬C, Expected: false
                new And(new Xor(C, D), new Not(A)), // Expression: (C ⊕ D) ∧ ¬A, Expected: false
                new And(new Xnor(A, B), new Or(C, D)), // Expression: (A ⊙ B) ∧ (C ∨ D), Expected: false
                new And(new Xnor(C, D), new Or(A, B)), // Expression: (C ⊙ D) ∧ (A ∨ B), Expected: false
                new And(new Val(true), new Val(false)), // Expression: T ∧ F, Expected: false
                new And(new Val(true), new Val(true)), // Expression: T ∧ T, Expected: true
                new And(new Val(false), new Val(false)), // Expression: F ∧ F, Expected: false
                new And(new Val(false), new Val(true)), // Expression: F ∧ T, Expected: false
                new Or(new Val(true), new Val(false)), // Expression: T ∨ F, Expected: true
                new Or(new Val(true), new Val(true)), // Expression: T ∨ T, Expected: true
                new Or(new Val(false), new Val(false)), // Expression: F ∨ F, Expected: false
                new Or(new Val(false), new Val(true)), // Expression: F ∨ T, Expected: true
                new Not(new Val(true)), // Expression: ¬T, Expected: false
                new Not(new Val(false)), // Expression: ¬F, Expected: true
                new Xor(new Val(true), new Val(false)), // Expression: T ⊕ F, Expected: true
                new Xor(new Val(true), new Val(true)), // Expression: T ⊕ T, Expected: false
                new Xor(new Val(false), new Val(false)), // Expression: F ⊕ F, Expected: false
                new Xor(new Val(false), new Val(true)), // Expression: F ⊕ T, Expected: true
                new Xnor(new Val(true), new Val(false)), // Expression: T ⊙ F, Expected: false
                new Xnor(new Val(true), new Val(true)), // Expression: T ⊙ T, Expected: true
                new Xnor(new Val(false), new Val(false)), // Expression: F ⊙ F, Expected: true
                new Xnor(new Val(false), new Val(true)), // Expression: F ⊙ T, Expected: false
                new And(new Var("A"), new Val(true)), // Expression: A ∧ T, Expected: true
                new And(new Var("A"), new Val(false)), // Expression: A ∧ F, Expected: false
                new And(new Var("B"), new Val(true)), // Expression: B ∧ T, Expected: false
                new And(new Var("B"), new Val(false)), // Expression: B ∧ F, Expected: false
                new And(new Var("C"), new Val(true)), // Expression: C ∧ T, Expected: true
                new And(new Var("C"), new Val(false)), // Expression: C ∧ F, Expected: false
                new And(new Var("D"), new Val(true)), // Expression: D ∧ T, Expected: false
                new And(new Var("D"), new Val(false)), // Expression: D ∧ F, Expected: false
                new Or(new Var("A"), new Val(true)), // Expression: A ∨ T, Expected: true
                new Or(new Var("A"), new Val(false)), // Expression: A ∨ F, Expected: true
                new Or(new Var("B"), new Val(true)), // Expression: B ∨ T, Expected: true
                new Or(new Var("B"), new Val(false)), // Expression: B ∨ F, Expected: false
                new Or(new Var("C"), new Val(true)), // Expression: C ∨ T, Expected: true
                new Or(new Var("C"), new Val(false)), // Expression: C ∨ F, Expected: true
                new Or(new Var("D"), new Val(true)), // Expression: D ∨ T, Expected: true
                new Or(new Var("D"), new Val(false)), // Expression: D ∨ F, Expected: false
                new Not(new Var("A")), // Expression: ¬A, Expected: false
                new Not(new Var("B")), // Expression: ¬B, Expected: true
                new Not(new Var("C")), // Expression: ¬C, Expected: false
                new Not(new Var("D")), // Expression: ¬D, Expected: true
                new Xor(new Var("A"), new Val(true)), // Expression: A ⊕ T, Expected: false
                new Xor(new Var("A"), new Val(false)), // Expression: A ⊕ F, Expected: true
                new Xor(new Var("B"), new Val(true)), // Expression: B ⊕ T, Expected: true
                new Xor(new Var("B"), new Val(false)), // Expression: B ⊕ F, Expected: false
                new Xor(new Var("C"), new Val(true)), // Expression: C ⊕ T, Expected: false
                new Xor(new Var("C"), new Val(false)), // Expression: C ⊕ F, Expected: true
                new Xor(new Var("D"), new Val(true)), // Expression: D ⊕ T, Expected: true
                new Xor(new Var("D"), new Val(false)), // Expression: D ⊕ F, Expected: false
                new Xnor(new Var("A"), new Val(true)), // Expression: A ⊙ T, Expected: true
                new Xnor(new Var("A"), new Val(false)), // Expression: A ⊙ F, Expected: false
                new Xnor(new Var("B"), new Val(true)), // Expression: B ⊙ T, Expected: false
                new Xnor(new Var("B"), new Val(false)), // Expression: B ⊙ F, Expected: true
                new Xnor(new Var("C"), new Val(true)), // Expression: C ⊙ T, Expected: true
                new Xnor(new Var("C"), new Val(false)), // Expression: C ⊙ F, Expected: false
                new Xnor(new Var("D"), new Val(true)), // Expression: D ⊙ T, Expected: false
                new Xnor(new Var("D"), new Val(false)), // Expression: D ⊙ F, Expected: true
                new Not(new Xor(new Var("A"), new Val(true))), // Expression: ¬(A ⊕ T), Expected: true
                new Not(new Xor(new Var("A"), new Val(false))), // Expression: ¬(A ⊕ F), Expected: false
                new Not(new Xor(new Var("B"), new Val(true))), // Expression: ¬(B ⊕ T), Expected: false
                new Not(new Xor(new Var("B"), new Val(false))), // Expression: ¬(B ⊕ F), Expected: true
                new Not(new Xor(new Var("C"), new Val(true))), // Expression: ¬(C ⊕ T), Expected: true
                new Not(new Xor(new Var("C"), new Val(false))), // Expression: ¬(C ⊕ F), Expected: false
                new Not(new Xor(new Var("D"), new Val(true))), // Expression: ¬(D ⊕ T), Expected: false
                new Not(new Xor(new Var("D"), new Val(false))), // Expression: ¬(D ⊕ F), Expected: true
                new And(new Or(A, B), new And(C, D)), // Expression: (A ∨ B) ∧ (C ∧ D), Expected: false
                new And(new Or(B, C), new And(D, A)), // Expression: (B ∨ C) ∧ (D ∧ A), Expected: false
                new And(new Or(C, D), new And(A, B)), // Expression: (C ∨ D) ∧ (A ∧ B), Expected: false
                new And(new Or(D, A), new And(B, C)), // Expression: (D ∨ A) ∧ (B ∧ C), Expected: false
                new Or(new And(A, B), new Or(C, D)), // Expression: (A ∧ B) ∨ (C ∨ D), Expected: true
                new Or(new And(B, C), new Or(D, A)), // Expression: (B ∧ C) ∨ (D ∨ A), Expected: true
                new Or(new And(C, D), new Or(A, B)), // Expression: (C ∧ D) ∨ (A ∨ B), Expected: true
                new Or(new And(D, A), new Or(B, C)), // Expression: (D ∧ A) ∨ (B ∨ C), Expected: true
                new Xnor(new Or(A, B), new Xnor(C, D)), // Expression: (A ∨ B) ⊙ (C ⊙ D), Expected: false
                new Xnor(new Or(B, C), new Xnor(D, A)), // Expression: (B ∨ C) ⊙ (D ⊙ A), Expected: false
                new Xnor(new Or(C, D), new Xnor(A, B)), // Expression: (C ∨ D) ⊙ (A ⊙ B), Expected: false
                new Xnor(new Or(D, A), new Xnor(B, C)), // Expression: (D ∨ A) ⊙ (B ⊙ C), Expected: false
                new Xor(new And(A, B), new Xor(C, D)), // Expression: (A ∧ B) ⊕ (C ⊕ D), Expected: true
                new Xor(new And(B, C), new Xor(D, A)), // Expression: (B ∧ C) ⊕ (D ⊕ A), Expected: true
                new Xor(new And(C, D), new Xor(A, B)), // Expression: (C ∧ D) ⊕ (A ⊕ B), Expected: true
                new Xor(new And(D, A), new Xor(B, C)), // Expression: (D ∧ A) ⊕ (B ⊕ C), Expected: true
                new Not(new Xnor(new Or(A, B), new Xor(C, D))), // Expression: ¬((A ∨ B) ⊙ (C ⊕ D)), Expected: false
                new Not(new Xnor(new Or(B, C), new Xor(D, A))), // Expression: ¬((B ∨ C) ⊙ (D ⊕ A)), Expected: false
                new Not(new Xnor(new Or(C, D), new Xor(A, B))), // Expression: ¬((C ∨ D) ⊙ (A ⊕ B)), Expected: false
                new Not(new Xnor(new Or(D, A), new Xor(B, C))), // Expression: ¬((D ∨ A) ⊙ (B ⊕ C)), Expected: false
        };
        boolean[] results = {
                false,
                true,
                false,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                true,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                true,
                true,
                false,
                true,
                false,
                true,
                true,
                false,
                false,
                true,
                false,
                true,
                true,
                false,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                false,
                true,
                true,
                true,
                false,
                false,
                true,
                false,
                true,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false};
//        for (int i = 0; i < expressions.length; i++) {
//            try {
//                System.out.println("Evaluating expression " + i + ": " + expressions[i].toString());
//                System.out.println("Result: " + expressions[i].evaluate(assignment));
//            } catch (Exception e) {
//                System.err.println("Error evaluating expression " + i + ": " + expressions[i].toString());
//                throw e;
//            }
//        }

        for (int i = 0; i < expressions.length; i++) {
            boolean result = expressions[i].evaluate(assignment);
            assert result == results[i] : "Failed for expression number " + i + ": " + expressions[i].toString() + ", got: " + result + ", expected: " + results[i];
        }
    }

    static void testComplexAssignments() throws Exception {
        for (int i = 0; i < 200; i++) {
            try {
                if (i % 50 == 0)
                    System.out.println("Running complex assignment test iteration: " + i);
                Expression expr = new Or(new And(A, new Not(B)), new Xor(C, D));
                Expression assignedExpr = expr.assign("B", new Val(i % 2 == 0));
                Map<String, Boolean> assignment = new HashMap<>();
                assignment.put("A", true);
                assignment.put("B", i % 2 == 0); // Ensure B is assigned correctly
                assignment.put("C", i % 3 == 0);
                assignment.put("D", i % 5 == 0);

                assert assignedExpr.evaluate(assignment) == expr.evaluate(assignment) :
                        "Mismatch in iteration " + i + " for expression: " + expr.toString();
                // Expression: (A ∧ ¬(B)) ∨ (C ⊕ D) with B assigned to Val(i % 2 == 0), Expected: Varies based on iteration
            } catch (Exception e) {
                System.err.println("Error in complex assignment test iteration: " + i);
                throw e;
            }
        }
    }
    static void testSimplifyExpressions() throws Exception {
        for (int i = 0; i < 200; i++) {
            Expression expr = null;
            try {
                if (i % 50 == 0)
                    System.out.println("Running complex assignment test iteration: " + i);
                expr = new Or(new And(new Val(i % 2 == 0), new Not(new Val(i % 3 == 0))),
                        new Xor(new Val(i % 5 == 0), new Val(i % 7 == 0)));
                Expression simplifiedExpr = expr.simplify();

                assert simplifiedExpr.evaluate() == expr.evaluate() :
                        "Mismatch in iteration " + i + " for expression: " + expr;
                // Expression: (Val(i % 2 == 0) ∧ ¬(Val(i % 3 == 0))) ∨ (Val(i % 5 == 0) ⊕ Val(i % 7 == 0)), Expected: Varies based on iteration
            } catch (Exception e) {
                System.err.println("Error in simplify expression test iteration: " + i + " for expression: "
                        + expr);
                throw e;
            }
        }
    }
    static void testEdgeCases() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("A", false);
        assignment.put("B", false);
        assignment.put("C", true);
        assignment.put("D", true);

        assert new And(A, B).evaluate(assignment) == false;
        // Expression: A ∧ B, Expected: false
        assert new Or(A, B).evaluate(assignment) == false;
        // Expression: A ∨ B, Expected: false
        assert new Xnor(A, B).evaluate(assignment);
        // Expression: A ⊙ B, Expected: true
        assert new Xor(A, B).evaluate(assignment) == false;
        // Expression: A ⊕ B, Expected: false
        assert new And(A, C).evaluate(assignment) == false;
        // Expression: A ∧ C, Expected: false
        assert new Or(A, C).evaluate(assignment);
        // Expression: A ∨ C, Expected: true
        assert new Xnor(A, C).evaluate(assignment) == false;
        // Expression: A ⊙ C, Expected: false
        assert new Xor(A, C).evaluate(assignment);
        // Expression: A ⊕ C, Expected: true
        assert new And(C, D).evaluate(assignment);
        // Expression: C ∧ D, Expected: true
        assert new Or(C, D).evaluate(assignment);
        // Expression: C ∨ D, Expected: true
        assert new Xnor(C, D).evaluate(assignment);
        // Expression: C ⊙ D, Expected: true
        assert new Xor(C, D).evaluate(assignment) == false;
        // Expression: C ⊕ D, Expected: false
    }
}
