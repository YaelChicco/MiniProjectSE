package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter;
    private Camera _camera;
    private RayTracerBase _rayTracer;

    /**
     * chaining methods
     *
     * @param imageWriter
     * @return
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    public void renderImage() {
        if(_imageWriter==null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");
        if(_camera==null)
            throw new MissingResourceException("_camera is null", "Render", "Camera");
        if(_rayTracer==null)
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

    public void printGrid(int interval, Color intervalColor) {
        if(_imageWriter==null)
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

    public void writeToImage() {
        if(_imageWriter==null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");
        _imageWriter.writeToImage();
    }

}

