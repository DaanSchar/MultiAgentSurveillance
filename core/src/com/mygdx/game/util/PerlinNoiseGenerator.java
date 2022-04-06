package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.MathUtils;

/**
 * Adapted from
 * <a href="http://devmag.org.za/2009/04/25/perlin-noise/">http://devmag.org.za/2009/04/25/perlin-noise/</a>.
 *
 * @author badlogic
 */
public final class PerlinNoiseGenerator {

    public static double[][] generateWhiteNoise(int width, int height) {
        double[][] noise = new double[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noise[x][y] = MathUtils.random();
            }
        }
        return noise;
    }

    public static double interpolate(double x0, double x1, double alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }

    public static double[][] generateSmoothNoise(double[][] baseNoise, int octave) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;
        double[][] smoothNoise = new double[width][height];

        int samplePeriod = 1 << octave; // calculates 2 ^ k
        float sampleFrequency = 1.0f / samplePeriod;
        for (int i = 0; i < width; i++) {
            int sampleI0 = (i / samplePeriod) * samplePeriod;
            int sampleI1 = (sampleI0 + samplePeriod) % width; // wrap around
            float horizontalBlend = (i - sampleI0) * sampleFrequency;

            for (int j = 0; j < height; j++) {
                int sampleJ0 = (j / samplePeriod) * samplePeriod;
                int sampleJ1 = (sampleJ0 + samplePeriod) % height; // wrap around
                double verticalBlend = (j - sampleJ0) * sampleFrequency;
                double top = interpolate(
                        baseNoise[sampleI0][sampleJ0],
                        baseNoise[sampleI1][sampleJ0],
                        horizontalBlend
                );
                double bottom = interpolate(
                        baseNoise[sampleI0][sampleJ1],
                        baseNoise[sampleI1][sampleJ1],
                        horizontalBlend
                );
                smoothNoise[i][j] = interpolate(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    public static double[][] generatePerlinNoise(double[][] baseNoise, int octaveCount) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;
        double[][][] smoothNoise = new double[octaveCount][][]; // an array of 2D arrays containing
        final float persistence = 0.7f;

        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        double[][] perlinNoise = new double[width][height]; // an array of floats initialised to 0

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    public static double[][] generatePerlinNoise(int width, int height, int octaveCount) {
        double[][] baseNoise = generateWhiteNoise(width, height);
        return generatePerlinNoise(baseNoise, octaveCount);
    }

    public static byte[] generateHeightMap(int width, int height, int min, int max, int octaveCount) {
        double[][] baseNoise = generateWhiteNoise(width, height);
        double[][] noise = generatePerlinNoise(baseNoise, octaveCount);
        byte[] bytes = new byte[baseNoise.length * baseNoise[0].length];
        int idx = 0;
        int range = max - min;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bytes[idx++] = (byte) (noise[x][y] * range + min);
            }
        }
        return bytes;
    }

    public static Pixmap generatePixmap(int width, int height, int min, int max, int octaveCount) {
        byte[] bytes = generateHeightMap(width, height, min, max, octaveCount);
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        for (int i = 0, idx = 0; i < bytes.length; i++) {
            byte val = bytes[i];
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, (byte) 255);
        }
        return pixmap;
    }

    private PerlinNoiseGenerator() {
    };

}
