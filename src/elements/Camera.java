package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
/**
 * Class for the construction of the camera and the view plane
 */
public class Camera {

    /**
     * camera location
     */
    private final Point3D _p0;

    /**
     * front vector of the camera
     */
    private final Vector _vTo;

    /**
     * upward vector of the camera
     */
    private final Vector _vUp;

    /**
     * right vector of the camera
    */
    private final Vector _vRight;

    /**
     * view plane width
     */
    private double _width;

    /**
     * view plane hight
     */
    private double _hight;

    /**
     * distance between the view plane and the Camera
     */
    private double _distance;

    /**
     * constructor that gets the camera location (point and vectors)
     *
     * @param p0 camera location
     * @param vTo front vector of the camera
     * @param vUp upward vector of the camera
     */
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

    /**
     * getter of p0
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter of vTo
     * @return vTo
     */
    public Vector getvTo() {
        return _vTo;
    }

    /**
     * getter of vUp
     * @return vUp
     */
    public Vector getvUp() {
        return _vUp;
    }

    /**
     * getter of vRight
     * @return vRight
     */
    public Vector getvRight() {
        return _vRight;
    }

    /**
     * getter of width
     * @return width
     */
    public double getWidth() {
        return _width;
    }

    /**
     * getter of hight
     * @return hight
     */
    public double getHight() {
        return _hight;
    }

    /**
     * getter of distance
     * @return distance
     */
    public double getDistance() {
        return _distance;
    }

    //setters using method chaining

    /**
     * sets the size of the view plane
     *
     * @param width view plane width
     * @param hight view plane hight
     * @return "this": camera current instance
     */
    public Camera setViewPlaneSize(double width, double hight) {
        _hight = hight;
        _width = width;
        return this;
    }

    /**
     * setter of distance
     * @param distance distance
     * @return distance between the view plane and athe camera
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        double Ry = _hight / nY;
        double Rx = _width / nX;

        Point3D Pij = Pc;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = (i - (nY - 1) / 2d) * Ry;
        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(-Yi));
        }
        return new Ray(_p0, Pij.subtract(_p0));
    }
}
