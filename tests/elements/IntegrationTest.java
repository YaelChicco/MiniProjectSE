package elements;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testSphere(){

        //TC01: Sphere radius=1
        Sphere sphere=new Sphere(new Point3D(0,0,-3),1);
        Camera camera=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setViewPlaneSize(3,3);
        camera.setDistance(1);
        int sum=0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,i,j);
                if(sphere.findIntersections(ray)!=null)
                    sum+=sphere.findIntersections(ray).size();
            }
        assertEquals(2,sum,"sphere, radius 1, wrong number of intersection points");

        //TC02: Sphere radius=2.5
        sphere=new Sphere(new Point3D(0,0,-2.5),2.5);
        camera=new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setViewPlaneSize(3,3);
        camera.setDistance(1);
        sum=0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,i,j);
                if(sphere.findIntersections(ray)!=null)
                    sum+=sphere.findIntersections(ray).size();
            }
        assertEquals(18,sum,"sphere, radius 2.5, wrong number of intersection points");

        //TC03: Sphere radius=2
        sphere=new Sphere(new Point3D(0,0,-2),2);
        sum=0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,i,j);
                if(sphere.findIntersections(ray)!=null)
                    sum+=sphere.findIntersections(ray).size();
            }
        assertEquals(10,sum,"sphere, radius 2, wrong number of intersection points");

        //TC04: Sphere radius=4
        sphere=new Sphere(new Point3D(0,0,-2),4);
        sum=0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,i,j);
                if(sphere.findIntersections(ray)!=null)
                    sum+=sphere.findIntersections(ray).size();
            }
        assertEquals(9,sum,"sphere, radius 4, wrong number of intersection points");

        //TC05: Sphere radius=0.5
        sphere=new Sphere(new Point3D(0,0,1),0.5);
        sum=0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
            {
                Ray ray= camera.constructRayThroughPixel(3,3,i,j);
                if(sphere.findIntersections(ray)!=null)
                    sum+=sphere.findIntersections(ray).size();
            }
        assertEquals(0,sum,"sphere, radius 0.5, wrong number of intersection points");
    }

    @Test
    void testPlane(){

    }

    @Test
    void testTriangle(){

    }

}
