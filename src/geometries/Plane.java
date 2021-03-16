package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{

    /**
     * the reference point of the plane
     */
    private Point3D point3D;

    /**
     * the normal to the plane
     */
    private Vector normal;

    /**
     * Calculates the normal to a triangle and saves one of the points as the reference point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector v1=p2.subtract(p1);
        Vector v2=p3.subtract(p1);
        normal=v1.crossProduct(v2);
        point3D=p1;
    }

    /**
     * primary constructor for Plane
     * @param point3D is the reference point
     * @param normal  is the normal to the plane
     */
    public Plane(Point3D point3D, Vector normal) {
        this.point3D = point3D;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }
}
