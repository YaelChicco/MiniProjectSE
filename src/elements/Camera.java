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
    private double _height;

    /**
     * size of the aperture
     */
    private double _apertureSize;

    /**
     * all the start points of the rays from the aperture
     */
    private List<Point3D> aperturePoints;

    /**
     * matrix of all the points on the aperture
     */
    private Point3D[][] pointsMat;

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
     * @param height view plane hight
     * @return "this": camera current instance
     */
    public Camera setViewPlaneSize(double width, double height) {
        _height = height;
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

    /**
     * setter of the aperture
     *
     * @param apertureSize- size of the aperture
     * @param apertureN-    amount of pixels in a row/column in the aperture
     * @return the object itself
     */
    public Camera setAperture(double apertureSize, int apertureN) {
        _apertureSize = apertureSize;
        _apertureN = (int) Math.pow(2, apertureN);
        pointsMat = new Point3D[_apertureN][_apertureN];
        aperturePoints = new ArrayList<>(_apertureN * _apertureN);
        aperturePointsInit();
        return this;
    }

    /**
     * getter of the number of pixels in a row/column in the aperture
     */
    public int getApertureN() {
        return _apertureN;
    }

    /**
     * getter of point from the matrix
     *
     * @param x-index of the point in the mat
     * @param y-index of the point in the mat
     * @return point3D from the mat
     */
    public Point3D getPointByMat(int x, int y) {
        return pointsMat[x][y];
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
        double Ry = _height / nY;
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
        Point3D focalPoint = getFocusPoint(nX, nY, j, i);

        //calculates the rays from the aperture
        List<Ray> apertureRays = new LinkedList<>();
        for (Point3D p : aperturePoints)
            apertureRays.add(new Ray(p, focalPoint.subtract(p)));
        return apertureRays;
    }

    /**
     * finds all the points on the aperture grid
     */
    public void aperturePointsInit() {
        //vectors in size of one unit of the aperture grid
        Vector newVUp = _vUp.scale(_apertureSize / _apertureN);
        Vector newVRight = _vRight.scale(_apertureSize / _apertureN);

        //the upper right point on the aperture grid
        Point3D upRight = _p0.add(newVUp.scale(_apertureN / 2 - 1 / 2)).add(newVRight.scale(_apertureN / 2 - 1 / 2));

        Vector startOf = newVUp.scale(_apertureN);
        newVUp = newVUp.scale(-1);
        newVRight = newVRight.scale(-1);

        //finds all the points by the newVUp and newVRight vectors
        for (int i = 0; i < _apertureN; i++) {
            for (int j = 0; j < _apertureN; j++) {
                aperturePoints.add(upRight);
                pointsMat[i][j] = upRight;
                upRight = upRight.add(newVUp);
            }
            upRight = upRight.add(newVRight);
            upRight = upRight.add(startOf);
        }
    }

    /**
     * calculates the focal point
     *
     * @param nX number of pixels in view plane width
     * @param nY number of pixels in view plane height
     * @param j  distance of the intercept from the midpoint on the y-axis
     * @param i  distance of the intercept from the midpoint on the X-axis
     * @return the focal point
     */
    public Point3D getFocusPoint(int nX, int nY, int j, int i) {
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        double Ry = _height / nY;
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
        return mainRay.getPoint(distCameraFP);
    }
}
