package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    @Test
    void testGetNormal() {
        Tube tb=new Tube(new Ray(new Point3D(0,0,0),new Vector(0,0,1)),1d);
        assertEquals( new Vector(0, 1, 0), tb.getNormal(new Point3D(0, 1, 1)),"Bad normal to tube");
        assertEquals( new Vector(0, 1, 0), tb.getNormal(new Point3D(0, 1, 0)),"Bad normal to tube");
    }
}