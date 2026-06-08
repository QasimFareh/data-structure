public class Validator {

    private static final int CAPACITY = 100;

    // ===================== فحص الأقواس =====================
    // الخوارزمية:
    // - '(' : push للـ stack
    // - ')' : إذا الـ stack فاضي → مش balanced، وإلا pop
    // - في الآخر إذا الـ stack مش فاضي → مش balanced

    public boolean isBalanced(String expression) {
        Stack<String> stack = new Stack<>(CAPACITY);
        String[] tokens = expression.trim().split("\\s+");

        for (String token : tokens) {
            if (token.equals("(")) {
                stack.push(token);

            } else if (token.equals(")")) {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    // ===================== فحص تعبير Infix =====================
    // القواعد:
    // - لازم يكون فيه operands وoperators بالتناوب
    // - لازم الأقواس متوازنة
    // - ما يبدأ أو ينتهي بـ operator
    // - ما يجي operatorين متتاليين أو operandين متتاليين

    public boolean isValidInfix(String expression, LinkedList<String> operands, LinkedList<String> operators) {
        if (expression == null || expression.trim().isEmpty()) return false;
        if (!isBalanced(expression)) return false;

        String[] tokens = expression.trim().split("\\s+");
        boolean lastWasOperand = false;

        for (String token : tokens) {
            if (token.equals("(") || token.equals(")")) {
                continue;
            } else if (isOperand(token, operands)) {
                if (lastWasOperand) return false; // operandين متتاليين
                lastWasOperand = true;
            } else if (isOperator(token, operators)) {
                if (!lastWasOperand) return false; // operatorين متتاليين أو بداية بـ operator
                lastWasOperand = false;
            } else {
                return false; // توكن غير معروف
            }
        }

        return lastWasOperand; // لازم ينتهي بـ operand
    }

    // ===================== فحص تعبير Postfix =====================
    // القواعد:
    // - عدد الـ operators = عدد الـ operands - 1
    // - ما في أقواس

    public boolean isValidPostfix(String expression, LinkedList<String> operands, LinkedList<String> operators) {
        if (expression == null || expression.trim().isEmpty()) return false;

        String[] tokens = expression.trim().split("\\s+");
        int count = 0;

        for (String token : tokens) {
            if (token.equals("(") || token.equals(")")) return false; // ما في أقواس بالـ postfix
            else if (isOperand(token, operands)) count++;
            else if (isOperator(token, operators)) count--;
            else return false; // توكن غير معروف

            if (count <= 0) return false; // operator قبل ما يكون فيه operands كافية
        }

        return count == 1; // لازم يضل operand واحد بس
    }

    // ===================== فحص تعبير Prefix =====================
    // نفس منطق الـ postfix بس من اليمين لليسار

    public boolean isValidPrefix(String expression, LinkedList<String> operands, LinkedList<String> operators) {
        if (expression == null || expression.trim().isEmpty()) return false;

        String[] tokens = expression.trim().split("\\s+");
        int count = 0;

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];

            if (token.equals("(") || token.equals(")")) return false;
            else if (isOperand(token, operands)) count++;
            else if (isOperator(token, operators)) count--;
            else return false;

            if (count <= 0) return false;
        }

        return count == 1;
    }

    // ===================== Helper Methods =====================

    private boolean isOperand(String token, LinkedList<String> operands) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return operands.find(token);
        }
    }
    private boolean isOperator(String token, LinkedList<String> operators) {
        return operators.find(token);
    }

}
