package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    public Color getIntensity() {
        return _intensity;
    }

    public AmbientLight(Color Ia, int Ka, Color intensity) {
        _intensity = Ia.scale(Ka);
    }
}
