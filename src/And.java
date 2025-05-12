import java.util.Map;

/**
 * Class and.
 */
public class And extends BinaryExpression implements Expression {
    /**
     * And constructor.
     * @param left left part of the expression.
     * @param right right part of the expression.
     */
    public And(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) && this.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() && this.getRight().evaluate();
    }

    @Override
    public Expression create(Expression expL, Expression expR) {
        return new And(expL, expR);
    }

    @Override
    public String toString() {
        return format("&");
    }

    @Override
    public Expression nandify() {
        Expression expL = getLeft().nandify();
        Expression expR = getRight().nandify();
        Expression nand = new Nand(expL, expR);
        return  new Nand(nand, nand);
    }

    @Override
    public Expression norify() {
        Expression expL = getLeft().norify();
        Expression expR = getRight().norify();
        Expression norL = new Nor(expL, expL);
        Expression norR = new Nor(expR, expR);
        return new Nor(norL, norR);
    }

    @Override
    public Expression simplify() throws Exception {
        Expression expL = getLeft().simplify();
        Expression expR = getRight().simplify();
        if (expL.getVariables().isEmpty() && expR.getVariables().isEmpty()) { //if no variables
            return new Val(this.evaluate());
        }
        if (expL.getVariables().isEmpty() && expL.evaluate()) { //T && x = x
            return expR;
        } else if (expR.getVariables().isEmpty() && expR.evaluate()) { //x && T = x
            return expL;
        } else if (!expR.evaluate() || !expL.evaluate()) { //F && x == x && F = F
            return new Val(false);
        } else if (getLeft().getVariables().equals(getRight().getVariables())) { //x && x = x
            return expL;
        }
        return new And(expL, expR);
    }
}
