package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * class for the color of the shape that intersects the ray first.
 */
public class BasicRayTracer extends RayTracerBase {

    /**
     * constructor with parameter
     *
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
        return calcColor(closestPoint, ray);
    }

    /**
     * calculates the color in a given point
     *
     * @param closestPoint closest point to that ray start
     * @return color in the point
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return _scene.ambientLight.getIntensity()
                .add(closestPoint.geometry.get_emission())
                .add(calcLocalEffects(closestPoint, ray));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(ray.getP0());
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }

        Material material = intersection.geometry.get_material();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;

        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(2*(l.dotProduct(l))));
        double minusVR=v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks*Math.max(0,Math.pow(minusVR,nShininess)));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
    }
}
