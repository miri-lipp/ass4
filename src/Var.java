import java.util.List;
import java.util.Map;

/**
 * evaluates variables.
 */
public class Var implements Expression {
    private final String varName;

    /**
     * Var constructor.
     * @param varName string.
     */
    public Var(String varName) {
        this.varName = varName;
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (assignment.containsKey(varName)) {
            return assignment.get(varName);
        } else {
            throw new Exception("Variable '" + varName + "' not found.");
        }
    }

    @Override
    public Boolean evaluate() throws Exception {
        return true;
    }

    @Override
    public List<String> getVariables() {
        return List.of(this.varName);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (var.equals(this.varName)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return this.varName;
    }
}
