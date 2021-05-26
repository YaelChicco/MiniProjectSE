package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import java.util.List;

/**
 * class for the color of the shape that intersects the ray first.
 */
public class BasicRayTracer extends RayTracerBase {

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            return Color.BLACK;
        return calcColor(closestPoint, ray);
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) {
        Color color = Color.BLACK; Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.get_material();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(geoPoint.point, v, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint.point, v, n), level, material.kT, kkt));
        return color;

    }

    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        Vector r=v.subtract(n.scale(2*v.dotProduct(n)));
        return new Ray(point, r, n);
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? _scene.backgroundcolor : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

//    /**
//     * calculates the color in a given point
//     *
//     * @param closestPoint closest point to that ray start
//     * @return color in the point
//     */
//    private Color calcColor(GeoPoint closestPoint, Ray ray) {
//        return _scene.ambientLight.getIntensity()
//                .add(closestPoint.geometry.getEmission())
//                .add(calcLocalEffects(closestPoint, ray));
//    }

    /**
     * calculates the local effects
     *
     * @param intersection intersection point for calculating the local effects
     * @param ray          intersected ray
     * @return local color of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
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
                if (unShaded(lightSource, l, n, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    private boolean unShaded(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Point3D point=geoPoint.point;
        Ray lightRay=new Ray(point, l.scale(-1), n);
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point));
        return intersections == null;

    }

    /**
     * calculates the specular effects
     *
     * @param ks             specular coefficient
     * @param l              vector from the light source
     * @param n              normal in the point
     * @param v              vector from the camera
     * @param nShininess     shininess coefficient
     * @param lightIntensity intensity of the light source
     * @return specular color of the point
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        double minusVR = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));
    }

    /**
     * calculates the diffuse effects
     *
     * @param kd             diffuse coefficient
     * @param l              vector from the light source
     * @param n              normal in the point
     * @param lightIntensity intensity of the light source
     * @return diffuse color of the point
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }
}
