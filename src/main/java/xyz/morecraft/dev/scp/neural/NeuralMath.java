package xyz.morecraft.dev.scp.neural;

import java.util.function.Function;

public class NeuralMath {

    private NeuralMath() {
    }

    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static Function<Double, Double> sigmoid() {
        return NeuralMath::sigmoid;
    }

    public static double[][] sigmoid(double[][] t) {
        final double[][] n = new double[t.length][t[0].length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                n[i][j] = sigmoid(t[i][j]);
            }
        }
        return n;
    }

    public static double sigmoidDerivative(double x) {
        return x * (1.0 - x);
    }

    public static Function<Double, Double> sigmoidDerivative() {
        return NeuralMath::sigmoidDerivative;
    }

    public static double[][] sigmoidDerivative(double[][] t) {
        final double[][] n = new double[t.length][t[0].length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                n[i][j] = sigmoidDerivative(t[i][j]);
            }
        }
        return n;
    }

    public static double[][] proccess(double[][] t, Function<Double, Double> f) {
        final double[][] n = new double[t.length][t[0].length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                n[i][j] = f.apply(t[i][j]);
            }
        }
        return n;
    }

    public static double mean(double[][] t) {
        double sum = 0;
        for (double[] d : t) {
            for (double v : d) {
                sum += v;
            }
        }
        return sum / (t.length * t[0].length);
    }

    public static double[][] abs(double[][] t) {
        final double[][] n = new double[t.length][t[0].length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                n[i][j] = Math.abs(t[i][j]);
            }
        }
        return n;
    }

    public static double[][] transpose(double[][] m) {
        final double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }

    public static double[][] dot(double[][] a, double[][] b) {
        final double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        return dot(a, b);
    }

    public static double[][] multiply(double factor, double[][] a) {
        final double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] * factor;
            }
        }
        return result;
    }

    public static double[][] subtract(double[][] a, double[][] b) {
        final double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }
}
