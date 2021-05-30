package elements;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class Exercise7 {

    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(1000, 0, 500), new Vector(-2, 0, -1), new Vector(-1, 0, 2)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    @Test
    public void myPicture() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray), 0.15));

        scene.geometries.add(
                new Polygon(new Point3D(-100, -100, 0), new Point3D(-100, 100, 0), new Point3D(100, 100, 0), new Point3D(100, -100, 0))
                        .setEmission(new Color(java.awt.Color.BLUE)),
                new Polygon(new Point3D(25, -70, 0.25), new Point3D(25, -20, 0.25), new Point3D(50, -20, 0.25), new Point3D(50, -70, 0.25))
                        .setEmission(new Color(java.awt.Color.red))
        );
        scene.lights.add( //
                new DirectionalLight(new Color(350, 200, 200), new Vector(0, 0, -1)));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("exercise7", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


}
