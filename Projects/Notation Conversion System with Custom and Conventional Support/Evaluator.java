public class Evaluator {

    private static final int CAPACITY = 100;

    // ===================== Evaluate Postfix =====================
    // الخوارزمية:
    // - Operand: push للـ stack
    // - Operator: pop n1 ثم n2، احسب n2 op n1، push النتيجة
    // - النتيجة النهائية = stack.peek()

    public double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>(CAPACITY);
        String[] tokens = postfix.trim().split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));

            } else if (isOperator(token)) {
                if (stack.isEmpty()) {
                    System.out.println("Error: invalid postfix expression");
                    return Double.NaN;
                }
                double n1 = stack.pop();
                if (stack.isEmpty()) {
                    System.out.println("Error: invalid postfix expression");
                    return Double.NaN;
                }
                double n2 = stack.pop();
                stack.push(applyOperator(n2, token, n1));

            } else {
                System.out.println("Error: invalid token -> " + token);
                return Double.NaN;

            }
        }

        if (stack.isEmpty()) {
            System.out.println("Error: empty expression");
            return Double.NaN;
        }
        return stack.pop();
    }

    // ===================== Evaluate Prefix =====================
    // الخوارزمية:
    // - نقرأ من اليمين لليسار
    // - Operand: push للـ stack
    // - Operator: pop n1 ثم n2، احسب n1 op n2، push النتيجة

    public double evaluatePrefix(String prefix) {
        Stack<Double> stack = new Stack<>(CAPACITY);
        String[] tokens = prefix.trim().split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];

            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));

            } else if (isOperator(token)) {
                if (stack.isEmpty()) {
                    System.out.println("Error: invalid prefix expression");
                    return Double.NaN;
                }
                double n1 = stack.pop();
                if (stack.isEmpty()) {
                    System.out.println("Error: invalid prefix expression");
                    return Double.NaN;
                }
                double n2 = stack.pop();
                stack.push(applyOperator(n1, token, n2));

            } else {
                System.out.println("Error: invalid token -> " + token);
                return Double.NaN;

            }
        }

        if (stack.isEmpty()) {
            System.out.println("Error: empty expression");
            return Double.NaN;
        }
        return stack.pop();
    }

    // ===================== Helper Methods =====================

    // تطبيق العملية الحسابية
    private double applyOperator(double a, String op, double b) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    System.out.println("Error: division by zero");
                    return Double.NaN;
                }
                return a / b;
            case "^":
                return Math.pow(a, b);
            default:
                System.out.println("Error: unknown operator -> " + op);
                return Double.NaN;
        }
    }

    // فحص إذا التوكن رقم
    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // فحص إذا التوكن operator
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
                token.equals("*") || token.equals("/") ||
                token.equals("^");
    }

}
