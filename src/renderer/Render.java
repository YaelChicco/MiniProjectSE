package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

/**
 * Creating the color matrix of the image from the scene
 */
public class Render {

    /**
     * creates the image
     */
    private ImageWriter _imageWriter;
    /**
     * camera details
     */
    private Camera _camera;
    /**
     * ray tracer
     */
    private RayTracerBase _rayTracer;

    /**
     * setter of the image writer (builder)
     *
     * @param imageWriter imageWriter
     * @return render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * setter of camera (builder)
     *
     * @param camera camera details
     * @return render
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     * setter of the ray tracer (builder)
     *
     * @param basicRayTracer the ray tracer
     * @return rebder
     */
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    /**
     * calculates the ray and the color in each pixel
     */
    public void renderImage() {
        //one of the parameters is null
        if (_imageWriter == null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");
        if (_camera == null)
            throw new MissingResourceException("_camera is null", "Render", "Camera");
        if (_rayTracer == null)
            throw new MissingResourceException("_rayTracer is null", "Render", "RayTracerBase");

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                Color color = _rayTracer.traceRay(ray);
                _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * creates the grid of the image
     *
     * @param interval      amount of pixels between two lines
     * @param intervalColor grid color
     */
    public void printGrid(int interval, Color intervalColor) {
        if (_imageWriter == null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, intervalColor);
                }
            }
        }
    }

    /**
     * saves the image
     */
    public void writeToImage() {
        if (_imageWriter == null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");
        _imageWriter.writeToImage();
    }
}

