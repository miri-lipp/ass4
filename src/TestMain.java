import java.util.List;

public class TestMain {
    public static void main(String[] args) throws Exception {
        Expression e = new Not(
                new Xor(
                        new And(
                                new Val(true),
                                new Or(
                                        new Var("x"),
                                        new Var("y")
                                )
                        ),
                        new Var("x")
                )
        );
        String s = e.toString();
        System.out.println(s);
        Expression e2 = new Xor(new And(new Var("x"), new Var("y")), new Val(true));
        List<String> vars = e2.getVariables();
        for (String v : vars) {
            System.out.println(v);
        }
        Expression e3 = e2.assign("y", e2);
        System.out.println(e3);
// ((x & ((x & y) ^ T)) ^ T)
        e3 = e3.assign("x", new Val(false));
        System.out.println(e3);
        //System.out.println(e.nandify());
        //System.out.println(e.norify());
        Expression e4 =  new And(new Or(new Var("x"), new  Var ("y")), new Or(new Var("y"), new Var("x")) );
        System.out.println(e4.simplify());
        System.out.println(e3.simplify());
        System.out.println(e2.simplify());
        System.out.println(e.simplify());
    }
}
