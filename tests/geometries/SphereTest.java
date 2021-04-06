package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        Sphere sp=new Sphere(new Point3D(0,0,0),1d);
       // double sqrt3 = Math.sqrt(1d / 3);
        //assertEquals( new Vector(sqrt3, sqrt3, sqrt3), sp.getNormal(new Point3D(0, 0, 1)),"Bad normal to trinagle");
    }

    @Test
    void testGetCenter() {
    }

    @Test
    void testGetRadius() {
    }
}