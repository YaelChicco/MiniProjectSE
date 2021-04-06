package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube implements Geometry{
    /**
     * tube direction
     */
    protected Ray _axisRay;
    /**
     * tube radius
     */
    protected double radius;

    /**
     * constructor of the tube
     * @param axisRay tube direction
     * @param radius tube radius
     */
    public Tube(Ray axisRay, double radius) {
        this._axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter of the tube direction
     * @return tube direction
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter of the tube radius
     * @return tube radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * getNormal
     * @param point the normal point in the geometry
     * @return normal of the tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0=_axisRay.getP0();
        Vector v=_axisRay.getDir();

        Vector p0_p= point.subtract(p0);
        double t=alignZero(p0_p.dotProduct(v));

        if (isZero(t)){
            return p0_p.normalize();
        }
        Point3D O=p0.add(v.scale(t));
        Vector O_P=point.subtract(O);

        return O_P.normalize();
    }
}
