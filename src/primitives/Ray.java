package primitives;

import static geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * class for a ray in the scene (stat point and direction)
 */
public class Ray {
    /**
     * starting point of the ray
     */
    Point3D p0;
    /**
     * direction vector
     */
    Vector dir;
    /**
     * A small unit extra for calculations
     */
    private static final double DELTA = 0.1;

    /**
     * constructor that normalizes the given vector
     *
     * @param p0  point of the ray beginning
     * @param dir direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /**
     * constructor with moving in the normal direction
     *
     * @param point start point of the ray
     * @param v     ray direction
     * @param n     the normal
     */
    public Ray(Point3D point, Vector v, Vector n) {
        Vector _delta = n.scale(n.dotProduct(v) > 0 ? DELTA : -DELTA);
        p0 = point.add(_delta);
        dir = v;
    }

    /**
     * getter of the ray beginning point
     *
     * @return p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter of the direction vector
     *
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * calculates and returns point on the ray (according to a given length)
     *
     * @param t1 distance between the start point and the final point
     * @return point on the ray
     */
    public Point3D getPoint(double t1) {
        return p0.add(dir.scale(t1));
    }

    /**
     * finding the closest point from the list to the ray start
     *
     * @param intersections list of intersections points
     * @return closest point from the list
     */
    public Point3D getClosestPoint(List<Point3D> intersections) {
        Point3D result = null;
        if (intersections == null) {
            return null;
        }

        //finding the shortest distance between the point and the ray start point
        double distance = Double.MAX_VALUE;
        for (Point3D p : intersections) {
            double dist = p0.distance(p);
            if (dist < distance) {
                distance = dist;
                result = p;
            }
        }
        return result;
    }

    /**
     * finds the closest point intersected with the ray
     *
     * @param geoPointsList list of the shapes intersected with the ray
     * @return closest geoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointsList) {
        GeoPoint result = null;
        if (geoPointsList == null) {
            return null;
        }

        //finding the shortest distance between the point and the ray start point
        double distance = Double.MAX_VALUE;
        for (GeoPoint gp : geoPointsList) {
            double dist = p0.distance(gp.point);
            if (dist < distance) {
                distance = dist;
                result = gp;
            }
        }
        return result;
    }
}
