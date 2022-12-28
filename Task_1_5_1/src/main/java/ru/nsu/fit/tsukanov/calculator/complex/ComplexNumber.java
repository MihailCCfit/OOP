package ru.nsu.fit.tsukanov.calculator.complex;

public record ComplexNumber(double real, double imaginary) {
    /**
     * Return real part of complex number.
     *
     * @return real part of complex number
     */
    @Override
    public double real() {
        return real;
    }

    /**
     * Return imaginary part of complex number.
     *
     * @return imaginary part of complex number
     */
    @Override
    public double imaginary() {
        return imaginary;
    }

    /**
     * Creates complex number by the two parts.
     *
     * @param real      the real part
     * @param imaginary the imaginary part
     */
    public ComplexNumber {
    }

    @Override
    public String toString() {
        if (imaginary == 0 && real == 0) {
            return "0";
        }
        if (real == 0) {
            return imaginary + "i";
        }
        if (imaginary == 0) {
            return real + "";
        }
        return "(" + real + "," + imaginary + "i)";
    }

    /**
     * a+b. Sum of the complex numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the summary of numbers
     */
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real + b.real, a.imaginary + b.imaginary);
    }

    /**
     * a-b. Subtraction of the complex numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the subtraction of numbers
     */
    public static ComplexNumber sub(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real - b.real, a.imaginary - b.imaginary);
    }

    /**
     * a*b. Multiplying of the complex numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the multiplying of numbers
     */
    public static ComplexNumber mul(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real * b.real - a.imaginary * b.imaginary,
                a.real * b.imaginary + a.imaginary * b.real);
    }

    /**
     * a/b. Multiplying of the complex numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the division of numbers
     */
    public static ComplexNumber div(ComplexNumber a, ComplexNumber b) {
        double koef = b.real * b.real + b.imaginary * b.imaginary;
        double real = (a.real * b.real + a.imaginary * b.imaginary) / koef;
        double imag = (a.imaginary * b.real - a.real * b.imaginary) / koef;
        return new ComplexNumber(real, imag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return that.real == real && that.imaginary == imaginary;
    }

    /**
     * sqrt(real^2 + imaginary^2). Return the module of this complex number (distance from the centre).
     *
     * @return the module of this complex number
     */
    public double module() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }


}
