package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 0, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }


    @Test
    void testGetNormal() {
        Plane pl=new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to plane");

    }


    @Test
    void testGetPoint3D() {
    }


}