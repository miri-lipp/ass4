import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Class for Binary expressions.
 */
public abstract class BinaryExpression extends BaseExpression implements Expression {
    private final Expression left;
    private final Expression right;

    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return null;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return null;
    }

    @Override
    public List<String> getVariables() {
        Set<String> vars = new HashSet<>();
        vars.addAll(left.getVariables());
        vars.addAll(right.getVariables());
        return new ArrayList<>(vars);
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
        return "(" + this.left.toString() + " " + symbol + " " + this.right.toString() + ")";
    }
}
