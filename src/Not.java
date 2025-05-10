
/**
 * Class Not.
 */
public class Not extends UnaryExpression implements Expression {
    /**
     * Constructor for Not of val.
     * @param expr Var object.
     */
    public Not(Expression expr) {
        super(expr);
    }

    @Override
    public Expression create(Expression exp) {
        return new Not(exp);
    }

    @Override
    public String toString() {
        return format("~");
    }

}
