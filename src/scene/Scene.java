package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * class for the image base
 */
public class Scene {

    /**
     * scene name
     */
    private final String _name;
    /**
     * ambient light of the scene
     */
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK,0);
    /**
     * scene background color
     */
    public Color backgroundcolor =Color.BLACK;
    /**
     * shapes in the scene
     */
    public Geometries geometries=new Geometries();

    /**
     * constructor that gets the scene name
     * @param name scene name
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }

    /**
     * setter of backGroundColor (builder)
     * @param backGroundColor scene background color
     * @return scene
     */
    public Scene setBackGround(Color backGroundColor) {
        this.backgroundcolor = backGroundColor;
        return this;
    }

    /**
     * setter of AmbientLight (builder)
     * @param ambientLight ambient light of the scene
     * @return scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
}
