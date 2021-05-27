package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class for representing a triangle in the scene
 */
public class Triangle extends Polygon {

    /**
     * constructor that gets points and build the triangle
     *
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> result = plane.findGeoIntersections(ray, maxDistance);
        if (result == null)
            return null;
        Point3D p0 = ray.getP0();

        Vector v1, v2, v3, n1, n2;
        v1 = (vertices.get(0)).subtract(p0);
        v2 = (vertices.get(1)).subtract(p0);
        v3 = (vertices.get(2)).subtract(p0);
        n1 = v1.crossProduct(v2).normalize();
        n2 = v2.crossProduct(v3).normalize();

        Vector rayV = ray.getDir();
        double vn1 = alignZero(rayV.dotProduct(n1));
        double vn2 = alignZero(rayV.dotProduct(n2));
        if (vn1 == 0 || vn2 == 0 || vn1 * vn2 < 0)
            return null;

        n2 = v3.crossProduct(v1).normalize();
        vn2 = alignZero(rayV.dotProduct(n2));
        if (vn2 == 0 || vn1 * vn2 < 0)
            return null;

        result.get(0).geometry = this;
        return result;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
