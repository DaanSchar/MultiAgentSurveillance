package nl.maastrichtuniversity.dke.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * 3D vector class.
 *
 * can also be used as a 2D vector, where z is always 0.
 *
 * @Author Daan
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * returns the length of this vector.
     * @return length of this vector.
     */
    public double length() {
        return Math.sqrt(
                Math.pow(this.x, 2) +
                Math.pow(this.y, 2) +
                Math.pow(this.z, 2)
        );
    }

    /**
     * returns the unit vector of this vector, which is the vector
     * with the same direction but a length of 1.
     * @return vector with length 1.
     */
    public Vector norm() {
        double length = this.length();

        if (this.isZero())
            return new Vector(0, 0, 0);

        return new Vector(
                this.x / length,
                this.y / length,
                this.z / length
        );
    }

    /**
     * computes the distance between two vectors.
     * @param other the other vector.
     * @return distance between this and other.
     */
    public double distance(Vector other) {
        return Math.sqrt(
                Math.pow(this.x - other.getX(), 2) +
                Math.pow(this.y - other.getY(), 2) +
                Math.pow(this.z - other.getZ(), 2)
        );
    }

    /**
     * multiplies this vector, or all components of the vector, by a scalar.
     * @param scalar the scalar we multiply by.
     * @return multiplied vector.
     */
    public Vector mul(double scalar) {
        return new Vector(
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        );
    }

    /**
     * vector addition with a scalar
     * @param scalar the value we add by.
     * @return the resulting vector.
     */
    public Vector add(double scalar) {
        return new Vector(
                this.x + scalar,
                this.y + scalar,
                this.z + scalar
        );
    }

    /**
     * vector addition.
     * @param other the vector we add by.
     * @return the resulting vector.
     */
    public Vector add(Vector other) {
        return new Vector(
                this.x + other.getX(),
                this.y + other.getY(),
                this.z + other.getZ()
        );
    }

    /**
     * vector subtraction with a scalar
     * @param scalar the value we subtract by.
     * @return the resulting vector.
     */
    public Vector sub(double scalar) {
        return new Vector(
                this.x - scalar,
                this.y - scalar,
                this.z - scalar
        );
    }

    /**
     * vector subtraction.
     * @param other the vector we subtract by.
     * @return the resulting vector.
     */
    public Vector sub(Vector other) {
        return new Vector(
                this.x - other.getX(),
                this.y - other.getY(),
                this.z - other.getZ()
        );
    }

    /**
     * dot product of two vectors.
     * @return new vector with the dot product.
     */
    public double dot(Vector other) {
        return  this.x * other.x +
                this.y * other.y +
                this.z * other.z;
    }

    /**
     * cross product of two vectors.
     * @return new vector with the cross product.
     */
    public Vector cross(Vector other) {
        return new Vector(
                this.getY() * other.getZ() - this.getZ() * other.getY(),
                this.getZ() * other.getX() - this.getX() * other.getZ(),
                this.getX() * other.getY() - this.getY() * other.getX()
        );
    }

    /**
     * rotates the vector around the z axis (so 2D)
     * @param angle the angle of rotation in degrees
     * @return rotated vector
     */
    public Vector rotate(double angle) {
        double x1 = (this.x * Math.cos(Math.toRadians(angle)) - this.y * Math.sin(Math.toRadians(angle)));
        double y1 = (this.x * Math.sin(Math.toRadians(angle)) + this.y * Math.cos(Math.toRadians(angle))) ;
        return new Vector(x1, y1);
    }

    /**
     * computes the angle between two vectors.
     * @param other the other vector.
     * @return angle in degrees
     */
    public double angle(Vector other) {
        return Math.acos(this.dot(other) / (this.length() * other.length())) / Math.PI * 180;
    }

    /**
     * computes the angle between the vector and the x-axis
     * @return angle in degrees
     */
    public double angle() {
        return Math.atan2(this.y, this.x) / Math.PI * 180;
    }

    /**
     * returns a vector with the absolute values of the components.
     */
    public Vector abs() {
        return new Vector(
                Math.abs(this.x),
                Math.abs(this.y),
                Math.abs(this.z)
        );
    }

    /**
     * checks if the vector is zero.
     * @return true if the vector is zero.
     */
    public boolean isZero() {
        return this.x == 0 && this.y == 0 && this.z == 0;
    }
}
