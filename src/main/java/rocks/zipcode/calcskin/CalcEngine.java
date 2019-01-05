package rocks.zipcode.calcskin;

public class CalcEngine {

    CalcEngine() {
    }

    public static double addition(double x, double y) {
        return (x + y);
    }

    public static double subtraction(double x, double y) {
        return (x - y);
    }

    public static double multiplication(double x, double y) {

        return (x * y);
    }

    public static double division(double x, double y) {
        return (x / y);
    }

    public static double square(double x) {
        return Math.pow(x, 2);
    }

    public static double squareroot(double x) {
        return Math.sqrt(x);
    }

    public static double exponent(double x, double y) {

        return Math.pow(x, y);
    }

    public static double inverse(double x) {
        return (1 / x);
    }

    public static double invert(double x) {
        return (-x);
    }

/*
    public static double findSin(double x) {
        return Math.sin(x);
    }

    public static double findCos(double x) {
        return Math.cos(x);
    }

    public static double findTan(double x) {
        return Math.tan(x);
    }

    public static double findAsin(double x) {
        return Math.asin(x);
    }

    public static double findAcos(double x) {
        return Math.acos(x);
    }

    public static double findAtan(double x) {
        return Math.atan(x);
    }
*/

}
