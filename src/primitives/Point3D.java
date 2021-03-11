package primitives;

import java.security.PublicKey;

public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * primary constructor for 3D Point
     *
     * @param x value for x Coordinate
     * @param y value for y Coordinate
     * @param z value for z Coordinate
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * @param x value for creating x Coordinate
     * @param y value for creating y Coordinate
     * @param z value for creating z Coordinate
     */

    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    public Point3D add(Vector vector) {
        double x=_x.coord+vector._head._x.coord;
        double y=_y.coord+vector._head._y.coord;
        double z=_z.coord+vector._head._z.coord;

        return new Point3D(x,y,z);
    }

    public Vector subtract(Point3D point) {
        double x=_x.coord-point._x.coord;
        double y=_y.coord-point._y.coord;
        double z=_z.coord-point._z.coord;

        return new Vector(x,y,z);
    }

    public double distanceSquared(Point3D other){
        double xx=(other._x.coord- _x.coord)*(other._x.coord- _x.coord);
        double yy=(other._y.coord- _y.coord)*(other._y.coord- _y.coord);
        double zz=(other._z.coord- _z.coord)*(other._z.coord- _z.coord);

        return (xx+yy+zz);
    }

    public double distance(Point3D other) {
        return Math.sqrt(distanceSquared(other));
    }
        @Override
    public String toString() {
        return "Point3D(" + "" + _x + "," + _y + "," + _z + ')';
    }
}
