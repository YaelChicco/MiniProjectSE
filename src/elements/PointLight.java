package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for point light source
 */
public class PointLight extends Light implements LightSource {

    /**
     * position of the light in the scene
     */
    private final Point3D _position;
    /**
     * constant coefficient
     */
    private double _Kc = 1;
    /**
     * linear coefficient
     */
    private double _Kl = 0;
    /**
     * square coefficient
     */
    private double _Kq = 0;

    /**
     * getter of the constant coefficient
     *
     * @return _Kc
     */
    public double getKc() {
        return _Kc;
    }

    /**
     * getter of the linear coefficient
     *
     * @return _Kl
     */
    public double get_Kl() {
        return _Kl;
    }

    /**
     * getter of the square coefficient
     *
     * @return _Kq
     */
    public double get_Kq() {
        return _Kq;
    }

    /**
     * setter of the constant coefficient
     *
     * @param kc _Kc
     * @return the object itself
     */
    public PointLight setKc(double kc) {
        this._Kc = kc;
        return this;
    }

    /**
     * setter of the linear coefficient
     *
     * @param _Kl _Kl
     * @return the object itself
     */
    public PointLight setKl(double _Kl) {
        this._Kl = _Kl;
        return this;
    }

    /**
     * setter of the square coefficient
     *
     * @param _Kq _Kq
     * @return the object itself
     */
    public PointLight setKq(double _Kq) {
        this._Kq = _Kq;
        return this;
    }

    /**
     * constructor with parameters
     *
     * @param intensity color of the light
     * @param position  position of the light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        double d = point3D.distance(_position);
        double attenuationFactor = _Kc + _Kl * d + _Kq * d * d;
        return getIntensity().reduce(attenuationFactor);
    }

    @Override
    public Vector getL(Point3D point3D) {
        return point3D.subtract(_position).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }
}
