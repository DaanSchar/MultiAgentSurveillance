package nl.maastrichtuniversity.dke.util;


public class Distribution {

    public static double uniform() {
        return Math.random();
    }

    public static double uniform(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public static double normal(double mean, double stddev) {
        return mean + stddev * Math.sqrt(-2 * Math.log(uniform())) * Math.cos(2 * Math.PI * uniform());
    }

    public static double exponential(double lambda) {
        return -Math.log(uniform()) / lambda;
    }

}
