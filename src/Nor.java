import java.util.Map;

/**
 * Class Nor.
 */
public class Nor extends BinaryExpression {
    /**
     * Nor constructor.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Nor(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !(this.getLeft().evaluate(assignment) || this.getRight().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {
        return !(this.getLeft().evaluate() || this.getRight().evaluate());
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new Nor(expL, expR);
    }

    @Override
    public String toString() {
        return format("V");
    }

    @Override
    public Expression nandify() {
        Expression expL = getLeft().nandify();
        Expression expR = getRight().nandify();
        Expression firstPart = new Nand(expL, expL);
        Expression secondPart = new Nand(expR, expR);
        Expression exp = new Nand(firstPart, secondPart);
        return new Nand(exp, exp);
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expR.getVariables().isEmpty() && expL.getVariables().isEmpty()) { //no variables
            return new Val(this.evaluate());
        }
        if (expL.getVariables().isEmpty() && !expL.evaluate()) { // F V x = ~x
            return new Not(expR);
        } else if (expR.getVariables().isEmpty() && !expR.evaluate()) { //x V F = ~x
            return new Not(expL);
        } else if ((expR.getVariables().isEmpty() && expR.evaluate())
                || (expL.getVariables().isEmpty() && expL.evaluate())) { // x V T = F
            return new Val(false);
        } else if (expL.equals(expR)) { //x V x = ~x
            return new Not(expL);
        }
        return new Nor(expL, expR);
    }
}
