package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;

/**
 * class for representing a sphere in the scene
 */
public class Sphere extends Geometry {

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
     * @param radius ball radius
     * @param center ball center point
     */
    public Sphere(double radius, Point3D center) {
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
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

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);
            return (List.of(new GeoPoint(this, p1), new GeoPoint(this, p2)));
        }

        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point3D p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }

        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }

        return null;
    }
}
