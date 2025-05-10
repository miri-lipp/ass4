import java.util.Map;

/**
 * Class Or.
 */
public class Or extends BinaryExpression implements Expression {
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

}
