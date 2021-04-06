package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import static primitives.Util.isZero;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 0, 1));
            fail("Failed constructing a correct plane");
        } catch (IllegalArgumentException e) { }

        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3));
            fail("Failed constructing a correct plane");
        } catch (IllegalArgumentException e) { }
    }


    @Test
    void testGetNormal() {
        Plane pl=new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        //assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to plane");
        assertTrue( isZero(pl.getNormal(new Point3D(0, 0, 1)).lengthSquared()-1),"Bad normal to plane");
    }
}