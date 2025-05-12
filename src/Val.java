import java.util.List;
import java.util.Map;

/**
 * Represents  boolean variables.
 */
public class Val implements Expression {
    private final Boolean val;

    /**
     * Constructor.
     * @param val boolean.
     */
    public Val(Boolean val) {
        this.val = val;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (this.val) {
            assignment.put("T", true);
        } else {
            assignment.put("F", false);
        }
        return this.val;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.val;
    }

    @Override
    public List<String> getVariables() {
        return List.of();
    }

    @Override
    public Expression assign(String var, Expression expression) {
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
    public Expression simplify() throws Exception {
        return this;
    }

    @Override
    public String toString() {
        return this.val ? "T" : "F";
    }
}
