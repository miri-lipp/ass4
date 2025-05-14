import java.util.Map;

/**
 * Class Nand.
 */
public class Nand extends BinaryExpression {

    /**
     * Nand constructor.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Nand(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !(this.getLeft().evaluate(assignment) && this.getRight().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {
        return !(this.getLeft().evaluate() && this.getRight().evaluate());
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new Nand(expL, expR);
    }

    @Override
    public String toString() {
        return format("A");
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        Expression expL = getLeft();
        Expression expR = getRight();
        Expression norL = new Nor(expL, expL);
        Expression norR = new Nor(expR, expR);
        Expression norFull = new Nor(norL, norR);
        return new Nor(norFull, norFull);
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expL.getVariables().isEmpty() && expR.getVariables().isEmpty()) { //no variables
            return new Val(this.evaluate());
        }
        if (expL.equals(expR) || equals(expL, expR)) { // x A x = ~x
            return new Not(expL);
        } else if (expR.getVariables().isEmpty() && expR.evaluate()) { //x A T = ~x
            return new Not(expL);
        } else if (expL.getVariables().isEmpty() && expL.evaluate()) { //T A x = ~x
            return new Not(expR);
        } else if ((expR.getVariables().isEmpty() && !expR.evaluate())
                || (expL.getVariables().isEmpty() && !expL.evaluate())) { //F A x == x A F = T
            return new Val(true);
        }
        return new Nand(expL, expR);
    }
}
