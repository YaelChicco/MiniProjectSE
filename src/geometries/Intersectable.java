package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * interface for representation of intersected shape
 */
public interface Intersectable {

    /**
     * class for representation a point on a shape
     */
    public static class GeoPoint {

        /**
         * the geometry of which the point is on
         */
        public Geometry geometry;
        /**
         * the point on the geometry
         */
        public Point3D point;

        /**
         * constructor with parameters
         *
         * @param geometry shape
         * @param point    point on the shape
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }
    }

    /**
     * finds the intersections points of the ray with the shape
     *
     * @param ray intersecting ray
     * @return intersections points
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * finds the intersections geoPoints of the ray with the shape
     * @param ray intersecting ray
     * @return intersections geoPoints
     */
    default List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);
}
