package elements;

import primitives.Color;

/**
 * ambient light of the scene
 */
public class AmbientLight extends Light {

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * constructor with parameters
     *
     * @param Ia color
     * @param Ka intensity of the color
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

}
