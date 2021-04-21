package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private final Point3D _p0;
    private final Vector _vTo;
    private final Vector _vUp;
    private final Vector _vRight;
    private double _width;
    private double _hight;
    private double _distance;

    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vTo is not orthogonal to vUp");
        }
        _p0 = p0;
        //
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        //
        _vRight = _vTo.crossProduct(_vUp);
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHight() {
        return _hight;
    }

    public double getDistance() {
        return _distance;
    }

    //setters using method chaining
    /**
     * @param width
     * @param hight
     * @return "this": camera current instance
     */
    public Camera setViewPlaneSize(double width, double hight) {
        _hight = hight;
        _width = width;
        return this;
    }

    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D Pc=_p0.add(_vTo.scale(_distance));
        double Ry=_hight/nY;
        double Rx=_width/nX;

        Point3D Pij=Pc;
        double Xj=(j-(nX-1)/2d)*Rx;
        double Yi=(i-(nY-1)/2d)*Ry;
        if(!isZero(Xj)){
            Pij=Pij.add(_vRight.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij=Pij.add(_vUp.scale(-Yi));
        }
        return new Ray(_p0,Pij.subtract(_p0));

    }
}
