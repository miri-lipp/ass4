import java.util.Map;

/**
 * Class Xor.
 */
public class Xor extends BinaryExpression implements Expression {
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
}
