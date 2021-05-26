package elements;

import geometries.Intersectable;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    /**
     * creating rays and counting the amount of intersection points
     *
     * @param camera camera of the rays
     * @param shape  intersected shape
     * @return amount of intersection points
     */
    int intersectionSum(Camera camera, Intersectable shape) {
        int sum = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, i, j);
                if (shape.findIntersections(ray) != null)
                    sum += shape.findIntersections(ray).size();
            }
        return sum;
    }

    @Test
    void testSphere() {

        //TC01: Sphere radius=1
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);
        Sphere sphere = new Sphere(1, new Point3D(0, 0, -3));
        assertEquals(2, intersectionSum(camera, sphere), "sphere, radius 1, wrong number of intersection points");

        //TC02: Sphere radius=2.5
        camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);
        sphere = new Sphere(2.5, new Point3D(0, 0, -2.5));
        assertEquals(18, intersectionSum(camera, sphere), "sphere, radius 2.5, wrong number of intersection points");

        //TC03: Sphere radius=2
        sphere = new Sphere(2, new Point3D(0, 0, -2));
        assertEquals(10, intersectionSum(camera, sphere), "sphere, radius 2, wrong number of intersection points");

        //TC04: Sphere radius=4
        sphere = new Sphere(4, new Point3D(0, 0, -2));
        assertEquals(9, intersectionSum(camera, sphere), "sphere, radius 4, wrong number of intersection points");

        //TC05: Sphere radius=0.5
        sphere = new Sphere(0.5, new Point3D(0, 0, 1));
        assertEquals(0, intersectionSum(camera, sphere), "sphere, radius 0.5, wrong number of intersection points");
    }

    @Test
    void testPlane() {

        //TC01: plane parallel to the view plane
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);
        Plane plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 0, -1));
        assertEquals(9, intersectionSum(camera, plane), "plane is parallel to the view plane, wrong number of intersection points");

        //TC02: plane creates an acute angle with the view plane
        plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 0.5, -1));
        assertEquals(9, intersectionSum(camera, plane), "plane is parallel to the view plane, wrong number of intersection points");

        //plane creates an obtuse angle with the view plane
        plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 1, -1));
        assertEquals(6, intersectionSum(camera, plane), "plane is parallel to the view plane, wrong number of intersection points");
    }

    @Test
    void testTriangle() {
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);

        //TC01: triangle is small
        Triangle tr = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertEquals(1, intersectionSum(camera, tr), "triangle is parallel to the view plane, wrong number of intersection points");

        //TC02: triangle is tall and narrow
        tr = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertEquals(2, intersectionSum(camera, tr), "triangle is parallel to the view plane, wrong number of intersection points");
    }

}
