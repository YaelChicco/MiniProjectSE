package geometries;

import primitives.Point3D;
import primitives.Vector;

public interface Geometry {

    /**
     * calculates the normal of the geometry
     * @param point the normal point in the geometry
     * @return normal of the geometry
     */
    Vector getNormal(Point3D point);
}
