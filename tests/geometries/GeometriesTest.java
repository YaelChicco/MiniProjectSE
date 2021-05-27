package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Intersectable.GeoPoint;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Polygon quadr=new Polygon(new Point3D(1,0,0),new Point3D(-2,0,0),new Point3D(-2,0,2),new Point3D(1,0,2));
        Ray ray=new Ray(new Point3D(4, -2, -1),new Vector(-6,4,4));
        Triangle tr=new Triangle(new Point3D(3,2,1), new Point3D(-2, 2, 1), new Point3D(0, -3, 1));

        //the collection is empty
        Geometries geometries=new Geometries();
        assertNull(geometries.findIntersections(ray),"Wrong number of points");

        //only one geometry has intersection point with the ray
        geometries=new Geometries(quadr,tr);
        assertEquals((geometries.findIntersections(ray)).size(),1,"Wrong number of points");

        //no intersection points with the ray
        ray=new Ray(new Point3D(-2.66,-1.23,0),new Vector(5.06,-2.01,0));
        assertNull(geometries.findIntersections(ray),"Wrong number of points");

        //all geometries have intersection points with the ray
        ray=new Ray(new Point3D(0,1,0),new Vector(0,-2,1));
        assertEquals((geometries.findIntersections(ray)).size(),2,"Wrong number of points");

        //some of the geometries have intersection points, but not all of them
        Triangle tr1=new Triangle(new Point3D(-3.07,-3.56,0), new Point3D(-0.53, -5, 0), new Point3D(-2.83, -1.67, 0));
        geometries.add(tr1);
        assertEquals((geometries.findIntersections(ray)).size(),2,"Wrong number of points");
    }

    @Test
    void findGeoIntersectionTest(){
        Ray ray=new Ray(new Point3D(6,0,0), new Vector(-1,0,0));
        Plane plane=new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        Triangle triangle=new Triangle(new Point3D(3,-4,-1), new Point3D(3,0,4), new Point3D(3,5,-1));
        Sphere sphere=new Sphere(2.0, new Point3D(-4,0,0));
        Geometries geometries=new Geometries(triangle, plane, sphere);


        // ============ Equivalence Partitions Tests ==============
        //TC01: Some of the geometries are in the range
        List<GeoPoint> intersections=geometries.findGeoIntersections(ray, 6);
        assertEquals(2,intersections.size(),"findGeoIntersection() some of the geometries are in the range");
        assertEquals(new GeoPoint(triangle, new Point3D(3,0,0)),intersections.get(0),"findGeoIntersection() some of the geometries are in the range");
        assertEquals(new GeoPoint(plane,new Point3D(1,0,0)), intersections.get(1),"findGeoIntersection() some of the geometries are in the range");

        // =============== Boundary Values Tests ==================

        // TC11: None of the geometries are in the range
        assertNull(geometries.findGeoIntersections(ray, 2) ,"findGeoIntersection() none of the geometries are in the range");

        // TC12: All of the geometries are in the range
        intersections=geometries.findGeoIntersections(ray, 13);
        assertEquals(4,intersections.size(),"findGeoIntersection() all of the geometries are in the range");
        assertEquals(new GeoPoint(triangle, new Point3D(3,0,0)),intersections.get(0),"findGeoIntersection() all of the geometries are in the range");
        assertEquals(new GeoPoint(plane,new Point3D(1,0,0)), intersections.get(1),"findGeoIntersection() all of the geometries are in the range");
        assertEquals(new GeoPoint(sphere,new Point3D(-2,0,0)), intersections.get(2),"findGeoIntersection() all of the geometries are in the range");
        assertEquals(new GeoPoint(sphere,new Point3D(-6,0,0)), intersections.get(3),"findGeoIntersection() all of the geometries are in the range");
    }
}