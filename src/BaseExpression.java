import java.util.List;
import java.util.Map;

/**
 * Base expression class.
 */
public abstract class BaseExpression implements Expression {
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
        return List.of();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return null;
    }
}
