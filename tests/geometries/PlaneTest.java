package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static primitives.Util.isZero;

import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================

        //TC01: 2 points converge
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 0, 1)),
                "Failed constructing a correct plane");

        //TC02: The points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3)),
                "Failed constructing a correct plane");
    }


    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));

        // TC01: Ray intersects the plane
        Ray ray=new Ray(new Point3D(-1, 2, -2),new Vector(0,2,1));
        assertEquals(pl.findIntersections(ray).size(),1,"Ray intersects the plane");

        // TC02: Ray does not intersect the plane
        ray=new Ray(new Point3D(-1, 0, 0),new Vector(-1,-1,1));
        assertNull(pl.findIntersections(ray),"Ray's line out of plane");


        // =============== Boundary Values Tests ==================
        pl = new Plane(new Point3D(1, 0, 1), new Point3D(0, 1, 1), new Point3D(0, 0, 1));

        // TC10: Ray is parallel to the plane- Ray included in the plane
        ray=new Ray(new Point3D(1, 1, 1),new Vector(1,-1,0));
        assertNull(pl.findIntersections(ray),"Ray is parallel and included in the plane");

        // TC11: Ray is parallel to the plane- Ray not included in the plane
        ray=new Ray(new Point3D(1, 1, 2),new Vector(1,-1,0));
        assertNull(pl.findIntersections(ray),"Ray is parallel and not included in the plane");

        // TC12: Ray is orthogonal to the plane- before the plane
        ray=new Ray(new Point3D(1, 5, -1),new Vector(0,0,4));
        assertEquals(pl.findIntersections(ray).size(),1,"Ray is orthogonal to the plane- before the plane");

        // TC13:Ray is orthogonal to the plane- in the plane
        ray=new Ray(new Point3D(1, 5, 1),new Vector(0,0,2));
        assertNull(pl.findIntersections(ray) ,"Ray is orthogonal to the plane- in the plane");

        // TC14: Ray is orthogonal to the plane- after the plane
        ray=new Ray(new Point3D(1, 1, 2),new Vector(0,0,1));
        assertNull(pl.findIntersections(ray),"Ray is orthogonal to the plane- after the plane");

        // TC15: Ray is neither orthogonal nor parallel to and begins at the plane
        ray=new Ray(new Point3D(1, 5, 1),new Vector(0,-5,2));
        assertNull(pl.findIntersections(ray),"Ray intersects and begins at the plane");

        // TC16: Ray is neither orthogonal nor parallel to the plane and begins in the reference point of the plane
        Point3D refPoint= pl.getPoint3D();
        ray=new Ray(refPoint,new Vector(0,-5,2));
        assertNull(pl.findIntersections(ray),"Ray intersects and begins at the reference point of the plane");
    }
}