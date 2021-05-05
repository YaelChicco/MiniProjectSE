package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase{
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
       List<Point3D> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) return scene.backGroundColor;
        Point3D closestPoint = ray.getClosestPoint(intersections);
        return calcColor(closestPoint);
    }
}
