import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

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

    /**
     * Binary to string.
     *
     * @param symbol of binary operator
     * @param left   expression.
     * @param right  expression.
     * @return new string.
     */
    protected String binaryToString(String symbol, Expression left, Expression right) {
        return "(" + left.toString() + " " + symbol + " " + right.toString() + ")";
    }

    /**
     * Unary to string.
     *
     * @param symbol     symbol of unary operator.
     * @param expression expression.
     * @return new string.
     */
    protected String unaryToString(String symbol, Expression expression) {
        return symbol + "(" + expression.toString() + ")";
    }

    /**
     * Merging two lists into one without repetitions.
     *
     * @param list1 first expression.
     * @param list2 second one.
     * @return new list.
     */
    protected List<String> mergeVariableLists(List<String> list1, List<String> list2) {
        Set<String> vars = new HashSet<>(list1);
        vars.addAll(list2);
        return new ArrayList<>(vars);
    }

    /**
     * equals method.
     * @param a first expression.
     * @param b second expression.
     * @return true of they are equal.
     */
    public static boolean equals(Expression a, Expression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass() != b.getClass()) {
            return false;
        }

        if (a instanceof BinaryExpression exprA && b instanceof BinaryExpression exprB) {
            // commutative check (x op y == y op x)
            return (equals(exprA.getLeft(), exprB.getLeft()) && equals(exprA.getRight(), exprB.getRight()))
                    || (equals(exprA.getLeft(), exprB.getRight())
                    && equals(exprA.getRight(), exprB.getLeft()));
        }
        if (a instanceof Var && b instanceof Var) {
            return (a).toString().equals((b).toString());
        }
        return a.equals(b);
    }
}

