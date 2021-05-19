package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public abstract class Geometry implements Intersectable {
    protected Color _emission=Color.BLACK;

    public Color get_emission() {
        return _emission;
    }

    public Geometry set_emission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * calculates the normal of the geometry
     * @param point the normal point in the geometry
     * @return normal of the geometry
     */
    public abstract Vector getNormal(Point3D point);
}
