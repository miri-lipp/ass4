import java.util.List;
/**
 * Class for Binary expressions.
 */
public abstract class BinaryExpression extends BaseExpression {
    private final Expression left;
    private final Expression right;

    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<String> getVariables() {
        return mergeVariableLists(left.getVariables(), right.getVariables());
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return create(this.left.assign(var, expression), this.right.assign(var, expression));
    }

    /**
     * Creating expression.
     * @param expL expression left.
     * @param expR expression right.
     * @return new expression.
     */
    public abstract Expression create(Expression expL, Expression expR);

    /**
     * Getter of left expression.
     * @return expression.
     */
    public Expression getLeft() {
        return this.left;
    }

    /**
     * Getter of right expression.
     * @return expression.
     */
    public Expression getRight() {
        return this.right;
    }

    protected String format(String symbol) {
        return binaryToString(symbol, this.left, this.right);
    }

    protected boolean equal(Expression e1, Expression e2) {
        if (e1.equals(e2)) {
            return true;
        }
        if (e1.getClass() != e2.getClass()) {
            return false;
        }
        if (e1 instanceof BinaryExpression be1 && e2 instanceof BinaryExpression be2) {
            return (be1.getLeft().equals(be2.getRight()) && be1.getRight().equals(be2.getLeft()));
        }

        return false;
    }
}
