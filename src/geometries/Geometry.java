package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public interface Geometry extends Intersectable {

    /**
     * calculates the normal of the geometry
     * @param point the normal point in the geometry
     * @return normal of the geometry
     */
    Vector getNormal(Point3D point);

    @Override
    default List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
