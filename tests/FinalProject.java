import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
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
    private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(30);
    private static final Material transMat = new Material().setKd(0.2).setKs(0.2).setShininess(35).setkT(0.4);

    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(1000, -600, 20), new Vector(-50, 30, -1), new Vector(-50, 30, 3400)) //
            .setViewPlaneSize(200, 200).setDistance(1000).setAperture(16,4).setFocalDistance(1166);

    @Test
    public void myPicture() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray), 0.15)).setBackGround(new Color(0, 0, 30));
        scene.geometries.add(

                //sea
                new Plane(new Point3D(0, 0, -50), new Vector(0, 0, 1))
                        .setEmission(new Color(0, 0, 26))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setkR(0.1)),

                //island
                new Sphere(70, new Point3D(0, 0, -70))
                        .setEmission(new Color(173, 138, 31))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //lightHouse

                //white
                new Polygon(new Point3D(15, -15, -10), new Point3D(10, -10, 80), new Point3D(10, 10, 80), new Point3D(15, 15, -10))
                        .setEmission(new Color(200, 200, 200)).setMaterial(mat),
                new Polygon(new Point3D(-15, -15, -10), new Point3D(-10, -10, 80), new Point3D(10, -10, 80), new Point3D(15, -15, -10))
                        .setEmission(new Color(200, 200, 200)).setMaterial(mat),
                new Polygon(new Point3D(-15, -15, -10), new Point3D(-10, -10, 80), new Point3D(-10, 10, 80), new Point3D(-15, 15, -10))
                        .setEmission(new Color(200, 200, 200)).setMaterial(mat),
                new Polygon(new Point3D(-15, 15, -10), new Point3D(-10, 10, 80), new Point3D(10, 10, 80), new Point3D(15, 15, -10))
                        .setEmission(new Color(200, 200, 200)).setMaterial(mat),

                //front red
                new Polygon(new Point3D(15.05, -15.05, -10), new Point3D(14.05, -14.05, 8), new Point3D(14.05, 14.05, 8), new Point3D(15.05, 15.05, -10))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(13.05, -13.05, 26), new Point3D(12.05, -12.05, 44), new Point3D(12.05, 12.05, 44), new Point3D(13.05, 13.05, 26))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(11.05, -11.05, 62), new Point3D(10.05, -10.05, 80), new Point3D(10.05, 10.05, 80), new Point3D(11.05, 11.05, 62))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),

                //left red
                new Polygon(new Point3D(-15.05, -15.05, -10), new Point3D(-14.05, -14.05, 8), new Point3D(14.05, -14.05, 8), new Point3D(15.05, -15.05, -10))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-13.05, -13.05, 26), new Point3D(-12.05, -12.05, 44), new Point3D(12.05, -12.05, 44), new Point3D(13.05, -13.05, 26))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-11.05, -11.05, 62), new Point3D(-10.05, -10.05, 80), new Point3D(10.05, -10.05, 80), new Point3D(11.05, -11.05, 62))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),

                //back red
                new Polygon(new Point3D(-15.05, -15.05, -10), new Point3D(-14.05, -14.05, 8), new Point3D(-14.05, 14.05, 8), new Point3D(-15.05, 15.05, -10))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-13.05, -13.05, 26), new Point3D(-12.05, -12.05, 44), new Point3D(-12.05, 12.05, 44), new Point3D(-13.05, 13.05, 26))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-11.05, -11.05, 62), new Point3D(-10.05, -10.05, 80), new Point3D(-10.05, 10.05, 80), new Point3D(-11.05, 11.05, 62))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),

                //right red
                new Polygon(new Point3D(-15.05, 15.05, -10), new Point3D(-14.05, 14.05, 8), new Point3D(14.05, 14.05, 8), new Point3D(15.05, 15.05, -10))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-13.05, 13.05, 26), new Point3D(-12.05, 12.05, 44), new Point3D(12.05, 12.05, 44), new Point3D(13.05, 13.05, 26))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),
                new Polygon(new Point3D(-11.05, 11.05, 62), new Point3D(-10.05, 10.05, 80), new Point3D(10.05, 10.05, 80), new Point3D(11.05, 11.05, 62))
                        .setEmission(new Color(200, 0, 0)).setMaterial(mat),

                //door
                new Polygon(new Point3D(15.06, -4, -10), new Point3D(14.06, -3, 8), new Point3D(14.06, 3, 8), new Point3D(15.06, 4, -10))
                        .setEmission(new Color(77,38,0)),

                //blue thing
                new Polygon(new Point3D(12,-12,80),new Point3D(-12,-12,80),new Point3D(-12,12,80),new Point3D(12,12,80))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Polygon(new Point3D(12,-12,85),new Point3D(-12,-12,85),new Point3D(-12,12,85),new Point3D(12,12,85))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Polygon(new Point3D(12,-12,80),new Point3D(12,-12,85),new Point3D(12,12,85),new Point3D(12,12,80))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Polygon(new Point3D(-12,-12,80),new Point3D(-12,-12,85),new Point3D(12,-12,85),new Point3D(12,-12,80))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Polygon(new Point3D(-12,-12,80),new Point3D(-12,-12,85),new Point3D(-12,12,85),new Point3D(-12,12,80))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Polygon(new Point3D(-12,12,80),new Point3D(-12,12,85),new Point3D(-12,-12,85),new Point3D(-12,-12,80))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),

                //windows
                 new Polygon(new Point3D(10,-10,85),new Point3D(10,-10,95),new Point3D(10,10,95),new Point3D(10,10,85))
                        .setEmission(new Color(255,255,0)).setMaterial(transMat),
                new Polygon(new Point3D(-10,-10,85),new Point3D(-10,-10,95),new Point3D(10,-10,95),new Point3D(10,-10,85))
                        .setEmission(new Color(255,255,0)).setMaterial(transMat),
                new Polygon(new Point3D(-10,-10,85),new Point3D(-10,-10,95),new Point3D(-10,10,95),new Point3D(-10,10,85))
                        .setEmission(new Color(255,255,0)).setMaterial(transMat),
                new Polygon(new Point3D(-10,10,85),new Point3D(-10,10,95),new Point3D(-10,-10,95),new Point3D(-10,-10,85))
                        .setEmission(new Color(255,255,0)).setMaterial(transMat),

                //roof
                new Polygon(new Point3D(12,-12,95),new Point3D(-12,-12,95),new Point3D(-12,12,95),new Point3D(12,12,95))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Triangle(new Point3D(12, -12, 95), new Point3D(12, 12, 95), new Point3D(0, 0, 105))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Triangle(new Point3D(-12, -12, 95), new Point3D(12, -12, 95), new Point3D(0, 0, 105))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Triangle(new Point3D(-12, -12, 95), new Point3D(-12, 12, 95), new Point3D(0, 0, 105))
                        .setEmission(new Color(102,102,153)).setMaterial(mat),
                new Triangle(new Point3D(-12, 12, 95), new Point3D(12, 12, 95), new Point3D(0, 0, 105))
                        .setEmission(new Color(2102,102,153)).setMaterial(mat),

                //moon
                new Sphere(18, new Point3D(-150,-30,100)).setEmission(new Color(110,110,110))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(35).setkT(0.4)),

                //stars
                new Sphere(1, new Point3D(-150,50,80)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(2.5, new Point3D(-150,135,95)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(1.5, new Point3D(-150,180,125)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(1, new Point3D(-150,220,60)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(1.8, new Point3D(-150,150,40)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(1.3, new Point3D(-150,240,102)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(2, new Point3D(-150,23,100)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(2, new Point3D(-150,-3,50)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(2, new Point3D(-150,-50,70)).setEmission(new Color(110,110,110)).setMaterial(mat),
                new Sphere(1, new Point3D(-150,-35,30)).setEmission(new Color(110,110,110)).setMaterial(mat)
        );

        scene.lights.add(new DirectionalLight(new Color(80, 80, 80), new Vector(150, 60, -100)));
        scene.lights.add(new PointLight(new Color(200, 200, 200),new Point3D(9.9,0,90)));
        scene.lights.add(new PointLight(new Color(200, 200, 200),new Point3D(-9.9,0,90)));
        scene.lights.add(new PointLight(new Color(200, 200, 200),new Point3D(0,9.9,90)));
        scene.lights.add(new PointLight(new Color(200, 200, 200),new Point3D(0,-9.9,90)));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("LightHouse focus", 600, 600)) //
                .setCamera(camera) //
                .setMultithreading(3)
                .setMultyRay(true)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}
