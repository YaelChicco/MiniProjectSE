package elements;

import primitives.Color;

class Light {
    /**
     * color of the ambient light
     */
    private final Color _intensity;

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter of _intensity
     * @return _intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
