package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

public class Plane implements Geometry{

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
        Vector v1=p2.subtract(p1);
        Vector v2=p3.subtract(p1);
        Vector n=v1.crossProduct(v2);
        normal=n.normalize();
        q0=p1;
    }

    /**
     * primary constructor for Plane
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
     * @return normal to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * getter of the plane point
     * @return plane point
     */
    public Point3D getPoint3D() {
        return q0;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "point3D=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
