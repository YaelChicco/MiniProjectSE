package geometries;

import primitives.*;

public abstract class Geometry implements Intersectable {
    protected Color _emission=Color.BLACK;
    protected Material _material=new Material();

    public Color get_emission() {
        return _emission;
    }

    public Geometry set_emission(Color emission) {
        _emission = emission;
        return this;
    }

    public Material get_material() {
        return _material;
    }

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
