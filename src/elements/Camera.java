package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
     * size of the aperture
     */
    private double _apertureSize;

    /**
     * all the start points of the rays from the aperture
     */
    private List<Point3D> aperturePoints;

    /**
     * amount of pixels in a row/column in the aperture
     */
    private int _apertureN;

    /**
     * distance between the view plane and the Camera
     */
    private double _distance;

    /**
     * distance between the focal plane and the Camera
     */
    private double _focalDist;

    /**
     * constructor that gets the camera location (point and vectors)
     *
     * @param p0  camera location
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
     *
     * @param distance distance
     * @return distance between the view plane and athe camera
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * setter of the distance between the aperture and the focal plane
     *
     * @param distance _focalDist
     * @return the object itself
     */
    public Camera setFocalDistance(double distance) {
        _focalDist = distance;
        return this;
    }

    public Camera setAperture(double apertureSize, int apertureN) {
        _apertureSize = apertureSize;
        _apertureN = (int) Math.pow(2,apertureN);
        aperturePointsInit();
        return this;
    }

    public Point3D getP0() {
        return _p0;
    }

    /**
     * creates a ray from the camera through a specific pixel center
     *
     * @param nX - number of pixels in view plane width
     * @param nY - number of pixels in view plane height
     * @param j  - distance of the intercept from the midpoint on the y-axis
     * @param i  - distance of the intercept from the midpoint on the X-axis
     * @return ray from the camera through a specific pixel center
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

    /**
     * creates rays from the aperture for a specific pixel center
     *
     * @param nX number of pixels in view plane width
     * @param nY number of pixels in view plane height
     * @param j  distance of the intercept from the midpoint on the y-axis
     * @param i  distance of the intercept from the midpoint on the X-axis
     * @return rays from the aperture for a specific pixel center
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
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
        //ray from the camera to the pixel in the viewPlane
        Ray mainRay = new Ray(_p0, Pij.subtract(_p0));

        //calculates the focus point on ray continuation
        double distCameraVP = _p0.distance(Pij);
        double distCameraFP = distCameraVP * _focalDist / _distance;
        Point3D focalPoint = mainRay.getPoint(distCameraFP);

        //calculates the rays from the aperture
        List<Ray> apertureRays = new LinkedList<>();
        for (Point3D p : aperturePoints)
            apertureRays.add(new Ray(p, focalPoint.subtract(p)));
        return apertureRays;
    }

    /**
     * finds all the points on the aperture grid
     *
     * @return all the points on the aperture grid
     */
    public List<Point3D> aperturePointsInit() {
        //vectors in size of one unit of the aperture grid
        Vector newVUp = _vUp.scale(_apertureSize / _apertureN);
        Vector newVRight = _vRight.scale(_apertureSize / _apertureN);

        //the upper right point on the aperture grid
        Point3D upRight = _p0.add(newVUp.scale(_apertureN / 2 - 1 / 2)).add(newVRight.scale(_apertureN / 2 - 1 / 2));

        aperturePoints = new ArrayList<>(_apertureN * _apertureN);

        Vector startOf = newVUp.scale(_apertureN);
        newVUp = newVUp.scale(-1);
        newVRight = newVRight.scale(-1);

        //finds all the points by the newVUp and newVRight vectors
        for (int i = 0; i < _apertureN; i++) {
            for (int j = 0; j < _apertureN; j++) {
                if (!upRight.equals(_p0))
                    aperturePoints.add(upRight);
                upRight = upRight.add(newVUp);
            }
            upRight = upRight.add(newVRight);
            upRight = upRight.add(startOf);
        }
        return aperturePoints;
    }

        /**
         * creates rays from the aperture for a specific pixel center
         *
         * @param nX number of pixels in view plane width
         * @param nY number of pixels in view plane height
         * @param j  distance of the intercept from the midpoint on the y-axis
         * @param i  distance of the intercept from the midpoint on the X-axis
         * @return rays from the aperture for a specific pixel center
         */

    public List<Ray> constructRaysThroughPixel2(int nX, int nY, int j, int i, Point3D center,int index) {
        double size= _apertureSize/index;
        int n= (int) (_apertureSize/index);


        n= (int) Math.pow(2,n);

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
        //ray from the camera to the pixel in the viewPlane
         Ray mainRay = new Ray(_p0, Pij.subtract(_p0));
        //calculates the focus point on ray continuation
        double distCameraVP = _p0.distance(Pij);
        double distCameraFP = distCameraVP * _focalDist / _distance;
        Point3D focalPoint = mainRay.getPoint(distCameraFP);

        //calculates the rays from the aperture
        List<Ray> apertureRays = new LinkedList<>();
        List<Point3D> corners=cornerPoints(size,center,n);
        for (Point3D p : corners)
            apertureRays.add(new Ray(p, focalPoint.subtract(p)));
        return apertureRays;
    }

    public Point3D calcCenter(int index, int iter){
        //vectors in size of one unit of the aperture grid
        Vector newVUp = _vUp.scale(_apertureSize / _apertureN);
        Vector newVRight = _vRight.scale(_apertureSize / _apertureN);

        int n=_apertureN/iter;

        if (index==2||index==4)
            newVUp=newVUp.scale(-1);

        if (index==1||index==3)
            newVRight=newVRight.scale(-1);

        return _p0.add(newVUp.scale(n / 2 )).add(newVRight.scale(n / 2 ));
    }

    public List<Point3D> cornerPoints(double size, Point3D center, int n) {

        //vectors in size of one unit of the aperture grid
        Vector newVUp = _vUp.scale(size / n);
        Vector newVRight = _vRight.scale(size /n);

        //the upper right point on the aperture grid
        Point3D upRight = center.add(newVUp.scale(n / 2 - 1 / 2)).add(newVRight.scale(n / 2 - 1 / 2));
        Point3D downRight = center.add(newVUp.scale(-n / 2 + 1 / 2)).add(newVRight.scale(n / 2 - 1 / 2));
        Point3D downLeft = center.add(newVUp.scale(-n / 2 + 1 / 2)).add(newVRight.scale(-n / 2 + 1 / 2));
        Point3D upLeft = center.add(newVUp.scale(n / 2 - 1 / 2)).add(newVRight.scale(-n / 2 + 1 / 2));

        return List.of(upRight,downRight, downLeft, upLeft);
    }
}
