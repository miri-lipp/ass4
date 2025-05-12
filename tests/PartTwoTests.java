import java.util.Objects;

public class PartTwoTests {
    public static void main(String[] args) {
        Var a = new Var("a");
        Var b = new Var("b");

        Not not = new Not(a);
        And and = new And(a, b);
        Or or = new Or(a, b);
        Nor nor = new Nor(a, b);
        Xor xor = new Xor(a, b);
        Xnor xnor = new Xnor(a, b);

        System.out.println("Expected output of NOT: ");
        System.out.println(not.nandify());

        System.out.println("Expected output of AND: ((a A b) A (a A b))");
        System.out.println(and.nandify());

        System.out.println("Expected output of OR: ((a A a) A (b A b))");
        System.out.println(or.nandify());

        System.out.println("Expected output of Nor: (((a A a) A (b A b)) A ((a A a) A (b A b)))");
        System.out.println(nor.nandify());

        System.out.println("Expected output of Xor: ((a A (a A b)) A (b A (a A b)))");
        System.out.println(xor.nandify());
        System.out.println("Expected output of Xnor: (((a A a) A (b A b)) A (a A b))");
        System.out.println(xnor.nandify());

        System.out.println("--------------------");
        System.out.println("Norify tests");
        boolean norifyErrors = false;

        if (!Objects.equals(not.norify().toString(), "(a V a)")) {
            System.out.println("Norify failed on NOT");
            System.out.println("given: " + and.norify());
            System.out.println("expected: (a V a)");
            norifyErrors = true;
        }

        if (!Objects.equals(and.norify().toString(), "((a V a) V (b V b))")) {
            System.out.println("Norify failed on AND");
            System.out.println("given: " + and.norify());
            System.out.println("expected: ((a V a) V (b V b))");
            norifyErrors = true;
        }

        if (!Objects.equals(or.norify().toString(), "((a V b) V (a V b))")) {
            System.out.println("Norify failed on OR");
            System.out.println("given: " + or.norify());
            System.out.println("expected: ((a V b) V (a V b))");
            norifyErrors = true;
        }

        if (!Objects.equals(nor.norify().toString(), "(a V b)")) {
            System.out.println("Norify failed on NOR");
            System.out.println("given: " + nor.norify());
            System.out.println("expected: (a V b)");
            norifyErrors = true;
        }

        if (!Objects.equals(xor.norify().toString(), "(((a V a) V (b V b)) V (a V b))")) {
            System.out.println("Norify failed on XOR");
            System.out.println("given: " + xor.norify());
            System.out.println("expected: (((a V a) V (b V b)) V (a V b))");
            norifyErrors = true;
        }

        if (!Objects.equals(xnor.norify().toString(), "((a V (a V b)) V (b V (a V b)))")) {
            System.out.println("Norify failed on XNOR");
            System.out.println("expected: ((a V (a V b)) V (b V (a V b)))");
            System.out.println("given: " + xnor.norify());
            norifyErrors = true;
        }
        if(!norifyErrors){
            System.out.println("All passed");
        }

        System.out.println("--------------------");
        System.out.println("Complex tests");

        boolean complexError = false;

        Expression testOne = new And(new Not(a),b);
        if (!Objects.equals(testOne.nandify().toString(), "(((a A a) A b) A ((a A a) A b))")) {
            System.out.println("Complex tests failed on testOne");
            System.out.println("expected: (((a A a) A b) A ((a A a) A b))");
            System.out.println("given: " + testOne.nandify());
            complexError = true;
        }
        testOne = testOne.assign("b", new Not(b));
        if (!Objects.equals(testOne.nandify().toString(), "(((a A a) A (b A b)) A ((a A a) A (b A b)))")) {
            System.out.println("Complex tests failed on testOne");
            System.out.println("expected: (((a A a) A (b A b)) A ((a A a) A (b A b)))");
            System.out.println("given: " + testOne.nandify());
            complexError = true;
        }

        Expression testTwo = new Or(a,b);
        testTwo = new And(testTwo, b);
        if (!Objects.equals(testTwo.norify().toString(), "((((a V b) V (a V b)) V ((a V b) V (a V b))) V (b V b))")) {
            System.out.println("Complex tests failed on testTwo");
            System.out.println("expected: ((((a V b) V (a V b)) V ((a V b) V (a V b))) V (b V b))");
            System.out.println("given: " + testTwo.norify());
            complexError = true;
        }

        if (!complexError){
            System.out.println("All passed");
        }
    }
}
