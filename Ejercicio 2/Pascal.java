package com.mycompany.pascal;

/**
 *
 * @author Sebastian
 */
import java.util.ArrayList;
import java.util.List;

public class Pascal {

     //Calcula el triangulo de pascal
    public static List<List<Integer>> generatePascalTriangle(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1); // Los lados son 1
                } else {
                    // Suma los de arriba
                    row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
                }
            }
            triangle.add(row);
        }
        return triangle;
    }


    //Imprime el triangulo de pascal
    public static void printPascalTriangle(List<List<Integer>> triangle) {
        for (List<Integer> row : triangle) {
            System.out.println(row);
        }
    }

    //Coeficientes
    public static List<Integer> getCoefficients(int n) {
        List<List<Integer>> triangle = generatePascalTriangle(n + 1);
        return triangle.get(n);
    }

    //Polinomio
    public static String polynomialToString(List<Integer> coefficients) {
        StringBuilder polynomial = new StringBuilder();
        int degree = coefficients.size() - 1;

        for (int i = 0; i < coefficients.size(); i++) {
            int coefficient = coefficients.get(i);

            if (coefficient != 0) {

                if (polynomial.length() > 0) {
                    polynomial.append(" + ");
                }

                if (coefficient != 1 || degree - i == 0) {
                    polynomial.append(coefficient);
                }

                if (degree - i > 0) {
                    polynomial.append("x");
                    if (degree - i > 1) {
                        polynomial.append("^").append(degree - i);
                    }
                }
            }
        }

        return polynomial.toString();
    }

    //Evalua el polinomio y muestra los pasos
    public static double evaluatePolynomial(List<Integer> coefficients, double x) {
        double result = 0;
        int degree = coefficients.size() - 1;

        System.out.println("Evaluando f(" + x + ") = " + polynomialToString(coefficients) + ":");

        for (int i = 0; i < coefficients.size(); i++) {
            int coefficient = coefficients.get(i);
            double termValue = coefficient * Math.pow(x, degree - i);

            System.out.println("Termino " + (i + 1) + ": " + coefficient + " * " + x + "^" + (degree - i) + " = " + termValue);

            result += termValue;
        }

        System.out.println("f(" + x + ") = " + result);

        return result;
    }



    public static void main(String[] args) {
        int numRows = 8; //Triangulo hasta la fila 6
        List<List<Integer>> pascalTriangle = generatePascalTriangle(numRows);

        System.out.println("Triangulo de Pascal:");
        printPascalTriangle(pascalTriangle);

        int n = 4;  //Coeficientes de (x+1)^4
        List<Integer> coefficients = getCoefficients(n);
        System.out.println("\nCoeficientes de (x+1)^" + n + ": " + coefficients);

        String polynomialString = polynomialToString(coefficients);
        System.out.println("Polinomio (x+1)^" + n + " = " + polynomialString);

        //Evaluar el polinomio para x = 2
        double xValue = 2;
        evaluatePolynomial(coefficients, xValue);


    }
}