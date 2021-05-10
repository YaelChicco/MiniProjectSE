package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class for the color of the shape that intersects the ray first.
 */
public class BasicRayTracer extends RayTracerBase{

    /**
     * constructor with parameter
     * @param scene shapes scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
       List<Point3D> intersections = _scene.geometries.findIntersections(ray);
        if (intersections == null)
            return _scene.backGroundColor;
        Point3D closestPoint = ray.getClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * calculates the color in a given point
     * @param closestPoint closest point to that ray start
     * @return color in the point
     */
    private Color calcColor(Point3D closestPoint) {
        return _scene.ambientLight.getIntensity();
    }
}
