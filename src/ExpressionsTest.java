import java.util.Map;
import java.util.TreeMap;

/**
 * Main class.
 *
 */
public class ExpressionsTest {
    /**
     * Main.
     * @param args string empty.
     */
    public static void main(String[] args) throws Exception {
        Expression e1 = new Xnor(new Nand(new Not(new Var("x")), new Val(true)),
                new Or(new Not(new Var("y")), new And(new Var("z"),
                        new Nor(new Not(new Val(false)), new Val(true)))));
        System.out.println(e1);
        // create map of variables and values.
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("y", true);
        assignment.put("x", false);
        assignment.put("z", false);
        System.out.println(e1.evaluate(assignment));
        //nandified version
        System.out.println(e1.nandify());
        //norified version
        System.out.println(e1.norify());
        //simplified version
        System.out.println(e1.simplify());
    }

}
