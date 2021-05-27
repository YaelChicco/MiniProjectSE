package primitives;

import geometries.Intersectable.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RayTest {

    @Test
    void getColsestPointTest() {
    Ray ray=new Ray(Point3D.ZERO,new Vector(new Point3D(1,0,0)));

        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the ray
        List<Point3D> points= new ArrayList<>(3);
        points.add(new Point3D(2,0,0));
        points.add(new Point3D(1,0,0));
        points.add(new Point3D(3,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() middle list point");

        // =============== Boundary Values Tests ==================

        // TC11: Empty list
        points=new LinkedList<>();
        assertNull(ray.getClosestPoint(points),"getClosestPoint() empty list");

        // TC12: First point is closest to the beginning of the ray
        points= new ArrayList<>(3);
        points.add(new Point3D(1,0,0));
        points.add(new Point3D(3,0,0));
        points.add(new Point3D(2,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() first point");

        // TC13: Last point is closest to the beginning of the ray
        points= new ArrayList<>(3);
        points.add(new Point3D(3,0,0));
        points.add(new Point3D(6,0,0));
        points.add(new Point3D(1,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() last point");
    }

    @Test
    void findColsestGeoPointTest(){
        Ray ray=new Ray(new Point3D(6,0,0), new Vector(-1,0,0));
        Plane plane=new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        Triangle triangle=new Triangle(new Point3D(3,-4,-1), new Point3D(3,0,4), new Point3D(3,5,-1));
        Sphere sphere=new Sphere(2.0, new Point3D(-4,0,0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the ray
        List<GeoPoint> geoPoints= new ArrayList<>(4);
        geoPoints.add(new GeoPoint(plane,new Point3D(1,0,0)));
        geoPoints.add(new GeoPoint(triangle, new Point3D(3,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-2,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-6,0,0)));

        GeoPoint intersectionPoint=ray.findClosestGeoPoint(geoPoints);
        assertEquals(new Point3D(3,0,0),intersectionPoint.point,"findClosestGeoPoint() middle list point-point");
        assertEquals(triangle,intersectionPoint.geometry,"findClosestGeoPoint() middle list point-geometry");

        // =============== Boundary Values Tests ==================

        // TC11: Empty list
        geoPoints=new LinkedList<>();
        assertNull(ray.findClosestGeoPoint(geoPoints),"findClosestGeoPoint() empty list");

        // TC12: First point is closest to the beginning of the ray
        geoPoints= new ArrayList<>(4);
        geoPoints.add(new GeoPoint(triangle,new Point3D(3,0,0)));
        geoPoints.add(new GeoPoint(plane, new Point3D(1,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-2,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-6,0,0)));

        intersectionPoint=ray.findClosestGeoPoint(geoPoints);
        assertEquals(new Point3D(3,0,0),intersectionPoint.point,"findClosestGeoPoint() first point-point");
        assertEquals(triangle,intersectionPoint.geometry,"findClosestGeoPoint() first point-geometry");

        // TC13: Last point is closest to the beginning of the ray
        geoPoints= new ArrayList<>(4);
        geoPoints.add(new GeoPoint(triangle,new Point3D(3,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-6,0,0)));
        geoPoints.add(new GeoPoint(sphere, new Point3D(-2,0,0)));
        geoPoints.add(new GeoPoint(plane, new Point3D(1,0,0)));
        assertEquals(new Point3D(3,0,0),intersectionPoint.point,"findClosestGeoPoint() last point-point");
        assertEquals(triangle,intersectionPoint.geometry,"findClosestGeoPoint() last point-geometry");
    }
}
