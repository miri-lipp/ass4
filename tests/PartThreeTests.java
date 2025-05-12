import java.util.Objects;

public class PartThreeTests {

    public static boolean test(Expression exp, String expected, String type) throws Exception{
        if (!Objects.equals(exp.simplify().toString(), expected)) {
            System.out.println(type + " failed:");
            System.out.println("given: " + exp.simplify());
            System.out.println("expected: " + expected);
            return false;
        }
        return true;
    }
    public static void main(String[] args) throws Exception {
        Var x = new Var("x");
        Var y = new Var("y");
        Val t = new Val(true);
        Val f = new Val(false);

        System.out.println("-------------");
        System.out.println("AND tests");
        boolean testsPassed = true;

        testsPassed &= test(new And(x, t), "x", "AND");
        testsPassed &= test(new And(x, f), "F", "AND");
        testsPassed &= test(new And(x, x), "x", "AND");
        testsPassed &= test(new And(x, y), "(x & y)", "AND");

        if (testsPassed) {
            System.out.println("AND tests passed.");
        }

        System.out.println("-------------");
        System.out.println("OR tests");
        testsPassed = true;

        testsPassed &= test(new Or(x, f), "x", "OR");
        testsPassed &= test(new Or(x, t), "T", "OR");
        testsPassed &= test(new Or(x, x), "x", "OR");
        testsPassed &= test(new Or(x, y), "(x | y)", "OR");

        if (testsPassed) {
            System.out.println("OR tests passed.");
        }
        System.out.println("-------------");
        System.out.println("XOR tests");
        testsPassed = true;

        testsPassed &= test(new Xor(x, f), "x", "XOR");
        testsPassed &= test(new Xor(x, t), "~(x)", "XOR");
        testsPassed &= test(new Xor(x, x), "F", "XOR");
        testsPassed &= test(new Xor(x, y), "(x ^ y)", "XOR");

        if (testsPassed) {
            System.out.println("XOR tests passed.");
        }
        System.out.println("-------------");
        System.out.println("NAND tests");
        testsPassed = true;

        testsPassed &= test(new Nand(x, t), "~(x)", "NAND");
        testsPassed &= test(new Nand(x, f), "T", "NAND");
        testsPassed &= test(new Nand(x, x), "~(x)", "NAND");
        testsPassed &= test(new Nand(x, y), "(x A y)", "NAND");

        if (testsPassed) {
            System.out.println("NAND tests passed.");
        }

        System.out.println("-------------");
        System.out.println("NOR tests");
        testsPassed = true;

        testsPassed &= test(new Nor(x, f), "~(x)", "NOR");
        testsPassed &= test(new Nor(x, t), "F", "NOR");
        testsPassed &= test(new Nor(x, x), "~(x)", "NOR");
        testsPassed &= test(new Nor(x, y), "(x V y)", "NOR");

        if (testsPassed) {
            System.out.println("NOR tests passed.");
        }
        System.out.println("-------------");
        System.out.println("XNOR tests");
        testsPassed = true;

        testsPassed &= test(new Xnor(x, x), "T", "NOR");
        testsPassed &= test(new Xnor(x, y), "(x # y)", "NOR");

        if (testsPassed) {
            System.out.println("XNOR tests passed.");
        }

        System.out.println("-------------");
        System.out.println("Complex tests");
        testsPassed = true;
        Expression test1 = new And(new Xnor(x, x), y);
        testsPassed &= test(test1, "y", "Complex test");
        Expression test2 = new And(new Xnor(x, y), new Nand(new Not(y), x));
        testsPassed &= test(test2, "((x # y) & (~(y) A x))", "Complex test");
        Expression test3 = new Xor(new Or(new And(t,t), f), t);
        testsPassed &= test(test3, "F", "Complex test");
        Expression test4 = new Xor(new Or(new And(new Not(t),t), f), t);
        testsPassed &= test(test4, "T", "Complex test");
        if (testsPassed) {
            System.out.println("Complex tests passed.");
        }
    }
}
