package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static primitives.Util.isZero;

import primitives.Vector;

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
}