package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    public Color getIntensity() {
        return _intensity;
    }

    public AmbientLight(Color Ia, int Ka) {
        _intensity = Ia.scale(Ka);
    }
}
