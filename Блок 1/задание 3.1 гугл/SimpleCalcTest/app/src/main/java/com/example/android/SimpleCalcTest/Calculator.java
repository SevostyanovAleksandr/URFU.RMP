
package com.example.android.SimpleCalcTest;

/**
 * * Класс утилиты для SimpleCalc для выполнения фактических вычислений.
 */
public class Calculator {

    // Доступные операции
    public enum Operator {ADD, SUB, DIV, MUL}

    /**
     * Операция сложения
     */
    public double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    /**
     * Операция вычитания
     */
    public double sub(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    /**
     * Операция разделения
     */
    public double div(double firstOperand, double secondOperand) {
        if (secondOperand == 0 ) {
            throw new IllegalArgumentException("You cannot divide by zero");
        }
        return firstOperand / secondOperand;
    }

    /**
     * Операция умножения
     */
    public double mul(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }
}