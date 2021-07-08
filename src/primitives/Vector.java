package primitives;

import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

/**
 * class for a vector in the scene (3 coordinates start point)
 */
public class Vector {

    /**
     * end point of the vector
     */
    public Point3D _head;

    /**
     * primary constructor for Vector
     *
     * @param head is the start point of the vector
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("head of vector cannot be point(0,0,0)");
        }
        _head = new Point3D(head._x.coord, head._y.coord, head._z.coord);
    }

    /**
     * constructor with parameters
     *
     * @param x x coordinate of the start point
     * @param y y coordinate of the start point
     * @param z z coordinate of the start point
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * multiplies vector by number
     *
     * @param number- parameter to multiply the vector with
     * @return the vector after multiplication
     */
    public Vector scale(double number) {
        if (isZero(number)) {
            throw new ArithmeticException("number equals 0");
        }
        return new Vector(_head._x.coord * number, _head._y.coord * number, _head._z.coord * number);
    }

    /**
     * does cross product between two vectors
     *
     * @param other is the second vector in the product
     * @return vector
     */
    public Vector crossProduct(Vector other) {
        return new Vector((_head._y.coord * other._head._z.coord) - (_head._z.coord * other._head._y.coord),
                (_head._z.coord * other._head._x.coord) - (_head._x.coord * other._head._z.coord),
                (_head._x.coord * other._head._y.coord) - (_head._y.coord * other._head._x.coord));
    }

    /**
     * does dot product between two vectors
     *
     * @param other is the second vector in the product
     * @return number
     */
    public double dotProduct(Vector other) {
        return (_head._x.coord * other._head._x.coord) + (_head._y.coord * other._head._y.coord) + (_head._z.coord * other._head._z.coord);
    }

    /**
     * does add between two vectors
     *
     * @param other vector for adding
     * @return original vector plus the other vector
     */
    public Vector add(Vector other) {
        return new Vector(other._head.add(this));
    }

    /**
     * does subtract between two vectors
     *
     * @param other second vector in the subtraction operation
     * @return current vector minus the other vector
     */
    public Vector subtract(Vector other) {
        return _head.subtract(other._head);
    }

    /**
     * calculates the squared length of vector
     *
     * @return the squared length
     */
    public double lengthSquared() {
        return (_head._x.coord * _head._x.coord) + (_head._y.coord * _head._y.coord) + (_head._z.coord * _head._z.coord);
    }

    /**
     * calculates the length of vector
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizes the current vector
     *
     * @return the current vector after normalization
     */
    public Vector normalize() {
        double len = length();
        _head = new Point3D(_head._x.coord / len, _head._y.coord / len, _head._z.coord / len);
        return this;
    }

    /**
     * places the current vector in a new vector and normalizes it
     *
     * @return the new vector after normalization
     */
    public Vector normalized() {
        return new Vector(_head).normalize();
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
