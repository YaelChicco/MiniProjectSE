package elements;

//import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import elements.Camera;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
public class CameraTest {

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    @Test
    public void testConstructRayThroughPixel() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, -2, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0),
                "Bad ray");

        // TC02: 4X4 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-3, -3, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0),
                "Bad ray");

        // TC03: 4X4 Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -3, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0),
                "Bad ray");

        // TC04: 4X4 Inside (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -1, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1),
                "Bad ray");

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, 0, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1),
                "Bad ray");

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, -2, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0),
                "Bad ray");

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, 0, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1),
                "Bad ray");

    }

    @Test
    public void testAperturePointsInit() {
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))
        .setAperture(3,3);
        List<Point3D> pointsExpected=List.of(
                new Point3D(1,1,0),
                new Point3D(1,0,0),
                new Point3D(1,-1,0),
                new Point3D(0,1,0),
                new Point3D(0,-1,0),
                new Point3D(-1,1,0),
                new Point3D(-1,0,0),
                new Point3D(-1,-1,0)
        );

        List<Point3D> pointsActual=camera.aperturePointsInit();
        int size = pointsActual.size();
        assertEquals(pointsExpected.size(), size, "aperturePointsInit()- wrong number of points");
        for(int i=0; i<size;i++)
            assertEquals(pointsExpected.get(i), pointsActual.get(i), "aperturePointsInit()- wrong point");
    }
}
