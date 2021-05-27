package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * class for spot light source
 */
public class SpotLight extends PointLight implements LightSource {

    /**
     * direction of the spot light
     */
    private final Vector centerDirection;

    /**
     * constructor with parameters
     *
     * @param intensity       color of the light
     * @param position        position of the light in the scene
     * @param centerDirection direction of the spot light
     */
    public SpotLight(Color intensity, Point3D position, Vector centerDirection) {
        super(intensity, position);
        this.centerDirection = centerDirection.normalized();
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        Vector l = super.getL(point3D);
        Color intensity = super.getIntensity(point3D);
        return intensity.scale(Math.max(0, Util.alignZero(l.dotProduct(centerDirection))));
    }
}
