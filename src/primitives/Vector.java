package primitives;

import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

public class Vector {
    /**
     * end point of the vector
     */
    Point3D _head;

    /**
     * primary constructor for Vector
     * @param head is the start point of the vector
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("head of vector cannot be point(0,0,0,)");
        }

        _head = new Point3D(head._x.coord,head._y.coord,head._z.coord);
    }

    /**
     * constructor for Vector
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    /**
     * getter of the head of the vector
     * @return _head is the start point of the vector
     */
    public Point3D getHead() {
        return _head;
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
     * @param number- parameter to multiply the vector with
     * @return the vector after multiplication
     */
    public Vector scale(double number) {

        if (isZero(number)) {
            throw new ArithmeticException("number equals 0");
        }

        double x = _head._x.coord *number;
        double y = _head._y.coord *number;
        double z = _head._z.coord *number;

        Vector newVector=new Vector(x, y, z);
        return newVector;
    }

    /**
     * does cross product between two vectors
     * @param other is the second vector in the product
     * @return vector
     */
    public Vector crossProduct(Vector other) {
        double x = (_head._y.coord * other.getHead()._z.coord)-(_head._z.coord * other.getHead()._y.coord);
        double y = (_head._z.coord * other.getHead()._x.coord)-(_head._x.coord * other.getHead()._z.coord);
        double z = (_head._x.coord * other.getHead()._y.coord)-(_head._y.coord * other.getHead()._x.coord);
        return new Vector(x,y,z);
    }

    /**
     * does dot product between two vectors
     * @param other is the second vector in the product
     * @return number
     */
    public double dotProduct(Vector other) {

        double x = (_head._x.coord * other.getHead()._x.coord);
        double y = (_head._y.coord * other.getHead()._y.coord);
        double z = (_head._z.coord * other.getHead()._z.coord);
        return x+y+z;
    }

    /**
     * does add between two vectors
     * @param other vector for adding
     * @return original vector plus the other vector
     */
    public Vector add(Vector other) {
        if (other.scale(-1).equals(this)) {
            throw new IllegalArgumentException("parameter vector cannot be the opposite of this vector");
        }

        Vector newVector=other;
        newVector._head.add(this);
        return newVector;
    }

    /**
     * does subtract between two vectors
     * @param other second vector in the subtraction operation
     * @return current vector minus the other vector
     */
    public Vector subtract(Vector other) {
        if (other.equals(this)) {
            throw new IllegalArgumentException("parameter vector cannot be equals to this vector");
        }
        return _head.subtract(other._head);
    }

    /**
     * calculates the squared length of vector
     * @return the squared length
     */
    public double lengthSquared() {
        double xx = (_head._x.coord * _head._x.coord);
        double yy = (_head._y.coord * _head._y.coord);
        double zz = (_head._z.coord * _head._z.coord);
        return xx + yy + zz;
    }

    /**
     * calculates the length of vector
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizes the current vector
     * @return the current vector after normalization
     */
    public Vector normalize() {

        double len = length();

        if (isZero(len)) {
            throw new ArithmeticException("length equals 0");
        }

        double x = _head._x.coord / len;
        double y = _head._y.coord / len;
        double z = _head._z.coord / len;

        _head = new Point3D(x, y, z);

        if (ZERO.equals(_head)) {
            throw new IllegalArgumentException("point(0,0,0) vector");
        }

        return this;
    }

    /**
     * places the current vector in a new vector and normalizes it
     * @return the new vector after normalization
     */
    public Vector normalized() {
        Vector result=new Vector(_head);
        result.normalize();
        return result;
    }

    /**
     * getter of the end point of the vector
     * @return end point
     */
    public Point3D get_head() {
        return _head;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
