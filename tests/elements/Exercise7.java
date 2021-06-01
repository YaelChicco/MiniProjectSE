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
                //desk
                new Polygon(new Point3D(-100, -100, 0), new Point3D(-100, 100, 0), new Point3D(100, 100, 0), new Point3D(100, -100, 0))
                        .setEmission(new Color(153, 102, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point3D(0,0,0),new Point3D(-50,-50,-100),new Point3D(50,50,-100))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point3D(0,0,0),new Point3D(-50,50,-100),new Point3D(50,-50,-100))
                        .setEmission(new Color(102, 51, 0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //computer
                new Polygon(new Point3D(25, -70, 0.25), new Point3D(25, -20, 0.25), new Point3D(50, -20, 0.25), new Point3D(50, -70, 0.25))
                        .setEmission(new Color(java.awt.Color.lightGray)),
                new Polygon(new Point3D(25,-70,0.25),new Point3D(25,-70,40),new Point3D(25,-20,40),new Point3D(25,-20,0.25))
                        .setEmission(new Color(java.awt.Color.blue)),
                //mouse
                new Sphere(4,new  Point3D(40,-10,0)).setEmission(new Color(java.awt.Color.black)),

                //window
                new Polygon(new Point3D(-120,-80,10),new Point3D(-120,-80,60),new Point3D(-120,-10,60),new Point3D(-120,-10,10))
                        .setEmission(new Color(51, 204, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setkT(0.8)),
                new Polygon(new Point3D(-120,-80,10),new Point3D(-110,-110,7),new Point3D(-110,-110,63),new Point3D(-120,-80,60))
                        .setEmission(new Color(102, 51, 0)),
                new Polygon(new Point3D(-120,-10,10),new Point3D(-110,20,7),new Point3D(-110,20,63),new Point3D(-120,-10,60))
                        .setEmission(new Color(102, 51, 0)),


                //wall
                new Polygon(new Point3D(-120,-120,10),new Point3D(-120,-120,-100),new Point3D(-120,120,-100),new Point3D(-120,120,10))
                .setEmission(new Color(255, 255, 153)),
                 new Polygon(new Point3D(-120,-120,10),new Point3D(-120,-80,10),new Point3D(-120,-80,80),new Point3D(-120,-120,80))
                .setEmission(new Color(255, 255, 153)),
                new Polygon(new Point3D(-120,-10,10),new Point3D(-120,120,10),new Point3D(-120,120,80),new Point3D(-120,-10,80))
                        .setEmission(new Color(255, 255, 153)),
                 new Polygon(new Point3D(-120,-80,60),new Point3D(-120,-80,80),new Point3D(-120,120,80),new Point3D(-120,120,60))
                          .setEmission(new Color(255, 255, 153))

        );
        scene.lights.add( //
                new DirectionalLight(new Color(350, 200, 200), new Vector(-1, 2, -10)));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("exercise7", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


}
