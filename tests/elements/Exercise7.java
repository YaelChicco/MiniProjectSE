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

    /**
     * picture for exercise 7
     */
    @Test
    public void myPicture() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray), 0.15));

        scene.geometries.add(
                //desk
                new Polygon(new Point3D(-100, -100, 0), new Point3D(-100, 100, 0), new Point3D(100, 100, 0), new Point3D(100, -100, 0))
                        .setEmission(new Color(153, 102, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point3D(100, -100, -10), new Point3D(100, -100, 0), new Point3D(100, 100, 0), new Point3D(100, 100, -10))
                        .setEmission(new Color(153, 76, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point3D(0, 0, 0), new Point3D(-50, -50, -100), new Point3D(50, 50, -100))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point3D(0, 0, 0), new Point3D(-50, 50, -100), new Point3D(50, -50, -100))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //computer
                new Polygon(new Point3D(25, -70, 0.25), new Point3D(25, -20, 0.25), new Point3D(64.75, -20, 0.25), new Point3D(64.75, -70, 0.25))
                        .setEmission(new Color(java.awt.Color.lightGray)),
                new Polygon(new Point3D(25, -70, 0.25), new Point3D(25, -70, 40), new Point3D(25, -20, 40), new Point3D(25, -20, 0.25))
                        .setEmission(new Color(128,128,128)),
                new Polygon(new Point3D(25.01, -67, 3.25), new Point3D(25.01, -67, 37), new Point3D(25.01, -23, 37), new Point3D(25.01, -23, 3.25))
                        .setEmission(new Color(java.awt.Color.blue)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(35).setkR(0.4)),

                //mouse
                new Sphere(4, new Point3D(40, -10, 0)).setEmission(new Color(java.awt.Color.black)),

                //window
                new Polygon(new Point3D(-120, -80, 10), new Point3D(-120, -80, 60), new Point3D(-120, -10, 60), new Point3D(-120, -10, 10))
                        .setEmission(new Color(153, 255, 255)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(20).setkT(0.7)),
                new Polygon(new Point3D(-120, -80, 10), new Point3D(-110, -110, 7), new Point3D(-110, -110, 63), new Point3D(-120, -80, 60))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point3D(-120, -10, 10), new Point3D(-110, 20, 7), new Point3D(-110, 20, 63), new Point3D(-120, -10, 60))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //window wall
                new Polygon(new Point3D(-120, -120, 10), new Point3D(-120, -120, -100), new Point3D(-120, 120, -100), new Point3D(-120, 120, 10))
                        .setEmission(new Color(255, 255, 153)),
                new Polygon(new Point3D(-120, -120, 10), new Point3D(-120, -80, 10), new Point3D(-120, -80, 80), new Point3D(-120, -120, 80))
                        .setEmission(new Color(255, 255, 153)),
                new Polygon(new Point3D(-120, -10, 10), new Point3D(-120, 120, 10), new Point3D(-120, 120, 80), new Point3D(-120, -10, 80))
                        .setEmission(new Color(255, 255, 153)),
                new Polygon(new Point3D(-120, -80, 60), new Point3D(-120, -80, 80), new Point3D(-120, 120, 80), new Point3D(-120, 120, 60))
                        .setEmission(new Color(255, 255, 153)),

                //sides wall
                new Polygon(new Point3D(120, -120, -100), new Point3D(120, -120, 80), new Point3D(-120, -120, 80), new Point3D(-120, -120, -100))
                        .setEmission(new Color(255, 204, 153)),
                new Polygon(new Point3D(120, 120, -100), new Point3D(120, 120, 80), new Point3D(-120, 120, 80), new Point3D(-120, 120, -100))
                        .setEmission(new Color(255, 204, 153)),

                //floor
                new Polygon(new Point3D(120, -120, -100), new Point3D(-120, -120, -100), new Point3D(-120, 120, -100), new Point3D(120, 120, -100))
                        .setEmission(new Color(255, 153, 204)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkR(0.2)),

                //lamp
                new Sphere(15,new Point3D(-120,70,40))
                        .setEmission(new Color(218,165,32)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(35).setkT(0.4))

        );
        scene.lights.add(new DirectionalLight(new Color(350, 200, 200), new Vector(3, 1, -1)));

        scene.lights.add(new PointLight(new Color(500,250,250), new Point3D(-119,70,40)).setKl(4E-5).setKq(2E-7));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("exercise7", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


}
