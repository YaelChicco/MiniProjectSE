package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

/**
 * class for representing a triangle in the scene
 */
public class Triangle extends Polygon{

    /**
     * constructor that gets points and build the triangle
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> geoPointList = super.findGeoIntersections(ray);
        if(geoPointList == null)
            return null;
        return List.of(new GeoPoint(this, geoPointList.get(0).point));
    }

    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
