package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.stream.Collectors;

public interface Intersectable {
    public static class GeoPoint{
        public Geometry geometry;
        public Point3D point;

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
     * finds the intersections of the ray and the shape
     * @param ray intersecting ray
     * @return intersections of the ray and the shape
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList .stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * finds the intersections of the ray and the shape
     * @param ray intersecting ray
     * @return intersections of the ray and the shape
     */
    List<GeoPoint> findGeoIntersections(Ray ray);
}
