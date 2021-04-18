package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

public class Plane implements Geometry{

    /**
     * the reference point of the plane
     */
    final Point3D q0;
    /**
     * the normal to the plane
     */
    final Vector normal;

    /**
     * constructor that calculates the normal to a triangle and saves one of the points as the reference point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector v1=p2.subtract(p1);
        Vector v2=p3.subtract(p1);
        Vector n=v1.crossProduct(v2);
        normal=n.normalize();
        q0=p1;
    }

    /**
     * primary constructor for Plane
     * @param point3D is the reference point
     * @param normal  is the normal to the plane
     */
    public Plane(Point3D point3D, Vector normal) {
        this.q0 = point3D;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     * getter of the normal of the plane
     * @return normal to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * getter of the plane point
     * @return plane point
     */
    public Point3D getPoint3D() {
        return q0;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p0 = ray.getP0();
        Vector V = ray.getDir();
        Vector U = q0.subtract(p0);

        //tD=denominator of parameter t
        double tD = normal.dotProduct(U);

        //tN=numerator of parameter t
        double tN = normal.dotProduct(V);

        //the ray begins in the same point which appears as
        //reference point in the plane
        if(p0.equals(q0)){
            return null;
        }

        //the ray begins in the plane
        if(isZero(tD)){
            return null;
        }

        //Ray is parallel to the plane
        if(isZero(tN)){
            return null;
        }
        double t= tD/tN;

        //Ray is orthogonal to the plane
        if(V.normalized().equals(normal)){
            return List.of(p0.add(V.scale(t)));
        }

        //if there is an intersection point
        if (t>0){
            Point3D iP=p0.add(V.scale(t));
            return List.of(iP);
        }
        else{
            return null;

        }
    }

    @Override
    public String toString() {
        return "Plane{" +
                "point3D=" + q0 +
                ", normal=" + normal +
                '}';
    }

}
