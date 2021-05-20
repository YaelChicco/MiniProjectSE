package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight implements LightSource{

    private final Vector centerDirection;

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

    @Override
    public  Vector getL(Point3D point3D) {
        return super.getL(point3D);
    }
}
