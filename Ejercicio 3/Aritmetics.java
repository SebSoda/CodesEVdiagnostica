package com.mycompany.aritmetics;

/**
 *
 * @author Sebastian
 */
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aritmetics {

    public static double evaluate(String expression) {
        // Limpieza y validación inicial
        String cleanedExpression = cleanAndValidate(expression);

        // A postfix pa' que sea más fácil
        String postfixExpression = infixToPostfix(cleanedExpression);

        // Dale, a evaluar
        return evaluatePostfix(postfixExpression);
    }

    private static String cleanAndValidate(String expression) {
        // Quitando esos espacios fastidiosos
        String cleanedExpression = expression.replaceAll("\\s+", "");

        // Expresión regular para validar los caracteres
        String regex = "^[\\dEe+\\-*/().]+$";
        if (!cleanedExpression.matches(regex)) {
            throw new IllegalArgumentException("Expresión tiene caracteres raros.");
        }

        // Chequeo de notación científica
        Pattern scientificNotationPattern = Pattern.compile("([\\d.]+)[Ee]([+\\-]?\\d+)");
        Matcher matcher = scientificNotationPattern.matcher(cleanedExpression);
        while (matcher.find()) {
            String match = matcher.group();
            try {
                Double.parseDouble(match); // Tratando de convertir a número
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Notación científica mal puesta: " + match);
            }
        }

        return cleanedExpression;
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0; // No es operador
        }
    }

    private static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.' || (c == 'E' || c == 'e') || (i > 0 && (expression.charAt(i-1) == 'E' || expression.charAt(i-1) == 'e') && (c == '+' || c == '-'))) {
                // Es un número
                postfix.append(c);
                if (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.' || (expression.charAt(i+1) == 'E' || expression.charAt(i+1) == 'e')  || ((expression.charAt(i+1) == '+' || expression.charAt(i+1) == '-') && (c == 'E' || c == 'e')))) {
                    continue; // Sigue agregando al número
                }
                postfix.append(" "); // Separa los numeros
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Quitando parentesis
                } else {
                    throw new IllegalArgumentException("Parentesis disparejos.");
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(c);
            } else {
                throw new IllegalArgumentException("Carácter raro en la expresión: " + c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(' || stack.peek() == ')') {
                throw new IllegalArgumentException("Parentesis sin cerrar.");
            }
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static double evaluatePostfix(String postfixExpression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfixExpression.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Faltan numeros para el operador: " + token);
                }
                double num2 = stack.pop();
                double num1 = stack.pop();
                double result = performOperation(num1, num2, token.charAt(0));
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Token inválido: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expresión incompleta.");
        }

        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double performOperation(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("División por cero.");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }

    public static void main(String[] args) {
        String expression1 = "(125E10 - 1e15)/5E-85*15";
        String expression2 = "5+2*3";
        String expression3 = "(5 + 2) * 3";
        String expression4 = "125E25 + 5E-8";
        String expression5 = "2 * (3 + 4) / 2";
        String expression6 = "10 / 2 - 3 + 1";
        String expression7 = "1.2E+3 * 2";
        String expression8 = "1E-5 + 2";

        try {
            System.out.println(expression1 + " = " + evaluate(expression1));
            System.out.println(expression2 + " = " + evaluate(expression2));
            System.out.println(expression3 + " = " + evaluate(expression3));
            System.out.println(expression4 + " = " + evaluate(expression4));
            System.out.println(expression5 + " = " + evaluate(expression5));
            System.out.println(expression6 + " = " + evaluate(expression6));
            System.out.println(expression7 + " = " + evaluate(expression7));
            System.out.println(expression8 + " = " + evaluate(expression8));

            // Ejemplo con error
            String expressionError = "2 * (3 + 4 / 0)";
            System.out.println(expressionError + " = " + evaluate(expressionError));

        } catch (Exception e) {
            System.err.println("Algo fallo: " + e.getMessage());
        }
    }
}