import java.util.Map;
import java.util.TreeMap;

/**
 * Main class.
 *ID 336239652
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
//        Expression e2 = new Nor(new And(new Var("x"), new Var("y")), new Val(true));
//        System.out.println(e2.norify());
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
