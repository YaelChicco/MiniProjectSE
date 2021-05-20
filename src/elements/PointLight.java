package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private final Point3D _position;
    private double _Kc = 1;
    private double _Kl = 0;
    private double _Kq = 0;

    public double getKc() {
        return _Kc;
    }

    public double get_Kl() {
        return _Kl;
    }

    public double get_Kq() {
        return _Kq;
    }

    public PointLight setKc(double kc) {
        this._Kc = kc;
        return this;
    }

    public PointLight setKl(double _Kl) {
        this._Kl = _Kl;
        return this;
    }

    public PointLight setKq(double _Kq) {
        this._Kq = _Kq;
        return this;
    }

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
}
