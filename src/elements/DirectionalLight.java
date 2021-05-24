package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light from an external source without attenuation
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * direction of the directional light
     */
    private final Vector _direction;

    /**
     * constructor with parameters
     * @param intensity light color
     * @param direction common direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point3D point3D) {
        return _direction;
    }
}
