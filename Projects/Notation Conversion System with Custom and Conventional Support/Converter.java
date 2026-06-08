public class Converter {


    // ===================== Infix to Postfix =====================
    public String infixToPostfix(String infix, LinkedList<String> operands, LinkedList<String> operators, LinkedList<String> precedence) {
        Stack<String> stack = new Stack<>(100);
        StringBuilder postfix = new StringBuilder();
        String[] tokens = infix.trim().split("\\s+");

        for (String token : tokens) {
            if (isOperand(token, operands)) {
                postfix.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop();
            } else if (isOperator(token, operators)) {
                while (!stack.isEmpty()
                        && !stack.peek().equals("(")
                        && getPrecedence(stack.peek(), precedence) >= getPrecedence(token, precedence)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else {
                return "Error: invalid token -> " + token;
            }
        }

        while (!stack.isEmpty()) {
            String top = stack.pop();
            if (top.equals("(")) return "Error: mismatched parentheses";
            postfix.append(top).append(" ");
        }

        return postfix.toString().trim();
    }

    // ===================== Infix to Prefix =====================
    public String infixToPrefix(String infix, LinkedList<String> operands, LinkedList<String> operators, LinkedList<String> precedence) {
        String postfix = infixToPostfix(infix, operands, operators, precedence);
        if (postfix.startsWith("Error")) return postfix;

        String[] tokens = postfix.split("\\s+");
        StringBuilder prefix = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            prefix.append(tokens[i]).append(" ");
        }
        return prefix.toString().trim();
    }

    // ===================== Postfix to Infix =====================
    public String postfixToInfix(String postfix, LinkedList<String> operands, LinkedList<String> operators) {
        Stack<String> stack = new Stack<>(100);
        String[] tokens = postfix.trim().split("\\s+");

        for (String token : tokens) {
            if (isOperand(token, operands)) {
                stack.push(token);
            } else if (isOperator(token, operators)) {
                if (stack.isEmpty()) return "Error: invalid postfix expression";
                String n1 = stack.pop();
                if (stack.isEmpty()) return "Error: invalid postfix expression";
                String n2 = stack.pop();
                stack.push("( " + n2 + " " + token + " " + n1 + " )");
            } else {
                return "Error: invalid token -> " + token;
            }
        }

        if (stack.isEmpty()) return "Error: empty expression";
        return stack.pop();
    }

    // ===================== Prefix to Infix =====================
    public String prefixToInfix(String prefix, LinkedList<String> operands, LinkedList<String> operators) {
        Stack<String> stack = new Stack<>(100);
        String[] tokens = prefix.trim().split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isOperand(token, operands)) {
                stack.push(token);
            } else if (isOperator(token, operators)) {
                if (stack.isEmpty()) return "Error: invalid prefix expression";
                String n1 = stack.pop();
                if (stack.isEmpty()) return "Error: invalid prefix expression";
                String n2 = stack.pop();
                stack.push("( " + n1 + " " + token + " " + n2 + " )");
            } else {
                return "Error: invalid token -> " + token;
            }
        }

        if (stack.isEmpty()) return "Error: empty expression";
        return stack.pop();
    }

    // ===================== Helper Methods =====================

    // رقم → operand مباشرة، مش رقم → فحص اللستة
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

    private int getPrecedence(String op, LinkedList<String> precedence) {
        Node<String> current = precedence.getHead();
        while (current != null) {
            String data = current.getData();
            int colon = data.indexOf(':');
            if (data.substring(0, colon).equals(op))
                return Integer.parseInt(data.substring(colon + 1));
            current = current.getNext();
        }
        return 0;
    }

}