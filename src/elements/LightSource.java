package elements;

import primitives.*;

/**
 * interface for source of the light
 */
public interface LightSource {

    /**
     * calculates the amount of light hitting the point
     *
     * @param point3D the intersection point
     * @return amount of light hitting the point
     */
    public Color getIntensity(Point3D point3D);

    /**
     * calculates the distance from the light source to the point
     *
     * @param point3D the intersection point
     * @return distance from the light source to the point
     */
    public Vector getL(Point3D point3D);

    double getDistance(Point3D point);
}
