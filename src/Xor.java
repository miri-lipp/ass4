import java.util.Map;

/**
 * Class Xor.
 */
public class Xor extends BinaryExpression {
    /**
     * Xor constructor.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Xor(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) ^ this.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() ^ this.getRight().evaluate();
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new Xor(expL, expR);
    }

    @Override
    public String toString() {
        return format("^");
    }

    @Override
    public Expression nandify() {
        Expression expL = getLeft().nandify();
        Expression expR = getRight().nandify();
        Expression exp1 = new Nand(expL, expR);
        Expression exp2 = new Nand(expL, exp1);
        Expression exp3 = new Nand(expR, exp1);
        return new Nand(exp2, exp3);
    }

    @Override
    public Expression norify() {
        Expression expL = getLeft().norify();
        Expression expR = getRight().norify();
        Expression exp1 = new Nor(expL, expL);
        Expression exp2 = new Nor(expR, expR);
        Expression exp3 = new Nor(exp1, exp2);
        Expression exp4 = new Nor(expL, expR);
        return new Nor(exp3, exp4);
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expL.getVariables().isEmpty() && expR.getVariables().isEmpty()) { //no variables
            return new Val(this.evaluate());
        }
        if (expL.getVariables().isEmpty() && expL.evaluate()) { // T ^ x = ~x
            return new Not(expR);
        } else if (expR.getVariables().isEmpty() && expR.evaluate()) { // x ^ T = ~x
            return new Not(expL);
        } else if (expR.getVariables().isEmpty() && !expR.evaluate()) { // x ^ F = x
            return expL;
        } else if (expL.getVariables().isEmpty() && !expL.evaluate()) { // F ^ x = x
            return expR;
        } else if (equals(expL, expR) || expL.equals(expR)) { // x ^ x = F
            return new Val(false);
        }
        return new Xor(expL, expR);
    }


}
