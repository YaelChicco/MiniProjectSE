package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(p1.add(new Vector(-1, -2, -3)),Point3D.ZERO,"ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(p1),"ERROR: Point - Point does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(4, 5, 6);
        assertEquals(27 ,p1.distanceSquared(p2),"ERROR: distanceSquared() wrong value");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(4, 5, 6);
        assertEquals( Math.sqrt(27) ,p1.distance(p2),"ERROR: distance() wrong value");
    }
}