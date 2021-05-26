package geometries;

import primitives.*;

/**
 * class for representation of an intersected shape
 */
public abstract class Geometry implements Intersectable {

    /**
     * the shape self color
     */
    protected Color _emission=Color.BLACK;
    /**
     * the shape material
     */
    private Material _material=new Material();

    /**
     * getter of the self color of the shape
     * @return _emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * setter of the self color of the shape
     * @param emission _emission
     * @return the object itself
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * getter of the shape material
     * @return _material
     */
    public Material get_material() {
        return _material;
    }

    /**
     * setter of the shape material
     * @param _material _material
     * @return the object itself
     */
    public Geometry setMaterial(Material _material) {
        this._material = _material;
        return this;
    }

    /**
     * calculates the normal of the geometry
     * @param point the normal point in the geometry
     * @return normal of the geometry
     */
    public abstract Vector getNormal(Point3D point);
}
