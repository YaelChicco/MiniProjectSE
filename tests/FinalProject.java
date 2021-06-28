import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
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

public class FinalProject {
    private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);
    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(1000, -600, 20), new Vector(-50, 30, -1), new Vector(-50, 30, 3400)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    @Test
    public void myPicture() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray), 0.15)).setBackGround(new Color(0, 0, 51));
        scene.geometries.add(
                //sea
                new Plane(new Point3D(0, 0, -50), new Vector(0, 0, 1))
                        .setEmission(new Color(23, 23, 79))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(35).setkR(0.1)),

                //island
                new Sphere(70, new Point3D(0, 0, -70))
                        .setEmission(new Color(173, 138, 31))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //lightHouse

                //white
                new Polygon(new Point3D(15, -15, -10), new Point3D(10, -10, 80), new Point3D(10, 10, 80), new Point3D(15, 15, -10))
                        .setEmission(new Color(255, 255, 255)).setMaterial(mat),
                new Polygon(new Point3D(-15, -15, -10), new Point3D(-10, -10, 80), new Point3D(10, -10, 80), new Point3D(15, -15, -10))
                        .setEmission(new Color(200, 200, 200)),
                new Polygon(new Point3D(-15, -15, -10), new Point3D(-10, -10, 80), new Point3D(-10, 10, 80), new Point3D(-15, 15, -10))
                        .setEmission(new Color(255, 255, 255)),
                new Polygon(new Point3D(-15, 15, -10), new Point3D(-10, 10, 80), new Point3D(10, 10, 80), new Point3D(15, 15, -10))
                        .setEmission(new Color(200, 200, 200)),

                //front red
                new Polygon(new Point3D(15.05, -15.05, -10), new Point3D(14.05, -14.05, 8), new Point3D(14.05, 14.05, 8), new Point3D(15.05, 15.05, -10))
                        .setEmission(new Color(255, 0, 0)),
                new Polygon(new Point3D(13.05, -13.05, 26), new Point3D(12.05, -12.05, 44), new Point3D(12.05, 12.05, 44), new Point3D(13.05, 13.05, 26))
                        .setEmission(new Color(255, 0, 0)),
                new Polygon(new Point3D(11.05, -11.05, 62), new Point3D(10.05, -10.05, 80), new Point3D(10.05, 10.05, 80), new Point3D(11.05, 11.05, 62))
                        .setEmission(new Color(255, 0, 0)),

                //left red
                new Polygon(new Point3D(-15.05, -15.05, -10), new Point3D(-14.05, -14.05, 8), new Point3D(14.05, -14.05, 8), new Point3D(15.05, -15.05, -10))
                        .setEmission(new Color(200, 0, 0)),
                new Polygon(new Point3D(-13.05, -13.05, 26), new Point3D(-12.05, -12.05, 44), new Point3D(12.05, -12.05, 44), new Point3D(13.05, -13.05, 26))
                        .setEmission(new Color(200, 0, 0)),
                new Polygon(new Point3D(-11.05, -11.05, 62), new Point3D(-10.05, -10.05, 80), new Point3D(10.05, -10.05, 80), new Point3D(11.05, -11.05, 62))
                        .setEmission(new Color(200, 0, 0)),

                //back red
                new Polygon(new Point3D(-15.05, -15.05, -10), new Point3D(-14.05, -14.05, 8), new Point3D(-14.05, 14.05, 8), new Point3D(-15.05, 15.05, -10))
                        .setEmission(new Color(255, 0, 0)),
                new Polygon(new Point3D(-13.05, -13.05, 26), new Point3D(-12.05, -12.05, 44), new Point3D(-12.05, 12.05, 44), new Point3D(-13.05, 13.05, 26))
                        .setEmission(new Color(255, 0, 0)),
                new Polygon(new Point3D(-11.05, -11.05, 62), new Point3D(-10.05, -10.05, 80), new Point3D(-10.05, 10.05, 80), new Point3D(-11.05, 11.05, 62))
                        .setEmission(new Color(255, 0, 0)),

                //right red
                new Polygon(new Point3D(-15.05, 15.05, -10), new Point3D(-14.05, 14.05, 8), new Point3D(14.05, 14.05, 8), new Point3D(15.05, 15.05, -10))
                        .setEmission(new Color(200, 0, 0)),
                new Polygon(new Point3D(-13.05, 13.05, 26), new Point3D(-12.05, 12.05, 44), new Point3D(12.05, 12.05, 44), new Point3D(13.05, 13.05, 26))
                        .setEmission(new Color(200, 0, 0)),
                new Polygon(new Point3D(-11.05, 11.05, 62), new Point3D(-10.05, 10.05, 80), new Point3D(10.05, 10.05, 80), new Point3D(11.05, 11.05, 62))
                        .setEmission(new Color(200, 0, 0)),

                new Triangle(new Point3D())




        );
        scene.lights.add(new DirectionalLight(new Color(80, 80, 80), new Vector(-50, -30, -100)));
        Render render = new Render() //
                .setImageWriter(new ImageWriter("LightHouse", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();


    }
}
