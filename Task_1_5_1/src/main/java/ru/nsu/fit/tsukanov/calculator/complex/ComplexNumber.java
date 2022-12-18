package ru.nsu.fit.tsukanov.calculator.complex;

import java.util.Objects;

public class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return "(" + real + "," + imaginary + ")";
    }

    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real + b.real, a.imaginary + b.imaginary);
    }

    public static ComplexNumber sub(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real - b.real, a.imaginary - b.imaginary);
    }

    public static ComplexNumber mul (ComplexNumber a, ComplexNumber b){
        return new ComplexNumber(a.real*b.real - a.imaginary*b.imaginary,
                a.real*b.imaginary + a.imaginary*b.real);
    }

    public static ComplexNumber div (ComplexNumber a, ComplexNumber b){
        double koef = b.real * b.real + b.imaginary * b.imaginary;
        double real = (a.real*b.real + a.imaginary*b.imaginary)/ koef;
        double imag = (a.imaginary*b.real - a.real*b.imaginary)/ koef;
        return new ComplexNumber(real, imag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.real, real) == 0 && Double.compare(that.imaginary, imaginary) == 0;
    }

    public double module(){
        return Math.sqrt(real*real + imaginary*imaginary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }
}
