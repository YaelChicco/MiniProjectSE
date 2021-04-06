package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

public class Sphere implements Geometry{

    /**
     * Mid-ball point
     */
    private Point3D _center;
    /**
     * Radius of the ball
     */
    private double radius;

    /**
     * constructor of the sphere
     * @param center ball center point
     * @param radius ball radius
     */
    public Sphere(Point3D center, double radius) {
        this._center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v=point.subtract(_center);
        return v.normalize();
    }

    /**
     * getter of the ball center point
     * @return ball center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter of the ball radius
     * @return ball radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + radius +
                '}';
    }
}
