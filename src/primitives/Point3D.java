package primitives;

/**
 * class for a 3 coordinates point
 */
public class Point3D {
    /**
     * x coordinate
     */
    final Coordinate _x;
    /**
     * y coordinate
     */
    final Coordinate _y;
    /**
     * z coordinate
     */
    final Coordinate _z;
    /**
     * 0 vector
     */
    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * constructor that gets the 3 coordinates
     *
     * @param x value for creating x Coordinate
     * @param y value for creating y Coordinate
     * @param z value for creating z Coordinate
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * adding a vector to the point
     *
     * @param vector for adding
     * @return new point (after adding)
     */
    public Point3D add(Vector vector) {
        return new Point3D(_x.coord + vector._head._x.coord, _y.coord + vector._head._y.coord, _z.coord + vector._head._z.coord);
    }

    /**
     * vector subtraction
     *
     * @param point head of the new vector (after subtraction)
     * @return vector from the given point to the current point
     */
    public Vector subtract(Point3D point) {
        return new Vector(_x.coord - point._x.coord, _y.coord - point._y.coord, _z.coord - point._z.coord);
    }

    /**
     * the distance between 2 point squared
     *
     * @param other the other point
     * @return the distance squared
     */
    public double distanceSquared(Point3D other) {
        return ((other._x.coord - _x.coord) * (other._x.coord - _x.coord)
                +(other._y.coord - _y.coord) * (other._y.coord - _y.coord)
                +(other._z.coord - _z.coord) * (other._z.coord - _z.coord));
    }

    /**
     * distance between 2 point
     *
     * @param other the other point
     * @return the distance
     */
    public double distance(Point3D other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public String toString() {
        return "Point3D(" + "" + _x + "," + _y + "," + _z + ')';
    }

    /**
     * getter for the x coordinate
     *
     * @return x coordinate
     */
    public double getX() {
        return _x.coord;
    }

    /**
     * getter for the y coordinate
     *
     * @return y coordinate
     */
    public double getY() {
        return _y.coord;
    }

    /**
     * getter for the z coordinate
     *
     * @return z coordinate
     */
    public double getZ() {
        return _z.coord;
    }
}
