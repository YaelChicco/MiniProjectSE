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
        Triangle tr=new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Ray ray=new Ray(new Point3D(-1,0,0),new Vector(1.5,2,0.5));
        assertEquals(1,(tr.findIntersections(ray)).size(),"number of points is wrong");
    }
}