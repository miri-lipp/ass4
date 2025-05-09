import java.util.List;
import java.util.Map;

/**
 * Expression interface.
 */
public interface Expression {
    // Evaluate the expression using the variable values provided
    // in the assignment, and return the result. If the expression
    // contains a variable which is not in the assignment, an exception
    // is thrown.

    /**
     * Evaluate the expression using the variable values provided.
     * @param assignment values.
     * @return result.
     * @throws Exception exception.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    // A convenience method. Like the `evaluate(assignment)` method above,
    // but uses an empty assignment.

    /**
     * Evaluating method.
     * @return true.
     * @throws Exception exception.
     */
    Boolean evaluate() throws Exception;

    // Returns a list of the variables in the expression.

    /**
     * getting Variables.
     * @return List.
     */
    List<String> getVariables();

    // Returns a nice string representation of the expression.

    /**
     * To string representation.
     * @return string.
     */
    String toString();

    // Returns a new expression in which all occurrences of the variable
    // var are replaced with the provided expression (Does not modify the
    // current expression).
    /**
     * Replacing occurrence of the variable.
     * @param var variable.
     * @param expression expression object.
     * @return new expression.
     */
    Expression assign(String var, Expression expression);
}