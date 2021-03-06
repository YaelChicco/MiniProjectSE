package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for representing a plane in the scene
 */
public class Plane extends Geometry {

    /**
     * the reference point of the plane
     */
    final Point3D q0;
    /**
     * the normal to the plane
     */
    final Vector normal;

    /**
     * constructor that calculates the normal to a triangle and saves one of the points as the reference point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        Vector n = v1.crossProduct(v2);
        normal = n.normalize();
        q0 = p1;
    }

    /**
     * primary constructor for Plane
     *
     * @param point3D is the reference point
     * @param normal  is the normal to the plane
     */
    public Plane(Point3D point3D, Vector normal) {
        this.q0 = point3D;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     * getter of the normal of the plane
     *
     * @return normal to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * getter of the plane point
     *
     * @return plane point
     */
    public Point3D getPoint3D() {
        return q0;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        Point3D p0 = ray.getP0();
        Vector V = ray.getDir();
        if (p0.equals(q0))
            return null;
        Vector U = q0.subtract(p0);

        //tD=denominator of parameter t
        double tD = normal.dotProduct(U);

        //tN=numerator of parameter t
        double tN = normal.dotProduct(V);

        //the ray begins in the same point which appears as
        //reference point in the plane
        if (p0.equals(q0)) {
            return null;
        }

        //the ray begins in the plane
        if (isZero(tD)) {
            return null;
        }

        //Ray is parallel to the plane
        if (isZero(tN)) {
            return null;
        }
        double t = tD / tN;

        //if there is an intersection point
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            Point3D iP = ray.getPoint(t);
            return List.of(new GeoPoint(this, iP));
        }
        return null;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "point3D=" + q0 +
                ", normal=" + normal +
                '}';
    }

}
