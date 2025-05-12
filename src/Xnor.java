import java.util.Map;
/**
 * Class Xnor.
 */
public class Xnor extends BinaryExpression {
    /**
     * Xnor constructor.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Xnor(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) == this.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() == this.getRight().evaluate();
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new Xnor(expL, expR);
    }

    @Override
    public String toString() {
        return format("#");
    }

    @Override
    public Expression nandify() {
        Expression expL = getLeft().nandify();
        Expression expR = getRight().nandify();
        Expression firstPart = new Nand(expL, expL);
        Expression secondPart = new Nand(expR, expR);
        Expression exp1 = new Nand(expL, expR);
        Expression exp2 = new Nand(firstPart, secondPart);
        return new Nand(exp1, exp2);
    }

    @Override
    public Expression norify() {
        Expression expL = getLeft().norify();
        Expression expR = getRight().norify();
        Expression insidePart = new Nor(expL, expR);
        Expression firstPart = new Nor(expL, insidePart);
        Expression secondPart = new Nor(expR, insidePart);
        return new Nor(firstPart, secondPart);
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expL.getVariables().isEmpty() && expR.getVariables().isEmpty()) { //no variables
            return new Val(this.evaluate());
        }
        if (expL.equals(expR)) { // x # x = T
            return new Val(true);
        }
        return new Xnor(expL, expR);
    }
}
