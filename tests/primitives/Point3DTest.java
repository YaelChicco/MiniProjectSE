package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertNotEquals(p1.add(new Vector(-1, -2, -3)),Point3D.ZERO,"ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertNotEquals(new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(p1),"ERROR: Point - Point does not work correctly");
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
    }
}