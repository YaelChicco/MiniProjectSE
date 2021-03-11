package primitives;

import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

public class Vector {
    Point3D _head;

    /**
     * primary constructor for Vector
     *
     * @param head
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("head of vector cannot be point(0,0,0,)");
        }

        _head = new Point3D(head._x.coord,head._y.coord,head._z.coord);
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

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

    public Vector crossProduct(Vector vector) {
        return null;
    }

    public double dotProduct(Vector vector) {
        return 0d;
    }

    public Vector subtract(Vector other) {
        if (other.equals(this)) {
            throw new IllegalArgumentException("parameter vector cannot be equals to this vector");
        }

        return other._head.subtract(_head);
    }

    public double lengthSquared() {
        double xx = (_head._x.coord * _head._x.coord);
        double yy = (_head._y.coord * _head._y.coord);
        double zz = (_head._z.coord * _head._z.coord);
        return xx + yy + zz;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

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

    public Vector normalized() {
        Vector result=new Vector(_head);
        result.normalize();
        return result;
    }


}
