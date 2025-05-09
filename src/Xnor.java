import java.util.Map;
/**
 * Class Xnor.
 */
public class Xnor extends BinaryExpression implements Expression {
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
}
