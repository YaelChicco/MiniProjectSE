package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import primitives.Point3D;

public class Scene {
    private final String _name;

    private AmbientLight ambientLight;
    private Color backGroundColor;
    public Geometries geometries;


    public Scene setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
        return this;
    }


    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene(String name) {
        _name = name;
        geometries=new Geometries();
    }

}
