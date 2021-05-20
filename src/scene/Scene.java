package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

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

    public List<LightSource> lights=new LinkedList<LightSource>();

    /**
     * constructor that gets the scene name
     * @param name scene name
     */
    public Scene(String name) {
        _name = name;
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

    public Scene setLights(List<LightSource> lightSource) {
        this.lights = lightSource;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
