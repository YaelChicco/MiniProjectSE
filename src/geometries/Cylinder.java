package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    /**
     * the height of the cylinder
     */
    double height;

    /**
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * constructor of the tube
     *
     * @param axisRay tube direction
     * @param radius  tube radius
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height==0)
            throw new IllegalArgumentException("height cannot be 0");

        this.height=height;
    }
}
