package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp=new Sphere(new Point3D(0,0,0),1d);
        assertEquals( new Vector(0, 0, 1), sp.getNormal(new Point3D(0, 0, 1)),"Bad normal to sphere");
    }
}