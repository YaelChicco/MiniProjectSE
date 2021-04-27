package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;

public class Sphere implements Geometry {

    /**
     * Mid-ball point
     */
    private Point3D _center;
    /**
     * Radius of the ball
     */
    private double _radius;

    /**
     * constructor of the sphere
     *
     * @param center ball center point
     * @param radius ball radius
     */
    public Sphere(Point3D center, double radius) {
        this._center = center;
        this._radius = radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = point.subtract(_center);
        return v.normalize();
    }

    /**
     * getter of the ball center point
     *
     * @return ball center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter of the ball radius
     *
     * @return ball radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Point3D D = _center;
        Vector V = ray.getDir();
        double tm = 0, d = 0;
        try {
            Vector U = D.subtract(p0);
            tm = V.dotProduct(U);
            d = Math.sqrt(U.lengthSquared() - tm * tm);
        } catch (IllegalArgumentException e) {
        }

        if (d >= _radius) {
            return null;
        }
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if ((t1 > 0) && (t2 > 0)) {
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);
            return (List.of(p1, p2));
        }

        if (t1 > 0) {
            Point3D p1 = ray.getPoint(t1);
            return List.of(p1);
        }

        if (t2 > 0) {
            Point3D p2 = ray.getPoint(t2);
            return List.of(p2);
        }

        return null;
    }

}
