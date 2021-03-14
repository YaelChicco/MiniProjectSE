package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{

    private Point3D point3D;
    private Vector normal;
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector v1=p2.subtract(p1);
        Vector v2=p3.subtract(p1);
        normal=v1.crossProduct(v2);
        point3D=p1;
    }

    public Plane(Point3D point3D, Vector normal) {
        this.point3D = point3D;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }
}
