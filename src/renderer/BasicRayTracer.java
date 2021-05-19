package renderer;

import static geometries.Intersectable.GeoPoint;
import primitives.Color;
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
       List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return _scene.backgroundcolor;
       GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * calculates the color in a given point
     * @param closestPoint closest point to that ray start
     * @return color in the point
     */
    private Color calcColor(GeoPoint closestPoint) {
        return _scene.ambientLight.getIntensity()
                .add(closestPoint.geometry.get_emission());
    }
}
