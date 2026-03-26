public class Complex {
    private double imaginary;
    private double real;

    public Complex() {
        this.imaginary = 0;
        this.real = 0;
    }

    public Complex(double real, double imaginary) {
        this.imaginary = imaginary;
        this.real = real;
    }

    public String toString() {
        if (this.imaginary > 0) return this.real + " + " + this.imaginary + "i";
        return this.real + " - " + Math.abs(this.imaginary) + "i";
    }

    public boolean equals(Complex that) {
        return (that.real == this.real) && (that.imaginary == this.imaginary);
    }

    public double getReal() {
        return this.real;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    public Complex add(Complex that) {
        return new Complex(this.real + that.real, this.imaginary + that.imaginary);
    }

    public Complex add(double factor) {
        return new Complex(this.real + factor, this.imaginary);
    }

    public Complex subtract(Complex that) {
        return new Complex(this.real - that.real, this.imaginary - that.imaginary);
    }

    public Complex subtract(double factor) {
        return new Complex(this.real - factor, this.imaginary);
    }

    public Complex multiply(Complex that) {
        double real = (this.real * that.real) - (this.imaginary * that.imaginary);
        double imaginary = (this.real * that.imaginary) + (this.imaginary * that.real);
        return new Complex(real, imaginary);
    }

    public Complex multiply(double factor) {
        return new Complex(this.real * factor, this.imaginary * factor);
    }

    public Complex divide(Complex that) {
        double c = that.real;
        double d = that.imaginary;
        double denom = c * c + d * d;
        if (denom == 0) {
            throw new ArithmeticException("Division by zero complex number");
        }

        double a = this.real;
        double b = this.imaginary;

        double real = (a * c + b * d) / denom;
        double imaginary = (b * c - a * d) / denom;

        return new Complex(real, imaginary);
    }

    public Complex divide(double factor) {
        return new Complex(this.real / factor, this.imaginary / factor);
    }

    public Complex square() {
        return multiply(this);
    }

    public double abs() {
        return Math.sqrt((this.real * this.real) + (this.imaginary * this.imaginary));
    }
}