package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {

    @Test
    void testGetNormal() {
        Triangle tr=new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);

        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(1d / 3, 1d / 3, 1d / 3)),"Bad normal to triangle");
        assertTrue( isZero(tr.getNormal(new Point3D(0, 0, 1)).lengthSquared()-1),"Bad normal to triangle");
    }
}