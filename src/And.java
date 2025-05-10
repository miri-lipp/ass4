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
}
