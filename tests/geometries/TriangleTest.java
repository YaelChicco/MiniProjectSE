package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr=new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(1d / 3, 1d / 3, 1d / 3)),"Bad normal to triangle");
    }

    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        Triangle tr=new Triangle(new Point3D(3,2,1), new Point3D(-2, 2, 1), new Point3D(0, -3, 1));

        // TC01: Inside triangle
        Ray ray=new Ray(new Point3D(4, -2, -1),new Vector(-6,4,4));
        assertEquals(tr.findIntersections(ray).size(),1,"Ray intersection inside triangle");

        // TC02: Outside against edge
        ray=new Ray(new Point3D(-1, 4, -2),new Vector(-2,1,7));
        assertNull(tr.findIntersections(ray),"Ray is outside against edge");

        //TC03: Outside against vertex
        ray=new Ray(new Point3D(6, 4, 1),new Vector(0,-2,2));
        assertNull(tr.findIntersections(ray),"Ray is outside against vertex");


        // =============== Boundary Values Tests ==================

        // TC10: On edge
        ray=new Ray(new Point3D(1, 2, -1),new Vector(2,0,4));
        assertNull(tr.findIntersections(ray),"Ray begins before the plane, intersection on edge" );

        // TC11: In vertex
        ray=new Ray(new Point3D(1, 2, -1),new Vector(-3,0,2));
        assertEquals(tr.findIntersections(ray).size(),1,"Ray begins before the plane, intersection on vertex");

        //TC12: On edge's continuation
        ray=new Ray(new Point3D(1, 2, -1),new Vector(-5,0,2));
        assertNull(tr.findIntersections(ray),"Ray begins before the plane and passes on edge's continuation" );
    }
}