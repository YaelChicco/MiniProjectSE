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

    /**
     * initial value of k
     */
    private static final double INITIAL_K = 1.0;
    /**
     * recursion depth
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * minimum value of k in the recursion depth
     */
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

    /**
     * calculating the color where the ray intersecting
     *
     * @param geoPoint first intersection point
     * @param ray      intersected ray
     * @return color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * calculating the color where the ray intersecting- recursion
     *
     * @param intersection intersection point
     * @param ray          intersected ray
     * @param level        current recursion level
     * @param k            current attention coefficient of the recursion
     * @return color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    /**
     * calculates the global effects
     *
     * @param geoPoint intersection point
     * @param v        vector from the camera to the point
     * @param level    current recursion level
     * @param k        current attention coefficient of the recursion
     * @return global color of the point
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
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

    /**
     * calculates the global effects- recursion
     *
     * @param ray   ray from the intersection point (reflection/ transparency ray)
     * @param level current recursion level
     * @param kx    original attention coefficient of the recursion
     * @param kkx   current attention coefficient of the recursion
     * @return global color of the point
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? _scene.backgroundcolor : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    /**
     * build a refracted ray
     *
     * @param point target point
     * @param v     vector from the camera
     * @param n     normal in the point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * build a reflected ray
     *
     * @param point target point
     * @param v     vector from the camera
     * @param n     normal in the point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(point, r, n);
    }

    /**
     * finds the closest intersection point to the ray head
     *
     * @param ray the intersected ray
     * @return closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * calculates the local effects
     *
     * @param intersection intersection point for calculating the local effects
     * @param ray          intersected ray
     * @return local color of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
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
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * checks if there is shadow in the point
     *
     * @param light    light source
     * @param l        direction vector from the light source to the point
     * @param n        normal in the point
     * @param geoPoint the point
     * @return shadow in the point
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geoPoint.point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.get_material().kT;
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
        }
        return ktr;
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
