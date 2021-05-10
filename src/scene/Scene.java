package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import primitives.Point3D;

public class Scene {
    private final String _name;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK,0);
    public Color backGroundColor=Color.BLACK;
    public Geometries geometries=new Geometries();

    public Scene setBackGround(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }
}
