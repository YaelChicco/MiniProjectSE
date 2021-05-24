package elements;

import primitives.Color;

/**
 * class for representing light
 */
class Light {

    /**
     * color of the light
     */
    private final Color _intensity;

    /**
     * constructor with parameters
     * @param intensity color of the light source
     */
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
