package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class for finite tube
 */
public class Cylinder extends Tube {

    /**
     * the height of the cylinder
     */
    double _height;

    /**
     * constructor of the cylinder
     *
     * @param axisRay cylinder direction
     * @param radius  cylinder radius
     * @param height  cylinder height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height == 0)
            throw new IllegalArgumentException("height cannot be 0");

        this._height = height;
    }

    /**
     * getter of the cylinder hight
     *
     * @return the height of the cylinder
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + radius +
                '}';
    }

}
