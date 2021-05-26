package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * class for representation of collection of intersected shapes
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables;

    /**
     * constructor that gets all the geometries
     *
     * @param list non limited array of Geometry implementing Intersectable
     */
    public Geometries(Intersectable... list) {
        _intersectables = new LinkedList<Intersectable>(Arrays.asList(list.clone()));
    }

    /**
     * adds geometries to the intersected geometries list
     * @param list intersected geometries list
     */
    public void add(Intersectable... list) {
        Collections.addAll(_intersectables, list);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (_intersectables == null) {
            return null;
        }
        List<GeoPoint> result = null;

        for (Intersectable geo : _intersectables) {
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray, maxDistance);
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }
}
