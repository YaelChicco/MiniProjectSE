package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

public class Tube implements Geometry{
    /**
     * tube direction
     */
    private Ray axisRay;
    /**
     * tube radius
     */
    private double radius;

    /**
     * constructor of the tube
     * @param axisRay tube direction
     * @param radius tube radius
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter of the tube direction
     * @return tube direction
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter of the tube radius
     * @return tube radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return Double.compare(tube.radius, radius) == 0 && Objects.equals(axisRay, tube.axisRay);
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
