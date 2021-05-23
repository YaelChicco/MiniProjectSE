package primitives;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RayTest {

    @Test
    void getColsestPointTest() {
    Ray ray=new Ray(Point3D.ZERO,new Vector(new Point3D(1,0,0)));

        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the ray
        List<Point3D> points= new ArrayList<>(3);
        points.add(new Point3D(2,0,0));
        points.add(new Point3D(1,0,0));
        points.add(new Point3D(3,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() middle list point");

        // =============== Boundary Values Tests ==================

        // TC11: Empty list
        points=new LinkedList<>();
        assertNull(ray.getClosestPoint(points),"getClosestPoint() empty list");

        // TC12: First point is closest to the beginning of the ray
        points= new ArrayList<>(3);
        points.add(new Point3D(1,0,0));
        points.add(new Point3D(3,0,0));
        points.add(new Point3D(2,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() first point");

        // TC13: Last point is closest to the beginning of the ray
        points= new ArrayList<>(3);
        points.add(new Point3D(3,0,0));
        points.add(new Point3D(6,0,0));
        points.add(new Point3D(1,0,0));
        assertEquals(new Point3D(1,0,0),ray.getClosestPoint(points),"getClosestPoint() last point");
    }

    @Test
    void getGeoColsestPointTest(){

    }
}
