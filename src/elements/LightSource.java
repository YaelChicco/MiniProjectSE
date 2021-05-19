package elements;

import primitives.*;

public interface LightSource {
    public Color getIntensity(Point3D point3D);
    public Vector getL(Point3D point3D);
}
