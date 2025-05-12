import java.util.HashMap;
import java.util.Map;

public class PartOneTests {
    public static void AndTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running And Tests");
        System.out.println("Checking T & T");
        Expression a = new And(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T & F");
        Expression b = new And(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F & T");
        Expression c = new And(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F & F");
        Expression d = new And(new Val(false), new Val(false));
        System.out.println(d.evaluate());

        System.out.println("Last one to string: " + d);
    }

    public static void OrTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Or Tests");
        System.out.println("Checking T | T");
        Expression a = new Or(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T | F");
        Expression b = new Or(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F | T");
        Expression c = new Or(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F | F");
        Expression d = new Or(new Val(false), new Val(false));
        System.out.println(d.evaluate());
        System.out.println("Last one to string: " + d);
    }

    public static void NotTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Not Tests");
        System.out.println("Checking ~T");
        Expression a = new Not(new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking ~F");
        Expression b = new Not(new Val(false));
        System.out.println(b.evaluate());

        System.out.println("Last one to string: " + b);
    }

    public static void XorTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Xor Tests");
        System.out.println("Checking T ^ T");
        Expression a = new Xor(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T ^ F");
        Expression b = new Xor(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F ^ T");
        Expression c = new Xor(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F ^ F");
        Expression d = new Xor(new Val(false), new Val(false));
        System.out.println(d.evaluate());
        System.out.println("Last one to string: " + d);
    }

    public static void NandTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Nand Tests");
        System.out.println("Checking T A T");
        Expression a = new Nand(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T A F");
        Expression b = new Nand(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F A T");
        Expression c = new Nand(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F A F");
        Expression d = new Nand(new Val(false), new Val(false));
        System.out.println(d.evaluate());
        System.out.println("Last one to string: " + d);
    }

    public static void NorTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Nor Tests");
        System.out.println("Checking T V T");
        Expression a = new Nor(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T V F");
        Expression b = new Nor(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F V T");
        Expression c = new Nor(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F V F");
        Expression d = new Nor(new Val(false), new Val(false));
        System.out.println(d.evaluate());
        System.out.println("Last one to string: " + d);
    }

    public static void XnorTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running Xnor() Tests");
        System.out.println("Checking T # T");
        Expression a = new Xnor(new Val(true), new Val(true));
        System.out.println(a.evaluate());
        System.out.println("Checking T # F");
        Expression b = new Xnor(new Val(true), new Val(false));
        System.out.println(b.evaluate());
        System.out.println("Checking F # T");
        Expression c = new Xnor(new Val(false), new Val(true));
        System.out.println(c.evaluate());
        System.out.println("Checking F # F");
        Expression d = new Xnor(new Val(false), new Val(false));
        System.out.println(d.evaluate());
        System.out.println("Last one to string: " + d);
    }

    public static void VarsTests() throws Exception {
        System.out.println("-----------------------");
        System.out.println("Running vars tests");
        Expression one = new Var("a");
        System.out.println("Expected output: a");
        System.out.println(one);

        Expression two = one.assign("a", new Var("b"));
        System.out.println("Expected output: b");
        System.out.println(two);

        Expression three = two.assign("b", new Var("c"));
        System.out.println("Expected output: c");
        System.out.println(three);

        Expression four = new And(new Var("left"), new Var("right"));
        System.out.println("Expected output: (left & right)");
        System.out.println(four);

        Expression five = four.assign("left", one);
        System.out.println("Expected output: (a & right)");
        System.out.println(five);
        System.out.println("getting var list, Expected output: [a, right]");
        System.out.println(five.getVariables());

        Expression six = five.assign("a", new Val(true));
        System.out.println("Expected output: (T & right)");
        System.out.println(six);

        Expression seven = six.assign("right", three.assign("c", new Val(true)));
        System.out.println("Expected output: (T & T)");
        System.out.println(seven);
        System.out.println("Getting var list, Expected output: []");
        System.out.println(seven.getVariables());
        System.out.println("Now evaluate the last expression, Expected output: true");
        System.out.println(seven.evaluate());

        Expression eight = four.assign("right", four);
        System.out.println("Expected output: (left & (left & right))");
        System.out.println(eight);

        Map<String, Boolean> varMap = new HashMap<>();
        varMap.put("left", true);
        System.out.println("Following line should throw an exception...");
        boolean wasErrorThrown = false;
        try {
            eight.evaluate(varMap);
        } catch (Exception e) {
            wasErrorThrown = true;
            System.out.println("Exception thrown as expected.");
        }
        if (!wasErrorThrown) {
            System.out.println("[ERROR] Exception was not thrown.");
            return;
        }
        varMap.put("right", true);
        System.out.println("Expected output: true");
        System.out.println(eight.evaluate(varMap));

        Expression nine = eight.assign("left", new Val(true));
        System.out.println("Expected output: (T & (T & right))");
        System.out.println(nine);
        System.out.println("Trying to evaluate, following line should throw an exception...");
        try {
            nine.evaluate();
        } catch (Exception e) {
            wasErrorThrown = true;
            System.out.println("Exception thrown as expected.");
        }
        if (!wasErrorThrown) {
            System.out.println("[ERROR] Exception was not thrown.");
            return;
        }

    }

    public static void otherTests() {
        System.out.println("-------------------");
        Expression e2 = new Xor(new And(new Var("x"), new Var("y")), new Val(true));
        String s = e2.toString();
        System.out.println("Expected output: ((x & y) ^ T)");
        System.out.println(s);

        Expression e3 = new Nand(e2, new Var("z"));
        System.out.println("Expected output: [x, y, z]");
        System.out.println(e3.getVariables());
    }

    public static void main(String[] args) throws Exception {
        AndTests();
        OrTests();
        NotTests();
        XorTests();
        NandTests();
        NorTests();
        XnorTests();
        VarsTests();
        otherTests();
    }
}
