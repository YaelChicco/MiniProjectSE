package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {

    /**
     * List of polygon's vertices
     */
    protected List<Point3D> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal(null);

//       Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal(null);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        List<GeoPoint> planeGeoIntersections = plane.findGeoIntersections(ray, maxDistance);
        Point3D p0 = ray.getP0();
        Vector V;
        Vector v1, v2, v3, v4, n1, n2, n3;

        //only if the ray intersects the plane that the polygon is included in
        // check if the intersection point is in the polygon
        if (planeGeoIntersections == null) {
            return null;
        } else {
            V = (planeGeoIntersections.get(0).point).subtract(p0);
            //Vi is the edges of the pyramid that the polygon is the bases of and the ray's head is the vertex of
            //Ni is the normals to each side of the pyramid
            //checks if each Ni*Vi have the same sign
            v1 = (vertices.get(0)).subtract(p0);
            v2 = (vertices.get(1)).subtract(p0);
            n1 = (v1.crossProduct(v2)).normalize();
            for (int i = 0; i < vertices.size() - 2; i++) {
                v3 = (vertices.get(i + 2)).subtract(p0);
                n2 = (v2.crossProduct(v3)).normalize();

                if (i == vertices.size() - 3) {
                    v4 = (vertices.get(vertices.size() - 1)).subtract(p0);
                    n3 = (v4.crossProduct((vertices.get(0)).subtract(p0))).normalize();
                    if (!checkSign(V.dotProduct(n2), V.dotProduct(n3))) {
                        return null;
                    }
                }
                if (!checkSign(V.dotProduct(n1), V.dotProduct(n2))) {
                    return null;
                }
                if (isZero(V.dotProduct(n1)) || isZero(V.dotProduct(n2))) {
                    return null;
                }
                v1 = v2;
                v2 = v3;
                n1 = n2;
            }
            return List.of(new GeoPoint(this, planeGeoIntersections.get(0).point));
        }
    }
}
