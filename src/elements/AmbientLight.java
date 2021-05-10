package elements;

import primitives.Color;

/**
 * ambient light of the scene
 */
public class AmbientLight {

    /**
     * color of the ambient light
     */
    private final Color _intensity;

    /**
     *constructor with parameters
     * @param Ia color
     * @param Ka intensity of the color
     */
    public AmbientLight(Color Ia, double Ka) {
        _intensity = Ia.scale(Ka);
    }

    /**
     * getter of _intensity
     * @return _intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
