package nl.maastrichtuniversity.dke.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 3D vector class.
 *
 * @Author Daan
 */
public class Vector {

    private @Getter @Setter double x;
    private @Getter @Setter double y;
    private @Getter @Setter double z;

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

    public double length() {
        return Math.sqrt(
                Math.pow(this.x, 2) +
                Math.pow(this.y, 2) +
                Math.pow(this.z, 2)
        );
    }

    public Vector norm() {
        double length = this.length();

        return new Vector(
                this.x / length,
                this.y / length,
                this.z / length
        );
    }

    public double getDistance(Vector other) {
        return Math.sqrt(
                Math.pow(this.x - other.getX(), 2) +
                Math.pow(this.y - other.getY(), 2) +
                Math.pow(this.z - other.getZ(), 2)
        );
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0 && Double.compare(vector.z, z) == 0;
    }


    public Vector copy(){
        return new Vector(x,y,z);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    public Vector rotate(double angle) {
        double x1 = (this.x * Math.cos(Math.toRadians(angle)) - this.y * Math.sin(Math.toRadians(angle)));
        double y1 = (this.x * Math.sin(Math.toRadians(angle)) + this.y * Math.cos(Math.toRadians(angle))) ;
        return new Vector(x1, y1);

    }


}
