import java.util.List;
import java.util.Map;

/**
 * Hierarchy class for unary operators.
 */
public abstract class UnaryExpression extends BaseExpression implements Expression {
    private final Expression expr;

    /**
     * Class constructor.
     * @param expr expression.
     */
    UnaryExpression(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !this.expr.evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return !this.expr.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return expr.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return create(this.expr.assign(var, expression));
    }

    /**
     * Creates expression.
     * @param exp expression.
     * @return expression.
     */
    public abstract Expression create(Expression exp);

    /**
     * Getting expression.
     * @return expression.
     */
    public Expression getExpr() {
        return expr;
    }
    //returns new string.
    protected String format(String symbol) {
        return unaryToString(symbol, this.expr);
    }
    @Override
    public Expression nandify() {
        Expression exp = getExpr().nandify();
        return new Nand(exp, exp);
    }
}
