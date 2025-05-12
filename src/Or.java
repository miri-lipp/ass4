import java.util.Map;

/**
 * Class Or.
 */
public class Or extends BinaryExpression {
    /**
     * Or constructor.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Or(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) || this.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() || this.getRight().evaluate();
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new Or(expL, expR);
    }

    @Override
    public String toString() {
        return format("|");
    }

    @Override
    public Expression nandify() {
        Expression expL = getLeft().nandify();
        Expression expR = getRight().nandify();
        Expression nandL = new Nand(expL, expL);
        Expression nandR = new Nand(expR, expR);
        return  new Nand(nandL, nandR);
    }

    @Override
    public Expression norify() {
        Expression expL = getLeft().norify();
        Expression expR = getRight().norify();
        Expression nor = new Nor(expL, expR);
        return new Nor(nor, nor);
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expL.getVariables().isEmpty() && expR.getVariables().isEmpty()) { //no variables
            return new Val(this.evaluate());
        }
        if (expL.getVariables().isEmpty() && !expL.evaluate()) { // F | x = x
            return expR;
        } else if (expR.getVariables().isEmpty() && !expR.evaluate()) { // x | F = x
            return expL;
        } else if ((expR.getVariables().isEmpty() && expR.evaluate())
                || (expL.getVariables().isEmpty() && expL.evaluate())) { //T | x == x | T = T
            return new Val(true);
        } else if (expL.equals(expR)) { // x | x = x
            return expL;
        }
        return new Or(expL, expR);
    }

}
