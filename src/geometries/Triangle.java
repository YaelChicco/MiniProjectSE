package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Triangle extends Polygon{

    /**
     * constructor that gets points and build the triangle
     * @param vertices points (has to be 3 points)
     */
    public Triangle(Point3D... vertices) {
        super(vertices);
        if(vertices.length!=3)
            throw new IllegalArgumentException("number of vertices is wrong (has to be 3)");
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     * getter of the normal of the triangle
     * @return normal to the triangle
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
