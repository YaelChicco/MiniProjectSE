package geometries;

import primitives.Point3D;
import primitives.Vector;


public class Sphere implements Geometry{
    /**
     * Mid-ball point
     */
    private Point3D center;
    /**
     * Radius of the ball
     */
    private double radius;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
